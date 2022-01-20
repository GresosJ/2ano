package Domain.Model;

import java.io.Serializable;

import Domain.Interfaces.IClassification;
import Domain.Interfaces.IReview;

public class Classification implements IClassification, Serializable {

    private Double stars;
    private Double funny;
    private Double cool;
    private Double useful;
    private Integer total;

    public Classification() {
        this.stars = 0.0;
        this.funny = 0.0;
        this.cool = 0.0;
        this.useful = 0.0;
        this.total = 0;
    }

    public Classification(Double stars, Double funny, Double cool, Double useful) {
        this.stars = stars;
        this.funny = funny;
        this.cool = cool;
        this.useful = useful;
        this.total = 1;
    }

    public Classification(Classification c) {
        this.stars = c.getStars();
        this.funny = c.getFunny();
        this.cool = c.getCool();
        this.useful = c.getUseful();
        this.total = c.total;
    }

    /**
     * Cálcula a média ponderada entre dois valores
     * 
     * @see https://matematicabasica.net/media-ponderada/
     * @param old Valor antigo da média
     * @param add Novo valor a acrescenter
     * @return Média ponderada entre os dois
     */
    private Double calculateClassification(Double old, Double add) {
        return (old * this.total + add) / (this.total + 1);
    }

    /**
     * Calcula a média ponderada entre dois valores com pesos diferentes
     * 
     * @param d1 Valor 1
     * @param s1 Peso valor 1
     * @param d2 Valor 2
     * @param s2 Peso valor 2
     * @return
     */
    private Double calculateClassification(Double d1, Integer s1, Double d2, Integer s2) {

        return (s1 + s2) == 0 ? 0.0 : (d1 * s1 + d2 * s2) / (s1 + s2);
    }

    @Override
    public void addClassification(Double stars, Double funny, Double useful, Double cool) {
        this.stars = calculateClassification(this.stars, stars);
        this.funny = calculateClassification(this.funny, funny);
        this.cool = calculateClassification(this.cool, useful);
        this.useful = calculateClassification(this.useful, cool);
        this.total++;
    }

    @Override
    public void addClassification(IReview r) {
        this.addClassification(r.getStars(), r.getFunny().doubleValue(), r.getUseful().doubleValue(),
                r.getCool().doubleValue());
    }

    @Override
    public IClassification addClassification(IClassification c) {
        Classification c_ = (Classification) c;

        this.stars = calculateClassification(this.stars, this.total, c_.stars, c_.total);
        this.funny = calculateClassification(this.funny, this.total, c_.funny, c_.total);
        this.useful = calculateClassification(this.useful, this.total, c_.useful, c_.total);
        this.useful = calculateClassification(this.useful, this.total, c_.useful, c_.total);
        this.total += c_.total;

        return this;
    }

    @Override
    public Double getCool() {
        return this.cool;
    }

    @Override
    public Double getFunny() {
        return this.funny;
    }

    @Override
    public Double getStars() {
        return this.stars;
    }

    @Override
    public Double getUseful() {
        return this.useful;
    }

    @Override
    public Integer getTotal() {
        return this.total;
    }

    @Override
    public IClassification clone() {
        return new Classification(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Stars: " + this.getStars() + "\n");
        sb.append("Cool: " + this.getCool() + "\n");
        sb.append("Funny: " + this.getFunny() + "\n");
        sb.append("Usefull: " + this.getUseful() + "\n");
        return sb.toString();
    }

}
