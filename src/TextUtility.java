/*
    Author: Andreas Hammarstrand
    Written: 2020/09/18
    Updated: 2020/09/28
    Purpose:
        TextUtility provides utility functions for texts. These functions
        are; filtering a text from non-alphabetical, non-newline, and non-blank
        characters.
    Usage:
        The functions inside this class can be used by importing the class
        or text can be filtered by running the main method.
        Requires `HashTable` to work.
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextUtility
{
    private static final int HASHTABLE_SIZE = 1024;

    // filters the text from all characters that aren't newline, alphabetical
    // or blank.
    // function filters by replacing unwanted characters with blank.
    public static void filterText(InputStream text, PrintStream output) throws IOException
    {
        // loop through all the lines of text and filter them out to output.
        Scanner input = new Scanner(text);

        // loop while there are more data to go through
        while (text.available() > 0)
        {
            // get the next available line of text, iterate through it, and
            // output the filtered version.
            String line = input.nextLine();
            for (int i = 0; i < line.length(); i++)
            {
                // print currently indexed character if it's an alphabetical
                // character, blank, or new line.
                // otherwise print a blank.

                char currentCharacter = line.charAt(i);

                boolean charIsAllowed =
                        Character.isAlphabetic(currentCharacter) ||
                                currentCharacter == ' '          ||
                                currentCharacter == '\n';

                if (charIsAllowed)
                {
                    output.print(currentCharacter);
                }
                else
                {
                    output.print(' ');
                }
            }

            // Scanner.nextLine() removes the end line separator, add it again
            // here
            output.println();
        }
    }

    // get all words contained in the line of text
    public static List<String> getWords(String line)
    {
        List<String> words = new ArrayList<>();

        // add all words from the current line into the list of words
        for (int i = 0; i < line.length(); i++)
        {
            // skip all spaces
            if (line.charAt(i) == ' ')
            {
                continue;
            }

            // save the starting index of the word, then iterate till a space
            // is found or end is found
            int startIndex = i;
            for (; i < line.length() &&
                        line.charAt(i) != ' ';
                   i++);

            // add word to the list of words
            words.add(line.substring(startIndex, i));

            // i will increment and thus skip the space that was found in the
            // inner for loop
        }

        return words;
    }

    // get all words or up to `max` amount of words in `in`
    public static List<String> getWords(int max, Scanner in)
    {
        List<String> words = new ArrayList<>(max);

        // add all words in scanner or up to `max` words into the word list
        while (words.size() < max && in.hasNextLine())
        {
            // get the next line, take out all the words, and add them to
            // the total list
            String currentLine = in.nextLine();

            List<String> lineWords = getWords(currentLine);

            words.addAll(lineWords);
        }

        return words;
    }

    // creates a hash table containing the indices of each word from the
    // scanner
    public static HashTable<String, List<Long>> createIndexTable(
            Scanner in)
    {
        HashTable<String, List<Long>> indexes =
                new HashTable<>(HASHTABLE_SIZE);

        // count is the character index (1-based index) while the input
        // has a next line
        for (long count = 1; in.hasNextLine();)
        {
            // go through each line of text and select everything in between
            // spaces
            String line = in.nextLine();
            for (int i = 0; i < line.length(); i++, count++)
            {
                // skip all spaces
                if (line.charAt(i) == ' ')
                {
                    continue;
                }

                // save the starting index of the word, then iterate till a space
                // is found or end is found.
                // also increment `count` for each character.
                int startIndex = i;
                for (; i < line.length() &&
                        line.charAt(i) == ' ';
                     i++, count++);

                // make it lowercase so it's consistent across all instances
                // of the word
                String word = line
                        .substring(startIndex, i)
                        .toLowerCase();

                // add the word to the table
                if (indexes.contains(word))
                {
                    indexes
                            .get(word)
                            .add(count);
                }
                else
                {
                    List<Long> wordIndexes = new ArrayList<>();
                    wordIndexes.add(count);

                    indexes.put(
                            word,
                            wordIndexes);
                }

                // i will increment and thus skip the space that was found
                // in the inner for loop
            }
        }

        return indexes;
    }

    // works as test method and a way to filter an inputted text files
    public static void main(String[] args) throws IOException
    {
        // filter the input to the program
        TextUtility.filterText(System.in, System.out);
    }
}
