package com.petstore.controller;

import com.petstore.entity.Users;
import com.petstore.service.GoodService;
import com.petstore.service.TypeService;
import com.petstore.util.WebUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/index")
public class UserController {
    static Logger logger = Logger.getLogger(UserController.class);
    @Autowired
    private GoodService goodService;
    @Autowired
    private TypeService typeService;
    @RequestMapping("/homePage")
    public String homePage(HttpServletRequest request, HttpSession session, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("typeList", typeService.getList());
        return "index.jsp";
    }

    @RequestMapping("/userRegister")
    public void userRegister(HttpServletResponse response,HttpServletRequest request,Users user,String verifyCode) {
        logger.debug("用户输入的验证码"+verifyCode);
        if(!verifyCode.toUpperCase().equals(getVerifyCodeInSession(request).toUpperCase())){
            WebUtil.reponseToAjax(response,"userRegister","验证码错误，请重新输入！");
        }else{

            WebUtil.reponseToAjax(response,"userRegister","注册成功,立即登录进入宠物之家吧！");
        }
    }

    @RequestMapping("/userLogin")
    public void userLogin(HttpServletResponse response, HttpServletRequest request, Users user,String verifyCode) {
        logger.debug("用户输入的验证码"+verifyCode);
        if(!verifyCode.toUpperCase().equals(getVerifyCodeInSession(request).toUpperCase())){
            WebUtil.reponseToAjax(response,"userLogin","验证码错误，请重新输入！");
        }else{
            WebUtil.reponseToAjax(response,"userLogin","登录成功！");
        }
    }

    @RequestMapping("/getVerifyCode")
    public void getVerifyCode(HttpServletRequest request,HttpServletResponse response) throws IOException {
        final int width=160; //图片宽度
        final int height=35; //图片高度
        final String imgType="png"; //指定图片格式（不是指MIME类型）只有png格式才能透明显示
        //创建验证码图片并将图片上的字符串设置到session域中
        WebUtil.createVerifyCode(width,height,imgType,request,response);
    }

    public  String getVerifyCodeInSession(HttpServletRequest request){
        String verifyUri=request.getRequestURI();
        verifyUri=verifyUri.substring(0,verifyUri.lastIndexOf("/"))+"/getVerifyCode";
        String verifyCodeInSession=String.valueOf(request.getSession().getAttribute(verifyUri));
        logger.debug("服务器上的验证码"+verifyCodeInSession);
        return verifyCodeInSession;
    }

    @RequestMapping("/logged/lookUpOrderList")
    public void lookUpOrderList(){
        logger.debug(">>>lookUpOrderList...");
    }

    @RequestMapping("/test")
    public void test(HttpServletRequest request){
        Object test=request.getAttribute("test");
        if(test==null){
            System.out.println("为null");
        }
        String teststr=String.valueOf(test);
        System.out.println(teststr);
        String test2=null;
        System.out.println(String.valueOf(test2)+"空空如也");

    }
}
