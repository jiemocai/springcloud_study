package com.atguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * 自定义负载均衡实现类的接口
 *
 * @author: c
 * @date: 2020/6/22 21:42
 * @Param: null
 * @return:
 */
public interface LoadBalance {
    /**
     * 注意： 要去除  com.atguigu.springcloud.config.ApplicationContextConfig() 中的
     * 返回本次请求，对应将去访问的服务； 轮询算法的简版（访问次数 % 服务总数）
     *
     * @author: c
     * @date: 2020/6/22 21:44
     * @Param: null
     * @return:
     */
    ServiceInstance instances(List<ServiceInstance> instances);

}
