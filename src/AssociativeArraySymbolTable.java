import java.util.Comparator;
import java.util.NoSuchElementException;

public class AssociativeArraySymbolTable<TKey extends Comparable<TKey>, TValue>
{
    private class Pair
    {
        public TKey Key;
        public TValue Value;
    }

    Pair[] pairs;
    int emptyIndex;

    public AssociativeArraySymbolTable()
    {
        pairs = (Pair[])new Object[8];
        emptyIndex = 0;
    }

    public AssociativeArraySymbolTable(int initialSize)
    {
        pairs = (Pair[])new Object[initialSize];
        emptyIndex = 0;
    }

    // resizes to the specified size
    private void resize(int newSize)
    {
        Pair[] newPairs = (Pair[])new Object[newSize];

        System.arraycopy(pairs, 0, newPairs, 0, pairs.length);

        pairs = newPairs;
    }

    // adds a key and associated key into the table
    public void put(TKey key, TValue value)
    {
        Pair p = new Pair();
        p.Key = key;
        p.Value = value;

        // if its the first element or bigger than the last element, place it
        // at the last empty spot.
        if (emptyIndex == 0 ||
                key.compareTo(pairs[emptyIndex - 1].Key) > -1)
        {
            pairs[emptyIndex++] = p;
            return;
        }

        // find the index where p.Key is bigger than pairs[i].Key
        // we go until before emptyIndex - 1 because emptyIndex - 1 has already
        // been checked above
        int i = 0;
        for (;
             i < emptyIndex - 1 && key.compareTo(pairs[i].Key) < 0;
             i++);

        // if keys are equal, set `value` as the new value
        if (key.compareTo(pairs[i].Key) == 0)
        {
            pairs[i].Value = value;
            // nothing more to be done in this case
            return;
        }
        // otherwise, push everything up and set `pairs[i]` to the node

        // push everything else up, as it is larger
        System.arraycopy(pairs, i, pairs, i + 1, emptyIndex - i);

        // put the pair into its correct place
        pairs[i] = p;

        // increment the index to an empty spot
        emptyIndex++;

        // in case array starts to fill up, resize so more space is available
        if (emptyIndex > pairs.length)
        {
            resize(pairs.length * 2);
        }
    }

    // returns the index of the key value pair, or -1 if no such key exists
    public int findIndex(TKey key)
    {
        // binary search

        // left is the left wall of the partition and likewise for right
        // we begin with both ends at either side of the array
        int left = 0;
        int right = emptyIndex - 1;

        // middle is the middle index
        int middle = (left + right) / 2;

        int compare = key.compareTo(pairs[middle].Key);

        // find the index of the element with the given key
        while (compare != 0)
        {
            // if left somehow becomes bigger than right, no such key exists
            if (left > right)
            {
                return -1;
            }

            // compare < 0 means that key < middle key, which means that
            // the pair searched for is in the lower half of the partition
            if (compare < 0)
            {
                right = middle;
            }
            // opposite case; pair searched for is in the upper half of the
            // partition
            else // compare > 0
            {
                left = middle;
            }

            // find the new middle of the partition and compare the next elements
            middle = (left + right) / 2;
            compare = key.compareTo(pairs[middle].Key);
        }

        // index has been found, return the index of it
        return middle;
    }

    // returns the value associated with that key
    public TValue get(TKey key)
    {
        int index = findIndex(key);

        // if index < 0 then no element was found, therefore we throw this
        // exception
        if (index < 0)
        {
            throw new NoSuchElementException();
        }

        // otherwise we return the value of the element at the index
        return pairs[index].Value;
    }

    // returns true if an element with the given key exists, otherwise false
    public boolean contains(TKey key)
    {
        // findIndex returns -1 if the index couldn't be found
        // which if its >= 0 then an element was found and exists
        return findIndex(key) >= 0;
    }

    public static void main(String[] args)
    {

    }
}
