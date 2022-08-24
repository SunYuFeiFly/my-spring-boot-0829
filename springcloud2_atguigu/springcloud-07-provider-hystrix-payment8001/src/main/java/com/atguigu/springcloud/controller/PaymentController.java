package com.atguigu.springcloud.controller;


import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author syf_12138
 * @Description Hystrix生产者控制类
 * @create 2022/8/23 17:16
 */

@Slf4j
@RestController
@RequestMapping("/hystrixPayment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;


    /**
     * 正常访问，一切OK
     */
    @GetMapping("/hystrix/ok/{id}")
    public String paymentInfoOK(@PathVariable("id") Integer id) {
        String result = paymentService.paymentInfoOK(id);
        log.info("****result: " + result);
        return result;
    }

    /**
     * 超时访问，演示降级
     */
    @GetMapping("/hystrix/timeout/{id}")
    public String paymentInfoTimeOut(@PathVariable("id") Integer id) throws InterruptedException {
        long start = System.currentTimeMillis();
        String result = paymentService.paymentInfoTimeOut(id);
        log.info("提供端调用时间为：{}", System.currentTimeMillis() - start);
        log.info("****result: " + result);
        return result;
    }


    /**
     * 演示服务熔断
     */
    @GetMapping("/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id)
    {
        String result = paymentService.paymentCircuitBreaker(id);
        log.info("****result: "+result);
        return result;
    }

}

