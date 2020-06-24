package com.ifan112.demo.ff4j;

import org.ff4j.FF4j;
// import org.ff4j.spring.boot.web.api.config.EnableFF4jSwagger;
import org.ff4j.web.FF4jDispatcherServlet;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// @EnableFF4jSwagger
@AutoConfigureAfter(FF4jConfiguration.class)
public class FF4jWebConsoleConfiguration extends SpringBootServletInitializer {

    @Bean
    @ConditionalOnMissingBean
    public FF4jDispatcherServlet defineFF4jServlet(FF4j ff4j) {
        FF4jDispatcherServlet ff4jConsoleServlet = new FF4jDispatcherServlet();
        ff4jConsoleServlet.setFf4j(ff4j);

        return ff4jConsoleServlet;
    }

    @Bean
    public ServletRegistrationBean<FF4jDispatcherServlet> registerFF4jServlet(FF4jDispatcherServlet ff4jDispatcherServlet) {
        return new FF4jDispatcherServletRegistrationBean(ff4jDispatcherServlet);
    }


    static class FF4jDispatcherServletRegistrationBean
            extends ServletRegistrationBean<FF4jDispatcherServlet> {

        public FF4jDispatcherServletRegistrationBean(FF4jDispatcherServlet servlet) {
            super(servlet, "/ff4j-web-console/*");
        }
    }
}
