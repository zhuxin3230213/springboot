package cn.gmuni.zsgl.dao;

import cn.gmuni.zsgl.entity.Menu;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import javax.transaction.Transactional;
import java.util.List;

/**
 * @Author:ZhuXin
 * @Description: 招生系统一级菜单与二级菜单数据接口
 * @Date:Create in 17:07 2018/5/7
 * @Modified By:
 **/
@CacheConfig(cacheNames = "menus")
public interface MenuRepository extends JpaRepository<Menu, String> {


    /**
     * 获取菜单列表
     *
     * @param status
     * @return
     */
    @Cacheable
    @Transactional
    @Query("select m from Menu m where m.status=:status order by m.sort+0")
    List<Menu> findAll(@Param("status") String status);

    /**
     * 通过菜单层次查询菜单
     *
     * @param level
     * @return
     */
    @Transactional
    @Query("select m from Menu m where m.level = ?1 order by m.sort+0")
    List<Menu> findByLevel(Integer level);

    /**
     * 通过子级菜单parentId查询所有子菜单
     *
     * @param parentId
     * @return
     */
    @Transactional
    @Query("select m from Menu m where  m.parentId = ?1 order by m.sort+0")
    List<Menu> findByParentId(String parentId);


    /**
     * 获取父级菜单
     *
     * @return
     */
    @Transactional
    @Query("select m from Menu m where m.parentId=-1 and m.status = ?1")
    List<Menu> getFirstMenu(String status);

    /**
     * 获取子菜单
     *
     * @return
     */
    @Transactional
    @Query("select m from Menu m,Menu me where m.parentId = me.code and m.status = ?1")
    List<Menu> getSecondMenu(String status);


    /**
     * 通过id查询菜单
     *
     * @param id
     * @return
     */
    Menu getMenuById(String id);


    /**
     * 根据code查询菜单
     *
     * @param code
     * @return
     */
    @Transactional
    @Query(value = "select  m from Menu  m where m.code =?1")
    Menu getByCode(String code);

    /**
     * 点击菜单次数自增
     * update操作返回值须为int、Integer、void类型
     *
     * @param code
     * @return
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update Menu m set m.clickThrough =m.clickThrough+1 where m.code =?1")
    Integer updateClickAdd(String code);
}