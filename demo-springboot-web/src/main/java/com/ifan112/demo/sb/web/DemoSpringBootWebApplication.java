package com.ifan112.demo.sb.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoSpringBootWebApplication {

    public static void main(String[] args) {

        /*
         * 核心包
         *
         * spring-boot
         * spring-boot-autoconfigure
         * tomcat-embed
         *
         * sources 可以是类型、包名或者XML配置文件路径
         * primarySources 类名列表，默认是启动类 [DemoSpringBootWebApplication.class]
         *
         * sources和primarySources最终都会被用来加载Bean
         *
         * mainApplicationClass Spring自动推断出的启动类名，只有一个 DemoSpringBootWebApplication.class。
         * 一般只会在输出启动信息时用到
         */

        /*
         * 重要组件
         * 1. ApplicationContextInitializer
         *    在ApplicationContext对象被创建，设置了Environment和ResourceLoader之后，调用ApplicationContextInitializer对
         *    ApplicationContext进行初始化。注意，此时ApplicationContext还没有执行加载Bean等操作。
         *    此后，发布ApplicationContextInitializedEvent
         *
         * 2. ApplicationListener，相应的事件接口是ApplicationEvent。
         *    在spring-context中，定义了如下4个事件：
         *        ContextRefreshedEvent   context刷新事件，spring启动时自动触发该事件。
         *        下面三个事件都由开发者主动调用ApplicationContext的相应方法才会触发
         *        ContextStartedEvent     context启动完成事件。
         *        ContextClosedEvent      context关闭事件。context在close之后不可以再次start
         *        ContextStoppedEvent     context停止事件。context在stop之后可以再次start
         *    在spring-boot中，扩展了如下事件：
         *        ApplicationStartingEvent
         *        ApplicationEnvironmentPreparedEvent
         *        ApplicationContextInitializedEvent 在prepareContext之后，即创建了ApplicationContext对象之后
         *        ApplicationPreparedEvent，加载了source之后
         *        ApplicationStartedEvent，context refresh之后
         *        ApplicationReadyEvent，call runners之后
         *        ApplicationFailedEvent
         *    在spring-boot-autoconfigure中，扩展了如下事件：
         *        DataSourceSchemaCreatedEvent
         *        JobExecutionEvent
         *    在spring-boot-web中，扩展了如下事件：
         *        ServletWebServerInitializedEvent
         *        ReactiveWebServerInitializedEvent
         *
         */

        SpringApplication springApplication = new SpringApplication(DemoSpringBootWebApplication.class);

        springApplication.run(args);
    }
}
