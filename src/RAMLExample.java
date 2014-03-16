import java.util.Scanner;

/**
 * Created by Dad on 3/9/14.
 */
public class RAMLExample extends RAMLToken {

    boolean pipedContent;
    String theRealExample;
    int indentSpaces;

    RAMLExample() {
        this.pipedContent = false;
        this.indentSpaces = 0;
        this.theRealExample = "";
    }

    String vaccuumRAMLFIle(Scanner example, String currentLine) {

        String token;
        int tabAmount;
        int baseLineSpaces;
        int lineSpacesCount;

        Scanner s = new Scanner(currentLine);
        s.next();                        // scan past the first token, which should be description:
        token = s.next();                // this token should be the pipe, or the start of the next row.

        lineOfRamlText dataAboutTheLine = new lineOfRamlText(currentLine);
        baseLineSpaces = dataAboutTheLine.getLeadingSpaces();


        String descriptionFromFile = "";
        if (!token.equals("|")) {
            currentLine = s.nextLine();                     // the text after the non-pipe.
            descriptionFromFile += token + currentLine;
            this.theRealExample = descriptionFromFile;
            this.pipedContent = false;
            return getNextNonNullString(example, false);    // advance the file scanner and return a line.
        } else {

            currentLine = getNextNonNullString(example, true);
            dataAboutTheLine = new lineOfRamlText(currentLine);
            lineSpacesCount = dataAboutTheLine.getLeadingSpaces();
            tabAmount = lineSpacesCount - baseLineSpaces;

            boolean quit = false;

            do {
                // If a line is blank include it, but no need of a sub-string operation
                if (dataAboutTheLine.getFirstToken().equals("")) {
                    descriptionFromFile += "\n";
                } else {
                    descriptionFromFile += currentLine.substring(baseLineSpaces, currentLine.length()) + "\n";
                }

                currentLine = getNextNonNullString(example, true);
                dataAboutTheLine = new lineOfRamlText(currentLine);
                lineSpacesCount = dataAboutTheLine.getLeadingSpaces();

            } while (example.hasNextLine() &&
                    ((lineSpacesCount > baseLineSpaces) || dataAboutTheLine.getFirstToken().equals(""))
                    );

            this.theRealExample = descriptionFromFile;
            this.pipedContent = true;

            return currentLine;
        }

        // return junk;
    }

    String formatRAMLasHTML(RAMLToken toFormat) {
        return "RAML Description as HTML";
    }

    void spewRAMLFile(String toSave) {
    }

    public String stringMe() {
        return "Example: " + "\n" + theRealExample;
    }
}
