// 解析有关用户信息表单功能返回的result，如注册、登录、忘记密码、重置个人信息等
// 响应代码规则：
// 0~成功   -1~验证码错误   -2~手机号错误  -3~密码错误   -4~密保错误   5~系统错误
function userModuleFeedbackParse(r,form) {
    var rs = r.split ("~");
    var code=rs[0];
    if(code>=0){//成功
        layer.msg(rs[1],{icon: 6});
        return true;
    }else{//失败
        layer.msg(rs[1],{icon: 5,anim: 6});
        var e;
        if(code==-1){
            e=layui.$(form).find("[name='verifyCode']")[0];
        }else if(code==-2){
            e=layui.$(form).find("[name='phone']")[0];
        }else if(code==-3){
            e=layui.$(form).find("[name='password']")[0];
        }else if(code==-4){
            e=layui.$(form).find("[name='securityAnswer']")[0];
        }else if(code==-5){
            form.reset();
        }else{
            alert("未知错误！")
        }
        e.value="";
        e.focus();
        return false;
    }
}

//获取当前商品被当前用户收藏的状态、获取当前商品被收藏的次数
// 响应代码规则：
// code>=0: code~collectedStatus~collectedCount  用户未登录情况下status默认为false
function getGoodCollectedStatus(element,goodId) {
    layui.$.ajax({
        url: 'getGoodCollectedStatus?goodId='+goodId,
        type: 'get',
        async: true,
        success: function (result) {
            var rs = result.split ("~");
            var code=rs[0];
            if(code<0){//系统异常，获取收藏状态失败
                Console.error("系统异常，不能正常获取收藏状态和收藏数量！");
            }
            //无论获取是否存在异常，都要将后台返回的值进行设值
            if(rs[1]=="true"){//已收藏
                layui.$(element).removeClass("layui-icon-rate");
                layui.$(element).addClass("layui-icon-rate-solid");
                layui.$(element).attr("data-opt",'del');
            }else{
                layui.$(element).removeClass("layui-icon-rate-solid");
                layui.$(element).addClass("layui-icon-rate");
                layui.$(element).attr("data-opt",'add');
            }
            layui.$(element).text(rs[2]);
        }
    })
}

//加入收藏或取消收藏
function changeGoodCollectedStatus(element,goodId,e) {
    var opt=layui.$(element).attr("data-opt");
    layui.$.ajax({
        url: 'logged/changeGoodCollectedStatus?ajax=true&opt='+opt+'&goodId='+goodId,
        type: 'get',
        async: true,
        success: function (result) {
            var rs = result.split ("~");
            var code=rs[0];
            if(code>=0){//加入收藏或取消收藏成功
                layui.layer.msg(rs[1],{icon: 1})
            }else{
                if(code==-1){//用户未登录
                    layui.layer.msg(rs[1],{icon: 7})
                }else if(code==-2){//系统出现异常
                    layui.layer.msg(rs[1],{icon: 2})
                }
            }
            getGoodCollectedStatus(element,goodId);
        }
    })
    // e.stopPropagation();
    e.preventDefault();
}

//加入购物车
function addToCart(goodId,amount) {
    layui.$.ajax({
        url: 'logged/addToCart?ajax=true&goodId='+goodId+'&amount='+amount,
        type: 'get',
        async: true,
        success: function (result) {
            var rs = result.split ("~");
            var code=rs[0];
            if(code>=0){//加入购物车成功
                alert(rs[1]);
                // layui.layer.msg(rs[1],{icon: 7})
            }else{
                if(code==-1){//用户未登录
                    layui.layer.msg(rs[1],{icon: 7})
                }else if(code==-2){//异常
                    layui.layer.msg(rs[1],{icon: 2})
                }
            }
        }
    })
}