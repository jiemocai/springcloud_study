package com.atguigu.springcloud.controller;

/**
 * @author: c
 * @date: 2020/6/24
 */

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentFeignService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class OrderFeignController {

    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping(value = "/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        return paymentFeignService.get(id);
    }

    @GetMapping(value = "/consumer/payment/findAll")
    public CommonResult<Payment> getAll() {
        return paymentFeignService.create();
    }

    @GetMapping(value = "/consumer/payment/lb")
    public String lb() {
        return paymentFeignService.getPaymentBL();
    }

    @GetMapping(value = "/consumer/payment/feign/timeout")
    public String paymentFeignTimeout() {
        return paymentFeignService.paymentFeignTimeout();
    }
}
