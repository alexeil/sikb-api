package com.boschat.sikb.persistence.converter;

import com.boschat.sikb.common.exceptions.TechnicalException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.NullNode;
import org.jooq.Converter;

import java.io.IOException;

import static com.boschat.sikb.common.configuration.ResponseCode.JSON_PARSE_ERROR;

public class PostgresJSONJacksonJsonNodeConverter implements Converter<Object, JsonNode> {

    @Override
    public JsonNode from(Object t) {
        try {
            return t == null
                ? NullNode.instance
                : new ObjectMapper().readTree(t + "");
        } catch (IOException e) {
            throw new TechnicalException(JSON_PARSE_ERROR, e);
        }
    }

    @Override
    public Object to(JsonNode u) {
        try {
            return u == null || u.equals(NullNode.instance)
                ? null
                : new ObjectMapper().writeValueAsString(u);
        } catch (IOException e) {
            throw new TechnicalException(JSON_PARSE_ERROR, e);
        }
    }

    @Override
    public Class<Object> fromType() {
        return Object.class;
    }

    @Override
    public Class<JsonNode> toType() {
        return JsonNode.class;
    }
}