package cn.gmuni.zsgl.service.impl;

import cn.gmuni.zsgl.dao.UpdateClickThroughRepository;
import cn.gmuni.zsgl.entity.UpdateClickThrough;
import cn.gmuni.zsgl.service.UpdateClickThroughService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @Author:ZhuXin
 * @Description: 点击次数业务类
 * @Date:Create in 14:32 2018/7/19
 * @Modified By:
 **/
@Transactional
@Service
public class UpdateClickThroughServiceImpl implements UpdateClickThroughService {

    @Autowired
    UpdateClickThroughRepository updateClickThroughRepository;


    /**
     * 获取点击次数
     * @param articleId
     * @param type
     * @return
     */
    @Override
    public UpdateClickThrough clickThrough(String articleId, String type) {
        return updateClickThroughRepository.getByArticleIdAndType(articleId,type);
    }

    /**
     * 根据文章id与type类型获取点击次数
     * type： 0：content 1：pnr
     *
     * @param articleId
     * @param type
     * @return
     */
    @Override
    public UpdateClickThrough clickAdd(String articleId, String type) {
        if (!StringUtils.isEmpty(articleId) && !StringUtils.isEmpty(type)) {
            Integer integer = updateClickThroughRepository.updateClickThrough(articleId, type);
            return updateClickThroughRepository.getByArticleIdAndType(articleId, type);
        } else {
            return null;
        }

    }

    /**
     * 根据id获取对象
     *
     * @param id
     * @return
     */
    @Override
    public UpdateClickThrough getById(String id) {
        return updateClickThroughRepository.getUpdateClickThroughById(id);
    }
}
