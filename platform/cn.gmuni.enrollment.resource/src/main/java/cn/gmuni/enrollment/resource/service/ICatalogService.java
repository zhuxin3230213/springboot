package cn.gmuni.enrollment.resource.service;

import cn.gmuni.enrollment.resource.model.Catalog;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface ICatalogService {

    List<Catalog> listAllCatalog();

    PageInfo<Catalog> listAllCatalogByPid(Map<String,Object> params);

    boolean checkName(Catalog catalog);

    boolean checkCode(Catalog catalog);

    Catalog saveCatalog(Catalog catalog);

    Catalog editCatalog(Catalog catalog);

    Map<String,Object> removeCatalog(String[] ids);
}
