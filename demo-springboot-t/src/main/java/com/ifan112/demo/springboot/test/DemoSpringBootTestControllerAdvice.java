package com.ifan112.demo.springboot.test;

import com.ifan112.demo.springboot.test.exception.AccessForbiddenException;
import com.ifan112.demo.springboot.test.exception.ParameterInvalidException;
import com.ifan112.demo.springboot.test.exception.ServiceException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DemoSpringBootTestControllerAdvice {

    @ExceptionHandler(value = AccessForbiddenException.class)
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public CodeAndMessage accessForbidden(AccessForbiddenException e) {
        return CodeAndMessage.of(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = ParameterInvalidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public CodeAndMessage parameterInvalid(ParameterInvalidException e) {
        return CodeAndMessage.of(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = ServiceException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public CodeAndMessage genericServiceException(ServiceException e) {
        return CodeAndMessage.of(e.getCode(), e.getMessage());
    }


    private static final String DATA_ACCESS_EXCEPTION_CODE = "Data Access Error.";

    @ExceptionHandler(value = DataAccessException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public CodeAndMessage dataAccessException(DataAccessException e) {
        return CodeAndMessage.of(DATA_ACCESS_EXCEPTION_CODE, e.getMessage());
    }


    private static class CodeAndMessage {
        private String code;
        private String message;

        public CodeAndMessage(String code, String message) {
            this.code = code;
            this.message = message;
        }

        static CodeAndMessage of(String code, String message) {
            return new CodeAndMessage(code, message);
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}
