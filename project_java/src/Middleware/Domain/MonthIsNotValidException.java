package Middleware.Domain;


public class MonthIsNotValidException extends Exception{

    public MonthIsNotValidException(String message) {
        super(message);
    }
    
}
