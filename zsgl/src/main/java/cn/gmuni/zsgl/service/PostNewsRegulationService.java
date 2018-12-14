package cn.gmuni.zsgl.service;

import cn.gmuni.zsgl.entity.PostNewsRegulation;
import org.springframework.data.domain.Page;


import java.util.List;
import java.util.Map;

/**
 * @Author:ZhuXin
 * @Description: 公告、新闻、报考指南业务接口
 * @Date:Create in 17:59 2018/5/28
 * @Modified By:
 **/


public interface PostNewsRegulationService {
    /**
     * 获取信息列表
     *
     * @return
     */
    List<PostNewsRegulation> listPNRs();

    /**
     * 根据id查询信息
     *
     * @param id
     * @return
     */
    PostNewsRegulation getById(String id);


    /**
     * 根据type获取限制信息条数
     *
     * @param type
     * @param currentPage
     * @param pageSizes
     * @return
     */
    List<Map> getPageByTypeLimit(String type, Integer currentPage, Integer pageSizes);

    /**
     * 分页
     *
     * @param type
     * @param currentPage
     * @param pageSizes
     * @return
     */
    Page<PostNewsRegulation> getByTypeNamePage(String type, Integer currentPage, Integer pageSizes);

    /**
     * 获取信息分页
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    Page<PostNewsRegulation> getPage(Integer currentPage, Integer pageSize);

    /**
     * 点击某一条信息
     * 点击次数自增
     *
     * @param id
     * @return
     */
    PostNewsRegulation clickAdd(String id);


    /**
     * 根据type类型分别获取
     * 公告、新闻、报考指南记录数
     *
     * @param type
     * @return
     */
    Integer getPNRCountByType(String type);

    /**
     * 获取信息记录总数
     *
     * @return
     */
    Integer getPNRCount();

    /**
     * 获取信息总页数
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    Integer getPNRTotalPages(Integer currentPage, Integer pageSize);

    /**
     * 根据type获取分页信息总页数
     *
     * @param type
     * @param currentPage
     * @param pageSizes
     * @return
     */
    Integer getPageByTypeTotalPages(String type, Integer currentPage, Integer pageSizes);


    /**
     * 根据type类型返回名称
     *
     * @param type
     * @return
     */
    String typeName(String type);
}
