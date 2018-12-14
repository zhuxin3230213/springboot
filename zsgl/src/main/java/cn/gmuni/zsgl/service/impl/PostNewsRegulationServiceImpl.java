package cn.gmuni.zsgl.service.impl;

import cn.gmuni.zsgl.dao.PostNewsRegulationRepository;
import cn.gmuni.zsgl.service.PostNewsRegulationService;
import cn.gmuni.zsgl.entity.PostNewsRegulation;
import cn.gmuni.zsgl.service.UpdateClickThroughService;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;


import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

import static cn.gmuni.zsgl.util.TemplateConfig.springTemplateEngine;

/**
 * @Author:ZhuXin
 * @Description: 公告、新闻资讯、报考指南业务接口
 * @Date:Create in 18:01 2018/5/28
 * @Modified By:
 **/
@Transactional
@Service
public class PostNewsRegulationServiceImpl implements PostNewsRegulationService {

    @Autowired
    PostNewsRegulationRepository postNewsRegulationRepository;

    @Autowired
    @Qualifier("updateClickThroughServiceImpl")
    UpdateClickThroughService updateClickThroughService;


    @Value("${content.imagePath}")
    private String imagePath;

    @Autowired
    EntityManager entityManager;

    /**
     * 获取公告、新闻、报考指南列表
     *
     * @return
     */
    @Override
    public List<PostNewsRegulation> listPNRs() {
        return postNewsRegulationRepository.findAll();
    }

    /**
     * 根据id查询信息
     *
     * @param id
     * @return
     */
    @Override
    public PostNewsRegulation getById(String id) {
        Session session = entityManager.unwrap(Session.class);
        if (!StringUtils.isEmpty(id)) {
            PostNewsRegulation pnr = postNewsRegulationRepository.getById(id);
            SpringTemplateEngine engine = springTemplateEngine();
            if (pnr != null) {
                String str = pnr.getContent();
                Context context = new Context();
                context.setVariable("imagePath", imagePath);
                session.evict(pnr);
                pnr.setContent(engine.process(str, context));
                return pnr;
            } else {
                return null;
            }

        } else {
            return null;
        }

    }

    /**
     * 根据type类型获取分页
     * typeName：网站公告、新闻资讯、报考指南
     *
     * @param type
     * @param currentPage
     * @param pageSizes
     * @return
     */
    @Override
    public List<Map> getPageByTypeLimit(String type, Integer currentPage, Integer pageSizes) {
        Pageable pageable = PageRequest.of(currentPage, pageSizes);
        if (!StringUtils.isEmpty(type)) {
            return postNewsRegulationRepository.getByTypeNameLimit(type,pageable);
        } else {
            return null;
        }

    }

    /**
     * 分页
     *
     * @param type
     * @param currentPage
     * @param pageSizes
     * @return
     */
    @Override
    public Page<PostNewsRegulation> getByTypeNamePage(String type, Integer currentPage, Integer pageSizes) {
        Pageable pageable = PageRequest.of(currentPage, pageSizes);
        if (!StringUtils.isEmpty(type)) {
            return postNewsRegulationRepository.getByTypeNamePage(type, pageable);
        } else {
            return null;
        }
    }

    /**
     * 获取信息分页
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public Page<PostNewsRegulation> getPage(Integer currentPage, Integer pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "clickThrough");
        Pageable pageable = PageRequest.of(currentPage, pageSize, sort);
        return postNewsRegulationRepository.findAll(pageable);
    }

    /**
     * 点击次数自增
     *
     * @param id
     * @return
     */
    @Override
    public PostNewsRegulation clickAdd(String id) {
        if (!StringUtils.isEmpty(id)) {
            updateClickThroughService.clickAdd(id,"1");
            return postNewsRegulationRepository.getById(id);
        } else {
            return null;
        }
    }



    /**
     * 根据type类型分别获取
     * 公告、新闻资讯、报考指南记录数
     *
     * @param type
     * @return
     */
    @Override
    public Integer getPNRCountByType(String type) {
        if (!StringUtils.isEmpty(type)) {
            return postNewsRegulationRepository.getPNRCountByType(type);
        } else {
            return null;
        }
    }

    /**
     * 获取信息的总记录数
     *
     * @return
     */
    @Override
    public Integer getPNRCount() {
        return postNewsRegulationRepository.getPNRCount();
    }

    /**
     * 获取信息总页数
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public Integer getPNRTotalPages(Integer currentPage, Integer pageSize) {
        Page<PostNewsRegulation> page = getPage(currentPage, pageSize);
        return page.getTotalPages();
    }

    /**
     * 根据type类型获取分页总页数
     *
     * @param type
     * @param currentPage
     * @param pageSizes
     * @return
     */
    @Override
    public Integer getPageByTypeTotalPages(String type, Integer currentPage, Integer pageSizes) {
        if (!StringUtils.isEmpty(type)) {
            Page<PostNewsRegulation> page = getByTypeNamePage(type, currentPage, pageSizes);
            return page.getTotalPages();
        } else {
            return null;
        }
    }

    /**
     * 根据type类型返回名称
     *
     * @param type
     * @return
     */
    @Override
    public String typeName(String type) {
        if ("0".equals(type)) {
            return cn.gmuni.zsgl.util.PostNewsRegulation.Post.getTypeName();
        }
        if ("1".equals(type)) {
            return cn.gmuni.zsgl.util.PostNewsRegulation.News.getTypeName();
        } else {
            return cn.gmuni.zsgl.util.PostNewsRegulation.Regulation.getTypeName();
        }

    }
}
