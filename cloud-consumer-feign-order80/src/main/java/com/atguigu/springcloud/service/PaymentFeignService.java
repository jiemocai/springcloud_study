package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
// 当前调哪个服务的哪些服务接口
// value 指明服务提供者
@FeignClient(value = "CLOUD-PAYMENT-SERVICE")
public interface PaymentFeignService {

    // 此处模仿服务提供者的 service 层，但是对外的接口一般是在controller中，故直接借鉴controller代码
    @GetMapping(value = "/payment/lb")
    public String getPaymentBL();

    @GetMapping(value = "/payment/findAll")
    public CommonResult create();

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult get(@PathVariable("id") Long id);

    @GetMapping(value = "/payment/feign/timeout")
    public String paymentFeignTimeout();
}
