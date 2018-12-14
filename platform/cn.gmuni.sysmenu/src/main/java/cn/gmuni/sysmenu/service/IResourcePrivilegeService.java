package cn.gmuni.sysmenu.service;

import cn.gmuni.sysmenu.model.ResourcePrivilege;

import java.util.List;
import java.util.Map;

public interface IResourcePrivilegeService {
    public Map<String, Object> savePrivilege(Map<String, String> params);
    public List<ResourcePrivilege> getPrivByObjId( Map<String, String> params);

}
