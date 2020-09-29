/*
    Author: Andreas Hammarstrand
    Written: 2020/09/23
    Updated: 2020/09/28
    Purpose:
        MostCommonWords takes in a file, indexes the word inside it, process
        that information to let the user ask for which are the k:th most common
        word or k+n:th most common word_s_.
    Usage:
        Run the main method with the file mentioned above as the first
        argument. The file may only contain alphabetical, newline, and/or
        blank characters.
        Requires `HashTable` and `TextUtility` to work.
 */

import java.io.File;
import java.io.IOException;
import java.util.*;

public class MostCommonWords
{
    private static final int HASHTABLE_SIZE = 512;

    // returns hash table with frequency of words associated with said words
    // from a hash table containing the indices for each word
    private static HashTable<Integer, List<String>> mostCommonWords(
            HashTable<String, List<Long>> indices)
    {
        HashTable<Integer, List<String>> frequencyToWords =
                new HashTable<>(HASHTABLE_SIZE);

        for (String word : indices)
        {
            // the frequency is the amount of indices that the word is found
            // on
            int frequency = indices
                    .get(word)
                    .size();

            // if the frequency already exists in the list, add the word
            // to the list of words for that frequency
            if (frequencyToWords.contains(frequency))
            {
                frequencyToWords
                        .get(frequency)
                        .add(word);
            }
            // otherwise create a new list with that word in it and put
            // that list into the table of frequencies together with
            // the frequency
            else
            {
                List<String> wordList =
                        new ArrayList<>();

                wordList.add(word);

                frequencyToWords.put(frequency, wordList);
            }
        }

        return frequencyToWords;
    }

    // returns an integer array containing all the frequencies, sorted in
    // descending order
    private static Integer[] orderFrequencies(
            HashTable<Integer, List<String>> frequenciesToWords)
    {
        // the hash table increases its size 3 times for nothing, adjust
        // for that fault here
        Integer[] orderedFrequencies = new Integer[frequenciesToWords.size() - 3];

        // go through all the frequencies and put them into an array
        int index = 0;
        for (int frequency : frequenciesToWords)
        {
            orderedFrequencies[index++] = frequency;
        }

        // sort said array
        Arrays.sort(orderedFrequencies, Collections.reverseOrder());

        return orderedFrequencies;
    }

    public static void main(String[] args) throws IOException
    {
        // index the file before taking input
        File theTextFile = new File(args[0]);
        Scanner theTextScanner = new Scanner(theTextFile);

        // index words in file
        System.out.print("Indexing...");

        // take time of the operation
        long start = System.nanoTime();

        HashTable<String, List<Long>> indices =
                TextUtility.createIndexTable(theTextScanner);

        System.out.println(" Done");
        System.out.print("Ordering...");

        // reverse the index table to a frequency (of each word) table
        HashTable<Integer, List<String>> frequencyToWords =
                mostCommonWords(indices);

        // collect all frequencies into an array and sort it in descending
        // order
        Integer[] orderedFrequencies =
                orderFrequencies(frequencyToWords);

        // get the total time of the operation
        long time = System.nanoTime() - start;

        System.out.println(" Done");


        // print out the operation time in seconds, 5th decimal cut off
        System.out.printf("Operations took %.4f seconds\n", time / 1e9);

        // take input from user
        Scanner in = new Scanner(System.in);

        while (true)
        {
            System.out.println("Please enter a number k for the kth most " +
                    "common word and k+n for kth to the k+nth most common" +
                    "element");
            String input = in.nextLine();

            // see just newline as message to exit the program
            if (input.isEmpty())
            {
                break;
            }

            // if the input contains a '+' it means that there will be two
            // numbers (separated by said '+'), one for the starting 1-based
            // index and the other for the overreaching length.

            // otherwise there is just one number and that is the 1-based index
            // of the most common words
            if (input.contains("+"))
            {
                // split the input by the '+' and parse them as numbers
                String[] values = input.split("\\+");

                // make 1-based index to 0-based index
                int index  = Integer.parseInt(values[0]) - 1;
                int length = Integer.parseInt(values[1]);

                // if the index is less than the start index of the array
                // or larger than the end index of the array, tell the user
                // that the index is too small or long
                if (index < 0 || index >= orderedFrequencies.length)
                {
                    System.out.println("Input index is too small or too " +
                            "large, please try again");
                    // using continue to not have huge else statement
                    continue;
                }
                // else

                    // index is 0-based, add 1 so its 1-based; the same as
                    // length and orderedFrequencies.length
                    if (index + 1 + length > orderedFrequencies.length)
                    {
                        length = orderedFrequencies.length - (index + 1);
                        System.out.printf(
                                "Too long, shortened length to %d\n",
                                length);
                    }


                    // print out the most common words selected by the user
                    System.out.printf(
                            "The most common %dth to %dth words are:\n",
                            index + 1,
                            index + length + 1);

                    for (int i = index; i < index + length; i++)
                    {
                        List<String> words = frequencyToWords.get(
                                orderedFrequencies[i]);

                        System.out.printf(
                                "%d. %s\n",
                                i - index + 1,
                                words);
                    }
            }
            else
            {
                // make inputted 1-based index from user into 0-bases index
                int index = Integer.parseInt(input) - 1;

                // if the index is less than the start index of the array
                // or larger than the end index of the array, tell the user
                // that the index is too small or long
                if (index < 0 || index >= orderedFrequencies.length)
                {
                    System.out.println("Input index is too small or too " +
                            "large, please try again");
                }
                else
                {
                    // print the most common word as indexed by the user
                    List<String> words = frequencyToWords.get(
                            orderedFrequencies[index]);

                    System.out.printf(
                            "The %dth most common words are %s\n",
                            index + 1,
                            words);

                }
            }

            // print a new line to clear some space
            System.out.println();
        }
    }
}
