package com.miracle.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.miracle.usercenter.common.CODE;
import com.miracle.usercenter.common.UserCenterException;
import com.miracle.usercenter.mapper.UserMapper;
import com.miracle.usercenter.mapper.UserRoleMapper;
import com.miracle.usercenter.pojo.dto.UserDTO;
import com.miracle.usercenter.pojo.entity.User;
import com.miracle.usercenter.pojo.entity.UserRole;
import com.miracle.usercenter.pojo.vo.TokenVO;
import com.miracle.usercenter.service.RegisterService;
import com.miracle.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户注册Service
 *
 * @author XieYT
 * @since 2023/03/02 22:40
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private UserService userService;

    @Value("${user-center.default-role-id}")
    private Integer defaultRoleId;
    @Value("${user-center.default-avatar}")
    private String defaultAvatar;

    /**
     * 用户名密码注册
     *
     * @param user 用户信息
     */
    @Override
    public TokenVO registerByUsernameAndPassword(UserDTO user) {

        // 检查注册信息
        checkRegisterInfo(user);

        // 保存用户信息
        User systemUser = saveUser(user.getUsername(), user.getPassword());

        // 绑定默认角色
        bindDefaultRole(systemUser.getId(), defaultRoleId);

        // 注册完成后，自动登录
        return userService.login(
                UserDTO.builder()
                        .username(systemUser.getUsername())
                        .password(user.getPassword())
                        .build()
        );
    }


    /**
     * 绑定默认角色
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     */
    private void bindDefaultRole(Integer userId, Integer roleId) {
        userRoleMapper.insert(UserRole.builder()
                .userId(userId).roleId(roleId)
                .build()
        );
    }

    /**
     * 保存用户信息
     *
     * @param username 用户信息
     * @param password 密码
     */
    private User saveUser(String username, String password) {
        return saveUser(username, password, username, null, null,
                0, 0, true, "2", "#", false);
    }

    /**
     * 保存用户信息
     *
     * @param username 用户名
     * @param password 密码
     * @param nickname 昵称
     * @param email    邮箱
     * @param phone    电话
     * @param createBy 创建人
     * @param updateBy 更新人
     * @param status   状态
     * @param sex      性别
     * @param avatar   头像
     * @param isSuper  是否超级管理员
     */
    private User saveUser(String username, String password, String nickname,
                          String email, String phone, Integer createBy, Integer updateBy,
                          Boolean status, String sex, String avatar, Boolean isSuper) {

        User user = User.builder()
                .username(username)
                .password(new BCryptPasswordEncoder().encode(password))
                .nickname(nickname == null ? username : nickname)
                .email(email)
                .phone(phone)
                .createBy(createBy == null ? 0 : createBy)
                .updateBy(updateBy == null ? 0 : updateBy)
                .status(status == null || status)
                .sex(StringUtils.isEmpty(sex) ? "2" : sex)
                .avatar(avatar == null ? defaultAvatar : avatar)
                .isSuper(isSuper != null && isSuper)
                .delFlag(false)
                .build();

        userMapper.insert(user);

        return user;

    }

    /**
     * 检查注册信息
     *
     * @param user 用户信息
     */
    private void checkRegisterInfo(UserDTO user) {

        String username = user.getUsername();
        String password = user.getPassword();


        // 用户名不能为空
        if (username == null) {
            throw new UserCenterException(CODE.USERNAME_NULL);
        }

        // 密码不能为空
        if (password == null) {
            throw new UserCenterException(CODE.PASSWORD_NULL);
        }

        // 用户名长度必须在6-20位之间
        if (username.length() < 6 || username.length() > 20) {
            throw new UserCenterException(CODE.USERNAME_LENGTH_ERROR);
        }

        // 密码长度必须在6-20位之间
        if (password.length() < 6 || password.length() > 20) {
            throw new UserCenterException(CODE.PASSWORD_LENGTH_ERROR);
        }

        // 密码必须包含大小写字母和数字
        if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,20}$")) {
            throw new UserCenterException(CODE.PASSWORD_FORMAT_ERROR);
        }

        // 检查用户名是否已经存在
        if (checkUsernameExist(username)) {
            throw new UserCenterException(CODE.USERNAME_EXIST);
        }

    }

    /**
     * 检查用户名是否已经存在
     *
     * @param username 用户名
     * @return 是否存在
     */
    private boolean checkUsernameExist(String username) {
        return userMapper.exists(
                new LambdaQueryWrapper<User>()
                        .eq(User::getUsername, username));
    }
}
