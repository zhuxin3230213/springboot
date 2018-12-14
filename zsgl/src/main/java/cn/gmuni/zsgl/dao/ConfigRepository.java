package cn.gmuni.zsgl.dao;

import cn.gmuni.zsgl.entity.Config;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Author:ZhuXin
 * @Description: 招生系统配置信息数据接口
 * @Date:Create in 17:10 2018/5/7
 * @Modified By:
 **/
@CacheConfig(cacheNames = "configs")
public interface ConfigRepository extends JpaRepository<Config, String> {

    /**
     * 通过id查询配置信息
     *
     * @param id
     * @return
     */
    @Cacheable
    Config getConfigById(String id);

    /**
     * 根据code ：gmuni_contact_us_urls
     * 获取常用链接
     *
     * @param code
     * @return
     */
    @Cacheable
    @Transactional
    @Query(value = "select  c from Config c where c.code=:code")
    Config getConfigByCode(@Param("code") String code);

    /**
     * 获取底部配置信息列表
     *
     * @return
     */
    @Cacheable
    @Transactional
    @Query(value = "select c from Config c order by c.sort")
    List<Config> listConfigs();
}
