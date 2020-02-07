/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : parking

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2020-02-07 17:10:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `fixedcard`
-- ----------------------------
DROP TABLE IF EXISTS `fixedcard`;
CREATE TABLE `fixedcard` (
  `card_id` varchar(14) NOT NULL,
  `seat_id` varchar(3) NOT NULL,
  `name` varchar(10) NOT NULL,
  `address` varchar(20) NOT NULL,
  `car_num` varchar(7) NOT NULL,
  PRIMARY KEY (`card_id`),
  KEY `seat_id` (`seat_id`),
  CONSTRAINT `seat_id` FOREIGN KEY (`seat_id`) REFERENCES `seat` (`seat_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fixedcard
-- ----------------------------

