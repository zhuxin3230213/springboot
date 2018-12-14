package cn.gmuni.zsgl.dao;

import cn.gmuni.zsgl.entity.EnrollmentPlan;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * @Author:ZhuXin
 * @Description: 招生计划数据接口
 * @Date:Create in 15:55 2018/6/4
 * @Modified By:
 **/
@CacheConfig(cacheNames = "admGuide")
public interface EnrollmentPlanRepository extends JpaRepository<EnrollmentPlan, String> {


    /**
     * 获取招生计划列表
     *
     * @return
     */
    @Transactional
    @Query(nativeQuery = true,
            value = "SELECT ep.id AS epid,ep.fap_id,ep.enrolment,ep.actual_enrollment,ep.provinces,ep.create_time,ep.year,ep.edu_systme,ep.cost,\n" +
                    "fa.id AS faid,fa.deptId,fa.type,fa.status,fa.typeText,fa.sort,fa.introduction,\n" +
                    "dept.ID AS deptid,dept.name,dept.faculty \n" +
                    "FROM zs_gmuni_enrollment_plan ep \n" +
                    "LEFT JOIN (\n" +
                    "SELECT p.*, l.name AS `typeText` \n" +
                    "FROM zs_gmuni_faculty_professional p \n" +
                    "LEFT JOIN pf_gmuni_lookup l\n" +
                    "ON p.type=l.code WHERE l.parent_Id = (SELECT id FROM pf_gmuni_lookup WHERE CODE = 'asclassify')\n" +
                    ") fa \n" +
                    "ON ep.fap_id =fa.id \n" +
                    "LEFT JOIN pf_gmuni_department dept ON fa.deptID=dept.ID \n" +
                    "WHERE dept.faculty='xk' ORDER BY fa.sort+0")
    List<Map> listEnrollmentPlans();


    /**
     * 根据年份获取历年招生计划
     *
     * @param year
     * @return
     */
    @Transactional
    @Query(nativeQuery = true,
            value = "SELECT ep.id AS epid,ep.fap_id,ep.enrolment,ep.actual_enrollment,ep.provinces,ep.create_time,ep.year,ep.edu_systme,ep.cost,\n" +
                    "fa.id AS faid,fa.deptId,fa.type,fa.status,fa.typeText,fa.sort,fa.introduction,\n" +
                    "dept.ID AS deptid,dept.name,dept.faculty \n" +
                    "FROM zs_gmuni_enrollment_plan ep \n" +
                    "LEFT JOIN (\n" +
                    "SELECT p.*, l.name AS `typeText` \n" +
                    "FROM zs_gmuni_faculty_professional p \n" +
                    "LEFT JOIN pf_gmuni_lookup l\n" +
                    "ON p.type=l.code WHERE l.parent_Id = (SELECT id FROM pf_gmuni_lookup WHERE CODE = 'asclassify')\n" +
                    ") fa \n" +
                    "ON ep.fap_id =fa.id \n" +
                    "LEFT JOIN pf_gmuni_department dept ON fa.deptID=dept.ID \n" +
                    "WHERE dept.faculty='xk'\n" +
                    "AND ep.year =:yea \n" +
                    "ORDER BY fa.sort+0")
    List<Map> listEnrollmentPlansByYear(@Param("yea") Integer year);


    /**
     * 根据省份获取历年招生计划
     *
     * @param provinces
     * @return
     */
    @Transactional
    @Query(nativeQuery = true,
            value = "SELECT ep.id AS epid,ep.fap_id,ep.enrolment,ep.actual_enrollment,ep.provinces,ep.create_time,ep.year,ep.edu_systme,ep.cost,\n" +
                    "fa.id AS faid,fa.deptId,fa.type,fa.status,fa.typeText,fa.sort,fa.introduction,\n" +
                    "dept.ID AS deptid,dept.name,dept.faculty \n" +
                    "FROM zs_gmuni_enrollment_plan ep \n" +
                    "LEFT JOIN (\n" +
                    "SELECT p.*, l.name AS `typeText` \n" +
                    "FROM zs_gmuni_faculty_professional p \n" +
                    "LEFT JOIN pf_gmuni_lookup l\n" +
                    "ON p.type=l.code WHERE l.parent_Id = (SELECT id FROM pf_gmuni_lookup WHERE CODE = 'asclassify')\n" +
                    ") fa \n" +
                    "ON ep.fap_id =fa.id \n" +
                    "LEFT JOIN pf_gmuni_department dept ON fa.deptID=dept.ID \n" +
                    "WHERE dept.faculty='xk'\n" +
                    "AND ep.provinces=:provinces \n" +
                    "ORDER BY fa.sort+0")
    List<Map> listEnrollmentPlansByProvinces(@Param("provinces") String provinces);


    /**
     * 根据年份和省份查询招生计划
     *
     * @param year
     * @param provinces
     * @return
     */
    @Transactional
    @Query(nativeQuery = true,
            value = "SELECT ep.id AS epid,ep.fap_id,ep.enrolment,ep.actual_enrollment,ep.provinces,ep.create_time,ep.year,ep.edu_systme,ep.cost,\n" +
                    "fa.id AS faid,fa.deptId,fa.type,fa.status,fa.typeText,fa.sort,fa.introduction,\n" +
                    "dept.ID AS deptid,dept.name,dept.faculty \n" +
                    "FROM zs_gmuni_enrollment_plan ep \n" +
                    "LEFT JOIN (\n" +
                    "SELECT p.*, l.name AS `typeText` \n" +
                    "FROM zs_gmuni_faculty_professional p \n" +
                    "LEFT JOIN pf_gmuni_lookup l\n" +
                    "ON p.type=l.code WHERE l.parent_Id = (SELECT id FROM pf_gmuni_lookup WHERE CODE = 'asclassify')\n" +
                    ") fa \n" +
                    "ON ep.fap_id =fa.id \n" +
                    "LEFT JOIN pf_gmuni_department dept ON fa.deptID=dept.ID \n" +
                    "WHERE dept.faculty='xk'\n" +
                    "AND ep.year=:yea \n" +
                    "AND ep.provinces=:provinces \n" +
                    "ORDER BY fa.sort+0")
    List<Map> listEnrollmentPlansByYearAndProvinces(@Param("yea") Integer year, @Param("provinces") String provinces);

    /**
     * 根据id查询招生计划
     *
     * @param id
     * @return
     */
    @Transactional
    @Query(nativeQuery = true,
            value = "SELECT ep.id AS epid,ep.fap_id,ep.enrolment,ep.actual_enrollment,ep.provinces,ep.create_time,ep.year,ep.edu_systme,ep.cost,\n" +
                    "fa.id AS faid,fa.deptId,fa.type,fa.status,fa.typeText,fa.sort,fa.introduction,\n" +
                    "dept.ID AS deptid,dept.name,dept.faculty \n" +
                    "FROM zs_gmuni_enrollment_plan ep \n" +
                    "LEFT JOIN (\n" +
                    "SELECT p.*, l.name AS `typeText` \n" +
                    "FROM zs_gmuni_faculty_professional p \n" +
                    "LEFT JOIN pf_gmuni_lookup l\n" +
                    "ON p.type=l.code WHERE l.parent_Id = (SELECT id FROM pf_gmuni_lookup WHERE CODE = 'asclassify')\n" +
                    ") fa \n" +
                    "ON ep.fap_id =fa.id \n" +
                    "LEFT JOIN pf_gmuni_department dept ON fa.deptID=dept.ID \n" +
                    "WHERE dept.faculty='xk'\n" +
                    "AND ep.id=:epId \n" +
                    "ORDER BY fa.sort+0")
    Map getById(@Param("epId") String id);


    /**
     * 获取省份列表
     *
     * @return
     */
    @Cacheable
    @Transactional
    @Query(nativeQuery = true,
            value = "SELECT a.f_areaid,a.f_areaname FROM pf_gmuni_base_area a WHERE a.f_layer='1' ORDER BY a.f_layer")
    List<Map> listProvinces();
}
