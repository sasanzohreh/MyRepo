import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
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
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index. Don't forget to consider whether
     * traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        //exceptions
        if (index < 0) {
            throw new IndexOutOfBoundsException("Cannot add to a list at negative index");
        } else if (index > size) {
            throw new IndexOutOfBoundsException("Cannot add to an index above list size");
        } else if (data == null) {
            throw new IllegalArgumentException("Cannot add null data to list");
        }


        if (index == 0) {
            this.addToFront(data);
        } else if (index == size) {
            this.addToBack(data);
        } else {
            //if index is closer to head, approach from front
            if (index < size / 2) {
                DoublyLinkedListNode<T> curr = head;
                //iterate through until desired index is reached
                while (index != 0) {
                    index--;
                    curr = curr.getNext();
                    if (index == 0) {
                        DoublyLinkedListNode<T> temp = new DoublyLinkedListNode<>(data, curr.getPrevious(), curr);
                        curr.getPrevious().setNext(temp);
                        curr.setPrevious(temp);
                    }
                }
            } else {
                //if index is closer to tail, approach from back
                DoublyLinkedListNode<T> curr = tail;
                //iterate through until desired index is reached
                while (index != size) {
                    index++;
                    curr = curr.getPrevious();
                    if (index == size) {
                        DoublyLinkedListNode<T> temp = new DoublyLinkedListNode<>(data, curr, curr.getNext());
                        curr.getNext().setPrevious(temp);
                        curr.setNext(temp);
                    }
                }
            }
            size++;
        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        //exceptions
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data to list");
        }

        if (size == 0) {
            head = new DoublyLinkedListNode<>(data, null, null);
            tail = head;
        } else {
            DoublyLinkedListNode<T> temp = new DoublyLinkedListNode<>(data, null, head);
            head.setPrevious(temp);
            head = temp;
        }
        size++;
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        //exceptions
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data to list");
        }

        if (size == 0) {
            head = new DoublyLinkedListNode<>(data, null, null);
            tail = head;
        } else {
            DoublyLinkedListNode<T> temp = new DoublyLinkedListNode<>(data, tail, null);
            tail.setNext(temp);
            tail = temp;
        }
        size++;
    }

    /**
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        //exceptions
        if (index < 0) {
            throw new IndexOutOfBoundsException("Cannot remove from a list at negative index");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("Cannot remove from an index above or equal to list size");
        }

        if (index == 0) {
            return this.removeFromFront();
        } else if (index == size - 1) {
            return this.removeFromBack();
        } else {
            //if index is closer to head, iterate starting at the front of the list
            if (index < size / 2) {
                DoublyLinkedListNode<T> curr = head;
                //iterate through list until index is reached
                while (index != 0) {
                    index--;
                    curr = curr.getNext();
                    if (index == 0) {
                        T data = curr.getData();
                        curr.getPrevious().setNext(curr.getNext());
                        curr.getNext().setPrevious(curr.getPrevious());
                        size--;
                        return data;
                    }
                }
            } else {
                //if index is closer to tail, iterate starting at the back of the list
                DoublyLinkedListNode<T> curr = tail;
                //iterate through list until index is reached
                for (int i = size - 2; i >= index; i--) {
                    curr = curr.getPrevious();
                    if (index == i) {
                        T data = curr.getData();
                        curr.getPrevious().setNext(curr.getNext());
                        curr.getNext().setPrevious(curr.getPrevious());
                        size--;
                        return data;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
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
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
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
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        //exceptions
        if (index < 0) {
            throw new IndexOutOfBoundsException("Cannot retrieve from a list at negative index");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("Cannot retrieve from an index above or equal to list size");
        }

        if (index == 0) {
            return head.getData();
        } else if (index == size - 1) {
            return tail.getData();
        } else if (index < size / 2) {
            //if index is closer to head, iterate starting at front of list
            DoublyLinkedListNode<T> curr = head;
            while (index != 0) {
                index--;
                curr = curr.getNext();
                if (index == 0) {
                    return curr.getData();
                }
            }
        } else {
            //if index is closer to tail, iterate starting at back of list
            DoublyLinkedListNode<T> curr = tail;
            index++;
            while (index != size) {
                index++;
                curr = curr.getPrevious();
                if (index == size) {
                    return curr.getData();
                }
            }
        }
        return null;
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        //exceptions
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove a null element");
        }

        DoublyLinkedListNode<T> curr = tail;
        T tempData = null;
        //iterate through back of list until first occurrence of data is found, then remove it
        for (int i = size - 1; i >= 0; i--) {
            if (curr.getData().equals(data)) {
                //specific case for removing head
                if (i == 0) {
                    tempData = head.getData();
                    if (size == 1) {
                        head = null;
                        tail = null;
                    } else {
                        head = head.getNext();
                        head.setPrevious(null);
                    }
                    size--;
                    return tempData;
                    //specific case for removing tail
                } else if (i == size - 1) {
                    tempData = tail.getData();
                    if (size == 1) {
                        head = null;
                        tail = null;
                    } else {
                        tail = tail.getPrevious();
                        tail.setNext(null);
                    }
                    size--;
                    return tempData;
                    //general case
                } else {
                    tempData = curr.getData();
                    curr.getPrevious().setNext(curr.getNext());
                    curr.getNext().setPrevious(curr.getPrevious());
                    size--;
                    return tempData;
                }
            } else {
                curr = curr.getPrevious();
            }
        }
        throw new NoSuchElementException("Cannot remove an element not found in list");
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        Object[] doublyLLArray = new Object[size];
        for (int i = 0; i < size; i++) {
            doublyLLArray[i] = this.get(i);
        }
        return doublyLLArray;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
