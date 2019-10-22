package com.linkallcloud.um.exception;

import com.linkallcloud.core.exception.BizException;

public class AuthException extends BizException {
    public AuthException() {
    }

    public AuthException(String code) {
        super(code);
    }

    public AuthException(String code, String message) {
        super(code, message);
    }

    public AuthException(String code, Object[] args) {
        super(code, args);
    }

    public AuthException(String code, String msgFormat, Object[] args) {
        super(code, msgFormat, args);
    }

    public AuthException(String code, Throwable cause) {
        super(code, cause);
    }

    public AuthException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }
}