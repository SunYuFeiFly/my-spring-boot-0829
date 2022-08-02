package com.atguigu.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 1、创建SpringApplication对象
 * 1）、保存主配置类
 * 2）、判断当前是否一个web应用
 * 3）、从类路径下找到META‐INF/spring.factories配置的所有ApplicationContextInitializer；然后保存起 来
 * 4）、/从类路径下找到ETA‐INF/spring.factories配置的所有ApplicationListener
 * 5）、从多个配置类中找到有main方法的主配置类
 * <p>
 * 2、运行run方法
 * 1）、获取SpringApplicationRunListeners；从类路径下META‐INF/spring.factories
 * 2）、回调所有获取的SpringApplicationRunListener.starting()方法
 * 3）、ApplicationArguments封装命令行参数args
 * 4）、准备环境,创建环境完成后回调SpringApplicationRunListener.environmentPrepared()；表示环境准 备完成
 * 5）、创建ApplicationContext；决定创建web的ioc还是普通的ioc
 * 6）、准备上下文环境，将environment保存到ioc中；且applyInitializers()；
 * 回调之前保存的所有的ApplicationContextInitializer的initialize方法
 * 回调所有的SpringApplicationRunListener的contextPrepared()
 * 7）、prepareContext运行完成以后回调所有的SpringApplicationRunListener的contextLoaded()
 * 8)、刷新容器，ioc容器初始化（如果是web应用还会创建嵌入式的Tomcat）；Spring注解版，扫描，创建，加载所有组件的地方；（配置类，组件，自动配置）
 * 9）、从ioc容器中获取所有的ApplicationRunner和CommandLineRunner进行回调，ApplicationRunner先回调，CommandLineRunner再回调
 * 10）、所有的SpringApplicationRunListener回调finished方法
 * 11）、整个SpringBoot应用启动完成以后返回启动的ioc容器。
 */

@SpringBootApplication
public class SpringBoot07TheoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot07TheoryApplication.class, args);
    }

    /**
     * 创建SpringApplication对象
     */
//    private void initialize(Object[] sources) {
//        //保存主配置类
//        if (sources != null && sources.length > 0) {
//            this.sources.addAll(Arrays.asList(sources));
//        }
//        // 判断当前是否一个web应用
//        this.webEnvironment = deduceWebEnvironment();
//        // 从类路径下找到META‐INF/spring.factories配置的所有ApplicationContextInitializer；然后保存起 来
//        setInitializers((Collection) getSpringFactoriesInstances( ApplicationContextInitializer.class));
//        // 从类路径下找到ETA‐INF/spring.factories配置的所有ApplicationListener
//        setListeners((Collection) getSpringFactoriesInstances(ApplicationListener.class));
//        // 从多个配置类中找到有main方法的主配置类
//        this.mainApplicationClass = deduceMainApplicationClass();
//        }
//    }


    /**
     * 运行run方法
     */
//    public ConfigurableApplicationContext run(String... args) {
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//        ConfigurableApplicationContext context = null;
//        FailureAnalyzers analyzers = null;
//        configureHeadlessProperty();
//        //获取SpringApplicationRunListeners，从类路径下META‐INF/spring.factories SpringApplicationRunListeners
//        listeners = getRunListeners(args);
//        // 回调所有的获取SpringApplicationRunListener.starting()方法
//        listeners.starting();
//        try {
//            // 封装命令行参数
//            ApplicationArguments applicationArguments = new DefaultApplicationArguments(args);
//            // 准备环境
//            ConfigurableEnvironment environment = prepareEnvironment(listeners, applicationArguments);
//            // 创建环境完成后回调SpringApplicationRunListener.environmentPrepared()；
//            // 表示环境准备完成
//            Banner printedBanner = printBanner(environment);
//            // 创建ApplicationContext；决定创建web的ioc还是普通的ioc
//            context = createApplicationContext();
//            analyzers = new FailureAnalyzers(context);
//            // 准备上下文环境;将environment保存到ioc中；而且applyInitializers()；
//            // applyInitializers()：回调之前保存的所有的ApplicationContextInitializer的initialize方法
//            // 回调所有的SpringApplicationRunListener的contextPrepared()；
//            prepareContext(context, environment, listeners, applicationArguments, printedBanner);
//            // prepareContext运行完成以后回调所有的SpringApplicationRunListener的contextLoaded（）；
//            // s刷新容器；ioc容器初始化（如果是web应用还会创建嵌入式的Tomcat）；Spring注解版
//            // 扫描，创建，加载所有组件的地方；（配置类，组件，自动配置）
//            refreshContext(context);
//            // 从ioc容器中获取所有的ApplicationRunner和CommandLineRunner进行回调
//            // ApplicationRunner先回调，CommandLineRunner再回调
//            afterRefresh(context, applicationArguments);
//            // 所有的SpringApplicationRunListener回调finished方法
//            listeners.finished(context, null);
//            stopWatch.stop();
//            if (this.logStartupInfo) {
//                // new StartupInfoLogger(this.mainApplicationClass) .logStarted(getApplicationLog(), stopWatch); }
//                // 整个SpringBoot应用启动完成以后返回启动的ioc容器；
//                return context;
//            } catch (Throwable ex){
//                handleRunFailure(context, listeners, analyzers, ex);
//                throw new IllegalStateException(ex);
//            }
//        }
//    }

}

