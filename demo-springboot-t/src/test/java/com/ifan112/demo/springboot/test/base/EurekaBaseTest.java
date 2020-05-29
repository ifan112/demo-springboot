package com.ifan112.demo.springboot.test.base;

import com.ifan112.demo.springboot.test.model.EurekaAppResult;

import java.util.Collections;

public class EurekaBaseTest {

    /**
     * 测试数据
     */
    protected static final String instanceId = "iZbp14qnxgiw2lyl71w8skZ:demo-springcloud-user:2019";
    protected static final String hostName = "iZbp14qnxgiw2lyl71w8skZ";
    protected static final String ipAddr = "10.10.8.167";
    protected static final String applicationName = "DEMO-SPRINGCLOUD-USER";

    public EurekaAppResult prepareRespEntity() {
        EurekaAppResult.Instance instance = new EurekaAppResult.Instance();
        instance.instanceId = instanceId;
        instance.hostName = hostName;
        instance.ipAddr = ipAddr;

        EurekaAppResult.Application application = new EurekaAppResult.Application();
        application.name = applicationName;
        application.instance = Collections.singletonList(instance);

        EurekaAppResult eurekaAppResult = new EurekaAppResult();
        eurekaAppResult.applications = new EurekaAppResult.Applications();
        eurekaAppResult.applications.application = Collections.singletonList(application);
        return eurekaAppResult;
    }
}
