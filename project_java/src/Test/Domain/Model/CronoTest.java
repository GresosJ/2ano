package Test.Domain.Model;

import org.junit.jupiter.api.Test;

import Domain.Model.Crono;

public class CronoTest {
    @Test
    void testGetTime() throws InterruptedException {
        
        Crono.start();
        Thread.sleep(5000);
        double d = Crono.stop();
        System.out.println(d);
    }
}
