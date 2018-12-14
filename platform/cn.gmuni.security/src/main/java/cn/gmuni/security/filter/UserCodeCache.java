package cn.gmuni.security.filter;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户信息缓存器
 */
public class UserCodeCache {
    public static final Map<String, String> USER_CODE_MAP = new HashMap<>();
    public static int authorizedUserNum = 0;
}
