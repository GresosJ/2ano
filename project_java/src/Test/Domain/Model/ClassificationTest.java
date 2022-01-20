package Test.Domain.Model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Domain.Interfaces.IClassification;
import Domain.Model.Classification;

public class ClassificationTest {
    
    @BeforeEach
    void set_up() {

    }

    @Test
    void testAddClassification() {
        IClassification c = new Classification();
        c.addClassification(5.0, 5.0, 5.0, 5.0);
        c.addClassification(6.0, 6.0, 6.0, 6.0);
        c.addClassification(2.0, 2.0, 6.0, 6.0);
        c.addClassification(4.0, 4.0, 6.0, 6.0);
        Double resultado = (5.0+6.0+2.0+4.0) / 4.0;
        assertTrue(c.getStars().compareTo(resultado) == 0);
    }
}
