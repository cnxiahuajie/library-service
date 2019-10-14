create table LIBRARY.ARTICLE
(
  ID         char(64)         not null comment '主键'
    primary key,
  TITLE      varchar(50)      not null comment '文章标题',
  CONTENT    longtext         not null comment '文章内容',
  AUTHOR_ID  char(64)         not null comment '作者主键',
  VIEW_COUNT int  default 0   not null comment '阅读数量',
  STATUS     char default 'E' not null comment '文章状态'
)
  comment '文章信息表';

create table LIBRARY.ARTICLE_CATEGORY
(
  ID         char(64)         not null comment '主键'
    primary key,
  NAME       varchar(10)      not null comment '类别名称',
  COLOR_CODE char(7)          not null comment '颜色代码',
  STATUS     char default 'E' not null comment '状态'
)
  comment '文章类别';

create table LIBRARY.AUTHOR
(
  ID    char(64)     not null comment '主键'
    primary key,
  NAME  varchar(10)  not null comment '昵称',
  EMAIL varchar(255) not null comment '邮箱'
)
  comment '作者信息表';

create table FEEDBACK
(
  ID          char(64)         not null
  comment '数据主键'
    primary key,
  DESCRIPTION varchar(100)     not null
  comment '反馈问题内容',
  STATUS      char default '0' null
  comment '是否解决[1=已解决/0=未解决]'
)
  comment '问题反馈信息表';

create table LIBRARY.R_ARTICLE_CATEGORY
(
  ID                  char(64) not null comment '主键'
    primary key,
  TARGET_ID           char(64) not null comment '文章主键',
  ARTICLE_CATEGORY_ID char(64) not null comment '文章分类主键',
  TARGET_TYPE         char     not null comment '目标类型[1=文章/2=作者]'
)
  comment '文章与文章分类关联表';

