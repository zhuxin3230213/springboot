package cn.gmuni.zsgl.dao;

import cn.gmuni.zsgl.entity.ProfessionContent;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/**
 * @Author:ZhuXin
 * @Description: 学科专业正文数据接口
 * @Date:Create in 9:37 2018/7/2
 * @Modified By:
 **/
@CacheConfig(cacheNames = "facp")
public interface ProfessionContentRepository extends JpaRepository<ProfessionContent, String> {

    /**
     * 根据学科专业的id查询正文对象
     *
     * @param proId
     * @return
     */
    @Cacheable
    @Transactional
    @Query(value = "select pc from ProfessionContent  pc where pc.proId=:proId")
    ProfessionContent getByProId(@Param("proId") String proId);
}
