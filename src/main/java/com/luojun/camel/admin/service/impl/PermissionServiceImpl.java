package com.luojun.camel.admin.service.impl;

import com.luojun.camel.admin.domain.SysPermission;
import com.luojun.camel.admin.mapper.SysPermissionMapper;
import com.luojun.camel.admin.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Override
    public List<SysPermission> selectSysPermissionByuserId(String user_id) {
        return sysPermissionMapper.selectSysPermissionByuserId(user_id);
    }
}
