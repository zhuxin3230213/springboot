package cn.gmuni.enrollment.guide.service;

import cn.gmuni.maintenance.model.InfoContent;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface IInfoContentService {
    PageInfo<InfoContent> listContents(Map<String, String> params);

    InfoContent saveOrUpDateContent(InfoContent info);

    Map<String, Object> delInfoByIds(Map<String, String> params);

    List<InfoContent> listInfoByModuleId(Map<String, Object> params);
}
