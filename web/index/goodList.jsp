<%@ page import="com.petstore.util.Constants" %>
<%@ page import="java.util.Objects" %><%--
  Created by IntelliJ IDEA.
  User: MountainTom-ASUS
  Date: 2020/4/19
  Time: 22:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><%=Constants.FRONT_WEB_TITLE_NAME%></title>
    <link rel="stylesheet" type="text/css" href="../resources/static/css/main.css">
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
            <a href="javascript:;">商品列表</a>
        </div>
    </div>
    <div class="product-intro layui-clear">
        <div style="min-height:450px;">
            <link rel="stylesheet" href="../resources/layui/admin/admin.css?v=1.4.0 pro-1" media="all">
            <div class="layui-container" id="LAY_app_body">
                <div class="layadmin-tabsbody-item layui-show">
                    <link rel="stylesheet" href="../resources/layui/admin/template.css?v=1.4.0 pro-1" media="all">
                    <div class="layui-fluid layadmin-cmdlist-fluid">
                        <div class="layui-row layui-col-space30" id="lazyLoadContainer">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    layui.use(['flow','laytpl'], function () {
        var flow = layui.flow;
        var laytpl = layui.laytpl;
        var type='${type}';//商品类型
        var goodListType='${goodListType}';//商品列表类型
        var tyeps='${types}';
        var fuzzyGoodName='${fuzzyGoodName}';
        if(tyeps!=""){
            layui.$(".crumb")[0].innerHTML=layui.$("#navigate")[0].innerHTML;
        }else if(goodListType!=""){
            if(goodListType=="collected"){
                layui.$(".crumb a")[0].innerText='我的收藏';
            }else if(goodListType=="fuzzy"){
                layui.$(".crumb a")[0].innerText='搜索结果';
            }
        }
        //手动加载
        flow.load({
            elem: '#lazyLoadContainer' //流加载容器
            // ,scrollElem: '#LAY_demo2' //滚动条所在元素，一般不用填，此处只是演示需要。
            , isAuto: false
            , isLazyimg: true
            , done: function (page, next) { //加载下一页
                var lis = [];
                var limit=8;
                layui.$.ajax({
                    url: 'goodList?type='+type+'&page=' + page + '&limit='+limit+'&goodListType='+goodListType+'&fuzzyGoodName='+fuzzyGoodName,
                    type: 'get',
                    async: true,
                    success: function (result) {
                        setTimeout(function () {
                            var getTpl = layui.$("#lazyLoadItem")[0].innerHTML;
                            // var lazyLoadContainer = layui.$("#lazyLoadContainer")[0];
                            laytpl(getTpl).render(result, function(html){
                                // lazyLoadContainer.innerHTML = html;
                                lis.push(html);
                                next(lis.join(''), page < (result.count)/limit);
                            });
                            layui.$('i').load();//立即刷新所有商品的被收藏状态和数量
                        },page==1?0:200)//如果是第一页，立即加载，不需要延时
                    }
                })
            }
        });
    });
</script>
<%--流加载项，使用了lauyui模板引擎--%>
<script id="lazyLoadItem" type="text/html">
    {{#  layui.each(d.data, function(index, item){ }}
    <div class="layui-col-md3 layui-col-sm4">
        <div class="cmdlist-container">
            <a href="goodDetail?id={{ item.id }}">
                <img style="height: 200px" src="../{{ item.cover }}" title="{{ item.type.name }}">
            </a>
            <a href="goodDetail?id={{ item.id }}">
                <div class="cmdlist-text">
                    <p class="info">{{ item.name }}</p>
                    <div class="price">
                        <b>￥{{ item.price }}</b>
                        <p>
                            ¥
                            <del>{{ item.price*1.25 }}</del>
                        </p>
                        <span class="flow">
                            <i class="layui-icon layui-icon-rate"
                               onload="getGoodCollectedStatus(this,'{{ item.id }}')" data-opt="add"
                               onclick="changeGoodCollectedStatus(this,'{{ item.id }}',event)">404</i>
                          </span>
                    </div>
                </div>
            </a>
        </div>
    </div>
    {{# }); }}
    {{# if(d.data.length === 0){ }}
    无数据
    {{# } }}
</script>
<script id="navigate" type="text/html">
    <span>></span>
    <a href="javascript:;">商品分类</a>
    <span>></span>
    <a href="javascript:;">${types.name}</a>
</script>
<div>
    <%@include file="footer.jsp" %>
</div>
</body>
</html>
