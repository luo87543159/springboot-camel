package com.luojun.camel.auth.service.impl;

import com.luojun.camel.admin.domain.SysPermission;
import com.luojun.camel.admin.domain.SysUser;
import com.luojun.camel.admin.mapper.SysPermissionMapper;
import com.luojun.camel.admin.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查询用户信息
        Example example = new Example(SysUser.class);
        example.createCriteria().andEqualTo("account", username);
        SysUser sysUser = sysUserMapper.selectOneByExample(example);
        System.out.println(sysUser);

        List<GrantedAuthority> grantedAuthorities= new ArrayList<>();
        boolean enabled = true; //账号是否正常
        boolean accountNonExpired= true; //账号是否过期
        boolean credentialsNonExpired= true; //账号是否锁定
        boolean accountNonLocked= true; //账户证书是否过期

        if (sysUser != null){
            //判断用户状态
            if (sysUser.getAccountStatus().equals("1")){
                enabled=false;
            }
            if (sysUser.getAccountStatus().equals("2")){
                accountNonExpired=false;
            }
            if (sysUser.getAccountStatus().equals("3")){
                credentialsNonExpired=false;
            }
            if (sysUser.getAccountStatus().equals("4")){
                accountNonLocked=false;
            }
            //获取用户的权限
            List<SysPermission> sysPermissionList = sysPermissionMapper.selectSysPermissionByuserId(sysUser.getId());
            // 声明用户授权
            sysPermissionList.forEach(sysPermission -> {
                if (sysPermission !=null && sysPermission.getName() != null){
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(sysPermission.getName());
                    grantedAuthorities.add(grantedAuthority);
                }
            });
        }else {
            throw new UsernameNotFoundException("用户不存在");
        }
        // 由框架完成认证工作
        return new User(sysUser.getAccount(), sysUser.getPassword(),enabled,accountNonExpired,credentialsNonExpired,accountNonLocked,grantedAuthorities);
    }
}
