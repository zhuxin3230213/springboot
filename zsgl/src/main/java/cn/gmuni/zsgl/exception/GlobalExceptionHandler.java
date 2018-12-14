package cn.gmuni.zsgl.exception;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

/**
 * @Author:ZhuXin
 * @Description:全局异常处理
 * @Date:Create in 11:15 2018/5/7
 * @Modified By:
 **/
@ControllerAdvice
public class GlobalExceptionHandler {

    public static final String GMUNI_DEFAULT_ERROR_VIEW = "500.html";
    public static final String GMUNI_DEFAULT_ERROR_NOTFOUND_VIEW = "404.html";

    /**
     * 统一异常处理
     *
     * @param request
     * @param response
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    public ModelAndView allExceptionHandler(HttpServletRequest request,
                                      HttpServletResponse response, Exception exception) throws Exception {
        System.out.println(exception);
        System.out.println("我报错了:" + exception.getLocalizedMessage());
        System.out.println("我报错了:" + exception.getCause());
        System.out.println("我报错了:" + exception.getSuppressed());
        System.out.println("我报错了:" + exception.getMessage());
        System.out.println("我报错了:" + exception.getStackTrace());
        System.out.println("状态码:" + response.getStatus());
       if (exception instanceof NoHandlerFoundException){ //404
           response.setStatus(HttpServletResponse.SC_NOT_FOUND); //设置状态码

           ModelAndView m = new ModelAndView();
           ErrorInfo errorInfo = new ErrorInfo();
           errorInfo.setTime(LocalDateTime.now().toString());
           errorInfo.setUrl(request.getRequestURI());
           errorInfo.setError(exception.toString());
           errorInfo.setStatusCode(response.getStatus());
           errorInfo.setStackTrace(exception.getStackTrace().toString());

           m.addObject("errorInfo",errorInfo);
           m.setViewName(GMUNI_DEFAULT_ERROR_NOTFOUND_VIEW);
           exception.printStackTrace();
           return m;
       }else { //500
           response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

           ModelAndView m = new ModelAndView();
           ErrorInfo errorInfo = new ErrorInfo();
           errorInfo.setTime(LocalDateTime.now().toString());
           errorInfo.setUrl(request.getRequestURI());
           errorInfo.setError(exception.toString());
           errorInfo.setStatusCode(response.getStatus());
           errorInfo.setStackTrace(exception.getStackTrace().toString());

           m.addObject("errorInfo",errorInfo);
           m.setViewName(GMUNI_DEFAULT_ERROR_VIEW);
           exception.printStackTrace();
           return m;
       }
    }


}
