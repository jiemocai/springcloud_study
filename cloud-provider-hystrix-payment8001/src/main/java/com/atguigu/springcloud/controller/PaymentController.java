package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.service.PaymentSerice;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: c
 * @date: 2020/6/25
 */
@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentSerice paymentSerice;

    @GetMapping(value = "/payment/hystrix/ok/{id}")
    public CommonResult paymentInfo_OK(@PathVariable("id") Integer id) {
        String result = paymentSerice.paymentInfo_OK(id);
        log.info("----------------" + result);
        return new CommonResult(200, "调用:hystrix-payment8001 -> paymentInfo_OK", result);
    }

    @GetMapping(value = "/payment/hystrix/timeout/{id}")
    public CommonResult paymentInfo_TimeOut(@PathVariable("id") Integer id) {
        String result = paymentSerice.paymentInfo_TimeOut(id);
        log.info("----------------" + result);
        return new CommonResult(200, "调用:hystrix-payment8001 -> paymentInfo_TimeOut", result);
    }

    @GetMapping(value = "/payment/hystrix/circuit/{id}")
    public CommonResult paymentCircuitBreaker(@PathVariable("id") Integer id) {
        String result = paymentSerice.paymentCircuitBreaker(id);
        log.info("----------------" + result);
        return new CommonResult(200, "调用:hystrix-payment8001 -> paymentInfo_TimeOut", result);
    }

}
