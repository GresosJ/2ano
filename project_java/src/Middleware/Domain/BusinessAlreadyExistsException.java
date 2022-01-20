package Middleware.Domain;


public class BusinessAlreadyExistsException extends Exception {
    public BusinessAlreadyExistsException(String msg) {
        super(msg);
    }
}
