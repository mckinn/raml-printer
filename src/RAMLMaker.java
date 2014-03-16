import java.io.File;

/**
 * Created by Ian on 3/9/14.
 */
import java.io.*;
import java.util.*;

public class RAMLMaker {
    public static void main(String args[]) {
        // open the text file for reading
        // for each of the lines
        // find  a RAML object type:
        //
        ArrayList<RAMLToken> tokens = new ArrayList<RAMLToken>();

        boolean successful;
        String token = "description:";
        Scanner example = null;
        try {
            example = new Scanner(new File("C:\\Users\\smckinnon\\Documents\\GitHub\\raml-printer\\Docs\\super-simple RAML Example.raml"));
            //example = new Scanner(new File("C:\\Users\\Ian\\Documents\\GitHub\\raml-printer\\Docs\\super-simple RAML Example.raml"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line;

        RAMLToken ramlEntity;
        RAMLDescription RAMLD /* = new RAMLDescription() */;
        RAMLBody RAMLB;

        // RAMLDescription theOne /* = new RAMLDescription() */;
        line = "";
        token = "";
        int arrayPosition = 0;
        assert example != null;
        line = example.nextLine();

        String followingLine = "";
        int followingWordCheck = 0;
        while (example.hasNextLine()) {
            successful = false;
            ramlEntity = null;
            if (followingLine.equals("")) {
                followingLine = example.nextLine();
            }
            Scanner s = new Scanner(followingLine);
            token = s.next();

            switch(markupType.stringToMarkuptype(token)){
                case description:
                    // create a RAMLDescription object and save it somewhere
                    System.out.println("description***");
                    ramlEntity = new RAMLDescription();
                    followingLine = ramlEntity.vaccuumRAMLFIle(example, followingLine);
                    successful = true;
                    break;
                case httpMethod:
                    System.out.println("HTTP Method***");
                    ramlEntity = new RAMLHTTPMethod(token);
                    followingLine = ramlEntity.vaccuumRAMLFIle(example, followingLine);
                    successful = true;
                    break;
                case body:
                    System.out.println("body***");
                    ramlEntity = new RAMLBody();
                    followingLine = ramlEntity.vaccuumRAMLFIle(example, followingLine);
                    successful = true;
                    break;
                case pathElement:
                    break;
                case applicationXML:
                    System.out.println("appXML***");
                    ramlEntity = new RAMLappXml();
                    followingLine = ramlEntity.vaccuumRAMLFIle(example, followingLine);
                    successful = true;
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
                    System.out.println("comment***");
                    ramlEntity = new RAMLComment();
                    followingLine = ramlEntity.vaccuumRAMLFIle(example, followingLine);
                    successful = true;
                    break;
                case unknown:
                    break;
                default:
                    System.out.println("default string token = "+token);
            }
            if (successful) {
                tokens.add(arrayPosition,ramlEntity);
                arrayPosition ++;
                successful = false;
            }
            /*if (followingWordCheck == 1) {
                followingWord = "";
                followingWordCheck = 0;
            }
            if (!followingWord.equals("")) {
                followingWordCheck = 1;
            }*/
            //token = example.next();

            /*if (token.equals("description:")) {
                // descriptionArray[arrayPosition] = description;
            } else if ((token.equals("put:")) || (token.equals("get:")) || (token.equals("delete:")) || (token.equals("post:"))) {
                // create a RAMLHTTPMethod object
                // arrayPosition ++;
                token = example.next();
            } else {
                token = example.next();
            } */
        }
        for (int i=0; i<arrayPosition; i++) {
            System.out.println("Slot " + i + " is: " + tokens.get(i));
        }
        System.out.println();
        for (int i=0; i<arrayPosition; i++) {
            System.out.println(tokens.get(i).stringMe());
        }
    }

    /*
    public static int lineSpaces(String line, Scanner example) {
        int i = 0;
        int spaces = 0;
        while (line.charAt(i) == ' ') {
            spaces ++;
            i++;
        }
        return spaces;
    }
    */
}