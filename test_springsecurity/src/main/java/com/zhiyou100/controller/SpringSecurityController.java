package com.zhiyou100.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SpringSecurityController {

    @RequestMapping("/")
    public String showHome() {
        // 从session中获取登录信息,判断有无登录
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return "home.html";
    }

    @RequestMapping("/login")
    public String login(){
        System.out.println("开始登陆");
        return "login.html";
    }
    // 访问/admin需要有ROLE_ADMIN角色
    @RequestMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    public String printAdmin() {
        return "如果你看见这句话，说明你有ROLE_ADMIN角色";
    }

    // 访问/user需要有ROLE_USER角色
    @RequestMapping("/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseBody
    public String printUser() {
        return "如果你看见这句话，说明你有ROLE_USER角色";
    }

    // 匿名可以访问/anno
    @RequestMapping("/anon")
    @PreAuthorize("permitAll()")
    @ResponseBody
    public String anon() {
        return "如果你看见这句话，说明你在匿名访问";
    }

    /**
     * 测试权限
     */
    @RequestMapping("/user/r")
    @PreAuthorize("hasPermission('/user','r')")
    @ResponseBody
    public String printUser3(){
        return "如果你看见这句话,就说明你有ROLE_USER角色并且有r权限";
    }
}
