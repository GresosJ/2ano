package Test.Domain.Model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Domain.Interfaces.IKeyValueSet;
import Domain.Model.KeyValueSet;

public class KeyValueSetTest {
    private IKeyValueSet<String, String> kvs;

    @BeforeEach
    public void set_up() {
        this.kvs = new KeyValueSet<String, String>("busId");
    }

    @Test
    void testAddValue() {
        kvs.addValue("userid1");
        kvs.addValue("userid2");
        kvs.addValue("userid2");
        assertTrue(kvs.size() == 2);
    }

    @Test
    void testGetKey() {
        assertTrue(kvs.getKey().equals("busId"));
    }

    @Test
    void testGetValues() {
        kvs.addValue("userid1");
        kvs.addValue("userid2");
        kvs.addValue("userid2");
        assertTrue(kvs.getValues().size() == 2);
    }

    @Test
    void testHasValue() {
        kvs.addValue("userid1");
        assertTrue(kvs.hasValue("userid1"));
    }

    @Test
    void testRemoveValue() {
        kvs.addValue("userid1");
        kvs.addValue("userid2");
        kvs.addValue("userid2");
        kvs.removeValue("userid2");
        assertFalse(kvs.hasValue("userid2"));
    }

    @Test
    void testInsideSetComparator() {
        
    }
}
