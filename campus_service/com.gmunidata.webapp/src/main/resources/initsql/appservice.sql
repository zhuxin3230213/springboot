/*
 初始化pf_gmuni_resource表中的数据
 */
 INSERT INTO `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) VALUES('5c272d01bf0a45d8b6fe61107e81ad83','智慧校园','0','','','-1','zhxy',NULL,'2018-08-17 16:41:54','1','4');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('e4049b2595f24181a88fbfa0bf3190e9','班级管理','1','/smartschool/class','','5c272d01bf0a45d8b6fe61107e81ad83','bjgl',NULL,'2018-08-17 16:42:57','1','1');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('c94b1f6019f84f50b57c4d619ab7b35d','新增','2','','add','e4049b2595f24181a88fbfa0bf3190e9','smartschoolAddBtn',NULL,'2018-08-17 16:43:52','1','1');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('aafad49c2d2e4403b9a3121e54c687f8','编辑','2','','edit','e4049b2595f24181a88fbfa0bf3190e9','smartschoolEditBtn',NULL,'2018-08-17 16:44:19','1','2');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('71938ab4f8f646c6a097215465f8171c','删除','2','','remove','e4049b2595f24181a88fbfa0bf3190e9','smartschoolRemoveBtn',NULL,'2018-08-17 16:45:04','1','3');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('50d54350a94340eeb94657ec505c14b2','导入','2','','importBtn','e4049b2595f24181a88fbfa0bf3190e9','smartschoolImportBtn',NULL,'2018-08-17 16:45:46','1','4');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('c416166248c9408bb3e442e90aef904d','导出','2','','exportBtn','e4049b2595f24181a88fbfa0bf3190e9','smartschoolExportBtn',NULL,'2018-08-17 16:46:39','1','5');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('b9315937ecb248d38d1091c3a7ef8765','发布','2','','publish','ac68dda6996846628cd09b33fd1da915','schoolNewsPublishBtn',NULL,'2018-10-09 15:41:38','1','4');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('e0b4a8039bc7482a979b2722cecd6e5e','专业管理','1','/enrollment/proManagmt/subject','','5c272d01bf0a45d8b6fe61107e81ad83','subject',NULL,'2018-10-09 16:08:21','1','8');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('a5366e20344c41d5a4da904fe6e544f2','同步','2','','sync','e0b4a8039bc7482a979b2722cecd6e5e','syncBtn',NULL,'2018-10-09 16:16:48','1','1');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('48e06f36bad944d3b93ea6cb60e45ad2','编辑','2','','edit','e0b4a8039bc7482a979b2722cecd6e5e','editBtn',NULL,'2018-10-09 16:17:08','1','2');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('049cb9147f9643448096310d0c3f3e44','撤销','2','','revert','ac68dda6996846628cd09b33fd1da915','schoolNewsRevertBtn',NULL,'2018-10-09 16:28:40','1','5');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('e27a895e3ec24216be613b257fb22515','发布','2','','publish','f99d3fe8f6354ab8a55c388139c150a4','noticePublishBtn',NULL,'2018-10-09 16:30:04','1','4');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('1794e9f81f0749078f6cd66b28c0142f','撤销','2','','revert','f99d3fe8f6354ab8a55c388139c150a4','noticeRevertBtn',NULL,'2018-10-09 16:30:36','1','5');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('f3d4074f8ee44e1da6790b95ee5f90f5','科目管理','1','/smartschool/course','','5c272d01bf0a45d8b6fe61107e81ad83','kcgl',NULL,'2018-08-21 16:18:54','1','2');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('4effa96da2ab4884a0995bc80414b439','新增','2','','add','f3d4074f8ee44e1da6790b95ee5f90f5','courseAddBtn',NULL,'2018-08-21 16:22:31','1','1');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('c732e10b28704c8093a8d36b0e8c161f','编辑','2','','edit','f3d4074f8ee44e1da6790b95ee5f90f5','courseEditBtn',NULL,'2018-08-21 17:03:29','1','2');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('5e5748e1033e47be9f5ddf175e9dae5f','删除','2','','remove','f3d4074f8ee44e1da6790b95ee5f90f5','courseDeleteBtn',NULL,'2018-08-21 17:06:21','1','3');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('6af6acbfebc045f5a5cca70a93243cfc','导入','2','','importBtn','f3d4074f8ee44e1da6790b95ee5f90f5','courseImportBtn',NULL,'2018-08-21 17:05:08','1','4');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('b2852de62a954b84b28f95e776efe0bf','导出','2','','exportBtn','f3d4074f8ee44e1da6790b95ee5f90f5','courseExportBtn',NULL,'2018-08-21 17:05:30','1','5');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('f809cc19b9994a54996313b1da1d1609','学生信息管理','1','/smartschool/studentInfo','','5c272d01bf0a45d8b6fe61107e81ad83','studentInfo',NULL,'2018-08-28 12:01:22','1','3');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('e744b4fcd8874d6395a327de793cf2a1','新增','2','','add','f809cc19b9994a54996313b1da1d1609','studentInfoAddBtn',NULL,'2018-08-28 12:01:49','1','1');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('af0500934e4b43d8ad103c074ab7dca2','编辑','2','','edit','f809cc19b9994a54996313b1da1d1609','studentInfoEditBtn',NULL,'2018-08-28 12:02:05','1','2');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('de01347306f946b5ae68985173fbf4dd','删除','2','','remove','f809cc19b9994a54996313b1da1d1609','studentInfoDelBtn',NULL,'2018-08-28 12:02:23','1','3');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('ec370d69154d434cbc90103d3d4905f8','导入','2','','importBtn','f809cc19b9994a54996313b1da1d1609','studentInfoImportBtn',NULL,'2018-08-28 12:02:41','1','4');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('0a9747a1939d4eed966f9795891a4fe7','导出','2','','exportBtn','f809cc19b9994a54996313b1da1d1609','studentInfoExportBtn',NULL,'2018-08-28 12:03:06','1','5');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('9b8db701851c4fe9b6e96cbce537be94','开课时间管理','1','/smartschool/courseTime','','5c272d01bf0a45d8b6fe61107e81ad83','kksjgl',NULL,'2018-08-30 11:03:48','1','4');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('16b30397ee00405cbc41f33f0510fe11','新增','2','','add','9b8db701851c4fe9b6e96cbce537be94','classTimeAddBtn',NULL,'2018-08-30 11:15:39','1','1');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('b68ba9b98dfc4de49fccf1e4ae58e738','编辑','2','','edit','9b8db701851c4fe9b6e96cbce537be94','classTimeEditBtn',NULL,'2018-08-30 11:15:53','1','2');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('645e39880e0c4bda8b4b132d31c7ef3a','删除','2','','remove','9b8db701851c4fe9b6e96cbce537be94','classTimeRemoveBtn',NULL,'2018-08-30 11:16:06','1','3');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('77ea753299694da4ac70ccf84836a90c','导入','2','','importBtn','9b8db701851c4fe9b6e96cbce537be94','classTimeImportBtn',NULL,'2018-08-30 11:16:53','1','4');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('d700241630944738940ef9075777669d','导出','2','','exportBtn','9b8db701851c4fe9b6e96cbce537be94','classTimeExportBtn',NULL,'2018-08-30 11:17:07','1','5');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('b1f2624e994d4a6d835d4309b6e2e548','课程表管理','1','/smartschool/schedule','','5c272d01bf0a45d8b6fe61107e81ad83','kcbgl',NULL,'2018-08-30 18:09:15','1','5');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('6952986f3b0c4fbfb1c4b10bb5c4b64b','查看','2','','view','b1f2624e994d4a6d835d4309b6e2e548','scheduleView',NULL,'2018-08-30 18:22:47','1','1');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('3da83c85ad2342f69d2e6854500fa2de','复用','2','','copy','b1f2624e994d4a6d835d4309b6e2e548','scheduleCopy',NULL,'2018-08-30 18:23:17','1','2');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('ac68dda6996846628cd09b33fd1da915','校内新闻','1','/smartschool/schoolNews','','5c272d01bf0a45d8b6fe61107e81ad83','xnxw',NULL,'2018-09-03 10:42:48','1','6');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('eda80ede1c614c91be22cba2dac33c8f','新增','2','','add','ac68dda6996846628cd09b33fd1da915','schoolNewsAddBtn',NULL,'2018-09-03 15:21:09','1','1');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('30b749f6fa414820bee8015764cad850','编辑','2','','edit','ac68dda6996846628cd09b33fd1da915','schoolNewsEditBtn',NULL,'2018-09-03 15:22:27','1','2');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('489b20ca9f594eaa886e8219ff264c82','删除','2','','remove','ac68dda6996846628cd09b33fd1da915','schoolNewsRemoveBtn',NULL,'2018-09-03 15:23:51','1','3');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('f99d3fe8f6354ab8a55c388139c150a4','通知公告','1','/smartschool/notice','','5c272d01bf0a45d8b6fe61107e81ad83','tzgg',NULL,'2018-09-05 14:02:17','1','7');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('adcc40ff7355481ca6a7939321771adf','新增','2','','add','f99d3fe8f6354ab8a55c388139c150a4','noticesAddBtn',NULL,'2018-09-05 14:04:39','1','1');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('720cce1f94324748a89bc3f109a32b07','删除','2','','remove','f99d3fe8f6354ab8a55c388139c150a4','noticesRemoveBtn',NULL,'2018-09-05 14:05:24','1','3');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('fdfd9175de5d4a1f8220bd78cd98e34c','编辑','2','','edit','f99d3fe8f6354ab8a55c388139c150a4','noticesEditBtn',NULL,'2018-09-05 14:12:41','1','3');
/*
 初始化pf_gmuni_lookup表中的数据
 */
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('f4417ed0e9de4c87bb2ba065d129036f','studyType','学习类型','-1','17','1',NULL,'1','2018-08-22 15:30:55','1');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('d93dd5801db84586aa64d8e569249d20','courseType','课程类型','-1','18','1',NULL,'1','2018-08-22 15:31:13','1');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('8e96341058214451ac19fbbd0f06a2c9','classroom','教室要求','-1','19','1',NULL,'1','2018-08-22 15:31:35','1');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('99c147e7788842d6b770b688cdf1f6ee','baseCourse','基础课程','-1','20','1',NULL,'1','2018-08-22 15:31:51','1');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('ac748a3b9d48453081b7394163430dc9','lilun','理论','f4417ed0e9de4c87bb2ba065d129036f','1','1',NULL,'0','2018-08-22 15:32:08','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('7d015bda64e84939a544db4adaacca98','shijian','实践','f4417ed0e9de4c87bb2ba065d129036f','2','1',NULL,'0','2018-08-22 15:32:20','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('fea832184abb4279808c5efb8290f658','bixiu','必修','d93dd5801db84586aa64d8e569249d20','1','1',NULL,'0','2018-08-22 15:32:38','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('04e974385d3741849ac303d049d20342','xuanxiu','选修','d93dd5801db84586aa64d8e569249d20','2','1',NULL,'0','2018-08-22 15:32:50','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('0ed7fee215da4ec8b7c8988955f20bf1','unlimited','不限','8e96341058214451ac19fbbd0f06a2c9','1','1',NULL,'0','2018-08-22 15:33:02','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('756bd55ab43547f0963f6f7772dfdc76','djs','大教室','8e96341058214451ac19fbbd0f06a2c9','2','1',NULL,'0','2018-08-22 15:33:20','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('cd3db73acc5f48cab9f7805a57587045','wjs','微机室','8e96341058214451ac19fbbd0f06a2c9','3','1',NULL,'0','2018-08-22 15:39:27','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('12994a7cdf9c4fe18ef1a2d464f6685e','ptjs','普通教室','8e96341058214451ac19fbbd0f06a2c9','4','1',NULL,'0','2018-08-22 15:39:39','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('f3930263011d4c90a1124e121a567c8a','1','是','99c147e7788842d6b770b688cdf1f6ee','1','1',NULL,'0','2018-08-22 15:40:02','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('45c8ef18ff6f4bc98da0ed2f60c0bd83','0','否','99c147e7788842d6b770b688cdf1f6ee','2','1',NULL,'0','2018-08-22 15:40:11','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('ca4c497cfe994404b449824659b5cb64','stuStatus','学生状态','-1','21','1',NULL,'1','2018-08-29 10:11:48','1');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('70b48dd27ce148fcb4d0077cbc90a4a6','way','入学方式','-1','22','1',NULL,'1','2018-08-29 10:12:00','1');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('58f4158c6ffc49e9b377a0e0593e8897','lc','学制','-1','23','1',NULL,'1','2018-08-29 10:12:18','1');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('2a2cddcf7ec44a8abaa49d9d2d35f838','politicsStatus','政治面貌','-1','24','1',NULL,'1','2018-08-29 10:12:33','1');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('2a70f81ac80940e0ae9dbcfae1bbd0d6','type','学习形式','-1','25','1',NULL,'1','2018-08-29 10:12:46','1');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('9f9d70b0cd4042c48ce59366f38da472','categoryType','类别形式','-1','26','1',NULL,'1','2018-08-29 10:13:00','1');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('2539a03a1cb34da7949f09ae9d59a9be','eType','招生类型','-1','27','1',NULL,'1','2018-08-29 10:13:17','1');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('9a6770ab0e9142848c8a307e3a1377a4','hkm','是否港澳台侨外','-1','28','1',NULL,'1','2018-08-29 10:13:31','1');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('358bb0e98b014ddda7c35d83be50b11c','0','毕业','ca4c497cfe994404b449824659b5cb64','1','1',NULL,'0','2018-08-29 10:14:13','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('a92f5ffeb4fb4ab2944a1f4be3a230c0','1','在读','ca4c497cfe994404b449824659b5cb64','2','1',NULL,'0','2018-08-29 10:14:19','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('334fe3c2a2274fce89c5852666ef4696','2','休学','ca4c497cfe994404b449824659b5cb64','3','1',NULL,'0','2018-08-29 10:14:29','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('401b304e35584d968d2444d506cfd597','0','统招','70b48dd27ce148fcb4d0077cbc90a4a6','1','1',NULL,'0','2018-08-29 10:14:43','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('27762e0b6e72433db0b2f94afa7bfbc9','1','体育特招','70b48dd27ce148fcb4d0077cbc90a4a6','2','1',NULL,'0','2018-08-29 10:14:53','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('843a92c112294a029397b1d459209c1c','2','艺术特招','70b48dd27ce148fcb4d0077cbc90a4a6','3','1',NULL,'0','2018-08-29 10:15:01','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('ee0e53d4183e468f83ddbcd2f5c28f6e','0','四年制本科','58f4158c6ffc49e9b377a0e0593e8897','1','1',NULL,'0','2018-08-29 10:15:15','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('b1c2822d3bba486094f459bdafa47135','0','群众','2a2cddcf7ec44a8abaa49d9d2d35f838','1','1',NULL,'0','2018-08-29 10:16:06','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('a6736a7f55b54cc691979e8c5089824d','1','预备党员','2a2cddcf7ec44a8abaa49d9d2d35f838','2','1',NULL,'0','2018-08-29 10:16:13','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('63d9d9b0a9904fe5bbcd154eeb174b00','2','党员','2a2cddcf7ec44a8abaa49d9d2d35f838','3','1',NULL,'0','2018-08-29 10:16:24','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('feecdfba05ef44a59f4b7bc2baa10862','0','脱产','2a70f81ac80940e0ae9dbcfae1bbd0d6','1','1',NULL,'0','2018-08-29 10:16:51','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('8a14ef1e0924490f9659e9c26464bb14','1','业余','2a70f81ac80940e0ae9dbcfae1bbd0d6','2','1',NULL,'0','2018-08-29 10:16:58','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('05e3d62fa17149b5afa348634df59a35','2','全日制','2a70f81ac80940e0ae9dbcfae1bbd0d6','3','1',NULL,'0','2018-08-29 10:17:07','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('3dc4ae3079164e439d07c6568d73f4c4','3','非全日制','2a70f81ac80940e0ae9dbcfae1bbd0d6','4','1',NULL,'0','2018-08-29 10:17:14','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('391960d43f3d44c0a97b0a8f0ce3ab45','0','普通考生','9f9d70b0cd4042c48ce59366f38da472','1','1',NULL,'0','2018-08-29 10:17:24','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('217d1df4da9f4789be28d8a29370c6d4','1','特长类考生','9f9d70b0cd4042c48ce59366f38da472','2','1',NULL,'0','2018-08-29 10:17:36','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('24082bb1df5b4ec8a95848a170fa1495','0','春季招生','2539a03a1cb34da7949f09ae9d59a9be','1','1',NULL,'0','2018-08-29 10:17:47','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('d437ab1fe7614322952a4f4f8f3210a6','1','秋季招生','2539a03a1cb34da7949f09ae9d59a9be','2','1',NULL,'0','2018-08-29 10:17:57','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('6f26f879d7604c1b9e40ab111ddbad0c','0','否','9a6770ab0e9142848c8a307e3a1377a4','1','1',NULL,'0','2018-08-29 10:27:03','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('86b6c14b29924bd68d88d384b5ae2747','1','是','9a6770ab0e9142848c8a307e3a1377a4','2','1',NULL,'0','2018-08-29 10:27:11','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('d7c90fc61cc74a62a5ea85cbd0d74543','nation','民族','-1','29','1',NULL,'1','2018-08-29 15:16:45','1');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('b19405daa8604bc5b461743a20b8678f','0','汉族','d7c90fc61cc74a62a5ea85cbd0d74543','1','1',NULL,'0','2018-08-29 15:18:54','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('067bb30c94b94d4c87635357779d6cbe','1','壮族','d7c90fc61cc74a62a5ea85cbd0d74543','2','1',NULL,'0','2018-08-29 15:19:02','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('393ad178bc554ed3bb83bb2eaad0f0bb','2','回族','d7c90fc61cc74a62a5ea85cbd0d74543','3','1',NULL,'0','2018-08-29 15:19:12','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('91a68f3e2fef43cdbedda45548eff0ba','3','满族','d7c90fc61cc74a62a5ea85cbd0d74543','4','1',NULL,'0','2018-08-29 15:19:21','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('8e5b259498f2468592b3ec64c69da587','4','维吾尔族','d7c90fc61cc74a62a5ea85cbd0d74543','5','1',NULL,'0','2018-08-29 15:19:33','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('94ef3e1a60524dbd961e026c4f1c1a0f','5','苗族','d7c90fc61cc74a62a5ea85cbd0d74543','6','1',NULL,'0','2018-08-29 15:19:49','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('224626c47d3941658728cd2bc54a501b','6','彝族','d7c90fc61cc74a62a5ea85cbd0d74543','7','1',NULL,'0','2018-08-29 15:19:57','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('6544a751f5364f5ea3b4e980688bc3ea','7','土家族','d7c90fc61cc74a62a5ea85cbd0d74543','8','1',NULL,'0','2018-08-29 15:20:06','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('a9c41407b33a4d138401b9ed94198b2d','8','藏族','d7c90fc61cc74a62a5ea85cbd0d74543','9','1',NULL,'0','2018-08-29 15:20:14','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('a56477ad764a4a39a401bd7dc7d08fe4','9','蒙古族','d7c90fc61cc74a62a5ea85cbd0d74543','10','1',NULL,'0','2018-08-29 15:20:28','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('e4464ab05ae74a5babe811029985f6a5','10','侗族','d7c90fc61cc74a62a5ea85cbd0d74543','11','1',NULL,'0','2018-08-29 15:21:23','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('0920ce109dd14bbfb71c7f1c54051dd0','11','布依族','d7c90fc61cc74a62a5ea85cbd0d74543','12','1',NULL,'0','2018-08-29 15:21:38','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('318ff67bfdbd4a99a4cfa51ee6018446','12','瑶族','d7c90fc61cc74a62a5ea85cbd0d74543','13','1',NULL,'0','2018-08-29 15:21:46','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('c61b45a8bdcc488482d8d4f0f74877ff','13','白族','d7c90fc61cc74a62a5ea85cbd0d74543','14','1',NULL,'0','2018-08-29 15:21:55','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('8c511fd5bb5642329539fe95a72c7d51','14','朝鲜族','d7c90fc61cc74a62a5ea85cbd0d74543','15','1',NULL,'0','2018-08-29 15:22:06','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('d0c1e4f70e4c4db8a1cdc2a9ea660d15','15','哈尼族','d7c90fc61cc74a62a5ea85cbd0d74543','16','1',NULL,'0','2018-08-29 15:22:16','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('8caac128636a418581044da6f1774193','16','黎族','d7c90fc61cc74a62a5ea85cbd0d74543','17','1',NULL,'0','2018-08-29 15:22:35','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('e1034e0a879149899c59122d53de31d1','17','哈萨克族','d7c90fc61cc74a62a5ea85cbd0d74543','18','1',NULL,'0','2018-08-29 15:22:48','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('8bbd12d4d1f84df28f9775d9108d47b8','18','傣族','d7c90fc61cc74a62a5ea85cbd0d74543','19','1',NULL,'0','2018-08-29 15:22:58','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('47c03b9c308a48a591dc6d1e293fd3b1','19','畲族','d7c90fc61cc74a62a5ea85cbd0d74543','20','1',NULL,'0','2018-08-29 15:23:09','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('c39fcd571f1748e79a2b0adde87091d2','20','傈僳族','d7c90fc61cc74a62a5ea85cbd0d74543','21','1',NULL,'0','2018-08-29 15:23:28','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('ef48c1bb367e497b8a6ce0e7815c08e7','21','东乡族','d7c90fc61cc74a62a5ea85cbd0d74543','22','1',NULL,'0','2018-08-29 15:25:07','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('426f08df7ae242f9b46afea504e3b2c8','22','仡佬族','d7c90fc61cc74a62a5ea85cbd0d74543','23','1',NULL,'0','2018-08-29 15:25:18','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('b4c9c0904935479189ae589e8d1a59b8','23','拉祜族','d7c90fc61cc74a62a5ea85cbd0d74543','24','1',NULL,'0','2018-08-29 15:26:26','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('b5f3a341f3a44112a7342af7eeeae060','24','佤族','d7c90fc61cc74a62a5ea85cbd0d74543','25','1',NULL,'0','2018-08-29 15:26:36','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('53c6051afb9d4ca1b089c270c3fd7077','25','水族','d7c90fc61cc74a62a5ea85cbd0d74543','26','1',NULL,'0','2018-08-29 15:26:48','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('29d164d4fe0d42b7b2c7f42e32f2d4a1','26','纳西族','d7c90fc61cc74a62a5ea85cbd0d74543','27','1',NULL,'0','2018-08-29 15:27:00','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('43158023c2e845848d7f8203fb646a59','27','羌族','d7c90fc61cc74a62a5ea85cbd0d74543','28','1',NULL,'0','2018-08-29 15:35:37','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('3dae8057a9be4514b739beee25bd2d57','28','土族','d7c90fc61cc74a62a5ea85cbd0d74543','29','1',NULL,'0','2018-08-29 15:35:46','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('bbc4ab95f1dd45fd83e8c2ec8b78ab1e','29','仫佬族','d7c90fc61cc74a62a5ea85cbd0d74543','30','1',NULL,'0','2018-08-29 15:36:00','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('46145ccb3b0d4dd8a9c7856b307dd65b','semester','学期','-1','30','1',NULL,'1','2018-08-30 11:11:56','1');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('bc23a8b0f3fc4fc590c8732aa38b02a3','6','星期六','15bc0bb18fed4523a78e8a4c1a2ead4f','6','1',NULL,'0','2018-09-03 16:03:54','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('ed1ccb37fed941ca9cff6f31ec65d701','2','第2学期','46145ccb3b0d4dd8a9c7856b307dd65b','2','1',NULL,'0','2018-09-04 18:23:09','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('84d09cfb8164485eb10ae5fae400436c','1','第1学期','46145ccb3b0d4dd8a9c7856b307dd65b','1','1',NULL,'0','2018-09-04 18:23:05','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('15bc0bb18fed4523a78e8a4c1a2ead4f','sunday','星期','-1','31','1',NULL,'1','2018-09-03 16:02:54','1');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('50bc7bf16c654c9cafd4b34861f5645b','1','星期一','15bc0bb18fed4523a78e8a4c1a2ead4f','1','1',NULL,'0','2018-09-03 16:03:05','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('0d26ed99e6e342599a84aef98cc83399','2','星期二','15bc0bb18fed4523a78e8a4c1a2ead4f','2','1',NULL,'0','2018-09-03 16:03:13','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('1f8b1625032b4e2a9988c8a7fbb2941a','3','星期三','15bc0bb18fed4523a78e8a4c1a2ead4f','3','1',NULL,'0','2018-09-03 16:03:23','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('fb898c77e4d74e29a4798acda95f5054','4','星期四','15bc0bb18fed4523a78e8a4c1a2ead4f','4','1',NULL,'0','2018-09-03 16:03:33','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('f54eb40da93041458a5fa7b37be8e4ee','5','星期五','15bc0bb18fed4523a78e8a4c1a2ead4f','5','1',NULL,'0','2018-09-03 16:03:46','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('0f300ce2a59a49f49e1d477e09936078','7','星期日','15bc0bb18fed4523a78e8a4c1a2ead4f','7','1',NULL,'0','2018-09-03 16:04:02','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('5394af72fe9441e7943e40e31e0ab667','30','锡伯族','d7c90fc61cc74a62a5ea85cbd0d74543','31','1',NULL,'0','2018-09-26 10:17:14','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('44a808a721354befb7b2d6e868f04062','31','柯尔克孜族','d7c90fc61cc74a62a5ea85cbd0d74543','32','1',NULL,'0','2018-09-26 10:17:32','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('4708b7b475934a5ca3402bfdc4a024f0','32','景颇族','d7c90fc61cc74a62a5ea85cbd0d74543','33','1',NULL,'0','2018-09-26 10:17:40','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('1f0208a18ede4a0381f0b4a27738a5fc','33','达斡尔族','d7c90fc61cc74a62a5ea85cbd0d74543','34','1',NULL,'0','2018-09-26 10:17:50','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('75b7c1793c7849ae809d9cc55ebd066b','34','撒拉族','d7c90fc61cc74a62a5ea85cbd0d74543','35','1',NULL,'0','2018-09-26 10:18:00','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('dbdf89c19b66490582a46556fb14c992','35','布朗族','d7c90fc61cc74a62a5ea85cbd0d74543','36','1',NULL,'0','2018-09-26 10:18:11','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('bf90f996f74d44919f0a6e122e0fea05','36','毛南族','d7c90fc61cc74a62a5ea85cbd0d74543','37','1',NULL,'0','2018-09-26 10:18:23','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('f75de25696c24c35befa97058d35e0ad','37','塔吉克族','d7c90fc61cc74a62a5ea85cbd0d74543','38','1',NULL,'0','2018-09-26 10:18:39','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('57cd7538e43d40cda797ce279fb2568e','38','普米族','d7c90fc61cc74a62a5ea85cbd0d74543','39','1',NULL,'0','2018-09-26 10:18:50','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('75946f4b2204489092d2e7fd9032c022','39','阿昌族','d7c90fc61cc74a62a5ea85cbd0d74543','40','1',NULL,'0','2018-09-26 10:19:00','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('3f6251868b274e488b65b2bbe7f43533','40','怒族','d7c90fc61cc74a62a5ea85cbd0d74543','41','1',NULL,'0','2018-09-26 10:19:11','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('338823bbb8d04fd99460dbf5a4add942','41','鄂温克族','d7c90fc61cc74a62a5ea85cbd0d74543','42','1',NULL,'0','2018-09-26 10:19:25','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('2a05e5b61ad149d28e18b3acc6549ce5','42','京族','d7c90fc61cc74a62a5ea85cbd0d74543','43','1',NULL,'0','2018-09-26 10:19:34','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('94e6150dac8e4df8a6ca50ac199e42e0','43','基诺族','d7c90fc61cc74a62a5ea85cbd0d74543','44','1',NULL,'0','2018-09-26 10:20:20','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('4c1d022a73354f21b300843f65acbeac','44','德昂族','d7c90fc61cc74a62a5ea85cbd0d74543','45','1',NULL,'0','2018-09-26 10:20:29','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('0e31badefdc7434fb856ec8331d4db43','45','保安族','d7c90fc61cc74a62a5ea85cbd0d74543','46','1',NULL,'0','2018-09-26 10:20:41','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('106af2198a2a400592cad59b08b24ce0','46','俄罗斯族','d7c90fc61cc74a62a5ea85cbd0d74543','47','1',NULL,'0','2018-09-26 10:20:51','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('3e66568f76ae48888642266046a7d95d','47','裕固族','d7c90fc61cc74a62a5ea85cbd0d74543','48','1',NULL,'0','2018-09-26 10:21:03','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('5e6b95508c404fa98f2c62dc58f21247','48','乌孜别克族','d7c90fc61cc74a62a5ea85cbd0d74543','49','1',NULL,'0','2018-09-26 10:21:20','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('56472656ef6a4754843038b4e2d8a703','49','门巴族','d7c90fc61cc74a62a5ea85cbd0d74543','50','1',NULL,'0','2018-09-26 10:21:35','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('097627cb4ffa48978cc9a63e9abbbaf2','50','鄂伦春族','d7c90fc61cc74a62a5ea85cbd0d74543','51','1',NULL,'0','2018-09-26 10:21:48','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('c04a363408df4400b192e63f1093e990','51','独龙族','d7c90fc61cc74a62a5ea85cbd0d74543','52','1',NULL,'0','2018-09-26 10:22:06','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('e0f811502d69404e8dcc2d8f75cf28b4','52','赫哲族','d7c90fc61cc74a62a5ea85cbd0d74543','53','1',NULL,'0','2018-09-26 10:22:17','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('cf158524d020444eba557cd5b1c75f5d','53','高山族','d7c90fc61cc74a62a5ea85cbd0d74543','54','1',NULL,'0','2018-09-26 10:22:28','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('4af66737197549089f87f6a3e3fcfd58','54','珞巴族','d7c90fc61cc74a62a5ea85cbd0d74543','55','1',NULL,'0','2018-09-26 10:22:40','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('af4ea16fb4504d1ca34b2f7f33c56005','55','塔塔尔族','d7c90fc61cc74a62a5ea85cbd0d74543','56','1',NULL,'0','2018-09-26 10:22:49','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('3e1532582db44362a957805306c95654','publishStatus','发布状态','-1','32','1',NULL,'1','2018-10-09 15:53:32','1');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('256bf83a3a7448cfa25aa2feb7454c4c','0','否','3e1532582db44362a957805306c95654','1','1',NULL,'0','2018-10-09 15:54:30','2');
insert into `pf_gmuni_lookup` (`ID`, `code`, `name`, `parent_id`, `sort`, `status`, `remark`, `type`, `create_time`, `level`) values('b4a2be3badac42e499bdda154fcb91e5','1','是','3e1532582db44362a957805306c95654','1','1',NULL,'0','2018-10-09 15:54:36','2');

CREATE TABLE `sc_gmuni_class` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `name` varchar(200) DEFAULT NULL COMMENT '名称',
  `subject_id` varchar(32) DEFAULT NULL COMMENT '所属专业',
  `period` varchar(50) DEFAULT NULL COMMENT '届',
  `create_time` datetime NOT NULL COMMENT '添加时间',
  `year` varchar(100) NOT NULL COMMENT '入学年份',
  `head_teacher` varchar(50) DEFAULT NULL COMMENT '班主任',
  `code` varchar(200) DEFAULT NULL COMMENT '编码',
  `class_no` varchar(100) DEFAULT NULL COMMENT '班级号唯一',
  `education` varchar(100) DEFAULT NULL COMMENT '学历',
  `class_num` varchar(50) DEFAULT NULL COMMENT '班级人数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `sc_gmuni_course` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `name` varchar(200) DEFAULT NULL COMMENT '课程名称',
  `code` varchar(200) DEFAULT NULL COMMENT '课程编码',
  `c_type` varchar(50) DEFAULT NULL COMMENT '课程类型(必修，选修)',
  `s_type` varchar(50) DEFAULT NULL COMMENT '学习类型(理论，实践)',
  `periods` int(11) DEFAULT NULL COMMENT '课程学时',
  `credit` decimal(10,1) DEFAULT NULL COMMENT '课程学分',
  `requires` varchar(50) DEFAULT NULL COMMENT '教室要求(不限制，大教室，微机室，普通教室)',
  `total_period` int(11) DEFAULT NULL COMMENT '课程总学时',
  `base_c` varchar(10) DEFAULT NULL COMMENT '基础课程(是1，否0)',
  `semester` varchar(50) DEFAULT NULL COMMENT '开课学期数',
  `create_time` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `sc_gmuni_course_time` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `grade` varchar(50) DEFAULT NULL COMMENT '年级',
  `academic_year` varchar(50) DEFAULT NULL COMMENT '学年',
  `semester` varchar(50) DEFAULT NULL COMMENT '学期',
  `s_date` date DEFAULT NULL COMMENT '开课日期',
  `e_date` date DEFAULT NULL COMMENT '结课日期',
  `create_time` date DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `sc_gmuni_login` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `student_code` varchar(200) DEFAULT NULL COMMENT '学号',
  `login_pwd` varchar(200) DEFAULT NULL COMMENT '登陆密码',
  `status` varchar(10) DEFAULT NULL COMMENT '状态1可用  0不可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `sc_gmuni_news` (
  `id` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '主键',
  `user_id` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '添加人员',
  `status` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '状态：0：终止，1生效 ',
  `title` varchar(300) COLLATE utf8_bin DEFAULT NULL COMMENT '正文标题',
  `content` longtext COLLATE utf8_bin COMMENT '正文内容，富文本',
  `create_time` datetime DEFAULT NULL COMMENT '添加时间',
  `end_time` datetime DEFAULT NULL COMMENT '终止时间',
  `source` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '来源',
  `keywords` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '文章关键字',
  `type` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '0通知 1公告 2新闻',
  `cover` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '文章封面',
  `attachment` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '对应附件id，多个用逗号隔开',
  `update_time` datetime DEFAULT NULL COMMENT '文章最后修改时间',
  `sort` decimal(10,0) DEFAULT NULL COMMENT '文章排序号',
  `top` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '是否置顶 0 否 1 是',
  `description` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '文章描述',
  `image_ids` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '保存正文中使用的所有图片id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;



CREATE TABLE `sc_gmuni_notice` (
  `id` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '主键',
  `user_id` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '添加人员(部门或人员)',
  `status` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '状态：0:禁用，1:启用',
  `title` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '正文标题',
  `content` longtext COLLATE utf8_bin COMMENT '正文内容，富文本',
  `create_time` datetime DEFAULT NULL COMMENT '添加时间',
  `end_time` datetime DEFAULT NULL COMMENT '终止时间',
  `source` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '来源',
  `keywords` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '文章关键字',
  `type` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '0通知 1公告 2新闻',
  `attachment` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '对应附件id，多个用逗号隔开',
  `update_time` datetime DEFAULT NULL COMMENT '文章最后修改时间',
  `sort` decimal(10,0) DEFAULT NULL COMMENT '文章排序号',
  `top` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '是否置顶 0 否 1 是',
  `description` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '文章描述',
  `image_ids` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '保存正文中使用的所有图片id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;



CREATE TABLE `sc_gmuni_schedule` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `course_code` varchar(32) DEFAULT NULL COMMENT '课程编码',
  `class_code` varchar(32) DEFAULT NULL COMMENT '所属班级',
  `day` date DEFAULT NULL COMMENT '具体日期',
  `classroom` varchar(32) DEFAULT NULL COMMENT '教室',
  `teacher` varchar(32) DEFAULT NULL COMMENT '教师',
  `week` varchar(32) DEFAULT NULL COMMENT '第几周',
  `course_time_id` varchar(100) DEFAULT NULL COMMENT '学年学期id',
  `target_nc` varchar(50) DEFAULT NULL COMMENT '目标节数',
  `sunday` varchar(50) DEFAULT NULL COMMENT '星期几',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `sc_gmuni_schedule_nc` (
  `id` varchar(150) DEFAULT NULL,
  `sch_id` varchar(150) DEFAULT NULL,
  `nc` int(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `sc_gmuni_student` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `name` varchar(200) DEFAULT NULL COMMENT '姓名',
  `code` varchar(200) DEFAULT NULL COMMENT '学生学号',
  `dept_id` varchar(200) DEFAULT NULL COMMENT '院系编码',
  `subject_id` varchar(200) DEFAULT NULL COMMENT '专业编码',
  `class_id` varchar(200) DEFAULT NULL COMMENT '班级编码',
  `sex` varchar(50) DEFAULT NULL COMMENT '性别',
  `status` varchar(10) DEFAULT NULL COMMENT '学生状态(0 毕业，1在读，2休学)',
  `nation` varchar(200) DEFAULT NULL COMMENT '民族',
  `way` varchar(200) DEFAULT NULL COMMENT '入学方式(0 统招 1体育特招 2艺术特招)',
  `lc` varchar(200) DEFAULT NULL COMMENT '学制(四年制本科)',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `identity` varchar(200) DEFAULT NULL COMMENT '身份证号',
  `e_time` date DEFAULT NULL COMMENT '入学时间',
  `g_time` date DEFAULT NULL COMMENT '毕业时间',
  `address` varchar(200) DEFAULT NULL COMMENT '家庭住址',
  `home_phone` varchar(200) DEFAULT NULL COMMENT '家庭电话',
  `phone` varchar(200) DEFAULT NULL COMMENT '个人电话',
  `email` varchar(200) DEFAULT NULL COMMENT '个人邮箱',
  `politics_status` varchar(200) DEFAULT NULL COMMENT '政治面貌',
  `other_party` varchar(200) DEFAULT NULL COMMENT '其他党派',
  `g_school` varchar(200) DEFAULT NULL COMMENT '毕业学校',
  `type` varchar(200) DEFAULT NULL COMMENT '学校形式(脱产，业务，全日制，非全日制)',
  `category_type` varchar(200) DEFAULT NULL COMMENT '类别形式(普通考生，特长类考生)',
  `e_type` varchar(200) DEFAULT NULL COMMENT '招生类型(春季招生，秋季招生)',
  `hkm` varchar(10) DEFAULT NULL COMMENT '是否港澳台侨外(0：否 1 是 默认0)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `sc_gmuni_student_family` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `name` varchar(200) DEFAULT NULL COMMENT '姓名',
  `relation` varchar(50) DEFAULT NULL COMMENT '关系',
  `address` varchar(300) DEFAULT NULL COMMENT '家庭住址',
  `telephone` varchar(100) DEFAULT NULL COMMENT '联系方式',
  `work_unit` varchar(300) DEFAULT NULL COMMENT '工作单位',
  `student_id` varchar(200) NOT NULL COMMENT '学生id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `sc_gmuni_student_resume` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `school` varchar(300) DEFAULT NULL COMMENT '学校',
  `e_time` date DEFAULT NULL COMMENT '入学时间',
  `g_time` date DEFAULT NULL COMMENT '毕业时间',
  `voucher` varchar(300) DEFAULT NULL COMMENT '证明人',
  `telephone` varchar(100) DEFAULT NULL COMMENT '联系方式',
  `student_id` varchar(200) NOT NULL COMMENT '学生id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `sc_gmuni_update_click_through` (
  `id` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '主键',
  `article_id` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '文章id',
  `click_through` decimal(10,0) DEFAULT NULL COMMENT '点击次数',
  `type` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '类别',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/**添加索引**/
ALTER TABLE `sc_gmuni_news` ADD INDEX news_index (`type`,`update_time`,`image_ids` ) ;
ALTER TABLE `sc_gmuni_notice` ADD INDEX notice_index (`type`,`update_time`,`attachment` ) ;
ALTER TABLE `sc_gmuni_update_click_through` ADD INDEX click_index (`type`,`article_id` ) ;

insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('aa21b33ef4634045939da8f187c91ccf','消息管理','1','/smartschool/message','','5c272d01bf0a45d8b6fe61107e81ad83','xxgl',NULL,'2018-11-20 10:42:18','1','8');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('b44487b0c71a41cc8b96267932a8f97a','新增','2','','add','aa21b33ef4634045939da8f187c91ccf','messageAddBtn',NULL,'2018-11-20 11:55:12','1','1');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('d48212eddce747c08ad6b5d7974f81eb','详情','2','','view','aa21b33ef4634045939da8f187c91ccf','messageViewBtn',NULL,'2018-11-20 11:56:27','1','2');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('92620c043191417e88bc26079415f5b1','删除','2','','remove','aa21b33ef4634045939da8f187c91ccf','messageRemoveBtn',NULL,'2018-11-20 11:56:49','1','3');
insert into `pf_gmuni_resource` (`id`, `name`, `type`, `url`, `button_code`, `parent_id`, `code`, `parent_code`, `create_time`, `status`, `sort`) values('0dc10f74ec1f4e66b712d577ed90bff5','重新发送','2','','again','aa21b33ef4634045939da8f187c91ccf','messageAgainBtn',NULL,'2018-11-20 11:58:10','1','4');

CREATE TABLE `sc_gmuni_stu_location` (
  `id` varchar(200) NOT NULL,
  `user_info` varchar(200) DEFAULT NULL COMMENT '用户',
  `school` varchar(200) DEFAULT NULL COMMENT '学校',
  `student_id` varchar(200) DEFAULT NULL COMMENT '学号',
  `gprs_time` varchar(200) DEFAULT NULL COMMENT '定位时间',
  `longitude` varchar(1000) DEFAULT NULL COMMENT '经度',
  `latitude` varchar(1000) DEFAULT NULL COMMENT '纬度',
  `altitude` varchar(1000) DEFAULT NULL COMMENT '海拔',
  `gprs_city` varchar(1000) DEFAULT NULL COMMENT '城市',
  `gprs_address` varchar(1000) DEFAULT NULL COMMENT '地址',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `stu_sign_time` varchar(200) DEFAULT NULL COMMENT '学生签到时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;