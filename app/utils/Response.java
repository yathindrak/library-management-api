package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;

/**
 * This class is for handle the response
 */
public class Response {
    /**
     * Generate a response
     * @param res - response
     * @param  ok - is the task succeed
     * @return response as ObjectNode
     */
    public static ObjectNode generateResponse(Object res, boolean ok) {
        // result obj
        ObjectNode result = Json.newObject();
        result.put("isOk", ok);
        if(res instanceof String){
            result.put("body", (String) res);
        }
        else{
            result.set("body", (JsonNode) res);
        }
        return result;
    }
}
