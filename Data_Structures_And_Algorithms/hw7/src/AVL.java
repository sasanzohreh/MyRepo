import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL.
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
public class AVL<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        size = 0;
        if (data == null) {
            throw new IllegalArgumentException("Cannot turn a null collection into AVL");
        }
        //call add method on each element in data and ensure it contains no null elements
        for (T elem : data) {
            if (elem == null) {
                throw new IllegalArgumentException("Cannot use AVL with null data elements");
            }
            this.add(elem);
        }
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data to an AVL");
        }
        root = this.addHelper(root, new AVLNode<T>(data));
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     *    simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     *    replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     *    replace the data, NOT successor. As a reminder, rotations can occur
     *    after removing the predecessor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove a null element from AVL");
        }
        AVLNode<T> dummy = new AVLNode<>(null);
        root = removeHelper(root, data, dummy);
        return dummy.getData();
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot retrieve null data from a AVL");
        }

        AVLNode<T> dummy = getAndContainsHelper(root, data);

        if (dummy == null) {
            throw new NoSuchElementException("Element not found in AVL");
        }
        return dummy.getData();
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("AVL cannot contain null element");
        }

        AVLNode<T> dummy = getAndContainsHelper(root, data);

        if (dummy != null) {
            return true;
        }
        return false;
    }

    /**
     * Returns the height of the root of the tree. Do NOT compute the height
     * recursively. This method should be O(1).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return (root != null) ? root.getHeight() : -1;
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Find a path of letters in the tree that spell out a particular word,
     * if the path exists.
     *
     * Ex: Given the following AVL
     *
     *                   g
     *                 /   \
     *                e     i
     *               / \   / \
     *              b   f h   n
     *             / \         \
     *            a   c         u
     *
     * wordSearch([b, e, g, i, n]) returns the list [b, e, g, i, n],
     * where each letter in the returned list is from the tree and not from
     * the word array.
     *
     * wordSearch([h, i]) returns the list [h, i], where each letter in the
     * returned list is from the tree and not from the word array.
     *
     * wordSearch([a]) returns the list [a].
     *
     * wordSearch([]) returns an empty list [].
     *
     * wordSearch([h, u, g, e]) throws NoSuchElementException. Although all
     * 4 letters exist in the tree, there is no path that spells 'huge'.
     * The closest we can get is 'hige'.
     *
     * To do this, you must first find the deepest common ancestor of the
     * first and last letter in the word. Then traverse to the first letter
     * while adding letters on the path to the list while preserving the order
     * they appear in the word (consider adding to the front of the list).
     * Finally, traverse to the last letter while adding its ancestor letters to
     * the back of the list. Please note that there is no relationship between
     * the first and last letters, in that they may not belong to the same
     * branch. You will most likely have to split off to traverse the tree when
     * searching for the first and last letters.
     *
     * You may only use 1 list instance to complete this method. Think about
     * what type of list to use since you may have to add to the front and
     * back of the list.
     *
     * You will only need to traverse to the deepest common ancestor once.
     * From that node, go to the first and last letter of the word in one
     * traversal each. Failure to do so will result in a efficiency penalty.
     * Validating the path against the word array efficiently after traversing
     * the tree will NOT result in an efficiency penalty.
     *
     * If there exists a path between the first and last letters of the word,
     * there will only be 1 valid path.
     *
     * You may assume that the word will not contain duplicate letters.
     *
     * WARNING: Do not return letters from the passed-in word array!
     * If a path exists, the letters should be retrieved from the tree.
     * Returning any letter from the word array will result in a penalty!
     *
     * @param word array of T, where each element represents a letter in the
     * word (in order).
     * @return list containing the path of letters in the tree that spell out
     * the word, if such a path exists. Order matters! The ordering of the
     * letters in the returned list should match that of the word array.
     * @throws java.lang.IllegalArgumentException if the word array is null
     * @throws java.util.NoSuchElementException if the path is not in the tree
     */
    public List<T> wordSearch(T[] word) {
        if (word == null) {
            throw new IllegalArgumentException("Cannot search for a null array");
        }

        List<T> list = new LinkedList<>();

        if (word.length == 0) {
            return list;
        }

        T first = word[0];
        T last = word[word.length - 1];
        AVLNode<T> dca = findDCA(root, first, last);

        if (dca == null) {
            throw new NoSuchElementException("Path was not found in tree");
        }

        traverseLeft(dca, first, list);
        list.add(dca.getData());
        traverseRight(dca, last, list);

        if (list.size() != word.length) {
            throw new NoSuchElementException("Path was not found in tree");
        }

        //check that the list matches the array, if not then path doesn't exist
        for (int i = 0; i < word.length; i++) {
            if (word[i].compareTo(list.get(i)) != 0) {
                throw new NoSuchElementException("Path was not found in tree");
            }
        }
        return list;
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Private recursive helper method for add
     *
     * @param currPar current parent node for searching
     * @param newNode data to be added
     * @return new root with data added
     */
    private AVLNode<T> addHelper(AVLNode<T> currPar, AVLNode<T> newNode) {
        //when empty spot is found, return the node to add
        if (currPar == null) {
            size++;
            return newNode;
        } else if (newNode.getData().compareTo(currPar.getData()) > 0) {
            currPar.setRight(addHelper(currPar.getRight(), newNode));
        } else if (newNode.getData().compareTo(currPar.getData()) < 0) {
            currPar.setLeft(addHelper(currPar.getLeft(), newNode));
        }
        updateHBF(currPar);
        currPar = balance(currPar);
        return currPar;
    }

    /**
     * Private recursive helper method for remove
     *
     * @param currPar current parent node for searching
     * @param data data to be removed
     * @param dummy node to store data being removed
     * @return new root if data was removed
     * @throws NoSuchElementException when data to be removed isn't in the AVL
     */
    private AVLNode<T> removeHelper(AVLNode<T> currPar, T data, AVLNode<T> dummy) {
        if (currPar == null) {
            throw new NoSuchElementException("Element could not be removed because it doesn't exist in AVL");
        } else if (data.compareTo(currPar.getData()) < 0) {
            currPar.setLeft(removeHelper(currPar.getLeft(), data, dummy));
        } else if (data.compareTo(currPar.getData()) > 0) {
            currPar.setRight(removeHelper(currPar.getRight(), data, dummy));
        }  else {
            //store removed data in dummy
            AVLNode<T> temp;
            dummy.setData(currPar.getData());
            size--;
            //case of 0 children
            if (currPar.getLeft() == null && currPar.getRight() == null) {
                return null;
            } else if (currPar.getLeft() == null) {
                //case of 1 child: if left child is missing, remove right child
                return currPar.getRight();
            } else if (currPar.getRight() == null) {
                //case of 1 child: if right child is missing, remove left child
                return currPar.getLeft();
            } else {
                //dummy node stores predecessor data
                AVLNode<T> dummy2 = new AVLNode<>(null);
                currPar.setLeft(removePredecessor(currPar.getLeft(), dummy2));
                currPar.setData(dummy2.getData());
            }
        }
        updateHBF(currPar);
        currPar = balance(currPar);
        return currPar;
    }

    /**
     * Private recursive helper method for removeHelper, finds predecessor
     *
     * @param currPar current parent node for searching
     * @param dummy node to store data being removed
     * @return predecessor node
     */
    private AVLNode<T> removePredecessor(AVLNode<T> currPar, AVLNode<T> dummy) {
        if (currPar.getRight() == null) {
            //store data of predecessor in dummy
            dummy.setData(currPar.getData());
            return currPar.getLeft();
        } else {
            //until there are no more left children, keep looking for predecessor
            currPar.setRight(removePredecessor(currPar.getRight(), dummy));
        }
        updateHBF(currPar);
        currPar = balance(currPar);
        return currPar;
    }

    /**
     * Private recursive helper method for rotations needed by add and remove
     * Performs a left rotation
     *
     * @param a node acting as root for rotation
     * @return new node acting as root for subtree
     */
    private AVLNode<T> rotateLeft(AVLNode<T> a) {
        AVLNode<T> b = a.getRight();
        a.setRight(b.getLeft());
        b.setLeft(a);
        updateHBF(a);
        updateHBF(b);
        return b;
    }

    /**
     * Private recursive helper method for rotations needed by add and remove
     * Performs a right rotation
     *
     * @param a node acting as root of subtree for rotation
     * @return new node acting as root for subtree
     */
    private AVLNode<T> rotateRight(AVLNode<T> a) {
        AVLNode<T> b = a.getLeft();
        a.setLeft(b.getRight());
        b.setRight(a);
        updateHBF(a);
        updateHBF(b);
        return b;
    }

    /**
     * Private helper method to update the balance factor and height of nodes
     * after a rotation is performed, data is added, or data is removed
     *
     * @param curr node to update the height and balance factor of
     */
    private void updateHBF(AVLNode<T> curr) {
        curr.setBalanceFactor(easyH(curr.getLeft()) - easyH(curr.getRight()));
        curr.setHeight(1 + Math.max(easyH(curr.getLeft()), easyH(curr.getRight())));
    }

    /**
     * Private recursive helper method to find the height of a node
     *
     * @param curr node to find the height of
     * @return height of the passed in node
     */
    private int easyH(AVLNode<T> curr) {
        if (curr == null) {
            return -1;
        } else {
            return curr.getHeight();
        }
    }

    /**
     * Private recursive helper method that performs rotations needed by add and remove
     * Performs single rotations or double rotations when necessary
     *
     * @param currPar node acting as root of subtree for rotation
     * @return new node acting as root for subtree
     */
    private AVLNode<T> balance(AVLNode<T> currPar) {
        //tree is left heavy
        if (currPar.getBalanceFactor() == 2) {
            //requires double rotation
            if (currPar.getLeft().getBalanceFactor() == -1) {
                currPar.setLeft(this.rotateLeft(currPar.getLeft()));
            }
            currPar = this.rotateRight(currPar);
            //tree is right heavy
        } else if (currPar.getBalanceFactor() == -2) {
            //requires double rotation
            if (currPar.getRight().getBalanceFactor() == 1) {
                currPar.setRight(this.rotateRight(currPar.getRight()));
            }
            currPar = this.rotateLeft(currPar);
        }
        return currPar;
    }

    /**
     * Private recursive helper method for get and contains
     *
     * @param currPar current parent node for searching
     * @param data data to be found
     * @return node with data element, null otherwise
     */
    private AVLNode<T> getAndContainsHelper(AVLNode<T> currPar, T data) {
        if (currPar == null) {
            return null;
        } else if (data.compareTo(currPar.getData()) > 0) {
            return getAndContainsHelper(currPar.getRight(), data);
        } else if (data.compareTo(currPar.getData()) < 0) {
            return getAndContainsHelper(currPar.getLeft(), data);
        } else {
            return currPar;
        }
    }

    /**
     * Private recursive helper method for word search that finds the deepest common
     * ancestor (DCA) for two values if they exist in the AVL
     *
     * @param curr node acting as root of subtree to see if it's the DCA
     * @param first first value of array for word search
     * @param last last value of array for word search
     * @return DCA if one exists, null otherwise
     */
    private AVLNode<T> findDCA(AVLNode<T> curr, T first, T last) {
        if (curr == null) {
            return null;
        }
        AVLNode<T> wordIsDCA = null;

        //check if either value is the DCA
        if (curr.getData().compareTo(first) == 0) {
            wordIsDCA = curr;
        } else if (curr.getData().compareTo(last) == 0) {
            wordIsDCA = curr;
        }
        if (wordIsDCA != null) {
            return wordIsDCA;
        }

        //search for the DCA in each subtree
        AVLNode<T> leftSearch = findDCA(curr.getLeft(), first, last);
        AVLNode<T> rightSearch = findDCA(curr.getRight(), first, last);

        if (leftSearch != null && rightSearch != null) {
            return curr;
        }
        if (leftSearch != null) {
            return leftSearch;
        }
        return rightSearch;
    }

    /**
     * Private recursive helper method for word search that traverses
     * down the left side of the DCA and adds values in between the DCA (exclusive)
     * and first value (inclusive)
     *
     * @param curr node acting as root of subtree to traverse down
     * @param first first value of array for word search
     * @param list list to add values to
     */
    private void traverseLeft(AVLNode<T> curr, T first, List<T> list) {
        //if data is larger than first traverse left
        if (curr.getData().compareTo(first) > 0) {
            if (curr.getLeft() != null) {
                list.add(0, curr.getLeft().getData());
                if (curr.getLeft().getData().compareTo(first) != 0) {
                    this.traverseLeft(curr.getLeft(), first, list);
                }
            }
            //if data is smaller than first traverse right
        } else if (curr.getData().compareTo(first) < 0) {
            if (curr.getRight() != null) {
                list.add(0, curr.getRight().getData());
                if (curr.getRight().getData().compareTo(first) != 0) {
                    this.traverseLeft(curr.getRight(), first, list);
                }
            }
        }
    }

    /**
     * Private recursive helper method for word search that traverses
     * down the right side of the DCA and adds values in between the DCA (exclusive)
     * and last value (inclusive)
     *
     * @param curr node acting as root of subtree to traverse down
     * @param last last value of array for word search
     * @param list list to add values to
     */
    private void traverseRight(AVLNode<T> curr, T last, List<T> list) {
        //if data is larger than last traverse left
        if (curr.getData().compareTo(last) > 0) {
            if (curr.getLeft() != null) {
                list.add(curr.getLeft().getData());
                if (curr.getLeft().getData().compareTo(last) != 0) {
                    this.traverseRight(curr.getLeft(), last, list);
                }
            }
            //if data is smaller than first traverse right
        } else if (curr.getData().compareTo(last) < 0) {
            if (curr.getRight() != null) {
                list.add(curr.getRight().getData());
                if (curr.getRight().getData().compareTo(last) != 0) {
                    this.traverseRight(curr.getRight(), last, list);
                }
            }
        }
    }
}