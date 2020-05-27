package com.ifan112.demo.springboot.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * AutoConfigureMockMvc 将不会触发启动嵌入式 Tomcat 服务器，只需要构建 ApplicationContext 即可。
 */

@WebMvcTest(controllers = {
        TestController.class
})
@RunWith(SpringRunner.class)
public class TestControllerTest2 {

    @Autowired
    private MockMvc mockMvc;

    /**
     * 默认的实现是 GenericWebApplicationContext
     */
    @Autowired
    private ApplicationContext applicationContext;


    /**
     * .   ____          _            __ _ _
     * /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
     * ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
     * \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
     * '  |____| .__|_| |_|_| |_\__, | / / / /
     * =========|_|==============|___/=/_/_/_/
     * :: Spring Boot ::        (v2.1.6.RELEASE)
     * <p>
     * 2020-05-27 23:32:42.174  INFO 6779 --- [           main] c.i.d.s.test.TestControllerTest2         : Starting TestControllerTest2 on iFans-Mac-mini.local with PID 6779 (started by ifan in /Users/ifan/Workspaces/Java/demo-springboot/demo-springboot-t)
     * 2020-05-27 23:32:42.175  INFO 6779 --- [           main] c.i.d.s.test.TestControllerTest2         : No active profile set, falling back to default profiles: default
     * 2020-05-27 23:32:43.912  INFO 6779 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
     * 2020-05-27 23:32:44.156  INFO 6779 --- [           main] o.s.b.t.m.w.SpringBootMockServletContext : Initializing Spring TestDispatcherServlet ''
     * 2020-05-27 23:32:44.156  INFO 6779 --- [           main] o.s.t.web.servlet.TestDispatcherServlet  : Initializing Servlet ''
     * 2020-05-27 23:32:44.166  INFO 6779 --- [           main] o.s.t.web.servlet.TestDispatcherServlet  : Completed initialization in 10 ms
     * 2020-05-27 23:32:44.187  INFO 6779 --- [           main] c.i.d.s.test.TestControllerTest2         : Started TestControllerTest2 in 2.352 seconds (JVM running for 3.697)
     * 2020-05-27 23:32:51.024  INFO 6779 --- [       Thread-3] o.s.s.concurrent.ThreadPoolTaskExecutor  : Shutting down ExecutorService 'applicationTaskExecutor'
     * <p>
     * 从上面的日志可以看出，并没有启动内嵌的 Tomcat 服务器
     */
    @Test
    public void testApplicationContext() {
        assertNotNull(applicationContext);

        assertFalse(applicationContext instanceof AnnotationConfigServletWebServerApplicationContext);
    }

    @Test
    public void testTest() throws Exception {
        /*
         * 由于 MVC 服务是 Mock 出来的，因此并不可以使用 RestTemplate 真实地去发起 HTTP 请求
         *
         * 相应的，交由 MockMvc 去模拟发起请求并得到相应
         */
        this.mockMvc.perform(get("/test"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("test"));
    }
}