package com.miracle.usercenter.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.miracle.usercenter.util.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyBatisPlusConfig implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        System.out.println("start insert fill ....");
        int createTime = TimeUtils.tenBitTimestamp();
        this.strictInsertFill(metaObject, "createTime", Integer.class, createTime);
        this.strictInsertFill(metaObject, "updateTime", Integer.class, createTime);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        System.out.println("start update fill ....");
        this.strictInsertFill(metaObject, "updateTime", Integer.class, TimeUtils.tenBitTimestamp());
    }
}