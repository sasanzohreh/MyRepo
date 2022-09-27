import java.util.NoSuchElementException;

/**
 * Your implementation of a LinkedDeque.
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
public class LinkedDeque<T> {

    // Do not add new instance variables or modify existing ones.
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the front of the deque.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addFirst(T data) {
        //exceptions
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data to list");
        }

        if (size == 0) {
            head = new LinkedNode<>(data, null, null);
            tail = head;
        } else {
            LinkedNode<T> temp = new LinkedNode<>(data, null, head);
            head.setPrevious(temp);
            head = temp;
        }
        size++;
    }

    /**
     * Adds the element to the back of the deque.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addLast(T data) {
        //exceptions
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data to list");
        }

        if (size == 0) {
            head = new LinkedNode<>(data, null, null);
            tail = head;
        } else {
            LinkedNode<T> temp = new LinkedNode<>(data, tail, null);
            tail.setNext(temp);
            tail = temp;
        }
        size++;
    }

    /**
     * Removes and returns the first element of the deque.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeFirst() {
        //exceptions
        if (size == 0) {
            throw new NoSuchElementException("Cannot remove from empty list");
        }

        T data;
        if (size == 1) {
            data = head.getData();
            head = null;
            tail = null;
        } else {
            data = head.getData();
            head = head.getNext();
            head.setPrevious(null);
        }
        size--;
        return data;
    }

    /**
     * Removes and returns the last element of the deque.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeLast() {
        //exceptions
        if (size == 0) {
            throw new NoSuchElementException("Cannot remove from empty list");
        }

        T data;
        if (size == 1) {
            data = head.getData();
            head = null;
            tail = null;
        } else {
            data = tail.getData();
            tail = tail.getPrevious();
            tail.setNext(null);
        }
        size--;
        return data;
    }

    /**
     * Returns the first data of the deque without removing it.
     *
     * Must be O(1).
     *
     * @return the data located at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getFirst() {
        //exceptions
        if (size == 0) {
            throw new NoSuchElementException("Cannot retrieve an item from an empty list");
        }

        return head.getData();
    }

    /**
     * Returns the last data of the deque without removing it.
     *
     * Must be O(1).
     *
     * @return the data located at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getLast() {
        //exceptions
        if (size == 0) {
            throw new NoSuchElementException("Cannot retrieve an item from an empty list");
        }

        return tail.getData();
    }

    /**
     * Returns the head node of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return node at the head of the deque
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY THIS METHOD!
        return head;
    }

    /**
     * Returns the tail node of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return node at the head of the deque
     */
    public LinkedNode<T> getTail() {
        // DO NOT MODIFY THIS METHOD!
        return tail;
    }

    /**
     * Returns the size of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the deque
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
