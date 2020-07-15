package com.ifan112.demo.springboot.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class TestController {

    // @ModelAttribute("name")
    // public String name(@PathVariable(name = "num") Integer num) {
    //     return num + " - name";
    // }

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/test")
    public String test(@RequestParam int num) {
        if (num < 0) {
            throw new IllegalArgumentException("Argument 'num' cannot not be negative. Current value: " + num);
        }

        return "test";
    }

    @GetMapping("/test2/{num}")
    public String test2(@ModelAttribute("name") String name) {
        System.out.println(name);

        return "test2";
    }

    @GetMapping("/test3/{num}")
    public String test3(String name) {
        System.out.println(name);

        return "test3";
    }


    @GetMapping("/test4")
    public P test4() {
        P p = new P();
        p.id = 1;
        p.message = "This is p";

        return p;
    }

    static class P {
        public Integer id;
        public String message;
    }

    @GetMapping("/test5")
    public String test5() {
        ResponseEntity<String> respEntity = restTemplate.getForEntity("http://www.baidu.com", String.class);
        System.out.println(respEntity.getStatusCode());

        System.out.println(respEntity.getBody());

        return "ok";
    }
}
