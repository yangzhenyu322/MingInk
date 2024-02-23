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

 Date: 22/02/2024 00:43:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 创建库
create database if not exists mingink

-- 切换库
use mingink

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE,
  INDEX `fk_user_role_role`(`role_id` ASC) USING BTREE,
  CONSTRAINT `fk_user_role_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_user_role_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('2318265015734272', 1);
INSERT INTO `user_role` VALUES ('2318265015734272', 2);
INSERT INTO `user_role` VALUES ('2318265015734273', 2);
INSERT INTO `user_role` VALUES ('2318265015734272', 3);
INSERT INTO `user_role` VALUES ('2318265015734273', 3);
INSERT INTO `user_role` VALUES ('2318265015734274', 3);

SET FOREIGN_KEY_CHECKS = 1;
