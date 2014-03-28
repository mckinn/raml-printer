import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by smckinnon on 3/19/14.
 */
public class RAMLQueryParameterToken extends RAMLToken {

    // This represents tokens of the form [a-z]*: used as queryParameter
    // names.  There are no restrictions on the letters that I know about,
    // but I expect lowercase names.


    String qpParmName;    // the [a-z]*:
    int indentSpaces;
    ArrayList<RAMLToken> subtendingThings;
    int numberOfThings;

    // Because we do no know whether the parameter token can be created without
    // checking we create an empty object and fill it, rather than creating a full one
    // with a constructor.
    //
    // another pattern could be to create one that might be empty, or might be full

    public RAMLQueryParameterToken() {
        this.qpParmName = null;
        this.indentSpaces = 0;
        this.numberOfThings = 0;
        this.subtendingThings = new ArrayList<RAMLToken>();
    }

    public RAMLQueryParameterToken(String qpParmName, int indentSpaces) {
        this.qpParmName = checkQueryParameterNamePattern(qpParmName);  // null if the pattern did not match, or the pattern itself
        this.indentSpaces = indentSpaces;
        this.numberOfThings = 0;
        this.subtendingThings = new ArrayList<RAMLToken>();
    }

    public void setIndentSpaces(int indentSpaces) {
        this.indentSpaces = indentSpaces;
    }

    public String getQpParmName() {
        return qpParmName;
    }

    public void setQpParmName(String qpParmName) {
        this.qpParmName = qpParmName;
    }

    @Override
    String vaccuumRAMLFIle(Scanner example, String currentLine) {

        // The RAMLQueryParameterToken on which this was called may or may not be full.

        // A qpParmName is followed by a set of single line parameters.  That and Descriptions are all that are allowed.

        RAMLToken ramlEntity;
        Boolean successful;
        markupType mut;

        if (this.isEmpty()) this.setQpParmName(checkQueryParameterNamePattern(currentLine));
        lineOfRamlText importantInformation = new lineOfRamlText(currentLine);
        this.indentSpaces = importantInformation.getLeadingSpaces();

        successful = false;
        ramlEntity = null;

        currentLine = getNextNonNullString(example, false);
        importantInformation = new lineOfRamlText(currentLine);

        while ((importantInformation.getLeadingSpaces() > this.indentSpaces) && example.hasNextLine()) {

            switch (mut = markupType.stringToMarkuptype(importantInformation.getFirstToken())) {
                case description:
//                    System.out.println("description in qpParms");
                    ramlEntity = new RAMLMultiLine(mut, importantInformation.getLeadingSpaces());
                    currentLine = ramlEntity.vaccuumRAMLFIle(example,currentLine);
                    successful = true;
                    break;
                case example:
//                    System.out.println("example in qpParms");
                    ramlEntity = new RAMLMultiLine(mut, importantInformation.getLeadingSpaces());
                    currentLine = ramlEntity.vaccuumRAMLFIle(example,currentLine);
                    successful = true;
                    break;
                case qpType:
                case qpMaximim:
                case qpMinimum:
                case qpRequired:
                case qpRepeat:
                case qpDefault:
                case qpEnum:
                case displayName:
//                    System.out.println("In qparms with: " + this.getQpParmName() + " : " + markupType.markupTypeToString(mut));
                    ramlEntity = new RAMLSingleLine(mut);
                    currentLine = ramlEntity.vaccuumRAMLFIle(example, currentLine);
                    successful = true;
                    break;
                case unknown:
                default:
                    System.out.println("Bad token in Query Parameter Token List: " + importantInformation.getFirstToken() );
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
        String returnString = getThisManySpaces(this.indentSpaces) + this.qpParmName + ":" + "\n";
        for (int i=0; i<subtendingThings.size(); i++) {
            if (subtendingThings.get(i) != null) {
                returnString += subtendingThings.get(i).stringMe();
                if (i<(subtendingThings.size()-1)) returnString += '\n';
            }
        }
        return returnString;
    }

    public Boolean isEmpty() {
        return this.qpParmName.equals(null);
    }

    public static String checkQueryParameterNamePattern ( String candidateName ) {

        // check the provided string for the right pattern

        Pattern pattern = Pattern.compile("\\w+.*:");
        Matcher matcher = pattern.matcher(candidateName);
        String found = "";

        if (matcher.find()) {
            found = matcher.group();
//            System.out.println("Matched: ---"+found+"---");
        }
        return found;
    }
}
