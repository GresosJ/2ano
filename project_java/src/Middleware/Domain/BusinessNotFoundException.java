package Middleware.Domain;


public class BusinessNotFoundException extends Exception {
    public BusinessNotFoundException(String msg) {
        super(msg);
    }
}
