<%--
  Created by IntelliJ IDEA.
  User: MountainTom-ASUS
  Date: 2020/4/4
  Time: 22:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@include file="header.jsp" %>
<script>
    layui.use('jquery',function () {
        var $=layui.jquery;
        $.ajax({
            url:"ajax",
            type:"get",
            async:true,
            success:function (result) {
                alert(result+"haha")
                console.log(result)
            }
        });
        alert("测试异步同步")
    })


    // var httprquest=new XMLHttpRequest();
    // var url="ajax";
    // httprequest.open('get',url,false)
    // httprequest.send(null)
    // if (httprequest.status==200){
    //     alert(httprequest.responseText)
    // }
</script>
</body>
</html>
