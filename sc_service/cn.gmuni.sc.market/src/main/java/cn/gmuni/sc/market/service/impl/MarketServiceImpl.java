package cn.gmuni.sc.market.service.impl;

import cn.gmuni.sc.base.response.BaseResponse;
import cn.gmuni.sc.base.response.Content;
import cn.gmuni.sc.market.mapper.LeaveMessageMapper;
import cn.gmuni.sc.market.mapper.MarketMapper;
import cn.gmuni.sc.market.model.Market;
import cn.gmuni.sc.market.service.IMarketService;
import cn.gmuni.sc.user.utils.UserUtils;
import cn.gmuni.utils.IdGenerator;
import cn.gmuni.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MarketServiceImpl implements IMarketService {

    @Autowired
    MarketMapper marketMapper;

    @Autowired
    LeaveMessageMapper leaveMessageMapper;

    @Override
    public Map<String,Object> addMarket(Market market) {
       String id = IdGenerator.getId();
        market.setId(id);
        Date date = new Date();
        market.setCreateTime(date);
        market.setSchoolCode(UserUtils.getLoginUser().getSchool());
        market.setUser(UserUtils.getLoginUserInfo().getIndentifier());
        market.setUpdateTime(date);
        market.setSaleStatus("0");
        market.setStatus("0");
       int flag = marketMapper.addMarket(market) > 0 ? 0 : 1;
       if (flag == 0){
           Map<String,Object> map = new HashMap<>();
           map.put("id",id);
           return marketMapper.listMarket(map).get(0);
       }
        return new HashMap<>();
    }

    @Override
    public Map<String,Object> updateMarket(Market market) {
        market.setUpdateTime(new Date());
        int flag  = marketMapper.updateMarket(market) > 0 ? 0 : 1;
        if (flag == 0){
            Map<String,Object> map = new HashMap<>();
            map.put("id",market.getId());
            return marketMapper.listMarket(map).get(0);
        }
        return new HashMap<>();
    }

    @Override
    public Map<String,Object> updateSaleStatus(Map<String, Object> params) {
        Map<String,Object> res =  new HashMap<>();
        params.put("updateTime",new Date());
        int flag = marketMapper.updateSaleStatus(params) > 0 ? 0 : 1;
         res.put("flag",flag);
        if (flag == 0){
            res.put("msg","修改成功");
        }else {
            res.put("msg","修改失败");
        }
        return res;
    }

    @Override
    public Map<String,Object> delMarket(List<String> list) {
        Map<String,Object> res =  new HashMap<>();
        int flag =  marketMapper.delMarket(list) > 0 ? 0 : 1;
        res.put("flag",flag);
        if (flag == 0){
            res.put("msg","删除成功");
        }else {
            res.put("msg","删除失败");
        }
        return res;
    }

    @Override
    public List<Map<String, Object>> listMarket(Map<String, Object> params) {
        return marketMapper.listMarket(params);
    }

    @Override
    public Map<String, Object> getMarketById(String id) {
        Map<String, Object> map = marketMapper.getMarketById(id);
        Map<String,Object> mapTemp = new HashMap<>();
        mapTemp.put("marketId",id);
        List<Map<String,Object>> res = leaveMessageMapper.getLeaveMessage(mapTemp);
        List<Map<String,Object>> result = new ArrayList<>();
        //获取所有留言
        for (Map<String,Object> temp:res) {
            if (temp.get("replier")==null || temp.get("replier").equals("")){
                result.add(temp);
            }
        }
        //组装留言信息即附属的回复信息
        for (Map<String,Object> temp:result) {
            List<Map<String,Object>> children = new ArrayList<>();
            for (Map<String,Object> temp1:res) {
                if (String.valueOf(temp1.get("replier")).equals(String.valueOf(temp.get("id")))){
                    children.add(temp1);
                }
            }
            temp.put("children",children);
        }
        map.put("list",result);
        return map;
    }
}
