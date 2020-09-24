import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextUtility
{
    private static final int HASHTABLE_SIZE = 1024;

    private InputStream text;

    public TextUtility(InputStream text)
    {
        this.text = text;
    }

    public void filterText(PrintStream output) throws IOException
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

            // Scanner.nextLine() removes the end line separator, add it here
            output.println();
        }
    }

    public long symbolTableTime()
    {
        return 0L;
    }

    public static List<String> getWords(String line)
    {
        List<String> words = new ArrayList<String>();

        // add all words from the current line into the list of words
        for (int i = 0; i < line.length(); i++)
        {
            // skip all spaces
            if (line.charAt(i) == ' ')
            {
                continue;
            }

            int startIndex = i;
            // iterate till a space is found or end is found
            for (; i < line.length() &&
                        line.charAt(i) != ' ';
                   i++);

            // add word to the list of words
            words.add(line.substring(startIndex, i));

            // i will increment and thus skip the space that was found
            // in the inner for loop
        }

        return words;
    }

    public static List<String> getWords(int max, Scanner in)
    {
        if (max < 0)
        {
            max = Integer.MAX_VALUE;
        }

        List<String> words = new ArrayList<String>(max);

        // add all words in scanner or up to `max` words into the word list
        while (words.size() < max && words.size() < max)
        {
            String currentLine = in.nextLine();

            List<String> lineWords = getWords(currentLine);

            words.addAll(lineWords);
        }

        return words;
    }

    public static HashTable<String, List<Long>> createIndexTable(
            Scanner in)
    {
        HashTable<String, List<Long>> indexes =
                new HashTable<>(HASHTABLE_SIZE);

        // count is the character index (1-based index)
        for (long count = 1; in.hasNextLine();)
        {
            String line = in.nextLine();
            for (int i = 0; i < line.length(); i++, count++)
            {
                // skip all spaces
                if (line.charAt(i) == ' ')
                {
                    continue;
                }

                int startIndex = i;
                // iterate till a space is found or end is found
                for (; i < line.length() &&
                        line.charAt(i) != ' ';
                     i++, count++);

                // make it lowercase so its consistent across all instances
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

    public static void main(String[] args) throws IOException
    {
        TextUtility tu = new TextUtility(System.in);
        tu.filterText(System.out);
    }
}
