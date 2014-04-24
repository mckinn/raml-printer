import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by smckinnon on 3/22/14.
 */
public class RAMLPathElement extends RAMLToken {

    String pathElement;
    int indentSpaces;
    RAMLTokenList subtendingThings;  // all will be Responses Values or Descriptions
    int numberOfThings;
    String precursorPath;

    public RAMLPathElement(String pathElement, int indentSpaces) {
        this.pathElement = pathElement.substring(0,pathElement.indexOf(":"));  // get rid of the ":"
        this.indentSpaces = indentSpaces;
        this.subtendingThings = new RAMLTokenList();
        this.numberOfThings = 0;
        this.precursorPath = "";
    }

    public void setPrecursorPath(String precursorPath) {
        this.precursorPath = precursorPath;
    }

    @Override
    String vaccuumRAMLFIle(Scanner example, String currentLine) {

        // A pathElement can contain...
        // - a description
        // - a response
        // - a another pathElement

        RAMLToken ramlEntity;
        Boolean successful;
        String token;

        // extract the indent level from current line.
        // CurrentLine should contain the queryParameter: tag

        lineOfRamlText importantInformation = new lineOfRamlText(currentLine);
        this.indentSpaces = importantInformation.getLeadingSpaces();

        successful = false;
        ramlEntity = null;

        currentLine = getNextNonNullString(example, false);
        importantInformation = new lineOfRamlText(currentLine);

        while ((importantInformation.getLeadingSpaces() > this.indentSpaces) && example.hasNextLine()) {

            // all of the following tokens are either responses "nnn:" or descriptions
            // extract the proposed token.  If null then there was an error

            token = importantInformation.getFirstToken();

            if (token.equals("description:")) {
//                System.out.println("description in path");
                ramlEntity = new RAMLMultiLine(markupType.description, importantInformation.getLeadingSpaces());
                currentLine = ramlEntity.vaccuumRAMLFIle(example,currentLine);
                successful = true;
            } else {
                // check to see if it is a response.
                if (markupType.stringToMarkuptype(token) == markupType.httpMethod ) {
                    // it is a response
//                    System.out.println(token + "http method in path");
                    ramlEntity = new RAMLHTTPMethod( token, importantInformation.getLeadingSpaces());
                    currentLine = ramlEntity.vaccuumRAMLFIle(example,currentLine);
                    successful = true;
                } else {
                    // hope for another path.
                    if (!RAMLPathElement.checkPathElementPattern(token).equals("")) {
//                        System.out.println(token + "another path in path");
                        ramlEntity = new RAMLPathElement(token, importantInformation.getLeadingSpaces());
                        currentLine = ramlEntity.vaccuumRAMLFIle(example,currentLine);
                        successful = true;
                    } else {
                        System.out.println("*** Error: bad token in Paths list: " + token + " --- " + currentLine);
                        currentLine = getNextNonNullString(example, false);
                    }
                }
            }

            importantInformation = new lineOfRamlText(currentLine);
            if (successful) {
                subtendingThings.add(numberOfThings, ramlEntity);
                numberOfThings++;
                successful = false;
            }
        }
        return currentLine;
    }

    @Override
    String formatRAMLasHTML(Boolean removeTokenName) {

        // ignore removeTokenName = always display the path element

        RAMLToken rat;

        String outcome = "\n<div><!-- in path -->\n" +
                "<h1 class = \"" + markupType.pathElement.CSSClass() + "\"> " + this.precursorPath + this.pathElement + " </h1>";

        if ((rat = subtendingThings.findMarkupType(markupType.description)) != null) outcome += rat.formatRAMLasHTML(false);

        for (RAMLToken rt: subtendingThings){  // get first if present, then POST, PUT, and DELETE
            if (rt.getMarkupType() == markupType.httpMethod) {
                if ( ((RAMLHTTPMethod) rt).getMethodName().equals("get:")) {
                    outcome += rt.formatRAMLasHTML(false);
                }
            }
        }
        for (RAMLToken rt: subtendingThings){  // get first if present, then POST, PUT, and DELETE
            if (rt.getMarkupType() == markupType.httpMethod) {
                if ( ((RAMLHTTPMethod) rt).getMethodName().equals("post:")) {
                    outcome += rt.formatRAMLasHTML(false);
                }
            }
        }
        for (RAMLToken rt: subtendingThings){  // get first if present, then POST, PUT, and DELETE
            if (rt.getMarkupType() == markupType.httpMethod) {
                if ( ((RAMLHTTPMethod) rt).getMethodName().equals("put:")) {
                    outcome += rt.formatRAMLasHTML(false);
                }
            }
        }
        for (RAMLToken rt: subtendingThings){  // get first if present, then POST, PUT, and DELETE
            if (rt.getMarkupType() == markupType.httpMethod) {
                if ( ((RAMLHTTPMethod) rt).getMethodName().equals("delete:")) {
                    outcome += rt.formatRAMLasHTML(false);
                }
            }
        }

        for (RAMLToken rt: subtendingThings){
            if (rt.getMarkupType() == markupType.pathElement) {
                ((RAMLPathElement) rt).setPrecursorPath(this.precursorPath + this.pathElement);
                outcome += rt.formatRAMLasHTML(false);
            }
        }
        outcome += "\n</div>\n";
        return outcome;
    }

    @Override
    void spewRAMLFile(String toSave) {

    }

    @Override
    markupType getMarkupType() {
        return markupType.pathElement;
    }

    @Override
    public String stringMe() {
        String returnString = getThisManySpaces(this.indentSpaces)  + this.pathElement + "\n";
        for (int i=0; i<subtendingThings.size(); i++) {
            if (subtendingThings.get(i) != null) {
                returnString += subtendingThings.get(i).stringMe();
                if (i<(subtendingThings.size()-1)) returnString += '\n';
            }
        }
        return returnString;
    }

    public static String checkPathElementPattern ( String candidateName ) {

        // check the provided string for the right pattern
        // 3 digits, an optional space or 2, and a colon
        // The metacharacters supported by this API are: <([{\^-=$!|]})?*+.>

        Pattern pattern = Pattern.compile("/\\{?\\w+\\}?:");
        // System.out.println("Pattern: ---"+pattern.toString()+"---"+candidateName+"---");
        Matcher matcher = pattern.matcher(candidateName);
        String found = "";

        if (matcher.find()) {
            found = matcher.group();
            // System.out.println("Matched: ---"+found+"---");
        }
        return found;
    }
}
