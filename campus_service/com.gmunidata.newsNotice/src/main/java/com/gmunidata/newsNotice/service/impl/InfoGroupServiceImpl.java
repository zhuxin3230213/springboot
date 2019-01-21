package com.gmunidata.newsNotice.service.impl;

import cn.gmuni.utils.IdGenerator;
import cn.gmuni.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.gmunidata.base.response.Content;
import com.gmunidata.newsNotice.mapper.InfoGroupMapper;
import com.gmunidata.newsNotice.mapper.MessageMapper;
import com.gmunidata.newsNotice.model.GroupPeople;
import com.gmunidata.newsNotice.model.InfoGroup;
import com.gmunidata.newsNotice.service.IInfoGroupService;
import com.gmunidata.newsNotice.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InfoGroupServiceImpl implements IInfoGroupService {

    @Autowired
    InfoGroupMapper mapper;

    @Override
    public List<Map<String, Object>> getClassTree(String userName) {
        List<Map<String,Object>> listSubject = new ArrayList<>();
        if (userName==null || userName==""){
            return null;
        }
            List<String> list = new ArrayList<>();
            if(!userName.equals("admin")){
                List<String> deptName =mapper.getDeptName(userName);
                List<String> deptNamePart =mapper.getDeptPartName(userName);
                list.addAll(deptName);
                list.addAll(deptNamePart);
                if (list.size() == 0){
                    return null;
                }
                listSubject = mapper.getDeptAndSubject(list);
                }else {
                listSubject = mapper.getAllDeptAndSubject();
                 }
            List<Map<String,Object>> listClass = mapper.getAllClass();
            List<Map<String, Object>> result = new ArrayList<>();
            for (int i = 0; i < listSubject.size(); i++) {
                Map map = new TreeMap(listSubject.get(i));
                if (map.get("faculty").equals("xy")) {
                    List<Map> children = new ArrayList<>();
                    for (int j = 0; j < listSubject.size(); j++) {
                        Map m = listSubject.get(j);
                        if (m.get("parentId").equals(map.get("deptId"))){
                            List<Map> classInfo = new ArrayList<>();
                            for (int k = 0; k < listClass.size(); k++) {
                                Map temp = listClass.get(k);
                                if (temp.get("subjectId").equals(m.get("id"))){
                                    classInfo.add(temp);
                                }
                            }
                            m.put("children",classInfo);
                            children.add(m);
                        }
                    }
                    map.put("children", children);
                    result.add(map);
                }
            }
            return result;
    }

    @Override
    public List<Map<String, Object>> selectGroup(Map<String,Object> params) {
        return mapper.selectGroup(params);
    }

    @Override
    public List<Map<String, Object>> getGroupStudent(Map<String,Object> params) {
        PageUtils page = PageUtils.getPage(params);
        PageHelper.startPage(page.getPage(), page.getSize());
        if(params.get("flag").equals("zy") || params.get("flag").equals("bj")){
        return mapper.getGroupStudentByStudent(params);
        }else if(params.get("flag").equals("group")){
            return mapper.getGroupStudentByGroup(params);
        }else{
            return null;
        }
    }

    @Override
    public Content addInfoGroup(InfoGroup infoGroup) {
        Content con = new Content();
        if(mapper.checkCode(infoGroup)!=0){
            con.setFlag(1);
            con.setMessage("编码重复!");
            return con;
        }

        if (infoGroup.getCodes()==null || infoGroup.getCodes()==""){
            con.setFlag(1);
            con.setMessage("组中人员为空，无法创建！");
            return con;
        }
        List<String> codes = Arrays.asList(infoGroup.getCodes().split(","));
        infoGroup.setId(IdGenerator.getId());
        infoGroup.setCreateTime(new Date());
        int f = mapper.addInfoGroup(infoGroup) == 1 ? 0 : 1;
        con.setFlag(f);
        if (f == 0){
               List<GroupPeople> peoples = new ArrayList<>();
               for (int i = 0; i < codes.size() ; i++) {
                   GroupPeople groupPeople = new GroupPeople();
                   groupPeople.setId(IdGenerator.getId());
                   groupPeople.setGroupCode(infoGroup.getCode());
                   groupPeople.setStudentCode(codes.get(i));
                   groupPeople.setCreatePeople(infoGroup.getCreatePeople());
                   peoples.add(groupPeople);
               }
               mapper.addGroupPeople(peoples);
            con.setMessage("添加成功！");
           }else{
            con.setMessage("添加失败！");
        }

        return con;
    }

    @Override
    public Content updateGroupPeople(Map<String,Object> params){
        Content con = new Content();
        String groupCode = (String)params.get("groupCode");
        String createPeople = (String)params.get("createPeople");
        List<String> codes = (List<String>) params.get("codes");
        if (codes.size() != 0){
            mapper.deleteGroupPeople(params);
            List<GroupPeople> peoples = new ArrayList<>();
            for (int i = 0; i < codes.size() ; i++) {
                GroupPeople groupPeople = new GroupPeople();
                groupPeople.setId(IdGenerator.getId());
                groupPeople.setGroupCode(groupCode);
                groupPeople.setStudentCode(codes.get(i));
                groupPeople.setCreatePeople(createPeople);
                peoples.add(groupPeople);
            }
            mapper.addGroupPeople(peoples);
            con.setFlag(0);
            con.setMessage("修改组成员成功！");
        }else{
            con.setFlag(1);
            con.setMessage("组中人员为空！");
        }
        return con;
    }

    @Override
    public Content delGroup(Map<String, Object> params) {
       Content con =new Content();
       try {
           mapper.deletePeopleByGroup(params);
           mapper.deleteGroup(params);
       }catch (Exception e){
           con.setFlag(1);
           con.setMessage("系统异常！");
       }
        con.setFlag(0);
        con.setMessage("删除成功");
        return con;
    }
}
