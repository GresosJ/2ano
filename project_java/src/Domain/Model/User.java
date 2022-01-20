package Domain.Model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import Domain.Interfaces.IUser;
import Middleware.Domain.FriendNotFoundException;

public class User implements IUser, Serializable, Comparable<User> {

    private String id;
    private String name;
    private Set<String> friends;

    public User() {
        this.id = "";
        this.name = "";
        this.friends = new TreeSet<>();
    }

    public User(String id, String name, List<String> friends) {
        this.id = id;
        this.name = name;
        this.setFriends(friends);
    }

    public User(User u) {
        this.id = u.getId();
        this.name = u.getName();
        this.setFriends(u.getFriends());
    }

    public User(String input) throws IllegalArgumentException {
        this();
        String []tokens = input.split(";", 3);

        if(tokens.length != 3){
            throw new IllegalArgumentException("O número de Strings está errado"); 
        }
        
        this.id = tokens[0];
        this.name = tokens[1];
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getFriends() {
        return this.friends.stream().collect(Collectors.toList());
    }

    public void setFriends(List<String> friends) {
        this.friends = friends.stream().collect(Collectors.toCollection(TreeSet::new));
    }

    public void addFriend(String friend) {
        this.friends.add(friend);
    }
    
    public void removeFriend(String friend) throws FriendNotFoundException{
        try {
            this.friends.remove(friend);
        } catch (NullPointerException npe) {
            throw new FriendNotFoundException(friend + "Não existe na lista deste User.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if(o == null)
            return false;

        if(!this.getClass().getSimpleName().equals(o.getClass().getSimpleName()))
            return false;

        User u = (User) o;
        return this.getId().equals(u.getId());
    }

    @Override
    public User clone() {
        return new User(this);
    }

    @Override
    public int compareTo(User o) {
        return this.getId().compareTo(o.getId());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[] {this.getId()});
    }
}
