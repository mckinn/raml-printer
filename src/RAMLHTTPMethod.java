import java.util.Scanner;

/**
 * Created by Dad on 3/9/14.
 */
public class RAMLHTTPMethod extends RAMLToken{

    RAMLToken [] subtendingThings;
    String methodName;
    int numberOfThings;
    int maxNumberOfThings;

    RAMLHTTPMethod (String typeOfMethod, int max) {
        methodName = typeOfMethod;
        maxNumberOfThings = max;
        numberOfThings = 0;
        subtendingThings = new RAMLToken[max];

    }

    String vaccuumRAMLFIle (Scanner example){

        // while the number of spaces is current-number + 4
        //     look for a description or a body
        //     call the appropriate vaccuum for the type
        //     place the resulting object in the array
        //

        String line = "";
        String token = "";
        boolean successful;
        RAMLToken ramlEntity;
        int arrayPosition = 0;
        assert example != null;
        //line = example.nextLine();
        String followingLine = "";
        int followingWordCheck = 0;
        int lineSpacesCount = 8;
        int baseLineSpacesCount = lineSpacesCount - 4;
        while (example.hasNextLine() && lineSpacesCount >= baseLineSpacesCount+4) {
            successful = false;
            ramlEntity = null;
            if (followingLine.equals("")) {
                token = example.next();
            } else {
                token = getFirstWord(followingLine);
            }
            switch(markupType.stringToMarkuptype(token)){
                case description:
                    // create a RAMLDescription object and save it somewhere
                    System.out.println("description***");
                    ramlEntity = new RAMLDescription();
                    followingLine = ramlEntity.vaccuumRAMLFIle(example);
                    successful = true;
                    break;
                case body:
                    System.out.println("body***");
                    ramlEntity = new RAMLBody();
                    followingLine = ramlEntity.vaccuumRAMLFIle(example);
                    successful = true;
                    break;
                case pathElement:
                    break;
                case applicationXML:
                    break;
                case schema:
                    break;
                case example:
                    break;
                case responses:
                    break;
                case responsesvalues:
                    break;
                case queryParameters:
                    break;
                case queryParameterNames:
                    break;
                case qpType:
                    break;
                case qpMaximim:
                    break;
                case qpMinimum:
                    break;
                case qpRequired:
                    break;
                case qpRepeats:
                    break;
                case qpDefault:
                    break;
                case qpEnum:
                    break;
                case displayName:
                    break;
                case otcoThorpe:
                    break;
                default:
                    System.out.println("default string token = "+token);
            }
            if (successful) {
                subtendingThings[arrayPosition] = ramlEntity;
                arrayPosition ++;
                successful = false;
            }
            lineSpacesCount = lineSpaces(followingLine);
            followingLine = getFirstWord(followingLine);
        }
        return followingLine;
    }

    String formatRAMLasHTML ( RAMLToken toFormat){
        return "RAML as HTML";
    }

    void spewRAMLFile (String toSave){}

    public  String stringMe(){
        String returnString = this.methodName + "\n";
        for (int i=0; i<subtendingThings.length; i++) {
            if (subtendingThings[i] != null) {
                returnString += subtendingThings[i].stringMe() + "\n";
            }
        }
        return returnString;
    }
}
