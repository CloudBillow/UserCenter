package com.miracle.usercenter.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户表
 *
 * @author XieYT
 * @since 2023/02/26 18:25
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User implements Serializable {

    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 账户
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String phone;

    /**
     * 创建人用户ID
     */
    private Integer createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer createTime;

    /**
     * 更新人
     */
    private Integer updateBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updateTime;

    /**
     * 状态
     */
    private Boolean status;

    /**
     * 性别（0男、1女、2未知）
     */
    private String sex;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 是否是超级管理员
     */
    private Boolean isSuper;

    /**
     * 是否已经删除
     */
    private Boolean delFlag;

    private static final long serialVersionUID = 1L;
}