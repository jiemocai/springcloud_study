package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author: c
 * @date: 2020/6/10
 */
@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    // 注意是  org.springframework.cloud.client.discovery.DiscoveryClient;
    private DiscoveryClient discoveryClient;

    @PostMapping(value = "/payment/create")
    //@RequestBody 保证数据的获取，要封装
    public CommonResult create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info("-------新增结果：" + result);
        log.info("-------新增数据:" + payment.toString());
        if (result > 0) {
            return new CommonResult(200, "新增数据成功！", result);
        } else
            return new CommonResult(444, "新增数据失败！", null);

    }


    @GetMapping(value = "/payment/get/{id}")
    public CommonResult get(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("-------查询结果：" + payment);
        if (payment != null)
            return new CommonResult(200, "查询数据成功！", payment);
        else
            return new CommonResult(444, "没有对应的记录，查询id = " + id, null);

    }

    @GetMapping(value = "/payment/findAll")
    public CommonResult create() {
        List<Payment> list = paymentService.findAll();
        log.info("-------查询结果：" + list);
        if (list.size() > 0)
            return new CommonResult(200, "查询数据成功！ serverPort:" + serverPort, list);
        else
            return new CommonResult(444, "无记录！", null);

    }

    /**
     * 查看 eureka中注册的服务信息
     *
     * @author: c
     * @date: 2020/6/13 13:09
     * @Param: null
     * @return:
     */
    @GetMapping("/payment/discovery")
    public Object discovery() {
        List<String> services = discoveryClient.getServices();
        for (String element : services) {
            log.info("*******************  server : " + element);

            List<ServiceInstance> instances = discoveryClient.getInstances(element);
            for (ServiceInstance serviceInstance : instances)
                log.info("*******************" + serviceInstance.getServiceId() + "\t" + serviceInstance.getHost() + "\t" + serviceInstance.getPort() + "\t" + serviceInstance.getUri());
        }
        return discoveryClient;
    }

    /**
     * 自定义轮询算法的测试，直接返回服务提供者的接口
     *
     * @author: c
     * @date: 2020/6/22 21:39
     * @Param: null
     * @return:
     */
    @GetMapping(value = "/payment/lb")
    public String getPaymentBL() {
        return serverPort;
    }


    /**
     * 验证feign的访问超时，默认访问的请求回复时间是 1s 此处休眠3s
     *
     * @author: c
     * @date: 2020/6/25 11:08
     * @Param: null
     * @return:
     */
    @GetMapping(value = "/payment/feign/timeout")
    public String paymentFeignTimeout() {

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return serverPort;
    }

}
