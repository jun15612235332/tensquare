package com.zhiyou100.mapper;

import com.zhiyou100.model.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysUserRoleMapper {
    // 根据用户id在[用户角色关联表]中查出用户的所有角色
    @Select("SELECT * FROM sys_user_role WHERE user_id = #{userId}")
    List<SysUserRole> listByUserId(Integer userId);
}
