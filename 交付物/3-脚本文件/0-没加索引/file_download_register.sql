/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : fileinfo

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2021-07-07 13:38:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `file_download_register`
-- ----------------------------
DROP TABLE IF EXISTS `file_download_register`;
CREATE TABLE `file_download_register` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `original_file_name` varchar(256) NOT NULL COMMENT '原文件名',
  `new_file_name` varchar(256) DEFAULT NULL COMMENT '新文件名',
  `file_id` varchar(12) DEFAULT NULL COMMENT '文件ID',
  `download_operator` varchar(40) NOT NULL COMMENT '文件下载人员ID',
  `download_time` datetime NOT NULL COMMENT '文件下载时间',
  `create_time` datetime NOT NULL COMMENT '记录创建时间',
  `update_time` datetime NOT NULL COMMENT '记录更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of file_download_register
-- ----------------------------
INSERT INTO `file_download_register` VALUES ('1', 'TestFile2021070601.txt', 'salt_1625558433800_2021-07-06_240TestFile2021070601.txt', '5', '001', '2021-07-06 16:30:04', '2021-07-06 16:30:04', '2021-07-06 16:30:04');
INSERT INTO `file_download_register` VALUES ('2', 'TestFile2021070601.txt', 'salt_1625558433800_2021-07-06_240TestFile2021070601.txt', '5', '001', '2021-07-06 16:45:29', '2021-07-06 16:45:29', '2021-07-06 16:45:29');
INSERT INTO `file_download_register` VALUES ('3', 'TestFile2021070601.txt', 'salt_1625558433800_2021-07-06_240TestFile2021070601.txt', '5', '001', '2021-07-06 16:50:22', '2021-07-06 16:50:22', '2021-07-06 16:50:22');
INSERT INTO `file_download_register` VALUES ('4', 'tjjTest.txt', 'salt_1625562033093_2021-07-06_392_tjjTest.txt', '6', '001', '2021-07-06 17:02:00', '2021-07-06 17:02:00', '2021-07-06 17:02:00');
