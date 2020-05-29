package com.ifan112.demo.springboot.test.service;

import com.ifan112.demo.springboot.test.base.EurekaBaseTest;
import com.ifan112.demo.springboot.test.model.EurekaAppResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EurekaServiceTest extends EurekaBaseTest {

    @Autowired
    private EurekaService eurekaService;

    /**
     * 它将会覆盖在 DemoSpringBootTestConfig 配置类中通过 @Bean 声明的 RestTemplate，
     * 之后，EurekaService 组件中注入的 RestTemplate 实例就是这个。
     */
    @MockBean
    private RestTemplate mockRestTemplate;

    @Test
    public void test() {
        assertNotNull(eurekaService);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetApps() {
        // 模拟了外部服务的响应
        when(mockRestTemplate.exchange(any(RequestEntity.class), any(Class.class)))
                .thenReturn(ResponseEntity.ok(prepareRespEntity()));

        // 该测试用例主要是用于测试 EurekaService 在获取到了响应之后的处理逻辑
        EurekaAppResult result = eurekaService.getApps();

        assertNotNull(result);
        assertNotNull(result.applications);
        assertTrue(result.applications.application.size() > 0);

        EurekaAppResult.Application firstApplication = result.applications.application.get(0);
        assertEquals(applicationName, firstApplication.name);
        assertTrue(firstApplication.instance.size() > 0);

        EurekaAppResult.Instance firstInstance = firstApplication.instance.get(0);
        assertEquals(instanceId, firstInstance.instanceId);
        assertEquals(hostName, firstInstance.hostName);
        assertEquals(ipAddr, firstInstance.ipAddr);
    }


    @Test
    @SuppressWarnings("unchecked")
    public void testGetUpApps() {
        // 模拟了外部服务的响应
        when(mockRestTemplate.exchange(any(RequestEntity.class), any(Class.class)))
                .thenReturn(ResponseEntity.ok(prepareRespEntityWithOutOffServiceInstance()));

        /*
         * 该测试用例主要是用于测试 EurekaService 在获取到了响应之后的处理逻辑
         *
         * 实际上，根据测试数据的不同，可能会执行不同的代码。例如，对于这个测试用例，
         * 即使去掉非 UP 状态的实例 instance2，应用也还有一个 UP 状态的实例 instance。
         *
         * 因此，应用不会被"移除"，相应地下面的这行源码不会被测试到
         * applicationList.remove(application);
         *
         * 这属于测试代码覆盖率的范畴，之后再学习。
         */
        EurekaAppResult result = eurekaService.getUpApps();

        assertNotNull(result);
        assertNotNull(result.applications);
        assertEquals(1, result.applications.application.size());

        EurekaAppResult.Application firstApplication = result.applications.application.get(0);
        assertEquals(applicationName, firstApplication.name);
        assertEquals(1, firstApplication.instance.size());

        EurekaAppResult.Instance firstInstance = firstApplication.instance.get(0);
        assertEquals(instanceId, firstInstance.instanceId);
        assertEquals(hostName, firstInstance.hostName);
        assertEquals(ipAddr, firstInstance.ipAddr);
    }



}