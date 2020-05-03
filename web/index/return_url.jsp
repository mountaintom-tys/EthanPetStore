<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="utf-8" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.alipay.config.*" %>
<%@ page import="com.alipay.api.*" %>
<%@ page import="com.alipay.api.internal.util.*" %>
<%@ page import="com.petstore.service.TypeService" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%--    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">--%>
    <title>宠物之家收账结果页</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/layui/css/layui.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/layui/layui.js"></script>
    <%
        /* *
         * 功能：支付宝服务器同步通知页面
         * 日期：2017-03-30
         * 说明：
         * 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
         * 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。


         *************************页面功能说明*************************
         * 该页面仅做页面展示，业务逻辑处理请勿在该页面执行
         */

        //获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
//            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
        pageContext.setAttribute("signVerified", signVerified);
        //——请在这里编写您的程序（以下代码仅作参考）——
        if (signVerified) {
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
            pageContext.setAttribute("out_trade_no", out_trade_no);
            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
            pageContext.setAttribute("trade_no", trade_no);
            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");
            pageContext.setAttribute("total_amount", total_amount);
        } else {
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
            pageContext.setAttribute("out_trade_no", out_trade_no);
            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
            pageContext.setAttribute("trade_no", trade_no);
            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");
            pageContext.setAttribute("total_amount", total_amount);
        }%>
</head>
<body style="background-color: #eaeaea">
<%@include file="logged/header.jsp" %>
<div class="content content-nav-base datails-content" style="width: 60%;margin: 0 auto;line-height: 3em">
    <div class="data-cont-wrap w1200">
        <div class="crumb">
            <a href="javascript:;">当前位置</a>
            <span>：</span>
            <a href="javascript:;">首页</a>
            <span>></span>
            <a href="javascript:;">支付页面</a>
        </div>
    </div>
    <div class="layui-container" style="width: 100%;height:400px;background-color: #ffffff;">
        <div class="layui-row" style="padding-top: 10%">
            <c:if test="${signVerified}">
                <div class="layui-col-md4" style="text-align: right;margin:40px 20px 0 0">
                    <i class="layui-icon layui-icon-ok-circle" style="font-size: 60px; color: #6bdda6;"></i>
                </div>
                <div class="layui-col-md4">
                    <h4>订单支付成功！</h4>
                    <p>订单号：${out_trade_no}&nbsp;&nbsp;支付金额：<span style="color: #ff5722">￥${total_amount}</span></p>
                    <p>
                        <a href="logged/orderList" style="color: #0000FF">查看我的订单</a>
                        <a href="homePage" style="color: #0000FF;margin-left: 50px">继续购物</a>
                    </p>
                </div>
            </c:if>
            <c:if test="${!signVerified}">
                <div class="layui-col-md4" style="text-align: right;margin:40px 20px 0 0">
                    <i class="layui-icon layui-icon-close-fill" style="font-size: 60px; color: #da1026;"></i>
                </div>
                <div class="layui-col-md4">
                    <h4>订单状态异常！</h4>
                    <p>订单号：${out_trade_no}&nbsp;&nbsp;支付金额：<span style="color: #ff5722">￥${total_amount}</span></p>
                    <p><a href="javascript:;" style="color: red">请点击网站页脚售后服务联系客服进行处理！</a></p>
                </div>
            </c:if>
        </div>
    </div>
</div>
<div>
    <%@include file="footer.jsp" %>
</div>
</body>
</html>