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
    headers,                // different string value, but behaviorally the same as queryParameters
    queryParameterNames,    // [(a-z)*:]
    qpType,
    qpMaximim,
    qpMinimum,
    qpRequired,
    qpRepeat,
    qpDefault,
    qpEnum,
    displayName,
    title,
    baseUri,
    version,
    securedBy,
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
        // treat headers the same way that we treat queryParameters
        else if (inputString.equals("headers:")) return headers;
        else if (inputString.equals("type:")) return qpType;
        else if (inputString.equals("maximum:")) return qpMaximim;
        else if (inputString.equals("minimum:")) return qpMinimum;
        else if (inputString.equals("required:")) return qpRequired;
        else if (inputString.equals("repeat:")) return qpRepeat;
        else if (inputString.equals("default:")) return qpDefault;
        else if (inputString.equals("enum:")) return qpEnum;
        else if (inputString.equals("displayName:")) return displayName;
        else if (inputString.equals("title:")) return title;
        else if (inputString.equals("baseUri:")) return baseUri;
        else if (inputString.equals("version:")) return version;
        else if (inputString.equals("securedBy:")) return securedBy;
        // check for the patterns in decreasing levels of specificity
        else if (!RAMLPathElement.checkPathElementPattern(inputString).equals(""))  return pathElement;
        else if (!RAMLResponsesValues.checkResponseCodePattern(inputString).equals(""))  return responsesvalues;
        else if (!RAMLQueryParameterToken.checkQueryParameterNamePattern(inputString).equals(""))  return queryParameterNames;

        else return unknown;
    }

    public static String markupTypeToString ( markupType mt ) {
        switch (mt) {
            case description: return "description:" ;
            case httpMethod: return "get,  post, put, delete:" ;
            case body: return "body:" ;
            case pathElement: return "path/:" ;
            case applicationXML: return "application/xml:" ;
            case schema: return "schema:" ;
            case example: return "example:" ;
            case responses: return "responses:" ;
            case responsesvalues: return "HTTP Responses [nnn]:" ;
            case queryParameters: return "queryParameters:" ;
            case headers: return "headers:";
            case queryParameterNames: return "qp names:" ;
            case qpType: return "type:" ;
            case qpMaximim: return "maximum:" ;
            case qpMinimum: return "minimum:" ;
            case qpRequired: return "required:" ;
            case qpRepeat: return "repeat:" ;
            case qpDefault: return "default:" ;
            case qpEnum: return "enum:" ;
            case displayName: return "displayName:" ;
            case title: return "title:" ;
            case baseUri: return "baseUri:" ;
            case version: return "version:" ;
            case securedBy: return "securedBy:" ;

            case unknown: return "*** UNKNOWN ***:" ;
        }
        return "*** UNKNOWN ***";
    }

    public  String CSSClass ( ) {
        switch (this) {
            case description: return "descriptionCSS" ;
            case httpMethod: return "httpMethodCSS" ;
            case body: return "bodyCSS" ;
            case pathElement: return "pathCSS" ;
            case applicationXML: return "appxmlCSS" ;
            case schema: return "schemaCSS" ;
            case example: return "exampleCSS" ;
            case responses: return "responsesCSS" ;
            case responsesvalues: return "responseCodesCSS" ;
            case queryParameters: return "queryParametersCSS" ;
            case queryParameterNames: return "qpNamesCSS" ;
            case headers: return "qpNamesCSS" ;
            case qpType: return "qpCSS" ;
            case qpMaximim: return "qpCSS" ;
            case qpMinimum: return "qpCSS" ;
            case qpRequired: return "qpCSS" ;
            case qpRepeat: return "qpCSS" ;
            case qpDefault: return "qpCSS" ;
            case qpEnum: return "qpCSS" ;
            case displayName: return "displayNameCSS" ;
            case title: return "titleCSS" ;
            case baseUri: return "baseUriCSS" ;
            case version: return "versionCSS" ;
            case securedBy: return "securedByCSS" ;

            case unknown: return "noCSS" ;
        }
        return "noCSS";
    }
}

