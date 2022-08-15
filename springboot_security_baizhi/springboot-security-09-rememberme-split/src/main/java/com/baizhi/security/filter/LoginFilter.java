package com.baizhi.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author syf_12138
 * @Description 自定义前后端系统用户信息校验过滤器
 * @create 2022/8/15 15:20
 */

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * 此时前端利用ajax或是vue发送请求，数据是json格式（此方法用于覆盖实现org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.attemptAuthentication()方法）
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 1.判断是否是 post 方式请求
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        // 2.判断是否是 json 格式请求类型
        if (request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
            try {
                // 3.从 json 数据中获取用户输入用户名和密码进行认证 {"uname":"xxx","password":"xxx"}
                Map<String, String> userInfo = new ObjectMapper().readValue(request.getInputStream(), Map.class);
                String username = userInfo.get(getUsernameParameter());
                username = username != null ? username.trim() : "";
                String password = userInfo.get(getPasswordParameter());
                password = password != null ? password : "";
                // 这里多加一步，从请求json中获取remember-me的值，放在request中，方便后面从request中获取该参数值，***request.getInputStream()只能获取一次
                String rememberValue = userInfo.get(AbstractRememberMeServices.DEFAULT_PARAMETER);
                rememberValue = rememberValue != null ? rememberValue : "";
                if (!ObjectUtils.isEmpty(rememberValue)) {
                    request.setAttribute(AbstractRememberMeServices.DEFAULT_PARAMETER, rememberValue);
                }
                System.out.println("用户名: " + username + " 密码: " + password + " 是否记住我: " + rememberValue);
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
                // 会把我们当前请求request中的session、ip等放到token中
                this.setDetails(request, authRequest);
                // 此处对应MySecurityConfigure.loginFilter().setAuthenticationManager()
                return this.getAuthenticationManager().authenticate(authRequest);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
