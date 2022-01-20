package GestReviews.Queries;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import Domain.Interfaces.IBusiness;

public class BusNotReviewed implements IResult {
    
    private Set<IBusiness> busNotReviewed;
    private Integer numBusNRev;
    
    public BusNotReviewed() {
        Comparator<IBusiness> byNameOrID = (b1, b2) -> {
            if(b1.getName().compareTo(b2.getName()) != 0)
               return b1.getName().compareTo(b2.getName());
            
            return b1.getId().compareTo(b2.getId());
        };

        this.busNotReviewed = new TreeSet<>(byNameOrID);
        this.numBusNRev = 0;
    }

    public void addBusiness(IBusiness b) {
        if(busNotReviewed.add(b)) {
            this.numBusNRev++;
        }
    }

    public Integer getNumBusNRev() {
        return numBusNRev;
    }

    public Set<String> getBusNotReviewed() {
        return this.busNotReviewed.stream().map(x -> x.getId()).collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        this.busNotReviewed.stream().forEach(x -> sb.append(x.getId()+"\n"));
        sb.append("Negócios não avaliados: "+ this.numBusNRev);

        return sb.toString();
    }   

}
