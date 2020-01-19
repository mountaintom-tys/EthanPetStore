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
    <title><%=Constants.WEB_TITLE_NAME%>
    </title>

    <script>
        var typeListDb;
        //检查类目名是符合要求
        function checkTypeName(typeName) {
            if (typeName.value == "") {
                layer.msg("类目名不能为空！")
                return false;
            } else {
                for (var i = 0; i < typeListDb.data.length; i++) {
                    if (typeListDb.data[i].name == typeName.value) {
                        layer.msg("类目名已存在！")
                        return false;
                    }
                }
                return true;
            }
        }
        //新增类目
        function typeAdd(obj) {
            var typeName = document.getElementById("typeName");
            if(checkTypeName(typeName)){
                obj.href = "typeAdd?name=" + typeName.value;
            }
        }
    </script>
</head>
<BODY>
<%@include file="header.jsp" %>


<div style="padding-left: 100px">
    <br>
    <div>
        <div class="layui-input-inline">
            <input type="text" id="typeName" required lay-verify="required" placeholder="请输入名称"
                   autocomplete="off"
                   class="layui-input">
        </div>
        <a class="layui-btn layui-btn-lg" onclick="typeAdd(this)" href="javascript:;">添加类目</a></div>
    <br>
    <table id="demo" lay-filter="typeList"></table>
    <link href="../resources/css/myCss.css" rel="stylesheet" type="text/css">
    <script>
        layui.use(['table', 'layer'], function () {
            var table = layui.table,$ = layui.$;
            //第一个实例
            table.render({
                elem: '#demo'
                , height: 600
                , url: '../admin/typeList?type=1' //数据接口
                , page:true
                , cols: [[ //表头
                    {field: 'id', title: 'ID', width: 300, sort: true}
                    , {field: 'name', title: '名称', width: 400}
                    , {fixed: 'right', title: '操作', width: 400, align: 'center', toolbar: '#barDemo'}
                ]]
                , done: function (res) {
                    typeListDb = res;
                }
                , even: true //开启隔行背景
                , size: 'lg' //小尺寸的表格
            });
        });
        function typeEdit(obj,id){
            layer.open({
                title: '修改类目',
                type: 1,
                area: ['600px', '200px'],
                shadeClose: true, //点击遮罩关闭
                content: layui.$("#typeEditForm")[0].innerHTML,
                btn: ['提交修改', '取消'],
                btnAlign: 'c',
                yes: function (index, layero) {
                    //按钮【按钮一】的回调
                    var newTypeName= layero.find("#newTypeName")[0];
                    if(checkTypeName(newTypeName)){
                        obj.href="typeEdit?id="+id+"&name="+newTypeName.value;
                        obj.click();
                        layer.close(index);
                    }
                }
            });
        }
    </script>
    <script type="text/html" id="barDemo">
        <a class="layui-btn layui-btn-sm" onclick="typeEdit(this,'{{d.id}}')" href="javascript:;">编辑</a>
        <a class="layui-btn layui-btn-danger layui-btn-sm"
           onclick="javascript:if (confirm('确定删除{{d.name}}类目吗？')) { return true;}else{return false;};"
           href="typeDelete?id={{d.id}}">删除</a>
    </script>

</div>
</BODY>
<form id="typeEditForm" class="layui-form" style="display:none;">
    <br><br>
    <div class="layui-form-item">
        <label class="layui-form-label">类目名称</label>
        <div class="layui-input-block" style="width: 70%">
            <input type="text" id="newTypeName" required lay-verify="required"
                   placeholder="请输入新类目名称"
                   autocomplete="off"
                   class="layui-input">
        </div>
    </div>
</form>
</html>
