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
    <link rel="stylesheet" href="../resources/layui/css/layui.css">
    <style>
        .layui-nav{
            padding: 0 0 0 20%;
            background-color: #482f09;
        }
        .layui-nav .layui-nav-itemp {
            position: relative;
            display: inline-block;
            *display: inline;
            *zoom: 1;
            vertical-align: middle;
            line-height: 60px;
        }
        .layui-select-tips.layui-this::after{
            background-color: unset;
        }
    </style>
</head>
<body>
<ul class="layui-nav" lay-filter="demo">
    <li class="layui-nav-item"><span style="font-size:25px;margin-right: 20px">宠物之家</span></li>
    <li id="orderList" class="layui-nav-item layui-this"><a href="goodList">首页</a></li>
    <li class="layui-nav-item" >
        <a href="javascript:;">商品分类</a>
        <dl class="layui-nav-child">
            <c:forEach var="type" items="${typeList}">
                <dd><a href="${type.id}">${type.name}</a></dd>
            </c:forEach>
        </dl>
    </li>
    <li id="goodList" class="layui-nav-item"><a href="goodList">注册</a></li>
    <li id="typeList1" class="layui-nav-item"><a href="typeList">登录</a></li>
    <li id="typeList2" class="layui-nav-item"><a href="typeList">后台管理</a></li>
    <li id="typeList3" class="layui-nav-itemp">
        <div class="layui-form" style="display: inline-block;color: black">
            <div class="">
                <div class="layui-input-inline">
                    <select name="city" lay-verify="required" lay-search="">
                        <option value="">带搜索的选择框</option>
                        <option value="1">layer</option>
                        <option value="2">form</option>
                        <option value="3">layim</option>
                        <option value="4">element</option>
                        <option value="5">laytpl</option>
                        <option value="6">upload</option>
                        <option value="7">laydate</option>
                        <option value="8">laypage</option>
                        <option value="9">flow</option>
                        <option value="10">util</option>
                        <option value="11">code</option>
                        <option value="12">tree</option>
                        <option value="13">layedit</option>
                        <option value="14">nav</option>
                        <option value="15">tab</option>
                        <option value="16">table</option>
                        <option value="17">select</option>
                        <option value="18">checkbox</option>
                        <option value="19">switch</option>
                        <option value="20">radio</option>
                    </select>
                </div>
            </div>
        </div>
    </li>
    <li id="newsList" class="layui-nav-item"><a href="javascript:;">新消息<span class="layui-badge">9</span></a></li>
    <li id="adminRe" class="layui-nav-item">
        <a href="javascript:;"><img src="//t.cn/RCzsdCq" class="layui-nav-img">我</a>
        <dl class="layui-nav-child">
            <dd><a href="adminRe">修改信息</a></dd>
            <dd><a href="logout">退出</a></dd>
        </dl>
    </li>
</ul>

<script type="text/javascript" src="../resources/layui/layui.js"></script>
<script>

    //注意：导航 依赖 element 模块，否则无法进行功能性操作
    layui.use(['jquery', 'element','form'], function () {
        var element = layui.element;
        var $ = layui.$;
        var curhref = location.href;
        $(".layui-nav li").removeClass("layui-this")
        if (curhref.indexOf("adminRe") != -1) {
            $("#adminRe").addClass("layui-this")
        } else if (curhref.indexOf("user") != -1) {
            $("#userList").addClass("layui-this")
        } else if (curhref.indexOf("good") != -1) {
            $("#goodList").addClass("layui-this")
        } else if (curhref.indexOf("type") != -1) {
            $("#typeList").addClass("layui-this")
        } else if (curhref.indexOf("news") != -1) {
            $("#newsList").addClass("layui-this")
        } else {
            $("#orderList").addClass("layui-this")
        }
    });
</script>

</body>
</html>
