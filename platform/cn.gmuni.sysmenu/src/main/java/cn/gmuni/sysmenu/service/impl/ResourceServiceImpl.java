package cn.gmuni.sysmenu.service.impl;

import cn.gmuni.sysmenu.mapper.ResourceMapper;
import cn.gmuni.sysmenu.model.Resource;
import cn.gmuni.sysmenu.service.IResourceService;
import cn.gmuni.utils.IdGenerator;
import cn.gmuni.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ResourceServiceImpl implements IResourceService {
    @Autowired
    ResourceMapper mapper;

    @Override
    public Resource addResource(Resource resource) {
        resource.setId(IdGenerator.getId());
        resource.setCreateTime(new Date());
        return mapper.insert(resource) > 0 ? resource : null;
    }

    @Override
    public Object getAllResource() {
        List<Resource> resources = mapper.selectAllWithoutButton();
        return parseListToTree(resources);
    }
    @Override
    public Object getAllResourceForPriv() {
        List<Resource> resources = mapper.selectAll();
        return parseListToTree(resources);
    }
    @Override
    public List<Map> parseListToTree(List<Resource> resources){
        List<Map> result = new ArrayList<>();
        for (int i = 0; i < resources.size(); i++) {
            Resource resource = resources.get(i);
            if (null == resource.getId() || "-1".equals(resource.getId())) {
                Map<String, Object> map = parseResource(resource);
                result.add(map);
                resources.remove(resource);
                setChildren(resources, (List<Map>) map.get("children"), map);
                break;
            }
        }
        return result;
    }
    private boolean setChildren(List<Resource> resources, List<Map> children, Map<String, Object> parent) {
        for (int i = 0; i < resources.size(); i++) {
            Resource resource = resources.get(i);
            if (parent.get("id").equals(resource.getParentId())) {
                Map<String, Object> map = parseResource(resource);
                children.add(map);
//                resources.remove(resource);
//                i--;
                setChildren(resources, (List<Map>) map.get("children"), map);
            }
        }
        return true;
    }

    private Map<String, Object> parseResource(Resource resource) {
        Map<String, Object> node = new HashMap<>();
        node.put("id", resource.getId());
        node.put("name", resource.getName());
        node.put("code", resource.getCode());
        node.put("parentCode", resource.getParentCode());
        node.put("type", resource.getType());
        node.put("parentId", resource.getParentId());
        node.put("children", new ArrayList<>());
        node.put("url",resource.getUrl());
        node.put("buttonCode",resource.getButtonCode());
        node.put("sort",resource.getSort());
        node.put("status",resource.getStatus());
        return node;
    }

    @Override
    public Resource updateResource(Resource resource) {
        return mapper.updateByPrimaryKey(resource) > 0 ? resource : null;
    }

    @Override
    public PageInfo<Resource> getResourceListById(Map<String, String> params) {
        PageUtils page = PageUtils.getPage(params);
        Map<String, Object> queryPara = new HashMap<>();
        queryPara.put("code", params.get("code"));
        queryPara.put("name", params.get("name"));
        queryPara.put("pid", params.get("resourceId"));
        PageHelper.startPage(page.getPage(),page.getSize());
        return new PageInfo<>(mapper.selectByPid(queryPara));
    }

    @Override
    public Map<String, Object> delResource(Map<String, String> params) {
        Map<String, Object> res = new HashMap<>();
        String resourceIds = params.get("resourceId");
        List<String> ids = Arrays.asList(resourceIds.split(","));
        //包含下属不允许删除
        long c = mapper.countByPids(ids);
        if (c > 0) {
            res.put("flag", false);
            res.put("msg", "hasChildren");
            return res;
        }
        int num = mapper.deleteByIdsIn(ids);
        res.put("flag", num > 0 ? true : false);
        return res;
    }

    @Override
    public Resource getResourceById(Map<String, String> params) {
        return mapper.selectByPrimaryKey(params.get("resourceId"));
    }

    @Override
    public Map<String, Object> checkNameExist(Resource resource) {
        String type = resource.getType();
        Map<String, Object> res = new HashMap<>();
        switch (type) {
            case "0":
                return checkByName(res, resource);
            case "1":
                return checkByUrl(res, resource);
            case "2":
                return checkByButtonCode(res, resource);
            default:
                res.put("flag", true);
                res.put("msg", "error");
        }
        return res;
    }

    /**
     * 菜单全局URL不重名
     * @param res
     * @param resource
     * @return
     */
    private Map<String, Object> checkByUrl(Map<String, Object> res, Resource resource){
        Resource r = mapper.selectByUrl(resource);
        if (null != r && !r.getId().equals(resource.getId())) {
            res.put("flag", true);
            if(r.getCode().equals(resource.getCode())){
                res.put("msg", "codeExist");
            }else {
                res.put("msg", "urlExist");
            }
        }else{
            res.put("flag", false);
        }
        return res;
    }

    /**
     * 按钮页面code不重复
     * @param res
     * @param resource
     * @return
     */
    private Map<String, Object> checkByButtonCode(Map<String, Object> res, Resource resource){
        Resource r = mapper.selectByButtonCode(resource);
        if (null != r && !r.getId().equals(resource.getId())) {
            res.put("flag", true);
            if(r.getCode().equals(resource.getCode())){
                res.put("msg", "codeExist");
            }else {
                res.put("msg", "buttonCodeExist");
            }
        }else{
            res.put("flag", false);
        }
        return res;
    }

    /**
     * 目录全局不重名
     * @param res
     * @param resource
     * @return
     */
    private Map<String, Object> checkByName(Map<String, Object> res, Resource resource){
        Resource r = mapper.selectByName(resource);
        if (null != r && !r.getId().equals(resource.getId())) {
            res.put("flag", true);
            if(r.getCode().equals(resource.getCode())){
                res.put("msg", "codeExist");
            }else{
                res.put("msg", "nameExist");
            }
        }else{
            res.put("flag", false);
        }
        return res;
    }
    @Override
    public Map<String, Object> updateStatus(Map<String, String> params) {
        String resourceIds = params.get("resourceIds");
        String status = params.get("status");
        int num = mapper.updateStatus(Arrays.asList(resourceIds.split(",")), status);
        Map<String, Object> res = new HashMap<>();
        res.put("flag", num > 0);
        return res;
    }
}
