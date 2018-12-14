package cn.gmuni.zsgl.service.impl;

import cn.gmuni.zsgl.dao.UploadRepository;
import cn.gmuni.zsgl.entity.Upload;
import cn.gmuni.zsgl.service.ContentService;
import cn.gmuni.zsgl.service.PostNewsRegulationService;
import cn.gmuni.zsgl.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:ZhuXin
 * @Description: 文件上传下载业务类
 * @Date:Create in 14:05 2018/6/15
 * @Modified By:
 **/

@Transactional
@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    UploadRepository uploadRepository;

    @Autowired
    @Qualifier("contentServiceImpl")
    ContentService contentService;

    @Autowired
    @Qualifier("postNewsRegulationServiceImpl")
    PostNewsRegulationService postNewsRegulationService;


    @Value("${gmuni.upload}")
    private String uploadPath;


    /**
     * 根据正文图片的id下载图片
     *
     * @param upId
     * @param res
     * @param contentType
     */
    @Override
    public void downloadImages(String upId, HttpServletRequest req,HttpServletResponse res, String contentType) {
        if (!StringUtils.isEmpty(upId)) {
            download(upId,req, res, contentType);
        }
    }

    /**
     * 根据id下载附件或者封面
     *
     * @param upId
     * @param res
     * @param contentType
     */
    @Override
    public void downloadAttachmentOrCover(String upId, HttpServletRequest req,HttpServletResponse res, String contentType) {
        if (!StringUtils.isEmpty(upId)) {
            download(upId, req,res, contentType);
        }
    }

    /**
     * 根据正文的id下载封面
     *
     * @param contentId
     * @param res
     * @param contentType
     */
    @Override
    public void downloadCover(String contentId,HttpServletRequest req, HttpServletResponse res, String contentType) {
        if (!StringUtils.isEmpty(contentId)) {
            download(contentService.getContentById(contentId).getCover(),
                   req, res, contentType);
        }
    }

    /**
     * 根据id获取对象
     *
     * @param id
     * @return
     */
    @Override
    public Upload getById(String id) {
        if (!StringUtils.isEmpty(id)) {
            return uploadRepository.getById(id);
        } else {
            return null;
        }
    }

    /**
     * 根据正文的id获取封面
     *
     * @param contentId
     * @return
     */
    @Override
    public Upload getCoverByContentId(String contentId) {
        if (!StringUtils.isEmpty(contentId)) {
            return uploadRepository.getById(contentService.getContentById(contentId).getCover());
        } else {
            return null;
        }

    }

    /**
     * 根据正文的id获取所有的附件
     *
     * @param contentId
     * @return
     */
    @Override
    public List<Upload> listAttachmentsByContentId(String contentId) {
        if (!StringUtils.isEmpty(contentId)) {
            String attachment = contentService.getContentById(contentId).getAttachment();
            return getListUpload(attachment);
        } else {
            return null;
        }


    }

    /**
     * 根据公告、新闻、报考指南正文的id获取封面
     *
     * @param pnrId
     * @return
     */
    @Override
    public Upload getCoverByPnrId(String pnrId) {
        if (!StringUtils.isEmpty(pnrId)) {
            return uploadRepository.getById(postNewsRegulationService.getById(pnrId).getCover());
        } else {
            return null;
        }
    }

    /**
     * 根据公告、新闻、报考指南正文的id获取所有的附件
     *
     * @param pnrId
     * @return
     */
    @Override
    public List<Upload> listAttachmentsByPnrId(String pnrId) {
        if (!StringUtils.isEmpty(pnrId)) {
            String attachment = postNewsRegulationService.getById(pnrId).getAttachment();
            return getListUpload(attachment);
        } else {
            return null;
        }
    }

    /**
     * 根据热点信息的id和type类型获取正文封面
     * 0：content
     * 1：postNewsRegulation
     *
     * @param hostId
     * @param hostType
     * @return
     */
    @Override
    public Upload getCoverByHostId(String hostId, String hostType) {
        if ("0".equals(hostType)) {
            return getCoverByContentId(hostId);
        } else if ("1".equals(hostType)) {
            return getCoverByPnrId(hostId);
        } else {
            return null;
        }
    }

    /**
     * 根据热点信息的id和type类型获取所有的附件
     * 0：content
     * 1：postNewsRegulation
     *
     * @param hostId
     * @param hostType
     * @return
     */
    @Override
    public List<Upload> listAttachmentsByHostId(String hostId, String hostType) {
        if ("0".equals(hostType)) {
            return listAttachmentsByContentId(hostId);
        } else if ("1".equals(hostType)) {
            return listAttachmentsByPnrId(hostId);
        } else {
            return null;
        }
    }


    /**
     * 通过附件的多个id获取列表
     *
     * @param attachment
     * @return
     */
    @Override
    public List<Upload> getByAttachment(List<String> attachment) {

        return uploadRepository.getByAttachment(attachment);
    }

    /**
     * 通过正文多个图片的id获取列表
     *
     * @param imageIds
     * @return
     */
    @Override
    public List<Upload> getByImageIds(List<String> imageIds) {
        return uploadRepository.getByAttachment(imageIds);
    }


    /**
     * 正文附件判断
     *
     * @param attachment
     * @return
     */
    private List<Upload> getListUpload(String attachment) {
        if (!StringUtils.isEmpty(attachment)) {
            String[] split = attachment.split(",");
            List<String> list = new ArrayList<>();
            for (String temp : split) {
                list.add(temp);
            }
            return getByAttachment(list);
        } else {
            return null;
        }

    }

    /*
     * 根据图片或者附件的id下载对象
     * @param id
     * @param res
     * @param contentType
     */
    private void download(String id, HttpServletRequest req, HttpServletResponse res, String contentType) {
        Upload upload = getById(id);
        if (upload != null) {
            try {
                String fileName = upload.getName() + "." + upload.getFormat();
                res.setHeader("content-type", contentType);
                res.setContentType(contentType);

                String header = req.getHeader("User-Agent").toUpperCase();
                if (header.contains("MSIE") || header.contains("TRIDENT") || header.contains("EDGE")) {//IE浏览器的乱码问题解决
                    fileName = URLEncoder.encode(fileName, "utf-8");
                    fileName = fileName.replace("+", "%20");    //IE下载文件名空格变+号问题
                } else {//万能乱码问题解决
                    fileName = new String(fileName.getBytes(), "ISO8859-1");
                }
                res.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");

                //res.addHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes("UTF-8"), "iso-8859-1"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            //res.setHeader("Content-Disposition", "attachment; fileName="+  fileName +";filename*=utf-8''"+URLEncoder.encode(fileName,"UTF-8"));
            byte[] buff = new byte[1024];
            BufferedInputStream bis = null;
            OutputStream os = null;
            try {
                os = res.getOutputStream();
                File file = new File(uploadPath + File.separator
                        + upload.getId());
                if (!file.exists()) {
                    //先得到文件的上级目录，并创建上级目录
                    file.getParentFile().mkdir();
                }
                bis = new BufferedInputStream(new FileInputStream(file));
                int i = bis.read(buff);
                while (i != -1) {
                    os.write(buff, 0, buff.length);
                    os.flush();
                    i = bis.read(buff);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


}
