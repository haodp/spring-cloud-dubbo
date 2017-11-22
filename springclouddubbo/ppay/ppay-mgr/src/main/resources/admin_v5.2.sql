
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `banner`
-- ----------------------------
DROP TABLE IF EXISTS `banner`;
CREATE TABLE `banner` (
  `id` bigint(110) NOT NULL AUTO_INCREMENT,
  `banner_name` varchar(200) NOT NULL COMMENT 'banner名称',
  `banner_pic` varchar(1000) NOT NULL COMMENT 'banner图片',
  `banner_type` int(2) NOT NULL COMMENT 'banner类型--哪个App的那个页面的Banner',
  `banner_Link` varchar(200) DEFAULT NULL COMMENT 'banner跳转链接',
  `start_flag` int(11) DEFAULT '1' COMMENT '是否启用，1：启用，2：不启用',
  `bannerds` int(11) DEFAULT NULL COMMENT 'banner显示顺序',
  `bcontent` text COMMENT '设置banner上显示的文本',
  `create_time` datetime DEFAULT NULL,
  `create_user_id` varchar(36) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10011 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='banner表';

-- ----------------------------
-- Records of banner
-- ----------------------------
INSERT INTO `banner` VALUES ('10000', '例子Banner3', '/res/uploads/10005/bannerfiles/1439130109484.jpg', '1', '', '1', '3', '<h1>banner演示</h1>\r\n                    <p>Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>\r\n                    <p><a class=\"btn btn-large btn-primary\" href=\"#\">今天登录</a></p>', null, null, null, null);
INSERT INTO `banner` VALUES ('10003', '例子banner2', '/res/uploads/10005/bannerfiles/1439130018597.jpg', '1', '', '0', '1', '<h1>banner演示</h1>\r\n                    <p>Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>\r\n                    <p><a class=\"btn btn-large btn-primary\" href=\"#\">今天登录</a></p>', null, null, null, null);
INSERT INTO `banner` VALUES ('10008', '例子banner1', '/res/uploads/10005/bannerfiles/1439129972510.jpg', '1', '', '1', '4', 'sdfsdfsdfsdf', null, null, null, null);
INSERT INTO `banner` VALUES ('10009', '例子Banner4', '/res/uploads/10005/bannerfiles/1439130178556.jpg', '1', '', '1', '5', '例子Banner4例子Banner4例子Banner4', null, null, null, null);
INSERT INTO `banner` VALUES ('10010', '例子Banner5', '/res/uploads/10005/bannerfiles/1439130224375.jpg', '1', '', '1', '2', '例子Banner5例子Banner5例子Banner5例子Banner5', null, null, null, null);

-- ----------------------------
-- Table structure for `banner_type`
-- ----------------------------
DROP TABLE IF EXISTS `banner_type`;
CREATE TABLE `banner_type` (
  `id` bigint(110) NOT NULL AUTO_INCREMENT,
  `banner_type_name` varchar(200) NOT NULL COMMENT 'banner类型名称',
  `start_flag` int(1) DEFAULT '1' COMMENT '是否启用，1：启用，2：不启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` varchar(36) DEFAULT NULL COMMENT '更新用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10014 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='banner类型表';

-- ----------------------------
-- Records of banner_type
-- ----------------------------
INSERT INTO `banner_type` VALUES ('1', 'haodp', '1', '2017-04-06 15:25:45', '1', '2017-04-06 15:25:52', '1');
INSERT INTO `banner_type` VALUES ('2', 'test', '1', '2017-04-06 15:25:57', '2', '2017-04-11 09:36:34', 'admin');
INSERT INTO `banner_type` VALUES ('10012', 'sdadadsa', '2', null, null, '2017-04-07 17:32:44', 'admin');

-- ----------------------------
-- Table structure for `content`
-- ----------------------------
DROP TABLE IF EXISTS `content`;
CREATE TABLE `content` (
  `content_id` bigint(110) NOT NULL AUTO_INCREMENT,
  `content_name` varchar(200) NOT NULL COMMENT '内容名称',
  `content_pic` varchar(1000) NOT NULL COMMENT '内容图片',
  `banner_link` varchar(200) DEFAULT NULL COMMENT '内容跳转链接',
  `start_flag` int(11) DEFAULT '0' COMMENT '是否启用，1：启用，0：不启用',
  `create_time` datetime DEFAULT NULL,
  `create_user_id` varchar(36) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`content_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10011 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='CMS内容表';

-- ----------------------------
-- Records of content
-- ----------------------------

-- ----------------------------
-- Table structure for `content_type`
-- ----------------------------
DROP TABLE IF EXISTS `content_type`;
CREATE TABLE `content_type` (
  `id` int(11) NOT NULL,
  `type_name` varchar(20) NOT NULL COMMENT '名称',
  `img_width` int(11) DEFAULT NULL COMMENT '图片宽',
  `img_height` int(11) DEFAULT NULL COMMENT '图片高',
  `has_image` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有图片',
  `disabled` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否禁用',
  `create_time` datetime DEFAULT NULL,
  `create_user_id` varchar(36) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容类型表';

-- ----------------------------
-- Records of content_type
-- ----------------------------

-- ----------------------------
-- Table structure for `menu`
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT '0',
  `menuname` varchar(45) DEFAULT NULL,
  `action` varchar(45) DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_user_id` int(11) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `sort` tinyint(4) DEFAULT '1',
  `flag` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', '0', '权限管理', '/', '1', '2017-03-24 22:17:40', null, null, '1', '1');
INSERT INTO `menu` VALUES ('2', '1', '用户管理', 'userlist.html', '1', '2017-03-24 22:17:40', null, null, '1', '1');
INSERT INTO `menu` VALUES ('3', '1', '角色管理', 'rolelist.html', '1', '2017-03-24 22:17:40', null, null, '2', '1');
INSERT INTO `menu` VALUES ('4', '1', '菜单管理', 'menulist.html', '1', '2017-03-24 22:17:40', null, null, '3', '1');
INSERT INTO `menu` VALUES ('5', '1', '子权限管理', 'permlist.html', '1', '2017-03-24 22:17:40', null, null, '4', '1');
INSERT INTO `menu` VALUES ('6', '0', '资源管理', '/', '1', '2017-03-24 22:17:40', null, null, '2', '1');
INSERT INTO `menu` VALUES ('7', '6', '电脑管理', 'computerlist.html', '1', '2017-03-24 22:17:40', null, null, '1', '1');
INSERT INTO `menu` VALUES ('8', '6', '鼠标管理', 'mouselist.html', '1', '2017-03-24 22:17:40', null, null, '2', '1');
INSERT INTO `menu` VALUES ('9', '8', '正常鼠标管理', 'normalmlist.html', '1', '2017-03-24 22:17:40', null, null, '1', '1');
INSERT INTO `menu` VALUES ('10', '8', '损坏鼠标管理', 'brokenmlist.html', '1', '2017-03-24 22:17:40', null, null, '2', '1');
INSERT INTO `menu` VALUES ('11', '0', '课程管理', '/', '1', '2017-03-24 22:17:40', null, null, '3', '1');
INSERT INTO `menu` VALUES ('12', '11', '课程分类', 'lessontype.html', '1', '2017-03-24 22:17:40', null, null, '1', '1');
INSERT INTO `menu` VALUES ('13', '11', '课程', 'lesson.html', '1', '2017-03-24 22:17:40', null, null, '2', '1');
INSERT INTO `menu` VALUES ('14', '0', '轮播图管理', '/', '1', '2017-03-24 22:17:40', null, null, '4', '1');
INSERT INTO `menu` VALUES ('15', '14', '轮播图类型', 'bannerType.html', '1', '2017-03-24 22:17:40', null, null, '1', '1');
INSERT INTO `menu` VALUES ('16', '14', '轮播图设置', 'banner.html', '1', '2017-04-11 09:38:22', null, null, '2', '1');
INSERT INTO `menu` VALUES ('17', '0', '组织管理', '/', '1', '2017-04-13 09:42:38', null, null, '5', '1');
INSERT INTO `menu` VALUES ('18', '17', '组织', 'organization.html', '1', '2017-04-13 09:43:28', null, null, '1', '1');
INSERT INTO `menu` VALUES ('19', '17', '人员', 'staff.html', '1', null, null, null, '2', '1');

-- ----------------------------
-- Table structure for `model`
-- ----------------------------
DROP TABLE IF EXISTS `model`;
CREATE TABLE `model` (
  `id` int(11) NOT NULL,
  `model_name` varchar(100) NOT NULL COMMENT '名称',
  `model_path` varchar(100) NOT NULL COMMENT '路径',
  `tpl_channel_prefix` varchar(20) DEFAULT NULL COMMENT '栏目模板前缀',
  `tpl_content_prefix` varchar(20) DEFAULT NULL COMMENT '内容模板前缀',
  `title_img_width` int(11) NOT NULL DEFAULT '139' COMMENT '栏目标题图宽度',
  `title_img_height` int(11) NOT NULL DEFAULT '139' COMMENT '栏目标题图高度',
  `content_img_width` int(11) NOT NULL DEFAULT '310' COMMENT '栏目内容图宽度',
  `content_img_height` int(11) NOT NULL DEFAULT '310' COMMENT '栏目内容图高度',
  `priority` int(11) NOT NULL DEFAULT '10' COMMENT '排列顺序',
  `has_content` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有内容',
  `is_disabled` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否禁用',
  `is_def` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否默认模型',
  `is_global` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否全站模型',
  `site_id` int(11) DEFAULT NULL COMMENT '非全站模型所属站点',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS模型表';

-- ----------------------------
-- Records of model
-- ----------------------------
INSERT INTO `model` VALUES ('1', '新闻', '1', 'news', 'news', '139', '139', '310', '310', '1', '1', '0', '1', '1', null);
INSERT INTO `model` VALUES ('2', '单页', '2', 'alone', 'alone', '139', '139', '310', '310', '2', '0', '0', '0', '1', null);
INSERT INTO `model` VALUES ('4', '下载', '4', 'download', 'download', '139', '139', '310', '310', '4', '1', '0', '0', '1', null);
INSERT INTO `model` VALUES ('5', '图库', '5', 'pic', 'pic', '139', '139', '310', '310', '5', '1', '0', '0', '1', null);
INSERT INTO `model` VALUES ('6', '视频', '6', 'video', 'video', '139', '139', '310', '310', '10', '1', '0', '0', '1', null);
INSERT INTO `model` VALUES ('8', '招聘', 'job', 'job', 'job', '139', '139', '310', '310', '10', '1', '0', '0', '1', null);

-- ----------------------------
-- Table structure for `office`
-- ----------------------------
DROP TABLE IF EXISTS `office`;
CREATE TABLE `office` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `parent_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '父级编号',
  `parent_ids` varchar(512) COLLATE utf8_bin NOT NULL COMMENT '所有父级编号',
  `office_name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '名称',
  `show_sort` int(11) NOT NULL COMMENT '排序',
  `office_grade` int(11) NOT NULL COMMENT '机构等级 0：集团 1：公司 2：分公司 3：事业部 4：部门 5：其他',
  `address` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '联系地址',
  `zip_code` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '邮政编码',
  `start_flag` int(11) DEFAULT '0' COMMENT '是否启用',
  `primary_master` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '主负责人',
  `deputy_master` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '副负责人',
  `create_user_id` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user_id` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `office_remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='组织表';

-- ----------------------------
-- Records of office
-- ----------------------------

-- ----------------------------
-- Table structure for `party_entity`
-- ----------------------------
DROP TABLE IF EXISTS `party_entity`;
CREATE TABLE `party_entity` (
  `ID` bigint(20) NOT NULL,
  `TYPE_ID` bigint(20) DEFAULT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `REF` varchar(100) DEFAULT NULL,
  `TENANT_ID` varchar(200) DEFAULT NULL,
  `LEVEL` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_PARTY_ENTITY_TYPE` (`TYPE_ID`),
  KEY `I_PARTY_ENTITY_REFERENCE_TYPE` (`REF`,`TYPE_ID`),
  KEY `I_PARTY_ENTITY_NAME` (`NAME`),
  CONSTRAINT `FK_PARTY_ENTITY_TYPE` FOREIGN KEY (`TYPE_ID`) REFERENCES `party_type` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of party_entity
-- ----------------------------
INSERT INTO `party_entity` VALUES ('1', '2', '大连雅谷', '1', '1', null);
INSERT INTO `party_entity` VALUES ('2', '3', '市场部', '1', '1', null);
INSERT INTO `party_entity` VALUES ('3', '3', '财务部', '2', '1', null);
INSERT INTO `party_entity` VALUES ('4', '3', '人事部', '3', '1', null);
INSERT INTO `party_entity` VALUES ('5', '3', '开发部', '4', '1', null);
INSERT INTO `party_entity` VALUES ('6', '4', '平台组', '6', '1', null);
INSERT INTO `party_entity` VALUES ('7', '4', 'OA组', '7', '1', null);
INSERT INTO `party_entity` VALUES ('8', '1', 'Lingo', '1', '1', null);
INSERT INTO `party_entity` VALUES ('9', '1', 'Vivian', '2', '1', null);
INSERT INTO `party_entity` VALUES ('10', '1', 'Steven', '3', '1', null);
INSERT INTO `party_entity` VALUES ('11', '1', 'King', '4', '1', null);
INSERT INTO `party_entity` VALUES ('12', '1', 'John', '5', '1', null);
INSERT INTO `party_entity` VALUES ('13', '1', 'William', '6', '1', null);
INSERT INTO `party_entity` VALUES ('14', '1', 'Adam', '7', '1', null);
INSERT INTO `party_entity` VALUES ('15', '5', '总经理', null, '1', '2');
INSERT INTO `party_entity` VALUES ('16', '5', '经理', null, '1', '1');
INSERT INTO `party_entity` VALUES ('10008', '1', 'Robot', '8', '1', null);
INSERT INTO `party_entity` VALUES ('10009', '1', 'Bob', '9', '1', null);
INSERT INTO `party_entity` VALUES ('10010', '1', 'Alice', '10', '1', null);
INSERT INTO `party_entity` VALUES ('10011', '1', 'Mike', '11', '1', null);
INSERT INTO `party_entity` VALUES ('10012', '1', 'Justin', '12', '1', null);
INSERT INTO `party_entity` VALUES ('10013', '1', 'Tom', '13', '1', null);
INSERT INTO `party_entity` VALUES ('10014', '1', 'James', '14', '1', null);
INSERT INTO `party_entity` VALUES ('10015', '1', 'Joe', '15', '1', null);
INSERT INTO `party_entity` VALUES ('10016', '1', 'Paul', '16', '1', null);
INSERT INTO `party_entity` VALUES ('10017', '1', 'Jane', '17', '1', null);
INSERT INTO `party_entity` VALUES ('10018', '1', 'Marry', '18', '1', null);
INSERT INTO `party_entity` VALUES ('10019', '1', 'Ben', '19', '1', null);
INSERT INTO `party_entity` VALUES ('10020', '1', 'Bot', '20', '1', null);
INSERT INTO `party_entity` VALUES ('10021', '1', 'Jack', '21', '1', null);
INSERT INTO `party_entity` VALUES ('10022', '1', 'Martin', '22', '1', null);
INSERT INTO `party_entity` VALUES ('10023', '1', 'Alex', '23', '1', null);
INSERT INTO `party_entity` VALUES ('10024', '1', 'Daniel', '24', '1', null);
INSERT INTO `party_entity` VALUES ('10025', '1', 'Eric', '25', '1', null);
INSERT INTO `party_entity` VALUES ('10026', '1', 'Leon', '26', '1', null);
INSERT INTO `party_entity` VALUES ('10027', '1', 'Clark', '27', '1', null);
INSERT INTO `party_entity` VALUES ('10028', '1', 'David', '28', '1', null);
INSERT INTO `party_entity` VALUES ('10029', '1', 'Henry', '29', '1', null);
INSERT INTO `party_entity` VALUES ('10030', '1', 'Helen', '30', '1', null);
INSERT INTO `party_entity` VALUES ('10031', '1', 'Sarah', '31', '1', null);
INSERT INTO `party_entity` VALUES ('30011', '3', '行政部', null, '1', null);
INSERT INTO `party_entity` VALUES ('30012', '3', '销售部', null, '1', null);
INSERT INTO `party_entity` VALUES ('30013', '3', '客服部', null, '1', null);
INSERT INTO `party_entity` VALUES ('599023101722624', '3', 'HR部门', null, null, null);
INSERT INTO `party_entity` VALUES ('599023716286464', '3', 'HR部', null, null, null);
INSERT INTO `party_entity` VALUES ('599024255991808', '4', '金盾项目', null, null, null);
INSERT INTO `party_entity` VALUES ('599024571006976', '4', '三线项目', null, null, null);
INSERT INTO `party_entity` VALUES ('599026458804224', '5', '部长', null, null, null);
INSERT INTO `party_entity` VALUES ('599026607308800', '5', '副部长', null, null, null);
INSERT INTO `party_entity` VALUES ('599026798477312', '5', '技术总监', null, null, null);
INSERT INTO `party_entity` VALUES ('599027081281536', '5', '项目经理', null, null, null);
INSERT INTO `party_entity` VALUES ('599029801484288', '1', 'haodp', '599029796634624', '1', null);
INSERT INTO `party_entity` VALUES ('599030523559936', '1', 'xuw', '599030506274816', '1', null);
INSERT INTO `party_entity` VALUES ('599034917076992', '5', '技术总监', null, null, null);
INSERT INTO `party_entity` VALUES ('599038503075840', '1', 'yanpf', '599038487986176', '1', null);
INSERT INTO `party_entity` VALUES ('599039143723008', '1', 'dongdl', '599039139872768', '1', null);
INSERT INTO `party_entity` VALUES ('599039438585856', '1', 'zhaoxg', '599039431737344', '1', null);
INSERT INTO `party_entity` VALUES ('599046081740800', '5', '项目经理', null, null, null);
INSERT INTO `party_entity` VALUES ('599084641746944', '5', '副经理', null, null, null);
INSERT INTO `party_entity` VALUES ('599093488730112', '2', '北京雅谷', '599093487468544', null, null);
INSERT INTO `party_entity` VALUES ('599094771515392', '2', '上海儒一', '599094770647040', null, null);
INSERT INTO `party_entity` VALUES ('599095386275840', '3', '行政部', '599095385341952', null, null);
INSERT INTO `party_entity` VALUES ('599107267477504', '5', '副经理', null, null, null);

-- ----------------------------
-- Table structure for `party_struct`
-- ----------------------------
DROP TABLE IF EXISTS `party_struct`;
CREATE TABLE `party_struct` (
  `STRUCT_TYPE_ID` bigint(20) NOT NULL,
  `PARENT_ENTITY_ID` bigint(20) DEFAULT NULL,
  `CHILD_ENTITY_ID` bigint(20) NOT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TENANT_ID` varchar(200) DEFAULT NULL,
  `ID` bigint(20) NOT NULL DEFAULT '0',
  `PART_TIME` int(11) DEFAULT NULL,
  `LINK` int(11) DEFAULT NULL,
  `ADMIN` int(11) DEFAULT NULL,
  `TYPE` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_PARTY_STRUCT_TYPE` (`STRUCT_TYPE_ID`),
  KEY `FK_PARTY_STRUCT_PARENT` (`PARENT_ENTITY_ID`),
  KEY `FK_PARTY_STRUCT_CHILD` (`CHILD_ENTITY_ID`),
  CONSTRAINT `FK_PARTY_STRUCT_CHILD` FOREIGN KEY (`CHILD_ENTITY_ID`) REFERENCES `party_entity` (`ID`),
  CONSTRAINT `FK_PARTY_STRUCT_PARENT` FOREIGN KEY (`PARENT_ENTITY_ID`) REFERENCES `party_entity` (`ID`),
  CONSTRAINT `FK_PARTY_STRUCT_TYPE` FOREIGN KEY (`STRUCT_TYPE_ID`) REFERENCES `party_struct_type` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of party_struct
-- ----------------------------
INSERT INTO `party_struct` VALUES ('1', '1', '2', '0', '1', '1', '0', '0', '0', null);
INSERT INTO `party_struct` VALUES ('1', '1', '3', '0', '1', '2', '0', '0', '0', null);
INSERT INTO `party_struct` VALUES ('1', '1', '4', '0', '1', '3', '0', '0', '0', null);
INSERT INTO `party_struct` VALUES ('1', '1', '5', '0', '1', '4', '0', '0', '0', null);
INSERT INTO `party_struct` VALUES ('1', '5', '6', '0', '1', '5', '0', '0', '0', null);
INSERT INTO `party_struct` VALUES ('1', '5', '7', '0', '1', '6', '0', '0', '0', null);
INSERT INTO `party_struct` VALUES ('1', '7', '8', '0', '1', '7', '0', '0', '0', null);
INSERT INTO `party_struct` VALUES ('1', '6', '9', '0', '1', '8', '0', '0', '0', null);
INSERT INTO `party_struct` VALUES ('1', '2', '10', '0', '1', '9', '0', '0', '1', null);
INSERT INTO `party_struct` VALUES ('1', '3', '13', '0', '1', '12', '0', '0', '0', null);
INSERT INTO `party_struct` VALUES ('1', '4', '14', '0', '1', '13', '0', '0', '0', null);
INSERT INTO `party_struct` VALUES ('1', null, '1', null, '1', '20', null, null, null, null);
INSERT INTO `party_struct` VALUES ('1', '1', '30011', null, '1', '201', null, null, null, null);
INSERT INTO `party_struct` VALUES ('1', '2', '30012', null, '1', '202', null, null, null, null);
INSERT INTO `party_struct` VALUES ('1', '2', '30013', null, '1', '203', null, null, null, null);
INSERT INTO `party_struct` VALUES ('1', '30011', '10008', null, '1', '301', null, null, null, null);
INSERT INTO `party_struct` VALUES ('1', '30011', '10009', null, '1', '302', null, null, null, null);
INSERT INTO `party_struct` VALUES ('1', '30011', '10010', null, '1', '303', null, null, null, null);
INSERT INTO `party_struct` VALUES ('1', '30012', '10011', null, '1', '304', null, null, null, null);
INSERT INTO `party_struct` VALUES ('1', '30012', '10012', null, '1', '305', null, null, null, null);
INSERT INTO `party_struct` VALUES ('1', '30012', '10013', null, '1', '306', null, null, null, null);
INSERT INTO `party_struct` VALUES ('1', '30013', '10014', null, '1', '307', null, null, null, null);
INSERT INTO `party_struct` VALUES ('1', '30013', '10015', null, '1', '308', null, null, null, null);
INSERT INTO `party_struct` VALUES ('1', '30013', '10016', null, '1', '309', null, null, null, null);
INSERT INTO `party_struct` VALUES ('1', '1', '10017', null, '1', '310', null, null, null, null);
INSERT INTO `party_struct` VALUES ('1', '1', '10018', null, '1', '311', null, null, null, null);
INSERT INTO `party_struct` VALUES ('1', '2', '10019', null, '1', '312', null, null, null, null);
INSERT INTO `party_struct` VALUES ('1', '2', '10021', null, '1', '313', null, null, null, null);
INSERT INTO `party_struct` VALUES ('1', '3', '10022', null, '1', '314', null, null, null, null);
INSERT INTO `party_struct` VALUES ('1', '3', '10023', null, '1', '315', null, null, null, null);
INSERT INTO `party_struct` VALUES ('1', '4', '10024', null, '1', '316', null, null, null, null);
INSERT INTO `party_struct` VALUES ('1', '4', '10025', null, '1', '317', null, null, null, null);
INSERT INTO `party_struct` VALUES ('1', '5', '10026', null, '1', '318', null, null, null, null);
INSERT INTO `party_struct` VALUES ('1', '5', '10027', null, '1', '319', null, null, null, null);
INSERT INTO `party_struct` VALUES ('1', '6', '10028', null, '1', '320', null, null, null, null);
INSERT INTO `party_struct` VALUES ('1', '6', '10029', null, '1', '321', null, null, null, null);
INSERT INTO `party_struct` VALUES ('1', '7', '10030', null, '1', '322', null, null, null, null);
INSERT INTO `party_struct` VALUES ('1', '7', '10031', null, '1', '323', null, null, null, null);
INSERT INTO `party_struct` VALUES ('1', '1', '12', '1', '1', '401', null, null, null, null);
INSERT INTO `party_struct` VALUES ('1', '5', '11', '1', '1', '402', null, null, null, null);
INSERT INTO `party_struct` VALUES ('5', '12', '15', '1', '1', '403', null, null, null, null);
INSERT INTO `party_struct` VALUES ('5', '11', '16', '1', '1', '404', null, null, null, null);
INSERT INTO `party_struct` VALUES ('2', '1', '15', '1', '1', '405', null, null, null, null);
INSERT INTO `party_struct` VALUES ('2', '5', '16', '1', '1', '406', null, null, null, null);
INSERT INTO `party_struct` VALUES ('1', '1', '599023716286464', '6', null, '599023717023744', null, null, null, null);
INSERT INTO `party_struct` VALUES ('1', '5', '599024255991808', '3', null, '599024256778240', null, null, null, null);
INSERT INTO `party_struct` VALUES ('1', '5', '599024571006976', '4', null, '599024571744256', null, null, null, null);
INSERT INTO `party_struct` VALUES ('2', '5', '599026798477312', '3', '1', '599026799230976', null, null, null, null);
INSERT INTO `party_struct` VALUES ('2', '5', '599027081281536', '4', '1', '599027082100736', null, null, null, null);
INSERT INTO `party_struct` VALUES ('1', '599024255991808', '599029801484288', '12', null, '599034507001856', null, null, null, null);
INSERT INTO `party_struct` VALUES ('5', '599029801484288', '599034917076992', '1', '1', '599034918158336', null, null, null, null);
INSERT INTO `party_struct` VALUES ('1', '599024571006976', '599039143723008', '1', null, '599045836062720', null, null, null, null);
INSERT INTO `party_struct` VALUES ('5', '599039143723008', '599046081740800', '1', '1', '599046082625536', null, null, null, null);
INSERT INTO `party_struct` VALUES ('1', '599024571006976', '599039438585856', '2', null, '599046584942592', null, null, null, null);
INSERT INTO `party_struct` VALUES ('2', '5', '599084641746944', '2', '1', '599084643188736', null, null, null, null);
INSERT INTO `party_struct` VALUES ('1', '5', '599038503075840', '1', null, '599106957443072', null, null, null, null);
INSERT INTO `party_struct` VALUES ('5', '599038503075840', '599107267477504', '12', '1', '599107268329472', null, null, null, null);

-- ----------------------------
-- Table structure for `party_struct_rule`
-- ----------------------------
DROP TABLE IF EXISTS `party_struct_rule`;
CREATE TABLE `party_struct_rule` (
  `STRUCT_TYPE_ID` bigint(20) NOT NULL,
  `PARENT_TYPE_ID` bigint(20) DEFAULT NULL,
  `CHILD_TYPE_ID` bigint(20) NOT NULL,
  `TENANT_ID` varchar(200) DEFAULT NULL,
  `ID` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  KEY `FK_PARTY_STRUCT_RULE_TYPE` (`STRUCT_TYPE_ID`),
  KEY `FK_PARTY_STRUCT_RULE_TYPE_PARENT` (`PARENT_TYPE_ID`),
  KEY `FK_PARTY_STRUCT_RULE_TYPE_CHILD` (`CHILD_TYPE_ID`),
  CONSTRAINT `FK_PARTY_STRUCT_RULE_TYPE` FOREIGN KEY (`STRUCT_TYPE_ID`) REFERENCES `party_struct_type` (`ID`),
  CONSTRAINT `FK_PARTY_STRUCT_RULE_TYPE_CHILD` FOREIGN KEY (`CHILD_TYPE_ID`) REFERENCES `party_type` (`ID`),
  CONSTRAINT `FK_PARTY_STRUCT_RULE_TYPE_PARENT` FOREIGN KEY (`PARENT_TYPE_ID`) REFERENCES `party_type` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of party_struct_rule
-- ----------------------------
INSERT INTO `party_struct_rule` VALUES ('1', '2', '2', '1', '1');
INSERT INTO `party_struct_rule` VALUES ('1', '2', '3', '1', '2');
INSERT INTO `party_struct_rule` VALUES ('1', '2', '4', '1', '3');
INSERT INTO `party_struct_rule` VALUES ('1', '2', '1', '1', '4');
INSERT INTO `party_struct_rule` VALUES ('1', '3', '3', '1', '5');
INSERT INTO `party_struct_rule` VALUES ('1', '3', '4', '1', '6');
INSERT INTO `party_struct_rule` VALUES ('1', '3', '1', '1', '7');
INSERT INTO `party_struct_rule` VALUES ('1', '4', '4', '1', '8');
INSERT INTO `party_struct_rule` VALUES ('1', '4', '1', '1', '9');

-- ----------------------------
-- Table structure for `party_struct_type`
-- ----------------------------
DROP TABLE IF EXISTS `party_struct_type`;
CREATE TABLE `party_struct_type` (
  `ID` bigint(20) NOT NULL,
  `NAME` varchar(50) DEFAULT NULL,
  `REF` varchar(50) DEFAULT NULL,
  `TENANT_ID` varchar(200) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `DISPLAY` varchar(200) DEFAULT NULL,
  `TYPE` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of party_struct_type
-- ----------------------------
INSERT INTO `party_struct_type` VALUES ('1', '行政组织', null, '1', '0', 'true', 'struct');
INSERT INTO `party_struct_type` VALUES ('2', '管理关系', null, '1', '200', 'true', 'manage');
INSERT INTO `party_struct_type` VALUES ('3', '上下级', null, '1', '300', 'true', 'superiour');
INSERT INTO `party_struct_type` VALUES ('4', '岗位关系', null, '1', '400', 'true', 'position');
INSERT INTO `party_struct_type` VALUES ('5', '岗位人员', null, '1', '500', 'true', 'user-position');
INSERT INTO `party_struct_type` VALUES ('6', '部门岗位', null, '1', '600', 'true', 'department-position');
INSERT INTO `party_struct_type` VALUES ('7', '角色', null, '1', '700', 'true', 'role');
INSERT INTO `party_struct_type` VALUES ('8', '群组', null, '1', '800', 'true', 'group');

-- ----------------------------
-- Table structure for `party_type`
-- ----------------------------
DROP TABLE IF EXISTS `party_type`;
CREATE TABLE `party_type` (
  `ID` bigint(20) NOT NULL,
  `NAME` varchar(50) DEFAULT NULL,
  `TENANT_ID` varchar(200) DEFAULT NULL,
  `TYPE` int(11) DEFAULT NULL,
  `REF` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of party_type
-- ----------------------------
INSERT INTO `party_type` VALUES ('1', '人员', '1', '1', 'user');
INSERT INTO `party_type` VALUES ('2', '公司', '1', '0', 'company');
INSERT INTO `party_type` VALUES ('3', '部门', '1', '0', 'department');
INSERT INTO `party_type` VALUES ('4', '小组', '1', '0', 'group');
INSERT INTO `party_type` VALUES ('5', '岗位', '1', '2', null);

-- ----------------------------
-- Table structure for `permission`
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_id` int(11) DEFAULT NULL,
  `permname` varchar(45) DEFAULT NULL,
  `action` varchar(45) DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_user_id` int(11) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `flag` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('1', '2', '新建用户', 'createUser', '1', '2017-03-24 22:17:40', '1', null, '1');
INSERT INTO `permission` VALUES ('2', '2', '删除用户', 'deleteUser', '1', '2017-03-24 22:17:40', '1', null, '1');
INSERT INTO `permission` VALUES ('3', '2', '修改用户', 'updateUser', '1', '2017-03-24 22:17:40', '1', null, '1');
INSERT INTO `permission` VALUES ('4', '2', '查询用户', 'getUserList', '1', '2017-03-24 22:17:40', '1', null, '1');
INSERT INTO `permission` VALUES ('5', '3', '新建角色1', 'createRole1', '1', '2017-04-12 09:56:38', '1', '2017-04-12 10:46:58', '2');

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rolename` varchar(45) DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_user_id` int(11) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `status` tinyint(4) DEFAULT '1',
  `flag` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '管理员', '1', '2017-03-24 22:17:40', '1', '2017-03-24 22:17:40', '1', '1');
INSERT INTO `role` VALUES ('2', '普通用户', '1', '2017-03-24 22:17:40', '1', '2017-03-24 22:17:40', '1', '1');
INSERT INTO `role` VALUES ('3', '测试1', '1', '2017-04-05 15:19:49', null, null, '1', '1');
INSERT INTO `role` VALUES ('4', '测试', '1', '2017-04-07 10:05:28', null, null, '1', '1');
INSERT INTO `role` VALUES ('5', '超级用户', '1', '2017-04-14 16:49:43', null, null, '1', '1');

-- ----------------------------
-- Table structure for `role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `menu_id` int(11) DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `flag` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES ('1', '1', '1', '1', '2017-03-24 22:17:40', '1');
INSERT INTO `role_menu` VALUES ('2', '1', '2', '1', '2017-03-24 22:17:40', '1');
INSERT INTO `role_menu` VALUES ('3', '1', '3', '1', '2017-03-24 22:17:40', '1');
INSERT INTO `role_menu` VALUES ('4', '1', '4', '1', '2017-03-24 22:17:40', '1');
INSERT INTO `role_menu` VALUES ('5', '1', '5', '1', '2017-03-24 22:17:40', '1');
INSERT INTO `role_menu` VALUES ('6', '1', '6', '1', '2017-03-24 22:17:40', '1');
INSERT INTO `role_menu` VALUES ('7', '1', '7', '1', '2017-03-24 22:17:40', '1');
INSERT INTO `role_menu` VALUES ('8', '1', '8', '1', '2017-03-24 22:17:40', '1');
INSERT INTO `role_menu` VALUES ('9', '1', '9', '1', '2017-03-24 22:17:40', '1');
INSERT INTO `role_menu` VALUES ('10', '1', '10', '1', '2017-03-24 22:17:40', '1');
INSERT INTO `role_menu` VALUES ('15', '1', '11', '1', '2017-04-11 09:55:46', '1');
INSERT INTO `role_menu` VALUES ('16', '1', '12', '1', '2017-04-11 09:55:46', '1');
INSERT INTO `role_menu` VALUES ('17', '1', '13', '1', '2017-04-11 09:55:46', '1');
INSERT INTO `role_menu` VALUES ('18', '1', '14', '1', '2017-04-11 09:55:46', '1');
INSERT INTO `role_menu` VALUES ('19', '1', '15', '1', '2017-04-11 09:55:46', '1');
INSERT INTO `role_menu` VALUES ('20', '1', '16', '1', '2017-04-11 09:55:46', '1');
INSERT INTO `role_menu` VALUES ('25', '1', '17', '1', '2017-04-13 09:46:00', '1');
INSERT INTO `role_menu` VALUES ('26', '1', '18', '1', '2017-04-13 09:46:18', '1');
INSERT INTO `role_menu` VALUES ('27', '1', '19', '1', '2017-04-13 09:46:40', '1');
INSERT INTO `role_menu` VALUES ('68', '2', '1', '1', '2017-04-14 16:22:24', '1');
INSERT INTO `role_menu` VALUES ('69', '2', '2', '1', '2017-04-14 16:22:24', '1');
INSERT INTO `role_menu` VALUES ('70', '2', '3', '1', '2017-04-14 16:22:24', '1');
INSERT INTO `role_menu` VALUES ('71', '2', '4', '1', '2017-04-14 16:22:24', '1');

-- ----------------------------
-- Table structure for `role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `permission_id` int(11) DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `flag` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES ('1', '1', '1', '1', '2017-03-24 22:17:40', '1');
INSERT INTO `role_permission` VALUES ('2', '1', '2', '1', '2017-03-24 22:17:40', '1');
INSERT INTO `role_permission` VALUES ('3', '1', '3', '1', '2017-03-24 22:17:40', '1');
INSERT INTO `role_permission` VALUES ('4', '1', '4', '1', '2017-03-24 22:17:40', '1');
INSERT INTO `role_permission` VALUES ('8', '2', '1', '1', '2017-04-14 16:22:24', '1');
INSERT INTO `role_permission` VALUES ('9', '2', '2', '1', '2017-04-14 16:22:24', '1');
INSERT INTO `role_permission` VALUES ('10', '2', '4', '1', '2017-04-14 16:22:24', '1');

-- ----------------------------
-- Table structure for `t_dictionary`
-- ----------------------------
DROP TABLE IF EXISTS `t_dictionary`;
CREATE TABLE `t_dictionary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dictionary_name` varchar(255) NOT NULL COMMENT 'dictionary_name',
  `dictionary_value` varchar(255) NOT NULL COMMENT 'value',
  `dictionary_type` varchar(255) NOT NULL COMMENT 'dictionary_type',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='字典表';

-- ----------------------------
-- Records of t_dictionary
-- ----------------------------
INSERT INTO `t_dictionary` VALUES ('1', '10-20人', '10-20人', 'scale');
INSERT INTO `t_dictionary` VALUES ('2', '20-50人', '20-50人', 'scale');
INSERT INTO `t_dictionary` VALUES ('3', '50-10人', '50-10人', 'scale');
INSERT INTO `t_dictionary` VALUES ('4', '100人以上', '100人以上', 'scale');
INSERT INTO `t_dictionary` VALUES ('5', '私企', '私企', 'nature');
INSERT INTO `t_dictionary` VALUES ('6', '股份制', '股份制', 'nature');
INSERT INTO `t_dictionary` VALUES ('7', '国企', '国企', 'nature');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL COMMENT '用户名',
  `real_name` varchar(45) DEFAULT NULL COMMENT '真实姓名',
  `gender` tinyint(4) DEFAULT '1',
  `email` varchar(45) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(45) DEFAULT NULL COMMENT '电话',
  `password` varchar(45) DEFAULT NULL COMMENT '密码',
  `salt` int(11) DEFAULT NULL COMMENT '密码加密盐',
  `pic_url` varchar(128) DEFAULT 'js/module/ace/avatars/user.jpg',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态',
  `create_user_id` int(11) DEFAULT NULL COMMENT '创建者id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_user_id` int(11) DEFAULT NULL COMMENT '修改者id',
  `modify_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `open_id` varchar(45) DEFAULT NULL COMMENT '微信open_id',
  `flag` tinyint(4) DEFAULT '1' COMMENT '删除标志位',
  PRIMARY KEY (`id`),
  KEY `username_index` (`username`),
  KEY `OPEN_ID_INDEX` (`open_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '辛海臣', '1', 'userX085@163.com', '15998724703', '0449bc3543f0335c0372dcbc43e54f18', '1016888356', 'http://ww2.sinaimg.cn/bmiddle/e83d0609gy1fedbpy4xeqj21jk1jkb29.jpg', '1', '1', '2017-03-24 22:17:40', null, null, '2017-03-24 22:17:40', null, '1');
INSERT INTO `user` VALUES ('2', 'user', 'user11', '1', 'user086@163.com', '18698724701', '0449bc3543f0335c0372dcbc43e54f18', '1016888356', 'js/module/ace/avatars/user.jpg', '1', '1', '2017-03-24 22:18:26', null, null, '2017-03-24 22:18:26', null, '1');
INSERT INTO `user` VALUES ('9', 'haodp', 'haodapeng1', '1', 'haodp0558@163.com', '18104111536', '0449bc3543f0335c0372dcbc43e54f18', '1016888356', 'http://ww3.sinaimg.cn/bmiddle/6a2dec21gy1fdt7vtm38bj20jq0n0tb0.jpg', '1', '1', '2017-04-01 21:34:57', '9', '2017-04-10 09:53:15', null, null, '1');
INSERT INTO `user` VALUES ('10', 'ccc', '猜猜猜', '1', '123123@11.com', '123123', '6fd070b572c86ea89be7843e2365502c', '312029593', 'js/module/ace/avatars/user.jpg', '1', '1', '2017-04-11 13:26:23', null, null, null, null, '1');
INSERT INTO `user` VALUES ('11', 'yangyiqiang', '杨一强', '1', '1293u@163.com', '1232323232323', 'bf3d001f74082efcc8d7920f2f18facf', '-1053142449', 'js/module/ace/avatars/user.jpg', '1', '1', '2017-04-14 16:46:41', null, null, null, null, '1');
INSERT INTO `user` VALUES ('12', 'yangerqiang', '杨二强', '1', '14564511145@163.com', '14564511145', '2629d7fd1f37a05ab7ede09632f43faf', '822876138', 'js/module/ace/avatars/user.jpg', '1', '1', '2017-04-14 16:47:12', null, null, null, null, '1');
INSERT INTO `user` VALUES ('13', 'yangsanqiang', '杨三强', '1', '18989856542@qq.com', '18989856542', 'e4e99a4e997c25449871675d1dbdd3ea', '258214167', 'js/module/ace/avatars/user.jpg', '1', '1', '2017-04-14 16:48:10', null, null, null, null, '1');
INSERT INTO `user` VALUES ('14', 'super', 'super', '1', '14565985621@qq.com', '14565985621', 'e5907d65c976be9473ddbc905c39cf6c', '-749136750', 'js/module/ace/avatars/user.jpg', '1', '1', '2017-04-14 16:50:44', null, null, null, null, '1');
INSERT INTO `user` VALUES ('15', 'liwenping', 'li', '1', '12jk@163.com', '1562325584112', '47ed611acf0b28db3e94d8fc6587e690', '917967048', 'js/module/ace/avatars/user.jpg', '1', '1', '2017-04-14 17:49:15', null, null, null, null, '1');
INSERT INTO `user` VALUES ('16', 'xuxichen', 'xxx', '1', '16525252@qq.com', '1892828228282', 'c76e1e25e2c0c7e393c2514e856a8aa7', '774178885', 'js/module/ace/avatars/user.jpg', '1', '1', '2017-04-14 19:07:14', null, null, null, null, '1');

-- ----------------------------
-- Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_user_id` int(11) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `flag` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `USERROLE_USER_ID_INDEX` (`user_id`),
  KEY `USERROLE_ROLE_ID_INDEX` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1', '1', '1', '2017-03-24 22:17:40', '1', '2017-03-24 22:17:40', '1');
INSERT INTO `user_role` VALUES ('2', '2', '2', '1', '2017-03-24 22:17:40', '1', '2017-03-24 22:17:40', '1');
INSERT INTO `user_role` VALUES ('3', '9', '1', '1', '2017-04-01 21:34:57', null, null, '1');
INSERT INTO `user_role` VALUES ('4', '10', '2', '1', '2017-04-11 13:26:23', null, null, '1');
INSERT INTO `user_role` VALUES ('5', '11', '4', '1', '2017-04-14 16:46:41', null, null, '1');
INSERT INTO `user_role` VALUES ('6', '12', '3', '1', '2017-04-14 16:47:13', null, null, '1');
INSERT INTO `user_role` VALUES ('7', '13', '2', '1', '2017-04-14 16:48:10', null, null, '1');
INSERT INTO `user_role` VALUES ('8', '14', '5', '1', '2017-04-14 16:50:44', null, null, '1');
INSERT INTO `user_role` VALUES ('9', '15', '5', '1', '2017-04-14 17:49:15', null, null, '1');
INSERT INTO `user_role` VALUES ('10', '16', '1', '1', '2017-04-14 19:07:15', null, null, '1');
