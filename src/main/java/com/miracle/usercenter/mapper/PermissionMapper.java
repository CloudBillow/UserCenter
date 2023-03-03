package com.miracle.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.miracle.usercenter.pojo.entity.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 权限Mapper
 *
 * @author XieYT
 * @since 2023/02/28 22:40
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 根据用户ID查询该用户的权限标识
     *
     * @param userId 用户ID
     * @return 权限标识列表
     */
    List<String> selectKeyListByUserId(@Param("userId") Integer userId);

    /**
     * 查询所有权限标识
     *
     * @return 权限标识列表
     */
    List<String> selectKeyList();
}