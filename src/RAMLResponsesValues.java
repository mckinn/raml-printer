import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by smckinnon on 3/21/14.
 */
public class RAMLResponsesValues extends RAMLToken {

    // looking for patterns that conform to nnn: <Descriptive String>
    // typical payload is description and body

    String responseCode;    // the [0-9][0-9][0-9]:
    String responseDescription;
    int indentSpaces;
    RAMLTokenList subtendingThings;
    int numberOfThings;

    public RAMLResponsesValues(int indentSpaces, String rc) {
        this.indentSpaces = indentSpaces;
        this.responseCode = rc;
        this.responseDescription = null;
        this.numberOfThings = 0;
        this.subtendingThings = new RAMLTokenList();
    }

    @Override
    String vaccuumRAMLFIle(RAMLScanner example, String currentLine) {

        RAMLToken ramlEntity;
        Boolean successful;
        // String token;

        // extract the indent level from current line.
        // CurrentLine should contain the body: tag
        // all that is allowed are descriptions: and application/xml tags.
        // the pattern is similar to QueryParameters.

        if (this.isEmpty()) this.responseCode = checkResponseCodePattern(currentLine);
        lineOfRamlText importantInformation = new lineOfRamlText(currentLine);
        this.indentSpaces = importantInformation.getLeadingSpaces();
        this.responseDescription = importantInformation.getRestOfTheLine();  // possibly null;

        successful = false;
        ramlEntity = null;

        currentLine = example.getNextNonNullString(false);
        importantInformation = new lineOfRamlText(currentLine);

        while ((importantInformation.getLeadingSpaces() > this.indentSpaces) && example.getScanner().hasNextLine()) {

            // extract the proposed token.  this token should be the first token on the current row,
            // either description or application/xml.
            // done - use importantInformation.getFirstToken in the switch, and discard "token" instead.
            // Scanner s = new Scanner(currentLine);
            // token = s.next();

            switch(markupType.stringToMarkuptype(importantInformation.getFirstToken())){
                case description:
//                    System.out.println("description in ResponseCode" + this.responseCode + ":");
                    ramlEntity = new RAMLMultiLine(markupType.description, importantInformation.getLeadingSpaces());
                    currentLine = ramlEntity.vaccuumRAMLFIle(example,currentLine);
                    successful = true;
                    break;
                case body:
//                    System.out.println("body in ResponseCode" + this.responseCode + ":");
                    ramlEntity = new RAMLBody( importantInformation.getLeadingSpaces());
                    currentLine = ramlEntity.vaccuumRAMLFIle(example,currentLine);
                    successful = true;
                    break;
                case headers:
                    System.out.println("headers: in ResponseCode" + this.responseCode + ":");
                    ramlEntity = new RAMLQueryParameter(importantInformation.getLeadingSpaces(), "Headers");
                    currentLine = ramlEntity.vaccuumRAMLFIle(example,currentLine);
                    successful = true;
                    break;
                default:
                    System.out.println("Warning: default or unknown token in response code: = " + this.responseCode + ":  at line "+ example.getLine() + " for token:  " + importantInformation.getFirstToken());
            }

            importantInformation = new lineOfRamlText(currentLine);
            if (successful) {
                subtendingThings.add(numberOfThings, ramlEntity);
                numberOfThings++;
                successful = false;
            } else {
                currentLine = example.getNextNonNullString(false);
            }
        }
        return currentLine;
    }

    @Override
    String formatRAMLasHTML(Boolean removeTokenName) {
        // return this.stringMe();
        // always output the response code
        RAMLToken rat;

        String outcome = "\n<span class = \"" + markupType.responsesvalues.CSSClass() + "\">\n";
        outcome += this.responseCode + "\n</span>\n<div>";

        if ((rat = subtendingThings.findMarkupType(markupType.description)) != null) outcome += rat.formatRAMLasHTML(true);

        if ((rat = subtendingThings.findMarkupType(markupType.queryParameters)) != null) outcome += rat.formatRAMLasHTML(true);

        if ((rat = subtendingThings.findMarkupType(markupType.body)) != null) outcome += rat.formatRAMLasHTML(true);

        outcome += "\n</div>\n";
        return outcome;
    }

    @Override
    void spewRAMLFile(String toSave) {

    }

    @Override
    markupType getMarkupType() {
        return markupType.responsesvalues;
    }

    @Override
    public String stringMe() {
        String returnString = getThisManySpaces(this.indentSpaces) + this.responseCode + "\n";
        for (int i=0; i<subtendingThings.size(); i++) {
            if (subtendingThings.get(i) != null) {
                returnString += subtendingThings.get(i).stringMe();
                if (i<(subtendingThings.size()-1)) returnString += '\n';
            }
        }
        return returnString;
    }

    public Boolean isEmpty() {
        return this.responseCode.equals(null);
    }

    public static String checkResponseCodePattern ( String candidateName ) {

        // check the provided string for the right pattern
        // 3 digits, an optional space or 2, and a colon

        Pattern pattern = Pattern.compile("\\d\\d\\d.*:");
        Matcher matcher = pattern.matcher(candidateName);
        String found = "";

        if (matcher.find()) {
            found = matcher.group();
//            System.out.println("Matched: ---"+found+"---");
        }
        return found;
    }
}


