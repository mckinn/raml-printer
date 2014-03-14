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
        RAMLToken[] tokens = new RAMLToken[10];
        String[] descriptionArray = new String[10];
        boolean successful;
        String token = "description:";
        Scanner example = null;
        try {
            //example = new Scanner(new File("C:\\Users\\smckinnon\\Documents\\GitHub\\raml-printer\\Docs\\super-simple RAML Example.raml"));
            example = new Scanner(new File("C:\\Users\\Ian\\Documents\\GitHub\\raml-printer\\Docs\\super-simple RAML Example.raml"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line;

        markupType theTypes [] = markupType.values();
        for(markupType m : theTypes) {
            System.out.println(m);
        }

        RAMLToken ramlEntity;
        RAMLDescription RAMLD /* = new RAMLDescription() */;
        RAMLBody RAMLB;

        // RAMLDescription theOne /* = new RAMLDescription() */;
        line = "";
        token = "";
        int arrayPosition = 0;
        assert example != null;
        line = example.nextLine();
        //token = example.next();
        String followingWord = "";
        int followingWordCheck = 0;
        while (example.hasNextLine()) {
            successful = false;
            ramlEntity = null;
            if (followingWord.equals("")) {
                token = example.next();
            } else {
                token = followingWord;
            }
            switch(markupType.stringToMarkuptype(token)){
                case description:
                    // create a RAMLDescription object and save it somewhere
                    System.out.println("description***");
                    ramlEntity = new RAMLDescription();
                    followingWord = ramlEntity.vaccuumRAMLFIle(example);
                    successful = true;
                    break;
                case httpMethod:
                    ramlEntity = new RAMLHTTPMethod(token, 10);
                    followingWord = ramlEntity.vaccuumRAMLFIle(example);
                    successful = true;
                    break;
                case body:
                    System.out.println("body***");
                    ramlEntity = new RAMLBody();
                    followingWord = ramlEntity.vaccuumRAMLFIle(example);
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
                tokens[arrayPosition] = ramlEntity;
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
            System.out.println("Slot " + i + " is: " + tokens[i]);
        }
        System.out.println();
        for (int i=0; i<arrayPosition; i++) {
            System.out.println(tokens[i].stringMe());
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