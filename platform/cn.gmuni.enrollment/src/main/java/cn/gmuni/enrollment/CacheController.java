package cn.gmuni.enrollment;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 缓存控制类
 */
@Api(value = "/cache", description = "缓存控制类")
@RestController
@RequestMapping(value = "cache")
public class CacheController {
    @Value("${cache.clear.base.url}")
    private String baseUrl;
    /**
     * 清除缓存
     *
     * @return
     */
    @ApiOperation(value = "清除缓存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "清除的类型{rvContent:清除content正文缓存\n" +
                    ",rvFacp:清除学科专业缓存,rvFasts:清除名师风采缓存,rvMenus:清除菜单缓存\n" +
                    ",rvPlans:清除历年招生计划缓存,rvPnrs:清除信息缓存,rvProContents:清除学科专业正文缓存\n" +
                    ",rvYearlys:清除历年分数缓存}")})
    @ResponseBody
    @RequestMapping(value = "/clearCache", method = RequestMethod.POST)
    public Map<String, Object> clearCache(@RequestBody @ApiIgnore Map<String, String> params) {
        Map<String, Object> res = new HashMap<>();
        res.put("flag", false);
        try {
            String cacheType = params.get("type");
            HttpClient client = HttpClients.createDefault();
            //发送get请求
            HttpGet request = new HttpGet(baseUrl + cacheType);
            HttpResponse response = client.execute(request);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
//                String strResult = EntityUtils.toString(response.getEntity());
                res.put("flag", true);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }finally {
            return res;
        }
    }
}
