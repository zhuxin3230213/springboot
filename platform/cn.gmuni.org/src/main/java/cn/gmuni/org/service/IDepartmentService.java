package cn.gmuni.org.service;

import cn.gmuni.org.model.Department;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.RequestBody;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

public interface IDepartmentService {
    /**
     * 新增部门
     * @param dept
     * @return
     */
    public Department addDept(Department dept);
    /**
     * 删除部门
     * @param deptId
     * @return
     */
    public Map<String, Object> delDept(String deptId);
    /**
     * 获取组织机构树
     * @return
     */
    public Object getAllDepts();
    /**
     * 修改部门
     * @param dept
     * @return
     */
    public Department updateDept(Department dept);
    /**
     * 根据id获取子部门
     * @param deptId
     * @return
     */
    public PageInfo<Department> getDeptListById(Map<String, String> params);
    /**
     * 根据id获组织机构
     * @param deptId
     * @return
     */
    public Department getDeptById(String deptId);
    /**
     * 校验重名
     * @param name
     * @param id
     * @return
     */
    public Map<String, Object> checkNameExist(Map<String, String> params);
    /**
     * 启用禁用
     * @param deptId
     * @param status 0：禁用，1:启用
     * @return
     */
    public Map<String, Object> updateStatus(String deptId, String status);
    /**
     * 部门调动
     * @param deptId
     * @param parentId
     * @return
     */
    public Department transferDept(String deptId, String parentId);
    public List<Department> listPartDeptByUserId(Map<String, String> params);
    public Map<String, Object> savePartInfos(Map<String, String> params);
}
