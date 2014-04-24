import java.util.ArrayList;

/**
 * Created by smckinnon on 3/29/14.
 */
public class RAMLTokenList extends ArrayList <RAMLToken> {

    ArrayList<RAMLToken> documentComponents;

    public RAMLTokenList() {
        this.documentComponents = new ArrayList<RAMLToken>();
    }

    public ArrayList<RAMLToken> getDocumentComponents() {
        return documentComponents;
    }

    public void setDocumentComponents(ArrayList<RAMLToken> documentComponents) {
        this.documentComponents = documentComponents;
    }

    public  RAMLToken findMarkupType ( markupType mut) {

        for (RAMLToken rt : this) {
            if (rt.getMarkupType() == mut){
                return rt;
            }
        }
        return null;   // failed to find it.
    }
}
