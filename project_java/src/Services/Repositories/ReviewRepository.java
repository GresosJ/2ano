package Services.Repositories;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import Domain.Interfaces.IReview;
import Middleware.Domain.NoReviewsWithBusinessException;
import Middleware.Domain.ReviewAlreadyExistsException;
import Middleware.Domain.ReviewNotFoundException;
import Services.Interfaces.IReviewRepository;


public class ReviewRepository implements IReviewRepository, Serializable {
    private Map<String, IReview> reviews;

    public ReviewRepository(){
        this.reviews = new HashMap<>();
    }

    public ReviewRepository (ReviewRepository rr){
        this();
        try {
            addReview(rr.getAllReviews());
        } catch (ReviewAlreadyExistsException e) {
           //Não acontece
        }
    }

    public void addReview(IReview r) throws ReviewAlreadyExistsException{
        if(this.reviews.containsKey(r.getReviewID())){
            throw new ReviewAlreadyExistsException("Esta Review ja existe");
        }
        else {
            IReview rc = r.clone();
            this.reviews.put(r.getReviewID(), rc);
            // addReviewByMonth(rc);

        }
    }

    public void addReview(List<IReview> lr) throws ReviewAlreadyExistsException {
        List<IReview> lre = new ArrayList<>();

        for (IReview review : lr) {
            try {
                addReview(review);
            } catch (ReviewAlreadyExistsException e) {
                lre.add(review);
            }
        }
        
        if (lre.size() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Os Reviews com id:");
            for(IReview review : lre) {
                sb.append(review.getReviewID() + ";");
            }
            sb.append("Já existiam no repositório.\n");
            throw new ReviewAlreadyExistsException(sb.toString());
        }
    }

    public boolean reviewWithBusinessID(String busID) {
        return this.reviews.values().stream().anyMatch(r -> r.getBusID().equals(busID));
    }

    public Double getReviewAverageByBusiness(String busID) throws NoReviewsWithBusinessException{

        if(!reviewWithBusinessID(busID)) {
            throw new NoReviewsWithBusinessException("Não existe reviews com o Business ID: " + busID);
        
        } else {
            
            List<IReview> allBusRev = getAllReviews(r -> reviewWithBusinessID(busID));
    
            return allBusRev.stream().mapToDouble(x -> x.getStars()).sum() / allBusRev.size(); 
        }    
    }

    public Double getReviewAverageByBusOnMonth(String busID, Integer month) throws NoReviewsWithBusinessException{

        if(!reviewWithBusinessID(busID)) {
            throw new NoReviewsWithBusinessException("Não existe reviews com o Business ID: " + busID);
        
        } else {
            
            List<IReview> allBusRev = getAllReviews(r -> reviewWithBusinessID(busID) && r.getDate().getMonthValue() == month);
    
            return allBusRev.stream().mapToDouble(x -> x.getStars()).sum() / allBusRev.size(); 
        }    
    }

    @Override
    public IReview getReview(String reId) throws ReviewNotFoundException{
        try {
            return this.reviews.get(reId).clone();
        } catch (NullPointerException np) {
            throw new ReviewNotFoundException(reId + "não existe!");
        }
    }

    @Override
    public IReview getReview(IReview r) throws ReviewNotFoundException{
        try {
            return this.reviews.get(r.getReviewID()).clone();
        } catch (NullPointerException np) {
            throw new ReviewNotFoundException(r.getReviewID() + "não existe!");
        }
    }

    @Override
    public List<IReview> getAllReviews(){
        return this.reviews.values().stream().map(IReview::clone).collect(Collectors.toList());
    }

    @Override
    public List<IReview> getAllReviews(Predicate<IReview> cp){
        return this.reviews.values().stream().filter(cp).map(IReview::clone).collect(Collectors.toList());
    }

    @Override
    public void removeReview(String reId) throws ReviewNotFoundException{
        try {
            this.reviews.get(reId);
            this.reviews.remove(reId);
        } catch (NullPointerException np) {
            throw new ReviewNotFoundException(reId + "não existe!");
        }
    }

    @Override
    public void removeReview(IReview r) throws ReviewNotFoundException{
        this.removeReview(r.getReviewID());
    }

    @Override
    public ReviewRepository clone(){
        return new ReviewRepository(this);
    }
}
