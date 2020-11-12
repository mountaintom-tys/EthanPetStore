<%@ page import="com.petstore.util.Constants" %>
<%@ page import="java.util.Objects" %><%--
  Created by IntelliJ IDEA.
  User: MountainTom-ASUS
  Date: 2020/4/19
  Time: 19:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><%=Constants.WEB_TITLE_NAME%></title>
    <link rel="stylesheet" type="text/css" href="../static/static/css/main.css">
</head>
<body onload="layui.$('i').load()">
<%
    Object user = request.getSession().getAttribute("user");
    if (Objects.nonNull(user) && !user.toString().trim().isEmpty()) {%>
<%@include file="logged/header.jsp" %>
<%} else {%>
<%@include file="header.jsp" %>
<%}%>
<div class="content content-nav-base datails-content">
    <div class="data-cont-wrap w1200">
        <div class="crumb">
            <span>></span>
            <a href="javascript:;">商品详情</a>
        </div>
        <div class="product-intro layui-clear">
            <div class="preview-wrap">
                <a href="javascript:;"><img src="../${good.cover}" style="margin-top:52px;width: 98%"></a>
            </div>
            <div class="itemInfo-wrap">
                <div class="itemInfo">
                    <div class="title">
                        <h4>${good.name}</h4>
                        <span><i class="layui-icon layui-icon-rate"
                                 onload="getGoodCollectedStatus(this,${good.id})" data-opt="add"
                                 onclick="changeGoodCollectedStatus(this,${good.id},event)">404</i>收藏</span>
                    </div>
                    <div class="summary">
                        <p class="reference"><span>参考价</span>
                            <del>￥${good.price*1.25}</del>
                        </p>
                        <p class="activity"><span>活动价</span><strong class="price"><i>￥</i>${good.price}</strong></p>
                        <p class="address-box"><span>介&nbsp;&nbsp;&nbsp;&nbsp;绍</span><p class="address">${good.intro}</p>
                        </p>
                    </div>
                    <div class="choose-attrs">
                        <div class="color layui-clear"><span class="title">类&nbsp;&nbsp;&nbsp;&nbsp;目</span>
                            <div class="color-cont"><span class="btn active">${good.type.name}</span></div>
                        </div>
                        <div class="color layui-clear"><span class="title">当前库存</span>
                            <div class="color-cont"><span class="btn active">${good.stock}</span></div>
                        </div>
                        <div class="number layui-clear"><span class="title">数&nbsp;&nbsp;&nbsp;&nbsp;量</span>
                            <div class="number-cont"><span class="cut btn">-</span><input id="amount"
                                    onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                    onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                    maxlength="4" type="" name="" value="1"><span class="add btn">+</span></div>
                        </div>
                    </div>
                    <div class="choose-btns">
<%--                        <button class="layui-btn layui-btn-primary purchase-btn">立刻购买</button>--%>
                        <button onclick="addToCart(${good.id},layui.$('#amount')[0].value)" class="layui-btn  layui-btn-danger car-btn"><i
                                class="layui-icon layui-icon-cart-simple"></i>加入购物车
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var msg='${msg}';
    layui.config({
        base: '../resources/static/js/util/' //你存放新模块的目录，注意，不是layui的模块目录
    }).use(['mm','jquery','layer'],function(){
        var mm = layui.mm,$ = layui.$,layer=layui.layer;
        if(msg!=null&&msg!=""){
            layui.layer.msg(msg.toString());
        }
        var cur = $('.number-cont input').val();
        $('.number-cont .btn').on('click',function(){
            if($(this).hasClass('add')){
                cur++;

            }else{
                if(cur > 1){
                    cur--;
                }
            }
            $('.number-cont input').val(cur)
        })

    });
</script>
</body>
</html>
