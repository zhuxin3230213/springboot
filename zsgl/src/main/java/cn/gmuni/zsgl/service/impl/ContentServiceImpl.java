package cn.gmuni.zsgl.service.impl;

import cn.gmuni.zsgl.service.*;
import cn.gmuni.zsgl.util.GlobalSearchUtil;
import cn.gmuni.zsgl.dao.ContentRepository;
import cn.gmuni.zsgl.entity.*;
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
import java.util.*;

import static cn.gmuni.zsgl.util.TemplateConfig.springTemplateEngine;


/**
 * @Author:ZhuXin
 * @Description: 正文业务类
 * @Date:Create in 11:19 2018/5/8
 * @Modified By:
 **/
@Transactional
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    ContentRepository contentRepository;

    @Autowired
    @Qualifier("menuServiceImpl")
    MenuService menuService;

    @Autowired
    @Qualifier("facultyProfessionServiceImpl")
    FacultyProfessionService facultyProfessionService;

    @Autowired
    @Qualifier("postNewsRegulationServiceImpl")
    PostNewsRegulationService postNewsRegulationService;

    @Autowired
    @Qualifier("professionContentServiceImpl")
    ProfessionContentService professionContentService;


    @Value("${content.imagePath}")
    private String imagePath;

    @Autowired
    @Qualifier("uploadServiceImpl")
    UploadService uploadService;

    @Autowired
    @Qualifier("updateClickThroughServiceImpl")
    UpdateClickThroughService updateClickThroughService;

    private Integer searchsTotalPage; //全局关键字搜索总页数

    @Autowired
    EntityManager entityManager;

    /**
     * 获取关键字全局查询总页数
     *
     * @return
     */
    @Override
    public Integer getSearchsTotalPage() {
        return this.searchsTotalPage;
    }

    /**
     * 根据关键字全局查询所有信息
     *
     * @param keyWord
     * @return
     */
    @Override
    public List<GlobalSearchUtil> listSearchs(String keyWord, Integer currentPage, Integer pageSize) {
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        if (!StringUtils.isEmpty(keyWord)) {
            Page<Map> page = contentRepository.globalSearchs(keyWord, pageable);
            searchsTotalPage = page.getTotalPages();
            List<GlobalSearchUtil> list = new ArrayList<>();
            for (Map<String, String> temp : page) {
                GlobalSearchUtil globalSearchUtil = new GlobalSearchUtil();
                globalSearchUtil.setId(temp.get("id"));
                globalSearchUtil.setName(temp.get("name"));
                globalSearchUtil.setSort(temp.get("sort"));
                globalSearchUtil.setType(temp.get("type"));
                list.add(globalSearchUtil);
            }
            return list;
        } else {
            return null;
        }
    }

    /**
     * 根据id获取正文信息
     *
     * @param id
     * @return
     */
    @Override
    public Content getContentById(String id) {
        if (!StringUtils.isEmpty(id)) {
            Content content = contentRepository.getContentById(id);
            return getContent(content);
        } else {
            return null;
        }
    }


    /**
     * 获取招生政策列表
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    public Page<Content> getAdmissionsPolicy(Integer currentPage, Integer pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "sort");
        Pageable pageable = PageRequest.of(currentPage, pageSize, sort);
        Menu menu = menuService.getMenuByCode("2000");
        return contentRepository.getAdmissionsPolicyByParentCode(menu.getCode(), pageable);

    }

    /**
     * 获取学生活动列表
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    public Page<Content> getStudentActivities(Integer currentPage, Integer pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "sort");
        Pageable pageable = PageRequest.of(currentPage, pageSize, sort);
        Menu menu = menuService.getMenuByCode("2010");
        return contentRepository.getStudentActivitiesByParentCode(menu.getCode(), pageable);
    }

    /**
     * 根据菜单的menuCode查询正文分页
     *
     * @param menuCode
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public Page<Content> getPageByMenuCode(String menuCode, Integer currentPage, Integer pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "sort");
        Pageable pageable = PageRequest.of(currentPage, pageSize, sort);
        if (!StringUtils.isEmpty(menuCode)) {
            Menu menu = menuService.getMenuByCode(menuCode);
            return contentRepository.getByParentCode(menu.getCode(), pageable);
        } else {
            return null;
        }
    }

    /**
     * 根据菜单的code查询正文分页并做缓存清除
     *
     * @param menuCode
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public Page<Content> getPageByMenuCodeCache(String menuCode, Integer currentPage, Integer pageSize) {
        if (!StringUtils.isEmpty(menuCode)) {
            if ("2000".equals(menuCode)) {
                return getAdmissionsPolicy(currentPage, pageSize);
            } else if ("2010".equals(menuCode)) {
                return getStudentActivities(currentPage, pageSize);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }


    /**
     * 获取招生政策正文
     *
     * @return
     */
    public Content getAdmissionsPolicyContent(String id) {
        return contentRepository.getContentAdmissionsPolicy(id, "2000");
    }

    /**
     * 获取学生活动正文
     *
     * @return
     */
    public Content getStudentActivitiesContent(String id) {
        return contentRepository.getContentStudentActivities(id, "2010");
    }

    /**
     * 获取学科专业正文
     *
     * @return
     */
    public Content getFacContent(String id) {
        return contentRepository.getContentFac(id, "2004");
    }

    /**
     * 根据id获取正文信息并清除缓存
     *
     * @param id
     * @return
     */
    @Override
    public Content getConteByIdCache(String id) {
        if (!StringUtils.isEmpty(id)) {
            Content content = contentRepository.getContentById(id);
            if ("2000".equals(content.getParentCode())) {
                return getAdmissionsPolicyContent(id);
            } else if ("2010".equals(content.getParentCode())) {
                return getStudentActivitiesContent(id);
            } else if ("2004".equals(content.getParentCode())) {
                return getFacContent(id);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }


    /**
     * 学校概况正文
     *
     * @return
     */
    public Content getSchoolProfile() {
        Content content = contentRepository.getSchoolProfileByCode("2003");
        return getContent(content);
    }

    /**
     * 学校荣誉
     *
     * @return
     */
    public Content getschoolHonor() {
        Content content = contentRepository.getschoolHonorByCode("2005");
        return getContent(content);
    }

    /**
     * 校际交流
     *
     * @return
     */
    public Content getIntercollegiateExchange() {
        Content content = contentRepository.getIntercollegiateExchangeByCode("2008");
        return getContent(content);
    }

    /**
     * 奖优助学
     *
     * @return
     */
    public Content getaward() {
        Content content = contentRepository.getawardByCode("2009");
        return getContent(content);
    }

    /**
     * 根据菜单的code查询正文
     *
     * @param menuCode
     * @return
     */
    @Override
    public Content getByMenuCode(String menuCode) {
        if (!StringUtils.isEmpty(menuCode)) {
            Content content = contentRepository.getContentByMajorCode(menuCode);
            return getContent(content);
        } else {
            return null;
        }
    }

    /**
     * 根据菜单的code查询正文并做缓存清除
     *
     * @param menuCode
     * @return
     */
    @Override
    public Content getContentByMenuCode(String menuCode) {
        if (!StringUtils.isEmpty(menuCode)) {
            if ("2003".equals(menuCode)) {
                return getSchoolProfile();
            } else if ("2005".equals(menuCode)) {
                return getschoolHonor();
            } else if ("2008".equals(menuCode)) {
                return getIntercollegiateExchange();
            } else if ("2009".equals(menuCode)) {
                return getaward();
            } else {
                return null;
            }

        } else {
            return null;
        }

    }


    /**
     * 获取正文信息解析
     *
     * @param content
     * @return
     */
    private Content getContent(Content content) {
        Session session = entityManager.unwrap(Session.class); // 获取JPA session
        if (content != null) {
            SpringTemplateEngine engine = springTemplateEngine();
            String str = content.getContent();
            Context context = new Context();
            context.setVariable("imagePath", imagePath); //context .setVariable（name，value）; name:要访问的上下文变量的名称
            session.evict(content); //将持久对象转换为游离态
            content.setContent(engine.process(str, context));
            return content;
        } else {
            return null;
        }
    }

    /**
     * 根据院系专业的id查询对应的正文
     *
     * @param fapId
     * @return
     */
    @Override
    public Content getByFapId(String fapId) {
        if (!StringUtils.isEmpty(fapId)) {
            ProfessionContent professionContent = professionContentService.getByProId(fapId);
            return contentRepository.getContentById(professionContent.getArticleId());
        } else {
            return null;
        }
    }


    /**
     * 获取正文列表
     *
     * @return
     */
    @Override
    public List<Content> listContents() {
        return contentRepository.findAll();
    }

    /**
     * 根据文章对应菜单的code查询正文
     *
     * @param parentCode
     * @return
     */
    @Override
    public List<Content> findByParentCode(String parentCode) {
        if (!StringUtils.isEmpty(parentCode)) {
            return contentRepository.findByParentCode(parentCode);
        } else {
            return null;
        }
    }

    /**
     * 动态的获取几条正文
     *
     * @return
     */
    @Override
    public List<Content> getContentLimit(Integer size) {
        List<Content> list = new ArrayList<Content>();
        List<Content> contentList = contentRepository.findAll();
        list = contentList.subList(0, size);
        return list;
    }

    /**
     * 根据菜单menuCode查询正文
     *
     * @param menuCode
     * @return
     */
    @Override
    public List<Content> findContentByMenuId(String menuCode) {
        if (!StringUtils.isEmpty(menuCode)) {
            Menu menu = menuService.getMenuByCode(menuCode);
            return contentRepository.findByParentCode(menu.getCode());
        } else {
            return null;
        }
    }

    /**
     * 分业（根据点击次数desc降序排列）
     *
     * @param currentPage 当前页
     * @param pageSize    一页显示多少条
     * @return
     */
    @Override
    public Page<Content> getPage(Integer currentPage, Integer pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "clickThrough");
        Pageable pageable = PageRequest.of(currentPage, pageSize, sort);
        return contentRepository.findAll(pageable);
    }


    /**
     * 点击次数自增
     *
     * @param id
     * @return
     */
    @Override
    public Content clickAdd(String id) {
        if (!StringUtils.isEmpty(id)) {
            updateClickThroughService.clickAdd(id,"0");
            return contentRepository.getContentById(id);
        } else {
            return null;
        }
    }


    /**
     * 热点信息排序
     *
     * @return
     */
    @Override
    public Page<Map> getByClickHosts(String menuCode, Integer currentPage, Integer pageSize) {
        if (!StringUtils.isEmpty(menuCode)) {
            Pageable pageable = PageRequest.of(currentPage, pageSize);
            return contentRepository.getByClickThroughHosts(menuCode, pageable);
        } else {
            return null;
        }
    }

    /**
     * 限制热点信息的条数
     *
     * @param startIndex
     * @param displayLength
     * @return
     */
    @Override
    public List<Map> getByLimitHosts(String menuCode, Integer startIndex, Integer displayLength) {
        if (!StringUtils.isEmpty(menuCode)) {
            return contentRepository.getByLimitClickThroughHosts(menuCode, startIndex, displayLength);
        } else {
            return null;
        }

    }

    /**
     * 根据id和type类型查询content和postNewsRegualation内容
     * type类型
     * 0：Content对象
     * 1：PostNewsRegulation.
     *
     * @param id
     * @param type
     * @return
     */
    @Override
    public Object getByType(String id, String type) {
        if ("0".equals(type)) {
            updateClickThroughService.clickAdd(id,type);
            return contentRepository.getContentById(id);
        } else if ("1".equals(type)) {
            updateClickThroughService.clickAdd(id,type);
            return postNewsRegulationService.getById(id);
        } else {
            return null;
        }
    }

    /**
     * 获取正文总记录数
     *
     * @return
     */
    @Override
    public Integer getCount() {
        return contentRepository.getCount();
    }

    /**
     * 获取热点信息的总纪录数
     *
     * @return
     */
    @Override
    public Integer getHostsCount() {
        return contentRepository.getHostsCount();
    }

    /**
     * 获取正文总页数
     *
     * @return
     */
    @Override
    public Integer getCountTotalPages(Integer currentPage, Integer pageSize) {
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        Page<Content> page = contentRepository.findAll(pageable);
        return page.getTotalPages();
    }

    /**
     * 获取热点信息总页数
     *
     * @return
     */
    @Override
    public Integer getHostTotalPages(String menuCode, Integer currentPage, Integer pageSize) {
        if (!StringUtils.isEmpty(menuCode)) {
            Page page = getByClickHosts(menuCode, currentPage, pageSize);
            return page.getTotalPages();
        } else {
            return null;
        }
    }

    /**
     * 根据菜单的menuCode查询正文分页
     *
     * @param menuCode
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public Integer getPageByMenuCodeTotalPages(String menuCode, Integer currentPage, Integer pageSize) {
        if (!StringUtils.isEmpty(menuCode)) {
            Page<Content> page = getPageByMenuCodeCache(menuCode, currentPage, pageSize);
            return page.getTotalPages();
        } else {
            return null;
        }
    }

}
