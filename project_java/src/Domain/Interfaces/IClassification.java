package Domain.Interfaces;

public interface IClassification {
    public Double getStars();

    public Double getFunny();

    public Double getUseful();

    public Double getCool();

    public Integer getTotal();

    public void addClassification(Double stars, Double funny, Double useful, Double cool);

    public void addClassification(IReview r);

    public IClassification addClassification(IClassification c);

    public IClassification clone();
}
