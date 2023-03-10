package com.miracle.usercenter.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.miracle.usercenter.util.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class MyBatisPlusConfig implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        int createTime = TimeUtils.tenBitTimestamp();
        this.strictInsertFill(metaObject, "createTime", Integer.class, createTime);
        this.strictInsertFill(metaObject, "updateTime", Integer.class, createTime);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "updateTime", Integer.class, TimeUtils.tenBitTimestamp());
    }
}