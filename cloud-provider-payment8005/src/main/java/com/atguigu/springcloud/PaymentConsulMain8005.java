package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author: c
 * @date: 2020/6/17
 */


@SpringBootApplication
@EnableDiscoveryClient
public class PaymentConsulMain8005 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentConsulMain8005.class, args);
    }
}
