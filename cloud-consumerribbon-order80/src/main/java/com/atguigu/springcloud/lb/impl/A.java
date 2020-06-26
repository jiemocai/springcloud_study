package com.atguigu.springcloud.lb.impl;

import com.atguigu.springcloud.lb.LoadBalance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: c
 * @date: 2020/6/22
 */
//@Component
public class A implements LoadBalance {
    @Override
    public ServiceInstance instances(List<ServiceInstance> instances) {
        return null;
    }
}
