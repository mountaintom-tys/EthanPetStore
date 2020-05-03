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
    <%
        String loginWindow=request.getParameter("loginWindow");
    %>
</head>
<body>
<ul class="layui-nav" lay-filter="demo">
    <li class="layui-nav-item"><span style="font-size:25px;margin-right: 20px">宠物之家</span></li>
    <li id="orderList" class="layui-nav-item layui-this"><a href="homePage">首页</a></li>
    <li class="layui-nav-item">
        <a href="javascript:;">商品分类</a>
        <dl class="layui-nav-child">
            <c:forEach var="type" items="${typeList}">
                <dd><a href="goodList?tempType=${type.id}">${type.name}</a></dd>
            </c:forEach>
        </dl>
    </li>
    <li id="register" class="layui-nav-item"><a onclick="userRegister()" href="javascript:;">注册</a></li>
    <li id="login" class="layui-nav-item"><a onclick="userLogin()" href="javascript:;">登录</a></li>
    <li id="admin" class="layui-nav-item"><a href="../admin/orderList">后台管理</a></li>
    <li id="typeList3" class="layui-nav-itemp" style="margin-left: 60px">
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
<%--    <li id="adminRe" class="layui-nav-item">--%>
<%--        <a href="javascript:;"><img src="//t.cn/RCzsdCq" class="layui-nav-img">我</a>--%>
<%--        <dl class="layui-nav-child">--%>
<%--            <dd><a href="adminRe">修改信息</a></dd>--%>
<%--            <dd><a href="logout">退出</a></dd>--%>
<%--        </dl>--%>
<%--    </li>--%>
</ul>

<script type="text/javascript" src="../resources/layui/layui.js"></script>
<script src="../resources/js/index/index.js"></script>
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
        layer.config({
            offset: '25%',
            extend: 'myskin/style.css'
        });
        form.verify({
            confirmPassword: function (value, item) {
                var password = item.parentElement.parentElement.previousElementSibling.getElementsByTagName("input")[0];
                if (password.value != value) {
                    item.value = "";
                    item.focus();
                    return "两次输入密码不一致！";
                }
            },
            checkPassword: function (value,item) {
                var msg="";
                if(!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)){
                    msg='密码不能有特殊字符';
                }
                if(/(^\_)|(\__)|(\_+$)/.test(value)){
                    msg='密码首尾不能出现下划线\'_\'';
                }
                if(/^\d*\d+\d$/.test(value)){
                    msg='密码不能全为数字';
                }
                if(!(/^[\S]{2,12}$/.test(value))){
                    msg='密码必须2到12位，且不能出现空格';
                }
                if(msg!=""){
                    item.value="";
                    item.focus();
                    return msg;
                }
            },
            checkLength: function (value,item) {
                if(!(/^[\S]{2,12}$/.test(value))){
                    item.value="";
                    item.focus();
                    return item.name+'必须2到12位，且不能出现空格';
                }
            }
        });
        var loginWindow='<%=loginWindow%>';
        if(loginWindow=="true"){
            userLogin();
            layer.msg("暂未登录，请先登录！");
        }
    });
    var userRegisterIndex;
    var userLoginIndex;
    //注册弹出层
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
            btn: ['立即注册', '取消','立即登录'],
            btnAlign: 'c',
            yes: function (index, layero) {
                //按钮【按钮一】的回调
                var userRegisterSubmit = layero.find("#userRegisterSubmit")[0];
                userRegisterSubmit.click();
                // layer.close(index);
            },
            btn3: function (index,layero) {
                layer.close(index);
                userLogin();
            }
        });
    }
    //登录弹出层
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
            btn: ['立即登录', '取消','忘记密码？','立即注册'],
            btnAlign: 'c',
            yes: function (index, layero) {
                //按钮【按钮一】的回调
                var userRegisterSubmit = layero.find("#userLoginSubmit")[0];
                userRegisterSubmit.click();
                // layer.close(index);
            },
            btn3: function(index,layero){
              layer.close(index);
              userPasswordReset();
            },
            btn4: function (index,layero) {
                layer.close(index);
                userRegister();
            }
        });
    }
    //忘记密码，重置密码弹出层
    function userPasswordReset() {
        userRegisterIndex = layer.open({
            title: '重置密码',
            type: 1,
            skin: 'layer-ext-myskin',
            maxmin: true, //允许全屏最小化
            anim: 1, //0-6的动画形式，-1不开启
            area: '600px',
            offset: '100px',
            shadeClose: true, //点击遮罩关闭
            content: layui.$("#userPasswordResetForm")[0].innerHTML,
            btn: ['立即重置', '取消','返回登录'],
            btnAlign: 'c',
            yes: function (index, layero) {
                //按钮【按钮一】的回调
                var userPasswordResetSubmit = layero.find("#userPasswordResetSubmit")[0];
                userPasswordResetSubmit.click();
                // layer.close(index);
            },
            btn3: function (index,layero) {
                layer.close(index);
                userLogin();
            }
        });
    }
    //刷新验证码
    function verifyCodeRefresh(verifyImg) {
        var source = verifyImg.src ; // 获得原来的 src 中的内容
        //console.log( "source : " + source  ) ;

        var index = source.indexOf( "?" ) ;  // 从 source 中寻找 ? 第一次出现的位置 (如果不存在则返回 -1 )
        //console.log( "index : " + index  ) ;

        if( index > -1 ) { // 如果找到了 ?  就进入内部
            var s = source.substring( 0 , index ) ; // 从 source 中截取 index 之前的内容 ( index 以及 index 之后的内容都被舍弃 )
            //console.log( "s : " + s  ) ;

            var date = new Date(); // 创建一个 Date 对象的 一个 实例
            var time = date.getTime() ; // 从 新创建的 Date 对象的实例中获得该时间对应毫秒值
            verifyImg.src = s + "?time=" + time ; // 将 加了 尾巴 的 地址 重新放入到 src 上

            //console.log( e.src ) ;
        } else {
            var date = new Date();
            verifyImg.src = source + "?time=" + date.getTime();
        }
    }
</script>

</body>
<%--注册弹出层表单--%>
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
                <input type="password" name="password" required lay-verify="required|checkPassword" placeholder="请输入密码"
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <i class="layui-form-label layui-icon layui-icon-password"></i>
            <div class="layui-input-inline">
                <input type="password" name="repassword" required lay-verify="required|confirmPassword"
                       placeholder="请再次输入密码"
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <i class="layui-form-label layui-icon layui-icon-survey"></i>
            <div class="layui-input-block">
                <input type="text" name="securityQuestion" required lay-verify="required|checkLength" placeholder="请输入密保问题"
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <i class="layui-form-label layui-icon layui-icon-auz"></i>
            <div class="layui-input-inline">
                <input type="password" name="securityAnswer" required lay-verify="required|checkLength" placeholder="请输入密保答案"
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <i class="layui-form-label layui-icon layui-icon-username"></i>
            <div class="layui-input-inline">
                <input type="text" name="username" required lay-verify="required|checkLength" placeholder="请输入昵称"
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <i class="layui-form-label layui-icon layui-icon-vercode"></i>
            <div class="layui-input-inline">
                <input type="text" name="verifyCode" required lay-verify="required" placeholder="请输入验证码"
                       autocomplete="off" class="layui-input">
            </div>
            <img src="getVerifyCode" onclick="verifyCodeRefresh(this)">
        </div>
        <div class="layui-form-item" style="display: none">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="userRegisterSubmit" id="userRegisterSubmit">立即提交
                </button>
            </div>
        </div>
    </form>
    <script>
        function userRegisterFormSubmit(form) {
            var formDate = layui.$(form).serialize();
            layui.$.ajax({
                url: 'userRegister',
                data: formDate,
                type: 'post',
                async: true,
                success: function (result) {
                    userModuleFeedbackParse(result,form);
                    // layer.msg(result);
                    // layer.close(userRegisterIndex)
                }
            })
        }
    </script>
</div>
<%--登录弹出层表单--%>
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
                <input type="password" name="password" required lay-verify="required|checkPassword" placeholder="请输入密码"
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <i class="layui-form-label layui-icon layui-icon-vercode"></i>
            <div class="layui-input-inline">
                <input type="text" name="verifyCode" required lay-verify="required" placeholder="请输入验证码"
                       autocomplete="off" class="layui-input">
            </div>
            <img src="getVerifyCode" onclick="verifyCodeRefresh(this)">
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
                    if(userModuleFeedbackParse(result,form)){
                        setTimeout(window.location.href='homePage',3000);//设置延时3秒
                    }
                    // layer.msg(result, {icon: 5,anim: 6});
                    // layer.close(userLoginIndex)
                }
            })
        }
    </script>
</div>
<%--密码重置弹出层表单--%>
<div id="userPasswordResetForm" style="display: none">
    <form class="layui-form" onsubmit="userPasswordResetFormSubmit(this)" action="javascript:;"
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
            <i class="layui-form-label layui-icon layui-icon-survey"></i>
            <div class="layui-input-block">
                <input type="text" name="securityQuestion" required lay-verify="required|checkLength" placeholder="请输入密保问题"
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <i class="layui-form-label layui-icon layui-icon-auz"></i>
            <div class="layui-input-inline">
                <input type="password" name="securityAnswer" required lay-verify="required|checkLength" placeholder="请输入密保答案"
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <i class="layui-form-label layui-icon layui-icon-password"></i>
            <div class="layui-input-inline">
                <input type="password" name="passwordNew" required lay-verify="required|checkPassword"
                       placeholder="请输入新密码"
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <i class="layui-form-label layui-icon layui-icon-vercode"></i>
            <div class="layui-input-inline">
                <input type="text" name="verifyCode" required lay-verify="required" placeholder="请输入验证码"
                       autocomplete="off" class="layui-input">
            </div>
            <img src="getVerifyCode" onclick="verifyCodeRefresh(this)">
        </div>
        <div class="layui-form-item" style="display: none">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="userRegisterSubmit" id="userPasswordResetSubmit">立即提交
                </button>
            </div>
        </div>
    </form>
    <script>
        function userPasswordResetFormSubmit(form) {
            var formDate = layui.$(form).serialize();
            layui.$.ajax({
                url: 'userPasswordReset',
                data: formDate,
                type: 'post',
                async: true,
                success: function (result) {
                    userModuleFeedbackParse(result,form);
                    // layer.msg(result);
                    // layer.close(userRegisterIndex)
                }
            })
        }
    </script>
</div>

</html>