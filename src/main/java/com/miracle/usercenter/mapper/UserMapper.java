package com.miracle.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.miracle.usercenter.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表Mapper
 *
 * @author XieYT
 * @since 2023/02/26 18:25
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}