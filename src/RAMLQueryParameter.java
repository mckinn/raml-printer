import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by smckinnon on 3/21/14.
 */
public class RAMLQueryParameter extends RAMLToken {

    // This object has a collection of possible QueryParameterTokens, or
    // Descriptions

    int indentSpaces;
    private String markupName;
    RAMLTokenList subtendingThings;
    int numberOfThings;

    public RAMLQueryParameter(int indentSpaces, String name) {
        this.indentSpaces = indentSpaces;
        indentSpaces = 0;
        subtendingThings = new RAMLTokenList();
        numberOfThings = 0;
        markupName = name;
    }

    @Override
    String vaccuumRAMLFIle(RAMLScanner example, String currentLine) {

        RAMLToken ramlEntity;
        Boolean successful;
        String token;

        // extract the indent level from current line.
        // CurrentLine should contain the queryParameter: tag

        lineOfRamlText importantInformation = new lineOfRamlText(currentLine);
        this.indentSpaces = importantInformation.getLeadingSpaces();

        successful = false;
        ramlEntity = null;

        currentLine = example.getNextNonNullString(false);
        importantInformation = new lineOfRamlText(currentLine);

        while ((importantInformation.getLeadingSpaces() > this.indentSpaces) && example.getScanner().hasNextLine()) {

            // extract the proposed token.  If null then there was an error
            token = RAMLQueryParameterToken.checkQueryParameterNamePattern(currentLine);

            if (!token.equals(null)) {
                if (token.equals("description:")) {
//                    System.out.println("description in query parameters list");
                    ramlEntity = new RAMLMultiLine(markupType.description, importantInformation.getLeadingSpaces());
                    currentLine = ramlEntity.vaccuumRAMLFIle(example,currentLine);
                    successful = true;
                } else {
                    // the token is correctly formatted and not a description, so it must be a
                    // RAML Query Parameter Token of the form "[a-z,A-Z]*:"
//                    System.out.println(token + " in query parameters list");
                    ramlEntity = new RAMLQueryParameterToken(token, importantInformation.getLeadingSpaces());
                    currentLine = ramlEntity.vaccuumRAMLFIle(example,currentLine);
                    successful = true;
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
        // return this.stringMe();
        // never display "queryParameter"

        String outcome = "\n<div><!-- in queryParameter -->\n" +
                "<h4 class = \"" + markupType.queryParameters.CSSClass() + "\"> "+ this.getMarkupName() + " </h4>";

        if (!subtendingThings.isEmpty()) {
            outcome += "<table style=\"text-align: left;\" " +
                    " border=\"1\" cellpadding=\"2\" cellspacing=\"2\"> " +
                    "  <tbody>\n";
            int i = 0;
            for (RAMLToken rt : subtendingThings) {
                if ((i % 3) == 0) {
                    outcome += "<tr>"; }
                outcome += "<td  style=\"vertical-align: top;\" >";
                if (rt.getMarkupType() == markupType.queryParameterNames) {
                    outcome += rt.formatRAMLasHTML(false);
                }
                outcome += "</td>";

                i++;
                if ((i % 3) == 0) {
                    outcome += "</tr>"; }
            }
            outcome += "  </tbody>\n" +
                    "</table>\n";
        }

        outcome += "\n</div>\n";
        return outcome;
    }


    @Override
    markupType getMarkupType() {
        return markupType.queryParameters;
    }

    @Override
    void spewRAMLFile(String toSave) {

    }

    @Override
    public String stringMe() {
        String returnString = getThisManySpaces(this.indentSpaces) + "queryParameters:" + "\n";
        for (int i=0; i<subtendingThings.size(); i++) {
            if (subtendingThings.get(i) != null) {
                returnString += subtendingThings.get(i).stringMe();
                if (i<(subtendingThings.size()-1)) returnString += '\n';
            }
        }
        return returnString;
    }


    public String getMarkupName() {
        return markupName;
    }

    public void setMarkupName(String markupName) {
        this.markupName = markupName;
    }
}
