package cn.gmuni.sc.menu.mapper;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface MenuMapper {
    List<Map<String, String>> getMenuItemByModule(String module);
    List<Map<String, String>> getCustomMenuItemByModule(Map<String, String> param);
}
