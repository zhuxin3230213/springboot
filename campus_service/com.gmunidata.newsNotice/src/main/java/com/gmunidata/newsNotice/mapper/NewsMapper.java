package com.gmunidata.newsNotice.mapper;

import com.gmunidata.newsNotice.model.Click;
import com.gmunidata.newsNotice.model.News;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public interface NewsMapper {

    int add(News news);

    int update(News news);

    int addClick(Click click);

    int del(List<String> ids);

    List<News> list(Map<String, Object> params);
    //查询正文
    String selectContent(String id);
    //发布撤销
    int updateStatus(Map<String,Object> map);

    //app数据渲染
    List<Map<String,Object>> listNews();
    //sc_service中间层数据获取
    Map<String,Object>  newById(String id);
    //下拉刷新根据当前列表最大时间向以后时间查询
    List<Map<String,Object>> listNewsByInitDataFirstUpdateTime(Map<String,String> params);
    //上拉加载根据当前列表最小时间向以前时间查询
    List<Map<String,Object>> listNewsByInitDataLastUpdateTime(Map<String,String> params);
    //根据更新时间获取发布后列表的最后一条数据
    Map<String,Object> lastNew(Map<String,String> params);
 }
