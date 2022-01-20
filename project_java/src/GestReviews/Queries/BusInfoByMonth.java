package GestReviews.Queries;

import java.util.Set;
import java.util.TreeSet;

import Domain.Interfaces.IReview;
import Services.Interfaces.IBusStats;

public class BusInfoByMonth implements IResult {

    private IBusStats busStats;
    private Set<RevUserMonth> revUserMonths;

    public BusInfoByMonth() {
        this.revUserMonths = new TreeSet<>();
    }

    public BusInfoByMonth(IBusStats busStats) {
        this();
        this.busStats = busStats;
    }

    public void addInfo(IReview r) {
        Integer month = r.getDate().getMonthValue();
        RevUserMonth rum = new RevUserMonth(month);

        if(this.revUserMonths.contains(rum)) {
           this.revUserMonths.stream().filter(x -> x.getMonth().equals(month)).findFirst().get().add(r);
        
        } else {
            rum.add(r);
            this.revUserMonths.add(rum);
        }
    }

    public Integer numMonths() {
        return this.revUserMonths.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("busId: " + busStats.getBusId() + "\n");
        revUserMonths.stream().forEach(x -> sb.append(x.toString()));
        return sb.toString();
    }
    
}
