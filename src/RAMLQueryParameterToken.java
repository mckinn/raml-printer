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
    RAMLTokenList subtendingThings;
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
        this.subtendingThings = new RAMLTokenList();
    }

    public RAMLQueryParameterToken(String qpParmName, int indentSpaces) {
        this.qpParmName = checkQueryParameterNamePattern(qpParmName);  // null if the pattern did not match, or the pattern itself
        this.indentSpaces = indentSpaces;
        this.numberOfThings = 0;
        this.subtendingThings = new RAMLTokenList();
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
    String vaccuumRAMLFIle(RAMLScanner example, String currentLine) {

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

        currentLine = example.getNextNonNullString(false);
        importantInformation = new lineOfRamlText(currentLine);

        while ((importantInformation.getLeadingSpaces() > this.indentSpaces) && example.getScanner().hasNextLine()) {

            switch (mut = markupType.stringToMarkuptype(importantInformation.getFirstToken())) {
                case description:
/*                  System.out.println("description in qpParms");
                    ramlEntity = new RAMLMultiLine(mut, importantInformation.getLeadingSpaces());
                    currentLine = ramlEntity.vaccuumRAMLFIle(example,currentLine);
                    successful = true;
                    break;*/
                case example:
                case qpType:
                case qpMaximim:
                case qpMinimum:
                case qpRequired:
                case qpRepeat:
                case qpDefault:
                case qpEnum:
                case displayName:
                    //  All potential subtending types are going to be treated as a RAMLQPTokenValue.
                    ramlEntity = new RAMLQPTokenValue(mut, importantInformation.getLeadingSpaces());
                    currentLine = ramlEntity.vaccuumRAMLFIle(example, currentLine);
                    successful = true;
                    break;
                case unknown:
                default:
                    System.out.println("Warning: default or unknown token in Query Parameters token list: at line "+ example.getLine() + " for token: " + importantInformation.getFirstToken() );
                    break;
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

        String outcome = "\n<span class = \"" + markupType.queryParameterNames.CSSClass() + "\">\n"
                         + this.qpParmName + "\n</span><!-- in QueryParameterToken -->\n<div><ul>";

        for (RAMLToken rt: subtendingThings){
            switch(rt.getMarkupType()) {
                case qpType:
                case qpMaximim:
                case qpMinimum:
                case qpRequired:
                case qpRepeat:
                case qpDefault:
                case qpEnum:
                case displayName:
                case description:
                case example:
                    outcome +=  "<li>" + rt.formatRAMLasHTML(false) + "</li>";
                    break;
                default:

            }
        }
        outcome += "\n</ul></div>\n";
        return outcome;
    }

    @Override
    void spewRAMLFile(String toSave) {

    }

    @Override
    public String stringMe() {
        String returnString = getThisManySpaces(this.indentSpaces) + this.qpParmName + " " + "\n";
        for (int i=0; i<subtendingThings.size(); i++) {
            if (subtendingThings.get(i) != null) {
                returnString += subtendingThings.get(i).stringMe();
                if (i<(subtendingThings.size()-1)) returnString += '\n';
            }
        }
        return returnString;
    }

    @Override
    markupType getMarkupType() {
        return markupType.queryParameterNames;
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
