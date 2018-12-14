package cn.gmuni.zsgl.dao;

import cn.gmuni.zsgl.entity.PostNewsRegulation;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * @Author:ZhuXin
 * @Description: 公告、新闻、报考指南数据接口
 * @Date:Create in 17:58 2018/5/28
 * @Modified By:
 **/
@CacheConfig(cacheNames = "homePage")
public interface PostNewsRegulationRepository extends JpaRepository<PostNewsRegulation, String> {


    /**
     * 根据type类型
     * 分别获取公告、新闻、报考指南总记录数
     *
     * @param type
     * @return
     */
    @Transactional
    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM zs_gmuni_post_news_regulations z " +
            "LEFT JOIN pf_gmuni_lookup l  \n" +
            " ON z.type=l.code " +
            "where l.parent_id=( SELECT id FROM pf_gmuni_lookup WHERE CODE = 'slideshowModule') " +
            "AND z.type=:type")
    Integer getPNRCountByType(@Param("type") String type);


    /**
     * 获取信息总记录数
     *
     * @return
     */
    @Transactional
    @Query(value = "select count(pnr) from PostNewsRegulation pnr")
    Integer getPNRCount();

    /**
     * 根据type类型获取信息分页限制条数
     * typeName：0：网站公告、1：新闻资讯、2：报考指南
     *
     * @param type
     * @param pageable
     * @return
     */
    @Cacheable
    @Transactional
    @Query(nativeQuery = true, value = "SELECT z.*,l.name AS 'pnrText'" +
            " FROM zs_gmuni_post_news_regulations z " +
            "LEFT JOIN pf_gmuni_lookup l  \n" +
            " ON z.type=l.code " +
            "where l.parent_id=( SELECT id FROM pf_gmuni_lookup WHERE CODE = 'slideshowModule') " +
            "AND z.type=:type")
    List<Map> getByTypeNameLimit(@Param("type") String type, Pageable pageable);


    /**
     * 根据type类型获取信息分页
     *
     * @param type
     * @param pageable
     * @return
     */
    @Cacheable
    @Transactional
    @Query("select pnr from PostNewsRegulation  pnr where pnr.type =?1")
    Page<PostNewsRegulation> getByTypeNamePage(String type, Pageable pageable);

    /**
     * 根据id查询信息对象
     *
     * @param id
     * @return
     */
    @Cacheable
    PostNewsRegulation getById(String id);

}
