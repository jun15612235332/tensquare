package com.tensquare.controller;

import com.tensquare.model.Admin;
import com.tensquare.service.AdminService;
import com.tensquare.util.ResultObject;
import com.tensquare.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    /**
     * 增加管理员
     */
    @PostMapping
    public ResultObject add(@RequestBody Admin admin){
        adminService.add(admin);
        return new ResultObject(true, StatusCode.OK,"添加成功");
    }
    /**
     * 登录
     */
    @PostMapping("/login")
    public ResultObject login(@RequestBody Admin admin){
        return adminService.login(admin);
    }
}
