import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.NoSuchElementException;

/**
 * Your implementation of a LinearProbingHashMap.
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
public class LinearProbingHashMap<K, V> {

    /**
     * The initial capacity of the LinearProbingHashMap when created with the
     * default constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /**
     * The max load factor of the LinearProbingHashMap
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final double MAX_LOAD_FACTOR = 0.67;

    // Do not add new instance variables or modify existing ones.
    private LinearProbingMapEntry<K, V>[] table;
    private int size;

    /**
     * Constructs a new LinearProbingHashMap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     *
     * Use constructor chaining.
     */
    public LinearProbingHashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Constructs a new LinearProbingHashMap.
     *
     * The backing array should have an initial capacity of initialCapacity.
     *
     * You may assume initialCapacity will always be positive.
     *
     * @param initialCapacity the initial capacity of the backing array
     */
    public LinearProbingHashMap(int initialCapacity) {
        table = new LinearProbingMapEntry[initialCapacity];
    }

    /**
     * Adds the given key-value pair to the map. If an entry in the map
     * already has this key, replace the entry's value with the new one
     * passed in.
     *
     * In the case of a collision, use linear probing as your resolution
     * strategy.
     *
     * Before actually adding any data to the HashMap, you should check to
     * see if the array would violate the max load factor if the data was
     * added. For example, let's say the array is of length 5 and the current
     * size is 3 (LF = 0.6). For this example, assume that no elements are
     * removed in between steps. If another entry is attempted to be added,
     * before doing anything else, you should check whether (3 + 1) / 5 = 0.8
     * is larger than the max LF. It is, so you would trigger a resize before
     * you even attempt to add the data or figure out if it's a duplicate. Be
     * careful to consider the differences between integer and double
     * division when calculating load factor.
     *
     * When regrowing, resize the length of the backing table to
     * 2 * old length + 1. You must use the resizeBackingTable method to do so.
     *
     * Return null if the key was not already in the map. If it was in the map,
     * return the old value associated with it.
     *
     * @param key   the key to add
     * @param value the value to add
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     * @throws java.lang.IllegalArgumentException if key or value is null
     */
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Cannot add null values or use null keys");
        }
        if (((size + 1.0) / (double) table.length) > MAX_LOAD_FACTOR) {
            this.resizeBackingTable(table.length * 2 + 1);
        }
        int startingIndex = key.hashCode();
        int removedIndex = 0;
        boolean foundRemoved = false;
        V oldValue = null;
        for (int i = Math.abs(startingIndex % table.length), count = -1; count <= size;
             i = Math.abs((i + 1) % table.length)) {
            //put key and value in null spot or deleted spot if one was passed earlier
            if (table[i] == null) {
                if (foundRemoved) {
                    table[removedIndex].setKey(key);
                    table[removedIndex].setValue(value);
                    table[removedIndex].setRemoved(false);
                } else {
                    table[i] = new LinearProbingMapEntry<>(key, value);
                }
                size++;
                return oldValue;
            } else if (table[i].getKey().equals(key) && !table[i].isRemoved()) {
                //if key already exists, replace value
                oldValue = table[i].getValue();
                table[i].setKey(key);
                table[i].setValue(value);
                table[i].setRemoved(false);
                return oldValue;
            } else if (table[i].isRemoved() && !foundRemoved) {
                //store the value of the first removed index
                removedIndex = i;
                foundRemoved = true;
            } else if (!table[i].isRemoved()) {
                count++;
                //if size number of non-deleted, non-null entries have been passed, add at deleted spot or next spot
                if (count == size) {
                    if (foundRemoved) {
                        table[removedIndex].setKey(key);
                        table[removedIndex].setValue(value);
                        table[removedIndex].setRemoved(false);
                    } else {
                        table[Math.abs((i + 1) % table.length)] = new LinearProbingMapEntry<>(key, value);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Removes the entry with a matching key from map by marking the entry as
     * removed.
     *
     * @param key the key to remove
     * @return the value previously associated with the key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot remove a null key");
        }

        int startingIndex = key.hashCode();
        for (int i = Math.abs(startingIndex % table.length), count = 0; count < size;
             i = Math.abs((i + 1) % table.length)) {
            if ((table[i] != null) && !table[i].isRemoved() && table[i].getKey().equals(key)) {
                table[i].setRemoved(true);
                size--;
                return table[i].getValue();
            } else if ((table[i] != null) && !table[i].isRemoved()) {
                //when size number of valid entries have been passed, end loop and throw exception
                count++;
            }
        }
        throw new NoSuchElementException("Key not found in map");
    }

    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for in the map
     * @return the value associated with the given key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot retrieve a null key");
        }
        int startingIndex = key.hashCode();
        for (int i = Math.abs(startingIndex % table.length), count = 0; count < size;
             i = Math.abs((i + 1) % table.length)) {
            if ((table[i] != null) && !table[i].isRemoved() && table[i].getKey().equals(key)) {
                return table[i].getValue();
            } else if (table[i] != null && !table[i].isRemoved()) {
                //when size number of valid entries have been passed, end loop and throw exception
                count++;
            }
        }
        throw new NoSuchElementException("Key does not exist in map");
    }

    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for in the map
     * @return true if the key is contained within the map, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if key is null
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot look for null key");
        }
        int startingIndex = key.hashCode();
        for (int i = Math.abs(startingIndex % table.length), count = 0; count < size;
             i = Math.abs((i + 1) % table.length)) {
            if ((table[i] != null) && !table[i].isRemoved() && table[i].getKey().equals(key)) {
                return true;
            } else if (table[i] != null && !table[i].isRemoved()) {
                //when size number of valid entries have been passed, end loop and return false
                count++;
            }

        }
        return false;
    }

    /**
     * Returns a Set view of the keys contained in this map.
     *
     * Use java.util.HashSet.
     *
     * @return the set of keys in this map
     */
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        for (int i = 0, count = 0; count < size; i++) {
            if ((table[i] != null) && !table[i].isRemoved()) {
                keySet.add(table[i].getKey());
                count++;
            }
        }
        return keySet;
    }

    /**
     * Returns a List view of the values contained in this map.
     *
     * Use java.util.ArrayList or java.util.LinkedList.
     *
     * You should iterate over the table in order of increasing index and add
     * entries to the List in the order in which they are traversed.
     *
     * @return list of values in this map
     */
    public List<V> values() {
        List<V> valueSet = new ArrayList<>();
        for (int i = 0, count = 0; count < size; i++) {
            if ((table[i] != null) && !table[i].isRemoved()) {
                valueSet.add(table[i].getValue());
                count++;
            }
        }
        return valueSet;
    }

    /**
     * Resize the backing table to length.
     *
     * Disregard the load factor for this method. So, if the passed in length is
     * smaller than the current capacity, and this new length causes the table's
     * load factor to exceed MAX_LOAD_FACTOR, you should still resize the table
     * to the specified length and leave it at that capacity.
     *
     * You should iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed.
     *
     * Since resizing the backing table is working with the non-duplicate
     * data already in the table, you shouldn't explicitly check for
     * duplicates.
     *
     * Hint: You cannot just simply copy the entries over to the new array.
     *
     * @param length new length of the backing table
     * @throws java.lang.IllegalArgumentException if length is less than the
     *                                            number of items in the hash
     *                                            map
     */
    public void resizeBackingTable(int length) {
        if (length < size) {
            throw new IllegalArgumentException("Cannot shrink table to size less than number of elements stored");
        }
        LinearProbingMapEntry<K, V>[] newTable = new LinearProbingMapEntry[length];
        int outerCount = 0;
        //run through old table until size number of valid entries are encountered
        for (int x = 0; outerCount < size; x++) {
            if ((table[x] != null) && !table[x].isRemoved()) {
                int startingIndex = table[x].getKey().hashCode();
                //add entry at first available null spot relative to its starting index
                for (int i = Math.abs(startingIndex % newTable.length); i < newTable.length;
                     i = Math.abs((i + 1) % newTable.length)) {
                    if (newTable[i] == null) {
                        newTable[i] = new LinearProbingMapEntry<>(table[x].getKey(), table[x].getValue());
                        break;
                    }
                }
                outerCount++;
            }
        }
        table = newTable;
    }



    /**
     * Clears the map.
     *
     * Resets the table to a new array of the INITIAL_CAPACITY and resets the
     * size.
     *
     * Must be O(1).
     */
    public void clear() {
        table = new LinearProbingMapEntry[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the table of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the table of the map
     */
    public LinearProbingMapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

    /**
     * Returns the size of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the map
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
