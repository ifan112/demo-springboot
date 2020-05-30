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

/**
 * /Library/Java/JavaVirtualMachines/jdk1.8.0_212.jdk/Contents/Home/bin/java            // java 命令行，启动 JVM 虚拟机
 * -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:56773,suspend=y,server=n -ea
 * -Didea.test.cyclic.buffer.size=1048576
 * -javaagent:/Users/ifan/Library/Caches/JetBrains/IdeaIC2020.1/captureAgent/debugger-agent.jar
 * -Dfile.encoding=UTF-8
 *
 * -classpath                                                                           // 执行测试用例时的 classpath
 * /Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar:
 * /Applications/IntelliJ IDEA CE.app/Contents/plugins/junit/lib/junit*.jar:            // idea 工具包
 *
 * /Library/Java/JavaVirtualMachines/jdk1.8.0_212.jdk/Contents/Home/*.jar:              // jdk 源码包
 *
 * /Users/ifan/Workspaces/Java/demo-springboot/demo-springboot-t/target/test-classes:   // test 包下源码及文件编译后的类
 * /Users/ifan/Workspaces/Java/demo-springboot/demo-springboot-t/target/classes:        // main 包下源码及文件在编译后的类
 *
 * /Users/ifan/Documents/Repository/*.jar                                               // 依赖包
 *
 * com.intellij.rt.junit.JUnitStarter                                                   // 入口类，将调用它的 main 方法
 * -ideVersion5 -junit4 com.ifan112.demo.springboot.test.DemoTest,test                  // 传入参数，com.ifan112.demo.springboot.test.DemoTest,test  分别测试用例所在的类方法
 *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
 *
 * 以上是使用 IDEA 执行测试用例时，实际上执行的命令。简单来说是，将测试用例所在的类和方法的名称作为参数，传递并启动 JUnitStarter。
 *
 * 需要特别注意的是在 -classpath 中指定了 target/test-classes 和 target/classes 这两个目录，之后使用到的一些文件（例如，每个
 * 测试类默认关联的上下文声明文件 XxxTest-context.xml 或 XxxTestContext.groovy）将会默认地从这两个目录下查找。
 * {@link org.springframework.test.context.support.AbstractContextLoader#generateDefaultLocations(Class)}
 *
 */

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
                "url.baidu=https://www.baidu.cn",    // 行内声明的属性
                "spring.main.banner-mode=off"
        },
        locations = {
                "demo-test.properties"               // 行内声明的属性文件
                                                     // 如果以 / 开头则表示 classpath 下绝对路径文件，否则表示 classpath 下与测试类相同目录的文件
                                                     // 以此声明为例，它表示 classpath:/com/ifan112/demo/springboot/test/demo-test.properties
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

    @Value("${url.tmall}")
    private String urlTMall;


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
         * 注意，此时，测试应用上下文的基于属性的配置项来源包括：
         *
         * 1. 系统属性
         * 2. 系统环境变量
         * 3. src/main/resources/application.properties 属性文件
         * 4. src/test/resources/application.properties 属性文件，
         *    如果同时存在 3 和 4 中的属性文件，那么将只会使用 4 中的属性文件
         * 5. src/test/resources/com/ifan112/demo/springboot/test/demo-test.properties
         * 6. 当前测试类上基于 @TestPropertySource 声明的行内属性
         */

        /*
         * 配置项 url.baidu 的来源一共有三处，按照优先级从低到高分别是：
         *
         * 1. src/main/resources/application.properties 属性文件
         * 2. src/test/resources/com/ifan112/demo/springboot/test/demo-test.properties 属性文件
         * 3. 当前测试类上基于 @TestPropertySource 声明的行内属性
         */
        Assert.assertEquals("https://www.baidu.cn", urlBaidu);

        /*
         * 配置项 urlTMall 的来源只有一处，通过 @TextPropertySource#locations 方法声明的属性文件。
         * 它的完整路径是：src/test/resources/com/ifan112/demo/springboot/test/demo-test.properties。
         */
        Assert.assertEquals("https://www.tmall.com", urlTMall);
    }
}
