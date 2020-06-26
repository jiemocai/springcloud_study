package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.lb.LoadBalance;
import com.atguigu.springcloud.lb.impl.MyLB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

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

    // @Resource 安装名称装配， @Autowire 按照 类型装配
    //@Resource(type = MyLB.class)
    @Resource
    private LoadBalance loadBalance;

    @Resource
    private DiscoveryClient discoveryClient;

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


    /*
     * RestTemplate 说明
     *
     * getForObject(), 返回响应体中数据转化成的对象，是一个JSON
     * getForEntity(), 返回消息全部(ResponseEntity 对象)，包含消息状态码，头，响应体等
     *
     * */

    @GetMapping("/consumer/payment/getForEntity/findAll")
    public CommonResult<Payment> findAll2() {
        log.info("-------------消费者80，查询全部记录");

        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/findAll", CommonResult.class);
        // 状态码
        log.info(entity.getStatusCode().toString());
        // 消息头
        log.info(entity.getHeaders().toString());
        if (entity.getStatusCode().is2xxSuccessful()) {

            return entity.getBody();
        } else {
            return new CommonResult<>(444, "访问失败！");
        }
    }


    @GetMapping("/consumer/payment/postForEntity/create")
    public CommonResult<Payment> create1(Payment payment) {
        ResponseEntity<CommonResult> entity = restTemplate.postForEntity(PAYMENT_URL + "/payment/create", payment, CommonResult.class);
        log.info(entity.getStatusCode().toString());

        if (entity.getStatusCode().is2xxSuccessful())
            return entity.getBody();
        else
            return new CommonResult<>(444, "新增失败！");

    }

    /**
     * 自定义负载均衡测试
     *
     * @author: c
     * @date: 2020/6/22 22:18
     * @Param: null
     * @return:
     */
    @GetMapping(value = "/consumer/payment/lb")
    public String getPaymentBL() {

        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");

        if (instances == null || instances.size() <= 0) {
            log.error("************* \t 无可用服务！");
            return null;
        }

        ServiceInstance serviceInstance = loadBalance.instances(instances);
        URI uri = serviceInstance.getUri();
        return restTemplate.getForObject(uri + "/payment/lb", String.class);
    }

}
