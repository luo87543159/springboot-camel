package com.luojun.camel.admin.mapper;

import com.luojun.camel.admin.domain.SysPermission;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysPermissionMapper extends Mapper<SysPermission> {
    /**
     * 根据用户id 获取权限集合
     * @param user_id
     * @return
     */
    List<SysPermission> selectSysPermissionByuserId(@Param("user_id") String user_id);
}
