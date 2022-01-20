package Services.Repositories;

import java.io.Serializable;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import Domain.Interfaces.IClassification;
import Domain.Interfaces.IReview;
import Domain.Model.Classification;
import Middleware.Domain.ReviewAlreadyExistsException;
import Middleware.Domain.UserNotFoundException;
import Services.Interfaces.IBusStats;

public class BusStats implements IBusStats, Serializable {

    private String busId; // identificador dos stats
    private Set<String> reviewsIds; // Set de reviewIds
    private Integer reviews; // total reviews
    private Map<String, Integer> userIds; // map<userId, contaReviewsFeitas>
    private Integer users; // unique users
    private IClassification classification;

    private BusStats() {
        reviewsIds = new TreeSet<>();
        userIds = new HashMap<>();
        classification = new Classification();
        users = 0;
        reviews = 0;
    }

    public BusStats(String busId) {
        this();
        this.busId = busId;
    }

    public BusStats(BusStats b) {
        this(b.busId);
        this.reviewsIds = b.getReviews();
        this.reviews = b.getTotalReviews();
        this.userIds = b.userIds.entrySet().stream().collect(Collectors.toMap(Entry::getKey, Entry::getValue));
        this.users = b.getTotalUsers();
        this.classification = b.getClassification();
    }

    @Override
    public String getBusId() {
        return busId;
    }

    /**
     * Adiciona um user id às stats: caso ainda não exista, faz put com valor 1 e
     * incrementa número de users únicos
     * 
     * @param userId Identificador do user
     */
    private void addUserId(String userId) {
        if (userIds.containsKey(userId)) {
            userIds.put(userId, userIds.get(userId) + 1);
        } else {
            userIds.put(userId, 1);
            users++;
        }
    }

    @Override
    public void addReview(IReview r) throws ReviewAlreadyExistsException {
        if (reviewsIds.contains(r.getReviewID())) {
            throw new ReviewAlreadyExistsException("Review " + r.toString() + "já existe!");
        }

        reviewsIds.add(r.getReviewID());
        addUserId(r.getUserID());
        classification.addClassification(r);
        reviews++;
    }

    @Override
    public Set<String> getReviews() {
        return this.reviewsIds.stream().collect(Collectors.toSet()); // não partilha apontador para Set!
    }

    @Override
    public List<String> getUsers() {
        return userIds.entrySet().stream().sorted((x, y) -> y.getValue().compareTo(x.getValue())).map(Entry::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public SimpleEntry<String, Integer> getUserCounter(String userId) throws UserNotFoundException {
        try {
            return new SimpleEntry<String, Integer>(userId, userIds.get(userId));
        } catch (NullPointerException e) {
            throw new UserNotFoundException("User não existe " + userId);
        }

    }

    @Override
    public List<SimpleEntry<String, Integer>> getUsersCounter() {
        return userIds.entrySet().stream().sorted((x, y) -> y.getValue().compareTo(x.getValue()))
                .map(x -> new SimpleEntry<>(x.getKey(), x.getValue())).collect(Collectors.toList());
    }

    @Override
    public Integer getTotalUsers() {
        return users;
    }

    @Override
    public Integer getTotalReviews() {
        return reviews;
    }

    @Override
    public IClassification getClassification() {
        return this.classification.clone();
    }

    @Override
    public IBusStats clone() {
        return new BusStats(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("BusID: " +this.getBusId() + "\nReview ids: ");
        sb.append(this.getReviews().toString() + "\nTotal de " + this.getTotalReviews() + ".\n");
        sb.append("Utilizadores que fizeram reviews: " + this.getUsers().toString() + "\nTotal: ");
        sb.append(this.getTotalUsers() + "\nClassificação média: \n");
        sb.append(this.classification.toString());
        return sb.toString();
    }

}
