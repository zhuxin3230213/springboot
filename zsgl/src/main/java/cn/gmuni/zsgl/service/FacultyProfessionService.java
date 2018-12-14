package cn.gmuni.zsgl.service;

import cn.gmuni.zsgl.entity.FacultyProfessional;

import java.util.List;
import java.util.Map;

/**
 * @Author:ZhuXin
 * @Description: 院系专业业务接口
 * @Date:Create in 11:46 2018/5/28
 * @Modified By:
 **/


public interface FacultyProfessionService {


    /**
     * 据deptId查询学科专业对应的id
     *
     * @param deptId
     * @return
     */
    String getFapIdByDeptId(String deptId);

    /**
     * 根据deptId查询学科专业对应的名称
     *
     * @param deptId
     * @return
     */
    String getNameByDeptId(String deptId);

    /**
     * 获取院系专业列表
     *
     * @return
     */
    List<FacultyProfessional> listFaps();

    /**
     * 获取院系专业树形结构
     *
     * @return
     */
    List<Map> facpTree();

    /**
     * 根据招生status状态获取学科专业列表
     * 状态：0招生,1当年不招生
     *
     * @param status
     * @return
     */
    List<Map> facpTreeByStatus(String status);

    /**
     * 获取学科专业列表
     *
     * @return
     */
    List<Map> listFacp();

    /**
     * 根据type类型和招生状态获取学科专业列表
     * 类型：文理艺体，从字典表获取
     * 状态：0招生,1当年不招生
     *
     * @param type
     * @param status
     * @return
     */
    List<Map> facpTreeByTypeAndStatus(String type, String status);

    /**
     * 根据id查询学科专业
     *
     * @param fapId
     * @return
     */
    Map getById(String fapId);

}
