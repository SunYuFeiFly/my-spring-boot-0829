package com.baizhi.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *  spring跨域解决方案
 *      1、@CrossOrigin（局部）：
 *          1.1、该注解可以添加在⽅法上，也可以添加在 Controller 上。
 *          @CrossOrigin (origins = "http://localhost:8081")
 *          @PostMapping ("/post")
 *              public String post (){
 *              return "hello post";
 *          }
 *          1.2、@CrossOrigin 注解各属性含义如下：
 *              alowCredentials：浏览器是否应当发送凭证信息，如 Cookie。
 *              allowedHeaders： 请求被允许的请求头字段，*表示所有字段。
 *              exposedHeaders：哪些响应头可以作为响应的⼀部分暴露出来。
 *              注意，这⾥只可以⼀⼀列举，通配符 * 在这⾥是⽆效的。
 *              maxAge：预检请求的有效期，有效期内不必再次发送预检请求，默认是1800秒。
 *              methods：允许的请求⽅法，* 表示允许所有⽅法。
 *              origins：允许的域，*表示允许所有域。
 *
 *      2、重写WebMvcConfigurer.addCorsMappings (CorsRegistry registry)方法(全局)
 *          @Configuration
 *          public class WebMvcConfig implements WebMvcConfigurer{
 *              @Override
 *              public void addCorsMappings (CorsRegistry registry){
 *                  // 对那些请求进行跨域处理
 *                  registry.addMapping("/**")
 *                  // 允许请求凭证
 *                  .allowCredentials(false)
 *                  // 返回请求头
 *                  .allowedHeaders("*")
 *                  // 允许请求方法
 *                  .allowedMethods("*")
 *                  // 允许请求来源
 *                  .allowedOrigins("*")
 *                  .maxAge(3600);
 *              }
 *          }
 *
 *      3、CrosFilter（全局）：是Spring Web 中提供的⼀个处理跨域的过滤器，开发者也可以通过该过该过滤器处理跨域。
 *          @Configuration
 *          public class MyWebMvcConfig {
 *               @Bean
 *               FilterRegistrationBean<CorsFilter> corsFilter() {
 *                   FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>();
 *                   CorsConfiguration corsConfiguration = new CorsConfiguration();
 *                   // 返回请求头
 *                   corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
 *                   // 允许请求方法
 *                   corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
 *                   // 允许请求来源
 *                   corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));
 *                   corsConfiguration.setMaxAge(3600L);
 *                   UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
 *                   source.registerCorsConfiguration("/**", corsConfiguration);
 *                   registrationBean.setFilter(new CorsFilter(source));
 *                   // 在所有内置filter之前执行
 *                   registrationBean.setOrder(-1);
 *                   return registrationBean;
 *               }
 *           }
 *  springsecurity跨域解决方案
 *      注：上述3中方法一二两种直接失效，第暗中要看零食分配的securityFilter 的order与我们为CorsFilter设置的值谁小，不一定先执行CorsFilter
 *      方法：在自定义security配置中作相关配置
 *      @Configuration
 *      public class MySecurityConfigure extends WebSecurityConfigurerAdapter {
 *
 *          @Override
 *          protected void configure(HttpSecurity http) throws Exception {
 *          // 开启请求权限管理
 *          http.authorizeRequests()
 *              // 所有请求在认证之后才能访问
 *              .anyRequest().authenticated()
 *              // 开启表单认证
 *              .and().formLogin()
 *              // 开启跨域
 *              .and().cors().configurationSource(configurationSource())
 *
 *              .......
 *          }
 *      }
 *
 *      CorsConfigurationSource configurationSource() {
 *         CorsConfiguration corsConfiguration = new CorsConfiguration();
 *         corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
 *         corsConfiguration.setAllowedMethods(Arrays.asList("*"));
 *         corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
 *         corsConfiguration.setMaxAge(3600L);
 *         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
 *         source.registerCorsConfiguration("/**", corsConfiguration);
 *         return source;
 *     }
 */


@SpringBootApplication
public class SpringbootSecurity13CorsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootSecurity13CorsApplication.class, args);
    }

}
