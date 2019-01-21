package com.gmunidata.newsNotice.service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.gmuni.upload.model.Upload;

import java.util.List;

public interface IFileService {


    Upload get(String id);
    List<Upload> listUploadByIds(List<String> ids);

    void downloadImages(String upId, HttpServletRequest req, HttpServletResponse res, String contentType) ;
    void downloadAttachmentOrCover(String upId, HttpServletRequest req, HttpServletResponse res, String contentType) ;
}
