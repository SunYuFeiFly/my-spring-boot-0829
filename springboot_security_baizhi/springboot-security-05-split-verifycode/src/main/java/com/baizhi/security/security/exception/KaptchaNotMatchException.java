package com.baizhi.security.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @Author syf_12138
 * @Description 自定义用户信息异常
 * @Date 2022/8/14 17:37
 **/

public class KaptchaNotMatchException extends AuthenticationException {

    public KaptchaNotMatchException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public KaptchaNotMatchException(String msg) {
        super(msg);
    }
}
