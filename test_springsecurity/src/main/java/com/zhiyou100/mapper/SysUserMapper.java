package com.zhiyou100.mapper;

import com.zhiyou100.model.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SysUserMapper {
    // 根据id查用户
    @Select("SELECT * FROM sys_user WHERE id = #{id}")
    SysUser selectById(Integer id);

    // 根据用户名查用户
    @Select("SELECT * FROM sys_user WHERE name = #{name}")
    SysUser selectByName(String name);
}
