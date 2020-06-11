package com.luojun.camel.admin.service;

import com.luojun.camel.admin.domain.SysPermission;

import java.util.List;

public interface PermissionService {
    /**
     *  根据用户id 获取权限集合
     * @param user_id
     * @return
     */
    List<SysPermission> selectSysPermissionByuserId(String user_id);
}
