package com.ifan112.demo.springboot.test.exception;

public class AccessForbiddenException extends ServiceException {

    private static final String ACCESS_Forbidden_EXCEPTION_CODe = "Access Forbidden";

    public AccessForbiddenException(String message) {
        super(ACCESS_Forbidden_EXCEPTION_CODe, message);
    }

    public AccessForbiddenException(String code, String message) {
        super(code, message);
    }
}
