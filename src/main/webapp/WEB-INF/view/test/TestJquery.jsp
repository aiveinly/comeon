<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2016/1/17
  Time: 11:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <script src="/static/jquery/jquery.min.js" type="text/javascript"></script>


    <link href="/static/jquery/themes/icon.css"/>
    <link href="/static/jquery/themes/color.css"/>
    <link href="/static/jquery/themes/default/easyui.css" rel="stylesheet" type="text/css"/>


    <script src="/static/jquery/jquery.easyui.min.js" type="text/javascript"></script>
    <script src="/static/jquery/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
    <script type="text/javascript">

        $(function () {
            initTable();
        });

        function initTable() {
            $("#testTable").datagrid({
                url: "/test/testEasyUI",
                method: "post",
                idField: "id",
                pagination: true,
                pageSize: 2,
                pageList: [2, 4],
                columns: [[
                    {
                        title: "id",
                        field: "id",
                        checkbox: true
                    },
                    {
                        title: "userName",
                        field: "userName"
                    }, {
                        title: "password",
                        field: "password"
                    }

                ]]
            })
            ;
        }

        function initTree() {
            $('#tree').tree({
                url: 'tree_data'
            });
        }

    </script>
</head>
<body style="margin: 0px;padding: 0px;">

<table id="testTable"></table>

<ul id="tree" class="easyui-tree">
    <li>
        <span>用户管理</span>
        <ul>
            <li>会员管理</li>
            <li>管理员管理</li>
        </ul>
    </li>
    <li>
        <span>订单管理</span>
        <ul>
            <li>...</li>
        </ul>
    </li>
    <li>
        <span>消息管理</span>
        <ul>
            <li>...</li>
        </ul>
    </li>
    <li>
        <span>积分管理</span>
        <ul>
            <li>...</li>
        </ul>
    </li>
    <li>
        <span>系统管理</span>
        <ul>
            <li>...</li>
        </ul>
    </li>
    <li>
        <span>产品管理</span>
        <ul>
            <li>产品中心</li>
            <li>产品发布</li>
            <li>
                <span>相关设置</span>
                <ul>
                    <li>系列</li>
                    <li>品牌</li>
                    <li>品类</li>

                </ul>
            </li>
        </ul>
    </li>
    <li>
        <span>VIP管理</span>
        <ul>
            <li>...</li>
        </ul>
    </li>
    <li>
        <span>系统日志</span>
        <ul>
            <li>...</li>
        </ul>
    </li>
    <li>
        <span>一键建议</span>
        <ul>
            <li>...</li>
        </ul>
    </li>
</ul>

</body>
</html>
