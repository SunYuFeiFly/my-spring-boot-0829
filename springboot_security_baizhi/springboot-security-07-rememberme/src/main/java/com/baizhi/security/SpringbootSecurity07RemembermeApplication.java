package com.baizhi.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 记住我使用步骤：
 *      1.自定义资源权限规则类MySecurityConfigure中protected void configure(HttpSecurity http)方法中，在开启表单认证formLogin()后开启记住我功能rememberMe()
 *      2.具体配置：http.authorizeRequests().antMatchers("/login.html").permitAll().anyRequest().authenticated().and().formLogin().and().rememberMe();
 *          .rememberMe()后如果配置alwaysRemember(true)，则前端手否勾选"记住我"后端都会实现此功能
 *      3.原理：
 *          TokenBasedRememberMeServices默认实现
 *              第一次登录：
 *                 1）、当我们配置rememberMe()方法，此时登录页中会有remember-me字段的勾选值，其属性值可以为on、true、yes、1，登录时会被RememberMeAuthenticationFilter拦截，
 *                      获取用户名密码进行验证登录，登录成功则将信息保存至SecurityContextHolder中
 *                 2）、用户登录成功后(onLoginSuccess())，根据用户名、密码、当前时间及过期时长MD5加密为字符串，创建cookie返回给前端（键：remember-me 值：加密生成字符串）
 *                     1>、在这个回调中，⾸先获取⽤户经和密码信息，如果⽤户密码在⽤户登录成功后从successfulAuthentication对象中擦除，则从数据库中重新加载出⽤户密码。
 *                     2>、计算出令牌的过期时间，令牌默认有效期是两周。
 *                     3>、根据令牌的过期时间、⽤户名以及⽤户密码，计算出⼀个签名。
 *                     4>、调⽤ setCookie ⽅法设置 Cookie， 第⼀个参数是⼀个数组，数组中⼀共包含三项。⽤户名、过期时间以及签名，在setCookie ⽅法中会将数组转为字符串，并进⾏Base64编码后响应给前端。
 *                 3）、全局校验成功，放行请求。
 *             再次登录：
 *                 1）、页面刷新等请求会被RememberMeAuthenticationFilter拦截（后端配置过.rememberMe()方法，一定会被此过滤器拦截）
 *                 2）、获取请求中所有cookie，查看是否存在remember-me键，存在则获取对应值，不存在则抛出异常需重新登录操作
 *                     1>、判断加密字符串长度，⻓度为0说明格式不对，则直接抛出异常，否则根据":"分割字符串
 *                     2>、获取数组第0下标内容（过期时间戳），判断令牌是否过期，如果⼰经过期，则拋出异常
 *                     3>、根据数组第1下标内容（用户名）查询用户信息（本地内存用户、数据库用户），不存在则拋出异常
 *                     4>、调⽤ makeTokenSignature ⽅法⽣成⼀个签名，签名的⽣成过程如下：⾸先将⽤户名、令牌过期时间、⽤户密码以及 key 组成⼀个宇符串，中间⽤“：”隔开，然后通过MD5 消息摘要算法对该宇符串进⾏加密，并将加密结果转为⼀个字符串返回
 *                     5>、判断第4 步⽣成的签名和通过 Cookie 传来的签名是否相等（即 cookieTokens 数组的第2项），如果相等，表示令牌合法，则直接返回⽤户对象，否则拋出异常
 *              总结
 *                  当⽤户通过⽤户名/密码的形式登录成功后，系统会根据⽤户的⽤户名、密码以及令牌的过期时间计算出⼀个签名，这个签名使⽤ MD5 消息摘要算法⽣成，是不可逆的。然后再将⽤户名、令牌过期时间以及签名拼接成⼀个字符串，中间⽤“:” 隔开，对拼接好的字符串进⾏
 *              Base64 编码，然后将编码后的结果返回到前端，也就是我们在浏览器中看到的令牌。当会话过期之后，访问系统资源时会⾃动携带上Cookie中的令牌，服务端拿到 Cookie中的令牌后，先进⾏ Bae64解码，解码后分别提取出令牌中的三项数据：接着根据令牌中的数据判断
 *              令牌是否已经过期，如果没有过期，则根据令牌中的⽤户名查询出⽤户信息：接着再计算出⼀个签名和令牌中的签名进⾏对⽐，如果⼀致，表示会牌是合法令牌，⾃动登录成功，否则⾃动登录失败。
 *
 *          PersistentTokenBasedRememberMeServices内存令牌
 *              实现：
 *                  // 开启表单认证
 *                 .and().formLogin()
 *                 // 开启记住我功能
 *                 .and().rememberMe()
 *                 // 指定记住我自定义实现（基于内存）
 *                 // .rememberMeServices(rememberMeServices())
 *
 *                @Bean
 *                public RememberMeServices rememberMeServices() {
 *                    // 参数1：⾃定义⼀个⽣成令牌 key 默认 UUID
 *                    // 参数2：认证数据源
 *                    // 参数3：令牌存储
 *                return new PersistentTokenBasedRememberMeServices(UUID.randomUUID().toString(), userDetailsService(), new InMemoryTokenRepositoryImpl());
 *                }
 *
 *              与TokenBasedRememberMeServices默认实现不同的是：
 *              1）、第一次登录成功后向前端返回的键值对值为series和用户名的加密字符串，不包含失效时间，向当地内存也保存series、加密字符串的键值对数值及当前date
 *              2）、当再次登录时，会获取前端请求Cookie中remember-me的值，不存在则抛出异常，如果存在则分割字符串获取series、token，利用series去内存中查询获取对应值及date
 *                  1>、利用date与设置过期时间（默认2周）确认当前请求是否过期，过期抛出异常
 *                  2>、对比Cookie中获取到token与内存中获取到token是否相等，相等则全局登录成功
 *                      1、同时根据series和用户名生成新token，更新对应内存series中token及date
 *                      2、更新返回前端remember-me键对应的新token值
 *              总结
 *                  解决了TokenBasedRememberMeServices默认实现时根据获取到的token可以模拟发送无数的假请求（因为token不过期是不会更新token的）情况，每次登录都会向前端返回新的cookie值，减少一个token无限次请求状况
 *
 *          持久化令牌：
 *              实现：
 *                  // 开启表单认证
 *                 .and().formLogin()
 *                 // 开启记住我功能
 *                 .and().rememberMe()
 *                 // 指定记住我自定义实现（数据库持久化）
 *                 .tokenRepository(persistentTokenRepository())
 *
 *                  // 注入数据源
 *                  private final DataSource dataSource;
 *                  @Autowired
 *                  public MySecurityConfigure(DataSource dataSource) {
 *                      this.dataSource = dataSource;
 *                  }
 *
 *                 @Bean
 *                  public PersistentTokenRepository persistentTokenRepository() {
 *                      JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
 *                      jdbcTokenRepository.setDataSource(dataSource);
 *                      // 启动创建表结构（第二次启动设置为false，因为表已经存在）
 *                      jdbcTokenRepository.setCreateTableOnStartup(true);
 *                      return jdbcTokenRepository;
 *                  }
 *              总结
 *                  在居于内存令牌的原理之上，series、token不存在与本地内存，避免本地服务赞同或重启等导致内存清空，需重新登录情况，series、token、last_used直接保存在数据库
 */

@SpringBootApplication
public class SpringbootSecurity07RemembermeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootSecurity07RemembermeApplication.class, args);
    }

}
