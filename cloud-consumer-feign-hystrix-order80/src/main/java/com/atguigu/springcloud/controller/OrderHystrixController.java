package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
// 问题一：如果对每一个方法都写一个兜底方法（理论上可行,实际很蠢），代码膨胀，可以简单的统一处理，复杂的自定义
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")
public class OrderHystrixController {
    @Resource
    private PaymentHystrixService paymentHystrixService;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public CommonResult paymentInfo_OK(@PathVariable("id") Integer id) {
        CommonResult result = paymentHystrixService.paymentInfo_OK(id);
        log.info("*******result:" + result);
        return result;
    }

    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
    @HystrixCommand(fallbackMethod = "paymentTimeOutFallbackMethod", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500") //服务消费者，要求可能更苛刻<3m秒(此时openfeignd的默认响应超时不在生效)
    })
    public CommonResult paymentInfo_TimeOut(@PathVariable("id") Integer id) {
        CommonResult result = paymentHystrixService.paymentInfo_TimeOut(id);
        log.info("*******result:" + result);
        return result;
    }

    //兜底方法（特殊定制）
    public CommonResult paymentTimeOutFallbackMethod(@PathVariable("id") Integer id) {
        return new CommonResult(400, "我是消费者80，对付支付系统繁忙请10秒钟后再试或者自己运行出错请检查自己,(┬＿┬)");
    }

    @GetMapping("/consumer/payment/hystrix/global/{id}")
    @HystrixCommand // 什么都不写，采用全局的降级方法
    public CommonResult paymentInfo_global(@PathVariable("id") Integer id) {

        //  int n = 10 / 0;

        CommonResult result = paymentHystrixService.paymentInfo_TimeOut(id);
        return result;
    }

    //下面是全局fallback方法（简单的通用）
    public CommonResult payment_Global_FallbackMethod() {
        return new CommonResult(400, "Global异常处理信息，请稍后再试,(┬＿┬)");
    }
}
