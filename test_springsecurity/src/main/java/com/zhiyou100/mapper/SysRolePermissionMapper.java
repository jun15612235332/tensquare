package com.zhiyou100.mapper;

import com.zhiyou100.model.SysRolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysRolePermissionMapper {

    // 根据角色id找对应的权限
    @Select("SELECT * FROM sys_role_permission WHERE role_id = #{roleId}")
    List<SysRolePermission> listByRoleId(Integer roleId);
}
