package com.baizhi.security.security.exception;

import javax.security.sasl.AuthenticationException;

/**
 * @author syf_12138
 * @Description 自定义验证码认证异常
 * @create 2022/8/12 16:52
 */

public class KaptchaNotMatchException extends AuthenticationException {

    public KaptchaNotMatchException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public KaptchaNotMatchException(String msg) {
        super(msg);
    }

}
