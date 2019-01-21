package cn.gmuni.sc.security.controller.endpoint;

import cn.gmuni.sc.base.response.BaseResponse;
import cn.gmuni.sc.http.remote.ICampusEndService;
import cn.gmuni.sc.user.container.UserCache;
import cn.gmuni.sc.user.model.ThirdPartUser;
import cn.gmuni.sc.user.service.IThirdUserService;
import cn.gmuni.sc.user.utils.UserUtils;
import cn.gmuni.sc.utils.RequestUtils;
import cn.gmuni.sc.utils.httputils.JsonUtil;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 对底层登录配置
 */
@Controller
public class EndPointLoginController {

    @Autowired
    ICampusEndService campusEndService;

    @Autowired
    IThirdUserService thirdUserServiceImpl;

    @PostMapping("/endpoint/login")
    @ResponseBody
    public BaseResponse<Map<String,Object>> login(HttpServletRequest request){

        try {
            String payload = RequestUtils.getPayload(request);
            Map<String,String> params = JsonUtil.json2Object(payload,Map.class);
            List<Header> headers = campusEndService.getHeaders();
            headers.add(new BasicHeader("Referer",campusEndService.getUrl("/index.html")));
            Object post = campusEndService.request(campusEndService.getUrl("/login"), new ArrayList<>(),headers, null, "POST",payload,true);
            if(post!=null){
                BaseResponse<String> result = JsonUtil.json2Object(post.toString(),BaseResponse.class);
                Map<String,Object> data = JsonUtil.json2Object(result.getData(),Map.class);
                if((Boolean) data.get("success")){
                    //如果登录成功，将登录信息保存到数据库
                    ThirdPartUser loginUser = UserUtils.getLoginUser();
                    ThirdPartUser user = new ThirdPartUser();
                    user.setId(loginUser.getId());
                    user.setStudentId(params.get("username"));
                    user.setPassword(params.get("password").trim());
                    user.setRealName(params.get("realName"));
                    user.setEndpointToken(data.get("token").toString());
                    user.setEndpointLoginId(data.get("loginId").toString());
                    thirdUserServiceImpl.updateUser(user);
                    BeanUtils.copyProperties(loginUser,user);
                    UserCache.addUser(user);
                    data.remove("token");
                    data.remove("loginId");
                }
                BaseResponse<Map<String,Object>> res = new BaseResponse<>(result.getCode(),result.getMessage());
                res.setData(data);
                return res;
            }

            return new BaseResponse<>(BaseResponse.ERROR_CODE,"登录失败");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

}
