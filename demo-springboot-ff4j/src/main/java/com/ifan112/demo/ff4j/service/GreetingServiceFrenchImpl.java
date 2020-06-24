package com.ifan112.demo.ff4j.service;

import org.springframework.stereotype.Component;

@Component("greeting.french")
public class GreetingServiceFrenchImpl implements GreetingService {

    @Override
    public String sayHello(String name) {
        return "Bonjour " + name;
    }
}
