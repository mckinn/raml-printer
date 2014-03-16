import java.util.Formatter;
import java.util.Scanner;

/**
 * Created by Dad on 3/9/14.
 */
public class RAMLDescription extends RAMLToken {

    public boolean pipedContent;
    public String theRealDescription;

        RAMLDescription () {
        }

        String vaccuumRAMLFIle (Scanner example, String currentLine){

            // RAMLToken theOne;
            String followingWord;
            String token;
            String line;

            Scanner s = new Scanner(currentLine);
            s.next();                        // scan past the first token, which should be description:
            token = s.next();                // this token should be the pipe, or the start of the next row.

            String descriptionFromFile = "";
            if (!token.equals("|")) {
                line = s.nextLine();         // the text after the non-pipe.
                descriptionFromFile += token + line;
                this.theRealDescription = descriptionFromFile;
                this.pipedContent = false;
                return example.nextLine();    // advance the file scanner and return a line.
            } else {
                line = example.nextLine();    // get to the end of the line todo - what happens if there is no space after the pipe?
//                line = example.nextLine();
                int lineSpacesCount = lineSpaces(line);
                int baseLineSpaces = lineSpacesCount - 4;
                boolean quit = false;
                String followingLine;
                do {
                    if (lineSpacesCount == 4+baseLineSpaces) {
                        String lineSegment = line.substring(lineSpacesCount,line.length());
                        descriptionFromFile += lineSegment + "\n";
                    }
                    line = example.nextLine();
                    lineSpacesCount = lineSpaces(line);
                    if (lineSpacesCount <= baseLineSpaces) {
                        quit = true;
                    }
                    followingLine = line;
                } while (example.hasNextLine() && !quit);
                //token = line.substring(lineSpacesCount,line.length());
                this.theRealDescription = descriptionFromFile;
                this.pipedContent = false;
                /*int endFirstWord = lineSpacesCount;
                while ((endFirstWord < followingLine.length()) && (followingLine.charAt(endFirstWord) != ' ')) {
                    endFirstWord++;
                }
                followingWord = followingLine.substring(lineSpacesCount,endFirstWord);*/
                return followingLine;
            }

            // return junk;
        }

        String formatRAMLasHTML ( RAMLToken toFormat){
            return "RAML Description as HTML";
        }

    void spewRAMLFile (String toSave){}

    public  String stringMe(){
        return "Description: " + theRealDescription;
    }
}
