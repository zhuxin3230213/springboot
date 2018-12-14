package cn.gmuni.zsgl.service;

import cn.gmuni.zsgl.entity.UpdateClickThrough;

/**
 * @Author:ZhuXin
 * @Description: 点击次数业务接口
 * @Date:Create in 14:32 2018/7/19
 * @Modified By:
 **/


public interface UpdateClickThroughService {


    /**
     * 根据文章id与type类型获取对象
     * @param articleId
     * @param type
     * @return
     */
    UpdateClickThrough clickThrough(String articleId,String type);

    /**
     * 根据文章id与type类型获取对象
     * type： 0：content 1：pnr
     *
     * @param articleId
     * @param type
     * @return
     */
    UpdateClickThrough clickAdd(String articleId, String type);

    /**
     * 根据id获取对象
     *
     * @param id
     * @return
     */
    UpdateClickThrough getById(String id);
}
