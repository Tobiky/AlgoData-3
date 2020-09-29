/*
    Author: Andreas Hammarstrand
    Written: 2020/09/22
    Updated: 2020/09/28
    Purpose:
        This file attempts at comparing the time performance between a symbol
        table using binary search and a binary search tree.
    Usage:
        Run main function with the number of hundreds of words (n * 100) to be
        used to test the datastructures based on the input. The input should
        only be a file containing only alphabetical, blank, and/or newline
        characters.
        Requires `AssociativeArraySymbolTable`, `BinarySearchTree` and
        `TextUtility` to work.
    Data:
        https://docs.google.com/spreadsheets/d/1IVKtUlZCghXclivxsqV2xyqhKgjjNRABMaP33QZkUmM/edit?usp=sharing
 */


import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.List;
import java.util.Scanner;

public class STvsBST
{
    // returns the time it took for a symbol table to add all the words from
    // the list of words
    private static long stTime(List<String> words, int max)
    {
        AssociativeArraySymbolTable<String, Integer> st =
                new AssociativeArraySymbolTable<>();

        long start = System.nanoTime();

        // make sure that only `max` words are used, otherwise just iterate
        // and add the elements
        int count = 0;

        for (String word : words)
        {
            if (count >= max)
            {
                break;
            }

            if (st.contains(word))
            {
                st.put(word, st.get(word) + 1);
            }
            else
            {
                st.put(word, 1);
            }

            count++;
        }

        return System.nanoTime() - start;
    }

    // returns the time it took for a binary search tree to add all the words
    // from the list of words
    private static long bstTime(List<String> words, int max)
    {
        BinarySearchTree<String, Integer> bst =
                new BinarySearchTree<>();

        long start = System.nanoTime();

        // make sure that only `max` words are used, otherwise just iterate
        // and add the elements
        int count = 0;

        for (String word : words)
        {
            if (count >= max)
            {
                break;
            }

            if (bst.contains(word))
            {
                bst.put(word, bst.get(word) + 1);
            }
            else
            {
                bst.put(word, 1);
            }

            count++;
        }

        return System.nanoTime() - start;
    }

    // the amount of times to repeat the test
    private static final int TEST_REPETITION = 10_000;
    private static final int TEST_COUNT_INCREMENT = 10;

    // tests a bst vs a st on n hundred words from the standard input
    private static void testSearches(int n) throws FileNotFoundException
    {
        // setting upp a list that will contain all n * 100 words
        int wordCount = n * 100;

        // read input
        Scanner the_text = new Scanner(System.in);

        // sort out all words
        List<String> words = TextUtility.getWords(wordCount, the_text);

        System.out.println("Binary Search Symbol Table, Binary Search Tree, Word Count");
        for (int count = 10; count <= wordCount; count += 10)
        {
            // using big integers because the time is in nanoseconds
            BigInteger stMean = BigInteger.ZERO, bstMean = BigInteger.ZERO;

            // adds all times from all tests into one variable
            for (int repetition = 0; repetition < TEST_REPETITION; repetition++)
            {
                stMean = stMean.add(BigInteger.valueOf(stTime(words, count)));
                bstMean = bstMean.add(BigInteger.valueOf(bstTime(words, count)));
            }

            // divide by the amount of repetitions to get the mean
            stMean = stMean.divide(BigInteger.valueOf(TEST_REPETITION));
            bstMean = bstMean.divide(BigInteger.valueOf(TEST_REPETITION));

            // output results
            System.out.printf("%d, %d, %d\n", stMean, bstMean, count);
        }
    }

    public static void main(String[] args) throws FileNotFoundException
    {
        // parses the number of hundreds of words to test for
        int n = Integer.parseInt(args[0]);
        testSearches(n);
    }
}
