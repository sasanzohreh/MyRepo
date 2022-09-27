import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.util.NoSuchElementException;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertSame;
/**
 * This is a basic set of unit tests for ArrayDeque and LinkedDeque.
 *
 * Passing these tests doesn't guarantee any grade on these assignments. These
 * student JUnits that we provide should be thought of as a sanity check to
 * help you get started on the homework and writing JUnits in general.
 *
 * We highly encourage you to write your own set of JUnits for each homework
 * to cover edge cases you can think of for each data structure. Your code must
 * work correctly and efficiently in all cases, which is why it's important
 * to write comprehensive tests to cover as many cases as possible.
 *
 * @author CS 1332 TAs
 * @version 1.0
 */
public class DequeStudentTest {

    private static final int TIMEOUT = 200;
    private ArrayDeque<String> array;
    private LinkedDeque<String> linked;

    @Before
    public void setup() {
        array = new ArrayDeque<>();
        linked = new LinkedDeque<>();
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, array.size());
        assertArrayEquals(new Object[ArrayDeque.INITIAL_CAPACITY],
            array.getBackingArray());
        assertEquals(0, linked.size());
        assertNull(linked.getHead());
        assertNull(linked.getTail());
    }

    @Test(timeout = TIMEOUT)
    public void testArrayDequeNoWrapAround() {
        array.addLast("0a"); // 0a, _, _, _, _, _, _, _, _, _, _
        array.addLast("1a"); // 0a, 1a, _, _, _, _, _, _, _, _, _
        array.addLast("2a"); // 0a, 1a, 2a,  _, _, _, _, _, _, _, _
        array.addLast("3a"); // 0a, 1a, 2a, 3a, _, _, _, _, _, _, _
        array.addLast("4a"); // 0a, 1a, 2a, 3a, 4a, _, _, _, _, _, _

        assertEquals(5, array.size());
        String[] expected = new String[ArrayDeque.INITIAL_CAPACITY];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        expected[4] = "4a";
        assertArrayEquals(expected, array.getBackingArray());
        assertEquals("0a", array.getFirst());
        assertEquals("4a", array.getLast());

        // _, 1a, 2a, 3a, 4a, _, _, _, _, _, _
        assertEquals("0a", array.removeFirst());
        // _, _, 2a, 3a, 4a, _, _, _, _, _, _
        assertEquals("1a", array.removeFirst());
        // _, _, 2a, 3a, _, _, _, _, _, _, _
        assertEquals("4a", array.removeLast());
        // _, _, 2a, _, _, _, _, _, _, _, _
        assertEquals("3a", array.removeLast());

        assertEquals(1, array.size());
        expected[0] = null;
        expected[1] = null;
        expected[3] = null;
        expected[4] = null;
        assertArrayEquals(expected, array.getBackingArray());
        assertEquals("2a", array.getFirst());
        assertEquals("2a", array.getLast());
    }

    @Test(timeout = TIMEOUT)
    public void testArrayDequeWrapAround() {
        array.addFirst("4a"); // _, _, _, _, _, _, _, _, _, _, 4a
        array.addFirst("3a"); // _, _, _, _, _, _, _, _, _, 3a, 4a
        array.addFirst("2a"); // _, _, _, _, _, _, _, _, 2a, 3a, 4a
        array.addFirst("1a"); // _, _, _, _, _, _, _, 1a, 2a, 3a, 4a
        array.addFirst("0a"); // _, _, _, _, _, _, 0a, 1a, 2a, 3a, 4a

        assertEquals(5, array.size());
        String[] expected = new String[ArrayDeque.INITIAL_CAPACITY];
        expected[10] = "4a";
        expected[9] = "3a";
        expected[8] = "2a";
        expected[7] = "1a";
        expected[6] = "0a";
        assertArrayEquals(expected, array.getBackingArray());
        assertEquals("0a", array.getFirst());
        assertEquals("4a", array.getLast());

        // _, _, _, _, _, _, 0a, 1a, 2a, 3a, _
        assertEquals("4a", array.removeLast());

        assertEquals(4, array.size());
        expected[10] = null;
        assertArrayEquals(expected, array.getBackingArray());
        assertEquals("0a", array.getFirst());
        assertEquals("3a", array.getLast());

        array.addLast("5a"); // _, _, _, _, _, _, 0a, 1a, 2a, 3a, 5a
        array.addLast("6a"); // 6a, _, _, _, _, _, 0a, 1a, 2a, 3a, 5a

        assertEquals(6, array.size());
        expected[10] = "5a";
        expected[0] = "6a";
        assertArrayEquals(expected, array.getBackingArray());
        assertEquals("0a", array.getFirst());
        assertEquals("6a", array.getLast());
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedDequeAdd() {
        linked.addFirst("1a"); // 1a
        linked.addFirst("0a"); // 0a, 1a
        linked.addLast("2a"); // 0a, 1a, 2a
        linked.addLast("3a"); // 0a, 1a, 2a, 3a

        assertEquals(4, linked.size());

        LinkedNode<String> cur = linked.getHead();
        assertNotNull(cur);
        assertNull(cur.getPrevious());
        assertEquals("0a", cur.getData());

        LinkedNode<String> prev = cur;
        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals(prev, cur.getPrevious());
        assertEquals("1a", cur.getData());

        prev = cur;
        cur = cur.getNext();
        assertNotEquals(null, cur);
        assertEquals(prev, cur.getPrevious());
        assertEquals("2a", cur.getData());

        prev = cur;
        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals(prev, cur.getPrevious());
        assertEquals("3a", cur.getData());
        assertEquals(linked.getTail(), cur);
        assertNull(cur.getNext());
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedDequeRemove() {
        linked.addFirst("1a"); // 1a
        linked.addFirst("0a"); // 0a, 1a
        linked.addLast("2a"); // 0a, 1a, 2a
        linked.addLast("3a"); // 0a, 1a, 2a, 3a

        assertEquals(4, linked.size());

        assertEquals("0a", linked.removeFirst()); // 1a, 2a, 3a
        assertEquals("3a", linked.removeLast()); // 1a, 2a

        LinkedNode<String> cur = linked.getHead();
        assertNotNull(cur);
        assertNull(cur.getPrevious());
        assertEquals("1a", cur.getData());

        LinkedNode<String> prev = cur;
        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals(prev, cur.getPrevious());
        assertEquals("2a", cur.getData());
        assertEquals(linked.getTail(), cur);

        cur = cur.getNext();
        assertNull(cur);
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedDequeGet() {
        linked.addLast("0a"); // 0a
        linked.addLast("1a"); // 0a, 1a
        linked.addLast("2a"); // 0a, 1a, 2a
        linked.addLast("3a"); // 0a, 1a, 2a, 3a

        assertEquals("0a", linked.getFirst());
        assertEquals("3a", linked.getLast());
    }

    @Test(timeout = TIMEOUT)
    public void testArrayDequeAddLastResize() {
        for (int i = 0; i < array.INITIAL_CAPACITY; ++i) {
            array.addLast(i + "a");
        }
        // 0a, 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a, 9a, 10a
        //front                                    last
        assertEquals(array.INITIAL_CAPACITY, array.size());

        String[] expected = new String[ArrayDeque.INITIAL_CAPACITY];
        for (int i = 0; i < array.INITIAL_CAPACITY; ++i) {
            expected[i] = i + "a";
        }
        assertArrayEquals(expected, array.getBackingArray());
        assertEquals("0a", array.getFirst());
        assertEquals("10a", array.getLast());

        array.addLast("11a");
        // 0a, 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a, 9a, 10a, 11a, ...
        //front                                         last
        assertEquals(array.INITIAL_CAPACITY + 1, array.size());
        assertEquals(array.INITIAL_CAPACITY * 2, ((Object[]) array.getBackingArray()).length);

        expected = new String[ArrayDeque.INITIAL_CAPACITY * 2];
        for (int i = 0; i < array.INITIAL_CAPACITY + 1; ++i) {
            expected[i] = i + "a";
        }
        assertArrayEquals(expected, array.getBackingArray());
        assertEquals("0a", array.getFirst());
        assertEquals("11a", array.getLast());
    }

    @Test(timeout = TIMEOUT)
    public void testArrayDequeAddFirstResize() {
        for (int i = 0; i < array.INITIAL_CAPACITY; ++i) {
            array.addFirst(i + "a");
        }
        // 10a, 9a, 8a, 7a, 6a, 5a, 4a, 3a, 2a, 1a, 0a
        //front                                    last
        assertEquals(array.INITIAL_CAPACITY, array.size());

        String[] expected = new String[ArrayDeque.INITIAL_CAPACITY];
        for (int i = array.INITIAL_CAPACITY - 1; i >= 0; --i) {
            expected[i] = (array.INITIAL_CAPACITY - 1 - i) + "a";
        }
        assertArrayEquals(expected, array.getBackingArray());
        assertEquals("10a", array.getFirst());
        assertEquals("0a", array.getLast());

        array.addFirst("11a");
        // 11a, 10a, 9a, 8a, 7a, 6a, 5a, 4a, 3a, 2a, 1a, 0a, .....
        //front                                          last
        assertEquals(array.INITIAL_CAPACITY + 1, array.size());
        assertEquals(array.INITIAL_CAPACITY * 2, ((Object[]) array.getBackingArray()).length);

        expected = new String[ArrayDeque.INITIAL_CAPACITY * 2];
        for (int i = ArrayDeque.INITIAL_CAPACITY; i >= 0; --i) {
            expected[i] = (ArrayDeque.INITIAL_CAPACITY - i) + "a";
        }
        assertArrayEquals(expected, array.getBackingArray());
        assertEquals("11a", array.getFirst());
        assertEquals("0a", array.getLast());
    }

    @Test(timeout = TIMEOUT)
    public void testArrayDequeRemoveFirstWrapAround() {
        array.addFirst("0a"); // _, _, _, _, _, _, _, _, _, _, 0a
        array.addLast("1a"); // 1a, _, _, _, _, _, _, _, _, _, 0a
        array.addLast("2a"); // 1a, 2a, _, _, _, _, _, _, _, _, 0a
        array.addLast("3a"); // 1a, 2a, 3a, _, _, _, _, _, _, _, 0a

        assertEquals(4, array.size());
        String[] expected = new String[ArrayDeque.INITIAL_CAPACITY];
        expected[0] = "1a";
        expected[1] = "2a";
        expected[2] = "3a";
        expected[ArrayDeque.INITIAL_CAPACITY - 1] = "0a";

        assertArrayEquals(expected, array.getBackingArray());
        assertEquals("0a", array.getFirst());
        assertEquals("3a", array.getLast());

        assertEquals("0a", array.removeFirst()); // 1a, 2a, 3a, _, _, _, _, _, _, _, _
        assertEquals("1a", array.getFirst());
        assertEquals("3a", array.getLast());
        assertEquals(3, array.size());
        expected[ArrayDeque.INITIAL_CAPACITY - 1] = null;
        assertArrayEquals(expected, array.getBackingArray());

        assertEquals("1a", array.removeFirst()); // _, 2a, 3a, _, _, _, _, _, _, _, _
        assertEquals("2a", array.getFirst());
        assertEquals("3a", array.getLast());
        assertEquals(2, array.size());
        expected[0] = null;
        assertArrayEquals(expected, array.getBackingArray());

        assertEquals("2a", array.removeFirst()); // _, _, 3a, _, _, _, _, _, _, _, _
        assertEquals("3a", array.getFirst());
        assertEquals("3a", array.getLast());
        assertEquals(1, array.size());
        expected[1] = null;
        assertArrayEquals(expected, array.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testArrayDequeRemoveLastWrapAround() {
        array.addFirst("0a"); // _, _, _, _, _, _, _, _, _, _, 0a
        array.addFirst("1a"); // _, _, _, _, _, _, _, _, _, 1a, 0a
        array.addFirst("2a"); // _, _, _, _, _, _, _, _, 2a, 1a, 0a
        array.addLast("3a"); // 3a, _, _, _, _, _, _, _, 2a, 1a, 0a

        assertEquals(4, array.size());
        String[] expected = new String[ArrayDeque.INITIAL_CAPACITY];
        expected[0] = "3a";
        expected[ArrayDeque.INITIAL_CAPACITY - 1] = "0a";
        expected[ArrayDeque.INITIAL_CAPACITY - 2] = "1a";
        expected[ArrayDeque.INITIAL_CAPACITY - 3] = "2a";

        assertArrayEquals(expected, array.getBackingArray());
        assertEquals("2a", array.getFirst());
        assertEquals("3a", array.getLast());

        assertEquals("3a", array.removeLast()); // _, _, _, _, _, _, _, _, 2a, 1a, 0a
        assertEquals("2a", array.getFirst());
        assertEquals("0a", array.getLast());
        assertEquals(3, array.size());
        expected[0] = null;
        assertArrayEquals(expected, array.getBackingArray());

        assertEquals("0a", array.removeLast()); // _, _, _, _, _, _, _, _, 2a, 1a, _
        assertEquals("2a", array.getFirst());
        assertEquals("1a", array.getLast());
        assertEquals(2, array.size());
        expected[ArrayDeque.INITIAL_CAPACITY - 1] = null;
        assertArrayEquals(expected, array.getBackingArray());

        assertEquals("1a", array.removeLast()); // _, _, _, _, _, _, _, _, 2a, _, _
        assertEquals("2a", array.getFirst());
        assertEquals("2a", array.getLast());
        assertEquals(1, array.size());
        expected[ArrayDeque.INITIAL_CAPACITY - 2] = null;
        assertArrayEquals(expected, array.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testArrayDequeRemoveLastBecomeEmpty() {
        array.addLast("0a"); // 0a, _, _, _, _, _, _, _, _, _, _
        array.addLast("1a"); // 0a, 1a, _, _, _, _, _, _, _, _, _

        assertEquals(2, array.size());
        String[] expected = new String[ArrayDeque.INITIAL_CAPACITY];
        expected[0] = "0a";
        expected[1] = "1a";
        assertArrayEquals(expected, array.getBackingArray());

        assertEquals("0a", array.removeFirst()); // _, 1a, _, _, _, _, _, _, _, _, _
        assertEquals(1, array.size());           //     f
        expected[0] = null;
        assertArrayEquals(expected, array.getBackingArray());

        assertEquals("1a", array.removeLast()); // _, _, _, _, _, _, _, _, _, _, _
        assertEquals(0, array.size());          //    f
        expected[1] = null;
        assertArrayEquals(expected, array.getBackingArray());

        array.addFirst("0a"); // 0a, _, _, _, _, _, _, _, _, _, _
        assertEquals(1, array.size());
        expected[0] = "0a";
        assertArrayEquals(expected, array.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testArrayDequeRemoveFirstBecomeEmpty() {
        array.addLast("0a"); // 0a, _, _, _, _, _, _, _, _, _, _
        array.addLast("1a"); // 0a, 1a, _, _, _, _, _, _, _, _, _
        // f
        assertEquals(2, array.size());
        String[] expected = new String[ArrayDeque.INITIAL_CAPACITY];
        expected[0] = "0a";
        expected[1] = "1a";
        assertArrayEquals(expected, array.getBackingArray());

        assertEquals("0a", array.removeFirst()); // _, 1a, _, _, _, _, _, _, _, _, _
        assertEquals(1, array.size());           //     f
        expected[0] = null;
        assertArrayEquals(expected, array.getBackingArray());

        assertEquals("1a", array.removeFirst()); // _, _, _, _, _, _, _, _, _, _, _
        assertEquals(0, array.size());           //       f
        expected[1] = null;
        assertArrayEquals(expected, array.getBackingArray());

        array.addFirst("0a"); // _, 0a, _, _, _, _, _, _, _, _, _
        assertEquals(1, array.size());
        expected[1] = "0a";
        assertArrayEquals(expected, array.getBackingArray());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testArrayDequeAddFirstNull() {
        array.addFirst(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testArrayDequeAddLastNull() {
        array.addLast(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testArrayDequeRemoveFirstWhenEmpty() {
        array.removeFirst();
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testArrayDequeRemoveLastWhenEmpty() {
        array.removeLast();
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testArrayDequeGetFirstWhenEmpty() {
        array.getFirst();
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testArrayDequeGetLastWhenEmpty() {
        array.getLast();
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedDequeRemoveFirstBecomeEmpty() {
        linked.addLast("0a"); // 0a
        linked.addLast("1a"); // 0a, 1a

        assertEquals("0a", linked.removeFirst());
        assertEquals(linked.getHead(), linked.getTail());
        assertNull(linked.getHead().getNext());
        assertNull(linked.getHead().getPrevious());

        assertEquals("1a", linked.removeFirst());

        assertNull(linked.getHead());
        assertNull(linked.getTail());
        assertEquals(0, linked.size());
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedDequeRemoveLastBecomeEmpty() {
        linked.addLast("0a"); // 0a
        linked.addLast("1a"); // 0a, 1a

        assertEquals("1a", linked.removeLast());
        assertEquals(linked.getHead(), linked.getTail());
        assertNull(linked.getHead().getNext());
        assertNull(linked.getHead().getPrevious());

        assertEquals("0a", linked.removeLast());

        assertNull(linked.getHead());
        assertNull(linked.getTail());
        assertEquals(0, linked.size());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testLinkedDequeAddFirstNull() {
        linked.addFirst(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testLinkedDequeAddLastNull() {
        linked.addLast(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testLinkedDequeRemoveFirstWhenEmpty() {
        linked.removeFirst();
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testLinkedDequeRemoveLastWhenEmpty() {
        linked.removeLast();
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testLinkedDequeGetFirstWhenEmpty() {
        linked.getFirst();
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testLinkedDequeGetLastWhenEmpty() {
        linked.getLast();
    }

    @Test(timeout = TIMEOUT)
    public void testArrayInit() {
        assertEquals(0, array.size());
        assertArrayEquals(new String[ArrayDeque.INITIAL_CAPACITY], array.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testArrayAddFirst() {
        int initCap = ArrayDeque.INITIAL_CAPACITY;
        String[] strArray = new String[initCap];

        array.addFirst("0a"); // _ _ _ _ _ _ _ _ _ _ 0a
        strArray[initCap - 1] = "0a";
        assertEquals(1, array.size());
        assertArrayEquals(strArray, array.getBackingArray());

        array.addFirst("1a"); // _ _ _ _ _ _ _ _ _ 1a 0a
        strArray[initCap - 2] = "1a";
        assertEquals(2, array.size());
        assertArrayEquals(strArray, array.getBackingArray());

        array.addFirst("2a"); // _ _ _ _ _ _ _ _ 2a 1a 0a
        strArray[initCap - 3] = "2a";
        assertEquals(3, array.size());
        assertArrayEquals(strArray, array.getBackingArray());

        array.addFirst("3a"); // _ _ _ _ _ _ _ 3a 2a 1a 0a
        strArray[initCap - 4] = "3a";
        assertEquals(4, array.size());
        assertArrayEquals(strArray, array.getBackingArray());

        for (int i = 4; i < 12; i++) {
            array.addFirst(i + "a");
        }

        strArray = new String[2 * initCap];
        for (int i = 0; i < 12; i++) {
            strArray[i] = (11 - i) + "a";
        }
        assertEquals(12, array.size());
        assertArrayEquals(strArray, array.getBackingArray());
        // 11a 10a 9a 8a 7a 6a 5a 4a 3a 2a 1a 0a _ _ _ _ _ _ _ _ _ _
        // See the HW3.pdf page5 example.
        //"In the example below, adding another element causes the deque to resize. The array capacity is doubled
        //and the front element moves to index 0"

        array.addFirst("12a"); // 11a 10a 9a 8a 7a 6a 5a 4a 3a 2a 1a 0a _ _ _ _ _ _ _ _ _ 12a
        strArray[(2 * initCap) - 1] = "12a";
        assertEquals(13, array.size());
        assertArrayEquals(strArray, array.getBackingArray());

        for (int i = 13; i < 23; i++) {
            array.addFirst(i + "a");
        }
        // 11a 10a 9a 8a 7a 6a 5a 4a 3a 2a 1a 0a 21a 20a 19a 18a 17a 16a 15a 14a 13a 12a
        // to                                   (21a was front)
        // 22a 21a 20a 19a 18a 17a 16a 15a 14a 13a 12a 11a 10a 9a 8a 7a 6a 5a 4a 3a 2a 1a 0a _ _ ...

        strArray = new String[2 * 2 * initCap];
        for (int i = 0; i < 23; i++) {
            strArray[i] = (22 - i) + "a";
        }
        assertEquals(23, array.size());
        assertArrayEquals(strArray, array.getBackingArray());

        array.addFirst("23a");
        // 22a 21a 20a 19a 18a 17a 16a 15a 14a 13a 12a 11a 10a 9a 8a 7a 6a 5a 4a 3a 2a 1a 0a _ _ ... _ _ 23a
        strArray[(2 * 2 * initCap) - 1] = "23a";
        assertEquals(24, array.size());
        assertArrayEquals(strArray, array.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testArrayAddFirstException() {
        try {
            array.addFirst(null);
            fail();
        } catch (IllegalArgumentException e) {

        }
    }

    @Test(timeout = TIMEOUT)
    public void testArrayAddLast() {
        int initCap = ArrayDeque.INITIAL_CAPACITY;
        String[] strArray = new String[initCap];

        array.addLast("0a"); // 0a _ _ _ _ _ _ _ _ _ _
        strArray[0] = "0a";
        assertEquals(1, array.size());
        assertArrayEquals(strArray, array.getBackingArray());

        array.addLast("1a"); // 0a 1a _ _ _ _ _ _ _ _ _
        strArray[1] = "1a";
        assertEquals(2, array.size());
        assertArrayEquals(strArray, array.getBackingArray());

        array.addLast("2a"); // 0a 1a 2a _ _ _ _ _ _ _ _
        strArray[2] = "2a";
        assertEquals(3, array.size());
        assertArrayEquals(strArray, array.getBackingArray());

        array.addLast("3a"); // 0a 1a 2a 3a _ _ _ _ _ _ _
        strArray[3] = "3a";
        assertEquals(4, array.size());
        assertArrayEquals(strArray, array.getBackingArray());

        for (int i = 4; i < 12; i++) {
            array.addLast(i + "a");
        }

        strArray = new String[2 * initCap];
        for (int i = 0; i < 12; i++) {
            strArray[i] = (i) + "a";
        }
        assertEquals(12, array.size());
        assertArrayEquals(strArray, array.getBackingArray());
        // 0a 1a 2a 3a 4a 5a 6a 7a 8a 9a 10a 11a _ _ _ _ _ _ _ _ _ _ _
        // See the HW3.pdf page5 example.
        //"In the example below, adding another element causes the deque to resize. The array capacity is doubled
        //and the front element moves to index 0"

        array.addLast("12a"); // 0a 1a 2a 3a 4a 5a 6a 7a 8a 9a 10a 11a 12a _ _ _ _ _ _ _ _ _ _
        strArray[12] = "12a";
        assertEquals(13, array.size());
        assertArrayEquals(strArray, array.getBackingArray());

        //ASSUME THAT ADDFIRST/LAST METHOD WORKS PROPERLY.
        array.addFirst("-1a"); // 0a 1a 2a 3a 4a 5a 6a 7a 8a 9a 10a 11a 12a _ _ _ _ _ _ _ _ _ -1a <-Front
        array.addLast("13a"); // 0a 1a 2a 3a 4a 5a 6a 7a 8a 9a 10a 11a 12a 13a _ _ _ _ _ _ _ _ -1a <-Front
        strArray[21] = "-1a";
        strArray[13] = "13a";
        assertEquals(15, array.size());
        assertArrayEquals(strArray, array.getBackingArray());


        for (int i = 14; i < 23; i++) {
            array.addLast(i + "a");
        }
        // 0a 1a 2a 3a 4a 5a 6a 7a 8a 9a 10a 11a 12a 13a 14a 15a 16a 17a 18a 19a 20a 21a -1a <-Front
        // to                                                                   (-1a was front)
        // -1a 0a 1a 2a 3a 4a 5a 6a 7a 8a 9a 10a 11a 12a 13a 14a 15a 16a 17a 18a 19a 20a 21a 22a _ _ ...

        strArray = new String[2 * 2 * initCap];
        for (int i = 0; i < 24; i++) {
            strArray[i] = (i - 1) + "a";
        }
        assertEquals(24, array.size());
        assertArrayEquals(strArray, array.getBackingArray());

        array.addLast("23a");
        // -1a 0a 1a 2a 3a 4a 5a 6a 7a 8a 9a 10a 11a 12a 13a 14a 15a 16a 17a 18a 19a 20a 21a 22a 23a _ ...
        strArray[24] = "23a";
        assertEquals(25, array.size());
        assertArrayEquals(strArray, array.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testArrayAddLastException() {
        try {
            array.addLast(null);
            fail();
        } catch (IllegalArgumentException e) {

        }
    }

    @Test(timeout = TIMEOUT)
    public void testArrayRemoveFirst() {
        for (int i = 0; i < 10; i++) {
            array.addLast(i + "a");
        }
        //0a 1a 2a 3a 4a 5a 6a 7a 8a 9a _ (Front == index 0)
        //ASSUME THAT ADDLAST METHOD WORKS PROPERLY.

        assertEquals("0a", array.removeFirst());
        //_ 1a 2a 3a 4a 5a 6a 7a 8a 9a _ (Front == index 1)
        Object[] temp = array.getBackingArray();
        assertNull(temp[0]);
        //Replace any spots that you remove from with null. Failure to do so can
        //result in loss of points. (FROM JAVA DOCS)
        assertEquals(9, array.size());

        assertEquals("1a", array.removeFirst());
        //_ _ 2a 3a 4a 5a 6a 7a 8a 9a _ (Front == index 2)
        temp = array.getBackingArray();
        assertNull(temp[0]);
        assertNull(temp[1]);
        assertEquals(8, array.size());

        assertEquals("2a", array.removeFirst());
        //_ _ _ 3a 4a 5a 6a 7a 8a 9a _ (Front == index 3)
        temp = array.getBackingArray();
        assertNull(temp[0]);
        assertNull(temp[1]);
        assertNull(temp[2]);
        assertEquals(7, array.size());

        array.addFirst("2a");
        //ASSUME THAT ADDFIRST METHOD WORKS PROPERLY. (For Checking Front variable)
        //_ _ 2a 3a 4a 5a 6a 7a 8a 9a _ (Front == index 2)

        assertEquals("2a", array.removeFirst());
        //_ _ _ 3a 4a 5a 6a 7a 8a 9a _ (Front == index 3)
        temp = array.getBackingArray();
        assertNull(temp[0]);
        assertNull(temp[1]);
        assertNull(temp[2]);
        assertEquals(7, array.size());

        for (int i = 0; i < 7; i++) {
            assertEquals((3 + i) + "a", array.removeFirst());
        }
        //_ _ _ _ _ _ _ _ _ _ _ EMPTY (Front SHOULD BE INDEX 10)
        assertEquals(0, array.size());
        temp = array.getBackingArray();
        for (int i = 0; i < 11; i++) {
            assertNull(temp[i]);
        }
        array.addFirst("0a");
        //_ _ _ _ _ _ _ _ _ 0a _
        temp = array.getBackingArray();
        //ASSUME THAT ADDFIRST METHOD WORKS PROPERLY. (For Checking Front variable)
        assertEquals("0a", temp[9]);
        // Front should be index 9
        // See HW3.pdf page5 example
        // From Page4 "after removing the last element in the deque, move the front variable like you normally
        // would. Do not explicitly reset it to 0. This effectively means that going from size 1 to size 0
        // @@SHOULD NOT BE A SPECIAL@@ CASE for your code."
    }

    @Test(timeout = TIMEOUT)
    public void testArrayRemoveFirstException() {
        try {
            array.removeFirst();
            fail();
        } catch (NoSuchElementException e) {

        }
    }

    @Test(timeout = TIMEOUT)
    public void testArrayRemoveLast() {
        for (int i = 0; i < 10; i++) {
            array.addLast(i + "a");
        }
        //0a 1a 2a 3a 4a 5a 6a 7a 8a 9a _ (Front == index 0)
        //ASSUME THAT ADDLAST METHOD WORKS PROPERLY.

        assertEquals("9a", array.removeLast());
        //0a 1a 2a 3a 4a 5a 6a 7a 8a _ _ (Front == index 0)
        Object[] temp = array.getBackingArray();
        assertNull(temp[9]);
        //Replace any spots that you remove from with null. Failure to do so can
        //result in loss of points. (FROM JAVA DOCS)
        assertEquals(9, array.size());

        assertEquals("8a", array.removeLast());
        //0a 1a 2a 3a 4a 5a 6a 7a _ _ _ (Front == index 0)
        temp = array.getBackingArray();
        assertNull(temp[8]);
        assertNull(temp[9]);
        assertEquals(8, array.size());

        assertEquals("7a", array.removeLast());
        //0a 1a 2a 3a 4a 5a 6a _ _ _ _ (Front == index 0)
        temp = array.getBackingArray();
        assertNull(temp[7]);
        assertNull(temp[8]);
        assertNull(temp[9]);
        assertEquals(7, array.size());

        array.addFirst("-1a");
        //ASSUME THAT ADDFIRST METHOD WORKS PROPERLY. (For Checking Front variable)
        //0a 1a 2a 3a 4a 5a 6a _ _ _ -1a (Front == index 10)

        assertEquals("6a", array.removeLast());
        //0a 1a 2a 3a 4a 5a _ _ _ _ -1a (Front == index 10)
        temp = array.getBackingArray();
        assertNull(temp[6]);
        assertNull(temp[7]);
        assertNull(temp[8]);
        assertNull(temp[9]);
        assertEquals("-1a", temp[10]);
        assertEquals(7, array.size());

        for (int i = 0; i < 7; i++) {
            assertEquals((5 - i) + "a", array.removeLast());
        }
        //_ _ _ _ _ _ _ _ _ _ _ EMPTY (Front SHOULD BE INDEX 10)
        assertEquals(0, array.size());
        temp = array.getBackingArray();
        for (int i = 0; i < 11; i++) {
            assertNull(temp[i]);
        }
        array.addFirst("0a");
        //_ _ _ _ _ _ _ _ _ 0a _
        temp = array.getBackingArray();
        //ASSUME THAT ADDFIRST METHOD WORKS PROPERLY. (For Checking Front variable)
        assertEquals("0a", temp[9]);
        // Front should be index 9
        // See HW3.pdf page5 example
        // From Page4 "after removing the last element in the deque, move the front variable like you normally
        // would. Do not explicitly reset it to 0. This effectively means that going from size 1 to size 0
        // @@SHOULD NOT BE A SPECIAL@@ CASE for your code."
    }

    @Test(timeout = TIMEOUT)
    public void testArrayRemoveLastException() {
        try {
            array.removeLast();
            fail();
        } catch (NoSuchElementException e) {

        }
    }

    @Test(timeout = TIMEOUT)
    public void testArrayGetFirst() {
        for (int i = 0; i < 4; i++) {
            array.addLast(i + "a");
        }
        //0a 1a 2a 3a _ _ _ _ _ _ _ (Front == index 0)
        //ASSUME THAT ADDLAST METHOD WORKS PROPERLY.
        assertEquals("0a", array.getFirst());

        array.addFirst("-1a");
        //0a 1a 2a 3a _ _ _ _ _ _ -1a (Front == index 10)
        //ASSUME THAT ADDFIRST METHOD WORKS PROPERLY.
        assertEquals("-1a", array.getFirst());

        array.addFirst("-2a");
        //0a 1a 2a 3a _ _ _ _ _ -2a -1a (Front == index 9)
        //ASSUME THAT ADDFIRST METHOD WORKS PROPERLY.
        assertEquals("-2a", array.getFirst());

        array.removeFirst();
        array.removeFirst();
        array.removeFirst();
        //_ 1a 2a 3a _ _ _ _ _ _ _ (Front == index 1)
        //ASSUME THAT REMOVEFIRST METHOD WORKS PROPERLY.
        assertEquals("1a", array.getFirst());

        for (int i = 0; i < 8; i++) {
            array.addLast((i + 4) + "a");
        }
        // 11a 1a 2a 3a 4a 5a 6a 7a 8a 9a 10a (Front == index 1)
        assertEquals("1a", array.getFirst());

        array.addLast("12a");
        // 1a 2a 3a 4a 5a 6a 7a 8a 9a 10a 11a 12a _ _ _ _ _ _ _ _ _ _ _(Front == index 0)
        assertEquals("1a", array.getFirst());
    }

    @Test(timeout = TIMEOUT)
    public void testArrayGetFirstException() {
        try {
            array.getFirst();
            fail();
        } catch (NoSuchElementException e) {
        }
    }

    @Test(timeout = TIMEOUT)
    public void testArrayGetLast() {
        for (int i = 0; i < 4; i++) {
            array.addLast(i + "a");
        }
        //0a 1a 2a 3a _ _ _ _ _ _ _ (Front == index 0)
        //ASSUME THAT ADDLAST METHOD WORKS PROPERLY.
        assertEquals("3a", array.getLast());

        array.addFirst("-1a");
        //0a 1a 2a 3a _ _ _ _ _ _ -1a (Front == index 10)
        //ASSUME THAT ADDFIRST METHOD WORKS PROPERLY.
        assertEquals("3a", array.getLast());

        array.addFirst("-2a");
        //0a 1a 2a 3a _ _ _ _ _ -2a -1a (Front == index 9)
        //ASSUME THAT ADDFIRST METHOD WORKS PROPERLY.
        assertEquals("3a", array.getLast());

        array.removeLast();
        array.removeLast();
        array.removeLast();
        //0a _ _ _ _ _ _ _ _ -2a -1a (Front == index 9)
        //ASSUME THAT REMOVEFIRST METHOD WORKS PROPERLY.
        assertEquals("0a", array.getLast());

        for (int i = 0; i < 8; i++) {
            array.addLast((i + 1) + "a");
        }
        // 0a 1a 2a 3a 4a 5a 6a 7a 8a -2a -1a (Front == index 9)
        assertEquals("8a", array.getLast());

        array.addLast("9a");
        // -2a -1a 0a 1a 2a 3a 4a 5a 6a 7a 8a 9a _ _ _ _ _ _ _ _ _ _ _(Front == index 0)
        assertEquals("9a", array.getLast());
    }

    @Test(timeout = TIMEOUT)
    public void testArrayGetLastException() {
        try {
            array.getLast();
            fail();
        } catch (NoSuchElementException e) {

        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////LINKED DEQUE TEST//////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////

    @Test(timeout = TIMEOUT)
    public void testLinkedInit() {
        assertEquals(0, linked.size());
        assertNull(linked.getHead());
        assertNull(linked.getTail());
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedAddFirst() {
        linked.addFirst("4a"); // head -> 4a <- tail
        assertEquals(1, linked.size());
        assertEquals("4a", linked.getHead().getData());
        assertNull(linked.getHead().getPrevious());
        assertNull(linked.getTail().getNext());
        assertSame(linked.getHead(), linked.getTail());

        linked.addFirst("3a"); // head -> 3a 4a <- tail
        assertEquals(2, linked.size());
        assertEquals("3a", linked.getHead().getData());
        assertEquals("4a", linked.getHead().getNext().getData());
        assertNull(linked.getHead().getPrevious());
        assertNull(linked.getTail().getNext());
        assertSame(linked.getHead(), linked.getTail().getPrevious());
        assertSame(linked.getHead().getNext(), linked.getTail());

        linked.addFirst("2a"); // head -> 2a 3a 4a <- tail
        assertEquals(3, linked.size());
        assertEquals("2a", linked.getHead().getData());
        assertEquals("3a", linked.getHead().getNext().getData());
        assertEquals("4a", linked.getHead().getNext().getNext().getData());
        assertNull(linked.getHead().getPrevious());
        assertNull(linked.getTail().getNext());
        assertSame(linked.getHead(), linked.getTail().getPrevious().getPrevious());
        assertSame(linked.getHead().getNext(), linked.getTail().getPrevious());
        assertSame(linked.getHead().getNext().getNext(), linked.getTail());
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedAddFirstException() {
        try {
            linked.addFirst(null);
            fail();
        } catch (IllegalArgumentException e) {

        }
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedAddLast() {
        linked.addLast("4a"); // head -> 4a <- tail
        assertEquals(1, linked.size());
        assertEquals("4a", linked.getHead().getData());
        assertNull(linked.getHead().getPrevious());
        assertNull(linked.getTail().getNext());
        assertSame(linked.getHead(), linked.getTail());

        linked.addLast("3a"); // head -> 4a 3a <- tail
        assertEquals(2, linked.size());
        assertEquals("4a", linked.getHead().getData());
        assertEquals("3a", linked.getHead().getNext().getData());
        assertNull(linked.getHead().getPrevious());
        assertNull(linked.getTail().getNext());
        assertSame(linked.getHead(), linked.getTail().getPrevious());
        assertSame(linked.getHead().getNext(), linked.getTail());

        linked.addLast("2a"); // head -> 4a 3a 2a <- tail
        assertEquals(3, linked.size());
        assertEquals("4a", linked.getHead().getData());
        assertEquals("3a", linked.getHead().getNext().getData());
        assertEquals("2a", linked.getHead().getNext().getNext().getData());
        assertNull(linked.getHead().getPrevious());
        assertNull(linked.getTail().getNext());
        assertSame(linked.getHead(), linked.getTail().getPrevious().getPrevious());
        assertSame(linked.getHead().getNext(), linked.getTail().getPrevious());
        assertSame(linked.getHead().getNext().getNext(), linked.getTail());
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedAddLastException() {
        try {
            linked.addLast(null);
            fail();
        } catch (IllegalArgumentException e) {

        }
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedRemoveFirst() {
        linked.addFirst("4a"); // head -> 4a <- tail
        linked.addFirst("3a"); // head -> 3a 4a <- tail
        linked.addFirst("2a"); // head -> 2a 3a 4a <- tail
        linked.addFirst("1a"); // head -> 1a 2a 3a 4a <- tail
        linked.addFirst("0a"); // head -> 0a 1a 2a 3a 4a <- tail
        // ASSUME THAT ADDFIRST METHOD WORKS PEROPERLY.

        assertEquals("0a", linked.removeFirst()); // head -> 1a 2a 3a 4a <- tail (returned: 0a)
        assertEquals(4, linked.size());
        assertEquals("1a", linked.getHead().getData());
        assertEquals("2a", linked.getHead().getNext().getData());
        assertEquals("3a", linked.getHead().getNext().getNext().getData());
        assertEquals("4a", linked.getHead().getNext().getNext().getNext().getData());
        assertNull(linked.getHead().getPrevious());
        assertNull(linked.getTail().getNext());
        assertSame(linked.getHead(), linked.getTail().getPrevious().getPrevious().getPrevious());
        assertSame(linked.getHead().getNext(), linked.getTail().getPrevious().getPrevious());
        assertSame(linked.getHead().getNext().getNext(), linked.getTail().getPrevious());
        assertSame(linked.getHead().getNext().getNext().getNext(), linked.getTail());
        //Check the links are properly connected

        assertEquals("1a", linked.removeFirst()); // head -> 2a 3a 4a <- tail (returned: 1a)
        assertEquals(3, linked.size());
        assertEquals("2a", linked.getHead().getData());
        assertEquals("3a", linked.getHead().getNext().getData());
        assertEquals("4a", linked.getHead().getNext().getNext().getData());
        assertNull(linked.getHead().getPrevious());
        assertNull(linked.getTail().getNext());
        assertSame(linked.getHead(), linked.getTail().getPrevious().getPrevious());
        assertSame(linked.getHead().getNext(), linked.getTail().getPrevious());
        assertSame(linked.getHead().getNext().getNext(), linked.getTail());
        //Check the links are properly connected

        assertEquals("2a", linked.removeFirst()); // head -> 3a 4a <- tail (returned: 2a)
        assertEquals(2, linked.size());
        assertEquals("3a", linked.getHead().getData());
        assertEquals("4a", linked.getHead().getNext().getData());
        assertNull(linked.getHead().getPrevious());
        assertNull(linked.getTail().getNext());
        assertSame(linked.getHead(), linked.getTail().getPrevious());
        assertSame(linked.getHead().getNext(), linked.getTail());
        //Check the links are properly connected

        assertEquals("3a", linked.removeFirst()); // head -> 4a <- tail (returned: 3a)
        assertEquals(1, linked.size());
        assertEquals("4a", linked.getHead().getData());
        assertNull(linked.getHead().getPrevious());
        assertNull(linked.getTail().getNext());
        assertSame(linked.getHead(), linked.getTail());
        //Check the links are properly connected

        assertEquals("4a", linked.removeFirst()); // head ->   <- tail (returned: 4a)
        assertEquals(0, linked.size());
        assertNull(linked.getHead());
        assertNull(linked.getTail());
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedRemoveFirstException() {
        try {
            linked.removeFirst();
            fail();
        } catch (NoSuchElementException e) {

        }
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedRemoveLast() {
        linked.addFirst("4a"); // head -> 4a <- tail
        linked.addFirst("3a"); // head -> 3a 4a <- tail
        linked.addFirst("2a"); // head -> 2a 3a 4a <- tail
        linked.addFirst("1a"); // head -> 1a 2a 3a 4a <- tail
        linked.addFirst("0a"); // head -> 0a 1a 2a 3a 4a <- tail
        // ASSUME THAT ADDFIRST METHOD WORKS PEROPERLY.

        assertEquals("4a", linked.removeLast()); // head -> 0a 1a 2a 3a <- tail (returned: 4a)
        assertEquals(4, linked.size());
        assertEquals("0a", linked.getHead().getData());
        assertEquals("1a", linked.getHead().getNext().getData());
        assertEquals("2a", linked.getHead().getNext().getNext().getData());
        assertEquals("3a", linked.getHead().getNext().getNext().getNext().getData());
        assertNull(linked.getHead().getPrevious());
        assertNull(linked.getTail().getNext());
        assertSame(linked.getHead(), linked.getTail().getPrevious().getPrevious().getPrevious());
        assertSame(linked.getHead().getNext(), linked.getTail().getPrevious().getPrevious());
        assertSame(linked.getHead().getNext().getNext(), linked.getTail().getPrevious());
        assertSame(linked.getHead().getNext().getNext().getNext(), linked.getTail());
        //Check the links are properly connected

        assertEquals("3a", linked.removeLast()); // head -> 0a 1a 2a <- tail (returned: 3a)
        assertEquals(3, linked.size());
        assertEquals("0a", linked.getHead().getData());
        assertEquals("1a", linked.getHead().getNext().getData());
        assertEquals("2a", linked.getHead().getNext().getNext().getData());
        assertNull(linked.getHead().getPrevious());
        assertNull(linked.getTail().getNext());
        assertSame(linked.getHead(), linked.getTail().getPrevious().getPrevious());
        assertSame(linked.getHead().getNext(), linked.getTail().getPrevious());
        assertSame(linked.getHead().getNext().getNext(), linked.getTail());
        //Check the links are properly connected

        assertEquals("2a", linked.removeLast()); // head -> 0a 1a <- tail (returned: 2a)
        assertEquals(2, linked.size());
        assertEquals("0a", linked.getHead().getData());
        assertEquals("1a", linked.getHead().getNext().getData());
        assertNull(linked.getHead().getPrevious());
        assertNull(linked.getTail().getNext());
        assertSame(linked.getHead(), linked.getTail().getPrevious());
        assertSame(linked.getHead().getNext(), linked.getTail());
        //Check the links are properly connected

        assertEquals("1a", linked.removeLast()); // head -> 0a <- tail (returned: 1a)
        assertEquals(1, linked.size());
        assertEquals("0a", linked.getHead().getData());
        assertNull(linked.getHead().getPrevious());
        assertNull(linked.getTail().getNext());
        assertSame(linked.getHead(), linked.getTail());
        //Check the links are properly connected

        assertEquals("0a", linked.removeLast()); // head ->   <- tail (returned: 0a)
        assertEquals(0, linked.size());
        assertNull(linked.getHead());
        assertNull(linked.getTail());
    }


    @Test(timeout = TIMEOUT)
    public void testLinkedRemoveLastException() {
        try {
            linked.removeLast();
            fail();
        } catch (NoSuchElementException e) {

        }
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedGetFirst() {
        linked.addFirst("4a"); // head -> 4a <- tail
        // ASSUME THAT ADDFIRST METHOD WORKS PEROPERLY.
        assertEquals("4a", linked.getFirst());
        assertEquals("4a", linked.getFirst());
        assertEquals(1, linked.size());
        assertEquals("4a", linked.getHead().getData());
        // Get method should not effects to any variable.

        linked.addFirst("3a"); // head -> 3a 4a <- tail
        // ASSUME THAT ADDFIRST METHOD WORKS PEROPERLY.
        assertEquals("3a", linked.getFirst());
        assertEquals("3a", linked.getFirst());
        assertEquals(2, linked.size());
        assertEquals("3a", linked.getHead().getData());
        // Get method should not effects to any variable.

        linked.addFirst("2a"); // head -> 2a 3a 4a <- tail
        // ASSUME THAT ADDFIRST METHOD WORKS PEROPERLY.
        assertEquals("2a", linked.getFirst());
        assertEquals("2a", linked.getFirst());
        assertEquals(3, linked.size());
        assertEquals("2a", linked.getHead().getData());
        // Get method should not effects to any variable.

        linked.addLast("5a"); // head -> 2a 3a 4a 5a <- tail
        // ASSUME THAT ADDLAST METHOD WORKS PEROPERLY.
        assertEquals("2a", linked.getFirst());
        assertEquals("2a", linked.getFirst());
        assertEquals(4, linked.size());
        assertEquals("2a", linked.getHead().getData());
        // Get method should not effects to any variable.

    }

    @Test(timeout = TIMEOUT)
    public void testLinkedGetFirstException() {
        try {
            linked.getFirst();
            fail();
        } catch (NoSuchElementException e) {

        }
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedGetLast() {
        linked.addFirst("4a"); // head -> 4a <- tail
        // ASSUME THAT ADDFIRST METHOD WORKS PEROPERLY.
        assertEquals("4a", linked.getLast());
        assertEquals("4a", linked.getLast());
        assertEquals(1, linked.size());
        assertEquals("4a", linked.getTail().getData());
        // Get method should not effects to any variable.

        linked.addFirst("3a"); // head -> 3a 4a <- tail
        // ASSUME THAT ADDFIRST METHOD WORKS PEROPERLY.
        assertEquals("4a", linked.getLast());
        assertEquals("4a", linked.getLast());
        assertEquals(2, linked.size());
        assertEquals("4a", linked.getTail().getData());
        // Get method should not effects to any variable.

        linked.addFirst("2a"); // head -> 2a 3a 4a <- tail
        // ASSUME THAT ADDFIRST METHOD WORKS PEROPERLY.
        assertEquals("4a", linked.getLast());
        assertEquals("4a", linked.getLast());
        assertEquals(3, linked.size());
        assertEquals("4a", linked.getTail().getData());
        // Get method should not effects to any variable.

        linked.addLast("5a"); // head -> 2a 3a 4a 5a <- tail
        // ASSUME THAT ADDLAST METHOD WORKS PEROPERLY.
        assertEquals("5a", linked.getLast());
        assertEquals("5a", linked.getLast());
        assertEquals(4, linked.size());
        assertEquals("5a", linked.getTail().getData());
        // Get method should not effects to any variable.

    }

    @Test(timeout = TIMEOUT)
    public void testLinkedGetLastException() {
        try {
            linked.getLast();
            fail();
        } catch (NoSuchElementException e) {

        }
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void addFirstArrayNull() {
        array.addFirst(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void addLastArrayNull() {
        array.addLast(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void removeFirstWhenEmpty() {
        array.removeFirst();
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void removeLastWhenEmpty() {
        array.removeLast();
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void getFirstWhenEmpty() {
        array.getFirst();
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void getLastWhenEmpty() {
        array.getLast();
    }

    @Test(timeout = TIMEOUT)
    public void addFirstArray() {
        array.addFirst("1a");
        array.addFirst("0a");
        String[] testArray = new String[11];
        testArray[9] = "0a";
        testArray[10] = "1a";
        assertEquals(2, array.size());
        assertArrayEquals(testArray, array.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void addFirstArrayResize() {
        String[] testArray = new String[]{"1a", "2a", "3a", "4a", "5a", "6a", "7a", "8a", "9a", "10a", "11a", "12a", null, null, null, null, null, null, null, null, null, "0a"};
        for (int i = 12; i >= 0; i--) {
            array.addFirst(i + "a");
        }
        assertEquals(13, array.size());
        assertEquals(22, ((Object[]) array.getBackingArray()).length);
        assertArrayEquals(testArray, array.getBackingArray());
    }

    @Test()
    public void addLastArrayResize() {
        String[] testArray = new String[]{"0a", "1a", "2a", "3a", "4a", "5a", "6a", "7a", "8a", "9a", "10a", "11a", "12a", null, null, null, null, null, null, null, null, null};
        for (int i = 0; i < 13; i++) {
            array.addLast(i + "a");
        }
        assertEquals(13, array.size());
        assertEquals(22, ((Object[]) array.getBackingArray()).length);
        assertArrayEquals(testArray, array.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void addFirstArrayResizeTwice() {
        for (int i = 24; i >= 0; i--) {
            array.addFirst(i + "a");
        }
        assertEquals(25, array.size());
        assertEquals(44, ((Object[]) array.getBackingArray()).length);
    }

    @Test(timeout = TIMEOUT)
    public void manipulateAfterEmptyList() {
        array.addFirst("0a");
        array.removeLast();
        array.addFirst("0a"); //Put at index 9
        array.addLast("1a"); //Put at index 8
        array.addFirst("-1a"); //Put at index 10
        String[] testArray = new String[11];
        testArray[8] = "-1a";
        testArray[9] = "0a";
        testArray[10] = "1a";
        assertArrayEquals(testArray, array.getBackingArray());
        assertEquals("1a", array.getLast());
        assertEquals("-1a", array.getFirst());
    }

    @Test(timeout = TIMEOUT)
    public void manipulateAfterEmptyListLast() {
        array.addLast("0a");
        array.removeFirst(); //Leave hole at index 0, front is at index 1
        array.addLast("0a"); //Put at index 1
        array.addFirst("1a"); //Put at index 0
        array.addLast("-1a"); //Put at index 2
        String[] testArray = new String[11];
        testArray[0] = "1a";
        testArray[1] = "0a";
        testArray[2] = "-1a";
        assertArrayEquals(testArray, array.getBackingArray());
        assertEquals("-1a", array.getLast());
        assertEquals("1a", array.getFirst());
    }


    //Linked List Tests
    @Test(timeout = TIMEOUT)
    public void addFirstMultiple() {
        linked.addFirst("2a");
        linked.addFirst("1a");
        linked.addFirst("0a");
        LinkedNode<String> head = linked.getHead();
        assertNull(head.getPrevious());
        assertEquals("0a", head.getData());
        assertEquals("1a", head.getNext().getData());
        assertEquals("2a", head.getNext().getNext().getData());
        assertEquals(3, linked.size());
        assertSame(head.getNext().getNext(), linked.getTail());
        assertNull(head.getNext().getNext().getNext());
    }

    @Test(timeout = TIMEOUT)
    public void addLastMultiple() {
        linked.addLast("0a");
        linked.addLast("1a");
        linked.addLast("2a");
        LinkedNode<String> tail = linked.getTail();
        assertNull(tail.getNext());
        assertEquals("2a", tail.getData());
        assertEquals("1a", tail.getPrevious().getData());
        assertEquals("0a", tail.getPrevious().getPrevious().getData());
        assertEquals(3, linked.size());
        assertSame(tail.getPrevious().getPrevious(), linked.getHead());
        assertNull(tail.getPrevious().getPrevious().getPrevious());
    }

    @Test(timeout = TIMEOUT)
    public void removeFirst() {
        linked.addLast("0a");
        linked.addLast("1a");
        assertEquals("1a", linked.removeLast());
        assertEquals(1, linked.size());
        assertEquals("0a", linked.getTail().getData());
    }

    @Test(timeout = TIMEOUT)
    public void removeLast() {
        linked.addFirst("1a");
        linked.addFirst("0a");
        assertEquals("0a", linked.removeFirst());
        assertEquals(1, linked.size());
        assertEquals("1a", linked.getHead().getData());
    }

    @Test(timeout = TIMEOUT)
    public void removeFirstToEmpty() {
        linked.addLast("0a");
        assertEquals("0a", linked.removeLast());
        assertEquals(0, linked.size());
        assertNull(linked.getTail());
    }

    @Test(timeout = TIMEOUT)
    public void removeLastToEmpty() {
        linked.addFirst("1a");
        assertEquals("1a", linked.removeFirst());
        assertEquals(0, linked.size());
        assertNull(linked.getHead());
    }

    @Test(timeout = TIMEOUT)
    public void addLast() {
        linked.addLast("1a");
        assertEquals("1a", linked.getHead().getData());
        assertEquals("1a", linked.getTail().getData());
        assertEquals(1, linked.size());
        assertSame(linked.getHead(), linked.getTail());
    }

    @Test(timeout = TIMEOUT)
    public void addFirst() {
        linked.addFirst("1a");
        assertEquals("1a", linked.getHead().getData());
        assertEquals("1a", linked.getTail().getData());
        assertEquals(1, linked.size());
        assertSame(linked.getHead(), linked.getTail());
    }

    @Test(timeout = TIMEOUT)
    public void getFirst() {
        linked.addFirst("3a");
        linked.addFirst("2a");
        linked.addFirst("1a");
        linked.addFirst("0a");
        assertEquals("0a", linked.getFirst());
    }

    @Test(timeout = TIMEOUT)
    public void getLast() {
        linked.addLast("0a");
        linked.addLast("1a");
        linked.addLast("2a");
        linked.addLast("3a");
        assertEquals("3a", linked.getLast());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void addFirstNull() {
        linked.addFirst(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void addLastNull() {
        linked.addLast(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void removeFirstNull() {
        linked.removeFirst();
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void removeLastNull() {
        linked.removeLast();
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void getFirstNull() {
        linked.getFirst();
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void getLastNull() {
        linked.getLast();
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void addFirstArrayException() {   // should throw IllegalArgumentException
        array.addFirst(null);
    }

    @Test(timeout = TIMEOUT)
    public void addFirstArraySingleElement() {  // front index needs to be set to the end = 10
        array.addFirst("0a");   // _, _, _, _, _, _, _, _, _, _, 0a

        assertEquals(1, array.size());
        String[] expected = new String[ArrayDeque.INITIAL_CAPACITY];
        for (int i = 0; i < 10; i++) {
            expected[i] = null;
        }
        expected[10] = "0a";
        assertArrayEquals(expected, array.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void addFirstArrayWrapAround() {  // EDGE CASE: front = 0 so you must use mod() to wrap around
        for (int i = 11; i >= 1; i--) {
            array.addFirst(i + "a");   // 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a, 9a, 10a, 11a
        }
        array.removeLast(); // 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a, 9a, 10a, _ (front is still 0 but need to add at the last index)
        array.addFirst("0a");   // 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a, 9a, 10a, 0a

        assertEquals(11, array.size());
        String[] expected = new String[ArrayDeque.INITIAL_CAPACITY];
        for (int i = 0; i < 11; i++) {
            expected[i] = (i + 1) % 11 + "a";
        }
        assertArrayEquals(expected, array.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void addFirstArrayFullCap() { // EDGE CASE: full capacity means new array of double initial cap size
        for (int i = 23; i >= 0; i--) {
            array.addFirst(i + "a");   // 0a, 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a, 9a, 10a, ..., 23a, ..., _
        }
        assertEquals(24, array.size());
        String[] expected = new String[44];
        for (int i = 0; i < 24; i++) {
            expected[(i + 43) % 44] = i + "a";
        }
        assertArrayEquals(expected, array.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void addFirstArrayFrontDifferent() { //
        array.addLast("0a");
        array.addLast("1a");    // 0a, 1a, _, _, _, _, _, _, _, _, _
        array.removeFirst();    // _, 1a, _, _, _, _, _, _, _, _, _

        array.addFirst("2a");   // 2a, 1a, _, _, _, _, _, _, _, _, _


        assertEquals(2, array.size());
        String[] expected = new String[11];
        expected[0] =  "2a";
        expected[1] = "1a";
        assertArrayEquals(expected, array.getBackingArray());
    }

    //ADDLAST TESTS...................................................
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void addLastArrayExceptionTest() {   // should throw IllegalArgumentException
        array.addLast(null);
    }

    @Test(timeout = TIMEOUT)
    public void addLastArraySingleElementTest() {  // front index needs to be 0
        array.addLast("0a");   // 0a, _, _, _, _, _, _, _, _, _, -

        assertEquals(1, array.size());
        String[] expected = new String[ArrayDeque.INITIAL_CAPACITY];
        expected[0] = "0a";
        for (int i = 1; i < 11; i++) {
            expected[i] = null;
        }
        assertArrayEquals(expected, array.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void addLastArrayWrapAround() {  // EDGE CASE: front = 1 (index 0 empty) so wrap around from back
        for (int i = 0; i < 11; i++) {
            array.addLast(i + "a");   // 0a, 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a, 9a, 10a
        }
        array.removeFirst();    // _, 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a, 9a, 10a ... front is now 1
        array.addLast("11a");   // 11a, 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a, 9a, 10a

        assertEquals(11, array.size());
        String[] expected = new String[ArrayDeque.INITIAL_CAPACITY];
        expected[0] = "11a";
        for (int i = 1; i < 11; i++) {
            expected[i] = i + "a";
        }
        assertArrayEquals(expected, array.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void addLastArrayFullCap() { // EDGE CASE: full capacity means new array of double initial cap size
        for (int i = 0; i < 24; i++) {
            array.addLast(i + "a");   // 0a, 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a, 9a, 10a, 11a, ..., 23a, ..., _
        }

        assertEquals(24, array.size());
        String[] expected = new String[44];
        for (int i = 0; i < 24; i++) {
            expected[i] = i + "a";
        }
        assertArrayEquals(expected, array.getBackingArray());
    }

    //REMOVEFIRST TESTS...................................................
    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void removeFirstArrayException() {
        array.removeFirst();
    }

    @Test(timeout = TIMEOUT)
    public void removeFirstArrayWrapAround() {   // EDGE CASE: removing both elements requires wrap around to 0 index
        array.addLast("0a");    // 0a, _, _, _, _, _, _, _, _, _, _
        array.addFirst("1a");   // 0a, _, _, _, _, _, _, _, _, _, 1a ... 1a is the front!!!

        array.removeFirst();    // 0a, _, _, _, _, _, _, _, _, _, _
        assertEquals(1, array.size());
        String[] expected = new String[11];
        expected[0] = "0a";
        assertArrayEquals(expected, array.getBackingArray());


        array.removeFirst();    // _, _, _, _, _, _, _, _, _, _, _
        assertEquals(0, array.size());
        expected = new String[11];
        assertArrayEquals(expected, array.getBackingArray());
    }

    //REMOVELAST TESTS...................................................
    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void removeLastArrayException() {
        array.removeLast();
    }

    @Test(timeout = TIMEOUT)
    public void removeLastArrayWrapAround() {    // EDGE CASE: removing both elements requires wrapping to last index
        array.addLast("0a");    // 0a, _, _, _, _, _, _, _, _, _, _
        array.addFirst("1a");   // 0a, _, _, _, _, _, _, _, _, _, 1a

        array.removeLast();    // _, _, _, _, _, _, _, _, _, _, 1a
        assertEquals(1, array.size());
        String[] expected = new String[11];
        expected[10] = "1a";
        assertArrayEquals(expected, array.getBackingArray());

        array.removeLast();    // _, _, _, _, _, _, _, _, _, _, _
        assertEquals(0, array.size());
        expected = new String[11];
        assertArrayEquals(expected, array.getBackingArray());
    }

    //GETFIRST TEST...................................................
    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void getFirstArrayException() {   // should throw NoSuchElementException
        array.getFirst();
    }

    //GETLAST TEST...................................................
    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void getLastArrayException() {    // should throw NoSuchElementException
        array.getLast();
    }



    //.......................................LINKED DEQUE TESTS.....................................................
    //ADDFIRST TESTS...................................................
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class) // should throw IllegalArgumentException
    public void addFirstLinkedException() {
        linked.addFirst(null);
    }

    @Test(timeout = TIMEOUT)
    public void addFirstLinkedSingleElement() { // head and tail should both be new element
        linked.addFirst("0a");
    }

    //ADDLAST TESTS...................................................
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class) // should throw IllegalArgumentException
    public void addLastLinkedException() {
        linked.addLast(null);
    }

    @Test(timeout = TIMEOUT)
    public void addLastLinkedSingleElement() { // head and tail should both be new element
        linked.addLast("0a");
    }

    //REMOVEFIRST TESTS...................................................
    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class) // should throw NoSuchElementException
    public void removeFirstLinkedException() {
        linked.removeFirst();
    }

    @Test(timeout = TIMEOUT)
    public void removeFirstLinkedBecomesEmpty() { // head and tail should both be null
        linked.addLast("0a");   // 0a

        assertEquals("0a", linked.removeFirst());   // _ EMPTY
        assertEquals(0, linked.size());
        assertNull(linked.getHead());
        assertNull(linked.getTail());
    }

    @Test(timeout = TIMEOUT)
    public void removeFirstLinkedBecomesOne() { // head and tail should both be the remaining element
        linked.addFirst("1a");
        linked.addFirst("0a");  // 0a 1a

        assertEquals("0a", linked.removeFirst());   // 1a
        assertEquals(1, linked.size());
        assertEquals("1a", linked.getHead().getData());
        assertEquals(linked.getHead(), linked.getTail());
        assertNull(linked.getHead().getPrevious());
        assertNull(linked.getHead().getNext());
    }

    //REMOVELAST TESTS...................................................
    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class) // should throw NoSuchElementException
    public void removeLastLinkedException() {
        linked.removeLast();
    }

    @Test(timeout = TIMEOUT)
    public void removeLastLinkedBecomesEmpty() { // head and tail should both be null
        linked.addLast("0a");   // 0a

        assertEquals("0a", linked.removeLast());    // _ EMPTY
        assertEquals(0, linked.size());
        assertNull(linked.getHead());
        assertNull(linked.getTail());
    }

    @Test(timeout = TIMEOUT)
    public void removeLastLinkedBecomesOne() { // head and tail should both be the remaining element
        linked.addFirst("1a");
        linked.addFirst("0a");  // 0a 1a

        assertEquals("1a", linked.removeLast());    // 0a
        assertEquals(1, linked.size());
        assertEquals("0a", linked.getHead().getData());
        assertEquals(linked.getHead(), linked.getTail());
        assertNull(linked.getHead().getPrevious());
        assertNull(linked.getHead().getNext());
    }

    //GETFIRST TESTS...................................................
    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class) // should throw NoSuchElementException
    public void getFirstLinkedException() {
        linked.getFirst();
    }

    @Test(timeout = TIMEOUT) // should throw NoSuchElementException
    public void getFirstLinkedTest() {
        linked.addFirst("2a");
        linked.addFirst("3a");
        linked.addLast("4a");
        linked.addFirst("1a");
        linked.addFirst("0a");

        assertEquals("0a", linked.getFirst());
        assertEquals(5, linked.size());
    }

    //GETLAST TESTS...................................................
    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class) // should throw NoSuchElementException
    public void getLastLinkedException() {
        linked.getLast();
    }

    @Test(timeout = TIMEOUT) // should throw NoSuchElementException
    public void getLastLinkedTest() {
        linked.addFirst("2a");
        linked.addFirst("3a");
        linked.addLast("4a");
        linked.addFirst("1a");
        linked.addFirst("0a");

        assertEquals("4a", linked.getLast());
        assertEquals(5, linked.size());
    }
}
