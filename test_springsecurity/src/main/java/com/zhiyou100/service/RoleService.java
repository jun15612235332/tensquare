package com.zhiyou100.service;

import com.zhiyou100.mapper.SysRoleMapper;
import com.zhiyou100.model.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    // 根据角色名查出角色信息
    public SysRole selectByName(String name){
        return sysRoleMapper.selectByName(name);
    }
}
