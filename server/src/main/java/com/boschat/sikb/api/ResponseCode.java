package com.boschat.sikb.api;

import org.apache.logging.log4j.Level;

import static org.apache.logging.log4j.Level.ERROR;
import static org.apache.logging.log4j.Level.INFO;

public enum ResponseCode {
    OK(INFO, 200),
    CREATED(INFO, 201),
    NO_CONTENT(INFO, 204),

    // 4xx code indicates an error caused by the user
    MISSING_BODY_FIELD(ERROR, 400, 1, "The body field %s is absent"),
    INVALID_BODY_FIELD(ERROR, 400, 2, "The body field value %s is invalid : %s"),
    INVALID_BODY_FIELD_WITHOUT_VALUE(ERROR, 400, 3, "The field value %s is invalid"),
    MISSING_HEADER(ERROR, 400, 4, "The header %s is missing"),
    INVALID_HEADER_VALUE(ERROR, 400, 5, "The header value %s is invalid  : %s"),
    MISSING_QUERY_STRING_PARAMETER(ERROR, 400, 6, "The query string %s is absent"),
    INVALID_QUERY_STRING_PARAMETER(ERROR, 400, 7, "The query string value %s is invalid : %s"),
    INVALID_BODY(INFO, 400, 8, "The body is invalid"),
    INVALID_BODY_FIELD_FORMAT(INFO, 400, 9, "Body Field format error"),
    WRONG_LOGIN_OR_PASSWORD(INFO, 400, 10, "Wrong login/password"),

    UNAUTHORIZED(INFO, 401, 1, "Unauthorized"),
    
    SERVICE_NOT_FOUND(ERROR, 404, 0, "Service not found"),
    CLUB_NOT_FOUND(INFO, 404, 1, "Club (Id %s) not found"),
    AFFILIATION_NOT_FOUND(INFO, 404, 2, "Affiliation (clubId %s, season %s) not found"),
    USER_NOT_FOUND(INFO, 404, 3, "User (id %s) not found"),
    CONFIRM_TOKEN_NOT_FOUND(INFO, 404, 4, "Confirm token not found"),
    CONFIRM_TOKEN_EXPIRED(INFO, 404, 5, "Confirm token is no longer available"),
    
    METHOD_NOT_ALLOWED(ERROR, 405, 0, "Method Not Allowed"),

    // 5xx codes tell the client that they did everything correctly and it’s the server itself who caused the problem
    INTERNAL_ERROR(ERROR, 500, 1, "Internal Error : %s"),
    CONFIG_TECH_LOADING_ERROR(ERROR, 500, 2, "Error loading technical configuration : %s"),
    EMAIL_ERROR(ERROR, 500, 3, "Error with emailing");

    /**
     * http code returned
     */
    private Short codeHttp;

    /**
     * body code returned
     */
    private int code;

    /**
     * Level of the log  message
     */
    private Level level;

    /**
     * body message returned
     */
    private String errorMessage;

    ResponseCode(Level level, int codeHttp) {
        this.codeHttp = (short) codeHttp;
        this.level = level;
    }

    ResponseCode(Level level, int codeHttp, int code, String message) {
        this.codeHttp = (short) codeHttp;
        this.code = code;
        this.errorMessage = message;
        this.level = level;
    }

    public Short getCodeHttp() {
        return codeHttp;
    }

    public int getCode() {
        return code;
    }

    public Level getLevel() {
        return level;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
