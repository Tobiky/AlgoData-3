import com.sun.javafx.tk.TKClipboard;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class MostCommonWords
{
    private static final int HASHTABLE_SIZE = 512;


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

    private static Integer[] orderFrequencies(
            HashTable<Integer, List<String>> frequenciesToWords)
    {
        // allocates 3 too many spaces
        Integer[] orderedFrequencies = new Integer[frequenciesToWords.size() - 3];

        int index = 0;
        for (int frequency : frequenciesToWords)
        {
            orderedFrequencies[index++] = frequency;
        }

        Arrays.sort(orderedFrequencies, Collections.reverseOrder());

        return orderedFrequencies;
    }

    public static void main(String[] args) throws IOException
    {
        // index the file before taking input
        File theTextFile = new File(args[0]);
        Scanner theTextScanner = new Scanner(theTextFile);

        System.out.print("Indexing...");

        long start = System.nanoTime();

        HashTable<String, List<Long>> indices =
                TextUtility.createIndexTable(theTextScanner);

        System.out.println(" Done");
        System.out.print("Ordering...");

        HashTable<Integer, List<String>> frequencyToWords =
                mostCommonWords(indices);

        Integer[] orderedFrequencies =
                orderFrequencies(frequencyToWords);

        long time = System.nanoTime() - start;

        System.out.println(" Done");


        System.out.printf("Operations took %.4f seconds\n", time / 1e9);

        Scanner in = new Scanner(System.in);


        while (true)
        {
            System.out.println("Please enter a number k for the kth most " +
                    "common word and k+n for kth to the k+nth most common" +
                    "element");
            String input = in.nextLine();

            if (input.isEmpty())
            {
                break;
            }

            if (input.contains("+"))
            {
                String[] values = input.split("\\+");
                int index  = Integer.parseInt(values[0]) - 1;
                int length = Integer.parseInt(values[1]);

                if (index < 0 || index >= orderedFrequencies.length)
                {
                    System.out.println("Input index is too small or too " +
                            "large, please try again");
                }
                else
                {
                    // index is 0-based, add 1 so its 1-based; the same as
                    // length and orderedFrequencies.length
                    if (index + 1 + length > orderedFrequencies.length)
                    {
                        length = orderedFrequencies.length - (index + 1);
                        System.out.printf(
                                "Too long, shortened length to %d\n",
                                length);
                    }


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
            }
            else
            {
                int index = Integer.parseInt(input) - 1;

                if (index < 0 || index >= orderedFrequencies.length)
                {
                    System.out.println("Input index is too small or too " +
                            "large, please try again");
                }
                else
                {
                    List<String> words = frequencyToWords.get(
                            orderedFrequencies[index]);

                    System.out.printf(
                            "The %dth most common words are %s\n",
                            index + 1,
                            words);

                }
            }

            System.out.println();
        }
    }
}
