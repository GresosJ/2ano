package Services.Repositories;

import java.io.Serializable;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import Domain.Interfaces.IClassification;
import Domain.Interfaces.IReview;
import Domain.Model.Classification;
import Middleware.Domain.BusinessNotFoundException;
import Middleware.Domain.ReviewAlreadyExistsException;
import Services.Interfaces.IUserStats;

public class UserStats implements IUserStats, Serializable {

    private String userId; // identificador dos stats
    private Set<String> reviewsIds; // Set de reviewIds
    private Integer reviews; // total reviews
    private Map<String, Integer> busIds; // map<busId, contaReviewsFeitas>
    private Integer business; // unique users
    private Map<String, Set<String>> busRevs; // map<busId, set<revIds>>
    private IClassification classification;

    public UserStats() {
        reviewsIds = new TreeSet<>();
        busIds = new HashMap<>();
        classification = new Classification();
        business = 0;
        busRevs = new HashMap<>();
        reviews = 0;
    }

    public UserStats(String userId) {
        this();
        this.userId = userId;
    }

    public UserStats(UserStats us) {
        this(us.getId());
        this.reviewsIds = us.getReviews();
        this.reviews = us.getTotalReviews();
        this.busIds = us.busIds.entrySet().stream().collect(Collectors.toMap(Entry::getKey, Entry::getValue));
        this.business = us.getTotalBus();
        this.busRevs = us.busRevs.entrySet().stream().collect(Collectors.toMap(Entry::getKey, Entry::getValue));
        this.classification = us.getClassification();
    }

    @Override
    public String getId() {
        return userId;
    }

    /**
     * Adiciona um busID e os seu respetivos reviewIDs
     * 
     * @param r Review a ser adicionado
     */
    private void addBusRev(IReview r){
        if(!this.busRevs.containsKey(r.getBusID())) {
            Set<String> revs = new TreeSet<>();

            this.busRevs.put(r.getBusID(), revs);
        }

        this.busRevs.get(r.getBusID()).add(r.getReviewID());
    }

    /**
     * Adiciona um bus id às stats: caso ainda não exista, faz put com valor 1 e
     * incrementa número de bus únicos
     * 
     * @param userId Identificador do user
     */
    private void addBusId(String busId) {
        if (busIds.containsKey(busId)) {
            busIds.put(busId, busIds.get(busId) + 1);
        } else {
            busIds.put(busId, 1);
            business++;
        }
    }

    @Override
    public void addReview(IReview r) throws ReviewAlreadyExistsException {
        if (reviewsIds.contains(r.getReviewID())) {
            throw new ReviewAlreadyExistsException("Review " + r.toString() + "já existe!");
        }

        reviewsIds.add(r.getReviewID());
        addBusId(r.getBusID());
        addBusRev(r);
        classification.addClassification(r);
        reviews++;

    }

    @Override
    public Set<String> getReviews() {
        return this.reviewsIds.stream().collect(Collectors.toSet());
    }

    @Override
    public List<String> getBusiness() {
        return this.busIds.entrySet().stream().sorted((x, y) -> y.getValue().compareTo(x.getValue())).map(Entry::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public SimpleEntry<String, Integer> getBusCounter(String busId) throws BusinessNotFoundException {
        try {
            return new SimpleEntry<>(busId, this.busIds.get(busId));
        } catch (NullPointerException e) {
            throw new BusinessNotFoundException("O business não existe. ID: " + busId);
        }
    }

    @Override
    public List<SimpleEntry<String, Integer>> getBusCounter() {
        return busIds.entrySet().stream().sorted((x, y) -> y.getValue().compareTo(x.getValue()))
                .map(x -> new SimpleEntry<>(x.getKey(), x.getValue())).collect(Collectors.toList());
    }

    @Override
    public Integer getTotalReviews() {
        return this.reviews;
    }
    
    @Override
    public Integer getTotalBus() {
        return business;
    }

    @Override
    public Set<String> getBusRevs(String busID) {
        return busRevs.get(busID);
    }

    @Override
    public IClassification getClassification() {
        return this.classification.clone();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id do user: " + this.getId() + "\nReview ids: ");
        sb.append(this.getReviews().toString() + "\nTotal de " + this.getTotalReviews() + ".");
        sb.append("Business que fez reviews: " + this.getBusiness().toString() + "\nTotal: ");
        sb.append(this.getTotalBus() + "\nClassificação média: \n");
        sb.append(this.classification.toString());
        return sb.toString();
    }

    @Override
    public IUserStats clone(){
        return new UserStats(this);
    }

}
