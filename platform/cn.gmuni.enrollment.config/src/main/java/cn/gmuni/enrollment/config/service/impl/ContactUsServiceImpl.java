package cn.gmuni.enrollment.config.service.impl;

import cn.gmuni.enrollment.config.ConfigConst;
import cn.gmuni.enrollment.config.mapper.ConfigMapper;
import cn.gmuni.enrollment.config.model.Config;
import cn.gmuni.enrollment.config.service.IContactUsService;
import cn.gmuni.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContactUsServiceImpl implements IContactUsService {
    @Autowired
    ConfigMapper configMapper;

    @Override
    public List<Config> getContactUsConfigs() {
        return configMapper.listConfigByPrefix(ConfigConst.CONTACT_US_CODE_PREFIX);
    }

    @Override
    public Map<String, Object> checkCode(Map<String, String> params) {
        Map<String, Object> res = new HashMap<>();
        res.put("flag", configMapper.checkByCodeEqual(ConfigConst.CONTACT_US_CODE_PREFIX + params.get("code")) > 0 ? true : false);
        return res;
    }

    @Override
    public Config saveConfig(Config config) {
        config.setId(IdGenerator.getId());
        config.setCode(ConfigConst.CONTACT_US_CODE_PREFIX + config.getCode());
        return configMapper.saveConfig(config) > 0 ? config : null;
    }

    @Override
    public Config updateConfig(Config config) {
        return configMapper.updateConfig(config) > 0 ? config : null;
    }
    @Override
    public List<Config> updateConfigs(List<Config> configs) {
        return configMapper.updateConfigs(configs) > 0 ? configs : null;
    }

    @Override
    public Map<String, Object> deleteConfig(Map<String, String> params) {
        Map<String, Object> res = new HashMap<>();
        res.put("flag", configMapper.deleteConfig(params.get("id")) > 0 ? true : false);
        return res;
    }
}
