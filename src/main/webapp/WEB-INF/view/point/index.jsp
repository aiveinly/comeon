<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2016/4/13
  Time: 15:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<html>
<head>
    <title>Title</title>


    <script src="/static/jquery/jquery.min.js" type="text/javascript"></script>
    <script src="/static/jquery/jquery.form.js" type="text/javascript"></script>
    <link href="/static/jquery/themes/icon.css" rel="stylesheet" type="text/css"/>
    <link href="/static/jquery/themes/color.css" rel="stylesheet" type="text/css"/>
    <link href="/static/jquery/themes/default/easyui.css" rel="stylesheet" type="text/css"/>
    <script src="/static/jquery/jquery.easyui.min.js" type="text/javascript"></script>
    <script src="/static/jquery/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
    <script type="text/javascript">

        $(function () {
            initTable();
        });

        function initTable() {
            $("#pointLogTable").datagrid({
                        url: "/point/searchPointLogs",
                        method: "post",
                        idField: "id",
                        fitColumns: true,
                        fit: true,
                        striped: true,
                        pagination: true,
                        rownumbers: true,
                        singleSelect: true,
                        autoRowHeight: true,
                        toolbar: toolBar,
                        pageSize: 10,
                        pageList: [10, 15, 20, 25, 30],
                        frozenColumns: [[
                            {
                                title: "编号",
                                field: "id",
                                hidden: true
//                                checkbox: true
                            }]],
                        columns: [[
                            {
                                title: "用户编号",
                                field: "uid",
                                hidden: true
//                                checkbox: true
                            }, {
                                title: "电话",
                                field: "tel",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                },
                                width: 10
                            }
                            , {
                                title: "详情",
                                field: "msg",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                },
                                width: 10
                            }, {
                                title: "历史积分",
                                field: "afterpoint",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                },
                                width: 10
                            }, {
                                title: "操作",
                                field: "type",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else if (data == 1) {
                                        return "增加";
                                    } else if (data == 2) {
                                        return "扣除";
                                    }
                                },
                                width: 10
                            }, {
                                title: "操作数值",
                                field: "point",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                },
                                width: 10
                            }, {
                                title: "操作人",
                                field: "adminname",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                },
                                width: 10
                            }


                        ]]
                    }
            )
            ;
        }


        function searchPointLog() {
            var tel = $("#search_tel").textbox("getValue");
            $("#pointLogTable").datagrid({
                url: "/point/searchPointLogs",
                queryParams: {
                    tel: tel
                }
            });
            $('#win').window('close');
        }

    </script>
</head>
<body>
<div id="toolBar">
    <br/>
    手机号：<input class="easyui-textbox" id="search_tel" type="text"/>
    <a class="easyui-linkbutton" onclick="searchPointLog()" data-options="iconCls:'icon-search'"
       style="margin:5px 5px 5px 0px;">查询</a>
    <br/>
</div>
<table id="pointLogTable" style="width:auto;"></table>
</body>
</html>
