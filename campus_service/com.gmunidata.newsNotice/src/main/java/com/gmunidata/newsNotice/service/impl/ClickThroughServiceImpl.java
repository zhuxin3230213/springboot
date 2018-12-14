package com.gmunidata.newsNotice.service.impl;

import com.gmunidata.newsNotice.mapper.ClickThroughMapper;
import com.gmunidata.newsNotice.service.IClickThroughService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author: zhuxin
 * @Date: 2018/9/6 11:31
 * @Description:
 */
@Service
public class ClickThroughServiceImpl implements IClickThroughService {

    @Autowired
    ClickThroughMapper clickThroughMapper;
    @Override
    public Map<String, Object> updateClickThrough(Map<String, Object> params) {
        clickThroughMapper.updateClickThrough(params);
        return clickThroughMapper.getClickByArticle(params);
    }
}
