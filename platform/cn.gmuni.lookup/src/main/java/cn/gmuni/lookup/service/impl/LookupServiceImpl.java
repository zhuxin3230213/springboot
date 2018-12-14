package cn.gmuni.lookup.service.impl;

import cn.gmuni.lookup.container.LookupCache;
import cn.gmuni.lookup.mapper.LookupMapper;
import cn.gmuni.lookup.model.LookupTree;
import cn.gmuni.lookup.model.Lookup;
import cn.gmuni.lookup.service.ILookupService;
import cn.gmuni.utils.IdGenerator;
import cn.gmuni.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LookupServiceImpl implements ILookupService {

    @Autowired
    LookupMapper mapper;

    @Override
    public List<LookupTree> listTree() {
        List<Lookup> lookups =  mapper.listAll();
        List<LookupTree> lookupTrees = buildTree(lookups,"-1");
        LookupTree root = new LookupTree();
        root.setId("-1");
        root.setChildren(lookupTrees);
        root.setCode("root");
        root.setLevel(0);
        root.setName("数据字典");
        root.setStatus("1");
        root.setType("0");
        List<LookupTree> tree = new ArrayList<>();
        tree.add(root);
        return tree;
    }

    @Override
    public PageInfo<Lookup> listAllByPid(Map<String, String> params) {
        PageUtils page = PageUtils.getPage(params);
        PageHelper.startPage(page.getPage(),page.getSize());
        return new PageInfo<>(mapper.listAllByPid(params));
    }

    @Override
    public Lookup add(Lookup lookup) {
        lookup.setId(IdGenerator.getId());
        lookup.setCreateTime(new Date());
        boolean success = mapper.insert(lookup)>0;
        if(success){
            LookupCache.add(lookup);
        }
        return success?lookup:null;
    }

    @Override
    public Lookup update(Lookup lookup) {
        boolean success = mapper.update(lookup)>0;
        if(success){
            LookupCache.update(lookup);
        }
        return success?lookup:null;
    }

    @Override
    public Map<String, Object> delete(List<String> ids) {
        int count = mapper.delete(ids);
        Map<String,Object> result = new HashMap<>();
        if(count>0){
            result.put("flag",true);
            LookupCache.remove(ids);
        }else{
            result.put("flag",false);
        }
        return result;
    }

    @Override
    public Map<String, Object> checkNameCode(Lookup lookup) {
        int count = mapper.checkName(lookup);
        Map<String,Object> result = new HashMap<>();
        if(count>0){
            result.put("flag",false);
            result.put("msg","hasName");
        }else{
            count = mapper.checkCode(lookup);
            if(count>0){
                result.put("flag",false);
                result.put("msg","hasCode");
            }else{
                result.put("flag",true);
            }
        }


        return result;
    }

    @Override
    public Map<String, Object> updateStatus(List<String> ids, String status) {
        Map<String, Object> result = new HashMap<>();
        if(mapper.udpateStatus(ids,status)>0){
            result.put("flag",true);
            LookupCache.updateStatus(ids,status);
        }else{
            result.put("flag",false);
        }
        return result;
    }


    //构建字典树
    private List<LookupTree> buildTree(List<Lookup> menus,String parentId){
        List<LookupTree> results = new ArrayList<>();
        for(Lookup menu : menus){
            if((parentId==null && menu.getParentId()==null)||(parentId!=null && parentId.equals(menu.getParentId()))){
                LookupTree tree = new LookupTree();
                BeanUtils.copyProperties(menu,tree);
                tree.setChildren(buildTree(menus,menu.getId()));
                results.add(tree);
            }
        }
        return results;
    }
}
