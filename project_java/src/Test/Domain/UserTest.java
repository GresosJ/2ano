package Test.Domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import Domain.Model.User;

import org.junit.jupiter.api.BeforeEach;


public class UserTest {
    private List<User> lus;

    public UserTest(){
        this.lus = new ArrayList<>();
    }

    @BeforeEach
    public void populate(){
        for (int i = 0; i < 100; i++) {
            this.lus.add(new User("" + i, "name " + i, new ArrayList<>()));
        }
    }

    @Test
    public void testConstructor() {
        User u = new User();
        assertTrue(u.getId().equals(""));
        assertTrue(u.getName().equals(""));
        List<String> friends = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            friends.add("friend " + i);
        }

        u = new User("id1", "name1", friends);
        assertTrue(u.getFriends().size() == 100);
        assertTrue(u.getId().equals("id1"));
        assertTrue(u.getName().equals("name1"));

        User newU = new User(u);
        assertTrue(newU.getId().equals(u.getId()));
        assertTrue(newU.getName().equals(u.getName()));
        assertTrue(newU.getFriends().equals(u.getFriends()));

        User newU2 = new User("q_QQ5kBBwlCcbL1s4NVK3g;Jane;xBDpTUbai0DXrvxCe3X16Q");
        assertTrue(newU2.getId().equals("q_QQ5kBBwlCcbL1s4NVK3g"));
        assertTrue(newU2.getName().equals("Jane"));
        
    }

    @Test
    public void testGetId() {
        for (int i = 0; i < 100; i++)
            assertTrue(this.lus.get(i).getId().equals("" + i));
    }

    @Test
    public void testSetId() {
        for(int i = 0; i < 100; i++)
            lus.get(i).setId("" + i*2);
        
        for(int j = 0; j < 100; j++)
            assertTrue(this.lus.get(j).getId().equals(""+ j*2));
    }

    @Test
    public void testGetName() {
        for(int i = 0; i < 100; i++)
            assertTrue(this.lus.get(i).getName().equals("name " + i));
    }

    @Test
    public void testSetName(){
        for(int i = 0; i < 100; i++)
            lus.get(i).setName("name " + i*2);
        
        for(int j = 0; j < 100; j++)
            assertTrue(this.lus.get(j).getName().equals("name "+ j*2));
    }

    @Test
    public void testAddFriend() {
        User u = lus.get(0);
        for(int i = 0; i < 100; i++) {
            u.addFriend("friend"+i);
        }
        assertTrue(u.getFriends().size() == 100);
        for (int i = 100; i < 200; i++) {
            u.addFriend("friend"+i);
        }
        assertTrue(u.getFriends().size() == 200);
    }

    @Test
    public void testClone() {
        User u = new User();
        User copy = new User(u);

        assertTrue(u.getId().equals(copy.getId()));
        assertTrue(u.getName().equals(copy.getName()));
        assertTrue(u.getFriends().equals(copy.getFriends()));
    }

    @Test
    public void testEquals() {
        User u = new User();
        User copy = new User(u);

        User u2 = this.lus.get(0);

        assertTrue(u.equals(copy));
        assertFalse(u.equals(u2));
    }
}
