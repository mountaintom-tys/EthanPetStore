package com.petstore.controller;

import com.petstore.util.WebUtil;
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
//        request.getSession().setAttribute("/EthanPetStore/index/getVerifyCode","test");//跳过验证码设置
        String uri = request.getRequestURI();
        if(uri.contains("/logged")) {
            Object user = request.getSession().getAttribute("user");
            if (Objects.nonNull(user) && !user.toString().trim().isEmpty()) {
                return true; // 登录验证通过
            }else{
                if(request.getParameter("ajax")==null){
                    response.sendRedirect("../homePage?loginWindow=true");
                    return false; // 其他情况一律拦截
                }else{//当前请求为ajax请求
                    String servletPath=request.getServletPath();
                    String msg=servletPath.substring(servletPath.lastIndexOf("/")+1);
                    WebUtil.reponseToAjax(response,msg,"-1~还未登录哦，请先登录！");
                }
            }
        }
       return true;
    }

}
