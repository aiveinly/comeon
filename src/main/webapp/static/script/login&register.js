$(function(){
    //判断登录状态
    var storage = window.localStorage;
    if (!storage.getItem("login_username")) {
        $(".islogin").hide();
        $(".unlogin").show();
        $(".outlogin").hide();
    }else{
        var $img=$('<img src="/static/img/islogin.png">');
        var $p1=$('<p class="p1">'+storage.getItem("login_username")+'</p>');
        var $p2=$('<p class="p2">已登录</p>');
        $(".bar-header .islogin").append($img).append($p1).append($p2);
        $(".unlogin").hide();
        $(".islogin").show();
        $(".outlogin").show();
    }

    //退出登录
    $(".outlogin").click(function(){
        localStorage.removeItem("login_username");
        $(".islogin").hide();
        $(".islogin img").remove();
        $(".islogin .p1").remove();
        $(".islogin .p2").remove();
        $(".unlogin").show();
        $(".outlogin").hide();
    })

    //登录
    $(".login-window .login").click(function () {
        var login_username = $("#login-username").val();
        var login_password = $("#login-password").val();
        var reg=/^1[34578]\d{9}$/;
        if(login_username==""){
            alert("用户名不能为空!");
            return;
        }
        if(!reg.test(login_username)){
            alert("用户名格式不正确!");
            return;
        }
        if(login_password==""){
            alert("密码不能为空!");
            return;
        }
        $.ajax({
            method: "get",
            url: "/personal/login",
            data: {username: login_username, password: login_password}
        }).success(function (data) {
            if (data.code == "200") {
                alert("卧槽,登录成功了");
                localStorage.setItem("login_username",login_username);
                localStorage.setItem("uid",data.result.uid);
                $(".login-window").hide();
                var $img=$('<img src="/static/img/islogin.png">');
                var $p1=$('<p class="p1">'+storage.getItem("login_username")+'</p>');
                var $p2=$('<p class="p2">已登录</p>');
                $(".bar-header .islogin").append($img).append($p1).append($p2);
                $(".unlogin").hide();
                $(".islogin").show();
                $(".outlogin").show();
            }else {
                alert("我去,登录失败了");
                return;
            }
        })
    })

    //注册
    $(".register-window .register").click(function () {
        var register_username = $("#register-username").val();
        var register_password = $("#register-password").val();
        var confirm_password = $("#confirm-password").val();
        var name = $("#name").val();
        var reg=/^1[34578]\d{9}$/;
        var checkCode = $("#checkCode").val();
        if(!reg.test(register_username)){
            alert("用户名格式不正确!");
            $("#register-username").val("");
            return;
        } else if( register_username=="") {
            alert("用户名不为空!");
            $("#register-username").val("");
            return;
        }
        if(register_password != confirm_password) {
            alert("两次密码输入不一致,请重新输入!")
            $("#register-password").val("");
            $("#confirm-password").val("");
            return;
        } else if(checkCode=="") {
            alert("验证码不为空!")
            return;
        } else if(register_password.length<8 || confirm_password.length<8){
            alert("密码长度不低于8位!");
            return;
        }
        $.ajax({
            method: "get",
            url: "/personal/register",
            data: {"uname": register_username,"name":name, "pwd": register_password,"referralcode":1111},
            success: function (data) {
                console.log(data);
                if (data.code != "200") {
                    alert("我去,注册失败了!!");
                    return;
                }else {
                    alert("注册成功了!");
                    $(".register-window").hide();
                    $(".login-window").show();
                }
            }
        })
    })

    //发送验证码短信
    var InterValObj; //timer变量，控制时间
    var count = 60; //间隔函数，1秒执行
    var curCount;//当前剩余秒数
    var code = ""; //验证码
    var codeLength = 4;//验证码长度
    $("#btnSendCode").click(function(){
        curCount = count;
        var phone=$("#register-username").val();//手机号码
        if(phone != ""){
            //产生验证码
            for (var i = 0; i < codeLength; i++) {
                code += parseInt(Math.random() * 9).toString();
            }
            //设置button效果，开始计时
            $("#btnSendCode").attr("disabled", "true");
            $("#btnSendCode").val("再次发送" + curCount + "s");
            InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
            //向后台发送处理数据
            $.ajax({
                method: "post",
                url: '/personal/identifycode?mobile='+phone, //目标地址
                success: function (data){
                    console.log(data);
                    if(data.code=="200"){
                        alert("验证码已发至手机,请查收!")
                    }else if($("#checkCode").val() != code){
                        alert("验证码填写错误!")
                        return;
                    } else{
                        alert("发送失败,请重新发送!");
                        return;
                    }
                }
            });
        }else{
            alert("手机号码不能为空！");
        }
    })
    //timer处理函数
    function SetRemainTime() {
        if (curCount == 0) {
            window.clearInterval(InterValObj);//停止计时器
            $("#btnSendCode").removeAttr("disabled");//启用按钮
            $("#btnSendCode").val("发送验证码");
            code = ""; //清除验证码。如果不清除，过时间后，输入收到的验证码依然有效
        }
        else {
            curCount--;
            $("#btnSendCode").val("再次发送" + curCount + "s");
        }
    }
})