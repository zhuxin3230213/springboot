package cn.gmuni.sc.college.service.impl;

import cn.gmuni.sc.base.response.Content;
import cn.gmuni.sc.college.cache.CollegeCache;
import cn.gmuni.sc.college.mapper.CollegeMapper;
import cn.gmuni.sc.college.model.College;
import cn.gmuni.sc.college.service.ICollegeService;
import cn.gmuni.utils.IdGenerator;
import cn.gmuni.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CollegeService implements ICollegeService {

    @Autowired
    private CollegeMapper collegeMapper;

    @Override
    public List<College> listAll() {
        return collegeMapper.listAll();
    }

    @Override
    public Content addSchool(College college) {
        Content con = new Content();
        //查询是否存在重复名称或编码
        int size = collegeMapper.checkNameAndCode(college);
        if (size != 0){
            con.setFlag(1);
            con.setMessage("名称或编码和已有的学校的名称或编码重复！");
            return con;
        }
        college.setId(IdGenerator.getId());
        college.setCreateTime(new Date());
        int f = collegeMapper.addSchool(college) == 1 ? 0 : 1;
        con.setFlag(f);
        if (f == 0){

            CollegeCache.addCollege(college);
            con.setMessage("添加成功！");
            con.setContent(college);
        }else {
            con.setMessage("添加失败！");
        }
        return con;
    }

    @Override
    public Content updateSchool(College college) {
        Content con = new Content();
        //查询是否存在重复名称或编码
        int size = collegeMapper.checkNameAndCode(college);
        if (size != 0){
            con.setFlag(1);
            con.setMessage("名称或编码和已有的学校的名称或编码重复！");
            return con;
        }
        int f =collegeMapper.updateSchool(college)==1?0:1;
        con.setFlag(f);
        if (f == 0){
            CollegeCache.updateCollege(college);
            con.setMessage("编辑成功！");
            con.setContent(college);
        }else {
            con.setMessage("编辑失败！");
        }
        return con;
    }

    @Override
    public Content deslSchool(List<String> ids) {
        Content con = new Content();
        int f = collegeMapper.delSchool(ids) > 0 ? 0 : 1;
        con.setFlag(f);
        if (f == 0){
            CollegeCache.delCollege(ids);
            con.setMessage("删除成功！");
        }else {
            con.setMessage("删除失败！");
        }
        return con;
    }

    @Override
    public List<College> listSchool(Map<String, Object> params) {
        PageUtils page = PageUtils.getPage(params);
        PageHelper.startPage(page.getPage(),page.getSize());
        return collegeMapper.listSchool(params);
    }

    @Override
    public List<Map<String, String>> getSchool() {
        return collegeMapper.getSchool();
    }
}
