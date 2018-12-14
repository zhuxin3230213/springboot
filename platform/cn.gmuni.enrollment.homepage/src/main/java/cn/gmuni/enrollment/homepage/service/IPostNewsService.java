package cn.gmuni.enrollment.homepage.service;

import cn.gmuni.enrollment.homepage.model.PostNews;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface IPostNewsService {
     PageInfo<PostNews> listContents(Map<String, String> params);

     PostNews saveOrUpDateContent(PostNews info);

     Map<String, Object> delInfoByIds(Map<String, String> params);
}
