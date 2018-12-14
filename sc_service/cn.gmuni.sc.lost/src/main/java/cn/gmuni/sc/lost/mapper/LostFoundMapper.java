package cn.gmuni.sc.lost.mapper;

import cn.gmuni.sc.lost.model.LostFound;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public interface LostFoundMapper {
    int add(LostFound info);

    int delete(String id);
    int finish(String id);
    int edit(LostFound info);

    List<LostFound> list(Map<String, Object> params);

    LostFound findOneById(String id);
}
