package cn.gmuni.enrollment.config.service;

import cn.gmuni.enrollment.config.model.Config;

import java.util.List;
import java.util.Map;

public interface IContactUsService {

    public List<Config> getContactUsConfigs();

    public Map<String, Object> checkCode(Map<String, String> params);

    public Config saveConfig(Config config);

    public Config updateConfig(Config config);
    public List<Config> updateConfigs(List<Config> configs);

    public Map<String, Object> deleteConfig(Map<String, String> params);
}
