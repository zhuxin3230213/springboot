package cn.gmuni.upload.controller;


import cn.gmuni.upload.model.Upload;
import cn.gmuni.upload.service.IUploadService;
import cn.gmuni.utils.IdGenerator;
import org.apache.commons.io.FileUtils;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

@Controller
@RequestMapping("upload")
public class UploadController {

    @Autowired
    @Qualifier(value = "uploadServiceImpl")
    private IUploadService uploadService;

    @Value("${gmuni.upload}")
    private String uploadPath;

    @PostMapping("/upload")
    @ResponseBody
    public Map<String, Object> upload(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        List<MultipartFile> files = ((MultipartHttpServletRequest) request)
                .getFiles("file");
        if (files.size() > 0) {
            List<Upload> uploads = new ArrayList<>();
            for (MultipartFile file : files) {
                Upload upload = buildUpload(file);
                upload = uploadService.save(upload);
                if (upload != null) {
                    uploads.add(upload);
                    saveFile(upload, file);
                }
            }
            result.put("success", true);
            result.put("result", uploads);
        } else {
            result.put("success", "false");
        }
        return result;
    }


    /**
     * 用于直接将图片通过页面打开
     * @param id
     * @param request
     * @param response
     */
    @RequestMapping("/download-image/{id}")
    public void downloadImage(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        download(id,response,"application/octet-stream");
    }

    /**
     * 用于将资源直接下载下来
     * @param id
     * @param res
     */
    @RequestMapping("/download/{id}")
    public void download(@PathVariable String id, HttpServletResponse res) {
        download(id,res,"application/force-download");
    }


    /**
     * 删除资源
     * @param params
     * @return
     */
    @PostMapping("/delete")
    @ResponseBody
    public Map<String,Object> delete(@RequestBody Map<String,String> params){
        List<String> ids = Arrays.asList(params.get("ids").split(","));
        Map<String,Object> map = uploadService.delete(ids);
        if((Boolean)map.get("flag")){
            deleteFile(ids);
        }
        return map;
    }

    @PostMapping("/listUploadByIds")
    @ResponseBody
    public List<Upload> listUploadByIds(@RequestBody Map<String,String> params){
        List<String> ids = Arrays.asList(params.get("ids").split(","));
        return uploadService.listUploadByIds(ids);
    }


    private void download(String id, HttpServletResponse res,String contentType){
        Upload upload = uploadService.get(id);
        if (upload != null) {
            BufferedInputStream bis = null;
            OutputStream os = null;
            try {
                String fileName = URLEncoder.encode(upload.getName() + "." + upload.getFormat(),"UTF-8");
                res.setHeader("content-type", contentType);
                res.setContentType(contentType);
                res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
                File file = new File(uploadPath + File.separator
                        + upload.getId());
//                int contentLength = new Long(file.length()).intValue()+ 9;
//                res.setContentLength( contentLength);
//                res.setHeader("Content-Length",contentLength+"");
                byte[] buff = new byte[1024];
                os = res.getOutputStream();
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

    /**
     * 保存文件到指定路径
     *
     * @param upload
     * @param file
     */
    private void saveFile(Upload upload, MultipartFile file) {
        String path = uploadPath + File.separator + upload.getId();
        try {
            File tempFile = new File(uploadPath);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            File newFile = new File(path);
            if (newFile.exists()) {
                newFile.delete();
            }
            FileUtils.copyInputStreamToFile(file.getInputStream(), newFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteFile(List<String> ids){
        for(String id : ids){
            try {
                FileUtils.deleteQuietly(new File(uploadPath+File.separator+id));
            }catch (Exception e){

            }
        }
    }


    private Upload buildUpload(MultipartFile file) {
        String id = IdGenerator.getId();
        Upload upload = new Upload();
        upload.setId(id);
        upload.setLastModified("" + new Date().getTime());
        String filename = file.getOriginalFilename();
        upload.setName(filename.substring(0, filename.lastIndexOf(".")));
        upload.setFormat(filename.substring(filename.lastIndexOf(".") + 1));
        upload.setSize(file.getSize());
        upload.setCreateTime(new Date());
        return upload;
    }
}
