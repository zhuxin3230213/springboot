package cn.gmuni.zsgl.service;

import cn.gmuni.zsgl.entity.FamousTeacherStyle;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @Author:ZhuXin
 * @Description: 名师风采业务接口
 * @Date:Create in 10:14 2018/6/14
 * @Modified By:
 **/


public interface FamousTeacherStyleService {


    /**
     * 根据id查询教师信息
     *
     * @param fastId
     * @return
     */
    FamousTeacherStyle getById(String fastId);


    /**
     * 根据id获取教师所有信息
     *
     * @param fastId
     * @return
     */
    Map getByFaId(String fastId);

    /**
     * 获取教师信息列表分页
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    Page<Map> listFatsPage(Integer currentPage, Integer pageSize);


    /**
     * 获取教师信息列表总页数
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    Integer getFastsPageTotal(Integer currentPage, Integer pageSize);


    /**
     * 根据职称获取分页列表
     *
     * @param title
     * @param currentPage
     * @param pageSize
     * @return
     */
    Page<Map> listByTitlePage(String title, Integer currentPage, Integer pageSize);

    /**
     * 根据荣誉获取分页列表
     *
     * @param honor
     * @param currentPage
     * @param pageSize
     * @return
     */
    Page<Map> listByHonorPage(String honor, Integer currentPage, Integer pageSize);


    /**
     * 根据职称和荣誉、姓名获取分页列表
     *
     * @param title
     * @param honor
     * @param currentPage
     * @param pageSize
     * @return
     */
    Page<Map> listByTitleAndHonorPage(String title, String honor, String name, Integer currentPage, Integer pageSize);

    /**
     * 获取职称列表
     *
     * @return
     */
    List<Map> getListTitle();

    /**
     * 获取荣誉列表
     *
     * @return
     */
    List<Map> getListHonor();

    /**
     * 获取总页数
     *
     * @param pageSize 一页显示多少条
     * @param allRow   总纪录数
     * @return
     */
    Integer getFatsTotalPage(Integer pageSize, Integer allRow);
}
