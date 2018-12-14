package cn.gmuni.enrollment.config.mapper;

import cn.gmuni.enrollment.config.model.Config;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 配置信息数据访问层
 */
@Service
public interface ConfigMapper {
    /**
     * 根据code的前缀判断是否存在相应的code
     *
     * @param codePrefix
     * @return
     */
    int checkContactUsCode(String codePrefix);

    /**
     * 批量插入配置信息
     */
    int insertConfigs(List<Config> list);

    /**
     * 根据前缀查找配置列表
     *
     * @param codePrefix
     * @return
     */
    List<Config> listConfigByPrefix(String codePrefix);

    /**
     * 根据code查找，校验用
     */
    int checkByCodeEqual(String code);

    int saveConfig(Config config);

    int updateConfig(Config config);
    int updateConfigs(List<Config> list);

    int deleteConfig(String id);
}
