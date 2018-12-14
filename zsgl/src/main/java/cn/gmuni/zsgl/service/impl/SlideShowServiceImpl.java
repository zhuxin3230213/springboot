package cn.gmuni.zsgl.service.impl;

import cn.gmuni.zsgl.dao.SlideShowRepository;
import cn.gmuni.zsgl.service.SlideShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * @Author:ZhuXin
 * @Description: 轮播图业务类
 * @Date:Create in 14:42 2018/6/29
 * @Modified By:
 **/
@Transactional
@Service
public class SlideShowServiceImpl implements SlideShowService {

    @Autowired
    SlideShowRepository slideShowRepository;


    /**
     * 获取轮播图列表
     *
     * @return
     */
    @Override
    public List<Map> listSlideShows() {
        return slideShowRepository.listSlideShows();
    }

}
