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

 Date: 21/02/2024 03:14:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 创建库
create database if not exists mingink

-- 切换库
use mingink

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户角色ID',
  `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(11) NOT NULL COMMENT '显示顺序',
  `data_scope` tinyint(1) NULL DEFAULT NULL COMMENT '数据范围',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '角色状态',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '超级管理员', 'admin', 1, 1, 0, '超级管理员', '2024-02-01 20:25:53', '2024-02-01 20:25:57');
INSERT INTO `role` VALUES (2, 'VIP用户', 'vip', 2, 2, 0, 'VIP用户', '2024-02-01 07:26:26', '2024-02-01 20:26:24');
INSERT INTO `role` VALUES (3, '普通角色', 'common', 3, 3, 0, '普通用户', '2024-02-01 20:26:47', '2024-02-01 20:26:51');

SET FOREIGN_KEY_CHECKS = 1;
