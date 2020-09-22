public class BinarySearchTree<TKey extends Comparable<TKey>, TValue>
{
    private class Node
    {
        public TKey key;
        public TValue value;
        public Node left;
        public Node right;
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
}
