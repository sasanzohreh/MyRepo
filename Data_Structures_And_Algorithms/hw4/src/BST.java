import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Collection;
import java.util.List;
import java.util.LinkedList;

/**
 * Your implementation of a BST.
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
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;
    //private BSTNode<T> currPar;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        size = 0;
        if (data == null) {
            throw new IllegalArgumentException("Cannot turn a null collection into BST");
        }
        //call add method on each element in data and ensure it contains no null elements
        for (T elem : data) {
            if (elem == null) {
                throw new IllegalArgumentException("Cannot use BST with null data elements");
            }
            this.add(elem);
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data to a BST");
        }
        root = this.addHelper(root, new BSTNode(data));
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove a null element from BST");
        }
        BSTNode<T> dummy = new BSTNode<>(null);
        root = removeHelper(root, data, dummy);
        return dummy.getData();
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot retrieve null data from a BST");
        }

        BSTNode<T> dummy = getAndContainsHelper(root, data);

        if (dummy == null) {
            throw new NoSuchElementException("Element not found in BST");
        }
        return dummy.getData();
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("BST cannot contain null element");
        }

        BSTNode<T> dummy = getAndContainsHelper(root, data);

        if (dummy != null) {
            return true;
        }
        return false;
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> list = new ArrayList<>();
        return preorderHelper(root, list);
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> list = new ArrayList<>();
        return inorderHelper(root, list);
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> list = new ArrayList<>();
        return postorderHelper(root, list);
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode<T>> queue = new LinkedList<>();
        List<T> list = new LinkedList<>();
        BSTNode<T> node;
        queue.add(root);

        while (!queue.isEmpty()) {
            node = queue.peek();
            if (node != null) {
                list.add(node.getData());
                queue.remove();

                //add children nodes, if any, to queue
                if (node.getLeft() != null) {
                    queue.add(node.getLeft());
                }
                if (node.getRight() != null) {
                    queue.add(node.getRight());
                }
            }
        }
        return list;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightHelper(root);
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds and retrieves the k-largest elements from the BST in sorted order,
     * least to greatest.
     *
     * This must be done recursively.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * EXAMPLE: Given the BST below composed of Integers:
     *
     *                50
     *              /    \
     *            25      75
     *           /  \
     *          12   37
     *         /  \    \
     *        10  15    40
     *           /
     *          13
     *
     * kLargest(5) should return the list [25, 37, 40, 50, 75].
     * kLargest(3) should return the list [40, 50, 75].
     *
     * Should have a running time of O(log(n) + k) for a balanced tree and a
     * worst case of O(n + k).
     *
     * @param k the number of largest elements to return
     * @return sorted list consisting of the k largest elements
     * @throws java.lang.IllegalArgumentException if k > n, the number of data
     *                                            in the BST
     */
    public List<T> kLargest(int k) {
        if (k > size) {
            throw new IllegalArgumentException("Cannot return more elements than BST size");
        } else if (k < 0) {
            throw new IllegalArgumentException("Cannot retrieve 0 or negative elements from BST");
        }
        List<T> list = new LinkedList<>();
        return kLargestHelper(root, list, k);
    }


    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
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
    private BSTNode<T> addHelper(BSTNode<T> currPar, BSTNode<T> newNode) {
        //when empty spot is found, return the node to add
        if (currPar == null) {
            size++;
            return newNode;
        } else if (newNode.getData().compareTo(currPar.getData()) > 0) {
            currPar.setRight(addHelper(currPar.getRight(), newNode));
        } else if (newNode.getData().compareTo(currPar.getData()) < 0) {
            currPar.setLeft(addHelper(currPar.getLeft(), newNode));
        }
        return currPar;
    }

    /**
     * Private recursive helper method for remove
     *
     * @param currPar current parent node for searching
     * @param data data to be removed
     * @param dummy node to store data being removed
     * @return new root if data was removed
     * @throws NoSuchElementException when data to be removed isn't in the BST
     */
    private BSTNode<T> removeHelper(BSTNode<T> currPar, T data, BSTNode<T> dummy) {
        if (currPar == null) {
            throw new NoSuchElementException("Element could not be removed because it doesn't exist in BST");
        } else if (data.compareTo(currPar.getData()) < 0) {
            currPar.setLeft(removeHelper(currPar.getLeft(), data, dummy));
        } else if (data.compareTo(currPar.getData()) > 0) {
            currPar.setRight(removeHelper(currPar.getRight(), data, dummy));
        }  else {
            //store removed data in dummy
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
                //dummy node stores successor data
                BSTNode<T> dummy2 = new BSTNode<>(null);
                currPar.setRight(removeSuccessor(currPar.getRight(), dummy2));
                currPar.setData(dummy2.getData());
            }
        }
        updateHBF(currPar);
        currPar = balance(currPar);
        return currPar;
    }

    /**
     * Private recursive helper method for removeHelper, finds successor
     *
     * @param currPar current parent node for searching
     * @param dummy node to store data being removed
     * @return successor node
     */
    private BSTNode<T> removeSuccessor(BSTNode<T> currPar, BSTNode<T> dummy) {
        if (currPar.getLeft() == null) {
            //store data of successor in dummy
            dummy.setData(currPar.getData());
            return currPar.getRight();
        } else {
            //until there are no more left children, keep looking for successor
            currPar.setLeft(removeSuccessor(currPar.getLeft(), dummy));
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
    private BSTNode<T> getAndContainsHelper(BSTNode<T> currPar, T data) {
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
     * Private recursive helper method for preorder
     *
     * @param currPar current parent node for searching
     * @param list empty list to store data
     * @return list with elements sorted by preorder
     */
    private List<T> preorderHelper(BSTNode<T> currPar, List<T> list) {
        if (currPar != null) {
            list.add(currPar.getData());
            preorderHelper(currPar.getLeft(), list);
            preorderHelper(currPar.getRight(), list);
        }
        return list;
    }

    /**
     * Private recursive helper method for inorder
     *
     * @param currPar current parent node for searching
     * @param list empty list to store data
     * @return list with elements sorted by inorder
     */
    private List<T> inorderHelper(BSTNode<T> currPar, List<T> list) {
        if (currPar != null) {
            inorderHelper(currPar.getLeft(), list);
            list.add(currPar.getData());
            inorderHelper(currPar.getRight(), list);
        }
        return list;
    }

    /**
     * Private recursive helper method for postorder
     *
     * @param currPar current parent node for searching
     * @param list empty list to store data
     * @return list with elements sorted by postorder
     */
    private List<T> postorderHelper(BSTNode<T> currPar, List<T> list) {
        if (currPar != null) {
            postorderHelper(currPar.getLeft(), list);
            postorderHelper(currPar.getRight(), list);
            list.add(currPar.getData());
        }
        return list;
    }

    /**
     * Private recursive helper method for height
     *
     * @param currPar current parent node for searching
     * @return height of tree
     */
    private int heightHelper(BSTNode<T> currPar) {
        if (currPar != null) {
            return 1 + Math.max(heightHelper(currPar.getLeft()), heightHelper(currPar.getRight()));
        } else {
            return -1;
        }
    }

    /**
     * Private recursive helper method for kLargest
     *
     * @param currPar current parent node for searching
     * @param list empty list to store data
     * @param k number of elements to add to list
     * @return list with k largest elements
     */
    private List<T> kLargestHelper(BSTNode<T> currPar, List<T> list, int k) {
        //keep looking for elements until k elements are added to list
        if (currPar != null && list.size() != k) {
            kLargestHelper(currPar.getRight(), list, k);
            if (list.size() != k) {
                list.add(0, currPar.getData());
            }
            kLargestHelper(currPar.getLeft(), list, k);

        }
        return list;
    }
}
