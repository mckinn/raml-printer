import java.util.Scanner;

/**
 * Created by Dad on 3/9/14.
 */
public class RAMLBody extends RAMLToken {
// constructor for the object.
    public String bodyContent;
        RAMLBody () {
        }

        String vaccuumRAMLFIle (Scanner example){

            // RAMLToken theOne;
            String token;
            String line;
            String bodyLines = "";
            // theOne = new RAMLDescription();
            token = example.next();
            String bodyFromFile = "";
            line = example.nextLine();
            line = example.nextLine();
            int lineSpacesCount = lineSpaces(line);
            int baseLineSpaces = lineSpacesCount - 4;
            boolean quit = false;
            String followingLine;
                do {
                    if (lineSpacesCount >= 4+baseLineSpaces) {
                        String lineSegment = line.substring((baseLineSpaces+4),line.length());
                        bodyLines += lineSegment + "\n";
                    }
                    line = example.nextLine();
                    lineSpacesCount = lineSpaces(line);
                    if (lineSpacesCount <= baseLineSpaces) {
                        quit = true;
                    }
                    followingLine = line;
                } while (example.hasNextLine() && !quit);
            this.bodyContent = bodyLines;
            return followingLine;
        }

        String formatRAMLasHTML ( RAMLToken toFormat){
            return "RAML as Body";
        }

    void spewRAMLFile (String toSave){}

    public  String stringMe(){ return "Body:\n" + this.bodyContent;}


}
