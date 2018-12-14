package cn.gmuni.zsgl.service.impl;

import cn.gmuni.zsgl.dao.ConfigRepository;
import cn.gmuni.zsgl.entity.Config;
import cn.gmuni.zsgl.service.ConfigService;
import cn.gmuni.zsgl.util.ConfigUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;



/**
 * @Author:ZhuXin
 * @Description: 系统配置信息业务类
 * @Date:Create in 11:50 2018/5/8
 * @Modified By:
 **/
@Transactional
@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    ConfigRepository configRepository;


    /**
     * 根据code获取常用链接
     * code： gmuni_contact_us_urls
     *
     * @param code
     * @return
     */
    @Override
    public Config getConfigByCode(String code) {
        if (!StringUtils.isEmpty(code)) {
            return configRepository.getConfigByCode(code);
        } else {
            return null;
        }

    }

    /**
     * 根据id查询配置信息
     *
     * @param id
     * @return
     */
    @Override
    public Config getConfigById(String id) {
        if (!StringUtils.isEmpty(id)) {
            return configRepository.getConfigById(id);
        } else {
            return null;
        }
    }

    /**
     * 获取配置信息列表
     *
     * @return
     */
    @Override
    public List<Config> listConfigs() {
        return configRepository.listConfigs();
    }

    /**
     * 获取所需配置信息
     *
     * @return
     */
    @Override
    public ConfigUtil listConfigsByCode() {
        List<Config> configs = configRepository.listConfigs();
        ConfigUtil configUtil = new ConfigUtil();
        if (configs.size() != 0) {
            for (Config temp : configs) {
                if ("gmuni_contact_us_address".equals(temp.getCode())) {
                    configUtil.setAddress(temp.getValue());
                } else if ("gmuni_contact_us_phone".equals(temp.getCode())) {
                    configUtil.setTel(temp.getValue());
                } else if ("gmuni_contact_us_fax".equals(temp.getCode())) {
                    configUtil.setFax(temp.getValue());
                } else if ("gmuni_contact_us_post".equals(temp.getCode())) {
                    configUtil.setPost(temp.getValue());
                } else if ("gmuni_contact_us_email".equals(temp.getCode())) {
                    configUtil.setEmail(temp.getValue());
                } else if ("gmuni_contact_us_micro_blog".equals(temp.getCode())) {
                    configUtil.setWeibo(temp.getValue());
                } else if ("gmuni_contact_us_official_website".equals(temp.getCode())) {
                    configUtil.setNetAddress(temp.getValue());
                }
            }
            return configUtil;
        } else {
            return null;
        }

    }

}
