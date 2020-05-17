package com.petstore.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.config.AlipayConfig;
import com.petstore.entity.*;
import com.petstore.service.GoodService;
import com.petstore.service.OrderService;
import com.petstore.service.TypeService;
import com.petstore.service.UserService;
import com.petstore.util.SafeUtil;
import com.petstore.util.WebUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Controller("userController")
@RequestMapping("/index")
public class UserController {
    static Logger logger = Logger.getLogger(UserController.class);
    @Autowired
    private GoodService goodService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;

    public TypeService getTypeService() {
        return typeService;
    }

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
        if(request.getSession().getAttribute("typeList")==null){
            request.getSession().setAttribute("typeList",typeService.getList());
        }
        request.setAttribute("mostCollectedGoods", getMostCollectedGoods(5));
        request.getSession().setAttribute("fuzzyGoodList",getMostCollectedGoods(-16));
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
                           @RequestParam(required = false, defaultValue = "") String goodListType, @RequestParam(required = false, defaultValue = "0") Integer tempType) throws IOException {
        if (type != -1) {//商品类型，type=-1表示不查询任何商品
            if (goodListType.equals("")) {//商品列表类型,为空时代表只有查询条件只有type
                Map<String, Object> map = goodService.getMap((byte) type, page, limit);
                WebUtil.reponseToJson(response, map);
            } else {
                if (goodListType.equals("collected")) {//用户收藏列表
                    Users user = (Users) request.getSession().getAttribute("user");
                    if (Objects.nonNull(user) && !user.toString().trim().isEmpty()) {//判断用户是否已登录
                        Map<String, Object> map = goodService.getCollectedGoodMap(user.getId(), (byte) type, page, limit);
                        WebUtil.reponseToJson(response, map);
                    } else {
                        response.sendRedirect("../homePage?loginWindow=true");
                    }
                }else if(goodListType.equals("fuzzy")){//模糊查询列表
                    String fuzzyGoodName=request.getParameter("fuzzyGoodName");
                    Map<String, Object> map = goodService.getFuzzyGoodMap(fuzzyGoodName, (byte) type, page, limit);
                    WebUtil.reponseToJson(response, map);
                }
            }
        }
        Types types = null;
        if (tempType != 0) {
            types = typeService.get(tempType);
        }
        String fuzzyGoodName=request.getParameter("fuzzyGoodName");
        request.setAttribute("fuzzyGoodName",fuzzyGoodName);
        request.setAttribute("type", tempType);
        request.setAttribute("types", types);
        request.setAttribute("goodListType", goodListType);
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
     * 加入购物车
     *
     * @param request
     * @param response
     * @param goodId
     * @param amount
     */
    @RequestMapping("/logged/addToCart")
    public void addToCart(HttpServletRequest request, HttpServletResponse response, Integer goodId, Integer amount) {
        Users user = (Users) request.getSession().getAttribute("user");
        try {
            if (goodId != null && amount != null) {
                if (goodService.addToCart(user.getId(), goodId, amount) > 0) {
                    WebUtil.reponseToAjax(response, "addToCart", "0~已加入购物车！");
                }
            } else {
                WebUtil.reponseToAjax(response, "addToCart", "-2~参数异常！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            WebUtil.reponseToAjax(response, "addToCart", "-2~系统异常！");
        }
    }

    /**
     * 我的购物车列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/logged/shopCart")
    public String shopCart(HttpServletRequest request) {
        Users user = (Users) request.getSession().getAttribute("user");
        List<Carts> cartList = goodService.getShopCart(user.getId());
        request.setAttribute("cartList", cartList);
        request.setAttribute("typeList", typeService.getList());
        return "shopCart.jsp";
    }

    /**
     * 更新购物车商品数量
     *
     * @param request
     * @param response
     * @param goodId
     * @param amount
     */
    @RequestMapping("/logged/changeCartAmount")
    public void changeCartAmount(HttpServletRequest request, HttpServletResponse response, Integer goodId, Integer amount) {
        Users user = (Users) request.getSession().getAttribute("user");
        try {
            if (goodId != null && amount != null) {
                goodService.changeCartAmount(user.getId(), goodId, amount);
            } else {
                WebUtil.reponseToAjax(response, "changeCartAmount", "-2~参数异常！");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            WebUtil.reponseToAjax(response, "changeCartAmount", "-2~系统异常！");
        }
    }

    /**
     * 根据用户id和商品id删除购物车
     *
     * @param request
     * @param response
     * @param goodId
     */
    @RequestMapping("/logged/deleteFromCart")
    public void deleteFromCart(HttpServletRequest request, HttpServletResponse response, Integer goodId) {
        Users user = (Users) request.getSession().getAttribute("user");
        try {
            if (goodId != null) {
                goodService.deleteFromCart(user.getId(), goodId);
            } else {
                WebUtil.reponseToAjax(response, "deleteFromCart", "-2~参数异常！");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            WebUtil.reponseToAjax(response, "deleteFromCart", "-2~系统异常！");
        }
    }

    /**
     * 购物车结算
     *
     * @param request
     * @param response
     * @param goodIdList
     * @return
     */
    @Transactional
    @RequestMapping("/logged/settlement")
    public String settlement(HttpServletRequest request, HttpServletResponse response, Integer[] goodIdList) {
        String page=request.getParameter("page");
        if(page!=null&&page.equals("payPage")){
            Orders order=(Orders) request.getSession().getAttribute("orderInSession");
            request.setAttribute("order",order);
        }else{
            Users user = (Users) request.getSession().getAttribute("user");
            List<Items> items = new ArrayList<Items>();
            Float total = new Float(0);
            int amount = 0;
            for (Integer goodId : goodIdList) {
                Items item = new Items();
                Carts cart = goodService.getShopCart(user.getId(), goodId);
                item.setPrice(cart.getGood().getPrice());
                item.setAmount(cart.getAmount());
                item.setCartId(cart.getId());
                item.setGoodId(cart.getGoodId());
                item.setTotal(item.getAmount() * item.getPrice());
                item.setGood(cart.getGood());
                total += item.getTotal();
                amount += item.getAmount();
                items.add(item);
            }
            Orders order = new Orders();
            order.setTotal(total);
            order.setAmount(amount);
            order.setStatus(Orders.STATUS_UNPAY);
//        order.setPaytype(Orders.PAYTYPE_OFFLINE);//默认付款方式为空
            order.setName(user.getName() == null ? user.getUsername() : user.getName());
            order.setPhone(user.getPhone());
            order.setAddress(user.getAddress() == null ? "暂未选择地址" : user.getAddress());
            order.setSystime(Calendar.getInstance().getTime());
            order.setUserId(user.getId());
            order.setUser(user);
            order.setItemsList(items);
            try {
                orderService.addOrder(order);
                for (Integer goodId : goodIdList) {
                    goodService.deleteFromCart(user.getId(),goodId);
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            request.setAttribute("order", order);
            request.getSession().setAttribute("orderInSession",order);
        }
        return "alipay.jsp";
    }

    /**
     * 我的订单
     * @param status
     * @param response
     * @return
     */
    @RequestMapping("/logged/orderList")
    public String orderList(@RequestParam(required = false, defaultValue = "-1") int status,HttpServletResponse response,HttpServletRequest request,
                            @RequestParam(required=false, defaultValue="1") Integer page,@RequestParam(required=false, defaultValue="10")Integer limit) {
        if(status!=-1){
            Users user = (Users) request.getSession().getAttribute("user");
            Map<String, Object> map = orderService.getMap((byte)status,page,limit,user.getId());
            WebUtil.reponseToJson(response, map);
        }
        request.setAttribute("typeList", typeService.getList());
        return "orderList.jsp";
    }

    /**
     * 支付订单
     * @param id
     * @param type
     * @param request
     * @return
     */
    @RequestMapping("/logged/orderUpdate")
    public String orderUpdate(Integer id,Integer type,@RequestParam(required = false, defaultValue = "0") int payType,HttpServletRequest request){
        if(id!=null&&type!=null){
            if(payType!=0){//付款操作
                Orders order=orderService.getOrder(id);
                if(order!=null){
                    if(payType==2){//支付宝方式付款
                        request.setAttribute("order",order);
                        return "alipay.jsp";
                    }else if(payType==3){//货到付款
                        orderService.orderUpdate(id,type);
                        orderService.orderPayTypeUpdate(id,payType);
                        return  "redirect:orderList";
                    }
                }else{
                    request.setAttribute("msg","订单不存在，请重试！");
                    return "orderList";
                }
            }else{//非付款操作
                int effects=orderService.orderUpdate(id,type);
                if(effects>0){
                    request.setAttribute("msg","状态更新成功！");
                    return "orderList";
                }else if(effects==0){
                    request.setAttribute("msg","状态更新失败，请重试！");
                    return "orderList";
                }
            }
        }else {
            request.setAttribute("msg","参数错误！");
            return "orderList";
        }
        request.setAttribute("msg","未定义的更新订单状态！");
        return "orderList";
    }


    /**
     * 支付宝订单异步通知
     * @param request
     */
    @RequestMapping("/orderAlipayNotify")
    public void orderAlipayNotify(HttpServletRequest request) throws AlipayApiException, UnsupportedEncodingException {
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
//		valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——

	/* 实际验证过程建议商户务必添加以下校验：
	1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
	2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
	3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
	4、验证app_id是否为该商户本身。
	*/
        if(signVerified) {//验证成功
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

            if(trade_status.equals("TRADE_FINISHED")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            }else if (trade_status.equals("TRADE_SUCCESS")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序
                orderService.orderUpdate(Integer.parseInt(out_trade_no),2);
                orderService.orderPayTypeUpdate(Integer.parseInt(out_trade_no),2);
                //注意：
                //付款完成后，支付宝系统发送该交易状态通知
            }

        }else {//验证失败
            System.out.println("交易失败！");

            //调试用，写文本函数记录程序运行情况是否正常
            //String sWord = AlipaySignature.getSignCheckContentV1(params);
            //AlipayConfig.logResult(sWord);
        }
    }
    /**
     * 删除订单
     * @param id
     * @return
     */
    @RequestMapping("/logged/orderDelete")
    public String orderDelete(Integer id){
        if(id!=null){
            orderService.deleteById(id);
        }
        return "redirect:orderList";
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
    public List<Goods> getMostCollectedGoods(int limit) {
        List<Map<String, Integer>> goodIdAndCountList = goodService.getMostCollectedGoodIdAndCount(limit);
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
     *用户进入修改信息页面
     * @param request
     * @param session
     * @return
     */
    @RequestMapping("/logged/userRe")
    public String userRe(HttpServletRequest request,HttpSession session){
        return "userReset.jsp";
    }

    /**
     * 用户修改自己的信息
     * @param userNew
     * @param request
     * @param session
     * @return
     */
    @RequestMapping("/logged/userReset")
    public String userReset(Users userNew,HttpServletRequest request,HttpSession session){
        userNew.setPassword(SafeUtil.encode(userNew.getPassword()));
        userNew.setPasswordNew(SafeUtil.encode(userNew.getPasswordNew()));
        Users user =(Users) request.getSession().getAttribute("user");
        user.setPasswordNew(user.getPassword());
        if(!user.getPassword().equals(userNew.getPassword())){
            request.setAttribute("msg","原密码不正确，请重新输入！");
            return "userRe";
        }
        if(user.equals(userNew)){
            request.setAttribute("msg","未作任何修改！");
            return "userRe";
        }
        if(userService.isExist(userNew)){
            request.setAttribute("msg","手机号已被注册！");
            return "userRe";
        }
        if(!user.getSecurityAnswer().equals(userNew.getSecurityAnswer())){
            userNew.setSecurityAnswer(SafeUtil.encode(userNew.getSecurityAnswer()));
        }
        userNew.setPassword(userNew.getPasswordNew());
        if(userService.updateUser(userNew)>0){
            request.getSession().setAttribute("user",userNew);
            request.setAttribute("msg","信息修改成功！");
            return "userRe";
        }
        request.setAttribute("msg","信息修改失败！");
        return "userRe";

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
