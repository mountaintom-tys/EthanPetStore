<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="com.petstore.util.Constants" %><%--
  Created by IntelliJ IDEA.
  User: MountainTom-ASUS
  Date: 2020/4/25
  Time: 16:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><%=Constants.FRONT_WEB_TITLE_NAME%>
    </title>
    <link rel="stylesheet" type="text/css" href="../../static/css/main.css">
    <%
        response.setHeader("Cache-Control","no-store");
        response.setDateHeader("Expires", 0);
        response.setHeader("Pragma","no-cache");
    %>
</head>
<body>
<%@include file="header.jsp" %>

<div class="content content-nav-base shopcart-content">
    <div class="data-cont-wrap w1200">
        <div class="crumb">
            <br>
            <br>
            <span>></span>
            <a href="javascript:;">我的购物车</a>
        </div>
    </div>
    <div class="banner-bg w1200">
        <h3>萌宠购翻天</h3>
        <p>全场包邮&nbsp;品质保证</p>
    </div>
    <div class="cart w1200">
        <form action="settlement" method="post" onsubmit="return checkFormData(this)">
            <div class="cart-table-th">
                <div class="th th-chk">
                    <div class="select-all">
                        <div class="cart-checkbox">
                            <input class="check-all check" id="allCheckked" type="checkbox" value="true">
                        </div>
                        <label>&nbsp;&nbsp;全选</label>
                    </div>
                </div>
                <div class="th th-item">
                    <div class="th-inner">
                        商品
                    </div>
                </div>
                <div class="th th-price">
                    <div class="th-inner">
                        单价
                    </div>
                </div>
                <div class="th th-amount">
                    <div class="th-inner">
                        数量
                    </div>
                </div>
                <div class="th th-sum">
                    <div class="th-inner">
                        小计
                    </div>
                </div>
                <div class="th th-op">
                    <div class="th-inner">
                        操作
                    </div>
                </div>
            </div>
            <div class="OrderList">
                <div class="order-content" id="list-cont">
                    <c:forEach var="cart" items="${cartList}">
                        <ul class="item-content layui-clear">
                            <li class="th th-chk">
                                <div class="select-all">
                                    <div class="cart-checkbox">
                                        <input class="CheckBoxShop check" type="checkbox" num="all"
                                               name="goodIdList"
                                               value="${cart.goodId}">
                                    </div>
                                </div>
                            </li>
                            <li class="th th-item">
                                <div class="item-cont">
                                    <a href="javascript:;"><img src="../../${cart.good.cover}"
                                                                title="${cart.good.type.name}"></a>
                                    <div class="text">
                                        <div class="title">${cart.good.name}</div>
                                    </div>
                                </div>
                            </li>
                            <li class="th th-price">
                                <span class="th-su">${cart.good.price}</span>
                            </li>
                            <li class="th th-amount">
                                <div class="box-btn layui-clear">
                                    <div class="less layui-btn">-</div>
                                    <input class="Quantity-input" onchange="changeCartAmount(${cart.goodId},this.value)"
                                           value="${cart.amount}" disabled="disabled">
                                    <div class="add layui-btn">+</div>
                                </div>
                            </li>
                            <li class="th th-sum">
                                <span class="sum">${cart.good.price*cart.amount}</span>
                            </li>
                            <li class="th th-op">
                                <span class="dele-btn" data-goodId="${cart.goodId}">删除</span>
                            </li>
                        </ul>
                    </c:forEach>
                </div>
            </div>


            <!-- 模版导入数据 -->
            <!-- <script type="text/html" id="demo">
              {{# layui.each(d.infoList,function(index,item){}}
                <ul class="item-content layui-clear">
                  <li class="th th-chk">
                    <div class="select-all">
                      <div class="cart-checkbox">
                        <input class="CheckBoxShop check" id="" type="checkbox" num="all" name="select-all" value="true">
                      </div>
                    </div>
                  </li>
                  <li class="th th-item">
                    <div class="item-cont">
                      <a href="javascript:;"><img src="../res/static/img/paging_img1.jpg" alt=""></a>
                      <div class="text">
                        <div class="title">宝宝T恤棉质小衫</div>
                        <p><span>粉色</span>  <span>130</span>cm</p>
                      </div>
                    </div>
                  </li>
                  <li class="th th-price">
                    <span class="th-su">189.00</span>
                  </li>
                  <li class="th th-amount">
                    <div class="box-btn layui-clear">
                      <div class="less layui-btn">-</div>
                      <input class="Quantity-input" type="" name="" value="1" disabled="disabled">
                      <div class="add layui-btn">+</div>
                    </div>
                  </li>
                  <li class="th th-sum">
                    <span class="sum">189.00</span>
                  </li>
                  <li class="th th-op">
                    <span class="dele-btn">删除</span>
                  </li>
                </ul>
              {{# });}}
            </script> -->


            <div class="FloatBarHolder layui-clear">
                <div class="th th-chk">
                    <div class="select-all">
                        <div class="cart-checkbox">
                            <input class="check-all check" id="" name="select-all" type="checkbox" value="true">
                        </div>
                        <label>&nbsp;&nbsp;已选<span class="Selected-pieces">0</span>件</label>
                    </div>
                </div>
                <div class="th batch-deletion">
                    <span class="batch-dele-btn">批量删除</span>
                </div>
                <div class="th Settlement">
                    <button type="submit" class="layui-btn">结算</button>
                </div>
                <div class="th total">
                    <p>应付：<span class="pieces-total">0</span></p>
                </div>
            </div>
        </form>
    </div>
</div>


<script type="text/javascript">
    layui.config({
        base: '../../resources/static/js/util/' //你存放新模块的目录，注意，不是layui的模块目录
    }).use(['mm', 'jquery', 'element', 'car'], function () {
        var mm = layui.mm, $ = layui.$, element = layui.element, car = layui.car;

        // 模版导入数据
        // var html = demo.innerHTML,
        // listCont = document.getElementById('list-cont');
        // mm.request({
        //   url: '../json/shopcart.json',
        //   success : function(res){
        //     listCont.innerHTML = mm.renderHtml(html,res)
        //     element.render();
        //     car.init()
        //   },
        //   error: function(res){
        //     console.log(res);
        //   }
        // })
        //
        car.init()

    });

    function changeCartAmount(goodId, amount) {
        layui.$.ajax({
            url: 'changeCartAmount?ajax=true&goodId=' + goodId + '&amount=' + amount,
            type: 'get',
            async: true,
            success: function (result) {
                var rs = result.split("~");
                var code = rs[0];
                if (code < 0) {
                    if (code == -1) {//用户未登录
                        layui.layer.msg(rs[1], {icon: 7})
                    } else if (code == -2) {//异常
                        layui.layer.msg(rs[1], {icon: 2})
                    }
                }
            }
        })
    }

    function checkFormData(form){
        var size=layui.$(form).find(".CheckBoxShop:checked").size();
        if(size<=0){
            layui.layer.msg("未选择任何商品！",{offset:'30%',icon:5});
            return false;
        }
        return true;
    }
</script>
</body>
</html>
