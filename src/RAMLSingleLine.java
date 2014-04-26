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
    String vaccuumRAMLFIle(RAMLScanner example, String currentLine) {

        Scanner s = new Scanner(currentLine);
        s.next();                        // scan past the first token, which should be the token describing the kind of single line element:

        lineOfRamlText dataAboutTheLine = new lineOfRamlText(currentLine);

        this.theRealContent = s.nextLine();
        this.indentSpaces = dataAboutTheLine.getLeadingSpaces();
        return example.getNextNonNullString(false);    // advance the file scanner and return a line.

    }

    @Override
    String formatRAMLasHTML( Boolean removeTokenName) {

        String toReturn = "";

        if (!removeTokenName) {
            toReturn = "<span class = \"" + this.tokenType.CSSClass() + "\" style = \"font-weight: bold;\" >" + markupType.markupTypeToString(this.tokenType) + "</span>" ;
        }
        toReturn +=  "<span class = \"" + this.tokenType.CSSClass()  // this.tokenType.CSSClass()
                 + "\" >" + this.theRealContent + "</span> <br>";

        return toReturn;
    }

    @Override
    void spewRAMLFile(String toSave) {

    }

    @Override
    markupType getMarkupType() {
        return this.tokenType;
    }

    @Override
    public String stringMe() {
        return getThisManySpaces(this.indentSpaces) + markupType.markupTypeToString(this.tokenType) + " " + this.theRealContent;
    }

    @Override
    public String toString () {
        return this.theRealContent;
    }
}
