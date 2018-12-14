package cn.gmuni.zsgl.service;

import cn.gmuni.zsgl.entity.Upload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author:ZhuXin
 * @Description: 文件上传下载业务接口
 * @Date:Create in 14:04 2018/6/15
 * @Modified By:
 **/
public interface UploadService {

    /**
     * 根据正文的id下载正文图片
     *
     * @param upId
     * @param res
     * @param contentType
     */
    void downloadImages(String upId, HttpServletRequest req, HttpServletResponse res, String contentType);


    /**
     * 根据正文的id下载封面(缩略图)
     *
     * @param contentId
     * @param res
     * @param contentType
     */
    void downloadCover(String contentId, HttpServletRequest req,HttpServletResponse res, String contentType);

    /**
     * 根据id下载封面或者附件
     *
     * @param upId
     * @param res
     * @param contentType
     */
    void downloadAttachmentOrCover(String upId,HttpServletRequest req, HttpServletResponse res, String contentType);


    /**
     * 根据id获取对象
     *
     * @param id
     * @return
     */
    Upload getById(String id);


    /**
     * 根据正文的id查询封面、缩略图
     *
     * @param contentId
     * @return
     */
    Upload getCoverByContentId(String contentId);

    /**
     * 根据正文的id查询所有的附件
     *
     * @param contentId
     * @return
     */
    List<Upload> listAttachmentsByContentId(String contentId);


    /**
     * 根据公告、新闻、报考指南正文的id查询封面
     *
     * @param pnrId
     * @return
     */
    Upload getCoverByPnrId(String pnrId);


    /**
     * 根据公告、新闻、报考指南正文的id查询所有的附件
     *
     * @param pnrId
     * @return
     */
    List<Upload> listAttachmentsByPnrId(String pnrId);


    /**
     * 根据热点信息的id和type类型获取正文封面
     * 0：content
     * 1: postNewsRegulation
     *
     * @param hostId
     * @param hostType
     * @return
     */
    Upload getCoverByHostId(String hostId, String hostType);


    /**
     * 根据热点信息的id和type类型获取所有的附件
     * 0：content
     * 1：postNewsRegulation
     *
     * @param hostId
     * @param hostType
     * @return
     */
    List<Upload> listAttachmentsByHostId(String hostId, String hostType);


    /**
     * 通过附件的多个id获取列表
     *
     * @param attachment
     * @return
     */
    List<Upload> getByAttachment(List<String> attachment);

    /**
     * 通过正文多个图片的id获取upload列表
     *
     * @param imageIds
     * @return
     */
    List<Upload> getByImageIds(List<String> imageIds);
}
