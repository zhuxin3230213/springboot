package cn.gmuni.zsgl.service.impl;

import cn.gmuni.zsgl.dao.FamousTeacherStyleRepository;
import cn.gmuni.zsgl.entity.FamousTeacherStyle;
import cn.gmuni.zsgl.service.FamousTeacherStyleService;
import cn.gmuni.zsgl.util.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
 * @Description: 名师风采业务类
 * @Date:Create in 10:15 2018/6/14
 * @Modified By:
 **/
@Transactional
@Service
public class FamousTeacherStyleServiceImpl implements FamousTeacherStyleService {

    @Autowired
    FamousTeacherStyleRepository famousTeacherStyleRepository;

    @Value("${content.imagePath}")
    private String imagePath;

    @Autowired
    EntityManager entityManager;

    /**
     * 根据id查询教师信息
     *
     * @param fastId
     * @return
     */
    @Override
    public FamousTeacherStyle getById(String fastId) {
        Session session = entityManager.unwrap(Session.class);
        if (!StringUtils.isEmpty(fastId)) {
            FamousTeacherStyle famousTeacherStyle = famousTeacherStyleRepository.getById(fastId);
            if (famousTeacherStyle != null) {
                String teacherIntroduction = famousTeacherStyle.getTeacherIntroduction();
                SpringTemplateEngine engine = springTemplateEngine();
                Context context = new Context();
                context.setVariable("imagePath", imagePath);
                session.evict(famousTeacherStyle);
                famousTeacherStyle.setTeacherIntroduction(engine.process(teacherIntroduction, context));
                return famousTeacherStyle;
            } else {
                return null;
            }

        } else {
            return null;
        }

    }

    /**
     * 根据id获取教师所有信息
     *
     * @param fastId
     * @return
     */
    @Override
    public Map getByFaId(String fastId) {
        if (!StringUtils.isEmpty(fastId)) {
            return famousTeacherStyleRepository.getByfaID(fastId);
        } else {
            return null;
        }
    }

    /**
     * 获取教师信息列表分页
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public Page<Map> listFatsPage(Integer currentPage, Integer pageSize) {
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        return famousTeacherStyleRepository.listFatesPage(pageable);
    }

    /**
     * 获取教师信息列表总页数
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public Integer getFastsPageTotal(Integer currentPage, Integer pageSize) {
        Page<Map> page = listFatsPage(currentPage, pageSize);
        return page.getTotalPages();
    }

    /**
     * 根据职称获取分页
     *
     * @param title
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public Page<Map> listByTitlePage(String title, Integer currentPage, Integer pageSize) {
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        if (!StringUtils.isEmpty(title)) {
            return famousTeacherStyleRepository.listByTitle(title, pageable);
        } else {
            return null;
        }
    }

    /**
     * 根据荣誉获取分页
     *
     * @param honor
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public Page<Map> listByHonorPage(String honor, Integer currentPage, Integer pageSize) {
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        if (!StringUtils.isEmpty(honor)) {
            return famousTeacherStyleRepository.listByHonor(honor, pageable);
        } else {
            return null;
        }
    }

    /**
     * 根据职称和荣誉、姓名获取分页
     *
     * @param title
     * @param honor
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public Page<Map> listByTitleAndHonorPage(String title, String honor, String name, Integer currentPage, Integer pageSize) {
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        if (title.equals("") && honor.length() != 0 && name.equals("")) {
            return famousTeacherStyleRepository.listByHonor(honor, pageable); //荣誉
        } else if (honor.equals("") && title.length() != 0 && name.equals("")) {
            return famousTeacherStyleRepository.listByTitle(title, pageable);  //职称
        } else if (title.equals("") && honor.equals("") && name.length() != 0) {
            return famousTeacherStyleRepository.listByName(name, pageable);   //姓名
        } else if (title.equals("") && title.equals("") && name.equals("")) {
            return famousTeacherStyleRepository.listFatesPage(pageable);  //全部为空
        } else if (title.length() != 0 && honor.length() != 0 && name.equals("")) {
            return famousTeacherStyleRepository.listByTitleAndHonor(title, honor, pageable); //职称和荣誉
        } else if (title.length() != 0 && honor.equals("") && name.length() != 0) {
            return famousTeacherStyleRepository.listByTitleAndName(title, name, pageable); //职称和姓名
        } else if (title.equals("") && honor.length() != 0 && name.length() != 0) {
            return famousTeacherStyleRepository.listByHonorAndName(honor, name, pageable); //荣誉和姓名
        } else {
            return famousTeacherStyleRepository.listByTitleAndHonorName(title, honor, name, pageable); //职称、荣誉和姓名
        }
    }

    /**
     * 获取职称列表
     *
     * @return
     */
    @Override
    public List<Map> getListTitle() {
        return famousTeacherStyleRepository.getListTitle();
    }

    /**
     * 获取荣誉列表
     *
     * @return
     */
    @Override
    public List<Map> getListHonor() {
        return famousTeacherStyleRepository.getListHonor();
    }


    /**
     * 获取总页数
     *
     * @param pageSize 一页显示多少条
     * @param allRow   总纪录数
     * @return
     */
    @Override
    public Integer getFatsTotalPage(Integer pageSize, Integer allRow) {
        return PageUtil.countTatalPage(pageSize, allRow);
    }


}
