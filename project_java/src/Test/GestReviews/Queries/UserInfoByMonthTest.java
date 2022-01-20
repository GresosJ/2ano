package Test.GestReviews.Queries;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Domain.Interfaces.IReview;
import Domain.Model.Review;
import GestReviews.Queries.UserInfoByMonth;
import Services.Repositories.UserStats;

public class UserInfoByMonthTest {

    private List<IReview> r;

    @BeforeEach
    void set_up() {
        r = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            r.add(new Review("review_id"+i, "user_id"+i, "business_id"+i, 0.0+i, i, i, i, LocalDateTime.now(), "text"+i));
        }
    }

    @Test
    void testAddInfo() {
        UserInfoByMonth uim = new UserInfoByMonth(new UserStats("userID1"));
        for (int i = 0; i < 100; i++) {
            uim.addInfo(r.get(i));
        }
        assertTrue(uim.numMonths() == 1);
    }

    @Test
    void testToString() {

    }
}
