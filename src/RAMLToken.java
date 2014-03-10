/**
 * Created by Dad on 3/9/14.
 */
abstract class RAMLToken {

    abstract RAMLToken vaccuumRAMLFIle ();

    abstract String formatRAMLasHTML ( RAMLToken toFormat);

    abstract void spewRAMLFile (String toSave);
 // todo: 1) read in a file and create and populate a single RAMLToken object of any type whatsoever
 // todo: 2) create the main class that reads in an array of RAMLTokens of different types.
    }

