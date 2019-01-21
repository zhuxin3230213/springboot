package cn.gmuni.sc.market.mapper;

import cn.gmuni.sc.market.model.Report;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public interface ReportMapper {

    int addReport(Report report);

    int updateMarketStatus(Map<String,Object> params);

    List<Map<String,Object>> listReport(Map<String,Object> params);

    Map<String,Object> getMarket(String id);

}
