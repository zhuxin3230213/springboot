package com.gmunidata.newsNotice.service.impl;


import cn.gmuni.upload.mapper.UploadMapper;
import com.gmunidata.newsNotice.service.IFileService;
import com.gmunidata.utils.ImageCompressUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import cn.gmuni.upload.model.Upload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

@Service
public class FileServiceImpl implements IFileService {

    @Autowired
    private UploadMapper mapper;

    @Value("${gmuni.upload}")
    private String uploadPath;


    @Override
    public Upload get(String id) {
        return mapper.selectById(id);
    }

    @Override
    public List<Upload> listUploadByIds(List<String> ids) {
        return mapper.listByIds(ids);
    }

    /**
     * 根据正文图片的id下载图片
     *
     * @param upId
     * @param res
     * @param contentType
     */
    @Override
    public void downloadImages(String upId, HttpServletRequest req, HttpServletResponse res, String contentType) {
        if (!StringUtils.isEmpty(upId)) {
            download(upId, req, res, contentType);
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
    public void downloadAttachmentOrCover(String upId, HttpServletRequest req, HttpServletResponse res, String contentType) {
        if (!StringUtils.isEmpty(upId)) {
            download(upId, req, res, contentType);
        }
    }


    /*
     * 根据图片或者附件的id下载对象
     * @param id
     * @param res
     * @param contentType
     */
    private void download(String id, HttpServletRequest req, HttpServletResponse res, String contentType) {
        Upload upload = get(id);
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
                res.setHeader("Content-disposition", "attachment; filename=\"" + URLEncoder.encode(fileName,"UTF-8") + "\"");

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
                String newPath = ImageCompressUtil.zipImageFile(uploadPath + File.separator
                        + upload.getId(), 200, 200, 1.0f, "x2"); //下载时:进行压缩后返回新的地址
                File file = new File(newPath);
                res.setContentLength(new Long(file.length()).intValue()+ 9); //"+9"解决底层：org.xnio.channels.FixedLengthOverflowException
                res.setHeader("Content-Length",file.length()+"");
                if (!file.exists()) {
                    //先得到文件的上级目录，并创建上级目录
                    file.getParentFile().mkdir();
                }
                bis = new BufferedInputStream(new FileInputStream(file));
                int i = bis.read(buff);
                while (i != -1) {
                    os.write(buff, 0, i);
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
