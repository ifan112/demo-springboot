package com.ifan112.demo.springboot.test.controller;

import com.ifan112.demo.springboot.test.DemoSpringBootTestApp;
import com.ifan112.demo.springboot.test.DemoSpringBootTestConfig;
import com.ifan112.demo.springboot.test.base.EurekaBaseTest;
import com.ifan112.demo.springboot.test.service.EurekaService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 这里使用了 @WebMvcTest 和 MockMvc 替代 @SpringBootTest，用于避免在启动内嵌的 Tomcat 服务器的情况下
 * 对 Controller 层的类进行测试，以加快测试用例的执行速度。
 *
 * 不过，需要注意的是由 @WebMvcTest 启动的 ApplicationContext 只会管理有 @Controller, @ControllerAdvice
 * 等这些通常只会用在 Controller 层的注解的类，而对于其它有 @Service, @Configuration 甚至是 @Component 注解
 * 的类都不会管理。
 *
 * 例如，不会管理在有着 @Configuration 注解的配置类 DemoSpringBootTestConfig 中由 @Bean 声明的 RestTemplate 组件。
 * 那么，对于其它的组件，通常只能通过 @MockBean 的方式获取到，而不能直接通过 @Autowire 的方式注入。
 *
 *
 * 但是，对于需要通过 @SpyBan 方式注入的非 Controller 层组件，如果它需要其它组件，那么将会由于缺少可用的其它组件实例
 * 而导致该 Bean 无法被成功创建出来。
 *
 * 此时，解决的方式有两种：
 * 1. 在测试类中使用 @MockBean 提供可用的第三方组件实例，但是这又需要同时对 Mock 出来的第三方组件实例进行定制。
 * 2. 在测试类上使用 @ContextConfiguration 指定上下文，让 ApplicationContext 管理第三方组件。
 *
 */

@WebMvcTest
@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = {
                /*
                 * 由 @WebMvcTest 声明的测试上下文，默认不会管理有 @Component 注解的组件，
                 * 这样通常会导致在使用 @SpyBean 管理组件时的无法注入它所依赖的其它组件
                 * 因此，这里通过指定测试上下文，让其管理所有组件
                 */
                DemoSpringBootTestApp.class,
                DemoSpringBootTestConfig.class
        }
)
public class EurekaControllerTest2 extends EurekaBaseTest {

    /**
     * 使用 @SpyBean 管理的 EurekaService 组件，只会代理在测试用例指定方法，
     * 而对于其它方法，则保持真实的执行逻辑。
     */
    @SpyBean
    private EurekaService eurekaService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void test() {
        Assert.assertNotNull(applicationContext);
        Assert.assertEquals(eurekaService, applicationContext.getBean(EurekaService.class));
    }

    @Test
    public void testUpInfo() throws Exception {
        // 注意区分与在使用 @MockBean 时对于方法调用和响应的声明方式的不同
        Mockito.doReturn(prepareRespEntityWithOutOffServiceInstance())
                .when(eurekaService)
                .getApps();

        mockMvc.perform(get("/eureka/up-info"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "应用个数: 1\n" +
                        "实例个数: 1"));

    }
}