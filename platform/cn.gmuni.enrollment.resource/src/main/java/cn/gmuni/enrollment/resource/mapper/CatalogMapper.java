package cn.gmuni.enrollment.resource.mapper;

import cn.gmuni.enrollment.resource.model.Catalog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CatalogMapper {

    List<Catalog> listAllCatalog();

    List<Catalog> listAllCatalogByPid(Map<String,Object> queryPara);

    int checkName(@Param("name") String name,@Param("parentId") String parentId,@Param("id") String id);

    int checkCode(@Param("code") String code,@Param("id") String id);

    int insert(Catalog catalog);

    int countCatalogByPid(Map<String,Object> queryPara);

    int update(Catalog catalog);

    int countChildrenByIds(List<String> ids);

    int countResourceByPids(List<String> idarray);

    int delete(List<String> idarray);
}
