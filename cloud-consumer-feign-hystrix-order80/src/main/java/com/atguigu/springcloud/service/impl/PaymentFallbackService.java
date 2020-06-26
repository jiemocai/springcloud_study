package com.atguigu.springcloud.service.impl;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.service.PaymentHystrixService;
import org.springframework.stereotype.Component;

/**
 * 问题二：
 * 由于服务调用者使用的openfeign,即通过一个接口调用服务提供者提供的全部方法，
 * 在此处直接处理，进行服务降级，可以避免对业务逻辑的影响
 *
 * @author: c
 * @date: 2020/6/25
 */
@Component
public class PaymentFallbackService implements PaymentHystrixService {

    @Override
    public CommonResult paymentInfo_OK(Integer id) {
        return new CommonResult(400, "-----PaymentFallbackService fall back-paymentInfo_OK , (┬＿┬)");
    }

    @Override
    public CommonResult paymentInfo_TimeOut(Integer id) {
        return new CommonResult(400, "-----PaymentFallbackService fall back-paymentInfo_TimeOut , (┬＿┬)");
    }
}