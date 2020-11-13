<%@ page import="com.petstore.util.Constants" %><%--
  Created by IntelliJ IDEA.
  User: ethant
  Date: 01/13/20
  Time: 17:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title><%=Constants.WEB_TITLE_NAME%></title>
    <style>
        .site-block .layui-form {
            margin-right: 200px;
        }

        .site-block {
            padding: 70px;
            border-bottom: 1px solid #eee;
        }

        .site-text {
            position: relative;
        }
    </style>
</head>
<BODY>
<%@include file="header.jsp" %>
<div class="layui-main site-inline">
    <div class="site-content">
        <div class="site-text site-block">
            <fieldset class="layui-elem-field layui-field-title site-title">
                <legend><a name="bgcolor">新增宠物</a></legend>
            </fieldset>
            <form class="layui-form" action="goodSave">
                <div class="layui-form-item">
                    <label class="layui-form-label">名称</label>
                    <div class="layui-input-block">
                        <input type="text" name="name" required lay-verify="required" placeholder="请输入名称"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">价格</label>
                    <div class="layui-input-inline">
                        <input type="text" name="price" required lay-verify="required|number" placeholder="请输入价格"
                               autocomplete="off" class="layui-input">
                    </div>
                    <div class="layui-form-mid layui-word-aux">单位：元</div>
                </div>
                <div class="layui-form-item layui-form-text">
                    <label class="layui-form-label">介绍</label>
                    <div class="layui-input-block">
                        <textarea name="intro" placeholder="请输入介绍" class="layui-textarea"></textarea>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">库存</label>
                    <div class="layui-input-inline">
                        <input type="text" name="stock" required lay-verify="required|number" placeholder="请输入库存"
                               autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">封面图片</label>
                    <div class="layui-input-inline">
                        <button type="button" class="layui-btn" id="img">
                            <i class="layui-icon">&#xe67c;</i>上传图片
                        </button>
                        <input type="hidden" id="img_url" name="cover" value=""/>
                        <div class="layui-upload-list">
                            <img class="layui-upload-img" width="100px" height="80px" id="demo1"/>
                            <p id="demoText"></p>
                        </div>
                        <div class="layui-form-mid layui-word-aux">推荐尺寸：500 * 500</div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">类目</label>
                    <div class="layui-input-block">
                        <select name="typeId" lay-verify="required">
                            <option value=""></option>
                            <c:forEach var="type" items="${typeList}">
                                <option value="${type.id}">${type.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
                        <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script>
    //Demo
    layui.use(['form','upload'], function () {
        var form = layui.form,upload=layui.upload,$=layui.$;

        var uploadInst = upload.render({
            elem: '#img' //绑定元素
            ,url: '../admin/uploadFile' //上传接口
            ,data: {oldImgUrl:$("#img_url")[0].value}
            ,before:function (obj) {
                this.data.oldImgUrl=$("#img_url")[0].value;
                //预读本地文件实例，不支持ie8
                obj.preview(function (index,file,result) {
                    $("#demo1").attr("src",result);//图片链接
                })
            }
            ,done: function(res){
                //上传完毕回调
                //如果上传失败
                if(res.code>0){
                    return layer.msg("上传失败");
                }
                alert("上传成功！");
                $("#img_url")[0].value=res.url;
            }
            ,error: function(){
                //请求异常回调
                var demoText=$("#demoText");
                demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
                demoText.find('.demo-reload').on('click', function(){
                    uploadInst.upload();
                });
            }
        });
    });
</script>
</BODY>
</html>
