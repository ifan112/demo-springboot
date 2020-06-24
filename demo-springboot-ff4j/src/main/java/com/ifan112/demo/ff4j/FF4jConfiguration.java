package com.ifan112.demo.ff4j;

import org.ff4j.FF4j;
import org.ff4j.audit.repository.InMemoryEventRepository;
import org.ff4j.conf.XmlConfig;
import org.ff4j.property.store.InMemoryPropertyStore;
import org.ff4j.store.InMemoryFeatureStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.ff4j.aop")
public class FF4jConfiguration {

    @Bean
    public FF4j ff4j() {
        FF4j ff4j = new FF4j();

        ff4j.setFeatureStore(new InMemoryFeatureStore());
        ff4j.setPropertiesStore(new InMemoryPropertyStore());
        ff4j.setEventRepository(new InMemoryEventRepository());

        XmlConfig xmlConfig = ff4j.parseXmlConfig("ff4j.xml");
        ff4j.importFeatures(xmlConfig.getFeatures().values());
        ff4j.importProperties(xmlConfig.getProperties().values());

        ff4j.setEnableAudit(true);
        ff4j.setAutocreate(true);

        return ff4j;
    }
}
