package Test.GestReviews.Queries;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import Domain.Interfaces.IBusiness;
import Domain.Model.Business;
import GestReviews.Queries.BusNotReviewed;

public class BusNotReviewedTest {

    public BusNotReviewedTest() {
    }

    @Test
    void testAddBusiness() {
        List<IBusiness> lbus = new ArrayList<>();

        for(int i = 0; i < 5; i++) {
            lbus.add(new Business("id"+i, "name"+i, "city"+i, "state"+i, new ArrayList<>()));
        }

        BusNotReviewed bnr = new BusNotReviewed();

        for(int i = 0; i < 5; i++) {
            bnr.addBusiness(lbus.get(i));
        }
        
        for(IBusiness b : lbus) {
            assertTrue(bnr.getBusNotReviewed().contains(b.getId()));
        }
    }

    @Test 
    void testGetNumBusNRev(){
        List<IBusiness> lbus = new ArrayList<>();

        for(int i = 0; i < 5; i++) {
            lbus.add(new Business("id"+i, "name"+i, "city"+i, "state"+i, new ArrayList<>()));
        }

        BusNotReviewed bnr = new BusNotReviewed();

        for(int i = 0; i < 5; i++) {
            bnr.addBusiness(lbus.get(i));
        }

        assertTrue(bnr.getNumBusNRev() == 5);
    }
}
