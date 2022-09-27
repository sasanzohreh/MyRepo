import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.Comparator;
import java.util.Random;
import java.util.Arrays;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

/**
 * This is a basic set of unit tests for Sorting.
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
public class SortingStudentTest {

    private static final int TIMEOUT = 200;
    private TeachingAssistant[] tas;
    private TeachingAssistant[] tasByName;
    private ComparatorPlus<TeachingAssistant> comp;
    private Integer[] array;
    private int[] intArray;
    private Comparator<Integer> comparator;
    private Integer[] len0Unsorted;
    private Integer[] len0Sorted;
    private Integer[] len1Unsorted;
    private Integer[] len1Sorted;
    private Comparator<Integer> comparator2 = Comparator.comparingInt(a -> a);
    private static final String dDuplicate = "".concat("D"); //Allocate different memory
    private static final String dDuplicate2 = "".concat("D"); //Allocate different memory

    private static final SortingStudentTest.TeachingAssistant[] arrayWithDuplicatesAnswer = {
            new SortingStudentTest.TeachingAssistant("A"),
            new SortingStudentTest.TeachingAssistant("B"),
            new SortingStudentTest.TeachingAssistant("C"),
            new SortingStudentTest.TeachingAssistant(dDuplicate),
            new SortingStudentTest.TeachingAssistant(dDuplicate2)
    };

    private SortingStudentTest.TeachingAssistant[] unsortedTAs;
    private SortingStudentTest.TeachingAssistant[] sortedTAs;
    private SortingStudentTest.TeachingAssistant[] reverseSortedTAs;
    private SortingStudentTest.TeachingAssistant[] stableSortedTas;
    private SortingStudentTest.TeachingAssistant[] quickSortUnsorted;
    private SortingStudentTest.TeachingAssistant[] quickSortReverseSorted;
    private Random rand;
    @Rule
    public Timeout globalTimeout = new Timeout(200, TimeUnit.MILLISECONDS);

    private SortingStudentTest.ComparatorPlus<SortingStudentTest.TeachingAssistant> comp2;



    @Before
    public void setUp() {
        /*
            Unsorted Names:
                index 0: Alex
                index 1: Ivan
                index 2: Miguel
                index 3: Reece
                index 4: Eunseo
                index 5: Antonia
                index 6: Yotam
                index 7: Mitchell
                index 8: Harrison
                index 9: Destini
         */

        /*
            Sorted Names:
                index 0: Alex
                index 1: Antonia
                index 2: Destini
                index 3: Eunseo
                index 4: Harrison
                index 5: Ivan
                index 6: Miguel
                index 7: Mitchell
                index 8: Reece
                index 9: Yotam
         */

        tas = new TeachingAssistant[10];
        tas[0] = new TeachingAssistant("Alex");
        tas[1] = new TeachingAssistant("Ivan");
        tas[2] = new TeachingAssistant("Miguel");
        tas[3] = new TeachingAssistant("Reece");
        tas[4] = new TeachingAssistant("Eunseo");
        tas[5] = new TeachingAssistant("Antonia");
        tas[6] = new TeachingAssistant("Yotam");
        tas[7] = new TeachingAssistant("Mitchell");
        tas[8] = new TeachingAssistant("Harrison");
        tas[9] = new TeachingAssistant("Destini");
        tasByName = new TeachingAssistant[10];
        tasByName[0] = tas[0];
        tasByName[1] = tas[5];
        tasByName[2] = tas[9];
        tasByName[3] = tas[4];
        tasByName[4] = tas[8];
        tasByName[5] = tas[1];
        tasByName[6] = tas[2];
        tasByName[7] = tas[7];
        tasByName[8] = tas[3];
        tasByName[9] = tas[6];

        comp = TeachingAssistant.getNameComparator();
    }

    @Test(timeout = TIMEOUT)
    public void testInsertionSort() {
        Sorting.insertionSort(tas, comp);
        assertArrayEquals(tasByName, tas);
        assertTrue("Number of comparisons: " + comp.getCount(),
            comp.getCount() <= 30 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testCocktailSort() {
        Sorting.cocktailSort(tas, comp);
        assertArrayEquals(tasByName, tas);
        assertTrue("Number of comparisons: " + comp.getCount(),
            comp.getCount() <= 33 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testMergeSort() {
        Sorting.mergeSort(tas, comp);
        assertArrayEquals(tasByName, tas);
        assertTrue("Number of comparisons: " + comp.getCount(),
            comp.getCount() <= 24 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testLsdRadixSort() {
        int[] unsortedArray = new int[] {54, 28, 58, 84, 20, 122, -85, 3};
        int[] sortedArray = new int[] {-85, 3, 20, 28, 54, 58, 84, 122};
        Sorting.lsdRadixSort(unsortedArray);
        assertArrayEquals(sortedArray, unsortedArray);
    }

    @Test(timeout = TIMEOUT)
    public void testQuickSort() {
        Sorting.quickSort(tas, comp, new Random(234));
        assertArrayEquals(tasByName, tas);
        assertTrue("Number of comparisons: " + comp.getCount(),
            comp.getCount() <= 30 && comp.getCount() != 0);
    }

    /**
     * Class for testing proper sorting.
     */
    private static class TeachingAssistant {
        private String name;

        /**
         * Create a teaching assistant.
         *
         * @param name name of TA
         */
        public TeachingAssistant(String name) {
            this.name = name;
        }

        /**
         * Get the name of the teaching assistant.
         *
         * @return name of teaching assistant
         */
        public String getName() {
            return name;
        }

        /**
         * Create a comparator that compares the names of the teaching
         * assistants.
         *
         * @return comparator that compares the names of the teaching assistants
         */
        public static ComparatorPlus<TeachingAssistant> getNameComparator() {
            return new ComparatorPlus<TeachingAssistant>() {
                @Override
                public int compare(TeachingAssistant ta1,
                                   TeachingAssistant ta2) {
                    incrementCount();
                    return ta1.getName().compareTo(ta2.getName());
                }
            };
        }

        @Override
        public String toString() {
            return "Name: " + name;
        }

        @Override
        public boolean equals(Object other) {
            if (other == null) {
                return false;
            }
            if (this == other) {
                return true;
            }
            return other instanceof TeachingAssistant
                && ((TeachingAssistant) other).name.equals(this.name);
        }
    }

    /**
     * Inner class that allows counting how many comparisons were made.
     */
    private abstract static class ComparatorPlus<T> implements Comparator<T> {
        private int count;

        /**
         * Get the number of comparisons made.
         *
         * @return number of comparisons made
         */
        public int getCount() {
            return count;
        }

        /**
         * Increment the number of comparisons made by one. Call this method in
         * your compare() implementation.
         */
        public void incrementCount() {
            count++;
        }
    }

    @Before
    public void setUp2() {
        array = new Integer[(int) (Math.random() * 20 + 1)];
        intArray = new int[array.length];
        comparator = Comparator.comparingInt(a -> a);
        for (int i = 0; i < array.length; i++) {
            array[i] = (int)(Math.random() * 101 - 50);
            intArray[i] = array[i];
        }
    }

    private boolean isSorted(Integer[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }

    private boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {

                return false;
            }
        }
        return true;
    }

    @Test(timeout = TIMEOUT)
    public void testInsertionSort2() {
        for (int i = 0; i < 100; i++) {
            setUp2();
            Sorting.insertionSort(array, comparator);
            assertTrue(isSorted(array));
        }
    }

    @Test(timeout = TIMEOUT)
    public void testCocktailSort2() {
        for (int i = 0; i < 100; i++) {
            setUp2();
            Sorting.cocktailSort(array, comparator);
            assertTrue(isSorted(array));
        }
    }

    @Test(timeout = TIMEOUT)
    public void testMergeSort2() {
        for (int i = 0; i < 100; i++) {
            setUp2();
            Sorting.mergeSort(array, comparator);
            assertTrue(isSorted(array));
        }
    }

    @Test(timeout = TIMEOUT)
    public void testRadixSort2() {
        for (int i = 0; i < 100; i++) {
            setUp2();
            Sorting.lsdRadixSort(intArray);
            assertTrue(isSorted(intArray));
        }
    }

    @Test(timeout = TIMEOUT)
    public void testQuickSort2() {
        for (int i = 0; i < 100; i++) {
            setUp2();
            Sorting.quickSort(array, comparator, new Random(254));
            assertTrue(isSorted(array));
        }
    }

    @Before
    public void setUp3() {
        len0Unsorted = new Integer[0];
        len0Sorted = new Integer[0];
        len1Unsorted = new Integer[]{Integer.MAX_VALUE};
        len1Sorted = new Integer[]{Integer.MAX_VALUE};
    }





    @Test(timeout = TIMEOUT)
    public void testInsertLen01() {
        Sorting.insertionSort(len0Unsorted, comparator2);
        assertArrayEquals(len0Sorted, len0Unsorted);

        Sorting.insertionSort(len1Unsorted, comparator2);
        assertArrayEquals(len1Sorted, len1Unsorted);
    }

    // test stable for insertion
    @Test(timeout = TIMEOUT)
    public void testInsertStable() {
        Integer[] sorted = new Integer[5];
        Integer sixA = new Integer(6);
        Integer sixB = new Integer(6);
        sorted[0] = 8;
        sorted[1] = 1;
        sorted[2] = 2;
        sorted[3] = sixA;
        sorted[4] = sixB;
        Sorting.insertionSort(sorted, comparator2);

        assertEquals(Integer.valueOf(1), sorted[0]);
        assertEquals(Integer.valueOf(2), sorted[1]);
        assertSame(sixA, sorted[2]);
        assertSame(sixB, sorted[3]);
        assertEquals(Integer.valueOf(8), sorted[4]);
    }




    @Test(timeout = TIMEOUT)
    public void testCocktailLen01() {
        Sorting.cocktailSort(len0Unsorted, comparator2);
        assertArrayEquals(len0Sorted, len0Unsorted);

        Sorting.cocktailSort(len1Unsorted, comparator2);
        assertArrayEquals(len1Sorted, len1Unsorted);
    }

    // test stable for cocktail shaker
    @Test(timeout = TIMEOUT)
    public void testCocktailStable() {
        Integer[] sorted = new Integer[2];
        Integer sixA = new Integer(6);
        Integer sixB = new Integer(6);
        sorted[0] = sixA;
        sorted[1] = sixB;
        Sorting.cocktailSort(sorted, comparator2);

        assertSame(sixA, sorted[0]);
        assertSame(sixB, sorted[1]);

        sorted = new Integer[5];
        sixA = new Integer(6);
        sixB = new Integer(6);
        sorted[0] = 10;
        sorted[1] = sixA;
        sorted[2] = sixB;
        sorted[3] = 7;
        sorted[4] = 8;
        Sorting.cocktailSort(sorted, comparator2);

        assertSame(sixA, sorted[0]);
        assertSame(sixB, sorted[1]);
        assertEquals(Integer.valueOf(7), sorted[2]);
        assertEquals(Integer.valueOf(8), sorted[3]);
        assertEquals(Integer.valueOf(10), sorted[4]);
    }



    @Test(timeout = TIMEOUT)
    public void testMergeLen01() {
        Sorting.mergeSort(len0Unsorted, comparator2);
        assertArrayEquals(len0Sorted, len0Unsorted);

        Sorting.mergeSort(len1Unsorted, comparator2);
        assertArrayEquals(len1Sorted, len1Unsorted);
    }

    // odd and even length arrays
    @Test(timeout = TIMEOUT)
    public void testMergeOdd() {
        Integer[] sorted = new Integer[7];
        sorted[0] = -1;
        sorted[1] = Integer.MAX_VALUE;
        sorted[2] = 0;
        sorted[3] = 21;
        sorted[4] = 7;
        sorted[5] = Integer.MIN_VALUE;
        sorted[6] = 5;
        Sorting.mergeSort(sorted, comparator2);

        assertEquals(Integer.valueOf(Integer.MIN_VALUE), sorted[0]);
        assertEquals(Integer.valueOf(-1), sorted[1]);
        assertEquals(Integer.valueOf(0), sorted[2]);
        assertEquals(Integer.valueOf(5), sorted[3]);
        assertEquals(Integer.valueOf(7), sorted[4]);
        assertEquals(Integer.valueOf(21), sorted[5]);
        assertEquals(Integer.valueOf(Integer.MAX_VALUE), sorted[6]);
    }

    @Test(timeout = TIMEOUT)
    public void testMergeEven() {
        Integer[] sorted = new Integer[6];
        sorted[0] = -1;
        sorted[1] = Integer.MAX_VALUE;
        sorted[2] = 0;
        sorted[3] = 21;
        sorted[4] = 7;
        sorted[5] = Integer.MIN_VALUE;
        Sorting.mergeSort(sorted, comparator2);

        assertEquals(Integer.valueOf(Integer.MIN_VALUE), sorted[0]);
        assertEquals(Integer.valueOf(-1), sorted[1]);
        assertEquals(Integer.valueOf(0), sorted[2]);
        assertEquals(Integer.valueOf(7), sorted[3]);
        assertEquals(Integer.valueOf(21), sorted[4]);
        assertEquals(Integer.valueOf(Integer.MAX_VALUE), sorted[5]);
    }

    // test stable for merge sort, make sure to look at splits and have 6A
    // as first in a left and 6B as lowest in right.
    @Test(timeout = TIMEOUT)
    public void testMergeStable() {
        Integer[] sorted = new Integer[5];
        Integer sixA = new Integer(6);
        Integer sixB = new Integer(6);
        sorted[0] = 10;
        sorted[1] = 12;
        sorted[2] = sixA;
        sorted[3] = sixB;
        sorted[4] = 8;
        Sorting.mergeSort(sorted, comparator2);

        assertSame(sixA, sorted[0]);
        assertSame(sixB, sorted[1]);
        assertEquals(Integer.valueOf(8), sorted[2]);
        assertEquals(Integer.valueOf(10), sorted[3]);
        assertEquals(Integer.valueOf(12), sorted[4]);
    }


    @Test(timeout = TIMEOUT)
    public void testQuickLen01() {
        Sorting.quickSort(len0Unsorted, comparator2, new Random(111));
        assertArrayEquals(len0Sorted, len0Unsorted);

        Sorting.quickSort(len1Unsorted, comparator2, new Random(111));
        assertArrayEquals(len1Sorted, len1Unsorted);
    }




    @Test(timeout = TIMEOUT)
    public void testRadixLen01() {
        int[] len0rad = new int[0];
        Sorting.lsdRadixSort(len0rad);
        assertArrayEquals(new int[0], len0rad);

        int[] len1rad = new int[]{Integer.MAX_VALUE};
        Sorting.lsdRadixSort(len1rad);
        assertArrayEquals(new int[]{Integer.MAX_VALUE}, len1rad);
    }

    // Have a MIN_INT with a bunch of small 2 digit ints, pos and neg,
    // and make sure some are less than -48, then MIN_INT should be first in final arr
    @Test(timeout = TIMEOUT)
    public void testRadixOverflow() {
        int[] sorted = new int[6];
        sorted[0] = 44;
        sorted[1] = 99;
        sorted[2] = Integer.MIN_VALUE;
        sorted[3] = -63;
        sorted[4] = 8;
        sorted[5] = -47;
        Sorting.lsdRadixSort(sorted);

        assertEquals(Integer.MIN_VALUE, sorted[0]);
        assertEquals(-63, sorted[1]);
        assertEquals(-47, sorted[2]);
        assertEquals(8, sorted[3]);
        assertEquals(44, sorted[4]);
        assertEquals(99, sorted[5]);
    }

    // a test with all different lengths of ints, pos and neg, including 0, duplicates.
    @Test(timeout = TIMEOUT)
    public void testRadixDiverse() {
        int[] sorted = new int[8];
        sorted[0] = 14;
        sorted[1] = 1;
        sorted[2] = 0;
        sorted[3] = -71;
        sorted[4] = 345;
        sorted[5] = -1;
        sorted[6] = -9834;
        sorted[7] = -71;
        Sorting.lsdRadixSort(sorted);

        assertEquals(-9834, sorted[0]);
        assertEquals(-71, sorted[1]);
        assertEquals(-71, sorted[2]);
        assertEquals(-1, sorted[3]);
        assertEquals(0, sorted[4]);
        assertEquals(1, sorted[5]);
        assertEquals(14, sorted[6]);
        assertEquals(345, sorted[7]);
    }

    // test stable (can't do because input is int[] not objects)



    // ===Exception Tests===

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testInsertArrNull() {
        Sorting.insertionSort(null, comparator2);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testInsertCompNull() {
        Sorting.insertionSort(new Integer[]{1, 2, 3}, null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testCocktailArrNull() {
        Sorting.cocktailSort(null, comparator2);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testCocktailCompNull() {
        Sorting.cocktailSort(new Integer[]{1, 2, 3}, null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testMergeArrNull() {
        Sorting.mergeSort(null, comparator2);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testMergeCompNull() {
        Sorting.mergeSort(new Integer[]{1, 2, 3}, null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testLSDArrNull() {
        Sorting.lsdRadixSort(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testQuickArrNull() {
        Sorting.quickSort(null, comparator2, new Random(111));
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testQuickCompNull() {
        Sorting.quickSort(new Integer[]{1, 2, 3}, null, new Random(111));
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testQuickRandNull() {
        Sorting.quickSort(new Integer[]{1, 2, 3}, comparator2, null);
    }

    /**
     * This test checks to see if you accidentally modified or added any instance fields.
     * If you're failing this test, check your instance field declarations! This homework shouldn't have any.
     */
    @Test(timeout = TIMEOUT)
    public void testInstanceFields() {
        Class sortingClass = Sorting.class;
        Field[] fields = sortingClass.getDeclaredFields();
        Field[] validFields = new Field[0];
        assertArrayEquals(fields, validFields);
    }

    /**
     * This test checks to see if you accidentally made any helper methods public.
     * If you're failing this test, check your access specifiers! All helpers need to be private.
     */
    @Test(timeout = TIMEOUT)
    public void testPublicMethods() {
        Class sortingClass = Sorting.class;
        Method[] methods = sortingClass.getMethods();
        String[] methodStrings = new String[methods.length];
        for (int i = 0; i < methods.length; i++) {
            methodStrings[i] = methods[i].getName();
        }
        String[] validMethodStrings = {
                "mergeSort",
                "insertionSort",
                "cocktailSort",
                "lsdRadixSort",
                "quickSort",
                "wait",
                "wait",
                "wait",
                "equals",
                "toString",
                "hashCode",
                "getClass",
                "notify",
                "notifyAll",
        };
        Arrays.sort(methodStrings);
        Arrays.sort(validMethodStrings);
        assertArrayEquals(methodStrings, validMethodStrings);
    }

    /**
     * This test checks to see if you're handling sorting null arrays properly.
     * If you're failing this test, make sure your check in insertion sort is correct!
     */
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testInsertionNull() {
        Integer[] arr = {4, 2, 1, 3};
        Sorting.insertionSort(null, comparator);
        Sorting.insertionSort(arr, null);
    }

    /**
     * This test checks to see if you're handling sorting null arrays properly.
     * If you're failing this test, make sure your check in cocktail sort is correct!
     */
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testCocktailNull() {
        Integer[] arr = {4, 2, 1, 3};
        Sorting.cocktailSort(null, comparator);
        Sorting.cocktailSort(arr, null);
    }

    /**
     * This test checks to see if you're handling sorting null arrays properly.
     * If you're failing this test, make sure your check in merge sort is correct!
     */
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testMergeNull() {
        Integer[] arr = {4, 2, 1, 3};
        Sorting.mergeSort(null, comparator);
        Sorting.mergeSort(arr, null);
    }

    /**
     * This test checks to see if you're handling sorting null arrays properly.
     * If you're failing this test, make sure your check in LSD radix sort is correct!
     */
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testRadixNull() {
        Sorting.lsdRadixSort(null);
    }

    /**
     * This test checks to see if you're handling sorting null arrays properly.
     * If you're failing this test, make sure your check in quicksort is correct!
     */
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testQuickNull() {
        Random random = new Random(1332);
        Integer[] arr = {4, 2, 1, 3};
        Sorting.quickSort(null, comparator, random);
        Sorting.quickSort(arr, null, random);
        Sorting.quickSort(arr, comparator, null);
    }

    /**
     * This tests checks the overflow condition of Math.abs(Integer.MIN_VALUE)
     * Math.abs(Integer.MIN_VALUE) will return Integer.MIN_VALUE, so make sure to handle that!
     */
    @Test(timeout = TIMEOUT)
    public void testRadixOverflow4() {
        int[] arr = {Integer.MIN_VALUE, 2, 1, -50};
        int[] original = {Integer.MIN_VALUE, 2, 1, -50};
        Sorting.lsdRadixSort(arr);
        Arrays.sort(original);
        assertArrayEquals(arr, original);
    }

    @Before
    public void setup() {
        comp2 = SortingStudentTest.TeachingAssistant.getNameComparator();
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullArrayInsertionSort() {
        Sorting.insertionSort(null, comp2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullComparatorInsertionSort() {
        Sorting.insertionSort(new SortingStudentTest.TeachingAssistant[]{}, null);
    }

    @Test
    public void insertionSortDuplicates() {
        SortingStudentTest.TeachingAssistant[] curr = getArrayWithDuplicates();
        Sorting.insertionSort(curr, comp2);
        assertTrue(comp2.getCount() <= 7);
        checkDuplicatesSame(curr);
    }

    @Test
    public void insertionNoSwaps() {
        Sorting.insertionSort(getArrayWithNoSwaps(), comp2);
        assertTrue(comp2.getCount() <= 5);
        assertArrayEquals(arrayWithDuplicatesAnswer, getArrayWithNoSwaps());
    }

    @Test
    public void insertionOneElement() {
        SortingStudentTest.TeachingAssistant[] curr = new SortingStudentTest.TeachingAssistant[]{
                new SortingStudentTest.TeachingAssistant("A")};
        Sorting.insertionSort(curr, comp2);
        assertEquals(0, comp2.getCount());
    }

    @Test
    public void insertionNoElements() {
        SortingStudentTest.TeachingAssistant[] curr = new SortingStudentTest.TeachingAssistant[]{};
        Sorting.insertionSort(curr, comp2);
        assertEquals(0, comp2.getCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullArrayCocktailSort() {
        Sorting.cocktailSort(null, comp2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullComparatorCocktailSort() {
        Sorting.cocktailSort(new SortingStudentTest.TeachingAssistant[]{}, null);
    }

    @Test
    public void cocktailSortDuplicates() {
        SortingStudentTest.TeachingAssistant[] curr = getArrayWithDuplicates();
        Sorting.cocktailSort(curr, comp2);
        assertTrue(comp2.getCount() <= 20);
        checkDuplicatesSame(curr);
    }

    @Test
    public void cocktailSortWithoutDuplicates() {
        SortingStudentTest.TeachingAssistant[] curr = getArrayWithoutDuplicates();
        Sorting.cocktailSort(curr, comp2);
        assertTrue(comp2.getCount() <= 13);
        assertArrayEquals(getArrayWithoutDuplicatesAnswer(), curr);
    }

    @Test
    public void cocktailNoSwaps() {
        Sorting.cocktailSort(getArrayWithNoSwaps(), comp2);
        assertTrue(comp2.getCount() <= 5);
        assertArrayEquals(arrayWithDuplicatesAnswer, getArrayWithNoSwaps());
    }

    @Test
    public void cocktailOneElement() {
        SortingStudentTest.TeachingAssistant[] curr = new SortingStudentTest.TeachingAssistant[]{
                new SortingStudentTest.TeachingAssistant("A")};
        Sorting.cocktailSort(curr, comp2);
        assertEquals(0, comp2.getCount());
    }

    @Test
    public void cocktailNoElements() {
        SortingStudentTest.TeachingAssistant[] curr = new SortingStudentTest.TeachingAssistant[]{};
        Sorting.cocktailSort(curr, comp2);
        assertEquals(0, comp2.getCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullArrayMergeSort() {
        Sorting.mergeSort(null, comp2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullComparatorMergeSort() {
        Sorting.mergeSort(new SortingStudentTest.TeachingAssistant[]{}, null);
    }

    @Test
    public void mergeSortWithDuplicates() {
        SortingStudentTest.TeachingAssistant[] curr = getArrayWithDuplicates();
        Sorting.mergeSort(curr, comp2);
        assertTrue(comp2.getCount() <= 8);
        checkDuplicatesSame(curr);
    }

    @Test
    public void mergeSortWithoutDuplicates() {
        SortingStudentTest.TeachingAssistant[] curr = getArrayWithoutDuplicates();
        Sorting.mergeSort(curr, comp2);
        assertTrue(comp2.getCount() <= 15);
        assertArrayEquals(getArrayWithoutDuplicatesAnswer(), curr);
    }

    @Test
    public void mergeSortPreSorted() {
        SortingStudentTest.TeachingAssistant[] curr = getArrayWithoutDuplicatesAnswer();
        Sorting.mergeSort(curr, comp2);
        assertTrue(comp2.getCount() <= 13);
        assertArrayEquals(getArrayWithoutDuplicatesAnswer(), curr);
    }

    @Test
    public void lsdRadix() {
        int[] test = new int[]{123, 1244, 21, 231, 1};
        Sorting.lsdRadixSort(test);
        assertArrayEquals(new int[]{1, 21, 123, 231, 1244}, test);
    }

    @Test
    public void negativeLsdRadix() {
        int[] test = new int[]{-123, -1244, -21, -231, -1};
        Sorting.lsdRadixSort(test);
        assertArrayEquals(new int[]{-1244, -231, -123, -21, -1}, test);
    }

    @Test
    public void lsdRadixSortOnes() {
        int[] test = new int[]{123, 120, 125, 122, 120, 124, 126, 127, 129, 128};
        Sorting.lsdRadixSort(test);
        assertArrayEquals(new int[]{120, 120, 122, 123, 124, 125, 126, 127, 128, 129}, test);
    }

    @Test
    public void lsdRadixSortTens() {
        int[] test = new int[]{131, 141, 151, 111, 101, 161, 171, 191, 181, 181};
        Sorting.lsdRadixSort(test);
        assertArrayEquals(new int[]{101, 111, 131, 141, 151, 161, 171, 181, 181, 191}, test);
    }

    @Test
    public void lsdRadixSortEmpty() {
        int[] test = new int[]{};
        Sorting.lsdRadixSort(test);
        assertArrayEquals(test, new int[]{});
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullArrayQuickSort() {
        Sorting.quickSort(null, comp2, new Random());
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullComparatorQuickSort() {
        Sorting.quickSort(new SortingStudentTest.TeachingAssistant[]{}, null, new Random());
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullRandomQuickSort() {
        Sorting.quickSort(new SortingStudentTest.TeachingAssistant[]{}, comp2, null);
    }

    @Test
    public void quicksort() {
        SortingStudentTest.TeachingAssistant[] curr =
                createArray("6", "8", "4", "2", "3", "7", "9", "6", "5");
        SortingStudentTest.TeachingAssistant[] solution =
                createArray("2", "3", "4", "5", "6", "6", "7", "8", "9");
        Sorting.quickSort(curr, comp2, new Random(234));
        assertArrayEquals(solution, curr);
        assertEquals(19, comp2.getCount());
    }

    @Test
    public void quicksortEmptyArray() {
        SortingStudentTest.TeachingAssistant[] curr =
                createArray();
        SortingStudentTest.TeachingAssistant[] solution =
                createArray();
        Sorting.quickSort(curr, comp2, new Random(234));
        assertArrayEquals(solution, curr);
        assertEquals(0, comp2.getCount());
    }

    private SortingStudentTest.TeachingAssistant[] getArrayWithoutDuplicates() {
        return createArray("1", "2", "6", "5", "3", "4", "7", "8", "9");
    }

    private SortingStudentTest.TeachingAssistant[] getArrayWithoutDuplicatesAnswer() {
        return createArray("1", "2", "3", "4", "5", "6", "7", "8", "9");
    }

    private void checkDuplicatesSame(SortingStudentTest.TeachingAssistant[] candidate) {
        assertArrayEquals(arrayWithDuplicatesAnswer, candidate);
        assertSame(candidate[3].getName(), dDuplicate);
        assertSame(candidate[4].getName(), dDuplicate2);
    }

    private SortingStudentTest.TeachingAssistant[] getArrayWithDuplicates() {
        return createArray(dDuplicate, "C", "B", "A", dDuplicate2);
    }

    private SortingStudentTest.TeachingAssistant[] getArrayWithNoSwaps() {
        return createArray("A", "B", "C", dDuplicate, dDuplicate2);
    }

    private SortingStudentTest.TeachingAssistant[] createArray(String... items) {
        SortingStudentTest.TeachingAssistant[] array =
                new SortingStudentTest.TeachingAssistant[items.length];
        for (int i = 0; i < items.length; i++) {
            array[i] = new SortingStudentTest.TeachingAssistant(items[i]);
        }
        return array;
    }

    /**
     * Check that every element in both arrays are reference equal
     * @param arr1 first array
     * @param arr2 second array
     * @param <T> generic data type stored in array
     * @return true if contents of both arrays are reference equal
     */
    private <T> boolean checkArraySame(T[] arr1, T[] arr2) {
        if (arr1.length != arr2.length) {
            return false;
        } else {
            for (int i = 0; i < arr1.length; i++) {
                if (arr1[i] != arr2[i]) {
                    return false;
                }
            }
            return true;
        }
    }

    @Before
    public void setUp5() {
        comp = SortingStudentTest.TeachingAssistant.getNameComparator();

        SortingStudentTest.TeachingAssistant amanda =
                new SortingStudentTest.TeachingAssistant("Amanda");
        SortingStudentTest.TeachingAssistant billy =
                new SortingStudentTest.TeachingAssistant("Billy");
        SortingStudentTest.TeachingAssistant catherine =
                new SortingStudentTest.TeachingAssistant("Catherine");
        SortingStudentTest.TeachingAssistant doug =
                new SortingStudentTest.TeachingAssistant("Doug");
        SortingStudentTest.TeachingAssistant erica1 =
                new SortingStudentTest.TeachingAssistant("Erica");
        SortingStudentTest.TeachingAssistant erica2 =
                new SortingStudentTest.TeachingAssistant("Erica");
        SortingStudentTest.TeachingAssistant fred1 =
                new SortingStudentTest.TeachingAssistant("Fred");
        SortingStudentTest.TeachingAssistant fred2 =
                new SortingStudentTest.TeachingAssistant("Fred");
        SortingStudentTest.TeachingAssistant gayle =
                new SortingStudentTest.TeachingAssistant("Gayle");
        SortingStudentTest.TeachingAssistant henry =
                new SortingStudentTest.TeachingAssistant("Henry");

        unsortedTAs = new SortingStudentTest.TeachingAssistant[]{erica1, billy,
                gayle, amanda, erica2, fred1, catherine, fred2, henry, doug};

        sortedTAs = new SortingStudentTest.TeachingAssistant[]{amanda, billy,
                catherine, doug, erica1, erica2, fred1, fred2, gayle, henry};

        reverseSortedTAs = new SortingStudentTest.TeachingAssistant[]{henry,
                gayle, fred1, fred2, erica1, erica2, doug, catherine, billy,
                amanda};

        stableSortedTas = new SortingStudentTest.TeachingAssistant[]{amanda,
                billy, catherine, doug, erica1, erica2, fred1, fred2, gayle, henry};

        quickSortUnsorted = new SortingStudentTest.TeachingAssistant[]{amanda,
                billy, catherine, doug, erica2, erica1, fred2, fred1, gayle, henry};

        quickSortReverseSorted = new SortingStudentTest.TeachingAssistant[]{amanda,
                billy, catherine, doug, erica1, erica2, fred2, fred1, gayle, henry};

        rand = new Random(234);
    }

    @Test(timeout = TIMEOUT)
    public void testInsertionSortAverageCase() {

        Sorting.insertionSort(this.unsortedTAs, comp);
        assertTrue(this.checkArraySame(this.unsortedTAs, stableSortedTas));
        assertEquals("Number of comparisons: " + comp.getCount(), 24, comp.getCount());
    }

    @Test(timeout = TIMEOUT)
    public void testInsertionSortBestCase() {

        Sorting.insertionSort(this.sortedTAs, comp);
        assertTrue(this.checkArraySame(this.sortedTAs, stableSortedTas));
        assertEquals("Number of comparisons: " + comp.getCount(),
                9, comp.getCount());
    }

    @Test(timeout = TIMEOUT)
    public void testInsertionSortWorstCase() {

        Sorting.insertionSort(reverseSortedTAs, comp);
        assertTrue(this.checkArraySame(reverseSortedTAs, stableSortedTas));
        assertEquals("Number of comparisons: " + comp.getCount(),
                45, comp.getCount());
    }

    @Test(timeout = TIMEOUT)
    public void testCocktailSortAverageCase() {

        Sorting.cocktailSort(this.unsortedTAs, comp);
        assertTrue(this.checkArraySame(this.unsortedTAs, stableSortedTas));
        assertEquals("Number of comparisons: " + comp.getCount(),
                29, comp.getCount());

    }

    @Test(timeout = TIMEOUT)
    public void testCocktailSortBestCase() {

        Sorting.cocktailSort(this.sortedTAs, comp);
        assertTrue(this.checkArraySame(this.sortedTAs, stableSortedTas));
        assertEquals("Number of comparisons: " + comp.getCount(),
                9, comp.getCount());
    }

    @Test(timeout = TIMEOUT)
    public void testCocktailSortWorstCase() {

        Sorting.cocktailSort(reverseSortedTAs, comp);
        assertTrue(this.checkArraySame(reverseSortedTAs, stableSortedTas));
        assertEquals("Number of comparisons: " + comp.getCount(),
                45, comp.getCount());
    }

    @Test(timeout = TIMEOUT)
    public void testMergeSortFullySorted() {

        Sorting.mergeSort(sortedTAs, comp);
        assertTrue(this.checkArraySame(sortedTAs, stableSortedTas));
        assertEquals("Number of comparisons: " + comp.getCount(),
                15, comp.getCount());
    }

    @Test(timeout = TIMEOUT)
    public void testMergeSortUnsorted() {
        Sorting.mergeSort(unsortedTAs, comp);
        assertTrue(this.checkArraySame(unsortedTAs, stableSortedTas));
        assertEquals("Number of comparisons: " + comp.getCount(),
                23, comp.getCount());
    }

    @Test(timeout = TIMEOUT)
    public void testMergeSortReverseSorted() {
        Sorting.mergeSort(reverseSortedTAs, comp);
        assertTrue(this.checkArraySame(reverseSortedTAs, stableSortedTas));
        assertEquals("Number of comparisons: " + comp.getCount(),
                20, comp.getCount());
    }

    @Test(timeout = TIMEOUT)
    public void testMergeEmpty() {
        SortingStudentTest.TeachingAssistant[] empty = {};
        SortingStudentTest.TeachingAssistant[] expected = {};
        Sorting.mergeSort(empty, comp);
        assertTrue(this.checkArraySame(expected, empty));
        assertEquals(0, comp.getCount());
    }

    @Test(timeout = TIMEOUT)
    public void testMergeOne() {
        SortingStudentTest.TeachingAssistant amanda =
                new SortingStudentTest.TeachingAssistant("Amanda");
        SortingStudentTest.TeachingAssistant[] oneItem = {amanda};
        SortingStudentTest.TeachingAssistant[] expected = {amanda};
        Sorting.mergeSort(oneItem, comp);
        assertTrue(this.checkArraySame(expected, oneItem));
        assertEquals(0, comp.getCount());
    }

    @Test(timeout = TIMEOUT)
    public void testQuickSortFullySorted() {

        Sorting.quickSort(sortedTAs, comp, rand);
        assertTrue(this.checkArraySame(sortedTAs, stableSortedTas));
        assertEquals("Number of comparisons: " + comp.getCount(),
                24, comp.getCount());
    }

    @Test(timeout = TIMEOUT)
    public void testQuickSortUnsorted() {

        Sorting.quickSort(unsortedTAs, comp, rand);
        assertTrue(this.checkArraySame(unsortedTAs, quickSortUnsorted));
        assertEquals("Number of comparisons: " + comp.getCount(),
                29, comp.getCount());
    }

    @Test(timeout = TIMEOUT)
    public void testQuickSortReverseSorted() {
        Sorting.quickSort(reverseSortedTAs, comp, rand);
        assertTrue(this.checkArraySame(reverseSortedTAs, quickSortReverseSorted));
        assertEquals("Number of comparisons: " + comp.getCount(),
                29, comp.getCount());
    }

    @Test(timeout = TIMEOUT)
    public void testQuickSortEmpty() {
        SortingStudentTest.TeachingAssistant[] empty = {};
        SortingStudentTest.TeachingAssistant[] expected = {};
        Sorting.quickSort(empty, comp, rand);
        assertTrue(this.checkArraySame(expected, empty));
        assertEquals(0, comp.getCount());
    }

    @Test(timeout = TIMEOUT)
    public void testQuickSortOne() {
        SortingStudentTest.TeachingAssistant amanda =
                new SortingStudentTest.TeachingAssistant("Amanda");
        SortingStudentTest.TeachingAssistant[] oneItem = {amanda};
        SortingStudentTest.TeachingAssistant[] expected = {amanda};
        Sorting.quickSort(oneItem, comp, rand);
        assertTrue(this.checkArraySame(expected, oneItem));
        assertEquals(0, comp.getCount());
    }

    @Test(timeout = TIMEOUT)
    public void testRadixSort() {
        int[] unSorted = {91, -130, -90, 57, -8, -15, 2, 32, 5, 172, -83};
        int[] reverseSorted = {172, 91, 57, 32, 5, 2, -8, -15, -83, -90, -130};
        int[] preSorted = {-130, -90, -83, -15, -8, 2, 5, 32, 57, 91, 172};

        int[] afterSorting = {-130, -90, -83, -15, -8, 2, 5, 32, 57, 91, 172};

        Sorting.lsdRadixSort(unSorted);
        assertArrayEquals(afterSorting, unSorted);

        Sorting.lsdRadixSort(reverseSorted);
        assertArrayEquals(afterSorting, reverseSorted);

        Sorting.lsdRadixSort(preSorted);
        assertArrayEquals(afterSorting, preSorted);
    }

    @Test(timeout = TIMEOUT)
    public void testInsertionNull5() {
        try {
            Sorting.insertionSort(null, comp);
            fail();
        } catch (IllegalArgumentException e) { }

        try {
            Sorting.insertionSort(unsortedTAs, null);
            fail();
        } catch (IllegalArgumentException e) { }

        try {
            Sorting.insertionSort(null, null);
            fail();
        } catch (IllegalArgumentException e) { }
    }

    @Test(timeout = TIMEOUT)
    public void testCocktailNull5() {
        try {
            Sorting.cocktailSort(null, comp);
            fail();
        } catch (IllegalArgumentException e) { }

        try {
            Sorting.cocktailSort(unsortedTAs, null);
            fail();
        } catch (IllegalArgumentException e) { }

        try {
            Sorting.cocktailSort(null, null);
            fail();
        } catch (IllegalArgumentException e) { }
    }

    @Test(timeout = TIMEOUT)
    public void testMergeSortNull() {
        try {
            Sorting.mergeSort(null, comp);
            fail();
        } catch (IllegalArgumentException e) { }

        try {
            Sorting.mergeSort(unsortedTAs, null);
            fail();
        } catch (IllegalArgumentException e) { }

        try {
            Sorting.mergeSort(null, null);
            fail();
        } catch (IllegalArgumentException e) { }
    }

    @Test(timeout = TIMEOUT)
    public void testLSDRadixNull() {
        try {
            Sorting.lsdRadixSort(null);
            fail();
        } catch (IllegalArgumentException e) { }
    }

    @Test(timeout = TIMEOUT)
    public void testQuickSortNull() {
        try {
            Sorting.quickSort(null, comp, new Random());
            fail();
        } catch (IllegalArgumentException e) { }

        try {
            Sorting.quickSort(unsortedTAs, null, new Random());
            fail();
        } catch (IllegalArgumentException e) { }

        try {
            Sorting.quickSort(unsortedTAs, comp, null);
            fail();
        } catch (IllegalArgumentException e) { }

        try {
            Sorting.quickSort(null, null, null);
            fail();
        } catch (IllegalArgumentException e) { }

    }
}