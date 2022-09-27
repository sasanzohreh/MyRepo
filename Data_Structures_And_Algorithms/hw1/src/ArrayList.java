//imports
import java.util.NoSuchElementException;
/**
 * Your implementation of an ArrayList.
 *
 * @author Sasan Zohreh
 * @version 1.0
 * @userid szohreh3
 * @GTID 903402201
 * <p>
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 * <p>
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class ArrayList<T> {

    /**
     * The initial capacity of the ArrayList.
     * <p>
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 9;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayList.
     * <p>
     * Java does not allow for regular generic array creation, so you will have
     * to cast an Object[] to a T[] to get the generic typing.
     */
    public ArrayList() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
    }

    /**
     * Adds the element to the specified index.
     * <p>
     * Remember that this add may require elements to be shifted.
     * <p>
     * Must be amortized O(1) for index size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        //exceptions
        if (index < 0) {
            throw new IndexOutOfBoundsException("Cannot add an element at negative index");
        } else if (index > size) {
            throw new IndexOutOfBoundsException("Cannot add elements past list size, list cannot have null spaces");
        } else if (data == null) {
            throw new IllegalArgumentException("Cannot insert null element into list");
        }

        //if list is full copy values over to new array double the size of the old array
        if (size == backingArray.length) {
            T[] temp = (T[]) new Object[size * 2];
            for (int i = 0; i < backingArray.length; i++) {
                temp[i] = backingArray[i];
            }
            backingArray = temp;
        }

        //move all elements at index and above to the right
        for (int i = size; i > index; i--) {
            backingArray[i] = backingArray[i - 1];
        }
        backingArray[index] = data;
        size++;
    }

    /**
     * Adds the element to the front of the list.
     * <p>
     * Remember that this add may require elements to be shifted.
     * <p>
     * Must be O(n).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        //exceptions
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null element into list");
        }

        //if list is full copy values over to new array double the size of the old array
        if (size == backingArray.length) {
            T[] temp = (T[]) new Object[size * 2];
            for (int i = 0; i < backingArray.length; i++) {
                temp[i] = backingArray[i];
            }
            backingArray = temp;
        }

        //move all elements to the right
        for (int i = size; i > 0; i--) {
            backingArray[i] = backingArray[i - 1];
        }
        backingArray[0] = data;
        size++;
    }

    /**
     * Adds the element to the back of the list.
     * <p>
     * Must be amortized O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        //exceptions
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null element into list");
        }

        //if list is full copy values over to new array double the size of the old array
        if (size == backingArray.length) {
            T[] temp = (T[]) new Object[size * 2];
            for (int i = 0; i < backingArray.length; i++) {
                temp[i] = backingArray[i];
            }
            backingArray = temp;
        }

        backingArray[size] = data;
        size++;
    }

    /**
     * Removes and returns the element at the specified index.
     * <p>
     * Remember that this remove may require elements to be shifted.
     * <p>
     * Must be O(1) for index size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        //exceptions
        if (index < 0) {
            throw new IndexOutOfBoundsException("Cannot remove a negative element from a negative index");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("Cannot remove elements greater than list size, they are null");
        }

        T removedElem = backingArray[index];
        //move elements right of the index to the left
        for (int i = index; i < size - 1; i++) {
            backingArray[i] = backingArray[i + 1];
        }
        backingArray[size - 1] = null;
        size--;
        return removedElem;
    }

    /**
     * Removes and returns the first element of the list.
     * <p>
     * Remember that this remove may require elements to be shifted.
     * <p>
     * Must be O(n).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        //exceptions
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove elements from an empty list");
        }

        T removedElem = backingArray[0];
        //move all elements to the left
        for (int i = 0; i < size - 1; i++) {
            backingArray[i] = backingArray[i + 1];
        }
        backingArray[size - 1] = null;
        size--;
        return removedElem;
    }

    /**
     * Removes and returns the last element of the list.
     * <p>
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        //exceptions
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove elements from an empty list");
        }

        T removedElem = backingArray[size - 1];
        backingArray[size - 1] = null;
        size--;
        return removedElem;
    }

    /**
     * Returns the element at the specified index.
     * <p>
     * Must be O(1).
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        //exceptions
        if (index < 0) {
            throw new IndexOutOfBoundsException("Cannot retrieve an element from negative index");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("Cannot retrieve an element larger than the list since it is null");
        }
        return backingArray[index];
    }

    /**
     * Returns whether or not the list is empty.
     * <p>
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list.
     * <p>
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     * <p>
     * Must be O(1).
     */
    public void clear() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the list.
     * <p>
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
     * Returns the size of the list.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
