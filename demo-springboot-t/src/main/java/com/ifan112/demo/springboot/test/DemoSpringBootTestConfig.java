package com.ifan112.demo.springboot.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class DemoSpringBootTestConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
