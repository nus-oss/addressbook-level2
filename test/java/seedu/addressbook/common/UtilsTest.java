package seedu.addressbook.common;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class UtilsTest {

    //test for PR
    @Test
    public void isAnyNull() throws Exception {
        //empty list
        assertNoneNull();

        //only one object
        assertNoneNull(0);
        assertHasNull((null);

        //contain null object
        assertHasNull(null, null, "CJ", "SOCOOLLL");
        assertHasNull(null, 1);

        //does not contain null object
        assertNoneNull("CJ", "SOCOOLLL");
        assertNoneNull("85241253);


    }


    @Test
    public void elementsAreUnique() throws Exception {
        // empty list
        assertAreUnique();


        // only one object
        assertAreUnique((Object) null);
        assertAreUnique(1);
        assertAreUnique("");
        assertAreUnique("abc");

        // all objects unique
        assertAreUnique("abc", "ab", "a");
        assertAreUnique(1, 2);

        // some identical objects
        assertNotUnique("abc", "abc");
        assertNotUnique("abc", "", "abc", "ABC");
        assertNotUnique("", "abc", "a", "abc");
        assertNotUnique(1, Integer.valueOf(1));
        assertNotUnique(null, 1, Integer.valueOf(1));
        assertNotUnique(null, null);
        assertNotUnique(null, "a", "b", null);
    }

    private void assertAreUnique(Object... objects) {
        assertTrue(Utils.elementsAreUnique(Arrays.asList(objects)));
    }

    private void assertNotUnique(Object... objects) {
        assertFalse(Utils.elementsAreUnique(Arrays.asList(objects)));
    }

    private void assertNoneNull(Object... objects) {
        assertTrue(Utils.isAnyNull(objects));
    }

    private void assertHasNull(Object... objects) {
        assertTrue(Utils.isAnyNull(objects));
    }

}
