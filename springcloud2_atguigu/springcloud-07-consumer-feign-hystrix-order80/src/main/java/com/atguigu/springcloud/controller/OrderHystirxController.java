package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author syf_12138
 * @Description Hystrix消费者控制类（注意消费端调用提供端服务中间结果返回也需要时间，所以我们设置的超时时间应把此部分时间计算入内）
 * @create 2022/8/23 17:36
 */

@Slf4j
@RestController
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")
public class OrderHystirxController {

    @Autowired
    private PaymentHystrixService paymentHystrixService;

    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String paymentInfoOK(@PathVariable("id") Integer id) {
        String result = paymentHystrixService.paymentInfoOK(id);
        return result;
    }

    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
    /** @HystrixCommand(fallbackMethod = "paymentTimeOutFallbackMethod", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500")
    }) */
    @HystrixCommand
    public String paymentInfoTimeOut(@PathVariable("id") Integer id) {
        // int age = 10 / 0;
        long start = System.currentTimeMillis();
        String result = paymentHystrixService.paymentInfoTimeOut(id);
        log.info("消费端调用时间为：{}", System.currentTimeMillis() - start);
        log.info("result:{}", result);
        return result;
    }

    /**
     * 兜底方法，当paymentInfoTimeOut()方法运行时间超过设置时长货车发生运行异常，则执行次方法作为返回数据
     */
    public String paymentTimeOutFallbackMethod(@PathVariable("id") Integer id) {
        return "我是消费者80,对方支付系统繁忙请10秒钟后再试或者自己运行出错请检查自己,o(╥﹏╥)o";
    }


    /**
     * 全局fallback方法(@HystrixCommand指定回滚方法则发生异常、超时由特定放法解决，没有特别说明的，都用此全局方法处理)
     */
    public String payment_Global_FallbackMethod() {
        return "Global异常处理信息，请稍后再试，/(ㄒoㄒ)/~~";
    }

    /**
     * 以上配置均为服务降级方法：五路如何均先访问正常方法，当出现异常、超时才访问fallback()
     * 以下为服务熔断测试：当请求次数过多且失败比列超过一定比列后，相同的请求不再过正常方法，直接执行fallback()方法返回数据。
     */
}
