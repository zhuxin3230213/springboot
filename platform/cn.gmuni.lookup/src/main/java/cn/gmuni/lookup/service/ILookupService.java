package cn.gmuni.lookup.service;

import cn.gmuni.lookup.model.Lookup;
import cn.gmuni.lookup.model.LookupTree;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface ILookupService {
    List<LookupTree> listTree();

    PageInfo<Lookup> listAllByPid(Map<String,String> params);

    Lookup add(Lookup lookup);

    Lookup update(Lookup lookup);

    Map<String,Object> delete(List<String> ids);

    Map<String,Object> checkNameCode(Lookup lookup);



    Map<String,Object> updateStatus(List<String> ids, String status);
}
