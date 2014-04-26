import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Dad on 3/9/14.
 */

/*  todo - move common stuff into the superclass
    todo - escape the < and > in the example and schema classes.
   the general pattern is...
   token: {t2}  {rest-of-line}
       {body}
       {sub-elements}*

   we could provide bound in recognizers for...
   - token(s)
   - valid sub-elements
   - intro strings

   and then we could move all elements to the super-class implementation of vaccuumRAML

   If we had a format-main-content activity then we could also generalize the String-me

   Conversion to HTML may involve rules for sequencing of the subtending content
   that means that the sub-class will have to fully implement its own HTML conversion.

 */
abstract class RAMLToken {

    abstract String vaccuumRAMLFIle (RAMLScanner example, String currentLine);

    abstract String formatRAMLasHTML(
            // String cssReformatClass,
            Boolean removeTokenName);
    // removeTokenName = true --> remove the name of the RAML Token, false, means print the name of the token

    abstract void spewRAMLFile (String toSave);

    abstract markupType getMarkupType();

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
            line = line.substring(lineSpacesCount, endFirstWord);
            return line;
        }
    }

    /*public static String getNextNonNullStringx(Scanner example, Boolean preserveBlankLines) {
        // get the next line to process, eliminating blank lines and comments.
        if (example.hasNextLine()) {
            String returnValue = example.nextLine();
            lineOfRamlText thisLine = new lineOfRamlText(returnValue);

            while (((thisLine.getFirstToken().equals("") && !preserveBlankLines)
                    || thisLine.getFirstToken().equals("#"))         // # is returned if a comment is found
                    && example.hasNextLine()) {
                returnValue = example.nextLine();
                thisLine = new lineOfRamlText(returnValue);
            }
//            System.out.println("                                            " + returnValue);
            return returnValue;
        } else {
            System.out.println("*** The End ***");
            return "";
        }
    }*/

    public static String getThisManySpaces(int length) {
        if (length > 0) {
            char[] array = new char[length];
            Arrays.fill(array, ' ');
            return new String(array);
        }
        return "";
    }

    public static String spaceOut(String theRealContent, int indentSpaces) {
        // scan for '\n' and place spaces just after each one.
        // presume that we start with spaces
        int position = 0;
        String outString = getThisManySpaces(indentSpaces);
        for (position = 0; position < theRealContent.length(); ++position) {
            if (theRealContent.charAt(position) != '\n') {
                outString += theRealContent.charAt(position);
            } else {
                if (position < theRealContent.length()-1) {
                    outString += '\n' + getThisManySpaces(indentSpaces);
                }
            }
        }
        return outString;
    }


     // done: 1) read in a file and create and populate a single RAMLToken object of any type whatsoever
     // done: 2) create the main class that reads in an array of RAMLTokens of different types.
     // todo: Could I generalize this even more by giving all non-leaf-nodes a list of the markupTypes that they understand, and making the parsing uniform ?  There might have to be a generic pre-processing step to allow the detection and formatting of the more diverse members of the universe.
     // todo: make the html generation create an aggregate title of all of the elements of the Resource Path.


}

