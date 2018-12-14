package cn.gmuni.zsgl.util;

/**
 * @Author:ZhuXin
 * @Description:结果集工具类
 * @Date:Create in 15:01 2018/5/7
 * @Modified By:
 **/

public class ResultUtil {

    public static Result success(Object object) {
        Result result = new Result();
        result.setStatus(0);
        result.setMsg("成功");
        result.setData(object);
        return result;

    }

    public static Result success() {
        return success(null);
    }

    public static Result error(Integer status, String msg) {
        Result result = new Result();
        result.setStatus(status);
        return result;

    }

}
