package com.gmunidata.security.service;

import cn.gmuni.org.mapper.DepartmentMapper;
import cn.gmuni.org.mapper.RoleMapper;
import cn.gmuni.org.model.Role;
import cn.gmuni.sysmenu.mapper.ResourceMapper;
import cn.gmuni.sysmenu.mapper.ResourcePrivilegeMapper;
import cn.gmuni.sysmenu.model.Resource;
import cn.gmuni.sysmenu.model.ResourcePrivilege;
import cn.gmuni.sysmenu.service.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;


@Service("loginPrivService")
public class LoginPrivService {
    @Autowired
    ResourceMapper resourceMapper;
    @Autowired
    ResourcePrivilegeMapper privilegeMapper;
    @Autowired
    DepartmentMapper departmentMapper;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    @Qualifier("resourceServiceImpl")
    IResourceService resourceService;

    public List<Map> getPrivResourcesTreeByUserCode(String userCode) {
        //查询出所有资源信息
        List<Resource> resources = resourceMapper.selectAll();
        List<Role> role = roleMapper.getRoleByUserCode(userCode);
        boolean hasAdmin = false;
        for(Role r : role){
            if("admin".equals(r.getCode())){
                hasAdmin = true;
                break;
            }
        }
        if(hasAdmin){
            List<Map> result =  resourceService.parseListToTree(resources);
            clearDisMenus(result);
            return result;
        }
        //查询出该用户有权限的资源id begin
        //获取用户所在部门的dlevelcode
        List<String> dlevelCodeArray = departmentMapper.getDlevelCodeByUserCode(userCode);
        List<String> dlevelCodes = new ArrayList<>();
        dlevelCodes.add("");
        //获取所有上级的dlevelCode数组dlevelCodes
        for (int i = 0; i < dlevelCodeArray.size(); i++) {
            String dlevelCode = dlevelCodeArray.get(i);
            do {
                dlevelCodes.add(dlevelCode);
                dlevelCode = dlevelCode.substring(0, dlevelCode.length() - 3);
            } while (dlevelCode.length() > 0);
        }
        //dlevelCodes获取所有部门的权限信息
        List<ResourcePrivilege> privs = privilegeMapper.getPrivByDeptDlevelCode(dlevelCodes);
        //获取该用户所用有的角色信息
        privs.addAll(privilegeMapper.getPrivByUserCode(userCode));
        List<String> resourceIds = new ArrayList<>();
        if(privs.size() == 0){
            return null;
        }
        for (int i = 0; i < privs.size(); i++) {
            resourceIds.add(privs.get(i).getResourceCode());
        }
        //查询出该用户有权限的资源id end
        //根据资源权限构建资源树
        Map<Integer, List<Resource>> resourceLevel = new HashMap<>();
        //循环
        parseListToLevel(resources, resourceLevel, 0);
        List<Resource> privedResources = getPrivedResources(resourceLevel, new HashSet<>(resourceIds));
        List<Map> result = resourceService.parseListToTree(privedResources);
        clearDisMenus(result);
        return result;
    }

    private void clearDisMenus(List<Map> result){
        List<Map> deletes = new ArrayList<>();
        for(Map<String,Object> map : result){
            if("0".equals(map.get("status"))){
                deletes.add(map);
            }else{
                if(map.containsKey("children")){
                    List<Map> children = (List<Map>) map.get("children");
                    clearDisMenus(children);
                }
            }
        }
        for(Map m : deletes){
            result.remove(m);
        }
    }

    /**
     * 获取包含上级的资源列表
     */
    private List<Resource> getPrivedResources(Map<Integer, List<Resource>> resourceLevel, Set<String> resourceIds) {
        List<Resource> res = new ArrayList<>();
        //树最大层数
        int levelNums = resourceLevel.size();
        for (int i = levelNums - 1; i >= 0; i--) {
            List<Resource> resources = resourceLevel.get(i);
            for (int j = 0; j < resources.size(); j++) {
                Resource resource = resources.get(j);
                if (resourceIds.contains(resource.getId())) {
                    res.add(resource);
                    resourceIds.add(resource.getParentId());
                }
            }
        }
        return res;
    }

    /**
     * 将资源列表分层
     *
     * @param resources
     * @param resourceLevel
     * @param level
     */
    private void parseListToLevel(List<Resource> resources, Map<Integer, List<Resource>> resourceLevel, Integer level) {
        for (int i = 0; i < resources.size(); i++) {
            Resource resource = resources.get(i);
            if (null == resource.getId() || "-1".equals(resource.getId())) {
//                resources.remove(resource);
//                i--;
                if (!resourceLevel.containsKey(level)) {
                    resourceLevel.put(level, new ArrayList<>());
                }
                resourceLevel.get(level).add(resource);
                nextLevel(resources, resource, resourceLevel, level + 1);
                break;
            }
        }
    }

    /**
     * 递归下一层资源
     *
     * @param resources
     * @param parent
     * @param resourceLevel
     * @param level
     */
    private void nextLevel(List<Resource> resources, Resource parent, Map<Integer, List<Resource>> resourceLevel, Integer level) {
        for (int i = 0; i < resources.size(); i++) {
            Resource resource = resources.get(i);
            if (parent.getId().equals(resource.getParentId())) {
                if (!resourceLevel.containsKey(level)) {
                    resourceLevel.put(level, new ArrayList<>());
                }
                resourceLevel.get(level).add(resource);
//                resources.remove(resource);
//                i--;
                nextLevel(resources, resource, resourceLevel, level + 1);
            }
        }
    }
}
