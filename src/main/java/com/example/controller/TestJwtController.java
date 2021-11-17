package com.example.controller;

import com.example.annotional.Check;
import com.example.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
public class TestJwtController {


    /**
     * 模拟认证中心，真实环境中此接口应该是一个单独的服务，这里方便演示，用一个接口代替。
     *
     * 登录授权接口，登录成功后返回jwt
     */
    @GetMapping("/login")
    public Object login(HttpServletResponse response){
        //这里默认登录成功，业务逻辑我就不写了，挑重点写
        String userId = "1";
        String userName = "张三";
        //用用户的id 和 用户名生成jwt  真实环境中这里还会存用户权限等等。。
        String JID = JwtUtils.getJwtToken(userId, userName);
        Cookie cookie = new Cookie("JID",JID);
        cookie.setPath("/" );
        response.addCookie(cookie);
        return JID;
    }


    /**
     * 主业务服务的主接口，返回主页面。
     *
     * 自定义注解@Check，用于aop拦截对此方法的请求，校验jwt
     *
     */
    @GetMapping("/mainData")
    @Check(module="获取主页面")
    public Object mainData(){

        return  "主页面";
    }
}
