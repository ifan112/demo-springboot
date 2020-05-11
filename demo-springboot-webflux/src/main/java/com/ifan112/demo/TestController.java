package com.ifan112.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class TestController {

    @Autowired
    private Environment environment;

    @GetMapping("/test")
    public String test() {
        String value = environment.getProperty("oms.serverIp");
        System.out.println(value);

        return "Test";
    }

    @GetMapping("/mono")
    public Mono<String> timeHandler() {
        return Mono.just("Hello World");
    }
}
