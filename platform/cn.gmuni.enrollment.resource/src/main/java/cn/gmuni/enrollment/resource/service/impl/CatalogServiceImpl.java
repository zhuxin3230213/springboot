package cn.gmuni.enrollment.resource.service.impl;

import cn.gmuni.enrollment.resource.mapper.CatalogMapper;
import cn.gmuni.enrollment.resource.model.Catalog;
import cn.gmuni.enrollment.resource.service.ICatalogService;
import cn.gmuni.utils.IdGenerator;
import cn.gmuni.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CatalogServiceImpl implements ICatalogService {

    @Autowired
    CatalogMapper mapper;

    @Override
    public List<Catalog> listAllCatalog() {
        List<Catalog> cats = mapper.listAllCatalog();

        return buildTree(cats,null);
    }

    @Override
    public PageInfo<Catalog> listAllCatalogByPid(Map<String, Object> params) {

        PageUtils page = PageUtils.getPage(params);
        Map<String, Object> queryPara = new HashMap<>();
        queryPara.put("code", params.get("code"));
        queryPara.put("name", params.get("name"));
        queryPara.put("parentId", params.get("parentId"));
//        Map<String,Object> result = new HashMap<>();
//        result.put("rows",impl.listAllCatalogByPid(queryPara));
//        result.put("total",impl.countCatalogByPid(queryPara));
//        return result;
        PageHelper.startPage(page.getPage(),page.getSize());
        return new PageInfo<>(mapper.listAllCatalogByPid(queryPara));
    }

    @Override
    public boolean checkName(Catalog catalog) {
        int count = mapper.checkName(catalog.getName(),catalog.getParentId(),catalog.getId());
        return count > 0;
    }

    @Override
    public boolean checkCode(Catalog catalog) {
        int count = mapper.checkCode(catalog.getCode(),catalog.getId());
        return count > 0;
    }

    @Override
    public Catalog saveCatalog(Catalog catalog) {
        catalog.setCreateTime(new Date());
        catalog.setId(IdGenerator.getId());
        return mapper.insert(catalog)>0?catalog:null;
    }

    @Override
    public Catalog editCatalog(Catalog catalog) {
        return mapper.update(catalog)>0?catalog:null;
    }

    @Override
    public Map<String, Object> removeCatalog(String[] ids) {
        List<String> idarray = Arrays.asList(ids);
        //1.校验是否有子节点
        int count = mapper.countChildrenByIds(idarray);
        Map<String,Object> result = new HashMap<>();
        if(count>0){
            result.put("flag",false);
            result.put("msg","hasChildren");
            return result;
        }
        count = mapper.countResourceByPids(idarray);
        if(count>0){
            result.put("flag",false);
            result.put("msg","hasResource");
            return result;
        }
        count = mapper.delete(idarray);
        if(count>0){
            result.put("flag",true);
        }else{
            result.put("flag",false);
            result.put("msg","removeError");
        }
        return result;
    }

    private List<Catalog> buildTree(List<Catalog> cats,String parentId){
        List<Catalog> results = new ArrayList<>();
        for(Catalog cat : cats){
             if(parentId==null){
                    if(cat.getParentId()==null){
                        cat.setChildren(buildTree(cats,cat.getId()));
                        results.add(cat);
                    }
             }else{
                 if(parentId.equals(cat.getParentId())){
                     cat.setChildren(buildTree(cats,cat.getId()));
                     results.add(cat);
                 }
             }
        }
        return results;
    }
}
