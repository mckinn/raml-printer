import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Dad on 3/9/14.
 */
public class RAMLBody extends RAMLToken {

    int indentSpaces;
    ArrayList<RAMLToken> subtendingThings;
    int numberOfThings;

    // constructor for the object.

    public RAMLBody(int indentSpaces) {
        this.indentSpaces = indentSpaces;
        this.subtendingThings = new ArrayList<RAMLToken>();
        this.numberOfThings = 0;
    }

    String vaccuumRAMLFIle(RAMLScanner example, String currentLine) {

        RAMLToken ramlEntity;
        Boolean successful;
        String token;

        // extract the indent level from current line.
        // CurrentLine should contain the body: tag
        // all that is allowed are descriptions: and application/xml tags.
        // the pattern is similar to QueryParameters.

        lineOfRamlText importantInformation = new lineOfRamlText(currentLine);
        this.indentSpaces = importantInformation.getLeadingSpaces();

        successful = false;
        ramlEntity = null;

        currentLine = example.getNextNonNullString(false);
        importantInformation = new lineOfRamlText(currentLine);

        while ((importantInformation.getLeadingSpaces() > this.indentSpaces) && example.getScanner().hasNextLine()) {

            // extract the proposed token.  this token should be the first token on the current row,
            // either description or application/xml.
            // done
            // - replace with importantInformation ???
            // Scanner s = new Scanner(currentLine);
            // token = s.next();

            switch(markupType.stringToMarkuptype(importantInformation.getFirstToken())){
                case description:
//                    System.out.println("description in body:");
                    ramlEntity = new RAMLMultiLine(markupType.description, importantInformation.getLeadingSpaces());
                    currentLine = ramlEntity.vaccuumRAMLFIle(example,currentLine);
                    successful = true;
                    break;
                case applicationXML:
//                    System.out.println("application/xml in body:");
                    // done RAMLXML needs an indent lines.
                    ramlEntity = new RAMLappXml(importantInformation.getLeadingSpaces());
                    currentLine = ramlEntity.vaccuumRAMLFIle(example,currentLine);
                    successful = true;
                    break;
                default:
                    System.out.println("Warning: default or unknown token in body: at line "+ example.getLine() + " for token: " + importantInformation.getFirstToken());
            }

            importantInformation = new lineOfRamlText(currentLine);
            if (successful) {
                subtendingThings.add(numberOfThings, ramlEntity);
                numberOfThings++;
                successful = false;
            } else {
                currentLine = example.getNextNonNullString(false) ;
            }
        }
        return currentLine;
    }

    String formatRAMLasHTML(Boolean removeTokenName) {
        // ignore removeTokenName = never print the "body:"
        // we expect a description and an application/xml
        String outcome = "\n<div>\n";
        for (RAMLToken rt : subtendingThings) {
            if (rt.getMarkupType() == markupType.description) outcome += rt.formatRAMLasHTML(true);
            if (rt.getMarkupType() == markupType.applicationXML) outcome += rt.formatRAMLasHTML(true);
        }
        outcome += "\n</div>\n";
        return outcome;
    }

    @Override
    markupType getMarkupType() {
        return markupType.body;
    }

    void spewRAMLFile (String toSave){}

    public  String stringMe() {
        String returnString = getThisManySpaces(this.indentSpaces) + "Body:" + "\n";
        for (int i=0; i<subtendingThings.size(); i++) {
            if (subtendingThings.get(i) != null) {
                returnString += subtendingThings.get(i).stringMe();
                if (i<(subtendingThings.size()-1)) returnString += '\n';
            }
        }
        return returnString;
    }


}
