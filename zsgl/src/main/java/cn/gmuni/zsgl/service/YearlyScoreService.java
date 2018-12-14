package cn.gmuni.zsgl.service;

import java.util.List;
import java.util.Map;

/**
 * @Author:ZhuXin
 * @Description: 历年分数业务接口
 * @Date:Create in 15:51 2018/6/8
 * @Modified By:
 **/


public interface YearlyScoreService {

    /**
     * 获取历年分数列表
     *
     * @return
     */
    List<Map> listYearlyScores();

    /**
     * 根据年份获取分数列表
     *
     * @param year
     * @return
     */
    List<Map> listByYear(Integer year);

    /**
     * 根据省份获取分数列表
     *
     * @param provinces
     * @return
     */
    List<Map> listByProvinces(String provinces);

    /**
     * 根据年份和省份获取分数列表
     *
     * @param year
     * @param provinces
     * @return
     */
    List<Map> listByYearAndProvinces(Integer year, String provinces);

    /**
     * 根据id查询历年分数
     *
     * @param ysId
     * @return
     */
    Map getYearlyScoreById(String ysId);
}
