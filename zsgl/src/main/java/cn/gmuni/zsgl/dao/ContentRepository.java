package cn.gmuni.zsgl.dao;

import cn.gmuni.zsgl.entity.Content;
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
 * @Description: 招生系统正文数据接口
 * @Date:Create in 17:09 2018/5/7
 * @Modified By:
 **/
public interface ContentRepository extends JpaRepository<Content, String> {

    /**
     * 根据关键字查询全局信息
     *
     * @param keyWord
     * @return
     */
    @Query(nativeQuery = true, value = "SELECT s.id,s.name,s.type,s.sort \n" +
            "FROM \n" +
            "(SELECT m.code AS id,m.name AS NAME ,m.sort,'0' AS TYPE FROM zs_gmuni_menu m WHERE  m.status=\"1\" AND m.level=2 \n" +
            "UNION ALL\n" +
            "SELECT c.id AS id ,c.title AS NAME,c.sort,'1' AS TYPE  FROM zs_gmuni_content c \n" +
            "UNION ALL \n" +
            "SELECT r.id AS id ,r.title AS NAME,r.sort,'2' AS TYPE  FROM zs_gmuni_post_news_regulations r \n" +
            "UNION ALL\n" +
            "SELECT d.ID AS id ,d.name AS NAME ,d.sort,'3' AS TYPE FROM pf_gmuni_department d WHERE d.faculty IN('xk','xy') AND d.status=\"1\"\n" +
            "UNION ALL\n" +
            "SELECT f.id AS id ,f.name AS NAME ,f.sort,'4' AS TYPE FROM zs_gmuni_famous_teachers_style f\n" +
            ") s \n" +
            "WHERE s.name LIKE %:keyWord% \n" +
            "ORDER BY s.type,s.sort\n"
            , countQuery = "SELECT COUNT(*) FROM (SELECT m.name AS NAME  FROM zs_gmuni_menu m WHERE  m.status=\"1\"\n" +
            "UNION ALL\n" +
            "SELECT c.title AS NAME  FROM zs_gmuni_content c \n" +
            "UNION ALL \n" +
            "SELECT r.title AS NAME  FROM zs_gmuni_post_news_regulations r \n" +
            "UNION ALL\n" +
            "SELECT d.name AS NAME FROM pf_gmuni_department d WHERE d.faculty IN('xk','xy') AND d.status=\"1\"\n" +
            "UNION ALL\n" +
            "SELECT f.name AS NAME  FROM zs_gmuni_famous_teachers_style f)s\n" +
            "WHERE s.name LIKE %:keyWord% ")
    Page<Map> globalSearchs(@Param("keyWord") String keyWord, Pageable pageable);

    /**
     * 根据文章对应菜单的code查询正文
     *
     * @param parentCode
     * @return
     */
    @Transactional
    @Query("select c from Content  c where c.parentCode = ?1  ")
    List<Content> findByParentCode(String parentCode);


    /**
     * 获取菜单下正文分页
     *
     * @param parentCode
     * @param pageable
     * @return
     */
    @Transactional
    @Query(value = "select c from Content  c where c.parentCode =?1 ")
    Page<Content> getByParentCode(String parentCode, Pageable pageable);


    /**
     * 获取招生政策列表
     *
     * @param parentCode
     * @param pageable
     * @return
     */
    @Cacheable(cacheNames = "admGuide")
    @Transactional
    @Query(value = "select c from Content  c where c.parentCode =?1 ")
    Page<Content> getAdmissionsPolicyByParentCode(String parentCode, Pageable pageable);

    /**
     * 获取学生活动列表
     *
     * @param parentCode
     * @param pageable
     * @return
     */
    @Cacheable(cacheNames = "campusLifeManagement")
    @Transactional
    @Query(value = "select c from Content  c where c.parentCode =?1 ")
    Page<Content> getStudentActivitiesByParentCode(String parentCode, Pageable pageable);


    /**
     * 获取正文的总纪录数
     *
     * @return
     */

    @Transactional
    @Query(value = "select count(c) from Content c")
    Integer getCount();

    /**
     * 院系专业：
     * 根据正文对应院系专业的code查询正文
     *
     * @param parentCode
     * @return
     */
    @Transactional
    @Query("select c from Content c where c.parentCode = ?1")
    Content getContentByMajorCode(String parentCode);


    /**
     * 学校概况
     *
     * @param parentCode
     * @return
     */
    @Cacheable(cacheNames = "facp")
    @Transactional
    @Query("select c from Content c where c.parentCode = ?1")
    Content getSchoolProfileByCode(String parentCode);


    /**
     * 学校荣誉
     *
     * @param parentCode
     * @return
     */
    @Cacheable(cacheNames = "schoolCharacteristics")
    @Transactional
    @Query("select c from Content c where c.parentCode = ?1")
    Content getschoolHonorByCode(String parentCode);

    /**
     * 校际交流
     *
     * @param parentCode
     * @return
     */
    @Cacheable(cacheNames = "schoolCharacteristics")
    @Transactional
    @Query("select c from Content c where c.parentCode = ?1")
    Content getIntercollegiateExchangeByCode(String parentCode);

    /**
     * 奖优助学
     *
     * @param parentCode
     * @return
     */
    @Cacheable(cacheNames = "campusLifeManagement")
    @Transactional
    @Query("select c from Content c where c.parentCode = ?1")
    Content getawardByCode(String parentCode);

    /**
     * 通过id查询正文
     *
     * @param id
     * @return
     */

    Content getContentById(String id);

    /**
     * 根据id和所属模块获取招生政策正文信息
     *
     * @param id
     * @param parentCode
     * @return
     */
    @Cacheable(cacheNames = "admGuide")
    @Query(value = "select  c from Content c where c.id=:id and c.parentCode=:parentCode ")
    Content getContentAdmissionsPolicy(@Param("id") String id, @Param("parentCode") String parentCode);

    /**
     * 根据id和所属模块获取学生活动正文信息
     *
     * @param id
     * @param parentCode
     * @return
     */
    @Cacheable(cacheNames = "campusLifeManagement")
    @Query(value = "select  c from Content c where c.id=:id and c.parentCode=:parentCode ")
    Content getContentStudentActivities(@Param("id") String id, @Param("parentCode") String parentCode);

    /**
     * 根据id和所属模块获取学科专业正文信息
     *
     * @param id
     * @param parentCode
     * @return
     */
    @Cacheable(cacheNames = "facp")
    @Query(value = "select  c from Content c where c.id=:id and c.parentCode=:parentCode ")
    Content getContentFac(@Param("id") String id, @Param("parentCode") String parentCode);




    /**
     * 两表联合根据点击次数查询排序
     * 0:为content:去除学科专业正文(2004)
     * 1：为postNewsRegulation
     *
     * @return
     */
    @Transactional
    @Query(nativeQuery = true,
            value = "SELECT t.id,t.title,\n" +
                    "u.type,u.click_through\n" +
                    "FROM \n" +
                    "(\n" +
                    "SELECT c.id,c.title\n" +
                    "FROM zs_gmuni_content c \n" +
                    "WHERE c.parent_code !=:menuCode \n" +
                    "UNION ALL \n" +
                    "SELECT pnr.id,pnr.title\n" +
                    "FROM zs_gmuni_post_news_regulations pnr)  t \n" +
                    "LEFT JOIN zs_gmuni_update_click_through u\n" +
                    "ON u.article_id=t.id\n" +
                    "ORDER BY u.click_through DESC",
            countQuery = "SELECT COUNT(*)\n" +
                    "FROM \n" +
                    "(\n" +
                    "SELECT c.id \n" +
                    "FROM zs_gmuni_content c \n" +
                    "UNION ALL \n" +
                    "SELECT pnr.id \n" +
                    "FROM zs_gmuni_post_news_regulations pnr)  t")
    Page<Map> getByClickThroughHosts(@Param("menuCode") String menuCode, Pageable pageable);


    /**
     * 获取热点信息的总纪录数
     *
     * @return
     */

    @Transactional
    @Query(nativeQuery = true,
            value = "SELECT COUNT(*)\n" +
                    "FROM \n" +
                    "(\n" +
                    "SELECT c.id \n" +
                    "FROM zs_gmuni_content c \n" +
                    "UNION ALL \n" +
                    "SELECT pnr.id \n" +
                    "FROM zs_gmuni_post_news_regulations pnr)  t")
    Integer getHostsCount();

    /**
     * 热点信息限制条数
     *
     * @param startIndex
     * @param displayLength
     * @return
     */
    @Transactional
    @Query(nativeQuery = true, value = " SELECT t.id,t.title,\n" +
            "u.type,u.click_through\n" +
            "FROM \n" +
            "(\n" +
            "SELECT c.id,c.title\n" +
            "FROM zs_gmuni_content c \n" +
            "WHERE c.parent_code !=:menuCode  \n" +
            "UNION ALL \n" +
            "SELECT pnr.id,pnr.title\n" +
            "FROM zs_gmuni_post_news_regulations pnr)  t \n" +
            "LEFT JOIN zs_gmuni_update_click_through u\n" +
            "ON u.article_id=t.id\n" +
            "ORDER BY u.click_through DESC LIMIT :startIndex,:displayLength")
    List<Map> getByLimitClickThroughHosts(@Param("menuCode") String menuCode, @Param("startIndex") Integer startIndex, @Param("displayLength") Integer displayLength);


    /**
     * 获取图片对象
     *
     * @param imageId
     * @return
     */
    @Query(nativeQuery = true, value = "SELECT u.id,u.name,u.format,u.size,u.last_modified,u.create_time " +
            "FROM pf_gmuni_upload u " +
            "WHERE u.id=:imageId ")
    Map getImage(@Param("imageId") String imageId);
}
