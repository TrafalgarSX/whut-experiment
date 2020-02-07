/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : parking

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2020-02-07 17:10:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tempcard`
-- ----------------------------
DROP TABLE IF EXISTS `tempcard`;
CREATE TABLE `tempcard` (
  `card_id` varchar(14) NOT NULL,
  `seat_id` varchar(3) NOT NULL,
  `car_num` varchar(7) NOT NULL,
  `entry_date` date NOT NULL,
  `entry_time` time NOT NULL,
  `out_date` date DEFAULT NULL,
  `out_time` time DEFAULT NULL,
  `pay` double(5,0) DEFAULT NULL,
  PRIMARY KEY (`card_id`),
  KEY `seat_id` (`seat_id`),
  CONSTRAINT `tempcard_ibfk_1` FOREIGN KEY (`seat_id`) REFERENCES `seat` (`seat_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tempcard
-- ----------------------------
