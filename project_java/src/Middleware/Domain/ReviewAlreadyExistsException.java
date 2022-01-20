package Middleware.Domain;


public class ReviewAlreadyExistsException extends Exception{
    public ReviewAlreadyExistsException(String msg) {
        super(msg);
    }
}