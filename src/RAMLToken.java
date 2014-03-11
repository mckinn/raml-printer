import java.util.Scanner;

/**
 * Created by Dad on 3/9/14.
 */
abstract class RAMLToken {

    abstract void vaccuumRAMLFIle (Scanner example);

    abstract String formatRAMLasHTML ( RAMLToken toFormat);

    abstract void spewRAMLFile (String toSave);

    public abstract String stringMe();

    public static int lineSpaces(String line, Scanner example) {
        int i = 0;
        int spaces = 0;
        while (line.charAt(i) == ' ') {
            spaces ++;
            i++;
        }
        return spaces;
    }
 // todo: 1) read in a file and create and populate a single RAMLToken object of any type whatsoever
 // todo: 2) create the main class that reads in an array of RAMLTokens of different types.
}

