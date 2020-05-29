package com.ifan112.demo.springboot.test.mapper;

import com.ifan112.demo.springboot.test.model.Module;
import com.ifan112.demo.springboot.test.service.EurekaService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * 注解 @MybatisTest 使用的测试应用上下文 ApplicationContext 实现是 AnnotationConfigApplicationContext
 * 它将不会引发测试应用上下加载所有的 Bean，例如有 @Service, @Controller 注解的 Bean。
 *
 * TODO 原理待了解。
 *
 */

// @SpringBootTest
@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ModuleMapperTest {

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testApplicationContext() {
        assertNotNull(applicationContext);
        assertTrue(applicationContext instanceof AnnotationConfigApplicationContext);
    }

    @Test
    public void testSelectAllModule() {
        List<Module> moduleList = moduleMapper.selectAllModule();

        Assert.assertEquals(770, moduleList.size());
    }
}