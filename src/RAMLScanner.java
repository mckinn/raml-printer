import java.io.InputStream;
import java.util.Scanner;


/**
 * Created by smckinnon on 4/25/14.
 */
public class RAMLScanner {

    Scanner scanner;
    int line;

    public RAMLScanner( Scanner source ) {
        this.scanner = source;
        this.line = 0;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public int getLine() {
        return line;
    }

    public void incrementLine() {
        this.line = this.line + 1;
    }

    public String getNextNonNullString(Boolean preserveBlankLines) {
        // get the next line to process, eliminating blank lines and comments.
        if (this.scanner.hasNextLine()) {
            String returnValue = this.scanner.nextLine();
            this.incrementLine();
            lineOfRamlText thisLine = new lineOfRamlText(returnValue);

            while (((thisLine.getFirstToken().equals("") && !preserveBlankLines)
                    || thisLine.getFirstToken().equals("#"))         // # is returned if a comment is found
                    && this.scanner.hasNextLine()) {
                returnValue = this.scanner.nextLine();
                this.incrementLine();
                thisLine = new lineOfRamlText(returnValue);
            }
//            System.out.println("                                            " + returnValue);
            return returnValue;
        } else {
            System.out.println("*** The End ***");
            return "";
        }
    }

}
