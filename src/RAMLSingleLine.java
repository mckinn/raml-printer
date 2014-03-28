import java.util.Scanner;

/**
 * Created by smckinnon on 3/19/14.
 */
public class RAMLSingleLine extends RAMLToken {

    // This class covers all of the cases where the format is of the form
    // "token: <the rest of the line>"

    markupType tokenType;
    String theRealContent;
    int indentSpaces;

    public RAMLSingleLine(markupType mt) {
        this.tokenType = mt;
        this.theRealContent = "";
        this.indentSpaces = 0;
    }

    @Override
    String vaccuumRAMLFIle(Scanner example, String currentLine) {

        Scanner s = new Scanner(currentLine);
        s.next();                        // scan past the first token, which should be description:

        lineOfRamlText dataAboutTheLine = new lineOfRamlText(currentLine);

        this.theRealContent = s.nextLine();
        this.indentSpaces = dataAboutTheLine.getLeadingSpaces();
        return getNextNonNullString(example, false);    // advance the file scanner and return a line.

    }

    @Override
    String formatRAMLasHTML(RAMLToken toFormat) {
        return null;
    }

    @Override
    void spewRAMLFile(String toSave) {

    }

    @Override
    public String stringMe() {
        return getThisManySpaces(this.indentSpaces) + markupType.markupTypeToString(this.tokenType) + ": " + this.theRealContent;
    }
}
