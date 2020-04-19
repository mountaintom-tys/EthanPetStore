package com.petstore.controller;

import com.petstore.entity.Admins;
import com.petstore.entity.Goods;
import com.petstore.entity.Types;
import com.petstore.service.AdminService;
import com.petstore.service.GoodService;
import com.petstore.service.OrderService;
import com.petstore.service.TypeService;
import com.petstore.util.SafeUtil;
import com.petstore.util.UploadUtil;
import com.petstore.util.WebUtil;
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
    @Autowired
    private TypeService typeService;
    @Autowired
    private OrderService orderService;

    /**
     * 管理员登录
     *
     * @return
     */
    @RequestMapping("/login")
    public String login(Admins admin, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws ServletException, IOException {
        if (adminService.checkUser(admin.getUsername(), admin.getPassword())) {
            session.setAttribute("adminName", admin.getUsername());
            return "orderList";
        }
        request.setAttribute("msg", "用户名或密码错误!");
        return "login.jsp";
    }

    /**
     * 退出
     * @param session
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("adminName");
        return "login.jsp";
    }

    /**
     *管理员进入修改信息页面
     * @param request
     * @param session
     * @return
     */
    @RequestMapping("/adminRe")
    public String adminRe(HttpServletRequest request,HttpSession session){
        request.setAttribute("admin",adminService.getByUserName(String.valueOf(session.getAttribute("adminName"))));
        return "adminReset.jsp";
    }

    /**
     * 管理员修改自己的信息
     * @param adminNew
     * @param request
     * @param session
     * @return
     */
    @RequestMapping("/adminReset")
    public String adminReset(Admins adminNew,HttpServletRequest request,HttpSession session){
        adminNew.setPassword(SafeUtil.encode(adminNew.getPassword()));
        adminNew.setPasswordNew(SafeUtil.encode(adminNew.getPasswordNew()));
        Admins admin = adminService.getByUserName(String.valueOf(session.getAttribute("adminName")));
        admin.setPasswordNew(admin.getPassword());
        if(!admin.getPassword().equals(adminNew.getPassword())){
            request.setAttribute("msg","原密码不正确，请重新输入！");
            return "adminRe";
        }
        if(admin.equals(adminNew)){
            request.setAttribute("msg","未作任何修改！");
            return "adminRe";
        }
        if(adminService.isExist(adminNew)){
            request.setAttribute("msg","用户名已存在！");
            return "adminRe";
        }
        adminNew.setSecurityAnswer(SafeUtil.encode(adminNew.getSecurityAnswer()));
        if(adminService.update(adminNew)){
            session.setAttribute("adminName",adminNew.getUsername());
            request.setAttribute("msg","信息修改成功！");
            return "adminRe";
        }
        request.setAttribute("msg","信息修改失败！");
        return "adminRe";

    }

    /**
     * 订单中心
     * @param status
     * @param response
     * @return
     */
    @RequestMapping("/orderList")
    public String orderList(@RequestParam(required = false, defaultValue = "-1") int status,HttpServletResponse response,
                            @RequestParam(required=false, defaultValue="1") Integer page,@RequestParam(required=false, defaultValue="10")Integer limit) {
        if(status!=-1){
            Map<String, Object> map = orderService.getMap((byte)status,page,limit);
            WebUtil.reponseToJson(response, map);
        }
        return "orderList.jsp";
    }

    /**
     * 更新订单
     * @param id
     * @param type
     * @param page
     * @param request
     * @return
     */
    @RequestMapping("/orderUpdate")
    public String orderUpdate(Integer id,Integer type,@RequestParam(required=false, defaultValue="1") int page,HttpServletRequest request){
        if(id!=null&&type!=null){
            int effects=orderService.orderUpdate(id,type);
            if(effects>0){
                request.setAttribute("msg","状态更新成功！");
                return "orderList";
            }else if(effects==0){
                request.setAttribute("msg","状态更新失败，请重试！");
                return "orderList";
            }else{
                request.setAttribute("msg","参数错误！");
                return "orderList";
            }
        }
        return "orderList";
    }

    /**
     * 删除订单
     * @param id
     * @return
     */
    @RequestMapping("/orderDelete")
    public String orderDelete(Integer id){
        if(id!=null){
            orderService.deleteById(id);
        }
        return "redirect:orderList";
    }
    /**
     * 产品列表
     *
     * @return
     */
    @RequestMapping("/goodList")
    public String goodList(@RequestParam(required = false, defaultValue = "-1") int type,HttpServletRequest request,HttpServletResponse response,
                           @RequestParam(required=false, defaultValue="1") Integer page,@RequestParam(required=false, defaultValue="10")Integer limit) {
        if (type != -1) {//type=-1表示不查询任何商品
            Map<String, Object> map = goodService.getMap((byte)type,page,limit);
            WebUtil.reponseToJson(response, map);
        }
        request.setAttribute("typeList", typeService.getList());
        return "goodList.jsp";
    }

    /**
     * 产品添加
     *
     * @param request
     * @return
     */
    @RequestMapping("/goodAdd")
    public String goodAdd(HttpServletRequest request) {
        request.setAttribute("typeList", typeService.getList());
        return "goodAdd.jsp";
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
        WebUtil.reponseToJson(res, map);
    }

    /**
     * 产品更新
     *
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("/goodEdit")
    public String goodEdit(int id, HttpServletRequest request) {
        request.setAttribute("typeList", typeService.getList());
        request.setAttribute("good", goodService.get(id));
        return "goodEdit.jsp";
    }

    /**
     * 产品更新
     * @return
     */
    @RequestMapping("/goodUpdate")
    public String goodUpdate(Goods good, HttpServletRequest request) {
        Goods gooddb = goodService.get(good.getId());
        good.setStatus(gooddb.getStatus());
        if (gooddb.equals(good)) {
            request.setAttribute("msg", "未修改任何数据！");
        } else {
            if (goodService.update(good)) {
                request.setAttribute("msg", "更新成功！");
            } else {
                request.setAttribute("msg", "更新失败！");
            }
        }
        return "goodEdit?id=" + good.getId();
    }

    /**
     * 产品删除
     * @param id
     * @return
     */
    @RequestMapping("/goodDelete")
    public String goodDelete(int id,String cover){
        Goods good = goodService.get(id);
        good.setStatus(2);//设置商品下架
        goodService.update(good);
//        goodService.delete(id);
//        UploadUtil.fileDelete(cover);
        return "redirect:goodList";
    }

    /**
     * 类目列表
     * @param request
     * @return
     */
    @RequestMapping("/typeList")
    public String typeList(@RequestParam(required = false, defaultValue = "-1") int type,HttpServletRequest request,HttpServletResponse response,
                           @RequestParam(required=false, defaultValue="1") Integer page,@RequestParam(required=false, defaultValue="10")Integer limit){
        if(type!=-1){
            Map<String, Object> map=typeService.getMap((byte) type,page,limit);
            WebUtil.reponseToJson(response, map);
        }
        return "typeList.jsp";
    }

    /**
     * 新增类目
     * @param type
     * @return
     */
    @RequestMapping("/typeAdd")
    public String typeAdd(Types type){
        typeService.add(type);
        return "redirect:typeList";
    }

    /**
     * 更新类目
     * @param type
     * @return
     */
    @RequestMapping("/typeEdit")
    public String typeEdit(Types type){
        typeService.update(type);
        return "redirect:typeList";
    }

    /**
     * 删除类目
     * @param id
     * @return
     */
    @RequestMapping("/typeDelete")
    public String typeDelete(int id){
        typeService.delete(id);
        return "redirect:typeList";
    }

}
