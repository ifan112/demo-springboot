package com.ifan112.demo.springboot.test;

import com.ifan112.demo.springboot.test.exception.AccessForbiddenException;
import com.ifan112.demo.springboot.test.exception.ParameterInvalidException;
import com.ifan112.demo.springboot.test.exception.ServiceException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DemoSpringBootTestControllerAdvice {

    @ExceptionHandler(value = AccessForbiddenException.class)
    public ResponseEntity<CodeAndMessage> accessForbidden(AccessForbiddenException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(CodeAndMessage.of(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(value = ParameterInvalidException.class)
    public ResponseEntity<CodeAndMessage> parameterInvalid(ParameterInvalidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CodeAndMessage.of(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(value = ServiceException.class)
    public ResponseEntity<CodeAndMessage> genericServiceException(ServiceException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CodeAndMessage.of(e.getCode(), e.getMessage()));
    }


    private static final String DATA_ACCESS_EXCEPTION_CODE = "Data Access Error.";

    @ExceptionHandler(value = DataAccessException.class)
    public ResponseEntity<CodeAndMessage> dataAccessException(DataAccessException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CodeAndMessage.of(DATA_ACCESS_EXCEPTION_CODE, e.getMessage()));
    }


    private static class CodeAndMessage {
        private String code;
        private String message;

        public CodeAndMessage(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public static CodeAndMessage of(String code, String message) {
            return new CodeAndMessage(code, message);
        }
    }

}
