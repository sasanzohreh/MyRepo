import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
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
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot convert a null list into heap");
        }
        backingArray = (T[]) new Comparable[2 * data.size() + 1];
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == null) {
                throw new IllegalArgumentException("Cannot add null elements to heap");
            }
            backingArray[i + 1] = data.get(i);
            size++;
        }
        this.buildHeap();
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null elements to heap");
        }
        if (backingArray.length - 1 == size) {
            T[] newArr = (T[]) new Comparable[backingArray.length * 2];
            for (int i = 0; i < size; i++) {
                newArr[i + 1] = backingArray[i + 1];
            }
            backingArray = newArr;
        }
        backingArray[size + 1] = data;
        size++;
        this.upHeap(size);
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove from an empty heap");
        }
        T temp = this.getMin();
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;
        downHeap(1);
        return temp;
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot retrieve element from empty heap");
        }
        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Helper method for constructor
     * Constructs a MinHeap from an unsorted, unordered array
     * Conducts series of down heaps to sort by minimum value
     */
    private void buildHeap() {
        int startIndex = size / 2;

        for (int i = startIndex; i > 0; i--) {
            this.downHeap(i);
        }
    }

    /**
     * Recursive helper method for buildHeap and remove
     * Swaps parent value with its smallest child value, then if a swap occurs, checks the children
     * of swapped child
     *
     * @param index the parent index which will be used to check against its children's values
     */
    private void downHeap(int index) {
        int min = index;
        int left = 2 * index;
        int right = 2 * index + 1;

        if (right < size + 1 && backingArray[right].compareTo(backingArray[min]) < 0) {
            min = right;
        }
        if (left < size + 1 && backingArray[left].compareTo(backingArray[min]) < 0) {
            min = left;
        }
        if (index != min) {
            T temp = backingArray[index];
            backingArray[index] = backingArray[min];
            backingArray[min] = temp;

            this.downHeap(min);
        }
    }

    /**
     * Recursive helper method for add
     * If child is less than parent, swap values. If swapped, continue to move up the heap
     *
     * @param index the child index to check if it's less than its parent's value
     */
    private void upHeap(int index) {
        if (index != 1 && backingArray[index].compareTo(backingArray[index / 2]) < 0) {
            T temp = backingArray[index];
            backingArray[index] = backingArray[index / 2];
            backingArray[index / 2] = temp;
            this.upHeap(index / 2);
        }
    }
}
