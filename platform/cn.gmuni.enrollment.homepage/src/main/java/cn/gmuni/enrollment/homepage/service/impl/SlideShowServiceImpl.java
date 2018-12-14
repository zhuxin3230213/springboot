package cn.gmuni.enrollment.homepage.service.impl;

import cn.gmuni.enrollment.homepage.mapper.SlideShowMapper;
import cn.gmuni.enrollment.homepage.service.ISlideShowService;
import cn.gmuni.utils.IdGenerator;
import cn.gmuni.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SlideShowServiceImpl implements ISlideShowService {
    @Autowired
    SlideShowMapper mapper;

    @Override
    public List<Map<String, Object>> list() {
        return mapper.list();
    }

    @Override
    public PageInfo<Map<String, Object>> listArticle(Map<String, String> params) {
        PageUtils page = PageUtils.getPage(params);
        Map<String, Object> queryPara = new HashMap<>();
        queryPara.put("module", params.get("module"));
        queryPara.put("selectedId", params.get("selectedId"));
        PageHelper.startPage(page.getPage(), page.getSize());
        return new PageInfo<>(mapper.listArticle(queryPara));
    }

    @Override
    public Map<String, Object> save(Map<String, String> params) {
        params.put("id", IdGenerator.getId());
        if(mapper.save(params) > 0){
            return mapper.list(params.get("id")).get(0);
        }else{
            return null;
        }

    }

    @Override
    public Map<String, Object> update(Map<String, String> params) {
        if(mapper.update(params) > 0){
            return mapper.list(params.get("id")).get(0);
        }else{
            return null;
        }
    }

    @Override
    public Map<String, Object> delete(Map<String, String> params) {
        Map<String, Object> res = new HashMap<>();
        res.put("flag", mapper.delete(params) > 0 );
        return res;
    }

    @Override
    public Map<String, Object> move(Map<String, String> params) {
        Map<String, Object> res = new HashMap<>();
        res.put("flag", mapper.move(params) > 0);
        return res;
    }
}
