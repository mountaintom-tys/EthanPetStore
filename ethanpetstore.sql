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

 Date: 14/04/2020 20:54:39
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
  `status` int(11) NULL DEFAULT 1 COMMENT '产品状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (10, '哈士奇', 'upload/20041053196.jpg', 100, '可爱的很', 20, 4, 1);
INSERT INTO `goods` VALUES (14, '阿斯蒂', 'upload/20041038979.jpg', 20, '阿斯蒂', 10, 2, 1);
INSERT INTO `goods` VALUES (15, '泰迪', 'upload/20041027478.jpg', 20, 'asdf', 10, 5, 1);
INSERT INTO `goods` VALUES (16, '周静儿', 'upload/20031511552.jpg', 20, '猪', 1, 3, 2);

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
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

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
INSERT INTO `orders` VALUES (2, 11827, 4, 2, 3, '唐意山', '15181777983', '四川剑阁', '2020-01-31 19:23:16', 15);

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
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户电话/登录账号',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `security_question` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密保问题',
  `security_answer` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密保答案',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货人(用户真实姓名)',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收获地址',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `users_unique_phone`(`phone`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (15, 'ethan', '15181777983', 'HAMVRZRssPCADKqGjGWJtQ==', NULL, NULL, '唐意山', '四川剑阁');

SET FOREIGN_KEY_CHECKS = 1;
