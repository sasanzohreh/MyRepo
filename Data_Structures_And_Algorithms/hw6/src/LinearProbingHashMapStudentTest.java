import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.NoSuchElementException;
import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * This is a basic set of unit tests for LinearProbingHashMap.
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
public class LinearProbingHashMapStudentTest {

    private static final int TIMEOUT = 200;
    private LinearProbingHashMap<Integer, String> map;
    private LinearProbingHashMap<String, Integer> map1;
    @Before
    public void setUp() {
        map = new LinearProbingHashMap<>();
        map1 = new LinearProbingHashMap<>();
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, map.size());
        assertArrayEquals(new LinearProbingMapEntry[
                LinearProbingHashMap.INITIAL_CAPACITY], map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testPut() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        assertEquals(5, map.size());
        LinearProbingMapEntry[] expected =
                new LinearProbingMapEntry[
                        LinearProbingHashMap.INITIAL_CAPACITY];
        expected[1] = new LinearProbingMapEntry<>(1, "A");
        expected[2] = new LinearProbingMapEntry<>(2, "B");
        expected[3] = new LinearProbingMapEntry<>(3, "C");
        expected[4] = new LinearProbingMapEntry<>(4, "D");
        expected[5] = new LinearProbingMapEntry<>(5, "E");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testRemove() {
        String temp = "D";

        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, temp));
        assertNull(map.put(5, "E"));

        // [_, (1, A), (2, B), (3, C), (4, D)X, (5, E), _, _, _, _, _, _, _]
        assertSame(temp, map.remove(4));
        assertEquals(4, map.size());
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[
                LinearProbingHashMap.INITIAL_CAPACITY];
        expected[1] = new LinearProbingMapEntry<>(1, "A");
        expected[2] = new LinearProbingMapEntry<>(2, "B");
        expected[3] = new LinearProbingMapEntry<>(3, "C");
        expected[4] = new LinearProbingMapEntry<>(4, "D");
        expected[4].setRemoved(true);
        expected[5] = new LinearProbingMapEntry<>(5, "E");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testGet() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        assertEquals("A", map.get(1));
        assertEquals("B", map.get(2));
        assertEquals("C", map.get(3));
        assertEquals("D", map.get(4));
        assertEquals("E", map.get(5));
    }

    @Test(timeout = TIMEOUT)
    public void testContainsKey() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        assertTrue(map.containsKey(3));
        assertFalse(map.containsKey(6));
    }

    @Test(timeout = TIMEOUT)
    public void testKeySet() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        Set<Integer> expected = new HashSet<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        expected.add(4);
        expected.add(5);
        assertEquals(expected, map.keySet());
    }

    @Test(timeout = TIMEOUT)
    public void testValues() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        List<String> expected = new LinkedList<>();
        expected.add("A");
        expected.add("B");
        expected.add("C");
        expected.add("D");
        expected.add("E");
        assertEquals(expected, map.values());
    }

    @Test(timeout = TIMEOUT)
    public void testResize() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E)]
        map.resizeBackingTable(6);
        assertEquals(5, map.size());
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[6];
        expected[1] = new LinearProbingMapEntry<>(1, "A");
        expected[2] = new LinearProbingMapEntry<>(2, "B");
        expected[3] = new LinearProbingMapEntry<>(3, "C");
        expected[4] = new LinearProbingMapEntry<>(4, "D");
        expected[5] = new LinearProbingMapEntry<>(5, "E");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testClear() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        map.clear();
        assertEquals(0, map.size());
        assertArrayEquals(new LinearProbingMapEntry[
                LinearProbingHashMap.INITIAL_CAPACITY], map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization2() {
        assertEquals(0, map.size());
        assertArrayEquals(new LinearProbingMapEntry[
                LinearProbingHashMap.INITIAL_CAPACITY], map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testPut2() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        assertEquals(5, map.size());
        LinearProbingMapEntry[] expected =
                new LinearProbingMapEntry[
                        LinearProbingHashMap.INITIAL_CAPACITY];
        expected[1] = new LinearProbingMapEntry<>(1, "A");
        expected[2] = new LinearProbingMapEntry<>(2, "B");
        expected[3] = new LinearProbingMapEntry<>(3, "C");
        expected[4] = new LinearProbingMapEntry<>(4, "D");
        expected[5] = new LinearProbingMapEntry<>(5, "E");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testRemove2() {
        String temp = "D";

        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, temp));
        assertNull(map.put(5, "E"));

        // [_, (1, A), (2, B), (3, C), (4, D)X, (5, E), _, _, _, _, _, _, _]
        assertSame(temp, map.remove(4));
        assertEquals(4, map.size());
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[
                LinearProbingHashMap.INITIAL_CAPACITY];
        expected[1] = new LinearProbingMapEntry<>(1, "A");
        expected[2] = new LinearProbingMapEntry<>(2, "B");
        expected[3] = new LinearProbingMapEntry<>(3, "C");
        expected[4] = new LinearProbingMapEntry<>(4, "D");
        expected[4].setRemoved(true);
        expected[5] = new LinearProbingMapEntry<>(5, "E");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testGet2() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        assertEquals("A", map.get(1));
        assertEquals("B", map.get(2));
        assertEquals("C", map.get(3));
        assertEquals("D", map.get(4));
        assertEquals("E", map.get(5));
    }

    @Test(timeout = TIMEOUT)
    public void testContainsKey2() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        assertTrue(map.containsKey(3));
        assertFalse(map.containsKey(6));
    }

    @Test(timeout = TIMEOUT)
    public void testKeySet2() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        Set<Integer> expected = new HashSet<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        expected.add(4);
        expected.add(5);
        assertEquals(expected, map.keySet());
    }

    @Test(timeout = TIMEOUT)
    public void testValues2() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        List<String> expected = new LinkedList<>();
        expected.add("A");
        expected.add("B");
        expected.add("C");
        expected.add("D");
        expected.add("E");
        assertEquals(expected, map.values());
    }

    @Test(timeout = TIMEOUT)
    public void testResize2() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        // [_, (1, A), (2, B), (3, C), (4, D), (5, E)]
        map.resizeBackingTable(6);
        assertEquals(5, map.size());
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[6];
        expected[1] = new LinearProbingMapEntry<>(1, "A");
        expected[2] = new LinearProbingMapEntry<>(2, "B");
        expected[3] = new LinearProbingMapEntry<>(3, "C");
        expected[4] = new LinearProbingMapEntry<>(4, "D");
        expected[5] = new LinearProbingMapEntry<>(5, "E");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testClear2() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        map.clear();
        assertEquals(0, map.size());
        assertArrayEquals(new LinearProbingMapEntry[
                LinearProbingHashMap.INITIAL_CAPACITY], map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testPutKeyAlreadyInMap() {
        String temp = "A";

        assertNull(map.put(1, temp));
        // [_, (1, A), _, _, _, _, _, _, _, _, _, _, _]

        assertSame(temp, map.put(1, "AA"));
        // [_, (1, AA), _, _, _, _, _, _, _, _, _, _, _]

        LinearProbingMapEntry[] expected =  new LinearProbingMapEntry[
                LinearProbingHashMap.INITIAL_CAPACITY];
        expected[1] = new LinearProbingMapEntry<>(1, "AA");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveAndPut() {
        String temp1 = "A";
        String temp2 = "B";
        String temp3 = "C";
        String temp4 = "D";
        LinearProbingMapEntry[] expected =  new LinearProbingMapEntry[
                LinearProbingHashMap.INITIAL_CAPACITY];

        assertNull(map.put(1, temp1));
        assertNull(map.put(14, temp2));
        // [_, (1, A), (14, B), _, _, _, _, _, _, _, _, _, _]
        expected[1] = new LinearProbingMapEntry<>(1, "A");
        expected[2] = new LinearProbingMapEntry<>(14, "B");
        assertArrayEquals(expected, map.getTable());
        assertSame(temp1, map.get(1));
        assertSame(temp2, map.get(14));
        assertFalse(map.containsKey(0));

        assertSame(temp1, map.remove(1));
        assertSame(temp2, map.remove(14));
        // [_, (1, A)X, (14, B)X, _, _, _, _, _, _, _, _, _, _]
        expected[1].setRemoved(true);
        expected[2].setRemoved(true);
        assertArrayEquals(expected, map.getTable());
        assertFalse(map.containsKey(1));
        assertFalse(map.containsKey(14));

        assertNull(map.put(27, "C"));
        // [_, (27, C), (14, B)X, _, _, _, _, _, _, _, _, _, _]
        expected[1] = new LinearProbingMapEntry<>(27, "C");
        assertArrayEquals(expected, map.getTable());
        assertTrue(map.containsKey(27));
        assertSame(temp3, map.get(27));

        assertNull(map.put(14, "D"));
        // [_, (27, C), (14, D), _, _, _, _, _, _, _, _, _, _]
        expected[2] = new LinearProbingMapEntry<>(14, "D");
        assertArrayEquals(expected, map.getTable());
        assertTrue(map.containsKey(14));
        assertSame(temp4, map.get(14));
        assertFalse(map.containsKey(12));

        assertSame(temp3, map.remove(27));
        assertSame(temp4, map.remove(14));
        // [_, (27, C)X, (14, D)X, _, _, _, _, _, _, _, _, _, _]
        expected[1].setRemoved(true);
        expected[2].setRemoved(true);
        assertArrayEquals(expected, map.getTable());
        assertFalse(map.containsKey(27));
        assertFalse(map.containsKey(14));
    }

    @Test(timeout = TIMEOUT)
    public void testResizingAfterMaxLoadFactor() {
        String temp = "A";
        LinearProbingMapEntry[] expected =  new LinearProbingMapEntry[
                LinearProbingHashMap.INITIAL_CAPACITY];

        assertNull(map.put(1, temp));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));
        assertNull(map.put(6, "F"));
        assertNull(map.put(7, "G"));
        assertNull(map.put(8, "H"));
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), (6, F), (7, G), (8, H), _, _, _, _]
        // Load Factor = 8 / 13 = 0.62
        expected[1] = new LinearProbingMapEntry<>(1, "A");
        expected[2] = new LinearProbingMapEntry<>(2, "B");
        expected[3] = new LinearProbingMapEntry<>(3, "C");
        expected[4] = new LinearProbingMapEntry<>(4, "D");
        expected[5] = new LinearProbingMapEntry<>(5, "E");
        expected[6] = new LinearProbingMapEntry<>(6, "F");
        expected[7] = new LinearProbingMapEntry<>(7, "G");
        expected[8] = new LinearProbingMapEntry<>(8, "H");
        assertArrayEquals(expected, map.getTable());

        assertSame(temp, map.remove(1));
        assertNull(map.put(9, "I"));
        assertNull(map.put(10, "J"));
        // Load Factor = 9 / 13 = 0.69
        // [_, _, (2, B), (3, C), (4, D), (5, E), (6, F), (7, G), (8, H), (9, I), (10, J), _, _,
        // _, _, _, _, _, _, _, _, _, _, _, _, _, _]
        expected =  new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY * 2 + 1];
        expected[2] = new LinearProbingMapEntry<>(2, "B");
        expected[3] = new LinearProbingMapEntry<>(3, "C");
        expected[4] = new LinearProbingMapEntry<>(4, "D");
        expected[5] = new LinearProbingMapEntry<>(5, "E");
        expected[6] = new LinearProbingMapEntry<>(6, "F");
        expected[7] = new LinearProbingMapEntry<>(7, "G");
        expected[8] = new LinearProbingMapEntry<>(8, "H");
        expected[9] = new LinearProbingMapEntry<>(9, "I");
        expected[10] = new LinearProbingMapEntry<>(10, "J");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testNegativeIndex() {
        String temp1 = "A";
        String temp2 = "B";
        String temp3 = "C";
        String temp4 = "D";
        LinearProbingMapEntry[] expected =  new LinearProbingMapEntry[
                LinearProbingHashMap.INITIAL_CAPACITY];

        assertNull(map.put(-12, temp1));
        assertNull(map.put(-25, temp2));
        // [(-25, B), _, _, _, _, _, _, _, _, _, _, _, (-12, A)]
        expected[12] = new LinearProbingMapEntry<>(-12, "A");
        expected[0] = new LinearProbingMapEntry<>(-25, "B");
        assertArrayEquals(expected, map.getTable());
        assertSame(temp1, map.get(-12));
        assertSame(temp2, map.get(-25));
        assertFalse(map.containsKey(-13));

        assertSame(temp1, map.remove(-12));
        assertSame(temp2, map.remove(-25));
        // [(-25, B)X, _, _, _, _, _, _, _, _, _, _, _, (-12, A)X]
        expected[12].setRemoved(true);
        expected[0].setRemoved(true);
        assertArrayEquals(expected, map.getTable());
        assertFalse(map.containsKey(-12));
        assertFalse(map.containsKey(-25));

        assertNull(map.put(-38, "C"));
        // [(-25, B)X, _, _, _, _, _, _, _, _, _, _, _, (-38, C)]
        expected[12] = new LinearProbingMapEntry<>(-38, "C");
        assertArrayEquals(expected, map.getTable());
        assertTrue(map.containsKey(-38));
        assertSame(temp3, map.get(-38));

        assertNull(map.put(-51, "D"));
        // [(-51, D), _, _, _, _, _, _, _, _, _, _, _, (-38, C)]
        expected[0] = new LinearProbingMapEntry<>(-51, "D");
        assertArrayEquals(expected, map.getTable());
        assertTrue(map.containsKey(-51));
        assertSame(temp4, map.get(-51));
        assertFalse(map.containsKey(-1));

        assertSame(temp3, map.remove(-38));
        assertSame(temp4, map.remove(-51));
        // [(-51, D)X, _, _, _, _, _, _, _, _, _, _, _, (-38, C)X]
        expected[12].setRemoved(true);
        expected[0].setRemoved(true);
        assertArrayEquals(expected, map.getTable());
        assertFalse(map.containsKey(-38));
        assertFalse(map.containsKey(-51));
    }

    @Test(timeout = TIMEOUT)
    public void testResizingWithNegativeIndex() {
        String temp = "A";
        LinearProbingMapEntry[] expected =  new LinearProbingMapEntry[
                LinearProbingHashMap.INITIAL_CAPACITY];

        assertNull(map.put(-12, temp));
        assertNull(map.put(-11, "B"));
        assertNull(map.put(-10, "C"));
        assertNull(map.put(-9, "D"));
        assertNull(map.put(-8, "E"));
        assertNull(map.put(-7, "F"));
        assertNull(map.put(-6, "G"));
        assertNull(map.put(-5, "H"));
        // [_, _, _, _, _, (-5, H), (-6, G), (-7, F), (-8, E), (-9, D), (-10, C), (-11, B), (-12, A)]
        // Load Factor = 8 / 13 = 0.62
        expected[12] = new LinearProbingMapEntry<>(-12, "A");
        expected[11] = new LinearProbingMapEntry<>(-11, "B");
        expected[10] = new LinearProbingMapEntry<>(-10, "C");
        expected[9] = new LinearProbingMapEntry<>(-9, "D");
        expected[8] = new LinearProbingMapEntry<>(-8, "E");
        expected[7] = new LinearProbingMapEntry<>(-7, "F");
        expected[6] = new LinearProbingMapEntry<>(-6, "G");
        expected[5] = new LinearProbingMapEntry<>(-5, "H");
        assertArrayEquals(expected, map.getTable());

        assertSame(temp, map.remove(-12));
        assertNull(map.put(-4, "I"));
        assertNull(map.put(-3, "J"));
        assertNull(map.put(-27, "K"));
        assertNull(map.put(-54, "L"));
        assertNull(map.put(0, "M"));
        // Load Factor = 9 / 13 = 0.69
        // [(-27, K), (-54, L), (0, M), (-3, J), (-4, I), (-5, H), (-6, G), (-7, F),
        // (-8, E), (-9, D), (-10, C), (-11, B), _, _, _, _, _, _, _, _, _, _, _, _, _, _, _]
        expected =  new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY * 2 + 1];
        expected[11] = new LinearProbingMapEntry<>(-11, "B");
        expected[10] = new LinearProbingMapEntry<>(-10, "C");
        expected[9] = new LinearProbingMapEntry<>(-9, "D");
        expected[8] = new LinearProbingMapEntry<>(-8, "E");
        expected[7] = new LinearProbingMapEntry<>(-7, "F");
        expected[6] = new LinearProbingMapEntry<>(-6, "G");
        expected[5] = new LinearProbingMapEntry<>(-5, "H");
        expected[4] = new LinearProbingMapEntry<>(-4, "I");
        expected[3] = new LinearProbingMapEntry<>(-3, "J");
        expected[0] = new LinearProbingMapEntry<>(-27, "K");
        expected[1] = new LinearProbingMapEntry<>(-54, "L");
        expected[2] = new LinearProbingMapEntry<>(0, "M");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testPutNullValue() {
        map.put(1, null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testPutNullKey() {
        map.put(null, "A");
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testRemoveNullKey() {
        map.remove(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveKeyNotInMap() {
        map.put(1, "A");
        map.put(14, "B");
        // [_, (1, A), (14, B), _, _, _, _, _, _, _, _, _, _]
        LinearProbingMapEntry[] expected =  new LinearProbingMapEntry[
                LinearProbingHashMap.INITIAL_CAPACITY];
        expected[1] = new LinearProbingMapEntry<>(1, "A");
        expected[2] = new LinearProbingMapEntry<>(14, "B");
        assertArrayEquals(expected, map.getTable());
        map.remove(2);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testGetNullKey() {
        map.remove(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testGetKeyNotInMap() {
        map.put(1, "A");
        map.put(14, "B");
        // [_, (1, A), (14, B), _, _, _, _, _, _, _, _, _, _]
        LinearProbingMapEntry[] expected =  new LinearProbingMapEntry[
                LinearProbingHashMap.INITIAL_CAPACITY];
        expected[1] = new LinearProbingMapEntry<>(1, "A");
        expected[2] = new LinearProbingMapEntry<>(14, "B");
        assertArrayEquals(expected, map.getTable());
        map.get(2);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testContainsNullKey() {
        map.containsKey(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testResizeToSmallerThanSize() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));
        map.resizeBackingTable(4);
    }

    @Test(timeout = TIMEOUT)
    public void testPut3() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(-1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        assertEquals(5, map.size());
        LinearProbingMapEntry[] expected =
                new LinearProbingMapEntry[
                        LinearProbingHashMap.INITIAL_CAPACITY];
        expected[1] = new LinearProbingMapEntry<>(-1, "A");
        expected[2] = new LinearProbingMapEntry<>(2, "B");
        expected[3] = new LinearProbingMapEntry<>(3, "C");
        expected[4] = new LinearProbingMapEntry<>(4, "D");
        expected[5] = new LinearProbingMapEntry<>(5, "E");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testRemove3() {
        String temp = "D";

        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, temp));
        assertNull(map.put(5, "E"));

        // [_, (1, A), (2, B), (3, C), (4, D)X, (5, E), _, _, _, _, _, _, _]
        assertSame(temp, map.remove(4));
        assertEquals(4, map.size());
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[
                LinearProbingHashMap.INITIAL_CAPACITY];
        expected[1] = new LinearProbingMapEntry<>(1, "A");
        expected[2] = new LinearProbingMapEntry<>(2, "B");
        expected[3] = new LinearProbingMapEntry<>(3, "C");
        expected[4] = new LinearProbingMapEntry<>(4, "D");
        expected[4].setRemoved(true);
        expected[5] = new LinearProbingMapEntry<>(5, "E");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveAndPutSameKey() {
        String temp = "D";

        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, temp));
        assertNull(map.put(5, "E"));

        // [_, (1, A), (2, B), (3, C), (4, D)X, (5, E), _, _, _, _, _, _, _]
        assertSame(temp, map.remove(4));
        assertEquals(4, map.size());

        // [_, (1, A), (2, B), (3, C), (4, F), (5, E), _, _, _, _, _, _, _]
        temp = "F";
        assertNull(map.put(4, temp));
        assertEquals(5, map.size());
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[
                LinearProbingHashMap.INITIAL_CAPACITY];
        expected[1] = new LinearProbingMapEntry<>(1, "A");
        expected[2] = new LinearProbingMapEntry<>(2, "B");
        expected[3] = new LinearProbingMapEntry<>(3, "C");
        expected[4] = new LinearProbingMapEntry<>(4, "F");
        expected[5] = new LinearProbingMapEntry<>(5, "E");
        assertArrayEquals(expected, map.getTable());

        // [_, (1, A), (2, B), (3, C), (4, F)X, (5, E), _, _, _, _, _, _, _]
        assertSame(temp, map.remove(4));
        assertEquals(4, map.size());

        // [_, (1, A), (2, B), (3, G), (4, F)X, (5, E), _, _, _, _, _, _, _]
        assertSame("C", map.put(3, "G"));
        expected[3] = new LinearProbingMapEntry<>(3, "G");
        expected[4].setRemoved(true);
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveAndPutDifferentKey() {
        String temp = "D";

        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, temp));
        assertNull(map.put(5, "E"));

        // [_, (1, A), (2, B), (3, C), (4, D)X, (5, E), _, _, _, _, _, _, _]
        assertSame(temp, map.remove(4));
        assertEquals(4, map.size());

        // [_, (1, A), (2, B), (3, C), (4, D)X, (5, E), (6, F), _, _, _, _, _, _]
        temp = "F";
        assertNull(map.put(6, temp));
        assertEquals(5, map.size());

        // [_, (1, A), (2, B), (3, C), (16, G), (5, E), (6, F), _, _, _, _, _, _]
        assertNull(map.put(16, "G"));
        assertEquals(6, map.size());

        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[
                LinearProbingHashMap.INITIAL_CAPACITY];
        expected[1] = new LinearProbingMapEntry<>(1, "A");
        expected[2] = new LinearProbingMapEntry<>(2, "B");
        expected[3] = new LinearProbingMapEntry<>(3, "C");
        expected[4] = new LinearProbingMapEntry<>(16, "G");
        expected[5] = new LinearProbingMapEntry<>(5, "E");
        expected[6] = new LinearProbingMapEntry<>(6, "F");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveAndPutSameKeyHash() {
        String temp = "D";

        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, temp));
        assertNull(map.put(5, "E"));

        // [_, (1, A), (2, B), (3, C), (4, D)X, (5, E), _, _, _, _, _, _, _]
        assertSame(temp, map.remove(4));
        assertEquals(4, map.size());

        // [_, (1, A), (2, B), (3, C), (4, F), (5, E), _, _, _, _, _, _, _]
        temp = "F";
        assertNull(map.put(4, temp));
        assertEquals(5, map.size());
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[
                LinearProbingHashMap.INITIAL_CAPACITY];
        expected[1] = new LinearProbingMapEntry<>(1, "A");
        expected[2] = new LinearProbingMapEntry<>(2, "B");
        expected[3] = new LinearProbingMapEntry<>(3, "C");
        expected[4] = new LinearProbingMapEntry<>(4, "F");
        expected[5] = new LinearProbingMapEntry<>(5, "E");
        assertArrayEquals(expected, map.getTable());

        // [_, (1, A), (2, B), (3, C), (4, F)X, (5, E), _, _, _, _, _, _, _]
        assertSame(temp, map.remove(4));
        assertEquals(4, map.size());

        // [_, (1, A), (2, B), (3, C)X, (4, F)X, (5, E), _, _, _, _, _, _, _]
        assertSame("C", map.remove(3));
        expected[3].setRemoved(true);
        expected[4].setRemoved(true);
        assertEquals(3, map.size());
        assertArrayEquals(expected, map.getTable());

        // [_, (1, A), (2, B), (16, G), (4, F)X, (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(16, "G"));
        expected[3] = new LinearProbingMapEntry<>(16, "G");
        assertEquals(4, map.size());
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveDELEntry() {
        String temp = "B";

        assertNull(map.put(1, "A"));
        assertSame("A", map.put(1, temp));
        assertNull(map.put(14, "C"));
        assertSame(temp, map.remove(1));
        assertEquals(1, map.size());

        // [_, (1, B)X, (14, C), _, _, _, _, _, _, _, _, _, _]
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[
                LinearProbingHashMap.INITIAL_CAPACITY];
        expected[1] = new LinearProbingMapEntry<>(1, "B");
        expected[2] = new LinearProbingMapEntry<>(14, "C");
        expected[1].setRemoved(true);
        assertArrayEquals(expected, map.getTable());

        map.remove(1);
    }

    @Test(timeout = TIMEOUT)
    public void testGet3() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        assertEquals("A", map.get(1));
        assertEquals("B", map.get(2));
        assertEquals("C", map.get(3));
        assertEquals("D", map.get(4));
        assertEquals("E", map.get(5));
    }

    @Test(timeout = TIMEOUT)
    public void testContainsKey3() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        // [_, (1, A)X, (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertSame("A", map.remove(1));

        assertFalse(map.containsKey(1));
        assertTrue(map.containsKey(3));
        assertFalse(map.containsKey(6));
    }

    @Test(timeout = TIMEOUT)
    public void testKeySet3() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        Set<Integer> expected = new HashSet<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        expected.add(4);
        expected.add(5);
        assertEquals(expected, map.keySet());
    }

    @Test(timeout = TIMEOUT)
    public void testValues3() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        List<String> expected = new LinkedList<>();
        expected.add("A");
        expected.add("B");
        expected.add("C");
        expected.add("D");
        expected.add("E");
        assertEquals(expected, map.values());
    }

    @Test(timeout = TIMEOUT)
    public void testResize3() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        // [_, (1, A), (2, B), (3, C), (4, D), (5, E)]
        map.resizeBackingTable(6);
        assertEquals(5, map.size());
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[6];
        expected[1] = new LinearProbingMapEntry<>(1, "A");
        expected[2] = new LinearProbingMapEntry<>(2, "B");
        expected[3] = new LinearProbingMapEntry<>(3, "C");
        expected[4] = new LinearProbingMapEntry<>(4, "D");
        expected[5] = new LinearProbingMapEntry<>(5, "E");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testClear3() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        map.clear();
        assertEquals(0, map.size());
        assertArrayEquals(new LinearProbingMapEntry[
                LinearProbingHashMap.INITIAL_CAPACITY], map.getTable());
    }

    @Test (timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testPutKeyIllegal() {
        map.put(null, "A");
    }

    @Test (timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testPutValueIllegal() {
        map.put(1, null);
    }

    @Test (timeout = TIMEOUT)
    public void testPutReplace() {
        // [_, (1, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertEquals(map.put(1, "B"), "A");
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));

        assertEquals(4, map.size());
        LinearProbingMapEntry[] expected =
                new LinearProbingMapEntry[
                        LinearProbingHashMap.INITIAL_CAPACITY];
        expected[1] = new LinearProbingMapEntry<>(1, "B");
        expected[2] = new LinearProbingMapEntry<>(2, "C");
        expected[3] = new LinearProbingMapEntry<>(3, "D");
        expected[4] = new LinearProbingMapEntry<>(4, "E");
        assertArrayEquals(expected, map.getTable());
    }

    @Test (timeout = TIMEOUT)
    public void testPutDel() {
        String temp = "C";

        // [_, (1, A), (2, B), (3, D), (4, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, temp));
        map.remove(3);
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));

        assertEquals(4, map.size());
        LinearProbingMapEntry[] expected =
                new LinearProbingMapEntry[
                        LinearProbingHashMap.INITIAL_CAPACITY];
        expected[1] = new LinearProbingMapEntry<>(1, "A");
        expected[2] = new LinearProbingMapEntry<>(2, "B");
        expected[3] = new LinearProbingMapEntry<>(3, "D");
        expected[4] = new LinearProbingMapEntry<>(4, "E");
        assertArrayEquals(expected, map.getTable());
    }

    @Test (timeout = TIMEOUT)
    public void testSimpleProbe() {
        // [_, _, _, (01, 1), (02, 2), (03, 3), (04, 4), (Aa, 5), (06, 6),(A04, 7)_, _, _]

        assertNull(map1.put("01", 1));
        assertNull(map1.put("02", 2));
        assertNull(map1.put("03", 3));
        assertNull(map1.put("04", 4));
        assertNull(map1.put("Aa", 5));  // Aa hashed is 2112 - goes to index 6
        assertNull(map1.put("06", 6));
        assertNull(map1.put("A04", 7)); // A04 hashed is 64005 - goes to index 6

        assertEquals(7, map1.size());
        LinearProbingMapEntry[] expected =
                new LinearProbingMapEntry[
                        LinearProbingHashMap.INITIAL_CAPACITY];
        expected[3] = new LinearProbingMapEntry<>("01", 1);
        expected[4] = new LinearProbingMapEntry<>("02", 2);
        expected[5] = new LinearProbingMapEntry<>("03", 3);
        expected[6] = new LinearProbingMapEntry<>("04", 4);
        expected[7] = new LinearProbingMapEntry<>("Aa", 5);
        expected[8] = new LinearProbingMapEntry<>("06", 6);
        expected[9] = new LinearProbingMapEntry<>("A04", 7);
        assertArrayEquals(expected, map1.getTable());
    }

    @Test (timeout = TIMEOUT)
    public void testDeletedProbe() {
        // [_, _, _, (01, 1), (02, 2), (03, 3), (04, 4), (05, 5), (A04, 7)_, _, _]

        assertNull(map1.put("01", 1));
        assertNull(map1.put("02", 2));
        assertNull(map1.put("03", 3));
        assertNull(map1.put("04", 4));
        assertNull(map1.put("05", 5));
        assertNull(map1.put("06", 6));
        map1.remove("06");
        assertNull(map1.put("A04", 7)); // A04 hashed is 64005 - goes to index 6

        assertEquals(6, map1.size());
        LinearProbingMapEntry[] expected =
                new LinearProbingMapEntry[
                        LinearProbingHashMap.INITIAL_CAPACITY];
        expected[3] = new LinearProbingMapEntry<>("01", 1);
        expected[4] = new LinearProbingMapEntry<>("02", 2);
        expected[5] = new LinearProbingMapEntry<>("03", 3);
        expected[6] = new LinearProbingMapEntry<>("04", 4);
        expected[7] = new LinearProbingMapEntry<>("05", 5);
        expected[8] = new LinearProbingMapEntry<>("A04", 7);
        assertArrayEquals(expected, map1.getTable());
    }

    @Test (timeout = TIMEOUT)
    public void testNullBeforeDeletedProbe() {
        int temp = 6;
        // [_, _, _, (01, 1), (02, 2), (03, 3), (A03, 4), (05, 5), X(06, 6), (07, 7) _, _]

        assertNull(map1.put("01", 1));
        assertNull(map1.put("02", 2));
        assertNull(map1.put("03", 3));
        assertNull(map1.put("05", 5));
        assertNull(map1.put("06", 6));
        assertEquals(java.util.Optional.of(temp), java.util.Optional.of(map1.remove("06")));
        assertNull(map1.put("07", 7));
        assertNull(map1.put("A03", 4)); // A03 hashed is 64004 - goes to index 5

        assertEquals(6, map1.size());
        LinearProbingMapEntry[] expected =
                new LinearProbingMapEntry[
                        LinearProbingHashMap.INITIAL_CAPACITY];
        expected[3] = new LinearProbingMapEntry<>("01", 1);
        expected[4] = new LinearProbingMapEntry<>("02", 2);
        expected[5] = new LinearProbingMapEntry<>("03", 3);
        expected[6] = new LinearProbingMapEntry<>("A03", 4);
        expected[7] = new LinearProbingMapEntry<>("05", 5);
        LinearProbingMapEntry entry = new LinearProbingMapEntry<>("06", 6);
        entry.setRemoved(true);
        expected[8] = entry;
        expected[9] = new LinearProbingMapEntry<>("07", 7);
        assertArrayEquals(expected, map1.getTable());
    }

    @Test (timeout = TIMEOUT)
    public void testNullAfterDeletedProbe() {
        int temp = 4;
        // [_, _, _, (01, 1), (02, 2), (03, 3), (A03, 4), _, _, _, _, _. _]

        assertNull(map1.put("01", 1));
        assertNull(map1.put("02", 2));
        assertNull(map1.put("03", 3));
        assertNull(map1.put("04", 4));
        assertEquals(java.util.Optional.of(temp), java.util.Optional.of(map1.remove("04")));
        assertNull(map1.put("A03", 4)); // A03 hashed is 64004 - goes to index 5

        assertEquals(4, map1.size());
        LinearProbingMapEntry[] expected =
                new LinearProbingMapEntry[
                        LinearProbingHashMap.INITIAL_CAPACITY];
        expected[3] = new LinearProbingMapEntry<>("01", 1);
        expected[4] = new LinearProbingMapEntry<>("02", 2);
        expected[5] = new LinearProbingMapEntry<>("03", 3);
        expected[6] = new LinearProbingMapEntry<>("A03", 4);
        assertArrayEquals(expected, map1.getTable());
    }

    @Test (timeout = TIMEOUT)
    public void testProbeAddFirstDeletedNotFound() {
        int temp4 = 4, temp5 = 5, temp6 = 6;
        // [_, _, _, (01, 1), (02, 2), (03, 3), (A03, 4), X(05, 5), X(06, 6), (07, 7) _, _]

        assertNull(map1.put("01", 1));
        assertNull(map1.put("02", 2));
        assertNull(map1.put("03", 3));
        assertNull(map1.put("04", 4));
        assertEquals(java.util.Optional.of(temp4), java.util.Optional.of(map1.remove("04")));
        assertNull(map1.put("05", 5));
        assertEquals(java.util.Optional.of(temp5), java.util.Optional.of(map1.remove("05")));
        assertNull(map1.put("06", 6));
        assertEquals(java.util.Optional.of(temp6), java.util.Optional.of(map1.remove("06")));
        assertNull(map1.put("07", 7));
        assertNull(map1.put("A03", 4)); // A03 hashed is 64004 - goes to index 5

        assertEquals(5, map1.size());
        LinearProbingMapEntry[] expected =
                new LinearProbingMapEntry[
                        LinearProbingHashMap.INITIAL_CAPACITY];
        expected[3] = new LinearProbingMapEntry<>("01", 1);
        expected[4] = new LinearProbingMapEntry<>("02", 2);
        expected[5] = new LinearProbingMapEntry<>("03", 3);
        expected[6] = new LinearProbingMapEntry<>("A03", 4);
        LinearProbingMapEntry entry5 = new LinearProbingMapEntry<>("05", 5);
        entry5.setRemoved(true);
        expected[7] = entry5;
        LinearProbingMapEntry entry6 = new LinearProbingMapEntry<>("06", 6);
        entry6.setRemoved(true);
        expected[8] = entry6;
        expected[9] = new LinearProbingMapEntry<>("07", 7);
        assertArrayEquals(expected, map1.getTable());
    }

    @Test (timeout = TIMEOUT)
    public void testProbeAddFirstDeletedFound() {
        int temp4 = 4, temp5 = 5, temp6 = 6;
        // [_, _, _, (01, 1), (02, 2), (03, 3), (A03, 4), X(05, 5), X(A03, 6), (07, 7) _, _]

        assertNull(map1.put("01", 1));
        assertNull(map1.put("02", 2));
        assertNull(map1.put("03", 3));
        assertNull(map1.put("04", 4));
        assertNull(map1.put("05", 5));
        assertNull(map1.put("A03", 6)); // A03 hashed is 64004 - goes to index 5
        assertNull(map1.put("07", 7));
        assertEquals(java.util.Optional.of(temp4), java.util.Optional.of(map1.remove("04")));
        assertEquals(java.util.Optional.of(temp5), java.util.Optional.of(map1.remove("05")));
        assertEquals(java.util.Optional.of(temp6), java.util.Optional.of(map1.remove("A03")));
        assertNull(map1.put("A03", 4)); // A03 hashed is 64004

        assertEquals(5, map1.size());
        LinearProbingMapEntry[] expected =
                new LinearProbingMapEntry[
                        LinearProbingHashMap.INITIAL_CAPACITY];
        expected[3] = new LinearProbingMapEntry<>("01", 1);
        expected[4] = new LinearProbingMapEntry<>("02", 2);
        expected[5] = new LinearProbingMapEntry<>("03", 3);
        expected[6] = new LinearProbingMapEntry<>("A03", 4);
        LinearProbingMapEntry entry5 = new LinearProbingMapEntry<>("05", 5);
        entry5.setRemoved(true);
        expected[7] = entry5;
        LinearProbingMapEntry entry6 = new LinearProbingMapEntry<>("A03", 6);
        entry6.setRemoved(true);
        expected[8] = entry6;
        expected[9] = new LinearProbingMapEntry<>("07", 7);
        assertArrayEquals(expected, map1.getTable());
    }

    @Test (timeout = TIMEOUT)
    public void testProbeReplacePastDeleted() {
        int temp4 = 4, temp5 = 5, temp6 = 6;
        // [_, _, _, (01, 1), (02, 2), (03, 3), X(04, 4), X(05, 5), X(06, 6), (Aa, 4), _, _]

        assertNull(map1.put("01", 1));
        assertNull(map1.put("02", 2));
        assertNull(map1.put("03", 3));
        assertNull(map1.put("04", 4));
        assertNull(map1.put("05", 5));
        assertNull(map1.put("06", 6));
        assertNull(map1.put("Aa", 7));
        assertEquals(java.util.Optional.of(temp4), java.util.Optional.of(map1.remove("04")));
        assertEquals(java.util.Optional.of(temp5), java.util.Optional.of(map1.remove("05")));
        assertEquals(java.util.Optional.of(temp6), java.util.Optional.of(map1.remove("06")));
        assertEquals(java.util.Optional.of(7), java.util.Optional.of(map1.put("Aa", 4)));

        assertEquals(4, map1.size());
        LinearProbingMapEntry[] expected =
                new LinearProbingMapEntry[
                        LinearProbingHashMap.INITIAL_CAPACITY];
        expected[3] = new LinearProbingMapEntry<>("01", 1);
        expected[4] = new LinearProbingMapEntry<>("02", 2);
        expected[5] = new LinearProbingMapEntry<>("03", 3);
        LinearProbingMapEntry entry4 = new LinearProbingMapEntry<>("04", 4);
        entry4.setRemoved(true);
        expected[6] = entry4;
        LinearProbingMapEntry entry5 = new LinearProbingMapEntry<>("05", 5);
        entry5.setRemoved(true);
        expected[7] = entry5;
        LinearProbingMapEntry entry6 = new LinearProbingMapEntry<>("06", 6);
        entry6.setRemoved(true);
        expected[8] = entry6;
        expected[9] = new LinearProbingMapEntry<>("Aa", 4);
        assertArrayEquals(expected, map1.getTable());
    }

    @Test (timeout = TIMEOUT)
    public void testRemoveLast() {
        assertNull(map.put(1, "A"));
        assertEquals("A", map.remove(1));

        assertEquals(0, map.size());
        LinearProbingMapEntry[] expected =
                new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];
        LinearProbingMapEntry entry = new LinearProbingMapEntry<>(1, "A");
        entry.setRemoved(true);
        expected[1] = entry;
        assertArrayEquals(expected, map.getTable());
    }

    @Test (timeout = TIMEOUT)
    public void testRemoveFirstandLast() {
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        assertEquals(5, map.size());
        assertEquals(map.remove(5), "E");
        assertEquals(4, map.size());
        assertEquals(map.remove(1), "A");
        assertEquals(3, map.size());
    }

    @Test (timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveRemoved() {
        assertNull(map.put(1, "A"));
        assertEquals("A", map.remove(1));
        map.remove(1);
    }

    @Test (timeout = TIMEOUT)
    public void testGetEverything() {
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        assertEquals(5, map.size());
        assertEquals(map.get(1), "A");
        assertEquals(map.get(2), "B");
        assertEquals(map.get(3), "C");
        assertEquals(map.get(4), "D");
        assertEquals(map.get(5), "E");
    }

    @Test (timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testGetNotFound() {
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        assertEquals(5, map.size());
        map.get(6);
    }

    @Test (timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testGetRemoved() {
        assertNull(map.put(1, "A"));
        assertEquals("A", map.remove(1));
        map.get(1);
    }

    @Test (timeout = TIMEOUT)
    public void testContains() {
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        assertEquals(5, map.size());
        assertTrue(map.containsKey(1));
        assertTrue(map.containsKey(3));
        assertTrue(map.containsKey(4));
    }

    @Test (timeout = TIMEOUT)
    public void testContainsNotFound() {
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        assertEquals(5, map.size());
        assertFalse(map.containsKey(6));
    }

    @Test (timeout = TIMEOUT)
    public void testContainsRemoved() {
        assertNull(map.put(1, "A"));
        assertEquals("A", map.remove(1));
        assertFalse(map.containsKey(6));
    }

    @Test (timeout = TIMEOUT)
    public void testResizeThenRemove() {
        // [_, _, _, (01, 1), (02, 2), (03, 3), (04, 4), (05, 5), (06, 6), (Aa, 7), (08, 8), (09, 9)]

        assertNull(map1.put("01", 1));
        assertNull(map1.put("02", 2));
        assertNull(map1.put("03", 3));
        assertNull(map1.put("04", 4));
        assertNull(map1.put("05", 5));
        assertNull(map1.put("06", 6));
        assertNull(map1.put("Aa", 7)); // Aa hashed is 2112 - goes to index 6
        assertNull(map1.put("08", 8));
        assertNull(map1.put("09", 9));
        map1.remove("03");
        map1.remove("09");

        assertEquals(7, map1.size());
        LinearProbingMapEntry[] expected =
                new LinearProbingMapEntry[
                        LinearProbingHashMap.INITIAL_CAPACITY * 2 + 1];
        expected[25] = new LinearProbingMapEntry<>("01", 1);
        expected[26] = new LinearProbingMapEntry<>("02", 2);
        LinearProbingMapEntry entry3 = new LinearProbingMapEntry<>("03", 3);
        entry3.setRemoved(true);
        expected[0] = entry3;
        expected[1] = new LinearProbingMapEntry<>("04", 4);
        expected[2] = new LinearProbingMapEntry<>("05", 5);
        expected[3] = new LinearProbingMapEntry<>("06", 6);
        expected[6] = new LinearProbingMapEntry<>("Aa", 7);
        expected[5] = new LinearProbingMapEntry<>("08", 8);
        LinearProbingMapEntry entry9 = new LinearProbingMapEntry<>("09", 9);
        entry9.setRemoved(true);
        expected[7] = entry9;
        assertArrayEquals(expected, map1.getTable());
    }

    @Test (timeout = TIMEOUT)
    public void testResizewithDels() {
        // [_, _, _, (01, 1), (02, 2), X(03, 3), (04, 4), (05, 5), (06, 6), (Aa, 7), (08, 8), X(09, 9)]

        assertNull(map1.put("01", 1));
        assertNull(map1.put("02", 2));
        assertNull(map1.put("03", 3));
        map1.remove("03");
        assertNull(map1.put("04", 4));
        assertNull(map1.put("05", 5));
        assertNull(map1.put("06", 6));
        assertNull(map1.put("Aa", 7)); // Aa hashed is 2112 - goes to index 6
        assertNull(map1.put("08", 8));
        assertNull(map1.put("09", 9));
        map1.remove("09");

        map1.resizeBackingTable(LinearProbingHashMap.INITIAL_CAPACITY * 2 + 1);

        assertEquals(7, map1.size());
        LinearProbingMapEntry[] expected =
                new LinearProbingMapEntry[
                        LinearProbingHashMap.INITIAL_CAPACITY * 2 + 1];
        expected[25] = new LinearProbingMapEntry<>("01", 1);
        expected[26] = new LinearProbingMapEntry<>("02", 2);
        expected[1] = new LinearProbingMapEntry<>("04", 4);
        expected[2] = new LinearProbingMapEntry<>("05", 5);
        expected[3] = new LinearProbingMapEntry<>("06", 6);
        expected[6] = new LinearProbingMapEntry<>("Aa", 7);
        expected[5] = new LinearProbingMapEntry<>("08", 8);
        assertArrayEquals(expected, map1.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testInitializationWithNoCapacity() {
        map = new LinearProbingHashMap<>(0);
        map.put(1, "A");
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[1];
        expected[0] = new LinearProbingMapEntry<>(1, "A");
        assertArrayEquals(map.getTable(), expected);

        map.put(2, "B");
        expected = new LinearProbingMapEntry[3];
        expected[1] = new LinearProbingMapEntry<>(1, "A");
        expected[2] = new LinearProbingMapEntry<>(2, "B");
    }

    @Test(timeout = TIMEOUT)
    public void testGeneralHashing() {
        //[(asdfjkl;, Value 5), null, null, null, null, null, null, null, null, (Key1, Value1), (Key2, Value2), (Key3, Value3), (qweruiop, Value 6)]
        LinearProbingHashMap<String, String> hashmap = new LinearProbingHashMap<>();
        hashmap.put("Key1", "Value1");
        hashmap.put("Key2", "Value2");
        hashmap.put("Key3", "Value3");
        hashmap.put("asdfjkl;", "Value4");
        assertEquals("Value4", hashmap.put("asdfjkl;", "Value5"));
        hashmap.put("qweruiop", "Value6");

        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];
        expected[0] = new LinearProbingMapEntry<>("asdfjkl;", "Value5");
        expected[9] = new LinearProbingMapEntry<>("Key1", "Value1");
        expected[10] = new LinearProbingMapEntry<>("Key2", "Value2");
        expected[11] = new LinearProbingMapEntry<>("Key3", "Value3");
        expected[12] = new LinearProbingMapEntry<>("qweruiop", "Value6");
        assertArrayEquals(expected, hashmap.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testPut4() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        assertEquals(5, map.size());
        LinearProbingMapEntry[] expected =
                new LinearProbingMapEntry[
                        LinearProbingHashMap.INITIAL_CAPACITY];
        expected[1] = new LinearProbingMapEntry<>(1, "A");
        expected[2] = new LinearProbingMapEntry<>(2, "B");
        expected[3] = new LinearProbingMapEntry<>(3, "C");
        expected[4] = new LinearProbingMapEntry<>(4, "D");
        expected[5] = new LinearProbingMapEntry<>(5, "E");
        assertArrayEquals(expected, map.getTable());
    }


    @Test(timeout = TIMEOUT)
    public void testHashedPut() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(14, "A"));
        assertNull(map.put(27, "B"));
        assertNull(map.put(40, "C"));
        assertNull(map.put(53, "D"));
        assertNull(map.put(66, "E"));

        assertEquals(5, map.size());
        LinearProbingMapEntry[] expected =
                new LinearProbingMapEntry[
                        LinearProbingHashMap.INITIAL_CAPACITY];
        expected[1] = new LinearProbingMapEntry<>(14, "A");
        expected[2] = new LinearProbingMapEntry<>(27, "B");
        expected[3] = new LinearProbingMapEntry<>(40, "C");
        expected[4] = new LinearProbingMapEntry<>(53, "D");
        expected[5] = new LinearProbingMapEntry<>(66, "E");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testNegativePut() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(-1, "A"));
        assertNull(map.put(-2, "B"));
        assertNull(map.put(-3, "C"));
        assertNull(map.put(-4, "D"));
        assertNull(map.put(-5, "E"));

        assertEquals(5, map.size());
        LinearProbingMapEntry[] expected =
                new LinearProbingMapEntry[
                        LinearProbingHashMap.INITIAL_CAPACITY];
        expected[1] = new LinearProbingMapEntry<>(-1, "A");
        expected[2] = new LinearProbingMapEntry<>(-2, "B");
        expected[3] = new LinearProbingMapEntry<>(-3, "C");
        expected[4] = new LinearProbingMapEntry<>(-4, "D");
        expected[5] = new LinearProbingMapEntry<>(-5, "E");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testNegativeProbingHashedPut() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(-14, "A"));
        assertNull(map.put(-27, "B"));
        assertNull(map.put(-40, "C"));
        assertNull(map.put(-53, "D"));
        assertNull(map.put(-66, "E"));

        assertEquals(5, map.size());
        LinearProbingMapEntry[] expected =
                new LinearProbingMapEntry[
                        LinearProbingHashMap.INITIAL_CAPACITY];
        expected[1] = new LinearProbingMapEntry<>(-14, "A");
        expected[2] = new LinearProbingMapEntry<>(-27, "B");
        expected[3] = new LinearProbingMapEntry<>(-40, "C");
        expected[4] = new LinearProbingMapEntry<>(-53, "D");
        expected[5] = new LinearProbingMapEntry<>(-66, "E");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveKeyPutSameKey() {
        // [_, (1, A), (2, B), (3, C), _, _, _, _, _, _, _, _, _]
        String temp = "B";
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, temp));
        assertNull(map.put(3, "C"));

        // [_, (1, A), _, (3, C), _, _, _, _, _, _, _, _, _]
        assertEquals(temp, map.remove(2));
        assertEquals(2, map.size());
        LinearProbingMapEntry[] expected =
                new LinearProbingMapEntry[
                        LinearProbingHashMap.INITIAL_CAPACITY];
        expected[1] = new LinearProbingMapEntry<>(1, "A");
        expected[2] = new LinearProbingMapEntry<>(2, temp);
        expected[2].setRemoved(true);
        expected[3] = new LinearProbingMapEntry<>(3, "C");
        assertArrayEquals(expected, map.getTable());

        // [_, (1, A), (2, B), (3, C), _, _, _, _, _, _, _, _, _]
        assertNull(map.put(2, temp));
        expected[2].setRemoved(false);
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveKeyPutDifferentKey() {
        // [_, (1, A), (2, B), (3, C), _, _, _, _, _, _, _, _, _]
        String B = "B";
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, B));
        assertNull(map.put(3, "C"));

        // [_, (1, A), _, (3, C), _, _, _, _, _, _, _, _, _]
        assertEquals(B, map.remove(2));
        assertEquals(2, map.size());

        // [_, (1, A), (14, test), (3, C), _, _, _, _, _, _, _, _, _]
        LinearProbingMapEntry[] expected =
                new LinearProbingMapEntry[
                        LinearProbingHashMap.INITIAL_CAPACITY];
        String test = "test";
        expected[1] = new LinearProbingMapEntry<>(1, "A");
        expected[2] = new LinearProbingMapEntry<>(14, test);
        expected[2].setRemoved(true);
        expected[3] = new LinearProbingMapEntry<>(3, "C");

        assertNull(map.put(14, test));
        expected[2].setRemoved(false);
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testRemove4() {
        String temp = "D";

        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, temp));
        assertNull(map.put(5, "E"));

        // [_, (1, A), (2, B), (3, C), (4, D)X, (5, E), _, _, _, _, _, _, _]
        assertSame(temp, map.remove(4));
        assertEquals(4, map.size());
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[
                LinearProbingHashMap.INITIAL_CAPACITY];
        expected[1] = new LinearProbingMapEntry<>(1, "A");
        expected[2] = new LinearProbingMapEntry<>(2, "B");
        expected[3] = new LinearProbingMapEntry<>(3, "C");
        expected[4] = new LinearProbingMapEntry<>(4, "D");
        expected[4].setRemoved(true);
        expected[5] = new LinearProbingMapEntry<>(5, "E");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testNoSuchElementException() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        // [_, (1, A), (2, B), (3, C), _, (5, E), _, _, _, _, _, _, _]
        map.remove(4);
        try {
            map.get(4);
            fail();
        } catch (NoSuchElementException e) {
        }
        try {
            map.remove(4);
            fail();
        } catch (NoSuchElementException e) {
        }
    }

    @Test(timeout = TIMEOUT)
    public void testNoSuchElementExceptionWithRemovedKeys() {
        // [_, _, _, (3, C), (4, D), (5, E), (6, F), _, _, _, _, _, _, _]
        map.put(3, "C");
        map.put(4, "D");
        map.put(5, "E");
        map.put(6, "F");
        // [_, _, _, _, _, (5, E), (6, F), _, _, _, _, _, _, _]
        map.remove(3);
        map.remove(4);
        try {
            map.get(4);
            fail();
        } catch (NoSuchElementException e) {
        }
        try {
            map.remove(4);
            fail();
        } catch (NoSuchElementException e) {
        }
        map.remove(6);
        map.remove(5);
    }

    @Test(timeout = TIMEOUT)
    public void testIllegalArgumentException() {
        // [_, _, _, (3, C), (4, D), (5, E), (6, F), _, _, _, _, _, _, _]
        map.put(3, "C");
        map.put(4, "D");
        map.put(5, "E");
        map.put(6, "F");
        try {
            map.put(null, "test");
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            map.put(1, null);
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            map.remove(null);
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            map.get(null);
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            map.containsKey(null);
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            map.resizeBackingTable(3);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test(timeout = TIMEOUT)
    public void testResizeIllegalArgumentExceptionWithRemovedKey() {
        // [_, _, _, (3, C), (4, D), (5, E), (6, F), _, _, _, _, _, _, _]
        map.put(3, "C");
        map.put(4, "D");
        map.put(5, "E");
        map.put(6, "F");

        map.remove(3);

        try {
            map.resizeBackingTable(2);
            fail();
        } catch (IllegalArgumentException e) {
        }
        map.resizeBackingTable(3);
    }


    @Test(timeout = TIMEOUT)
    public void testGet4() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        assertEquals("A", map.get(1));
        assertEquals("B", map.get(2));
        assertEquals("C", map.get(3));
        assertEquals("D", map.get(4));
        assertEquals("E", map.get(5));
    }

    @Test(timeout = TIMEOUT)
    public void testContainsKey4() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        assertTrue(map.containsKey(3));
        assertFalse(map.containsKey(6));
    }

    @Test(timeout = TIMEOUT)
    public void testKeySet4() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        Set<Integer> expected = new HashSet<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        expected.add(4);
        expected.add(5);
        assertEquals(expected, map.keySet());
    }

    @Test(timeout = TIMEOUT)
    public void testValues4() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        List<String> expected = new LinkedList<>();
        expected.add("A");
        expected.add("B");
        expected.add("C");
        expected.add("D");
        expected.add("E");
        assertEquals(expected, map.values());
    }

    @Test(timeout = TIMEOUT)
    public void testResize4() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        // [_, (1, A), (2, B), (3, C), (4, D), (5, E)]
        map.resizeBackingTable(6);
        assertEquals(5, map.size());
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[6];
        expected[1] = new LinearProbingMapEntry<>(1, "A");
        expected[2] = new LinearProbingMapEntry<>(2, "B");
        expected[3] = new LinearProbingMapEntry<>(3, "C");
        expected[4] = new LinearProbingMapEntry<>(4, "D");
        expected[5] = new LinearProbingMapEntry<>(5, "E");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testResizeWithRemovedKey() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        assertEquals(map.remove(2), "B");
        assertEquals(map.remove(3), "C");
        assertEquals(map.remove(4), "D");
        assertEquals(map.remove(5), "E");

        // [_, (1, A), _, _, _, _]
        map.resizeBackingTable(6);
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[6];
        expected[1] = new LinearProbingMapEntry<>(1, "A");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testClear4() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        map.clear();
        assertEquals(0, map.size());
        assertArrayEquals(new LinearProbingMapEntry[
                LinearProbingHashMap.INITIAL_CAPACITY], map.getTable());
    }

    @Test
    public void duplicateNoSizeIncrease() {
        assertNull(map.put(0, "First"));
        assertEquals("First", map.put(0, "Second"));
        assertEquals(1, map.size());
        assertEquals("Second", map.getTable()[0].getValue());
    }

    @Test
    public void wrapAround() {
        assertNull(map.put(12, "First"));
        assertNull(map.put(25, "Second"));
        assertEquals(2, map.size());
        assertEquals("First", map.getTable()[12].getValue());
        assertEquals("Second", map.getTable()[0].getValue());
    }

    @Test
    public void removeAndReplace() {
        assertNull(map.put(0, "First"));
        map.remove(0);
        assertNull(map.put(0, "First"));
        assertEquals(1, map.size());
        assertEquals("First", map.getTable()[0].getValue());
    }

    @Test
    public void returnToRemoved() {
        assertNull(map.put(0, "First"));
        assertNull(map.put(13, "Second"));
        assertNull(map.put(26, "Third"));
        assertEquals("Second", map.remove(13));
        assertNull(map.put(39, "Fourth"));
        assertEquals(3, map.size());
        assertEquals("First", map.getTable()[0].getValue());
        assertEquals("Fourth", map.getTable()[1].getValue());
        assertEquals("Third", map.getTable()[2].getValue());
    }

    @Test
    public void skipRemovedToReplaceDuplicate() {
        assertNull(map.put(0, "First"));
        assertNull(map.put(13, "Second"));
        assertNull(map.put(26, "Third"));
        assertEquals("Second", map.remove(13));
        assertEquals("Third", map.put(26, "Fourth"));
        assertEquals(2, map.size());

        LinearProbingMapEntry<Integer, String>[] entries =
                (LinearProbingMapEntry<Integer, String>[]) new LinearProbingMapEntry[13];

        entries[0] = new LinearProbingMapEntry<>(0, "First");
        entries[1] = new LinearProbingMapEntry<>(13, "Second");
        entries[1].setRemoved(true);
        entries[2] = new LinearProbingMapEntry<>(26, "Fourth");
        assertArrayEquals(entries, map.getTable());
    }

    @Test
    public void negativeHash() {
        assertNull(map.put(-1, "First"));
        assertEquals(1, map.size());
        assertEquals("First", map.getTable()[1].getValue());
    }

    @Test
    public void ignoreLoadFactorDuringForcedResize() {
        map.put(0, "First");
        map.put(1, "Second");
        map.put(2, "Third");
        map.resizeBackingTable(3);

        assertEquals(3, map.size());

        LinearProbingMapEntry<Integer, String>[] entries =
                (LinearProbingMapEntry<Integer, String>[]) new LinearProbingMapEntry[3];

        entries[0] = new LinearProbingMapEntry<>(0, "First");
        entries[1] = new LinearProbingMapEntry<>(1, "Second");
        entries[2] = new LinearProbingMapEntry<>(2, "Third");
        assertArrayEquals(entries, map.getTable());
    }

    @Test
    public void resizeWithCollisions() {
        map.put(0, "First");
        map.put(13, "Second");
        map.put(26, "Third");
        map.resizeBackingTable(3);

        assertEquals(3, map.size());

        LinearProbingMapEntry<Integer, String>[] entries =
                (LinearProbingMapEntry<Integer, String>[]) new LinearProbingMapEntry[3];

        entries[0] = new LinearProbingMapEntry<>(0, "First");
        entries[1] = new LinearProbingMapEntry<>(13, "Second");
        entries[2] = new LinearProbingMapEntry<>(26, "Third");
        assertArrayEquals(entries, map.getTable());
    }

    @Test
    public void keySetWithDeletions() {
        map.put(0, "First");
        map.put(13, "Second");
        map.put(26, "Third");
        map.remove(13);
        assertEquals(2, map.size());

        Set<Integer> expected = new HashSet<>(2);
        expected.add(0);
        expected.add(26);

        assertEquals(expected, map.keySet());
    }

    @Test
    public void valuesWithDeletions() {
        assertNull(map.put(0, "First"));
        assertNull(map.put(13, "Second"));
        assertNull(map.put(26, "Third"));
        assertEquals("Second", map.remove(13));
        assertEquals(2, map.size());

        List<String> expected = new ArrayList<>(2);
        expected.add("First");
        expected.add("Third");

        assertEquals(expected, map.values());
    }

    @Test
    public void resizeWithPut() {
        assertNull(map.put(0,  "0"));
        assertNull(map.put(1,  "1"));
        assertNull(map.put(2,  "2"));
        assertNull(map.put(3,  "3"));
        assertNull(map.put(4,  "4"));
        assertNull(map.put(5,  "5"));
        assertNull(map.put(6,  "6"));
        assertNull(map.put(7,  "7"));
        assertEquals(13, map.getTable().length);
        assertNull(map.put(8,  "8"));
        assertEquals(27, map.getTable().length);
    }

    @Test
    public void stopAtDeletedMatchingKey() {
        assertNull(map.put(0, "First"));
        assertNull(map.put(13, "Second"));
        assertNull(map.put(26, "Third"));

        assertEquals("Second", map.remove(13));
        assertEquals("Third", map.remove(26));

        assertNull(map.put(13, "Fourth"));

        assertEquals("Fourth", map.getTable()[1].getValue());
    }

    @Test
    public void insertAtDeletedBeforeMatchingKey() {
        assertNull(map.put(0, "First"));
        assertNull(map.put(13, "Second"));
        assertNull(map.put(26, "Third"));

        assertEquals("Second", map.remove(13));
        assertEquals("Third", map.remove(26));

        assertNull(map.put(26, "Fourth"));

        assertEquals("Fourth", map.getTable()[1].getValue());
    }

    //Constructor Tests
    @Test(timeout = TIMEOUT)
    public void testOtherConstructor() {
        map = new LinearProbingHashMap<>(17);
        assertEquals(0, map.size());
        assertArrayEquals(new LinearProbingMapEntry[17], map.getTable());
    }

    //Put Tests
    @Test(timeout = TIMEOUT)
    public void testNullKey() {
        try {
            map.put(null, "THWg");
            fail();
        } catch (IllegalArgumentException e) {

        }
    }

    @Test(timeout = TIMEOUT)
    public void testNullValue() {
        try {
            map.put(1332, null);
            fail();
        } catch (IllegalArgumentException e) {

        }
    }

    @Test(timeout = TIMEOUT)
    public void testAddALot() {

        assertNull(map.put(1331, "Intro to Object Oriented Programming"));
        assertNull(map.put(1332, "Data Structures and Algorithms"));
        assertNull(map.put(1371, "Programming for Engineers"));
        assertNull(map.put(2340, "Objects and Design"));
        assertNull(map.put(3510, "Design and Analysis of Algorithms"));
        assertNull(map.put(3600, "Introduction to Artificial Intelligence"));
        assertNull(map.put(4510, "Automata and Complexity"));
        assertNull(map.put(4641, "Machine Learning"));

        assertEquals(8, map.size());

        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[13];

        expected[0] = new LinearProbingMapEntry<>(2340, "Objects and Design");
        expected[1] = new LinearProbingMapEntry<>(3510, "Design and Analysis of Algorithms");
        expected[2] = new LinearProbingMapEntry<>(4510, "Automata and Complexity");
        expected[3] = new LinearProbingMapEntry<>(4641, "Machine Learning");
        expected[5] = new LinearProbingMapEntry<>(1331, "Intro to Object Oriented Programming");
        expected[6] = new LinearProbingMapEntry<>(1332, "Data Structures and Algorithms");
        expected[7] = new LinearProbingMapEntry<>(1371, "Programming for Engineers");
        expected[12] = new LinearProbingMapEntry<>(3600, "Introduction to Artificial Intelligence");

        assertArrayEquals(expected, map.getTable());

    }

    @Test(timeout = TIMEOUT)
    public void testAddResize() {

        assertNull(map.put(1331, "Intro to Object Oriented Programming"));
        assertNull(map.put(1332, "Data Structures and Algorithms"));
        assertNull(map.put(1371, "Programming for Engineers"));
        assertNull(map.put(2340, "Objects and Design"));
        assertNull(map.put(3510, "Design and Analysis of Algorithms"));
        assertNull(map.put(3600, "Introduction to Artificial Intelligence"));
        assertNull(map.put(4510, "Automata and Complexity"));
        assertNull(map.put(4641, "Machine Learning"));

        assertNull(map.put(3240, "Languages and Computation"));

        assertEquals(9, map.size());

        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[27];

        expected[18] = new LinearProbingMapEntry<>(2340, "Objects and Design");
        expected[0] = new LinearProbingMapEntry<>(3510, "Design and Analysis of Algorithms");
        expected[1] = new LinearProbingMapEntry<>(4510, "Automata and Complexity");
        expected[24] = new LinearProbingMapEntry<>(4641, "Machine Learning");
        expected[8] = new LinearProbingMapEntry<>(1331, "Intro to Object Oriented Programming");
        expected[9] = new LinearProbingMapEntry<>(1332, "Data Structures and Algorithms");
        expected[21] = new LinearProbingMapEntry<>(1371, "Programming for Engineers");
        expected[10] = new LinearProbingMapEntry<>(3600, "Introduction to Artificial Intelligence");
        expected[2] = new LinearProbingMapEntry<>(3240, "Languages and Computation");

        assertArrayEquals(expected, map.getTable());

    }

    @Test(timeout = TIMEOUT)
    public void testReplace() {

        assertNull(map.put(1331, "Intro to Object Oriented Programming"));
        assertNull(map.put(1332, "Data Structures and Algorithms"));
        assertNull(map.put(1371, "Programming for Engineers"));
        assertNull(map.put(2340, "Objects and Design"));
        assertNull(map.put(3510, "Design and Analysis of Algorithms"));
        assertNull(map.put(3600, "Introduction to Artificial Intelligence"));
        assertNull(map.put(4510, "Automata and Complexity"));
        assertNull(map.put(4641, "Machine Learning"));
        assertNull(map.put(3240, "Languages and Computation"));

        assertEquals("Data Structures and Algorithms", map.put(1332, "Algorithms and Data Structures"));

        assertEquals(9, map.size());

        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[27];

        expected[18] = new LinearProbingMapEntry<>(2340, "Objects and Design");
        expected[0] = new LinearProbingMapEntry<>(3510, "Design and Analysis of Algorithms");
        expected[1] = new LinearProbingMapEntry<>(4510, "Automata and Complexity");
        expected[24] = new LinearProbingMapEntry<>(4641, "Machine Learning");
        expected[8] = new LinearProbingMapEntry<>(1331, "Intro to Object Oriented Programming");
        expected[9] = new LinearProbingMapEntry<>(1332, "Algorithms and Data Structures");
        expected[21] = new LinearProbingMapEntry<>(1371, "Programming for Engineers");
        expected[10] = new LinearProbingMapEntry<>(3600, "Introduction to Artificial Intelligence");
        expected[2] = new LinearProbingMapEntry<>(3240, "Languages and Computation");

        assertArrayEquals(expected, map.getTable());

    }

    //Remove Tests
    @Test(timeout = TIMEOUT)
    public void testRemove5() {

        assertNull(map.put(1331, "Intro to Object Oriented Programming"));
        assertNull(map.put(1332, "Data Structures and Algorithms"));
        assertNull(map.put(1371, "Programming for Engineers"));
        assertNull(map.put(2340, "Objects and Design"));
        assertNull(map.put(3510, "Design and Analysis of Algorithms"));
        assertNull(map.put(3600, "Introduction to Artificial Intelligence"));
        assertNull(map.put(4510, "Automata and Complexity"));
        assertNull(map.put(4641, "Machine Learning"));
        assertNull(map.put(3240, "Languages and Computation"));

        assertEquals("Intro to Object Oriented Programming", map.remove(1331));
        assertTrue(map.getTable()[8].isRemoved());
        assertEquals(8, map.size());

        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[27];

        expected[18] = new LinearProbingMapEntry<>(2340, "Objects and Design");
        expected[0] = new LinearProbingMapEntry<>(3510, "Design and Analysis of Algorithms");
        expected[1] = new LinearProbingMapEntry<>(4510, "Automata and Complexity");
        expected[24] = new LinearProbingMapEntry<>(4641, "Machine Learning");
        expected[8] = new LinearProbingMapEntry<>(1331, "Intro to Object Oriented Programming");
        expected[8].setRemoved(true);
        expected[9] = new LinearProbingMapEntry<>(1332, "Data Structures and Algorithms");
        expected[21] = new LinearProbingMapEntry<>(1371, "Programming for Engineers");
        expected[10] = new LinearProbingMapEntry<>(3600, "Introduction to Artificial Intelligence");
        expected[2] = new LinearProbingMapEntry<>(3240, "Languages and Computation");

        assertArrayEquals(expected, map.getTable());

    }

    @Test(timeout = TIMEOUT)
    public void testNoSuchElement() {

        try {
            assertNull(map.put(1331, "Intro to Object Oriented Programming"));
            assertNull(map.put(1332, "Data Structures and Algorithms"));
            assertNull(map.put(1371, "Programming for Engineers"));
            assertNull(map.put(2340, "Objects and Design"));
            assertNull(map.put(3510, "Design and Analysis of Algorithms"));
            assertNull(map.put(3600, "Introduction to Artificial Intelligence"));
            assertNull(map.put(4510, "Automata and Complexity"));
            assertNull(map.put(4641, "Machine Learning"));
            assertNull(map.put(3240, "Languages and Computation"));

            map.remove(0);

            fail();
        } catch (NoSuchElementException e) {

        }

    }

    @Test(timeout = TIMEOUT)
    public void testDoubleRemove() {

        try {
            assertNull(map.put(1331, "Intro to Object Oriented Programming"));
            assertNull(map.put(1332, "Data Structures and Algorithms"));
            assertNull(map.put(1371, "Programming for Engineers"));
            assertNull(map.put(2340, "Objects and Design"));
            assertNull(map.put(3510, "Design and Analysis of Algorithms"));
            assertNull(map.put(3600, "Introduction to Artificial Intelligence"));
            assertNull(map.put(4510, "Automata and Complexity"));
            assertNull(map.put(4641, "Machine Learning"));
            assertNull(map.put(3240, "Languages and Computation"));

            assertEquals("Intro to Object Oriented Programming", map.remove(1331));
            assertTrue(map.getTable()[8].isRemoved());
            assertEquals(8, map.size());

            map.remove(1331);
            fail();
        } catch (NoSuchElementException e) {

        }

    }

    @Test(timeout = TIMEOUT)
    public void testAddAgain() {

        assertNull(map.put(1331, "Intro to Object Oriented Programming"));
        assertNull(map.put(1332, "Data Structures and Algorithms"));
        assertNull(map.put(1371, "Programming for Engineers"));
        assertNull(map.put(2340, "Objects and Design"));
        assertNull(map.put(3510, "Design and Analysis of Algorithms"));
        assertNull(map.put(3600, "Introduction to Artificial Intelligence"));
        assertNull(map.put(4510, "Automata and Complexity"));
        assertNull(map.put(4641, "Machine Learning"));
        assertNull(map.put(3240, "Languages and Computation"));

        assertEquals("Intro to Object Oriented Programming", map.remove(1331));
        assertTrue(map.getTable()[8].isRemoved());
        assertEquals(8, map.size());

        assertNull(map.put(1331, "Introduction to Java"));
        assertFalse(map.getTable()[8].isRemoved());
        assertEquals(9, map.size());

        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[27];

        expected[18] = new LinearProbingMapEntry<>(2340, "Objects and Design");
        expected[0] = new LinearProbingMapEntry<>(3510, "Design and Analysis of Algorithms");
        expected[1] = new LinearProbingMapEntry<>(4510, "Automata and Complexity");
        expected[24] = new LinearProbingMapEntry<>(4641, "Machine Learning");
        expected[8] = new LinearProbingMapEntry<>(1331, "Introduction to Java");
        expected[9] = new LinearProbingMapEntry<>(1332, "Data Structures and Algorithms");
        expected[21] = new LinearProbingMapEntry<>(1371, "Programming for Engineers");
        expected[10] = new LinearProbingMapEntry<>(3600, "Introduction to Artificial Intelligence");
        expected[2] = new LinearProbingMapEntry<>(3240, "Languages and Computation");

        assertArrayEquals(expected, map.getTable());

    }

    @Test(timeout = TIMEOUT)
    public void testRemoveAndAdd() {

        assertNull(map.put(1331, "Intro to Object Oriented Programming"));
        assertNull(map.put(1332, "Data Structures and Algorithms"));
        assertNull(map.put(1371, "Programming for Engineers"));
        assertNull(map.put(2340, "Objects and Design"));
        assertNull(map.put(3510, "Design and Analysis of Algorithms"));
        assertNull(map.put(3600, "Introduction to Artificial Intelligence"));
        assertNull(map.put(4510, "Automata and Complexity"));
        assertNull(map.put(4641, "Machine Learning"));
        assertNull(map.put(3240, "Languages and Computation"));

        assertEquals("Intro to Object Oriented Programming", map.remove(1331));
        assertTrue(map.getTable()[8].isRemoved());
        assertEquals(8, map.size());

        assertNull(map.put(8, "Tobias Oliver"));
        assertFalse(map.getTable()[8].isRemoved());
        assertEquals(9, map.size());

        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[27];

        expected[18] = new LinearProbingMapEntry<>(2340, "Objects and Design");
        expected[0] = new LinearProbingMapEntry<>(3510, "Design and Analysis of Algorithms");
        expected[1] = new LinearProbingMapEntry<>(4510, "Automata and Complexity");
        expected[24] = new LinearProbingMapEntry<>(4641, "Machine Learning");
        expected[8] = new LinearProbingMapEntry<>(8, "Tobias Oliver");
        expected[9] = new LinearProbingMapEntry<>(1332, "Data Structures and Algorithms");
        expected[21] = new LinearProbingMapEntry<>(1371, "Programming for Engineers");
        expected[10] = new LinearProbingMapEntry<>(3600, "Introduction to Artificial Intelligence");
        expected[2] = new LinearProbingMapEntry<>(3240, "Languages and Computation");

        assertArrayEquals(expected, map.getTable());

    }

    @Test(timeout = TIMEOUT)
    public void testRemoveAndAdd2() {

        assertNull(map.put(1331, "Intro to Object Oriented Programming"));
        assertNull(map.put(1332, "Data Structures and Algorithms"));
        assertNull(map.put(1371, "Programming for Engineers"));
        assertNull(map.put(2340, "Objects and Design"));
        assertNull(map.put(3510, "Design and Analysis of Algorithms"));
        assertNull(map.put(3600, "Introduction to Artificial Intelligence"));
        assertNull(map.put(4510, "Automata and Complexity"));
        assertNull(map.put(4641, "Machine Learning"));
        assertNull(map.put(3240, "Languages and Computation"));

        assertNull(map.put(27, "Jordan Mason"));

        assertEquals("Design and Analysis of Algorithms", map.remove(3510));
        assertTrue(map.getTable()[0].isRemoved());
        assertEquals(9, map.size());

        assertEquals("Jordan Mason", map.put(27, "Pressley Harvin III"));

        assertEquals(9, map.size());

        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[27];

        expected[18] = new LinearProbingMapEntry<>(2340, "Objects and Design");
        expected[0] = new LinearProbingMapEntry<>(3510, "Design and Analysis of Algorithms");
        expected[0].setRemoved(true);
        expected[1] = new LinearProbingMapEntry<>(4510, "Automata and Complexity");
        expected[3] = new LinearProbingMapEntry<>(27, "Pressley Harvin III");
        expected[24] = new LinearProbingMapEntry<>(4641, "Machine Learning");
        expected[8] = new LinearProbingMapEntry<>(1331, "Intro to Object Oriented Programming");
        expected[9] = new LinearProbingMapEntry<>(1332, "Data Structures and Algorithms");
        expected[21] = new LinearProbingMapEntry<>(1371, "Programming for Engineers");
        expected[10] = new LinearProbingMapEntry<>(3600, "Introduction to Artificial Intelligence");
        expected[2] = new LinearProbingMapEntry<>(3240, "Languages and Computation");

        assertArrayEquals(expected, map.getTable());

    }

    //Get Tests
    @Test(timeout = TIMEOUT)
    public void testNullArg() {

        try {
            assertNull(map.put(1331, "Intro to Object Oriented Programming"));
            assertNull(map.put(1332, "Data Structures and Algorithms"));
            assertNull(map.put(1371, "Programming for Engineers"));
            assertNull(map.put(2340, "Objects and Design"));
            assertNull(map.put(3510, "Design and Analysis of Algorithms"));
            assertNull(map.put(3600, "Introduction to Artificial Intelligence"));
            assertNull(map.put(4510, "Automata and Complexity"));
            assertNull(map.put(4641, "Machine Learning"));

            assertNull(map.put(3240, "Languages and Computation"));

            assertEquals(9, map.size());

            map.get(null);
            fail();

        } catch (IllegalArgumentException e) {

        }

    }

    @Test(timeout = TIMEOUT)
    public void testElementNotFound() {

        try {
            assertNull(map.put(1331, "Intro to Object Oriented Programming"));
            assertNull(map.put(1332, "Data Structures and Algorithms"));
            assertNull(map.put(1371, "Programming for Engineers"));
            assertNull(map.put(2340, "Objects and Design"));
            assertNull(map.put(3510, "Design and Analysis of Algorithms"));
            assertNull(map.put(3600, "Introduction to Artificial Intelligence"));
            assertNull(map.put(4510, "Automata and Complexity"));
            assertNull(map.put(4641, "Machine Learning"));

            assertNull(map.put(3240, "Languages and Computation"));

            assertEquals(9, map.size());

            map.get(27);
            fail();
        } catch (NoSuchElementException e) {

        }

    }

    @Test(timeout = TIMEOUT)
    public void getTest() {

        assertNull(map.put(1331, "Intro to Object Oriented Programming"));
        assertNull(map.put(1332, "Data Structures and Algorithms"));
        assertNull(map.put(1371, "Programming for Engineers"));
        assertNull(map.put(2340, "Objects and Design"));
        assertNull(map.put(3510, "Design and Analysis of Algorithms"));
        assertNull(map.put(3600, "Introduction to Artificial Intelligence"));
        assertNull(map.put(4510, "Automata and Complexity"));
        assertNull(map.put(4641, "Machine Learning"));
        assertNull(map.put(3240, "Languages and Computation"));

        assertEquals(9, map.size());

        assertEquals("Languages and Computation", map.get(3240));

        assertEquals(9, map.size());

    }

    @Test(timeout = TIMEOUT)
    public void getRemoved() {

        try {
            assertNull(map.put(1331, "Intro to Object Oriented Programming"));
            assertNull(map.put(1332, "Data Structures and Algorithms"));
            assertNull(map.put(1371, "Programming for Engineers"));
            assertNull(map.put(2340, "Objects and Design"));
            assertNull(map.put(3510, "Design and Analysis of Algorithms"));
            assertNull(map.put(3600, "Introduction to Artificial Intelligence"));
            assertNull(map.put(4510, "Automata and Complexity"));
            assertNull(map.put(4641, "Machine Learning"));
            assertNull(map.put(3240, "Languages and Computation"));

            assertEquals(9, map.size());

            assertEquals("Languages and Computation", map.remove(3240));

            assertEquals(8, map.size());

            map.get(3240);
            fail();

        } catch (NoSuchElementException e) {

        }

    }

    //Contains Key Tests
    @Test(timeout = TIMEOUT)
    public void containsKeyBasicTest() {

        assertNull(map.put(1331, "Intro to Object Oriented Programming"));
        assertNull(map.put(1332, "Data Structures and Algorithms"));
        assertNull(map.put(1371, "Programming for Engineers"));
        assertNull(map.put(2340, "Objects and Design"));
        assertNull(map.put(3510, "Design and Analysis of Algorithms"));
        assertNull(map.put(3600, "Introduction to Artificial Intelligence"));
        assertNull(map.put(4510, "Automata and Complexity"));
        assertNull(map.put(4641, "Machine Learning"));
        assertNull(map.put(3240, "Languages and Computation"));

        assertEquals(9, map.size());

        assertTrue(map.containsKey(3240));

        assertEquals(9, map.size());

    }

    @Test(timeout = TIMEOUT)
    public void containsRemovedKey() {

        assertNull(map.put(1331, "Intro to Object Oriented Programming"));
        assertNull(map.put(1332, "Data Structures and Algorithms"));
        assertNull(map.put(1371, "Programming for Engineers"));
        assertNull(map.put(2340, "Objects and Design"));
        assertNull(map.put(3510, "Design and Analysis of Algorithms"));
        assertNull(map.put(3600, "Introduction to Artificial Intelligence"));
        assertNull(map.put(4510, "Automata and Complexity"));
        assertNull(map.put(4641, "Machine Learning"));
        assertNull(map.put(3240, "Languages and Computation"));

        assertEquals(9, map.size());

        assertEquals("Languages and Computation", map.remove(3240));

        assertEquals(8, map.size());

        assertFalse(map.containsKey(3240));

    }

    //Resize Tests
    @Test(timeout = TIMEOUT)
    public void resizeToLengthOfHashMap() {

        assertNull(map.put(1331, "Intro to Object Oriented Programming"));
        assertNull(map.put(1332, "Data Structures and Algorithms"));
        assertNull(map.put(1371, "Programming for Engineers"));
        assertNull(map.put(2340, "Objects and Design"));
        assertNull(map.put(3510, "Design and Analysis of Algorithms"));
        assertNull(map.put(3600, "Introduction to Artificial Intelligence"));
        assertNull(map.put(4510, "Automata and Complexity"));
        assertNull(map.put(4641, "Machine Learning"));
        assertNull(map.put(3240, "Languages and Computation"));

        map.resizeBackingTable(9);

        assertEquals(9, map.size());

        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[9];

        expected[5] = new LinearProbingMapEntry<>(2340, "Objects and Design");
        expected[0] = new LinearProbingMapEntry<>(3510, "Design and Analysis of Algorithms");
        expected[1] = new LinearProbingMapEntry<>(4510, "Automata and Complexity");
        expected[7] = new LinearProbingMapEntry<>(4641, "Machine Learning");
        expected[8] = new LinearProbingMapEntry<>(1331, "Intro to Object Oriented Programming");
        expected[3] = new LinearProbingMapEntry<>(1332, "Data Structures and Algorithms");
        expected[6] = new LinearProbingMapEntry<>(1371, "Programming for Engineers");
        expected[4] = new LinearProbingMapEntry<>(3600, "Introduction to Artificial Intelligence");
        expected[2] = new LinearProbingMapEntry<>(3240, "Languages and Computation");

        assertArrayEquals(expected, map.getTable());

    }

    @Test(timeout = TIMEOUT)
    public void resizeTooLittle() {

        try {
            assertNull(map.put(1331, "Intro to Object Oriented Programming"));
            assertNull(map.put(1332, "Data Structures and Algorithms"));
            assertNull(map.put(1371, "Programming for Engineers"));
            assertNull(map.put(2340, "Objects and Design"));
            assertNull(map.put(3510, "Design and Analysis of Algorithms"));
            assertNull(map.put(3600, "Introduction to Artificial Intelligence"));
            assertNull(map.put(4510, "Automata and Complexity"));
            assertNull(map.put(4641, "Machine Learning"));
            assertNull(map.put(3240, "Languages and Computation"));

            map.resizeBackingTable(3);
            fail();

        } catch (IllegalArgumentException e) {

        }

    }

    //Key Set Test
    @Test(timeout = TIMEOUT)
    public void testKeySetAdv() {

        assertNull(map.put(1331, "Intro to Object Oriented Programming"));
        assertNull(map.put(1332, "Data Structures and Algorithms"));
        assertNull(map.put(1371, "Programming for Engineers"));
        assertNull(map.put(2340, "Objects and Design"));
        assertNull(map.put(3510, "Design and Analysis of Algorithms"));
        assertNull(map.put(3600, "Introduction to Artificial Intelligence"));
        assertNull(map.put(4510, "Automata and Complexity"));
        assertNull(map.put(4641, "Machine Learning"));
        assertNull(map.put(3240, "Languages and Computation"));

        assertNull(map.put(27, "Jordan Mason"));

        assertEquals("Design and Analysis of Algorithms", map.remove(3510));
        assertTrue(map.getTable()[0].isRemoved());
        assertEquals(9, map.size());

        assertEquals("Jordan Mason", map.put(27, "Pressley Harvin III"));

        assertEquals(9, map.size());

        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[27];

        expected[18] = new LinearProbingMapEntry<>(2340, "Objects and Design");
        expected[0] = new LinearProbingMapEntry<>(3510, "Design and Analysis of Algorithms");
        expected[0].setRemoved(true);
        expected[1] = new LinearProbingMapEntry<>(4510, "Automata and Complexity");
        expected[3] = new LinearProbingMapEntry<>(27, "Pressley Harvin III");
        expected[24] = new LinearProbingMapEntry<>(4641, "Machine Learning");
        expected[8] = new LinearProbingMapEntry<>(1331, "Intro to Object Oriented Programming");
        expected[9] = new LinearProbingMapEntry<>(1332, "Data Structures and Algorithms");
        expected[21] = new LinearProbingMapEntry<>(1371, "Programming for Engineers");
        expected[10] = new LinearProbingMapEntry<>(3600, "Introduction to Artificial Intelligence");
        expected[2] = new LinearProbingMapEntry<>(3240, "Languages and Computation");

        assertArrayEquals(expected, map.getTable());

        Set<Integer> expectedSet = new HashSet<>();

        expectedSet.add(4510);
        expectedSet.add(3240);
        expectedSet.add(27);
        expectedSet.add(1331);
        expectedSet.add(1332);
        expectedSet.add(3600);
        expectedSet.add(2340);
        expectedSet.add(1371);
        expectedSet.add(4641);

        assertEquals(expectedSet, map.keySet());

    }

    //Values Test
    @Test(timeout = TIMEOUT)
    public void testValuesAdv() {

        assertNull(map.put(1331, "Intro to Object Oriented Programming"));
        assertNull(map.put(1332, "Data Structures and Algorithms"));
        assertNull(map.put(1371, "Programming for Engineers"));
        assertNull(map.put(2340, "Objects and Design"));
        assertNull(map.put(3510, "Design and Analysis of Algorithms"));
        assertNull(map.put(3600, "Introduction to Artificial Intelligence"));
        assertNull(map.put(4510, "Automata and Complexity"));
        assertNull(map.put(4641, "Machine Learning"));
        assertNull(map.put(3240, "Languages and Computation"));

        assertNull(map.put(27, "Jordan Mason"));

        assertEquals("Design and Analysis of Algorithms", map.remove(3510));
        assertTrue(map.getTable()[0].isRemoved());
        assertEquals(9, map.size());

        assertEquals("Jordan Mason", map.put(27, "Pressley Harvin III"));

        assertEquals(9, map.size());

        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[27];

        expected[18] = new LinearProbingMapEntry<>(2340, "Objects and Design");
        expected[0] = new LinearProbingMapEntry<>(3510, "Design and Analysis of Algorithms");
        expected[0].setRemoved(true);
        expected[1] = new LinearProbingMapEntry<>(4510, "Automata and Complexity");
        expected[3] = new LinearProbingMapEntry<>(27, "Pressley Harvin III");
        expected[24] = new LinearProbingMapEntry<>(4641, "Machine Learning");
        expected[8] = new LinearProbingMapEntry<>(1331, "Intro to Object Oriented Programming");
        expected[9] = new LinearProbingMapEntry<>(1332, "Data Structures and Algorithms");
        expected[21] = new LinearProbingMapEntry<>(1371, "Programming for Engineers");
        expected[10] = new LinearProbingMapEntry<>(3600, "Introduction to Artificial Intelligence");
        expected[2] = new LinearProbingMapEntry<>(3240, "Languages and Computation");

        assertArrayEquals(expected, map.getTable());

        List<String> values = new ArrayList<>();

        values.add("Automata and Complexity");
        values.add("Languages and Computation");
        values.add("Pressley Harvin III");
        values.add("Intro to Object Oriented Programming");
        values.add("Data Structures and Algorithms");
        values.add("Introduction to Artificial Intelligence");
        values.add("Objects and Design");
        values.add("Programming for Engineers");
        values.add("Machine Learning");

        assertEquals(values, map.values());

    }

    //Another Edge Case :)
    @Test(timeout = TIMEOUT)
    public void smallStartingMap() {

        map = new LinearProbingHashMap<>(0);
        assertNull(map.put(10, "Jeff Sims"));
        assertNull(map.put(15, "Malachi Carter"));
        assertNull(map.put(21, "Jahmyr Gibbs"));

        assertEquals(3, map.size());

        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[7];

        expected[0] = new LinearProbingMapEntry<>(21, "Jahmyr Gibbs");
        expected[1] = new LinearProbingMapEntry<>(15, "Malachi Carter");
        expected[3] = new LinearProbingMapEntry<>(10, "Jeff Sims");

        assertArrayEquals(expected, map.getTable());

    }

    @Test(timeout = TIMEOUT)
    public void testInitialization6() {
        LinearProbingMapEntry<Integer, String>[] emptyTable =
                new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];
        assertEquals(0, this.map.size());
        assertArrayEquals(emptyTable, this.map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testIntConstructor() {
        this.map = new LinearProbingHashMap<>(20);
        LinearProbingMapEntry<Integer, String>[] emptyTable =
                new LinearProbingMapEntry[20];
        assertEquals(0, this.map.size());
        assertArrayEquals(emptyTable, this.map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testPutNull() {
        try {
            this.map.put(null, "hello");
            fail();
        } catch (IllegalArgumentException e) { }

        try {
            this.map.put(2, null);
            fail();
        } catch (IllegalArgumentException e) { }

        try {
            this.map.put(null, null);
            fail();
        } catch (IllegalArgumentException e) { }

    }

    @Test(timeout = TIMEOUT)
    public void testPutSimple() {
        this.map.put(0, "hello");
        this.map.put(5, "there");
        this.map.put(16, "my friend");
        this.map.put(20, "how are you");
        this.map.put(-6, "today");

        LinearProbingMapEntry<Integer, String>[] table =
                new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];

        table[0] = new LinearProbingMapEntry<>(0, "hello");
        table[5] = new LinearProbingMapEntry<>(5, "there");
        table[3] = new LinearProbingMapEntry<>(16, "my friend");
        table[6] = new LinearProbingMapEntry<>(-6, "today");
        table[7] = new LinearProbingMapEntry<>(20, "how are you");

        assertEquals(5, this.map.size());
        assertArrayEquals(table, this.map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testPutResize() {
        for (int i = 0; i < 9; i++) {
            this.map.put(i, i + "a");
        }
        LinearProbingMapEntry<Integer, String>[] table =
                new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY * 2 + 1];
        for (int i = 0; i < 9; i++) {
            table[i] = new LinearProbingMapEntry<>(i, i + "a");
        }
        assertArrayEquals(table, this.map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testPutCollisions() {
        this.map.put(1, "a");
        this.map.put(14, "b");
        this.map.put(3, "c");
        this.map.put(-3, "d");
        this.map.put(0, "e");
        this.map.put(4, "f");
        this.map.put(13, "g");
        this.map.put(25, "h");
        // no resize

        LinearProbingMapEntry<Integer, String>[] table =
                new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];
        table[0] = new LinearProbingMapEntry<>(0, "e");
        table[1] = new LinearProbingMapEntry<>(1, "a");
        table[2] = new LinearProbingMapEntry<>(14, "b");
        table[3] = new LinearProbingMapEntry<>(3, "c");
        table[4] = new LinearProbingMapEntry<>(-3, "d");
        table[5] = new LinearProbingMapEntry<>(4, "f");
        table[6] = new LinearProbingMapEntry<>(13, "g");
        table[12] = new LinearProbingMapEntry<>(25, "h");

        assertEquals(8, this.map.size());
        assertArrayEquals(table, this.map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testPutCollisionsResize() {
        this.map.put(1, "a");
        this.map.put(28, "b");
        this.map.put(2, "c");
        this.map.put(14, "d");
        this.map.put(41, "e");
        this.map.put(29, "f");
        this.map.put(3, "g");
        this.map.put(30, "h");
        this.map.put(57, "i");

        LinearProbingMapEntry<Integer, String>[] table =
                new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY * 2 + 1];
        table[1] = new LinearProbingMapEntry<>(1, "a");
        table[2] = new LinearProbingMapEntry<>(28, "b");
        table[3] = new LinearProbingMapEntry<>(2, "c");
        table[4] = new LinearProbingMapEntry<>(29, "f");
        table[5] = new LinearProbingMapEntry<>(3, "g");
        table[6] = new LinearProbingMapEntry<>(30, "h");
        table[7] = new LinearProbingMapEntry<>(57, "i");
        table[14] = new LinearProbingMapEntry<>(14, "d");
        table[15] = new LinearProbingMapEntry<>(41, "e");

        assertEquals(9, this.map.size());
        assertArrayEquals(table, this.map.getTable());
    }

    private void mapWithDeletedEntries() {
        this.map.put(14, "a");
        this.map.put(2, "b");
        this.map.put(-3, "c");
        this.map.put(-17, "d");
        this.map.put(-6, "e");
        this.map.put(16, "a");
        this.map.put(3, "c");
        this.map.put(32, "d");
        this.map.put(45, "d");

        this.map.remove(2);
        this.map.remove(-17);
        this.map.remove(3);

    }

    @Test(timeout = TIMEOUT)
    public void testPutProbe() {
        this.mapWithDeletedEntries();
        this.map.put(19, "f");
        this.map.put(-17, "g");

        LinearProbingMapEntry<Integer, String>[] table =
                new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY * 2 + 1];


        LinearProbingMapEntry<Integer, String> key2 =
                new LinearProbingMapEntry<>(2, "b");
        key2.setRemoved(true);
        table[2] = key2;

        table[3] = new LinearProbingMapEntry<>(-3, "c");
        LinearProbingMapEntry<Integer, String> key3 =
                new LinearProbingMapEntry<>(3, "c");
        key3.setRemoved(true);
        table[4] = key3;
        table[5] = new LinearProbingMapEntry<>(32, "d");
        table[6] = new LinearProbingMapEntry<>(-6, "e");

        table[14] = new LinearProbingMapEntry<>(14, "a");
        table[16] = new LinearProbingMapEntry<>(16, "a");
        table[17] = new LinearProbingMapEntry<>(-17, "g");
        table[18] = new LinearProbingMapEntry<>(45, "d");
        table[19] = new LinearProbingMapEntry<>(19, "f");

        assertArrayEquals(table, this.map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testPutDuplicate() {
        this.map.put(1, "a");
        this.map.put(28, "b");
        this.map.put(2, "c");
        this.map.put(14, "d");
        this.map.put(41, "e");
        this.map.remove(2);
        this.map.remove(1);

        assertEquals("b", this.map.put(28, "hi"));
        assertEquals("d", this.map.put(14, "there"));
        assertEquals("e", this.map.put(41, "friend"));

    }


    @Test(timeout = TIMEOUT)
    public void testRemove6() {
        this.map.resizeBackingTable(7);
        this.map.put(16, "a");
        this.map.put(1, "b");
        this.map.put(15, "c");
        this.map.put(-5, "d");
        this.map.put(-20, "e");
        this.map.put(35, "f");

        this.map.remove(1);
        this.map.remove(-5);
        this.map.remove(35);

        LinearProbingMapEntry<Integer, String>[] table =
                new LinearProbingMapEntry[15];
        table[0] = new LinearProbingMapEntry<>(15, "c");
        LinearProbingMapEntry<Integer, String> key1 =
                new LinearProbingMapEntry<>(1, "b");
        key1.setRemoved(true);
        table[1] = key1;

        table[2] = new LinearProbingMapEntry<>(16, "a");

        LinearProbingMapEntry<Integer, String> keyMinus5 =
                new LinearProbingMapEntry<>(-5, "d");
        keyMinus5.setRemoved(true);
        table[5] = keyMinus5;
        table[6] = new LinearProbingMapEntry<>(-20, "e");
        LinearProbingMapEntry<Integer, String> key35 =
                new LinearProbingMapEntry<>(35, "f");
        key35.setRemoved(true);
        table[7] = key35;

        assertArrayEquals(table, this.map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveNotExists() {
        this.map.put(1, "a");
        this.map.put(2, "b");
        try {
            this.map.remove(4);
            fail();
        } catch (NoSuchElementException e) { }

    }

    @Test(timeout = TIMEOUT)
    public void testRemoveTwice() {
        this.map.put(1, "a");
        this.map.put(2, "b");
        this.map.put(3, "c");
        this.map.remove(3);
        try {
            this.map.remove(3);
            fail();
        } catch (NoSuchElementException e) { }
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveNull() {
        try {
            this.map.remove(null);
        } catch (IllegalArgumentException e) { }
    }


    @Test(timeout = TIMEOUT)
    public void testGetNotExists() {
        this.mapWithDeletedEntries();

        try {
            this.map.get(1);
            fail();
        } catch (NoSuchElementException e) { }
    }


    @Test(timeout = TIMEOUT)
    public void testGetNull() {
        this.mapWithDeletedEntries();

        try {
            this.map.get(null);
        } catch (IllegalArgumentException e) { }
    }

    @Test(timeout = TIMEOUT)
    public void testGet6() {
        this.mapWithDeletedEntries();
        assertEquals("a", this.map.get(14));
        assertEquals("c", this.map.get(-3));
        assertEquals("e", this.map.get(-6));
        assertEquals("a", this.map.get(16));
        assertEquals("d", this.map.get(32));
        assertEquals("d", this.map.get(45));
    }

    @Test(timeout = TIMEOUT)
    public void testGetDeleted() {
        this.mapWithDeletedEntries();
        try {
            this.map.get(2);
            fail();
        } catch (NoSuchElementException e) { }
        try {
            this.map.get(-17);
            fail();
        } catch (NoSuchElementException e) { }
        try {
            this.map.get(3);
            fail();
        } catch (NoSuchElementException e) { }
    }

    @Test(timeout = TIMEOUT)
    public void testContainsKey6() {
        this.mapWithDeletedEntries();
        assertTrue(this.map.containsKey(14));
        assertTrue(this.map.containsKey(-3));
        assertTrue(this.map.containsKey(-6));
        assertTrue(this.map.containsKey(16));
        assertTrue(this.map.containsKey(32));
        assertTrue(this.map.containsKey(45));

        assertFalse(this.map.containsKey(2));
        assertFalse(this.map.containsKey(-17));
        assertFalse(this.map.containsKey(3));
    }

    @Test(timeout = TIMEOUT)
    public void testContainsKeyNull() {
        this.mapWithDeletedEntries();
        try {
            this.map.containsKey(null);
            fail();
        } catch (IllegalArgumentException e) { }
    }

    @Test(timeout = TIMEOUT)
    public void testKeySet6() {
        this.mapWithDeletedEntries();
        Set<Integer> keys = new HashSet<>();
        keys.add(14);
        keys.add(-3);
        keys.add(-6);
        keys.add(16);
        keys.add(32);
        keys.add(45);
        assertEquals(keys, this.map.keySet());
    }

    @Test(timeout = TIMEOUT)
    public void testEmptyKeySet() {
        Set<Integer> keys = new HashSet<>();
        assertEquals(keys, this.map.keySet());
    }

    @Test(timeout = TIMEOUT)
    public void testValues6() {
        this.mapWithDeletedEntries();
        List<String> values = new ArrayList<>();
        values.add("c");
        values.add("d");
        values.add("e");
        values.add("a");
        values.add("a");
        values.add("d");
        assertEquals(values, this.map.values());
    }

    @Test(timeout = TIMEOUT)
    public void testEmptyValues() {
        List<String> values = new ArrayList<>();
        assertEquals(values, this.map.values());
    }

    @Test(timeout = TIMEOUT)
    public void testResizeBackingTable() {
        this.map.put(1, "a");
        this.map.put(-6, "b");
        this.map.put(-27, "c");
        this.map.put(47, "d");
        this.map.put(2, "e");
        this.map.put(25, "f");

        this.map.remove(47);
        this.map.remove(25);

        this.map.resizeBackingTable(4);
        LinearProbingMapEntry<Integer, String>[] table =
                new LinearProbingMapEntry[4];

        table[0] = new LinearProbingMapEntry<>(-6, "b");
        table[1] = new LinearProbingMapEntry<>(1, "a");
        table[2] = new LinearProbingMapEntry<>(2, "e");
        table[3] = new LinearProbingMapEntry<>(-27, "c");

        assertArrayEquals(table, this.map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testResizeTooSmall() {
        this.map.put(1, "a");
        this.map.put(6, "b");
        this.map.put(0, "c");
        this.map.put(7, "d");
        this.map.remove(7);

        try {
            this.map.resizeBackingTable(2);
        } catch (IllegalArgumentException e) { }
    }

    @Test(timeout = TIMEOUT)
    public void testSize() {
        this.map.resizeBackingTable(4);
        assertEquals(0, this.map.size());
        this.map.put(1, "a");
        assertEquals(1, this.map.size());
        this.map.put(6, "b");
        assertEquals(2, this.map.size());
        this.map.put(0, "c");

        this.map.remove(1);
        assertEquals(2, this.map.size());

        this.map.put(7, "d");
        assertEquals(3, this.map.size());
        this.map.put(2, "e");
        assertEquals(4, this.map.size());

        this.map.remove(6);
        assertEquals(3, this.map.size());
    }

    @Test(timeout = TIMEOUT)
    public void testClear6() {

        this.map.resizeBackingTable(8);
        this.map.put(0, "a");
        this.map.put(1, "b");
        this.map.put(9, "c");
        this.map.put(-9, "d");
        this.map.remove(9);

        LinearProbingMapEntry<Integer, String>[] table =
                new LinearProbingMapEntry[8];
        table[0] = new LinearProbingMapEntry<>(0, "a");
        table[1] = new LinearProbingMapEntry<>(1, "b");
        LinearProbingMapEntry<Integer,String> key9 =
                new LinearProbingMapEntry<>(9, "c");
        key9.setRemoved(true);
        table[2] = key9;
        table[3] = new LinearProbingMapEntry<>(-9, "d");

        assertArrayEquals(table, this.map.getTable());
        assertEquals(3, this.map.size());

        this.map.clear();
        LinearProbingMapEntry<Integer, String>[] cleared =
                new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];
        assertArrayEquals(cleared, this.map.getTable());
        assertEquals(0, this.map.size());
    }
}
