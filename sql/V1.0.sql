 
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for im_group
-- ----------------------------
DROP TABLE IF EXISTS `im_group`;
CREATE TABLE `im_group` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `create_uid` bigint(11) NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='\n群组表';

-- ----------------------------
-- Table structure for im_group_msg
-- ----------------------------
DROP TABLE IF EXISTS `im_group_msg`;
CREATE TABLE `im_group_msg` (
  `id` bigint(20) NOT NULL,
  `content` varchar(500) CHARACTER SET utf8mb4 NOT NULL COMMENT '内容',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '消息类型 0 文字 1.表情2图片3,语音4视频 5 json',
  `recall` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否撤回',
  `send_uid` bigint(20) NOT NULL,
  `create_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3),
  `update_time` datetime(3) DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3),
  `gid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `gid` (`gid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息表';

-- ----------------------------
-- Table structure for im_group_msg_delete
-- ----------------------------
DROP TABLE IF EXISTS `im_group_msg_delete`;
CREATE TABLE `im_group_msg_delete` (
  `g_msg_id` bigint(20) NOT NULL,
  `uid` bigint(20) DEFAULT NULL,
  `gid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`g_msg_id`),
  KEY `gid` (`gid`),
  KEY `uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for im_group_msg_read
-- ----------------------------
DROP TABLE IF EXISTS `im_group_msg_read`;
CREATE TABLE `im_group_msg_read` (
  `g_msg_id` bigint(20) NOT NULL,
  `send_uid` bigint(20) DEFAULT NULL,
  `recv_uid` bigint(20) DEFAULT NULL,
  `gid` bigint(20) DEFAULT NULL,
  `read` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`g_msg_id`) USING BTREE,
  KEY `g_msg_id` (`g_msg_id`),
  KEY `gid` (`gid`),
  KEY `send_uid` (`send_uid`),
  KEY `recv_uid` (`recv_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for im_group_user
-- ----------------------------
DROP TABLE IF EXISTS `im_group_user`;
CREATE TABLE `im_group_user` (
  `id` bigint(20) NOT NULL,
  `uid` bigint(20) NOT NULL,
  `gid` bigint(20) NOT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`),
  KEY `gid` (`gid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='\n群用户表 ';

-- ----------------------------
-- Table structure for im_msg
-- ----------------------------
DROP TABLE IF EXISTS `im_msg`;
CREATE TABLE `im_msg` (
  `id` bigint(20) NOT NULL,
  `content` varchar(500) CHARACTER SET utf8mb4 NOT NULL COMMENT '内容',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '消息类型 0 文字 1.表情2图片3,语音4视频 5 json',
  `recall` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否撤回',
  `recv_uid` bigint(20) NOT NULL,
  `send_uid` bigint(20) NOT NULL,
  `read` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已读',
  `create_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3),
  `update_time` datetime(3) DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息表';

-- ----------------------------
-- Table structure for im_msg_index
-- ----------------------------
DROP TABLE IF EXISTS `im_msg_index`;
CREATE TABLE `im_msg_index` (
  `msg_id` bigint(20) NOT NULL,
  `uid` bigint(20) NOT NULL,
  `other_uid` bigint(20) NOT NULL,
  `type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0 send 1 recv',
  PRIMARY KEY (`msg_id`,`uid`) USING BTREE,
  KEY `send_uid` (`uid`),
  KEY `recv_uid` (`other_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='单聊的 消息索引 （新增一条消息 存2条  ）';

-- ----------------------------
-- Table structure for im_offline_instruct
-- ----------------------------
DROP TABLE IF EXISTS `im_offline_instruct`;
CREATE TABLE `im_offline_instruct` (
  `id` bigint(20) NOT NULL,
  `uid` bigint(20) NOT NULL,
  `content` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='\n记录离线的指令 （指令可能是删除消息，撤回消息之类的）';

-- ----------------------------
-- Table structure for im_session
-- ----------------------------
DROP TABLE IF EXISTS `im_session`;
CREATE TABLE `im_session` (
  `msg` json NOT NULL COMMENT '最后一条消息内容 json存储',
  `uid` bigint(20) NOT NULL,
  `session_id` bigint(20) NOT NULL COMMENT '单聊是另一个用户的uid 群聊是gid ',
  `type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0 单聊 1 群聊',
  `last_ack_msg_id` bigint(20) DEFAULT NULL COMMENT '最后ack的消息id',
  PRIMARY KEY (`session_id`,`uid`) USING BTREE,
  KEY `send_uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='\n最新的会话的 索引 只记录最新一条消息';

SET FOREIGN_KEY_CHECKS = 1;
