package cn.gmuni.upload.mapper;

import cn.gmuni.upload.model.Upload;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UploadMapper {
    int insert(Upload upload);

    Upload selectById(@Param("id") String id);

    int delete(List<String> ids);

    List<Upload> listByIds(List<String> ids);
}
