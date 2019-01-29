package com.boschat.sikb.common.exceptions;

import com.boschat.sikb.common.configuration.ResponseCode;

public class FunctionalException extends RuntimeException {

    private ResponseCode errorCode;

    public FunctionalException(ResponseCode errorCode, Object... paramsMessage) {
        super(String.format(errorCode.getErrorMessage(), paramsMessage));
        this.errorCode = errorCode;
    }

    public FunctionalException(ResponseCode errorCode, Throwable cause, Object... paramsMessage) {
        super(String.format(errorCode.getErrorMessage(), paramsMessage), cause);
        this.errorCode = errorCode;
    }

    public ResponseCode getErrorCode() {
        return errorCode;
    }
}
