package cn.gmuni.enrollment.homepage.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface SlideShowMapper {

    List<Map<String,Object>> list();

    List<Map<String, Object>> list(@Param(value = "id") String id);

    List<Map<String, Object>> listArticle(Map<String, Object> params);

    int save(Map<String, String> params);

    int update(Map<String, String> params);

    int delete(Map<String, String> params);

    int move(Map<String, String> params);
}
