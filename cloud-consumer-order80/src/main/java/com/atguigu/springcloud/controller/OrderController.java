package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author: c
 * @date: 2020/6/10
 */

@RestController
@Slf4j
public class OrderController {

    //单机版，地址唯一写死
    //public static final String PAYMENT_URL = "http://localhost:8001";

    //集群版，会根据euraka中注册的服务去执行，与域名和端口无关，
    // 要注意负载均衡的策略， @LoadBalanced
    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";


    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment) {
        log.info("-------------消费者80，新增数据成功！");
        log.info("-------------消费者80，新增数据:" + payment.toString());
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create", payment, CommonResult.class);
    }

    @GetMapping("/consumer/payment/query/{id}")
    public CommonResult<Payment> query(@PathVariable("id") Long id) {
        log.info("-------------消费者80，查询id = " + id);
        return restTemplate.getForObject(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
    }

    @GetMapping("/consumer/payment/findAll")
    public CommonResult<Payment> findAll() {
        log.info("-------------消费者80，查询全部记录");
        return restTemplate.getForObject(PAYMENT_URL + "/payment/findAll", CommonResult.class);
    }
}
