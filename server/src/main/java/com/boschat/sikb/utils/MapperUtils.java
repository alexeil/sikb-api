package com.boschat.sikb.utils;

import com.boschat.sikb.api.ResponseCode;
import com.boschat.sikb.model.ZError;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.core.Response;

public class MapperUtils {

    private static final Logger LOGGER = LogManager.getLogger(MapperUtils.class);

    private MapperUtils() {

    }

    public static Response logAndBuildResponse(Throwable e, ResponseCode error, Object... params) {
        LOGGER.log(error.getLevel(), error.getErrorMessage(), e);
        return buildResponse(error, params);
    }

    public static Response buildResponse(ResponseCode error, Object... params) {
        return Response.status(error.getCodeHttp()).entity(buildErrorResponse(error, params)).build();
    }

    private static ZError buildErrorResponse(ResponseCode code, Object... paramsMessage) {
        ZError error = new ZError();
        error.setCode(code.getCode());
        error.setMessage(String.format(code.getErrorMessage(), paramsMessage));
        return error;
    }
}
