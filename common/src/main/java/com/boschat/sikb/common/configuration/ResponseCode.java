package com.boschat.sikb.common.configuration;

import org.apache.logging.log4j.Level;

import static org.apache.logging.log4j.Level.ERROR;
import static org.apache.logging.log4j.Level.INFO;

public enum ResponseCode {
    OK(INFO, 200),
    CREATED(INFO, 201),
    NO_CONTENT(INFO, 204),

    // 4xx code indicates an error caused by the user
    VALIDATION_ERROR(ERROR, 400, 0, "The request contains validation errors : %s"),

    MISSING_BODY_FIELD(ERROR, 400, 1, "The body field %s is missing"),
    INVALID_BODY_FIELD(ERROR, 400, 2, "The body field value %s is invalid : %s"),
    INVALID_BODY_FIELD_WITHOUT_VALUE(ERROR, 400, 3, "The field value %s is invalid"),
    MISSING_HEADER(ERROR, 400, 4, "The header %s is missing"),
    INVALID_HEADER_VALUE(ERROR, 400, 5, "The header value %s is invalid  : %s"),
    MISSING_QUERY_STRING_PARAMETER(ERROR, 400, 6, "The query string %s is missing"),
    INVALID_QUERY_STRING_PARAMETER(ERROR, 400, 7, "The query string value %s is invalid : %s"),
    INVALID_BODY(INFO, 400, 8, "The body is invalid"),
    INVALID_BODY_FIELD_FORMAT(INFO, 400, 9, "Body Field format error : %s"),
    WRONG_LOGIN_OR_PASSWORD(INFO, 400, 10, "Wrong login/password"),
    WRONG_OLD_PASSWORD(INFO, 400, 11, "Wrong old Password"),
    NEW_PASSWORD_CANNOT_BE_SAME(INFO, 400, 12, "New and old passwords cannot be the same"),
    SEASON_ALREADY_EXISTS(INFO, 400, 13, "The season %s Already Exists"),
    FILE_EXTENSION_NOT_SUPPORTED(INFO, 400, 14, "This file (%s) extension is not supported"),
    FILE_EXTENSION_NOT_AUTHORIZED(INFO, 400, 15, "This content type (%s) of file (%s) is not Authorized"),
    TRANSITION_FORBIDDEN(INFO, 400, 16, "Transition \"%s\" to \"%s\" forbidden"),
    
    UNAUTHORIZED(INFO, 401, 1, "Unauthorized"),
    NOT_ENOUGH_RIGHT(INFO, 401, 2, "Not enough rights"),
    
    SERVICE_NOT_FOUND(ERROR, 404, 0, "Service not found"),
    CLUB_NOT_FOUND(INFO, 404, 1, "Club (Id %s) not found"),
    AFFILIATION_NOT_FOUND(INFO, 404, 2, "Affiliation (clubId %s, season %s) not found"),
    USER_NOT_FOUND(INFO, 404, 3, "User (%s) not found"),
    CONFIRM_TOKEN_NOT_FOUND(INFO, 404, 4, "Confirm token not found"),
    CONFIRM_TOKEN_EXPIRED(INFO, 404, 5, "Confirm token is no longer available"),
    PERSON_NOT_FOUND(INFO, 404, 6, "Person (%s) not found"),
    SEASON_NOT_FOUND(INFO, 404, 7, "Season (%s) not found"),
    LICENCE_NOT_FOUND(INFO, 404, 8, "Licence (%s) not found"),
    DOCUMENT_TYPE_NOT_FOUND(INFO, 404, 9, "Document Type (%s) not found"),
    MEDICAL_CERTIFICATE_NOT_FOUND(INFO, 404, 10, "Medical Certificate (%s) not found"),
    PHOTO_NOT_FOUND(INFO, 404, 11, "Photo (%s) not found"),
    TEAM_NOT_FOUND(INFO, 404, 12, "Team (%s) not found"),
    LOGO_NOT_FOUND(INFO, 404, 13, "Logo (%s) not found"),



    METHOD_NOT_ALLOWED(ERROR, 405, 0, "Method Not Allowed"),

    // 5xx codes tell the client that they did everything correctly and it’s the server itself who caused the problem
    INTERNAL_ERROR(ERROR, 500, 1, "Internal Error : %s"),
    CONFIG_TECH_LOADING_ERROR(ERROR, 500, 2, "Error loading technical configuration : %s"),
    EMAIL_ERROR(ERROR, 500, 3, "Error with emailing"),
    DATABASE_ERROR(ERROR, 500, 4, "Database Error : %s"),
    JSON_PARSE_ERROR(ERROR, 500, 5, "Json Parse Error"),
    JASPER_TEMPLATE_ERROR(ERROR, 500, 6, "Jasper template %s has producing an error"),
    EXPORT_PDF_ERROR(ERROR, 500, 7, "Error while exporting PDF");

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
