package com.atguigu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

/**
 * @author: c
 * @date: 2020/6/25
 */

@Service
public class PaymentSerice {

    // ================ 服务降级 ==================
    // 核心是放回友好的失败提示
    public String paymentInfo_OK(Integer id) {
        return "线程池：" + Thread.currentThread().getName() + "  paymentInfo_OK,id = " + id + "\t" + "O(∩_∩)O哈哈~";
    }

    // 当前方法出现错误时，调用 paymentInfo_TimeOutHandler 兜底（可以返回一些友好信息，防止引起其他问题）
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000") //触发条件，处理时间大于3秒，则执行fallback(paymentInfo_TimeOutHandler)
    })
    public String paymentInfo_TimeOut(Integer id) {

        // 1.测试程序出现异常，能否触发
        // int n = 10 / 0;

        // 2.休眠时间>3秒，直接服务降级
        int t = 5;
        try {
            TimeUnit.SECONDS.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "线程池：" + Thread.currentThread().getName() + "  paymentInfo_TimeOut,id = " + id + "\t" + "O(∩_∩)O哈哈~" + " \t 耗时（秒）：" + t;
    }

    // 兜底方法(fallback)
    public String paymentInfo_TimeOutHandler(Integer id) {
        return "o(╥﹏╥)o  调用服务响应超时/服务异常。线程池：" + Thread.currentThread().getName() + " paymentInfo_TimeOutHandler, id = " + id + " \t  o(╥﹏╥)o";
    }


    // ================ 服务熔断 ==================
    // 核心:发生熔断 --> 服务降级（友好提示） --> 后续会试探性恢复（可以成功恢复正常，不成功继续熔断）
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            // 在 10秒内，至少访问10 次，访问失败率>60%, 则发生服务熔断
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),  //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),   //请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),  //时间范围
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")     // 失败率
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        if (id < 0)
            throw new RuntimeException("----------------- id 为负数，非法！");
        //使用 hutool 工具包生成UUID
        String number = IdUtil.simpleUUID();
        return Thread.currentThread().getName() + "\t" + "调用成功,流水号：" + number;
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id) {
        return "id 不能负数，请稍候再试,(┬＿┬)/~~     id: " + id;
    }

}
