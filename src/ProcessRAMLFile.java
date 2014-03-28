import java.io.File;

/**
 * Created by Ian on 3/9/14.
 */
import java.io.*;
import java.util.*;

public class ProcessRAMLFile {

    public static void main(String args[]) {
        // open the text file for reading
        // for each of the lines
        // find  a RAML object type:
        //
        ArrayList<RAMLToken> documentComponents = new ArrayList<RAMLToken>();

        Boolean successful;
        String token;
        RAMLToken ramlEntity;
        int indentSpaces ;

        Scanner example = null;
        try {
            example = new Scanner(new File("C:\\Users\\smckinnon\\Documents\\GitHub\\raml-printer\\Docs\\iris.min.raml"));
            //example = new Scanner(new File("C:\\Users\\Ian\\Documents\\GitHub\\raml-printer\\Docs\\super-simple RAML Example.raml"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int arrayPosition = 0;
        assert example != null;

        String followingLine = RAMLToken.getNextNonNullString(example, false);

        while (example.hasNextLine()) {
            successful = false;
            ramlEntity = null;

            lineOfRamlText importantInformation = new lineOfRamlText(followingLine);
            indentSpaces = importantInformation.getLeadingSpaces();
            token = importantInformation.getFirstToken();

            switch(markupType.stringToMarkuptype(token)){
                case description:
//                    System.out.println("description***");
                    ramlEntity = new RAMLMultiLine(markupType.description, importantInformation.getLeadingSpaces());
                    followingLine = ramlEntity.vaccuumRAMLFIle(example, followingLine);
                    successful = true;
                    break;
                case httpMethod:
//                    System.out.println("HTTP Method***");
                    ramlEntity = new RAMLHTTPMethod(token, importantInformation.getLeadingSpaces());
                    followingLine = ramlEntity.vaccuumRAMLFIle(example, followingLine);
                    successful = true;
                    break;
                case body:
//                    System.out.println("body***");
                    ramlEntity = new RAMLBody(importantInformation.getLeadingSpaces());
                    followingLine = ramlEntity.vaccuumRAMLFIle(example, followingLine);
                    successful = true;
                    break;
                case pathElement:
                    token = RAMLPathElement.checkPathElementPattern(token);
                    if (!token.equals("")) {
//                        System.out.println(token + "another path in path");
                        ramlEntity = new RAMLPathElement(token, importantInformation.getLeadingSpaces());
                        followingLine = ramlEntity.vaccuumRAMLFIle(example,followingLine);
                        successful = true;
                    } else {
                        System.out.println("*** Error: bad token in Paths list: " + token );
                    }
                    break;

                case otcoThorpe:
//                    System.out.println("comment***");
                    ramlEntity = new RAMLComment();
                    followingLine = ramlEntity.vaccuumRAMLFIle(example, followingLine);
                    successful = true;
                    break;
                case unknown:
                default: {
                    System.out.println("default / unknown string token = "+token);

                }
            }
            if (successful) {
                documentComponents.add(arrayPosition, ramlEntity);
                arrayPosition ++;
            } else {
                followingLine = RAMLToken.getNextNonNullString(example, false);
            }

        }

        System.out.println("**********************************************************************************");

        PrintStream out = getOutputPrintStream();
        String outcome;

        for (RAMLToken rt: documentComponents){
            outcome = rt.stringMe();
            // System.out.println(outcome);
            out.println(outcome);
        }

    }

    public static PrintStream getOutputPrintStream() {
        Scanner console = new Scanner(System.in);
        System.out.print("Enter output file name: ");
        PrintStream out = null;
        do {
            String outputName = console.next();
            File f = new File(outputName);
            try{
                if(f.exists()) {
                    System.out.print("File already exists! Overwrite? (y/n): ");
                    String response = console.next();
                    if (response.equalsIgnoreCase("y")) {
                        out = new PrintStream(f);
                    } else {
                        System.out.print("New file name: ");
                    }
                } else {
                    out = new PrintStream(f);
                }
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
        } while (out == null);
        return out;
    }


}