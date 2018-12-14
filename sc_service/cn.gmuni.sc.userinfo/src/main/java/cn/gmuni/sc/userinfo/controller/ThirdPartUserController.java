package cn.gmuni.sc.userinfo.controller;

import cn.gmuni.sc.base.response.BaseResponse;
import cn.gmuni.sc.log.anntation.SysLog;
import cn.gmuni.sc.log.constant.SysLogModule;
import cn.gmuni.sc.log.constant.SysLogType;
import cn.gmuni.sc.user.container.UserCache;
import cn.gmuni.sc.user.model.ThirdPartUser;
import cn.gmuni.sc.user.service.IThirdUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/thirdPartUser")
public class ThirdPartUserController {

    @Autowired
    private IThirdUserService thirdUserServiceImpl;

    @PostMapping("/updateUser")
    @SysLog(desc = "更新用户信息",module = SysLogModule.OTHER_LOG,type = SysLogType.UPDATE_LOG)
    public BaseResponse<Map<String,Object>> updateUser(@RequestBody  ThirdPartUser user){

        if(thirdUserServiceImpl.updateUser(user)){
            UserCache.updateUser(user);
            return new BaseResponse<>(new HashMap<>());
        }else{
            return new BaseResponse<>(BaseResponse.ERROR_CODE,"更新用户信息异常");
        }
    }

}
