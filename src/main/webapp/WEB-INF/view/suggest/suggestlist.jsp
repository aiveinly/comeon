<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2016/2/23
  Time: 17:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
            $("#suggestTable").datagrid({
                        url: "/suggest/listSuggest?",
                        method: "post",
                        idField: "sgid",
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
                                title: "SGID",
                                field: "sgid",
                                width: 50,
                                hidden: true
                            }]],
                        columns: [[

                            {
                                title: "意见",
                                field: "suggest",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                },
                                width: 10
                            }, {
                                title: "日期",
                                field: "updatedateString",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                },
                                width: 10
                            }, {
                                title: "用户",
                                field: "uid",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                },
                                width: 10,
                                hidden: true
                            }, {
                                title: "用户",
                                field: "name",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                },
                                width: 10
                            }, {
                                title: "状态",
                                field: "status",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else if (data == 0) {
                                        return "<font color='green'>未读<font>";
                                    } else {
                                        return "已读";
                                    }
                                },
                                width: 10
                            }, {
                                title: "操作",
                                field: "operate",
                                formatter: function (data, rowData) {
                                    if (data == null) {
                                        return "<input style='background:-moz-linear-gradient(top,#ffffff,#eeeeee);border-radius: 4px;border:1px #cccccc solid;' value='查看' type='button' onclick='read(" + JSON.stringify(rowData) + ")'/>";
                                    }
                                },
                                width: 10
                            }
                        ]]
                    }
            );
        }

        function read(data) {

            openReadDiv(data.sgid, data.uid, data.suggest, data.status);


        }

        function openReadDiv(sgid, uid, content, status) {
            $('#win').window('open');  // open a window
            $('#win').window('center');


            if (status != 0) {
                $('#readButton').css({"display": "none"});

            } else {
                $('#readButton').css({"display": "inline-block"});

            }

            $('#readButton').attr("onclick", "setRead(" + sgid + "," + uid + ")");
            $('#suggest_content').text(content);
        }

        function setRead(sgid, uid) {
            $('#win').window('close');
            $.ajax({
                url: "/suggest/read",
                dataType: "json",
                type: "post",
                data: {
                    sgid: sgid,
                    uid: uid
                },
                error: function () {
                    alert("系统错误!");
                },
                success: function (data) {
                    if (data.success != 1)
                        $.messager.confirm("提示", data.msg);
                    $("#suggestTable").datagrid("reload");
                }
            });
        }

        function cancelWinDiv() {
            $('#win').window('close');
        }

    </script>
</head>
<body>


<div id="toolBar">
    <%--<a class="easyui-linkbutton" onclick="read()" data-options="iconCls:'icon-edit'"--%>
    <%--style="margin:5px 5px 5px 0px;">已读</a>--%>

</div>


<table id="suggestTable"></table>

<div id="win" class="easyui-window" title="查看" style="width:260px;height:200px;"
     data-options="iconCls:'icon-search',modal:true" closed="true">
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'north'" style="height:80%;">
            <textarea id="suggest_content" style="width: 99%;height: 99%" readonly="true"></textarea>
        </div>
        <div data-options="region:'center'" style="text-align:center;">
            <a class="easyui-linkbutton" id="readButton" data-options="iconCls:'icon-ok'"
               style="margin: 5px">设置已读</a>
            <a class="easyui-linkbutton" onclick="cancelWinDiv()" data-options="iconCls:'icon-cancel'"
               style="margin: 5px">取消</a>
        </div>

    </div>
</div>

</body>
</html>
