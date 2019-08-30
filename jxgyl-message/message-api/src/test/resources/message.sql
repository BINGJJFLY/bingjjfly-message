/*
Navicat MySQL Data Transfer

Source Server         : dashboard_user(preonline)
Source Server Version : 50626
Source Host           : 172.16.12.2:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50626
File Encoding         : 65001

Date: 2019-08-28 10:23:21
*/

create database message default character set utf8 collate utf8_general_ci;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ATTACHMENT
-- ----------------------------
DROP TABLE IF EXISTS `ATTACHMENT`;
CREATE TABLE `ATTACHMENT` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `MSG_ID` int(11) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `PATH` varchar(255) DEFAULT NULL,
  `ADD_TIME` datetime DEFAULT NULL,
  `STATUS` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for MESSAGE
-- ----------------------------
DROP TABLE IF EXISTS `MESSAGE`;
CREATE TABLE `MESSAGE` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `SENDER` varchar(255) DEFAULT NULL,
  `RECEIVER` varchar(255) DEFAULT NULL COMMENT '多个用英文逗号隔开',
  `SUBJECT` varchar(255) DEFAULT NULL,
  `TEXT` varchar(255) DEFAULT NULL,
  `TYPE` varchar(255) DEFAULT NULL,
  `TEMPLATE` varchar(255) DEFAULT NULL,
  `ADD_TIME` datetime DEFAULT NULL,
  `STATUS` int(11) DEFAULT NULL,
  `IDENTIFY_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for VARIABLE
-- ----------------------------
DROP TABLE IF EXISTS `VARIABLE`;
CREATE TABLE `VARIABLE` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `MSG_ID` int(11) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `VAL` varchar(255) DEFAULT NULL,
  `ADD_TIME` datetime DEFAULT NULL,
  `STATUS` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
