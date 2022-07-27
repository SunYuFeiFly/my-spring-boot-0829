package com.baizhi.shiro.shiro.config;

import com.baizhi.shiro.shiro.realm.UserRelam;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

/**
 * @author syf_12138
 * @Description Shiro配置类
 * @create 2022/7/26 11:39
 */

@Configuration
public class ShiroConfig {

    /**
     * Shiro的Web过滤器Factory
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        System.out.println("进入Shiro的Web过滤器......");
        // 设置安全管理器
        filterFactoryBean.setSecurityManager(securityManager);
        // 自定义认证页面（登录页面）
        filterFactoryBean.setLoginUrl("/main/login.jsp");
        // 受限资源配置列表
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 对静态资源设置匿名访问
        filterChainDefinitionMap.put("/img/**","anon");
        // 退出logout地址，shiro去清除session
        filterChainDefinitionMap.put("/logout", "logout");
        // 不需要拦截的访问
        filterChainDefinitionMap.put("/test/**","anon");
        filterChainDefinitionMap.put("/user/login","anon");
        // 注册相关
        filterChainDefinitionMap.put("/register.jsp", "anon");
        filterChainDefinitionMap.put("/user/register", "anon");

        // 需拦截验证页面
        filterChainDefinitionMap.put("/**","authc");
        filterChainDefinitionMap.put("/index.jsp","authc");
        filterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return filterFactoryBean;
    }


    /**
     * 创建shiro安全管理器（权限管理）
     */
    @Bean
    public DefaultWebSecurityManager getSecurityManager(UserRelam userRelam) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        // 注入realm
        defaultWebSecurityManager.setRealm(userRelam);
        return defaultWebSecurityManager;
    }


    /**
     * 自定义Realm
     */
    @Bean
    public UserRelam getRealm(HashedCredentialsMatcher hashedCredentialsMatcher) {
        UserRelam userRelam = new UserRelam();
        userRelam.setCredentialsMatcher(hashedCredentialsMatcher);

        return userRelam;
    }


    /**
     * 凭证匹配器 密码验证
     */
    @Bean
    public HashedCredentialsMatcher getCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        // 散列的次数，比如散列两次，相当于 md5(md5(""));
        hashedCredentialsMatcher.setHashIterations(1024);
        // storedCredentialsHexEncoded默认是true，此时用的是密码加密用的是Hex编码；false时用Base64编码
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);

        return hashedCredentialsMatcher;
    }
}
