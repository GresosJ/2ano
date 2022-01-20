package Domain.Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

import Domain.Interfaces.IReview;

public class Review implements IReview, Serializable, Comparable<Review> {


    private String review_id;
    private String user_id;
    private String business_id;
    private Double stars;
    private Integer useful;
    private Integer funny;
    private Integer cool;
    private LocalDateTime date;
    private String text;

    public Review() {
        this.review_id = "";
        this.user_id = "";
        this.business_id = "";
        this.stars = 0.0;
        this.useful = 0;
        this.funny = 0;
        this.cool = 0;
        this.date = LocalDateTime.now();
        this.text = "";
    }

    public Review(String review_id, String user_id, String business_id, Double stars, Integer useful, Integer funny,
            Integer cool, LocalDateTime date, String text) {
        this.review_id = review_id;
        this.user_id = user_id;
        this.business_id = business_id;
        this.stars = stars;
        this.useful = useful;
        this.funny = funny;
        this.cool = cool;
        this.date = date;
        this.text = text;
    }

    public Review(Review r) {
        this.review_id = r.getReviewID();
        this.user_id = r.getUserID();
        this.business_id = r.getBusID();
        this.stars = r.getStars();
        this.useful = r.getUseful();
        this.funny = r.getFunny();
        this.cool = r.getCool();
        this.date = r.getDate();
        this.text = r.getText();
    }

    public Review(String input) throws IllegalArgumentException {
        String tokens[] = input.split(";", 9);

        if(tokens.length != 9) {
            throw new IllegalArgumentException("O número de Strings está errado!");
        }

        this.review_id = tokens[0];
        this.user_id = tokens[1];
        this.business_id = tokens[2];
        try {
            
            this.stars = Double.parseDouble(tokens[3]);
        } catch (NullPointerException | NumberFormatException e) {
            this.stars = 0.0;
        }
        try {
            
            this.useful = Integer.parseInt(tokens[4]);
        } catch (NumberFormatException e) {
            this.useful = 0;
        }
        try {
            
            this.funny = Integer.parseInt(tokens[5]);
        } catch (NumberFormatException e) {
            this.funny = 0;
        }
        try {
            
            this.cool = Integer.parseInt(tokens[6]);
        } catch (NumberFormatException e) {
            this.cool = 0;
        }
        try {
            this.date = LocalDateTime.parse(tokens[7].replace(' ', 'T'));
        } catch (DateTimeParseException e) {
            this.date = LocalDateTime.now();
        }
        this.text = tokens[8];
    }

    public String getReviewID() {
        return this.review_id;
    }

    public void setReviewID(String review_id) {
        this.review_id = review_id;
    }

    public String getUserID() {
        return this.user_id;
    }

    public void setUserID(String user_id) {
        this.user_id = user_id;
    }

    public String getBusID() {
        return this.business_id;
    }

    public void setBusID(String bus_id) {
        this.business_id = bus_id;
    }

    public Double getStars() {
        return this.stars;
    }

    public void setStars(Double stars) {
        this.stars = stars;
    }

    public Integer getUseful() {
        return this.useful;
    }

    public void setUseful(Integer useful) {
        this.useful = useful;
    }

    public Integer getFunny() {
        return this.funny;
    }

    public void setFunny(Integer funny) {
        this.funny = funny;
    }

    public Integer getCool() {
        return this.cool;
    }

    public void setCool(Integer cool) {
        this.cool = cool;
    }

    @Override
    public boolean isUsefull() {
        return this.getCool() > 0 && this.getUseful() > 0 && this.getFunny() > 0;
    }

    public LocalDateTime getDate() {
        return this.date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (obj == null)
            return false;

        if (!this.getClass().getSimpleName().equals(obj.getClass().getSimpleName()))
            return false;

        Review r = (Review) obj;
        return this.getReviewID().equals(r.getReviewID());
    }

    @Override
    public Review clone() {
        return new Review(this);
    }

    @Override
    public int compareTo(Review o) {
        return this.getReviewID().compareTo(o.getReviewID());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[] { this.getReviewID() });
    }

}
