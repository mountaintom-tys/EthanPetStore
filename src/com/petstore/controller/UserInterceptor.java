package com.petstore.controller;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class UserInterceptor extends HandlerInterceptorAdapter {
    /**
     * 检测登录状态
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        request.getSession().setAttribute("/EthanPetStore/index/getVerifyCode","test");//跳过验证码设置
        String uri = request.getRequestURI();
        if(uri.contains("/logged")) {
            Object username = request.getSession().getAttribute("userName");
            if (Objects.nonNull(username) && !username.toString().trim().isEmpty()) {
                return true; // 登录验证通过
            }else{
                response.sendRedirect("../homePage?loginWindow=true");
                return false; // 其他情况一律拦截
            }
        }
       return true;
    }

}
