package com.zhiyou100.config;

import com.zhiyou100.model.SysPermission;
import com.zhiyou100.model.SysRolePermission;
import com.zhiyou100.service.RoleService;
import com.zhiyou100.service.SysPermissionService;
import com.zhiyou100.service.SysRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 对接口方法的更细粒度的角色权限控制
 */
@Component
public class MyPermissionEvaluatorImpl implements PermissionEvaluator{

    @Autowired
    private RoleService roleService;
    @Autowired
    private SysRolePermissionService rolePermissionService;
    @Autowired
    private SysPermissionService permissionService;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetUrl, Object targetPermission) {
        System.out.println("目标 url : "+targetUrl);
        System.out.println("目标 权限 : "+targetPermission);
        // 获得loadUserByUsername()方法的结果
        // User是org.springframework.security.core.userdetails.User
        // 是Userdetails的实现类
        User user = (User)authentication.getPrincipal();
        // 获得loadUserByUsername()中注入的角色
        Collection<GrantedAuthority> authorities = user.getAuthorities();

        // 遍历登录用户中所有角色
        for(GrantedAuthority authority : authorities) {
            String roleName = authority.getAuthority();
            // 根据角色名查出角色信息并获得角色id
            Integer roleId = roleService.selectByName(roleName).getId();
            // 得到角色所有的权限
            // 根据角色id去[角色权限中间表]查全部的数据
            List<SysRolePermission> rolePermissions = rolePermissionService.listByRoleId(roleId);
            // 遍历根据角色id查出的全部中间表数据
            for(SysRolePermission sysRolePermission : rolePermissions){
                // 通过中间表数据获得角色id
                // 并根据权限id去[权限表]查全部权限数据
                List<SysPermission> permissionList = permissionService.listById(sysRolePermission.getPermissionId());
                // 遍历全部权限数据
                for(SysPermission sysPermission : permissionList) {
                    // 获取权限集 --> 实体类中按,拆分又封装的权限List
                    List permissions = sysPermission.getPermissions();
                    // 如果访问的Url和权限用户符合的话，返回true
                    System.out.println("目标 url : "+targetUrl);
                    System.out.println("目标 权限 : "+targetPermission);
                    if(targetUrl.equals(sysPermission.getUrl()) && permissions.contains(targetPermission)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }
}
