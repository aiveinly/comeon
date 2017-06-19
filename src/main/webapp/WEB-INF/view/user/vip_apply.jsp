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
    <title>vip申请</title>


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
            $("#applyTable").datagrid({
                        url: "/apply/listApply",
                        method: "post",
                        idField: "id",
//                        fitColumns: true,
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
                                title: "id",
                                field: "id",
                                hidden: true
                            }]],
                        columns: [[
                            {
                                title: "uid",
                                field: "uid",
                                hidden: true
                            }, {
                                title: "用户名",
                                field: "username",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                },
                                width: 100
                            }, {
                                title: "内容",
                                field: "content",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                },
                                width: 400
                            }, {
                                title: "姓名",
                                field: "truename",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                },
                                width: 60
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
                                width: 100
                            }, {
                                title: "地区",
                                field: "area",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                },
                                width: 80
                            }, {
                                title: "工龄",
                                field: "workdate",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                },
                                width: 40
                            }, {
                                title: "fileurl",
                                field: "fileurl",
                                hidden: true
                            }, {
                                title: "状态",
                                field: "status",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else if (data == 0) {
                                        return "未处理";
                                    } else if (data == -1) {
                                        return "<font color='red'>未通过</font>";
                                    } else if (data == 1) {
                                        return "<font color='green'>通过</font>";
                                    }
                                },
                                width: 50
                            }, {
                                title: "申请类型",
                                field: "applytype",
                                width: 60,
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else if (data == 2) {
                                        return "工程师";
                                    } else {
                                        return "经纪人";
                                    }
                                },
                            }, {
                                title: "文件",
                                field: "file1",
                                width: 50,
                                formatter: function (data) {
                                    if (data == null || data == "") {
                                        return "";
                                    } else {
                                        return "<img  src='/static/img/download.png' style='height: 20px;width: 20px' onclick='downloadFile(" + JSON.stringify(data) + ")'/>";
                                    }
                                }
                            }, {
                                title: "处理时间",
                                field: "updatedateString",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                },
                                width: 150
                            }, {
                                title: "操作",
                                field: "operate",
                                formatter: function (data, rowData) {
                                    if (data == null) {
                                        return "<input style='background:-moz-linear-gradient(top,#ffffff,#eeeeee);border-radius: 4px;border:1px #cccccc solid;' value='查看' type='button' onclick='read(" + JSON.stringify(rowData) + ")'/>";
                                    }
                                },
                                width: 50
                            }
                        ]]
                    }
            );
        }

        function read(data) {

            openReadDiv(data.id, data.content, data.uid, data.status, data.applytype);


        }

        function openReadDiv(id, content, uid, status, applytype) {


            $('#win').window('open');  // open a window
            $('#win').window('center');


            if (status != 0) {
                $('#pass').css({"display": "none"});
                $('#refuse').css({"display": "none"});
            } else {
                $('#pass').css({"display": "inline-block"});
                $('#refuse').css({"display": "inline-block"});
            }

            $('#pass').attr("onclick", "deal(" + id + ",1," + uid + "," + applytype + ")");
            $('#refuse').attr("onclick", "deal(" + id + ",-1," + uid + "," + applytype + ")");
            $('#apply_content').text(content);
        }

        function deal(id, status, uid, applytype) {
            $('#win').window('close');
            $.ajax({
                url: "/apply/deal",
                dataType: "json",
                type: "post",
                data: {
                    id: id,
                    status: status,
                    uid: uid,
                    applytype: applytype
                },
                error: function () {
                    alert("系统错误!");
                },
                success: function (data) {
                    if (data.success != 1)
                        $.messager.confirm("提示", data.msg);
                    $("#applyTable").datagrid("reload");
                }
            });
        }

        function cancelWinDiv() {
            $('#win').window('close');
        }



        var imgurl = "http://image.comeon365.com/";
        /**
         * 下载文件, 和图片地址一样都是七牛的
         * @param url
         */
        function downloadFile(url) {
            window.parent.addTab(imgurl + url, "查看文件");
        }
    </script>
</head>
<body>


<div id="toolBar">
    <%--<a class="easyui-linkbutton" onclick="read()" data-options="iconCls:'icon-edit'"--%>
    <%--style="margin:5px 5px 5px 0px;">已读</a>--%>

</div>


<table id="applyTable"></table>

<div id="win" class="easyui-window" title="查看" style="width:260px;height:200px;"
     data-options="iconCls:'icon-search',modal:true" closed="true">
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'north'" style="height:80%;">
            <textarea id="apply_content" style="width: 99%;height: 99%" readonly="true"></textarea>
        </div>
        <div data-options="region:'center'" style="text-align:center;">
            <a class="easyui-linkbutton" id="pass" data-options="iconCls:'icon-ok'"
               style="margin: 5px">通过</a>
            <a class="easyui-linkbutton" id="refuse" data-options="iconCls:'icon-no'"
               style="margin: 5px">拒绝</a>
            <a class="easyui-linkbutton" onclick="cancelWinDiv()" data-options="iconCls:'icon-cancel'"
               style="margin: 5px">取消</a>
        </div>

    </div>
</div>

</body>
</html>
