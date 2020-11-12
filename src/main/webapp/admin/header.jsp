<%--
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
    <link rel="stylesheet" href="../static/layui/css/layui.css">
</head>
<body>
<ul class="layui-nav" lay-filter="demo">
    <li class="layui-nav-item"><span>后台管理</span></li>
    <li id="orderList" class="layui-nav-item layui-this"><a href="orderList">订单管理</a></li>
    <li id="goodList" class="layui-nav-item"><a href="goodList">商品管理</a></li>
    <li id="typeList" class="layui-nav-item"><a href="typeList">类目管理</a></li>
<%--    <li id="newsList" class="layui-nav-item"><a href="javascript:;">新消息<span class="layui-badge">9</span></a></li>--%>
    <li id="adminRe" class="layui-nav-item">
        <a href="javascript:;"><img src="//t.cn/RCzsdCq" class="layui-nav-img">我</a>
        <dl class="layui-nav-child">
            <dd><a href="adminRe">修改信息</a></dd>
            <dd><a href="logout">退出</a></dd>
        </dl>
    </li>
</ul>
<script type="text/javascript" src="../static/layui/layui.js"></script>
<script>
    //注意：导航 依赖 element 模块，否则无法进行功能性操作
    layui.use(['jquery','element'], function(){
        var element = layui.element;
            var $=layui.$;
        var curhref=location.href;
        $(".layui-nav li").removeClass("layui-this")
        if (curhref.indexOf("adminRe")!=-1){
            $("#adminRe").addClass("layui-this")
        } else if(curhref.indexOf("user")!=-1){
            $("#userList").addClass("layui-this")
        }else if(curhref.indexOf("good")!=-1){
            $("#goodList").addClass("layui-this")
        }else if(curhref.indexOf("type")!=-1){
            $("#typeList").addClass("layui-this")
        }else if(curhref.indexOf("news")!=-1){
            $("#newsList").addClass("layui-this")
        }else{
            $("#orderList").addClass("layui-this")
        }
    });
</script>

</body>
</html>
