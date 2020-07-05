package com.zhiyou100.mapper;

import com.zhiyou100.model.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SysRoleMapper {
    // 根据角色id查角色信息
    @Select("SELECT * FROM sys_role WHERE id = #{id}")
    SysRole selectById(Integer id);

    // 根据角色名找角色信息
    @Select("SELECT * FROM sys_role WHERE name = #{name}")
    SysRole selectByName(String name);
}
