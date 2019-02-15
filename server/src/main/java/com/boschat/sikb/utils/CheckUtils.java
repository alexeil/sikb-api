package com.boschat.sikb.utils;

import com.boschat.sikb.common.configuration.ResponseCode;
import com.boschat.sikb.common.exceptions.FunctionalException;
import org.apache.logging.log4j.util.Strings;

import static com.boschat.sikb.common.configuration.ResponseCode.INVALID_BODY_FIELD;
import static com.boschat.sikb.common.configuration.ResponseCode.INVALID_HEADER_VALUE;
import static com.boschat.sikb.common.configuration.ResponseCode.INVALID_QUERY_STRING_PARAMETER;
import static com.boschat.sikb.common.configuration.ResponseCode.MISSING_BODY_FIELD;
import static com.boschat.sikb.common.configuration.ResponseCode.MISSING_HEADER;
import static com.boschat.sikb.common.configuration.ResponseCode.MISSING_QUERY_STRING_PARAMETER;

public class CheckUtils {

    private CheckUtils() {

    }

    private static <T> T checkRequest(T param, String paramName, ResponseCode missingError) {
        if (param == null) {
            throw new FunctionalException(missingError, paramName);
        }
        return param;
    }

    private static String checkRequest(String param, String paramName, ResponseCode missingError, ResponseCode invalidError, String validPattern) {
        if (param == null) {
            throw new FunctionalException(missingError, paramName);
        }
        return checkValidity(param, paramName, invalidError, validPattern);
    }

    private static String checkValidity(String param, String paramName, ResponseCode invalidError, String validPattern) {
        if (param != null && Strings.isNotEmpty(validPattern) && !param.matches(validPattern)) {
            throw new FunctionalException(invalidError, paramName, param);
        }
        return param;
    }

    public static String checkRequestHeader(String header, String paramName, String validPattern) {
        return checkRequest(header, paramName, MISSING_HEADER, INVALID_HEADER_VALUE, validPattern);
    }

    public static String checkRequestQueryStringParam(String queryString, String paramName, String validPattern) {
        return checkRequest(queryString, paramName, MISSING_QUERY_STRING_PARAMETER, INVALID_QUERY_STRING_PARAMETER, validPattern);
    }

    public static String checkRequestBodyField(String bodyField, String paramName, String validPattern) {
        return checkRequest(bodyField, paramName, MISSING_BODY_FIELD, INVALID_BODY_FIELD, validPattern);
    }

    public static <T> T checkRequestBodyField(T bodyField, String paramName) {
        return checkRequest(bodyField, paramName, MISSING_BODY_FIELD);
    }

}
