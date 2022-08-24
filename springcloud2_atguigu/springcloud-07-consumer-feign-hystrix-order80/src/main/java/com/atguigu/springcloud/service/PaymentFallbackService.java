package com.atguigu.springcloud.service;

import org.springframework.stereotype.Component;

/**
 * @author syf_12138
 * @Description 统一异常处理类
 * @create 2022/8/24 14:38
 */

@Component
public class PaymentFallbackService implements PaymentHystrixService {

    @Override
    public String paymentInfoOK(Integer id) {
        return "-----PaymentFallbackService fall back-paymentInfo_OK ,o(╥﹏╥)o";
    }

    @Override
    public String paymentInfoTimeOut(Integer id) {
        return "-----PaymentFallbackService fall back-paymentInfo_TimeOut ,o(╥﹏╥)o";
    }
}
