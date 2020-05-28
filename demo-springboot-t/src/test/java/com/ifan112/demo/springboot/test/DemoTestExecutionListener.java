package com.ifan112.demo.springboot.test;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

public class DemoTestExecutionListener implements TestExecutionListener {

    public DemoTestExecutionListener() {
        System.out.println("----- DemoTestExecutionListener -----");
    }

    @Override
    public void beforeTestClass(TestContext testContext) throws Exception {

        // 早于 @BeforeClass 注解的方法

        System.out.println("---------- beforeTestClass ---------- ");
    }

    @Override
    public void prepareTestInstance(TestContext testContext) throws Exception {

    }

    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {

    }

    @Override
    public void beforeTestExecution(TestContext testContext) throws Exception {

    }

    @Override
    public void afterTestExecution(TestContext testContext) throws Exception {

    }

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {

    }

    @Override
    public void afterTestClass(TestContext testContext) throws Exception {

    }

}
