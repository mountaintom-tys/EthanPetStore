package com.petstore.controller;

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
import java.io.PrintWriter;

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
        logger.debug("I AM ETHANT!");
        return "index.jsp";
    }

    @RequestMapping("/userRegister")
    public void userRegister(HttpServletResponse response) {
        WebUtil.reponseToAjax(response,"userRegister","注册成功！");
    }
    @RequestMapping("/userLogin")
    public void userLogin(HttpServletResponse response) {
        WebUtil.reponseToAjax(response,"userLogin","登录成功！");
    }
}
