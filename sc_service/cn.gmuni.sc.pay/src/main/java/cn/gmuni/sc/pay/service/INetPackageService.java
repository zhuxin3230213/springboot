package cn.gmuni.sc.pay.service;

import cn.gmuni.sc.base.response.Content;
import cn.gmuni.sc.pay.model.NetPackage;

import java.util.List;
import java.util.Map;

public interface INetPackageService {

    /**
     * 查询套餐列表
     * @param params
     * @return
     */
    List<NetPackage> getNetPackage(Map<String,Object> params);

    /**
     * 添加套餐
     * @param netPackage
     * @return
     */
    Content addNetPackage(NetPackage netPackage);

    /**
     * 修改套餐
     * @param netPackage
     * @return
     */
    Content updateNetPackage(NetPackage netPackage);

    /**
     * 删除套餐
     * @param ids
     * @return
     */
    Content delNetPackage(List<String> ids);


}
