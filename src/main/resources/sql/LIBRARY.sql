/*
Navicat MySQL Data Transfer

Source Server         : 39
Source Server Version : 50726
Source Host           : 192.168.122.39:3306
Source Database       : LIBRARY

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-10-17 17:16:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ARTICLE
-- ----------------------------
DROP TABLE IF EXISTS `ARTICLE`;
CREATE TABLE `ARTICLE` (
  `ID` char(64) NOT NULL COMMENT '主键',
  `TITLE` varchar(50) NOT NULL COMMENT '文章标题',
  `CONTENT` longtext NOT NULL COMMENT '文章内容',
  `AUTHOR_ID` char(64) NOT NULL COMMENT '作者主键',
  `VIEW_COUNT` int(11) NOT NULL DEFAULT '0' COMMENT '阅读数量',
  `STATUS` char(1) NOT NULL DEFAULT 'E' COMMENT '文章状态',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章信息表';

-- ----------------------------
-- Records of ARTICLE
-- ----------------------------

-- ----------------------------
-- Table structure for ARTICLE_CATEGORY
-- ----------------------------
DROP TABLE IF EXISTS `ARTICLE_CATEGORY`;
CREATE TABLE `ARTICLE_CATEGORY` (
  `ID` char(64) NOT NULL COMMENT '主键',
  `NAME` varchar(10) NOT NULL COMMENT '类别名称',
  `COLOR_CODE` char(7) NOT NULL COMMENT '颜色代码',
  `STATUS` char(1) NOT NULL DEFAULT 'E' COMMENT '状态',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章类别';

-- ----------------------------
-- Records of ARTICLE_CATEGORY
-- ----------------------------
INSERT INTO `ARTICLE_CATEGORY` VALUES ('1', '前端', '#FF9900', 'E');
INSERT INTO `ARTICLE_CATEGORY` VALUES ('2', '后端', '#CC0000', 'E');
INSERT INTO `ARTICLE_CATEGORY` VALUES ('3', '数据库', '#3333FF', 'E');
INSERT INTO `ARTICLE_CATEGORY` VALUES ('4', '服务器', '#66FF00', 'E');
INSERT INTO `ARTICLE_CATEGORY` VALUES ('5', '测试', '#FFFF00', 'E');
INSERT INTO `ARTICLE_CATEGORY` VALUES ('6', '运维', '#CC00FF', 'E');

-- ----------------------------
-- Table structure for AUTHOR
-- ----------------------------
DROP TABLE IF EXISTS `AUTHOR`;
CREATE TABLE `AUTHOR` (
  `ID` char(64) NOT NULL COMMENT '主键',
  `NAME` varchar(10) NOT NULL COMMENT '昵称',
  `EMAIL` varchar(255) NOT NULL COMMENT '邮箱',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='作者信息表';

-- ----------------------------
-- Records of AUTHOR
-- ----------------------------

-- ----------------------------
-- Table structure for BARRAGE
-- ----------------------------
DROP TABLE IF EXISTS `BARRAGE`;
CREATE TABLE `BARRAGE` (
  `ID` char(64) NOT NULL COMMENT '数据主键',
  `TYPE` char(1) NOT NULL DEFAULT '1' COMMENT '弹幕类型[1=广播/2=定向/3=文章]',
  `COLOR` char(7) NOT NULL DEFAULT '#000000' COMMENT '弹幕文字颜色',
  `CONTENT` varchar(100) NOT NULL COMMENT '弹幕文字',
  `TARGET` char(64) NOT NULL DEFAULT 'ALL' COMMENT '接收人[弹幕类型为1时置空/2时置作者ID/3时置文章ID]',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='弹幕表';

-- ----------------------------
-- Records of BARRAGE
-- ----------------------------

-- ----------------------------
-- Table structure for FEEDBACK
-- ----------------------------
DROP TABLE IF EXISTS `FEEDBACK`;
CREATE TABLE `FEEDBACK` (
  `ID` char(64) NOT NULL COMMENT '数据主键',
  `DESCRIPTION` varchar(100) NOT NULL COMMENT '反馈问题内容',
  `STATUS` char(1) DEFAULT '0' COMMENT '是否解决[1=已解决/0=未解决]',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='问题反馈信息表';

-- ----------------------------
-- Records of FEEDBACK
-- ----------------------------

-- ----------------------------
-- Table structure for R_ARTICLE_CATEGORY
-- ----------------------------
DROP TABLE IF EXISTS `R_ARTICLE_CATEGORY`;
CREATE TABLE `R_ARTICLE_CATEGORY` (
  `ID` char(64) NOT NULL COMMENT '主键',
  `TARGET_ID` char(64) NOT NULL COMMENT '文章主键',
  `ARTICLE_CATEGORY_ID` char(64) NOT NULL COMMENT '文章分类主键',
  `TARGET_TYPE` char(1) NOT NULL COMMENT '目标类型[1=文章/2=作者]',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章与文章分类关联表';

-- ----------------------------
-- Records of R_ARTICLE_CATEGORY
-- ----------------------------
