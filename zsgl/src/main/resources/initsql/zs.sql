
ALTER TABLE zs_gmuni_content ADD INDEX content_c_index (`cover`);
ALTER TABLE zs_gmuni_content ADD INDEX content_a_index (`attachment`);
ALTER TABLE zs_gmuni_content ADD INDEX content_index (`title`,`parent_code`);
ALTER TABLE zs_gmuni_content ADD INDEX content_k_index (`keywords`);

ALTER TABLE zs_gmuni_enrollment_plan ADD INDEX plan_index (`provinces`,`year`);

ALTER TABLE zs_gmuni_faculty_professional ADD INDEX fa_index (`type`,`status`);

ALTER TABLE zs_gmuni_famous_teachers_style ADD INDEX fast_index (`code`,`name`,`academic_title`,`honor`);


ALTER TABLE zs_gmuni_menu ADD INDEX menu_index (`name`,`code`,`status`);

ALTER TABLE zs_gmuni_post_news_regulations ADD INDEX pnr_index (`title`,`type`);
ALTER TABLE zs_gmuni_post_news_regulations ADD INDEX pnr_k_index (`keywords`);
ALTER TABLE zs_gmuni_post_news_regulations ADD INDEX pnr_c_index (`cover`);
ALTER TABLE zs_gmuni_post_news_regulations ADD INDEX pnr_a_index (`attachment`);


ALTER TABLE zs_gmuni_yearly_score ADD INDEX yealy_index (`provinces`,`year`);

