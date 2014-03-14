import java.util.Scanner;

/**
 * Created by Dad on 3/9/14.
 */
abstract class RAMLToken {

    abstract String vaccuumRAMLFIle (Scanner example);

    abstract String formatRAMLasHTML ( RAMLToken toFormat);

    abstract void spewRAMLFile (String toSave);

    public abstract String stringMe();

    public static int lineSpaces(String line) {
        int i = 0;
        int spaces = 0;
        while (line.charAt(i) == ' ') {
            spaces ++;
            i++;
        }
        return spaces;
    }

    public static String getFirstWord(String line) {
        if (line.equals("")) {
            return "";
        } else {
            int lineSpacesCount = lineSpaces(line);
            int endFirstWord = lineSpacesCount;
            while ((endFirstWord < line.length()) && (line.charAt(endFirstWord) != ' ')) {
                endFirstWord++;
            }
            line = line.substring(lineSpacesCount,endFirstWord);
           return line;
        }
    }
 // todo: 1) read in a file and create and populate a single RAMLToken object of any type whatsoever
 // todo: 2) create the main class that reads in an array of RAMLTokens of different types.
}

