<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>首页</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <script src="/static/lib/jquery-2.1.4.min.js" type="text/javascript"></script>
    <script src="/static/lib/KinSlideshow1.2.1.js" type="text/javascript"></script>
    <script src="/static/script/home.js" type="text/javascript"></script>
    <script src="/static/script/mouse-wheel.js" type="text/javascript"></script>
    <script src="/static/script/modernizr.js" type="text/javascript"></script>
    <script src='/static/script/stopExecutionOnTimeout.js?t=1' type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="/static/lib/ionic.min.css">
    <link rel="stylesheet" type="text/css" href="/static/css/home.css"/>
    <link rel="stylesheet" type="text/css" href="/static/css/default.css">
    <link rel="stylesheet" type="text/css" href="/static/css/styles.css">
    <link rel="stylesheet" type="text/css" href="/static/jquery/themes/icon.css"/>
    <link rel="stylesheet" type="text/css" href="/static/jquery/themes/color.css"/>
    <link rel="stylesheet" type="text/css" href="/static/jquery/themes/default/easyui.css"/>
    <script src="/static/jquery/jquery.easyui.min.js" type="text/javascript"></script>
    <script src="/static/jquery/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
</head>
<script type="text/javascript">
    $(function () {
        //首页屏幕滚动效果
        $(".slide").KinSlideshow({
            moveStyle: "left",
            intervalTime: 3,
            mouseEvent: "mouseover"
        });


        //判断登录状态
        var storage = window.localStorage;
        if (!storage.getItem("login_username")) {
            $(".islogin").hide();
            $(".unlogin").show();
            $(".outlogin").hide();
        } else {
            var $img = $('<img src="/static/img/islogin.png">');
            var $p1 = $('<p class="p1">' + storage.getItem("login_username") + '</p>');
            var $p2 = $('<p class="p2">已登录</p>');
            $(".bar-header .islogin").append($img).append($p1).append($p2);
            $(".unlogin").hide();
            $(".islogin").show();
            $(".outlogin").show();
        }

        //退出登录
        $(".outlogin").click(function () {
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
            var reg = /^1[34578]\d{9}$/;
            if (login_username == "") {
                alert("用户名不能为空!");
                return;
            }
            if (!reg.test(login_username)) {
                alert("用户名格式不正确!");
                return;
            }
            if (login_password == "") {
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
                    localStorage.setItem("login_username", login_username);
                    localStorage.setItem("uid", data.result.uid);
                    $(".login-window").hide();
                    var $img = $('<img src="/static/img/islogin.png">');
                    var $p1 = $('<p class="p1">' + storage.getItem("login_username") + '</p>');
                    var $p2 = $('<p class="p2">已登录</p>');
                    $(".bar-header .islogin").append($img).append($p1).append($p2);
                    $(".unlogin").hide();
                    $(".islogin").show();
                    $(".outlogin").show();
                } else {
                    alert("我去,登录失败了");
                    return;
                }
            })
        })

        //发送验证码短信
        var InterValObj; //timer变量，控制时间
        var count = 60; //间隔函数，1秒执行
        var curCount;//当前剩余秒数
        var code = ""; //验证码
        var codeLength = 4;//验证码长度
        $("#btnSendCode").click(function () {
            curCount = count;
            var phone = $("#register-username").val();//手机号码
            if (phone != "") {
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
                    method: "post", //用get方式传输
                    url: '/personal/identifycode?mobile=' + phone, //目标地址
                    success: function (data) {
                        console.log(data);
                        if (data.code == "200") {
                            alert("验证码已发至手机,请查收!")
                        } else if ($("#checkCode").val() != code) {
                            return alert("验证码填写错误!");
                        } else {
                            return alert("发送失败,请重新发送!");
                        }
                    }
                });
            } else {
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

        //注册
        $(".register-window .register").click(function () {
            var register_username = $("#register-username").val();
            var register_password = $("#register-password").val();
            var confirm_password = $("#confirm-password").val();
            var name = $("#name").val();
            var reg = /^1[34578]\d{9}$/;
            var checkCode = $("#checkCode").val();
            if (!reg.test(register_username)) {
                alert("用户名格式不正确!");
                $("#register-username").val("");
                return;
            } else if (register_username == "") {
                alert("用户名不为空!");
                $("#register-username").val("");
                return;
            }
            if (register_password != confirm_password) {
                alert("两次密码输入不一致,请重新输入!")
                $("#register-password").val("");
                $("#confirm-password").val("");
                return;
            } else if (checkCode == "") {
                alert("验证码不为空!")
                return;
            } else if (register_password.length < 8 || confirm_password.length < 8) {
                alert("密码长度不低于8位!");
                return;
            }
            $.ajax({
                method: "get",
                url: "/personal/register",
                data: {"uname": register_username, "name": name, "pwd": register_password, "referralcode": 1111},
                success: function (data) {
                    console.log(data);
                    if (data.code != "200") {
                        alert("我去,注册失败了!!");
                        return;
                    } else {
                        alert("注册成功了!");
                        $(".register-window").hide();
                        $(".login-window").show();
                    }
                }
            })
        })


        $("#upload-btn").click(function () {

            var formData = new FormData();
            formData.append('file', $('#file')[0].files[0]);
            $.ajax({
                url: '/req/req-file-upload',
                type: 'POST',
                cache: false,
                data: formData,
                processData: false,
                contentType: false
            }).done(function (res) {
                if (res.code == 200) {
                    var productname = $(".content-1 .input-0").val();
                    var brandname = $(".content-1 .input-1").val();
                    var qty = $(".content-1 .input-2").val();
                    var modelname = $(".content-1 .input-3").val();
                    $.ajax({
                        method: "post",
                        url: "/req/product-req",
                        contentType: "application/json", //必须有
                        dataType: "json", //表示返回值类型，不必须
                        data: JSON.stringify({
                            "uid": localStorage.getItem("uid"),
                            "productname": productname,
                            "brandname": brandname,
                            "qty": qty,
                            "modelname": modelname,
                            "fileid": res.result
                        }),
                        success: function (jsonResult) {
                            console.log(jsonResult);
                        }
                    })

                }


            }).fail(function (res) {
            });

        })


        //搜索功能
        $("#search").click(function () {
            var search = $(".search").val();
            if (search == "") {
                $("#search").attr("disabled", "disabled");
            } else {
                $(".goods-list").empty();
                console.log(search);
                $.ajax({
                    method: "post",
                    url: "/product/list?type=1&rows=20&page=1&keywords=" + search,
                    success: function (data) {
                        console.log(data);
                        var $frame = $(document.createDocumentFragment());
                        for (var i = 0; i < data.result.productList.length; i++) {
                            if (data.result.productList[i].productname.indexOf(search) == -1) {
                                alert("搜索内容不存在!");
                            } else {
                                var $divGoodsList1 = $('<div class="goods-list-1" data-psid="' + data.result.productList[i].psid + '" onclick="goGoodsDetail(this);"></div> ');
                                var $img = $('<img src="http://image.comeon365.com/' + data.result.productList[i].imgurl + '"/>');
                                var $pP1 = $('<p class="p1">' + data.result.productList[i].productname + '</p>');
                                var $pP2 = $('<p class="p2">库存:&nbsp;&nbsp;' + data.result.productList[i].stock + '</p>');
                                var $pP3 = $('<p class="p3">¥' + data.result.productList[i].price + '</p>');
                                $divGoodsList1.append($img).append($pP1).append($pP2).append($pP3);
                                $frame.append($divGoodsList1);
                            }
                        }
                        $(".goods-list").append($frame.find(".goods-list-1"));
                    }
                });
            }
        })

        //上传文件
        var file = $(".file").val();
        $.ajax({
            method: "post",
            url: "/req/req-file-upload",
            contentType: "application/json",
            success: function (data) {
                console.log(data);
                $("#commit").click(function () {
                    var productname = $(".content-1 .input-0").val();
                    var brandname = $(".content-1 .input-1").val();
                    var qty = $(".content-1 .input-2").val();
                    var modelname = $(".content-1 .input-3").val();
//                        $.ajax({
//                            method:"post",
//                            url:"/req/product-req",
//                            data:{"productname":productname,"brandname":brandname,"qty":qty,"modelname":modelname,"fileid":data.result},
//                            success:function(jsonResult){
//                                console.log(jsonResult);
//                            }
//                        })
                })

            }
        })
    })

</script>
<body>
<div class="bar bar-header">
    <p class="title-left"><b>坤玛机电</b></p>
    <img class="logo" src="/static/img/logo.png">
    <div class="islogin"></div>
    <div class="unlogin">
        <img src="/static/img/unlogin.png">
        <p class="name">登录</p>
    </div>
    <input class="outlogin" value="退出">
</div>
<div class="login-window light-bg">
    <input id="login-username" class="btn-1" type="text" maxlength="11" placeholder="请输入手机号">
    <input id="login-password" class="btn-2" type="password" maxlength="16" placeholder="请输入密码">
    <a id="goLogin">还没有账号？点击注册！</a>
    <input class="cancel light-bg" type="button" value="取消">
    <input class="login light-bg" type="button" value="登录">
</div>
<div class="register-window light-bg">
    <input class="btn-1" id="register-username" type="text" maxlength="11" placeholder="请输入手机号">
    <input class="btn-3" id="checkCode" type="text" placeholder="请输入验证码">
    <input class="btnSendCode" id="btnSendCode" type="button" value="发送验证码"/>
    <input class="btn-2" id="name" type="text" maxlength="10"
           placeholder="请输入您的名字" required>
    <input class="btn-2" id="register-password" type="password" maxlength="12"
           placeholder="请设置密码" required>
    <input class="btn-2" id="confirm-password" type="password" maxlength="12"
           placeholder="请确认密码" required>
    <input class="cancel light-bg" type="button" value="取消">
    <input class="register light-bg" type="button" value="注册">
</div>
<div class="slide" style="visibility:hidden;">
    <a href="#" target="_blank"><img src="/static/img/picture1.png"></a>
    <a href="#" target="_blank"><img src="/static/img/picture1.png"></a>
    <a href="#" target="_blank"><img src="/static/img/picture1.png"></a>
</div>
<div id="sec-2" class="sec-2 light-bg">
    <input id="bargain-price" class="btn" type="button" value="特价产品">
    <input id="recommend" class="btn-group" type="button" value="推荐产品">
    <input id="goods-center" class="btn-group" type="button" value="产品中心">
    <input id="about-us" class="btn-group" type="button" value="关于坤玛">
    <img src="/static/img/search.png">
    <div>
        <input class="search" type="search" placeholder="请输入搜索内容">
        <button id="search">开始搜索</button>
    </div>

</div>
<div class="has-header">
    <%--<div class="contract">--%>
    <%--<p class="p1">微信平台登录</p>--%>
    <%--<img class="qcode" src="/static/img/Qcode.png">--%>
    <%--<div class="dashed"></div>--%>
    <%--<a href="tencent://message/?uin=237506756&Site=&Menu=yes">--%>
    <%--<img class="qq" src="/static/img/qq.png">--%>
    <%--</a>--%>
    <%--<p class="service">客服一</p>--%>
    <%--<a href="tencent://message/?uin=710602072&Site=&Menu=yes">--%>
    <%--<img class="qq" src="/static/img/qq.png">--%>
    <%--</a>--%>
    <%--<p class="service">客服二</p>--%>
    <%--</div>--%>
    <div class="title-1">我要询价</div>
    <div class="sec-1">
        <div class="content-1">
            <input class="input-0" type="text" maxlength="30" placeholder="请输入产品名称">
            <input class="input-1" type="text" maxlength="30" placeholder="请输入产品品牌">
            <input class="input-2" type="text" maxlength="10" placeholder="请输入数量">
            <input class="input-3" type="text" maxlength="30" placeholder="请输入产品型号">
        </div>
        <div class="content-2">
            <div class="htmleaf-content">
                <div class="progress">
                    <b class="progress__bar">
                        <span class="progress__text">
                          <em>0%</em>
                        </span>
                    </b>
                </div>
            </div>

            <div id="uploadForm">
                <input id="file" type="file"/>
                <button id="upload-btn" type="button">upload</button>
            </div>

            <div class="upload">

                <%--<input class="file" type="file">--%>
                <%--<input class="upload-btn" id="upload-btn" type="button" value="上传">--%>


            </div>

            <input class="btn-1" type="button" value="清空输入">
            <input id="commit" class="btn-2" type="button" value="提交">
        </div>
    </div>
    <div class="title-1">每日特价</div>
    <div class="goods-list"></div>
</div>
<footer>
    <div class="Qcode"></div>
    <div class="p1 light">Copyright @ 2009 上海坤玛机电有限公司 All Rights Reserved 沪ICP备08111853号</div>
    <div class="p2 light">地址：上海市闸北区灵石路大徐家阁199号 电话：021-51082175</div>
</footer>
<script src="/static/script/upload.js"></script>
</body>
</html>