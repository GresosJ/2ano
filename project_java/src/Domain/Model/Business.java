package Domain.Model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import Domain.Interfaces.IBusiness;
import Middleware.Domain.CategoryNotFoundException;

public class Business implements IBusiness, Serializable, Comparable<Business> {

    private String id;
    private String name;
    private String city;
    private String state;
    private Set<String> categories;

    public Business() {
        this.id = "";
        this.name = "";
        this.city = "";
        this.state = "";
        this.categories = new TreeSet<>();
    }

    public Business(String id, String name, String city, String state, List<String> categories) {
        this();
        this.id = id;
        this.name = name;
        this.city = city;
        this.state = state;
        setCategories(categories);
    }

    public Business(Business b) {
        this.id = b.getId();
        this.name = b.getName();
        this.city = b.getCity().toLowerCase();
        this.state = b.getState().toUpperCase();
        setCategories(b.getCategories());
    }
    
    public Business(String input) throws IllegalArgumentException {
        String []tokens = input.split(";", 5);
        
        if(tokens.length != 5) {
            throw new IllegalArgumentException("O número de argumentos está errado!" + input);
        }

        this.id = tokens[0];
        this.name = tokens[1];
        this.city = tokens[2].toLowerCase();
        this.state = tokens[3].toUpperCase();
        setCategories(Arrays.asList(tokens[4].split(",")));

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
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    
    public List<String> getCategories() {
        return this.categories.stream().collect(Collectors.toList());
    }
    
    public void setCategories(List<String> categories) {
        this.categories = categories.stream().filter(x -> x.length() > 0).collect(Collectors.toCollection(TreeSet::new));
    }
    
    public void addCategory(String category) {
        this.categories.add(category);
    }
    
    public void removeCategory(String category) throws CategoryNotFoundException {
        try {
            this.categories.remove(category);
        } catch (NullPointerException npe) {
            throw new CategoryNotFoundException(category + " não existe neste business.");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (obj == null)
            return false;

        if (!this.getClass().getSimpleName().equals(obj.getClass().getSimpleName()))
            return false;

        Business b = (Business) obj;
        return this.getId().equals(b.getId());
    }

    @Override
    public Business clone() {
        return new Business(this);
    }

    @Override
    public int compareTo(Business o) {
        return this.getId().compareTo(o.getId());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[] { this.getId() });
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getId()+";");
        sb.append(this.getName()+";");
        sb.append(this.getCity()+";");
        sb.append(this.getState()+";");
        sb.append(this.getCategories()+";");
        return sb.toString();
    }

}
