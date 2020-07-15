package com.ifan112.demo.springboot.web;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
public class DemoWebConfiguration {

    @Bean
    public RestTemplateCustomizer demoRestTemplateCustomer() {
        return restTemplate -> {
            System.out.println("----- demoRestTemplateCustomer -----");
        };
    }

    @Bean
    public RestTemplateCustomizer demoRestTemplateCustomer2() {
        return restTemplate -> {
            System.out.println("----- demoRestTemplateCustomer2 -----");
        };
    }

    /**
     * 自定义请求客户端请求拦截器
     *
     * @return 自定义请求客户端请求拦截器
     */
    @Bean
    public ClientHttpRequestInterceptor demoInterceptor() {
        return (request, body, execution) -> {
            long start = System.currentTimeMillis();

            System.out.println("----- 记录请求，开始：" + start + " -----");

            // 交由下一个拦截器处理，当拦截器处理完之后，发出 HTTP 请求，返回 HTTP 响应
            ClientHttpResponse response = execution.execute(request, body);

            long end = System.currentTimeMillis();

            System.out.println("----- 记录请求，结束：" + end + " -----");
            System.out.println("----- 记录请求，耗时：" + (end - start) + " -----");

            return response;
        };
    }



    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        /*
         * RestTemplate 内部有一个 ClientHttpRequestFactory 组件，用于决定创建什么样的 ClientHttpRequest，例如是默认的
         * 基于 java.net.HttpURLConnection 的还是基于 Apache HttpClient 或者是 Netty 实现的。
         *
         * 该组件的默认实现是在 RestTemplate 的父类 HttpAccessor 中创建的 SimpleClientHttpRequestFactory，它将基于
         * java.net.HttpURLConnection 创建 ClientHttpRequest 的子类 SimpleBufferingClientHttpRequest 以完成请求和响应。
         *
         *
         *
         * 那么，如果使用 Apache HttpClient 完成请求呢？
         * 1. 引入 apache httpclient 依赖
         * 2. 创建基于 apache httpclient 的 ClientHttpRequestFactory 实例 HttpComponentsClientHttpRequestFactory
         * 3. 创建 RestTemplate 实例时传入上一步中创建的 HttpComponentsClientHttpRequestFactory 对象
         *
         * 由 HttpComponentsClientHttpRequestFactory 创建出的 ClientHttpRequest 实现是 HttpComponentsClientHttpRequest。
         * 可以在创建 HttpComponentsClientHttpRequestFactory 实例时，传入自定义的 org.apache.http.client.HttpClient 实例。
         *
         *
         *
         * RestTemplateAutoConfiguration 这个看似和 RestTemplate 自动配置有关的类是干什么用的？
         * 1. 收集框架或者用户自定义的 HttpMessageConverters 和 RestTemplateCustomizer Bean，
         *    前者用于处理响应消息体，后者用于对 RestTemplate 实例自定义配置。
         * 2. 创建了 RestTemplateBuilder，整合上一步收集到的 Bean，用于在创建 RestTemplate 实例时对其自定义配置。
         *
         * 因此，除了直接创建 RestTemplate 实例，还可以通过 RestTemplateBuilder 来构建。
         * 同时，可以创建 RestTemplateCustomizer 实例用于自定义配置 RestTemplate 实例。
         *
         * 此外，在使用 RestTemplateBuilder 来创建 RestTemplate 实例时，它内部的 ClientHttpRequestFactorySupplier 组件将会
         * 根据 classpath 中是否存在指定的 http（支持 apache httpclient 和 okhttp）来自动选择 ClientHttpRequestFactory 的实现。
         *
         *
         *
         * Spring 支持通过实现 ClientHttpRequestInterceptor 接口并注册到 RestTemplate 中来拦截请求和响应。
         *
         * 例如，匿名的 demoInterceptor 用于记录请求和响应时间，以及在 Spring Boot Actuator 中对 RestTemplate
         * 调用采样的 MetricsClientHttpRequestInterceptor。
         *
         *
         *
         * Spring 提供了 BasicAuthenticationInterceptor 这个请求拦截器，它将会拦截请求并且在 Header 中添加
         * Authorization: Basic Base64(username:password) 以支持 Basic 认证方式。
         *
         */


        // RestTemplateBuilder 将会自动选择 http 的实现
        // HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        // RestTemplate restTemplate = new RestTemplate();
        // restTemplate.setRequestFactory(requestFactory);

        // Base64 编码的 Basic Authorization，本质上通过添加 BasicAuthenticationInterceptor 这个请求拦截器实现的
        // builder.basicAuthentication("username", "password");

        RestTemplate restTemplate = builder.build();

        // 添加自定义的拦截器
        restTemplate.setInterceptors(Collections.singletonList(demoInterceptor()));

        return restTemplate;
    }
}
