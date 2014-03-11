import java.util.Scanner;

/**
 * Created by Dad on 3/9/14.
 */
public class RAMLBody extends RAMLToken {
// constructor for the object.
        RAMLBody () {
        }

        void vaccuumRAMLFIle (Scanner example){

            RAMLToken junk = new RAMLBody();


        }

        String formatRAMLasHTML ( RAMLToken toFormat){
            return "RAML as Body";
        }

    void spewRAMLFile (String toSave){}

    public  String stringMe(){ return " Body ";}

}
