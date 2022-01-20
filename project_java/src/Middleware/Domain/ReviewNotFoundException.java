package Middleware.Domain;


public class ReviewNotFoundException extends Exception{
    public ReviewNotFoundException(String msg) {
        super(msg);
    }
}