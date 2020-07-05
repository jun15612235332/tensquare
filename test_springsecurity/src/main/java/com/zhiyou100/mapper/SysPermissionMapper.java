package com.zhiyou100.mapper;

import com.zhiyou100.model.SysPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysPermissionMapper {
    // 根据权限id找全部对应的权限信息
    @Select("SELECT * FROM sys_permission WHERE id=#{id}")
    List<SysPermission> listById(Integer id);
}
