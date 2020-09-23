import java.util.Scanner;

public class BinarySearchTree<TKey extends Comparable<TKey>, TValue>
{
    private class Node
    {
        public TKey key;
        public TValue value;
        public Node left;
        public Node right;

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

    @Override
    public String toString()
    {
        return "[" +
                (root == null
                        ? ""
                        : root) +
                "]";
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
        in.nextLine();

        System.out.println("Inputs:");
        for (int count = 0; count < amount; count++)
        {
            String line = in.nextLine();
            String[] values = line.split("\\s+");

            int integer = Integer.parseInt(values[1]);
            String str = values[0];

            bst.put(str, integer);
        }

        System.out.println(bst);
    }
}
