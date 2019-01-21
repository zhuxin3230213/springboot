CREATE TABLE `sc_gmuni_college_info` (
  `id` varchar(32) COLLATE utf8_bin NOT NULL,
  `name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '大学名称',
  `simple_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '大学简称',
  `code` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '大学编码',
  `url` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '学校访问地址',
  `col1` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '备用字段1',
  `col2` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '备用字段2',
  `col3` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '备用字段3',
  `col4` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '备用字段4',
  `col5` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '备用字段5',
  `col6` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '备用字段6',
  `col7` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '备用字段7',
  `col8` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '备用字段8',
  `col9` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '备用字段9',
  `col10` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '备用字段10',
  `create_time` datetime DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `sc_gmuni_college_info` */
/*Table structure for table `sc_gmuni_express` */
CREATE TABLE `sc_gmuni_express` (
  `id` varchar(32) COLLATE utf8_bin NOT NULL,
  `bussiness_id` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '电商id',
  `order_code` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '订单编码',
  `exp_no` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '物流单号',
  `exp_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '物流公司名称',
  `exp_code` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '物流公司编码',
  `exp_logo` longtext COLLATE utf8_bin COMMENT '物流公司logo',
  `information` longtext COLLATE utf8_bin COMMENT '物流详情（包括轨迹与轨迹时间）',
  `state` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '物流状态 0： 无轨迹   1：已揽收  2：在途中 3：签收 4：问题件',
  `user_info` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '当前用户',
  `campus` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '用户所在学校',
  `click_through` decimal(10,0) DEFAULT NULL COMMENT '查询次数',
  `create_time` datetime DEFAULT NULL COMMENT '添加时间',
  `update_time` datetime DEFAULT NULL COMMENT '当前更改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `sc_gmuni_express` */
/*Table structure for table `sc_gmuni_lost_found` */
CREATE TABLE `sc_gmuni_lost_found` (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'id',
  `title` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '标题',
  `obj_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '物品名称',
  `obj_address` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '地点',
  `obj_time` datetime DEFAULT NULL COMMENT '时间',
  `description` varchar(1000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '详细描述',
  `user_info` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '提交用户',
  `campus` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '所属学校',
  `create_time` datetime DEFAULT NULL COMMENT '信息提交时间',
  `lf_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '类型{0：丢失，1：捡到}',
  `info_status` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '状态{0：激活，1：完结}',
  `snapshot` longtext COMMENT '缩略图',
  `image` longtext COMMENT '大图',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `sc_gmuni_lost_found` */
/*Table structure for table `sc_gmuni_thirdpart_user` */
CREATE TABLE `sc_gmuni_thirdpart_user` (
  `id` varchar(32) COLLATE utf8_bin NOT NULL,
  `phone_number` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '手机号码',
  `wechat` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '微信号码',
  `qq` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT 'qq号码',
  `email` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `weibo` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '微博',
  `school` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '学校',
  `student_id` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '学号',
  `faculty` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '学院',
  `subject` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '专业',
  `clazz` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '班级',
  `avatar` longtext COLLATE utf8_bin COMMENT '头像',
  `name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '姓名',
  `sex` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '性别',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `identity_card` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '身份证号码',
  `real_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '真实姓名',
  `password` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '院校方登录密码',
  `endpoint_token` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '院校方登录token',
  `endpoint_login_id` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '院校方登录loginId',
  `net_account` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '上网账号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `sc_gmuni_thirdpart_user` */
/*Table structure for table `sc_gmuni_thirdpart_userinfo` */
CREATE TABLE `sc_gmuni_thirdpart_userinfo` (
  `id` varchar(32) COLLATE utf8_bin NOT NULL,
  `identity_type` int(10) DEFAULT NULL COMMENT '登录方式 0:电话,1:微信,2:qq,3:微博',
  `user_id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '关联用户表',
  `indentifier` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '登录账号',
  `credential` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '登录密码，对于第三方可以是token 或者验证码',
  `token` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '根据第三方提供信息再次加密的token',
  `state` varchar(10) COLLATE utf8_bin DEFAULT '1' COMMENT '状态 1 登录 0 未登录',
  `device_unique_id` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '设备唯一标识码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('36dd2c5c9ebe4fa48677cf8b4aa5b77a','中间层APP','0','','','-1','middleApp',NULL,'2018-10-29 16:31:49','1','3');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('8b7130a1e2504d1788b688711fe5ae4b','校园管理','1','/smartSchoolMiddle/school','','36dd2c5c9ebe4fa48677cf8b4aa5b77a','xygl',NULL,'2018-10-29 16:32:34','1','1');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('e8a90824226b4ccba6737eed07fe3ba1','新增','2','','add','8b7130a1e2504d1788b688711fe5ae4b','schoolAddBtn',NULL,'2018-10-29 16:33:03','1','1');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('6adc39571c2c43a1bd04cd6102500c82','编辑','2','','edit','8b7130a1e2504d1788b688711fe5ae4b','schoolEditBtn',NULL,'2018-10-29 16:33:20','1','2');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('cb3a83f3e28444fcb174697ae7d28045','删除','2','','remove','8b7130a1e2504d1788b688711fe5ae4b','schoolRemoveBtn',NULL,'2018-10-29 16:33:42','1','1');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('4a0527700c4148ac916789cf22489290','日志管理','0','','','36dd2c5c9ebe4fa48677cf8b4aa5b77a','rzgl',NULL,'2018-11-01 17:14:36','1','2');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('44a583290d0341d1b851b0d7cafcbba9','操作日志','1','/smartSchoolMiddle/operationlog','','4a0527700c4148ac916789cf22489290','czrz',NULL,'2018-11-01 17:16:00','1','1');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('612d727ff5df40ae84b192ef77059c6a','登录日志','1','/smartSchoolMiddle/loginlog','','4a0527700c4148ac916789cf22489290','dlrz',NULL,'2018-11-01 17:16:31','1','2');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('d8e5e33bea6c4cc997364afdce2cb628','查看更多','2','','viewMore','44a583290d0341d1b851b0d7cafcbba9','viewMoreBtn',NULL,'2018-11-01 17:16:58','1','1');


insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('c48b5f81325244aca3e960981909baa0','logType','日志类型','-1','7','1',NULL,'1','2018-10-30 14:54:02','1');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('de295297afa44c63b320e23a5b284ceb','3','异常日志','c48b5f81325244aca3e960981909baa0','4','1',NULL,'0','2018-10-30 15:05:44','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('81a757987fde43f9aaf3074ab654c5b9','2','操作日志','c48b5f81325244aca3e960981909baa0','3','1',NULL,'0','2018-10-30 15:05:35','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('431b6fa4399a4f858ffec9e8ce8980c6','1','访问日志','c48b5f81325244aca3e960981909baa0','2','1',NULL,'0','2018-10-30 15:05:27','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('38b0cf8725e94a0787f44205877e6ce0','0','登录日志','c48b5f81325244aca3e960981909baa0','1','1',NULL,'0','2018-11-01 11:04:01','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('a2267e110dd240dfa00f8e3eeae27a70','4','修改日志','c48b5f81325244aca3e960981909baa0','5','1',NULL,'0','2018-11-01 11:04:58','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('78f0790b1cbc4911b414d4c14790ef18','5','新增日志','c48b5f81325244aca3e960981909baa0','6','1',NULL,'0','2018-11-01 11:05:49','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('2b6daef60b4444c2bd4cfcb102a2e6fd','6','删除日志','c48b5f81325244aca3e960981909baa0','7','1',NULL,'0','2018-11-01 11:06:08','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('5d24e1a40aa04ba7bb07c6b19093d7cd','loginWay','登录方式','-1','11','1',NULL,'1','2018-11-01 17:46:40','1');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('c431e0a213be4695a95407d1e0b02883','0','手机登录','5d24e1a40aa04ba7bb07c6b19093d7cd','1','1',NULL,'0','2018-11-01 17:48:10','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('ed07d40de70545f0b993a58a4dc733a6','1','微信登录','5d24e1a40aa04ba7bb07c6b19093d7cd','2','1',NULL,'0','2018-11-01 17:48:19','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('19423d528cea4fe8b3902a6ba03675d0','opType','操作类型','-1','12','1',NULL,'1','2018-11-01 17:48:54','1');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('43460d61bbf44a209e79f0168059a894','0','登录','19423d528cea4fe8b3902a6ba03675d0','1','1',NULL,'0','2018-11-01 17:49:08','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('cce111742c1443338ce0cfcd329cab72','1','退出登录','19423d528cea4fe8b3902a6ba03675d0','2','1',NULL,'0','2018-11-01 17:49:23','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('0b88866ff4814bf2adc94ffe3b8d7768','loginLogType','登录状态','-1','13','1',NULL,'1','2018-11-01 17:50:28','1');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('7d32c7b1341b417a88b7453057eec6a8','0','正常','0b88866ff4814bf2adc94ffe3b8d7768','1','1',NULL,'0','2018-11-01 17:50:42','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('9b31d781da8c4a67a2d43b0c6bf4cf88','1','异常','0b88866ff4814bf2adc94ffe3b8d7768','2','1',NULL,'0','2018-11-01 17:50:54','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('2b54661b73274d86907eb6e09a5ae60b','firstLogin','是否首次登录','-1','14','1',NULL,'1','2018-11-01 17:52:04','1');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('0f7f15e3368a430180bab3f350eb6ca6','0','是','2b54661b73274d86907eb6e09a5ae60b','1','1',NULL,'0','2018-11-01 17:52:12','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('204542d6cf7647549115efc1ac35d594','1','否','2b54661b73274d86907eb6e09a5ae60b','2','1',NULL,'0','2018-11-01 17:52:25','2');

CREATE TABLE `sc_gmuni_login_log` (
  `id` varchar(50) COLLATE utf8_bin NOT NULL,
  `username` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '登录用户账号',
  `login_type` char(10) COLLATE utf8_bin DEFAULT NULL COMMENT '登录方式 0:手机号登录 1微信登录',
  `operator` char(10) COLLATE utf8_bin DEFAULT NULL COMMENT '0:登录 1:退出登录',
  `log_type` char(10) COLLATE utf8_bin DEFAULT NULL COMMENT '0:登录 1：异常',
  `create_time` datetime DEFAULT NULL COMMENT '日志添加时间',
  `log_desc` longtext COLLATE utf8_bin COMMENT '登录描述',
  `ip` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '登录用户ip',
  `token` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '用户token',
  `mac` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '用户mac地址',
  `method` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '登录调用方法名称',
  `params` longtext COLLATE utf8_bin DEFAULT NULL COMMENT '登录请求参数',
  `first_login` char(10) COLLATE utf8_bin DEFAULT '1' COMMENT '0:是 1不是',
  `school_code` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '学校编码',
  `request_method` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT 'post get',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `sc_gmuni_operator_log` (
  `id` varchar(50) COLLATE utf8_bin NOT NULL,
  `url` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '访问url',
  `module` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '日志所属模块，需要在数据字典维护',
  `ip` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '访问ip地址',
  `method` varchar(400) COLLATE utf8_bin DEFAULT NULL COMMENT '访问方法名',
  `params` longtext COLLATE utf8_bin DEFAULT NULL COMMENT '访问参数',
  `log_desc` longtext COLLATE utf8_bin COMMENT '日志描述',
  `type` char(10) COLLATE utf8_bin DEFAULT NULL COMMENT '日志类型 1:访问日志，2:操作日志,3:异常日志',
  `request_method` char(10) COLLATE utf8_bin DEFAULT NULL COMMENT '访问方式 1:get 2:post',
  `create_time` datetime DEFAULT NULL COMMENT '添加时间',
  `username` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '当前访问用户',
  `school_code` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '当前用户所属学校编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
CREATE TABLE `sc_gmuni_app_menu` (
  `id` varchar(32) COLLATE utf8_bin NOT NULL COMMENT 'id',
  `code` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '编码',
  `name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `module` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '所属模块',
  `menu_group` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '分组',
  `icon` longtext COLLATE utf8_bin COMMENT '图标base64',
  `url` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '链接地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
CREATE TABLE `sc_gmuni_app_menu_custom` (
  `id` varchar(32) COLLATE utf8_bin NOT NULL COMMENT 'id',
  `schoole_code` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '学校编码',
  `menu_id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '菜单表对应的Id',
  `url` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '自定义的url',
  `icon` longtext COLLATE utf8_bin COMMENT '自定义的图标',
  `module` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '所属模块',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
CREATE TABLE `sc_gmuni_pay_log` (
  `trade_no` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '订单号',
  `total_amount` decimal(9,2) DEFAULT NULL COMMENT '    订单金额',
  `net_id` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '上网账号',
  `pay_way` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '支付方式ali，weichat',
  `pay_status` varchar(2) COLLATE utf8_bin DEFAULT NULL COMMENT '缴费状态0失败1成功',
  `login_code` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT 'app登陆账号',
  `stu_code` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '学号',
  `school_code` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '学校编码',
  `pay_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '缴费成功时间',
  `module` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '类型：网费，电费，水费',
  `reason` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '结果以及原因'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
CREATE TABLE `sc_gmuni_pay_log_ali` (
  `notify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '通知时间通知的发送时间。格式为yyyy-MM-dd HH:mm:ss',
  `notify_type` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '通知类型',
  `notify_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT '通知校验ID',
  `app_id` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '开发者的app_id',
  `charset` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '编码格式，如utf-8、gbk、gb2312等',
  `version` varchar(3) COLLATE utf8_bin NOT NULL DEFAULT '1.0' COMMENT '调用的接口版本，固定为：1.0',
  `sign_type` varchar(10) COLLATE utf8_bin NOT NULL DEFAULT 'RSA2' COMMENT '商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2',
  `sign` varchar(512) COLLATE utf8_bin NOT NULL COMMENT '签名',
  `trade_no` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '支付宝交易号',
  `out_trade_no` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '原支付请求的商户订单号',
  `out_biz_no` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '商户业务ID，主要是退款通知中返回退款申请的流水号',
  `buyer_id` varchar(16) COLLATE utf8_bin DEFAULT NULL COMMENT '买家支付宝账号对应的支付宝唯一用户号。以2088开头的纯16位数字',
  `buyer_logon_id` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '买家支付宝账号',
  `seller_id` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '卖家支付宝用户号',
  `seller_email` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '卖家支付宝账号',
  `trade_status` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '交易目前所处的状态，见交易状态说明',
  `total_amount` decimal(9,2) DEFAULT NULL COMMENT '本次交易支付的订单金额，单位为人民币（元）',
  `receipt_amount` decimal(9,2) DEFAULT NULL COMMENT '商家在交易中实际收到的款项，单位为元',
  `invoice_amount` decimal(9,2) DEFAULT NULL COMMENT '    用户在交易中支付的可开发票的金额',
  `buyer_pay_amount` decimal(9,2) DEFAULT NULL COMMENT '用户在交易中支付的金额',
  `point_amount` decimal(9,2) DEFAULT NULL COMMENT '使用集分宝支付的金额',
  `refund_fee` decimal(9,2) DEFAULT NULL COMMENT '退款通知中，返回总退款金额，单位为元，支持两位小数',
  `subject` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '商品的标题/交易标题/订单标题/订单关键字等，是请求时对应的参数，原样通知回来',
  `body` varchar(400) COLLATE utf8_bin DEFAULT NULL COMMENT '该订单的备注、描述、明细等。对应请求时的body参数，原样通知回来',
  `gmt_create` timestamp NULL DEFAULT NULL COMMENT '该笔交易创建的时间。格式为yyyy-MM-dd HH:mm:ss',
  `gmt_payment` timestamp NULL DEFAULT NULL COMMENT '该笔交易的买家付款时间。格式为yyyy-MM-dd HH:mm:ss',
  `gmt_refund` timestamp NULL DEFAULT NULL COMMENT '该笔交易的退款时间。格式为yyyy-MM-dd HH:mm:ss.S',
  `gmt_close` timestamp NULL DEFAULT NULL COMMENT '该笔交易结束时间。格式为yyyy-MM-dd HH:mm:ss',
  `fund_bill_list` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '支付成功的各个渠道金额信息，',
  `passback_params` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '公共回传参数，如果请求时传递了该参数，则返回给商户时会在异步通知时将该参数原样返回。本参数必须进行UrlEncode之后才可以发送给支付宝',
  `voucher_detail_list` longtext COLLATE utf8_bin COMMENT '本交易支付时所使用的所有优惠券信息'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `sc_gmuni_integral_detail` (
  `id` varchar(200) COLLATE utf8_bin NOT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `year` decimal(10,0) DEFAULT NULL COMMENT '签到年份',
  `month` decimal(10,0) DEFAULT NULL COMMENT '签到月份',
  `day` decimal(10,0) DEFAULT NULL COMMENT '签到天',
  `state` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '积分状态: 0:未获取   1:获取',
  `integral` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '获取的积分',
  `user_info` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '当前用户',
  `campus` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '所属校园',
  `task_code` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '积分任务编码',
  `type` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '积分类型（预留字段）',
  `ext` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '预留字段',
  `sort` decimal(10,0) DEFAULT NULL COMMENT '排序号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `sc_gmuni_integral_statistics` (
  `id` varchar(200) COLLATE utf8_bin NOT NULL,
  `user_info` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '登录用户',
  `integral_total` decimal(10,0) DEFAULT '0' COMMENT '积分总数',
  `integral_remaining` decimal(10,0) DEFAULT NULL COMMENT '剩余积分',
  `count_inte` decimal(10,0) DEFAULT NULL COMMENT '签到计数:连续签到时加1，断签了就归1',
  `cu_time` datetime DEFAULT NULL COMMENT '当前获取积分到时间',
  `create_time` datetime DEFAULT NULL COMMENT '获取积分起始时间',
  `click_through` decimal(10,0) DEFAULT NULL COMMENT '获取次数',
  `task_code` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '所属任务编码(登录、签到、完善信息等)',
  `type` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '任务类型（预留）',
  `sort` decimal(10,0) DEFAULT NULL COMMENT '排序号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `sc_gmuni_integral_task` (
  `id` varchar(200) COLLATE utf8_bin NOT NULL,
  `task_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '任务名称: 签到、登录、完善学籍信息等',
  `task_code` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '任务编码',
  `integral_set` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '积分设置:5、10等',
  `task_validity` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '任务有效期: 长期、自定义',
  `task_status` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '任务状态： 0：未生效 1:生效',
  `task_limit` decimal(10,0) DEFAULT NULL COMMENT '任务限制次数： 0为不限 ',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `task_introduction` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '任务简介',
  `task_content` longtext COLLATE utf8_bin COMMENT '任务详情',
  `user_info` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '发布人',
  `campus` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '所属学校',
  `task_type` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '任务类型(预留)',
  `sort` decimal(10,0) DEFAULT NULL COMMENT '排序号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

insert  into `sc_gmuni_integral_task`(`id`,`task_name`,`task_code`,`integral_set`,`task_validity`,`task_status`,`task_limit`,`create_time`,`update_time`,`task_introduction`,`task_content`,`user_info`,`campus`,`task_type`,`sort`) values ('1','登录','login','10','长期','1','3',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1'),('2','签到','signin','10','长期','1','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2'),('3','学籍信息完善','cioss','5','长期','1','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'3');


ALTER TABLE sc_gmuni_pay_log ADD COLUMN set_meal_type VARCHAR(300);

CREATE TABLE `sc_gmuni_net_package` (
  `id` varchar(32) COLLATE utf8_bin NOT NULL,
  `name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '套餐名称',
  `code` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '套餐编码',
  `school_code` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '套餐对应学校',
  `type` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '套餐类型 包月，包年，流量包等等',
  `create_time` datetime DEFAULT NULL COMMENT '添加时间',
  `tariffs` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '套餐资费',
  `net_desc` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '套餐组描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('c7316c41a5ef45e18105009b5792e5ee','payWay','支付方式','-1','15','1',NULL,'1','2018-11-07 10:20:49','1');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('be4b5efb908d4165a24e679c419b5bc1','onecard','一卡通','c7316c41a5ef45e18105009b5792e5ee','1','1',NULL,'0','2018-11-07 10:22:27','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('050c019da567482bb9c31d446abc2dee','ali','支付宝','c7316c41a5ef45e18105009b5792e5ee','2','1',NULL,'0','2018-11-07 10:22:37','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('054f894563b347419f0b1ac3b53248e9','packageType','套餐类型','-1','16','1',NULL,'1','2018-11-07 13:43:04','1');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('5bd06de2613645c3a97f5ec9b95d2dbe','0','包月','054f894563b347419f0b1ac3b53248e9','1','1',NULL,'0','2018-11-07 13:43:26','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('50b1c6dc36a74007bf7e0af56d2d80ad','1','包年','054f894563b347419f0b1ac3b53248e9','2','1',NULL,'0','2018-11-07 13:43:33','2');

insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('3f65baf3044948d6aa0c0a39577efbe5','缴费统计','0','','','36dd2c5c9ebe4fa48677cf8b4aa5b77a','jftj',NULL,'2018-11-06 15:05:52','1','3');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('5bbff2913cdf4f3796706e12554c4036','网费统计','1','/smartSchoolMiddle/networkfee','','3f65baf3044948d6aa0c0a39577efbe5','netFee',NULL,'2018-11-06 15:08:14','1','1');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('bcd1a21756c645a7a09d2f3503d3c7e6','网费套餐管理','1','/smartSchoolMiddle/networkPackage','','36dd2c5c9ebe4fa48677cf8b4aa5b77a','wftc',NULL,'2018-11-07 13:40:26','1','4');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('08f4192a305f48e28cb94e65f618e5c4','新增','2','','add','bcd1a21756c645a7a09d2f3503d3c7e6','packageAddBtn',NULL,'2018-11-07 13:41:34','1','1');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('6b9a4d54413f4873ada7d4c83e35924f','编辑','2','','edit','bcd1a21756c645a7a09d2f3503d3c7e6','packageEditBtn',NULL,'2018-11-07 13:42:06','1','2');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('43008119e2b5403f83b19332e9cfc104','删除','2','','remove','bcd1a21756c645a7a09d2f3503d3c7e6','packageRemoveBtn',NULL,'2018-11-07 13:42:28','1','3');

insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('f28d2351d4304b0d92056623021dadab','0','学生组','e73dac116f584c89ad69d2cee004fbe5','1','1',NULL,'0','2018-11-13 10:20:00','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('e73dac116f584c89ad69d2cee004fbe5','packageGroup','网费套餐组','-1','17','1',NULL,'1','2018-11-13 10:19:45','1');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('e1af3d45d58440bface1d4180cbb5d19','1','教师组','e73dac116f584c89ad69d2cee004fbe5','2','1',NULL,'0','2018-11-13 10:20:07','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('e812d77e7d3449a9ac8da600064f13ad','2','实验组','e73dac116f584c89ad69d2cee004fbe5','3','1',NULL,'0','2018-11-13 10:20:23','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('1781290615f64fdfbe66919f32380b89','2','包学期','054f894563b347419f0b1ac3b53248e9','3','1',NULL,'0','2018-11-13 10:21:04','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('07316bbd478242418633e886feb54b91','3','包流量','054f894563b347419f0b1ac3b53248e9','4','1',NULL,'0','2018-11-13 10:21:26','2');


insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('f28d2351d4304b0d92056623021dadab','0','学生组','e73dac116f584c89ad69d2cee004fbe5','1','1',NULL,'0','2018-11-13 10:20:00','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('e73dac116f584c89ad69d2cee004fbe5','packageGroup','网费套餐组','-1','17','1',NULL,'1','2018-11-13 10:19:45','1');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('e1af3d45d58440bface1d4180cbb5d19','1','教师组','e73dac116f584c89ad69d2cee004fbe5','2','1',NULL,'0','2018-11-13 10:20:07','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('e812d77e7d3449a9ac8da600064f13ad','2','实验组','e73dac116f584c89ad69d2cee004fbe5','3','1',NULL,'0','2018-11-13 10:20:23','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('1781290615f64fdfbe66919f32380b89','2','包学期','054f894563b347419f0b1ac3b53248e9','3','1',NULL,'0','2018-11-13 10:21:04','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('07316bbd478242418633e886feb54b91','3','包流量','054f894563b347419f0b1ac3b53248e9','4','1',NULL,'0','2018-11-13 10:21:26','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('41c8b23538ef4370b58e95d5f06e4f5a','jfzt','缴费状态','-1','18','1',NULL,'1','2018-11-14 10:27:29','1');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('69a03612198349be9f541cac8de27215','0','成功','41c8b23538ef4370b58e95d5f06e4f5a','1','1',NULL,'0','2018-11-14 10:28:13','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('a911a2e090274504a907f6387f62a178','1','失败','41c8b23538ef4370b58e95d5f06e4f5a','2','1',NULL,'0','2018-11-14 10:28:20','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('c86b48f05c5b4b80839a31459157df1c','jflx','缴费类型','-1','19','1',NULL,'1','2018-11-14 10:29:53','1');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('7a567d2ab0b34f4aa571cd8d97642def','wangfei','网费','c86b48f05c5b4b80839a31459157df1c','1','1',NULL,'0','2018-11-14 12:25:38','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('b5903b26b0ad4f7b881524e5aa87ba65','dianfei','电费','c86b48f05c5b4b80839a31459157df1c','2','1',NULL,'0','2018-11-14 12:26:10','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('fdc7a87da0c14f2ea1816dd3dde41c35','shuifei','水费','c86b48f05c5b4b80839a31459157df1c','3','1',NULL,'0','2018-11-14 12:26:21','2');


insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('85a23b8c16464c5c815ee0fd6dcebc92','钱包充值管理','0','','','36dd2c5c9ebe4fa48677cf8b4aa5b77a','qbcz',NULL,'2018-11-14 09:52:08','1','5');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('87bba72dc7bc4e40aa2d90487cdb4025','充值明细','1','/smartSchoolMiddle/rechargeDetail','','85a23b8c16464c5c815ee0fd6dcebc92','czmx',NULL,'2018-11-14 09:55:41','1','1');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('2cbea58865a3477382a409e5f831d27c','充值统计','1','/smartSchoolMiddle/rechargeTotal','','85a23b8c16464c5c815ee0fd6dcebc92','cztj',NULL,'2018-11-14 09:56:29','1','2');



CREATE TABLE `sc_gmuni_message` (
  `id` varchar(200) COLLATE utf8_bin NOT NULL,
  `title` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '标题',
  `content` longtext COLLATE utf8_bin COMMENT '正文',
  `publisher` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '发布者',
  `dept_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '发布人部门',
  `publish_time` datetime DEFAULT NULL COMMENT '发布时间或者交易时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `user_info` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '登录用户(indentifier)',
  `student_code` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '学号',
  `school_code` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '所属学校(school)',
  `type` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '信息类型： 0 ：通知 1：网费',
  `state` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '阅读状态: 默认为 0：未读  1:已读',
  `trade_no` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '缴费订单号',
  `total_amount` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '出账金额',
  `net_id` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '上网账号',
  `net_type` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '费用类型: 0:支出 1:收入',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `sc_gmuni_device` (
  `id` varchar(100) COLLATE utf8_bin NOT NULL COMMENT 'id',
  `code` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '设备编码',
  `status` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '状态(0:故障1:正常2:正在使用3:锁定)',
  `school_code` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '所属学校',
  `user_code` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `work_time` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '时间长度',
  `type` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '设备类型',
  `device_ip` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '设备ip',
  `usb_port` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT 'usb端口',
  `serial_number` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '序列号',
  `address` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '设备地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


insert  into `sc_gmuni_device`(`id`,`code`,`status`,`school_code`,`user_code`,`update_time`,`work_time`,`type`,`device_ip`,`usb_port`,`serial_number`,`address`) values ('1','1001','1','fuzhuang',NULL,NULL,NULL,'共享吹风机','http://192.168.3.202:8080','com3','0110','老孙的电脑');

CREATE TABLE `sc_gmuni_device_services` (
  `id` varchar(32) COLLATE utf8_bin NOT NULL,
  `name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '大学名称',
  `code` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '大学编码',
  `url` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '学校设备管理服务访问ip',
  `col1` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '备用字段1',
  `col2` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '备用字段2',
  `col3` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '备用字段3',
  `col4` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '备用字段4',
  `col5` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '备用字段5',
  `col6` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '备用字段6',
  `col7` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '备用字段7',
  `col8` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '备用字段8',
  `col9` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '备用字段9',
  `col10` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '备用字段10',
  `create_time` datetime DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('0b4def0e6e7d4fcdbce9158665396822','吹风机管理','0','','','36dd2c5c9ebe4fa48677cf8b4aa5b77a','cfjgl',NULL,'2018-12-21 17:38:32','1','8');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('5a29a5c1db924f7dba181f6180f7a074','消费记录','1','/smartSchoolMiddle/hairDryerFee','','0b4def0e6e7d4fcdbce9158665396822','xfjl',NULL,'2018-12-24 10:16:09','1','1');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('3164f8917bf54f9c97b83151ed3d3b61','设备报修','1','/smartSchoolMiddle/hairDryerManagement','','0b4def0e6e7d4fcdbce9158665396822','sbbx',NULL,'2018-12-24 10:16:42','1','2');

CREATE TABLE `sc_gmuni_leave_message` (
  `id` varchar(100) COLLATE utf8_bin NOT NULL COMMENT 'id',
  `market_id` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '所留言集市商品id',
  `message_user` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '留言者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `school_code` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '所属学校',
  `message` longtext COLLATE utf8_bin COMMENT '留言信息',
  `replier` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '回复者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `sc_gmuni_market` (
  `id` varchar(50) COLLATE utf8_bin NOT NULL,
  `school_code` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '所属学校',
  `user` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '用户',
  `title` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '标题',
  `introduce` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '详情',
  `img_s` longtext COLLATE utf8_bin COMMENT '缩略图',
  `img_b` longtext COLLATE utf8_bin COMMENT '大图',
  `price` decimal(9,2) DEFAULT NULL COMMENT '价格',
  `type` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '类型',
  `sale_status` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '出售状态(0:未出售 1:已出售)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '状态(0:正常，1:锁定)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;



CREATE TABLE `sc_gmuni_report` (
  `id` varchar(100) COLLATE utf8_bin NOT NULL COMMENT 'id',
  `market_id` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '集市信息的id',
  `create_time` datetime DEFAULT NULL COMMENT '举报时间（创建时间）',
  `report_user` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '举报人',
  `reason` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '举报原因',
  `school_code` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '所属学校',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


