import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class STvsBST
{
    private static long stTime(List<String> words)
    {
        AssociativeArraySymbolTable<String, Integer> st =
                new AssociativeArraySymbolTable<>();

        long start = System.nanoTime();

        for (String word : words)
        {
            if (st.contains(word))
            {
                st.put(word, st.get(word) + 1);
            }
            else
            {
                st.put(word, 1);
            }
        }

        return System.nanoTime() - start;
    }

    private static long bstTime(List<String> words)
    {
        BinarySearchTree<String, Integer> bst =
                new BinarySearchTree<>();

        long start = System.nanoTime();

        for (String word : words)
        {
            if (bst.contains(word))
            {
                bst.put(word, bst.get(word) + 1);
            }
            else
            {
                bst.put(word, 1);
            }
        }

        return System.nanoTime() - start;
    }




    private static final int TEST_COUNT = 10_000;

    private static void testSearches(int n) throws FileNotFoundException
    {
        // setting upp a list that will contain all n * 100 words
        int wordCount = n * 100;

        // read input
        Scanner the_text = new Scanner(System.in);

        // sort out all words
        List<String> words = TextUtility.getWords(wordCount, the_text);

        BigInteger stMean = BigInteger.ZERO, bstMean = BigInteger.ZERO;
        for (int count = 0; count < TEST_COUNT; count++)
        {
            stMean = stMean.add(BigInteger.valueOf(stTime(words)));
            bstMean = bstMean.add(BigInteger.valueOf(bstTime(words)));
        }
        stMean = stMean.divide(BigInteger.valueOf(TEST_COUNT));
        bstMean = bstMean.divide(BigInteger.valueOf(TEST_COUNT));

        System.out.println(
                String.format(
                    "Symbol Table (Ordered Associated Array): avg %d ns",
                    stMean
                )
        );

        System.out.println(
                String.format(
                        "Binary Search Tree: avg %d ns",
                        bstMean
                )
        );
    }

    public static void main(String[] args) throws FileNotFoundException
    {
        int n = Integer.parseInt(args[0]);

        testSearches(n);
    }
}
