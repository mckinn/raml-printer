import java.util.Scanner;

/**
 * Created by Dad on 3/9/14.
 */
public class RAMLBody extends RAMLToken {
// constructor for the object.
    public String[] bodyContent;
        RAMLBody () {
        }

        void vaccuumRAMLFIle (Scanner example){

            // RAMLToken theOne;
            String token;
            String line;
            String[] bodyLines = new String[100];
            // theOne = new RAMLDescription();
            token = example.next();
            String bodyFromFile = "";
            line = example.nextLine();
            int lineSpacesCount = lineSpaces(line, example);
            int baseLineSpaces = lineSpacesCount - 4;
            boolean quit = false;
            int arrayPosition = 0;
                do {
                    if (lineSpacesCount >= 4+baseLineSpaces) {
                        String lineSegment = line.substring(lineSpacesCount,line.length());
                        bodyLines[arrayPosition] = lineSegment;
                        arrayPosition ++;
                    }
                    line = example.nextLine();
                    lineSpacesCount = lineSpaces(line, example);
                    if (lineSpacesCount <= baseLineSpaces) {
                        quit = true;
                    }
                } while (example.hasNextLine() && !quit);
            token = line.substring(lineSpacesCount,line.length());
            this.bodyContent = bodyLines;
        }

        String formatRAMLasHTML ( RAMLToken toFormat){
            return "RAML as Body";
        }

    void spewRAMLFile (String toSave){}

    public  String stringMe(){ return " Body ";}

}
