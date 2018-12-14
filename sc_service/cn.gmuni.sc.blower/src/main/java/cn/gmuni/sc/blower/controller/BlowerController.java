package cn.gmuni.sc.blower.controller;

import cn.gmuni.sc.base.response.BaseResponse;
import cn.gmuni.sc.blower.service.IBlowerService;
import cn.gmuni.sc.log.anntation.SysLog;
import cn.gmuni.sc.log.constant.SysLogModule;
import cn.gmuni.sc.log.constant.SysLogType;
import cn.gmuni.sc.user.model.ThirdPartUser;
import cn.gmuni.sc.user.model.ThirdPartUserInfo;
import cn.gmuni.sc.user.utils.UserUtils;
import cn.gmuni.sc.utils.AliPayUtil;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.maven.shared.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

@Api(value = "/blower",description = "吹风机支付模块")
@RestController
@RequestMapping("/blower")
public class BlowerController {

    @Value("${app.server.center.addr}")
    private String serverAddr;

    @Autowired
    @Qualifier("blowerServiceImpl")
    IBlowerService blowerServiceImpl;
    /**
     * 吹风机支付支付宝
     *
     * @return
     */
    @PostMapping("/blowerPayByALi")
  /*  @SysLog(desc = "吹风机支付宝支付", module = SysLogModule.CONSUME_LOG, type = SysLogType.OPERATOR_LOG)*/
    public void blowerPay(HttpServletRequest request, HttpServletResponse response){
        //充值金额
        String jine = request.getParameter("chargeJinE");
        String workTime = request.getParameter("workTime");
        //BLOWER+充值金额
        String productCode = "BLOWER" + jine;
        DecimalFormat df = new DecimalFormat("##0.00");
        jine = df.format(Double.parseDouble(jine));

        String blowerId = request.getParameter("blowerCode");
        ThirdPartUserInfo loginUserInfo = UserUtils.getLoginUserInfo();
        ThirdPartUser loginUser = UserUtils.getLoginUser();
        //支付宝缴费
            String loginCode = loginUserInfo.getIndentifier();
            String schoolCode = loginUser.getSchool();
            Map<String, String> body = new HashMap<>();
            body.put("loginCode", loginCode);
            //学校编码
            body.put("schoolCode", schoolCode);
            //吹风机id
            body.put("blowerId", blowerId);
            //时间
            body.put("workTime",workTime);
            //金额
            String subject = "共享吹风机" + jine + "元";
            //吹风机任务
            body.put("subject",subject);
            AliPayUtil.doPay(response, "", productCode, serverAddr + "net/net_pay_success", serverAddr + "blower/blowerNotice", jine, subject, JSON.toJSONString(body));
    }

    /**
     * 支付宝吹风机扣费成功回调
     *
     * @param request
     */
    @RequestMapping("/blowerNotice")
    public void blowerNotice(HttpServletRequest request,HttpServletResponse response) {
        System.out.println("Blower回调");

        try {
            request.setCharacterEncoding("GBK");
            blowerServiceImpl.saveBlowerPayInfo(request.getParameterMap());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.print("success");
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "一卡通收费吹风机")
    @PostMapping("/blowerPayByCard")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "blowerCode", name = "吹风机编码", required = true),
            @ApiImplicitParam(value = "workTime", name = "工作时间", required = true),
            @ApiImplicitParam(value = "chargeJinE", name = "收费金额", required = true)
    })
    public void blowerPayByCard(@RequestBody @ApiIgnore Map<String, Object> params){
        params.put("schoolCode", UserUtils.getLoginUser().getSchool());
        params.put("userCode", UserUtils.getLoginUserInfo().getIndentifier());
        params.put("userCode", UserUtils.getLoginUserInfo().getIndentifier());
        blowerServiceImpl.blowerPayByCard(params);
    }

    @ApiOperation(value = "查询吹风机状态")
    @PostMapping("/getBlowerStatus")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "blowerCode", name = "吹风机编码", required = true)
    })
    public BaseResponse<Map<String,Object>> getBlowerStatus(@RequestBody @ApiIgnore Map<String, Object> params){
            params.put("schoolCode", UserUtils.getLoginUser().getSchool());
            params.put("userCode", UserUtils.getLoginUserInfo().getIndentifier());
           return new BaseResponse<>(blowerServiceImpl.getBlowerStatus(params));
    }

    @ApiOperation(value = "取消吹风机锁定")
    @PostMapping("/cancelBlowerLock")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "blowerCode", name = "吹风机编码", required = true)
    })
    public BaseResponse<Map<String,Object>> cancelBlowerLock(@RequestBody @ApiIgnore Map<String, Object> params){
        params.put("schoolCode", UserUtils.getLoginUser().getSchool());
        return new BaseResponse<>(blowerServiceImpl.cancelBlowerLock(params));
    }

    @ApiOperation(value = "中断吹风机使用")
    @PostMapping("/endBlowerStatus")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "blowerCode", name = "吹风机编码", required = true)
    })
    public BaseResponse<Map<String,Object>> endBlowerStatus(@RequestBody @ApiIgnore Map<String, Object> params){
        params.put("schoolCode", UserUtils.getLoginUser().getSchool());
        return new BaseResponse<>(blowerServiceImpl.endBlowerStatus(params));
    }

    @RequestMapping("/hairDryer")
    public String netPaySuccess(HttpServletRequest request, Model model){
        return "hairDryer";
    }

}
