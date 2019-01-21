package cn.gmuni.sc.pay.service.impl;

import cn.gmuni.sc.pay.service.INetworkFeeService;

public abstract class BaseNetworkFeeService implements INetworkFeeService {


    /**
     * 获取缴费明细，可通用方法
     *
     * @param obj
     * @return
     */
    @Override
    public Object listPayDetails(Object obj) {
        return null;
    }
}
