package com.tensquare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableEurekaClient//注册进eureka中心
@EnableZuulProxy//开启路由
public class ManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class);
    }
}
