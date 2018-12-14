package com.gmunidata.newsNotice.controller;

import com.gmunidata.base.response.BaseResponse;
import com.gmunidata.newsNotice.service.IFileService;
import com.gmunidata.utils.Base64Util;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: zhuxin
 * @Date: 2018/9/7 16:09
 * @Description:
 */
@Controller
@RequestMapping
public class FileController {

    @Autowired
    @Qualifier(value = "fileServiceImpl")
    private IFileService fileService;


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
        //处理图片缓存
        long mos = request.getDateHeader("If-Modified-Since");
        if (mos != -1) {
            long lmd = Long.parseLong(lastModified);
            if (lmd - mos > 1000) {
                res.setDateHeader("Last-Modified", Long.parseLong(lastModified));
                res.setStatus(200);
                fileService.downloadImages(id, request,res, "application/octet-stream");
            } else {
                res.setStatus(304);
            }
        } else {
            res.setDateHeader("Last-Modified", Long.parseLong(lastModified));
            res.setStatus(200);
            fileService.downloadImages(id, request,res, "application/octet-stream");
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
    @RequestMapping("/download/{id}")
    public void downloadAttachment(@PathVariable("id") String id, HttpServletResponse res,
                                   HttpServletRequest request) {
        fileService.downloadAttachmentOrCover(id,request, res, "application/octet-stream");
    }

    //查询附件列表
    @ApiOperation(value = "附件列表查询")
    @RequestMapping("/listUploadByIds/{attachment}")
    @ResponseBody
    public BaseResponse listUploadByIds(@PathVariable("attachment") String attachment){
        List<String> ids = Arrays.asList(attachment.split(","));
        BaseResponse response = new BaseResponse();
        response.setCode(200);
        response.setMessage("附件");
        response.setData(fileService.listUploadByIds(ids));
        return response;
    }

    @RequestMapping("/base64")
    @ResponseBody
    public String fileToBase64(){
        String s = Base64Util.fileToBase64("F:\\工作文档\\android开发\\新闻、通知、公告\\通知\\通知.jpg");
        return s;
    }
}
