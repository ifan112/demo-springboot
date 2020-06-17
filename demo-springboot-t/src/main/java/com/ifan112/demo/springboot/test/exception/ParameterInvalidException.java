package com.ifan112.demo.springboot.test.exception;

public class ParameterInvalidException extends ServiceException {

    private static final String PARAMETER_INVALID_EXCEPTION_CODE = "Parameter Invalid";

    public ParameterInvalidException(String message) {
        super(PARAMETER_INVALID_EXCEPTION_CODE, message);
    }

    public ParameterInvalidException(String code, String message) {
        super(code, message);
    }

}
