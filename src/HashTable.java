/*
    Author: Andreas Hammarstrand
    Written: 2020/09/23
    Updated: 2020/09/27
    Purpose:
        HashTable<TKey, TValue> attempts to implement a hash table with
        separate chaining.
        This class only implements searching, retrieval, and appending.
    Usage:
        Import the class to use the hash table or run the main method
        to run its tests. The input for the tests must be representation
        of "String{blank space}Integer".
 */

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class HashTable<TKey, TValue> implements Iterable<TKey>
{
    private static class Node<TKey, TValue>
    {
        public TKey key;
        public TValue value;
        public Node<TKey, TValue> next;

        @Override
        public String toString()
        {
            return String.format(
                    "{%s, %s}",
                    key,
                    value);
        }
    }

    private final Node<TKey, TValue>[] buckets;
    private int size;

    public HashTable()
    {
        buckets = (Node<TKey, TValue>[])new Node[128];
    }

    public HashTable(int size)
    {
        buckets = (Node<TKey, TValue>[])new Node[size];
    }


    public int size()
    {
        return size;
    }

    // returns the hash index for the given key
    private int hashIndex(TKey key)
    {
        // masked with 0x7fffffff to remove the negative sign then modulo the
        // length of the bucket array to limit the range of the index to
        // the buckets.
        return (key.hashCode() & 0x7fffffff) % buckets.length;
    }

    // adds a key and an associated value to the table
    public void put(TKey key, TValue value)
    {
        Node<TKey, TValue> newNode = new Node<>();
        newNode.key = key;
        newNode.value = value;

        // bucket index created from the hash
        int hashIndex = hashIndex(key);

        // the first item for the index is null which means there will not be
        // any items of that hash in the bucket
        if (buckets[hashIndex] == null)
        {
            buckets[hashIndex] = newNode;
            size++;
        }
        else
        {
            // first node of the inner list in the bucket
            Node<TKey, TValue> current = buckets[hashIndex];

            // before equality is checked, hashcode equality is checked
            // this is because the hashcode is stored in the object natively
            // and checking two int values is faster than checking the equality
            // of two entire objects
            while (current.next != null
                    && current.key.hashCode() != key.hashCode()
                    && !current.key.equals(key))
            {
                current = current.next;
            }

            // if an element with the same key was found then just replace
            // the value inside the element
            if (current.key.equals(key))
            {
                current.value = value;
            }
            // otherwise set the next node to be this new node, as `current`
            // is the last one
            else
            {
                current.next = newNode;
                size++;
            }
        }
    }

    // returns the value associated with the key or throws a NoSuchElement
    // exception
    public TValue get(TKey key)
    {
        // get the start of the inner list
        int hashIndex = hashIndex(key);
        Node<TKey, TValue> current = buckets[hashIndex];

        // no element at hash index, so there is no stored key of that value
        if (current == null)
        {
            throw new NoSuchElementException(
                    String.format(
                            "%s{%s}",
                            getClass().getName(),
                            key)
            );
        }

        // before equality is checked, hashcode equality is checked
        // this is because the hashcode is stored in the object natively
        // and checking two int values is faster than checking the equality
        // of two entire objects
        while (current.next != null
                && !(current.key.hashCode() == key.hashCode()
                    && current.key.equals(key)))
        {
            current = current.next;
        }

        // which of the cases above was failing is unknown, check for key
        // equality, otherwise that element doesn't exist (current should
        // be the last or the element with the equal key)
        if (current.key.equals(key))
        {
            return current.value;
        }

        // no element with that key could be found
        throw new NoSuchElementException(
                String.format(
                        "%s{%s}",
                        getClass().getName(),
                        key)
        );
    }

    public boolean contains(TKey key)
    {
        // get the start of the inner list
        int hashIndex = hashIndex(key);
        Node<TKey, TValue> current = buckets[hashIndex];

        // first element with that hashIndex is null so no such keys in bucket
        if (current == null)
        {
            return false;
        }

        // before equality is checked, hashcode equality is checked
        // this is because the hashcode is stored in the object natively
        // and checking two int values is faster than checking the equality
        // of two entire objects
        while (current.next != null
                && current.key.hashCode() != key.hashCode()
                && !current.key.equals(key))
        {
            current = current.next;
        }

        // the last node or possible equal
        return current.key.equals(key);
    }

    // returns a string representation of the object
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        // all values are contained within two square brackets
        sb.append('[');

        for (TKey key : this)
        {
            // change to nodes
            sb
                    .append(
                            String.format(
                                    "{%s, %s}",
                                    key,
                                    get(key))
                    ).append(", ");
        }

        // clear the last ", "
        sb.replace(sb.length() - 2, sb.length(), "");

        sb.append(']');
        return sb.toString();
    }

    // returns an iterator over the keys of the hash table
    @Override
    public Iterator<TKey> iterator()
    {
        return new Iterator<TKey>()
        {
            // index is the index of the current bucket
            int bucketIndex = 0;

            // count is the number of values that have been returned
            int count = 0;

            // currentNode is the currently selected node from a bucket
            Node<TKey, TValue> currentNode;

            // returns true if there is a next value, otherwise false
            @Override
            public boolean hasNext()
            {
                // there can only be more keys if
                //  1. there are more buckets
                // and
                //  2. the amount of returned keys is less than the amount of
                //     keys in the table
                return bucketIndex < buckets.length && count < size();
            }

            // returns the next key if there is one, otherwise a
            // NoSuchException is thrown
            @Override
            public TKey next()
            {
                // if there are no more items, throw an exception
                if (!hasNext())
                {
                    throw new NoSuchElementException();
                }
                // if the currentNode is not null then there is an additional
                // value in the bucket
                else if (currentNode != null)
                {
                    Node<TKey, TValue> holder =
                            currentNode;

                    currentNode =
                            currentNode.next;

                    count++;
                    return holder.key;
                }
                // if the next bucket is null, then iterate forwards
                // until a non null bucket is found
                // not reachable when there are no elements unless manually
                // called under such circumstances
                else if (buckets[bucketIndex] == null)
                {
                    bucketIndex++;
                    return next();
                }
                // the next bucket wasn't null; increment the index, set the
                // current node to the next one, and return the first node
                // key.
                else
                {
                    Node<TKey, TValue> holder =
                            buckets[bucketIndex++];

                    currentNode = holder.next;

                    count++;
                    return holder.key;
                }
            }
        };
    }

    // test method
    public static void main(String[] args)
    {
        // take the number of inputs from the user in the form of
        // "{string} {integer}", split them by whitespace and add them to
        // the symbol table as key and value, respectively.
        // lastly, print out the table
        HashTable<String, Integer> ht =
                new HashTable<>();

        Scanner in = new Scanner(System.in);
        System.out.print("Number of inputs: ");

        int amount = in.nextInt();
        // the '\n' character is not cleared from the buffer by nextInt(),
        // this clears it
        in.nextLine();

        // take in given amount of inputs
        System.out.println("Inputs:");
        for (int count = 0; count < amount; count++)
        {
            // separate the key and value by blank space
            String line = in.nextLine();
            String[] values = line.split("\\s+");

            // parse values into correct form
            int integer = Integer.parseInt(values[1]);
            String str = values[0];

            // add them to the hashtable
            ht.put(str, integer);
        }

        // print out the hash table
        System.out.println(ht);
    }
}
