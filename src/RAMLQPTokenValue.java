import java.util.Scanner;

/**
 * Created by smckinnon on 4/18/14.
 */

public class RAMLQPTokenValue extends RAMLToken {

    // This class is the very simple unformatted content that is contained under a QueryParameter Token
    // This is very similar to a SingleLine, except that the markuptypes do not have their own formatting type.


    boolean pipedContent;
    markupType tokenType;
    String theRealContent;
    int indentSpaces;

    RAMLQPTokenValue(markupType mt, int is) {
        this.pipedContent = false;
        this.tokenType = mt;
        this.indentSpaces = is;
        this.theRealContent = "";
    }

    String vaccuumRAMLFIle(RAMLScanner example, String currentLine) {

        String token;

        lineOfRamlText importantInformation = new lineOfRamlText(currentLine);
        this.indentSpaces = importantInformation.getLeadingSpaces();
        token = importantInformation.getRestOfTheLine().trim();

        String descriptionFromFile = "";

        if (token.equals("")) {
            // this.theRealContent = "";
            // this.pipedContent = false;
            // currentLine = getNextNonNullString(example, false);
            // assume if we are in multi-line and missing both a token and a pipe
            // that we must need a pipe.
            System.out.println("Warning: missing a pipe in Query Parameter description: at line "+ example.getLine());
            token = "|";
        }
        if (token.charAt(0) != '|') {
            this.theRealContent = importantInformation.getRestOfTheLine().trim();
            this.pipedContent = false;
            currentLine = example.getNextNonNullString(false);
        } else {
            // step forward and start getting lines

            currentLine = example.getNextNonNullString(true);
            importantInformation = new lineOfRamlText(currentLine);

            while (((importantInformation.getLeadingSpaces() > this.indentSpaces)
                    || (importantInformation.getFirstToken().equals("")))
                    && example.getScanner().hasNextLine()) {

                if (importantInformation.getFirstToken().equals("")) {
                    descriptionFromFile += "\n";
                } else {
                    descriptionFromFile += currentLine.substring(this.indentSpaces, currentLine.length()) + "\n";
                }

                currentLine = example.getNextNonNullString(true);
                importantInformation = new lineOfRamlText(currentLine);

            }
            this.theRealContent = descriptionFromFile; // strip the final \n
            this.pipedContent = true;

        }
        while (currentLine.trim().equals("") && example.getScanner().hasNextLine()) {
            currentLine = example.getNextNonNullString(false);
        }
        return currentLine;

    }

    @Override
    String formatRAMLasHTML( Boolean removeTokenName) {

        String toReturn = "";

        if (!removeTokenName) {
            toReturn = "<span class = \"" + markupType.qpDefault.CSSClass() + "\" style = \"font-weight: bold;\" >" + markupType.markupTypeToString(this.tokenType) + "</span>" ;
        }
        toReturn +=  "<span class = \"" + markupType.qpDefault  // this.tokenType.CSSClass()
                + "\" > " + this.theRealContent + "</span> <br>";

        return toReturn;
    }

    @Override
    void spewRAMLFile(String toSave) {
    }

    @Override
    markupType getMarkupType() {
        return this.tokenType;
    }

    public String stringMe() {
        String tString;
        if (!this.pipedContent) {
            tString = getThisManySpaces(this.indentSpaces) +
                    markupType.markupTypeToString(this.tokenType) + " "+ theRealContent;
        } else {
            tString = getThisManySpaces(this.indentSpaces);
            tString += markupType.markupTypeToString(this.tokenType) + " | \n";
            tString += spaceOut(theRealContent, this.indentSpaces);
            // todo there is still an issue with stuff at the end of theRealContent causing problems - fix this next.
        }

        return tString;
    }
}

