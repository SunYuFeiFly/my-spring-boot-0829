package com.atguigu.springcloud.loadBalancer;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * @author syf_12138
 * @Description 自定义轮训规则接口
 * @create 2022/8/23 14:32
 */

public interface LoadBalancer {

    ServiceInstance instances(List<ServiceInstance> serviceInstances);
}
