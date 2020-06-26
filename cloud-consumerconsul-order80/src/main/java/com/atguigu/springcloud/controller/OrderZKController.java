package com.atguigu.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author: c
 * @date: 2020/6/10
 */

@RestController
@Slf4j
public class OrderZKController {

    // 注意大小写敏感
    public static final String INVOKE_URL = "http://cloud-provider-payment";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/consumer/payment/consul")
    public String getInfo() {
        return restTemplate.getForObject(INVOKE_URL + "/payment/consul", String.class);
    }

}
