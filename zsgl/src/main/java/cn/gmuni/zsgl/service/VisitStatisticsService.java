package cn.gmuni.zsgl.service;

import cn.gmuni.zsgl.entity.VisitStatistics;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author:ZhuXin
 * @Description: 页面访问业务接口
 * @Date:Create in 16:48 2018/6/12
 * @Modified By:
 **/


public interface VisitStatisticsService {


    /**
     * 新增访问对象
     *
     * @param request
     * @param pageName
     * @param module
     * @return
     */
    VisitStatistics add(HttpServletRequest request, String pageName, String module);
}
