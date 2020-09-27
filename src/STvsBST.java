/*
    Author: Andreas Hammarstrand
    Written: 2020/09/22
    Updated: 2020/09/27
    Purpose:
        This file attempts at comparing the time performance between a symbol
        table using binary search and a binary search tree.
    Usage:
        Run main function with the number of hundreds of words (n * 100) to be
        used to test the datastructures based on the input. The input should
        only be a file containing only alphabetical, blank, and/or newline
        characters.
 */


import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.List;
import java.util.Scanner;

public class STvsBST
{
    // returns the time it took for a symbol table to add all the words from
    // the list of words
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

    // returns the time it took for a binary search tree to add all the words
    // from the list of words
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

    // the amount of times to repeat the test
    private static final int TEST_COUNT = 10_000;

    // tests a bst vs a st on n hundred words from the standard input
    private static void testSearches(int n) throws FileNotFoundException
    {
        // setting upp a list that will contain all n * 100 words
        int wordCount = n * 100;

        // read input
        Scanner the_text = new Scanner(System.in);

        // sort out all words
        List<String> words = TextUtility.getWords(wordCount, the_text);

        // using big integers because the time is in nanoseconds
        BigInteger stMean = BigInteger.ZERO, bstMean = BigInteger.ZERO;

        // adds all times from all tests into one variable
        for (int count = 0; count < TEST_COUNT; count++)
        {
            stMean = stMean.add(BigInteger.valueOf(stTime(words)));
            bstMean = bstMean.add(BigInteger.valueOf(bstTime(words)));
        }

        // divide by the amount of repetitions to get the mean
        stMean = stMean.divide(BigInteger.valueOf(TEST_COUNT));
        bstMean = bstMean.divide(BigInteger.valueOf(TEST_COUNT));

        // output results
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
        // parses the number of hundreds of words to test for
        int n = Integer.parseInt(args[0]);
        testSearches(n);
    }
}
