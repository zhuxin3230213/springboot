package cn.gmuni.zsgl.dao;

import cn.gmuni.zsgl.entity.SlideShow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * @Author:ZhuXin
 * @Description: 轮播图数据接口
 * @Date:Create in 11:44 2018/6/28
 * @Modified By:
 **/
public interface SlideShowRepository extends JpaRepository<SlideShow, String> {

    /**
     * 0：content
     * 1：pnr
     * 获取轮播图列表
     *
     * @return
     */
    @Transactional
    @Query(nativeQuery = true,
            value = "SELECT sl.* \n" +
                    " FROM \n" +
                    " (SELECT\n" +
                    "  s.article_id AS articleId,\n" +
                    "  s.module,\n" +
                    "  s.sort,\n" +
                    "  c.title,\n" +
                    "  c.cover,\n" +
                    "  u.name,\n" +
                    "  u.last_modified AS lastModified,\n" +
                    "  u.format,'0'AS TYPE\n" +
                    "FROM zs_gmuni_slideshow s\n" +
                    "  INNER JOIN zs_gmuni_content c\n" +
                    "    ON s.article_id = c.id\n" +
                    "  INNER JOIN pf_gmuni_upload u\n" +
                    "    ON c.cover = u.id\n" +
                    "    UNION ALL \n" +
                    " SELECT\n" +
                    "  s.article_id AS articleId,\n" +
                    "  s.module,\n" +
                    "  s.sort,\n" +
                    "  c.title,\n" +
                    "  c.cover,\n" +
                    "  u.name,\n" +
                    "  u.last_modified AS lastModified,\n" +
                    "  u.format,'1'AS TYPE\n" +
                    "FROM zs_gmuni_slideshow s\n" +
                    "  INNER JOIN zs_gmuni_post_news_regulations c\n" +
                    "    ON s.article_id = c.id\n" +
                    "  INNER JOIN pf_gmuni_upload u\n" +
                    "    ON c.cover = u.id ) sl\n" +
                    " ORDER BY sl.sort")
    List<Map> listSlideShows();
}
