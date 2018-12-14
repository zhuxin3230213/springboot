package cn.gmuni.zsgl.dao;

import cn.gmuni.zsgl.entity.Upload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Author:ZhuXin
 * @Description: 文件上传下载数据接口
 * @Date:Create in 14:02 2018/6/15
 * @Modified By:
 **/


public interface UploadRepository extends JpaRepository<Upload, String> {

    /**
     * 根据id查询
     *
     * @param upId
     * @return
     */
    Upload getById(String upId);


    /**
     * example: in("atta1","atta2","atta3")
     * 通过附件多个id获取列表
     *
     * @param attachment
     * @return
     */
    @Transactional
    @Query(nativeQuery = true, value = "SELECT u.id,u.name,u.format,u.size,u.last_modified,u.create_time " +
            "FROM pf_gmuni_upload u " +
            "WHERE u.id IN(:attachment)")
    List<Upload> getByAttachment(@Param("attachment") List<String> attachment);
}
