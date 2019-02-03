package com.boschat.sikb.mapper;

import com.boschat.sikb.common.exceptions.FunctionalException;
import com.boschat.sikb.common.exceptions.TechnicalException;

import javax.annotation.Priority;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static com.boschat.sikb.common.configuration.ResponseCode.INTERNAL_ERROR;
import static com.boschat.sikb.common.configuration.ResponseCode.METHOD_NOT_ALLOWED;
import static com.boschat.sikb.common.configuration.ResponseCode.SERVICE_NOT_FOUND;
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
        } else if (e instanceof FunctionalException) {
            return logAndBuildResponse(e, ((FunctionalException) e).getErrorCode());
        } else if (e instanceof TechnicalException) {
            return logAndBuildResponse(e, ((TechnicalException) e).getErrorCode());
        } else {
            return logAndBuildResponse(e, INTERNAL_ERROR, e.getClass().getName());
        }
    }
}
