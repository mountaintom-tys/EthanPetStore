package com.petstore.controller;

import com.alibaba.fastjson.JSON;
import com.petstore.entity.Admins;
import com.petstore.entity.Goods;
import com.petstore.service.AdminService;
import com.petstore.service.GoodService;
import com.petstore.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final int rows = 10;

    @Autowired
    private AdminService adminService;
    @Autowired
    private GoodService goodService;

    /**
     * 管理员登录
     *
     * @return
     */
    @RequestMapping("/login")
    public String login(Admins admin, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws ServletException, IOException {
        if (adminService.checkUser(admin.getUsername(), admin.getPassword())) {
            session.setAttribute("username", admin.getUsername());
            return "orderList";
        }
        request.setAttribute("msg", "用户名或密码错误!");
        return "login.jsp";
    }

    /**
     * 订单列表
     *
     * @return
     */
    @RequestMapping("/orderList")
    public String orderList() {
        return "orderList.jsp";
    }

    /**
     * 顾客管理
     *
     * @return
     */
    @RequestMapping("/userList")
    public String userList() {
        return "userList.jsp";
    }

    /**
     * 产品列表
     *
     * @return
     */
    @RequestMapping("/goodList")
    public String goodList(@RequestParam(required = false, defaultValue = "0") byte type, HttpServletRequest request, HttpServletResponse response,
                           @RequestParam(required = false,defaultValue = "1")int page) {
        if (type != 0) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=utf-8");
            Map<String, Object> map=goodService.getList(type,page,rows);
            reponseToJson(response,map);
        }
        return "goodList.jsp";
    }

    public void reponseToJson(HttpServletResponse response,Map<String, Object> map){
        PrintWriter writer= null;
        try {
            writer = response.getWriter();
            writer.write(JSON.toJSONString(map));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(writer!=null){
                writer.close();
            }
        }
    }

    /**
     * 产品添加
     *
     * @param name
     * @param price
     * @param intro
     * @param stock
     * @param typeId
     * @param cover
     * @return
     */
    @RequestMapping("/goodSave")
    public String goodSave(String name, int price, String intro, int stock, int typeId, String cover) throws Exception {
        Goods good = new Goods();
        good.setName(name);
        good.setPrice(price);
        good.setIntro(intro);
        good.setStock(stock);
        good.setTypeId(typeId);
        good.setCover(cover);
        goodService.add(good);
        return "redirect:goodList";
    }

    /**
     * 上传文件
     *
     * @param file
     * @param res
     */
    @RequestMapping("/uploadFile")
    public void uploadFile(MultipartFile file, HttpServletResponse res) throws Exception {
        String savaPath = UploadUtil.fileUpload(file);
        res.setCharacterEncoding("UTF-8");
        res.setContentType("application/json;charset=utf-8");
        Map<String, Object> map = new HashMap<>();
        if (savaPath != null) {
            map.put("url", savaPath);
            map.put("code", 0);
        } else {
            map.put("code", 1);
        }
        reponseToJson(res,map);
    }
}
