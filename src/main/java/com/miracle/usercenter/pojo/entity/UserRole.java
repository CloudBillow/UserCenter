package com.miracle.usercenter.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户角色绑定关系
 *
 * @author XieYT
 * @since 2023/03/02 23:20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRole implements Serializable {
    /**
    * 用户ID
    */
    private Integer userId;

    /**
    * 角色ID
    */
    private Integer roleId;

    private static final long serialVersionUID = 1L;
}