package Test.Domain.Model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Domain.Model.Business;


public class BusinessTest {
    private List<Business> lb;

    public BusinessTest() {
        lb = new ArrayList<>();
    }

    @BeforeEach
    public void populate(){
        for (int i = 0; i < 100; i++) {
            lb.add(new Business(""+i, "name " + i, "city "+ i, "state" +i, new ArrayList<>()));
        }
    }

    @Test
    public void testConstructor() {
        Business bus = new Business();
        assertTrue(bus.getId().equals(""));
        List<String> categories = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            categories.add("category " + i);
        }
        bus = new Business("id1", "name1", "city1", "state1", categories);
        assertTrue(bus.getCategories().size() == 100);

        bus = new Business("id;jose;braga;porto;comida,cerveja,dormir");
        assertTrue(bus.getId().equals("id"));
        assertTrue(bus.getName().equals("jose"));
        assertTrue(bus.getCity().equals("braga"));
        assertTrue(bus.getState().equals("porto"));
        assertTrue(bus.getCategories().get(0).equals("comida"));
    }

    @Test
    void testGetId() {
        for (int i = 0; i < 100; i++) {
            assertTrue(this.lb.get(i).getId().equals("" + i));
        }
    }
    
    @Test
    void testGetName() {
        for (int i = 0; i < 100; i++) {
            assertTrue(this.lb.get(i).getName().equals("name " + i));
        }
    }
    
    @Test
    void testSetId() {
        for (int i = 0; i < 100; i++) {
            lb.get(i).setId(""+i*2);
        }
        for (int i = 0; i < 100; i++) {
            assertTrue(this.lb.get(i).getId().equals("" + i*2));
        }
    }
    
    @Test
    void testSetName() {
        for (int i = 0; i < 100; i++) {
            lb.get(i).setName("new name "+i*2);
        }
        for (int i = 0; i < 100; i++) {
            assertTrue(this.lb.get(i).getName().equals("new name " + i*2));
        }
    }
    
    @Test
    void testAddCategory() {
        Business b = lb.get(0);
        for (int i = 0; i < 100; i++) {
            b.addCategory("category " + i);
        }
        assertTrue(b.getCategories().size() == 100);
        for (int i = 100; i < 200; i++) {
            b.addCategory("category " + i);
        }
        assertTrue(b.getCategories().size() == 200);
    }

    @Test
    void testClone() {
        
    }

    @Test
    void testGetCategories() {
        
    }

    @Test
    void testGetCity() {
        
    }

    @Test
    void testSetCategories() {
        
    }

    @Test
    void testSetCity() {
        
    }

}
