package com.gmunidata.newsNotice.service.impl;

import cn.gmuni.utils.IdGenerator;
import cn.gmuni.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.gmunidata.base.response.BaseResponse;
import com.gmunidata.base.response.Content;
import com.gmunidata.newsNotice.mapper.NewsMapper;
import com.gmunidata.newsNotice.model.Click;
import com.gmunidata.newsNotice.model.News;
import com.gmunidata.newsNotice.service.INewsService;
import com.gmunidata.push.constants.WebScoketFlagConst;
import com.gmunidata.push.model.WebSocketModel;
import com.gmunidata.push.server.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class NewsServiceImp implements INewsService {

    @Autowired
    NewsMapper newsMapper;

    @Override
    public Content addOrUpdate(News news) {
        Content con = new Content();
        //判断是否存在id有则修改，无则添加
        if (news.getId() == null || "".equals(news.getId())) {
            news.setId(IdGenerator.getId());
            Date date = new Date();
            news.setCreateTime(date);
            news.setUpdateTime(date);
            news.setType("0");
            int f = newsMapper.add(news) == 1 ? 0 : 1;
            if (f == 0) {
                Click click = new Click(IdGenerator.getId(), news.getId(), news.getType());
                newsMapper.addClick(click);
            }
            con.setFlag(f);
            if (f == 0) {
                con.setMessage("添加成功！");
                con.setContent(news);
                //推送到中转层
                messagePush(news);
            } else {
                con.setMessage("添加失败！");
            }

        } else {
            news.setUpdateTime(new Date());
            int f = newsMapper.update(news) == 1 ? 0 : 1;
            con.setFlag(f);
            if (f == 0) {
                con.setMessage("修改成功！");
                con.setContent(news);
                //推送到中转层
                  messagePush(news);
            } else {
                con.setMessage("修改失败！");
            }

        }
        return con;
    }

    @Override
    public Content del(String ids) {
        Content con = new Content();
        List<String> lis = Arrays.asList(ids.split(","));
        int f = newsMapper.del(lis) > 0 ? 0 : 1;
        con.setFlag(f);
        if (f == 0) {
            con.setMessage("删除成功！");
        } else {
            con.setMessage("删除失败！");
        }
        return con;
    }

    @Override
    public List<News> list(Map<String, Object> params) {
        PageUtils page = PageUtils.getPage(params);
        PageHelper.startPage(page.getPage(), page.getSize());
        return newsMapper.list(params);
    }

    //app查询列表
    @Override
    public List<Map<String, Object>> listNews(Map<String, String> params) {
        List<Map<String, Object>> list = new ArrayList<>();
        PageHelper.startPage(Integer.parseInt(params.get("currentPage")), Integer.parseInt(params.get("pageSize")));
         list = newsMapper.listNews();

        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("status","1");
        Map<String, Object> lastNew = newsMapper.lastNew(queryParam);
        if (list.size() != 0){
            list.add(lastNew);
        }else {
            list.add(lastNew);
        }
        return list;
    }

    //sc_service中间层数据获取
    @Override
    public Map<String, Object> newById(String id) {
        return newsMapper.newById(id);
    }

    @Override
    public List<Map<String, Object>> addNewsTop(Map<String, String> params) {
        PageHelper.startPage(Integer.parseInt(params.get("currentPage")),Integer.parseInt(params.get("pageSize")));
        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("initDataFirstUpdateTime", params.get("initDataFirstUpdateTime"));
        return newsMapper.listNewsByInitDataFirstUpdateTime(queryParam);
    }

    @Override
    public List<Map<String, Object>> addNewsBottom(Map<String, String> params) {
        PageHelper.startPage(Integer.parseInt(params.get("currentPage")), Integer.parseInt(params.get("pageSize")));
        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("initDataLastUpdateTime", params.get("initDataLastUpdateTime"));
        return newsMapper.listNewsByInitDataLastUpdateTime(queryParam);
    }

    @Override
    public Map<String, Object> lastNew(Map<String, String> params) {
        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("status",params.get("status"));
        return newsMapper.lastNew(queryParam);
    }

    //推送给中转层
    public void messagePush(News news){
        if ("1".equals(news.getStatus())){
        try {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("id",news.getId());
            map.put("title",news.getTitle());
            if (StringUtils.isEmpty(news.getDescription())){
                map.put("description","");
            }else {
                map.put("description",news.getDescription());
            }
            if (StringUtils.isEmpty(news.getCover())){
                map.put("cover","");
            }else {
                map.put("cover",news.getCover());
            }
            map.put("deptName",news.getDeptName());
            WebSocketServer.sendInfo(new WebSocketModel(WebScoketFlagConst.NEWS_FLAG, map));
        } catch (Exception e) {
            e.printStackTrace();
        }
     }
    }

}
