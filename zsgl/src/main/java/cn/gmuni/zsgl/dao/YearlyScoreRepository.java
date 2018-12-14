package cn.gmuni.zsgl.dao;

import cn.gmuni.zsgl.entity.YearlyScore;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * @Author:ZhuXin
 * @Description: 历年分数数据接口
 * @Date:Create in 15:50 2018/6/8
 * @Modified By:
 **/
public interface YearlyScoreRepository extends JpaRepository<YearlyScore, String> {


    /**
     * 获取历年分数列表
     *
     * @return
     */
    @Transactional
    @Query(nativeQuery = true,
            value = "SELECT ys.*, l.name AS `familyText`,\n" +
                    "lo.name AS `batchText` \n" +
                    "FROM zs_gmuni_yearly_score ys \n" +
                    "LEFT JOIN pf_gmuni_lookup l \n" +
                    "ON ys.family=l.code \n" +
                    "AND l.parent_Id = (SELECT id FROM pf_gmuni_lookup WHERE CODE = 'asclassify')\n" +
                    "LEFT JOIN pf_gmuni_lookup lo\n" +
                    "ON ys.batch=lo.code \n" +
                    "AND lo.parent_Id = (SELECT id FROM pf_gmuni_lookup WHERE CODE = 'batch') \n" +
                    "ORDER BY ys.sort+0")
    List<Map> listYearlyScores();


    /**
     * 根据年份获取历年分数列表
     *
     * @param year
     * @return
     */
    @Transactional
    @Query(nativeQuery = true,
            value = "SELECT ys.*, l.name AS `familyText`,\n" +
                    "lo.name AS `batchText` \n" +
                    "FROM zs_gmuni_yearly_score ys \n" +
                    "LEFT JOIN pf_gmuni_lookup l \n" +
                    "ON ys.family=l.code \n" +
                    "AND l.parent_Id = (SELECT id FROM pf_gmuni_lookup WHERE CODE = 'asclassify')\n" +
                    "LEFT JOIN pf_gmuni_lookup lo\n" +
                    "ON ys.batch=lo.code \n" +
                    "AND lo.parent_Id = (SELECT id FROM pf_gmuni_lookup WHERE CODE = 'batch') \n" +
                    "WHERE ys.year=:yea \n" +
                    "ORDER BY ys.sort+0")
    List<Map> listYearlyScoreByYear(@Param("yea") Integer year);


    /**
     * 根据省份获取历年分数列表
     *
     * @param provinces
     * @return
     */
    @Transactional
    @Query(nativeQuery = true,
            value = "SELECT ys.*, l.name AS `familyText`,\n" +
                    "lo.name AS `batchText` \n" +
                    "FROM zs_gmuni_yearly_score ys \n" +
                    "LEFT JOIN pf_gmuni_lookup l \n" +
                    "ON ys.family=l.code \n" +
                    "AND l.parent_Id = (SELECT id FROM pf_gmuni_lookup WHERE CODE = 'asclassify')\n" +
                    "LEFT JOIN pf_gmuni_lookup lo\n" +
                    "ON ys.batch=lo.code \n" +
                    "AND lo.parent_Id = (SELECT id FROM pf_gmuni_lookup WHERE CODE = 'batch') \n" +
                    "WHERE ys.provinces=:provinces \n" +
                    "ORDER BY ys.sort+0")
    List<Map> listYearlyScoreByProvinces(@Param("provinces") String provinces);

    /**
     * 根据年份和省份查询历年分数
     *
     * @param year
     * @param provinces
     * @return
     */
    @Transactional
    @Query(nativeQuery = true,
            value = "SELECT ys.*, l.name AS `familyText`,\n" +
                    "lo.name AS `batchText` \n" +
                    "FROM zs_gmuni_yearly_score ys \n" +
                    "LEFT JOIN pf_gmuni_lookup l \n" +
                    "ON ys.family=l.code \n" +
                    "AND l.parent_Id = (SELECT id FROM pf_gmuni_lookup WHERE CODE = 'asclassify')\n" +
                    "LEFT JOIN pf_gmuni_lookup lo\n" +
                    "ON ys.batch=lo.code \n" +
                    "AND lo.parent_Id = (SELECT id FROM pf_gmuni_lookup WHERE CODE = 'batch') \n" +
                    "WHERE ys.year=:yea \n" +
                    "AND ys.provinces=:provinces \n" +
                    "ORDER BY ys.sort+0")
    List<Map> listByYearAndProvinces(@Param("yea") Integer year, @Param("provinces") String provinces);


    /**
     * 根据id查询历年分数
     *
     * @param id
     * @return
     */
    @Transactional
    @Query(nativeQuery = true,
            value = "SELECT ys.*, l.name AS `familyText`,\n" +
                    "lo.name AS `batchText` \n" +
                    "FROM zs_gmuni_yearly_score ys \n" +
                    "LEFT JOIN pf_gmuni_lookup l \n" +
                    "ON ys.family=l.code \n" +
                    "AND l.parent_Id = (SELECT id FROM pf_gmuni_lookup WHERE CODE = 'asclassify')\n" +
                    "LEFT JOIN pf_gmuni_lookup lo\n" +
                    "ON ys.batch=lo.code \n" +
                    "AND lo.parent_Id = (SELECT id FROM pf_gmuni_lookup WHERE CODE = 'batch') \n" +
                    "WHERE ys.id=:ysId ")
    Map getById(@Param("ysId") String id);
}
