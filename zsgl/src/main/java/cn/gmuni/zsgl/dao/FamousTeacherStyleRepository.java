package cn.gmuni.zsgl.dao;

import cn.gmuni.zsgl.entity.FamousTeacherStyle;
import cn.gmuni.zsgl.entity.Menu;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * @Author:ZhuXin
 * @Description: 名师风采数据接口
 * @Date:Create in 9:48 2018/6/14
 * @Modified By:
 **/
@CacheConfig(cacheNames = "schoolCharacteristics")
public interface FamousTeacherStyleRepository extends JpaRepository<FamousTeacherStyle, String> {


    /**
     * 根据id查询教师
     *
     * @param id
     * @return
     */
    @Cacheable
    FamousTeacherStyle getById(String id);

    /**
     * 获取教师信息列表
     *
     * @return
     */
    @Cacheable
    @Transactional
    @Query(nativeQuery = true, value = "SELECT\n" +
            "  f.id,\n" +
            "  f.name,\n" +
            "  f.academic_title,\n" +
            "  f.honor,\n" +
            "  f.e_mail,\n" +
            "  f.telephone,\n" +
            "  f.avatars,\n" +
            "  f.teacher_introduction,\n" +
            "  f.sort,\n" +
            "  f.homepage,\n" +
            "  f.Dname,\n" +
            "  f.Aname,\n" +
            "  f.Hname,\n" +
            "  f.Ename,\n" +
            "  f.SEname,\n" +
            "  dept.name              AS Fname,\n" +
            "  de.name                AS Sname\n" +
            "FROM (SELECT\n" +
            "        fa.id,\n" +
            "        fa.name,\n" +
            "        fa.faculty_code,\n" +
            "        fa.subject_code,\n" +
            "        fa.academic_title,\n" +
            "        fa.honor,\n" +
            "        fa.e_mail,\n" +
            "        fa.telephone,\n" +
            "        fa.avatars,\n" +
            "        fa.teacher_introduction,\n" +
            "        fa.sort,\n" +
            "        fa.homepage,\n" +
            "        l.name                  AS Dname,\n" +
            "        a.name                  AS Aname,\n" +
            "        h.name                  AS Hname,\n" +
            "        e.name                  AS Ename,\n" +
            "        s.name                  AS SEname\n" +
            "      FROM zs_gmuni_famous_teachers_style fa\n" +
            "        LEFT JOIN pf_gmuni_lookup l\n" +
            "          ON l.code = fa.degree\n" +
            "            AND l.parent_id = (SELECT\n" +
            "                                 id\n" +
            "                               FROM pf_gmuni_lookup l\n" +
            "                               WHERE l.code = \"degree\")\n" +
            "        LEFT JOIN pf_gmuni_lookup a\n" +
            "          ON a.code = fa.academic_title\n" +
            "            AND a.parent_id = (SELECT\n" +
            "                                 id\n" +
            "                               FROM pf_gmuni_lookup l\n" +
            "                               WHERE l.code = \"academic\")\n" +
            "        LEFT JOIN pf_gmuni_lookup h\n" +
            "          ON h.code = fa.honor\n" +
            "            AND h.parent_id = (SELECT\n" +
            "                                 id\n" +
            "                               FROM pf_gmuni_lookup l\n" +
            "                               WHERE l.code = \"honor\")\n" +
            "        LEFT JOIN pf_gmuni_lookup e\n" +
            "          ON e.code = fa.eduction\n" +
            "            AND e.parent_id = (SELECT\n" +
            "                                 id\n" +
            "                               FROM pf_gmuni_lookup l\n" +
            "                               WHERE l.code = \"education\")\n" +
            "        LEFT JOIN pf_gmuni_lookup s\n" +
            "          ON fa.sex = s.code\n" +
            "            AND s.parent_id = (SELECT\n" +
            "                                 id\n" +
            "                               FROM pf_gmuni_lookup l\n" +
            "                               WHERE l.code = \"sex\")) f\n" +
            "  LEFT JOIN zs_gmuni_faculty_professional fac\n" +
            "    ON f.faculty_code = fac.id\n" +
            "  LEFT JOIN pf_gmuni_department dept\n" +
            "    ON fac.deptId = dept.ID\n" +
            "      AND dept.faculty = \"xy\"\n" +
            "  LEFT JOIN zs_gmuni_faculty_professional facp\n" +
            "    ON f.subject_code = facp.id\n" +
            "  LEFT JOIN pf_gmuni_department de\n" +
            "    ON facp.deptId = de.ID\n" +
            "      AND de.faculty = \"xk\"\n" +
            "ORDER BY f.sort")
    List<Map> listFates();

    /**
     * 获取教师信息列表分页
     *
     * @param pageable
     * @return
     */
    @Cacheable
    @Transactional
    @Query(nativeQuery = true, value = "SELECT\n" +
            "  f.id,\n" +
            "  f.name,\n" +
            "  f.academic_title,\n" +
            "  f.honor,\n" +
            "  f.e_mail,\n" +
            "  f.telephone,\n" +
            "  f.avatars,\n" +
            "  f.teacher_introduction,\n" +
            "  f.sort,\n" +
            "  f.homepage,\n" +
            "  f.Dname,\n" +
            "  f.Aname,\n" +
            "  f.Hname,\n" +
            "  f.Ename,\n" +
            "  f.SEname,\n" +
            "  dept.name              AS Fname,\n" +
            "  de.name                AS Sname\n" +
            "FROM (SELECT\n" +
            "        fa.id,\n" +
            "        fa.name,\n" +
            "        fa.faculty_code,\n" +
            "        fa.subject_code,\n" +
            "        fa.academic_title,\n" +
            "        fa.honor,\n" +
            "        fa.e_mail,\n" +
            "        fa.telephone,\n" +
            "        fa.avatars,\n" +
            "        fa.teacher_introduction,\n" +
            "        fa.sort,\n" +
            "        fa.homepage,\n" +
            "        l.name                  AS Dname,\n" +
            "        a.name                  AS Aname,\n" +
            "        h.name                  AS Hname,\n" +
            "        e.name                  AS Ename,\n" +
            "        s.name                  AS SEname\n" +
            "      FROM zs_gmuni_famous_teachers_style fa\n" +
            "        LEFT JOIN pf_gmuni_lookup l\n" +
            "          ON l.code = fa.degree\n" +
            "            AND l.parent_id = (SELECT\n" +
            "                                 id\n" +
            "                               FROM pf_gmuni_lookup l\n" +
            "                               WHERE l.code = \"degree\")\n" +
            "        LEFT JOIN pf_gmuni_lookup a\n" +
            "          ON a.code = fa.academic_title\n" +
            "            AND a.parent_id = (SELECT\n" +
            "                                 id\n" +
            "                               FROM pf_gmuni_lookup l\n" +
            "                               WHERE l.code = \"academic\")\n" +
            "        LEFT JOIN pf_gmuni_lookup h\n" +
            "          ON h.code = fa.honor\n" +
            "            AND h.parent_id = (SELECT\n" +
            "                                 id\n" +
            "                               FROM pf_gmuni_lookup l\n" +
            "                               WHERE l.code = \"honor\")\n" +
            "        LEFT JOIN pf_gmuni_lookup e\n" +
            "          ON e.code = fa.eduction\n" +
            "            AND e.parent_id = (SELECT\n" +
            "                                 id\n" +
            "                               FROM pf_gmuni_lookup l\n" +
            "                               WHERE l.code = \"education\")\n" +
            "        LEFT JOIN pf_gmuni_lookup s\n" +
            "          ON fa.sex = s.code\n" +
            "            AND s.parent_id = (SELECT\n" +
            "                                 id\n" +
            "                               FROM pf_gmuni_lookup l\n" +
            "                               WHERE l.code = \"sex\")) f\n" +
            "  LEFT JOIN zs_gmuni_faculty_professional fac\n" +
            "    ON f.faculty_code = fac.id\n" +
            "  LEFT JOIN pf_gmuni_department dept\n" +
            "    ON fac.deptId = dept.ID\n" +
            "      AND dept.faculty = \"xy\"\n" +
            "  LEFT JOIN zs_gmuni_faculty_professional facp\n" +
            "    ON f.subject_code = facp.id\n" +
            "  LEFT JOIN pf_gmuni_department de\n" +
            "    ON facp.deptId = de.ID\n" +
            "      AND de.faculty = \"xk\"\n" +
            "ORDER BY f.sort"
            , countQuery = "SELECT COUNT(*) FROM zs_gmuni_famous_teachers_style")
    Page<Map> listFatesPage(Pageable pageable);


    /**
     * 根据职称获取名师风采分页
     *
     * @param title
     * @param pageable
     * @return
     */
    @Cacheable
    @Transactional
    @Query(nativeQuery = true,
            value = "SELECT\n" +
                    "  f.id,\n" +
                    "  f.name,\n" +
                    "  f.academic_title,\n" +
                    "  f.honor,\n" +
                    "  f.e_mail,\n" +
                    "  f.telephone,\n" +
                    "  f.avatars,\n" +
                    "  f.teacher_introduction,\n" +
                    "  f.sort,\n" +
                    "  f.homepage,\n" +
                    "  f.Dname,\n" +
                    "  f.Aname,\n" +
                    "  f.Hname,\n" +
                    "  f.Ename,\n" +
                    "  f.SEname,\n" +
                    "  dept.name              AS Fname,\n" +
                    "  de.name                AS Sname\n" +
                    "FROM (SELECT\n" +
                    "        fa.id,\n" +
                    "        fa.name,\n" +
                    "        fa.faculty_code,\n" +
                    "        fa.subject_code,\n" +
                    "        fa.academic_title,\n" +
                    "        fa.honor,\n" +
                    "        fa.e_mail,\n" +
                    "        fa.telephone,\n" +
                    "        fa.avatars,\n" +
                    "        fa.teacher_introduction,\n" +
                    "        fa.sort,\n" +
                    "        fa.homepage,\n" +
                    "        l.name                  AS Dname,\n" +
                    "        a.name                  AS Aname,\n" +
                    "        h.name                  AS Hname,\n" +
                    "        e.name                  AS Ename,\n" +
                    "        s.name                  AS SEname\n" +
                    "      FROM zs_gmuni_famous_teachers_style fa\n" +
                    "        LEFT JOIN pf_gmuni_lookup l\n" +
                    "          ON l.code = fa.degree\n" +
                    "            AND l.parent_id = (SELECT\n" +
                    "                                 id\n" +
                    "                               FROM pf_gmuni_lookup l\n" +
                    "                               WHERE l.code = \"degree\")\n" +
                    "        LEFT JOIN pf_gmuni_lookup a\n" +
                    "          ON a.code = fa.academic_title\n" +
                    "            AND a.parent_id = (SELECT\n" +
                    "                                 id\n" +
                    "                               FROM pf_gmuni_lookup l\n" +
                    "                               WHERE l.code = \"academic\")\n" +
                    "        LEFT JOIN pf_gmuni_lookup h\n" +
                    "          ON h.code = fa.honor\n" +
                    "            AND h.parent_id = (SELECT\n" +
                    "                                 id\n" +
                    "                               FROM pf_gmuni_lookup l\n" +
                    "                               WHERE l.code = \"honor\")\n" +
                    "        LEFT JOIN pf_gmuni_lookup e\n" +
                    "          ON e.code = fa.eduction\n" +
                    "            AND e.parent_id = (SELECT\n" +
                    "                                 id\n" +
                    "                               FROM pf_gmuni_lookup l\n" +
                    "                               WHERE l.code = \"education\")\n" +
                    "        LEFT JOIN pf_gmuni_lookup s\n" +
                    "          ON fa.sex = s.code\n" +
                    "            AND s.parent_id = (SELECT\n" +
                    "                                 id\n" +
                    "                               FROM pf_gmuni_lookup l\n" +
                    "                               WHERE l.code = \"sex\")) f\n" +
                    "  LEFT JOIN zs_gmuni_faculty_professional fac\n" +
                    "    ON f.faculty_code = fac.id\n" +
                    "  LEFT JOIN pf_gmuni_department dept\n" +
                    "    ON fac.deptId = dept.ID\n" +
                    "      AND dept.faculty = \"xy\"\n" +
                    "  LEFT JOIN zs_gmuni_faculty_professional facp\n" +
                    "    ON f.subject_code = facp.id\n" +
                    "  LEFT JOIN pf_gmuni_department de\n" +
                    "    ON facp.deptId = de.ID\n" +
                    "      AND de.faculty = \"xk\"\n" +
                    "WHERE f.academic_title =:title \n" +
                    "ORDER BY f.sort"
            , countQuery = "SELECT COUNT(*) FROM zs_gmuni_famous_teachers_style f WHERE f.academic_title=:title ")
    Page<Map> listByTitle(@Param("title") String title, Pageable pageable);


    /**
     * 根据荣誉获取名师风采分页
     *
     * @param honor
     * @param pageable
     * @return
     */
    @Cacheable
    @Transactional
    @Query(nativeQuery = true,
            value = "SELECT\n" +
                    "  f.id,\n" +
                    "  f.name,\n" +
                    "  f.academic_title,\n" +
                    "  f.honor,\n" +
                    "  f.e_mail,\n" +
                    "  f.telephone,\n" +
                    "  f.avatars,\n" +
                    "  f.teacher_introduction,\n" +
                    "  f.sort,\n" +
                    "  f.homepage,\n" +
                    "  f.Dname,\n" +
                    "  f.Aname,\n" +
                    "  f.Hname,\n" +
                    "  f.Ename,\n" +
                    "  f.SEname,\n" +
                    "  dept.name              AS Fname,\n" +
                    "  de.name                AS Sname\n" +
                    "FROM (SELECT\n" +
                    "        fa.id,\n" +
                    "        fa.name,\n" +
                    "        fa.faculty_code,\n" +
                    "        fa.subject_code,\n" +
                    "        fa.academic_title,\n" +
                    "        fa.honor,\n" +
                    "        fa.e_mail,\n" +
                    "        fa.telephone,\n" +
                    "        fa.avatars,\n" +
                    "        fa.teacher_introduction,\n" +
                    "        fa.sort,\n" +
                    "        fa.homepage,\n" +
                    "        l.name                  AS Dname,\n" +
                    "        a.name                  AS Aname,\n" +
                    "        h.name                  AS Hname,\n" +
                    "        e.name                  AS Ename,\n" +
                    "        s.name                  AS SEname\n" +
                    "      FROM zs_gmuni_famous_teachers_style fa\n" +
                    "        LEFT JOIN pf_gmuni_lookup l\n" +
                    "          ON l.code = fa.degree\n" +
                    "            AND l.parent_id = (SELECT\n" +
                    "                                 id\n" +
                    "                               FROM pf_gmuni_lookup l\n" +
                    "                               WHERE l.code = \"degree\")\n" +
                    "        LEFT JOIN pf_gmuni_lookup a\n" +
                    "          ON a.code = fa.academic_title\n" +
                    "            AND a.parent_id = (SELECT\n" +
                    "                                 id\n" +
                    "                               FROM pf_gmuni_lookup l\n" +
                    "                               WHERE l.code = \"academic\")\n" +
                    "        LEFT JOIN pf_gmuni_lookup h\n" +
                    "          ON h.code = fa.honor\n" +
                    "            AND h.parent_id = (SELECT\n" +
                    "                                 id\n" +
                    "                               FROM pf_gmuni_lookup l\n" +
                    "                               WHERE l.code = \"honor\")\n" +
                    "        LEFT JOIN pf_gmuni_lookup e\n" +
                    "          ON e.code = fa.eduction\n" +
                    "            AND e.parent_id = (SELECT\n" +
                    "                                 id\n" +
                    "                               FROM pf_gmuni_lookup l\n" +
                    "                               WHERE l.code = \"education\")\n" +
                    "        LEFT JOIN pf_gmuni_lookup s\n" +
                    "          ON fa.sex = s.code\n" +
                    "            AND s.parent_id = (SELECT\n" +
                    "                                 id\n" +
                    "                               FROM pf_gmuni_lookup l\n" +
                    "                               WHERE l.code = \"sex\")) f\n" +
                    "  LEFT JOIN zs_gmuni_faculty_professional fac\n" +
                    "    ON f.faculty_code = fac.id\n" +
                    "  LEFT JOIN pf_gmuni_department dept\n" +
                    "    ON fac.deptId = dept.ID\n" +
                    "      AND dept.faculty = \"xy\"\n" +
                    "  LEFT JOIN zs_gmuni_faculty_professional facp\n" +
                    "    ON f.subject_code = facp.id\n" +
                    "  LEFT JOIN pf_gmuni_department de\n" +
                    "    ON facp.deptId = de.ID\n" +
                    "      AND de.faculty = \"xk\"\n" +
                    "WHERE f.honor =:honor \n" +
                    "ORDER BY f.sort"
            , countQuery = "SELECT COUNT(*) FROM zs_gmuni_famous_teachers_style f WHERE f.honor=:honor ")
    Page<Map> listByHonor(@Param("honor") String honor, Pageable pageable);


    /**
     * 根据姓名模糊查询分页
     *
     * @param name
     * @param pageable
     * @return
     */
    @Cacheable
    @Transactional
    @Query(nativeQuery = true,
            value = "SELECT\n" +
                    "  f.id,\n" +
                    "  f.name,\n" +
                    "  f.academic_title,\n" +
                    "  f.honor,\n" +
                    "  f.e_mail,\n" +
                    "  f.telephone,\n" +
                    "  f.avatars,\n" +
                    "  f.teacher_introduction,\n" +
                    "  f.sort,\n" +
                    "  f.homepage,\n" +
                    "  f.Dname,\n" +
                    "  f.Aname,\n" +
                    "  f.Hname,\n" +
                    "  f.Ename,\n" +
                    "  f.SEname,\n" +
                    "  dept.name              AS Fname,\n" +
                    "  de.name                AS Sname\n" +
                    "FROM (SELECT\n" +
                    "        fa.id,\n" +
                    "        fa.name,\n" +
                    "        fa.faculty_code,\n" +
                    "        fa.subject_code,\n" +
                    "        fa.academic_title,\n" +
                    "        fa.honor,\n" +
                    "        fa.e_mail,\n" +
                    "        fa.telephone,\n" +
                    "        fa.avatars,\n" +
                    "        fa.teacher_introduction,\n" +
                    "        fa.sort,\n" +
                    "        fa.homepage,\n" +
                    "        l.name                  AS Dname,\n" +
                    "        a.name                  AS Aname,\n" +
                    "        h.name                  AS Hname,\n" +
                    "        e.name                  AS Ename,\n" +
                    "        s.name                  AS SEname\n" +
                    "      FROM zs_gmuni_famous_teachers_style fa\n" +
                    "        LEFT JOIN pf_gmuni_lookup l\n" +
                    "          ON l.code = fa.degree\n" +
                    "            AND l.parent_id = (SELECT\n" +
                    "                                 id\n" +
                    "                               FROM pf_gmuni_lookup l\n" +
                    "                               WHERE l.code = \"degree\")\n" +
                    "        LEFT JOIN pf_gmuni_lookup a\n" +
                    "          ON a.code = fa.academic_title\n" +
                    "            AND a.parent_id = (SELECT\n" +
                    "                                 id\n" +
                    "                               FROM pf_gmuni_lookup l\n" +
                    "                               WHERE l.code = \"academic\")\n" +
                    "        LEFT JOIN pf_gmuni_lookup h\n" +
                    "          ON h.code = fa.honor\n" +
                    "            AND h.parent_id = (SELECT\n" +
                    "                                 id\n" +
                    "                               FROM pf_gmuni_lookup l\n" +
                    "                               WHERE l.code = \"honor\")\n" +
                    "        LEFT JOIN pf_gmuni_lookup e\n" +
                    "          ON e.code = fa.eduction\n" +
                    "            AND e.parent_id = (SELECT\n" +
                    "                                 id\n" +
                    "                               FROM pf_gmuni_lookup l\n" +
                    "                               WHERE l.code = \"education\")\n" +
                    "        LEFT JOIN pf_gmuni_lookup s\n" +
                    "          ON fa.sex = s.code\n" +
                    "            AND s.parent_id = (SELECT\n" +
                    "                                 id\n" +
                    "                               FROM pf_gmuni_lookup l\n" +
                    "                               WHERE l.code = \"sex\")) f\n" +
                    "  LEFT JOIN zs_gmuni_faculty_professional fac\n" +
                    "    ON f.faculty_code = fac.id\n" +
                    "  LEFT JOIN pf_gmuni_department dept\n" +
                    "    ON fac.deptId = dept.ID\n" +
                    "      AND dept.faculty = \"xy\"\n" +
                    "  LEFT JOIN zs_gmuni_faculty_professional facp\n" +
                    "    ON f.subject_code = facp.id\n" +
                    "  LEFT JOIN pf_gmuni_department de\n" +
                    "    ON facp.deptId = de.ID\n" +
                    "      AND de.faculty = \"xk\"\n" +
                    "WHERE f.name LIKE %:name% \n" +
                    "ORDER BY f.sort"
            , countQuery = "SELECT COUNT(*) FROM zs_gmuni_famous_teachers_style f WHERE f.name LIKE %:name% ")
    Page<Map> listByName(@Param("name") String name, Pageable pageable);

    /**
     * 根据职称和荣誉查询名师风采列表分页
     *
     * @param title
     * @param honor
     * @param pageable
     * @return
     */
    @Cacheable
    @Transactional
    @Query(nativeQuery = true,
            value = "SELECT\n" +
                    "  f.id,\n" +
                    "  f.name,\n" +
                    "  f.academic_title,\n" +
                    "  f.honor,\n" +
                    "  f.e_mail,\n" +
                    "  f.telephone,\n" +
                    "  f.avatars,\n" +
                    "  f.teacher_introduction,\n" +
                    "  f.sort,\n" +
                    "  f.homepage,\n" +
                    "  f.Dname,\n" +
                    "  f.Aname,\n" +
                    "  f.Hname,\n" +
                    "  f.Ename,\n" +
                    "  f.SEname,\n" +
                    "  dept.name              AS Fname,\n" +
                    "  de.name                AS Sname\n" +
                    "FROM (SELECT\n" +
                    "        fa.id,\n" +
                    "        fa.name,\n" +
                    "        fa.faculty_code,\n" +
                    "        fa.subject_code,\n" +
                    "        fa.academic_title,\n" +
                    "        fa.honor,\n" +
                    "        fa.e_mail,\n" +
                    "        fa.telephone,\n" +
                    "        fa.avatars,\n" +
                    "        fa.teacher_introduction,\n" +
                    "        fa.sort,\n" +
                    "        fa.homepage,\n" +
                    "        l.name                  AS Dname,\n" +
                    "        a.name                  AS Aname,\n" +
                    "        h.name                  AS Hname,\n" +
                    "        e.name                  AS Ename,\n" +
                    "        s.name                  AS SEname\n" +
                    "      FROM zs_gmuni_famous_teachers_style fa\n" +
                    "        LEFT JOIN pf_gmuni_lookup l\n" +
                    "          ON l.code = fa.degree\n" +
                    "            AND l.parent_id = (SELECT\n" +
                    "                                 id\n" +
                    "                               FROM pf_gmuni_lookup l\n" +
                    "                               WHERE l.code = \"degree\")\n" +
                    "        LEFT JOIN pf_gmuni_lookup a\n" +
                    "          ON a.code = fa.academic_title\n" +
                    "            AND a.parent_id = (SELECT\n" +
                    "                                 id\n" +
                    "                               FROM pf_gmuni_lookup l\n" +
                    "                               WHERE l.code = \"academic\")\n" +
                    "        LEFT JOIN pf_gmuni_lookup h\n" +
                    "          ON h.code = fa.honor\n" +
                    "            AND h.parent_id = (SELECT\n" +
                    "                                 id\n" +
                    "                               FROM pf_gmuni_lookup l\n" +
                    "                               WHERE l.code = \"honor\")\n" +
                    "        LEFT JOIN pf_gmuni_lookup e\n" +
                    "          ON e.code = fa.eduction\n" +
                    "            AND e.parent_id = (SELECT\n" +
                    "                                 id\n" +
                    "                               FROM pf_gmuni_lookup l\n" +
                    "                               WHERE l.code = \"education\")\n" +
                    "        LEFT JOIN pf_gmuni_lookup s\n" +
                    "          ON fa.sex = s.code\n" +
                    "            AND s.parent_id = (SELECT\n" +
                    "                                 id\n" +
                    "                               FROM pf_gmuni_lookup l\n" +
                    "                               WHERE l.code = \"sex\")) f\n" +
                    "  LEFT JOIN zs_gmuni_faculty_professional fac\n" +
                    "    ON f.faculty_code = fac.id\n" +
                    "  LEFT JOIN pf_gmuni_department dept\n" +
                    "    ON fac.deptId = dept.ID\n" +
                    "      AND dept.faculty = \"xy\"\n" +
                    "  LEFT JOIN zs_gmuni_faculty_professional facp\n" +
                    "    ON f.subject_code = facp.id\n" +
                    "  LEFT JOIN pf_gmuni_department de\n" +
                    "    ON facp.deptId = de.ID\n" +
                    "      AND de.faculty = \"xk\"\n" +
                    "WHERE f.academic_title =:title \n" +
                    "    AND f.honor =:honor \n" +
                    "ORDER BY f.sort"
            , countQuery = "SELECT COUNT(*) FROM zs_gmuni_famous_teachers_style f " +
            "WHERE f.academic_title=:title " +
            "AND f.honor=:honor ")
    Page<Map> listByTitleAndHonor(@Param("title") String title, @Param("honor") String honor, Pageable pageable);

    /**
     * 根据职称和姓名查询名师风采列表分页
     *
     * @param title
     * @param name
     * @param pageable
     * @return
     */
    @Cacheable
    @Transactional
    @Query(nativeQuery = true,
            value = "SELECT\n" +
                    "  f.id,\n" +
                    "  f.name,\n" +
                    "  f.academic_title,\n" +
                    "  f.honor,\n" +
                    "  f.e_mail,\n" +
                    "  f.telephone,\n" +
                    "  f.avatars,\n" +
                    "  f.teacher_introduction,\n" +
                    "  f.sort,\n" +
                    "  f.homepage,\n" +
                    "  f.Dname,\n" +
                    "  f.Aname,\n" +
                    "  f.Hname,\n" +
                    "  f.Ename,\n" +
                    "  f.SEname,\n" +
                    "  dept.name              AS Fname,\n" +
                    "  de.name                AS Sname\n" +
                    "FROM (SELECT\n" +
                    "        fa.id,\n" +
                    "        fa.name,\n" +
                    "        fa.faculty_code,\n" +
                    "        fa.subject_code,\n" +
                    "        fa.academic_title,\n" +
                    "        fa.honor,\n" +
                    "        fa.e_mail,\n" +
                    "        fa.telephone,\n" +
                    "        fa.avatars,\n" +
                    "        fa.teacher_introduction,\n" +
                    "        fa.sort,\n" +
                    "        fa.homepage,\n" +
                    "        l.name                  AS Dname,\n" +
                    "        a.name                  AS Aname,\n" +
                    "        h.name                  AS Hname,\n" +
                    "        e.name                  AS Ename,\n" +
                    "        s.name                  AS SEname\n" +
                    "      FROM zs_gmuni_famous_teachers_style fa\n" +
                    "        LEFT JOIN pf_gmuni_lookup l\n" +
                    "          ON l.code = fa.degree\n" +
                    "            AND l.parent_id = (SELECT\n" +
                    "                                 id\n" +
                    "                               FROM pf_gmuni_lookup l\n" +
                    "                               WHERE l.code = \"degree\")\n" +
                    "        LEFT JOIN pf_gmuni_lookup a\n" +
                    "          ON a.code = fa.academic_title\n" +
                    "            AND a.parent_id = (SELECT\n" +
                    "                                 id\n" +
                    "                               FROM pf_gmuni_lookup l\n" +
                    "                               WHERE l.code = \"academic\")\n" +
                    "        LEFT JOIN pf_gmuni_lookup h\n" +
                    "          ON h.code = fa.honor\n" +
                    "            AND h.parent_id = (SELECT\n" +
                    "                                 id\n" +
                    "                               FROM pf_gmuni_lookup l\n" +
                    "                               WHERE l.code = \"honor\")\n" +
                    "        LEFT JOIN pf_gmuni_lookup e\n" +
                    "          ON e.code = fa.eduction\n" +
                    "            AND e.parent_id = (SELECT\n" +
                    "                                 id\n" +
                    "                               FROM pf_gmuni_lookup l\n" +
                    "                               WHERE l.code = \"education\")\n" +
                    "        LEFT JOIN pf_gmuni_lookup s\n" +
                    "          ON fa.sex = s.code\n" +
                    "            AND s.parent_id = (SELECT\n" +
                    "                                 id\n" +
                    "                               FROM pf_gmuni_lookup l\n" +
                    "                               WHERE l.code = \"sex\")) f\n" +
                    "  LEFT JOIN zs_gmuni_faculty_professional fac\n" +
                    "    ON f.faculty_code = fac.id\n" +
                    "  LEFT JOIN pf_gmuni_department dept\n" +
                    "    ON fac.deptId = dept.ID\n" +
                    "      AND dept.faculty = \"xy\"\n" +
                    "  LEFT JOIN zs_gmuni_faculty_professional facp\n" +
                    "    ON f.subject_code = facp.id\n" +
                    "  LEFT JOIN pf_gmuni_department de\n" +
                    "    ON facp.deptId = de.ID\n" +
                    "      AND de.faculty = \"xk\"\n" +
                    "WHERE f.academic_title=:title \n" +
                    "AND f.name LIKE %:name% \n" +
                    "ORDER BY f.sort\n"
            , countQuery = "SELECT COUNT(*) FROM zs_gmuni_famous_teachers_style f " +
            "WHERE f.academic_title=:title " +
            "AND f.name LIKE %:name% ")
    Page<Map> listByTitleAndName(@Param("title") String title, @Param("name") String name, Pageable pageable);


    /**
     * 根据荣誉和姓名查询名师风采列表分页
     *
     * @param honor
     * @param name
     * @param pageable
     * @return
     */
    @Cacheable
    @Transactional
    @Query(nativeQuery = true,
            value = "SELECT\n" +
                    "  f.id,\n" +
                    "  f.name,\n" +
                    "  f.academic_title,\n" +
                    "  f.honor,\n" +
                    "  f.e_mail,\n" +
                    "  f.telephone,\n" +
                    "  f.avatars,\n" +
                    "  f.teacher_introduction,\n" +
                    "  f.sort,\n" +
                    "  f.homepage,\n" +
                    "  f.Dname,\n" +
                    "  f.Aname,\n" +
                    "  f.Hname,\n" +
                    "  f.Ename,\n" +
                    "  f.SEname,\n" +
                    "  dept.name              AS Fname,\n" +
                    "  de.name                AS Sname\n" +
                    "FROM (SELECT\n" +
                    "        fa.id,\n" +
                    "        fa.name,\n" +
                    "        fa.faculty_code,\n" +
                    "        fa.subject_code,\n" +
                    "        fa.academic_title,\n" +
                    "        fa.honor,\n" +
                    "        fa.e_mail,\n" +
                    "        fa.telephone,\n" +
                    "        fa.avatars,\n" +
                    "        fa.teacher_introduction,\n" +
                    "        fa.sort,\n" +
                    "        fa.homepage,\n" +
                    "        l.name                  AS Dname,\n" +
                    "        a.name                  AS Aname,\n" +
                    "        h.name                  AS Hname,\n" +
                    "        e.name                  AS Ename,\n" +
                    "        s.name                  AS SEname\n" +
                    "      FROM zs_gmuni_famous_teachers_style fa\n" +
                    "        LEFT JOIN pf_gmuni_lookup l\n" +
                    "          ON l.code = fa.degree\n" +
                    "            AND l.parent_id = (SELECT\n" +
                    "                                 id\n" +
                    "                               FROM pf_gmuni_lookup l\n" +
                    "                               WHERE l.code = \"degree\")\n" +
                    "        LEFT JOIN pf_gmuni_lookup a\n" +
                    "          ON a.code = fa.academic_title\n" +
                    "            AND a.parent_id = (SELECT\n" +
                    "                                 id\n" +
                    "                               FROM pf_gmuni_lookup l\n" +
                    "                               WHERE l.code = \"academic\")\n" +
                    "        LEFT JOIN pf_gmuni_lookup h\n" +
                    "          ON h.code = fa.honor\n" +
                    "            AND h.parent_id = (SELECT\n" +
                    "                                 id\n" +
                    "                               FROM pf_gmuni_lookup l\n" +
                    "                               WHERE l.code = \"honor\")\n" +
                    "        LEFT JOIN pf_gmuni_lookup e\n" +
                    "          ON e.code = fa.eduction\n" +
                    "            AND e.parent_id = (SELECT\n" +
                    "                                 id\n" +
                    "                               FROM pf_gmuni_lookup l\n" +
                    "                               WHERE l.code = \"education\")\n" +
                    "        LEFT JOIN pf_gmuni_lookup s\n" +
                    "          ON fa.sex = s.code\n" +
                    "            AND s.parent_id = (SELECT\n" +
                    "                                 id\n" +
                    "                               FROM pf_gmuni_lookup l\n" +
                    "                               WHERE l.code = \"sex\")) f\n" +
                    "  LEFT JOIN zs_gmuni_faculty_professional fac\n" +
                    "    ON f.faculty_code = fac.id\n" +
                    "  LEFT JOIN pf_gmuni_department dept\n" +
                    "    ON fac.deptId = dept.ID\n" +
                    "      AND dept.faculty = \"xy\"\n" +
                    "  LEFT JOIN zs_gmuni_faculty_professional facp\n" +
                    "    ON f.subject_code = facp.id\n" +
                    "  LEFT JOIN pf_gmuni_department de\n" +
                    "    ON facp.deptId = de.ID\n" +
                    "      AND de.faculty = \"xk\"\n" +
                    "WHERE f.honor=:honor \n" +
                    "AND f.name LIKE %:name% \n" +
                    "ORDER BY f.sort\n"
            , countQuery = "SELECT COUNT(*) FROM zs_gmuni_famous_teachers_style f " +
            "WHERE f.honor=:honor " +
            "AND f.name LIKE %:name% ")
    Page<Map> listByHonorAndName(@Param("honor") String honor, @Param("name") String name, Pageable pageable);


    /**
     * 根据职称和荣誉、姓名查询名师风采列表分页
     *
     * @param title
     * @param honor
     * @param pageable
     * @return
     */
    @Cacheable
    @Transactional
    @Query(nativeQuery = true,
            value = "SELECT\n" +
                    "  f.id,\n" +
                    "  f.name,\n" +
                    "  f.academic_title,\n" +
                    "  f.honor,\n" +
                    "  f.e_mail,\n" +
                    "  f.telephone,\n" +
                    "  f.avatars,\n" +
                    "  f.teacher_introduction,\n" +
                    "  f.sort,\n" +
                    "  f.homepage,\n" +
                    "  f.Dname,\n" +
                    "  f.Aname,\n" +
                    "  f.Hname,\n" +
                    "  f.Ename,\n" +
                    "  f.SEname,\n" +
                    "  dept.name              AS Fname,\n" +
                    "  de.name                AS Sname\n" +
                    "FROM (SELECT\n" +
                    "        fa.id,\n" +
                    "        fa.name,\n" +
                    "        fa.faculty_code,\n" +
                    "        fa.subject_code,\n" +
                    "        fa.academic_title,\n" +
                    "        fa.honor,\n" +
                    "        fa.e_mail,\n" +
                    "        fa.telephone,\n" +
                    "        fa.avatars,\n" +
                    "        fa.teacher_introduction,\n" +
                    "        fa.sort,\n" +
                    "        fa.homepage,\n" +
                    "        l.name                  AS Dname,\n" +
                    "        a.name                  AS Aname,\n" +
                    "        h.name                  AS Hname,\n" +
                    "        e.name                  AS Ename,\n" +
                    "        s.name                  AS SEname\n" +
                    "      FROM zs_gmuni_famous_teachers_style fa\n" +
                    "        LEFT JOIN pf_gmuni_lookup l\n" +
                    "          ON l.code = fa.degree\n" +
                    "            AND l.parent_id = (SELECT\n" +
                    "                                 id\n" +
                    "                               FROM pf_gmuni_lookup l\n" +
                    "                               WHERE l.code = \"degree\")\n" +
                    "        LEFT JOIN pf_gmuni_lookup a\n" +
                    "          ON a.code = fa.academic_title\n" +
                    "            AND a.parent_id = (SELECT\n" +
                    "                                 id\n" +
                    "                               FROM pf_gmuni_lookup l\n" +
                    "                               WHERE l.code = \"academic\")\n" +
                    "        LEFT JOIN pf_gmuni_lookup h\n" +
                    "          ON h.code = fa.honor\n" +
                    "            AND h.parent_id = (SELECT\n" +
                    "                                 id\n" +
                    "                               FROM pf_gmuni_lookup l\n" +
                    "                               WHERE l.code = \"honor\")\n" +
                    "        LEFT JOIN pf_gmuni_lookup e\n" +
                    "          ON e.code = fa.eduction\n" +
                    "            AND e.parent_id = (SELECT\n" +
                    "                                 id\n" +
                    "                               FROM pf_gmuni_lookup l\n" +
                    "                               WHERE l.code = \"education\")\n" +
                    "        LEFT JOIN pf_gmuni_lookup s\n" +
                    "          ON fa.sex = s.code\n" +
                    "            AND s.parent_id = (SELECT\n" +
                    "                                 id\n" +
                    "                               FROM pf_gmuni_lookup l\n" +
                    "                               WHERE l.code = \"sex\")) f\n" +
                    "  LEFT JOIN zs_gmuni_faculty_professional fac\n" +
                    "    ON f.faculty_code = fac.id\n" +
                    "  LEFT JOIN pf_gmuni_department dept\n" +
                    "    ON fac.deptId = dept.ID\n" +
                    "      AND dept.faculty = \"xy\"\n" +
                    "  LEFT JOIN zs_gmuni_faculty_professional facp\n" +
                    "    ON f.subject_code = facp.id\n" +
                    "  LEFT JOIN pf_gmuni_department de\n" +
                    "    ON facp.deptId = de.ID\n" +
                    "      AND de.faculty = \"xk\"\n" +
                    "WHERE  f.academic_title=:title \n" +
                    "AND f.honor=:honor \n" +
                    "AND f.name LIKE %:name% \n" +
                    "ORDER BY f.sort\n"
            , countQuery = "SELECT COUNT(*) FROM zs_gmuni_famous_teachers_style f " +
            "WHERE f.academic_title=:title " +
            "AND f.honor=:honor " +
            "AND f.name LIKE %:name% ")
    Page<Map> listByTitleAndHonorName(@Param("title") String title, @Param("honor") String honor,
                                      @Param("name") String name, Pageable pageable);


    /**
     * 获取职称列表
     *
     * @return
     */
    @Cacheable
    @Transactional
    @Query(nativeQuery = true,
            value = "SELECT l.code,l.name AS title \n" +
                    "FROM pf_gmuni_lookup l \n" +
                    "WHERE l.name IN(\n" +
                    "SELECT l.name FROM zs_gmuni_famous_teachers_style fa\n" +
                    "LEFT JOIN pf_gmuni_lookup l\n" +
                    "ON fa.academic_title =l.code AND l.parent_id=(SELECT id FROM pf_gmuni_lookup l WHERE l.code=\"academic\")\n" +
                    "GROUP BY l.name)")
    List<Map> getListTitle();

    /**
     * 获取荣誉列表
     *
     * @return
     */
    @Transactional
    @Query(nativeQuery = true,
            value = "SELECT l.code,l.name AS honor \n" +
                    "FROM pf_gmuni_lookup l \n" +
                    "WHERE l.name IN(\n" +
                    "SELECT l.name FROM zs_gmuni_famous_teachers_style fa\n" +
                    "LEFT JOIN pf_gmuni_lookup l   \n" +
                    "ON fa.honor =l.code AND l.parent_id=(SELECT id FROM pf_gmuni_lookup l WHERE l.code=\"honor\")\n" +
                    "GROUP BY l.name)")
    List<Map> getListHonor();


    /**
     * 根据id获取对象
     *
     * @param id
     * @return
     */
    @Transactional
    @Query(nativeQuery = true, value = "SELECT\n" +
            "  f.id,\n" +
            "  f.name,\n" +
            "  f.academic_title,\n" +
            "  f.honor,\n" +
            "  f.e_mail,\n" +
            "  f.telephone,\n" +
            "  f.avatars,\n" +
            "  f.teacher_introduction,\n" +
            "  f.sort,\n" +
            "  f.homepage,\n" +
            "  f.Dname,\n" +
            "  f.Aname,\n" +
            "  f.Hname,\n" +
            "  f.Ename,\n" +
            "  f.SEname,\n" +
            "  dept.name              AS Fname,\n" +
            "  de.name                AS Sname\n" +
            "FROM (SELECT\n" +
            "        fa.id,\n" +
            "        fa.name,\n" +
            "        fa.faculty_code,\n" +
            "        fa.subject_code,\n" +
            "        fa.academic_title,\n" +
            "        fa.honor,\n" +
            "        fa.e_mail,\n" +
            "        fa.telephone,\n" +
            "        fa.avatars,\n" +
            "        fa.teacher_introduction,\n" +
            "        fa.sort,\n" +
            "        fa.homepage,\n" +
            "        l.name                  AS Dname,\n" +
            "        a.name                  AS Aname,\n" +
            "        h.name                  AS Hname,\n" +
            "        e.name                  AS Ename,\n" +
            "        s.name                  AS SEname\n" +
            "      FROM zs_gmuni_famous_teachers_style fa\n" +
            "        LEFT JOIN pf_gmuni_lookup l\n" +
            "          ON l.code = fa.degree\n" +
            "            AND l.parent_id = (SELECT\n" +
            "                                 id\n" +
            "                               FROM pf_gmuni_lookup l\n" +
            "                               WHERE l.code = \"degree\")\n" +
            "        LEFT JOIN pf_gmuni_lookup a\n" +
            "          ON a.code = fa.academic_title\n" +
            "            AND a.parent_id = (SELECT\n" +
            "                                 id\n" +
            "                               FROM pf_gmuni_lookup l\n" +
            "                               WHERE l.code = \"academic\")\n" +
            "        LEFT JOIN pf_gmuni_lookup h\n" +
            "          ON h.code = fa.honor\n" +
            "            AND h.parent_id = (SELECT\n" +
            "                                 id\n" +
            "                               FROM pf_gmuni_lookup l\n" +
            "                               WHERE l.code = \"honor\")\n" +
            "        LEFT JOIN pf_gmuni_lookup e\n" +
            "          ON e.code = fa.eduction\n" +
            "            AND e.parent_id = (SELECT\n" +
            "                                 id\n" +
            "                               FROM pf_gmuni_lookup l\n" +
            "                               WHERE l.code = \"education\")\n" +
            "        LEFT JOIN pf_gmuni_lookup s\n" +
            "          ON fa.sex = s.code\n" +
            "            AND s.parent_id = (SELECT\n" +
            "                                 id\n" +
            "                               FROM pf_gmuni_lookup l\n" +
            "                               WHERE l.code = \"sex\")) f\n" +
            "  LEFT JOIN zs_gmuni_faculty_professional fac\n" +
            "    ON f.faculty_code = fac.id\n" +
            "  LEFT JOIN pf_gmuni_department dept\n" +
            "    ON fac.deptId = dept.ID\n" +
            "      AND dept.faculty = \"xy\"\n" +
            "  LEFT JOIN zs_gmuni_faculty_professional facp\n" +
            "    ON f.subject_code = facp.id\n" +
            "  LEFT JOIN pf_gmuni_department de\n" +
            "    ON facp.deptId = de.ID\n" +
            "      AND de.faculty = \"xk\"\n" +
            "WHERE   f.id=:id ")
    Map getByfaID(@Param("id") String id);
}


