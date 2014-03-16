import java.util.Scanner;

/**
 * Created by Dad on 3/9/14.
 */
public class RAMLBody extends RAMLToken {
// constructor for the object.
    public String bodyContent;
        RAMLBody () {
        }

    String vaccuumRAMLFIle(Scanner example, String currentLine) {

        String bodyLines = "";

        int lineSpacesCount = lineSpaces(currentLine);
        int baseLineSpaces = lineSpacesCount - 4;  // the ending condition
        boolean quit = false;

        do {
            if (lineSpacesCount >= 4 + baseLineSpaces) {
                String lineSegment = currentLine.substring((baseLineSpaces + 4), currentLine.length());
                bodyLines += lineSegment + "\n";
            }
            currentLine = example.nextLine();
            lineSpacesCount = lineSpaces(currentLine);
            if (lineSpacesCount <= baseLineSpaces) {
                quit = true;
            }
        } while (example.hasNextLine() && !quit);

        this.bodyContent = bodyLines;
        return currentLine;
    }

        String formatRAMLasHTML ( RAMLToken toFormat){
            return "RAML as Body";
        }

    void spewRAMLFile (String toSave){}

    public  String stringMe(){ return "Body:\n" + this.bodyContent;}


}
