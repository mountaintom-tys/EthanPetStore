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
    <link rel="stylesheet" href="../static/css/index/style.css">
    <title><%=Constants.FRONT_WEB_TITLE_NAME%>
    </title>
    <style>
        .layui-flow-more a cite{
            background-color: #fff!important;
        }
    </style>
</head>
<body onload="layui.$('i').load()">
<%
    Object user = request.getSession().getAttribute("user");
    if (Objects.nonNull(user) && !user.toString().trim().isEmpty()) {%>
<%@include file="logged/header.jsp" %>
<%} else {%>
<%@include file="header.jsp" %>
<%}%>
<div class="site-banner">
    <div id="hostCarousel" class="layui-carousel">
        <div carousel-item>
            <c:forEach var="good" items="${mostCollectedGoods}">
                <div class="banner">
                    <div class="bannerInfo">
                        <h2 class="hdng"><a href="goodDetail?id=${good.id}">${good.name}</a><span></span></h2>
                        <p>今日人气推荐</p>
                        <a class="banner_a" href="javascript:;" onclick="addToCart(${good.id},1)">加入购物车</a>
                    </div>
                    <div class="bannerImg">
                        <a href="goodDetail?id=${good.id}">
                            <img src="../${good.cover}" alt="${good.name}" width="348" height="350">
                        </a>
                    </div>
                </div>
            </c:forEach>
        </div>

    </div>
    <script>
        layui.use('carousel', function () {
            var carousel = layui.carousel;
            //建造实例
            carousel.render({
                elem: '#hostCarousel'
                , width: '100%' //设置容器宽度
                , height: '100%'
                , arrow: 'always' //始终显示箭头
                // ,anim: 'fade' //切换动画方式
            });
        });
    </script>
</div>
<div class="subscribe"></div>
<div style="min-height:450px;">
    <link rel="stylesheet" href="../static/layui/admin/admin.css?v=1.4.0 pro-1" media="all">
    <div class="layui-container" id="LAY_app_body">
        <div class="layadmin-tabsbody-item layui-show">
            <link rel="stylesheet" href="../static/layui/admin/template.css?v=1.4.0 pro-1" media="all">
            <div class="layui-fluid layadmin-cmdlist-fluid">
                <div class="layui-row layui-col-space30" id="lazyLoadContainer">
<%--                    取消直接从session中获取goodList方式，采用layui流加载结合模板引擎方式动态获取批量商品列表--%>
<%--                    <c:forEach var="good" items="${goodList}">--%>
<%--                        <div class="layui-col-md3 layui-col-sm4">--%>
<%--                            <div class="cmdlist-container">--%>
<%--                                <a href="gioodDetail?id=${good.id}">--%>
<%--                                    <img style="height: 200px" src="../${good.cover}" title="${good.type.name}">--%>
<%--                                </a>--%>
<%--                                <a href="goodDetail?id=${good.id}">--%>
<%--                                    <div class="cmdlist-text">--%>
<%--                                        <p class="info">${good.name}</p>--%>
<%--                                        <div class="price">--%>
<%--                                            <b>￥${good.price}</b>--%>
<%--                                            <p>--%>
<%--                                                ¥--%>
<%--                                                <del>${good.price*1.25}</del>--%>
<%--                                            </p>--%>
<%--                                            <span class="flow">--%>
<%--                                                <i class="layui-icon layui-icon-rate"--%>
<%--                                                   onload="getGoodCollectedStatus(this,${good.id})" data-opt="add"--%>
<%--                                                   onclick="changeGoodCollectedStatus(this,${good.id},event)">404</i>--%>
<%--                                            </span>--%>
<%--                                        </div>--%>
<%--                                    </div>--%>
<%--                                </a>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                    </c:forEach>--%>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="subscribe"></div>
<script>
    layui.use(['flow','laytpl'], function () {
        var flow = layui.flow;
        var laytpl = layui.laytpl;
        //手动加载
        flow.load({
            elem: '#lazyLoadContainer' //流加载容器
            // ,scrollElem: '#LAY_demo2' //滚动条所在元素，一般不用填，此处只是演示需要。
            , isAuto: false
            , isLazyimg: true
            , done: function (page, next) { //加载下一页
                var lis = [];
                var limit=4;
                layui.$.ajax({
                    url: 'goodList?type=0&page=' + page + '&limit='+limit,
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
<div>
    <%@include file="footer.jsp" %>
</div>
</body>
</html>
