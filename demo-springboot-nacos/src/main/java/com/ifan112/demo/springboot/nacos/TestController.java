package com.ifan112.demo.springboot.nacos;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @NacosValue(value = "${useLocalCache:false}", autoRefreshed = true)
    private boolean userLocalCache;

    @NacosInjected
    private NamingService namingService;

    @GetMapping("/test")
    public String test() {
        return String.valueOf(userLocalCache);
    }

    @GetMapping("/test2")
    public List<Instance> test2(@RequestParam String serviceName) throws NacosException {
        return namingService.getAllInstances(serviceName);
    }
}
