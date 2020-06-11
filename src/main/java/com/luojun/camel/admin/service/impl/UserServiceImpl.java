package com.luojun.camel.admin.service.impl;

import com.luojun.camel.admin.domain.SysUser;
import com.luojun.camel.admin.mapper.SysUserMapper;
import com.luojun.camel.admin.service.UserService;
import com.luojun.camel.common.result.JsonResult;
import com.luojun.camel.common.result.ResultCode;
import com.luojun.camel.common.result.ResultTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

@RestController
public class UserServiceImpl implements UserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @GetMapping(value = "/test123")
    public JsonResult test() {
        SysUser sysUser = sysUserMapper.selectAll().get(0);
        return ResultTool.fail(ResultCode.USER_ACCOUNT_NOT_EXIST);
    }

    @Override
    public SysUser selectByUserName(String account) {
        Example example = new Example(SysUser.class);
        example.createCriteria().andEqualTo("account", account);
        return sysUserMapper.selectOneByExample(example);
    }

    @GetMapping(value = "/abc")
    public String abc() {
        return "wos.骆军";
    }
}
