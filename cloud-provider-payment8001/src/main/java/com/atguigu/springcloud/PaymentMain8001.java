package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author: c
 * @date: 2020/6/10 15:07
 * @Param: null
 * @return:
 */

@SpringBootApplication

// 开启的是eureka的客户端
@EnableEurekaClient
// 开启服务发现
@EnableDiscoveryClient

public class PaymentMain8001 {

    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8001.class, args);
    }

}
