package cn.gmuni.org.service.impl;

import cn.gmuni.org.mapper.DepartmentMapper;
import cn.gmuni.org.model.Department;
import cn.gmuni.org.model.DeptPart;
import cn.gmuni.org.service.IDepartmentService;
import cn.gmuni.org.util.DlevelCodeUtil;
import cn.gmuni.utils.IdGenerator;
import cn.gmuni.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class DepartmentServiceImpl implements IDepartmentService {

    @Autowired
    DepartmentMapper mapper;

    @Override
    public Department addDept(Department dept) {
        dept.setCreateTime(new Date());
        dept.setId(IdGenerator.getId());
        String maxDlevelCode = mapper.getMaxDlevelCodeByPid(dept.getParentId());
        String dlevelCode = "";
        Department department = mapper.selectByPrimaryKey(dept.getParentId());
        if (StringUtils.isEmpty(maxDlevelCode)) {
            dlevelCode = department.getDlevelCode() + "001";
        } else {
            dlevelCode = DlevelCodeUtil.generateDlevelCode(maxDlevelCode);
        }
        dept.setDlevel(department.getDlevel() + 1);
        dept.setDlevelCode(dlevelCode);
        List<String> partsUserIds = dept.getPartsUsers();
        List<DeptPart> deptParts = new ArrayList<>();
        buildPart(dept, partsUserIds, deptParts);
        int res = mapper.insert(dept);
        if(res > 0 && deptParts.size() > 0){
            mapper.addDeptParts(deptParts);
        }
        return res == 1 ? dept : null;
    }

    @Override
    public Map<String, Object> delDept(String deptId) {
        Map<String, Object> res = new HashMap<>();
        List<String> ids = Arrays.asList(deptId.split(","));
        //部门包含下属不允许删除
        long c = mapper.countByPids(ids);
        if (c > 0) {
            res.put("flag", false);
            res.put("msg", "hasChildren");
            return res;
        }
        //部门包含人员不可删除
        c = mapper.countUserByPids(ids);
        if (c > 0) {
            res.put("flag", false);
            res.put("msg", "hasUser");
            return res;
        }
        int num = mapper.deleteByIdsIn(ids);
        if(num > 0){
            mapper.delDeptPartsByDeptIds(ids);
        }
        res.put("flag", num > 0 ? true : false);
        return res;
    }

    @Override
    public Object getAllDepts() {
        List<Department> depts = mapper.selectAll();
        List<Map> result = new ArrayList<>();
        for (int i = 0; i < depts.size(); i++) {
            Department dept = depts.get(i);
            if (null == dept.getId() || "-1".equals(dept.getId())) {
                Map<String, Object> map = parseDept(dept);
                result.add(map);
                depts.remove(dept);
                getDeptTree(depts, (List<Map>) map.get("children"), map);
                break;
            }
        }
        return result;
    }

    private boolean getDeptTree(List<Department> depts, List<Map> children, Map<String, Object> parent) {
        for (int i = 0; i < depts.size(); i++) {
            Department dept = depts.get(i);
            if (parent.get("id").equals(dept.getParentId())) {
                Map<String, Object> map = parseDept(dept);
                children.add(map);
                getDeptTree(depts, (List<Map>) map.get("children"), map);
            }
        }
        return true;
    }

    private Map<String, Object> parseDept(Department dept) {
        Map<String, Object> node = new HashMap<>();
        node.put("id", dept.getId());
        node.put("name", dept.getName());
        node.put("simpleName", dept.getSimpleName());
        node.put("dlevel", dept.getDlevel());
        node.put("dlevelCode", dept.getDlevelCode());
        node.put("type", dept.getType());
        node.put("parentId", dept.getParentId());
        node.put("children", new ArrayList<>());
        return node;
    }

    @Override
    public Department updateDept(Department dept) {
        int res =  mapper.updateNameSimpleName(dept);
        if(res > 0){
            List<String> partsUserIds = dept.getPartsUsers();
            List<DeptPart> deptParts = new ArrayList<>();
            buildPart(dept, partsUserIds, deptParts);
            List<String> ids = new ArrayList<>();
            ids.add(dept.getId());
            mapper.delDeptPartsByDeptIds(ids);
            if(deptParts.size() > 0){
                mapper.addDeptParts(deptParts);
            }
        }
        return res > 0 ? dept : null;
    }

    private void buildPart(Department dept, List<String> partsUserIds, List<DeptPart> deptParts) {
        for (int i = 0; i < partsUserIds.size(); i++) {
            DeptPart part = new DeptPart();
            part.setId(IdGenerator.getId());
            part.setDeptId(dept.getId());
            part.setUserId(partsUserIds.get(i));
            deptParts.add(part);
        }
    }

    @Override
    public PageInfo<Department> getDeptListById(Map<String, String> params) {
        PageUtils page = PageUtils.getPage(params);
        Map<String, Object> queryPara = new HashMap<>();
        queryPara.put("code", params.get("code"));
        queryPara.put("name", params.get("name"));
        queryPara.put("pid", params.get("deptId"));
        PageHelper.startPage(page.getPage(),page.getSize());
        return  new PageInfo<>(mapper.selectByPid(queryPara));
    }


    @Override
    public Department getDeptById(String deptId) {
        return mapper.selectByPrimaryKey(deptId);
    }

    @Override
    public Map<String, Object> checkNameExist(Map<String, String> params) {
        String id = params.get("id");
        String name = params.get("name");
        String code = params.get("code");
        String parentId = params.get("parentId");
        Map<String, Object> res = new HashMap<>();
        Department d;
        //一级机构全部不重复
        if (null == id) {
            id = "";
        }
        try{
            if ("-1".equals(parentId)) {
                d = mapper.getByName(name);
                if (null != d && !d.getId().equals(id)) {
                    return getRes(res, "nameExist");
                }
            } else {
                Department parent = mapper.selectByPrimaryKey(parentId);
                String firstDlevelCode = parent.getDlevelCode().substring(0, 6);
                d = mapper.selectByNameAndDlevelCodeLike(name, firstDlevelCode);
                if (null != d && !d.getId().equals(id)) {
                    return getRes(res, "nameExist");
                }
            }
            d = mapper.getByCode(code);
            if (null != d && !d.getId().equals(id)) {
                return getRes(res, "codeExist");
            }
        }catch (Exception e){
            return getRes(res, "nameExist");
        }
        res.put("flag", false);
        return res;
    }

    private Map<String, Object> getRes(Map<String, Object> res, String msg){
        res.put("flag", true);
        res.put("msg", msg);
        return res;
    }

    @Override
    public Map<String, Object> updateStatus(String deptId, String status) {
        Map<String, Object> res = new HashMap<>();
        res.put("flag", mapper.updateStatus(deptId, status) > 0 ? true : false);
        return res;
    }

    @Override
    public Department transferDept(String deptId, String parentId) {
        String maxDlevelCode = mapper.getMaxDlevelCodeByPid(parentId);
        String dlevelCode = "";
        Department department = mapper.selectByPrimaryKey(parentId);
        if (StringUtils.isEmpty(maxDlevelCode)) {
            dlevelCode = department.getDlevelCode() + "001";
        } else {
            dlevelCode = DlevelCodeUtil.generateDlevelCode(maxDlevelCode);
        }
        Long dlevel = department.getDlevel();
        return mapper.updateParent(deptId, parentId, dlevelCode, dlevel) > 0 ? mapper.selectByPrimaryKey(deptId) : null;
    }
    @Override
    public List<Department> listPartDeptByUserId(Map<String, String> params) {
        return mapper.listPartDeptByUserId(params.get("userId"));
    }
    @Override
    public Map<String, Object> savePartInfos(Map<String, String> params){
        Map<String, Object> res = new HashMap<>();
        String userId = params.get("userId");
        mapper.delDeptPartsByUserIds(Arrays.asList(userId.split(",")));
        String deptIds = params.get("deptIds");
        if(!StringUtils.isEmpty(deptIds)){
            String[] deptIdArray = deptIds.split(",");
            List<DeptPart> deptParts = new ArrayList<>();
            for (int i = 0; i < deptIdArray.length; i++) {
                DeptPart part = new DeptPart();
                part.setId(IdGenerator.getId());
                part.setDeptId(deptIdArray[i]);
                part.setUserId(userId);
                deptParts.add(part);
            }
            mapper.addDeptParts(deptParts);
            res.put("flag", true);
        }else{
            res.put("flag", false);
        }
        return res;
    }
}
