package Middleware.Domain;


public class YearIsNotValidException extends Exception{

    public YearIsNotValidException(String message) {
        super(message);
    }
    
}
