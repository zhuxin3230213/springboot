package cn.gmuni.enrollment.guide.service.impl;

import cn.gmuni.enrollment.guide.mapper.InfoContentMapper;
import cn.gmuni.enrollment.guide.model.ClickThrough;
import cn.gmuni.enrollment.guide.service.IInfoContentService;
import cn.gmuni.maintenance.model.InfoContent;
import cn.gmuni.utils.IdGenerator;
import cn.gmuni.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class InfoContentServiceImpl implements IInfoContentService {
    @Autowired
    InfoContentMapper mapper;

    @Override
    public PageInfo<InfoContent> listContents(Map<String, String> params) {
        PageUtils page = PageUtils.getPage(params);
        Map<String, Object> queryPara = new HashMap<>(params);
        PageHelper.startPage(page.getPage(), page.getSize());
        return new PageInfo<>(mapper.listContents(queryPara));
    }

    @Override
    public InfoContent saveOrUpDateContent(InfoContent info) {
        Date currentTime = new Date();
        if (StringUtils.isEmpty(info.getId())) {
            String id = IdGenerator.getId();
            info.setId(id);
            info.setCreateTime(currentTime);
            info.setUpdateTime(currentTime);
            int i = mapper.saveInfo(info);
            if(i > 0){
                ClickThrough ct = new ClickThrough();
                ct.setId(IdGenerator.getId());
                ct.setArticleId(id);
                ct.setClickThrough(0);
                ct.setType("0");
                mapper.saveClickThrough(ct);
            }
        } else {
            info.setUpdateTime(currentTime);
            mapper.updateInfo(info);
        }
        return info;
    }

    @Override
    public Map<String, Object> delInfoByIds(Map<String, String> params) {
        Map<String, Object> res = new HashMap<>();
        List<String> ids = Arrays.asList(params.get("ids").split(","));
        res.put("flag", mapper.deleteByIds(ids) > 0 ? true : false);
        return res;
    }

    @Override
    public List<InfoContent> listInfoByModuleId(Map<String, Object> params) {
        return mapper.listContents(params);
    }
}
