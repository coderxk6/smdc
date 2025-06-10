-- 创建数据库
DROP DATABASE IF EXISTS smdc;
CREATE DATABASE smdc DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE smdc;

-- 用户表
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `open_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信openid',
  `session_key` varchar(255) DEFAULT NULL COMMENT '会话密钥',
  `nick_name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '昵称',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `avatar_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像',
  `gender` tinyint(2) DEFAULT '0' COMMENT '性别 0-未知 1-男 2-女',
  `city` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '城市',
  `province` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '省份',
  `country` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '国家',
  `phone` varchar(11) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_open_id` (`open_id`),
  UNIQUE KEY `idx_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户';

-- 添加测试用户
INSERT INTO `user` (`id`, `nick_name`, `avatar_url`, `gender`, `city`, `province`, `country`, `phone`, `session_key`, `username`, `password`, `last_login_time`, `create_time`, `update_time`) 
VALUES (9999, '测试用户', '/images/default-avatar.png', 1, '广州', '广东', '中国', '13800138000', 'init_session_key', 'test', '123456', NOW(), NOW(), NOW());

-- 员工表
CREATE TABLE IF NOT EXISTS `employee` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `name` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '姓名',
  `phone` varchar(11) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号',
  `role` tinyint(4) NOT NULL DEFAULT '2' COMMENT '角色，1管理员，2普通员工',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='员工信息表';

-- 初始化管理员账号，密码为123456的MD5值
INSERT INTO `employee` (`id`, `username`, `password`, `name`, `role`, `create_time`, `update_time`) 
VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', '管理员', 1, NOW(), NOW());

-- 添加测试账号，密码为123456的MD5值
INSERT INTO `employee` (`id`, `username`, `password`, `name`, `role`, `phone`, `create_time`, `update_time`) 
VALUES (2, 'test', 'e10adc3949ba59abbe56e057f20f883e', '测试账号', 2, '13800138000', NOW(), NOW());

-- 分类表
CREATE TABLE IF NOT EXISTS `category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '分类名称',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='菜品分类表';

-- 添加测试分类数据
INSERT INTO `category` (`id`, `name`, `sort`, `create_time`, `update_time`) VALUES
(1, '热销推荐', 1, NOW(), NOW()),
(2, '新品上市', 2, NOW(), NOW()),
(3, '特色小吃', 3, NOW(), NOW()),
(4, '饮品', 4, NOW(), NOW());

-- 菜品表
CREATE TABLE IF NOT EXISTS `dish` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜品名称',
  `category_id` bigint(20) NOT NULL COMMENT '分类id',
  `price` decimal(10,2) NOT NULL COMMENT '价格',
  `image` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图片',
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述信息',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '售卖状态 0:停售 1:起售',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_category_id` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='菜品表';

-- 添加测试菜品数据
INSERT INTO `dish` (`id`, `name`, `category_id`, `price`, `image`, `description`, `status`, `sort`, `create_time`, `update_time`) VALUES
(1, '麻婆豆腐', 1, 32.00, '/images/dish/mapodoufu.jpg', '麻婆豆腐，香辣可口，米饭杀手', 1, 1, NOW(), NOW()),
(2, '宫保鸡丁', 1, 48.00, '/images/dish/gongbaojiding.jpg', '宫保鸡丁，经典川菜，老少皆宜', 1, 2, NOW(), NOW()),
(3, '回锅肉', 1, 58.00, '/images/dish/huiguorou.jpg', '回锅肉，肥而不腻，香辣下饭', 1, 3, NOW(), NOW()),
(4, '酸辣土豆丝', 3, 22.00, '/images/dish/suanlatudousi.jpg', '酸辣土豆丝，清爽开胃，酸辣适中', 1, 1, NOW(), NOW()),
(5, '可乐', 4, 8.00, '/images/dish/kele.jpg', '冰镇可乐，解渴神器', 1, 1, NOW(), NOW());

-- 规格表
CREATE TABLE IF NOT EXISTS `specification` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dish_id` bigint(20) NOT NULL COMMENT '菜品id',
  `name` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '规格名称',
  `price` decimal(10,2) NOT NULL COMMENT '价格',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_dish_id` (`dish_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='规格表';

-- 添加测试规格数据
INSERT INTO `specification` (`id`, `dish_id`, `name`, `price`, `create_time`, `update_time`) VALUES
(1, 1, '小份', 32.00, NOW(), NOW()),
(2, 1, '中份', 42.00, NOW(), NOW()),
(3, 1, '大份', 52.00, NOW(), NOW()),
(4, 2, '小份', 48.00, NOW(), NOW()),
(5, 2, '大份', 68.00, NOW(), NOW()),
(6, 5, '大杯', 8.00, NOW(), NOW()),
(7, 5, '小杯', 5.00, NOW(), NOW());

-- 桌位表
CREATE TABLE IF NOT EXISTS `table_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '桌位名称',
  `code` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '桌位二维码',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态 0:空闲 1:使用中',
  `capacity` int(11) DEFAULT NULL COMMENT '容纳人数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='桌位表';

-- 订单表
CREATE TABLE IF NOT EXISTS `order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `number` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单号',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `table_id` bigint(20) NOT NULL COMMENT '桌位id',
  `amount` decimal(10,2) NOT NULL COMMENT '总金额',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '订单状态 1:待付款 2:待接单 3:待上菜 4:已完成 5:已取消',
  `pay_method` tinyint(4) DEFAULT NULL COMMENT '支付方式 1:微信支付 2:支付宝支付',
  `pay_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '支付状态 0:未支付 1:已支付',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_number` (`number`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_table_id` (`table_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表';

-- 订单明细表
CREATE TABLE IF NOT EXISTS `order_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_id` bigint(20) NOT NULL COMMENT '订单id',
  `dish_id` bigint(20) NOT NULL COMMENT '菜品id',
  `specification_id` bigint(20) DEFAULT NULL COMMENT '规格id',
  `number` int(11) NOT NULL COMMENT '数量',
  `amount` decimal(10,2) NOT NULL COMMENT '金额',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单明细表';

-- 店铺信息表
CREATE TABLE `shop_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '店铺名称',
  `slogan` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '店铺标语',
  `logo` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '店铺logo',
  `address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '店铺地址',
  `phone` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系电话',
  `business_hours` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '营业时间',
  `longitude` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '经度',
  `latitude` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '纬度',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态，0关闭，1营业中',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='店铺信息表';

-- 添加测试桌位
INSERT INTO `table_info` (`name`, `code`, `status`, `capacity`, `create_time`, `update_time`) VALUES 
('A1', 'TABLE_A1', 0, 4, NOW(), NOW()),
('A2', 'TABLE_A2', 0, 6, NOW(), NOW()),
('B1', 'TABLE_B1', 0, 2, NOW(), NOW()),
('B2', 'TABLE_B2', 0, 8, NOW(), NOW());

-- 添加测试店铺信息
INSERT INTO `shop_info` (`name`, `slogan`, `logo`, `address`, `phone`, `business_hours`, `longitude`, `latitude`, `status`, `create_time`, `update_time`) VALUES 
('测试餐厅', '用心做好每一道菜', '/images/shop/logo.png', '广州市天河区天河路123号', '020-12345678', '10:00-22:00', '113.323568', '23.129163', 1, NOW(), NOW()); 