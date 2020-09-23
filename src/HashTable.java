import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class HashTable<TKey, TValue>
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
                        "\n{%s, %s}",
                        key,
                        value) +
                    (next == null
                            ? ""
                            : ", " + next);
        }
    }

    private Node<TKey, TValue>[] bucket;

    public HashTable()
    {
        bucket = (Node<TKey, TValue>[])new Node[8];
    }

    public HashTable(int size)
    {
        bucket = (Node<TKey, TValue>[])new Node[size];
    }


    private int hashIndex(TKey key)
    {
        return Math.abs(key.hashCode() % bucket.length);
    }

    // adds a key and an associated value to the table
    public void put(TKey key, TValue value)
    {
        Node<TKey, TValue> newNode = new Node();
        newNode.key = key;
        newNode.value = value;

        // bucket index created from the hash
        int hashIndex = hashIndex(key);

        // the first item for the index is null which means there will not be
        // any items of that hash in the bucket
        if (bucket[hashIndex] == null)
        {
            bucket[hashIndex] = newNode;
        }
        else
        {
            // first node of the inner list in the bucket
            Node<TKey, TValue> current = bucket[hashIndex];

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
            }
        }
    }

    // returns the value associated with the key or throws a NoSuchElement
    // exception
    public TValue get(TKey key)
    {
        int hashIndex = hashIndex(key);

        // get the start of the inner list
        Node<TKey, TValue> current = bucket[hashIndex];

        // no element at hash index, so there is no stored key of that value
        if (current == null)
        {
            throw new NoSuchElementException();
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

        // which of the cases above was failing is unknown, check for key
        // equality, otherwise that element doesn't exist (current should
        // be the last or the element with the equal key)
        if (current.key.equals(key))
        {
            return current.value;
        }

        throw new NoSuchElementException();
    }

    public boolean contains(TKey key)
    {
        int hashIndex = hashIndex(key);

        // first element with that hashIndex is null so no such keys in bucket
        if (bucket[hashIndex] == null)
        {
            return false;
        }

        // get the start of the inner list
        Node<TKey, TValue> current = bucket[hashIndex];

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
        return Arrays.toString(bucket);
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
        // the '\n' character is not cleared from the buffer by nextInt(),
        // this clears it
        System.out.print("Number of inputs: ");

        int amount = in.nextInt();
        in.nextLine();

        System.out.println("Inputs:");
        for (int count = 0; count < amount; count++)
        {
            String line = in.nextLine();
            String[] values = line.split("\\s+");

            int integer = Integer.parseInt(values[1]);
            String str = values[0];

            ht.put(str, integer);
        }

        System.out.println(ht);
    }
}
