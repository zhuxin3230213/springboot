package com.gmunidata.newsNotice.service;

import com.gmunidata.base.response.Content;
import com.gmunidata.newsNotice.model.Info;

import java.util.*;

public interface IMessageService {

    //获取消息
    List<Map<String,Object>> getListInfo(Map<String,Object> params);

    //添加消息
    Content addInfo(Info info);

    //删除消息
    Content delInfo(List<String> list);

    Content updateStatus(Info info);




}
