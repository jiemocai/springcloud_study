package com.atguigu.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author: c
 * @date: 2020/6/10
 */
@Configuration
public class ApplicationContextConfig {

    @Bean
    // @LoadBalanced   // 自定义时，一定要记得注释
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}
