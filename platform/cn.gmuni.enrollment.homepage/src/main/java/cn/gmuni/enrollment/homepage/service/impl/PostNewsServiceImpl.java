package cn.gmuni.enrollment.homepage.service.impl;

import cn.gmuni.enrollment.homepage.mapper.PostNewsMapper;
import cn.gmuni.enrollment.homepage.model.ClickThrough;
import cn.gmuni.enrollment.homepage.model.PostNews;
import cn.gmuni.enrollment.homepage.service.IPostNewsService;
import cn.gmuni.utils.IdGenerator;
import cn.gmuni.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class PostNewsServiceImpl implements IPostNewsService {
    @Autowired
    PostNewsMapper mapper;

    @Override
    public PageInfo<PostNews> listContents(Map<String, String> params) {
        PageUtils page = PageUtils.getPage(params);
        Map<String, Object> queryPara = new HashMap<>(params);
        PageHelper.startPage(page.getPage(), page.getSize());
        return new PageInfo<>(mapper.listContents(queryPara));
    }

    @Override
    public PostNews saveOrUpDateContent(PostNews news) {
        Date currentTime = new Date();
        if (StringUtils.isEmpty(news.getId())) {
            String id = IdGenerator.getId();
            news.setId(id);
            news.setUpdateTime(currentTime);
            news.setCreateTime(currentTime);
            Calendar c = Calendar.getInstance();
            c.add(Calendar.YEAR, 10);
            news.setEndTime(c.getTime());
            mapper.saveInfo(news);
            ClickThrough ct = new ClickThrough();
            ct.setId(IdGenerator.getId());
            ct.setArticleId(id);
            ct.setClickThrough(0);
            ct.setType("1");
            mapper.saveClickThrough(ct);
        } else {
            news.setUpdateTime(currentTime);
            if(news.getEndTime()==null){
                Calendar c = Calendar.getInstance();
                c.add(Calendar.YEAR, 10);
                news.setEndTime(c.getTime());
            }
            mapper.updateInfo(news);
        }
        return news;
    }

    @Override
    public Map<String, Object> delInfoByIds(Map<String, String> params) {
        Map<String, Object> res = new HashMap<>();
        List<String> ids = Arrays.asList(params.get("ids").split(","));
        res.put("flag", mapper.deleteByIds(ids) > 0 ? true : false);
        return res;
    }
}
