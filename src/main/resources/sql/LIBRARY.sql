create table ARTICLE
(
  ID      char(64)         not null comment '主键'
    primary key,
  TITLE   varchar(50)      not null comment '文章标题',
  CONTENT longtext         not null comment '文章内容',
  STATUS  char default 'E' not null comment '文章状态'
)
  comment '文章信息表';

create table ARTICLE_CATEGORY
(
  ID     char(64)         not null comment '主键'
    primary key,
  NAME   varchar(10)      not null comment '类别名称',
  STATUS char default 'E' not null comment '状态'
)
  comment '文章类别';

create table AUTHOR
(
  ID    char(64)     not null comment '主键'
    primary key,
  NAME  varchar(10)  not null comment '昵称',
  EMAIL varchar(255) not null comment '邮箱'
)
  comment '作者信息表';

create table R_ARTICLE_CATEGORY
(
  ID                  char(64) not null comment '主键'
    primary key,
  TARGET_ID           char(64) not null comment '文章主键',
  ARTICLE_CATEGORY_ID char(64) not null comment '文章分类主键',
  TARGET_TYPE         char     not null comment '目标类型[1=文章/2=作者]'
)
  comment '文章与文章分类关联表';

