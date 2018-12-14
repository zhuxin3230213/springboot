package cn.gmuni.upload.service;

import cn.gmuni.upload.model.Upload;

import java.util.List;
import java.util.Map;

public interface IUploadService {
    Upload save(Upload upload);

    Upload get(String id);

    Map<String,Object> delete(List<String> ids);

    List<Upload> listUploadByIds(List<String> ids);
}
