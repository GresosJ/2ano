package GestReviews.Queries;

import java.util.Set;
import java.util.TreeSet;

import Domain.Interfaces.IReview;
import Services.Interfaces.IUserStats;

public class UserInfoByMonth implements IResult {

    private IUserStats userStats;
    private Set<RevBusMonth> revBusMonths;

    public UserInfoByMonth() {
        this.revBusMonths = new TreeSet<>();
    }

    public UserInfoByMonth(IUserStats userStats) {
        this();
        this.userStats = userStats;
    }

    public void addInfo(IReview r) {
        Integer month = r.getDate().getMonthValue();
        RevBusMonth rbm = new RevBusMonth(month);
        if (this.revBusMonths.contains(rbm)) {
            this.revBusMonths.stream().filter(x -> x.getMonth().equals(month)).findFirst().get().add(r);
        } else {
            rbm.add(r);
            this.revBusMonths.add(rbm);
        }
    }

    public Integer numMonths() {
        return this.revBusMonths.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("userID: " + userStats.getId() + "\n");
        revBusMonths.stream().forEach(x -> sb.append(x.toString()));
        return sb.toString();
    }

}
