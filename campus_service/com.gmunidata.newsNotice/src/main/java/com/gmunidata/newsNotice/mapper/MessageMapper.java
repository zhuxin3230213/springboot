package com.gmunidata.newsNotice.mapper;

import com.gmunidata.newsNotice.model.Info;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public interface MessageMapper {

 //获取消息
 List<Map<String,Object>> getListInfo(Map<String,Object> params);

 //添加消息
 int addInfo(Info info);

 //获取组成员编码
 List<String> getStudentCode(Info info);

 //校验创建消息的编码重复
 int checkCode(Info info);

  //获取老师
  Map<String,Object>  getReleaseUser(String releaseUser);

  //删除消息
 int delInfo(List<String> list);

 //修改状态
 int updateStatus(Map<String,Object> params);
 }
