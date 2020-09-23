import sun.awt.Symbol;
import sun.text.normalizer.Utility;

import javax.rmi.CORBA.Util;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IndexedWords
{

    public static void main(String[] args) throws FileNotFoundException
    {
        // index the file before taking input
        File theTextFile = new File(args[0]);
        Scanner theTextScanner = new Scanner(theTextFile);

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
