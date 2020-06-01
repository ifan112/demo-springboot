package com.ifan112.demo.springboot.test.mapper;

import com.ifan112.demo.springboot.test.model.Module;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * 注解 @MybatisTest 使用的测试应用上下文 ApplicationContext 实现是 AnnotationConfigApplicationContext。
 *
 * 测试应用上下文不会管理除 @Mapper 之外的其它 Bean（例如有 @Service, @Controller 注解的 Bean），这是因为
 * 它引入了 MybatisTypeExcludeFilter 这个组件，该组件通过 getComponentIncludes 和 getDefaultIncludes 方法返回空集合，
 * 来控制 ApplicationContext 不管理任何类型的 Bean。
 * 与此用法相似的还有 @WebMvcTest 注解，它控制了 ApplicationContext 只管理 Controller 层的组件。
 *
 *
 * 注解 @MybatisTest 上有一个 @Transactional 注解，与之相关有一个 TransactionalTestExecutionListener 组件，
 * 该组件在每次执行测试用例时都会检查测试用例所在的 method 和 class 是否有 @Transactional 注解，如果有则开启事务。否则，不处理。
 *
 */

// @SpringBootTest
@RunWith(SpringRunner.class)
@MybatisTest
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

        // 基于内存的 h2 数据库在每次测试之前数据表中都没有数据
        Assert.assertEquals(0, moduleList.size());
    }
}