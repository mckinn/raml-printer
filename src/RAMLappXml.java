import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by smckinnon on 3/15/14.
 */
public class RAMLappXml extends RAMLToken {

    ArrayList<RAMLToken> subtendingThings;
    int numberOfThings;
    int indentSpaces ;

    public RAMLappXml(int is) {
        this.subtendingThings = new ArrayList<RAMLToken>();
        this.numberOfThings = 0;
        this.indentSpaces = is;
    }

    @Override
    String vaccuumRAMLFIle(Scanner example, String currentLine) {

        // currentLine contains a line that should contain only "application/xml"
        // The following lines should contain descriptions, examples, and schema
        // the ending condition is that the spaces in front of the current string are less than or equal to indentSpaces.

        RAMLToken ramlEntity;
        Boolean successful;
        Boolean firstIn = true;

        lineOfRamlText importantInformation = new lineOfRamlText(currentLine);
        this.indentSpaces = importantInformation.getLeadingSpaces();

        successful = false;
        ramlEntity = null;

        currentLine = getNextNonNullString(example, false); // the first line of the rest of the payload.
        importantInformation = new lineOfRamlText(currentLine);

        while ((importantInformation.getLeadingSpaces() > this.indentSpaces) && example.hasNextLine()) {

            switch(markupType.stringToMarkuptype(importantInformation.getFirstToken())){
                // todo - create a mut variable and the three cases below can be collapsed into one
                case description:
//                    System.out.println("description in application/xml");
                    ramlEntity = new RAMLMultiLine(markupType.description, importantInformation.getLeadingSpaces());
                    currentLine = ramlEntity.vaccuumRAMLFIle(example,currentLine);
                    successful = true;
                    break;
                case example:
//                    System.out.println("example in application/xml");
                    ramlEntity = new RAMLMultiLine(markupType.example, importantInformation.getLeadingSpaces());
                    currentLine = ramlEntity.vaccuumRAMLFIle(example,currentLine);
                    successful = true;
                    break;
                case schema:
//                    System.out.println("example/description in application/xml");
                    ramlEntity = new RAMLMultiLine(markupType.schema, importantInformation.getLeadingSpaces());
                    currentLine = ramlEntity.vaccuumRAMLFIle(example,currentLine);
                    successful = true;
                    break;
                case unknown:
                    break;
                default:
                    break;
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
        String returnString = getThisManySpaces(this.indentSpaces) + "Application/XML:" + "\n";
        for (int i=0; i<subtendingThings.size(); i++) {
            if (subtendingThings.get(i) != null) {
                returnString += subtendingThings.get(i).stringMe();
                if (i<(subtendingThings.size()-1)) returnString += '\n';
            }
        }
        return returnString;
    }
}
