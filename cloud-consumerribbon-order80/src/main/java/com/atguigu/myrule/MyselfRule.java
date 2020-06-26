package com.atguigu.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: c
 * @date: 2020/6/17
 */
@Configuration
public class MyselfRule {

    /**
     * 替换默认的轮询方式负载均衡，改为随机方式
     *
     * @author: c
     * @date: 2020/6/17 22:04
     * @Param: null
     * @return:
     */

    @Bean
    public IRule myRule() {
        return new RandomRule();

    }
}
