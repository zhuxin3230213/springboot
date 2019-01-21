package cn.gmuni.sc.market.service;

import cn.gmuni.sc.base.response.BaseResponse;
import cn.gmuni.sc.base.response.Content;
import cn.gmuni.sc.market.model.Market;

import java.util.List;
import java.util.Map;

public interface IMarketService {
    //新增商品信息
    Map<String,Object> addMarket(Market market);

    //修改信息
  Map<String,Object> updateMarket(Market market);

    //修改出售状态
    Map<String,Object> updateSaleStatus(Map<String,Object> params);

    //删除商品信息
    Map<String,Object> delMarket(List<String> list);

    //展示商品信息
    List<Map<String,Object>> listMarket(Map<String,Object> params);

    //获取商品详细信息
    Map<String,Object> getMarketById(String id);

}
