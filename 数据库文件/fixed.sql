/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : parking

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2020-02-07 17:10:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `fixed`
-- ----------------------------
DROP TABLE IF EXISTS `fixed`;
CREATE TABLE `fixed` (
  `card_id` varchar(50) NOT NULL,
  `entry_date` date NOT NULL,
  `entry_time` time NOT NULL,
  `out_date` date DEFAULT NULL,
  `out_time` time DEFAULT NULL,
  PRIMARY KEY (`card_id`,`entry_date`,`entry_time`),
  CONSTRAINT `fixed_ibfk_1` FOREIGN KEY (`card_id`) REFERENCES `fixedcard` (`card_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fixed
-- ----------------------------

