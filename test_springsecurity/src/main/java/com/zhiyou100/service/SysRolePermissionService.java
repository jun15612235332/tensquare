package com.zhiyou100.service;

import com.zhiyou100.mapper.SysRolePermissionMapper;
import com.zhiyou100.model.SysRolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRolePermissionService {
    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

    // 根据角色id在[角色权限中间表]中查全部权限
    public List<SysRolePermission> listByRoleId(Integer roleId){
        return sysRolePermissionMapper.listByRoleId(roleId);
    }


}
