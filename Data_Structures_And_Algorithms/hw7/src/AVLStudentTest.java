import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * This is a basic set of unit tests for AVL.
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
public class AVLStudentTest {

    private static final int TIMEOUT = 200;
    private AVL<Integer> tree;
    private AVL<Integer> avl;
    private AVL<String> wordAvl;


    @Before
    public void setup() {
        tree = new AVL<>();
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, tree.size());
        assertNull(tree.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void testConstructor() {
        /*
              1
             / \
            0   2
        */

        List<Integer> toAdd = new ArrayList<>();
        toAdd.add(1);
        toAdd.add(0);
        toAdd.add(2);
        tree = new AVL<>(toAdd);

        assertEquals(3, tree.size());

        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 1, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        AVLNode<Integer> left = root.getLeft();
        assertEquals((Integer) 0, left.getData());
        assertEquals(0, left.getHeight());
        assertEquals(0, left.getBalanceFactor());
        AVLNode<Integer> right = root.getRight();
        assertEquals((Integer) 2, right.getData());
        assertEquals(0, right.getHeight());
        assertEquals(0, right.getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testAdd() {
        // Right rotate
        /*
                2
               /
              1
             /
            0

            ->

              1
             / \
            0   2
         */

        tree.add(2);
        tree.add(1);
        tree.add(0);

        assertEquals(3, tree.size());

        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 1, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        AVLNode<Integer> left = root.getLeft();
        assertEquals((Integer) 0, left.getData());
        assertEquals(0, left.getHeight());
        assertEquals(0, left.getBalanceFactor());
        AVLNode<Integer> right = root.getRight();
        assertEquals((Integer) 2, right.getData());
        assertEquals(0, right.getHeight());
        assertEquals(0, right.getBalanceFactor());


        // Right left rotate
        /*
            0
             \
              2
             /
            1

            ->

              1
             / \
            0   2
         */

        tree = new AVL<>();
        tree.add(0);
        tree.add(2);
        tree.add(1);

        assertEquals(3, tree.size());

        assertEquals(3, tree.size());
        root = tree.getRoot();
        assertEquals((Integer) 1, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        left = root.getLeft();
        assertEquals((Integer) 0, left.getData());
        assertEquals(0, left.getHeight());
        assertEquals(0, left.getBalanceFactor());
        right = root.getRight();
        assertEquals((Integer) 2, right.getData());
        assertEquals(0, right.getHeight());
        assertEquals(0, right.getBalanceFactor());
    }


    @Test(timeout = TIMEOUT)
    public void testRemove() {
        Integer temp = 1;

        /*
                  3
                /   \
              1      4
             / \
            0   2
            ->
                3
              /   \
            0      4
             \
              2
         */


        tree.add(3);
        tree.add(temp);
        tree.add(4);
        tree.add(0);
        tree.add(2);
        assertEquals(5, tree.size());
        assertSame(temp, tree.remove(1));
        assertEquals(4, tree.size());
        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 3, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(1, root.getBalanceFactor());
        AVLNode<Integer> left = root.getLeft();
        assertEquals((Integer) 0, left.getData());
        assertEquals(1, left.getHeight());
        assertEquals(-1, left.getBalanceFactor());
        AVLNode<Integer> right = root.getRight();
        assertEquals((Integer) 4, right.getData());
        assertEquals(0, right.getHeight());
        assertEquals(0, right.getBalanceFactor());
        AVLNode<Integer> leftRight = left.getRight();
        assertEquals((Integer) 2, leftRight.getData());
        assertEquals(0, leftRight.getHeight());
        assertEquals(0, leftRight.getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testGet() {
        Integer temp1 = 1;
        Integer temp0 = 0;
        Integer temp2 = 2;
        Integer temp3 = 3;

        /*
               1
             /   \
            0     2
                    \
                     3
         */

        tree.add(temp1);
        tree.add(temp0);
        tree.add(temp2);
        tree.add(temp3);
        assertEquals(4, tree.size());

        // We want to make sure the data we retrieve is the one from the tree,
        // not the data that was passed in.
        assertSame(temp0, tree.get(0));
        assertSame(temp1, tree.get(1));
        assertSame(temp2, tree.get(2));
        assertSame(temp3, tree.get(3));
    }

    @Test(timeout = TIMEOUT)
    public void testContains() {
        /*
               1
             /   \
            0     2
                    \
                     3
         */

        tree.add(1);
        tree.add(0);
        tree.add(2);
        tree.add(3);
        assertEquals(4, tree.size());

        assertTrue(tree.contains(0));
        assertTrue(tree.contains(1));
        assertTrue(tree.contains(2));
        assertTrue(tree.contains(3));
    }

    @Test(timeout = TIMEOUT)
    public void testHeight() {
        /*
                     3
                   /   \
                 1      4
                / \
               0   2
         */

        tree.add(3);
        tree.add(1);
        tree.add(4);
        tree.add(0);
        tree.add(2);

        assertEquals(2, tree.height());
    }

    @Test(timeout = TIMEOUT)
    public void testClear() {
        /*
              1
             / \
            0   2
        */

        List<Integer> toAdd = new ArrayList<>();
        toAdd.add(1);
        toAdd.add(0);
        toAdd.add(2);
        tree = new AVL<>(toAdd);

        tree.clear();
        assertEquals(0, tree.size());
        assertNull(tree.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void testWordSearch() {

        AVL<String> letterTree = new AVL<>();

        String g = new String("g");
        String e = new String("e");
        String i = new String("i");
        String b = new String("b");
        String f = new String("f");
        String h = new String("h");
        String n = new String("n");

        /*
                g
              /   \
             e     i
            / \   / \
           b   f h   n
        */

        letterTree.add(g);
        letterTree.add(e);
        letterTree.add(i);
        letterTree.add(b);
        letterTree.add(f);
        letterTree.add(h);
        letterTree.add(n);
        assertEquals(7, letterTree.size());

        String[] word = new String[] {"b", "e", "g", "i", "n"};
        List<String> path = letterTree.wordSearch(word);
        assertEquals(5, path.size());

        // We want to ensure that the letters we retrieve are from the tree,
        // not from the word array that was passed in.
        assertSame(b, path.get(0));
        assertSame(e, path.get(1));
        assertSame(g, path.get(2));
        assertSame(i, path.get(3));
        assertSame(n, path.get(4));
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization2() {
        assertEquals(0, tree.size());
        assertNull(tree.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void testConstructor2() {
        /*
              1
             / \
            0   2
        */

        List<Integer> toAdd = new ArrayList<>();
        toAdd.add(1);
        toAdd.add(0);
        toAdd.add(2);
        tree = new AVL<>(toAdd);

        assertEquals(3, tree.size());

        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 1, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        AVLNode<Integer> left = root.getLeft();
        assertEquals((Integer) 0, left.getData());
        assertEquals(0, left.getHeight());
        assertEquals(0, left.getBalanceFactor());
        AVLNode<Integer> right = root.getRight();
        assertEquals((Integer) 2, right.getData());
        assertEquals(0, right.getHeight());
        assertEquals(0, right.getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testAdd2() {
        // Right rotate
        /*
                2
               /
              1
             /
            0
            ->
              1
             / \
            0   2
         */

        tree.add(2);
        tree.add(1);
        tree.add(0);

        assertEquals(3, tree.size());

        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 1, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        AVLNode<Integer> left = root.getLeft();
        assertEquals((Integer) 0, left.getData());
        assertEquals(0, left.getHeight());
        assertEquals(0, left.getBalanceFactor());
        AVLNode<Integer> right = root.getRight();
        assertEquals((Integer) 2, right.getData());
        assertEquals(0, right.getHeight());
        assertEquals(0, right.getBalanceFactor());


        // Right left rotate
        /*
            0
             \
              2
             /
            1
            ->
              1
             / \
            0   2
         */

        tree = new AVL<>();
        tree.add(0);
        tree.add(2);
        tree.add(1);

        assertEquals(3, tree.size());

        assertEquals(3, tree.size());
        root = tree.getRoot();
        assertEquals((Integer) 1, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        left = root.getLeft();
        assertEquals((Integer) 0, left.getData());
        assertEquals(0, left.getHeight());
        assertEquals(0, left.getBalanceFactor());
        right = root.getRight();
        assertEquals((Integer) 2, right.getData());
        assertEquals(0, right.getHeight());
        assertEquals(0, right.getBalanceFactor());

        // left rotate
        /*
            0
             \
              1
               \
                2
            ->
              1
             / \
            0   2
         */

        tree = new AVL<>();
        tree.add(0);
        tree.add(1);
        tree.add(2);

        assertEquals(3, tree.size());

        assertEquals(3, tree.size());
        root = tree.getRoot();
        assertEquals((Integer) 1, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        left = root.getLeft();
        assertEquals((Integer) 0, left.getData());
        assertEquals(0, left.getHeight());
        assertEquals(0, left.getBalanceFactor());
        right = root.getRight();
        assertEquals((Integer) 2, right.getData());
        assertEquals(0, right.getHeight());
        assertEquals(0, right.getBalanceFactor());

        // left right rotate
        /*
              2
             /
            0
             \
              1
            ->
              1
             / \
            0   2
         */

        tree = new AVL<>();
        tree.add(2);
        tree.add(0);
        tree.add(1);

        assertEquals(3, tree.size());

        assertEquals(3, tree.size());
        root = tree.getRoot();
        assertEquals((Integer) 1, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        left = root.getLeft();
        assertEquals((Integer) 0, left.getData());
        assertEquals(0, left.getHeight());
        assertEquals(0, left.getBalanceFactor());
        right = root.getRight();
        assertEquals((Integer) 2, right.getData());
        assertEquals(0, right.getHeight());
        assertEquals(0, right.getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testAddUnbalancedSubtree() {

        /*
                    201
                  /     \
                10      207
                 \      /
                 53   202
         */

        tree.add(201);
        tree.add(10);
        tree.add(207);
        tree.add(53);
        tree.add(202);
        assertEquals(5, tree.size());

        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 201, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(0, root.getBalanceFactor());

        AVLNode<Integer> left = root.getLeft();
        assertEquals((Integer) 10, left.getData());
        assertEquals(1, left.getHeight());
        assertEquals(-1, left.getBalanceFactor());

        AVLNode<Integer> right = root.getRight();
        assertEquals((Integer) 207, right.getData());
        assertEquals(1, right.getHeight());
        assertEquals(1, right.getBalanceFactor());

        AVLNode<Integer> leftRight = left.getRight();
        assertEquals((Integer) 53, leftRight.getData());
        assertEquals(0, leftRight.getHeight());
        assertEquals(0, leftRight.getBalanceFactor());

        AVLNode<Integer> rightLeft = right.getLeft();
        assertEquals((Integer) 202, rightLeft.getData());
        assertEquals(0, rightLeft.getHeight());
        assertEquals(0, rightLeft.getBalanceFactor());

        tree.add(22);
        assertEquals(6, tree.size());

        root = tree.getRoot();
        assertEquals((Integer) 201, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(0, root.getBalanceFactor());

        left = root.getLeft();
        assertEquals((Integer) 22, left.getData());
        assertEquals(1, left.getHeight());
        assertEquals(0, left.getBalanceFactor());

        right = root.getRight();
        assertEquals((Integer) 207, right.getData());
        assertEquals(1, right.getHeight());
        assertEquals(1, right.getBalanceFactor());

        AVLNode<Integer> leftLeft = left.getLeft();
        assertEquals((Integer) 10, leftLeft.getData());
        assertEquals(0, leftLeft.getHeight());
        assertEquals(0, leftLeft.getBalanceFactor());

        leftRight = left.getRight();
        assertEquals((Integer) 53, leftRight.getData());
        assertEquals(0, leftRight.getHeight());
        assertEquals(0, leftRight.getBalanceFactor());

        rightLeft = right.getLeft();
        assertEquals((Integer) 202, rightLeft.getData());
        assertEquals(0, rightLeft.getHeight());
        assertEquals(0, rightLeft.getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testAddBalanced() {
        /*
                  4
                /   \
               1     5
              / \     \
             0   2     6
                  \
                   3
         */

        tree.add(4);
        tree.add(1);
        tree.add(5);
        tree.add(0);
        tree.add(2);
        tree.add(6);
        tree.add(3);
        assertEquals(7, tree.size());

        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 4, root.getData());
        assertEquals(3, root.getHeight());
        assertEquals(1, root.getBalanceFactor());

        AVLNode<Integer> left = root.getLeft();
        assertEquals((Integer) 1, left.getData());
        assertEquals(2, left.getHeight());
        assertEquals(-1, left.getBalanceFactor());

        AVLNode<Integer> leftLeft = left.getLeft();
        assertEquals((Integer) 0, leftLeft.getData());
        assertEquals(0, leftLeft.getHeight());
        assertEquals(0, leftLeft.getBalanceFactor());

        AVLNode<Integer> leftRight = left.getRight();
        assertEquals((Integer) 2, leftRight.getData());
        assertEquals(1, leftRight.getHeight());
        assertEquals(-1, leftRight.getBalanceFactor());

        AVLNode<Integer> leftRightRight = leftRight.getRight();
        assertEquals((Integer) 3, leftRightRight.getData());
        assertEquals(0, leftRightRight.getHeight());
        assertEquals(0, leftRightRight.getBalanceFactor());

        AVLNode<Integer> right = root.getRight();
        assertEquals((Integer) 5, right.getData());
        assertEquals(1, right.getHeight());
        assertEquals(-1, right.getBalanceFactor());

        AVLNode<Integer> rightRight = right.getRight();
        assertEquals((Integer) 6, rightRight.getData());
        assertEquals(0, rightRight.getHeight());
        assertEquals(0, rightRight.getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testAddRemoveBalanced() {
        Integer temp = 1;
        /*
                  4
                /   \
               1     5
              / \     \
             0   2     6
                  \
                   3
              ->
                  4
                /   \
               1     5
              / \     \
             0   2     6
            /     \
          -1       3
              ->
                  4
                /   \
               0     5
              / \     \
            -1   2     6
                  \
                   3
         */

        tree.add(4);
        tree.add(temp);
        tree.add(5);
        tree.add(0);
        tree.add(2);
        tree.add(6);
        tree.add(3);
        assertEquals(7, tree.size());

        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 4, root.getData());
        assertEquals(3, root.getHeight());
        assertEquals(1, root.getBalanceFactor());

        AVLNode<Integer> left = root.getLeft();
        assertEquals((Integer) 1, left.getData());
        assertEquals(2, left.getHeight());
        assertEquals(-1, left.getBalanceFactor());

        AVLNode<Integer> leftLeft = left.getLeft();
        assertEquals((Integer) 0, leftLeft.getData());
        assertEquals(0, leftLeft.getHeight());
        assertEquals(0, leftLeft.getBalanceFactor());

        AVLNode<Integer> leftRight = left.getRight();
        assertEquals((Integer) 2, leftRight.getData());
        assertEquals(1, leftRight.getHeight());
        assertEquals(-1, leftRight.getBalanceFactor());

        AVLNode<Integer> leftRightRight = leftRight.getRight();
        assertEquals((Integer) 3, leftRightRight.getData());
        assertEquals(0, leftRightRight.getHeight());
        assertEquals(0, leftRightRight.getBalanceFactor());

        AVLNode<Integer> right = root.getRight();
        assertEquals((Integer) 5, right.getData());
        assertEquals(1, right.getHeight());
        assertEquals(-1, right.getBalanceFactor());

        AVLNode<Integer> rightRight = right.getRight();
        assertEquals((Integer) 6, rightRight.getData());
        assertEquals(0, rightRight.getHeight());
        assertEquals(0, rightRight.getBalanceFactor());

        tree.add(-1);
        assertEquals(8, tree.size());

        root = tree.getRoot();
        assertEquals((Integer) 4, root.getData());
        assertEquals(3, root.getHeight());
        assertEquals(1, root.getBalanceFactor());

        left = root.getLeft();
        assertEquals((Integer) 1, left.getData());
        assertEquals(2, left.getHeight());
        assertEquals(0, left.getBalanceFactor());

        leftLeft = left.getLeft();
        assertEquals((Integer) 0, leftLeft.getData());
        assertEquals(1, leftLeft.getHeight());
        assertEquals(1, leftLeft.getBalanceFactor());

        AVLNode<Integer> leftLeftLeft = leftLeft.getLeft();
        assertEquals((Integer) (-1), leftLeftLeft.getData());
        assertEquals(0, leftLeftLeft.getHeight());
        assertEquals(0, leftLeftLeft.getBalanceFactor());

        leftRight = left.getRight();
        assertEquals((Integer) 2, leftRight.getData());
        assertEquals(1, leftRight.getHeight());
        assertEquals(-1, leftRight.getBalanceFactor());

        leftRightRight = leftRight.getRight();
        assertEquals((Integer) 3, leftRightRight.getData());
        assertEquals(0, leftRightRight.getHeight());
        assertEquals(0, leftRightRight.getBalanceFactor());

        right = root.getRight();
        assertEquals((Integer) 5, right.getData());
        assertEquals(1, right.getHeight());
        assertEquals(-1, right.getBalanceFactor());

        rightRight = right.getRight();
        assertEquals((Integer) 6, rightRight.getData());
        assertEquals(0, rightRight.getHeight());
        assertEquals(0, rightRight.getBalanceFactor());

        assertSame(temp, tree.remove(1));
        assertEquals(7, tree.size());
        assertEquals(3, tree.height());

        root = tree.getRoot();
        assertEquals((Integer) 4, root.getData());
        assertEquals(3, root.getHeight());
        assertEquals(1, root.getBalanceFactor());

        left = root.getLeft();
        assertEquals((Integer) 0, left.getData());
        assertEquals(2, left.getHeight());
        assertEquals(-1, left.getBalanceFactor());

        leftLeft = left.getLeft();
        assertEquals((Integer) (-1), leftLeft.getData());
        assertEquals(0, leftLeft.getHeight());
        assertEquals(0, leftLeft.getBalanceFactor());

        leftRight = left.getRight();
        assertEquals((Integer) 2, leftRight.getData());
        assertEquals(1, leftRight.getHeight());
        assertEquals(-1, leftRight.getBalanceFactor());

        leftRightRight = leftRight.getRight();
        assertEquals((Integer) 3, leftRightRight.getData());
        assertEquals(0, leftRightRight.getHeight());
        assertEquals(0, leftRightRight.getBalanceFactor());

        right = root.getRight();
        assertEquals((Integer) 5, right.getData());
        assertEquals(1, right.getHeight());
        assertEquals(-1, right.getBalanceFactor());

        rightRight = right.getRight();
        assertEquals((Integer) 6, rightRight.getData());
        assertEquals(0, rightRight.getHeight());
        assertEquals(0, rightRight.getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveRotateSubtree() {
        Integer temp = 5;

        /*
                    6
                  /   \
                 3     7
                / \     \
               1   5     9
              /
             0
             ->
                    6
                  /   \
                 1     7
                / \     \
               0   3     9
         */

        tree.add(6);
        tree.add(3);
        tree.add(7);
        tree.add(1);
        tree.add(temp);
        tree.add(9);
        tree.add(0);
        assertEquals(7, tree.size());

        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 6, root.getData());
        assertEquals(3, root.getHeight());
        assertEquals(1, root.getBalanceFactor());

        AVLNode<Integer> left = root.getLeft();
        assertEquals((Integer) 3, left.getData());
        assertEquals(2, left.getHeight());
        assertEquals(1, left.getBalanceFactor());

        AVLNode<Integer> leftLeft = left.getLeft();
        assertEquals((Integer) 1, leftLeft.getData());
        assertEquals(1, leftLeft.getHeight());
        assertEquals(1, leftLeft.getBalanceFactor());

        AVLNode<Integer> leftLeftLeft = leftLeft.getLeft();
        assertEquals((Integer) 0, leftLeftLeft.getData());
        assertEquals(0, leftLeftLeft.getHeight());
        assertEquals(0, leftLeftLeft.getBalanceFactor());

        AVLNode<Integer> leftRight = left.getRight();
        assertEquals((Integer) 5, leftRight.getData());
        assertEquals(0, leftRight.getHeight());
        assertEquals(0, leftRight.getBalanceFactor());

        AVLNode<Integer> right = root.getRight();
        assertEquals((Integer) 7, right.getData());
        assertEquals(1, right.getHeight());
        assertEquals(-1, right.getBalanceFactor());

        AVLNode<Integer> rightRight = right.getRight();
        assertEquals((Integer) 9, rightRight.getData());
        assertEquals(0, rightRight.getHeight());
        assertEquals(0, rightRight.getBalanceFactor());

        assertSame(temp, tree.remove(5));
        assertEquals(6, tree.size());

        root = tree.getRoot();
        assertEquals((Integer) 6, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(0, root.getBalanceFactor());

        left = root.getLeft();
        assertEquals((Integer) 1, left.getData());
        assertEquals(1, left.getHeight());
        assertEquals(0, left.getBalanceFactor());

        leftLeft = left.getLeft();
        assertEquals((Integer) 0, leftLeft.getData());
        assertEquals(0, leftLeft.getHeight());
        assertEquals(0, leftLeft.getBalanceFactor());

        leftRight = left.getRight();
        assertEquals((Integer) 3, leftRight.getData());
        assertEquals(0, leftRight.getHeight());
        assertEquals(0, leftRight.getBalanceFactor());

        right = root.getRight();
        assertEquals((Integer) 7, right.getData());
        assertEquals(1, right.getHeight());
        assertEquals(-1, right.getBalanceFactor());

        rightRight = right.getRight();
        assertEquals((Integer) 9, rightRight.getData());
        assertEquals(0, rightRight.getHeight());
        assertEquals(0, rightRight.getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testRemovePredecessorUpdateHeight() {
        Integer temp = -352;

        /*
                  -109
                 /     \
              -352     169
             /   \        \
          -960  -161      261
             \
            -944
            ->
                  -109
                 /     \
              -944     169
             /   \        \
          -960  -161      261

         */


        tree.add(-109);
        tree.add(temp);
        tree.add(169);
        tree.add(-960);
        tree.add(-161);
        tree.add(261);
        tree.add(-944);
        assertEquals(7, tree.size());

        assertSame(temp, tree.remove(-352));
        assertEquals(6, tree.size());
        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) (-109), root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        AVLNode<Integer> left = root.getLeft();
        assertEquals((Integer) (-944), left.getData());
        assertEquals(1, left.getHeight());
        assertEquals(0, left.getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testRemove2() {
        Integer temp = 1;

        /*
                  3
                /   \
              1      4
             / \
            0   2
            ->
                3
              /   \
            0      4
             \
              2
         */


        tree.add(3);
        tree.add(temp);
        tree.add(4);
        tree.add(0);
        tree.add(2);
        assertEquals(5, tree.size());

        assertSame(temp, tree.remove(1));
        assertEquals(4, tree.size());
        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 3, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(1, root.getBalanceFactor());
        AVLNode<Integer> left = root.getLeft();
        assertEquals((Integer) 0, left.getData());
        assertEquals(1, left.getHeight());
        assertEquals(-1, left.getBalanceFactor());
        AVLNode<Integer> right = root.getRight();
        assertEquals((Integer) 4, right.getData());
        assertEquals(0, right.getHeight());
        assertEquals(0, right.getBalanceFactor());
        AVLNode<Integer> leftRight = left.getRight();
        assertEquals((Integer) 2, leftRight.getData());
        assertEquals(0, leftRight.getHeight());
        assertEquals(0, leftRight.getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveUnbalanced() {
        Integer temp = 5;

        /*
                  4
                /   \
              1      5
             / \      \
            0   2      6
                 \
                  3
            ->
                4
              /   \
             1     6
            / \
           0   2
                \
                 3
            ->
                2
              /   \
             1     4
            /     / \
           0     3   6
         */


        tree.add(4);
        tree.add(1);
        tree.add(temp);
        tree.add(0);
        tree.add(2);
        tree.add(6);
        tree.add(3);
        assertEquals(7, tree.size());

        assertSame(temp, tree.remove(5));
        assertEquals(6, tree.size());

        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 2, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(0, root.getBalanceFactor());

        AVLNode<Integer> left = root.getLeft();
        assertEquals((Integer) 1, left.getData());
        assertEquals(1, left.getHeight());
        assertEquals(1, left.getBalanceFactor());

        AVLNode<Integer> leftLeft = left.getLeft();
        assertEquals((Integer) 0, leftLeft.getData());
        assertEquals(0, leftLeft.getHeight());
        assertEquals(0, leftLeft.getBalanceFactor());

        AVLNode<Integer> right = root.getRight();
        assertEquals((Integer) 4, right.getData());
        assertEquals(1, right.getHeight());
        assertEquals(0, right.getBalanceFactor());

        AVLNode<Integer> rightLeft = right.getLeft();
        assertEquals((Integer) 3, rightLeft.getData());
        assertEquals(0, rightLeft.getHeight());
        assertEquals(0, rightLeft.getBalanceFactor());

        AVLNode<Integer> rightRight = right.getRight();
        assertEquals((Integer) 6, rightRight.getData());
        assertEquals(0, rightRight.getHeight());
        assertEquals(0, rightRight.getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testGet2() {
        Integer temp1 = 1;
        Integer temp0 = 0;
        Integer temp2 = 2;
        Integer temp3 = 3;

        /*
               1
             /   \
            0     2
                    \
                     3
         */

        tree.add(temp1);
        tree.add(temp0);
        tree.add(temp2);
        tree.add(temp3);
        assertEquals(4, tree.size());

        // We want to make sure the data we retrieve is the one from the tree,
        // not the data that was passed in.
        assertSame(temp0, tree.get(0));
        assertSame(temp1, tree.get(1));
        assertSame(temp2, tree.get(2));
        assertSame(temp3, tree.get(3));
    }

    @Test(timeout = TIMEOUT)
    public void testContains2() {
        /*
               1
             /   \
            0     2
                    \
                     3
         */

        tree.add(1);
        tree.add(0);
        tree.add(2);
        tree.add(3);
        assertEquals(4, tree.size());

        assertTrue(tree.contains(0));
        assertTrue(tree.contains(1));
        assertTrue(tree.contains(2));
        assertTrue(tree.contains(3));
    }

    @Test(timeout = TIMEOUT)
    public void testHeight2() {
        /*
                     3
                   /   \
                 1      4
                / \
               0   2
         */

        tree.add(3);
        tree.add(1);
        tree.add(4);
        tree.add(0);
        tree.add(2);

        assertEquals(2, tree.height());
    }

    @Test(timeout = TIMEOUT)
    public void testClear2() {
        /*
              1
             / \
            0   2
        */

        List<Integer> toAdd = new ArrayList<>();
        toAdd.add(1);
        toAdd.add(0);
        toAdd.add(2);
        tree = new AVL<>(toAdd);

        tree.clear();
        assertEquals(0, tree.size());
        assertNull(tree.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void testWordSearch2() {
        AVL<String> letterTree = new AVL<>();

        String g = new String("g");
        String e = new String("e");
        String i = new String("i");
        String b = new String("b");
        String f = new String("f");
        String h = new String("h");
        String n = new String("n");

        /*
                g
              /   \
             e     i
            / \   / \
           b   f h   n
        */

        letterTree.add(g);
        letterTree.add(e);
        letterTree.add(i);
        letterTree.add(b);
        letterTree.add(f);
        letterTree.add(h);
        letterTree.add(n);
        assertEquals(7, letterTree.size());

        String[] word = new String[] {"b", "e", "g", "i", "n"};
        List<String> path = letterTree.wordSearch(word);
        assertEquals(5, path.size());

        // We want to ensure that the letters we retrieve are from the tree,
        // not from the word array that was passed in.
        assertSame(b, path.get(0));
        assertSame(e, path.get(1));
        assertSame(g, path.get(2));
        assertSame(i, path.get(3));
        assertSame(n, path.get(4));

        // test maintained order
        letterTree.clear();

        /*
                g
              /   \
             e     i
            / \   / \
           b   f h   n
        */

        letterTree.add(g);
        letterTree.add(e);
        letterTree.add(i);
        letterTree.add(b);
        letterTree.add(f);
        letterTree.add(h);
        letterTree.add(n);
        assertEquals(7, letterTree.size());

        word = new String[] {"n", "i", "g", "e", "b"};
        path = letterTree.wordSearch(word);
        assertEquals(5, path.size());

        // We want to ensure that the letters we retrieve are from the tree,
        // not from the word array that was passed in.
        assertSame(n, path.get(0));
        assertSame(i, path.get(1));
        assertSame(g, path.get(2));
        assertSame(e, path.get(3));
        assertSame(b, path.get(4));

        // test deepest common ancestor
        letterTree.clear();

        /*
                g
              /   \
             e     i
            / \   / \
           b   f h   n
        */

        letterTree.add(g);
        letterTree.add(e);
        letterTree.add(i);
        letterTree.add(b);
        letterTree.add(f);
        letterTree.add(h);
        letterTree.add(n);
        assertEquals(7, letterTree.size());

        word = new String[] {"b", "e", "f"};
        path = letterTree.wordSearch(word);
        assertEquals(3, path.size());

        // We want to ensure that the letters we retrieve are from the tree,
        // not from the word array that was passed in.
        assertSame(b, path.get(0));
        assertSame(e, path.get(1));
        assertSame(f, path.get(2));

        // test first letter equal to dca
        letterTree.clear();

        /*
                g
              /   \
             e     i
            / \   / \
           b   f h   n
        */

        letterTree.add(g);
        letterTree.add(e);
        letterTree.add(i);
        letterTree.add(b);
        letterTree.add(f);
        letterTree.add(h);
        letterTree.add(n);
        assertEquals(7, letterTree.size());

        word = new String[] {"g", "i", "n"};
        path = letterTree.wordSearch(word);
        assertEquals(3, path.size());

        // We want to ensure that the letters we retrieve are from the tree,
        // not from the word array that was passed in.
        assertSame(g, path.get(0));
        assertSame(i, path.get(1));
        assertSame(n, path.get(2));

        // test last letter equal to dca
        letterTree.clear();

        /*
                g
              /   \
             e     i
            / \   / \
           b   f h   n
        */

        letterTree.add(g);
        letterTree.add(e);
        letterTree.add(i);
        letterTree.add(b);
        letterTree.add(f);
        letterTree.add(h);
        letterTree.add(n);
        assertEquals(7, letterTree.size());

        word = new String[] {"h", "i"};
        path = letterTree.wordSearch(word);
        assertEquals(2, path.size());

        // We want to ensure that the letters we retrieve are from the tree,
        // not from the word array that was passed in.
        assertSame(h, path.get(0));
        assertSame(i, path.get(1));

        word = new String[] {"b", "e", "g"};
        path = letterTree.wordSearch(word);
        assertEquals(3, path.size());

        // We want to ensure that the letters we retrieve are from the tree,
        // not from the word array that was passed in.
        assertSame(b, path.get(0));
        assertSame(e, path.get(1));
        assertSame(g, path.get(2));

        // test word 0 length input

        word = new String[0];
        path = letterTree.wordSearch(word);
        assertEquals(0, path.size());

        // test only input dca

        word = new String[] {"g"};
        path = letterTree.wordSearch(word);
        assertEquals(1, path.size());

        // We want to ensure that the letters we retrieve are from the tree,
        // not from the word array that was passed in.
        assertSame(g, path.get(0));

        word = new String[] {"f"};
        path = letterTree.wordSearch(word);
        assertEquals(1, path.size());

        // We want to ensure that the letters we retrieve are from the tree,
        // not from the word array that was passed in.
        assertSame(f, path.get(0));
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testWordNotInTree() {
        AVL<String> letterTree = new AVL<>();

        String g = new String("g");
        String e = new String("e");
        String i = new String("i");
        String b = new String("b");
        String f = new String("f");
        String h = new String("h");
        String n = new String("n");

        /*
                g
              /   \
             e     i
            / \   / \
           b   f h   n
        */

        letterTree.add(g);
        letterTree.add(e);
        letterTree.add(i);
        letterTree.add(b);
        letterTree.add(f);
        letterTree.add(h);
        letterTree.add(n);
        assertEquals(7, letterTree.size());

        String[] word = new String[] {"f", "e", "g", "i", "h"};
        List<String> path = letterTree.wordSearch(word);
        assertEquals(5, path.size());

        // We want to ensure that the letters we retrieve are from the tree,
        // not from the word array that was passed in.
        assertSame(f, path.get(0));
        assertSame(e, path.get(1));
        assertSame(g, path.get(2));
        System.out.println(path);
        assertSame(i, path.get(3));
        assertSame(h, path.get(4));

        word = new String[] {"a"};
        path = letterTree.wordSearch(word);
        assertEquals(1, path.size());
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testObstructedWordPath() {
        AVL<String> letterTree = new AVL<>();

        String g = new String("g");
        String e = new String("e");
        String i = new String("i");
        String b = new String("b");
        String f = new String("f");
        String h = new String("h");
        String n = new String("n");

        /*
                g
              /   \
             e     i
            / \   / \
           b   f h   n
        */

        letterTree.add(g);
        letterTree.add(e);
        letterTree.add(i);
        letterTree.add(b);
        letterTree.add(f);
        letterTree.add(h);
        letterTree.add(n);
        assertEquals(7, letterTree.size());

        String[] word = new String[] {"n", "i", "b"};
        List<String> path = letterTree.wordSearch(word);
        assertEquals(3, path.size());
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testIncorrectWordPath() {
        AVL<String> letterTree = new AVL<>();

        String g = new String("g");
        String e = new String("e");
        String i = new String("i");
        String b = new String("b");
        String f = new String("f");
        String h = new String("h");
        String n = new String("n");

        /*
                g
              /   \
             e     i
            / \   / \
           b   f h   n
        */

        letterTree.add(g);
        letterTree.add(e);
        letterTree.add(i);
        letterTree.add(b);
        letterTree.add(f);
        letterTree.add(h);
        letterTree.add(n);
        assertEquals(7, letterTree.size());

        String[] word = new String[] {"b", "e", "j", "i", "n"};
        List<String> path = letterTree.wordSearch(word);
        assertEquals(5, path.size());
    }

    /**
     * Determines whether the two AVL nodes have the same data, height, BF,
     * and children
     * @param node1 the first node to compare
     * @param node2 the second node to compare
     * @param <T> the generic type of data contained in the AVL node
     * @return true if the nodes are equal, false else
     */
    public <T extends Comparable<? super T>> boolean nodeEquals(AVLNode<T> node1, AVLNode<T> node2) {
        return (node1.getData().compareTo(node2.getData()) == 0)
                && node1.getHeight() == node2.getHeight()
                && node1.getBalanceFactor() == node2.getBalanceFactor()
                && this.equalCheckNull(node1.getLeft(), node2.getLeft())
                && this.equalCheckNull(node1.getRight(), node2.getRight());
    }

    /**
     * Determines whether the two nodes are equal, given that either may be
     * null and two null values are equal
     * @param node1 the first node to be compared
     * @param node2 the second node to be compared
     * @param <T> the generic type of data in the AVL nodes
     * @return true if nodes are equal, else false
     */
    private <T extends Comparable<? super T>> boolean equalCheckNull(AVLNode<T> node1, AVLNode<T> node2) {
        if (node1 == null && node2 == null) {
            return true;
        } else if (node1 == null && node2 != null) {
            return false;
        } else if (node1 != null && node2 == null) {
            return false;
        } else {
            return this.nodeEquals(node1, node2);
        }
    }

    @Before
    public void setUp() {
        this.avl = new AVL<>();
        this.wordAvl = new AVL<>();
    }

    @Test(timeout = TIMEOUT)
    public void testEmptyConstructor() {
        assertNull(this.avl.getRoot());
        assertEquals(0, this.avl.size());
        assertEquals(-1, this.avl.height());
    }

    @Test(timeout = TIMEOUT)
    public void testAddRotateLeftNoSubtree() {
        this.avl.add(38);

        AVLNode<Integer> node38 = new AVLNode<>(38);

        assertTrue(this.nodeEquals(node38, this.avl.getRoot()));
        assertEquals(1, this.avl.size());

        /* After 38:
         *     38
         */

        this.avl.add(49);

        AVLNode<Integer> node38After49 = new AVLNode<>(38);
        node38After49.setHeight(1);
        node38After49.setBalanceFactor(-1);
        AVLNode<Integer> node49 = new AVLNode<>(49);
        node49.setHeight(0);
        node49.setBalanceFactor(0);
        node38After49.setRight(node49);

        assertTrue(this.nodeEquals(node38After49, this.avl.getRoot()));
        assertEquals(2, this.avl.size());

        /* After 49:
                38
                  \
                   49
         */

        this.avl.add(85);
        AVLNode<Integer> node49After85 = new AVLNode<>(49);
        node49After85.setHeight(1);
        node49After85.setBalanceFactor(0);
        AVLNode<Integer> node38After85 = new AVLNode<>(38);
        node38After85.setBalanceFactor(0);
        node38After85.setHeight(0);
        AVLNode<Integer> node85 = new AVLNode<>(85);
        node85.setBalanceFactor(0);
        node85.setHeight(0);
        node49After85.setLeft(node38After85);
        node49After85.setRight(node85);

        assertTrue(this.nodeEquals(node49After85, this.avl.getRoot()));
        assertEquals(3, this.avl.size());

        /*  After 85:
                49
               /  \
             38    85
         */
    }

    @Test(timeout = TIMEOUT)
    public void testAddRotateLeftSubtree() {
        this.avl.add(38);
        this.avl.add(10);
        this.avl.add(49);
        this.avl.add(41);
        this.avl.add(57);

        /*          38
                  /     \
                 10      49
                        /   \
                      41    57
         */

        this.avl.add(85);

        /*           49
                   /   \
                  38    57
                 /  \     \
                10  41    85
         */

        AVLNode<Integer> node49 = new AVLNode<>(49);
        node49.setBalanceFactor(0);
        node49.setHeight(2);

        AVLNode<Integer> node38 = new AVLNode<>(38);
        node38.setBalanceFactor(0);
        node38.setHeight(1);
        AVLNode<Integer> node57 = new AVLNode<>(57);
        node57.setBalanceFactor(-1);
        node57.setHeight(1);

        AVLNode<Integer> node10 = new AVLNode<>(10);
        node10.setHeight(0);
        node10.setBalanceFactor(0);

        AVLNode<Integer> node41 = new AVLNode<>(41);
        node41.setHeight(0);
        node41.setBalanceFactor(0);

        AVLNode<Integer> node85 = new AVLNode<>(85);
        node85.setHeight(0);
        node85.setBalanceFactor(0);

        node49.setLeft(node38);
        node49.setRight(node57);
        node38.setLeft(node10);
        node38.setRight(node41);
        node57.setRight(node85);

        assertEquals(6, this.avl.size());
        assertEquals(2, this.avl.height());
        assertTrue(this.nodeEquals(node49, this.avl.getRoot()));
    }

    @Test(timeout = TIMEOUT)
    public void testAddRotateRightNoSubtree() {

        /*
                85
               /
              49      ->  49
             /           /  \
            38          38   85
         */

        this.avl.add(85);
        AVLNode<Integer> node85 = new AVLNode<>(85);
        node85.setBalanceFactor(0);
        node85.setHeight(0);

        assertEquals(1, this.avl.size());
        assertTrue(this.nodeEquals(node85, this.avl.getRoot()));

        this.avl.add(49);
        AVLNode<Integer> node85After49 = new AVLNode<>(85);
        node85After49.setBalanceFactor(1);
        node85After49.setHeight(1);

        AVLNode<Integer> node49 = new AVLNode<>(49);
        node49.setBalanceFactor(0);
        node49.setHeight(0);
        node85After49.setLeft(node49);

        assertEquals(2, this.avl.size());
        assertTrue(this.nodeEquals(node85After49, this.avl.getRoot()));

        this.avl.add(38);
        AVLNode<Integer> node49After38 = new AVLNode<>(49);
        node49After38.setBalanceFactor(0);
        node49After38.setHeight(1);

        AVLNode<Integer> node38 = new AVLNode<>(38);
        node38.setBalanceFactor(0);
        node38.setHeight(0);

        AVLNode<Integer> node85After38 = new AVLNode<>(85);
        node85After38.setBalanceFactor(0);
        node85After38.setHeight(0);

        node49After38.setLeft(node38);
        node49After38.setRight(node85After38);

        assertEquals(3, this.avl.size());
        assertTrue(this.nodeEquals(node49After38, this.avl.getRoot()));
    }

    @Test(timeout = TIMEOUT)
    public void testAddRotateRightSubtree() {
        this.avl.add(85);
        this.avl.add(49);
        this.avl.add(100);
        this.avl.add(16);
        this.avl.add(60);

        /*          85
                   /  \
                  49  100
                 /  \
                16  60
         */

        this.avl.add(38);

        /*           49
                   /    \
                  16     85
                   \     /  \
                   38  60  100
         */
        AVLNode<Integer> node49 = new AVLNode<>(49);
        node49.setHeight(2);
        node49.setBalanceFactor(0);

        AVLNode<Integer> node16 = new AVLNode<>(16);
        node16.setHeight(1);
        node16.setBalanceFactor(-1);

        AVLNode<Integer> node85 = new AVLNode<>(85);
        node85.setHeight(1);
        node85.setBalanceFactor(0);
        AVLNode<Integer> node38 = new AVLNode<>(38);
        AVLNode<Integer> node60 = new AVLNode<>(60);
        AVLNode<Integer> node100 = new AVLNode<>(100);

        node49.setLeft(node16);
        node49.setRight(node85);
        node16.setRight(node38);
        node85.setLeft(node60);
        node85.setRight(node100);

        assertEquals(6, this.avl.size());
        assertEquals(2, this.avl.height());
        assertTrue(this.nodeEquals(node49, this.avl.getRoot()));
    }

    @Test(timeout = TIMEOUT)
    public void testAddRotateLeftRight() {

        this.avl.add(50);
        this.avl.add(30);
        this.avl.add(90);
        this.avl.add(15);
        this.avl.add(40);

        /*             50
                      /  \
                    30    90
                   /  \
                  15  40
         */

        this.avl.add(33);

        /*
                    40
                   /  \
                  30   50
                 /  \    \
                15  33   90
         */

        AVLNode<Integer> node15 = new AVLNode<>(15);
        AVLNode<Integer> node33 = new AVLNode<>(33);
        AVLNode<Integer> node90 = new AVLNode<>(90);

        AVLNode<Integer> node30 = new AVLNode<>(30);
        node30.setBalanceFactor(0);
        node30.setHeight(1);
        node30.setLeft(node15);
        node30.setRight(node33);

        AVLNode<Integer> node50 = new AVLNode<>(50);
        node50.setBalanceFactor(-1);
        node50.setHeight(1);
        node50.setRight(node90);

        AVLNode<Integer> node40 = new AVLNode<>(40);
        node40.setBalanceFactor(0);
        node40.setHeight(2);
        node40.setLeft(node30);
        node40.setRight(node50);

        assertEquals(6, this.avl.size());
        assertEquals(2, this.avl.height());
        assertTrue(this.nodeEquals(node40, this.avl.getRoot()));
    }

    @Test(timeout = TIMEOUT)
    public void testAddRotateRightLeft() {
        this.avl.add(120);
        this.avl.add(70);
        this.avl.add(150);
        this.avl.add(140);
        this.avl.add(210);

        /*          120
                   /   \
                  70   150
                      /   \
                     140  210
         */

        this.avl.add(130);

        /*      After right rotation:
                    120
                   /   \
                  70   140
                      /   \
                     130  150
                            \
                            210
         */

        /*      After left-right rotation:
                    140
                   /   \
                  120   150
                 /  \     \
                70  130   210
         */

        AVLNode<Integer> node70 = new AVLNode<>(70);
        AVLNode<Integer> node130 = new AVLNode<>(130);
        AVLNode<Integer> node210 = new AVLNode<>(210);

        AVLNode<Integer> node120 = new AVLNode<>(120);
        node120.setBalanceFactor(0);
        node120.setHeight(1);
        node120.setLeft(node70);
        node120.setRight(node130);

        AVLNode<Integer> node150 = new AVLNode<>(150);
        node150.setBalanceFactor(-1);
        node150.setHeight(1);
        node150.setRight(node210);

        AVLNode<Integer> node140 = new AVLNode<>(140);
        node140.setBalanceFactor(0);
        node140.setHeight(2);
        node140.setLeft(node120);
        node140.setRight(node150);

        assertEquals(6, this.avl.size());
        assertEquals(2, this.avl.height());
        assertTrue(this.nodeEquals(node140, this.avl.getRoot()));
    }

    @Test(timeout = TIMEOUT)
    public void testAddNull() {
        this.avl.add(120);
        this.avl.add(70);
        this.avl.add(150);
        try {
            this.avl.add(null);
            fail();
        } catch (IllegalArgumentException e) { }
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveRotateLeft() {
        this.avl.add(98);
        this.avl.add(15);
        this.avl.add(280);
        this.avl.add(625);

        assertEquals((Integer) 15, this.avl.remove(15));
        /*      280
               /   \
              98    625
         */

        AVLNode<Integer> node98 = new AVLNode<>(98);
        AVLNode<Integer> node625 = new AVLNode<>(625);

        AVLNode<Integer> node280 = new AVLNode<>(280);
        node280.setBalanceFactor(0);
        node280.setHeight(1);
        node280.setLeft(node98);
        node280.setRight(node625);

        assertEquals(3, this.avl.size());
        assertEquals(1, this.avl.height());
        assertTrue(this.nodeEquals(node280, this.avl.getRoot()));
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveRotateRight() {
        this.avl.add(280);
        this.avl.add(98);
        this.avl.add(625);
        this.avl.add(15);

        /*      280
               /   \
              98   625
             /
            15
         */

        assertEquals((Integer) 625, this.avl.remove(625));
        /*      98
               /   \
              15    280
         */

        AVLNode<Integer> node280 = new AVLNode<>(280);
        AVLNode<Integer> node15 = new AVLNode<>(15);

        AVLNode<Integer> node98 = new AVLNode<>(98);
        node98.setBalanceFactor(0);
        node98.setHeight(1);
        node98.setLeft(node15);
        node98.setRight(node280);

        assertEquals(3, this.avl.size());
        assertEquals(1, this.avl.height());
        assertTrue(this.nodeEquals(node98, this.avl.getRoot()));
    }

    @Test(timeout = TIMEOUT)
    public void testRemovePredLeftRight() {

        this.avl.add(50);
        this.avl.add(35);
        this.avl.add(70);
        this.avl.add(20);
        this.avl.add(60);
        this.avl.add(90);
        this.avl.add(55);
        this.avl.add(65);

        /*              50
                       /   \
                      35   70
                     /    /  \
                    20   60  90
                        /  \
                       55  65
         */

        assertEquals((Integer) 50, this.avl.remove(50));

        /*               60
                       /    \
                      35     70
                     /  \   /  \
                    20  55 65  90
         */

        AVLNode<Integer> node20 = new AVLNode<>(20);
        AVLNode<Integer> node55 = new AVLNode<>(55);
        AVLNode<Integer> node65 = new AVLNode<>(65);
        AVLNode<Integer> node90 = new AVLNode<>(90);

        AVLNode<Integer> node35 = new AVLNode<>(35);
        node35.setHeight(1);
        node35.setLeft(node20);
        node35.setRight(node55);

        AVLNode<Integer> node70 = new AVLNode<>(70);
        node70.setHeight(1);
        node70.setLeft(node65);
        node70.setRight(node90);

        AVLNode<Integer> node60 = new AVLNode<>(60);
        node60.setHeight(2);
        node60.setLeft(node35);
        node60.setRight(node70);

        assertEquals(2, this.avl.height());
        assertEquals(7, this.avl.size());
        assertTrue(this.nodeEquals(node60, this.avl.getRoot()));
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveRightLeft() {

        this.avl.add(50);
        this.avl.add(35);
        this.avl.add(70);
        this.avl.add(20);
        this.avl.add(40);
        this.avl.add(90);
        this.avl.add(38);
        this.avl.add(45);

        /*               50
                       /    \
                      35     70
                     /  \      \
                    20  40     90
                        / \
                       38 45
         */

        assertEquals((Integer) 90, this.avl.remove(90));

        /*           40
                   /    \
                  35     50
                 /  \   /  \
                20  38 45  70
         */

        AVLNode<Integer> node20 = new AVLNode<>(20);
        AVLNode<Integer> node38 = new AVLNode<>(38);
        AVLNode<Integer> node45 = new AVLNode<>(45);
        AVLNode<Integer> node70 = new AVLNode<>(70);

        AVLNode<Integer> node35 = new AVLNode<>(35);
        node35.setHeight(1);
        node35.setLeft(node20);
        node35.setRight(node38);

        AVLNode<Integer> node50 = new AVLNode<>(50);
        node50.setHeight(1);
        node50.setLeft(node45);
        node50.setRight(node70);

        AVLNode<Integer> node40 = new AVLNode<>(40);
        node40.setHeight(2);
        node40.setLeft(node35);
        node40.setRight(node50);

        assertEquals(2, this.avl.height());
        assertEquals(7, this.avl.size());
        assertTrue(this.nodeEquals(node40, this.avl.getRoot()));
    }

    // checked that if remove predecessor, that should be rotated if necessary

    @Test(timeout = TIMEOUT)
    public void testRemoveNull() {
        this.avl.add(60);
        this.avl.add(20);
        this.avl.add(100);

        try {
            this.avl.remove(null);
            fail();
        } catch (IllegalArgumentException e) { }
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveNotExists() {
        this.avl.add(60);
        this.avl.add(20);
        this.avl.add(100);

        try {
            this.avl.remove(50);
            fail();
        } catch (NoSuchElementException e) { }
    }

    @Test(timeout = TIMEOUT)
    public void testGet3() {

        Integer i1 = 50;
        Integer i2 = 35;
        Integer i3 = 70;
        Integer i4 = 20;
        Integer i5 = 10;
        Integer i6 = 100;

        this.avl.add(i1);
        this.avl.add(i2);
        this.avl.add(i3);
        this.avl.add(i4);
        this.avl.add(i5);
        this.avl.add(i6);

        assertSame(i4, this.avl.remove(20));
        assertSame(i1, this.avl.remove(50));

        assertSame(i2, this.avl.get(35));
        assertSame(i3, this.avl.get(70));
        assertSame(i5, this.avl.get(10));
        assertSame(i6, this.avl.get(100));

        try {
            this.avl.get(20);
            fail();
        } catch (NoSuchElementException e) { }

        try {
            this.avl.get(50);
            fail();
        } catch (NoSuchElementException e) { }
    }

    @Test(timeout = TIMEOUT)
    public void testContains3() {

        Integer i1 = 50;
        Integer i2 = 35;
        Integer i3 = 70;
        Integer i4 = 20;
        Integer i5 = 10;
        Integer i6 = 100;

        this.avl.add(i1);
        this.avl.add(i2);
        this.avl.add(i3);
        this.avl.add(i4);
        this.avl.add(i5);
        this.avl.add(i6);

        this.avl.remove(20);
        this.avl.remove(50);

        assertTrue(this.avl.contains(35));
        assertTrue(this.avl.contains(70));
        assertTrue(this.avl.contains(10));
        assertTrue(this.avl.contains(100));
        assertFalse(this.avl.contains(20));
        assertFalse(this.avl.contains(50));
        assertFalse(this.avl.contains(-10));
    }

    @Test(timeout = TIMEOUT)
    public void testContainsNull() {

        this.avl.add(10);
        this.avl.add(20);
        this.avl.add(30);

        try {
            this.avl.contains(null);
            fail();
        } catch (IllegalArgumentException e) { }
    }

    @Test(timeout = TIMEOUT)
    public void testClear3() {
        this.avl.add(10);
        this.avl.add(20);
        this.avl.add(30);
        this.avl.add(55);
        this.avl.add(15);

        this.avl.remove(10);
        this.avl.remove(55);

        AVLNode<Integer> node15 = new AVLNode<>(15);
        AVLNode<Integer> node30 = new AVLNode<>(30);
        AVLNode<Integer> node20 = new AVLNode<>(20);
        node20.setHeight(1);
        node20.setLeft(node15);
        node20.setRight(node30);

        assertEquals(3, this.avl.size());
        assertEquals(1, this.avl.height());
        assertTrue(this.nodeEquals(node20, this.avl.getRoot()));

        this.avl.clear();
        assertEquals(0, this.avl.size());
        assertEquals(-1, this.avl.height());
        assertNull(this.avl.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void testWordSearch3() {
        String l1 = "t";
        String l2 = "h";
        String l3 = "y";
        String l4 = "a";
        String l5 = "o";
        String l6 = "w";
        String l7 = "z";
        String l8 = "n";
        String l9 = "p";

        this.wordAvl.add(l1);
        this.wordAvl.add(l2);
        this.wordAvl.add(l3);
        this.wordAvl.add(l4);
        this.wordAvl.add(l5);
        this.wordAvl.add(l6);
        this.wordAvl.add(l7);
        this.wordAvl.add(l8);
        this.wordAvl.add(l9);

        /*
                t
              /   \
             h     y
            / \   / \
           a   o w   z
              / \
             n   p
         */

        String[] word1 = {"z", "y", "t", "h", "o", "n"};
        List<String> search1 = this.wordAvl.wordSearch(word1);
        assertEquals(6, search1.size());
        assertSame(l7, search1.get(0));
        assertSame(l3, search1.get(1));
        assertSame(l1, search1.get(2));
        assertSame(l2, search1.get(3));
        assertSame(l5, search1.get(4));
        assertSame(l8, search1.get(5));

        String[] word2 = {"p", "o", "h", "t", "y"};
        List<String> search2 = this.wordAvl.wordSearch(word2);
        assertEquals(5, search2.size());
        assertSame(l9, search2.get(0));
        assertSame(l5, search2.get(1));
        assertSame(l2, search2.get(2));
        assertSame(l1, search2.get(3));
        assertSame(l3, search2.get(4));

        try {
            String[] word3 = {"p", "y", "t", "h", "o", "n"};
            List<String> search3 = this.wordAvl.wordSearch(word3);
            fail();
        } catch (NoSuchElementException e) { }

        try {
            String[] word4 = {"p", "o", "t"};
            List<String> search4 = this.wordAvl.wordSearch(word4);
            fail();
        } catch (NoSuchElementException e) { }

        try {
            String[] word5 = {"p", "o", "n", "a"};
            List<String> search5 = this.wordAvl.wordSearch(word5);
            fail();
        } catch (NoSuchElementException e) { }

        try {
            String[] word6 = {"w", "y", "l", "t", "h", "e"};
            List<String> search6 = this.wordAvl.wordSearch(word6);
            fail();
        } catch (NoSuchElementException e) { }

    }

    @Test(timeout = TIMEOUT)
    public void testWordSearchNull() {
        try {
            this.avl.wordSearch(null);
            fail();
        } catch (IllegalArgumentException e) { }
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization4() {
        assertEquals(0, tree.size());
        assertNull(tree.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void testConstructor4() {

        /*
             Height
             max(left node’s height, right node’s height) + 1
             where the height of a leaf node is 0 and the
             heights of its null children are -1.
             (From HW pdf pg4)
             BF (Balance Factor)
             Calculated by subtracting the height of the left subtree
             by the height of right subtree.
             (From TreesAVL.pdf pg6)
         */



        /*
              10
              10 : height = 0, BF = (-1) - (-1) = 0
         */
        List<Integer> list = new ArrayList<>();
        list.add(10);
        tree = new AVL<>(list);

        AVLNode<Integer> node = tree.getRoot();

        assertEquals(1, tree.size());
        assertEquals((Integer) 10, node.getData());
        assertEquals(0, node.getHeight());
        assertEquals(0, node.getBalanceFactor());

        /*
               10
              /
             3
             10 : height = 1, BF = (0) - (-1) = 1
             3 : height = 0, BF = (-1) - (-1) = 0
         */

        list.add(3); // list == {10, 3}
        tree = new AVL<>(list);
        node = tree.getRoot();

        assertEquals(2, tree.size());
        assertEquals((Integer) 10, node.getData());
        assertEquals(1, node.getHeight());
        assertEquals(1, node.getBalanceFactor());

        assertEquals((Integer) 3, node.getLeft().getData());
        assertEquals(0, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        /*
              10 <-- BF = (1) - (-1) = 2
              /
             3
            /
           1
           Right rotate will occur.
               3
              / \
             1   10
             3 : height = 1, BF = (0) - (0) = 0
             1 : height = 0, BF = (-1) - (-1) = 0
             10 : height = 0, BF = (-1) - (-1) = 0
         */

        list.add(1); // list == {10, 3, 1}
        tree = new AVL<>(list);
        node = tree.getRoot();

        assertEquals(3, tree.size());
        assertEquals((Integer) 3, node.getData());
        assertEquals(1, node.getHeight());
        assertEquals(0, node.getBalanceFactor());

        assertEquals((Integer) 1, node.getLeft().getData());
        assertEquals(0, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 10, node.getRight().getData());
        assertEquals(0, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());


        /*
               3
              / \
             1   10
                  \
                   15
             3 : height = 2, BF = (0) - (1) = -1
             1 : height = 0, BF = (-1) - (-1) = 0
             10 : height = 1, BF = (-1) - (0) = -1
             15 : height = 0, BF = (-1) - (-1) = 0
         */

        list.add(15); // list == {10, 3, 1, 15}
        tree = new AVL<>(list);
        node = tree.getRoot();

        assertEquals(4, tree.size());
        assertEquals((Integer) 3, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(-1, node.getBalanceFactor());

        assertEquals((Integer) 1, node.getLeft().getData());
        assertEquals(0, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 10, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(-1, node.getRight().getBalanceFactor());

        assertEquals((Integer) 15, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());

        /*
               3
              / \
             1   10 <- BF = (-1) - (1) = -2
                  \
                   15
                    \
                     20
           left rotation will occur.
               3
              / \
             1   15
                 /\
                10 20
             3 : height = 2, BF = (0) - (1) = -1
             1 : height = 0, BF = (-1) - (-1) = 0
             15 : height = 1, BF = (0) - (0) = 0
             10 : height = 0, BF = (-1) - (-1) = 0
             20 : height = 0, BF = (-1) - (-1) = 0
         */

        list.add(20); // list == {10, 3, 1, 15, 20}
        tree = new AVL<>(list);
        node = tree.getRoot();

        assertEquals(5, tree.size());
        assertEquals((Integer) 3, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(-1, node.getBalanceFactor());

        assertEquals((Integer) 1, node.getLeft().getData());
        assertEquals(0, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 15, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());

        assertEquals((Integer) 10, node.getRight().getLeft().getData());
        assertEquals(0, node.getRight().getLeft().getHeight());
        assertEquals(0, node.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 20, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());

        //Rotation Test

        /*
        LEFT ROTATION INITIAL SETTING
        this is basically same as TreeAVL.pdf example (pg14)
        37 == A
        48 == B
               38
              / \
             37  49
                 /\
                48 85
             38 : height = 2, BF = (0) - (1) = -1
             37 : height = 0, BF = (-1) - (-1) = 0
             49 : height = 1, BF = (0) - (0) = 0
             48 : height = 0, BF = (-1) - (-1) = 0
             85 : height = 0, BF = (-1) - (-1) = 0
         */
        list = new ArrayList<>();
        list.add(38);
        list.add(37);
        list.add(49);
        list.add(48);
        list.add(85); //list = {38, 37, 49, 48, 85}
        tree = new AVL<>(list);
        node = tree.getRoot();
        //Check setting before the test.

        assertEquals(5, tree.size());
        assertEquals((Integer) 38, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(-1, node.getBalanceFactor());

        assertEquals((Integer) 37, node.getLeft().getData());
        assertEquals(0, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 49, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());

        assertEquals((Integer) 48, node.getRight().getLeft().getData());
        assertEquals(0, node.getRight().getLeft().getHeight());
        assertEquals(0, node.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 85, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());


        /*
        LEFT ROTATION
        this is basically same as TreeAVL.pdf example (pg14)
        37 == A
        48 == B
        100 == D
               38
              / \
             37  49
                 /\
                48 85
                    \
                    100 <-- add
                    LEFT ROTATION OCCUR
                49
               / \
              38  85
             / \    \
            37 48   100
             49 : height = 2, BF = (1) - (1) = 0
             38 : height = 1, BF = (0) - (0) = 0
             37 : height = 0, BF = (-1) - (-1) = 0
             48 : height = 0, BF = (-1) - (-1) = 0
             85 : height = 1, BF = (-1) - (0) = -1
             100: height = 0, BF = (-1) - (-1) = 0
         */

        list.add(100); //list = {38, 37, 49, 48, 85, 100}
        tree = new AVL<>(list);
        node = tree.getRoot();

        assertEquals(6, tree.size());
        assertEquals((Integer) 49, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(0, node.getBalanceFactor());

        assertEquals((Integer) 38, node.getLeft().getData());
        assertEquals(1, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 37, node.getLeft().getLeft().getData());
        assertEquals(0, node.getLeft().getLeft().getHeight());
        assertEquals(0, node.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 48, node.getLeft().getRight().getData());
        assertEquals(0, node.getLeft().getRight().getHeight());
        assertEquals(0, node.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 85, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(-1, node.getRight().getBalanceFactor());

        assertEquals((Integer) 100, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());


        /*
        RIGHT ROTATION INITIAL SETTING
        this is basically same as TreeAVL.pdf example (pg17)
        100 == D
        50  == C
               85
              /  \
             49  100
            / \
           38  50
             85 : height = 2, BF = (1) - (0) = 1
             49 : height = 1, BF = (0) - (0) = 0
             38 : height = 0, BF = (-1) - (-1) = 0
             50 : height = 0, BF = (-1) - (-1) = 0
             100: height = 0, BF = (-1) - (-1) = 0
         */
        list = new ArrayList<>();
        list.add(85);
        list.add(49);
        list.add(100);
        list.add(38);
        list.add(50); //list = {85, 49, 100, 38, 50}
        tree = new AVL<>(list);
        node = tree.getRoot();
        //Check setting before the test.

        assertEquals(5, tree.size());
        assertEquals((Integer) 85, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(1, node.getBalanceFactor());

        assertEquals((Integer) 49, node.getLeft().getData());
        assertEquals(1, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 38, node.getLeft().getLeft().getData());
        assertEquals(0, node.getLeft().getLeft().getHeight());
        assertEquals(0, node.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 50, node.getLeft().getRight().getData());
        assertEquals(0, node.getLeft().getRight().getHeight());
        assertEquals(0, node.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 100, node.getRight().getData());
        assertEquals(0, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());


        /*
        RIGHT ROTATION
        this is basically same as TreeAVL.pdf example (pg14)
        100 == D
        50 == C
        37 == A
               85
              /  \
             49  100
            / \
           38  50
          /
         37 <--will add
                    RIGHT ROTATION OCCUR
                49
               / \
              38  85
             /    /\
            37  50 100
             49 : height = 2, BF = (1) - (1) = 0
             38 : height = 1, BF = (0) - (-1) = 1
             37 : height = 0, BF = (-1) - (-1) = 0
             85 : height = 1, BF = (0) - (0) = 0
             50 : height = 0, BF = (-1) - (-1) = 0
             100: height = 0, BF = (-1) - (-1) = 0
         */

        list.add(37); //list = {85, 49, 100, 38, 50, 37}
        tree = new AVL<>(list);
        node = tree.getRoot();

        assertEquals(6, tree.size());
        assertEquals((Integer) 49, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(0, node.getBalanceFactor());

        assertEquals((Integer) 38, node.getLeft().getData());
        assertEquals(1, node.getLeft().getHeight());
        assertEquals(1, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 37, node.getLeft().getLeft().getData());
        assertEquals(0, node.getLeft().getLeft().getHeight());
        assertEquals(0, node.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 85, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());

        assertEquals((Integer) 50, node.getRight().getLeft().getData());
        assertEquals(0, node.getRight().getLeft().getHeight());
        assertEquals(0, node.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 100, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());


        /*
        LEFT-RIGHT ROTATION INITIAL SETTING
        this is basically same as TreeAVL.pdf example (pg20)
        37  == A
        100 == D
               85
              /  \
             38  100
            / \
           37  49
             85 : height = 2, BF = (1) - (0) = 1
             38 : height = 1, BF = (0) - (0) = 0
             37 : height = 0, BF = (-1) - (-1) = 0
             49 : height = 0, BF = (-1) - (-1) = 0
             100: height = 0, BF = (-1) - (-1) = 0
         */
        list = new ArrayList<>();
        list.add(85);
        list.add(38);
        list.add(100);
        list.add(37);
        list.add(49); //list = {85, 38, 100, 37, 49}
        tree = new AVL<>(list);
        node = tree.getRoot();
        //Check setting before the test.

        assertEquals(5, tree.size());
        assertEquals((Integer) 85, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(1, node.getBalanceFactor());

        assertEquals((Integer) 38, node.getLeft().getData());
        assertEquals(1, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 37, node.getLeft().getLeft().getData());
        assertEquals(0, node.getLeft().getLeft().getHeight());
        assertEquals(0, node.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 49, node.getLeft().getRight().getData());
        assertEquals(0, node.getLeft().getRight().getHeight());
        assertEquals(0, node.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 100, node.getRight().getData());
        assertEquals(0, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());


        /*
        LEFT-RIGHT ROTATION
        this is basically same as TreeAVL.pdf example (pg14)
        37  == A
        100 == D
        50  == C
               85
              /  \
             38  100
            / \
           37  49
                \
                50 <--will add
          LEFT-RIGHT ROTATION OCCUR
                49
               / \
              38  85
             /    /\
            37  50 100
             49 : height = 2, BF = (1) - (1) = 0
             38 : height = 1, BF = (0) - (-1) = 1
             37 : height = 0, BF = (-1) - (-1) = 0
             85 : height = 1, BF = (0) - (0) = 0
             50 : height = 0, BF = (-1) - (-1) = 0
             100: height = 0, BF = (-1) - (-1) = 0
         */

        list.add(50); //list = {85, 38, 100, 37, 49, 50}
        tree = new AVL<>(list);
        node = tree.getRoot();

        assertEquals(6, tree.size());
        assertEquals((Integer) 49, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(0, node.getBalanceFactor());

        assertEquals((Integer) 38, node.getLeft().getData());
        assertEquals(1, node.getLeft().getHeight());
        assertEquals(1, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 37, node.getLeft().getLeft().getData());
        assertEquals(0, node.getLeft().getLeft().getHeight());
        assertEquals(0, node.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 85, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());

        assertEquals((Integer) 50, node.getRight().getLeft().getData());
        assertEquals(0, node.getRight().getLeft().getHeight());
        assertEquals(0, node.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 100, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());

        /*
        RIGHT-LEFT ROTATION INITIAL SETTING
        this is basically same as TreeAVL.pdf example (pg23)
        37  == A
        100 == D
               38
              /  \
             37  85
                 /\
               49 100
             38 : height = 2, BF = (0) - (1) = -1
             37 : height = 0, BF = (-1) - (-1) = 0
             85 : height = 1, BF = (0) - (0) = 0
             49 : height = 0, BF = (-1) - (-1) = 0
             100: height = 0, BF = (-1) - (-1) = 0
         */
        list = new ArrayList<>();
        list.add(38);
        list.add(37);
        list.add(85);
        list.add(49);
        list.add(100); //list = {38, 37, 85, 49, 100}
        tree = new AVL<>(list);
        node = tree.getRoot();
        //Check setting before the test.

        assertEquals(5, tree.size());
        assertEquals((Integer) 38, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(-1, node.getBalanceFactor());

        assertEquals((Integer) 37, node.getLeft().getData());
        assertEquals(0, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 85, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());

        assertEquals((Integer) 49, node.getRight().getLeft().getData());
        assertEquals(0, node.getRight().getLeft().getHeight());
        assertEquals(0, node.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 100, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());


        /*
        RIGHT-LEFT ROTATION
        this is basically same as TreeAVL.pdf example (pg14)
        37  == A
        100 == D
        48  == B
               38
              /  \
             37  85
                 /\
               49 100
               /
              48 <-- will add
          RIGHT-LEFT ROTATION OCCUR
                49
               / \
              38  85
             /\    \
            37 48  100
             49 : height = 2, BF = (1) - (1) = 0
             38 : height = 1, BF = (0) - (0) = 0
             37 : height = 0, BF = (-1) - (-1) = 0
             48 : height = 0, BF = (-1) - (-1) = 0
             85 : height = 1, BF = (-1) - (0) = -1
             100: height = 0, BF = (-1) - (-1) = 0
         */

        list.add(48); //list = {38, 37, 85, 49, 100, 48}
        tree = new AVL<>(list);
        node = tree.getRoot();

        assertEquals(6, tree.size());
        assertEquals((Integer) 49, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(0, node.getBalanceFactor());

        assertEquals((Integer) 38, node.getLeft().getData());
        assertEquals(1, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 37, node.getLeft().getLeft().getData());
        assertEquals(0, node.getLeft().getLeft().getHeight());
        assertEquals(0, node.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 48, node.getLeft().getRight().getData());
        assertEquals(0, node.getLeft().getRight().getHeight());
        assertEquals(0, node.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 85, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(-1, node.getRight().getBalanceFactor());

        assertEquals((Integer) 100, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());

    }

    @Test(timeout = TIMEOUT)
    public void testConstructorException() {
        try {
            tree = new AVL<>(null);
            fail();
        } catch (IllegalArgumentException e) {
        }

        try {
            List<Integer> list = new ArrayList<>();
            list.add(10);
            list.add(null);
            list.add(20);
            tree = new AVL<>(list);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test(timeout = TIMEOUT)
    public void testAdd4() {

        /*
             Height
             max(left node’s height, right node’s height) + 1
             where the height of a leaf node is 0 and the
             heights of its null children are -1.
             (From HW pdf pg4)
             BF (Balance Factor)
             Calculated by subtracting the height of the left subtree
             by the height of right subtree.
             (From TreesAVL.pdf pg6)
         */



        /*
              10
              10 : height = 0, BF = (-1) - (-1) = 0
         */

        tree.add(10);

        AVLNode<Integer> node = tree.getRoot();

        assertEquals(1, tree.size());
        assertEquals((Integer) 10, node.getData());
        assertEquals(0, node.getHeight());
        assertEquals(0, node.getBalanceFactor());

        /*
               10
              /
             3
             10 : height = 1, BF = (0) - (-1) = 1
             3 : height = 0, BF = (-1) - (-1) = 0
         */

        tree.add(3);

        node = tree.getRoot();

        assertEquals(2, tree.size());
        assertEquals((Integer) 10, node.getData());
        assertEquals(1, node.getHeight());
        assertEquals(1, node.getBalanceFactor());

        assertEquals((Integer) 3, node.getLeft().getData());
        assertEquals(0, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        /*
              10 <-- BF = (1) - (-1) = 2
              /
             3
            /
           1
           Right rotate will occur.
               3
              / \
             1   10
             3 : height = 1, BF = (0) - (0) = 0
             1 : height = 0, BF = (-1) - (-1) = 0
             10 : height = 0, BF = (-1) - (-1) = 0
         */

        tree.add(1);

        node = tree.getRoot();

        assertEquals(3, tree.size());
        assertEquals((Integer) 3, node.getData());
        assertEquals(1, node.getHeight());
        assertEquals(0, node.getBalanceFactor());

        assertEquals((Integer) 1, node.getLeft().getData());
        assertEquals(0, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 10, node.getRight().getData());
        assertEquals(0, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());


        /*
               3
              / \
             1   10
                  \
                   15
             3 : height = 2, BF = (0) - (1) = -1
             1 : height = 0, BF = (-1) - (-1) = 0
             10 : height = 1, BF = (-1) - (0) = -1
             15 : height = 0, BF = (-1) - (-1) = 0
         */

        tree.add(15);

        node = tree.getRoot();

        assertEquals(4, tree.size());
        assertEquals((Integer) 3, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(-1, node.getBalanceFactor());

        assertEquals((Integer) 1, node.getLeft().getData());
        assertEquals(0, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 10, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(-1, node.getRight().getBalanceFactor());

        assertEquals((Integer) 15, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());

        /*
               3
              / \
             1   10 <- BF = (-1) - (1) = -2
                  \
                   15
                    \
                     20
           left rotation will occur.
               3
              / \
             1   15
                 /\
                10 20
             3 : height = 2, BF = (0) - (1) = -1
             1 : height = 0, BF = (-1) - (-1) = 0
             15 : height = 1, BF = (0) - (0) = 0
             10 : height = 0, BF = (-1) - (-1) = 0
             20 : height = 0, BF = (-1) - (-1) = 0
         */

        tree.add(20);

        node = tree.getRoot();

        assertEquals(5, tree.size());
        assertEquals((Integer) 3, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(-1, node.getBalanceFactor());

        assertEquals((Integer) 1, node.getLeft().getData());
        assertEquals(0, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 15, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());

        assertEquals((Integer) 10, node.getRight().getLeft().getData());
        assertEquals(0, node.getRight().getLeft().getHeight());
        assertEquals(0, node.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 20, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());

        //If the data is already in the tree. (15 already exist)
        tree.add(15);

        node = tree.getRoot();

        assertEquals(5, tree.size());
        assertEquals((Integer) 3, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(-1, node.getBalanceFactor());

        assertEquals((Integer) 1, node.getLeft().getData());
        assertEquals(0, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 15, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());

        assertEquals((Integer) 10, node.getRight().getLeft().getData());
        assertEquals(0, node.getRight().getLeft().getHeight());
        assertEquals(0, node.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 20, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());

        //If the data is already in the tree. (20, already exist)
        tree.add(20);

        node = tree.getRoot();

        assertEquals(5, tree.size());
        assertEquals((Integer) 3, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(-1, node.getBalanceFactor());

        assertEquals((Integer) 1, node.getLeft().getData());
        assertEquals(0, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 15, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());

        assertEquals((Integer) 10, node.getRight().getLeft().getData());
        assertEquals(0, node.getRight().getLeft().getHeight());
        assertEquals(0, node.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 20, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());


        //Rotation Test

        /*
        LEFT ROTATION INITIAL SETTING
        this is basically same as TreeAVL.pdf example (pg14)
        37 == A
        48 == B
               38
              / \
             37  49
                 /\
                48 85
             38 : height = 2, BF = (0) - (1) = -1
             37 : height = 0, BF = (-1) - (-1) = 0
             49 : height = 1, BF = (0) - (0) = 0
             48 : height = 0, BF = (-1) - (-1) = 0
             85 : height = 0, BF = (-1) - (-1) = 0
         */
        tree = new AVL<>();
        tree.add(38);
        tree.add(37);
        tree.add(49);
        tree.add(48);
        tree.add(85);

        node = tree.getRoot();
        //Check setting before the test.

        assertEquals(5, tree.size());
        assertEquals((Integer) 38, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(-1, node.getBalanceFactor());

        assertEquals((Integer) 37, node.getLeft().getData());
        assertEquals(0, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 49, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());

        assertEquals((Integer) 48, node.getRight().getLeft().getData());
        assertEquals(0, node.getRight().getLeft().getHeight());
        assertEquals(0, node.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 85, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());


        /*
        LEFT ROTATION
        this is basically same as TreeAVL.pdf example (pg14)
        37 == A
        48 == B
        100 == D
               38
              / \
             37  49
                 /\
                48 85
                    \
                    100 <-- add
                    LEFT ROTATION OCCUR
                49
               / \
              38  85
             / \    \
            37 48   100
             49 : height = 2, BF = (1) - (1) = 0
             38 : height = 1, BF = (0) - (0) = 0
             37 : height = 0, BF = (-1) - (-1) = 0
             48 : height = 0, BF = (-1) - (-1) = 0
             85 : height = 1, BF = (-1) - (0) = -1
             100: height = 0, BF = (-1) - (-1) = 0
         */

        tree.add(100);
        node = tree.getRoot();

        assertEquals(6, tree.size());
        assertEquals((Integer) 49, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(0, node.getBalanceFactor());

        assertEquals((Integer) 38, node.getLeft().getData());
        assertEquals(1, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 37, node.getLeft().getLeft().getData());
        assertEquals(0, node.getLeft().getLeft().getHeight());
        assertEquals(0, node.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 48, node.getLeft().getRight().getData());
        assertEquals(0, node.getLeft().getRight().getHeight());
        assertEquals(0, node.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 85, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(-1, node.getRight().getBalanceFactor());

        assertEquals((Integer) 100, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());


        /*
        RIGHT ROTATION INITIAL SETTING
        this is basically same as TreeAVL.pdf example (pg17)
        100 == D
        50  == C
               85
              /  \
             49  100
            / \
           38  50
             85 : height = 2, BF = (1) - (0) = 1
             49 : height = 1, BF = (0) - (0) = 0
             38 : height = 0, BF = (-1) - (-1) = 0
             50 : height = 0, BF = (-1) - (-1) = 0
             100: height = 0, BF = (-1) - (-1) = 0
         */
        tree = new AVL<>();
        tree.add(85);
        tree.add(49);
        tree.add(100);
        tree.add(38);
        tree.add(50);
        node = tree.getRoot();
        //Check setting before the test.

        assertEquals(5, tree.size());
        assertEquals((Integer) 85, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(1, node.getBalanceFactor());

        assertEquals((Integer) 49, node.getLeft().getData());
        assertEquals(1, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 38, node.getLeft().getLeft().getData());
        assertEquals(0, node.getLeft().getLeft().getHeight());
        assertEquals(0, node.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 50, node.getLeft().getRight().getData());
        assertEquals(0, node.getLeft().getRight().getHeight());
        assertEquals(0, node.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 100, node.getRight().getData());
        assertEquals(0, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());


        /*
        RIGHT ROTATION
        this is basically same as TreeAVL.pdf example (pg14)
        100 == D
        50 == C
        37 == A
               85
              /  \
             49  100
            / \
           38  50
          /
         37 <--will add
                    RIGHT ROTATION OCCUR
                49
               / \
              38  85
             /    /\
            37  50 100
             49 : height = 2, BF = (1) - (1) = 0
             38 : height = 1, BF = (0) - (-1) = 1
             37 : height = 0, BF = (-1) - (-1) = 0
             85 : height = 1, BF = (0) - (0) = 0
             50 : height = 0, BF = (-1) - (-1) = 0
             100: height = 0, BF = (-1) - (-1) = 0
         */

        tree.add(37);
        node = tree.getRoot();

        assertEquals(6, tree.size());
        assertEquals((Integer) 49, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(0, node.getBalanceFactor());

        assertEquals((Integer) 38, node.getLeft().getData());
        assertEquals(1, node.getLeft().getHeight());
        assertEquals(1, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 37, node.getLeft().getLeft().getData());
        assertEquals(0, node.getLeft().getLeft().getHeight());
        assertEquals(0, node.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 85, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());

        assertEquals((Integer) 50, node.getRight().getLeft().getData());
        assertEquals(0, node.getRight().getLeft().getHeight());
        assertEquals(0, node.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 100, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());


        /*
        LEFT-RIGHT ROTATION INITIAL SETTING
        this is basically same as TreeAVL.pdf example (pg20)
        37  == A
        100 == D
               85
              /  \
             38  100
            / \
           37  49
             85 : height = 2, BF = (1) - (0) = 1
             38 : height = 1, BF = (0) - (0) = 0
             37 : height = 0, BF = (-1) - (-1) = 0
             49 : height = 0, BF = (-1) - (-1) = 0
             100: height = 0, BF = (-1) - (-1) = 0
         */
        tree = new AVL<>();
        tree.add(85);
        tree.add(38);
        tree.add(100);
        tree.add(37);
        tree.add(49);
        node = tree.getRoot();
        //Check setting before the test.

        assertEquals(5, tree.size());
        assertEquals((Integer) 85, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(1, node.getBalanceFactor());

        assertEquals((Integer) 38, node.getLeft().getData());
        assertEquals(1, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 37, node.getLeft().getLeft().getData());
        assertEquals(0, node.getLeft().getLeft().getHeight());
        assertEquals(0, node.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 49, node.getLeft().getRight().getData());
        assertEquals(0, node.getLeft().getRight().getHeight());
        assertEquals(0, node.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 100, node.getRight().getData());
        assertEquals(0, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());


        /*
        LEFT-RIGHT ROTATION
        this is basically same as TreeAVL.pdf example (pg14)
        37  == A
        100 == D
        50  == C
               85
              /  \
             38  100
            / \
           37  49
                \
                50 <--will add
          LEFT-RIGHT ROTATION OCCUR
                49
               / \
              38  85
             /    /\
            37  50 100
             49 : height = 2, BF = (1) - (1) = 0
             38 : height = 1, BF = (0) - (-1) = 1
             37 : height = 0, BF = (-1) - (-1) = 0
             85 : height = 1, BF = (0) - (0) = 0
             50 : height = 0, BF = (-1) - (-1) = 0
             100: height = 0, BF = (-1) - (-1) = 0
         */

        tree.add(50);
        node = tree.getRoot();

        assertEquals(6, tree.size());
        assertEquals((Integer) 49, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(0, node.getBalanceFactor());

        assertEquals((Integer) 38, node.getLeft().getData());
        assertEquals(1, node.getLeft().getHeight());
        assertEquals(1, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 37, node.getLeft().getLeft().getData());
        assertEquals(0, node.getLeft().getLeft().getHeight());
        assertEquals(0, node.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 85, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());

        assertEquals((Integer) 50, node.getRight().getLeft().getData());
        assertEquals(0, node.getRight().getLeft().getHeight());
        assertEquals(0, node.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 100, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());

        /*
        RIGHT-LEFT ROTATION INITIAL SETTING
        this is basically same as TreeAVL.pdf example (pg23)
        37  == A
        100 == D
               38
              /  \
             37  85
                 /\
               49 100
             38 : height = 2, BF = (0) - (1) = -1
             37 : height = 0, BF = (-1) - (-1) = 0
             85 : height = 1, BF = (0) - (0) = 0
             49 : height = 0, BF = (-1) - (-1) = 0
             100: height = 0, BF = (-1) - (-1) = 0
         */
        tree = new AVL<>();
        tree.add(38);
        tree.add(37);
        tree.add(85);
        tree.add(49);
        tree.add(100);

        node = tree.getRoot();
        //Check setting before the test.

        assertEquals(5, tree.size());
        assertEquals((Integer) 38, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(-1, node.getBalanceFactor());

        assertEquals((Integer) 37, node.getLeft().getData());
        assertEquals(0, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 85, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());

        assertEquals((Integer) 49, node.getRight().getLeft().getData());
        assertEquals(0, node.getRight().getLeft().getHeight());
        assertEquals(0, node.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 100, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());


        /*
        RIGHT-LEFT ROTATION
        this is basically same as TreeAVL.pdf example (pg14)
        37  == A
        100 == D
        48  == B
               38
              /  \
             37  85
                 /\
               49 100
               /
              48 <-- will add
          RIGHT-LEFT ROTATION OCCUR
                49
               / \
              38  85
             /\    \
            37 48  100
             49 : height = 2, BF = (1) - (1) = 0
             38 : height = 1, BF = (0) - (0) = 0
             37 : height = 0, BF = (-1) - (-1) = 0
             48 : height = 0, BF = (-1) - (-1) = 0
             85 : height = 1, BF = (-1) - (0) = -1
             100: height = 0, BF = (-1) - (-1) = 0
         */

        tree.add(48);
        node = tree.getRoot();

        assertEquals(6, tree.size());
        assertEquals((Integer) 49, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(0, node.getBalanceFactor());

        assertEquals((Integer) 38, node.getLeft().getData());
        assertEquals(1, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 37, node.getLeft().getLeft().getData());
        assertEquals(0, node.getLeft().getLeft().getHeight());
        assertEquals(0, node.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 48, node.getLeft().getRight().getData());
        assertEquals(0, node.getLeft().getRight().getHeight());
        assertEquals(0, node.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 85, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(-1, node.getRight().getBalanceFactor());

        assertEquals((Integer) 100, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testAddException() {
        try {
            tree.add(null);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test(timeout = TIMEOUT)
    public void testRemove4() {


        //ROTATION CHECK (REMOVE) [RIGHT ROTATION]
        //WILL REMOVE CHILD NODE FOR ROTATE TEST

        /*
              50
             /  \
            25   75
           / \   / \
          12 37 62 100
         /\
        6 18
        */
        Integer i50 = 50;
        Integer i25 = 25;
        Integer i75 = 75;
        Integer i12 = 12;
        Integer i37 = 37;
        Integer i62 = 62;
        Integer i100 = 100;
        Integer i6 = 6;
        Integer i18 = 18;
        tree.add(i50);
        tree.add(i25);
        tree.add(i75);
        tree.add(i12);
        tree.add(i37);
        tree.add(i62);
        tree.add(i100);
        tree.add(i6);
        tree.add(i18);
        //ASSUME THAT YOUR ADD METHOD WORKS PROPERLY.

        /*
              50
             /  \
            25   75
           / \   / \
          12(37)62 100
         /\
        6 18
        -->
              50
             /  \
 BF = 2 --> 25   75
           /     / \
BF = 0--> 12    62 100
         /\
        6 18
        Right rotation will occur
        "If the signs of the balance factors are the same or 'the child has BF 0',
        then do a single rotation. If the signs are opposite, do a double rotation."
        (PIAZZA POST @1162)
       -->
              50
             /  \
            12   75
           /\    / \
          6 25  62 100
            /
           18
        */

        assertSame(i37, tree.remove(37));
        assertEquals(8, tree.size());
        AVLNode<Integer> root = tree.getRoot();

        assertEquals((Integer) 50, root.getData());
        assertEquals(3, root.getHeight());
        assertEquals(1, root.getBalanceFactor());

        assertEquals((Integer) 12, root.getLeft().getData());
        assertEquals(2, root.getLeft().getHeight());
        assertEquals(-1, root.getLeft().getBalanceFactor());

        assertEquals((Integer) 75, root.getRight().getData());
        assertEquals(1, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());

        assertEquals((Integer) 6, root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 25, root.getLeft().getRight().getData());
        assertEquals(1, root.getLeft().getRight().getHeight());
        assertEquals(1, root.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 62, root.getRight().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 100, root.getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getBalanceFactor());

        assertEquals((Integer) 18, root.getLeft().getRight().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());




        //ROTATION CHECK (REMOVE) [LEFT ROTATION]
        //WILL REMOVE CHILD NODE FOR ROTATE TEST

        /*
              50
             /  \
            12   75
           /\    / \
          6 25  62 100
            /
           18
         */

        Integer i99 = 99;
        Integer i101 = 101;
        tree.add(i99);
        tree.add(i101);

        //ASSUME THAT YOUR ADD METHOD WORKS PROPERLY.

        /*
              50
             /  \
            12   75
           /\   /  \
          6 25 (62) 100
            /        /\
           18      99 101
           we will remove (62)
         -->
              50
             /  \
            12   75 <-- BF = -2
           /\      \
          6 25      100 <-- BF = 0
            /        /\
           18      99 101
           Left Rotate will occur
           -->
              50
             /   \
            12    100
           /\     / \
          6 25   75 101
            /      \
           18      99
         */

        assertSame(i62, tree.remove(62));
        root = tree.getRoot();
        assertEquals(9, tree.size());

        assertEquals((Integer) 50, root.getData());
        assertEquals(3, root.getHeight());
        assertEquals(0, root.getBalanceFactor());

        assertEquals((Integer) 12, root.getLeft().getData());
        assertEquals(2, root.getLeft().getHeight());
        assertEquals(-1, root.getLeft().getBalanceFactor());

        assertEquals((Integer) 100, root.getRight().getData());
        assertEquals(2, root.getRight().getHeight());
        assertEquals(1, root.getRight().getBalanceFactor());

        assertEquals((Integer) 6, root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 25, root.getLeft().getRight().getData());
        assertEquals(1, root.getLeft().getRight().getHeight());
        assertEquals(1, root.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 75, root.getRight().getLeft().getData());
        assertEquals(1, root.getRight().getLeft().getHeight());
        assertEquals(-1, root.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 101, root.getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getBalanceFactor());

        assertEquals((Integer) 18, root.getLeft().getRight().getLeft().getData());
        assertEquals(0, root.getLeft().getRight().getLeft().getHeight());
        assertEquals(0, root.getLeft().getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 99, root.getRight().getLeft().getRight().getData());
        assertEquals(0, root.getRight().getLeft().getRight().getHeight());
        assertEquals(0, root.getRight().getLeft().getRight().getBalanceFactor());



        //ROTATION CHECK (REMOVE) [LEFTRIGHT ROTATION]
        //WILL REMOVE CHILD NODE FOR ROTATE TEST

        /*
              50
             /   \
            12    100
           /\     / \
          6 25   75 (101)
            /      \
           18      99
           we will remove (101)
         -->
              50
             /   \
            12    100 <-- BF = 2
           /\     /
          6 25   75 <-- BF = -1
            /      \
           18      99 <-- BF = 0
           Left Right Rotate will occur
           -->
               50
             /   \
            12    99
           /\     / \
          6 25   75 100
            /
           18
         */

        assertSame(i101, tree.remove(101));
        root = tree.getRoot();
        assertEquals(8, tree.size());

        assertEquals((Integer) 50, root.getData());
        assertEquals(3, root.getHeight());
        assertEquals(1, root.getBalanceFactor());

        assertEquals((Integer) 12, root.getLeft().getData());
        assertEquals(2, root.getLeft().getHeight());
        assertEquals(-1, root.getLeft().getBalanceFactor());

        assertEquals((Integer) 99, root.getRight().getData());
        assertEquals(1, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());

        assertEquals((Integer) 6, root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 25, root.getLeft().getRight().getData());
        assertEquals(1, root.getLeft().getRight().getHeight());
        assertEquals(1, root.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 75, root.getRight().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 100, root.getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getBalanceFactor());

        assertEquals((Integer) 18, root.getLeft().getRight().getLeft().getData());
        assertEquals(0, root.getLeft().getRight().getLeft().getHeight());
        assertEquals(0, root.getLeft().getRight().getLeft().getBalanceFactor());


        //ROTATION CHECK (REMOVE) [RIGHTLEFT ROTATION]
        //WILL REMOVE CHILD NODE FOR ROTATE TEST

        /*
               50
             /   \
            12    99
           /\     / \
         (6) 25   75 100
            /
           18
           we will remove (6)
         -->
               50
             /   \
 BF = -2 -->12    99
             \    / \
   BF = 1 -->25  75 100
             /
 BF = 0 -->18
           Left Right Rotate will occur
           -->
               50
             /   \
           18     99
           /\     / \
         12 25   75 100
         */

        assertSame(i6, tree.remove(6));
        root = tree.getRoot();
        assertEquals(7, tree.size());

        assertEquals((Integer) 50, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(0, root.getBalanceFactor());

        assertEquals((Integer) 18, root.getLeft().getData());
        assertEquals(1, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());

        assertEquals((Integer) 99, root.getRight().getData());
        assertEquals(1, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());

        assertEquals((Integer) 12, root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 25, root.getLeft().getRight().getData());
        assertEquals(0, root.getLeft().getRight().getHeight());
        assertEquals(0, root.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 75, root.getRight().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 100, root.getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getBalanceFactor());



        // MAKING HARDER
        // PREDECESSOR TEST

        //ROTATION CHECK (REMOVE) [RIGHTLEFT ROTATION]
        //WILL REMOVE CHILD NODE FOR ROTATE TEST

        /*
               50
             /   \
           18     99
           /\     / \
         12 25   75 100
         */

        Integer i11 = 11;
        Integer i13 = 13;
        Integer i24 = 24;
        Integer i26 = 26;
        Integer i74 = 74;
        Integer i76 = 76;
        Integer i105 = 105;
        tree.add(i11);
        tree.add(i13);
        tree.add(i24);
        tree.add(i26);
        tree.add(i74);
        tree.add(i76);
        tree.add(i105);

        //ASSUME THAT ADD METHOD WORKS PROPERLY

        /*
                50
              /    \
            18      99
          /  \      / \
        12   25    75  100
       / \   / \   / \   \
      11 13 24 26 74 76  105
         */




        /*
               (50)
              /    \
            18      99
          /  \      / \
        12   25    75  100
       / \   / \   / \   \
      11 13 24 26 74 76  105
      predecessor = 26
      move 26 to top node.
                26
              /    \
            18      99
          /  \      / \
        12   25    75  100
       / \   /     / \   \
      11 13 24    74 76  105
         */


        assertSame(i50, tree.remove(50));
        root = tree.getRoot();
        assertEquals(13, tree.size());

        assertEquals((Integer) 26, root.getData());
        assertEquals(3, root.getHeight());
        assertEquals(0, root.getBalanceFactor());

        assertEquals((Integer) 18, root.getLeft().getData());
        assertEquals(2, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());

        assertEquals((Integer) 99, root.getRight().getData());
        assertEquals(2, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());

        assertEquals((Integer) 12, root.getLeft().getLeft().getData());
        assertEquals(1, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 25, root.getLeft().getRight().getData());
        assertEquals(1, root.getLeft().getRight().getHeight());
        assertEquals(1, root.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 75, root.getRight().getLeft().getData());
        assertEquals(1, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 100, root.getRight().getRight().getData());
        assertEquals(1, root.getRight().getRight().getHeight());
        assertEquals(-1, root.getRight().getRight().getBalanceFactor());

        assertEquals((Integer) 11, root.getLeft().getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 13, root.getLeft().getLeft().getRight().getData());
        assertEquals(0, root.getLeft().getLeft().getRight().getHeight());
        assertEquals(0, root.getLeft().getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 24, root.getLeft().getRight().getLeft().getData());
        assertEquals(0, root.getLeft().getRight().getLeft().getHeight());
        assertEquals(0, root.getLeft().getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 74, root.getRight().getLeft().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 76, root.getRight().getLeft().getRight().getData());
        assertEquals(0, root.getRight().getLeft().getRight().getHeight());
        assertEquals(0, root.getRight().getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 105, root.getRight().getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getRight().getBalanceFactor());




        /*
               (26)
              /    \
            18      99
          /  \      / \
        12   25    75  100
       / \   /     / \   \
      11 13 24    74 76  105
      predecessor = 25
      move 25 to top node.
                25
              /    \
            18      99
          /  \      / \
        12   24    75  100
       / \         / \   \
      11 13       74 76  105
         */


        assertSame(i26, tree.remove(26));
        root = tree.getRoot();
        assertEquals(12, tree.size());

        assertEquals((Integer) 25, root.getData());
        assertEquals(3, root.getHeight());
        assertEquals(0, root.getBalanceFactor());

        assertEquals((Integer) 18, root.getLeft().getData());
        assertEquals(2, root.getLeft().getHeight());
        assertEquals(1, root.getLeft().getBalanceFactor());

        assertEquals((Integer) 99, root.getRight().getData());
        assertEquals(2, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());

        assertEquals((Integer) 12, root.getLeft().getLeft().getData());
        assertEquals(1, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 24, root.getLeft().getRight().getData());
        assertEquals(0, root.getLeft().getRight().getHeight());
        assertEquals(0, root.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 75, root.getRight().getLeft().getData());
        assertEquals(1, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 100, root.getRight().getRight().getData());
        assertEquals(1, root.getRight().getRight().getHeight());
        assertEquals(-1, root.getRight().getRight().getBalanceFactor());

        assertEquals((Integer) 11, root.getLeft().getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 13, root.getLeft().getLeft().getRight().getData());
        assertEquals(0, root.getLeft().getLeft().getRight().getHeight());
        assertEquals(0, root.getLeft().getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 74, root.getRight().getLeft().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 76, root.getRight().getLeft().getRight().getData());
        assertEquals(0, root.getRight().getLeft().getRight().getHeight());
        assertEquals(0, root.getRight().getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 105, root.getRight().getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getRight().getBalanceFactor());



        /*
               (25)
              /    \
            18      99
          /  \      / \
        12   24    75  100
       / \         / \   \
      11 13       74 76  105
      predecessor = 24
      move 24 to top node.
                24
              /    \
            18      99
           /        / \
         12        75  100
        / \        / \   \
       11 13      74 76  105
       after rotation
                24
              /    \
            12      99
           / \      / \
         11  18    75  100
             /     / \   \
           13     74 76  105
         */


        assertSame(i25, tree.remove(25));
        root = tree.getRoot();
        assertEquals(11, tree.size());

        assertEquals((Integer) 24, root.getData());
        assertEquals(3, root.getHeight());
        assertEquals(0, root.getBalanceFactor());

        assertEquals((Integer) 12, root.getLeft().getData());
        assertEquals(2, root.getLeft().getHeight());
        assertEquals(-1, root.getLeft().getBalanceFactor());

        assertEquals((Integer) 99, root.getRight().getData());
        assertEquals(2, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());

        assertEquals((Integer) 11, root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 18, root.getLeft().getRight().getData());
        assertEquals(1, root.getLeft().getRight().getHeight());
        assertEquals(1, root.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 75, root.getRight().getLeft().getData());
        assertEquals(1, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 100, root.getRight().getRight().getData());
        assertEquals(1, root.getRight().getRight().getHeight());
        assertEquals(-1, root.getRight().getRight().getBalanceFactor());

        assertEquals((Integer) 13, root.getLeft().getRight().getLeft().getData());
        assertEquals(0, root.getLeft().getRight().getLeft().getHeight());
        assertEquals(0, root.getLeft().getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 74, root.getRight().getLeft().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 76, root.getRight().getLeft().getRight().getData());
        assertEquals(0, root.getRight().getLeft().getRight().getHeight());
        assertEquals(0, root.getRight().getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 105, root.getRight().getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getRight().getBalanceFactor());



        /*
               (24)
              /    \
            12      99
           / \      / \
         11  18    75  100
             /     / \   \
           13     74 76  105
      predecessor = 18
      move 18 to top node.
                18
              /    \
            12      99
           / \      / \
         11  13    75  100
                   / \   \
                  74 76  105
         */


        assertSame(i24, tree.remove(24));
        root = tree.getRoot();
        assertEquals(10, tree.size());


        assertEquals((Integer) 18, root.getData());
        assertEquals(3, root.getHeight());
        assertEquals(-1, root.getBalanceFactor());

        assertEquals((Integer) 12, root.getLeft().getData());
        assertEquals(1, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());

        assertEquals((Integer) 99, root.getRight().getData());
        assertEquals(2, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());

        assertEquals((Integer) 11, root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 13, root.getLeft().getRight().getData());
        assertEquals(0, root.getLeft().getRight().getHeight());
        assertEquals(0, root.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 75, root.getRight().getLeft().getData());
        assertEquals(1, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 100, root.getRight().getRight().getData());
        assertEquals(1, root.getRight().getRight().getHeight());
        assertEquals(-1, root.getRight().getRight().getBalanceFactor());

        assertEquals((Integer) 74, root.getRight().getLeft().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 76, root.getRight().getLeft().getRight().getData());
        assertEquals(0, root.getRight().getLeft().getRight().getHeight());
        assertEquals(0, root.getRight().getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 105, root.getRight().getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getRight().getBalanceFactor());


        /*
               (18)
              /    \
            12      99
           / \      / \
         11  13    75  100
                   / \   \
                  74 76  105
      predecessor = 13
      move 13 to top node.
                13
              /    \
            12      99
           /        / \
         11        75  100
                   / \   \
                  74 76  105
         */


        assertSame(i18, tree.remove(18));
        root = tree.getRoot();
        assertEquals(9, tree.size());


        assertEquals((Integer) 13, root.getData());
        assertEquals(3, root.getHeight());
        assertEquals(-1, root.getBalanceFactor());

        assertEquals((Integer) 12, root.getLeft().getData());
        assertEquals(1, root.getLeft().getHeight());
        assertEquals(1, root.getLeft().getBalanceFactor());

        assertEquals((Integer) 99, root.getRight().getData());
        assertEquals(2, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());

        assertEquals((Integer) 11, root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 75, root.getRight().getLeft().getData());
        assertEquals(1, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 100, root.getRight().getRight().getData());
        assertEquals(1, root.getRight().getRight().getHeight());
        assertEquals(-1, root.getRight().getRight().getBalanceFactor());

        assertEquals((Integer) 74, root.getRight().getLeft().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 76, root.getRight().getLeft().getRight().getData());
        assertEquals(0, root.getRight().getLeft().getRight().getHeight());
        assertEquals(0, root.getRight().getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 105, root.getRight().getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getRight().getBalanceFactor());



        /*
               (13)
              /    \
            12      99
           /        / \
         11        75  100
                   / \   \
                  74 76  105
      predecessor = 12
      move 12 to top node.
                12
              /    \
            11      99
                    / \
                   75  100
                   / \   \
                  74 76  105
           ->
                12  99
                / \ / \
               11  75  100
                   / \   \
                  74 76  105
           ->
                   99
                  /  \
                12   100
                / \    \
               11  75  105
                   / \
                  74 76
         */


        assertSame(i13, tree.remove(13));
        root = tree.getRoot();
        assertEquals(8, tree.size());

        assertEquals((Integer) 99, root.getData());
        assertEquals(3, root.getHeight());
        assertEquals(1, root.getBalanceFactor());

        assertEquals((Integer) 12, root.getLeft().getData());
        assertEquals(2, root.getLeft().getHeight());
        assertEquals(-1, root.getLeft().getBalanceFactor());

        assertEquals((Integer) 100, root.getRight().getData());
        assertEquals(1, root.getRight().getHeight());
        assertEquals(-1, root.getRight().getBalanceFactor());

        assertEquals((Integer) 11, root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 75, root.getLeft().getRight().getData());
        assertEquals(1, root.getLeft().getRight().getHeight());
        assertEquals(0, root.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 105, root.getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getBalanceFactor());

        assertEquals((Integer) 74, root.getLeft().getRight().getLeft().getData());
        assertEquals(0, root.getLeft().getRight().getLeft().getHeight());
        assertEquals(0, root.getLeft().getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 76, root.getLeft().getRight().getRight().getData());
        assertEquals(0, root.getLeft().getRight().getRight().getHeight());
        assertEquals(0, root.getLeft().getRight().getRight().getBalanceFactor());


    }

    @Test(timeout = TIMEOUT)
    public void testRemoveException() {

        /*
                50
              /    \
            18      99
          /  \      / \
        12   25    75  100
       / \   / \   / \   \
      11 13 24 26 74 76  105
         */

        Integer i50 = 50;
        Integer i18 = 18;
        Integer i99 = 99;
        Integer i12 = 12;
        Integer i25 = 25;
        Integer i75 = 75;
        Integer i100 = 100;
        Integer i11 = 11;
        Integer i13 = 13;
        Integer i24 = 24;
        Integer i26 = 26;
        Integer i74 = 74;
        Integer i76 = 76;
        Integer i105 = 105;
        Integer i999 = 999;

        tree.add(i50);
        tree.add(i18);
        tree.add(i99);
        tree.add(i12);
        tree.add(i25);
        tree.add(i75);
        tree.add(i100);
        tree.add(i11);
        tree.add(i13);
        tree.add(i24);
        tree.add(i26);
        tree.add(i74);
        tree.add(i76);
        tree.add(i105);

        try {
            tree.remove(null);
            fail();
        } catch (IllegalArgumentException e) {

        }

        try {
            tree.remove(i999);
            fail();
        } catch (NoSuchElementException e) {

        }
    }

    @Test(timeout = TIMEOUT)
    public void testGet4() {

        /*
                50
              /    \
            18      99
          /  \      / \
        12   25    75  100
       / \   / \   / \   \
      11 13 24 26 74 76  105
         */

        Integer i50 = 50;
        Integer i18 = 18;
        Integer i99 = 99;
        Integer i12 = 12;
        Integer i25 = 25;
        Integer i75 = 75;
        Integer i100 = 100;
        Integer i11 = 11;
        Integer i13 = 13;
        Integer i24 = 24;
        Integer i26 = 26;
        Integer i74 = 74;
        Integer i76 = 76;
        Integer i105 = 105;

        tree.add(i50);
        tree.add(i18);
        tree.add(i99);
        tree.add(i12);
        tree.add(i25);
        tree.add(i75);
        tree.add(i100);
        tree.add(i11);
        tree.add(i13);
        tree.add(i24);
        tree.add(i26);
        tree.add(i74);
        tree.add(i76);
        tree.add(i105);

        assertEquals(14, tree.size());

        assertSame(i50, tree.get(50));
        assertSame(i18, tree.get(18));
        assertSame(i99, tree.get(99));
        assertSame(i12, tree.get(12));
        assertSame(i25, tree.get(25));
        assertSame(i75, tree.get(75));
        assertSame(i100, tree.get(100));
        assertSame(i11, tree.get(11));
        assertSame(i13, tree.get(13));
        assertSame(i24, tree.get(24));
        assertSame(i26, tree.get(26));
        assertSame(i74, tree.get(74));
        assertSame(i76, tree.get(76));
        assertSame(i105, tree.get(105));
    }

    @Test(timeout = TIMEOUT)
    public void testGetException() {

        /*
                50
              /    \
            18      99
          /  \      / \
        12   25    75  100
       / \   / \   / \   \
      11 13 24 26 74 76  105
         */

        Integer i50 = 50;
        Integer i18 = 18;
        Integer i99 = 99;
        Integer i12 = 12;
        Integer i25 = 25;
        Integer i75 = 75;
        Integer i100 = 100;
        Integer i11 = 11;
        Integer i13 = 13;
        Integer i24 = 24;
        Integer i26 = 26;
        Integer i74 = 74;
        Integer i76 = 76;
        Integer i105 = 105;
        Integer i999 = 999;

        tree.add(i50);
        tree.add(i18);
        tree.add(i99);
        tree.add(i12);
        tree.add(i25);
        tree.add(i75);
        tree.add(i100);
        tree.add(i11);
        tree.add(i13);
        tree.add(i24);
        tree.add(i26);
        tree.add(i74);
        tree.add(i76);
        tree.add(i105);

        try {
            tree.get(null);
            fail();
        } catch (IllegalArgumentException e) {

        }

        try {
            tree.get(i999);
            fail();
        } catch (NoSuchElementException e) {

        }
    }

    @Test(timeout = TIMEOUT)
    public void testContains4() {

        /*
                50
              /    \
            18      99
          /  \      / \
        12   25    75  100
       / \   / \   / \   \
      11 13 24 26 74 76  105
         */

        Integer i50 = 50;
        Integer i18 = 18;
        Integer i99 = 99;
        Integer i12 = 12;
        Integer i25 = 25;
        Integer i75 = 75;
        Integer i100 = 100;
        Integer i11 = 11;
        Integer i13 = 13;
        Integer i24 = 24;
        Integer i26 = 26;
        Integer i74 = 74;
        Integer i76 = 76;
        Integer i105 = 105;

        tree.add(i50);
        tree.add(i18);
        tree.add(i99);
        tree.add(i12);
        tree.add(i25);
        tree.add(i75);
        tree.add(i100);
        tree.add(i11);
        tree.add(i13);
        tree.add(i24);
        tree.add(i26);
        tree.add(i74);
        tree.add(i76);
        tree.add(i105);

        assertEquals(14, tree.size());

        assertTrue(tree.contains(50));
        assertTrue(tree.contains(18));
        assertTrue(tree.contains(99));
        assertTrue(tree.contains(12));
        assertTrue(tree.contains(25));
        assertTrue(tree.contains(75));
        assertTrue(tree.contains(100));
        assertTrue(tree.contains(11));
        assertTrue(tree.contains(13));
        assertTrue(tree.contains(24));
        assertTrue(tree.contains(26));
        assertTrue(tree.contains(74));
        assertTrue(tree.contains(76));
        assertTrue(tree.contains(105));
        assertFalse(tree.contains(999));
    }

    @Test(timeout = TIMEOUT)
    public void testContainsException() {

        /*
                50
              /    \
            18      99
          /  \      / \
        12   25    75  100
       / \   / \   / \   \
      11 13 24 26 74 76  105
         */

        Integer i50 = 50;
        Integer i18 = 18;
        Integer i99 = 99;
        Integer i12 = 12;
        Integer i25 = 25;
        Integer i75 = 75;
        Integer i100 = 100;
        Integer i11 = 11;
        Integer i13 = 13;
        Integer i24 = 24;
        Integer i26 = 26;
        Integer i74 = 74;
        Integer i76 = 76;
        Integer i105 = 105;
        Integer i999 = 999;

        tree.add(i50);
        tree.add(i18);
        tree.add(i99);
        tree.add(i12);
        tree.add(i25);
        tree.add(i75);
        tree.add(i100);
        tree.add(i11);
        tree.add(i13);
        tree.add(i24);
        tree.add(i26);
        tree.add(i74);
        tree.add(i76);
        tree.add(i105);

        try {
            tree.contains(null);
            fail();
        } catch (IllegalArgumentException e) {

        }

    }

    @Test(timeout = TIMEOUT)
    public void testHeight4() {

        Integer i50 = 50;
        Integer i18 = 18;
        Integer i99 = 99;
        Integer i12 = 12;
        Integer i25 = 25;
        Integer i75 = 75;
        Integer i100 = 100;
        Integer i11 = 11;
        Integer i13 = 13;
        Integer i24 = 24;
        Integer i26 = 26;
        Integer i74 = 74;
        Integer i76 = 76;
        Integer i105 = 105;

        tree.add(i50);
        assertEquals(0, tree.height());
        /*
                50
         */
        tree.add(i18);
        tree.add(i99);
        assertEquals(1, tree.height());
        /*
                50
              /    \
            18      99
         */
        tree.add(i12);
        tree.add(i25);
        tree.add(i75);
        tree.add(i100);
        assertEquals(2, tree.height());
        /*
                50
              /    \
            18      99
          /  \      / \
        12   25    75  100
         */
        tree.add(i11);
        tree.add(i13);
        tree.add(i24);
        tree.add(i26);
        tree.add(i74);
        tree.add(i76);
        tree.add(i105);
        assertEquals(3, tree.height());
        /*
                50
              /    \
            18      99
          /  \      / \
        12   25    75  100
       / \   / \   / \   \
      11 13 24 26 74 76  105
         */
    }

    @Test(timeout = TIMEOUT)
    public void testClear4() {

        /*
                50
              /    \
            18      99
          /  \      / \
        12   25    75  100
       / \   / \   / \   \
      11 13 24 26 74 76  105
         */

        Integer i50 = 50;
        Integer i18 = 18;
        Integer i99 = 99;
        Integer i12 = 12;
        Integer i25 = 25;
        Integer i75 = 75;
        Integer i100 = 100;
        Integer i11 = 11;
        Integer i13 = 13;
        Integer i24 = 24;
        Integer i26 = 26;
        Integer i74 = 74;
        Integer i76 = 76;
        Integer i105 = 105;

        tree.add(i50);
        tree.add(i18);
        tree.add(i99);
        tree.add(i12);
        tree.add(i25);
        tree.add(i75);
        tree.add(i100);
        tree.add(i11);
        tree.add(i13);
        tree.add(i24);
        tree.add(i26);
        tree.add(i74);
        tree.add(i76);
        tree.add(i105);

        tree.clear();
        assertEquals(0, tree.size());
        assertNull(tree.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void testWordSearch4() {

        AVL<String> letterTree = new AVL<>();

        String a = new String("a");
        String b = new String("b");
        String c = new String("c");
        String d = new String("d");
        String e = new String("e");
        String f = new String("f");
        String g = new String("g");
        String h = new String("h");
        String i = new String("i");
        String j = new String("j");
        String k = new String("k");
        String l = new String("l");
        String m = new String("m");
        String n = new String("n");
        String o = new String("o");
        String p = new String("p");
        String q = new String("q");
        String r = new String("r");
        String s = new String("s");
        String t = new String("t");
        String u = new String("u");
        String v = new String("v");
        String w = new String("w");
        String x = new String("x");
        String y = new String("y");
        String z = new String("z");
        letterTree.add(p);
        letterTree.add(h);
        letterTree.add(w);
        letterTree.add(d);
        letterTree.add(l);
        letterTree.add(t);
        letterTree.add(y);
        letterTree.add(b);
        letterTree.add(f);
        letterTree.add(j);
        letterTree.add(n);
        letterTree.add(r);
        letterTree.add(v);
        letterTree.add(x);
        letterTree.add(z);
        letterTree.add(a);
        letterTree.add(c);
        letterTree.add(e);
        letterTree.add(g);
        letterTree.add(i);
        letterTree.add(k);
        letterTree.add(m);
        letterTree.add(o);
        letterTree.add(q);
        letterTree.add(s);
        letterTree.add(u);


        /*
                       p
                   /      \
                /            \
              /                \
             h                  w
           /   \             /     \
          d      l           t      y
         / \    /  \       /  \   /  \
        b   f   j   n     r   v  x    z
       /\  /\   /\  /\   /\  /
      a c  e g  i k m o q s  u
        */

        assertEquals(26, letterTree.size());

        String[] word = new String[] {"p", "h", "d", "b", "a"};
        List<String> path = letterTree.wordSearch(word);
        assertEquals(5, path.size());

        assertSame(p, path.get(0));
        assertSame(h, path.get(1));
        assertSame(d, path.get(2));
        assertSame(b, path.get(3));
        assertSame(a, path.get(4));

        word = new String[] {"a", "b", "d", "h", "p"};
        path = letterTree.wordSearch(word);
        assertEquals(5, path.size());

        assertSame(a, path.get(0));
        assertSame(b, path.get(1));
        assertSame(d, path.get(2));
        assertSame(h, path.get(3));
        assertSame(p, path.get(4));

        word = new String[] {"p", "w", "y", "z"};
        path = letterTree.wordSearch(word);
        assertEquals(4, path.size());

        assertSame(p, path.get(0));
        assertSame(w, path.get(1));
        assertSame(y, path.get(2));
        assertSame(z, path.get(3));

        word = new String[] {"z", "y", "w", "p"};
        path = letterTree.wordSearch(word);
        assertEquals(4, path.size());

        assertSame(z, path.get(0));
        assertSame(y, path.get(1));
        assertSame(w, path.get(2));
        assertSame(p, path.get(3));

        word = new String[] {"d", "h", "p", "w"};
        path = letterTree.wordSearch(word);
        assertEquals(4, path.size());

        assertSame(d, path.get(0));
        assertSame(h, path.get(1));
        assertSame(p, path.get(2));
        assertSame(w, path.get(3));

        word = new String[] {"y", "w", "p", "h"};
        path = letterTree.wordSearch(word);
        assertEquals(4, path.size());

        assertSame(y, path.get(0));
        assertSame(w, path.get(1));
        assertSame(p, path.get(2));
        assertSame(h, path.get(3));

        word = new String[] {"p", "h", "l", "j", "k"};
        path = letterTree.wordSearch(word);
        assertEquals(5, path.size());

        assertSame(p, path.get(0));
        assertSame(h, path.get(1));
        assertSame(l, path.get(2));
        assertSame(j, path.get(3));
        assertSame(k, path.get(4));

        word = new String[] {"k", "j", "l", "h", "p"};
        path = letterTree.wordSearch(word);
        assertEquals(5, path.size());

        assertSame(k, path.get(0));
        assertSame(j, path.get(1));
        assertSame(l, path.get(2));
        assertSame(h, path.get(3));
        assertSame(p, path.get(4));

        word = new String[] {"j", "l", "n", "m"};
        path = letterTree.wordSearch(word);
        assertEquals(4, path.size());

        assertSame(j, path.get(0));
        assertSame(l, path.get(1));
        assertSame(n, path.get(2));
        assertSame(m, path.get(3));

        word = new String[] {"m", "n", "l", "j"};
        path = letterTree.wordSearch(word);
        assertEquals(4, path.size());

        assertSame(m, path.get(0));
        assertSame(n, path.get(1));
        assertSame(l, path.get(2));
        assertSame(j, path.get(3));

        word = new String[] {"m"};
        path = letterTree.wordSearch(word);
        assertEquals(1, path.size());

        assertSame(m, path.get(0));

        word = new String[] {"p"};
        path = letterTree.wordSearch(word);
        assertEquals(1, path.size());

        assertSame(p, path.get(0));

        word = new String[] {};
        path = letterTree.wordSearch(word);
        assertEquals(0, path.size());
        // This should not throw any Exception
        // This does not means null.
        // wordSearch([]) returns an empty list [].
        // From JavaDOCS.


    }

    @Test(timeout = TIMEOUT)
    public void testWordSearchException() {

        AVL<String> letterTree = new AVL<>();

        String a = new String("a");
        String b = new String("b");
        String c = new String("c");
        String d = new String("d");
        String e = new String("e");
        String f = new String("f");
        String g = new String("g");
        String h = new String("h");
        String i = new String("i");
        String j = new String("j");
        String k = new String("k");
        String l = new String("l");
        String m = new String("m");
        String n = new String("n");
        String o = new String("o");
        String p = new String("p");
        String q = new String("q");
        String r = new String("r");
        String s = new String("s");
        String t = new String("t");
        String u = new String("u");
        String v = new String("v");
        String w = new String("w");
        String x = new String("x");
        String y = new String("y");
        String z = new String("z");
        letterTree.add(p);
        letterTree.add(h);
        letterTree.add(w);
        letterTree.add(d);
        letterTree.add(l);
        letterTree.add(t);
        letterTree.add(y);
        letterTree.add(b);
        letterTree.add(f);
        letterTree.add(j);
        letterTree.add(n);
        letterTree.add(r);
        letterTree.add(v);
        letterTree.add(x);
        letterTree.add(z);
        letterTree.add(c);
        letterTree.add(e);
        letterTree.add(g);
        letterTree.add(i);
        letterTree.add(k);
        letterTree.add(m);
        letterTree.add(o);
        letterTree.add(q);
        letterTree.add(s);


        /*
                       p
                   /      \
                /            \
              /                \
             h                  w
           /   \             /     \
          d      l           t      y
         / \    /  \       /  \   /  \
        b   f   j   n     r   v  x    z
        \  /\   /\  /\   /\
        c  e g  i k m o q s
        */

        assertEquals(24, letterTree.size());

        String[] word;
        List<String> path;

        try {
            word = null;
            path = letterTree.wordSearch(word);
            fail();
        } catch (IllegalArgumentException ee) {

        }

        try {
            word = new String[] {"o", "x"};
            path = letterTree.wordSearch(word);
            fail();

        } catch (NoSuchElementException ee) {

        }

        try {
            word = new String[] {"o", "s", "q", "n"};
            path = letterTree.wordSearch(word);
            fail();

        } catch (NoSuchElementException ee) {

        }

        try {
            word = new String[] {"a", "u"};
            path = letterTree.wordSearch(word);
            fail();

        } catch (NoSuchElementException ee) {

        }


        try {
            word = new String[] {"a"};
            path = letterTree.wordSearch(word);
            fail();

        } catch (NoSuchElementException ee) {

        }

        try {
            word = new String[] {"u"};
            path = letterTree.wordSearch(word);
            fail();

        } catch (NoSuchElementException ee) {

        }

        try {
            word = new String[] {"t", "w", "i", "h"};
            path = letterTree.wordSearch(word);
            fail();

        } catch (NoSuchElementException ee) {

        }

        try {
            word = new String[] {"d", "h", "l", "j", "a", "b", "k"};
            path = letterTree.wordSearch(word);
            fail();

        } catch (NoSuchElementException ee) {

        }

        try {
            word = new String[] {"r", "t", "v", "u"};
            path = letterTree.wordSearch(word);
            fail();

        } catch (NoSuchElementException ee) {

        }

    }

    @Test(timeout = TIMEOUT)
    public void testInitialization5() {
        assertEquals(0, tree.size());
        assertNull(tree.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void testConstructor5() {

        /*
             Height
             max(left node’s height, right node’s height) + 1
             where the height of a leaf node is 0 and the
             heights of its null children are -1.
             (From HW pdf pg4)
             BF (Balance Factor)
             Calculated by subtracting the height of the left subtree
             by the height of right subtree.
             (From TreesAVL.pdf pg6)
         */



        /*
              10
              10 : height = 0, BF = (-1) - (-1) = 0
         */
        List<Integer> list = new ArrayList<>();
        list.add(10);
        tree = new AVL<>(list);

        AVLNode<Integer> node = tree.getRoot();

        assertEquals(1, tree.size());
        assertEquals((Integer) 10, node.getData());
        assertEquals(0, node.getHeight());
        assertEquals(0, node.getBalanceFactor());

        /*
               10
              /
             3
             10 : height = 1, BF = (0) - (-1) = 1
             3 : height = 0, BF = (-1) - (-1) = 0
         */

        list.add(3); // list == {10, 3}
        tree = new AVL<>(list);
        node = tree.getRoot();

        assertEquals(2, tree.size());
        assertEquals((Integer) 10, node.getData());
        assertEquals(1, node.getHeight());
        assertEquals(1, node.getBalanceFactor());

        assertEquals((Integer) 3, node.getLeft().getData());
        assertEquals(0, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        /*
              10 <-- BF = (1) - (-1) = 2
              /
             3
            /
           1
           Right rotate will occur.
               3
              / \
             1   10
             3 : height = 1, BF = (0) - (0) = 0
             1 : height = 0, BF = (-1) - (-1) = 0
             10 : height = 0, BF = (-1) - (-1) = 0
         */

        list.add(1); // list == {10, 3, 1}
        tree = new AVL<>(list);
        node = tree.getRoot();

        assertEquals(3, tree.size());
        assertEquals((Integer) 3, node.getData());
        assertEquals(1, node.getHeight());
        assertEquals(0, node.getBalanceFactor());

        assertEquals((Integer) 1, node.getLeft().getData());
        assertEquals(0, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 10, node.getRight().getData());
        assertEquals(0, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());


        /*
               3
              / \
             1   10
                  \
                   15
             3 : height = 2, BF = (0) - (1) = -1
             1 : height = 0, BF = (-1) - (-1) = 0
             10 : height = 1, BF = (-1) - (0) = -1
             15 : height = 0, BF = (-1) - (-1) = 0
         */

        list.add(15); // list == {10, 3, 1, 15}
        tree = new AVL<>(list);
        node = tree.getRoot();

        assertEquals(4, tree.size());
        assertEquals((Integer) 3, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(-1, node.getBalanceFactor());

        assertEquals((Integer) 1, node.getLeft().getData());
        assertEquals(0, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 10, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(-1, node.getRight().getBalanceFactor());

        assertEquals((Integer) 15, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());

        /*
               3
              / \
             1   10 <- BF = (-1) - (1) = -2
                  \
                   15
                    \
                     20
           left rotation will occur.
               3
              / \
             1   15
                 /\
                10 20
             3 : height = 2, BF = (0) - (1) = -1
             1 : height = 0, BF = (-1) - (-1) = 0
             15 : height = 1, BF = (0) - (0) = 0
             10 : height = 0, BF = (-1) - (-1) = 0
             20 : height = 0, BF = (-1) - (-1) = 0
         */

        list.add(20); // list == {10, 3, 1, 15, 20}
        tree = new AVL<>(list);
        node = tree.getRoot();

        assertEquals(5, tree.size());
        assertEquals((Integer) 3, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(-1, node.getBalanceFactor());

        assertEquals((Integer) 1, node.getLeft().getData());
        assertEquals(0, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 15, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());

        assertEquals((Integer) 10, node.getRight().getLeft().getData());
        assertEquals(0, node.getRight().getLeft().getHeight());
        assertEquals(0, node.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 20, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());

        //Rotation Test

        /*
        LEFT ROTATION INITIAL SETTING
        this is basically same as TreeAVL.pdf example (pg14)
        37 == A
        48 == B
               38
              / \
             37  49
                 /\
                48 85
             38 : height = 2, BF = (0) - (1) = -1
             37 : height = 0, BF = (-1) - (-1) = 0
             49 : height = 1, BF = (0) - (0) = 0
             48 : height = 0, BF = (-1) - (-1) = 0
             85 : height = 0, BF = (-1) - (-1) = 0
         */
        list = new ArrayList<>();
        list.add(38);
        list.add(37);
        list.add(49);
        list.add(48);
        list.add(85); //list = {38, 37, 49, 48, 85}
        tree = new AVL<>(list);
        node = tree.getRoot();
        //Check setting before the test.

        assertEquals(5, tree.size());
        assertEquals((Integer) 38, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(-1, node.getBalanceFactor());

        assertEquals((Integer) 37, node.getLeft().getData());
        assertEquals(0, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 49, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());

        assertEquals((Integer) 48, node.getRight().getLeft().getData());
        assertEquals(0, node.getRight().getLeft().getHeight());
        assertEquals(0, node.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 85, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());


        /*
        LEFT ROTATION
        this is basically same as TreeAVL.pdf example (pg14)
        37 == A
        48 == B
        100 == D
               38
              / \
             37  49
                 /\
                48 85
                    \
                    100 <-- add
                    LEFT ROTATION OCCUR
                49
               / \
              38  85
             / \    \
            37 48   100
             49 : height = 2, BF = (1) - (1) = 0
             38 : height = 1, BF = (0) - (0) = 0
             37 : height = 0, BF = (-1) - (-1) = 0
             48 : height = 0, BF = (-1) - (-1) = 0
             85 : height = 1, BF = (-1) - (0) = -1
             100: height = 0, BF = (-1) - (-1) = 0
         */

        list.add(100); //list = {38, 37, 49, 48, 85, 100}
        tree = new AVL<>(list);
        node = tree.getRoot();

        assertEquals(6, tree.size());
        assertEquals((Integer) 49, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(0, node.getBalanceFactor());

        assertEquals((Integer) 38, node.getLeft().getData());
        assertEquals(1, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 37, node.getLeft().getLeft().getData());
        assertEquals(0, node.getLeft().getLeft().getHeight());
        assertEquals(0, node.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 48, node.getLeft().getRight().getData());
        assertEquals(0, node.getLeft().getRight().getHeight());
        assertEquals(0, node.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 85, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(-1, node.getRight().getBalanceFactor());

        assertEquals((Integer) 100, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());


        /*
        RIGHT ROTATION INITIAL SETTING
        this is basically same as TreeAVL.pdf example (pg17)
        100 == D
        50  == C
               85
              /  \
             49  100
            / \
           38  50
             85 : height = 2, BF = (1) - (0) = 1
             49 : height = 1, BF = (0) - (0) = 0
             38 : height = 0, BF = (-1) - (-1) = 0
             50 : height = 0, BF = (-1) - (-1) = 0
             100: height = 0, BF = (-1) - (-1) = 0
         */
        list = new ArrayList<>();
        list.add(85);
        list.add(49);
        list.add(100);
        list.add(38);
        list.add(50); //list = {85, 49, 100, 38, 50}
        tree = new AVL<>(list);
        node = tree.getRoot();
        //Check setting before the test.

        assertEquals(5, tree.size());
        assertEquals((Integer) 85, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(1, node.getBalanceFactor());

        assertEquals((Integer) 49, node.getLeft().getData());
        assertEquals(1, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 38, node.getLeft().getLeft().getData());
        assertEquals(0, node.getLeft().getLeft().getHeight());
        assertEquals(0, node.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 50, node.getLeft().getRight().getData());
        assertEquals(0, node.getLeft().getRight().getHeight());
        assertEquals(0, node.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 100, node.getRight().getData());
        assertEquals(0, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());


        /*
        RIGHT ROTATION
        this is basically same as TreeAVL.pdf example (pg14)
        100 == D
        50 == C
        37 == A
               85
              /  \
             49  100
            / \
           38  50
          /
         37 <--will add
                    RIGHT ROTATION OCCUR
                49
               / \
              38  85
             /    /\
            37  50 100
             49 : height = 2, BF = (1) - (1) = 0
             38 : height = 1, BF = (0) - (-1) = 1
             37 : height = 0, BF = (-1) - (-1) = 0
             85 : height = 1, BF = (0) - (0) = 0
             50 : height = 0, BF = (-1) - (-1) = 0
             100: height = 0, BF = (-1) - (-1) = 0
         */

        list.add(37); //list = {85, 49, 100, 38, 50, 37}
        tree = new AVL<>(list);
        node = tree.getRoot();

        assertEquals(6, tree.size());
        assertEquals((Integer) 49, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(0, node.getBalanceFactor());

        assertEquals((Integer) 38, node.getLeft().getData());
        assertEquals(1, node.getLeft().getHeight());
        assertEquals(1, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 37, node.getLeft().getLeft().getData());
        assertEquals(0, node.getLeft().getLeft().getHeight());
        assertEquals(0, node.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 85, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());

        assertEquals((Integer) 50, node.getRight().getLeft().getData());
        assertEquals(0, node.getRight().getLeft().getHeight());
        assertEquals(0, node.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 100, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());


        /*
        LEFT-RIGHT ROTATION INITIAL SETTING
        this is basically same as TreeAVL.pdf example (pg20)
        37  == A
        100 == D
               85
              /  \
             38  100
            / \
           37  49
             85 : height = 2, BF = (1) - (0) = 1
             38 : height = 1, BF = (0) - (0) = 0
             37 : height = 0, BF = (-1) - (-1) = 0
             49 : height = 0, BF = (-1) - (-1) = 0
             100: height = 0, BF = (-1) - (-1) = 0
         */
        list = new ArrayList<>();
        list.add(85);
        list.add(38);
        list.add(100);
        list.add(37);
        list.add(49); //list = {85, 38, 100, 37, 49}
        tree = new AVL<>(list);
        node = tree.getRoot();
        //Check setting before the test.

        assertEquals(5, tree.size());
        assertEquals((Integer) 85, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(1, node.getBalanceFactor());

        assertEquals((Integer) 38, node.getLeft().getData());
        assertEquals(1, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 37, node.getLeft().getLeft().getData());
        assertEquals(0, node.getLeft().getLeft().getHeight());
        assertEquals(0, node.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 49, node.getLeft().getRight().getData());
        assertEquals(0, node.getLeft().getRight().getHeight());
        assertEquals(0, node.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 100, node.getRight().getData());
        assertEquals(0, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());


        /*
        LEFT-RIGHT ROTATION
        this is basically same as TreeAVL.pdf example (pg14)
        37  == A
        100 == D
        50  == C
               85
              /  \
             38  100
            / \
           37  49
                \
                50 <--will add
          LEFT-RIGHT ROTATION OCCUR
                49
               / \
              38  85
             /    /\
            37  50 100
             49 : height = 2, BF = (1) - (1) = 0
             38 : height = 1, BF = (0) - (-1) = 1
             37 : height = 0, BF = (-1) - (-1) = 0
             85 : height = 1, BF = (0) - (0) = 0
             50 : height = 0, BF = (-1) - (-1) = 0
             100: height = 0, BF = (-1) - (-1) = 0
         */

        list.add(50); //list = {85, 38, 100, 37, 49, 50}
        tree = new AVL<>(list);
        node = tree.getRoot();

        assertEquals(6, tree.size());
        assertEquals((Integer) 49, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(0, node.getBalanceFactor());

        assertEquals((Integer) 38, node.getLeft().getData());
        assertEquals(1, node.getLeft().getHeight());
        assertEquals(1, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 37, node.getLeft().getLeft().getData());
        assertEquals(0, node.getLeft().getLeft().getHeight());
        assertEquals(0, node.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 85, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());

        assertEquals((Integer) 50, node.getRight().getLeft().getData());
        assertEquals(0, node.getRight().getLeft().getHeight());
        assertEquals(0, node.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 100, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());

        /*
        RIGHT-LEFT ROTATION INITIAL SETTING
        this is basically same as TreeAVL.pdf example (pg23)
        37  == A
        100 == D
               38
              /  \
             37  85
                 /\
               49 100
             38 : height = 2, BF = (0) - (1) = -1
             37 : height = 0, BF = (-1) - (-1) = 0
             85 : height = 1, BF = (0) - (0) = 0
             49 : height = 0, BF = (-1) - (-1) = 0
             100: height = 0, BF = (-1) - (-1) = 0
         */
        list = new ArrayList<>();
        list.add(38);
        list.add(37);
        list.add(85);
        list.add(49);
        list.add(100); //list = {38, 37, 85, 49, 100}
        tree = new AVL<>(list);
        node = tree.getRoot();
        //Check setting before the test.

        assertEquals(5, tree.size());
        assertEquals((Integer) 38, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(-1, node.getBalanceFactor());

        assertEquals((Integer) 37, node.getLeft().getData());
        assertEquals(0, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 85, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());

        assertEquals((Integer) 49, node.getRight().getLeft().getData());
        assertEquals(0, node.getRight().getLeft().getHeight());
        assertEquals(0, node.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 100, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());


        /*
        RIGHT-LEFT ROTATION
        this is basically same as TreeAVL.pdf example (pg14)
        37  == A
        100 == D
        48  == B
               38
              /  \
             37  85
                 /\
               49 100
               /
              48 <-- will add
          RIGHT-LEFT ROTATION OCCUR
                49
               / \
              38  85
             /\    \
            37 48  100
             49 : height = 2, BF = (1) - (1) = 0
             38 : height = 1, BF = (0) - (0) = 0
             37 : height = 0, BF = (-1) - (-1) = 0
             48 : height = 0, BF = (-1) - (-1) = 0
             85 : height = 1, BF = (-1) - (0) = -1
             100: height = 0, BF = (-1) - (-1) = 0
         */

        list.add(48); //list = {38, 37, 85, 49, 100, 48}
        tree = new AVL<>(list);
        node = tree.getRoot();

        assertEquals(6, tree.size());
        assertEquals((Integer) 49, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(0, node.getBalanceFactor());

        assertEquals((Integer) 38, node.getLeft().getData());
        assertEquals(1, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 37, node.getLeft().getLeft().getData());
        assertEquals(0, node.getLeft().getLeft().getHeight());
        assertEquals(0, node.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 48, node.getLeft().getRight().getData());
        assertEquals(0, node.getLeft().getRight().getHeight());
        assertEquals(0, node.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 85, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(-1, node.getRight().getBalanceFactor());

        assertEquals((Integer) 100, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());

    }

    @Test(timeout = TIMEOUT)
    public void testConstructorException5() {
        try {
            tree = new AVL<>(null);
            fail();
        } catch (IllegalArgumentException e) {
        }

        try {
            List<Integer> list = new ArrayList<>();
            list.add(10);
            list.add(null);
            list.add(20);
            tree = new AVL<>(list);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test(timeout = TIMEOUT)
    public void testAdd5() {

        /*
             Height
             max(left node’s height, right node’s height) + 1
             where the height of a leaf node is 0 and the
             heights of its null children are -1.
             (From HW pdf pg4)
             BF (Balance Factor)
             Calculated by subtracting the height of the left subtree
             by the height of right subtree.
             (From TreesAVL.pdf pg6)
         */



        /*
              10
              10 : height = 0, BF = (-1) - (-1) = 0
         */

        tree.add(10);

        AVLNode<Integer> node = tree.getRoot();

        assertEquals(1, tree.size());
        assertEquals((Integer) 10, node.getData());
        assertEquals(0, node.getHeight());
        assertEquals(0, node.getBalanceFactor());

        /*
               10
              /
             3
             10 : height = 1, BF = (0) - (-1) = 1
             3 : height = 0, BF = (-1) - (-1) = 0
         */

        tree.add(3);

        node = tree.getRoot();

        assertEquals(2, tree.size());
        assertEquals((Integer) 10, node.getData());
        assertEquals(1, node.getHeight());
        assertEquals(1, node.getBalanceFactor());

        assertEquals((Integer) 3, node.getLeft().getData());
        assertEquals(0, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        /*
              10 <-- BF = (1) - (-1) = 2
              /
             3
            /
           1
           Right rotate will occur.
               3
              / \
             1   10
             3 : height = 1, BF = (0) - (0) = 0
             1 : height = 0, BF = (-1) - (-1) = 0
             10 : height = 0, BF = (-1) - (-1) = 0
         */

        tree.add(1);

        node = tree.getRoot();

        assertEquals(3, tree.size());
        assertEquals((Integer) 3, node.getData());
        assertEquals(1, node.getHeight());
        assertEquals(0, node.getBalanceFactor());

        assertEquals((Integer) 1, node.getLeft().getData());
        assertEquals(0, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 10, node.getRight().getData());
        assertEquals(0, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());


        /*
               3
              / \
             1   10
                  \
                   15
             3 : height = 2, BF = (0) - (1) = -1
             1 : height = 0, BF = (-1) - (-1) = 0
             10 : height = 1, BF = (-1) - (0) = -1
             15 : height = 0, BF = (-1) - (-1) = 0
         */

        tree.add(15);

        node = tree.getRoot();

        assertEquals(4, tree.size());
        assertEquals((Integer) 3, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(-1, node.getBalanceFactor());

        assertEquals((Integer) 1, node.getLeft().getData());
        assertEquals(0, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 10, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(-1, node.getRight().getBalanceFactor());

        assertEquals((Integer) 15, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());

        /*
               3
              / \
             1   10 <- BF = (-1) - (1) = -2
                  \
                   15
                    \
                     20
           left rotation will occur.
               3
              / \
             1   15
                 /\
                10 20
             3 : height = 2, BF = (0) - (1) = -1
             1 : height = 0, BF = (-1) - (-1) = 0
             15 : height = 1, BF = (0) - (0) = 0
             10 : height = 0, BF = (-1) - (-1) = 0
             20 : height = 0, BF = (-1) - (-1) = 0
         */

        tree.add(20);

        node = tree.getRoot();

        assertEquals(5, tree.size());
        assertEquals((Integer) 3, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(-1, node.getBalanceFactor());

        assertEquals((Integer) 1, node.getLeft().getData());
        assertEquals(0, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 15, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());

        assertEquals((Integer) 10, node.getRight().getLeft().getData());
        assertEquals(0, node.getRight().getLeft().getHeight());
        assertEquals(0, node.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 20, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());

        //If the data is already in the tree. (15 already exist)
        tree.add(15);

        node = tree.getRoot();

        assertEquals(5, tree.size());
        assertEquals((Integer) 3, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(-1, node.getBalanceFactor());

        assertEquals((Integer) 1, node.getLeft().getData());
        assertEquals(0, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 15, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());

        assertEquals((Integer) 10, node.getRight().getLeft().getData());
        assertEquals(0, node.getRight().getLeft().getHeight());
        assertEquals(0, node.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 20, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());

        //If the data is already in the tree. (20, already exist)
        tree.add(20);

        node = tree.getRoot();

        assertEquals(5, tree.size());
        assertEquals((Integer) 3, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(-1, node.getBalanceFactor());

        assertEquals((Integer) 1, node.getLeft().getData());
        assertEquals(0, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 15, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());

        assertEquals((Integer) 10, node.getRight().getLeft().getData());
        assertEquals(0, node.getRight().getLeft().getHeight());
        assertEquals(0, node.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 20, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());


        //Rotation Test

        /*
        LEFT ROTATION INITIAL SETTING
        this is basically same as TreeAVL.pdf example (pg14)
        37 == A
        48 == B
               38
              / \
             37  49
                 /\
                48 85
             38 : height = 2, BF = (0) - (1) = -1
             37 : height = 0, BF = (-1) - (-1) = 0
             49 : height = 1, BF = (0) - (0) = 0
             48 : height = 0, BF = (-1) - (-1) = 0
             85 : height = 0, BF = (-1) - (-1) = 0
         */
        tree = new AVL<>();
        tree.add(38);
        tree.add(37);
        tree.add(49);
        tree.add(48);
        tree.add(85);

        node = tree.getRoot();
        //Check setting before the test.

        assertEquals(5, tree.size());
        assertEquals((Integer) 38, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(-1, node.getBalanceFactor());

        assertEquals((Integer) 37, node.getLeft().getData());
        assertEquals(0, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 49, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());

        assertEquals((Integer) 48, node.getRight().getLeft().getData());
        assertEquals(0, node.getRight().getLeft().getHeight());
        assertEquals(0, node.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 85, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());


        /*
        LEFT ROTATION
        this is basically same as TreeAVL.pdf example (pg14)
        37 == A
        48 == B
        100 == D
               38
              / \
             37  49
                 /\
                48 85
                    \
                    100 <-- add
                    LEFT ROTATION OCCUR
                49
               / \
              38  85
             / \    \
            37 48   100
             49 : height = 2, BF = (1) - (1) = 0
             38 : height = 1, BF = (0) - (0) = 0
             37 : height = 0, BF = (-1) - (-1) = 0
             48 : height = 0, BF = (-1) - (-1) = 0
             85 : height = 1, BF = (-1) - (0) = -1
             100: height = 0, BF = (-1) - (-1) = 0
         */

        tree.add(100);
        node = tree.getRoot();

        assertEquals(6, tree.size());
        assertEquals((Integer) 49, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(0, node.getBalanceFactor());

        assertEquals((Integer) 38, node.getLeft().getData());
        assertEquals(1, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 37, node.getLeft().getLeft().getData());
        assertEquals(0, node.getLeft().getLeft().getHeight());
        assertEquals(0, node.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 48, node.getLeft().getRight().getData());
        assertEquals(0, node.getLeft().getRight().getHeight());
        assertEquals(0, node.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 85, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(-1, node.getRight().getBalanceFactor());

        assertEquals((Integer) 100, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());


        /*
        RIGHT ROTATION INITIAL SETTING
        this is basically same as TreeAVL.pdf example (pg17)
        100 == D
        50  == C
               85
              /  \
             49  100
            / \
           38  50
             85 : height = 2, BF = (1) - (0) = 1
             49 : height = 1, BF = (0) - (0) = 0
             38 : height = 0, BF = (-1) - (-1) = 0
             50 : height = 0, BF = (-1) - (-1) = 0
             100: height = 0, BF = (-1) - (-1) = 0
         */
        tree = new AVL<>();
        tree.add(85);
        tree.add(49);
        tree.add(100);
        tree.add(38);
        tree.add(50);
        node = tree.getRoot();
        //Check setting before the test.

        assertEquals(5, tree.size());
        assertEquals((Integer) 85, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(1, node.getBalanceFactor());

        assertEquals((Integer) 49, node.getLeft().getData());
        assertEquals(1, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 38, node.getLeft().getLeft().getData());
        assertEquals(0, node.getLeft().getLeft().getHeight());
        assertEquals(0, node.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 50, node.getLeft().getRight().getData());
        assertEquals(0, node.getLeft().getRight().getHeight());
        assertEquals(0, node.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 100, node.getRight().getData());
        assertEquals(0, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());


        /*
        RIGHT ROTATION
        this is basically same as TreeAVL.pdf example (pg14)
        100 == D
        50 == C
        37 == A
               85
              /  \
             49  100
            / \
           38  50
          /
         37 <--will add
                    RIGHT ROTATION OCCUR
                49
               / \
              38  85
             /    /\
            37  50 100
             49 : height = 2, BF = (1) - (1) = 0
             38 : height = 1, BF = (0) - (-1) = 1
             37 : height = 0, BF = (-1) - (-1) = 0
             85 : height = 1, BF = (0) - (0) = 0
             50 : height = 0, BF = (-1) - (-1) = 0
             100: height = 0, BF = (-1) - (-1) = 0
         */

        tree.add(37);
        node = tree.getRoot();

        assertEquals(6, tree.size());
        assertEquals((Integer) 49, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(0, node.getBalanceFactor());

        assertEquals((Integer) 38, node.getLeft().getData());
        assertEquals(1, node.getLeft().getHeight());
        assertEquals(1, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 37, node.getLeft().getLeft().getData());
        assertEquals(0, node.getLeft().getLeft().getHeight());
        assertEquals(0, node.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 85, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());

        assertEquals((Integer) 50, node.getRight().getLeft().getData());
        assertEquals(0, node.getRight().getLeft().getHeight());
        assertEquals(0, node.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 100, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());


        /*
        LEFT-RIGHT ROTATION INITIAL SETTING
        this is basically same as TreeAVL.pdf example (pg20)
        37  == A
        100 == D
               85
              /  \
             38  100
            / \
           37  49
             85 : height = 2, BF = (1) - (0) = 1
             38 : height = 1, BF = (0) - (0) = 0
             37 : height = 0, BF = (-1) - (-1) = 0
             49 : height = 0, BF = (-1) - (-1) = 0
             100: height = 0, BF = (-1) - (-1) = 0
         */
        tree = new AVL<>();
        tree.add(85);
        tree.add(38);
        tree.add(100);
        tree.add(37);
        tree.add(49);
        node = tree.getRoot();
        //Check setting before the test.

        assertEquals(5, tree.size());
        assertEquals((Integer) 85, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(1, node.getBalanceFactor());

        assertEquals((Integer) 38, node.getLeft().getData());
        assertEquals(1, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 37, node.getLeft().getLeft().getData());
        assertEquals(0, node.getLeft().getLeft().getHeight());
        assertEquals(0, node.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 49, node.getLeft().getRight().getData());
        assertEquals(0, node.getLeft().getRight().getHeight());
        assertEquals(0, node.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 100, node.getRight().getData());
        assertEquals(0, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());


        /*
        LEFT-RIGHT ROTATION
        this is basically same as TreeAVL.pdf example (pg14)
        37  == A
        100 == D
        50  == C
               85
              /  \
             38  100
            / \
           37  49
                \
                50 <--will add
          LEFT-RIGHT ROTATION OCCUR
                49
               / \
              38  85
             /    /\
            37  50 100
             49 : height = 2, BF = (1) - (1) = 0
             38 : height = 1, BF = (0) - (-1) = 1
             37 : height = 0, BF = (-1) - (-1) = 0
             85 : height = 1, BF = (0) - (0) = 0
             50 : height = 0, BF = (-1) - (-1) = 0
             100: height = 0, BF = (-1) - (-1) = 0
         */

        tree.add(50);
        node = tree.getRoot();

        assertEquals(6, tree.size());
        assertEquals((Integer) 49, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(0, node.getBalanceFactor());

        assertEquals((Integer) 38, node.getLeft().getData());
        assertEquals(1, node.getLeft().getHeight());
        assertEquals(1, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 37, node.getLeft().getLeft().getData());
        assertEquals(0, node.getLeft().getLeft().getHeight());
        assertEquals(0, node.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 85, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());

        assertEquals((Integer) 50, node.getRight().getLeft().getData());
        assertEquals(0, node.getRight().getLeft().getHeight());
        assertEquals(0, node.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 100, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());

        /*
        RIGHT-LEFT ROTATION INITIAL SETTING
        this is basically same as TreeAVL.pdf example (pg23)
        37  == A
        100 == D
               38
              /  \
             37  85
                 /\
               49 100
             38 : height = 2, BF = (0) - (1) = -1
             37 : height = 0, BF = (-1) - (-1) = 0
             85 : height = 1, BF = (0) - (0) = 0
             49 : height = 0, BF = (-1) - (-1) = 0
             100: height = 0, BF = (-1) - (-1) = 0
         */
        tree = new AVL<>();
        tree.add(38);
        tree.add(37);
        tree.add(85);
        tree.add(49);
        tree.add(100);

        node = tree.getRoot();
        //Check setting before the test.

        assertEquals(5, tree.size());
        assertEquals((Integer) 38, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(-1, node.getBalanceFactor());

        assertEquals((Integer) 37, node.getLeft().getData());
        assertEquals(0, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 85, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());

        assertEquals((Integer) 49, node.getRight().getLeft().getData());
        assertEquals(0, node.getRight().getLeft().getHeight());
        assertEquals(0, node.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 100, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());


        /*
        RIGHT-LEFT ROTATION
        this is basically same as TreeAVL.pdf example (pg14)
        37  == A
        100 == D
        48  == B
               38
              /  \
             37  85
                 /\
               49 100
               /
              48 <-- will add
          RIGHT-LEFT ROTATION OCCUR
                49
               / \
              38  85
             /\    \
            37 48  100
             49 : height = 2, BF = (1) - (1) = 0
             38 : height = 1, BF = (0) - (0) = 0
             37 : height = 0, BF = (-1) - (-1) = 0
             48 : height = 0, BF = (-1) - (-1) = 0
             85 : height = 1, BF = (-1) - (0) = -1
             100: height = 0, BF = (-1) - (-1) = 0
         */

        tree.add(48);
        node = tree.getRoot();

        assertEquals(6, tree.size());
        assertEquals((Integer) 49, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(0, node.getBalanceFactor());

        assertEquals((Integer) 38, node.getLeft().getData());
        assertEquals(1, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 37, node.getLeft().getLeft().getData());
        assertEquals(0, node.getLeft().getLeft().getHeight());
        assertEquals(0, node.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 48, node.getLeft().getRight().getData());
        assertEquals(0, node.getLeft().getRight().getHeight());
        assertEquals(0, node.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 85, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(-1, node.getRight().getBalanceFactor());

        assertEquals((Integer) 100, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testAddException5() {
        try {
            tree.add(null);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test(timeout = TIMEOUT)
    public void testRemove5() {


        //ROTATION CHECK (REMOVE) [RIGHT ROTATION]
        //WILL REMOVE CHILD NODE FOR ROTATE TEST

        /*
              50
             /  \
            25   75
           / \   / \
          12 37 62 100
         /\
        6 18
        */
        Integer i50 = 50;
        Integer i25 = 25;
        Integer i75 = 75;
        Integer i12 = 12;
        Integer i37 = 37;
        Integer i62 = 62;
        Integer i100 = 100;
        Integer i6 = 6;
        Integer i18 = 18;
        tree.add(i50);
        tree.add(i25);
        tree.add(i75);
        tree.add(i12);
        tree.add(i37);
        tree.add(i62);
        tree.add(i100);
        tree.add(i6);
        tree.add(i18);
        //ASSUME THAT YOUR ADD METHOD WORKS PROPERLY.

        /*
              50
             /  \
            25   75
           / \   / \
          12(37)62 100
         /\
        6 18
        -->
              50
             /  \
 BF = 2 --> 25   75
           /     / \
BF = 0--> 12    62 100
         /\
        6 18
        Right rotation will occur
        "If the signs of the balance factors are the same or 'the child has BF 0',
        then do a single rotation. If the signs are opposite, do a double rotation."
        (PIAZZA POST @1162)
       -->
              50
             /  \
            12   75
           /\    / \
          6 25  62 100
            /
           18
        */

        assertSame(i37, tree.remove(37));
        assertEquals(8, tree.size());
        AVLNode<Integer> root = tree.getRoot();

        assertEquals((Integer) 50, root.getData());
        assertEquals(3, root.getHeight());
        assertEquals(1, root.getBalanceFactor());

        assertEquals((Integer) 12, root.getLeft().getData());
        assertEquals(2, root.getLeft().getHeight());
        assertEquals(-1, root.getLeft().getBalanceFactor());

        assertEquals((Integer) 75, root.getRight().getData());
        assertEquals(1, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());

        assertEquals((Integer) 6, root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 25, root.getLeft().getRight().getData());
        assertEquals(1, root.getLeft().getRight().getHeight());
        assertEquals(1, root.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 62, root.getRight().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 100, root.getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getBalanceFactor());

        assertEquals((Integer) 18, root.getLeft().getRight().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());




        //ROTATION CHECK (REMOVE) [LEFT ROTATION]
        //WILL REMOVE CHILD NODE FOR ROTATE TEST

        /*
              50
             /  \
            12   75
           /\    / \
          6 25  62 100
            /
           18
         */

        Integer i99 = 99;
        Integer i101 = 101;
        tree.add(i99);
        tree.add(i101);

        //ASSUME THAT YOUR ADD METHOD WORKS PROPERLY.

        /*
              50
             /  \
            12   75
           /\   /  \
          6 25 (62) 100
            /        /\
           18      99 101
           we will remove (62)
         -->
              50
             /  \
            12   75 <-- BF = -2
           /\      \
          6 25      100 <-- BF = 0
            /        /\
           18      99 101
           Left Rotate will occur
           -->
              50
             /   \
            12    100
           /\     / \
          6 25   75 101
            /      \
           18      99
         */

        assertSame(i62, tree.remove(62));
        root = tree.getRoot();
        assertEquals(9, tree.size());

        assertEquals((Integer) 50, root.getData());
        assertEquals(3, root.getHeight());
        assertEquals(0, root.getBalanceFactor());

        assertEquals((Integer) 12, root.getLeft().getData());
        assertEquals(2, root.getLeft().getHeight());
        assertEquals(-1, root.getLeft().getBalanceFactor());

        assertEquals((Integer) 100, root.getRight().getData());
        assertEquals(2, root.getRight().getHeight());
        assertEquals(1, root.getRight().getBalanceFactor());

        assertEquals((Integer) 6, root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 25, root.getLeft().getRight().getData());
        assertEquals(1, root.getLeft().getRight().getHeight());
        assertEquals(1, root.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 75, root.getRight().getLeft().getData());
        assertEquals(1, root.getRight().getLeft().getHeight());
        assertEquals(-1, root.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 101, root.getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getBalanceFactor());

        assertEquals((Integer) 18, root.getLeft().getRight().getLeft().getData());
        assertEquals(0, root.getLeft().getRight().getLeft().getHeight());
        assertEquals(0, root.getLeft().getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 99, root.getRight().getLeft().getRight().getData());
        assertEquals(0, root.getRight().getLeft().getRight().getHeight());
        assertEquals(0, root.getRight().getLeft().getRight().getBalanceFactor());



        //ROTATION CHECK (REMOVE) [LEFTRIGHT ROTATION]
        //WILL REMOVE CHILD NODE FOR ROTATE TEST

        /*
              50
             /   \
            12    100
           /\     / \
          6 25   75 (101)
            /      \
           18      99
           we will remove (101)
         -->
              50
             /   \
            12    100 <-- BF = 2
           /\     /
          6 25   75 <-- BF = -1
            /      \
           18      99 <-- BF = 0
           Left Right Rotate will occur
           -->
               50
             /   \
            12    99
           /\     / \
          6 25   75 100
            /
           18
         */

        assertSame(i101, tree.remove(101));
        root = tree.getRoot();
        assertEquals(8, tree.size());

        assertEquals((Integer) 50, root.getData());
        assertEquals(3, root.getHeight());
        assertEquals(1, root.getBalanceFactor());

        assertEquals((Integer) 12, root.getLeft().getData());
        assertEquals(2, root.getLeft().getHeight());
        assertEquals(-1, root.getLeft().getBalanceFactor());

        assertEquals((Integer) 99, root.getRight().getData());
        assertEquals(1, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());

        assertEquals((Integer) 6, root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 25, root.getLeft().getRight().getData());
        assertEquals(1, root.getLeft().getRight().getHeight());
        assertEquals(1, root.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 75, root.getRight().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 100, root.getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getBalanceFactor());

        assertEquals((Integer) 18, root.getLeft().getRight().getLeft().getData());
        assertEquals(0, root.getLeft().getRight().getLeft().getHeight());
        assertEquals(0, root.getLeft().getRight().getLeft().getBalanceFactor());


        //ROTATION CHECK (REMOVE) [RIGHTLEFT ROTATION]
        //WILL REMOVE CHILD NODE FOR ROTATE TEST

        /*
               50
             /   \
            12    99
           /\     / \
         (6) 25   75 100
            /
           18
           we will remove (6)
         -->
               50
             /   \
 BF = -2 -->12    99
             \    / \
   BF = 1 -->25  75 100
             /
 BF = 0 -->18
           Left Right Rotate will occur
           -->
               50
             /   \
           18     99
           /\     / \
         12 25   75 100
         */

        assertSame(i6, tree.remove(6));
        root = tree.getRoot();
        assertEquals(7, tree.size());

        assertEquals((Integer) 50, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(0, root.getBalanceFactor());

        assertEquals((Integer) 18, root.getLeft().getData());
        assertEquals(1, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());

        assertEquals((Integer) 99, root.getRight().getData());
        assertEquals(1, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());

        assertEquals((Integer) 12, root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 25, root.getLeft().getRight().getData());
        assertEquals(0, root.getLeft().getRight().getHeight());
        assertEquals(0, root.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 75, root.getRight().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 100, root.getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getBalanceFactor());



        // MAKING HARDER
        // PREDECESSOR TEST

        //ROTATION CHECK (REMOVE) [RIGHTLEFT ROTATION]
        //WILL REMOVE CHILD NODE FOR ROTATE TEST

        /*
               50
             /   \
           18     99
           /\     / \
         12 25   75 100
         */

        Integer i11 = 11;
        Integer i13 = 13;
        Integer i24 = 24;
        Integer i26 = 26;
        Integer i74 = 74;
        Integer i76 = 76;
        Integer i105 = 105;
        tree.add(i11);
        tree.add(i13);
        tree.add(i24);
        tree.add(i26);
        tree.add(i74);
        tree.add(i76);
        tree.add(i105);

        //ASSUME THAT ADD METHOD WORKS PROPERLY

        /*
                50
              /    \
            18      99
          /  \      / \
        12   25    75  100
       / \   / \   / \   \
      11 13 24 26 74 76  105
         */




        /*
               (50)
              /    \
            18      99
          /  \      / \
        12   25    75  100
       / \   / \   / \   \
      11 13 24 26 74 76  105
      predecessor = 26
      move 26 to top node.
                26
              /    \
            18      99
          /  \      / \
        12   25    75  100
       / \   /     / \   \
      11 13 24    74 76  105
         */


        assertSame(i50, tree.remove(50));
        root = tree.getRoot();
        assertEquals(13, tree.size());

        assertEquals((Integer) 26, root.getData());
        assertEquals(3, root.getHeight());
        assertEquals(0, root.getBalanceFactor());

        assertEquals((Integer) 18, root.getLeft().getData());
        assertEquals(2, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());

        assertEquals((Integer) 99, root.getRight().getData());
        assertEquals(2, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());

        assertEquals((Integer) 12, root.getLeft().getLeft().getData());
        assertEquals(1, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 25, root.getLeft().getRight().getData());
        assertEquals(1, root.getLeft().getRight().getHeight());
        assertEquals(1, root.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 75, root.getRight().getLeft().getData());
        assertEquals(1, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 100, root.getRight().getRight().getData());
        assertEquals(1, root.getRight().getRight().getHeight());
        assertEquals(-1, root.getRight().getRight().getBalanceFactor());

        assertEquals((Integer) 11, root.getLeft().getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 13, root.getLeft().getLeft().getRight().getData());
        assertEquals(0, root.getLeft().getLeft().getRight().getHeight());
        assertEquals(0, root.getLeft().getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 24, root.getLeft().getRight().getLeft().getData());
        assertEquals(0, root.getLeft().getRight().getLeft().getHeight());
        assertEquals(0, root.getLeft().getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 74, root.getRight().getLeft().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 76, root.getRight().getLeft().getRight().getData());
        assertEquals(0, root.getRight().getLeft().getRight().getHeight());
        assertEquals(0, root.getRight().getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 105, root.getRight().getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getRight().getBalanceFactor());




        /*
               (26)
              /    \
            18      99
          /  \      / \
        12   25    75  100
       / \   /     / \   \
      11 13 24    74 76  105
      predecessor = 25
      move 25 to top node.
                25
              /    \
            18      99
          /  \      / \
        12   24    75  100
       / \         / \   \
      11 13       74 76  105
         */


        assertSame(i26, tree.remove(26));
        root = tree.getRoot();
        assertEquals(12, tree.size());

        assertEquals((Integer) 25, root.getData());
        assertEquals(3, root.getHeight());
        assertEquals(0, root.getBalanceFactor());

        assertEquals((Integer) 18, root.getLeft().getData());
        assertEquals(2, root.getLeft().getHeight());
        assertEquals(1, root.getLeft().getBalanceFactor());

        assertEquals((Integer) 99, root.getRight().getData());
        assertEquals(2, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());

        assertEquals((Integer) 12, root.getLeft().getLeft().getData());
        assertEquals(1, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 24, root.getLeft().getRight().getData());
        assertEquals(0, root.getLeft().getRight().getHeight());
        assertEquals(0, root.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 75, root.getRight().getLeft().getData());
        assertEquals(1, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 100, root.getRight().getRight().getData());
        assertEquals(1, root.getRight().getRight().getHeight());
        assertEquals(-1, root.getRight().getRight().getBalanceFactor());

        assertEquals((Integer) 11, root.getLeft().getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 13, root.getLeft().getLeft().getRight().getData());
        assertEquals(0, root.getLeft().getLeft().getRight().getHeight());
        assertEquals(0, root.getLeft().getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 74, root.getRight().getLeft().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 76, root.getRight().getLeft().getRight().getData());
        assertEquals(0, root.getRight().getLeft().getRight().getHeight());
        assertEquals(0, root.getRight().getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 105, root.getRight().getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getRight().getBalanceFactor());



        /*
               (25)
              /    \
            18      99
          /  \      / \
        12   24    75  100
       / \         / \   \
      11 13       74 76  105
      predecessor = 24
      move 24 to top node.
                24
              /    \
            18      99
           /        / \
         12        75  100
        / \        / \   \
       11 13      74 76  105
       after rotation
                24
              /    \
            12      99
           / \      / \
         11  18    75  100
             /     / \   \
           13     74 76  105
         */


        assertSame(i25, tree.remove(25));
        root = tree.getRoot();
        assertEquals(11, tree.size());

        assertEquals((Integer) 24, root.getData());
        assertEquals(3, root.getHeight());
        assertEquals(0, root.getBalanceFactor());

        assertEquals((Integer) 12, root.getLeft().getData());
        assertEquals(2, root.getLeft().getHeight());
        assertEquals(-1, root.getLeft().getBalanceFactor());

        assertEquals((Integer) 99, root.getRight().getData());
        assertEquals(2, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());

        assertEquals((Integer) 11, root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 18, root.getLeft().getRight().getData());
        assertEquals(1, root.getLeft().getRight().getHeight());
        assertEquals(1, root.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 75, root.getRight().getLeft().getData());
        assertEquals(1, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 100, root.getRight().getRight().getData());
        assertEquals(1, root.getRight().getRight().getHeight());
        assertEquals(-1, root.getRight().getRight().getBalanceFactor());

        assertEquals((Integer) 13, root.getLeft().getRight().getLeft().getData());
        assertEquals(0, root.getLeft().getRight().getLeft().getHeight());
        assertEquals(0, root.getLeft().getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 74, root.getRight().getLeft().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 76, root.getRight().getLeft().getRight().getData());
        assertEquals(0, root.getRight().getLeft().getRight().getHeight());
        assertEquals(0, root.getRight().getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 105, root.getRight().getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getRight().getBalanceFactor());



        /*
               (24)
              /    \
            12      99
           / \      / \
         11  18    75  100
             /     / \   \
           13     74 76  105
      predecessor = 18
      move 18 to top node.
                18
              /    \
            12      99
           / \      / \
         11  13    75  100
                   / \   \
                  74 76  105
         */


        assertSame(i24, tree.remove(24));
        root = tree.getRoot();
        assertEquals(10, tree.size());


        assertEquals((Integer) 18, root.getData());
        assertEquals(3, root.getHeight());
        assertEquals(-1, root.getBalanceFactor());

        assertEquals((Integer) 12, root.getLeft().getData());
        assertEquals(1, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());

        assertEquals((Integer) 99, root.getRight().getData());
        assertEquals(2, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());

        assertEquals((Integer) 11, root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 13, root.getLeft().getRight().getData());
        assertEquals(0, root.getLeft().getRight().getHeight());
        assertEquals(0, root.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 75, root.getRight().getLeft().getData());
        assertEquals(1, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 100, root.getRight().getRight().getData());
        assertEquals(1, root.getRight().getRight().getHeight());
        assertEquals(-1, root.getRight().getRight().getBalanceFactor());

        assertEquals((Integer) 74, root.getRight().getLeft().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 76, root.getRight().getLeft().getRight().getData());
        assertEquals(0, root.getRight().getLeft().getRight().getHeight());
        assertEquals(0, root.getRight().getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 105, root.getRight().getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getRight().getBalanceFactor());


        /*
               (18)
              /    \
            12      99
           / \      / \
         11  13    75  100
                   / \   \
                  74 76  105
      predecessor = 13
      move 13 to top node.
                13
              /    \
            12      99
           /        / \
         11        75  100
                   / \   \
                  74 76  105
         */


        assertSame(i18, tree.remove(18));
        root = tree.getRoot();
        assertEquals(9, tree.size());


        assertEquals((Integer) 13, root.getData());
        assertEquals(3, root.getHeight());
        assertEquals(-1, root.getBalanceFactor());

        assertEquals((Integer) 12, root.getLeft().getData());
        assertEquals(1, root.getLeft().getHeight());
        assertEquals(1, root.getLeft().getBalanceFactor());

        assertEquals((Integer) 99, root.getRight().getData());
        assertEquals(2, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());

        assertEquals((Integer) 11, root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 75, root.getRight().getLeft().getData());
        assertEquals(1, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 100, root.getRight().getRight().getData());
        assertEquals(1, root.getRight().getRight().getHeight());
        assertEquals(-1, root.getRight().getRight().getBalanceFactor());

        assertEquals((Integer) 74, root.getRight().getLeft().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 76, root.getRight().getLeft().getRight().getData());
        assertEquals(0, root.getRight().getLeft().getRight().getHeight());
        assertEquals(0, root.getRight().getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 105, root.getRight().getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getRight().getBalanceFactor());



        /*
               (13)
              /    \
            12      99
           /        / \
         11        75  100
                   / \   \
                  74 76  105
      predecessor = 12
      move 12 to top node.
                12
              /    \
            11      99
                    / \
                   75  100
                   / \   \
                  74 76  105
           ->
                12  99
                / \ / \
               11  75  100
                   / \   \
                  74 76  105
           ->
                   99
                  /  \
                12   100
                / \    \
               11  75  105
                   / \
                  74 76
         */


        assertSame(i13, tree.remove(13));
        root = tree.getRoot();
        assertEquals(8, tree.size());

        assertEquals((Integer) 99, root.getData());
        assertEquals(3, root.getHeight());
        assertEquals(1, root.getBalanceFactor());

        assertEquals((Integer) 12, root.getLeft().getData());
        assertEquals(2, root.getLeft().getHeight());
        assertEquals(-1, root.getLeft().getBalanceFactor());

        assertEquals((Integer) 100, root.getRight().getData());
        assertEquals(1, root.getRight().getHeight());
        assertEquals(-1, root.getRight().getBalanceFactor());

        assertEquals((Integer) 11, root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 75, root.getLeft().getRight().getData());
        assertEquals(1, root.getLeft().getRight().getHeight());
        assertEquals(0, root.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 105, root.getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getBalanceFactor());

        assertEquals((Integer) 74, root.getLeft().getRight().getLeft().getData());
        assertEquals(0, root.getLeft().getRight().getLeft().getHeight());
        assertEquals(0, root.getLeft().getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 76, root.getLeft().getRight().getRight().getData());
        assertEquals(0, root.getLeft().getRight().getRight().getHeight());
        assertEquals(0, root.getLeft().getRight().getRight().getBalanceFactor());


    }

    @Test(timeout = TIMEOUT)
    public void testRemoveException5() {

        /*
                50
              /    \
            18      99
          /  \      / \
        12   25    75  100
       / \   / \   / \   \
      11 13 24 26 74 76  105
         */

        Integer i50 = 50;
        Integer i18 = 18;
        Integer i99 = 99;
        Integer i12 = 12;
        Integer i25 = 25;
        Integer i75 = 75;
        Integer i100 = 100;
        Integer i11 = 11;
        Integer i13 = 13;
        Integer i24 = 24;
        Integer i26 = 26;
        Integer i74 = 74;
        Integer i76 = 76;
        Integer i105 = 105;
        Integer i999 = 999;

        tree.add(i50);
        tree.add(i18);
        tree.add(i99);
        tree.add(i12);
        tree.add(i25);
        tree.add(i75);
        tree.add(i100);
        tree.add(i11);
        tree.add(i13);
        tree.add(i24);
        tree.add(i26);
        tree.add(i74);
        tree.add(i76);
        tree.add(i105);

        try {
            tree.remove(null);
            fail();
        } catch (IllegalArgumentException e) {

        }

        try {
            tree.remove(i999);
            fail();
        } catch (NoSuchElementException e) {

        }
    }

    @Test(timeout = TIMEOUT)
    public void testGet5() {

        /*
                50
              /    \
            18      99
          /  \      / \
        12   25    75  100
       / \   / \   / \   \
      11 13 24 26 74 76  105
         */

        Integer i50 = 50;
        Integer i18 = 18;
        Integer i99 = 99;
        Integer i12 = 12;
        Integer i25 = 25;
        Integer i75 = 75;
        Integer i100 = 100;
        Integer i11 = 11;
        Integer i13 = 13;
        Integer i24 = 24;
        Integer i26 = 26;
        Integer i74 = 74;
        Integer i76 = 76;
        Integer i105 = 105;

        tree.add(i50);
        tree.add(i18);
        tree.add(i99);
        tree.add(i12);
        tree.add(i25);
        tree.add(i75);
        tree.add(i100);
        tree.add(i11);
        tree.add(i13);
        tree.add(i24);
        tree.add(i26);
        tree.add(i74);
        tree.add(i76);
        tree.add(i105);

        assertEquals(14, tree.size());

        assertSame(i50, tree.get(50));
        assertSame(i18, tree.get(18));
        assertSame(i99, tree.get(99));
        assertSame(i12, tree.get(12));
        assertSame(i25, tree.get(25));
        assertSame(i75, tree.get(75));
        assertSame(i100, tree.get(100));
        assertSame(i11, tree.get(11));
        assertSame(i13, tree.get(13));
        assertSame(i24, tree.get(24));
        assertSame(i26, tree.get(26));
        assertSame(i74, tree.get(74));
        assertSame(i76, tree.get(76));
        assertSame(i105, tree.get(105));
    }

    @Test(timeout = TIMEOUT)
    public void testGetException5() {

        /*
                50
              /    \
            18      99
          /  \      / \
        12   25    75  100
       / \   / \   / \   \
      11 13 24 26 74 76  105
         */

        Integer i50 = 50;
        Integer i18 = 18;
        Integer i99 = 99;
        Integer i12 = 12;
        Integer i25 = 25;
        Integer i75 = 75;
        Integer i100 = 100;
        Integer i11 = 11;
        Integer i13 = 13;
        Integer i24 = 24;
        Integer i26 = 26;
        Integer i74 = 74;
        Integer i76 = 76;
        Integer i105 = 105;
        Integer i999 = 999;

        tree.add(i50);
        tree.add(i18);
        tree.add(i99);
        tree.add(i12);
        tree.add(i25);
        tree.add(i75);
        tree.add(i100);
        tree.add(i11);
        tree.add(i13);
        tree.add(i24);
        tree.add(i26);
        tree.add(i74);
        tree.add(i76);
        tree.add(i105);

        try {
            tree.get(null);
            fail();
        } catch (IllegalArgumentException e) {

        }

        try {
            tree.get(i999);
            fail();
        } catch (NoSuchElementException e) {

        }
    }

    @Test(timeout = TIMEOUT)
    public void testContains5() {

        /*
                50
              /    \
            18      99
          /  \      / \
        12   25    75  100
       / \   / \   / \   \
      11 13 24 26 74 76  105
         */

        Integer i50 = 50;
        Integer i18 = 18;
        Integer i99 = 99;
        Integer i12 = 12;
        Integer i25 = 25;
        Integer i75 = 75;
        Integer i100 = 100;
        Integer i11 = 11;
        Integer i13 = 13;
        Integer i24 = 24;
        Integer i26 = 26;
        Integer i74 = 74;
        Integer i76 = 76;
        Integer i105 = 105;

        tree.add(i50);
        tree.add(i18);
        tree.add(i99);
        tree.add(i12);
        tree.add(i25);
        tree.add(i75);
        tree.add(i100);
        tree.add(i11);
        tree.add(i13);
        tree.add(i24);
        tree.add(i26);
        tree.add(i74);
        tree.add(i76);
        tree.add(i105);

        assertEquals(14, tree.size());

        assertTrue(tree.contains(50));
        assertTrue(tree.contains(18));
        assertTrue(tree.contains(99));
        assertTrue(tree.contains(12));
        assertTrue(tree.contains(25));
        assertTrue(tree.contains(75));
        assertTrue(tree.contains(100));
        assertTrue(tree.contains(11));
        assertTrue(tree.contains(13));
        assertTrue(tree.contains(24));
        assertTrue(tree.contains(26));
        assertTrue(tree.contains(74));
        assertTrue(tree.contains(76));
        assertTrue(tree.contains(105));
        assertFalse(tree.contains(999));
    }

    @Test(timeout = TIMEOUT)
    public void testContainsException5() {

        /*
                50
              /    \
            18      99
          /  \      / \
        12   25    75  100
       / \   / \   / \   \
      11 13 24 26 74 76  105
         */

        Integer i50 = 50;
        Integer i18 = 18;
        Integer i99 = 99;
        Integer i12 = 12;
        Integer i25 = 25;
        Integer i75 = 75;
        Integer i100 = 100;
        Integer i11 = 11;
        Integer i13 = 13;
        Integer i24 = 24;
        Integer i26 = 26;
        Integer i74 = 74;
        Integer i76 = 76;
        Integer i105 = 105;
        Integer i999 = 999;

        tree.add(i50);
        tree.add(i18);
        tree.add(i99);
        tree.add(i12);
        tree.add(i25);
        tree.add(i75);
        tree.add(i100);
        tree.add(i11);
        tree.add(i13);
        tree.add(i24);
        tree.add(i26);
        tree.add(i74);
        tree.add(i76);
        tree.add(i105);

        try {
            tree.contains(null);
            fail();
        } catch (IllegalArgumentException e) {

        }

    }

    @Test(timeout = TIMEOUT)
    public void testHeight5() {

        Integer i50 = 50;
        Integer i18 = 18;
        Integer i99 = 99;
        Integer i12 = 12;
        Integer i25 = 25;
        Integer i75 = 75;
        Integer i100 = 100;
        Integer i11 = 11;
        Integer i13 = 13;
        Integer i24 = 24;
        Integer i26 = 26;
        Integer i74 = 74;
        Integer i76 = 76;
        Integer i105 = 105;

        tree.add(i50);
        assertEquals(0, tree.height());
        /*
                50
         */
        tree.add(i18);
        tree.add(i99);
        assertEquals(1, tree.height());
        /*
                50
              /    \
            18      99
         */
        tree.add(i12);
        tree.add(i25);
        tree.add(i75);
        tree.add(i100);
        assertEquals(2, tree.height());
        /*
                50
              /    \
            18      99
          /  \      / \
        12   25    75  100
         */
        tree.add(i11);
        tree.add(i13);
        tree.add(i24);
        tree.add(i26);
        tree.add(i74);
        tree.add(i76);
        tree.add(i105);
        assertEquals(3, tree.height());
        /*
                50
              /    \
            18      99
          /  \      / \
        12   25    75  100
       / \   / \   / \   \
      11 13 24 26 74 76  105
         */
    }

    @Test(timeout = TIMEOUT)
    public void testClear5() {

        /*
                50
              /    \
            18      99
          /  \      / \
        12   25    75  100
       / \   / \   / \   \
      11 13 24 26 74 76  105
         */

        Integer i50 = 50;
        Integer i18 = 18;
        Integer i99 = 99;
        Integer i12 = 12;
        Integer i25 = 25;
        Integer i75 = 75;
        Integer i100 = 100;
        Integer i11 = 11;
        Integer i13 = 13;
        Integer i24 = 24;
        Integer i26 = 26;
        Integer i74 = 74;
        Integer i76 = 76;
        Integer i105 = 105;

        tree.add(i50);
        tree.add(i18);
        tree.add(i99);
        tree.add(i12);
        tree.add(i25);
        tree.add(i75);
        tree.add(i100);
        tree.add(i11);
        tree.add(i13);
        tree.add(i24);
        tree.add(i26);
        tree.add(i74);
        tree.add(i76);
        tree.add(i105);

        tree.clear();
        assertEquals(0, tree.size());
        assertNull(tree.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void testWordSearch5() {

        AVL<String> letterTree = new AVL<>();

        String a = new String("a");
        String b = new String("b");
        String c = new String("c");
        String d = new String("d");
        String e = new String("e");
        String f = new String("f");
        String g = new String("g");
        String h = new String("h");
        String i = new String("i");
        String j = new String("j");
        String k = new String("k");
        String l = new String("l");
        String m = new String("m");
        String n = new String("n");
        String o = new String("o");
        String p = new String("p");
        String q = new String("q");
        String r = new String("r");
        String s = new String("s");
        String t = new String("t");
        String u = new String("u");
        String v = new String("v");
        String w = new String("w");
        String x = new String("x");
        String y = new String("y");
        String z = new String("z");
        letterTree.add(p);
        letterTree.add(h);
        letterTree.add(w);
        letterTree.add(d);
        letterTree.add(l);
        letterTree.add(t);
        letterTree.add(y);
        letterTree.add(b);
        letterTree.add(f);
        letterTree.add(j);
        letterTree.add(n);
        letterTree.add(r);
        letterTree.add(v);
        letterTree.add(x);
        letterTree.add(z);
        letterTree.add(a);
        letterTree.add(c);
        letterTree.add(e);
        letterTree.add(g);
        letterTree.add(i);
        letterTree.add(k);
        letterTree.add(m);
        letterTree.add(o);
        letterTree.add(q);
        letterTree.add(s);
        letterTree.add(u);


        /*
                       p
                   /      \
                /            \
              /                \
             h                  w
           /   \             /     \
          d      l           t      y
         / \    /  \       /  \   /  \
        b   f   j   n     r   v  x    z
       /\  /\   /\  /\   /\  /
      a c  e g  i k m o q s  u
        */

        assertEquals(26, letterTree.size());

        String[] word = new String[] {"p", "h", "d", "b", "a"};
        List<String> path = letterTree.wordSearch(word);
        assertEquals(5, path.size());

        assertSame(p, path.get(0));
        assertSame(h, path.get(1));
        assertSame(d, path.get(2));
        assertSame(b, path.get(3));
        assertSame(a, path.get(4));

        word = new String[] {"a", "b", "d", "h", "p"};
        path = letterTree.wordSearch(word);
        assertEquals(5, path.size());

        assertSame(a, path.get(0));
        assertSame(b, path.get(1));
        assertSame(d, path.get(2));
        assertSame(h, path.get(3));
        assertSame(p, path.get(4));

        word = new String[] {"p", "w", "y", "z"};
        path = letterTree.wordSearch(word);
        assertEquals(4, path.size());

        assertSame(p, path.get(0));
        assertSame(w, path.get(1));
        assertSame(y, path.get(2));
        assertSame(z, path.get(3));

        word = new String[] {"z", "y", "w", "p"};
        path = letterTree.wordSearch(word);
        assertEquals(4, path.size());

        assertSame(z, path.get(0));
        assertSame(y, path.get(1));
        assertSame(w, path.get(2));
        assertSame(p, path.get(3));

        word = new String[] {"d", "h", "p", "w"};
        path = letterTree.wordSearch(word);
        assertEquals(4, path.size());

        assertSame(d, path.get(0));
        assertSame(h, path.get(1));
        assertSame(p, path.get(2));
        assertSame(w, path.get(3));

        word = new String[] {"y", "w", "p", "h"};
        path = letterTree.wordSearch(word);
        assertEquals(4, path.size());

        assertSame(y, path.get(0));
        assertSame(w, path.get(1));
        assertSame(p, path.get(2));
        assertSame(h, path.get(3));

        word = new String[] {"p", "h", "l", "j", "k"};
        path = letterTree.wordSearch(word);
        assertEquals(5, path.size());

        assertSame(p, path.get(0));
        assertSame(h, path.get(1));
        assertSame(l, path.get(2));
        assertSame(j, path.get(3));
        assertSame(k, path.get(4));

        word = new String[] {"k", "j", "l", "h", "p"};
        path = letterTree.wordSearch(word);
        assertEquals(5, path.size());

        assertSame(k, path.get(0));
        assertSame(j, path.get(1));
        assertSame(l, path.get(2));
        assertSame(h, path.get(3));
        assertSame(p, path.get(4));

        word = new String[] {"j", "l", "n", "m"};
        path = letterTree.wordSearch(word);
        assertEquals(4, path.size());

        assertSame(j, path.get(0));
        assertSame(l, path.get(1));
        assertSame(n, path.get(2));
        assertSame(m, path.get(3));

        word = new String[] {"m", "n", "l", "j"};
        path = letterTree.wordSearch(word);
        assertEquals(4, path.size());

        assertSame(m, path.get(0));
        assertSame(n, path.get(1));
        assertSame(l, path.get(2));
        assertSame(j, path.get(3));

        word = new String[] {"m"};
        path = letterTree.wordSearch(word);
        assertEquals(1, path.size());

        assertSame(m, path.get(0));

        word = new String[] {"p"};
        path = letterTree.wordSearch(word);
        assertEquals(1, path.size());

        assertSame(p, path.get(0));

        word = new String[] {};
        path = letterTree.wordSearch(word);
        assertEquals(0, path.size());
        // This should not throw any Exception
        // This does not means null.
        // wordSearch([]) returns an empty list [].
        // From JavaDOCS.


    }

    @Test(timeout = TIMEOUT)
    public void testWordSearchException5() {

        AVL<String> letterTree = new AVL<>();

        String a = new String("a");
        String b = new String("b");
        String c = new String("c");
        String d = new String("d");
        String e = new String("e");
        String f = new String("f");
        String g = new String("g");
        String h = new String("h");
        String i = new String("i");
        String j = new String("j");
        String k = new String("k");
        String l = new String("l");
        String m = new String("m");
        String n = new String("n");
        String o = new String("o");
        String p = new String("p");
        String q = new String("q");
        String r = new String("r");
        String s = new String("s");
        String t = new String("t");
        String u = new String("u");
        String v = new String("v");
        String w = new String("w");
        String x = new String("x");
        String y = new String("y");
        String z = new String("z");
        letterTree.add(p);
        letterTree.add(h);
        letterTree.add(w);
        letterTree.add(d);
        letterTree.add(l);
        letterTree.add(t);
        letterTree.add(y);
        letterTree.add(b);
        letterTree.add(f);
        letterTree.add(j);
        letterTree.add(n);
        letterTree.add(r);
        letterTree.add(v);
        letterTree.add(x);
        letterTree.add(z);
        letterTree.add(c);
        letterTree.add(e);
        letterTree.add(g);
        letterTree.add(i);
        letterTree.add(k);
        letterTree.add(m);
        letterTree.add(o);
        letterTree.add(q);
        letterTree.add(s);


        /*
                       p
                   /      \
                /            \
              /                \
             h                  w
           /   \             /     \
          d      l           t      y
         / \    /  \       /  \   /  \
        b   f   j   n     r   v  x    z
        \  /\   /\  /\   /\
        c  e g  i k m o q s
        */

        assertEquals(24, letterTree.size());

        String[] word;
        List<String> path;

        try {
            word = null;
            path = letterTree.wordSearch(word);
            fail();
        } catch (IllegalArgumentException ee) {

        }

        try {
            word = new String[] {"o", "x"};
            path = letterTree.wordSearch(word);
            fail();

        } catch (NoSuchElementException ee) {

        }

        try {
            word = new String[] {"o", "s", "q", "n"};
            path = letterTree.wordSearch(word);
            fail();

        } catch (NoSuchElementException ee) {

        }

        try {
            word = new String[] {"a", "u"};
            path = letterTree.wordSearch(word);
            fail();

        } catch (NoSuchElementException ee) {

        }


        try {
            word = new String[] {"a"};
            path = letterTree.wordSearch(word);
            fail();

        } catch (NoSuchElementException ee) {

        }

        try {
            word = new String[] {"u"};
            path = letterTree.wordSearch(word);
            fail();

        } catch (NoSuchElementException ee) {

        }

        try {
            word = new String[] {"t", "w", "i", "h"};
            path = letterTree.wordSearch(word);
            fail();

        } catch (NoSuchElementException ee) {

        }

        try {
            word = new String[] {"d", "h", "l", "j", "a", "b", "k"};
            path = letterTree.wordSearch(word);
            fail();

        } catch (NoSuchElementException ee) {

        }

        try {
            word = new String[] {"r", "t", "v", "u"};
            path = letterTree.wordSearch(word);
            fail();

        } catch (NoSuchElementException ee) {

        }

    }
}