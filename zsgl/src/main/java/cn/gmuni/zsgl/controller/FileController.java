package cn.gmuni.zsgl.controller;


import cn.gmuni.zsgl.service.ContentService;
import cn.gmuni.zsgl.service.UploadService;
import cn.gmuni.zsgl.service.VisitStatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @Author:ZhuXin
 * @Description: 文件上传下载控制层
 * @Date:Create in 11:38 2018/6/15
 * @Modified By:
 **/
@Api(value = "文件上传下载接口类", description = "文件上传下载服务API根目录")
@Controller
public class FileController {

    @Autowired
    @Qualifier("uploadServiceImpl")
    UploadService uploadService;

    @Autowired
    @Qualifier("visitStatisticsServiceImpl")
    VisitStatisticsService visitStatisticsService;

    @Autowired
    @Qualifier("contentServiceImpl")
    ContentService contentService;

    @Value("${contentType}")
    private String contentType;

    @Value("${attachmentContentType}")
    private String attachmentContentType;


    /**
     * 文件下载相关代码
     *
     * @param id
     * @param lastModified
     * @param res
     * @param request
     */
    @ApiOperation(value = "文件下载", notes = "")
    @RequestMapping("/imagePath/{id}/{lastModified}")
    public void downloadFile(@PathVariable("id") String id, @PathVariable("lastModified") String lastModified,
                             HttpServletResponse res,
                             HttpServletRequest request) {
        visitStatisticsService.add(request, "welcome",
                "首页");
        //处理图片缓存
        long mos = request.getDateHeader("If-Modified-Since");
        if (mos != -1) {
            long lmd = Long.parseLong(lastModified);
            if (lmd - mos > 1000) {
                res.setDateHeader("Last-Modified", Long.parseLong(lastModified));
                res.setStatus(200);
                uploadService.downloadImages(id, request,res, contentType);
            } else {
                res.setStatus(304);
            }
        } else {
            res.setDateHeader("Last-Modified", Long.parseLong(lastModified));
            res.setStatus(200);
            uploadService.downloadImages(id, request,res, contentType);
        }
    }


    /**
     * 附件或者缩略图下载
     *
     * @param id
     * @param res
     * @param request
     * @return
     */
    @ApiOperation(value = "附件或者缩略图下载", notes = "")
    @RequestMapping("/filePath/{id}")
    public void downloadAttachment(@PathVariable("id") String id, HttpServletResponse res,
                                   HttpServletRequest request) {
        uploadService.downloadAttachmentOrCover(id,request, res, attachmentContentType);
    }

}
