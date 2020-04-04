<%@ page import="com.petstore.util.Constants" %><%--
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
    <link rel="stylesheet" href="../resources/layui/css/layui.css">
    <link rel="stylesheet" href="../resources/css/index/style.css">
    <title><%=Constants.WEB_TITLE_NAME%></title>
</head>
<body>
    <%@include file="header.jsp" %>
<div class="subscribe" style="border: 1px solid red"></div>
<div style="height: 200px;width: 300px;border: 1px solid #000000"></div>
<div style="height: 200px;width: 300px;border: 1px solid #000000"></div>
<div class="subscribe" style="border: 1px solid blue"></div>
<div style="height: 200px;width: 300px;border: 1px solid #000000"></div>
</body>
</html>
