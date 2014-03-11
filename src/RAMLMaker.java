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
        String token = "description:";
        Scanner example = null;
        try {
            example = new Scanner(new File("C:\\Users\\smckinnon\\Documents\\GitHub\\raml-printer\\Docs\\super-simple RAML Example.raml"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line;



        RAMLDescription theOne /* = new RAMLDescription() */;
        line = "";
        token = "";
        int arrayPosition = 0;
        assert example != null;
        line = example.nextLine();
        token = example.next();
        while (example.hasNextLine()) {
            if (token.equals("description:")) {
                // create a RAMLDescription object and save it somewhere
                theOne = new RAMLDescription();
                theOne.vaccuumRAMLFIle(example);
                tokens[arrayPosition] = theOne;
                arrayPosition ++;
                token = example.next();
                // descriptionArray[arrayPosition] = description;
            } else if ((token.equals("put:")) || (token.equals("get:")) || (token.equals("delete:")) || (token.equals("post:"))) {
                // create a RAMLHTTPMethod object
                // arrayPosition ++;
                token = example.next();
            } else {
                token = example.next();
            }
        }
        for (int i=0; i<arrayPosition; i++) {
            System.out.println("Slot " + i + " is: " + tokens[i]);
        }
        System.out.println();
        for (int i=0; i<arrayPosition; i++) {
            System.out.println("Description " + i + ": " + tokens[i].stringMe());
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