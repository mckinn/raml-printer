import java.util.Scanner;
/**
 * Created by smckinnon on 3/15/14.
 */
public class lineOfRamlText {

    String firstToken;
    String restOfTheLine;
    int leadingSpaces;

    public lineOfRamlText() {
        this.firstToken = "";
        this.restOfTheLine = "";
        this.leadingSpaces = 0;
    }

    public lineOfRamlText(String inputLine) {

        this();
        if (!inputLine.equals("")) {
            int i = 0;
            while ((i<inputLine.length()) &&(inputLine.charAt(i) == ' ')) {
                i++;
            }
            this.leadingSpaces = i;
            if (i >= inputLine.length()) {
                this.firstToken = "";
                this.restOfTheLine = "";
                return;
            }
            Scanner s = new Scanner(inputLine);
            if ((i<inputLine.length()) && (inputLine.charAt(i) == '#')) {
                this.firstToken = "#";
            } else {
                this.firstToken = s.next();
            }

            if (s.hasNextLine()){
                this.restOfTheLine = s.nextLine();
            } else {
                this.restOfTheLine = "";
            }

        }
    }

    public String getFirstToken() {
        return firstToken;
    }

    public String getRestOfTheLine() {
        return restOfTheLine;
    }

    public int getLeadingSpaces() {
        return leadingSpaces;
    }
}
