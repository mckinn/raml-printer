/**
 * Created by Dad on 3/9/14.
 */
public class RAMLDescription extends RAMLToken {

    public boolean pipedContent;
    public String theRealDescription;

        RAMLDescription () {
        }

        RAMLToken vaccuumRAMLFIle (){

            RAMLToken junk = new RAMLHTTPMethod();

            return junk;
        }

        String formatRAMLasHTML ( RAMLToken toFormat){
            return "RAML Description as HTML";
        }

        void spewRAMLFile (String toSave){}
}
