import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Dad on 3/9/14.
 */
public class RAMLHTTPMethod extends RAMLToken{

    RAMLTokenList subtendingThings;
    String methodName;
    int numberOfThings;
    int indentSpaces;

    RAMLHTTPMethod (String typeOfMethod, int ids) {
        methodName = typeOfMethod;
        numberOfThings = 0;
        subtendingThings = new RAMLTokenList();
        indentSpaces = ids;

    }

    public String getMethodName() {
        return methodName;
    }

    String vaccuumRAMLFIle (RAMLScanner example, String currentLine){

        // while the number of spaces is current-number + 4
        //     look for a description or a body
        //     call the appropriate vaccuum for the type
        //     place the resulting object in the array
        //
        String token;
        boolean successful;
        RAMLToken ramlEntity;

        this.numberOfThings = 0;
        assert example != null;

        lineOfRamlText importantInformation = new lineOfRamlText(currentLine);
        this.indentSpaces = importantInformation.getLeadingSpaces();

        currentLine = example.getNextNonNullString(false);
        importantInformation = new lineOfRamlText(currentLine);

        while (example.getScanner().hasNextLine() && (importantInformation.getLeadingSpaces() > this.indentSpaces)) {
            successful = false;
            ramlEntity = null;

            switch(markupType.stringToMarkuptype(importantInformation.getFirstToken())){
                case description:
//                    System.out.println("description***");
                    ramlEntity = new RAMLMultiLine(markupType.description, importantInformation.getLeadingSpaces());
                    currentLine = ramlEntity.vaccuumRAMLFIle(example,currentLine);
                    successful = true;
                    break;
                case body:
//                    System.out.println("body***");
                    ramlEntity = new RAMLBody(importantInformation.getLeadingSpaces());
                    currentLine = ramlEntity.vaccuumRAMLFIle(example,currentLine);
                    successful = true;
                    break;
                case responses:
//                    System.out.println("Responses ***");
                    ramlEntity = new RAMLResponses(importantInformation.getLeadingSpaces());
                    currentLine = ramlEntity.vaccuumRAMLFIle(example,currentLine);
                    successful = true;
                    break;
                case queryParameters:
                    ramlEntity = new RAMLQueryParameter(importantInformation.getLeadingSpaces(), "Query Parameters");
                    currentLine = ramlEntity.vaccuumRAMLFIle(example,currentLine);
                    successful = true;
                    break;
                default:
                    System.out.println("Warning: default or unknown token in HTTP Method at line "+ example.getLine() + " for token: "+ importantInformation.getFirstToken());
            }
            if (successful) {
                subtendingThings.add(this.numberOfThings, ramlEntity);
                this.numberOfThings ++;
            } else {
                currentLine = example.getNextNonNullString(false);
            }
            importantInformation = new lineOfRamlText(currentLine);
        }
        return currentLine;
    }

    @Override
    String formatRAMLasHTML(Boolean removeTokenName){
//        return "\n<xmp>\n" + this.stringMe() + "\n</xmp>\n";

        RAMLToken rat;

        String outcome = "\n<div><!-- in httpMethod -->\n" +
                "<h3 class = \"" + markupType.httpMethod.CSSClass() + "\"> " + this.methodName.toUpperCase() +" </h3><div class=\"hangingindent\">";

        if ((rat = subtendingThings.findMarkupType(markupType.description)) != null) outcome += rat.formatRAMLasHTML(true);

        if ((rat = subtendingThings.findMarkupType(markupType.queryParameters)) != null) outcome += rat.formatRAMLasHTML(true);

        if ((rat = subtendingThings.findMarkupType(markupType.body)) != null) outcome += rat.formatRAMLasHTML(true);

        for (RAMLToken rt: subtendingThings){
            if (rt.getMarkupType() == markupType.responses) {
                outcome += rt.formatRAMLasHTML(false);
            }
        }
        outcome += "\n</div>\n</div>\n";
        return outcome;
    }

    @Override
    markupType getMarkupType() {
        return markupType.httpMethod;
    }

    void spewRAMLFile (String toSave){}

    public  String stringMe(){
        String returnString = getThisManySpaces(this.indentSpaces) + this.methodName + "\n";
        for (int i=0; i<subtendingThings.size(); i++) {
            if (subtendingThings.get(i) != null) {
                returnString += subtendingThings.get(i).stringMe();
                if (i<(subtendingThings.size()-1)) returnString += '\n';
            }
        }
        return returnString;
    }
}
