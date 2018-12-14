package cn.gmuni.zsgl.service.impl;

import cn.gmuni.zsgl.dao.ProfessionContentRepository;
import cn.gmuni.zsgl.entity.ProfessionContent;
import cn.gmuni.zsgl.service.FacultyProfessionService;
import cn.gmuni.zsgl.service.ProfessionContentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @Author:ZhuXin
 * @Description: 学科专业正文业务类
 * @Date:Create in 9:51 2018/7/2
 * @Modified By:
 **/
@Transactional
@Service
public class ProfessionContentServiceImpl implements ProfessionContentService {

    @Autowired
    ProfessionContentRepository professionContentRepository;

    @Autowired
    @Qualifier("facultyProfessionServiceImpl")
    FacultyProfessionService facultyProfessionService;

    /**
     * 根据学科专业的id查询对象
     *
     * @param proId
     * @return
     */
    @Override
    public ProfessionContent getByProId(String proId) {
        return professionContentRepository.getByProId(proId);
    }

    /**
     * 根据deptId获取articleId
     *
     * @param deptId
     * @return
     */
    @Override
    public String getContentIdByDeptId(String deptId) {
        if (!StringUtils.isEmpty(deptId)) {
            String fapId = facultyProfessionService.getFapIdByDeptId(deptId);
            ProfessionContent professionContent = professionContentRepository.getByProId(fapId);
            if (professionContent != null) {
                return professionContent.getArticleId();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
