import java.util.Formatter;
import java.util.Scanner;

/**
 * Created by Dad on 3/9/14.
 */
public class RAMLDescription extends RAMLToken {

    public boolean pipedContent;
    public String theRealDescription;
    public String followingWord;

        RAMLDescription () {
        }

        String vaccuumRAMLFIle (Scanner example){

            // RAMLToken theOne;
            String token;
            String line;
            // theOne = new RAMLDescription();
            token = example.next();
            String descriptionFromFile = "";
            if (!token.equals("|")) {
                line = example.nextLine();
                descriptionFromFile += token + line;
                this.theRealDescription = descriptionFromFile;
                this.pipedContent = true;
                String returnWord = "";
                return returnWord;
                //token = example.next();
            } else {
                line = example.nextLine();
                line = example.nextLine();
                int lineSpacesCount = lineSpaces(line, example);
                int baseLineSpaces = lineSpacesCount - 4;
                boolean quit = false;
                String followingLine;
                do {
                    if (lineSpacesCount == 4+baseLineSpaces) {
                        String lineSegment = line.substring(lineSpacesCount,line.length());
                        descriptionFromFile += lineSegment;
                    }
                    line = example.nextLine();
                    lineSpacesCount = lineSpaces(line, example);
                    if (lineSpacesCount <= baseLineSpaces) {
                        quit = true;
                    }
                    followingLine = line;
                } while (example.hasNextLine() && !quit);
                //token = line.substring(lineSpacesCount,line.length());
                this.theRealDescription = descriptionFromFile;
                this.pipedContent = false;
                int endFirstWord = lineSpacesCount;
                while ((endFirstWord < followingLine.length()) && (followingLine.charAt(endFirstWord) != ' ')) {
                    endFirstWord++;
                }
                this.followingWord = followingLine.substring(lineSpacesCount,endFirstWord);
                return followingWord;
            }

            // return junk;
        }

        String formatRAMLasHTML ( RAMLToken toFormat){
            return "RAML Description as HTML";
        }

    void spewRAMLFile (String toSave){}

    public  String stringMe(){
        return "Piped Content: " + pipedContent + " \n " + theRealDescription;
    }
}
