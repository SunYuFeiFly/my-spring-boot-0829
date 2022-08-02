package com.atguigu.springboot.Listener;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author syf_12138
 * @Description 自定义监听器
 *              ServletContextListener 是 ServletContext 的监听者，如果 ServletContext 发生变化，如服务器启动时 ServletContext 被创建，服务器关闭时 ServletContext 将要被销毁。
 *              简单使用：
 *                  1、服务器启动时，ServletContextListener 的 contextInitialized()方法被调用，所以在里面创建好缓存。可以从文件中或者从数据库中读取取缓存内容生成类，用 ervletContext.setAttribute()方法将缓存类保存在 ServletContext 的实例中。
 *                  2、程序使用 ServletContext.getAttribute()读取缓存。如果是 JSP，使用a pplication.getAttribute()。如果是 Servlet，使用 getServletContext().getAttribute()。如果缓存发生变化(如访问计数)，你可以同时更改缓存和文件/数据库。或者你等 变化积累到一定程序再保存，也可以在下一步保存。
 *                  3、服务器将要关闭时，ServletContextListener 的 contextDestroyed()方法被调用，所以在里面保存缓存的更改。将更改后的缓存保存回文件或者数据库，更新原来的内容。
 * @create 2022/8/2 12:39
 */

public class MyListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("contextInitialized...web应用启动");

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("contextDestroyed...当前web项目销毁");
    }
}
