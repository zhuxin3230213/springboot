package cn.gmuni.enrollment.guide.service;

import cn.gmuni.enrollment.guide.model.YearlyScore;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface IYearlyScoreService {
    public PageInfo<YearlyScore> getAllYearlyScore(Map<String, String> params);

    public Map<String, Object> saveScores(List<YearlyScore> scores);
    public Map<String, Object> deleteById(Map<String, String> params);
    public Map<String, Object> updateScore(YearlyScore score);

    public List<Map<String, String>> listBAndC(Map<String, String> params);
}
