package com.ifan112.demo.springboot.nacos;

import com.alibaba.nacos.spring.context.annotation.EnableNacos;
import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@NacosPropertySource(dataId = "demo-springboot-nacos", autoRefreshed = true)
public class DemoNacosApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoNacosApplication.class, args);
    }
}
