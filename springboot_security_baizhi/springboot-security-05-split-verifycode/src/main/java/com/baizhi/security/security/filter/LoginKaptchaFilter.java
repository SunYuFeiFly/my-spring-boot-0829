package com.baizhi.security.security.filter;

import com.baizhi.security.security.exception.KaptchaNotMatchException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author syf_12138
 * @Description 自定义用户信息、验证码校验过滤器
 * @Date 2022/8/14 16:52
 **/

public class LoginKaptchaFilter extends UsernamePasswordAuthenticationFilter {

    private static final String FORM_KAPTCHA_KEY = "kaptcha";

    private String kaptchaParameter = FORM_KAPTCHA_KEY;

    public String getKaptchaParameter() {
        return kaptchaParameter;
    }

    public void setKaptchaParameter(String kaptcha) {
        this.kaptchaParameter = kaptcha;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 1.判断是否是 post 方式请求
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        try {
            // 2.获取请求中输入的数据
            Map<String, String> userInfo = new ObjectMapper().readValue(request.getInputStream(), Map.class);
            // 2.1、获取输入用户名
            String password = userInfo.get(getPasswordParameter());
            // 2.2、获取输入密码
            String username = userInfo.get(getUsernameParameter());
            // 2.3、获取输入验证码
            String verifyCode = userInfo.get(getKaptchaParameter());
            // 3.从请求session中获取输入验证码
            String sessionVerifyCode = (String) request.getSession().getAttribute("kaptcha");
            // 4.验证码验证
            if (!ObjectUtils.isEmpty(verifyCode) && !ObjectUtils.isEmpty(sessionVerifyCode) && verifyCode.equalsIgnoreCase(sessionVerifyCode)) {
                // 5.用户名、密码验证
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
                setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 抛出验证码比匹配异常
        throw new KaptchaNotMatchException("验证码不匹配!");
    }
}
