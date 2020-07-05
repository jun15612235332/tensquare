package com.zhiyou100.model;

import java.util.Arrays;
import java.util.List;

public class SysPermission {
    private Integer id;
    private String url;
    private String permission; // 权限
    private List permissions; // 存储根据,拆分后的权限集合

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public void setPermissions(List permissions) {
        this.permissions = permissions;
    }

    public List getPermissions() {
        return Arrays.asList(this.permission.trim().split(","));
    }
}
