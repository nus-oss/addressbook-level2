package seedu.addressbook.common;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class UtilsTest {
    @Test
    public void isAnyNull() {
        // empty list
        assertFalse(Utils.isAnyNull());

        // Any non-empty list
        assertFalse(Utils.isAnyNull(new Object(), new Object()));
        assertFalse(Utils.isAnyNull("test"));

        // non empty list with just one at the beginning
        assertTrue(Utils.isAnyNull((Object)null));
        assertTrue(Utils.isAnyNull(null, "", new Object(), null));
        assertTrue(Utils.isAnyNull(null, new Object(), new Object()));

        // non empty list with nulls in the middle
        assertTrue(Utils.isAnyNull(new Object(), null, null, "test"));
        assertTrue(Utils.isAnyNull("", null, new Object()));

        // non empty list with one null as the last element
        assertTrue(Utils.isAnyNull("", new Object(), null));
        assertTrue(Utils.isAnyNull(new Object(), new Integer(1), null));
        
        // test whether method peeks into the elements inside container
        List<Object> emptyList = createList((Object)null);
        assertFalse(Utils.isAnyNull(emptyList));
    }

    @Test
    public void elementsAreUnique() throws Exception {
        // empty list
        assertAreUnique();

        // only one object
        assertAreUnique((Object)null);
        assertAreUnique(1);

        // all objects unique
        assertAreUnique("abc", "ab", "a");
        assertAreUnique(1, 2);

        // some identical objects
        assertNotUnique("abc", "", "abc", "ABC");
        assertNotUnique(null, 1, new Integer(1));
        assertNotUnique("", "abc", "a", "abc");
        assertNotUnique(null, "a", "b", null);
    }

    private void assertAreUnique(Object... objects) {
        assertTrue(Utils.elementsAreUnique(createList(objects)));
    }

    private void assertNotUnique(Object... objects) {
        assertFalse(Utils.elementsAreUnique(createList(objects)));
    }
    
    private List<Object> createList(Object... objects) {
        return Arrays.asList(objects);
    }
}
