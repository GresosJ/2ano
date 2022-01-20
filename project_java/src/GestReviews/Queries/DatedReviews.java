package GestReviews.Queries;

import java.util.Set;
import java.util.TreeSet;

import Domain.Interfaces.IReview;
import Domain.Interfaces.IUser;

public class DatedReviews implements IResult {

    private Set<IReview> totalReviews;
    private Set<IUser> totalUsers;
    private Integer numTotalRevs;
    private Integer numTotalUsers;

    public DatedReviews() {
        this.totalReviews = new TreeSet<>();
        this.totalUsers = new TreeSet<>();
        this.numTotalRevs = 0;
        this.numTotalUsers = 0;
    }

    public void addReview(IReview r) {
        if(totalReviews.add(r)) {
            this.numTotalRevs++;
        }
    }

    public void addUser(IUser u) {
        if(totalUsers.add(u)) {
            this.numTotalUsers++;
        }
    }

    public Integer getNumTotalRevs() {
        return this.numTotalRevs;
    }

    public Integer getNumTotalUsers() {
        return this.numTotalUsers;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Total de reviews: " + this.numTotalRevs + "\n");
        sb.append("Total de Users: " + this.numTotalUsers + "\n");

        return sb.toString();
    }

    
}
