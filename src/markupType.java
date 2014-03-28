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
    qpRepeat,
    qpDefault,
    qpEnum,
    displayName,
    otcoThorpe,
    unknown;

    // todo make these methods executable on markupType objects.  I suspect that it involves removing the "static" designation.

    public static markupType stringToMarkuptype (String inputString) {
        
        if      (inputString.equals("description:")) return description;
        else if ((inputString.equals("put:"))
                || (inputString.equals("get:"))
                || (inputString.equals("delete:"))
                || (inputString.equals("post:"))) return httpMethod;
        else if (inputString.equals("body:")) return body;
        else if (inputString.equals("application/xml:")) return applicationXML;
        else if (inputString.equals("schema:")) return schema;
        else if (inputString.equals("example:")) return example;
        else if (inputString.equals("responses:")) return responses;
        else if (inputString.equals("queryParameters:")) return queryParameters;
        else if (inputString.equals("type:")) return qpType;
        else if (inputString.equals("maximum:")) return qpMaximim;
        else if (inputString.equals("minimum:")) return qpMinimum;
        else if (inputString.equals("required:")) return qpRequired;
        else if (inputString.equals("repeat:")) return qpRepeat;
        else if (inputString.equals("default:")) return qpDefault;
        else if (inputString.equals("enum:")) return qpEnum;
        else if (inputString.equals("displayName:")) return displayName;
        // check for the patterns in decreasing levels of specificity
        else if (!RAMLPathElement.checkPathElementPattern(inputString).equals(""))  return pathElement;
        else if (!RAMLResponsesValues.checkResponseCodePattern(inputString).equals(""))  return responsesvalues;
        else if (!RAMLQueryParameterToken.checkQueryParameterNamePattern(inputString).equals(""))  return queryParameterNames;

        else if (inputString.equals("#")) return otcoThorpe;
        else return unknown;
    }

    public static String markupTypeToString ( markupType mt ) {
        switch (mt) {
            case description: return "description:" ;
            case httpMethod: return "description:" ;
            case body: return "description:" ;
            case pathElement: return "path/:" ;
            case applicationXML: return "application/xml::" ;
            case schema: return "schema:" ;
            case example: return "example:" ;
            case responses: return "responses:" ;
            case responsesvalues: return "HTTP Responses [nnn]:" ;
            case queryParameters: return "queryParameters:" ;
            case queryParameterNames: return "qp names:" ;
            case qpType: return "type:" ;
            case qpMaximim: return "maximum:" ;
            case qpMinimum: return "minimum:" ;
            case qpRequired: return "required:" ;
            case qpRepeat: return "repeat:" ;
            case qpDefault: return "default:" ;
            case qpEnum: return "enum:" ;
            case displayName: return "displayName:" ;
            case otcoThorpe: return "# " ;
            case unknown: return "*** UNKNOWN ***:" ;
        }
        return "*** UNKNOWN ***";
    }
}

