package com.atguigu.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: c
 * @date: 2020/6/10
 */

@Data
@AllArgsConstructor  // 全参数
@NoArgsConstructor   // 空参数
public class CommonResult<T> {
    //例如： 404 not_found

    private Integer code;
    private String message;
    private T data;

    public CommonResult(Integer code, String message) {
        this(code, message, null);
    }

}
