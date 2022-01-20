package Middleware.Domain;

public class LoadCSVHasErrorsException extends Exception {

    public LoadCSVHasErrorsException(String message) {
        super(message);
    }
}
