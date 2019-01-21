package cn.gmuni.sc.lost.service;

import cn.gmuni.sc.base.response.BaseResponse;
import cn.gmuni.sc.lost.model.LostFound;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface ILostFoundService {
    BaseResponse<LostFound> add(LostFound info, HttpServletRequest request);

    BaseResponse<Map<String, String>> delete(Map<String, String> params);
    BaseResponse<Map<String, String>> finish(Map<String, String> params);

    BaseResponse<Map<String, String>> edit(LostFound info);

    BaseResponse<List<LostFound>> list(Map<String,Object> params);
    BaseResponse<LostFound> get(String id);
}
