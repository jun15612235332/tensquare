package com.tensquare.util;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@RestControllerAdvice
public class ExceptionHandlerUtil {
    @ExceptionHandler(Exception.class)
    public ResultObject error(Exception e){
        e.printStackTrace();
        return new ResultObject(false, StatusCode.ERROR,"操作失败");
    }
}
