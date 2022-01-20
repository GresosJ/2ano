package GestReviews.Queries;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import Domain.Interfaces.IClassification;
import Domain.Interfaces.IReview;
import Domain.Model.Classification;
import Services.Interfaces.IUserStats;

public class UsersWithMostReviews implements IResult {

    private Set<IUserStats> users;
    private Map<IUserStats, Set<IReview>> userRevs;
    private Integer maxUsers;

    public UsersWithMostReviews(Integer maxUsers) {
        Comparator<IUserStats> byClassfOrID = (u1, u2) -> {
            if (u2.getClassification().getStars().compareTo(u1.getClassification().getStars()) != 0)
                return u2.getClassification().getStars().compareTo(u1.getClassification().getStars());

            return u2.getId().compareTo(u2.getId());
        };
        this.users = new TreeSet<>(byClassfOrID);
        this.userRevs = new HashMap<>();
        this.maxUsers = maxUsers;
    }

    public void add(IUserStats user, IReview r) {
        if (!users.contains(user)) {
            Set<IReview> busRevs = new TreeSet<>();

            userRevs.put(user, busRevs);
        }

        userRevs.get(user).add(r);
        users.add(user);
    }

    public Map<IUserStats, IClassification> getClassification() {

        return this.userRevs.entrySet().stream()
                .collect(Collectors.toMap(Entry::getKey,
                        x -> x.getValue().stream()
                                .map(y -> (IClassification) new Classification(y.getStars(), y.getFunny().doubleValue(),
                                        y.getCool().doubleValue(), y.getUseful().doubleValue()))
                                .reduce(new Classification(), (acc, cur) -> acc.addClassification(cur))));

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        getClassification().entrySet().stream().limit(this.maxUsers).forEach(x -> {
            sb.append(x.getKey().getId() + ":\n");
            sb.append(x.getValue().toString());
        });
        return sb.toString();
    }
}
