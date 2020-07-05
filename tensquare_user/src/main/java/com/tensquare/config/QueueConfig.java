package com.tensquare.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {
    /**
     * 发送短信对列
     */
    @Bean
    public Queue getQueue(){
        return new Queue("sms");
    }
}
