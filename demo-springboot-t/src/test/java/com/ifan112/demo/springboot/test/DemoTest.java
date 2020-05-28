package com.ifan112.demo.springboot.test;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestExecutionListeners(
        listeners = {               // 自定义监听器列表
                DemoTestExecutionListener.class
        },
        inheritListeners = false,   // 是否继承在父类及祖先类上声明的监听器，默认 true
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS    // 是否合并测试框架中自有的监听器，共12个，默认不合并
)
@TestPropertySource(
        properties = {
                "url.baidu=http://www.baidu.com",    // 行内声明的属性
                "spring.main.banner-mode=off"
        }
)
@IfProfileValue(name = "user.name", value = "ifan") // 当系统用户是 ifan 时才会执行测试用例
public class DemoTest {

    @BeforeClass
    public static void beforeClass() {
        System.out.println("----- beforeClass -----");
    }

    @Value("${url.baidu}")
    private String urlBaidu;


    /**
     * 在依次执行了
     * 1. TestExecutionListener#beforeTestClass
     * 2. @BeforeClass 注解的方法
     * 3. 无参数构造测试类的实例
     * 之后，在执行 TestExecutionListener#prepareTestInstance 时才调用
     * SpringBootContextLoader#loadContext 方法创建 ApplicationContext
     *
     * 实际上，它还是创建了一个完整的 SpringApplication，就是通常使用的那个 SpringBoot 服务启动入口。
     *
     * 例如，可以通过 @TestPropertySource 注解声明的行内属性 spring.main.banner-mode=off 来关闭 banner 输出
     */

    @Test
    public void test() {
        /*
         * 配置项 url.baidu 的来源一共有三处，按照优先级从低到高分别是：
         *
         * 1. src/main/resources/application.properties 属性文件
         * 2. src/test/resources/application.properties 属性文件
         * 3. 当前测试类上基于 @TestPropertySource 声明的行内属性
         *
         * 因此，这个测试用例将会失败
         */
        Assert.assertEquals("https://www.baidu.com", urlBaidu);
    }
}
