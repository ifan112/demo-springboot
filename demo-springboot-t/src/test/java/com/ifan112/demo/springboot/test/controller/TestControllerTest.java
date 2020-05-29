package com.ifan112.demo.springboot.test.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * WebEnvironment.RANDOM_PORT 或者 WebEnvironment.DEFINED_PORT 的方式
 * 将会启动一个完整的嵌入式 Tomcat 服务器，这也就基本相当于服务完整启动了。
 *
 * 此时，用于测试的 ApplicationContext 的实现也变成了 AnnotationConfigServletWebServerApplicationContext
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestControllerTest {

    @Autowired
    private TestController testController;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testTestController() {
        assertNotNull(testController);
    }

    /**
     *   .   ____          _            __ _ _
     *  /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
     * ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
     *  \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
     *   '  |____| .__|_| |_|_| |_\__, | / / / /
     *  =========|_|==============|___/=/_/_/_/
     *  :: Spring Boot ::        (v2.1.6.RELEASE)
     *
     * 2020-05-27 23:33:21.113  INFO 6785 --- [           main] c.i.d.s.test.TestControllerTest          : Starting TestControllerTest on iFans-Mac-mini.local with PID 6785 (started by ifan in /Users/ifan/Workspaces/Java/demo-springboot/demo-springboot-t)
     * 2020-05-27 23:33:21.114  INFO 6785 --- [           main] c.i.d.s.test.TestControllerTest          : No active profile set, falling back to default profiles: default
     * 2020-05-27 23:33:22.445  INFO 6785 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 0 (http)
     * 2020-05-27 23:33:22.481  INFO 6785 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
     * 2020-05-27 23:33:22.481  INFO 6785 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.21]
     * 2020-05-27 23:33:22.601  INFO 6785 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
     * 2020-05-27 23:33:22.601  INFO 6785 --- [           main] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 1423 ms
     * 2020-05-27 23:33:23.049  INFO 6785 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
     * 2020-05-27 23:33:23.370  INFO 6785 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 52615 (http) with context path ''
     * 2020-05-27 23:33:23.374  INFO 6785 --- [           main] c.i.d.s.test.TestControllerTest          : Started TestControllerTest in 3.082 seconds (JVM running for 4.314)
     * 2020-05-27 23:33:23.631  INFO 6785 --- [o-auto-1-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
     * 2020-05-27 23:33:23.632  INFO 6785 --- [o-auto-1-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
     * 2020-05-27 23:33:23.649  INFO 6785 --- [o-auto-1-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 17 ms
     * 2020-05-27 23:33:23.795  INFO 6785 --- [       Thread-3] o.s.s.concurrent.ThreadPoolTaskExecutor  : Shutting down ExecutorService 'applicationTaskExecutor'
     *
     *
     * 从上面的日志可以看到，启动了一个内嵌的 Tomcat 服务器
     */
    @Test
    public void testApplicationContext() {
        assertNotNull(applicationContext);
        assertTrue(applicationContext instanceof AnnotationConfigServletWebServerApplicationContext);
    }

    @Test
    public void testTest2() {
        ResponseEntity<String> respEntity = this.restTemplate.getForEntity(getUrl("/test"), String.class);
        assertEquals("test", respEntity.getBody());
    }

    private String getUrl(String path) {
        return "http://localhost:" + port + path;
    }
}