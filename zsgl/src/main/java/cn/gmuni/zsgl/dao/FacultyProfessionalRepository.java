package cn.gmuni.zsgl.dao;

import cn.gmuni.zsgl.entity.FacultyProfessional;
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
 * @Description: 院系专业数据接口
 * @Date:Create in 11:44 2018/5/28
 * @Modified By:
 **/
@CacheConfig(cacheNames = "facp")
public interface FacultyProfessionalRepository extends JpaRepository<FacultyProfessional, String> {

    /**
     * 通过id查询院系专业
     *
     * @param id
     * @return
     */
    @Cacheable
    @Transactional
    @Query(nativeQuery = true,
            value = "SELECT dept.ID,dept.name,dept.code,dept.dLevel_code,dept.faculty,fa.id,fa.deptId,fa.type,fa.status\n" +
                    "FROM zs_gmuni_faculty_professional fa \n" +
                    "INNER JOIN pf_gmuni_department dept " +
                    "ON fa.deptId = dept.ID" +
                    " WHERE fa.id =:id")
    Map getByFapId(@Param("id") String id);

    /**
     * 根据faculty
     * 'xy' :学院  ‘xk' :学科
     * 获取院系或者专业
     *
     * @return
     */
    @Cacheable
    @Transactional
    @Query(nativeQuery = true, value = "SELECT d.id,d.name,d.code,d.dLevel_code,d.faculty " +
            "FROM pf_gmuni_department d " +
            "WHERE d.faculty = :faculty")
    List<Map> listFasOrMajor(@Param("faculty") String faculty);


    /**
     * 获取院系专业列表
     *
     * @return
     */
    @Cacheable
    @Transactional
    @Query(nativeQuery = true, value = "SELECT d.id,d.name,d.code,d.dLevel_code,d.faculty F" +
            "ROM pf_gmuni_department d " +
            "WHERE d.faculty IN('xy','xk')")
    List<Map> listFaps();


    /**
     * 根据status状态获取学科专业列表：
     * 0招生,1当年不招生
     *
     * @param status
     * @return
     */
    @Cacheable
    @Transactional
    @Query(nativeQuery = true, value = "SELECT dept.ID,dept.name,dept.code,dept.dLevel_code,dept.faculty,fa.id,fa.deptId,fa.type,fa.status\n" +
            " FROM zs_gmuni_faculty_professional fa " +
            "INNER JOIN pf_gmuni_department dept ON fa.deptId = dept.ID " +
            "WHERE fa.status=:status or fa.status is null")
    List<Map> listFapsByStatus(@Param("status") String status);


    /**
     * 获取学科专业列表
     *
     * @return
     */
    @Cacheable
    @Transactional
    @Query(nativeQuery = true, value = "SELECT dept.ID,dept.name,dept.code,dept.dLevel_code,dept.faculty,fa.id,fa.deptId,fa.type,fa.status \n" +
            "FROM zs_gmuni_faculty_professional fa \n" +
            "INNER JOIN pf_gmuni_department dept \n" +
            "ON fa.deptId = dept.ID" +
            " ORDER BY fa.sort+0")
    List<Map> listFap();

    /**
     * 根据type类型：
     * 类型：文理艺体，从字典表获取
     * 并且根据status状态获取学科专业列表：
     * 0招生,1当年不招生
     *
     * @param type
     * @param status
     * @return
     */
    @Cacheable
    @Transactional
    @Query(nativeQuery = true, value = "SELECT dept.ID,dept.name,dept.code,dept.dLevel_code,dept.faculty,fa.id,fa.deptId,fa.type,fa.status\n" +
            "FROM zs_gmuni_faculty_professional fa " +
            "INNER JOIN pf_gmuni_department dept ON fa.deptId = dept.ID \n" +
            "WHERE  fa.type=:type AND fa.status=:status or fa.status is null")
    List<Map> listFapsByTypeAndStatus(@Param("type") String type, @Param("status") String status);


    /**
     * 根据deptId查询学科专业的id
     *
     * @param deptId
     * @return
     */
    @Cacheable
    @Transactional
    @Query(nativeQuery = true, value = "SELECT id FROM zs_gmuni_faculty_professional p " +
            "WHERE p.deptId=:deptId")
    String getFapIdByDeptId(@Param("deptId") String deptId);

    /**
     * 通过deptId的查询学科专业对应的名称
     *
     * @param deptId
     * @return
     */
    @Cacheable
    @Transactional
    @Query(nativeQuery = true,
            value = "SELECT d.name FROM pf_gmuni_department d WHERE d.ID=:deptId")
    String getNameByDeptId(@Param("deptId") String deptId);
}
