package GestReviews.Queries;

import java.util.Set;
import java.util.TreeSet;

import Domain.Interfaces.IClassification;
import Domain.Interfaces.IReview;
import Domain.Model.Classification;

public class RevBusMonth implements Comparable<RevBusMonth>, IResult {
    private Integer month;
    private Integer numRev;
    private Integer numBus;
    private Set<String> busIds;
    private IClassification classification;

    public RevBusMonth(Integer month) {
        this.month = month;
        this.numRev = 0;
        this.numBus = 0;
        this.classification = new Classification();
        this.busIds = new TreeSet<>();
    }

    public Integer getMonth() {
        return this.month;
    }

    public void add(IReview r) {
        this.numRev++;
        if (!busIds.contains(r.getBusID()))
            this.numBus++;
        this.classification.addClassification(r);
    }

    public Integer getNumRev() {
        return numRev;
    }

    public Integer getNumBus() {
        return numBus;
    }

    public IClassification getClassification() {
        return classification.clone();
    }

    @Override
    public int compareTo(RevBusMonth o) {
        return this.getMonth().compareTo(o.getMonth());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Mês: " + this.getMonth() + "\n");
        sb.append("Número reviews: " + this.getNumRev() + "\n");
        sb.append("Número business distintos: " + this.getNumBus() + "\n");
        sb.append("Classificação média:\n" + this.classification.toString());
        return sb.toString();
    }

}
