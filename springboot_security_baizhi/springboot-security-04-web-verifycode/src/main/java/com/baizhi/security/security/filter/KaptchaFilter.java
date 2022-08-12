package com.baizhi.security.security.filter;

import com.baizhi.security.security.exception.KaptchaNotMatchException;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author syf_12138
 * @Description 自定义验证码校验过滤器
 * @create 2022/8/12 16:32
 */

public class KaptchaFilter extends UsernamePasswordAuthenticationFilter {

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
        // 2、从请求中获取输入验证码
        String verifyCode = request.getParameter(getKaptchaParameter());
        // 3、与session中获取验证码对比
        String sessionVerifyCode = (String) request.getSession().getAttribute("kaptcha");
        if (!ObjectUtils.isEmpty(verifyCode) && !ObjectUtils.isEmpty(sessionVerifyCode) && verifyCode.equalsIgnoreCase(sessionVerifyCode)) {
            // 调用父类验证器
            return super.attemptAuthentication(request, response);
        }

        // 抛出验证码比匹配异常
        throw new KaptchaNotMatchException("验证码不匹配!");
    }
}
