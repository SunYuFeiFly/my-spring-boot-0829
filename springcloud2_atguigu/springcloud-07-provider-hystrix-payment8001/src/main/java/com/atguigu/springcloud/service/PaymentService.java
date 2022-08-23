package com.atguigu.springcloud.service;

/**
 * @author syf_12138
 * @Description Hystrix生产者服务接口
 * @create 2022/8/23 17:15
 */

public interface PaymentService {

    /**
     * 正常访问，一切OK
     */
    public String paymentInfo_OK(Integer id);

    /**
     * 超时访问，演示降级
     */
    public String paymentInfo_TimeOut(Integer id);

}
