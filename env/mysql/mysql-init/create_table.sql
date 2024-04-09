# 数据库初始化

-- 创建库
create database if not exists nacos_config;

-- 切换库
use nacos_config;

-- 创建表
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for config_info
-- ----------------------------
create table if not exists config_info (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                                `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
                                `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
                                `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
                                `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
                                `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
                                `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
                                `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
                                `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
                                `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
                                `c_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
                                `c_use` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
                                `effect` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
                                `type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
                                `c_schema` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
                                `encrypted_data_key` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '秘钥',
                                PRIMARY KEY (`id`) USING BTREE,
                                UNIQUE INDEX `uk_configinfo_datagrouptenant`(`data_id` ASC, `group_id` ASC, `tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for config_info_aggr
-- ----------------------------
create table if not exists config_info_aggr  (
                                     `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                                     `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
                                     `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
                                     `datum_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'datum_id',
                                     `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '内容',
                                     `gmt_modified` datetime NOT NULL COMMENT '修改时间',
                                     `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
                                     `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
                                     PRIMARY KEY (`id`) USING BTREE,
                                     UNIQUE INDEX `uk_configinfoaggr_datagrouptenantdatum`(`data_id` ASC, `group_id` ASC, `tenant_id` ASC, `datum_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '增加租户字段' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for config_info_beta
-- ----------------------------
create table if not exists config_info_beta  (
                                     `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                                     `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
                                     `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
                                     `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
                                     `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
                                     `beta_ips` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'betaIps',
                                     `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
                                     `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                     `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
                                     `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
                                     `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
                                     `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
                                     `encrypted_data_key` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '秘钥',
                                     PRIMARY KEY (`id`) USING BTREE,
                                     UNIQUE INDEX `uk_configinfobeta_datagrouptenant`(`data_id` ASC, `group_id` ASC, `tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info_beta' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for config_info_tag
-- ----------------------------
create table if not exists config_info_tag  (
                                    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                                    `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
                                    `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
                                    `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
                                    `tag_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_id',
                                    `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
                                    `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
                                    `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
                                    `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
                                    `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
                                    `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
                                    PRIMARY KEY (`id`) USING BTREE,
                                    UNIQUE INDEX `uk_configinfotag_datagrouptenanttag`(`data_id` ASC, `group_id` ASC, `tenant_id` ASC, `tag_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info_tag' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for config_tags_relation
-- ----------------------------
create table if not exists config_tags_relation  (
                                         `id` bigint(20) NOT NULL COMMENT 'id',
                                         `tag_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_name',
                                         `tag_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'tag_type',
                                         `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
                                         `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
                                         `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
                                         `nid` bigint(20) NOT NULL AUTO_INCREMENT,
                                         PRIMARY KEY (`nid`) USING BTREE,
                                         UNIQUE INDEX `uk_configtagrelation_configidtag`(`id` ASC, `tag_name` ASC, `tag_type` ASC) USING BTREE,
                                         INDEX `idx_tenant_id`(`tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_tag_relation' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for group_capacity
-- ----------------------------
create table if not exists group_capacity  (
                                   `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                   `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
                                   `quota` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
                                   `usage` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
                                   `max_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
                                   `max_aggr_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数，，0表示使用默认值',
                                   `max_aggr_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
                                   `max_history_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
                                   `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
                                   PRIMARY KEY (`id`) USING BTREE,
                                   UNIQUE INDEX `uk_group_id`(`group_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '集群、各Group容量信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for his_config_info
-- ----------------------------
create table if not exists his_config_info  (
                                    `id` bigint(20) UNSIGNED NOT NULL,
                                    `nid` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
                                    `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
                                    `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
                                    `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
                                    `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
                                    `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
                                    `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
                                    `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
                                    `op_type` char(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
                                    `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
                                    `encrypted_data_key` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '秘钥',
                                    PRIMARY KEY (`nid`) USING BTREE,
                                    INDEX `idx_gmt_create`(`gmt_create` ASC) USING BTREE,
                                    INDEX `idx_gmt_modified`(`gmt_modified` ASC) USING BTREE,
                                    INDEX `idx_did`(`data_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 61 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '多租户改造' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for permissions
-- ----------------------------
create table if not exists permissions  (
                                `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                `resource` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                `action` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                UNIQUE INDEX `uk_role_permission`(`role` ASC, `resource` ASC, `action` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for roles
-- ----------------------------
create table if not exists roles  (
                          `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                          `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                          UNIQUE INDEX `idx_user_role`(`username` ASC, `role` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tenant_capacity
-- ----------------------------
create table if not exists tenant_capacity  (
                                    `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                    `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
                                    `quota` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
                                    `usage` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
                                    `max_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
                                    `max_aggr_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数',
                                    `max_aggr_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
                                    `max_history_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
                                    `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
                                    PRIMARY KEY (`id`) USING BTREE,
                                    UNIQUE INDEX `uk_tenant_id`(`tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '租户容量信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tenant_info
-- ----------------------------
create table if not exists tenant_info  (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                                `kp` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'kp',
                                `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
                                `tenant_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_name',
                                `tenant_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'tenant_desc',
                                `create_source` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'create_source',
                                `gmt_create` bigint(20) NOT NULL COMMENT '创建时间',
                                `gmt_modified` bigint(20) NOT NULL COMMENT '修改时间',
                                PRIMARY KEY (`id`) USING BTREE,
                                UNIQUE INDEX `uk_tenant_info_kptenantid`(`kp` ASC, `tenant_id` ASC) USING BTREE,
                                INDEX `idx_tenant_id`(`tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'tenant_info' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for users
-- ----------------------------
create table if not exists users  (
                          `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                          `password` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                          `enabled` tinyint(1) NOT NULL,
                          PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;





-- 创建库
create database if not exists mingink;

-- 切换库
use mingink;

-- 创建表
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for author
-- ----------------------------
create table if not exists author  (
    `id` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '作家id',
    `user_id` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户id',
    `pen_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '笔名',
    `status` tinyint(1) NOT NULL COMMENT '作家状态：0-正常、1-禁用',
    `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
    `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '个人信息更新时间',
    PRIMARY KEY (`id`, `user_id`) USING BTREE,
    INDEX `fk_author_user`(`user_id` ASC) USING BTREE,
    INDEX `id`(`id` ASC) USING BTREE,
    CONSTRAINT `fk_author_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for book
-- ----------------------------
create table if not exists book  (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '小说id',
    `author_id` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '作家id',
    `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
    `description` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
    `pic_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '封面图片url',
    `status` tinyint(1) NULL DEFAULT NULL COMMENT '状态：0-连载、1-已完结、2-下架',
    `read_count` bigint NULL DEFAULT NULL COMMENT '点击量',
    `like_count` bigint NULL DEFAULT NULL COMMENT '喜欢量',
    `star_count` bigint NULL DEFAULT NULL COMMENT '收藏量',
    `word_count` int NULL DEFAULT NULL COMMENT '总字数(已发布)',
    `comment_count` int NULL DEFAULT NULL COMMENT '评论数',
    `score` float NULL DEFAULT NULL COMMENT '评分：总分10',
    `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`, `author_id`) USING BTREE,
    INDEX `fk_book_author`(`author_id` ASC) USING BTREE,
    CONSTRAINT `fk_book_author` FOREIGN KEY (`author_id`) REFERENCES `author` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for book_comment
-- ----------------------------
create table if not exists book_comment  (
    `id` bigint NOT NULL COMMENT '小说评论id',
    `book_id` bigint NOT NULL COMMENT '小说id',
    `user_id` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户id（读者）',
    `upper_level_id` bigint NOT NULL COMMENT '上一级评论id',
    `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '评论内容',
    `like_count` int NULL DEFAULT NULL COMMENT '点赞数',
    `tread_count` int NULL DEFAULT NULL COMMENT '点踩数',
    `is_top` tinyint(1) NULL DEFAULT NULL COMMENT '是否置顶：0-否、1-是',
    `is_essence` tinyint(1) NULL DEFAULT NULL COMMENT '是否精华：0-否、1-是',
    `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fk_comment_book`(`book_id` ASC) USING BTREE,
    CONSTRAINT `fk_comment_book` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for book_tag
-- ----------------------------
create table if not exists book_tag  (
    `book_id` bigint NOT NULL COMMENT '小说ID',
    `tag_id` bigint NOT NULL COMMENT '标签ID',
    PRIMARY KEY (`book_id`, `tag_id`) USING BTREE,
    INDEX `fk_book_tag_tag`(`tag_id` ASC) USING BTREE,
    CONSTRAINT `fk_book_tag_book` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_book_tag_tag` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for chapter
-- ----------------------------
create table if not exists chapter  (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '小说章节id',
    `book_id` bigint NOT NULL COMMENT '小说id',
    `serial_number` int NOT NULL COMMENT '序号',
    `title` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标题',
    `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '内容',
    `word_count` int NULL DEFAULT NULL COMMENT '总字数',
    `status` tinyint(1) NOT NULL COMMENT '章节状态：0-创建（草稿）、1-发布、2-下架',
    `is_vip` tinyint(1) NOT NULL COMMENT '是否VIP专属：0-否、1-是',
    `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `publish_time` datetime NULL DEFAULT NULL COMMENT '发布时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fk_chapter_book`(`book_id` ASC) USING BTREE,
    CONSTRAINT `fk_chapter_book` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
create table if not exists goods  (
      `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品ID',
      `subject` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品标题，如VIP',
      `amount` decimal(11, 2) NOT NULL COMMENT '商品总金额，单位为元，精确到小数点后两位，取值范围：[0.01,100000000]，如9.00 ',
      `inventory` bigint NOT NULL COMMENT '库存量, -1表示不限量',
      `role_id` bigint NOT NULL COMMENT '商品权限，购买后即授予响应role权限',
      `effective_duration` int NOT NULL COMMENT '有效时长,即用户购买后持有商品的时长,单位分钟,-1表示购买即永久持有',
      `status` tinyint(1) NOT NULL COMMENT '商品状态,0-上架、2-告罄、3-下架',
      `create_time` datetime NULL DEFAULT NULL COMMENT '商品创建时间',
      `exhausted_time` datetime NULL DEFAULT NULL COMMENT '商品告罄时间',
      `take_down_time` datetime NULL DEFAULT NULL COMMENT '商品下架时间',
      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gorse_feedback
-- ----------------------------
create table if not exists gorse_feedback  (
       `feedback_type` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '反馈类型，包含like、star、read等',
       `user_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
       `item_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '物品(小说)ID',
       `time_stamp` datetime NOT NULL,
       `comment` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
       PRIMARY KEY (`feedback_type`, `user_id`, `item_id`) USING BTREE,
       INDEX `user_id`(`user_id` ASC) USING BTREE,
       INDEX `item_id`(`item_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for gorse_items
-- ----------------------------
create table if not exists gorse_items  (
        `item_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '物品(小说)的唯一ID，不能包含斜线',
        `time_stamp` datetime NOT NULL,
        `labels` json NOT NULL,
        `comment` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
        `is_hidden` tinyint(1) NOT NULL,
        `categories` json NOT NULL,
        PRIMARY KEY (`item_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for gorse_users
-- ----------------------------
create table if not exists gorse_users (
        `user_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
        `labels` json NOT NULL,
        `comment` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
        `subscribe` json NOT NULL,
        PRIMARY KEY (`user_id`) USING BTREE,
        CONSTRAINT `fk_gorse_users_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for oauth
-- ----------------------------
create table if not exists oauth  (
      `id` bigint(20) NOT NULL COMMENT '授权ID',
      `type_key` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '授权类型key',
      `type_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '授权类型名称',
      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for orders
-- ----------------------------
create table if not exists orders  (
       `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商户订单号，由商家自定义，64个字符以内，仅支持字母、数字、下划线且需保证在商户端不重复。如20150320010101001',
       `user_id` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
       `goods_id` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品ID',
       `total_amount` decimal(11, 2) NOT NULL COMMENT '订单总金额，单位为元，精确到小数点后两位，取值范围：[0.01,100000000]，如9.00 ',
       `subject` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单标题，如Iphone6 16G',
       `pay_mode` tinyint(1) NOT NULL COMMENT '支付方式，0-微信、1-支付宝',
       `trade_status` tinyint(1) NOT NULL COMMENT '订单状态，0-创建、1-已支付、2-退款、3-过期',
       `out_trade_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '支付平台交易凭证号',
       `buyer_id` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '买家在支付平台的唯一ID',
       `buyer_pay_amount` decimal(11, 2) NULL DEFAULT NULL COMMENT '买家付款金额',
       `receipt_amount` decimal(11, 2) NULL DEFAULT NULL COMMENT '实收金额（订单总金额 - 平台手续费）',
       `create_time` datetime NULL DEFAULT NULL COMMENT '订单创建时间',
       `pay_time` datetime NULL DEFAULT NULL COMMENT '订单支付时间',
       `refund_time` datetime NULL DEFAULT NULL COMMENT '订单退款时间',
       `expiration_time` datetime NULL DEFAULT NULL COMMENT '订单过期时间',
       PRIMARY KEY (`id`, `user_id`, `goods_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role
create table if not exists role  (
    `role_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户角色ID',
    `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
    `role_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色权限字符串',
    `role_sort` int NOT NULL COMMENT '显示顺序',
    `data_scope` tinyint(1) NULL DEFAULT NULL COMMENT '数据范围',
    `status` tinyint(1) NULL DEFAULT NULL COMMENT '角色状态',
    `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for tag
-- ----------------------------
create table if not exists tag  (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '标签ID',
    `name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标签名字',
    `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标签描述',
    `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`, `name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
create table if not exists undo_log  (
     `branch_id` bigint(20) NOT NULL COMMENT 'branch transaction id',
     `xid` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'global transaction id',
     `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'undo_log context,such as serialization',
     `rollback_info` longblob NOT NULL COMMENT 'rollback info',
     `log_status` int(11) NOT NULL COMMENT '0:normal status,1:defense status',
     `log_created` datetime(6) NOT NULL COMMENT 'create datetime',
     `log_modified` datetime(6) NOT NULL COMMENT 'modify datetime',
     UNIQUE INDEX `ux_undo_log`(`xid` ASC, `branch_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'AT transaction mode undo table' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for user
-- ----------------------------
create table if not exists user  (
    `user_id` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
    `uid` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'uid（100001开始）',
    `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名称（账号）',
    `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户密码',
    `nick_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户昵称',
    `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户头像（有默认头像）',
    `sex` tinyint(1) NOT NULL DEFAULT 2 COMMENT '用户性别（0：男，1:女，2：未知，默认为未知）',
    `birthday` datetime NULL DEFAULT NULL COMMENT '生日',
    `remark` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '自我介绍\r\n',
    `country` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '国家/地区',
    `region` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '所在区域',
    `address` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '具体地址',
    `phone_number` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电话号码',
    `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
    `status` tinyint(1) NULL DEFAULT 0 COMMENT '账户状态 0正常 1停用 2注销\n默认正常',
    `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户IP',
    `login_date` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '用户最后登录时间',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间\n',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`user_id`) USING BTREE,
    UNIQUE INDEX `userNameUniKey`(`user_name` ASC) USING BTREE COMMENT '用户名唯一'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for user_oauth
-- ----------------------------
create table if not exists user_oauth  (
    `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
    `oauth_id` bigint NOT NULL COMMENT '授权类型',
    `uuid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '第三方平台的用户UID',
    `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '第三方平台的用户账号',
    PRIMARY KEY (`user_id`, `oauth_id`) USING BTREE,
    INDEX `fk_user_oauth_oauth`(`oauth_id` ASC) USING BTREE,
    CONSTRAINT `fk_user_oauth_oauth` FOREIGN KEY (`oauth_id`) REFERENCES `oauth` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_user_oauth_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for user_role
-- ----------------------------
create table if not exists user_role (
    `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
    `role_id` bigint NOT NULL COMMENT '角色ID',
    `is_durable` tinyint(1) NOT NULL COMMENT '是否永久，0-永久，1-非永久',
    `expiration_time` datetime NULL DEFAULT NULL COMMENT '权限过期时间',
    PRIMARY KEY (`user_id`, `role_id`) USING BTREE,
    INDEX `fk_user_role_role`(`role_id` ASC) USING BTREE,
    CONSTRAINT `fk_user_role_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_user_role_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for user_tag
-- ----------------------------
create table if not exists user_tag (
    `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
    `tag_id` bigint NOT NULL COMMENT '标签ID',
    PRIMARY KEY (`user_id`, `tag_id`) USING BTREE,
    INDEX `fk_user_tag_tag`(`tag_id` ASC) USING BTREE,
    CONSTRAINT `fk_user_tag_tag` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_user_tag_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;


-- 创建库
create database if not exists mingink_seata;

-- 切换库
use mingink_seata;

-- 创建表
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for branch_table
-- ----------------------------
create table if not exists branch_table (
     `branch_id` bigint(20) NOT NULL,
     `xid` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
     `transaction_id` bigint(20) NULL DEFAULT NULL,
     `resource_group_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
     `resource_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
     `branch_type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
     `status` tinyint(4) NULL DEFAULT NULL,
     `client_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
     `application_data` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
     `gmt_create` datetime(6) NULL DEFAULT NULL,
     `gmt_modified` datetime(6) NULL DEFAULT NULL,
     PRIMARY KEY (`branch_id`) USING BTREE,
     INDEX `idx_xid`(`xid` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for distributed_lock
-- ----------------------------
create table if not exists distributed_lock  (
     `lock_key` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
     `lock_value` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
     `expire` bigint(20) NULL DEFAULT NULL,
     PRIMARY KEY (`lock_key`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for global_table
-- ----------------------------
create table if not exists global_table  (
     `xid` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
     `transaction_id` bigint(20) NULL DEFAULT NULL,
     `status` tinyint(4) NOT NULL,
     `application_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
     `transaction_service_group` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
     `transaction_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
     `timeout` int(11) NULL DEFAULT NULL,
     `begin_time` bigint(20) NULL DEFAULT NULL,
     `application_data` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
     `gmt_create` datetime NULL DEFAULT NULL,
     `gmt_modified` datetime NULL DEFAULT NULL,
     PRIMARY KEY (`xid`) USING BTREE,
     INDEX `idx_status_gmt_modified`(`status` ASC, `gmt_modified` ASC) USING BTREE,
     INDEX `idx_transaction_id`(`transaction_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for lock_table
-- ----------------------------
create table if not exists lock_table  (
       `row_key` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
       `xid` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
       `transaction_id` bigint(20) NULL DEFAULT NULL,
       `branch_id` bigint(20) NOT NULL,
       `resource_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
       `table_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
       `pk` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
       `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0:locked ,1:rollbacking',
       `gmt_create` datetime NULL DEFAULT NULL,
       `gmt_modified` datetime NULL DEFAULT NULL,
       PRIMARY KEY (`row_key`) USING BTREE,
       INDEX `idx_status`(`status` ASC) USING BTREE,
       INDEX `idx_branch_id`(`branch_id` ASC) USING BTREE,
       INDEX `idx_xid`(`xid` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

-- 切换库
use quartz_jobs;

-- 创建表
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for QRTZ_BLOB_TRIGGERS
-- ----------------------------
create table if not exists QRTZ_BLOB_TRIGGERS  (
       `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
       `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
       `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
       `BLOB_DATA` blob NULL,
       PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
       CONSTRAINT `QRTZ_BLOB_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for QRTZ_CALENDARS
-- ----------------------------
create table if not exists QRTZ_CALENDARS  (
       `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
       `CALENDAR_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
       `CALENDAR` blob NOT NULL,
       PRIMARY KEY (`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for QRTZ_CRON_TRIGGERS
-- ----------------------------
create table if not exists QRTZ_CRON_TRIGGERS  (
       `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
       `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
       `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
       `CRON_EXPRESSION` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
       `TIME_ZONE_ID` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
       PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
       CONSTRAINT `QRTZ_CRON_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for QRTZ_FIRED_TRIGGERS
-- ----------------------------
create table if not exists QRTZ_FIRED_TRIGGERS  (
        `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
        `ENTRY_ID` varchar(95) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
        `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
        `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
        `INSTANCE_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
        `FIRED_TIME` bigint NOT NULL,
        `SCHED_TIME` bigint NOT NULL,
        `PRIORITY` int NOT NULL,
        `STATE` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
        `JOB_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
        `JOB_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
        `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
        `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
        PRIMARY KEY (`SCHED_NAME`, `ENTRY_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for QRTZ_JOB_DETAILS
-- ----------------------------
create table if not exists QRTZ_JOB_DETAILS  (
         `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
         `JOB_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
         `JOB_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
         `DESCRIPTION` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
         `JOB_CLASS_NAME` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
         `IS_DURABLE` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
         `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
         `IS_UPDATE_DATA` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
         `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
         `JOB_DATA` blob NULL,
         PRIMARY KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for QRTZ_LOCKS
-- ----------------------------
create table if not exists QRTZ_LOCKS  (
       `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
       `LOCK_NAME` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
       PRIMARY KEY (`SCHED_NAME`, `LOCK_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for QRTZ_PAUSED_TRIGGER_GRPS
-- ----------------------------
create table if not exists QRTZ_PAUSED_TRIGGER_GRPS  (
         `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
         `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
         PRIMARY KEY (`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for QRTZ_SCHEDULER_STATE
-- ----------------------------
create table if not exists QRTZ_SCHEDULER_STATE  (
         `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
         `INSTANCE_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
         `LAST_CHECKIN_TIME` bigint NOT NULL,
         `CHECKIN_INTERVAL` bigint NOT NULL,
         PRIMARY KEY (`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for QRTZ_SIMPLE_TRIGGERS
-- ----------------------------
create table if not exists QRTZ_SIMPLE_TRIGGERS  (
         `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
         `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
         `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
         `REPEAT_COUNT` bigint NOT NULL,
         `REPEAT_INTERVAL` bigint NOT NULL,
         `TIMES_TRIGGERED` bigint NOT NULL,
         PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
         CONSTRAINT `QRTZ_SIMPLE_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for QRTZ_SIMPROP_TRIGGERS
-- ----------------------------
create table if not exists QRTZ_SIMPROP_TRIGGERS  (
          `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
          `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
          `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
          `STR_PROP_1` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
          `STR_PROP_2` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
          `STR_PROP_3` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
          `INT_PROP_1` int NULL DEFAULT NULL,
          `INT_PROP_2` int NULL DEFAULT NULL,
          `LONG_PROP_1` bigint NULL DEFAULT NULL,
          `LONG_PROP_2` bigint NULL DEFAULT NULL,
          `DEC_PROP_1` decimal(13, 4) NULL DEFAULT NULL,
          `DEC_PROP_2` decimal(13, 4) NULL DEFAULT NULL,
          `BOOL_PROP_1` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
          `BOOL_PROP_2` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
          PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
          CONSTRAINT `QRTZ_SIMPROP_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for QRTZ_TRIGGERS
-- ----------------------------
create table if not exists QRTZ_TRIGGERS  (
          `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
          `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
          `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
          `JOB_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
          `JOB_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
          `DESCRIPTION` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
          `NEXT_FIRE_TIME` bigint NULL DEFAULT NULL,
          `PREV_FIRE_TIME` bigint NULL DEFAULT NULL,
          `PRIORITY` int NULL DEFAULT NULL,
          `TRIGGER_STATE` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
          `TRIGGER_TYPE` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
          `START_TIME` bigint NOT NULL,
          `END_TIME` bigint NULL DEFAULT NULL,
          `CALENDAR_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
          `MISFIRE_INSTR` smallint NULL DEFAULT NULL,
          `JOB_DATA` blob NULL,
          PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
          INDEX `SCHED_NAME`(`SCHED_NAME` ASC, `JOB_NAME` ASC, `JOB_GROUP` ASC) USING BTREE,
          CONSTRAINT `QRTZ_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `QRTZ_JOB_DETAILS` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
