package com.boschat.sikb.mapper;

import com.boschat.sikb.utils.MapperUtils;

import javax.annotation.Priority;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static com.boschat.sikb.common.configuration.ResponseCode.VALIDATION_ERROR;

@Provider
@Priority(1)
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException e) {
        return MapperUtils.logAndBuildResponse(e, VALIDATION_ERROR, e.getMessage());
    }
}