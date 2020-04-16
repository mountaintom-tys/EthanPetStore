package com.petstore.controller;

import com.petstore.entity.Users;
import com.petstore.service.GoodService;
import com.petstore.service.TypeService;
import com.petstore.service.UserService;
import com.petstore.util.SafeUtil;
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
    @Autowired
    private UserService userService;
    @RequestMapping("/homePage")
    public String homePage(HttpServletRequest request, HttpSession session, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("typeList", typeService.getList());
        return "index.jsp";
    }

    /**
     * 用户注册
     * 响应代码规则：
     * 0~成功   -1~验证码错误   -2~手机号错误  -3~密码错误   -4~密保错误   5~系统错误
     * @param response
     * @param request
     * @param user
     * @param verifyCode
     */
    @RequestMapping("/userRegister")
    public void userRegister(HttpServletResponse response,HttpServletRequest request,Users user,String verifyCode) {
        logger.debug("用户输入的验证码"+verifyCode);
        if(!verifyCode.toUpperCase().equals(getVerifyCodeInSession(request).toUpperCase())){
            WebUtil.reponseToAjax(response,"userRegister","-1~验证码错误，请重新输入！");
        }else{
            if(userService.getUserByPhone(user.getPhone())!=null){
                WebUtil.reponseToAjax(response,"userRegister","-2~该手机号已被注册，请更换手机号！");
            }else{
                user.setPassword(SafeUtil.encode(user.getPassword()));
                user.setSecurityAnswer(SafeUtil.encode(user.getSecurityAnswer()));
                if(userService.addUser(user)<0){
                    WebUtil.reponseToAjax(response,"userRegister","-5~注册失败,请稍后再试！");
                }else{
                    WebUtil.reponseToAjax(response,"userRegister","0~注册成功,立即登录进入宠物之家吧！");
                }
            }

        }
    }

    /**
     * 用户登录
     * 响应代码规则：
     * 0~成功   -1~验证码错误   -2~手机号错误  -3~密码错误   -4~密保错误   5~系统错误
     * @param response
     * @param request
     * @param user
     * @param verifyCode
     */
    @RequestMapping("/userLogin")
    public void userLogin(HttpServletResponse response, HttpServletRequest request, Users user,String verifyCode) {
        logger.debug("用户输入的验证码"+verifyCode);
        if(!verifyCode.toUpperCase().equals(getVerifyCodeInSession(request).toUpperCase())){
            WebUtil.reponseToAjax(response,"userLogin","-1~验证码错误，请重新输入！");
        }else{
            Users userdb=userService.getUserByPhone(user.getPhone());
            if(userdb==null){
                WebUtil.reponseToAjax(response,"userLogin","-2~手机号暂未注册，请先注册！");
            }else{
                if(!userdb.getPassword().equals(SafeUtil.encode(user.getPassword()))){
                    WebUtil.reponseToAjax(response,"userLogin","-3~账号或密码错误，请重新输入！");
                }else{
                    request.getSession().setAttribute("userName", user.getUsername());
                    WebUtil.reponseToAjax(response,"userLogin","0~登录成功！");
                }
            }
        }
    }

    /**
     * 用户忘记密码，重置密码
     * 响应代码规则：
     * 0~成功   -1~验证码错误   -2~手机号错误  -3~密码错误   -4~密保错误   5~系统错误
     * @param response
     * @param request
     * @param user
     * @param verifyCode
     */
    @RequestMapping("/userPasswordReset")
    public void userPasswordReset(HttpServletResponse response,HttpServletRequest request,Users user,String verifyCode){
        logger.debug("用户输入的验证码"+verifyCode);
        if(!verifyCode.toUpperCase().equals(getVerifyCodeInSession(request).toUpperCase())){
            WebUtil.reponseToAjax(response,"userPasswordReset","-1~验证码错误，请重新输入！");
        }else{
            Users userdb=userService.getUserByPhone(user.getPhone());
            if(userdb==null){
                WebUtil.reponseToAjax(response,"userPasswordReset","-2~手机号暂未注册，请先注册！");
            }else{
                if(userdb.getSecurityQuestion().equals(user.getSecurityQuestion())&&userdb.getSecurityAnswer().equals(SafeUtil.encode(user.getSecurityAnswer()))){
                    userdb.setSecurityAnswer(SafeUtil.encode(user.getSecurityAnswer()));
                    if(userService.updateUser()<0){
                        WebUtil.reponseToAjax(response,"userPasswordReset","-5~密码重置失败，请稍后再试！");
                    }else{
                        WebUtil.reponseToAjax(response,"userPasswordReset","-0~密码重置成功，立即登录吧！");
                    }
                }else{
                    WebUtil.reponseToAjax(response,"userPasswordReset","-4~密保问题或密保答案有误，请重新输入！");
                }
            }
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
