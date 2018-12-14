package cn.gmuni.security.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CheckToken {
    @Scheduled(fixedDelay = 7200000 )
    public void doCheck() {
        System.out.println("执行清理token缓存调度");
        Map<String, String> userCodeMap = UserCodeCache.USER_CODE_MAP;
        long currentTimeMillis = new Date().getTime();
        Set<String> strings = new HashSet<>(userCodeMap.keySet());
        Iterator<String> iterator = strings.iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            String token = userCodeMap.get(key);
            try{
                Claims claims = Jwts.parser().setSigningKey("MyJwtSecret")
                        .parseClaimsJws(token.replace("Bearer ", ""))
                        .getBody();
                Date expiration = claims.getExpiration();
                if (currentTimeMillis > expiration.getTime()) {
                    UserCodeCache.USER_CODE_MAP.remove(key);
                }
            }catch (Exception e){
                System.out.println("出现异常强制删除用户：" + key);
                UserCodeCache.USER_CODE_MAP.remove(key);
                e.printStackTrace();
            }
        }
    }
}
