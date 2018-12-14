package cn.gmuni.zsgl.service.impl;

import cn.gmuni.zsgl.dao.FacultyProfessionalRepository;
import cn.gmuni.zsgl.entity.FacultyProfessional;
import cn.gmuni.zsgl.service.FacultyProfessionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:ZhuXin
 * @Description: 院系专业业务类
 * @Date:Create in 11:47 2018/5/28
 * @Modified By:
 **/
@Transactional
@Service
public class FacultyProfessionServiceImpl implements FacultyProfessionService {

    @Autowired
    FacultyProfessionalRepository facultyProfessionalRepository;

    /**
     * 根据deptId查询学科专业对应的id
     *
     * @param deptId
     * @return
     */
    @Override
    public String getFapIdByDeptId(String deptId) {
        if (!StringUtils.isEmpty(deptId)) {
            return facultyProfessionalRepository.getFapIdByDeptId(deptId);
        } else {
            return null;
        }
    }

    /**
     * 根据deptId查询学科专业对应的名称
     *
     * @param deptId
     * @return
     */
    @Override
    public String getNameByDeptId(String deptId) {
        if (!StringUtils.isEmpty(deptId)) {
            return facultyProfessionalRepository.getNameByDeptId(deptId);
        } else {
            return null;
        }
    }

    /**
     * 获取院系专业列表
     *
     * @return
     */
    @Override
    public List<FacultyProfessional> listFaps() {
        return facultyProfessionalRepository.findAll();
    }

    /**
     * 获取院系专业的树型结构
     *
     * @return
     */
    @Override
    public List<Map> facpTree() {
        List<Map> maps = facultyProfessionalRepository.listFaps();
        List<Map> res = new ArrayList<>();
        return getTree(maps, res);
    }

    /**
     * 根据招生status状态获取学科专业列表
     * 状态：0招生,1当年不招生
     *
     * @param status
     * @return
     */
    @Override
    public List<Map> facpTreeByStatus(String status) {
        if (!StringUtils.isEmpty(status)) {
            List<Map> maps = facultyProfessionalRepository.listFapsByStatus(status);
            List<Map> res = new ArrayList<>();
            return getTree(maps, res);
        } else {
            return null;
        }

    }

    /**
     * 获取学科专业列表
     *
     * @return
     */
    @Override
    public List<Map> listFacp() {
        List<Map> maps = facultyProfessionalRepository.listFap();
        List<Map> res = new ArrayList<>();
        return getTree(maps, res);
    }

    /**
     * 根据type类型和招生状态获取学科专业列表
     * 类型：文理艺体，从字典表获取
     * 状态：0招生,1当年不招生
     *
     * @param type
     * @param status
     * @return
     */
    @Override
    public List<Map> facpTreeByTypeAndStatus(String type, String status) {
        if (!StringUtils.isEmpty(type) && !StringUtils.isEmpty(status)) {
            List<Map> maps = facultyProfessionalRepository.listFapsByTypeAndStatus(type, status);
            List<Map> res = new ArrayList<>();
            return getTree(maps, res);
        } else {
            return null;
        }

    }

    /**
     * 根据id查询学科专业
     *
     * @param fapId
     * @return
     */
    @Override
    public Map getById(String fapId) {
        if (!StringUtils.isEmpty(fapId)) {
            return facultyProfessionalRepository.getByFapId(fapId);
        } else {
            return null;
        }
    }


    /**
     * 获取学科专业树型方法
     *
     * @param maps
     * @param res
     * @return
     */
    private List<Map> getTree(List<Map> maps, List<Map> res) {
        for (int i = 0; i < maps.size(); i++) {
            Map map = new HashMap<>(maps.get(i));
            if ("xy".equals(map.get("faculty"))) {
                List<Map> children = new ArrayList<>();
                for (int j = 0; j < maps.size(); j++) {
                    Map m = maps.get(j);
                    if (!m.get("dLevel_code").equals(map.get("dLevel_code"))
                            && m.get("dLevel_code").toString().indexOf(map.get("dLevel_code").toString()) == 0) {
                        children.add(m);
                    }
                }
                map.put("children", children);
                res.add(map);
            }
        }
        return res;
    }

}
