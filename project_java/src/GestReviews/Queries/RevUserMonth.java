package GestReviews.Queries;

import java.util.Set;
import java.util.TreeSet;

import Domain.Interfaces.IClassification;
import Domain.Interfaces.IReview;
import Domain.Model.Classification;

public class RevUserMonth implements Comparable<RevUserMonth>, IResult {

    private Integer month;
    private Integer numRev;
    private Integer numUser;
    private Set<String> userIds;
    private IClassification classification;

    public RevUserMonth(Integer month) {
        this.month = month;
        this.numRev = 0;
        this.numUser = 0;
        this.userIds = new TreeSet<>();
        this.classification = new Classification();
    }

    public Integer getMonth() {
        return this.month;
    }

    public void add(IReview r) {
        this.numRev++;
        if (!userIds.contains(r.getUserID()))
            this.numUser++;

        this.classification.addClassification(r);
    }

    public Integer getNumRev() {
        return numRev;
    }

    public Integer getNumUser() {
        return numUser;
    }

    public IClassification getClassification() {
        return classification.clone();
    }

    @Override
    public int compareTo(RevUserMonth o) {
        return this.getMonth().compareTo(o.getMonth());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Mês: " + this.getMonth() + "\n");
        sb.append("Número de reviews: " + this.getNumRev() + "\n");
        sb.append("Número users distintos: " + this.getNumUser() + "\n");
        sb.append("Classificação média:\n" + this.classification.toString());
        return sb.toString();
    }

}
