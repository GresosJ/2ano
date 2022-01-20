package GestReviews.Queries;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleEntry;

import Services.Interfaces.IBusStats;

public class MostRevByYear implements IResult {
    private Set<Entry<Integer, List<IBusStats>>> revByYr;

    public MostRevByYear() {
        this.revByYr = new TreeSet<>((x, y) -> x.getKey().compareTo(y.getKey()));
    }

    public void add(Integer yr, List<IBusStats> lst) {
        Entry<Integer, List<IBusStats>> e = new SimpleEntry<>(yr, lst);
        this.revByYr.add(e);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        this.revByYr.stream().forEach(x -> {
            sb.append("\nAno: " + x.getKey());
            sb.append("Mais avaliados:\n");
            x.getValue().stream().forEach(y -> {
                sb.append("busID: " + y.getBusId());
                sb.append(" - " + y.getTotalUsers() + " avaliaram.\n");
            });
        });
        return sb.toString();
    }

}
