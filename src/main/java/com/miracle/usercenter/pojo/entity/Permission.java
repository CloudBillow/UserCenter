package com.miracle.usercenter.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 权限实体类
 *
 * @author XieYT
 * @since 2023/02/28 22:40
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("permission")
public class Permission implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 菜单可见性
     */
    private Boolean visible;

    /**
     * 权限标识
     */
    private String key;

    /**
     * 图标
     */
    private String icon;

    /**
     * 权限类型
     */
    private Integer type;

    /**
     * 权限排序
     */
    private Integer sort;

    /**
     * 创建人ID
     */
    private Integer createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer createTime;

    /**
     * 更新人ID
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
     * 删除标识
     */
    private Boolean delFlag;

    /**
     * 备注
     */
    private String remark;

    private static final long serialVersionUID = 1L;
}