<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2016/2/18
  Time: 14:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>用户登录</title>

    <style>
        .login-div-center {
            position: absolute;
            top: 50%;
            left: 50%;
            margin-top: -100px;
            margin-left: -200px;
            width: 400px;
            height: 200px;
            border: 1px solid silver;
            border-radius: 18px;
            box-shadow: 3px 3px 5px rgba(116, 93, 98, 0.68);
            text-align: center;
            background: -webkit-gradient(linear, 0% 0%, 0% 100%, from(#1d63cb), to(#3789f8));
            background: linear-gradient(to bottom, #1d63cb 0%, #3789f8 100%);
        }

        .login-img-font {
            position: relative;
            top: 10px;
            font-weight: bold;
            font-size: 20px;
            margin-left: 30px;
            color: lavenderblush;
        }

        .login-div-two {
            margin-top: 28px;
        }

        .login-div-three {
            margin-top: 10px;
            text-align: center;
        }

        .login-input {
            border-radius: 6px;
            height: 22px;
            box-shadow: 1px 1px 1px rgba(39, 32, 10, 0.84);
        }

        .login-input-code {
            width: 70px;
            border-radius: 6px;
            height: 22px;
            margin-right: 13px;
            box-shadow: 1px 1px 1px rgba(39, 32, 10, 0.84);
        }

        .login-input-code1 {
            width: 70px;
            height: 22px;
            border: 0;
            font-family: Arial;
            font-style: italic;
            color: #ff97cf;
            padding: 2px 3px;
            letter-spacing: 9px;
            font-weight: bolder;
            background-color: #00A000;
        }

        .login-button {
            width: 100px;
            height: 28px;
            background-color: #ffab29;
            border-color: #ffab29;
            border-radius: 5px;
            color: white;
            font-weight: bold;
            margin-left: 5px;
        }

        @-moz-document url-prefix() {
            .selector {
                position: relative;
                margin-left: 10px;
            }
        }

        @-moz-document url-prefix() {
            .selector-1 {
                position: relative;
                margin-left: 20px;
            }
        }

        @-moz-document url-prefix() {
            .top {
                position: relative;
                top: -0.5em;
            }
        }

        .header {
            position: relative \0;
            margin-left: 5px \0;
        }

        .header1 {
            position: relative \0;
            margin-left: 15px \0;
        }

        .top1 {
            position: relative;
            top: -0.25em;
        }
    </style>

    <script src="/static/jquery/jquery.min.js" type="text/javascript"></script>
    <script src="/static/jquery/jquery.form.js" type="text/javascript"></script>
    <link href="/static/jquery/themes/icon.css" rel="stylesheet" type="text/css"/>
    <link href="/static/jquery/themes/color.css" rel="stylesheet" type="text/css"/>
    <link href="/static/jquery/themes/default/easyui.css" rel="stylesheet" type="text/css"/>
    <script src="/static/jquery/jquery.easyui.min.js" type="text/javascript"></script>
    <script src="/static/jquery/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>


</head>
<body>
<div>
    <div class="login-div-center">
        <div>
            <span class="login-img-font">坤玛机电后台管理登陆</span>
        </div>
        <div class="login-div-two top top1">
            <form id="loginForm" action="/admin/submitLogin" method="post">
                <div class="login-div-three">
                    <label>用户名：</label>
                    <input type="text" class="login-input" id="name" name="userName"
                           onkeyup="value=value.replace(/[^\w\.\/]/ig,'')" maxlength="20">
                </div>
                <div class="login-div-three selector header">
                    <label>密&nbsp;码：</label>
                    <input type="password" id="pw" class="login-input" name="password" maxlength="12">
                </div>
                <div class="login-div-three selector-1 header1">
                    <label>验证码：</label>
                    <input type="text" class="login-input-code" id="input1" maxlength="4">
                    <input type="text" readonly="readonly" id="checkCode" onclick="createCode()"
                           class="login-input-code1">
                </div>
                <div class="login-div-three">
                    <%--<button class="login-button " type="submit" onclick="window">登陆</button>--%>
                    <a class="easyui-linkbutton" onclick="validate()"
                       style="margin: 5px">&nbsp;&nbsp;确&nbsp;定&nbsp;&nbsp;</a>
                </div>
            </form>
        </div>
    </div>
</div>
<script language="javascript" type="text/javascript">

    var count = 0;
    var code;//定义全局变量
    function createCode() {
        code = "";//定义验证码
        var codeLength = 4;//验证码的长度
        var checkCode = document.getElementById("checkCode");
        var selectChar = new Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 'A', 'B', 'C', 'D',
                'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
                'T', 'U', 'V', 'W', 'X', 'Y', 'Z');//所有候选组成验证码的字符
        for (var i = 0; i < codeLength; i++) {
            var charIndex = Math.floor(Math.random() * 36);
            code += selectChar[charIndex];
        }
        //alert(code);
        if (checkCode) {
            //checkCode.className = "code";
            checkCode.value = code;
        }
    }
    function validate() {
        var inputCode = document.getElementById("input1").value.toLowerCase();//toLowerCase()将字符串转换成小写
        var autodata = document.getElementById("checkCode").value.toLowerCase();
        if (inputCode.length <= 0 || inputCode != autodata) {
//            $.messager.confirm("提示", '验证码输入错误，请重新输入验证码');
            alert("验证码输入错误，请重新输入验证码！");
            createCode();
        } else if (inputCode == autodata) {
            checkpost();
        }
    }
    function init() {
        createCode();
    }
    init();
    function checkpost() {

        $("#loginForm").ajaxSubmit(function (resultJson) {

            if (resultJson.success) {
                window.location.href = "/admin/menu";
            } else {
                //回调操作
                alert("用户名或密码不正确！");
//                $.messager.confirm("提示", "用户名或密码不正确！");
            }

        });
    }
</script>
</body>
</html>