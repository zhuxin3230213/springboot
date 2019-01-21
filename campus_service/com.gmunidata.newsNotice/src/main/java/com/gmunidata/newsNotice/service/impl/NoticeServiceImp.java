package com.gmunidata.newsNotice.service.impl;

import cn.gmuni.utils.IdGenerator;
import cn.gmuni.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.gmunidata.base.response.BaseResponse;
import com.gmunidata.base.response.Content;
import com.gmunidata.newsNotice.mapper.NoticeMapper;
import com.gmunidata.newsNotice.model.Click;
import com.gmunidata.newsNotice.model.News;
import com.gmunidata.newsNotice.model.Notice;
import com.gmunidata.newsNotice.service.INoticeService;
import com.gmunidata.push.constants.WebScoketFlagConst;
import com.gmunidata.push.model.WebSocketModel;
import com.gmunidata.push.server.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class NoticeServiceImp implements INoticeService {

    @Autowired
    NoticeMapper noticeMapper;

    @Override
    public Content addOrUpdate(Notice notice) {
        Content con = new Content();
        //判断是否存在id有则修改，无则添加
        if (notice.getId() == null || "".equals(notice.getId())) {
            notice.setId(IdGenerator.getId());
            Date date = new Date();
            notice.setCreateTime(date);
            notice.setUpdateTime(date);
            notice.setType("1");
            int f = noticeMapper.add(notice) == 1 ? 0 : 1;
            if (f == 0) {
                Click click = new Click(IdGenerator.getId(), notice.getId(), notice.getType());
                noticeMapper.addClick(click);
            }
            con.setFlag(f);
            if (f == 0) {
                con.setMessage("添加成功！");
                con.setContent(notice);
                messagePush(notice);
            } else {
                con.setMessage("添加失败！");
            }

        } else {
            notice.setUpdateTime(new Date());
            int f = noticeMapper.update(notice) == 1 ? 0 : 1;
            con.setFlag(f);
            if (f == 0) {
                con.setMessage("修改成功！");
                con.setContent(notice);
                    messagePush(notice);
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
        int f = noticeMapper.del(lis) > 0 ? 0 : 1;
        con.setFlag(f);
        if (f == 0) {
            con.setMessage("删除成功！");
        } else {
            con.setMessage("删除失败！");
        }
        return con;
    }

    @Override
    public List<Notice> list(Map<String, Object> params) {
        PageUtils page = PageUtils.getPage(params);
        PageHelper.startPage(page.getPage(), page.getSize());
        return noticeMapper.list(params);
    }

    //app查询列表
    @Override
    public List<Map<String, Object>> listNotice(Map<String, String> params) {
        List<Map<String, Object>> list = new ArrayList<>();
        PageHelper.startPage(Integer.parseInt(params.get("currentPage")), Integer.parseInt(params.get("pageSize")));

        list = noticeMapper.listNotice();
        //获取最后一条数据id
        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("status","1");
        Map<String, Object> lastNotice = noticeMapper.lastNotice(queryParam);

        if (list.size() != 0){
            list.add(lastNotice);
        }else {
            list.add(lastNotice);
        }
        return list;
    }

    //sc_service中间层数据获取
    @Override
    public Map<String, Object> noticeById(String id) {
        return noticeMapper.noticeById(id);
    }

    @Override
    public List<Map<String, Object>> addNoticeTop(Map<String, String> params) {
        PageHelper.startPage(Integer.parseInt(params.get("currentPage")), Integer.parseInt(params.get("pageSize")));
        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("initDataFirstUpdateTime", params.get("initDataFirstUpdateTime"));
        return noticeMapper.listNoticeByInitDataFirstUpdateTime(queryParam);
    }

    @Override
    public List<Map<String, Object>> addNoticeBottom(Map<String, String> params) {
        PageHelper.startPage(Integer.parseInt(params.get("currentPage")), Integer.parseInt(params.get("pageSize")));
        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("initDataLastUpdateTime", params.get("initDataLastUpdateTime"));
        return noticeMapper.listNoticeByInitDataLastUpdateTime(queryParam);
    }

    @Override
    public Map<String, Object> lastNotice(Map<String, String> params) {
        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("status",params.get("status"));
        return noticeMapper.lastNotice(queryParam);
    }

    //推送给中转层
    public void messagePush(Notice notice) {
        if ("1".equals(notice.getStatus())) {
            try {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("id", notice.getId());
                map.put("title", notice.getTitle());
                if (StringUtils.isEmpty(notice.getDescription())) {
                    map.put("description", "");
                } else {
                    map.put("description", notice.getDescription());
                }
                map.put("deptName",notice.getDeptName());
                WebSocketServer.sendInfo(new WebSocketModel(WebScoketFlagConst.NOTICE_FLAG, map));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
