package testspringboot.exception;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {


    //声明要捕获的异常
    @ExceptionHandler(value = Exception.class)
    public String allExceptionHandler(HttpServletRequest request, Exception exception) throws Exception {
        exception.printStackTrace();
        System.out.println("我报错了:" + exception.getLocalizedMessage());
        System.out.println("我报错了:" + exception.getCause());
        System.out.println("我报错了:" + exception.getSuppressed());
        System.out.println("我报错了:" + exception.getMessage());
        System.out.println("我报错了:" + exception.getStackTrace());

        return "服务器异常，请联系管理员！";
    }

}
