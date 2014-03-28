import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Created by smckinnon on 3/22/14.
 */
public class RAMLResponses extends RAMLToken {

    int indentSpaces;
    ArrayList<RAMLToken> subtendingThings;  // all will be Responses Values or Descriptions
    int numberOfThings;

    public RAMLResponses(int indentSpaces) {
        this.indentSpaces = indentSpaces;
        this.subtendingThings = new ArrayList<RAMLToken>();
        this.numberOfThings = 0;
    }

    @Override
    String vaccuumRAMLFIle(Scanner example, String currentLine) {

        RAMLToken ramlEntity;
        Boolean successful;
        String token;
        String responseValue;

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
            responseValue = RAMLResponsesValues.checkResponseCodePattern(token);

            if ( responseValue.equals("")) {  // "description:" is the only valid alternative
                if (token.equals("description:")) {
//                    System.out.println("description in Responses");
                    ramlEntity = new RAMLMultiLine(markupType.description, importantInformation.getLeadingSpaces());
                    currentLine = ramlEntity.vaccuumRAMLFIle(example,currentLine);
                    successful = true;
                } else {
                    System.out.println("*** Error: bad token in Responses list: " + token );
                }
            } else {
                // it must be a valid response
//                System.out.println(token + " in query parameters list");
                ramlEntity = new RAMLResponsesValues(importantInformation.getLeadingSpaces(), token);
                currentLine = ramlEntity.vaccuumRAMLFIle(example,currentLine);
                successful = true;
            }

            importantInformation = new lineOfRamlText(currentLine);
            if (successful) {
                subtendingThings.add(numberOfThings, ramlEntity);
                numberOfThings++;
                successful = false;
            } else {
                currentLine = getNextNonNullString(example, false);
            }
        }
        return currentLine;

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
        String returnString = getThisManySpaces(this.indentSpaces) + "responses:" + "\n";
        for (int i=0; i<subtendingThings.size(); i++) {
            if (subtendingThings.get(i) != null) {
                returnString += subtendingThings.get(i).stringMe();
                if (i<(subtendingThings.size()-1)) returnString += '\n';
            }
        }
        return returnString;
    }
}
