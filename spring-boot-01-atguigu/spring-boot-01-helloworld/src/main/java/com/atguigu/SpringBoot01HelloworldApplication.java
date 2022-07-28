package com.atguigu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 *  @SpringBootApplication 来标注一个主程序类，说明这是一个Spring Boot应用
 *  主启动类只能扫描@ComponentScan注入值，所以位于主启动类上一层的控制类需用@ComponentScan改变扫描陆金进行扫描范围扩大，不然相应请求接口不起作用;
 *  @SpringBootApplication: Spring Boot应用标注在某个类上说明这个类是SpringBoot的主配置类，SpringBoot 就应该运行这个类的main方法来启动SpringBoot应用；
 *  @SpringBootApplication = @ComponentScan + @SpringBootConfiguration + @EnableAutoConfiguration
 *      @SpringBootConfiguration:Spring Boot的配置类； 标注在某个类上，表示这是一个Spring Boot的配置类；
 *          @Configuration:配置类上来标注这个注解； 配置类 ----- 配置文件；配置类也是容器中的一个组件；@Component
 *      @EnableAutoConfiguration：开启自动配置功能； 以前我们需要配置的东西，Spring Boot帮我们自动配置；@EnableAutoConfiguration告诉SpringBoot开启自动配置功能，这样自动配置才能生效；
 *          @AutoConfigurationPackage：自动配置包
 *          @Import(AutoConfigurationPackages.Registrar.class)：Spring的底层注解@Import，给容器中导入一个组件；导入的组件由 AutoConfigurationPackages.Registrar.class， 将主配置类（@SpringBootApplication标注的类）的所在包及下面所有子包里面的所有组件扫描到Spring容器；
 *          Spring Boot在启动的时候从类路径下的META-INF/spring.factories中获取EnableAutoConfiguration指定的值，将 这些值作为自动配置类导入到容器中，自动配置类就生效，帮我们进行自动配置工作；以前我们需要自己配置的东 西，自动配置类都帮我们；
 */

// @ComponentScan(basePackages="com.atguigu")
@ComponentScan(basePackages="com")
@SpringBootConfiguration
@SpringBootApplication
public class SpringBoot01HelloworldApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot01HelloworldApplication.class, args);
    }

}
