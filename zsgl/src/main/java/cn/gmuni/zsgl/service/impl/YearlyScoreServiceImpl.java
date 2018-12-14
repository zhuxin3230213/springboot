package cn.gmuni.zsgl.service.impl;

import cn.gmuni.zsgl.dao.YearlyScoreRepository;
import cn.gmuni.zsgl.service.YearlyScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * @Author:ZhuXin
 * @Description: 历年分数业务类
 * @Date:Create in 15:52 2018/6/8
 * @Modified By:
 **/
@Transactional
@Service
public class YearlyScoreServiceImpl implements YearlyScoreService {


    @Autowired
    YearlyScoreRepository yearlyScoreRepository;

    /**
     * 获取历年分数列表
     *
     * @return
     */
    @Override
    public List<Map> listYearlyScores() {
        return yearlyScoreRepository.listYearlyScores();
    }

    /**
     * 根据年份获取分数列表
     *
     * @param year
     * @return
     */
    @Override
    public List<Map> listByYear(Integer year) {
        return yearlyScoreRepository.listYearlyScoreByYear(year);
    }

    /**
     * 根据省份获取分数列表
     *
     * @param provinces
     * @return
     */
    @Override
    public List<Map> listByProvinces(String provinces) {
        return yearlyScoreRepository.listYearlyScoreByProvinces(provinces);
    }

    /**
     * 根据年份和省份获取分数列表
     *
     * @param year
     * @param provinces
     * @return
     */
    @Override
    public List<Map> listByYearAndProvinces(Integer year, String provinces) {
        return   yearlyScoreRepository.listByYearAndProvinces(year, provinces);

    }

    /**
     * 根据id获取历年分数
     *
     * @param ysId
     * @return
     */
    @Override
    public Map getYearlyScoreById(String ysId) {
        return yearlyScoreRepository.getById(ysId);
    }
}
