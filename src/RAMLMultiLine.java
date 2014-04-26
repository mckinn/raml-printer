import java.util.Scanner;

/**
 * Created by Dad on 3/9/14.
 */
public class RAMLMultiLine extends RAMLToken {

    boolean pipedContent;
    markupType tokenType;
    String theRealContent;
    int indentSpaces;

    RAMLMultiLine(markupType mt, int is) {
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
            System.out.println("Warning - missing a pipe in " + this.tokenType + ": near line:"+ example.getLine() );
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
            // we are trying to return an empty string, which will be confusing,
            // gotta fix this.
            currentLine = example.getNextNonNullString(false);
        }
        return currentLine;

    }

    @Override
    String formatRAMLasHTML(Boolean removeTokenName) {
        String result = "";
        String nameToken = (removeTokenName ? "" : "<b>" + (markupType.markupTypeToString(tokenType)) + "</b>\n");

        switch(this.tokenType) {
            case description:
                result = "\n<span class = " + this.tokenType.CSSClass() + ">\n" + nameToken +
                        this.theRealContent + "\n" +
                        "</span>\n";
                break;
            case example:
                result = "\n<span class = \" bold\">" + nameToken + "</span>" +
                        "\n<pre class = \"" + markupType.example.CSSClass() + "\">\n" +
                        (this.theRealContent.replace("<", "&lt;")).replace(">", "&gt;") + "\n" +
                        "</pre>";
                break;
            case schema:
                result = "\n<span class = \" bold\">" + nameToken + "</span>" +
                        "\n<pre class = \"" + markupType.schema.CSSClass() + "\">\n"  +
                        (this.theRealContent.replace("<", "&lt;")).replace(">", "&gt;") + "\n" +
                        "</pre>";
                break;
            default:
                result = "\n<span class = \"" + this.tokenType.CSSClass() + "\">\n"  + nameToken +
                        this.theRealContent +
                        "\n</span>\n";
                break;
        }
        return result;
    }

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
