package tests.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import util.GenericArray;

import static org.junit.Assert.*;

/**
 * Created by marius on 10/13/16.
 */
public class GenericArrayTest {

    @Before
    public void setUp() {

    }

    @Test
    public void getAt() throws Exception {
        GenericArray<String> stringArray = new GenericArray<>();

        stringArray.add("first");
        stringArray.add("second");
        stringArray.add("third");
        stringArray.add("fourth");

        assertEquals("first", stringArray.getAt(0));
        assertEquals("second", stringArray.getAt(1));
        assertEquals("third", stringArray.getAt(2));
        assertEquals("fourth", stringArray.getAt(3));
    }

    @Test
    public void getSize() throws Exception {
        GenericArray<Integer> integerArray = new GenericArray<>();

        assertEquals(0, integerArray.getSize().intValue());

        integerArray.add(1);
        assertEquals(1, integerArray.getSize().intValue());
        integerArray.add(22343);
        assertEquals(2, integerArray.getSize().intValue());
        integerArray.add(111123);
        assertEquals(3, integerArray.getSize().intValue());
        integerArray.add(-1);
        assertEquals(4, integerArray.getSize().intValue());

        integerArray.clear();
        assertEquals(0, integerArray.getSize().intValue());

    }

    @Test
    public void removeAt() throws Exception {
        GenericArray<Integer> integerArray = new GenericArray<>();

        integerArray.add(111);
        integerArray.add(112);
        integerArray.add(113);
        integerArray.add(114);
        integerArray.add(115);

        assertEquals(5, integerArray.getSize().intValue());
        assertEquals(111, integerArray.getAt(0).intValue());

        integerArray.removeAt(0);
        assertEquals(112, integerArray.getAt(0).intValue());

        integerArray.removeAt(1);
        assertEquals(114, integerArray.getAt(1).intValue());

        assertEquals(3, integerArray.getSize().intValue());

    }

    @Test
    public void find() throws Exception {
        GenericArray<String> stringArray = new GenericArray<>();

        stringArray.add("first");
        stringArray.add("second");
        stringArray.add("third");
        stringArray.add("fourth");

        assertEquals(0, stringArray.find("first").intValue());
        assertEquals(3, stringArray.find("fourth").intValue());
        assertEquals(2, stringArray.find("third").intValue());
        assertEquals(1, stringArray.find("second").intValue());
    }

    @Test
    public void has() throws Exception {
        GenericArray<String> stringArray = new GenericArray<>();

        stringArray.add("first");
        stringArray.add("second");

        assertTrue(stringArray.has("first"));
        assertTrue(stringArray.has("second"));

        assertFalse(stringArray.has("seconfsdfsdd"));
        assertFalse(stringArray.has("secosfsdfsnd"));
        assertFalse(stringArray.has(""));
        assertFalse(stringArray.has("r3r"));
    }

    @Test
    public void set() throws Exception {
        GenericArray<String> stringArray = new GenericArray<>();

        stringArray.add("first");
        stringArray.add("second");

        assertEquals(0, stringArray.find("first").intValue());

        stringArray.set(0, "new-value");
        assertFalse(stringArray.has("first"));
        assertTrue(stringArray.has("new-value"));
        assertEquals(0, stringArray.find("new-value").intValue());
    }

}