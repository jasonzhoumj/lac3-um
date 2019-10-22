package com.linkallcloud.um.exception;

public class AppException extends UmException {
    public AppException() {
    }

    public AppException(String code) {
        super(code);
    }

    public AppException(String code, String message) {
        super(code, message);
    }

    public AppException(String code, Object[] args) {
        super(code, args);
    }

    public AppException(String code, String msgFormat, Object[] args) {
        super(code, msgFormat, args);
    }

    public AppException(String code, Throwable cause) {
        super(code, cause);
    }

    public AppException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
