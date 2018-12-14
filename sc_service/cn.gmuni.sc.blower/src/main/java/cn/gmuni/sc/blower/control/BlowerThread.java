package cn.gmuni.sc.blower.control;

import cn.gmuni.sc.blower.mapper.BlowerMapper;
import cn.gmuni.sc.utils.DateUtils;

import java.util.Date;
import java.util.Map;

public class BlowerThread extends Thread {

    private BlowerMapper mapper;

    private Map<String, Object> params;

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public BlowerMapper getMapper() {
        return mapper;
    }

    public void setMapper(BlowerMapper mapper) {
        this.mapper = mapper;
    }

    public BlowerThread(Map<String, Object> params, BlowerMapper mapper){
        this.params = params;
        this.mapper = mapper;
    }

    public void run() {
        int time = Integer.valueOf(String.valueOf(params.get("workTime")));
             try {
                  Thread.sleep(1000*60*time);
                   mapper.initStatus(params);
                   } catch (InterruptedException e) {
                            //设置中断状态
                    Thread.currentThread().interrupt();
                  }
                  Thread.yield();
            }
}
