/*
    Author: Andreas Hammarstrand
    Written: 2020/09/21
    Updated: 2020/09/27
    Purpose:
        BinarySearchTree attempts at implementing a binary search tree.
        This class only implements searching, retrieval, and appending.
    Usage:
        Import the class to use the binary search tree or run the main method
        to run its tests as well as showing the keys of the tree in prefix,
        infix, and postfix notation. The input for the tests must be representation
        of "String{blank space}Integer".
 */

import java.util.Scanner;

public class BinarySearchTree<TKey extends Comparable<TKey>, TValue>
{
    private class Node
    {
        public TKey key;
        public TValue value;
        public Node left;
        public Node right;

        // returns the a prefix representation of the node
        @Override
        public String toString()
        {
            return String.format(
                    "{%s, %s}"
                        + (left == null
                            ? ""
                            : ", " + left)
                        + (right == null
                            ? ""
                            : ", " + right),
                    key,
                    value
            );
        }
    }

    Node root;

    public BinarySearchTree() {}

    // recursive function to insert a new node into the binary search tree
    private void insert(Node root, Node newNode)
    {
        int comparison = newNode.key.compareTo(root.key);

        // newNode = root, just change values
        if (comparison == 0)
        {
            root.value = newNode.value;
        }
        // newNode > root, insert on left side
        else if (comparison > 0)
        {
            // there is no left sub tree, set node to be sub tree
            if (root.left == null)
            {
                root.left = newNode;
            }
            // recursively search left sub tree
            else
            {
                insert(root.left, newNode);
            }
        }
        // newNode < root, insert on right side
        else
        {
            // there is no right sub tree, set node to be sub tree
            if (root.right == null)
            {
                root.right = newNode;
            }
            // recursively search right sub tree
            else
            {
                insert(root.right, newNode);
            }
        }
    }

    // adds the key and the associated value into the binary search tree
    public void put(TKey key, TValue value)
    {
        Node n = new Node();
        n.value = value;
        n.key = key;

        // no nodes inside, n is the root
        if (root == null)
        {
            root = n;
        }
        // recursively search the tree fpr the right spot
        else
        {
            insert(root, n);
        }
    }

    // traverses the tree recursively until a root with the same key is found and
    // returns said root or null if no such root could be found
    private Node traverseFor(Node root, TKey key)
    {
        // no tree
        if (root == null)
        {
            return null;
        }

        int comparison = key.compareTo(root.key);

        // key = root, return root value
        if (comparison == 0)
        {
            return root;
        }
        // key > root, traverse left side for the key
        else if ( comparison > 0)
        {
            return traverseFor(root.left, key);
        }
        // key < root, traverse right side for the key
        else
        {
            return traverseFor(root.right, key);
        }
    }

    // returns the value associated with the key, or throws a null exception
    // if not found.
    public TValue get(TKey key)
    {
        return traverseFor(root, key).value;
    }

    // returns true if an element associated with the given key exists,
    // otherwise false
    public boolean contains(TKey key)
    {
        // if traverseFor returns null it means it couldn't find any element
        // associated with the key, which means the pair doesn't exists
        return traverseFor(root, key) != null;
    }

    // returns a string representation of the object; specifically in prefix
    // notion.
    @Override
    public String toString()
    {
        return "[" +
                (root == null
                        ? ""
                        : root) +
                "]";
    }

    // returns the prefix representation of the keys of the tree
    public String prefixRepresentation(Node n)
    {
        if (n == null)
        {
            return null;
        }

        String left = prefixRepresentation(n.left);
        String right = prefixRepresentation(n.right);

        return
                n.key +
                (left == null
                        ? ""
                        : " " + left) +
                (right == null
                        ? ""
                        : " " + right);
    }

    // returns the infix representation of the keys of the tree
    public String infixRepresentation(Node n)
    {
        if (n == null)
        {
            return null;
        }

        String left = prefixRepresentation(n.left);
        String right = prefixRepresentation(n.right);

        return
                (left == null
                        ? ""
                        : " " + left + " "
                ) + n.key +
                (right == null
                        ? ""
                        : " " + right);
    }

    // returns the postfix representation of the keys of the tree
    public String postfixRepresentation(Node n)
    {
        if (n == null)
        {
            return null;
        }

        String left = prefixRepresentation(n.left);
        String right = prefixRepresentation(n.right);

        return
                (left == null
                        ? ""
                        : " " + left) +
                (right == null
                        ? ""
                        : " " + right + " ")
                + n.key;
    }

    // test methods
    public static void main(String[] args)
    {
        // take the number of inputs from the user in the form of
        // "{string} {integer}", split them by whitespace and add them to
        // the symbol table as key and value, respectively.
        // lastly, print out the table
        BinarySearchTree<String, Integer> bst =
                new BinarySearchTree<>();

        Scanner in = new Scanner(System.in);
        // the '\n' character is not cleared from the buffer by nextInt(),
        // this clears it
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
            bst.put(str, integer);
        }

        // print out the hash table
        System.out.println(bst);

        // prefix
        System.out.println("Prefix notation: "
                + bst.prefixRepresentation(bst.root));

        // infix
        System.out.println("Infix notation: "
                + bst.infixRepresentation(bst.root));

        // postfix
        System.out.println("Postfix notation: "
                + bst.postfixRepresentation(bst.root));
    }
}
