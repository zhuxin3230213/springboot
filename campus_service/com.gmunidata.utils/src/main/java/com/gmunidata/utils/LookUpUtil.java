package com.gmunidata.utils;

import cn.gmuni.lookup.container.LookupCache;
import cn.gmuni.lookup.model.LookupTree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LookUpUtil {

    //根据name获取code
    public static String codeByName(String value,String code,Map<String,LookupTree> lookups){
        LookupTree tree = lookups.get(value);
        List<LookupTree> children = tree.getChildren();
        String str = null;
        for (LookupTree lookupTree:children) {
            if (lookupTree.getCode().equals(code)){
                str = lookupTree.getName();
            }
        }
        return  str;
    }

    //根据code获取name
    public static String nameByCode(String value,String name,Map<String,LookupTree> lookups){
        LookupTree tree = lookups.get(value);
        List<LookupTree> children = tree.getChildren();
        String str = "无";
        for (LookupTree lookupTree:children) {
            if (lookupTree.getName().equals(name)){
                str = lookupTree.getCode();
            }
        }
        return  str;
    }
}
