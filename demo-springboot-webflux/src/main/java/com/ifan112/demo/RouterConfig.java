package com.ifan112.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Component
public class RouterConfig {

    @Autowired
    private DemoHandler demoHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route(GET("/time"), demoHandler::time)
                .andRoute(GET("/date"), req -> demoHandler.date(req))
                .andRoute(GET("/times"), req -> demoHandler.times(req));
    }

}
