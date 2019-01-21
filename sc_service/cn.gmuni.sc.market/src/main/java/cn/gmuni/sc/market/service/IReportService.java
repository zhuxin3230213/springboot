package cn.gmuni.sc.market.service;

import cn.gmuni.sc.base.response.Content;
import cn.gmuni.sc.market.model.Report;

import java.util.List;
import java.util.Map;

public interface IReportService {
    //添加举报信息
    Map<String, Object> addReport(Report report);

    //删除商品信息根据举报信息
    Content delMarketByReport(List<String> list);

    //修改商品信息的状态
    Content updateMarketStatus(Map<String,Object> params);

    //列表举报信息
    List<Map<String,Object>> listReport(Map<String,Object> params);

    //获取举报信息对应的商品信息详情
    Map<String,Object> getMarket(String id);
}
