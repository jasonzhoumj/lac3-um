package com.linkallcloud.um.exception;

import com.linkallcloud.core.exception.BizException;

public class ArgException extends BizException {
    public ArgException() {
    }

    public ArgException(String code) {
        super(code);
    }

    public ArgException(String code, String message) {
        super(code, message);
    }

    public ArgException(String code, Object[] args) {
        super(code, args);
    }

    public ArgException(String code, String msgFormat, Object[] args) {
        super(code, msgFormat, args);
    }

    public ArgException(String code, Throwable cause) {
        super(code, cause);
    }

    public ArgException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
