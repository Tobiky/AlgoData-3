import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class EvenHashDistributions
{
    public static void main(String[] args) throws IOException
    {
        Scanner in = new Scanner(System.in);

        BinarySearchTree<Integer, Integer> bst
                = new BinarySearchTree<>();

        // read all lines for words and put them into the BST
        while (in.hasNextLine())
        {
            for (String word : TextUtility.getWords(in.nextLine()))
            {
                // if the word is in the tree, get the value and increment it
                // otherwise put the word in as key with value as 1
                int hash = word.hashCode();
                if (bst.contains(hash))
                {
                    bst.put(hash, bst.get(hash) + 1);
                }
                else
                {
                    bst.put(hash, 1);
                }
            }
        }

        // print out the BST to show the distribution
        System.out.println(bst);
    }
}
