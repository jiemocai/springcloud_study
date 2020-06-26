package com.atguigu.springcloud.service;


import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.service.impl.PaymentFallbackService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
// 通过，fallback = PaymentFallbackService.class 对每一个接口进行服务降级（加强）
@FeignClient(value = "CLOUD-PROVIDER-HYSTRIX-PAYMENT", fallback = PaymentFallbackService.class)
public interface PaymentHystrixService {

    @GetMapping(value = "/payment/hystrix/ok/{id}")
    public CommonResult paymentInfo_OK(@PathVariable("id") Integer id);

    @GetMapping(value = "/payment/hystrix/timeout/{id}")
    public CommonResult paymentInfo_TimeOut(@PathVariable("id") Integer id);

}
