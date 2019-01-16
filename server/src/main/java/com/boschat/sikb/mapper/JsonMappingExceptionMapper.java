package com.boschat.sikb.mapper;

import com.fasterxml.jackson.databind.JsonMappingException;

import javax.annotation.Priority;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static com.boschat.sikb.api.ResponseCode.INVALID_BODY_FIELD_FORMAT;
import static com.boschat.sikb.utils.MapperUtils.logAndBuildResponse;

@Provider
@Priority(1)
public class JsonMappingExceptionMapper implements ExceptionMapper<JsonMappingException> {

    @Override
    public Response toResponse(JsonMappingException e) {
        // all exception related to serialization or deserialization are caught here
        return logAndBuildResponse(e, INVALID_BODY_FIELD_FORMAT);
    }

}
