package com.ifan112.demo.springboot.test.controller;

import com.ifan112.demo.springboot.test.exception.AccessForbiddenException;
import com.ifan112.demo.springboot.test.exception.ParameterInvalidException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class TestController {

    static class TestRespDTO {
        private Integer id;
        private String name;

        public TestRespDTO(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("test");
    }

    @GetMapping("/test2")
    public String test2() {
        return "test2";
    }

    @GetMapping("/test3")
    public TestRespDTO test3(@RequestParam Integer num) {
        if (num < 0) {
            throw new ParameterInvalidException("Num must be positive");
        }

        return new TestRespDTO(num, "a");
    }

    @GetMapping("/test4")
    public TestRespDTO test4(@RequestParam Integer num) {
        if (num < 0) {
            throw new AccessForbiddenException("You cannot manage project of " + num);
        }

        return new TestRespDTO(num, "a");
    }

    @GetMapping("/test5")
    public TestRespDTO test5(@RequestParam Integer num) {
        if (num < 0) {
            throw new QueryTimeoutException("Query timeout");
        }

        return new TestRespDTO(num, "a");
    }

    @GetMapping("/test6")
    public TestRespDTO test6(@RequestParam Integer num) {
        if (num < 0) {
            return null;
        }

        return new TestRespDTO(num, "a");
    }

    @GetMapping("/test7")
    public List<TestRespDTO> test7(@RequestParam Integer num) {
        if (num < 0) {
            return Collections.emptyList();
        }

        return Collections.singletonList(new TestRespDTO(num, "a"));
    }



}
