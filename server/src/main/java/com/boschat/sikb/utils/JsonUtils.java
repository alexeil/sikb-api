package com.boschat.sikb.utils;

import com.boschat.sikb.common.exceptions.TechnicalException;
import com.boschat.sikb.model.Formation;
import com.boschat.sikb.servlet.JacksonJsonProvider;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.boschat.sikb.common.configuration.ResponseCode.JSON_PARSE_ERROR;

public class JsonUtils {

    private JsonUtils() {

    }

    public static JsonNode formationsToJsonNode(List<Formation> formations) {
        return objectToJsonNode(formations);
    }

    public static List<Formation> formationsToJsonNode(JsonNode formations) {
        return Arrays.asList(jsonNodeToObject(formations, Formation[].class));
    }

    private static <T> JsonNode objectToJsonNode(T object) {
        try {
            return JacksonJsonProvider.getMapper().readTree(JacksonJsonProvider.getMapper().writeValueAsString(object));
        } catch (IOException e) {
            throw new TechnicalException(JSON_PARSE_ERROR, e);
        }
    }

    private static <T> T jsonNodeToObject(JsonNode jsonNode, Class<T> clazz) {
        try {
            return JacksonJsonProvider.getMapper().readValue(jsonNode.toString(), clazz);
        } catch (IOException e) {
            throw new TechnicalException(JSON_PARSE_ERROR, e);
        }
    }

}
