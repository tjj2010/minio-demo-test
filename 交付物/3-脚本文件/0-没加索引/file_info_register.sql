/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : fileinfo

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2021-07-07 13:38:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `file_info_register`
-- ----------------------------
DROP TABLE IF EXISTS `file_info_register`;
CREATE TABLE `file_info_register` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `original_file_name` varchar(256) NOT NULL COMMENT '原文件名',
  `new_file_name` varchar(256) NOT NULL COMMENT '新文件名：文件上传生命名之后生成的文件名',
  `module_code` varchar(100) NOT NULL COMMENT '所属模块编码',
  `business_id` varchar(60) NOT NULL COMMENT '文件所属业务ID',
  `upload_operator` varchar(40) NOT NULL COMMENT '上传人ID',
  `delete_operator` varchar(40) DEFAULT NULL COMMENT '删除人员',
  `file_size` varchar(10) DEFAULT NULL COMMENT '文件大小',
  `file_type` varchar(20) DEFAULT NULL COMMENT '文件类型',
  `upload_time` datetime NOT NULL COMMENT '文件上传时间',
  `delete_time` datetime DEFAULT NULL COMMENT '文件删除时间',
  `state` varchar(2) NOT NULL DEFAULT '' COMMENT '文件状态：00—正常，01—已删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of file_info_register
-- ----------------------------
INSERT INTO `file_info_register` VALUES ('4', 'TestFile2021070601.txt', 'salt_1625558306086_2021-07-06_171.txt', 'module01', 'businessId01', '001', null, '38', '.txt', '2021-07-06 15:58:26', null, '00', '2021-07-06 15:58:26', '2021-07-06 15:58:26');
INSERT INTO `file_info_register` VALUES ('5', 'TestFile2021070601.txt', 'salt_1625558433800_2021-07-06_240TestFile2021070601.txt', 'module01', 'businessId01', '001', null, '38', '.txt', '2021-07-06 16:00:34', null, '00', '2021-07-06 16:00:34', '2021-07-06 16:00:34');
INSERT INTO `file_info_register` VALUES ('6', 'tjjTest.txt', 'salt_1625562033093_2021-07-06_392_tjjTest.txt', 'module01', 'businessId01', '001', '001', '4', '.txt', '2021-07-06 17:00:33', '2021-07-06 17:02:10', '01', '2021-07-06 17:00:33', '2021-07-06 17:02:10');
INSERT INTO `file_info_register` VALUES ('7', 'tjjTest.txt', 'salt_1625562445839_2021-07-06_886_tjjTest.txt', 'module01', 'businessId01', '001', null, '4', '.txt', '2021-07-06 17:07:26', null, '00', '2021-07-06 17:07:26', '2021-07-06 17:07:26');
