package cn.gmuni.enrollment.homepage.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface ISlideShowService {
    public List<Map<String, Object>> list();

    public PageInfo<Map<String, Object>> listArticle(Map<String, String> params);

    public Map<String, Object> save(Map<String, String> params);

    public Map<String, Object> update(Map<String, String> params);

    public Map<String, Object> delete(Map<String, String> params);

    public Map<String, Object> move(Map<String, String> params);

}
