package com.example.Interceptors;

import com.example.annotional.Check;
import com.example.util.JwtUtils;
import com.example.util.UserUtil;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class loginCheckInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Check check = null;
        if(handler instanceof  HandlerMethod){
            HandlerMethod hm  = (HandlerMethod)handler;
             check = hm.getMethodAnnotation(Check.class);
        }
        if(check != null){ //如果check不为空，说明此接口被自定义注解@Check注释，需要校验登录状态
            if(JwtUtils.checkToken(request)){ //通过此If判断，说明本次请求携带了jwt，并且未过期。 解密jwt获取用户信息
                Claims userInfo = JwtUtils.getMemberByJwtToken(request);
                //将用户信息保存到User工具类或Redis缓存中。方便用户信息跟踪。 我们这里报错到UserUtil工具类中。
                UserUtil.setUserName((String) userInfo.get("userName"));
                UserUtil.setUsId(Long.parseLong((String) userInfo.get("id")));
            }else {
                //jwt为空或过期，返回登录页面让用户登录，重新获取jwt
                return false;
            }
        }
        return true;
    }

}
