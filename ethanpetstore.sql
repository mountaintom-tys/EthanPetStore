/*
 Navicat Premium Data Transfer

 Source Server         : MySql
 Source Server Type    : MySQL
 Source Server Version : 50556
 Source Host           : localhost:3306
 Source Schema         : ethanpetstore

 Target Server Type    : MySQL
 Target Server Version : 50556
 File Encoding         : 65001

 Date: 19/04/2020 21:43:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admins
-- ----------------------------
DROP TABLE IF EXISTS `admins`;
CREATE TABLE `admins`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `security_question` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `security_answer` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `admins_unique_username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of admins
-- ----------------------------
INSERT INTO `admins` VALUES (1, 'admin', 'tuShOfiBrA8+br7ENrMS8A==', '我的英文名？', 'jNtdFOchMLVpIPVGBNjaLQ==');

-- ----------------------------
-- Table structure for carts
-- ----------------------------
DROP TABLE IF EXISTS `carts`;
CREATE TABLE `carts`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `total` int(11) NULL DEFAULT NULL COMMENT '总价',
  `amount` int(11) NULL DEFAULT NULL COMMENT '商品总数',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for collections
-- ----------------------------
DROP TABLE IF EXISTS `collections`;
CREATE TABLE `collections`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `good_id` int(11) NOT NULL COMMENT '商品id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `collections_unique_uid_gid`(`user_id`, `good_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户收藏表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of collections
-- ----------------------------
INSERT INTO `collections` VALUES (19, 22, 10);
INSERT INTO `collections` VALUES (18, 22, 14);
INSERT INTO `collections` VALUES (24, 22, 19);
INSERT INTO `collections` VALUES (20, 23, 14);
INSERT INTO `collections` VALUES (22, 23, 17);
INSERT INTO `collections` VALUES (13, 23, 19);
INSERT INTO `collections` VALUES (17, 24, 17);
INSERT INTO `collections` VALUES (16, 24, 18);

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `cover` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '封面',
  `price` int(11) NULL DEFAULT NULL COMMENT '价格',
  `intro` varchar(2550) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '介绍',
  `stock` int(11) NULL DEFAULT NULL COMMENT '库存',
  `type_id` int(11) NULL DEFAULT NULL COMMENT '分类',
  `status` int(11) NULL DEFAULT 1 COMMENT '产品状态：上线1，下架2',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (10, '哈士奇', 'upload/20041751187.jpg', 100, '可爱的很', 20, 2, 1);
INSERT INTO `goods` VALUES (14, '阿斯蒂', 'upload/20041742117.jpg', 20, '这是一个很神奇的dog，带给你快乐，哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈，怎么样是不是很搞笑哈哈哈哈哈哈哈哈哈哇哈哈哈哈哈哈哈哈哈', 10, 3, 1);
INSERT INTO `goods` VALUES (15, '这是什么狗', 'upload/20041733242.jpg', 20, 'asdf', 10, 4, 1);
INSERT INTO `goods` VALUES (16, '周静儿', 'upload/20031511552.jpg', 20, '猪', 1, 3, 2);
INSERT INTO `goods` VALUES (17, '泰迪单身狗', 'upload/20041746728.jpg', 12, 'qwerf', 1, 5, 1);
INSERT INTO `goods` VALUES (18, '小金毛', 'upload/20041729720.jpg', 12, '1212', 1, 5, 1);
INSERT INTO `goods` VALUES (19, '泰迪情侣', 'upload/20041754644.jpg', 32, '232', 1, 5, 1);
INSERT INTO `goods` VALUES (20, '一包狗粮', 'upload/20041916220.jpg', 20, '好吃不胖', 10, 1, 1);

-- ----------------------------
-- Table structure for items
-- ----------------------------
DROP TABLE IF EXISTS `items`;
CREATE TABLE `items`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `price` int(11) NULL DEFAULT NULL COMMENT '购买时价格',
  `amount` int(11) NULL DEFAULT NULL COMMENT '购买数量',
  `order_id` int(11) NULL DEFAULT NULL COMMENT '订单id',
  `cart_id` int(11) NULL DEFAULT NULL COMMENT '购物车id',
  `good_id` int(11) NULL DEFAULT NULL COMMENT '蛋糕id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单项表，每个订单可能有多个商品，由此表关联' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of items
-- ----------------------------
INSERT INTO `items` VALUES (3, 1028, 1, 2, 2, 10);
INSERT INTO `items` VALUES (4, 999, 1, 2, 2, 14);

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `total` int(11) NULL DEFAULT NULL COMMENT '总价',
  `amount` int(11) NULL DEFAULT NULL COMMENT '商品总数',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '订单状态（1未付款/2已付款/3已发货/4已完成）',
  `paytype` tinyint(4) NULL DEFAULT 0 COMMENT '支付方式（1微信/2支付宝/3货到付款）',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货人',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收获电话',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收获地址',
  `systime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '下单时间',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '下单用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (2, 11827, 4, 2, 3, '唐意山', '15181777983', '四川剑阁', '2020-04-17 16:14:44', 22);

-- ----------------------------
-- Table structure for types
-- ----------------------------
DROP TABLE IF EXISTS `types`;
CREATE TABLE `types`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of types
-- ----------------------------
INSERT INTO `types` VALUES (1, '宠物周边');
INSERT INTO `types` VALUES (2, '猫星人');
INSERT INTO `types` VALUES (3, '大型犬');
INSERT INTO `types` VALUES (4, '中型犬');
INSERT INTO `types` VALUES (5, '小型犬');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户电话/登录账号',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `security_question` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密保问题',
  `security_answer` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密保答案',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货人(用户真实姓名)',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收获地址',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `users_unique_phone`(`phone`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (22, 'ethant', '15181777983', 'a0oXLKopgPh2Emu56MC12Q==', '123?', 'yJOXpxCuYkm+zIQrRHPdpQ==', NULL, NULL);
INSERT INTO `users` VALUES (23, '123', '15181777984', 'a0oXLKopgPh2Emu56MC12Q==', '123?', 'yJOXpxCuYkm+zIQrRHPdpQ==', NULL, NULL);
INSERT INTO `users` VALUES (24, '321', '15181777985', 'a0oXLKopgPh2Emu56MC12Q==', '123?', 'yJOXpxCuYkm+zIQrRHPdpQ==', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
