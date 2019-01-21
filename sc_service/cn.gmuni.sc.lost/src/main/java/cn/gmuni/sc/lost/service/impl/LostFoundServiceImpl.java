package cn.gmuni.sc.lost.service.impl;

import cn.gmuni.sc.base.response.BaseResponse;
import cn.gmuni.sc.lost.mapper.LostFoundMapper;
import cn.gmuni.sc.lost.model.LostFound;
import cn.gmuni.sc.lost.service.ILostFoundService;
import cn.gmuni.sc.user.model.ThirdPartUser;
import cn.gmuni.sc.user.model.ThirdPartUserInfo;
import cn.gmuni.sc.user.utils.UserUtils;
import cn.gmuni.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class LostFoundServiceImpl implements ILostFoundService {

    @Autowired
    LostFoundMapper mapper;
    @Override
    public BaseResponse<LostFound> add(LostFound info, HttpServletRequest request) {
        BaseResponse<LostFound> res = new BaseResponse<>();
        info.setCreateTime(new Date());
        info.setId(IdGenerator.getId());
        info.setInfoStatus("0");
        ThirdPartUserInfo loginUserInfo = UserUtils.getLoginUserInfo();
        ThirdPartUser loginUser = UserUtils.getLoginUser();
        info.setUserInfo(loginUserInfo.getIndentifier());
        info.setUserImg(loginUser.getAvatar());
        info.setCampus(loginUser.getSchool());
        int i = mapper.add(info);
        info.setCreateTime(mapper.findOneById(info.getId()).getCreateTime());
        info.setUserInfo(loginUser.getName());
        res.setData(info);
        return res;
    }

    @Override
    public BaseResponse<Map<String, String>> delete(Map<String, String> params) {
        BaseResponse<Map<String, String>> res = new BaseResponse<>();
        Map<String, String> map = new HashMap<>();
        int i = mapper.delete(params.get("id"));
        map.put("flag", i > 0 ? "true" : "false");
        res.setData(map);
        return res;
    }
    @Override
    public BaseResponse<Map<String, String>> finish(Map<String, String> params) {
        BaseResponse<Map<String, String>> res = new BaseResponse<>();
        Map<String, String> map = new HashMap<>();
        int i = mapper.finish(params.get("id"));
        map.put("flag", i > 0 ? "true" : "false");
        res.setData(map);
        return res;
    }

    @Override
    public BaseResponse<Map<String, String>> edit(LostFound info) {
        BaseResponse<Map<String, String>> res = new BaseResponse<>();
        Map<String, String> map = new HashMap<>();
        int i = mapper.edit(info);
        map.put("flag", i > 0 ? "true" : "false");
        res.setData(map);
        return res;
    }

    @Override
    public BaseResponse<List<LostFound>> list(Map<String, Object> params) {
        ThirdPartUserInfo loginUserInfo = UserUtils.getLoginUserInfo();
        ThirdPartUser loginUser = UserUtils.getLoginUser();
        if(params.containsKey("isMine") && "isMine".equals(params.get("isMine"))){
            params.put("userInfo", loginUserInfo.getIndentifier());
        }
        params.put("campus", loginUser.getSchool());
        List<LostFound> list = mapper.list(params);
        BaseResponse< List<LostFound>> res = new BaseResponse<>();
        res.setData(list);
        return res;
    }

    @Override
    public BaseResponse<LostFound> get(String id){
        LostFound model = mapper.findOneById(id);
        BaseResponse<LostFound> res = new BaseResponse<>();
        if(mapper != null){
            res.setData(model);
        }else{
            res.setCode(BaseResponse.NONE_DATA);
            res.setMessage("数据查询失败");
        }
        return res;
    }
}
