package com.petstore.controller;

import com.petstore.entity.Goods;
import com.petstore.entity.Types;
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
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    /**
     * 首页
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/homePage")
    public String homePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("typeList", typeService.getList());
        request.setAttribute("mostCollectedGoods", getMostCollectedGoods());
        return "index.jsp";
    }

    /**
     * 产品列表
     *
     * @return
     */
    @RequestMapping("/goodList")
    public String goodList(@RequestParam(required = false, defaultValue = "-1") int type, HttpServletRequest request, HttpServletResponse response,
                           @RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "8") Integer limit,
                           @RequestParam(required = false,defaultValue = "") String goodListType,@RequestParam(required = false,defaultValue = "0") Integer tempType) throws IOException {
        if (type != -1) {//商品类型，type=-1表示不查询任何商品
            if (goodListType.equals("")) {//商品列表类型,为空时代表只有查询条件只有type
                Map<String, Object> map = goodService.getMap((byte) type, page, limit);
                WebUtil.reponseToJson(response, map);
            } else {
                if(goodListType.equals("collected")){
                    Users user = (Users) request.getSession().getAttribute("user");
                    if (Objects.nonNull(user) && !user.toString().trim().isEmpty()) {//判断用户是否已登录
                        Map<String, Object> map = goodService.getCollectedGoodMap(user.getId(),(byte) type, page, limit);
                        WebUtil.reponseToJson(response, map);
                    } else {
                        response.sendRedirect("../homePage?loginWindow=true");
                    }
                }
            }
        }
        Types types=null;
        if(tempType!=0){
            types=typeService.get(tempType);
        }
        request.setAttribute("type",tempType);
        request.setAttribute("types",types);
        request.setAttribute("goodListType",goodListType);
        request.setAttribute("typeList", typeService.getList());
        return "goodList.jsp";
    }

    /**
     * 商品详情
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/goodDetail")
    public String goodDetail(HttpServletRequest request, Integer id) {
        try {
            Goods good = goodService.get(id);
            if (good != null) {
                request.setAttribute("good", good);
            } else {
                request.setAttribute("msg", "数据获取异常！");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            request.setAttribute("msg", "系统异常！");
        } finally {
            request.setAttribute("typeList", typeService.getList());
            return "detail.jsp";
        }
    }

    /**
     * 根据用户id和商品id获取当前商品收藏状态和被收藏总次数，用户未登录的情况下默认收藏状态为false
     *
     * @param request
     * @param response
     * @param goodId
     */
    @RequestMapping("/getGoodCollectedStatus")
    public void getGoodCollectedStatus(HttpServletRequest request, HttpServletResponse response, int goodId) {
        Users user = (Users) request.getSession().getAttribute("user");
        boolean collectedStatus = false;
        int collectedCount = 0;
        try {
            if (Objects.nonNull(user) && !user.toString().trim().isEmpty()) {//判断用户是否已登录
                if (goodService.getGoodCollectedStatus(user.getId(), goodId)) {
                    collectedStatus = true;
                }
            }
            collectedCount = goodService.getGoodCollectedCount(goodId);
            WebUtil.reponseToAjax(response, "getGoodCollectedStatus", "0~" + collectedStatus + "~" + collectedCount);
        } catch (Exception e) {
            logger.error(e.getMessage());
            WebUtil.reponseToAjax(response, "getGoodCollectedStatus", "-1~" + collectedStatus + "~" + collectedCount);
        }
    }

    /**
     * 获取被收藏次数最多的商品，底层接口同时返回了对应的商品的数量，暂未使用
     *
     * @return
     */
    @RequestMapping("/getMostCollectedGoods")
    public List<Goods> getMostCollectedGoods() {
        List<Map<String, Integer>> goodIdAndCountList = goodService.getMostCollectedGoodIdAndCount();
        List<Goods> mostCollectedGoodList = new ArrayList<>();
        if (goodIdAndCountList != null) {
            for (Map<String, Integer> goodIdAndCount : goodIdAndCountList) {
                mostCollectedGoodList.add(goodService.get(goodIdAndCount.get("good_id")));
            }
        } else {
            mostCollectedGoodList.add(((List<Goods>) goodService.getMap((byte) 0, 1, 1).get("data")).get(0));
        }
        return mostCollectedGoodList;
    }

    /**
     * 根据用户id、opt操作类型和商品id对collections收藏表进行增加和删除
     *
     * @param response
     * @param request
     * @param opt
     * @param goodId
     */
    @RequestMapping("/logged/changeGoodCollectedStatus")
    public void changeGoodCollectedStatus(HttpServletResponse response, HttpServletRequest request, String opt, int goodId) {
        Users user = (Users) request.getSession().getAttribute("user");
        try {
            if (opt.equals("add")) {
                goodService.addGoodCollection(user.getId(), goodId);
                WebUtil.reponseToAjax(response, "changeGoodCollectedStatus", "0~已添加至收藏夹！");
            } else if (opt.equals("del")) {
                goodService.deleteGoodCollection(user.getId(), goodId);
                WebUtil.reponseToAjax(response, "changeGoodCollectedStatus", "0~已移出收藏夹！");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            WebUtil.reponseToAjax(response, "changeGoodCollectedStatus", "-2~操作失败，请稍后再试！");
        }
    }

    /**
     * 用户注册
     * 响应代码规则：
     * 0~成功   -1~验证码错误   -2~手机号错误  -3~密码错误   -4~密保错误   5~系统错误
     *
     * @param response
     * @param request
     * @param user
     * @param verifyCode
     */
    @RequestMapping("/userRegister")
    public void userRegister(HttpServletResponse response, HttpServletRequest request, Users user, String verifyCode) {
        logger.debug("用户输入的验证码" + verifyCode);
        if (!verifyCode.toUpperCase().equals(getVerifyCodeInSession(request).toUpperCase())) {
            WebUtil.reponseToAjax(response, "userRegister", "-1~验证码错误，请重新输入！");
        } else {
            if (userService.getUserByPhone(user.getPhone()) != null) {
                WebUtil.reponseToAjax(response, "userRegister", "-2~该手机号已被注册，请更换手机号！");
            } else {
                user.setPassword(SafeUtil.encode(user.getPassword()));
                user.setSecurityAnswer(SafeUtil.encode(user.getSecurityAnswer()));
                if (userService.addUser(user) < 0) {
                    WebUtil.reponseToAjax(response, "userRegister", "-5~注册失败,请稍后再试！");
                } else {
                    WebUtil.reponseToAjax(response, "userRegister", "0~注册成功,立即登录进入宠物之家吧！");
                }
            }

        }
    }

    /**
     * 用户登录
     * 响应代码规则：
     * 0~成功   -1~验证码错误   -2~手机号错误  -3~密码错误   -4~密保错误   5~系统错误
     *
     * @param response
     * @param request
     * @param user
     * @param verifyCode
     */
    @RequestMapping("/userLogin")
    public void userLogin(HttpServletResponse response, HttpServletRequest request, Users user, String verifyCode) {
        logger.debug("用户输入的验证码" + verifyCode);
        if (!verifyCode.toUpperCase().equals(getVerifyCodeInSession(request).toUpperCase())) {
            WebUtil.reponseToAjax(response, "userLogin", "-1~验证码错误，请重新输入！");
        } else {
            Users userdb = userService.getUserByPhone(user.getPhone());
            if (userdb == null) {
                WebUtil.reponseToAjax(response, "userLogin", "-2~手机号暂未注册，请先注册！");
            } else {
                if (!userdb.getPassword().equals(SafeUtil.encode(user.getPassword()))) {
                    WebUtil.reponseToAjax(response, "userLogin", "-3~账号或密码错误，请重新输入！");
                } else {
                    request.getSession().setAttribute("user", userdb);
                    WebUtil.reponseToAjax(response, "userLogin", "0~登录成功！");
                }
            }
        }
    }

    /**
     * 用户忘记密码，重置密码
     * 响应代码规则：
     * 0~成功   -1~验证码错误   -2~手机号错误  -3~密码错误   -4~密保错误   5~系统错误
     *
     * @param response
     * @param request
     * @param user
     * @param verifyCode
     */
    @RequestMapping("/userPasswordReset")
    public void userPasswordReset(HttpServletResponse response, HttpServletRequest request, Users user, String verifyCode) {
        logger.debug("用户输入的验证码" + verifyCode);
        if (!verifyCode.toUpperCase().equals(getVerifyCodeInSession(request).toUpperCase())) {
            WebUtil.reponseToAjax(response, "userPasswordReset", "-1~验证码错误，请重新输入！");
        } else {
            Users userdb = userService.getUserByPhone(user.getPhone());
            if (userdb == null) {
                WebUtil.reponseToAjax(response, "userPasswordReset", "-2~手机号暂未注册，请先注册！");
            } else {
                if (userdb.getSecurityQuestion().equals(user.getSecurityQuestion()) && userdb.getSecurityAnswer().equals(SafeUtil.encode(user.getSecurityAnswer()))) {
                    userdb.setPassword(SafeUtil.encode(user.getPasswordNew()));
                    if (userService.updateUser(userdb) < 0) {
                        WebUtil.reponseToAjax(response, "userPasswordReset", "-5~密码重置失败，请稍后再试！");
                    } else {
                        WebUtil.reponseToAjax(response, "userPasswordReset", "-0~密码重置成功，立即登录吧！");
                    }
                } else {
                    WebUtil.reponseToAjax(response, "userPasswordReset", "-4~密保问题或密保答案有误，请重新输入！");
                }
            }
        }
    }

    /**
     * 退出登录
     *
     * @param session
     * @return
     */
    @RequestMapping("/logged/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:../homePage";
    }

    @RequestMapping("/getVerifyCode")
    public void getVerifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final int width = 160; //图片宽度
        final int height = 35; //图片高度
        final String imgType = "png"; //指定图片格式（不是指MIME类型）只有png格式才能透明显示
        //创建验证码图片并将图片上的字符串设置到session域中
        WebUtil.createVerifyCode(width, height, imgType, request, response);
    }

    public String getVerifyCodeInSession(HttpServletRequest request) {
        String verifyUri = request.getRequestURI();
        verifyUri = verifyUri.substring(0, verifyUri.lastIndexOf("/")) + "/getVerifyCode";
        String verifyCodeInSession = String.valueOf(request.getSession().getAttribute(verifyUri));
        logger.debug("服务器上的验证码" + verifyCodeInSession);
        return verifyCodeInSession;
    }

    @RequestMapping("/logged/lookUpOrderList")
    public void lookUpOrderList() {
        logger.debug(">>>lookUpOrderList...");
    }

    @RequestMapping("/test")
    public void test(HttpServletRequest request) {
        Object test = request.getAttribute("test");
        if (test == null) {
            System.out.println("为null");
        }
        String teststr = String.valueOf(test);
        System.out.println(teststr);
        String test2 = null;
        System.out.println(String.valueOf(test2) + "空空如也");

    }
}
