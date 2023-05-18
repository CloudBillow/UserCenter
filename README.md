# 用户中心

# 建表语句

```sql
create table permission
(
    id          int auto_increment comment '主键'
        primary key,
    name        varchar(100)             null comment '权限名称',
    path        varchar(100)             null comment '路由地址',
    component   varchar(200)             null comment '组件路径',
    visible     bit         default b'1' not null comment '菜单可见性',
    `key`       varchar(100)             null comment '权限标识',
    icon        varchar(50) default '#'  not null comment '图标',
    type        int                      null comment '权限类型',
    sort        int         default 0    not null comment '权限排序',
    create_by   int                      null comment '创建人ID',
    create_time int                      not null comment '创建时间',
    update_by   int                      null comment '更新人ID',
    update_time int                      not null comment '更新时间',
    status      bit         default b'1' not null comment '状态',
    del_flag    bit         default b'0' not null comment '删除标识',
    remark      varchar(256)             null comment '备注'
)
    comment '权限';

create table role
(
    id          int auto_increment comment '主键'
        primary key,
    name        varchar(100)     not null comment '角色名',
    status      bit default b'1' not null comment '状态',
    create_time int              not null comment '创建时间',
    update_time int              not null comment '更新时间'
)
    comment '角色表';

create table role_permission
(
    role_id       int not null comment '角色ID',
    permission_id int not null comment '权限ID',
    constraint role_permission_pk
        unique (role_id, permission_id)
)
    comment '角色权限绑定关系表';

create table user
(
    id          int auto_increment comment '用户ID'
        primary key,
    username    varchar(100)      not null comment '账户',
    password    varchar(100)      not null comment '密码',
    nickname    varchar(100)      null comment '用户昵称',
    email       varchar(100)      null comment '邮箱',
    phone       varchar(100)      null comment '电话',
    create_by   int  default 0    not null comment '创建人用户ID',
    create_time int               not null comment '创建时间',
    update_by   int  default 0    not null comment '更新人',
    update_time int               not null comment '更新时间',
    status      bit  default b'1' not null comment '状态',
    sex         char default '2'  not null comment '性别（0男、1女、2未知）',
    avatar      varchar(128)      null comment '头像',
    is_super    bit  default b'0' not null comment '是否是超级管理员',
    del_flag    bit  default b'0' not null comment '是否已经删除'
)
    comment '用户表';

create table user_role
(
    user_id int not null comment '用户ID',
    role_id int not null comment '角色ID',
    constraint user_role_pk
        unique (user_id, role_id)
)
    comment '用户角色绑定关系';


```