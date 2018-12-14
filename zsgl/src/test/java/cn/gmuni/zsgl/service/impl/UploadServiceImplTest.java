package cn.gmuni.zsgl.service.impl;

import cn.gmuni.zsgl.dao.UploadRepository;
import cn.gmuni.zsgl.entity.Content;
import cn.gmuni.zsgl.entity.Upload;
import cn.gmuni.zsgl.service.ContentService;
import cn.gmuni.zsgl.service.UploadService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author:ZhuXin
 * @Description:
 * UploadServiceImplTest
 * @Date:Create in 15:11 2018/6/15
 * @Modified By:
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class UploadServiceImplTest {

    @Autowired
    UploadService uploadService;
    @Autowired
    ContentService contentService;
    @Autowired
    UploadRepository uploadRepository;

    /**
     *in()查询
     */
    @Test
    public void test(){
        Content content = contentService.getContentById("4173486179df43dab49b887357d4ec88");
        String[] split = content.getAttachment().split(",");
        List<String> list = new ArrayList<>();
        for (String temp:split){
              list.add(temp);
        }
        for (String temp:list){
            System.out.println(temp);
        }
        List<Upload> byAttachment = uploadRepository.getByAttachment(list);
        System.out.println(byAttachment.size());
    }


}