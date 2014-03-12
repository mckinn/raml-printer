import java.util.Scanner;

/**
 * Created by Dad on 3/9/14.
 */
public class RAMLHTTPMethod extends RAMLToken{

    RAMLToken [] subtendingThings;
    String methodName;
    int numberOfThings;
    int maxNumberOfThings;

    RAMLHTTPMethod (String typeOfMethod, int max) {
        methodName = typeOfMethod;
        maxNumberOfThings = max;
        numberOfThings = 0;
        subtendingThings = new RAMLToken[max];

    }

    String vaccuumRAMLFIle (Scanner example){

        // while the number of spaces is current-number + 4
        //     look for a description or a body
        //     call the appropriate vaccuum for the type
        //     place the resulting object in the array
        //
        String returnWord = "";
        return returnWord;
    }

    String formatRAMLasHTML ( RAMLToken toFormat){
        return "RAML as HTML";
    }

    void spewRAMLFile (String toSave){}

    public  String stringMe(){ return " HTTP Method ";}
}
