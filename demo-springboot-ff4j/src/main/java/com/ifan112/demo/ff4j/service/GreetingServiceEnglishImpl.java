package com.ifan112.demo.ff4j.service;

import org.springframework.stereotype.Component;

@Component("greeting.english")
public class GreetingServiceEnglishImpl implements GreetingService {

    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
