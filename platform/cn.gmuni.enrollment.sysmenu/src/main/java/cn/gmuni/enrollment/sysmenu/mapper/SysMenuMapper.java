package cn.gmuni.enrollment.sysmenu.mapper;

import cn.gmuni.enrollment.sysmenu.model.SysMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface SysMenuMapper {
    List<SysMenu> listAllSysMenu();

    List<SysMenu> listAllSysMenuByPid(Map<String,Object> queryPara);

    int checkName(@Param("name") String name, @Param("parentId") String parentId, @Param("id") String id);

    int checkCode(@Param("code") String code,@Param("id") String id);

    int insert(SysMenu catalog);

    int countSysMenuByPid(Map<String,Object> queryPara);

    int update(SysMenu catalog);

    int countChildrenByIds(List<String> ids);

    int delete(List<String> idarray);
}
