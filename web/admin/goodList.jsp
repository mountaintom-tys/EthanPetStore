<%--
  Created by IntelliJ IDEA.
  User: ethant
  Date: 01/13/20
  Time: 17:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<BODY>
<%@include file="header.jsp" %>


<div style="padding-left: 20px">
    <br>
    <div><a href="goodAdd.jsp">添加商品</a></div>
    <br>
    <table id="demo" lay-filter="test"></table>
    <script>
        layui.use('table', function () {
            var table = layui.table;

            //第一个实例
            table.render({
                elem: '#demo'
                , height: 600
                , url: '../admin/goodList?type=1' //数据接口
                , page: true //开启分页
                , cols: [[ //表头
                    {field: 'id', title: 'ID', width: 120, sort: true, fixed: 'left'}
                    , {field: 'cover', title: '图片', width: 200}
                    , {field: 'name', title: '名称', width: 120}
                    , {field: 'intro', title: '介绍', width: 200}
                    , {field: 'price', title: '价格', width: 120, sort: true}
                    , {field: 'type', title: '类目', width: 120, sort: true}
                    , {field: 'score', title: '评分', width: 120, sort: true}
                    , {fixed: 'right', title: '操作', width: 200, align: 'center', toolbar: '#barDemo'}
                ]]
            });

        });
    </script>
    <script type="text/html" id="barDemo">
        <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
    </script>
</div>
</BODY>
</html>
