package com.gmunidata.newsNotice.mapper;

import com.gmunidata.newsNotice.model.Click;
import com.gmunidata.newsNotice.model.News;
import com.gmunidata.newsNotice.model.Notice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public interface NoticeMapper {

    int add(Notice notice);

    int update(Notice notice);

    int addClick(Click click);

    int del(List<String> ids);

    List<Notice> list(Map<String, Object> params);
    //app数据渲染
    List<Map<String,Object>> listNotice();
   //sc_service层获取通知对象
    Map<String,Object> noticeById(String id);
    //查询正文
    String selectContent(String id);
    //发布撤销
    int updateStatus(Map<String,Object> map);
    //下拉刷新根据当前列表最大时间向以后时间查询
    List<Map<String,Object>> listNoticeByInitDataFirstUpdateTime(Map<String,String> params);
    //上拉加载根据当前列表最小时间向以前时间查询
    List<Map<String,Object>> listNoticeByInitDataLastUpdateTime(Map<String,String> params);
    //根据更新时间获取发布后列表的最后一条数据
    Map<String,Object> lastNotice(Map<String,String> params);
}
