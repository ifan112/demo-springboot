package com.ifan112.demo.springboot.test.base;

import com.ifan112.demo.springboot.test.model.EurekaAppResult;
import com.ifan112.demo.springboot.test.service.EurekaService;

import java.util.ArrayList;
import java.util.Arrays;

public class EurekaBaseTest {

    /**
     * 测试数据
     */
    protected static final String instanceId = "iZbp14qnxgiw2lyl71w8skZ:demo-springcloud-user:2019";
    protected static final String hostName = "iZbp14qnxgiw2lyl71w8skZ";
    protected static final String ipAddr = "10.10.8.167";

    protected static final String instanceId2 = "iZbp14qnxgiw2lyl71w8skZ2:demo-springcloud-user:2019";
    protected static final String hostName2 = "iZbp14qnxgiw2lyl71w8skZ2";
    protected static final String ipAddr2 = "10.10.8.168";

    protected static final String applicationName = "DEMO-SPRINGCLOUD-USER";

    public EurekaAppResult prepareRespEntity() {
        return makeEurekaAppResult(makeApplication(makeUpInstance()));
    }

    public EurekaAppResult prepareRespEntityWithOutOffServiceInstance() {
        return makeEurekaAppResult(makeApplication(makeUpInstance(), makeOutOffServiceInstance()));
    }


    EurekaAppResult makeEurekaAppResult(EurekaAppResult.Application... applications) {
        EurekaAppResult eurekaAppResult = new EurekaAppResult();
        eurekaAppResult.applications = new EurekaAppResult.Applications();
        eurekaAppResult.applications.application = new ArrayList<>(Arrays.asList(applications));

        return eurekaAppResult;
    }

    private EurekaAppResult.Application makeApplication(EurekaAppResult.Instance... instances) {
        EurekaAppResult.Application application = new EurekaAppResult.Application();
        application.name = applicationName;

        /*
         * Arrays#asList 返回的是 Arrays.ArrayList 类的实例，
         * Collections#singletonList 返回的是 Collections.SingletonList 类的实例
         * 注意，与 java.util.ArrayList 相比，它们有许多不支持的方法，例如 removeIf
         * 因此，这里不能直接使用上面两个方法，而必须 new ArrayList
         */
        application.instance = new ArrayList<>(Arrays.asList(instances));

        return application;
    }

    private EurekaAppResult.Instance makeOutOffServiceInstance() {
        EurekaAppResult.Instance instance = new EurekaAppResult.Instance();
        instance.instanceId = instanceId2;
        instance.hostName = hostName2;
        instance.ipAddr = ipAddr2;
        instance.status = EurekaService.STATUS_OUT_OFF_SERVICE;

        return instance;
    }

    private EurekaAppResult.Instance makeUpInstance() {
        EurekaAppResult.Instance instance = new EurekaAppResult.Instance();
        instance.instanceId = instanceId;
        instance.hostName = hostName;
        instance.ipAddr = ipAddr;
        instance.status = EurekaService.STATUS_UP;

        return instance;
    }
}
