<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>坤玛报价系统</title>
    <link href="//cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/pc/css/ema_2016091201.css">
</head>
<body>
<div class="alert fade" id="alert" role="alert">
    <button type="button" class="close" aria-label="Close"><span aria-hidden="true">×</span></button>
    <span class="js-msg">登陆成功</span>
</div>
<header class="gn-header" id="gn-header">
    <div class="gn-header-left">
        坤玛报价系统
    </div>
    <div class="gn-header-center">
        <img src="/pc/images/logo.png" alt="">
    </div>
    <div class="gn-header-right">
        <button class="login-btn" id="js-login" data-toggle="modal" data-target="#loginModal">
            <img src="/pc/images/unlogin.png" alt="">
            登录
        </button>
        <button class="username" id="userName">

        </button>
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
    <!--leo-->
    <a href="#my-center" id="user-center">个人中心</a>
    <a href="#p-sort" id="p-center">产品中心</a>

    <div class="search-groups" id="search-groups">
        <input type="search" class="search-input" id="searchBox" title="">
        <img src="/pc/images/search.png" id="search-icon">
        <button class="search-btn template" id="searchBtn">搜索</button>
    </div>

    <!--<button id="clearSearchBtn">取消搜索</button>-->
</nav>
<main class="gn-body">
    <!-- 产品列表start -->
    <section class="product-list-wrap" id="product-list-wrap">
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
            <li class="page-go">共<span class="js-total-page">1</span>页 跳转到<input type="text" class="js-page-go"
                                                                                 placeholder="">页
                <button class="btn btn-default btn-sm js-confirm">确定</button>
            </li>
        </ul>
    </section>

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
                    <em>立即购买</em>
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
    <!-- 产品详情 end-->
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
                        <input type="text" id="productSeries" placeholder="请输入产品系列">
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
                        <%--<span class="js-progress">0%</span>--%>
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
                    自主产品研发&nbsp;——&nbsp;坤玛机电以“价值销售”为核心，逐步建立营销、服务、价格的组合优势。先后推出了坤玛KM-10C系列全数字直流调速器、坤玛Z4系列直流电机、坤玛KM-SLK电抗器。</p>
                <p>控制系统的研发和配套服务&nbsp;——&nbsp;直流调速柜、变频控制柜、PLC控制柜的方案设计、产品升级、并承接冶金、橡塑、线缆、纺织、造纸、印刷、
                    包装、机械等各行业电控系统的制造、安装和调试。</p>
                <p>人才外借&nbsp;——&nbsp;利用行业人才资源，建立产品经纪人销售网络和产品服务工程师技术服务支持，为客户提供最为直接、便捷的服务。</p>
            </div>
            <h3>我们的信念</h3>
            <div>传播世界先进技术,促进民族工业发展</div>
        </div>
    </section>

    <section class="my-center template" id="my-center">
        <ul class="me-nav" id="me-nav">
            <li class="active" data-target="me-order-wrap">
                <img src="/pc/images/3314D8D1-00DE-4159-8C05-C394ED5693EF@1x.png" alt="">
                <span>我的订单</span>
            </li>
            <li data-target="me-invoice-wrap">
                <img src="/pc/images/B2710154-867F-4D6B-9AF3-0AD4FC3D5EC1@1x.png" alt="">
                <span>开票信息</span>
            </li>
            <li data-target="me-message-wrap">
                <img src="/pc/images/911DCFFE-4F04-417C-8560-4015EB9B6D67@1x.png" alt="">
                <span>消息中心</span>
            </li>
            <li data-target="me-reset-pwd-wrap">
                <img src="/pc/images/1E3030FE-EAC8-4336-BF71-136FB6069575@1x.png" alt="">
                <span>修改密码</span>
            </li>
            <li data-target="me-contact-wrap">
                <img src="/pc/images/B0096DA7-C3EB-40AB-865F-60724DBE682D@1x.png" alt="">
                <span>联系坤玛</span>
            </li>
            <li data-target="account-wrap">
                <img src="/pc/images/93885EFF-BCA9-4E89-B67F-DD5789D5A365@1x.png" alt="">
                <span>公司账户</span>
            </li>
        </ul>
        <div class="me-content">
            <!--******我的订单 start ******-->
            <div class="m-wrap me-order-wrap" id="me-order-wrap">
                <header class="row m-header">
                    <div class="col-xs-9 m-h-left">
                        订单信息
                    </div>
                    <%--<div class="col-xs-3 m-h-right">--%>
                        <%--<img src="/pc/images/BF5A7373-3F36-450E-8CAC-566B2733C4FF@1x.png" alt="">--%>
                        <%--<button>联系客服</button>--%>
                    <%--</div>--%>
                </header>
                <ul class="m-o-list">
                    <li class="m-o-item template">
                        <div class="row m-o-info">
                            <div class="col-xs-1">
                                <img src="/pc/images/28E04360-D21A-4425-B372-942BC11A967E@2x.png"
                                     alt="">
                            </div>
                            <p class="col-xs-8">
                                订单号:
                                <span class="js-order-no"></span>
                            </p>
                            <div class="col-xs-3">
                                <p class="js-o-time">2016-08-08 06:29:45</p>
                                <p class="js-o-status">订单已提交</p>
                            </div>
                        </div>
                        <div class="m-o-detail">
                            <dl class="m-o-req">
                                <dt>询价单</dt>
                                <dd>
                                    <dl>
                                        <dt>产品名称:</dt>
                                        <dd class="js-o-r-name"></dd>
                                        <dt>产品数量:</dt>
                                        <dd class="js-o-r-num"></dd>
                                        <dt>产品品牌:</dt>
                                        <dd class="js-o-r-brand"></dd>
                                        <dt>产品型号:</dt>
                                        <dd class="js-o-r-model"></dd>
                                        <dt>附件信息:</dt>
                                        <dd></dd>
                                    </dl>
                                </dd>
                            </dl>
                            <dl class="m-o-req-reply">
                                <dt>询价回复
                                    <button class="btn-primary js-see-quote">看报价</button>
                                </dt>
                                <dd>
                                    <ul class="mor-list">
                                        <li class="row mor-item template">
                                            <div class="col-xs-3 js-mor-id"></div>
                                            <div class="col-xs-4 js-mor-name"></div>
                                            <div class="col-xs-2 js-mor-price"></div>
                                            <div class="col-xs-2">
                                                数量: <span class="js-mor-qty"></span>
                                            </div>
                                        </li>
                                    </ul>
                                </dd>
                            </dl>
                        </div>
                    </li>
                </ul>
                <footer class="p-g">
                    <span class="js-prev">上一页</span>
                    <span class="js-page-index">1</span>
                    <span class="js-next">下一页</span>
                </footer>
            </div>
            <div class="m-wrap me-quote-wrap" id="me-quote-wrap">
                <header class="row m-header">
                    <div class="col-xs-9 m-h-left">
                        报价单详情
                    </div>
                </header>
                <div class="q-content">
                    <p>订单编号: <span class="js-q-id"></span></p>
                    <p>创建时间: <span class="js-q-time"></span></p>
                    <p>报价单状态: <span class="js-q-status"></span></p>
                    <p>开票信息:
                        <select name="" id="q-sel" title="开票信息">
                            <option value="">请选择开票信息</option>
                            <option class="js-q-opt template" value=""></option>
                        </select>
                        <span class="js-q-info"></span>
                    </p>
                    <h4>产品详情</h4>
                    <ul class="q-p-list">
                        <li class="row q-p-item template">
                            <div class="col-xs-12">
                                产品编号:
                                <span class="js-q-p-id">pd1312414</span>
                            </div>
                            <div class="col-xs-12">
                                产品名称:
                                <span class="js-q-p-name">
                                    测试
                                </span>
                            </div>
                            <div class="col-xs-6">
                                产品单价:
                                <span class="js-q-p-price">1.00</span>
                            </div>
                            <div class="col-xs-6">
                                开票单价:
                                <span class="js-q-price"></span>
                            </div>
                            <div class="col-xs-6">
                                需求数量:
                                <span class="js-q-qty"></span>
                            </div>
                            <div class="col-xs-6">
                                库存数量:
                                <span class="js-q-p-stock"></span>
                            </div>
                        </li>
                    </ul>
                    <div class="fb">
                        <p>反馈意见:</p>
                        <textarea name="" placeholder="请输入反馈意见" class="js-q-fb"></textarea>
                        <p class="js-q-fb"></p>
                    </div>

                    <button class="btn-sm btn-primary js-q-confirm">报价确认</button>
                </div>
            </div>
            <!--******我的订单 end  ******-->
            <!--******开票信息 start******-->
            <div class="m-wrap me-invoice-wrap" id="me-invoice-wrap">
                <header class="row m-header">
                    <div class="col-xs-9 m-h-left">
                        开票信息
                    </div>
                    <div class="col-xs-3 m-h-right">
                        <img src="/pc/images/1BA4C111-DBC0-417C-9D20-1AE92EE21ABA@1x.png" alt="">
                        <button>新增开票信息</button>
                    </div>
                </header>
                <ul class="m-i-list">
                    <li class="row m-i-item template">
                        <div class="col-xs-11 js-m-i-name">
                            张三
                        </div>
                        <div class="col-xs-1">
                            <img class="js-i-edit" src="/pc/images/05A15422-336F-4904-8D97-8D5A7E707606@1x.png" alt="">
                            <img class="js-i-del" src="/pc/images/F8C4CFFD-FB47-4D4A-B18C-C931A76A6C83@1x.png" alt="">
                        </div>
                    </li>
                </ul>
            </div>
            <!--******开票信息 end  ******-->
            <!--***新增开票信息 start***-->
            <div class="m-wrap me-invoice-wrap" id="add-invoice">
                <header class="row m-header">
                    <div class="col-xs-9 m-h-left">
                        开票信息
                    </div>
                    <div class="col-xs-3 m-h-right">
                        <img src="/pc/images/00975CAE-4D07-43C0-A3B0-BD65249FBC01@1x.png" alt="">
                        <button>查看开票信息</button>
                    </div>
                </header>
                <dl class="m-i-detail">
                    <dt class="require">开&nbsp;票&nbsp;抬&nbsp;头:</dt>
                    <dd><input class="js-titleName" type="text" title=""></dd>
                    <dt>开&emsp;户&emsp;行:</dt>
                    <dd><input class="js-bank" type="text" title=""></dd>
                    <dt>账&emsp;&emsp;&emsp;号:</dt>
                    <dd><input class="js-account" type="text" title=""></dd>
                    <dt>税&emsp;&emsp;&emsp;号:</dt>
                    <dd><input class="js-tax" type="text" title=""></dd>
                    <dt>公&nbsp;司&nbsp;电&nbsp;话:</dt>
                    <dd><input class="js-companyTel" type="text" title=""></dd>
                    <dt class="require">签&emsp;收&emsp;人:</dt>
                    <dd><input class="js-receiver" type="text" title=""></dd>
                    <dt class="require">签收人电话:</dt>
                    <dd><input class="js-receiverTel" type="text" title=""></dd>
                    <dt class="require">地&emsp;&emsp;&emsp;址:</dt>
                    <dd><input class="js-address" type="text" title=""></dd>
                    <dd class="m-i-d-foot">
                        <div>注:带<i>*</i>号的为必填项</div>
                        <button class="btn-primary js-i-save">保存</button>
                    </dd>
                </dl>
            </div>
            <!--***新增开票信息 end***-->

            <div class="m-wrap me-message-wrap" id="me-message-wrap">
                <header class="row m-header">
                    <div class="col-xs-9 m-h-left">
                        消息中心
                    </div>
                    <!--<div class="col-xs-3 m-h-right">-->
                    <!--<img src="/pc/images/942BB494-3FA0-4AFF-B64F-762634A4F900@1x.png" alt="">-->
                    <!--&lt;!&ndash;<button>新增开票信息</button>&ndash;&gt;-->
                    <!--</div>-->
                </header>
                <ul class="m-m-list">
                    <li class="m-m-item template">
                        <p class="row">
                            <span class="col-xs-6 js-m-m-type">
                                系统消息
                            </span>
                            <span class="col-xs-6 js-m-m-time">
                                2016/3/20 下午02:57:41
                            </span>
                        </p>
                        <p class="js-m-m-info">
                            D160300029订单，报价单已生成。感谢您对我们的信任与支持！感谢您对我们的信任与支持！
                        </p>
                    </li>
                </ul>
                <footer class="p-g">
                    <span class="js-m-prev">上一页</span>
                    <span class="js-m-page-index">1</span>
                    <span class="js-m-next">下一页</span>
                </footer>
            </div>
            <div class="m-wrap me-reset-pwd-wrap" id="me-reset-pwd-wrap">
                <header class="row m-header">
                    <div class="col-xs-9 m-h-left">
                        修改密码
                    </div>
                </header>
                <div class="pwd-wrap">
                    <input class="js-rp-phone" type="text" placeholder="请输入手机号">
                    <div class="gain-code-wrap">
                        <input class="js-rp-code" type="text" placeholder="请输入验证码">
                        <button class="btn-primary js-rp-gain-code">获取验证码</button>
                    </div>
                    <input class="js-rp-pwd" type="password" placeholder="请输入密码">
                    <input class="js-rp-qPwd" type="password" placeholder="请确认密码">
                    <button class="btn-primary confirm-btn js-rp-confirm">确认修改</button>
                </div>
            </div>
            <div class="m-wrap me-contact-wrap" id="me-contact-wrap">
                <header class="row m-header">
                    <div class="col-xs-9 m-h-left">
                        联系坤玛
                    </div>
                </header>
                <main>
                    <textarea class="js-suggest" placeholder="请输入您的意见或者建议"></textarea>
                    <img src="/pc/images/D89E7AC4-ADB8-4CA6-A566-CD82E94BB322@1x.png" alt="">
                    <button class="btn-primary js-btn-commit">提交反馈</button>
                </main>
            </div>
            <div class="m-wrap account-wrap" id="account-wrap">
                <header class="row m-header">
                    <div class="col-xs-9 m-h-left">
                        公司账户
                    </div>
                </header>
                <dl>
                    <dt>对公账号</dt>
                    <dd>名称：上海坤玛机电有限公司</dd>
                    <dd>开户行：交行市北工业区支行</dd>
                    <dd>账号：310066247018160085443</dd>
                    <dd>上海汇款 坤玛行号：066247</dd>
                    <dd>外地汇款 坤玛行号：301290050383</dd>
                </dl>
                <dl>
                    <dt>对私账号</dt>
                    <dd>名称：陈雪梅</dd>
                    <dd>开户行：建行阳城路支行</dd>
                    <dd>账号：43674212115954201970</dd>
                    <dd>名称：陈雪梅</dd>
                    <dd>开户行：工行上海东方环球企业中心支行</dd>
                    <dd>账号：6222001001129048931</dd>
                    <dd>名称：陈雪梅</dd>
                    <dd>开户行：农行闸北马戏城支行</dd>
                    <dd>账号：9228480030898902413</dd>
                </dl>
            </div>
        </div>
    </section>
    <section class="template" id="p-sort">
        <ul class="p-sort-list" id="p-sort-list">
            <li class="p-sort-item template">
                <img src="" alt="">
                <p></p>
            </li>
        </ul>
    </section>
    <section class="p-brand template" id="p-brand">
        <div class="p-b-head js-title">
            <h4>选择品牌</h4>
            <p>
                <img src="" alt="">
                <span></span>
            </p>
        </div>
        <ul class="p-brand-list" id="p-brand-list">
            <li class="p-brand-item template">
                <img src="" alt="">
            </li>
        </ul>
    </section>
    <section class="template" id="p-series">
        <h4>请选择产品系列</h4>
        <ul class="p-series-list" id="p-series-list">
            <li class="p-series-item template">
                <p></p>
            </li>
        </ul>
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
<footer class="gn-footer fix" id="gn-footer">
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
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span>&times;</span>
                </button>
                <h4 class="modal-title" id="loginModelTitle">登录坤玛机电</h4>
            </div>
            <div class="modal-body">
                <div class="register-form hidden" id="registerForm">
                    <div class="form-group">
                        <input type="text" name="phone" class="form-control" placeholder="请输入手机号">
                    </div>
                    <div class="form-group">
                        <label class="col-xs-8">
                            <input type="text" name="code" class="form-control" placeholder="请输入验证码">
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
<script src="//cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<script src="/pc/js/ema_2016091201.js"></script>
</body>
</html>