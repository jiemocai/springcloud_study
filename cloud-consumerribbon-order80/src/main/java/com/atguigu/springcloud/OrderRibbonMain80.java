package com.atguigu.springcloud;

import com.atguigu.myrule.MyselfRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

/**
 * @author: c
 * @date: 2020/6/10
 */

@SpringBootApplication

// eureka 开启客户端
@EnableEurekaClient

// 替换ribbon默认轮询负载均衡方式 为 随机方式，应用在服务 CLOUD-PAYMENT-SERVICE上
// @RibbonClient(name = "CLOUD-PAYMENT-SERVICE",configuration = MyselfRule.class)
public class OrderRibbonMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderRibbonMain80.class, args);
    }
}
