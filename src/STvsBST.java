import java.io.File;
import java.io.FileNotFoundException;
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


    private static List<String> getWords(int max, Scanner in)
    {
        List<String> words = new ArrayList<String>(max);

        // add all words in scanner or up to `max` words into the word list
        while (in.hasNextLine() && words.size() < max)
        {
            String currentLine = in.nextLine();

            // add all words from the current line into the list of words
            for (int i = 0; i < currentLine.length() && words.size() < max; i++)
            {
                // skip all spaces
                if (currentLine.charAt(i) == ' ')
                {
                    continue;
                }

                int startIndex = i;
                // iterate till a space is found or end is found
                for (; currentLine.charAt(i) != ' '
                        && i < currentLine.length();
                     i++);

                // add word to the list of words
                words.add(currentLine.substring(startIndex, i));

                // i will increment and thus skip the space that was found
                // in the inner for loop
            }
        }

        return words;
    }

    private static final int TEST_COUNT = 1000;

    private static void testSearches(int n, String fileName) throws FileNotFoundException
    {
        // setting upp a list that will contain all n * 100 words
        int wordCount = n * 100;

        // reading input file
        File file = new File(fileName);
        Scanner the_text = new Scanner(file);

        // sort out all words
        List<String> words = getWords(wordCount, the_text);

        long stMean = 0, bstMean = 0;
        for (int count = 0; count < TEST_COUNT; count++)
        {
            stMean += stTime(words);
            bstMean += bstTime(words);
        }
        stMean /= TEST_COUNT;
        bstMean /= TEST_COUNT;

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
        String fileName = args[0];
        int n = Integer.parseInt(args[1]);

        testSearches(n, fileName);
    }
}
