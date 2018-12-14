package cn.gmuni.zsgl.controller;

import cn.gmuni.zsgl.service.impl.CacheRemove;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:ZhuXin
 * @Description: 缓存清除
 * @Date:Create in 11:38 2018/7/18
 * @Modified By:
 **/
@Api(value = "cache清除接口类", description = "缓存清除API根目录")
@RestController
@RequestMapping(value = "/cache")
public class CacheRemoveController {

    @Autowired
    CacheRemove cacheRemove;


    /**
     * 清除缓存
     * @param cacheNames
     */
    @ApiOperation(value = "清除缓存", notes = "")
    @GetMapping(value = "/{cacheNames}")
    public void removeCache(@PathVariable("cacheNames") String cacheNames) {
        cacheRemove.removeCache(cacheNames);
    }


}
