package com.tensquare.controller;

import com.tensquare.model.User;
import com.tensquare.service.UserService;
import com.tensquare.util.ResultObject;
import com.tensquare.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    //一个测试方法
    @GetMapping
    public ResultObject test(){
        List<User> users=userService.findAll();
        return new ResultObject(true, StatusCode.OK, "测试成功",users);
    }

    //发送验证码
    @PostMapping("/sendsms/{mobile}")
    public ResultObject sendsms(@PathVariable String mobile){
        userService.sendsms(mobile);
        return new ResultObject(true, StatusCode.OK,"发送成功");
    }
    //注册用户
    @PostMapping("/regidter/{code}")
    public ResultObject regidter(@PathVariable String code, @RequestBody User user){
        String msg=userService.regidter(code,user);
        return new ResultObject(true, StatusCode.OK,msg);
    }
    //登录
    @PostMapping("/login")
    public ResultObject login(@RequestBody User user){
        return userService.login(user);
    }
    //删除用户
    @DeleteMapping("/{userId}")
    public ResultObject delete(@PathVariable String userId){
        return userService.delete(userId);
    }
}
