package com.ifan112.demo.springboot.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {

    // @ModelAttribute("name")
    // public String name(@PathVariable(name = "num") Integer num) {
    //     return num + " - name";
    // }

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/test")
    public String test(@RequestParam int num) {
        if (num < 0) {
            throw new IllegalArgumentException("Argument 'num' cannot not be negative. Current value: " + num);
        }

        return "test";
    }

    @GetMapping("/test2/{num}")
    public String test2(@ModelAttribute("name") String name) {
        System.out.println(name);

        return "test2";
    }

    @GetMapping("/test3/{num}")
    public String test3(String name) {
        System.out.println(name);

        return "test3";
    }


    @GetMapping("/test4")
    public P test4() {
        P p = new P();
        p.id = 1;
        p.message = "This is p";

        return p;
    }

    static class P {
        public Integer id;
        public String message;
    }

    @GetMapping("/test5")
    public String test5() {
        ResponseEntity<String> respEntity = restTemplate.getForEntity("http://www.baidu.com", String.class);
        System.out.println(respEntity.getStatusCode());

        System.out.println(respEntity.getBody());

        return "ok";
    }


    static class GetEurekaApp {
        public Applications applications;

        static class Applications {

            public List<Application> application;

            static class Application {
                public String name;
                public List<Instance> instance;

                static class Instance {
                    public String instanceId;
                    public String hostName;
                    public String app;

                }
            }
        }
    }

    static class GetEurekaInstance {
        public GetEurekaApp.Applications.Application.Instance instance;
    }


    @GetMapping("/test6")
    public void test6() {
        ResponseEntity<GetEurekaApp> respEntity
                = restTemplate.getForEntity("http://10.10.8.167:8761/eureka/apps", GetEurekaApp.class);

        System.out.println(respEntity.getStatusCode());

        GetEurekaApp eureka = respEntity.getBody();

        if (null != eureka) {
            GetEurekaApp.Applications.Application application = eureka.applications.application.get(0);
            System.out.println(application.name);

            for (GetEurekaApp.Applications.Application.Instance instance : application.instance) {
                System.out.println(instance.instanceId);
                System.out.println(instance.hostName);
            }
        }
    }

    @GetMapping("/test7")
    public void test7() {
        Map<String, String> params = new HashMap<>();
        params.put("appId", "sba-ecs");
        params.put("instanceId", "172.16.48.34:sba-ecs:80");

        GetEurekaInstance getEurekaInstance
                = restTemplate.getForObject("http://10.10.8.167:8761/eureka/apps/{appId}/{instanceId}", GetEurekaInstance.class, params);

        if (null != getEurekaInstance) {
            System.out.println(getEurekaInstance.instance.hostName);
        }
    }
}