package com.tensquare.util;

import lombok.Data;

/**
 * 响应结果
 */
@Data
public class ResultObject {
    private boolean flag;
    private Integer code;
    private String message;
    private Object data;

    public ResultObject(boolean flag, Integer code, String message, Object data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResultObject(boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }
}
