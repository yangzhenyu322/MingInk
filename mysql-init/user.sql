/*
 Navicat Premium Data Transfer

 Source Server         : lovelumine
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : 223.82.75.76:3306
 Source Schema         : mingink

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

 Date: 21/02/2024 03:11:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 创建库
create database if not exists mingink

-- 切换库
use mingink

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `uid` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'uid（100001开始）',
  `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名称（账号）',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户密码',
  `nick_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户昵称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户头像（有默认头像）',
  `sex` tinyint(1) NOT NULL DEFAULT 2 COMMENT '用户性别（0：男，1:女，2：未知，默认为未知）',
  `birthday` datetime NULL DEFAULT NULL COMMENT '生日',
  `role_id` bigint(20) NULL DEFAULT 3 COMMENT '用户权限 (1: 管理员 2:VIP用户 3: 普通用户)\n默认普通成员',
  `remark` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '自我介绍\r\n',
  `country` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '国家/地区',
  `region` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '所在区域',
  `address` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '具体地址',
  `tag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户标签',
  `phone_number` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电话号码',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '账户状态 0正常 1停用 2注销\n默认正常',
  `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户IP',
  `login_date` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '用户最后登录时间',
  `oauth_type` tinyint(1) NULL DEFAULT NULL COMMENT '第三方登录类型（1：微信，2：QQ）等等\n',
  `oauth_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '第三方登录账号ID\n',
  `access_token` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '认证access_token\n',
  `refresh_token` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '认证refresh_token\n',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间\n',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `userNameUniKey`(`user_name` ASC) USING BTREE COMMENT '用户名唯一'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('2318265015734272', '100001', 'admin', 'admin333', 'admin', 'null', 0, '2024-02-02 02:19:05', 1, NULL, NULL, NULL, NULL, NULL, '15211095912', NULL, 0, NULL, '2024-02-19 14:10:42', NULL, NULL, NULL, NULL, '2024-02-02 02:52:30', '2024-02-19 14:10:42');
INSERT INTO `user` VALUES ('2318265015734273', '100002', 'vip', 'vip', 'vip', 'null', 1, '2024-02-02 02:20:37', 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, '2024-02-01 13:21:48', NULL, NULL, NULL, NULL, '2024-02-01 13:21:48', '2024-02-01 13:21:48');
INSERT INTO `user` VALUES ('2318265015734274', '100003', 'guest', 'guest', 'guest', 'null', 2, '2024-02-02 02:21:34', 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, '2024-02-01 13:21:37', NULL, NULL, NULL, NULL, '2024-02-01 13:21:37', '2024-02-01 13:21:37');
INSERT INTO `user` VALUES ('2339611078692864', '100004', 'guest1', 'guest1', 'guest1', 'null', 2, NULL, 3, NULL, NULL, NULL, NULL, NULL, '16494984934', NULL, 0, NULL, '2024-02-02 03:41:16', NULL, NULL, NULL, NULL, '2024-02-02 02:48:04', '2024-02-02 02:48:04');
INSERT INTO `user` VALUES ('2339957591117824', '100005', 'guest2', 'guest2', 'guest2', 'null', 2, NULL, 3, NULL, NULL, NULL, NULL, NULL, '16494984935', NULL, 0, NULL, '2024-02-02 03:42:13', NULL, NULL, NULL, NULL, '2024-02-02 03:42:13', '2024-02-02 03:42:13');
INSERT INTO `user` VALUES ('8305104511963136', '100006', 'hulxxx', 'qwe123000', 'hulx666', 'null', 0, NULL, 1, '', '中国', '湖南省长沙市', '长沙师范学院第二校区', '', '13732466394', '627194985@qq.com', 0, '', '2024-02-18 02:39:49', NULL, '', '', '', '2024-02-18 01:58:25', '2024-02-18 02:39:49');
INSERT INTO `user` VALUES ('8786577673293824', '100007', 'guest3', 'guest3', 'guest3', 'null', 2, NULL, 3, NULL, NULL, NULL, NULL, NULL, '16494984936', NULL, 0, NULL, '2024-02-19 22:38:47', NULL, NULL, NULL, NULL, '2024-02-19 22:38:47', '2024-02-19 22:38:47');
INSERT INTO `user` VALUES ('8855105843630080', '100008', 'guest4', 'guest4', 'guest4', 'null', 2, NULL, 3, NULL, NULL, NULL, NULL, NULL, '16494984932', NULL, 0, NULL, '2024-02-19 14:11:06', NULL, NULL, NULL, NULL, '2024-02-19 14:11:06', '2024-02-19 14:11:06');

SET FOREIGN_KEY_CHECKS = 1;
