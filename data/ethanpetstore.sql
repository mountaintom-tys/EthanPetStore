/*
 Navicat Premium Data Transfer

 Source Server         : Mysql
 Source Server Type    : MySQL
 Source Server Version : 50556
 Source Host           : localhost:3306
 Source Schema         : ethanpetstore

 Target Server Type    : MySQL
 Target Server Version : 50556
 File Encoding         : 65001

 Date: 16/11/2020 14:58:41
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
  `amount` int(11) NULL DEFAULT NULL COMMENT '商品数量',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `good_id` int(11) NOT NULL COMMENT '商品id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `Carts_unique_userIdAndGoodId`(`user_id`, `good_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

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
) ENGINE = InnoDB AUTO_INCREMENT = 40 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户收藏表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of collections
-- ----------------------------
INSERT INTO `collections` VALUES (39, 25, 25);
INSERT INTO `collections` VALUES (38, 25, 26);
INSERT INTO `collections` VALUES (37, 25, 27);

-- ----------------------------
-- Table structure for good_type
-- ----------------------------
DROP TABLE IF EXISTS `good_type`;
CREATE TABLE `good_type`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of good_type
-- ----------------------------
INSERT INTO `good_type` VALUES (1, '宠物周边');
INSERT INTO `good_type` VALUES (2, '猫星人');
INSERT INTO `good_type` VALUES (3, '大型犬');
INSERT INTO `good_type` VALUES (4, '中型犬');
INSERT INTO `good_type` VALUES (5, '小型犬');

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
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (22, '单身狗', '/static/upload/20111621570.jpg', 1111, '单身的狗', 11, 5, 1);
INSERT INTO `goods` VALUES (23, '猫猫', '/static/upload/20111657606.jpg', 22, 'cute', 12, 2, 1);
INSERT INTO `goods` VALUES (24, '一包狗粮', '/static/upload/20111303342.jpg', 100, '好吃极了', 20, 1, 1);
INSERT INTO `goods` VALUES (25, '情侣装', '/static/upload/20111650985.jpg', 520, '不是穿上情侣装就可以装情侣', 1314, 1, 1);
INSERT INTO `goods` VALUES (26, '金毛', '/static/upload/20111602283.jpg', 1000, '金毛狮王', 500, 3, 1);
INSERT INTO `goods` VALUES (27, '狗粮2', '/static/upload/20111651357.jpg', 10, '好吃不腻', 10, 4, 1);

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
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单项表，每个订单可能有多个商品，由此表关联' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of items
-- ----------------------------
INSERT INTO `items` VALUES (45, 10, 1, 36, 1, 27);
INSERT INTO `items` VALUES (46, 520, 1, 37, 2, 25);

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `total` float(11, 0) NULL DEFAULT NULL COMMENT '总价',
  `amount` int(11) NULL DEFAULT NULL COMMENT '商品总数',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '订单状态（1未付款/2已付款/3已发货/4已完成）',
  `paytype` tinyint(4) NULL DEFAULT 0 COMMENT '支付方式（1微信/2支付宝/3货到付款）',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货人',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收获电话',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收获地址',
  `systime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '下单时间',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '下单用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (36, 10, 1, 1, NULL, '123', '15181777983', '暂未选择地址', '2020-11-16 14:05:38', 25);
INSERT INTO `orders` VALUES (37, 520, 1, 1, NULL, '123', '15181777983', '暂未选择地址', '2020-11-16 14:08:09', 25);

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
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (25, '123', '15181777983', 'F6YKH0yuxcnyFb4GQnX4Yg==', '123', 'yJOXpxCuYkm+zIQrRHPdpQ==', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
