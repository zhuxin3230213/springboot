package cn.gmuni.sc.college.mapper;

import cn.gmuni.sc.college.model.College;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CollegeMapper {

    List<College> listAll();

    //新增学校
    int addSchool(College college);

    //修改学校信息
    int updateSchool(College college);

    //删除学校信息
    int delSchool(List<String> ids);

    //查询学校信息
    List<College> listSchool(Map<String,Object> params);

    //查询所有学校信息
    List<Map<String,String>> getSchool();

    //校验名称和编码
    int checkNameAndCode(College college);
}
