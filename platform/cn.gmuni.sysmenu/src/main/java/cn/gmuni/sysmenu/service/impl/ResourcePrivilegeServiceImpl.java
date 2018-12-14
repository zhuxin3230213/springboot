package cn.gmuni.sysmenu.service.impl;

import cn.gmuni.sysmenu.mapper.ResourcePrivilegeMapper;
import cn.gmuni.sysmenu.model.ResourcePrivilege;
import cn.gmuni.sysmenu.service.IResourcePrivilegeService;
import cn.gmuni.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class ResourcePrivilegeServiceImpl implements IResourcePrivilegeService {

    @Autowired
    ResourcePrivilegeMapper mapper;
    @Override
    public Map<String, Object> savePrivilege(Map<String, String> params) {
        //获取到要保存的信息
        String privType = params.get("privType");
        String privCode = params.get("privCode");
        String resourceCodes = params.get("resourceCodes");
        //先删除
        mapper.deleteByPrivCode(privCode);
        List<String> resourceIds = Arrays.asList(resourceCodes.split(","));
        //再新增
        for (int i = 0; i < resourceIds.size(); i++) {
            String resourceId = resourceIds.get(i);
            ResourcePrivilege priv = new ResourcePrivilege();
            priv.setId(IdGenerator.getId());
            priv.setPrivCode(privCode);
            priv.setResourceCode(resourceId);
            priv.setPrivType(privType);
            mapper.insert(priv);
        }
        Map<String, Object> res = new HashMap<>();
        res.put("flag", true);
        return res;
    }

    @Override
    public List<ResourcePrivilege> getPrivByObjId(Map<String, String> params) {
        return mapper.selectByPrivCode(params.get("privCode"));
    }
}
