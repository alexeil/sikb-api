package com.boschat.sikb.mapper;

import com.boschat.sikb.utils.MapperUtils;
import com.fasterxml.jackson.core.JsonParseException;

import javax.annotation.Priority;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static com.boschat.sikb.api.ResponseCode.INVALID_BODY;

@Provider
@Priority(1)
public class JsonParseExceptionMapper implements ExceptionMapper<JsonParseException> {

    public Response toResponse(JsonParseException e) {
        // all exception related to serialization or deserialization are caught here
        return MapperUtils.logAndBuildResponse(e, INVALID_BODY);
    }
}
