package cn.gmuni.sc.market.service.impl;

import cn.gmuni.sc.base.response.Content;
import cn.gmuni.sc.market.mapper.LeaveMessageMapper;
import cn.gmuni.sc.market.mapper.MarketMapper;
import cn.gmuni.sc.market.mapper.ReportMapper;
import cn.gmuni.sc.market.model.Report;
import cn.gmuni.sc.market.service.IReportService;
import cn.gmuni.sc.user.utils.UserUtils;
import cn.gmuni.utils.IdGenerator;
import cn.gmuni.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReportServiceImpl implements IReportService {

    @Autowired
    ReportMapper mapper;

    @Autowired
    MarketMapper marketMapper;

    @Autowired
    LeaveMessageMapper leaveMessageMapper;

    @Override
    public Map<String, Object> addReport(Report report) {
        Map<String,Object> map = new HashMap<>();
        String id =IdGenerator.getId();
        report.setId(id);
        report.setReportUser(UserUtils.getLoginUserInfo().getIndentifier());
        report.setCreateTime(new Date());
       int flag=  mapper.addReport(report) > 0 ? 0 : 1;
       if (flag == 0){
           map.put("flag",true);
       }else {
           map.put("flag",false);
       }
        return map;
    }

    @Override
    public Content delMarketByReport(List<String> list) {
        Content con  =new Content();
        int flag =   marketMapper.delMarket(list) > 0 ? 0 : 1;
        if (flag == 0){
            marketMapper.delLeaveMessageByMerked(list);
            con.setMessage("删除成功");
        }else {
            con.setMessage("删除失败");
        }
        return con;
    }

    @Override
    public Content updateMarketStatus(Map<String, Object> params) {
        Content con  =new Content();
        int flag =   mapper.updateMarketStatus(params) > 0 ? 0 : 1;
        if (flag == 0){
            con.setMessage("修改成功");
        }else {
            con.setMessage("修改失败");
        }
        return con;
    }

    @Override
    public List<Map<String, Object>> listReport(Map<String, Object> params) {
        PageUtils page = PageUtils.getPage(params);
        PageHelper.startPage(page.getPage(),page.getSize());
        return mapper.listReport(params);
    }

    @Override
    public Map<String, Object> getMarket(String id) {
        Map<String, Object> map = mapper.getMarket(id);
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
