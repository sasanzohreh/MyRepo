import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;

/**
 * This is a basic set of unit tests for ArrayList.
 * <p>
 * Passing these tests doesn't guarantee any grade on these assignments. These
 * student JUnits that we provide should be thought of as a sanity check to
 * help you get started on the homework and writing JUnits in general.
 * <p>
 * We highly encourage you to write your own set of JUnits for each homework
 * to cover edge cases you can think of for each data structure. Your code must
 * work correctly and efficiently in all cases, which is why it's important
 * to write comprehensive tests to cover as many cases as possible.
 *
 * @author CS 1332 TAs
 * @version 1.0
 */
public class ArrayListStudentTest {

    private static final int TIMEOUT = 200;
    private ArrayList<String> list;

    @Before
    public void setUp() {
        list = new ArrayList<>();
    }

    // ADD TESTS .................................................................
    @Test(timeout = TIMEOUT)
    public void testAddAtIndex() {  // tests adding to front, back, and elsewhere indexes
        list.addAtIndex(0, "3a");   // 3a   ... EDGE CASE: tests adding to front
        list.addAtIndex(0, "1a");   // 1a, 3a
        list.addAtIndex(1, "2a");   // 1a, 2a, 3a   ... tests adding to middle
        list.addAtIndex(3, "8a");   // 1a, 2a, 3a, 8a   ... EDGE CASE: tests adding to back
        list.addAtIndex(3, "5a");   // 1a, 2a, 3a, 5a, 8a
        list.addAtIndex(4, "6a");   // 1a, 2a, 3a, 5a, 6a, 8a
        list.addAtIndex(5, "7a");   // 1a, 2a, 3a, 5a, 6a, 7a, 8a
        list.addAtIndex(3, "4a");   // 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a
        list.addAtIndex(8, "9a");   // 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a, 9a

        assertEquals(9, list.size());
        String[] expected = new String[9];
        expected[0] = "1a";
        expected[1] = "2a";
        expected[2] = "3a";
        expected[3] = "4a";
        expected[4] = "5a";
        expected[5] = "6a";
        expected[6] = "7a";
        expected[7] = "8a";
        expected[8] = "9a";
        assertArrayEquals(expected, list.getBackingArray());

        list.addAtIndex(9, "3a");   // ... 3a   ... EDGE CASE: adding to back after full, capacity doubles
        list.addAtIndex(9, "1a");   // ... 1a, 3a
        list.addAtIndex(10, "2a");   // ... 1a, 2a, 3a
        list.addAtIndex(12, "9a");   // ... 1a, 2a, 3a, 9a
        list.addAtIndex(12, "5a");   // ... 1a, 2a, 3a, 5a, 9a
        list.addAtIndex(13, "6a");   // ... 1a, 2a, 3a, 5a, 6a, 9a
        list.addAtIndex(14, "7a");   // ... 1a, 2a, 3a, 5a, 6a, 7a, 9a
        list.addAtIndex(12, "4a");   // ... 1a, 2a, 3a, 4a, 5a, 6a, 7a, 9a
        list.addAtIndex(16, "8a");   // ... 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a, 9a

        assertEquals(18, list.size());
        expected = new String[18];
        expected[0] = "1a";
        expected[1] = "2a";
        expected[2] = "3a";
        expected[3] = "4a";
        expected[4] = "5a";
        expected[5] = "6a";
        expected[6] = "7a";
        expected[7] = "8a";
        expected[8] = "9a";
        expected[9] = "1a";
        expected[10] = "2a";
        expected[11] = "3a";
        expected[12] = "4a";
        expected[13] = "5a";
        expected[14] = "6a";
        expected[15] = "7a";
        expected[16] = "8a";
        expected[17] = "9a";
        assertArrayEquals(expected, list.getBackingArray());

        // should be double capacity now
        list.addAtIndex(0, "start");    // start ... ...    EDGE CASE: adding to front after full, capacity doubles
        list.addAtIndex(19, "3a");   // start ... ... 3a
        list.addAtIndex(19, "2a");   // start ... ... 2a, 3a
        list.addAtIndex(21, "8a");   // start ... ... 2a, 3a, 8a
        list.addAtIndex(21, "5a");   // start ... ... 2a, 3a, 5a, 8a
        list.addAtIndex(22, "6a");   // start .. ... 2a, 3a, 5a, 6a, 8a
        list.addAtIndex(23, "7a");   // start ... ... 2a, 3a, 5a, 6a, 7a, 8a
        list.addAtIndex(21, "4a");   // start ... ... 2a, 3a, 4a, 5a, 6a, 7a, 8a
        list.addAtIndex(26, "9a");   // start ... ... 2a, 3a, 4a, 5a, 6a, 7a, 8a, 9a
        list.addAtIndex(19, "1a");   // start ... ... 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a, 9a
        assertEquals(28, list.size());
        expected = new String[36];
        expected[0] = "start";
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        expected[4] = "4a";
        expected[5] = "5a";
        expected[6] = "6a";
        expected[7] = "7a";
        expected[8] = "8a";
        expected[9] = "9a";
        expected[10] = "1a";
        expected[11] = "2a";
        expected[12] = "3a";
        expected[13] = "4a";
        expected[14] = "5a";
        expected[15] = "6a";
        expected[16] = "7a";
        expected[17] = "8a";
        expected[18] = "9a";
        expected[19] = "1a";
        expected[20] = "2a";
        expected[21] = "3a";
        expected[22] = "4a";
        expected[23] = "5a";
        expected[24] = "6a";
        expected[25] = "7a";
        expected[26] = "8a";
        expected[27] = "9a";
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testAddToFront() {
        list.addToFront("9a");
        list.addToFront("8a");
        list.addToFront("7a");
        list.addToFront("6a");
        list.addToFront("5a");
        list.addToFront("4a");
        list.addToFront("3a");
        list.addToFront("2a");
        list.addToFront("1a");  // 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a, 9a
        // full, need to resize for next input

        assertEquals(9, list.size());

        list.addToFront("9a");  // should be double capacity now
        list.addToFront("8a");
        list.addToFront("7a");
        list.addToFront("6a");
        list.addToFront("5a");
        list.addToFront("4a");
        list.addToFront("3a");
        list.addToFront("2a");
        list.addToFront("1a");  // ... 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a, 9a
        // full, need to resize for next input

        assertEquals(18, list.size());

        list.addToFront("9a");  // should be double capacity now
        list.addToFront("8a");
        list.addToFront("7a");
        list.addToFront("6a");
        list.addToFront("5a");
        list.addToFront("4a");
        list.addToFront("3a");
        list.addToFront("2a");
        list.addToFront("1a");  // ... ... 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a, 9a

        assertEquals(27, list.size());
        String[] expected = new String[36];
        expected[0] = "1a";
        expected[1] = "2a";
        expected[2] = "3a";
        expected[3] = "4a";
        expected[4] = "5a";
        expected[5] = "6a";
        expected[6] = "7a";
        expected[7] = "8a";
        expected[8] = "9a";
        expected[9] = "1a";
        expected[10] = "2a";
        expected[11] = "3a";
        expected[12] = "4a";
        expected[13] = "5a";
        expected[14] = "6a";
        expected[15] = "7a";
        expected[16] = "8a";
        expected[17] = "9a";
        expected[18] = "1a";
        expected[19] = "2a";
        expected[20] = "3a";
        expected[21] = "4a";
        expected[22] = "5a";
        expected[23] = "6a";
        expected[24] = "7a";
        expected[25] = "8a";
        expected[26] = "9a";
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testAddToBack() {
        list.addToBack("1a");
        list.addToBack("2a");
        list.addToBack("3a");
        list.addToBack("4a");
        list.addToBack("5a");
        list.addToBack("6a");
        list.addToBack("7a");
        list.addToBack("8a");
        list.addToBack("9a");   // 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a, 9a

        assertEquals(9, list.size());
        // full, need to resize for next input

        list.addToBack("1a");   // should be double capacity now
        list.addToBack("2a");
        list.addToBack("3a");
        list.addToBack("4a");
        list.addToBack("5a");
        list.addToBack("6a");
        list.addToBack("7a");
        list.addToBack("8a");
        list.addToBack("9a");   // ... 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a, 9a

        assertEquals(18, list.size());
        // full, need to resize for next input

        list.addToBack("1a");   // should be double capacity now
        list.addToBack("2a");
        list.addToBack("3a");
        list.addToBack("4a");
        list.addToBack("5a");
        list.addToBack("6a");
        list.addToBack("7a");
        list.addToBack("8a");
        list.addToBack("9a");   // ... ... 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a, 9a

        assertEquals(27, list.size());
        String[] expected = new String[36];
        expected[0] = "1a";
        expected[1] = "2a";
        expected[2] = "3a";
        expected[3] = "4a";
        expected[4] = "5a";
        expected[5] = "6a";
        expected[6] = "7a";
        expected[7] = "8a";
        expected[8] = "9a";
        expected[9] = "1a";
        expected[10] = "2a";
        expected[11] = "3a";
        expected[12] = "4a";
        expected[13] = "5a";
        expected[14] = "6a";
        expected[15] = "7a";
        expected[16] = "8a";
        expected[17] = "9a";
        expected[18] = "1a";
        expected[19] = "2a";
        expected[20] = "3a";
        expected[21] = "4a";
        expected[22] = "5a";
        expected[23] = "6a";
        expected[24] = "7a";
        expected[25] = "8a";
        expected[26] = "9a";
        assertArrayEquals(expected, list.getBackingArray());
    }

    // REMOVE TESTS ..............................................................
    @Test(timeout = TIMEOUT)
    public void testRemoveAtIndex() {
        list.addAtIndex(0, "3a");   // 3a
        list.addAtIndex(0, "1a");   // 1a, 3a
        list.addAtIndex(1, "2a");   // 1a, 2a, 3a
        list.addAtIndex(3, "8a");   // 1a, 2a, 3a, 8a
        list.addAtIndex(3, "5a");   // 1a, 2a, 3a, 5a, 8a
        list.addAtIndex(4, "6a");   // 1a, 2a, 3a, 5a, 6a, 8a
        list.addAtIndex(5, "7a");   // 1a, 2a, 3a, 5a, 6a, 7a, 8a
        list.addAtIndex(3, "4a");   // 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a
        list.addAtIndex(8, "9a");   // 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a, 9a

        assertEquals(9, list.size());

        //EDGE CASE: removing from back.. could run into problems with your left-shifting!
        list.removeAtIndex(8);  // 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a
        //EDGE CASE: removing from front
        list.removeAtIndex(0);  // 2a, 3a, 4a, 5a, 6a, 7a, 8a
        //removing from a middle index
        list.removeAtIndex(3);  // 2a, 3a, 4a, 6a, 7a, 8a

        assertEquals(6, list.size());   //size should decrease
        String[] expected = new String[9];
        expected[0] = "2a";
        expected[1] = "3a";
        expected[2] = "4a";
        expected[3] = "6a";
        expected[4] = "7a";
        expected[5] = "8a";
        assertArrayEquals(expected, list.getBackingArray());
    }
    @Test(timeout = TIMEOUT)
    public void testRemoveFromFront() {
        list.addAtIndex(0, "3a");   // 3a
        list.addAtIndex(0, "1a");   // 1a, 3a
        list.addAtIndex(1, "2a");   // 1a, 2a, 3a
        list.addAtIndex(3, "8a");   // 1a, 2a, 3a, 8a
        list.addAtIndex(3, "5a");   // 1a, 2a, 3a, 5a, 8a
        list.addAtIndex(4, "6a");   // 1a, 2a, 3a, 5a, 6a, 8a
        list.addAtIndex(5, "7a");   // 1a, 2a, 3a, 5a, 6a, 7a, 8a
        list.addAtIndex(3, "4a");   // 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a
        list.addAtIndex(8, "9a");   // 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a, 9a
        list.addAtIndex(9, "1a");   // 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a, 9a, 1a

        assertEquals(10, list.size());

        //capacity should stay the same!!
        list.removeFromFront(); // 2a, 3a, 4a, 5a, 6a, 7a, 8a, 9a, 1a

        assertEquals(9, list.size());
        String[] expected = new String[18];
        expected[0] = "2a";
        expected[1] = "3a";
        expected[2] = "4a";
        expected[3] = "5a";
        expected[4] = "6a";
        expected[5] = "7a";
        expected[6] = "8a";
        expected[7] = "9a";
        expected[8] = "1a";
        assertArrayEquals(expected, list.getBackingArray());

        for (int i = 0; i < 9; i++) {   // clearing everything
            list.removeFromFront();
        }

        assertEquals(0, list.size());
        expected = new String[18];
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromBack() {
        list.addAtIndex(0, "3a");   // 3a
        list.addAtIndex(0, "1a");   // 1a, 3a
        list.addAtIndex(1, "2a");   // 1a, 2a, 3a
        list.addAtIndex(3, "8a");   // 1a, 2a, 3a, 8a
        list.addAtIndex(3, "5a");   // 1a, 2a, 3a, 5a, 8a
        list.addAtIndex(4, "6a");   // 1a, 2a, 3a, 5a, 6a, 8a
        list.addAtIndex(5, "7a");   // 1a, 2a, 3a, 5a, 6a, 7a, 8a
        list.addAtIndex(3, "4a");   // 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a
        list.addAtIndex(8, "9a");   // 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a, 9a
        list.addAtIndex(9, "1a");   // 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a, 9a, 1a

        assertEquals(10, list.size());

        //capacity should stay the same!!
        list.removeFromBack(); // 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a, 9a

        assertEquals(9, list.size());
        String[] expected = new String[18];
        expected[0] = "1a";
        expected[1] = "2a";
        expected[2] = "3a";
        expected[3] = "4a";
        expected[4] = "5a";
        expected[5] = "6a";
        expected[6] = "7a";
        expected[7] = "8a";
        expected[8] = "9a";
        assertArrayEquals(expected, list.getBackingArray());

        for (int i = 0; i < 9; i++) { // clearing everything
            list.removeFromFront();
        }

        assertEquals(0, list.size());
        expected = new String[18];
        assertArrayEquals(expected, list.getBackingArray());
    }

    // GET, ISEMPTY, CLEAR TESTS .............................................................
    @Test(timeout = TIMEOUT)
    public void getTest() {
        list.addToFront("4a");
        list.addToFront("3a");
        list.addToFront("2a");
        list.addToFront("1a");  // 1a, 2a, 3a, 4a

        assertEquals("4a", list.get(3));
        assertEquals("1a", list.get(0));
    }

    @Test(timeout = TIMEOUT)
    public void isEmptyTest() {
        assertTrue(list.isEmpty());

        list.addToFront("4a");

        assertFalse(list.isEmpty());

        list.removeFromBack();

        assertTrue(list.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void clearTest() {
        list.addAtIndex(0, "3a");   // 3a
        list.addAtIndex(0, "1a");   // 1a, 3a
        list.addAtIndex(1, "2a");   // 1a, 2a, 3a
        list.addAtIndex(3, "8a");   // 1a, 2a, 3a, 8a
        list.addAtIndex(3, "5a");   // 1a, 2a, 3a, 5a, 8a
        list.addAtIndex(4, "6a");   // 1a, 2a, 3a, 5a, 6a, 8a
        list.addAtIndex(5, "7a");   // 1a, 2a, 3a, 5a, 6a, 7a, 8a
        list.addAtIndex(3, "4a");   // 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a
        list.addAtIndex(8, "9a");   // 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a, 9a
        list.addAtIndex(9, "1a");   // 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a, 9a, 1a

        //should go back to initial capacity 9
        list.clear();

        assertEquals(0, list.size());
        String[] expected = new String[9];
        assertArrayEquals(expected, list.getBackingArray());
    }

    // EXCEPTION TESTS .............................................................
    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void addAtIndexException1() {    //negative index
        list.addToFront("4a");
        list.addToFront("3a");
        list.addToFront("2a");
        list.addToFront("1a");  // 1a, 2a, 3a, 4a

        list.addAtIndex(-1, "invalid index");
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void addAtIndexException2() {    //index > size (adding to further than back)
        list.addToFront("4a");
        list.addToFront("3a");
        list.addToFront("2a");
        list.addToFront("1a");  // 1a, 2a, 3a, 4a

        list.addAtIndex(5, "invalid index");
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void addAtIndexException3() {
        list.addToFront("4a");
        list.addToFront("3a");
        list.addToFront("2a");
        list.addToFront("1a");  // 1a, 2a, 3a, 4a

        list.addAtIndex(2, null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void addToFrontException() {
        list.addToFront("4a");
        list.addToFront("3a");
        list.addToFront("2a");
        list.addToFront("1a");  // 1a, 2a, 3a, 4a

        list.addToFront(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void addToBackException() {
        list.addToFront("4a");
        list.addToFront("3a");
        list.addToFront("2a");
        list.addToFront("1a");  // 1a, 2a, 3a, 4a

        list.addToBack(null);
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void removeAtIndexException1() {
        list.addToFront("4a");
        list.addToFront("3a");
        list.addToFront("2a");
        list.addToFront("1a");  // 1a, 2a, 3a, 4a

        list.removeAtIndex(-1);
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void removeAtIndexException2() {
        list.addToFront("4a");
        list.addToFront("3a");
        list.addToFront("2a");
        list.addToFront("1a");  // 1a, 2a, 3a, 4a

        list.removeAtIndex(4);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void removeFromFrontException() {
        list.removeFromFront();
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void removeFromBackException() {
        list.removeFromBack();
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void getException1() {
        list.get(-3);
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void getException2() {
        list.addToFront("4a");
        list.addToFront("3a");
        list.addToFront("2a");
        list.addToFront("1a");  // 1a, 2a, 3a, 4a

        list.get(4);
    }
    /*@Before
    public void setUp() {
        list = new ArrayList<>();
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, list.size());
        assertArrayEquals(new Object[ArrayList.INITIAL_CAPACITY],
                list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testAddAtIndex() {
        list.addAtIndex(0, "2a");   // 2a
        list.addAtIndex(0, "1a");   // 1a, 2a
        list.addAtIndex(2, "4a");   // 1a, 2a, 4a
        list.addAtIndex(2, "3a");   // 1a, 2a, 3a, 4a
        list.addAtIndex(0, "0a");   // 0a, 1a, 2a, 3a, 4a

        assertEquals(5, list.size());

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        expected[4] = "4a";
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testAddToFront() {
        list.addToFront("4a");  // 4a
        list.addToFront("3a");  // 3a, 4a
        list.addToFront("2a");  // 2a, 3a, 4a
        list.addToFront("1a");  // 1a, 2a, 3a, 4a
        list.addToFront("0a");  // 0a, 1a, 2a, 3a, 4a

        assertEquals(5, list.size());

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        expected[4] = "4a";
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testAddToBack() {
        list.addToBack("0a");   // 0a
        list.addToBack("1a");   // 0a, 1a
        list.addToBack("2a");   // 0a, 1a, 2a
        list.addToBack("3a");   // 0a, 1a, 2a, 3a
        list.addToBack("4a");   // 0a, 1a, 2a, 3a, 4a

        assertEquals(5, list.size());

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        expected[4] = "4a";
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveAtIndex() {
        String temp = "2a"; // For equality checking.

        list.addAtIndex(0, "0a");   // 0a
        list.addAtIndex(1, "1a");   // 0a, 1a
        list.addAtIndex(2, temp);   // 0a, 1a, 2a
        list.addAtIndex(3, "3a");   // 0a, 1a, 2a, 3a
        list.addAtIndex(4, "4a");   // 0a, 1a, 2a, 3a, 4a
        list.addAtIndex(5, "5a");   // 0a, 1a, 2a, 3a, 4a, 5a
        assertEquals(6, list.size());

        // assertSame checks for reference equality whereas assertEquals checks
        // value equality.
        assertSame(temp, list.removeAtIndex(2));    // 0a, 1a, 3a, 4a, 5a

        assertEquals(5, list.size());

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "3a";
        expected[3] = "4a";
        expected[4] = "5a";
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromFront() {
        String temp = "0a"; // For equality checking.

        list.addAtIndex(0, temp);   // 0a
        list.addAtIndex(1, "1a");   // 0a, 1a
        list.addAtIndex(2, "2a");   // 0a, 1a, 2a
        list.addAtIndex(3, "3a");   // 0a, 1a, 2a, 3a
        list.addAtIndex(4, "4a");   // 0a, 1a, 2a, 3a, 4a
        list.addAtIndex(5, "5a");   // 0a, 1a, 2a, 3a, 4a, 5a
        assertEquals(6, list.size());

        // assertSame checks for reference equality whereas assertEquals checks
        // value equality.
        assertSame(temp, list.removeFromFront());   // 1a, 2a, 3a, 4a, 5a

        assertEquals(5, list.size());

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = "1a";
        expected[1] = "2a";
        expected[2] = "3a";
        expected[3] = "4a";
        expected[4] = "5a";
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromBack() {
        String temp = "5a"; // For equality checking.

        list.addAtIndex(0, "0a");   // 0a
        list.addAtIndex(1, "1a");   // 0a, 1a
        list.addAtIndex(2, "2a");   // 0a, 1a, 2a
        list.addAtIndex(3, "3a");   // 0a, 1a, 2a, 3a
        list.addAtIndex(4, "4a");   // 0a, 1a, 2a, 3a, 4a
        list.addAtIndex(5, temp);   // 0a, 1a, 2a, 3a, 4a, 5a
        assertEquals(6, list.size());

        // assertSame checks for reference equality whereas assertEquals checks
        // value equality.
        assertSame(temp, list.removeFromBack());    // 0a, 1a, 2a, 3a, 4a

        assertEquals(5, list.size());

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        expected[4] = "4a";
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testGet() {
        list.addAtIndex(0, "0a");   // 0a
        list.addAtIndex(1, "1a");   // 0a, 1a
        list.addAtIndex(2, "2a");   // 0a, 1a, 2a
        list.addAtIndex(3, "3a");   // 0a, 1a, 2a, 3a
        list.addAtIndex(4, "4a");   // 0a, 1a, 2a, 3a, 4a
        assertEquals(5, list.size());

        assertEquals("0a", list.get(0));
        assertEquals("1a", list.get(1));
        assertEquals("2a", list.get(2));
        assertEquals("3a", list.get(3));
        assertEquals("4a", list.get(4));
    }

    @Test(timeout = TIMEOUT)
    public void testIsEmpty() {
        // Should be empty at initialization
        assertTrue(list.isEmpty());

        // Should not be empty after adding elements
        list.addAtIndex(0, "0a");   // 0a
        list.addAtIndex(1, "1a");   // 0a, 1a
        list.addAtIndex(2, "2a");   // 0a, 1a, 2a
        list.addAtIndex(3, "3a");   // 0a, 1a, 2a, 3a
        list.addAtIndex(4, "4a");   // 0a, 1a, 2a, 3a, 4a
        assertEquals(5, list.size());

        assertFalse(list.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void testClear() {
        list.addAtIndex(0, "0a");   // 0a
        list.addAtIndex(1, "1a");   // 0a, 1a
        list.addAtIndex(2, "2a");   // 0a, 1a, 2a
        list.addAtIndex(3, "3a");   // 0a, 1a, 2a, 3a
        list.addAtIndex(4, "4a");   // 0a, 1a, 2a, 3a, 4a
        assertEquals(5, list.size());

        // Clearing the list should empty the array and reset size
        list.clear();

        assertEquals(0, list.size());
        assertArrayEquals(new Object[ArrayList.INITIAL_CAPACITY],
                list.getBackingArray());
    }*/
}