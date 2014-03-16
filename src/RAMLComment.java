import java.util.Scanner;

/**
 * Created by smckinnon on 3/14/14.
 */
public class RAMLComment extends RAMLToken {

    // todo we might want to get rid of this altogether, and just put logic in to throw away lines everywhere that starts with a "#"

    private String commentBody;

    public RAMLComment() {
        commentBody = null;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    @Override
    String vaccuumRAMLFIle(Scanner example, String currentLine) {
        this.commentBody = currentLine;    // take the text to the end of the line
        return example.nextLine();               // return the entire next line, with the scanner pointer at line next + 1
    }

    @Override
    String formatRAMLasHTML(RAMLToken toFormat) {
        return null;
    }

    @Override
    void spewRAMLFile(String toSave) {

    }

    @Override
    public String stringMe() {
        return "#" + this.commentBody;
    }
}
