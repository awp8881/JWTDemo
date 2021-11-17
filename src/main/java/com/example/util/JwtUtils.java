package com.example.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtUtils {
    //定义两个常量
    public static final long EXPIRE = 1000 * 60 * 60 * 24; //设置token过期时间
    public static final String APP_SECRET = "xbrceXUKwYIRoQJndTPFNzAmhDagkLMExbrceXUKwYIRoQJndTPFNzAmhDagkLME"; //密钥，随便写，做加密操作
    //生成token字符串的方法
    public static String getJwtToken(String id, String nickname){

        String JwtToken = Jwts.builder()
                //设置头信息，固定
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                //设置过期时间
                .setSubject("guli-user")//名字随便取
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                //设置token主体部分
                .claim("id", id)
                .claim("userName", nickname)
                //根据密钥生成字符串
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();

        return JwtToken;
    }

    /**
     * 判断token是否存在与有效
     * @param JID
     * @return
     */
    public static boolean checkToken(String JID) {
        if(StringUtils.isEmpty(JID)) return false;
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(JID);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    /**
     * 判断token是否存在与有效
     * @param request
     * @return
     */
    public static boolean checkToken(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader("Cookie").split("=")[1];
            if(StringUtils.isEmpty(jwtToken)) return false;
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    /**
     * 根据token获取用户id
     * @param request
     * @return
     */
    public static Claims getMemberByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("Cookie").split("=")[1];
        if(StringUtils.isEmpty(jwtToken)) return null;
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        return claims;
    }
}
