package com.atguigu.springboot.filter;


import javax.servlet.*;
import java.io.IOException;

/**
 * @author syf_12138
 * @Description 自定义过滤器
 *              过滤器如何实现拦截：
 *                  1、当客户端发生请求后，在HttpServletRequest到达Servlet之前，过滤器拦截客户的HttpServletRequest
 *                  2、根据需要检查HttpServletRequest，也可以修改HttpServletRequest头和数据
 *                  3、在过滤器中调用doFilter方法，对请求放行。请求到达Servlet后，对请求进行处理并产生HttpServletResponse发送给客户端
 *                  4、在HttpServletResponse到达客户端之前，过滤器拦截HttpServletResponse
 *                  5、根据需要检查HttpServletResponse，可以修改HttpServletResponse头和数据
 *                  6、最后，HttpServletResponse到达客户端
 *              Filter的生命周期：
 *                  Filter的创建和销毁由web服务器控制
 *                  服务器启动的时候，Web服务器创建Filter的实例对象，并调用其init方法，完成对象的初始化功能。filter对象只会创建一次，init方法也只会执行一次。
 *                  拦截到请求时，执行doFilter方法，可以执行多次。
 *                  服务关闭时，web服务器销毁Filter的实例对象
 *              Filter对象 - FilterConfig：
 *                  用户在配置filter时，可以使用<init-param>为filter配置一些初始化参数，当web容器实例化Filter对象，调用其init方法时，会把封装filter初始化参数的FilterConfig对象传递进来。
 *                  通过filterConfig对象的方法，可以获得：
 *                      String getFilterName()：得到filter名称
 *                      String getInitParameter(String name)：返回在部署描述中指定名称的初始化参数的值，如果不存在返回null
 *                      Enumeration getInitParameterNames()：返回过滤器的所有初始化参数的名称的枚举集合
 *                      public ServletContext getServletContext()：返回Servlet上下文对象的引用
 * @create 2022/8/2 12:31
 */

public class MyFilter implements Filter {

    /**
     * 初始化参数，在创建Filter时自动调用，当我们需要设置初始化参数的时候，可以写到该方法中
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("MyFilter...init()...");
    }

    /**
     * 拦截到要执行的请求时，doFilter就会执行。这里面写我们对请求和响应的预处理
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("MyFilter doFilter()...");
        chain.doFilter(request,response);
    }

    /**
     * 在销毁Filter时自动调用
     */
    @Override
    public void destroy() {
        System.out.println("MyFilter...destroy()...");
    }
}
