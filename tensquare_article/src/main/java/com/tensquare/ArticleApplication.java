package com.tensquare;

import com.tensquare.util.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling//开启定时调度
@EnableEurekaClient
public class ArticleApplication {
    public static void main(String[] args) {
        SpringApplication.run(ArticleApplication.class);
    }
    @Bean
    public IdWorker idWorker(){
        return new IdWorker();
    }
}
