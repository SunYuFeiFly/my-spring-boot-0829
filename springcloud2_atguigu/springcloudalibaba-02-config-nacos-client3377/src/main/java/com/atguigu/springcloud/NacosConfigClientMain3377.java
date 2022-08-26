package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author syf_12138
 * @Description 三种配置方案说明
 *              1、DataId方案
 *                  在nacos创建配置文件不用特意修改分组（Group），只写Data ID即可
 *                  我们从bootstrap.yml和application.yml配置文件中获取值根据公式${spring.application.name}-${spring.profiles.active}.${spring.cloud.nacos.config.file-extension0}组装nacos中配置文件名
 *                  ${spring.application.name} -> nacos-config-client
 *                  ${spring.profiles.active} -> dev
 *                  ${spring.cloud.nacos.config.file-extension0} -> yaml
 *                  -> nacos-config-client-dev.yaml
 *              2、group方案
 *                  在nacos创建配置文件,文件名相同，均为${spring.application.name}-${spring.profiles.active}.${spring.cloud.nacos.config.file-extension0}组装名，但要根据不同的环境设置不同的group，
 *                  如test -> TEST_GROUP, prod -> PROD_GROUP.
 *                  本地bootstrap.yml配置多group一项，内容与想读取的group内容相同
 *                  最终，我们nacos有多个相同的配置文件名，但group不同，根据我们配置文件里group去选择对应配置文件
 *                  文件名均为${spring.application.name}-${spring.profiles.active}.${spring.cloud.nacos.config.file-extension0}
 *                  如：nacos-config-client-info.yaml  group: TEST_GROUP
 *              3、nameSpace方案
 *                  在group方案基础上添加nameSpace配置项，根据配置找对应nameSpace里面group分组${spring.application.name}-${spring.profiles.active}.${spring.cloud.nacos.config.file-extension0}组装名的文件即可
 * @create 2022/8/26 10:35
 */

@EnableDiscoveryClient
@SpringBootApplication
public class NacosConfigClientMain3377 {

    public static void main(String[] args) {
        SpringApplication.run(NacosConfigClientMain3377.class, args);
    }
}
