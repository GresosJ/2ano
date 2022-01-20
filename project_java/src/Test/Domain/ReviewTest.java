package Test.Domain;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.Test;

import Domain.Model.Review;


public class ReviewTest {
    
    public ReviewTest() {

    }

    @Test
    public void testConstructor() {
        Review r = new Review();
        assertTrue(r.getReviewID().equals(""));
        assertTrue(r.getUserID().equals(""));
        assertTrue(r.getBusID().equals(""));
        assertTrue(r.getStars().equals(0.0));
        assertTrue(r.getUseful().equals(0));
        assertTrue(r.getFunny().equals(0));
        assertTrue(r.getCool().equals(0));
        assertTrue(r.getText().equals(""));

        r = new Review("","","",0.0,0,0,0, LocalDateTime.now(), "");
        assertTrue(r.getReviewID().equals(""));
        assertTrue(r.getUserID().equals(""));
        assertTrue(r.getBusID().equals(""));
        assertTrue(r.getStars().equals(0.0));
        assertTrue(r.getUseful().equals(0));
        assertTrue(r.getFunny().equals(0));
        assertTrue(r.getCool().equals(0));
        assertTrue(r.getText().equals(""));

        Review r2 = new Review(r);
        assertTrue(r2.getReviewID().equals(""));
        assertTrue(r2.getUserID().equals(""));
        assertTrue(r2.getBusID().equals(""));
        assertTrue(r2.getStars().equals(0.0));
        assertTrue(r2.getUseful().equals(0));
        assertTrue(r2.getFunny().equals(0));
        assertTrue(r2.getCool().equals(0));
        assertTrue(r2.getText().equals(""));

        Review r3 = new Review("id;userid;busid;0.0;0;0;0;2014-05-07 18:10:21;text");
        assertTrue(r3.getReviewID().equals("id"));
        assertTrue(r3.getUserID().equals("userid"));
        assertTrue(r3.getBusID().equals("busid"));
        assertTrue(r3.getStars().equals(0.0));
        assertTrue(r3.getUseful().equals(0));
        assertTrue(r3.getFunny().equals(0));
        assertTrue(r3.getCool().equals(0));
        assertTrue(r3.getDate().equals(LocalDateTime.of(2014, 5, 7, 18, 10, 21)));
        assertTrue(r3.getText().equals("text"));
    }

}
