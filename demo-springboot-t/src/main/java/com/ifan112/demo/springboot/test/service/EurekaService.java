package com.ifan112.demo.springboot.test.service;

import com.ifan112.demo.springboot.test.model.EurekaAppResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Service
public class EurekaService {

    /**
     * 正常运行时，restTemplate 将会是在 DemoSpringBootTestConfig 配置类中
     * 通过 @Bean 声明的那个实例
     *
     * 不过，在测试时，可以 mock 出一个 RestTemplate 实例，用于隔离和模拟外部服务
     */
    @Autowired
    private RestTemplate restTemplate;

    @Value("${url.eureka.registry}")
    private String urlEurekaRegistry;

    private static final String GET_APPS_PATH = "/eureka/apps";

    public static final String STATUS_UP = "UP";
    public static final String STATUS_OUT_OFF_SERVICE = "OUT_OFF_SERVICE";


    public EurekaAppResult getApps() {
        RequestEntity<Void> req = RequestEntity.get(URI.create(urlEurekaRegistry + GET_APPS_PATH))
                .accept(MediaType.APPLICATION_JSON)
                .build();

        ResponseEntity<EurekaAppResult> respResult = restTemplate.exchange(req, EurekaAppResult.class);

        return respResult.getBody();
    }

    /**
     * 获取实例状态为 UP 的应用注册信息
     *
     * @return 实例状态为 UP 的应用注册信息
     */
    public EurekaAppResult getUpApps() {
        EurekaAppResult appResult = getApps();

        if (null != appResult.applications
                && !CollectionUtils.isEmpty(appResult.applications.application)) {

            List<EurekaAppResult.Application> applicationList = appResult.applications.application;

            for (EurekaAppResult.Application application : applicationList) {
                if (!CollectionUtils.isEmpty(application.instance)) {
                    // 如果实例状态非 UP，则从注册信息中移除
                    application.instance.removeIf(i -> !isUp(i.status));
                }

                if (CollectionUtils.isEmpty(application.instance)) {
                    // 如果应用下没有状态为 UP 的实例，则从注册信息中移除
                    applicationList.remove(application);
                }
            }
        }

        return appResult;
    }

    private boolean isUp(String status) {
        return STATUS_UP.equals(status);
    }
}
