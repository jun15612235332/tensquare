package com.zhiyou100.service;

import com.zhiyou100.mapper.SysPermissionMapper;
import com.zhiyou100.model.SysPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysPermissionService {
    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    // 根据权限id查全部的权限信息
    public List<SysPermission> listById(Integer id) {
        return sysPermissionMapper.listById(id);
    }
}
