<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2016/1/18
  Time: 10:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="/static/jquery/jquery.min.js" type="text/javascript"></script>
    <link href="/static/jquery/themes/icon.css" rel="stylesheet" type="text/css"/>
    <link href="/static/jquery/themes/color.css" rel="stylesheet" type="text/css"/>
    <link href="/static/jquery/themes/default/easyui.css" rel="stylesheet" type="text/css"/>
    <link href="/static/css/index.css" rel="stylesheet" type="text/css"/>
    <script src="/static/jquery/jquery.easyui.min.js" type="text/javascript"></script>
    <script src="/static/jquery/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function () {


            $(".js-menu-item").click(function (event) {
                event.preventDefault();
                $(".js-menu-item").removeClass("active");
                $(this).addClass("active");
                var url = $(this).attr('href');
                var title = $(this).text();
                addTab(url, title);

            })
        });


        function addTab(url, title) {


//            var t = $('#tt').tabs("getTab", title);
//            if (t != null) {
//                $('#tt').tabs('select', title);
//                var currentTab = $('#tt').tabs('getSelected');
//                $('#tt').tabs('update', {
//                    tab: currentTab,
//                    type:'all',
//                    options: {
//                        title: title,
//                        href: url  // the new content URL
//                    }
//                });
//                currentTab.panel('refresh', url);
//                return;
//            }

            $('#tt').tabs("close", title);
            // add a new tab panel
            $('#tt').tabs('add', {
                title: title,
                content: "<iframe src='" + url + "' style='width:100%;height:100%;border:0'></iframe>",
                closable: true
            });
        }

    </script>
</head>
<body>

<div id="cc" class="easyui-layout" style="width:100%;height:100%;">
    <%--<div data-options="region:'north',split:true" style="height:100px;">--%>
    <%--<img src="/static/php.jpg" style="width: 100%;height:100%"/>--%>
    <%--&lt;%&ndash;</div>&ndash;%&gt;   <img src="/static/img/man.png"/>--%>
    <%--<div data-options="region:'south',title:'South Title',split:true" style="height:100px;"></div>--%>
    <%--<div data-options="region:'east',title:'East',split:true" style="width:100px;"></div>--%>
    <div class="js-menu-list" data-options="region:'west',title:'菜单栏:   ${admin.name}',split:true"
         style="width:300px;overflow: hidden;">
        <div id="aa" class="easyui-accordion" style="width:100%;height:100%;">
            <div title="用户管理" data-options="iconCls:'icon-reload'" class="planet-menu">
                <a href="/user/index" class="planet-menu-item js-menu-item">用户列表</a>
                <a href="/apply/index" class="planet-menu-item js-menu-item">VIP管理</a>
                <a href="/point/pointIndex" class="planet-menu-item js-menu-item">积分日志</a>

            </div>
            <div title="相关设置" data-options="iconCls:'icon-reload'" class="planet-menu">
                <a href="/dict/toProdict?type=1" class="planet-menu-item js-menu-item">品类</a>
                <a href="/dict/toProdict?type=2" class="planet-menu-item js-menu-item">品牌</a>
                <a href="/dict/toProdict?type=3" class="planet-menu-item js-menu-item">系列</a>
                <a href="/advertising/toAdvertising" class="planet-menu-item js-menu-item">广告</a>
            </div>

            <div title="产品设置" data-options="iconCls:'icon-reload'" class="planet-menu">
                <a href="/product/toProduct" class="planet-menu-item js-menu-item">产品列表</a>
                <a href="/merchandise/index" class="planet-menu-item js-menu-item">商品列表</a>
            </div>

            <div title="订单管理" data-options="iconCls:'icon-reload'" class="planet-menu">
                <a href="/order/index" class="planet-menu-item js-menu-item">订单列表</a>
            </div>

            <div title="投诉建议" data-options="iconCls:'icon-reload'" class="planet-menu">
                <a href="/suggest/index" class="planet-menu-item js-menu-item">投诉建议</a>
            </div>
            <div title="管理中心" data-options="iconCls:'icon-reload'" class="planet-menu">
                <a href="/admin/index" class="planet-menu-item js-menu-item">管理员中心</a>
                <a href="/message/index" class="planet-menu-item js-menu-item">消息管理</a>
                <a href="/admin/logout?username=${admin.username}" class="planet-menu-item">退出登录</a>
            </div>

        </div>

    </div>
    <div data-options="region:'center',split:true" style="padding:0px;background:#eee;">
        <div id="tt" class="easyui-tabs" style="width:100%;height:100%;" border=0>

        </div>

    </div>
</div>


</body>
</html>
</html>
