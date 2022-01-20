package GestReviews;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import Domain.Interfaces.IBusiness;
import Domain.Interfaces.IFileRead;
import Domain.Interfaces.IReview;
import Domain.Interfaces.IUser;
import Domain.Model.Business;
import Domain.Model.FileRead;
import Domain.Model.Review;
import Domain.Model.User;
import GestReviews.Queries.BusInfoByMonth;
import GestReviews.Queries.BusNotReviewed;
import GestReviews.Queries.CityStateAvg;
import GestReviews.Queries.DatedReviews;
import GestReviews.Queries.MostFamousBusByCity;
import GestReviews.Queries.MostRevByYear;
import GestReviews.Queries.UserBusCounter;
import GestReviews.Queries.UserInfoByMonth;
import GestReviews.Queries.UserMostBusDTO;
import GestReviews.Queries.UsersWithMostReviews;
import Middleware.Domain.BusinessAlreadyExistsException;
import Middleware.Domain.BusinessNotFoundException;
import Middleware.Domain.LoadCSVHasErrorsException;
import Middleware.Domain.MonthIsNotValidException;
import Middleware.Domain.ReviewAlreadyExistsException;
import Middleware.Domain.ReviewNotFoundException;
import Middleware.Domain.UserAlreadyExistsException;
import Middleware.Domain.UserNotFoundException;
import Middleware.Domain.YearIsNotValidException;
import Persistence.FileReader;
import Services.Interfaces.IBusStats;
import Services.Interfaces.IBusinessRepository;
import Services.Interfaces.IReviewRepository;
import Services.Interfaces.IStatsRepository;
import Services.Interfaces.IUserRepository;
import Services.Interfaces.IUserStats;
import Services.Repositories.BusinessRepository;
import Services.Repositories.ReviewRepository;
import Services.Repositories.StatsRepository;
import Services.Repositories.UserRepository;

public class GestReviews implements IGestReviews, Serializable {

    private IReviewRepository reviews;
    private IBusinessRepository businesses;
    private IUserRepository users;
    private IStatsRepository stats;

    public GestReviews() {
        this.reviews = new ReviewRepository();
        this.businesses = new BusinessRepository();
        this.users = new UserRepository();
        this.stats = new StatsRepository();
    }

    public GestReviews(IReviewRepository reviews, IBusinessRepository businesses, IUserRepository users,
            IStatsRepository statsRepository) {
        this();
        setReviews(reviews);
        setBusinesses(businesses);
        setUsers(users);
    }

    public GestReviews(GestReviews sgr) {
        this();
        setReviews(sgr.getReviews());
        setBusinesses(sgr.getBusinesses());
        setUsers(sgr.getUsers());
    }

    @Override
    public void addBusiness(IBusiness b) throws BusinessAlreadyExistsException {
        stats.addStatsBus(b);
        businesses.addBusiness(b);
    }

    @Override
    public void addBusiness(List<IBusiness> lb) throws BusinessAlreadyExistsException {
        boolean duplicates = false;
        for (IBusiness b : lb) {
            try {
                addBusiness(b);

            } catch (BusinessAlreadyExistsException e) {
                duplicates = true;
            }
        }
        if (duplicates)
            throw new BusinessAlreadyExistsException("Havia business que já existiam!");
    }

    @Override
    public void addReview(IReview r)
            throws UserNotFoundException, BusinessNotFoundException, ReviewAlreadyExistsException {
        stats.addStatsReview(r);
        reviews.addReview(r);

    }

    @Override
    public void addReview(List<IReview> lr) throws ReviewAlreadyExistsException {
        boolean duplicates = false;
        for (IReview r : lr) {
            try {
                addReview(r);
            } catch (UserNotFoundException | BusinessNotFoundException | ReviewAlreadyExistsException e) {
                duplicates = true;
            }
        }
        if (duplicates)
            throw new ReviewAlreadyExistsException("Houve erro nas reviews");

    }

    @Override
    public void addUser(IUser u) throws UserAlreadyExistsException {
        stats.addStatsUser(u);
        users.addUser(u);

    }

    @Override
    public void addUser(List<IUser> lu) throws UserAlreadyExistsException {
        boolean duplicates = false;
        for (IUser u : lu) {
            try {
                addUser(u);
            } catch (UserAlreadyExistsException e) {
                duplicates = true;
            }
        }
        if (duplicates)
            throw new UserAlreadyExistsException("Havia users repetidos!");
    }

    @Override
    public IBusiness getBusiness(String id) throws BusinessNotFoundException {
        return businesses.getBusiness(id);
    }

    @Override
    public IBusStats getBusinessStats(String id) throws BusinessNotFoundException {
        return this.stats.getBusStats(id);
    }

    @Override
    public IReview getReview(String id) throws ReviewNotFoundException {
        return reviews.getReview(id);
    }

    @Override
    public IUser getUser(String id) throws UserNotFoundException {
        return users.getUser(id);
    }

    @Override
    public IUserStats getUserStats(String id) throws UserNotFoundException {
        return this.stats.getUserStats(id);
    }

    // Query 1

    public BusNotReviewed getBusNotReviewed() {
        BusNotReviewed bnr = new BusNotReviewed();

        Predicate<IBusStats> bs = x -> x.getTotalReviews() == 0;

        this.stats.iterateBusStats(bs).stream().map(x -> {
            try {
                return getBusiness(x.getBusId());
            } catch (BusinessNotFoundException e) {
                return null;
            }
        }).forEach(x -> {
            if (x != null) {
                bnr.addBusiness(x);
            }
        });

        return bnr;
    }

    // Query 2

    public DatedReviews getDatedReviews(Integer year, Integer month)
            throws UserNotFoundException, ReviewNotFoundException, YearIsNotValidException, MonthIsNotValidException {
        DatedReviews dr = new DatedReviews();

        for (String revID : this.stats.getReviewsYearMonth(year, month)) {
            IReview r = getReview(revID);

            dr.addReview(r);
            dr.addUser(getUser(r.getUserID()));
        }

        return dr;
    }

    // Query 3

    public UserInfoByMonth getUserInfoByMonth(String userID)
            throws UserNotFoundException, ReviewNotFoundException, BusinessNotFoundException {

        IUserStats userStats = this.stats.getUserStats(userID);
        UserInfoByMonth uim = new UserInfoByMonth(userStats);

        for (String reviewID : userStats.getReviews()) {
            IReview r = getReview(reviewID);
            uim.addInfo(r);
        }

        return uim;
    }

    // Query 4

    public BusInfoByMonth getBusInfoByMonth(String busID) throws ReviewNotFoundException, BusinessNotFoundException {

        IBusStats busStats = this.stats.getBusStats(busID);
        BusInfoByMonth bim = new BusInfoByMonth(busStats);

        for (String reviewID : busStats.getReviews()) {
            IReview r = getReview(reviewID);
            bim.addInfo(r);
        }

        return bim;
    }

    // Query 5

    public UserBusCounter getUserBus(String userId) throws UserNotFoundException {
        UserBusCounter ubc = new UserBusCounter(userId);
        IUserStats us = stats.getUserStats(userId);
        us.getBusCounter().stream().forEach(x -> {
            try {
                ubc.addBus(this.getBusiness(x.getKey()), x.getValue());
            } catch (BusinessNotFoundException e) {
                // não faz nada
            }
        });

        return ubc;
    }

    // Query 6

    public MostRevByYear getMostReviewedBusYear(Integer n) {
        MostRevByYear mrby = new MostRevByYear();
        Map<Integer, List<IBusStats>> lst = stats.getStatsbyYear(n);
        lst.entrySet().forEach(x -> {
            mrby.add(x.getKey(), x.getValue());
        });
        return mrby;
    }

    // Query 7

    public MostFamousBusByCity getMostFamousBusByCity() {

        MostFamousBusByCity mfc = new MostFamousBusByCity();

        this.stats.getCityBusiness().stream().forEach(x -> {
            String city = x.getKey();

            for (String b : x.getValues()) {
                try {
                    mfc.add(city, getBusinessStats(b));
                } catch (BusinessNotFoundException e) {
                    // Caso não exista não faz nada
                }
            }
        });

        return mfc;

    }

    // Query 8

    public UserMostBusDTO getMostActiveUsers(Integer n) {
        UserMostBusDTO umbDTO = new UserMostBusDTO(n);
        Comparator<IUserStats> c = (x, y) -> y.getTotalBus().compareTo(x.getTotalBus());
        List<IUserStats> lus = stats.iterateUsersStats(c);

        lus.stream().limit(n).forEach(x -> umbDTO.add(x));

        return umbDTO;
    }

    // Query 9

    public UsersWithMostReviews getXUsersWMostRevs(String busID, Integer maxUsers) throws BusinessNotFoundException {

        UsersWithMostReviews umr = new UsersWithMostReviews(maxUsers);

        this.stats.getBusStats(busID).getUsers().stream().forEach(x -> {
            try {
                IUserStats userStats = getUserStats(x);

                for (String reviewID : userStats.getBusRevs(busID)) {
                    umr.add(userStats, getReview(reviewID));
                }
            } catch (UserNotFoundException | ReviewNotFoundException e) {
                // Não adiciona o user, caso não seja encontrado
            }

        });

        return umr;
    }

    // query 10
    @Override
    public CityStateAvg getAvgStateCity() {
        return new CityStateAvg(this.stats.getClassfStates());

    }

    public IReviewRepository getReviews() {
        return reviews.clone();
    }

    public void setReviews(IReviewRepository reviews) {
        this.reviews = reviews.clone();
    }

    public IBusinessRepository getBusinesses() {
        return businesses.clone();
    }

    public void setBusinesses(IBusinessRepository businesses) {
        this.businesses = businesses.clone();
    }

    public IUserRepository getUsers() {
        return users.clone();
    }

    public void setUsers(IUserRepository users) {
        this.users = users.clone();
    }

    @Override
    public Integer numFilesRead() {
        try {
            this.stats.getFileRead();
            return 1;
        } catch (NoSuchElementException e) {
            return 0;
        }
    }

    @Override
    public void loadFromCSV(String users, String business, String reviews) throws LoadCSVHasErrorsException {
        FileReader<IUser> fru = new FileReader<>(users);
        FileReader<IBusiness> frb = new FileReader<>(business);
        FileReader<IReview> frr = new FileReader<>(reviews);

        IFileRead fr = new FileRead(users);

        boolean success = true;
        List<String> lse = new ArrayList<>();

        List<IUser> allUsers = fru.readLinesConverted(s -> {
            try {
                return new User(s);
            } catch (IllegalArgumentException e) {
                fr.addUserWrong();
                return null;
            }
        });
        for (IUser user : allUsers) {
            if (user != null) {
                try {
                    addUser(user);
                    fr.addUser();
                } catch (UserAlreadyExistsException e) {
                    success = false;
                    fr.addUserWrong();
                    lse.add(e.getMessage());
                }
            }
        }

        fr.addFileName(business);
        List<IBusiness> allBus = frb.readLinesConverted(s -> {
            try {
                return new Business(s);
            } catch (IllegalArgumentException e) {
                return null;
            }
        });
        for (IBusiness bus : allBus) {
            if (bus != null) {
                try {
                    addBusiness(bus);
                    fr.addBus();
                } catch (BusinessAlreadyExistsException e) {
                    lse.add(e.getMessage());
                    success = false;
                }
            }
        }

        List<IReview> allRevs = frr.readLinesConverted(s -> {
            try {
                return new Review(s);
            } catch (IllegalArgumentException e) {
                fr.addWrongRev();
                return null;
            }
        });
        for (IReview rev : allRevs) {
            if (rev != null) {
                try {
                    addReview(rev);
                    fr.addRev();
                    if (!rev.isUsefull())
                        fr.addUselessReview();
                } catch (UserNotFoundException | BusinessNotFoundException | ReviewAlreadyExistsException e) {
                    lse.add(e.getMessage());
                    fr.addWrongRev();
                }

            }

        }

        stats.addFileRead(fr);
        if (!success) {
            throw new LoadCSVHasErrorsException("Houve erros no load dos ficheiros!" + lse.toString());
        }
    }

    @Override
    public IFileRead getLastFileRead() throws NoSuchElementException {
        return this.stats.getFileRead();
    }

    public void saveToBinary(String filepath) throws IOException, FileNotFoundException {
        FileOutputStream fos = new FileOutputStream(filepath);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.close();
    }

    public static IGestReviews loadFromBinary(String filepath)
            throws IOException, FileNotFoundException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(filepath);
        ObjectInputStream ois = new ObjectInputStream(fis);
        IGestReviews igr = (GestReviews) ois.readObject();
        ois.close();
        return igr;
    }

}
