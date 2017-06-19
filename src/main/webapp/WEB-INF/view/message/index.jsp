<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2016/3/3
  Time: 15:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <title>消息管理</title>

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
            $("#messageTable").datagrid({
                        url: "/message/listMessage",
                        method: "post",
                        idField: "msgid",
                        remoteSort: true,
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
                                field: "msgid",
                                hidden: true
                            }]],
                        columns: [[
                            {
                                title: "mbid",
                                field: "mbid",
                                width: 10,
                                hidden: true
                            }, {
                                title: "消息",
                                field: "message",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                },
                                width: 10
                            }, {
                                title: "发送人",
                                field: "sender",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else if (data == 0) {
                                        return "系统";
                                    } else if (data == 1) {
                                        return "人工";
                                    }
                                },
                                width: 10
                            }, {
                                title: "接收人",
                                field: "receiver",
                                hidden: true
                            }, {
                                title: "接收人",
                                field: "nameString",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                }

                            }, {
                                title: "状态",
                                field: "isread",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else if (data == 0) {
                                        return "未读";
                                    } else {
                                        return "已读";
                                    }
                                }
                                ,
                                width: 10
                            }, {
                                title: "发送日期",
                                field: "senddateString",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                }
                            },
                            {
                                title: "操作",
                                field: "operate",
                                formatter: function (data, rowData) {
                                    if (data == null) {
                                        return "<input style='background:-moz-linear-gradient(top,#ffffff,#eeeeee);border-radius: 4px;border:1px #cccccc solid;' value='删除' type='button' onclick='removeMessage(" + JSON.stringify(rowData) + ")'/>";
                                    } else {
                                        return "";
                                    }
                                },
                                width: 20
                            }
                        ]]
                    }
            )
            ;
        }

        /**
         * search by name and username
         */
        function searchMessage() {
            var receiver = $("#search_receiver").textbox("getValue");
            var startTime = $("#search_startTime").datetimebox("getValue");
            var endTime = $("#search_endTime").datetimebox("getValue");
            $("#messageTable").datagrid({
                url: "/message/listMessage",
                queryParams: {
                    receiver: receiver,
                    startTime: startTime,
                    endTime: endTime
                }
            });
        }


        function removeMessage(data) {
            var msgid = data.msgid;
            $.messager.confirm('提示', '确定要删除这条记录吗？', function (r) {
                if (r) {
                    $.ajax({
                        url: "/message/removeMessage",
                        dataType: "json",
                        type: "post",
                        data: {
                            msgid: msgid
                        },
                        error: function () {
                            alert("系统错误!");
                        },
                        success: function (data) {
                            $.messager.confirm("提示", data.msg);
                            $("#messageTable").datagrid("reload");
                        }
                    });
                }
            });
        }


    </script>


</head>
<body>
<div id="toolBar">
    <br/>
    接收人：&nbsp <input class="easyui-textbox" id="search_receiver" type="text"/>
    日期：&nbsp
    <input class="easyui-datetimebox" name="birthday" id="search_startTime"
           data-options="showSeconds:false" style="width:150px">
    <input class="easyui-datetimebox" name="birthday" id="search_endTime"
           data-options="showSeconds:false" style="width:150px">
    <a class="easyui-linkbutton" onclick="searchMessage()" data-options="iconCls:'icon-search'"
       style="margin:5px 5px 5px 0px;">查询</a>
    <br/>

</div>
<table id="messageTable"></table>


</body>
</html>
