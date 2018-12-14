package cn.gmuni.enrollment.guide.mapper;

import cn.gmuni.enrollment.guide.model.YearlyScore;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface YearlyScoreMapper {

    List<YearlyScore> selectAllByPage(Map<String, Object> params);

    int saveScores(List<YearlyScore> list);

    int maxSort(YearlyScore score);


    int deleteById(List<String> ids);
    int updateScore(YearlyScore score);

    List<Map<String, String>> listBAndCByYearAndProv(Map<String, String> params);
}
