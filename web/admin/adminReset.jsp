<%@ page import="com.petstore.util.Constants" %><%--
  Created by IntelliJ IDEA.
  User: mountaintom-ASUS
  Date: 2020/1/19
  Time: 18:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><%=Constants.WEB_TITLE_NAME%></title>
    <style>
        .site-block .layui-form {
            margin-right: 200px;
        }

        .site-block {
            padding: 70px;
            border-bottom: 1px solid #eee;
        }

        .site-text {
            position: relative;
        }
    </style>
</head>
<BODY>
<%@include file="header.jsp" %>
<div class="layui-main site-inline">
    <div class="site-content">
        <div class="site-text site-block">
            <fieldset class="layui-elem-field layui-field-title site-title">
                <legend><a name="bgcolor">修改信息</a></legend>
            </fieldset>
            <form class="layui-form" action="adminReset" method="post">
                <div class="layui-form-item">
                    <label class="layui-form-label">用户名</label>
                    <div class="layui-input-block">
                        <input type="hidden" name="id" value="${admin.id}">
                        <input type="text" name="username" required lay-verify="required" value="${admin.username}" placeholder="请输入名称"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">原密码</label>
                    <div class="layui-input-inline">
                        <input type="password" name="password" required lay-verify="required" value="" placeholder="请输入原密码"
                               autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">新密码</label>
                    <div class="layui-input-inline">
                        <input type="password" name="passwordNew" required lay-verify="required" value="" placeholder="请输入新密码"
                               autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">密保问题</label>
                    <div class="layui-input-block">
                        <input type="text" name="securityQuestion" required lay-verify="required" value="${admin.securityQuestion}" placeholder="请输入密保问题"
                               autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">密保答案</label>
                    <div class="layui-input-inline">
                        <input type="password" name="securityAnswer" required lay-verify="required" value="${admin.securityAnswer}" placeholder="请输入密保答案"
                               autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <button class="layui-btn" lay-submit lay-filter="formDemo">提交修改</button>
                        <button type="button" onclick="javascript:location.reload();" class="layui-btn layui-btn-primary">重置</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script>
    layui.use(['form','layer'], function(){
        var form = layui.form,$=layui.$,layer=layui.layer;
        var msg="${msg}";
        if(msg!=null && msg.trim()!="" && msg!=undefined){
            layer.msg(msg);
        }
    });

</script>
</BODY>
</html>
