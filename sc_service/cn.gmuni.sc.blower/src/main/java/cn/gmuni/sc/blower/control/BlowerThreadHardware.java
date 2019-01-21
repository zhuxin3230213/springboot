package cn.gmuni.sc.blower.control;

import cn.gmuni.sc.blower.cache.BlowerCache;
import cn.gmuni.sc.blower.mapper.BlowerMapper;
import cn.gmuni.sc.device.BlowerDervice;

import java.util.Map;

public class BlowerThreadHardware extends Thread {

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

    public BlowerThreadHardware(Map<String, Object> params, BlowerMapper mapper){
        this.params = params;
        this.mapper = mapper;

    }

    public void run() {
        int time = Integer.valueOf(String.valueOf(params.get("workTime")));
             try {
                 Map maps =mapper.selectDeviceByCode((String) params.get("blowerCode"));
                  Thread.sleep(1000*10*time);
                  Map res = new BlowerDervice().requestDevice("close",maps);
                  if (res.get("flag").equals("false")){
                      BlowerCache.addBlowerClose(maps);
                  }
             } catch (InterruptedException e) {
                            //设置中断状态
                    Thread.currentThread().interrupt();
                  }
                  Thread.yield();
            }
}
