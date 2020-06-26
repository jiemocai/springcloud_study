package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author: c
 * @date: 2020/6/12
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekeMain7003 {

    public static void main(String[] args) {
        SpringApplication.run(EurekeMain7003.class, args);
    }
}
