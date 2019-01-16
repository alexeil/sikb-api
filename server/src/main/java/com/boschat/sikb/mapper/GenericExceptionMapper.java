package com.boschat.sikb.mapper;

import javax.annotation.Priority;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static com.boschat.sikb.api.ResponseCode.INTERNAL_ERROR;
import static com.boschat.sikb.api.ResponseCode.METHOD_NOT_ALLOWED;
import static com.boschat.sikb.api.ResponseCode.SERVICE_NOT_FOUND;
import static com.boschat.sikb.utils.MapperUtils.logAndBuildResponse;

@Provider
@Priority(1)
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable e) {

        if (e instanceof NotAllowedException) {
            return logAndBuildResponse(e, METHOD_NOT_ALLOWED);
        } else if (e instanceof NotFoundException) {
            return logAndBuildResponse(e, SERVICE_NOT_FOUND);
        }
        // } else if (e instanceof CircuitBreakingException) {
        //    return MapperUtils.buildResponse(CIRCUIT_BREAKER_OPEN);
        // }
        else {
            return logAndBuildResponse(e, INTERNAL_ERROR, e.getClass().getName());
        }
    }
}
