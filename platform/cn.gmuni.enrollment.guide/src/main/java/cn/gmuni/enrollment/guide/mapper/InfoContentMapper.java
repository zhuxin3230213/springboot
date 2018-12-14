package cn.gmuni.enrollment.guide.mapper;

import cn.gmuni.enrollment.guide.model.ClickThrough;
import cn.gmuni.maintenance.model.InfoContent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface InfoContentMapper {
    List<InfoContent> listContents(Map<String, Object> params);

    int saveInfo(InfoContent info);

    int updateInfo(InfoContent info);

    int deleteByIds(List<String> ids);
    int saveClickThrough(ClickThrough ct);

    int deleteEnrollmentPlan(List<String> ids);
}
