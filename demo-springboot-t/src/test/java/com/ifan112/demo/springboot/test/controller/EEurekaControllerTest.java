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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

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
 * 此时，解决的方式是：
 * 在测试类中使用 @MockBean 提供可用的第三方组件实例，但是这又需要同时对 Mock 出来的第三方组件实例进行定制。
 *
 *
 * 遇到一个奇怪的问题，如果当前测试类以'数字'结尾，那么 mvn test 命令将不会执行这个类中的测试用例，不清楚原因。
 */

@WebMvcTest(
        controllers = EurekaController.class    // 指定待测试的 Controller，避免管理其它 Controller 及其依赖，
                                                // 否则可能导致由于依赖不满足而启动失败。例如依赖 @Service 或 @Repository 组件
                                                // 而这些组件由于在 WebMvcTest 上下文中根本不进行管理，因此无法实现自动注入。
)
@RunWith(SpringRunner.class)
public class EEurekaControllerTest extends EurekaBaseTest {

    /**
     * 使用 @SpyBean 管理的 EurekaService 组件，只会代理在测试用例指定方法，
     * 而对于其它方法，则保持真实的执行逻辑。
     */
    @SpyBean
    private EurekaService eurekaService;

    /**
     * 由于 @WebMvcTest 将会使测试应用上下文不会管理非 Controller 层的组件（如 DemoSpringBootTestConfig 这个配置组件
     * 以及在其内部通过 @Bean 声明的 RestTemplate 组件），因此这里通过 @MockBean 显式声明一个以自动注入到 EurekaService 组件中。
     *
     * 如果 EurekaController 依赖的许多的组件，而这些组件又依赖了其它的组件，那么这些组件可能都需要通过 @MockBean 的方式显式声明。
     * 这也算是 @WebMvcTest 带来的一个不好的影响。
     */
    @MockBean
    private RestTemplate restTemplate;

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