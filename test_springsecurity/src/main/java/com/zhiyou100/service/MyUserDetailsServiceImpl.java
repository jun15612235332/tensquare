package com.zhiyou100.service;

import com.zhiyou100.mapper.SysRoleMapper;
import com.zhiyou100.mapper.SysUserMapper;
import com.zhiyou100.mapper.SysUserRoleMapper;
import com.zhiyou100.model.SysRole;
import com.zhiyou100.model.SysUser;
import com.zhiyou100.model.SysUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class MyUserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名获取用户对象
        SysUser user = sysUserMapper.selectByName(username);
        //判断用户是否存在
        if (user == null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        //根据用户id查询角色id
        List<SysUserRole> sysUserRoles = sysUserRoleMapper.listByUserId(user.getId());
        //创建GrantedAuthority集合,并把角色名存入该集合
        Collection<GrantedAuthority> authorities=new ArrayList<>();
        //根据角色id查询角色对象
        for (SysUserRole sysUserRole : sysUserRoles) {
            SysRole sysRole = sysRoleMapper.selectById(sysUserRole.getRoleId());
            authorities.add(new SimpleGrantedAuthority(sysRole.getName()));
        }
        return new User(username,user.getPassword(),authorities);
    }
}
