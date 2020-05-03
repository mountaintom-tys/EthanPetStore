<%@ page import="com.petstore.util.Constants" %>
<%@ page import="com.petstore.entity.Orders" %><%--
  Created by IntelliJ IDEA.
  User: ethant
  Date: 01/13/20
  Time: 16:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><%=Constants.WEB_TITLE_NAME%></title>
</head>
<BODY>
<%@include file="header.jsp" %>


<div style="padding-left: 20px">
    <br>
    <br>
    <div class="layui-tab">
        <ul class="layui-tab-title">
            <li onclick="displayByStatus(<%=Orders.STATUS_UNPAY%>)">未付款</li>
            <li onclick="displayByStatus(<%=Orders.STATUS_PAYED%>)">已付款</li>
            <li onclick="displayByStatus(<%=Orders.STATUS_SEND%>)">配送中</li>
            <li onclick="displayByStatus(<%=Orders.STATUS_FINISH%>)">已完成</li>
        </ul>
    </div>
    <table id="demo" lay-filter="goodsList"></table>
    <link href="../../resources/css/admin/style.css" rel="stylesheet" type="text/css">
    <script>
        var status=0;
        function displayByStatus(statusId){
            status=statusId;
            layui.table.reload('demo', {
                url: 'orderList?status='+status
            });
        }
        layui.use('table', function () {
            var table = layui.table,$=layui.$;
            var msg="${msg}";
            if(msg!=null && msg.trim()!="" && msg!=undefined){
                layer.msg(msg);
            }
            //第一个实例
            table.render({
                elem: '#demo'
                , height: 600
                , url: 'orderList?status='+status //数据接口
                , page: true //开启分页
                , cols: [[ //表头
                    {field: 'id', title: 'ID', width: 100, sort: true}
                    , {field: 'total', title: '总价', width: 100,sort:true}
                    , {title: '商品详情', width: 160,templet:'#goodDetail'}
                    , {title: '收货信息', width: 200,templet:'#receivingInformation'}
                    , {field: 'status', title: '订单状态', width: 120, sort: true,templet:'#orderStatus'}
                    , {field: 'paytype', title: '支付方式', width: 120,templet:function (d) { return getPaytype(d); }}
                    , {field: 'userId', title: '下单用户', width: 110,templet:function (d) {return d.user.username;}}
                    , {field: 'systime', title: '下单时间', width: 150, sort: true,templet:'<div>{{layui.util.toDateString(d.systime, \'yyyy年MM月dd日 HH:mm:ss\')}}</div>'}
                    , {title: '操作', width: 250, align: 'center', toolbar: '#barDemo'}
                ]]
                ,size: 'lg'
            });
        });
    </script>
    <script type="text/html" id="goodDetail">
        {{#  layui.each(d.itemsList, function(index, item){ }}
            <p>{{ item.good.name }}({{ item.price }})x{{ item.amount }}</p>
        {{#  }); }}
    </script>
    <script type="text/html" id="receivingInformation">
       <p>{{d.name}}</p>
       <p>{{d.phone}}</p>
       <p>{{d.address}}</p>
    </script>
    <script type="text/html" id="orderStatus">
      {{# switch(d.status){
         case 1:}}
            <p>未付款</p>
        {{# break;
        case 2:}}
            <p>已付款</p>
        {{# break;
        case 3:}}
            <p>配送中</p>
        {{# break;
        case 4:}}
            <p>已完成</p>
      {{# break;
      default:}}
            <p>状态未知</p>
      {{# break;
      } }}
    </script>
    <script>
        function getPaytype(d){
            switch (d.paytype) {
                case 1:return '微信';break;
                case 2:return '支付宝';break;
                case 3:return "货到付款";break;
                default:return '';break;
            }
        }
    </script>
    <script type="text/html" id="barDemo">
        {{# switch(d.status){
                case 1:}}
                <a class="layui-btn" href="orderUpdate?id={{d.id}}&type=2">付款</a>
                <a class="layui-btn layui-btn-danger" onclick="javascript:if (confirm('确定取消{{d.user.username}}的订单吗？')) { return true;}else{return false;};" href="orderDelete?id={{d.id}}">取消</a>
                {{# break;
                default:}}
                <a class="layui-btn layui-btn-danger" onclick="javascript:if (confirm('确定删除{{d.user.username}}的订单吗？')) { return true;}else{return false;};" href="orderDelete?id={{d.id}}">删除</a>
                {{# break;
                }
        }}

    </script>
</div>
</BODY>
</html>
