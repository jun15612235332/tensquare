package com.tensquare.util;

import lombok.Data;

/**
 * 自定义异常类
 */
@Data
public class CustomException extends RuntimeException {
    private int code;
    private String msg;
    public CustomException(int code,String msg){
        this.code=code;
        this.msg=msg;
    }
}
