package GestReviews.Queries;

import java.util.HashMap;
import java.util.Map;

import Domain.Interfaces.IClassification;

public class CityStateAvg implements IResult {
    private Map<String, Map<String, IClassification>> avg;

    public CityStateAvg() {
        this.avg = new HashMap<>();
    }

    public CityStateAvg(Map<String, Map<String, IClassification>> avg) {
        this.avg = avg;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        avg.entrySet().stream().forEach(x -> {
            sb.append("State: " + x.getKey() + "\n");
            x.getValue().entrySet().stream().forEach(y -> {
                sb.append(y.getKey() + ":\n");
                sb.append(y.getValue().toString());
            });
        });
        return sb.toString();
    }

}
