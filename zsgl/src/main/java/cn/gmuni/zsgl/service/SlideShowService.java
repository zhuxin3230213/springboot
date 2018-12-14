package cn.gmuni.zsgl.service;

import java.util.List;
import java.util.Map;

/**
 * @Author:ZhuXin
 * @Description: 轮播图业务接口
 * @Date:Create in 14:41 2018/6/29
 * @Modified By:
 **/


public interface SlideShowService {

    /**
     * 获取轮播图列表
     *
     * @return
     */
    List<Map> listSlideShows();
}
