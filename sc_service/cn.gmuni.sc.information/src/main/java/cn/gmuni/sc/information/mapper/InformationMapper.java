package cn.gmuni.sc.information.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Mapper
public interface InformationMapper {
    //根据学生学号与学校编码查询登录用户
    Map<String,Object> findUserInfoByStudentIdAndSchool(Map<String,Object> params);
}
