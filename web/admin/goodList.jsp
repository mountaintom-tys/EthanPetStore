<%@ page import="com.petstore.util.Constants" %><%--
  Created by IntelliJ IDEA.
  User: ethant
  Date: 01/13/20
  Time: 17:11
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
    <div><a class="layui-btn layui-btn-lg" href="goodAdd">添加商品</a></div>
    <br>
    <div class="layui-tab">
        <ul class="layui-tab-title">
            <c:forEach var="type" items="${typeList}">
                <li onclick="displayByType(${type.id})">${type.name}</li>
            </c:forEach>
        </ul>
    </div>
    <table id="demo" lay-filter="goodsList"></table>
    <link href="../resources/css/myCss.css" rel="stylesheet" type="text/css">
    <script>
        var type=0;
        function displayByType(typeId){
            type=typeId;
            layui.table.reload('demo', {
                url: '../admin/goodList?type='+type
            });
        }
        layui.use(['table','element'], function () {
            var table = layui.table,$=layui.$,element = layui.element;
            //第一个实例
            table.render({
                elem: '#demo'
                , height: 600
                , url: '../admin/goodList?type='+type //数据接口
                , page: true //开启分页
                , cols: [[ //表头
                    {field: 'id', title: 'ID', width: 100, sort: true}
                    , {field: 'cover', title: '图片', width: 230,templet:'<div><img src="../{{d.cover}}"></div>'}
                    , {field: 'name', title: '名称', width: 150}
                    , {field: 'intro', title: '介绍', width: 250}
                    , {field: 'price', title: '价格', width: 100, sort: true}
                    , {field: 'stock', title: '库存', width: 100, sort: true}
                    , {field: 'type', title: '类目', width: 120, sort: true, templet:function (d) {return d.type.name;}}
                    , {title: '操作', width: 250, align: 'center', toolbar: '#barDemo'}
                ]]
                ,size: 'lg'
            });
        });
    </script>
    <script type="text/html" id="barDemo">
        <a class="layui-btn" href="goodEdit?id={{d.id}}">编辑</a>
        <a class="layui-btn layui-btn-danger" onclick="javascript:if (confirm('确定删除这只可爱的{{d.name}}吗？')) { return true;}else{return false;};" href="goodDelete?id={{d.id}}&cover={{d.cover}}">删除</a>
    </script>
</div>
</BODY>
</html>
