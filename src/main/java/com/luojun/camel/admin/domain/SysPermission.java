package com.luojun.camel.admin.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

/**
    * 权限资源表 系统管理-权限资源表
    */
@Data
@Table(name = "sys_permission")
public class SysPermission implements Serializable {
    /**
     * ID
     */
    @Column(name = "ID")
    private String id;

    /**
     * 父ID
     */
    @Column(name = "P_ID")
    private String pId;

    /**
     * 第三方链接
     */
    @Column(name = "URL")
    private String url;

    /**
     * 前端组件
     */
    @Column(name = "COMPONENT")
    private String component;

    /**
     * 请求路径
     */
    @Column(name = "`PATH`")
    private String path;

    /**
     * 资源名称 英文名称
     */
    @Column(name = "`NAME`")
    private String name;

    /**
     * 名称 中文名称
     */
    @Column(name = "TITLE")
    private String title;

    /**
     * 资源级别
     */
    @Column(name = "`LEVEL`")
    private Integer level;

    /**
     * 排序
     */
    @Column(name = "SORT")
    private Integer sort;

    /**
     * 资源图标
     */
    @Column(name = "ICON")
    private String icon;

    /**
     * 类型 -1头部菜单、0菜单，1按钮
     */
    @Column(name = "`TYPE`")
    private String type;

    /**
     * 按钮类型
     */
    @Column(name = "BUTTON_TYPE")
    private String buttonType;

    /**
     * 状态 0启用、1为禁用
     */
    @Column(name = "`STATUS`")
    private String status;

    /**
     * 备注
     */
    @Column(name = "REMARK")
    private String remark;

    /**
     * 创建人
     */
    @Column(name = "CREATED_BY")
    private String createdBy;

    /**
     * 创建时间
     */
    @Column(name = "CREATED_TIME")
    private Date createdTime;

    /**
     * 更新人
     */
    @Column(name = "UPDATED_BY")
    private String updatedBy;

    /**
     * 更新时间
     */
    @Column(name = "UPDATED_TIME")
    private Date updatedTime;

    private static final long serialVersionUID = 1L;
}