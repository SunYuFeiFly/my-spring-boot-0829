package com.baizhi.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Properties;

@SpringBootApplication
public class SpringbootSecurity01CustomApplication {

    public static void main(String[] args) {
        // System.setProperty("spring.security.strategy","MODE_INHERITABLETHREADLOCAL");
        SpringApplication.run(SpringbootSecurity01CustomApplication.class, args);
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }

}
