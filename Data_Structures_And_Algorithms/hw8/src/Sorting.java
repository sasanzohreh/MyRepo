import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Sasan Zohreh
 * @version 1.0
 * @userid szohreh3
 * @GTID 903402201
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */

public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Cannot sort a null array or use a null comparator");
        }
        T temp;
        for (int i = 1; i < arr.length; i++) {
            temp = arr[i];
            int j;
            for (j = i - 1; j >= 0 && comparator.compare(arr[j], temp) > 0; j--) {
                arr[j + 1] = arr[j];
            }
            arr[j + 1] = temp;
        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Cannot sort a null array or use a null comparator");
        }
        boolean swapped = true;
        int start = 0, end = arr.length - 1;
        while (swapped) {
            //change to false since it may be true from previous iteration
            swapped = false;
            int startIndex = 0, endIndex = 0;
            //bottom to top sorting
            for (int i = start; i < end; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swapped = true;
                    endIndex = i;
                }
            }
            if (endIndex != 0) {
                end = endIndex;
            }
            //if nothing is swapped, sorting is done
            if (!swapped) {
                return;
            }
            //ensure swapped is false for next sorting stage
            swapped = false;
            //top to bottom sorting
            for (int i = end; i > start; i--) {
                if (comparator.compare(arr[i - 1], arr[i]) > 0) {
                    T temp = arr[i];
                    arr[i] = arr[i - 1];
                    arr[i - 1] = temp;
                    swapped = true;
                    startIndex = i;
                }
            }
            if (startIndex != 0) {
                start = startIndex;
            }
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Cannot sort a null array or use a null comparator");
        }
        if (arr.length > 1) {
            int midPoint = arr.length/2;
            //create 2 subarrays and fill them with values from left and right side of arr, respectively
            T[] left = (T[]) new Object[midPoint];
            T[] right = (T[]) new Object[arr.length - midPoint];
            for (int i = 0; i < left.length; i++) {
                left[i] = arr[i];
            }
            for (int i = midPoint; i < arr.length; i++) {
                right[i - midPoint] = arr[i];
            }
            //continue to subdivide arrays until each array is size 1
            mergeSort(left, comparator);
            mergeSort(right, comparator);
            int i = 0, j = 0, k;
            //begin merge process of left and right arrays
            for (k = 0; i < left.length && j < right.length; k++) {
                if (comparator.compare(left[i], right[j]) <= 0) {
                    arr[k] = left[i];
                    i++;
                } else {
                    arr[k] = right[j];
                    j++;
                }
            }
            //empty out the rest of the arrays
            while (i < left.length) {
                arr[k] = left[i];
                i++;
                k++;
            }
            while (j < right.length) {
                arr[k] = right[j];
                j++;
                k++;
            }
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Cannot sort a null array");
        }
        if (arr.length <= 1) {
            return;
        }
        int max;
        if (arr[0] == Integer.MIN_VALUE) {
            max = Integer.MAX_VALUE;
        } else {
            max = Math.abs(arr[0]);
        }
        int k = 1;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] == Integer.MIN_VALUE) {
                max = Integer.MAX_VALUE;
            } else if (max < Math.abs(arr[i])) {
                max = Math.abs(arr[i]);
            }
        }
        //use max value to find number of iterations required
        while (max / 10 != 0) {
            k++;
            max /= 10;
        }
        LinkedList<Integer>[] buckets = (LinkedList<Integer>[]) new LinkedList[19];
        for (int i = 0; i < 19; i++) {
            buckets[i] = new LinkedList<>();
        }
        int mod = 10, div = 1;
        for (int iteration = 0; iteration < k; iteration++) {
            //fill buckets
            for (int num : arr) {
                int bucket = num / div;
                if (buckets[bucket % mod + 9] == null) {
                    buckets[bucket % mod + 9] = new LinkedList<Integer>();
                }
                buckets[bucket % mod + 9].add(num);
            }
            int arrIdx = 0;
            //empty buckets
            for (int i = 0; i < buckets.length; i++) {
                if (buckets[i] != null) {
                    for (int num : buckets[i]) {
                        arr[arrIdx++] = num;
                    }
                    buckets[i].clear();
                }
            }
            div *= 10;
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("Cannot sort a null array, use a null comparator, or use a null pivot");
        }
        if (arr.length <= 1) {
            return;
        }
        quickSortHelper(arr, comparator, 0, arr.length - 1, rand);
    }

    /**
     * Recursive helper method for quick sort
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param low        the start index for comparing in a section of the array
     * @param high       the end index for comparing in a section of the array
     * @param rand       the Random object used to select pivots
     */
    private static <T> void quickSortHelper(T[] arr, Comparator<T> comparator, int low, int high, Random rand) {
        if (high - low < 1) {
            return;
        }
        //get random pivot and swap with first element
        int randPivot = rand.nextInt(high - low + 1) + low;
        T temp = arr[randPivot];
        arr[randPivot] = arr[low];
        arr[low] = temp;
        int i = low + 1, j = high;
        //while i and j haven't crossed
        while (i <= j) {
            while (i <= j && comparator.compare(arr[i], arr[low]) <= 0) {
                i++;
            }
            while (i <= j && comparator.compare(arr[j], arr[low]) >= 0) {
                j--;
            }
            if (i <= j) {
                temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }
        }
        temp = arr[j];
        arr[j] = arr[low];
        arr[low] = temp;
        quickSortHelper(arr, comparator, low, j - 1, rand);
        quickSortHelper(arr, comparator, j + 1, high, rand);
    }
}
