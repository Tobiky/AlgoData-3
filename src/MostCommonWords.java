import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class MostCommonWords
{
    private static

    public static void main(String[] args) throws IOException
    {
        // index the file before taking input
        File theTextFile = new File(args[0]);
        Scanner theTextScanner = new Scanner(theTextFile);

        System.out.print("Indexing...");

        HashTable<String, List<Long>> indexes =
                TextUtility.createIndexTable(theTextScanner);

        System.out.println(" Done");


    }
}
