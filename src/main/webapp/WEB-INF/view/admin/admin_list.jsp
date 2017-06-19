<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2016/2/25
  Time: 10:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Title</title>
</head>

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
        $("#adminTable").datagrid({
                    url: "/admin/listAdmin",
                    method: "post",
                    idField: "suid",
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
                            title: "suid",
                            field: "suid",
                            width: 50,
                            hidden: true
                        }]],
                    columns: [[

                        {
                            title: "用户名",
                            field: "username",
                            formatter: function (data) {
                                if (data == null) {
                                    return "-";
                                } else {
                                    return data;
                                }
                            },
                            width: 10
                        }, {
                            title: "昵称",
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
                            title: "密码",
                            field: "password",
                            formatter: function (data) {
                                if (data == null) {
                                    return "-";
                                } else {
                                    return data;
                                }
                            },
                            width: 10
                        }, {
                            title: "角色",
                            field: "role",
                            formatter: function (data) {
                                if (data == null) {
                                    return "-";
                                } else if (data == 0) {
                                    return "超级管理员";
                                } else {
                                    return "管理员";
                                }
                            },
                            width: 10
                        }, {
                            title: "手机号",
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
                    ]]
                }
        );
    }

    var isSubmitAdd;
    function openAddDiv(isAdd) {
        isSubmitAdd = isAdd;
        if (isAdd) {

            $('#addDiv').window({title: "增加"});
            $('#addDiv').window('open');  // open a window
            $('#addDiv').window('center');


            var inputs = $("#addForm input.easyui-textbox");
            inputs.textbox("setText", "");
            inputs.textbox("setValue", "");
            $('#add_role').combobox('clear');

        } else {
            var data = $("#adminTable").datagrid("getSelected");
            if (data == null) {
                $.messager.confirm('提示', '请选中一条记录进行操作!');
                return;
            }
            $('#addDiv').window({title: "修改"});
            $('#addDiv').window('open');  // open a window
            $('#addDiv').window('center');


            $('#add_suid').val(data.suid);


            $('#add_username').textbox('setText',data.username);
            $('#add_username').textbox('setValue',data.username);

            $('#add_name').textbox('setText',data.name);
            $('#add_name').textbox('setValue',data.name);

            $('#add_password').textbox('setText',data.password);
            $('#add_password').textbox('setValue',data.password);

            $('#add_role').combobox('select',data.role);

            $('#add_tel').textbox('setText',data.tel);
            $('#add_tel').textbox('setValue',data.tel);
        }
    }


    function addAdmin() {

        $('#addDiv').window('close');

        if (isSubmitAdd) {
            $("#addForm").attr("action", "/admin/addAdmin");
            $("#addForm").ajaxSubmit(function (resultJson) {
                //回调操作
                $.messager.confirm("提示", resultJson.msg);
                $("#adminTable").datagrid("reload");
            });
        } else {
            $("#addForm").attr("action", "/admin/editAdmin");
            $("#addForm").ajaxSubmit(function (resultJson) {
                //回调操作
                $.messager.confirm("提示", resultJson.msg);
                $("#adminTable").datagrid("reload");
            });

        }
    }

    function cancelAddDiv() {
        $('#addDiv').window('close');
    }


    function removeAdmin() {
        var data = $("#adminTable").datagrid("getSelected");
        if (data == null) {
            $.messager.confirm('提示', '请选中一条记录进行操作!');
            return;
        }
        var suid = data.suid;
        $.messager.confirm('提示', '确定要删除这条记录吗？', function (r) {
            if (r) {
                $.ajax({
                    url: "/admin/removeAdmin",
                    dataType: "json",
                    type: "post",
                    data: {
                        suid: suid
                    },
                    error: function () {
                        alert("系统错误!");
                    },
                    success: function (data) {
                        $.messager.confirm("提示", data.msg);
                        $("#adminTable").datagrid("reload");
                    }
                });
            }
        });
    }
</script>
<body>


<div id="toolBar">

    <a class="easyui-linkbutton" onclick="openAddDiv(true)" data-options="iconCls:'icon-add'"
       style="margin:5px 5px 5px 0px;">新增</a>
    <a class="easyui-linkbutton" onclick="openAddDiv(false)" data-options="iconCls:'icon-edit'"
       style="margin:5px 5px 5px 0px;">修改</a>
    <a class="easyui-linkbutton" onclick="removeAdmin()" data-options="iconCls:'icon-remove'"
       style="margin:5px 5px 5px 0px;">删除</a>

</div>
<table id="adminTable"></table>


<div id="addDiv" class="easyui-window" title="添加" style="height:260px;width:260px;"
     data-options="iconCls:'icon-search',modal:true" closed="true">
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'north'" style="height:80%;">
            <form id="addForm" method="POST">
                <input id="add_suid" name="suid" type="hidden"/>
                <table style="text-align: center;width: 100%;height:100%" border="0" cellpadding="2" cellspacing="0">
                    <tr>
                        <td>用户名:</td>
                        <td><input class="easyui-textbox" id="add_username" name="username" type="text"/></td>
                    </tr>
                    <tr>
                        <td>昵称:</td>
                        <td><input class="easyui-textbox" id="add_name" name="name" type="text"/></td>
                    </tr>
                    <tr>
                        <td>密码:</td>
                        <td><input class="easyui-textbox" id="add_password" name="password" type="text"/></td>
                    </tr>
                    <tr>
                        <td>角色:</td>
                        <td><input class="easyui-combobox" id="add_role" name="role"
                                   data-options="
                                                valueField: 'value',
                                                textField: 'label',
                                                data: [{
                                                    label: '管理员',
                                                    value: '1'
                                                },{
                                                    label: '超级管理员',
                                                    value: '0'
                                                }]"/>
                        </td>
                    </tr>
                    <tr>
                        <td>手机号:</td>
                        <td><input class="easyui-numberbox" id="add_tel" name="tel" type="text"/></td>
                    </tr>
                </table>
            </form>
        </div>
        <div data-options="region:'center'" style="text-align:center;">
            <a class="easyui-linkbutton" onclick="addAdmin()" data-options="iconCls:'icon-ok'"
               style="margin: 5px">确定</a>
            <a class="easyui-linkbutton" onclick="cancelAddDiv()" data-options="iconCls:'icon-cancel'"
               style="margin: 5px">取消</a>
        </div>
    </div>
</div>

</body>
</html>
