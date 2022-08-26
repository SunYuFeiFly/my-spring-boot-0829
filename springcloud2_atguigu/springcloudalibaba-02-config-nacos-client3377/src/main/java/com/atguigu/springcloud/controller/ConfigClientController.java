package com.atguigu.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author syf_12138
 * @Description 动态获取配置数据测试类
 *              @RefreshScope:在控制器类加入@RefreshScope注解使当前类下的配置支持Nacos的动态刷新功能
 * @create 2022/8/26 10:35
 */

@RestController
@RefreshScope
public class ConfigClientController {

    @Value("${config.info}")
    private String configInfo;

    @GetMapping("/config/info")
    public String getConfigInfo() {
        return configInfo;
    }
}
