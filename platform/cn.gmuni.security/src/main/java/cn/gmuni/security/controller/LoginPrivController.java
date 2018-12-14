package cn.gmuni.security.controller;

import cn.gmuni.security.service.LoginPrivService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 登录相关权限控制类
 */
@Api(value = "/loginPriv", description = "系统菜单管理控制类")
@RestController
@RequestMapping(value = "loginPriv")
public class LoginPrivController {
    @Autowired
    @Qualifier("loginPrivService")
    LoginPrivService loginPrivService;
    /**
     * 获取用户权限信息
     *
     * @return
     */
    @ApiOperation(value = "获取用户权限信息")
    @RequestMapping(value = "getPriv", method = RequestMethod.POST)
    @ResponseBody
    public List<Map> getPriv(HttpServletResponse response, HttpServletRequest request) {
        String token = request.getHeader("token");
        Claims claims = Jwts.parser().setSigningKey("MyJwtSecret")
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody();

        //得到用户名
        String code = claims.getSubject();
        List<Map> privs = loginPrivService.getPrivResourcesTreeByUserCode(code);
        return privs;
    }
}
