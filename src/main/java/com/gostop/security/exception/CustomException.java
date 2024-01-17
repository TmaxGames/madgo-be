package com.gostop.security.exception;

public class CustomException extends RuntimeException {

    private static final long serialVersionID = 1L;
    private ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
