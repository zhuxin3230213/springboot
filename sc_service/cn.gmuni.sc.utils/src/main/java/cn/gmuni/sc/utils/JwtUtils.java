package cn.gmuni.sc.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Calendar;
import java.util.Date;

public class JwtUtils {

    public static final String SECRETKEY = "jwtSecretKey";

    public static final String  BEARER = "Bearer ";

    public static final String TOKEN_NAME = "app_token";

    private static Date getExporation(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,1);
        return calendar.getTime();
    }

    /**
     * 生成token
     * @param plainText
     * @return
     */
    public static String generate(final String plainText){
      return BEARER+Jwts.builder().setSubject(plainText)
                .setExpiration(getExporation())
                .signWith(SignatureAlgorithm.HS512,SECRETKEY)
                .compact();
    }

    /**
     * 生成带有过期时间的token
     * @param plainText
     * @param expire
     * @return
     */
    public static String generate(final String plainText,final Date expire){
        return BEARER +Jwts.builder().setSubject(plainText)
                .setExpiration(expire)
                .signWith(SignatureAlgorithm.HS512,SECRETKEY)
                .compact();
    }

    /**
     * 解析token
     * @param token
     * @return
     */
    public static Claims parser(final String token){
        return Jwts.parser().setSigningKey(SECRETKEY)
                .parseClaimsJws(token.replace(BEARER,""))
                .getBody();
    }
}
