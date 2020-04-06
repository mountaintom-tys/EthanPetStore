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
    <li id="orderList" class="layui-nav-item layui-this"><a href="goodList">首页</a></li>
    <li class="layui-nav-item">
        <a href="javascript:;">商品分类</a>
        <dl class="layui-nav-child">
            <c:forEach var="type" items="${typeList}">
                <dd><a href="${type.id}">${type.name}</a></dd>
            </c:forEach>
        </dl>
    </li>
    <li id="register" class="layui-nav-item"><a onclick="userRegister()" href="javascript:;">注册</a></li>
    <li id="login" class="layui-nav-item"><a onclick="userLogin()" href="javascript:;">登录</a></li>
    <li id="admin" class="layui-nav-item"><a href="../admin/orderList">后台管理</a></li>
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
    layui.use(['jquery', 'element', 'form', 'layer'], function () {
        var element = layui.element;
        var form = layui.form;
        var $ = layui.$;
        var layer = layui.layer;
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
        layer.config({
            offset: '40%',
            extend: 'myskin/style.css'
        })
    });
    var userRegisterIndex;
    var userLoginIndex;

    function userRegister() {
        userRegisterIndex = layer.open({
            title: '用户注册',
            type: 1,
            skin: 'layer-ext-myskin',
            maxmin: true, //允许全屏最小化
            anim: 1, //0-6的动画形式，-1不开启
            area: '600px',
            offset: '100px',
            shadeClose: true, //点击遮罩关闭
            content: layui.$("#userRegisterForm")[0].innerHTML,
            btn: ['立即注册', '取消'],
            btnAlign: 'c',
            yes: function (index, layero) {
                //按钮【按钮一】的回调
                var userRegisterSubmit = layero.find("#userRegisterSubmit")[0];
                userRegisterSubmit.click();
                // layer.close(index);
            }
        });
    }
    function userLogin() {
        userLoginIndex = layer.open({
            title: '用户登录',
            type: 1,
            skin: 'layer-ext-myskin',
            maxmin: true, //允许全屏最小化
            anim: 1, //0-6的动画形式，-1不开启
            area: '600px',
            offset: '100px',
            shadeClose: true, //点击遮罩关闭
            content: layui.$("#userLoginForm")[0].innerHTML,
            btn: ['立即登录', '取消','忘记密码？'],
            btnAlign: 'c',
            yes: function (index, layero) {
                //按钮【按钮一】的回调
                var userRegisterSubmit = layero.find("#userLoginSubmit")[0];
                userRegisterSubmit.click();
                // layer.close(index);
            }
        });
    }
</script>

</body>
<div id="userRegisterForm" style="display: none">
    <form class="layui-form" onsubmit="userRegisterFormSubmit(this)" action="javascript:;"
          style="width: 80%;margin: 0 auto;">
        <br>
        <br>
        <div class="layui-form-item">
            <i class="layui-form-label layui-icon layui-icon-cellphone"></i>
            <div class="layui-input-block">
                <input type="text" name="phone" required lay-verify="required|number|phone" placeholder="请输入手机号"
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <i class="layui-form-label layui-icon layui-icon-password"></i>
            <div class="layui-input-inline">
                <input type="password" name="password" required lay-verify="required" placeholder="请输入密码"
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <i class="layui-form-label layui-icon layui-icon-password"></i>
            <div class="layui-input-inline">
                <input type="password" name="repassword" required lay-verify="required|checkPassword"
                       placeholder="请再次输入密码"
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <i class="layui-form-label layui-icon layui-icon-survey"></i>
            <div class="layui-input-block">
                <input type="text" name="securityQuestion" required lay-verify="required" placeholder="请输入密保问题"
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <i class="layui-form-label layui-icon layui-icon-vercode"></i>
            <div class="layui-input-inline">
                <input type="password" name="securityAnswer" required lay-verify="required" placeholder="请输入密保答案"
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <i class="layui-form-label layui-icon layui-icon-username"></i>
            <div class="layui-input-inline">
                <input type="text" name="username" required lay-verify="required" placeholder="请输入昵称"
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item" style="display: none">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="userRegisterSubmit" id="userRegisterSubmit">立即提交
                </button>
            </div>
        </div>
    </form>
    <script>
        layui.use(['form', 'jquery'], function () {
            var form = layui.form, $ = layui.$;
            form.verify({
                checkPassword: function (value, item) {
                    var password = item.parentElement.parentElement.previousElementSibling.getElementsByTagName("input")[0];
                    if (password.value != value) {
                        item.value = "";
                        item.focus();
                        return "两次输入密码不一致！";
                    }
                }
            });
        });

        function userRegisterFormSubmit(form) {
            var formDate = layui.$(form).serialize();
            layui.$.ajax({
                url: 'userRegister',
                data: formDate,
                type: 'post',
                async: true,
                success: function (result) {
                    alert(result)
                    layer.msg(result);
                    layer.close(userRegisterIndex)
                }
            })
        }
    </script>
</div>
<div id="userLoginForm" style="display: none">
    <form class="layui-form" onsubmit="userLoginFormSubmit(this)" action="javascript:;"
          style="width: 80%;margin: 0 auto;">
        <br>
        <br>
        <div class="layui-form-item">
            <i class="layui-form-label layui-icon layui-icon-cellphone"></i>
            <div class="layui-input-block">
                <input type="text" name="phone" required lay-verify="required|number|phone" placeholder="请输入手机号"
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <i class="layui-form-label layui-icon layui-icon-password"></i>
            <div class="layui-input-inline">
                <input type="password" name="password" required lay-verify="required" placeholder="请输入密码"
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <i class="layui-form-label layui-icon layui-icon-vercode"></i>
            <div class="layui-input-inline">
                <input type="text" name="securityCode" required lay-verify="required" placeholder="请输入验证码"
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item" style="display: none">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="userRegisterSubmit" id="userLoginSubmit">立即提交
                </button>
            </div>
        </div>
    </form>
    <script>
        function userLoginFormSubmit(form) {
            var formDate = layui.$(form).serialize();
            layui.$.ajax({
                url: 'userLogin',
                data: formDate,
                type: 'post',
                async: true,
                success: function (result) {
                    alert(result)
                    layer.msg(result);
                    layer.close(userLoginIndex)
                }
            })
        }
    </script>
</div>

</html>