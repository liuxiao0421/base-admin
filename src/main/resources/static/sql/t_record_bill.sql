/*
 Navicat Premium Data Transfer

 Source Server         : mysql-localhost
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : localhost:3306
 Source Schema         : record

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 11/10/2020 23:16:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_record_bill
-- ----------------------------
DROP TABLE IF EXISTS `t_record_bill`;
CREATE TABLE `t_record_bill` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `gmt_create` varchar(64) NOT NULL COMMENT '创建时间',
  `gmt_modify` varchar(64) DEFAULT NULL COMMENT '修改时间',
  `receivable_amt` varchar(64) NOT NULL COMMENT '应收金额',
  `net_receipts_amt` varchar(64) DEFAULT NULL COMMENT '实收金额',
  `is_end` char(1) NOT NULL DEFAULT '0' COMMENT '0：未完结，1:已完结',
  `user_id` varchar(32) NOT NULL COMMENT '用户标识',
  `bill_desc` varchar(255) NOT NULL COMMENT '账单说明',
  `payer` varchar(128) NOT NULL COMMENT '付款人',
  `project_nature` varchar(128) NOT NULL COMMENT '项目性质（广告，专题等）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_record_bill
-- ----------------------------
BEGIN;
INSERT INTO `t_record_bill` VALUES ('10', '20201004', NULL, '3489', '3489', '0', '001', '账单10', '马云', '');
INSERT INTO `t_record_bill` VALUES ('11', '20201004', NULL, '789', '789', '1', '001', '账单11', '马化腾', '');
INSERT INTO `t_record_bill` VALUES ('12', '20201004', NULL, '230', '230', '1', '001', '账单12', '李彦宏', '');
INSERT INTO `t_record_bill` VALUES ('135113d8e61a40cbbc352f3949f7c8b9', '20201011', NULL, '250', '250', '0', '1', '马云拖欠的账单', '马云', '广告');
INSERT INTO `t_record_bill` VALUES ('1d390fe554434784af6bf924483b8bb4', '20201011', NULL, '100', '100', '0', '1', '张三的账单', '张三', '');
INSERT INTO `t_record_bill` VALUES ('3', '20201004', NULL, '1290', '1290', '1', '001', '账单03', '许家印', '');
INSERT INTO `t_record_bill` VALUES ('30fd1fb27ee24d99bd46590adcae5c2b', '20101011', NULL, '120', '120', '0', '1', '账单描述', '刘强东', '');
INSERT INTO `t_record_bill` VALUES ('4', '20201004', NULL, '1200', '1200', '0', '001', '账单04', '张近东', '');
INSERT INTO `t_record_bill` VALUES ('47f00bbf0f104c479439ad4a5b924f78', '20201011', NULL, '120', '120', '0', '1', '账单描述', '张一鸣', '');
INSERT INTO `t_record_bill` VALUES ('5', '20201004', NULL, '2900', '1902', '0', '001', '账单05', '刘德华', '');
INSERT INTO `t_record_bill` VALUES ('5c18976796754dfdb2b4ed939c0fee51', '20201012', NULL, '110', '110', '0', '1', '二狗的待结算订单', '二狗', '');
INSERT INTO `t_record_bill` VALUES ('6', '20201004', NULL, '2901', '2900', '1', '001', '账单06', '梁朝伟', '');
INSERT INTO `t_record_bill` VALUES ('7', '20201004', NULL, '280', '280', '1', '001', '账单07', '郭富城', '');
INSERT INTO `t_record_bill` VALUES ('8', '20201004', NULL, '289', '289', '1', '001', '账单08', '张学友', '');
INSERT INTO `t_record_bill` VALUES ('9', '20201004', NULL, '2789', '2789', '1', '001', '账单09', '黎明', '');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
