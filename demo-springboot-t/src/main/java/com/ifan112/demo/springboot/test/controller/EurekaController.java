package com.ifan112.demo.springboot.test.controller;

import com.ifan112.demo.springboot.test.model.EurekaAppResult;
import com.ifan112.demo.springboot.test.service.EurekaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/eureka")
public class EurekaController {

    @Autowired
    private EurekaService eurekaService;

    @GetMapping("/info")
    public String info() {
        EurekaAppResult appResult = eurekaService.getApps();

        String infoContent = "应用个数: " + appResult.applications.application.size();
        infoContent += "\n";

        int instanceCount = 0;
        for (EurekaAppResult.Application application : appResult.applications.application) {
            instanceCount += application.instance.size();
        }

        infoContent += "实例个数: " + instanceCount;

        return infoContent;
    }
}
