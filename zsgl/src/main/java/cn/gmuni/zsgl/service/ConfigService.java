package cn.gmuni.zsgl.service;

import cn.gmuni.zsgl.entity.Config;
import cn.gmuni.zsgl.util.ConfigUtil;

import java.util.List;

/**
 * @Author:ZhuXin
 * @Description: 招生系统信息配置业务类
 * @Date:Create in 11:05 2018/5/8
 * @Modified By:
 **/


public interface ConfigService {


    /**
     * 根据code获取常用链接
     *
     * @param code
     * @return
     */
    Config getConfigByCode(String code);

    /**
     * 通过id查询系统配置信息
     *
     * @param id
     * @return
     */
    Config getConfigById(String id);


    /**
     * 获取系统配置信息列表
     *
     * @return
     */
    List<Config> listConfigs();

    /**
     * 获取所需配置信息
     *
     * @return
     */
    ConfigUtil listConfigsByCode();

}
