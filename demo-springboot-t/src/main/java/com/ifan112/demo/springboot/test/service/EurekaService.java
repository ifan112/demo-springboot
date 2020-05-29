package com.ifan112.demo.springboot.test.service;

import com.ifan112.demo.springboot.test.model.EurekaAppResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

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

    public EurekaAppResult getApps() {
        RequestEntity<Void> req = RequestEntity.get(URI.create(urlEurekaRegistry + GET_APPS_PATH))
                .accept(MediaType.APPLICATION_JSON)
                .build();

        ResponseEntity<EurekaAppResult> respResult = restTemplate.exchange(req, EurekaAppResult.class);

        return respResult.getBody();
    }
}
