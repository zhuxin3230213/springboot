package cn.gmuni.enrollment.homepage.mapper;

import cn.gmuni.enrollment.homepage.model.ClickThrough;
import cn.gmuni.enrollment.homepage.model.PostNews;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface PostNewsMapper {
    List<PostNews> listContents(Map<String, Object> params);

    int saveInfo(PostNews info);

    int updateInfo(PostNews info);

    int deleteByIds(List<String> ids);

    int saveClickThrough(ClickThrough ct);
}
