package com.luojun.camel.admin.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

/**
 * 用户信息;用户登陆信息
 */
@Data
@Table(name = "sys_user")
public class SysUser implements Serializable {
    /**
     * ID
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 用户名
     */
    @Column(name = "ACCOUNT")
    private String account;

    /**
     * 密码
     */
    @Column(name = "`PASSWORD`")
    private String password;

    /**
     * 头像
     */
    @Column(name = "AVATAR_ADDRESS")
    private String avatarAddress;

    /**
     * 唯一识别;唯一识别
     */
    @Column(name = "`ONLY`")
    private String only;

    /**
     * 姓名
     */
    @Column(name = "USERNAME")
    private String username;

    /**
     * 最后登陆时间
     */
    @Column(name = "LASTLOGIN_TIME")
    private Date lastloginTime;

    /**
     * 用户状态（0正常、1注销、2过期、3锁定、4证书过期）
     */
    @Column(name = "ACCOUNT_STATUS")
    private String accountStatus;

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