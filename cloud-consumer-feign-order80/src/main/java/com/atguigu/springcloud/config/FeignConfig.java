package com.atguigu.springcloud.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: c
 * @date: 2020/6/25
 */
@Configuration
public class FeignConfig {

    @Bean
    Logger.Level feignLogLevel() {
        //return Logger.Level.FULL;
        return Logger.Level.BASIC;
    }

}
