package Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import Domain.Interfaces.IFileRead;
import GestReviews.GestReviews;
import GestReviews.IGestReviews;
import GestReviews.Queries.IResult;
import Middleware.Domain.BusinessNotFoundException;
import Middleware.Domain.LoadCSVHasErrorsException;
import Middleware.Domain.MonthIsNotValidException;
import Middleware.Domain.ReviewNotFoundException;
import Middleware.Domain.UserNotFoundException;
import Middleware.Domain.YearIsNotValidException;

public class Controller implements IController {

    public static final String USERS_CSV = "db/users.csv";
    public static final String BUS_CSV = "db/business.csv";
    public static final String REVIEWS_CSV = "db/reviews.csv";

    private IGestReviews gestReviews;

    public Controller() {
        this.gestReviews = new GestReviews();
    }

    public Controller(IGestReviews gestReviews) {
        this.gestReviews = gestReviews;
    }

    @Override
    public boolean isFirstUse() {
        return this.gestReviews.numFilesRead() == 0;
    }

    @Override
    public void loadCSVFiles(String users, String business, String reviews)
            throws IOException, FileNotFoundException, LoadCSVHasErrorsException {

        this.gestReviews.loadFromCSV(users, business, reviews);

    }

    @Override
    public void saveState(String filepath) throws IOException {
        this.gestReviews.saveToBinary(filepath);

    }

    @Override
    public void loadState(String filepath) throws IOException, FileNotFoundException, ClassNotFoundException {
        this.gestReviews = GestReviews.loadFromBinary(filepath);

    }

    @Override
    public List<IResult> getFilesRead() {
        return null;
    }

    @Override
    public IFileRead getFileRead(String fileName) throws FileNotFoundException {
        return gestReviews.getLastFileRead();
    }

    @Override
    public IResult getFileRead() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IResult getBusNotReviewed() {
        return this.gestReviews.getBusNotReviewed();
    }

    @Override
    public IResult getDatedReviews(Integer year, Integer month)
            throws UserNotFoundException, ReviewNotFoundException, YearIsNotValidException, MonthIsNotValidException {
        return this.gestReviews.getDatedReviews(year, month);
    }

    @Override
    public IResult getUserInfoByMonth(String userID)
            throws UserNotFoundException, ReviewNotFoundException, BusinessNotFoundException {
        return this.gestReviews.getUserInfoByMonth(userID);
    }

    @Override
    public IResult getBusInfoByMonth(String busID) throws ReviewNotFoundException, BusinessNotFoundException {
        return this.gestReviews.getBusInfoByMonth(busID);
    }

    @Override
    public IResult getUserMostReviewedBus(String userId) throws UserNotFoundException {
        return this.gestReviews.getUserBus(userId);
    }

    @Override
    public IResult getXMostReviewedBusByYear(Integer n) {
        return this.gestReviews.getMostReviewedBusYear(n);
    }

    @Override
    public IResult getMostFamousBusByCity() {

        return this.gestReviews.getMostFamousBusByCity();
    }

    @Override
    public IResult getXUsersMostDifBus(Integer n) {
        return this.gestReviews.getMostActiveUsers(n);
    }

    @Override
    public IResult getXUsersWMostRevs(String busID, Integer userNum) throws BusinessNotFoundException {

        return this.gestReviews.getXUsersWMostRevs(busID, userNum);
    }

    @Override
    public IResult getBusAvgByStateNCity() {
        return this.gestReviews.getAvgStateCity();
    }

}
