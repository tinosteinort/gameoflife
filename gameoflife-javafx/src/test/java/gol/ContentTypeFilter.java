package gol;

import spark.Filter;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * The Client System has to know how to handle JNLP Files. Therefore the ContentType is needed
 */
class ContentTypeFilter implements Filter {

    @Override public void handle(final Request request, final Response response) throws Exception {
        String requestUrl = request.uri();

        final Map<String, String> mimeMapping = new HashMap<>();
        mimeMapping.put(".jnlp","application/x-java-jnlp-file");
        mimeMapping.put(".jar","application/octet-stream");

        boolean mappingFound = false;
        for(Map.Entry<String,String> entry : mimeMapping.entrySet()) {
            if(requestUrl.endsWith(entry.getKey())) {
                response.header("Content-Type",entry.getValue());
                mappingFound = true;
            }
        }

        if (!mappingFound) {
            response.header("Content-Type", "text/html");
        }
    }
}
