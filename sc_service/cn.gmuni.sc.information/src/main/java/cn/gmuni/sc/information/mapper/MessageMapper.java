package cn.gmuni.sc.information.mapper;

import cn.gmuni.sc.information.model.MessageModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author:ZhuXin
 * @Description:
 * @Date:Create 2018/11/22 9:37
 * @Modified By:
 **/
@Service
@Mapper
public interface MessageMapper {

    int add(MessageModel messageModel);

    int delete(String id);

    int update(MessageModel messageModel);

    MessageModel findById(String id);

    List<Map<String, Object>> list(Map<String, String> params);

    //下拉刷新
    List<Map<String, Object>> listByInitDataFirstTime(Map<String, String> params);

    //上拉加载
    List<Map<String, Object>> listByInitDataLastTime(Map<String, String> params);

    //根据时间获取消息列表的最后一条数据
    Map<String, Object> lastMessage(Map<String, String> params);

    //根据消息id与type更改消息阅读状态
    int updateMessageState(Map<String, String> params);

    //获取所有状态
    List<String> getState(Map<String,String> params);
}
