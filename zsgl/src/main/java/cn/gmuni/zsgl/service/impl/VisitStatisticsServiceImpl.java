package cn.gmuni.zsgl.service.impl;

import cn.gmuni.zsgl.dao.VisitStatisticsRepository;
import cn.gmuni.zsgl.entity.VisitStatistics;
import cn.gmuni.zsgl.service.VisitStatisticsService;
import cn.gmuni.zsgl.util.IpAdrressUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author:ZhuXin
 * @Description: 页面访问业务类
 * @Date:Create in 16:49 2018/6/12
 * @Modified By:
 **/
@Transactional
@Service
public class VisitStatisticsServiceImpl implements VisitStatisticsService {

    @Autowired
    VisitStatisticsRepository visitStatisticsRepository;

    /**
     * 新增访问对象
     *
     * @param request
     * @param pageName
     * @param module
     * @return
     */
    @Override
    public VisitStatistics add(HttpServletRequest request, String pageName, String module) {
        VisitStatistics vs = new VisitStatistics();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        vs.setHostIp(IpAdrressUtil.getIpAdrress(request));
        vs.setPageUrl(request.getRequestURI());
        vs.setTime(date);
        vs.setPageName(pageName);
        vs.setModule(module);
        return visitStatisticsRepository.save(vs);
    }
}
