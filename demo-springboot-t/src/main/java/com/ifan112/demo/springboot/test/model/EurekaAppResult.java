package com.ifan112.demo.springboot.test.model;

import java.util.List;

public class EurekaAppResult {

    public Applications applications;

    public static class Applications {
        public List<Application> application;
    }

    public static class Application {
        public String name;
        public List<Instance> instance;
    }

    public static class Instance {
        public String instanceId;
        public String hostName;
        public String ipAddr;
    }
}
