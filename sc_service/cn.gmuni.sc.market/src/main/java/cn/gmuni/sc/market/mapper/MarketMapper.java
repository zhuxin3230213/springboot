package cn.gmuni.sc.market.mapper;

import cn.gmuni.sc.market.model.Market;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public interface MarketMapper {

    int addMarket(Market market);

    int updateMarket(Market market);

    int updateSaleStatus(Map<String,Object> params);

    int delMarket(List<String> list);

    int delLeaveMessageByMerked(List<String> list);

    List<Map<String,Object>> listMarket(Map<String,Object> params);

    Map<String,Object> getMarketById(String id);



}
