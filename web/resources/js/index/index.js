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