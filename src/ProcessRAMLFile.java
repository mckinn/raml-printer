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
        RAMLTokenList documentComponents = new RAMLTokenList();

        Boolean successful;
        String token;
        RAMLToken ramlEntity;
        markupType mut;

        RAMLScanner example = getInputStream();
/*        try {
            example = new Scanner(new File("C:\\Users\\smckinnon\\Documents\\GitHub\\raml-printer\\Docs\\iris.min.raml"));
            //example = new Scanner(new File("C:\\Users\\Ian\\Documents\\GitHub\\raml-printer\\Docs\\super-simple RAML Example.raml"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/

        int arrayPosition = 0;
        assert example != null;

        String followingLine = example.getNextNonNullString(false);

        while (example.getScanner().hasNextLine()) {
            successful = false;
            ramlEntity = null;

            lineOfRamlText importantInformation = new lineOfRamlText(followingLine);
            token = importantInformation.getFirstToken();

            switch(mut=markupType.stringToMarkuptype(token)){
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
                        System.out.println("Error: bad token in Paths list at line:" + example.getLine() + " for token: " + token );
                    }
                    break;

                case title:
                case baseUri:
                case version:
                case securedBy:
                    ramlEntity = new RAMLSingleLine(mut);
                    followingLine = ramlEntity.vaccuumRAMLFIle(example, followingLine);
                    successful = true;
                    break;
                case unknown:
                default: {
                    System.out.println("Warning: default or unknown token at line "+ example.getLine() + " for token: " +token);

                }
            }
            if (successful) {
                documentComponents.add(arrayPosition, ramlEntity);
                arrayPosition ++;
            } else {
                followingLine = example.getNextNonNullString( false);
            }

        }

        PrintStream out = getOutputPrintStream();
        String outcome;

/*        for (RAMLToken rt: documentComponents){
            outcome = rt.stringMe();
            out.println(outcome);
        }*/

        RAMLToken rat;
        outcome =  "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">" +
                   "<html>\n" +
                   "<head>\n" +
                   "<link rel=\"stylesheet\" type=\"text/css\" href=\"RAMLStyle.css\">" +
                   "<title>";
        if ((rat = documentComponents.findMarkupType(markupType.title)) != null) outcome += rat.toString();
        outcome += "</title>" +
                   "</head>\n" +
                   "<body>\n";

        if ((rat = documentComponents.findMarkupType(markupType.title)) != null) outcome += rat.formatRAMLasHTML(true);
        if ((rat = documentComponents.findMarkupType(markupType.version)) != null) outcome += rat.formatRAMLasHTML(false);
        if ((rat = documentComponents.findMarkupType(markupType.baseUri)) != null) outcome += rat.formatRAMLasHTML(false);
        if ((rat = documentComponents.findMarkupType(markupType.securedBy)) != null) outcome += rat.formatRAMLasHTML(false);
        if ((rat = documentComponents.findMarkupType(markupType.description)) != null) outcome += rat.formatRAMLasHTML(false);

        out.println(outcome);
        outcome = "";

        for (RAMLToken rt: documentComponents){
            if (rt.getMarkupType() == markupType.pathElement) {
                outcome = "<hr>";
                outcome += rt.formatRAMLasHTML(false);
                out.println(outcome);
                outcome = "";
            }


        }

        outcome = "</body>\n</html>";
        out.println(outcome);

    }

    public static PrintStream getOutputPrintStream() {
        Scanner console = new Scanner(System.in);
        System.out.print("Enter output file name: ");
        PrintStream out = null;
        do {
            String outputName;
            outputName = console.next();
//            outputName = "c:\\users\\smckinnon\\raml\\output.html";
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

    public static RAMLScanner getInputStream() {

        Scanner console = new Scanner(System.in);
        System.out.print("Enter input file path and name: ");
        Scanner out = null;

        do {
            String outputName;
//            outputName = "c:\\users\\smckinnon\\raml\\iris.min.raml";
            outputName = console.next();
            try {
                out = new Scanner(new File(outputName));
                //example = new Scanner(new File("C:\\Users\\Ian\\Documents\\GitHub\\raml-printer\\Docs\\super-simple RAML Example.raml"));
            } catch (FileNotFoundException e) {
                out = null;
            }
            // out has a valid value, or null
            if (out == null) {
                System.out.print("can't find it - did you want to try again ? ");
                String response = console.next();
                if (!response.equalsIgnoreCase("y")) {
                    System.exit(1);
                } else {
                    System.out.print("try again: ");
                }
            }
        } while (out == null);
        return new RAMLScanner(out);
    }


}