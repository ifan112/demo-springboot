package com.ifan112.demo.springboot.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DemoControllerAdvice {

    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public CodeAndMessage illegalArgumentException(IllegalArgumentException e) {
        return CodeAndMessage.of("Illegal Argument", e.getMessage());
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
