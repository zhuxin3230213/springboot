package cn.gmuni.zsgl.service;

import cn.gmuni.zsgl.entity.Content;
import cn.gmuni.zsgl.util.GlobalSearchUtil;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @Author:ZhuXin
 * @Description: 招生系统正文业务接口
 * @Date:Create in 11:05 2018/5/8
 * @Modified By:
 **/


public interface ContentService {


    /**
     * 获取关键字查询信息总页数
     *
     * @return
     */
    Integer getSearchsTotalPage();

    /**
     * 根据关键字全局查询
     *
     * @param keyWord
     * @return
     */
    List<GlobalSearchUtil> listSearchs(String keyWord, Integer currentPage, Integer pageSize);


    /**
     * 通过id查询正文
     *
     * @param id
     * @return
     */
    Content getContentById(String id);

    /**
     * 通过id查询正文并清处缓存
     *
     * @param id
     * @return
     */
    Content getConteByIdCache(String id);

    /**
     * 根据菜单的code查询正文并做缓存清除
     *
     * @param menuCode
     * @return
     */
    Content getContentByMenuCode(String menuCode);

    /**
     * 根据菜单的code查询正文
     *
     * @param menuCode
     * @return
     */
    Content getByMenuCode(String menuCode);

    /**
     * 根据院系专业的id查询正文
     *
     * @param fapId
     * @return
     */
    Content getByFapId(String fapId);

    /**
     * 获取正文列表
     *
     * @return
     */
    List<Content> listContents();


    /**
     * 根据文章对应菜单的code查询正文
     *
     * @param parentCode
     * @return
     */
    List<Content> findByParentCode(String parentCode);


    /**
     * 动态获取几条
     *
     * @return
     */
    List<Content> getContentLimit(Integer size);

    /**
     * 根据菜单code查询正文
     *
     * @param menuCdoe
     * @return
     */
    List<Content> findContentByMenuId(String menuCdoe);


    /**
     * 正文分页
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    Page<Content> getPage(Integer currentPage, Integer pageSize);


    /**
     * 根据菜单的code查询正文分页
     *
     * @param menuCode
     * @param currentPage
     * @param pageSize
     * @return
     */
    Page<Content> getPageByMenuCode(String menuCode, Integer currentPage, Integer pageSize);

    /**
     * 根据菜单的code查询正文分页并做缓存清除
     *
     * @param menuCode
     * @param currentPage
     * @param pageSize
     * @return
     */
    Page<Content> getPageByMenuCodeCache(String menuCode, Integer currentPage, Integer pageSize);


    /**
     * 点击次数自增
     *
     * @param id
     * @return
     */
    Content clickAdd(String id);




    /**
     * 联合查询根据点击次数排序
     *
     * @return
     */
    Page<Map> getByClickHosts(String menuCode, Integer currentPage, Integer pageSize);

    /**
     * 限制热点信息的条数
     *
     * @param startIndex
     * @param displayLength
     * @return
     */
    List<Map> getByLimitHosts(String menuCode, Integer startIndex, Integer displayLength);

    /**
     * 根据类型和id查询content和postNewsRegulation内容
     *
     * @param id
     * @param type
     * @return
     */
    Object getByType(String id, String type);

    /**
     * 获取正文总记录数
     *
     * @return
     */
    Integer getCount();

    /**
     * 获取热点信息的总记录数
     *
     * @return
     */
    Integer getHostsCount();


    /**
     * 获取正文总页数
     *
     * @return
     */
    Integer getCountTotalPages(Integer currentPage, Integer pageSize);

    /**
     * 获取热点信息总页数
     *
     * @return
     */
    Integer getHostTotalPages(String menuCode, Integer currentPage, Integer pageSize);

    /**
     * 根据菜单的code查询正文分页
     *
     * @param menuCode
     * @param currentPage
     * @param pageSize
     * @return
     */
    Integer getPageByMenuCodeTotalPages(String menuCode, Integer currentPage, Integer pageSize);


}
