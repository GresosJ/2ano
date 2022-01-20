package Middleware.Domain;


public class FriendNotFoundException extends Exception{
    public FriendNotFoundException(String msg) {
        super(msg);
    }
}
