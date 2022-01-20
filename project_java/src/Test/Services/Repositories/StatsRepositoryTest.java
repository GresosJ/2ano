package Test.Services.Repositories;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Domain.Interfaces.IBusiness;
import Domain.Interfaces.IReview;
import Domain.Interfaces.IUser;
import Domain.Model.Business;
import Domain.Model.KeyValueSet;
import Domain.Model.Review;
import Domain.Model.User;
import Middleware.Domain.BusinessAlreadyExistsException;
import Middleware.Domain.BusinessNotFoundException;
import Middleware.Domain.MonthIsNotValidException;
import Middleware.Domain.ReviewAlreadyExistsException;
import Middleware.Domain.UserAlreadyExistsException;
import Middleware.Domain.UserNotFoundException;
import Middleware.Domain.YearIsNotValidException;
import Services.Interfaces.IStatsRepository;
import Services.Repositories.StatsRepository;

public class StatsRepositoryTest {
    private List<IUser> lu;
    private List<IBusiness> lb;
    private List<IReview> lr;
    private IStatsRepository statsRepository;
    private Map<Integer, Set<KeyValueSet<Integer, String>>> revByYear;

    @BeforeEach
    public void set_up() {
        lu = new ArrayList<>();
        lb = new ArrayList<>();
        lr = new ArrayList<>();
        statsRepository = new StatsRepository();
        for (int i = 0; i < 100; i++) {
            lu.add(new User("" + i, "name" + i, new ArrayList<>()));
            lb.add(new Business(
                    "6iYb2HFDywm3zjuRg0shjw;Oskar Blues Taproom;Boulder;CO;Gastropubs,Food,BeerGardens,Restaurants,Bars,American(Traditional),BeerBar,Nightlife,Breweries"));
            lr.add(new Review("" + i, "" + i, "6iYb2HFDywm3zjuRg0shjw", 0.0 + i, i, i, i,
                    LocalDateTime.now().plusYears(i), "text" + i));
        }
    }

    @Test
    void testAddStatsBus() {
        for (int i = 0; i < 100; i++) {
            try {
                statsRepository.addStatsBus(lb.get(i));
            } catch (BusinessAlreadyExistsException e) {
            }
        }
        assertTrue(statsRepository.getTotalBus() == 100);
    }

    @Test
    void testAddStatsUser() {
        for (int i = 0; i < 100; i++) {
            try {
                statsRepository.addStatsUser(lu.get(i));
            } catch (UserAlreadyExistsException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        assertTrue(statsRepository.getTotalUsers() == 100);
    }

    @Test
    void testAddStatsReview() throws YearIsNotValidException, MonthIsNotValidException {
        for (int i = 0; i < 100; i++) {
            try {
                statsRepository.addStatsUser(lu.get(i));
                statsRepository.addStatsBus(lb.get(i));
            } catch (UserAlreadyExistsException e) {
            } catch (BusinessAlreadyExistsException e) {
            }
        }
        for (int j = 0; j < 100; j++) {
            try {
                statsRepository.addStatsReview(lr.get(j));
            } catch (UserNotFoundException | BusinessNotFoundException | ReviewAlreadyExistsException e) {
            }

        }
        assertTrue(statsRepository.getTotalReviews() == 100);
        assertTrue(statsRepository.getUselessReviews() == 1);

        assertThrows(UserNotFoundException.class, () -> statsRepository
                .addStatsReview(new Review("101", "200", "200", 2.0, 2, 5, 6, LocalDateTime.now(), "text")));

        assertThrows(BusinessNotFoundException.class, () -> statsRepository
                .addStatsReview(new Review("101", "99", "200", 2.0, 2, 5, 6, LocalDateTime.now(), "text")));
        assertTrue(statsRepository.getReviewsYearMonth(2025, 6).size() == 1);
    }

    @Test
    void testGetBusReviews() throws UserAlreadyExistsException, BusinessAlreadyExistsException, UserNotFoundException,
            BusinessNotFoundException, ReviewAlreadyExistsException {
        statsRepository.addStatsUser(lu.get(0));
        statsRepository.addStatsBus(lb.get(0));
        statsRepository.addStatsReview(lr.get(0));
        Set<String> getter = statsRepository.getBusReviews("6iYb2HFDywm3zjuRg0shjw");
        assertTrue(getter.size() == 1);

    }

    @Test
    void testMonthYearSet() {
        Comparator<KeyValueSet<Integer, String>> cp = (x, y) -> x.getKey().compareTo(y.getKey());
        this.revByYear = new HashMap<>();
        this.revByYear.put(LocalDateTime.now().getYear(), new TreeSet<>(cp));
        this.revByYear.get(LocalDateTime.now().getYear())
                .add(new KeyValueSet<Integer, String>(LocalDateTime.now().getMonthValue()));
        KeyValueSet<Integer, String> s = this.revByYear.get(LocalDateTime.now().getYear()).stream()
                .filter(x -> x.getKey().compareTo(LocalDateTime.now().getMonthValue()) == 0).findFirst().get();
        s.addValue("id1");
    }

}
