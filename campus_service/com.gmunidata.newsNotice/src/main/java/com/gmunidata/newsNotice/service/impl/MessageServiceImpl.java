package com.gmunidata.newsNotice.service.impl;

import cn.gmuni.utils.IdGenerator;
import cn.gmuni.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.gmunidata.base.response.Content;
import com.gmunidata.newsNotice.mapper.MessageMapper;
import com.gmunidata.newsNotice.model.Info;
import com.gmunidata.newsNotice.service.IMessageService;
import com.gmunidata.push.constants.WebScoketFlagConst;
import com.gmunidata.push.model.WebSocketModel;
import com.gmunidata.push.server.WebSocketServer;
import com.gmunidata.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MessageServiceImpl implements IMessageService {

    @Autowired
    MessageMapper mapper;

    @Override
    public List<Map<String, Object>> getListInfo(Map<String, Object> params) {
        PageUtils page = PageUtils.getPage(params);
        PageHelper.startPage(page.getPage(), page.getSize());
        return mapper.getListInfo(params);
    }

    @Override
    public Content addInfo(Info info) {
        Content con = new Content();
        info.setId(IdGenerator.getId());
        info.setCreateTime(new Date());
        info.setStatus("已发送");
        int f = mapper.addInfo(info) == 1 ? 0 : 1;
        con.setFlag(f);
        if (f == 0) {
            con.setMessage("添加成功!");
            if (WebSocketServer.isClose() == false) {
                try {
                    messagePush(info);
                } catch (Exception e) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", info.getId());
                    map.put("status", "发送失败");
                    mapper.updateStatus(map);
                }
            } else {
                Map<String, Object> map = new HashMap<>();
                map.put("id", info.getId());
                map.put("status", "发送失败");
                mapper.updateStatus(map);
            }
        } else {
            con.setMessage("添加失败!");
        }
        return con;
    }

    @Override
    public Content delInfo(List<String> list) {
        Content con = new Content();
        int f = mapper.delInfo(list) == list.size() ? 0 : 1;
        con.setFlag(f);
        if (f == 0) {
            con.setMessage("删除成功！");
        } else {
            con.setMessage("删除失败！");
        }
        return con;
    }

    @Override
    public Content updateStatus(Info info) {
        Content con = new Content();
        if (WebSocketServer.isClose() == false) {
            try {
                messagePush(info);
            } catch (Exception e) {
                con.setFlag(1);
                con.setMessage("发送失败！");
                return con;
            }
        } else {
            con.setFlag(1);
            con.setMessage("链接异常！");
            return con;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("id", info.getId());
        map.put("status", "已发送");
        mapper.updateStatus(map);
        con.setFlag(0);
        con.setMessage("已发送");
        return con;
    }

    //推送给中转层
    public void messagePush(Info info) {
        List<String> codes = mapper.getStudentCode(info);
        Map userMap = mapper.getReleaseUser(info.getReleaseUser());
        try {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", info.getId());
            map.put("title", info.getTitle());
            map.put("content", info.getContent());
            map.put("createTime", DateUtils.date2String(info.getCreateTime(), DateUtils.COMMON_DATETIME));
            map.put("releaseUser", userMap.get("name"));
            map.put("deptName", userMap.get("deptName"));
            map.put("studentCodes", codes);
            WebSocketServer.sendInfo(new WebSocketModel(WebScoketFlagConst.INFORMATION_FLAG, map));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
