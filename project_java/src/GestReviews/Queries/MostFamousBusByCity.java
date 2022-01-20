package GestReviews.Queries;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import Services.Interfaces.IBusStats;

public class MostFamousBusByCity implements IResult {

    private Map<String, Set<IBusStats>> busByCity;

    public MostFamousBusByCity() {
        this.busByCity = new HashMap<>();
    }

    public void add(String city, IBusStats bus) {

        if (!busByCity.containsKey(city)) {
            Comparator<IBusStats> byNumRevs = (b1, b2) -> b2.getTotalReviews().compareTo(b1.getTotalReviews());
            Set<IBusStats> busStats = new TreeSet<>(byNumRevs);

            this.busByCity.put(city, busStats);
        }

        this.busByCity.get(city).add(bus);

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        this.busByCity.entrySet().stream().forEach(x -> {
            sb.append("City: " + x.getKey() + "\n");
            x.getValue().stream().limit(3).map(IBusStats::toString).forEach(y -> {
                sb.append(y);
            });
        });

        return sb.toString();
    }

}
