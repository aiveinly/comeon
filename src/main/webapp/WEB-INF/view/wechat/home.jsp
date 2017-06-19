<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>首页</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <script src="/static/script/require.js"></script>
    <script src="/static/lib/jquery-2.1.4.min.js"></script>
    <script src="/static/lib/KinSlideshow1.2.1.js"></script>
    <script src="/static/script/login&register.js"></script>
    <script src="/static/script/search&upload.js"></script>
    <script src="/static/script/home.js"></script>
    <script src="/static/script/mouse-wheel.js"></script>
    <script src="/static/script/modernizr.js" type="text/javascript"></script>
    <script src='/static/script/stopExecutionOnTimeout.js?t=1'></script>
    <link rel="stylesheet" type="text/css" href="/static/lib/ionic.min.css">
    <link rel="stylesheet" type="text/css" href="/static/css/home.css" />
    <link rel="stylesheet" type="text/css" href="/static/css/default.css">
    <link rel="stylesheet" type="text/css" href="/static/css/styles.css">
</head>

<body>
<div class="bar bar-header">
    <p class="title-left"><b>坤玛询价系统</b></p>
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
<<<<<<< HEAD
                <input id="file" class="file" type="file">
=======

                <%--<input class="file" type="file">--%>
                <%--<input class="upload-btn" id="upload-btn" type="button" value="上传">--%>


>>>>>>> aa1f70c3a0fe66118faa5b8d4644d4ca264d6c0c
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
        <div class="p1 light">Copyright @ 2009 上海坤玛机电有限公司 All Rights Reserved          沪ICP备08111853号</div>
        <div class="p2 light">地址：上海市闸北区灵石路大徐家阁199号   电话：021-51082175</div>
    </footer>
    <script src="/static/script/upload.js"></script>
</body>
</html>