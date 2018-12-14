package cn.gmuni.zsgl.dao;

import cn.gmuni.zsgl.entity.VisitStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author:ZhuXin
 * @Description: 页面访问统计数据接口
 * @Date:Create in 16:46 2018/6/12
 * @Modified By:
 **/


public interface VisitStatisticsRepository extends JpaRepository<VisitStatistics, String> {
}
