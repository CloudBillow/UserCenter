package com.miracle.usercenter;

import com.miracle.usercenter.mapper.PermissionMapper;
import com.miracle.usercenter.mapper.UserMapper;
import com.miracle.usercenter.pojo.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * Mapper测试类
 *
 * @author XieYT
 * @since 2023/02/26 18:34
 */
@SpringBootTest
public class MapperTest {

    @Resource
    private UserMapper userMapper;
    @Resource
    private PermissionMapper permissionMapper;

    @Test
    public void testUserMapperSelectList() {
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }

    @Test
    public void testUserMapperAddUser() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("1234");
        userMapper.insert(user);
    }

    @Test
    public void testSelectKeyListByUserId() {
        List<String> permissionKeys = permissionMapper.selectKeyListByUserId(1);
        System.out.println(permissionKeys);
    }
}
