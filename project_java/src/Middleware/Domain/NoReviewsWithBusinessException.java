package Middleware.Domain;


public class NoReviewsWithBusinessException extends Exception{

    public NoReviewsWithBusinessException(String message) {
        super(message);
    }
    
}
