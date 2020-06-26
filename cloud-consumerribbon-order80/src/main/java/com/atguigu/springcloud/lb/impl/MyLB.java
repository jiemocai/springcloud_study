package com.atguigu.springcloud.lb.impl;

import com.atguigu.springcloud.lb.LoadBalance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: c
 * @date: 2020/6/22
 */

@Component
public class MyLB implements LoadBalance {

    //整型原子类
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    /**
     * 返回第几次访问，要防止高并发，采用自旋锁解决
     *
     * @author: c
     * @date: 2020/6/22 22:13
     * @Param: null
     * @return:
     */
    public final int getAndIncrement() {
        int current;
        int next;

        do {
            // 1.获取原始值
            current = this.atomicInteger.get();

            // 2.期望值，希望改为的值（改时不能有其他线程操作，保证一致性，原子性）
            // 预防整型越界（intMAX = 2147483647）
            next = current > 2147483647 ? 0 : current + 1;

            System.out.println("******************* \t current = " + current + "  \t  next = " + next);
            /*
             * compareAndSet(current, next)
             * 将 current和内存中的value比较，如果没有变，则将 next 赋给 current 返回true，
             *   否者什么都不做，返回 false ,进入自旋锁
             * */

        } while (!atomicInteger.compareAndSet(current, next));
        System.out.println("*******************  \t第几次访问，此次数next = " + next);
        return next;
    }

    @Override
    public ServiceInstance instances(List<ServiceInstance> instances) {

        int index = getAndIncrement() % instances.size();

        return instances.get(index);
    }
}
