package com.example.mybatis.mapper;


import com.example.mybatis.model.Config;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


import java.util.List;


/**
 * 配置信息数据访问层
 */

@Mapper
@Repository
public interface ConfigMapper {


    /**
     * 根据前缀查找配置列表
     *
     * @return
     */
    //注解版本
    //@Select(value = " select id, code, name, value,sort from zs_gmuni_config order by sort")
    List<Config> listConfigs();


}
