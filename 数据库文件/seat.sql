/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : parking

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2020-02-07 17:10:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `seat`
-- ----------------------------
DROP TABLE IF EXISTS `seat`;
CREATE TABLE `seat` (
  `seat_id` varchar(3) NOT NULL,
  `seat_section` varchar(2) NOT NULL,
  `seat_state` int(2) NOT NULL,
  `seat_tag` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`seat_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of seat
-- ----------------------------
INSERT INTO `seat` VALUES ('1', 'A区', '0', '固定车主车位');
INSERT INTO `seat` VALUES ('10', 'A区', '1', '固定车主车位');
INSERT INTO `seat` VALUES ('100', 'A区', '0', '固定车主车位');
INSERT INTO `seat` VALUES ('11', 'B区', '0', '临时车主车位');
INSERT INTO `seat` VALUES ('12', 'B区', '0', '临时车主车位');
INSERT INTO `seat` VALUES ('13', 'B区', '0', '临时车主车位');
INSERT INTO `seat` VALUES ('14', 'B区', '0', '临时车主车位');
INSERT INTO `seat` VALUES ('16', 'B区', '0', '临时车主车位');
INSERT INTO `seat` VALUES ('17', 'B区', '0', '临时车主车位');
INSERT INTO `seat` VALUES ('18', 'B区', '0', '临时车主车位');
INSERT INTO `seat` VALUES ('19', 'B区', '0', '临时车主车位');
INSERT INTO `seat` VALUES ('2', 'A区', '0', '固定车主车位');
INSERT INTO `seat` VALUES ('20', 'B区', '0', '临时车主车位');
INSERT INTO `seat` VALUES ('21', 'B区', '1', '临时车主车位');
INSERT INTO `seat` VALUES ('3', 'A区', '0', '固定车主车位');
INSERT INTO `seat` VALUES ('4', 'A区', '0', '固定车主车位');
INSERT INTO `seat` VALUES ('5', 'A区', '0', '固定车主车位');
INSERT INTO `seat` VALUES ('50', 'A区', '0', '固定车主车位');
INSERT INTO `seat` VALUES ('51', 'A区', '0', '固定车主车位');
INSERT INTO `seat` VALUES ('52', 'A区', '0', '固定车主车位');
INSERT INTO `seat` VALUES ('6', 'A区', '0', '固定车主车位');
INSERT INTO `seat` VALUES ('7', 'A区', '0', '固定车主车位');
INSERT INTO `seat` VALUES ('8', 'A区', '1', '固定车主车位');
INSERT INTO `seat` VALUES ('9', 'A区', '1', '固定车主车位');
