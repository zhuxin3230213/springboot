package cn.gmuni.enrollment.guide.service.impl;

import cn.gmuni.enrollment.guide.mapper.YearlyScoreMapper;
import cn.gmuni.enrollment.guide.model.YearlyScore;
import cn.gmuni.enrollment.guide.service.IYearlyScoreService;
import cn.gmuni.utils.IdGenerator;
import cn.gmuni.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class YearlyScoreServiceImpl implements IYearlyScoreService {
    @Autowired
    YearlyScoreMapper mapper;

    @Override
    public PageInfo<YearlyScore> getAllYearlyScore(Map<String, String> params) {
        PageUtils page = PageUtils.getPage(params);
        Map<String, Object> queryPara = new HashMap<>();
        queryPara.put("year", params.get("year"));
        queryPara.put("provinces", params.get("provinces"));
        queryPara.put("family", params.get("family"));
        queryPara.put("batch", params.get("batch"));
        PageHelper.startPage(page.getPage(), page.getSize());
        return new PageInfo<>(mapper.selectAllByPage(queryPara));
    }

    @Override
    public Map<String, Object> deleteById(Map<String, String> params) {
        Map<String, Object> res = new HashMap<>();
        String[] ids = params.get("ids").split(",");
        res.put("flag", mapper.deleteById(Arrays.asList(ids)) > 0 ? true : false);
        return res;
    }

    @Override
    public Map<String, Object> saveScores(List<YearlyScore> scores) {
        Map<String, Object> res = new HashMap<>();
        res.put("flag", false);
        if (scores.size() > 0) {
            YearlyScore score = scores.get(0);
//            YearlyScore score = null;
//            String provinces = score.getProvinces();
//            String year = score.getYear();
//            impl.deleteByYearAndProvinces(provinces, year);
            int maxSort = mapper.maxSort(score);
            int sort = 1;
            if(maxSort!=0){
                sort = maxSort + 1;
            }
            for (int i = 0; i < scores.size(); i++) {
                score = scores.get(i);
                score.setSort(sort + "");
                sort++;
                if (StringUtils.isEmpty(score.getId())) {
                    score.setId(IdGenerator.getId());
                }
            }
            mapper.saveScores(scores);
            res.put("flag", true);
        }
        return res;
    }

    @Override
    public Map<String, Object> updateScore(YearlyScore score) {
        Map<String, Object> res = new HashMap<>();
        res.put("flag", mapper.updateScore(score) == 1 ? true : false);
        return res;
    }

    @Override
    public List<Map<String, String>> listBAndC(Map<String, String> params) {
        return mapper.listBAndCByYearAndProv(params);
    }
}
