import java.io.FileReader;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.util.Scanner;

public class TextUtility
{
    private InputStream text;

    public TextUtility(InputStream text)
    {
        this.text = text;
    }

    public void filterText(PrintStream output)
    {
        // loop through all the lines of text and filter them out to output.
        Scanner input = new Scanner(text);
        while (input.hasNextLine())
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


    public static void main(String[] args)
    {
        TextUtility tu = new TextUtility(System.in);
        tu.filterText(System.out);
    }
}
