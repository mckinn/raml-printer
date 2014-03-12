/**
 * Created by Dad on 3/11/14.
 */
public enum markupType {
    description,
    httpMethod,
    body,
    pathElement,
    applicationXML,
    schema,
    example,
    responses,
    responsesvalues,         //  [200,201...]
    queryParameters,
    queryParameterNames,    // [(a-z)*:]
    qpType,
    qpMaximim,
    qpMinimum,
    qpRequired,
    qpRepeats,
    qpDefault,
    qpEnum,
    displayName,
    otcoThorpe,
    unknown;


    public static markupType stringToMarkuptype (String inputString) {
        
        if      (inputString.equals("description:")) return description;
        else if ((inputString.equals("put:")) || (inputString.equals("get:")) || (inputString.equals("delete:")) || (inputString.equals("post:"))) return httpMethod;
        else if (inputString.equals("body:")) return body;
        else if (inputString.equals("/path:")) return pathElement;            // this is a placeholder for a more complicated match
        else if (inputString.equals("application/xml:")) return applicationXML;
        else if (inputString.equals("schema:")) return schema;
        else if (inputString.equals("example:")) return example;
        else if (inputString.equals("responses:")) return responses;
        else if (inputString.equals("200:")) return responsesvalues;         // this is a placeholder for a more complicated match
        else if (inputString.equals("queryParameters:")) return queryParameters;
        else if (inputString.equals("qpName:")) return queryParameterNames;  // this is a placeholder for a more complicated match
        else if (inputString.equals("type:")) return qpType;
        else if (inputString.equals("maximum:")) return qpMaximim;
        else if (inputString.equals("minimum:")) return qpMinimum;
        else if (inputString.equals("required:")) return qpRequired;
        else if (inputString.equals("repeats:")) return qpRepeats;
        else if (inputString.equals("default:")) return qpDefault;
        else if (inputString.equals("enum:")) return qpEnum;
        else if (inputString.equals("displayName:")) return displayName;
        else if (inputString.equals("#")) return otcoThorpe;
        else return unknown;
    }
}

