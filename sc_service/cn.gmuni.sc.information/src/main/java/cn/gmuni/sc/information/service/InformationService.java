package cn.gmuni.sc.information.service;

import java.util.Map;

public interface InformationService {

    //根据学生学号与学校编码获取用户登录信息
    Map<String,Object>  findIndentifier(Map<String,Object> params);
}
