package com.ifan112.demo.session;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableRedisHttpSession(redisNamespace = "demo-springboot-session")
public class DemoSessionApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoSessionApplication.class, args);
    }
}
