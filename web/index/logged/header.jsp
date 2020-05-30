<%@ page import="java.util.ArrayList" %>
<%@ page import="com.petstore.entity.Types" %><%--
  Created by IntelliJ IDEA.
  User: ethant
  Date: 01/10/20
  Time: 13:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/layui/css/layui.css">
    <style>
        .layui-nav {
            padding: 0 0 0 20%;
            background-color: #392f26;
        }

        .layui-nav .layui-nav-itemp {
            position: relative;
            display: inline-block;
            *display: inline;
            *zoom: 1;
            vertical-align: middle;
            line-height: 60px;
        }

        .layui-select-tips.layui-this::after {
            background-color: unset;
        }
    </style>
</head>
<body>
<ul class="layui-nav" lay-filter="demo">
    <li class="layui-nav-item"><span style="font-size:25px;margin-right: 20px">宠物之家</span></li>
    <li id="homePage" class="layui-nav-item layui-this"><a href="${pageContext.request.contextPath}/index/homePage">首页</a></li>
    <li id="goodList" class="layui-nav-item">
        <a href="javascript:;">商品分类</a>
        <dl class="layui-nav-child">
            <c:forEach var="type" items="${typeList}">
                <dd><a href="${pageContext.request.contextPath}/index/goodList?tempType=${type.id}">${type.name}</a></dd>
            </c:forEach>
        </dl>
    </li>
    <li id="collections" class="layui-nav-item"><a href="${pageContext.request.contextPath}/index/goodList?goodListType=collected">我的收藏</a></li>
    <li id="shopCart" class="layui-nav-item"><a href="${pageContext.request.contextPath}/index/logged/shopCart">购物车</a></li>
    <li id="orderList" class="layui-nav-item"><a href="${pageContext.request.contextPath}/index/logged/orderList">我的订单</a></li>
    <li id="admin" class="layui-nav-item"><a href="${pageContext.request.contextPath}/admin/orderList">后台管理</a></li>
    <li id="typeList3" class="layui-nav-itemp">
        <div class="layui-form" style="display: inline-block;color: black">
            <div class="">
                <div class="layui-input-inline">
                    <select name="fuzzyGoodSelect" lay-verify="required" lay-search="" lay-filter="fuzzyGoodSelect">
                        <option value="">带搜索的选择框</option>
                        <c:forEach var="good" items="${fuzzyGoodList}">
                            <option value="${good.name}">${good.name}</option>
                        </c:forEach>
                    </select>
                    <a href="" id="fuzzyGoodLink" style="visibility: hidden"></a>
                </div>
            </div>
        </div>
    </li>
<%--    <li id="newsList" class="layui-nav-item"><a href="javascript:;">新消息<span class="layui-badge">9</span></a></li>--%>
    <li id="userRe" class="layui-nav-item">
        <a href="javascript:;"><img src="//t.cn/RCzsdCq" class="layui-nav-img">我</a>
        <dl class="layui-nav-child">
            <dd><a href="${pageContext.request.contextPath}/index/logged/userRe">修改信息</a></dd>
            <dd><a href="${pageContext.request.contextPath}/index/logged/logout">退出</a></dd>
        </dl>
    </li>
</ul>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/layui/layui.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/index/index.js"></script>
<script>

    //注意：导航 依赖 element 模块，否则无法进行功能性操作
    layui.use(['jquery', 'element', 'form', 'layer'], function () {
        var element = layui.element;
        var form = layui.form;
        var $ = layui.$;
        var layer = layui.layer;
        var curhref = location.href;
        $(".layui-nav li").removeClass("layui-this")
        if (curhref.indexOf("goodList") != -1) {
            $("#goodList").addClass("layui-this")
        } else if (curhref.indexOf("collected") != -1) {
            $("#collections").addClass("layui-this")
        } else if (curhref.indexOf("shopCart") != -1) {
            $("#shopCart").addClass("layui-this")
        } else if (curhref.indexOf("orderList") != -1) {
            $("#orderList").addClass("layui-this")
        }else if (curhref.indexOf("news") != -1) {
            $("#newsList").addClass("layui-this")
        }else if (curhref.indexOf("userRe") != -1) {
            $("#userRe").addClass("layui-this")
        } else {
            $("#homePage").addClass("layui-this")
        }
        var fuzzy="";
        //输入框的值改变时触发
        $(".layui-select-title .layui-input").on("input",function(e){
            //获取input输入的值
            fuzzy=e.delegateTarget.value
        });
        form.on('select(fuzzyGoodSelect)',function(data){
            var fuzzyGoodName;
            if(data.value==""){
                fuzzyGoodName=fuzzy;
            }else{
                fuzzyGoodName=data.value;
            }

            var url="${pageContext.request.contextPath}/index/goodList?goodListType=fuzzy&fuzzyGoodName="+fuzzyGoodName;
            var a=$("#fuzzyGoodLink")[0];
            a.href=url;
            a.click();
        })
    });
</script>
</body>
</html>