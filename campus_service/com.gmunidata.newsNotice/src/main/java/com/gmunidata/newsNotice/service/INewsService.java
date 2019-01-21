package com.gmunidata.newsNotice.service;

import com.gmunidata.base.response.Content;
import com.gmunidata.newsNotice.model.News;

import java.util.List;
import java.util.Map;


public interface INewsService {

    //新增或修改
     Content addOrUpdate(News news);

     //删除
     Content del(String ids);

     //查询
     List<News> list(Map<String, Object> params);

     //app查询列表
     List<Map<String,Object>> listNews(Map<String, String> params);

     //sc_service中间层数据获取
     Map<String,Object> newById(String id);

     //下拉刷新顶部添加最新数据
     List<Map<String,Object>> addNewsTop(Map<String,String> params);
     //上拉加载添加以前旧数据
     List<Map<String,Object>> addNewsBottom(Map<String,String> params);
     //获取列表最后一条数据
     Map<String,Object> lastNew(Map<String,String> params);
}
