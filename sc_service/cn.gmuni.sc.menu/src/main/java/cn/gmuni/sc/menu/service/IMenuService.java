package cn.gmuni.sc.menu.service;

import cn.gmuni.sc.base.response.BaseResponse;

import java.util.Map;

public interface IMenuService {

    BaseResponse<Map<String, Object>> getMenuItem(Map<String,String> params);

}
