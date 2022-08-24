package com.atguigu.springcloud.service.impl;

import cn.hutool.core.util.IdUtil;
import com.atguigu.springcloud.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

/**
 * @author syf_12138
 * @Description Hystrix生产者服务接口实现类
 * @create 2022/8/23 17:15
 */

@Service
public class PaymentServiceImpl implements PaymentService {

    /**
     * 正常访问，一切OK
     */
    @Override
    public String paymentInfoOK(Integer id) {
        return "线程池:" + Thread.currentThread().getName() + "paymentInfo_OK,id: " + id + "\t" + "O(∩_∩)O";
    }


    /**
     * 超时访问，演示降级
     */
    @Override
    @HystrixCommand(fallbackMethod = "paymentInfoTimeOutFallbackMethod", commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100")})
    public String paymentInfoTimeOut(Integer id) {
        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池:" + Thread.currentThread().getName() + " paymentInfo_TimeOut,id: " + id + "\t" + "O(∩_∩)O，耗费2秒";
    }

    /**
     * 兜底方法，当paymentInfoTimeOut（）执行异常或运行时间超过@HystrixProperty定义时长，均执行次方法返回数据
     */
    public String paymentInfoTimeOutFallbackMethod(Integer id) {
        return "线程池:  " + Thread.currentThread().getName() + "  8001系统繁忙或者运行报错，请稍后再试,id:  " + id + "\t" + "o(╥﹏╥)o";
    }


    /**
     * 服务熔断测试
     * circuitBreaker.enabled: 是否开启熔断器
     * circuitBreaker.requestVolumeThreshold: 请求次数
     * circuitBreaker.sleepWindowInMilliseconds: 时间窗口期
     * circuitBreaker.errorThresholdPercentage: 失败率达到多少后跳闸
     * 含义：在10秒内请求次数大于10次，且失败率大于60%则开启服务熔断
     */
    @HystrixCommand(fallbackMethod = "paymentCircuitBreakerFallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),
    })
    @Override
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        if (id < 0) {
            throw new RuntimeException("******id 不能负数");
        }
        String serialNumber = IdUtil.simpleUUID();

        return Thread.currentThread().getName() + "\t" + "调用成功，流水号: " + serialNumber;
    }

    public String paymentCircuitBreakerFallback(@PathVariable("id") Integer id) {
        return "id 不能负数，请稍后再试，/(ㄒoㄒ)/~~   id: " + id;
    }
}
