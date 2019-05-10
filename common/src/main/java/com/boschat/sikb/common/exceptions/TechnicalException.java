package com.boschat.sikb.common.exceptions;

import com.boschat.sikb.common.configuration.ResponseCode;

public class TechnicalException extends RuntimeException {

    private ResponseCode errorCode;

    private Object[] paramsMessage;

    public TechnicalException(ResponseCode errorCode, Object... paramsMessage) {
        super(String.format(errorCode.getErrorMessage(), paramsMessage));
        this.errorCode = errorCode;
        this.paramsMessage = paramsMessage;
    }

    public TechnicalException(ResponseCode errorCode, Throwable cause, Object... paramsMessage) {
        super(String.format(errorCode.getErrorMessage(), paramsMessage), cause);
        this.errorCode = errorCode;
        this.paramsMessage = paramsMessage;
    }

    public ResponseCode getErrorCode() {
        return errorCode;
    }

    public Object[] getParamsMessage() {
        return paramsMessage;
    }
}
