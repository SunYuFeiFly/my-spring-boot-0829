package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entity.Payment;

/**
 * @author syf_12138
 * @Description 支付服务接口
 * @create 2022/8/22 12:03
 */

public interface PaymentService {

    public int create(Payment payment);

    public Payment getPaymentById(Long id);
}
