package cn.gmuni.upload.service.impl;

import cn.gmuni.upload.mapper.UploadMapper;
import cn.gmuni.upload.model.Upload;
import cn.gmuni.upload.service.IUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UploadServiceImpl implements IUploadService {

    @Autowired
    private UploadMapper mapper;

    @Override
    public Upload save(Upload upload) {
        int count = mapper.insert(upload);
        return count>0?upload:null;
    }

    @Override
    public Upload get(String id) {
        return mapper.selectById(id);
    }

    @Override
    public Map<String, Object> delete(List<String> ids) {
        List<Upload> ups = listUploadByIds(ids);
        Map<String,Object> map = new HashMap<>();
        if(ups.size()>0){
            int count = mapper.delete(ids);
            map.put("flag",count>0);
        }else{
            map.put("flag",true);
        }
        return map;
    }

    @Override
    public List<Upload> listUploadByIds(List<String> ids) {
        return mapper.listByIds(ids);
    }
}
