package cn.gmuni.lookup.mapper;


import cn.gmuni.lookup.model.Lookup;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface LookupMapper {
    List<Lookup> listAllByPid(Map<String,String> params);

    int insert(Lookup lookup);

    int update(Lookup lookup);

    int delete(List<String> ids);

    int checkName(Lookup lookup);

    List<Lookup> listAll();

    int checkCode(Lookup lookup);

    int udpateStatus(@Param("ids") List<String> ids, @Param("status") String status);
}
