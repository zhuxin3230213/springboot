package cn.gmuni.sc.pay.mapper;

import cn.gmuni.sc.pay.model.NetPackage;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public interface NetPackageMapper {

    //查询套餐
    List<NetPackage> getNetPackage(Map<String,Object> params);
    //添加套餐
    int addNetPackage(NetPackage netPackage);
    //修改套餐
    int updateNetPackage(NetPackage netPackage);
    //删除套餐
    int delNetPackage(List<String> ids);
    //校验是否重复
    int checkNetPackage(NetPackage netPackage);
}
