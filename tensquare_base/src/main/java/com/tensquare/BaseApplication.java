package com.tensquare;

import com.tensquare.util.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class BaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class);
    }
    @Bean
    public IdWorker idWorker(){
        return new IdWorker();
    }
}
