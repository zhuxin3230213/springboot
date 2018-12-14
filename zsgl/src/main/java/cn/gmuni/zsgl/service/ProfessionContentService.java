package cn.gmuni.zsgl.service;

import cn.gmuni.zsgl.entity.ProfessionContent;

/**
 * @Author:ZhuXin
 * @Description: 学科专业正文业务接口
 * @Date:Create in 9:50 2018/7/2
 * @Modified By:
 **/


public interface ProfessionContentService {

    /**
     * 根据学科专业的id查询其对象
     *
     * @param proId
     * @return
     */
    ProfessionContent getByProId(String proId);

    /**
     * 通过deptId获取
     *
     * @param deptId
     * @return
     */
    String getContentIdByDeptId(String deptId);
}
