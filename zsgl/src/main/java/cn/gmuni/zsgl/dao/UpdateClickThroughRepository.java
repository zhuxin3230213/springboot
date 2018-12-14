package cn.gmuni.zsgl.dao;

import cn.gmuni.zsgl.entity.UpdateClickThrough;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/**
 * @Author:ZhuXin
 * @Description: 点击次数接口类
 * @Date:Create in 11:25 2018/7/19
 * @Modified By:
 **/


public interface UpdateClickThroughRepository extends JpaRepository<UpdateClickThrough, String> {


    /**
     * 根据文章id和文章所属类型type获取对象
     * type： 0：content 1：pnr
     *
     * @param articleId
     * @param type
     * @return
     */
    @Transactional
    @Query(value = "select  u from UpdateClickThrough  u where u.articleId=:articleId and u.type=:type")
    UpdateClickThrough getByArticleIdAndType(@Param("articleId") String articleId, @Param("type") String type);

    /**
     * 根据id获取对象
     *
     * @param id
     * @return
     */
    @Transactional
    UpdateClickThrough getUpdateClickThroughById(String id);

    /**
     * 根据文章id和文章所属类型type更新次数
     * type：  0：content 1：pnr
     *
     * @param articleId
     * @param type
     * @return
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update UpdateClickThrough  u set u.clickThrough=u.clickThrough+1 where u.articleId=:articleId and u.type=:type")
    Integer updateClickThrough(@Param("articleId") String articleId, @Param("type") String type);

}
