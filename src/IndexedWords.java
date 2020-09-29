/*
    Author: Andreas Hammarstrand
    Written: 2020/09/23
    Updated: 2020/09/28
    Purpose:
        IndexWords indexes all the words in a file and lets the user ask for
        the index of a certain word.
    Usage:
        Run the main function with the path to the file to be indexed as the
        first argument. The file may only contain alphabetical, blank, and/or
        newline characters.
        The user may then write a word to find the character indices of, in any
        letter casing.
        Requires `HashTable` and `TextUtility` to work.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class IndexedWords
{
    public static void main(String[] args) throws FileNotFoundException
    {
        // index the file before taking input
        File theTextFile = new File(args[0]);
        Scanner theTextScanner = new Scanner(theTextFile);

        // index file
        System.out.print("Indexing...");

        HashTable<String, List<Long>> indexes =
                TextUtility.createIndexTable(theTextScanner);

        System.out.println(" Done");

        // take input
        Scanner in = new Scanner(System.in);

        while (true)
        {
            System.out.print("\nPlease enter a word to find the index of: ");
            String input = in.nextLine();

            // empty string, means exit
            if (input.length() == 0)
            {
                break;
            }

            // using all words as lowercase so all instances are recorded
            // and looked at
            String lowercaseWord = input.toLowerCase();

            // word existed, print the indexes
            if (indexes.contains(lowercaseWord))
            {
                System.out.printf(
                        "@ %s\n",
                        indexes.get(lowercaseWord)
                );
            }
            // word did NOT exist, tell user about it
            else
            {
                System.out.println("That word is not contained within the text");
            }
        }
    }
}
