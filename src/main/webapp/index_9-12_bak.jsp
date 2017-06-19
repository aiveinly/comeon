<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>坤玛机电</title>
    <link href="//cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/pc/css/ema_2016081101.css">
</head>
<body>
<div class="alert fade" id="alert" role="alert">
    <button type="button" class="close" aria-label="Close"><span aria-hidden="true">×</span></button>
    <span class="js-msg">登陆成功</span>
</div>
<header class="gn-header" id="gn-header">
    <div class="gn-header-left">
        坤玛机电
    </div>
    <div class="gn-header-center">
        <img src="/pc/images/logo.png" alt="">
    </div>
    <div class="gn-header-right">
        <button class="login-btn" id="js-login" data-toggle="modal" data-target="#loginModal">
            <img src="/pc/images/unlogin.png" alt="">
            登录
        </button>
        <button class="username" id="userName"></button>
    </div>
</header>
<div class="gn-gallery" id="gn-gallery">
    <ul>
        <li><img src="/pc/images/1banner.jpg" alt=""></li>
        <li><img src="/pc/images/2banner.jpg" alt=""></li>
        <li><img src="/pc/images/3banner.jpg" alt=""></li>
        <li><img src="/pc/images/4banner.jpg" alt=""></li>
        <li><img src="/pc/images/5banner.jpg" alt=""></li>
    </ul>
    <div class="slide-page">
        <span class="active">1</span><span>2</span><span>3</span><span>4</span><span>5</span>
    </div>
    <!--<img src="/pc/images/home_bg.png" alt="">-->
</div>
<nav class="gn-nav-bar" id="gn-nav-bar">
    <a href="#product-list-wrap" id="sale">特价产品</a>
    <a href="#product-list-wrap" id="recommend">推荐产品</a>
    <!--<a href="#product-list-wrap" id="product-center">产品中心</a>-->
    <a href="#enquiry" id="xunjia">在线询价</a>
    <a href="#about-km" id="about">关于坤玛</a>
    <div class="search-groups" id="search-groups">
        <input type="search" class="search-input" id="searchBox" title="">
        <img src="/pc/images/search.png" id="search-icon">
        <button class="search-btn template" id="searchBtn">搜索</button>
    </div>

    <!--<button id="clearSearchBtn">取消搜索</button>-->
</nav>
<main class="gn-body">
    <!-- 产品列表start -->
    <div class="product-list-wrap" id="product-list-wrap">
        <ul class="product-list template" id="product-list">
            <li class="product-item template">
                <div class="content">
                    <img class="p-img js-pImg" src="" alt="">
                    <p class="font-20 p-name js-pName"></p>
                    <p class="font-16 color-grey">库存:<span class="p-store js-pStore"></span></p>
                    <p class="font-18 color-red">¥<span class="js-pPrice"></span></p>
                </div>
            </li>
        </ul>
        <ul class="page-bar" id="pageBar">
            <li class="page-prev">&lt;</li>
            <li class="page">1</li>
            <li class="page-next">&gt;</li>
            <li class="page-go">共<span class="js-total-page">1</span>页  跳转到<input type="text" class="js-page-go" placeholder="">页
                <button class="btn btn-default btn-sm js-confirm">确定</button></li>
        </ul>
    </div>

    <!-- 产品列表end -->
    <!-- 产品详情 start  -->
    <section class="product-detail template" id="product-detail">
        <div class="pd-header">
            <div class="h-left">
                <img class="js-hl-img" src="" alt="">
            </div>
            <div class="h-right">
                <div class="hr-head js-p-name"></div>
                <div class="hr-body">
                    <p>
                        <span>编&emsp;&emsp;号</span>
                        <span class="js-p-no"></span>
                    </p>
                    <p>
                        <span>产品类型</span>
                        <span class="js-p-sort"></span>
                    </p>
                    <p>
                        <span>品&emsp;&emsp;牌</span>
                        <span class="js-p-brand"></span>
                    </p>
                    <p>
                        <span>系&emsp;&emsp;列</span>
                        <span class="js-p-series"></span>
                    </p>
                    <p>
                        <span>上架时间</span>
                        <span class="js-p-time"></span>
                    </p>
                </div>
                <div class="hr-foot">
                    <span>价&emsp;&emsp;格</span>
                    <span class="font-20 color-red js-p-price">详情请咨询客服</span>

                </div>
            </div>
        </div>
        <div class="pd-footer">
            <div class="pf-left">
                <p class="font-18">产品描述:</p>
                <p class="font-14 color-grey js-desc"></p>
            </div>
            <div class="pf-right">
                <img class="js-pf-img" src="" alt="">
            </div>
        </div>
    </section>
    <!-- 产品详情 end  -->
    <!-- 在线询价 start -->
    <section class="enquiry template" id="enquiry">
        <div class="product-req-panes">
            <div class="req-top">
                <div class="left-pane">
                    <h3>询价单:</h3>
                    <div class="input-field">
                        <input type="text" id="productModel" placeholder="请输入产品型号(必填项)">
                    </div>

                    <div class="input-field">
                        <input type="text" id="productNum" placeholder="请输入需求数量" value="1">
                    </div>

                    <div class="input-field">
                        <input type="text" id="productName" placeholder="请输入产品名称">
                    </div>

                    <div class="input-field">
                        <input type="text" id="productBrand" placeholder="请输入产品品牌">
                    </div>

                </div>
                <div class="right-pane">
                    <h3>附件:</h3>
                    <form enctype="multipart/form-data">
                        <input type="file" name="file" class="hidden" id="js-file">
                        <button class="upload-btn" id="uploadBtn">上传附件</button>
                        <progress max="100" value="5">
                            <ie></ie>
                        </progress>
                    </form>
                    <!--<button>附件一</button>-->
                    <!--<button>附件一</button>-->
                </div>
                <input type="hidden" name="uid">
            </div>
            <div class="req-bottom">
                <button type="reset" id="clear-form">清空输入</button>
                <button id="commit-req">提交</button>
            </div>
        </div>
    </section>
    <!-- 在线询价 end -->

    <section class="about-km template" id="about-km">
        <div class="wrapper">
            <h3>联系我们</h3>
            <div class="contact">
                <p>客服热线：4006590690</p>
                <p>销售热线：15921215218/15901651735</p>
                <p>技术热线：13816377155/13671766941</p>
                <p>传&emsp;&emsp;真：021-56035011</p>
                <p>公司地址：上海市闸北区灵石路大徐家阁199号</p>
                <p>邮&emsp;&emsp;箱：admin@comeon365.com</p>
            </div>
            <h3>关于我们</h3>
            <div>
                坤玛机电是一家集国际知名品牌代理、工业自动化成套和配套销售、行业互联网平台资源整合于一体的公司。
            </div>
            <h3>服务行业</h3>
            <div>
                机床、冶金、电力、橡塑、线缆、矿山、水泥、包装、化工、纺织、造纸、港机、造船、污水处理等。
            </div>
            <h3>核心优势</h3>
            <div>
                <p>
                    自主产品研发&nbsp;——&nbsp;坤玛机电以“价值销售”为核心，逐步建立营销、服务、价格的组合优势。先后退出了坤玛KM-10C系列全数字直流调速器、坤玛Z4系列直流电机、坤玛KM-SLK电抗器。</p>
                <p>控制系统的研发和配套服务&nbsp;——&nbsp;直流调速柜、变频控制柜、PLC控制柜的方案设计、产品升级、并承接冶金、橡塑、线缆、纺织、造纸、印刷、
                    包装、机械等各行业电控系统的制造、安装和调试。</p>
                <p>人才外借&nbsp;——&nbsp;利用行业人才资源，建立产品经纪人销售网络和产品服务工程师技术服务支持，为客户提供最为直接、便捷的服务。</p>
            </div>
            <h3>我们的信念</h3>
            <div>传播世界先进技术,促进民族工业发展</div>
        </div>
    </section>
</main>
<aside class="gn-service" id="gn-service">
    <div class="wx">
        <i class="icon-wechat"></i>
        <p>微信平台登录</p>
        <img class="qr-code" src="/pc/images/Qcode.png" alt="">
    </div>
    <div class="qq">
        <a href="tencent://message/?uin=18256011&Site=QQ交谈&Menu=yes">
            <i class="icon-qq"></i>
            <p>客服一</p>
        </a>
    </div>
    <div class="qq">
        <a href="tencent://message/?uin=18256011&Site=QQ交谈&Menu=yes">
            <i class="icon-qq"></i>
            <p>客服二</p>
        </a>
    </div>
</aside>
<footer class="gn-footer" id="gn-footer">
    <div class="qr-code">
        <img src="/pc/images/Qcode.png" alt="">
    </div>
    <div class="text-wrap">
        <p>Copyright&ensp;@&ensp;2009&ensp;上海坤玛机电有限公司&ensp;All&ensp;Rights&ensp;Reserved&emsp;沪ICP备08111853号</p>
        <p>地址:上海市静安区灵石路大徐家阁199号&emsp;电话:021-51082175</p>
    </div>
</footer>

<!-- Modal -->
<div class="modal fade" id="loginModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="loginModelTitle">登录坤玛机电</h4>
            </div>
            <div class="modal-body">
                <div class="register-form hidden" id="registerForm">
                    <div class="form-group">
                        <input type="text" name="phone" class="form-control" placeholder="请输入手机号">
                    </div>
                    <div class="form-group">
                        <label class="col-xs-8">
                            <input type="password" class="form-control" placeholder="请输入验证码">
                        </label>
                        <button class="btn btn-default col-xs-4 gain-code-btn" id="js-gain-code">获取验证码</button>
                    </div>
                    <div class="form-group">
                        <input type="text" name="username" class="form-control" placeholder="请输入您的名字">
                    </div>
                    <div class="form-group">
                        <input type="password" name="pwd" class="form-control" placeholder="请设置密码">
                    </div>
                    <div class="form-group">
                        <input type="password" name="pwd2" class="form-control" placeholder="请确认密码">
                    </div>
                    <div class="form-group">
                        <input type="text" name="inviteCode" class="form-control" placeholder="请输入邀请码(可不填)">
                    </div>
                    <div class="has-account" id="has-account">
                        已有账号，登录
                    </div>
                    <div class="form-group">
                        <button class="btn btn-block btn-lg btn-primary" id="registerBtn">注册</button>
                    </div>
                </div>
                <div class="login-form" id="loginForm">
                    <div class="form-group">
                        <input type="text" class="form-control js-phone" placeholder="请输入手机号">
                    </div>
                    <div class="form-group">

                        <input type="password" class="form-control js-password" placeholder="请输入密码">
                    </div>
                    <div class="no-account" id="no-account">
                        没有账号，注册
                    </div>
                    <div class="form-group">
                        <button class="btn btn-block btn-lg btn-primary" id="loginBtn">登录</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="/pc/lib/jquery-2.1.4.min.js"></script>
<script src="/pc/js/main_2016081101.js"></script>
<script src="//cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
</body>
</html>