/**
 * Created by Dad on 3/9/14.
 */
public class RAMLBody extends RAMLToken {
// constructor for the object.
        RAMLBody () {
        }

        RAMLToken vaccuumRAMLFIle (){

            RAMLToken junk = new RAMLBody();

            return junk;

        }

        String formatRAMLasHTML ( RAMLToken toFormat){
            return "RAML as Body";
        }

        void spewRAMLFile (String toSave){}

}
