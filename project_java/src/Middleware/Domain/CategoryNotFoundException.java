package Middleware.Domain;


public class CategoryNotFoundException extends Exception {
    public CategoryNotFoundException(String msg) {
        super(msg);
    }
}
