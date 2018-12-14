package cn.gmuni.sysmenu.service;

import cn.gmuni.sysmenu.model.Resource;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface IResourceService {

    /**
     * 新增菜单
     *
     * @param resource
     * @return
     */
    public Resource addResource(Resource resource);

    /**
     * 获取菜单树
     *
     * @return
     */
    public Object getAllResource();
    public Object getAllResourceForPriv();
    /**
     * 修改菜单
     *
     * @param resource
     * @return
     */
    public Resource updateResource(Resource resource);
    /**
     * 根据id获下级菜单
     *
     * @return
     */
    public PageInfo<Resource> getResourceListById(Map<String, String> params);
    /**
     * 删除菜单
     *
     * @param params
     * @return
     */
    public Map<String, Object> delResource(Map<String, String> params);
    /**
     * 根据id获菜单
     *
     * @param params
     * @return
     */
    public Resource getResourceById(Map<String, String> params);
    /**
     * 校验重名
     *
     * @param resource
     * @return
     */
    public Map<String, Object> checkNameExist(Resource resource);

    /**
     * 启用禁用
     *
     * @param params
     * @return
     */
    public Map<String, Object> updateStatus(Map<String, String> params);

    /**
     * 构建资源树
     * @param resources
     * @return
     */
    public List<Map> parseListToTree(List<Resource> resources);
}
