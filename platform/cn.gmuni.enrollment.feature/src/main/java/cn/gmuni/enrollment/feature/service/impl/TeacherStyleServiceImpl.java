package cn.gmuni.enrollment.feature.service.impl;

import cn.gmuni.enrollment.feature.mapper.TeacharStyleMapper;
import cn.gmuni.enrollment.feature.model.TeacherStyle;
import cn.gmuni.enrollment.feature.service.ITeacherStyleService;
import cn.gmuni.utils.IdGenerator;
import cn.gmuni.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TeacherStyleServiceImpl implements ITeacherStyleService {

    @Autowired
    private TeacharStyleMapper mapper;

    @Override
    public PageInfo<TeacherStyle> list(Map<String, String> params) {
        PageUtils page = PageUtils.getPage(params);
        PageHelper.startPage(page.getPage(), page.getSize());
        return new PageInfo<>(mapper.list(params));
    }

    @Override
    public TeacherStyle save(TeacherStyle ts) {
        ts.setId(IdGenerator.getId());
        ts.setCreateTime(new Date());
        return mapper.save(ts) > 0 ? ts : null;
    }

    @Override
    public TeacherStyle update(TeacherStyle ts) {
        return mapper.update(ts) > 0 ? ts : null;
    }

    @Override
    public Map<String, Object> delete(Map<String, String> params) {
        Map<String, Object> res = new HashMap<>();
        String idStr = params.get("ids");
        res.put("flag", mapper.delete(Arrays.asList(idStr.split(","))) > 0);
        return res;
    }
    @Override
    public Map<String, Object> checkCode(Map<String, String> params) {
        String code = params.get("code");
        Map<String, Object> res = new HashMap<>();
        res.put("flag", mapper.checkByCodeEqual(code) > 0 ? true : false);
        return res;
    }
}
