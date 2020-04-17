<%@ page import="com.petstore.util.Constants" %>
<%@ page import="java.util.Objects" %><%--
  Created by IntelliJ IDEA.
  User: ethant
  Date: 01/09/20
  Time: 9:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="../resources/css/index/style.css">
    <title><%=Constants.FRONT_WEB_TITLE_NAME%>
    </title>
</head>
<body onload="layui.$('i').load()">
<%
    Object user = request.getSession().getAttribute("user");
    if (Objects.nonNull(user) && !user.toString().trim().isEmpty()) {%>
<%@include file="logged/header.jsp" %>
<%} else {%>
<%@include file="header.jsp" %>
<%}%>
<div style="height: 200px;width: 300px;border: 1px solid #000000"></div>
<div class="subscribe"></div>
<div style="min-height:450px;">
    <link rel="stylesheet" href="../resources/layui/admin/admin.css?v=1.4.0 pro-1" media="all">
    <div class="layui-container" id="LAY_app_body">
        <div class="layadmin-tabsbody-item layui-show">
            <link rel="stylesheet" href="../resources/layui/admin/template.css?v=1.4.0 pro-1" media="all">
            <div class="layui-fluid layadmin-cmdlist-fluid">
                <div class="layui-row layui-col-space30">
                    <c:forEach var="good" items="${goodList}">
                        <div class="layui-col-md3 layui-col-sm4">
                            <div class="cmdlist-container">
                                <a href="gioodDetail?id=${good.id}">
                                    <img style="height: 200px" src="../${good.cover}">
                                </a>
                                <a href="goodDetail?id=${good.id}">
                                    <div class="cmdlist-text">
                                        <p class="info">${good.name}</p>
                                        <div class="price">
                                            <b>￥${good.price}</b>
                                            <p>
                                                ¥
                                                <del>${good.price*1.25}</del>
                                            </p>
                                            <span class="flow">
                                                <i class="layui-icon layui-icon-rate" onload="getGoodCollectedStatus(this,${good.id})" data-opt="add" onclick="changeGoodCollectedStatus(this,${good.id},event)">404</i>
                                            </span>
                                        </div>
                                    </div>
                                </a>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="subscribe"></div>
<div style="height: 200px;width: 300px;border: 1px solid #000000"></div>
</body>
</html>
