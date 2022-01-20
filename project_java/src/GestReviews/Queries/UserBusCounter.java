package GestReviews.Queries;

import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleEntry;

import Domain.Interfaces.IBusiness;

public class UserBusCounter implements IResult {
    private String userId;
    private Set<Entry<String, Integer>> busQnt;

    public UserBusCounter(String userId) {
        this.userId = userId;
        this.busQnt = new TreeSet<>((x, y) -> { // ordenar por contador -> nome
            if (y.getValue().compareTo(x.getValue()) != 0)
                return y.getValue().compareTo(x.getValue());
            else
                return x.getKey().compareTo(y.getKey());
        });
    }

    public void addBus(IBusiness b, Integer c) {
        Entry<String, Integer> e = new SimpleEntry<>(b.getName(), c);
        busQnt.add(e);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User ID: " + userId + "\n");
        busQnt.stream().forEach(x -> sb.append(x.getKey() + " - " + x.getValue() + "\n"));
        return sb.toString();
    }

}
