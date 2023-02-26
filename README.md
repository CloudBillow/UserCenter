# 用户中心

# 建表语句

`用户表：user`
```sql
create table user
(
    id          int auto_increment comment '用户ID'
        primary key,
    username    varchar(100)     not null comment '账户',
    password    varchar(100)     not null comment '密码',
    nickname    varchar(100)     null comment '用户昵称',
    email       varchar(100)     null comment '邮箱',
    phone       varchar(100)     null comment '电话',
    create_by   int default 0    not null comment '创建人用户ID',
    create_time int              not null comment '创建时间',
    update_by   int default 0    not null comment '更新人',
    update_time int              not null comment '更新时间',
    status      bit              not null comment '状态',
    sex         char             not null comment '性别（0男、1女、2未知）',
    avatar      varchar(128)     null comment '头像',
    is_super    bit              not null comment '是否是超级管理员',
    del_flag    bit default b'0' not null comment '是否已经删除'
)
    comment '用户表';
```