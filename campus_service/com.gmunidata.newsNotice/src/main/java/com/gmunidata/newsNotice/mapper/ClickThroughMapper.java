package com.gmunidata.newsNotice.mapper;

import org.springframework.stereotype.Service;
import java.util.Map;

/**
 * @Author: zhuxin
 * @Date: 2018/9/6 11:09
 * @Description:
 */
@Service
public interface ClickThroughMapper {

    int updateClickThrough(Map<String, Object> params);

    Map<String,Object> getClickByArticle(Map<String, Object> params);
}
