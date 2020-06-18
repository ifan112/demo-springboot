package com.ifan112.demo.springboot.test;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Collection;
import java.util.Collections;

@RestControllerAdvice
public class DemoSpringBootTestResponseBodyAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  MediaType selectedContentType, Class selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {

        if (null == body) {
            response.setStatusCode(HttpStatus.NO_CONTENT);
        } else {
            if (body instanceof Collection && ((Collection<?>) body).isEmpty()) {
                response.setStatusCode(HttpStatus.NO_CONTENT);
            }
        }

        return body;
    }
}
