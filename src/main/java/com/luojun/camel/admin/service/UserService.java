package com.luojun.camel.admin.service;

import com.luojun.camel.admin.domain.SysUser;

public interface UserService {
    /**
     * 根据用户名查找用户信息
     *
     * @param username
     * @return
     */
    SysUser selectByUserName(String username);
}
