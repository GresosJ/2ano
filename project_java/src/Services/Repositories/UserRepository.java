package Services.Repositories;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import Domain.Interfaces.IUser;
import Middleware.Domain.UserAlreadyExistsException;
import Middleware.Domain.UserNotFoundException;
import Services.Interfaces.IUserRepository;


public class UserRepository implements IUserRepository, Serializable {
    public Map<String, IUser> users;
    
    public UserRepository() {
        this.users = new HashMap<>();
    }

    public UserRepository(UserRepository ur) {
        this();
        try {
            addUser(ur.getAllUsers());
        } catch (UserAlreadyExistsException e) {
           //Não vai acontecer
        }
    }

    public void addUser(IUser u) throws UserAlreadyExistsException {
        if(this.users.containsKey(u.getId()))
            throw new UserAlreadyExistsException("O User ja existe!");

        else    
            this.users.put(u.getId(), u.clone());
    }

    public void addUser(List<IUser> lus) throws UserAlreadyExistsException {
        List<IUser> lu = new ArrayList<>();

        for (IUser user : lus) {
            try {
                addUser(user);
            } catch (UserAlreadyExistsException e) {
                lu.add(user);
            }
        }
        
        if (lu.size() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Os Users com id:");
            for(IUser user : lu) {
                sb.append(user.getId() + ";");
            }
            sb.append("Já existiam no repositório.\n");
            throw new UserAlreadyExistsException(sb.toString());
        }
    }

    @Override
    public IUser getUser(String usId) throws UserNotFoundException {
        try {
            return this.users.get(usId).clone();
        } catch (NullPointerException np) {
            throw new UserNotFoundException(usId + "não existe!");
        }
    } 

    @Override
    public IUser getUser(IUser u) throws UserNotFoundException{
        try {
            return this.users.get(u.getId()).clone();
        } catch (NullPointerException np) {
            throw new UserNotFoundException("User com o id:" + u.getId() + "não existe!");
        }
    }

    @Override 
    public List<IUser> getAllUsers() {
        return this.users.values().stream().map(IUser::clone).collect(Collectors.toList());
    }

    @Override
    public List<IUser> getAllUsers(Predicate<IUser> up) {
        return this.users.values().stream().filter(up).map(IUser::clone).collect(Collectors.toList());
    }

    @Override
    public void removeUser(String userId) throws UserNotFoundException{
        try {
            this.users.remove(userId);
        } catch (NullPointerException np) {
            throw new UserNotFoundException("O user com id:" + userId + "já não existe!");
        }
    }

    @Override 
    public void removeUser(IUser u) throws UserNotFoundException{
        this.removeUser(u.getId());
    }

    @Override 
    public UserRepository clone(){
        return new UserRepository(this);
    }
}
