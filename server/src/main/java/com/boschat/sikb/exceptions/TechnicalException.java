package com.boschat.sikb.exceptions;

import com.boschat.sikb.api.ResponseCode;

public class TechnicalException extends RuntimeException {
    private ResponseCode errorCode;


    public TechnicalException(ResponseCode errorCode, Object... paramsMessage) {
        super(String.format(errorCode.getErrorMessage(), paramsMessage));
    }

    public TechnicalException(ResponseCode errorCode, Throwable cause, Object... paramsMessage) {
        super(String.format(errorCode.getErrorMessage(), paramsMessage), cause);
    }

    public ResponseCode getErrorCode() {
        return errorCode;
    }
}
