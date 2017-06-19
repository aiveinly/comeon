<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2016/1/20
  Time: 10:42
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
            $("#userTable").datagrid({
                        url: "/user/userList",
                        method: "post",
                        idField: "uid",
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
                                title: "uid",
                                field: "uid",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                },
                                hidden: true
//                                checkbox: true
                            }]],
                        columns: [[
                            {
                                title: "iid",
                                field: "iid",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                },
                                hidden: true
                            },
                            {
                                title: "qid",
                                field: "qid",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                },
                                hidden: true
                            }
                            ,
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
                                width: 8
                            }
                            ,
                            {
                                title: "昵称",
                                field: "name",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                },
                                width: 6
                            }
                            ,
                            {
                                title: "密码",
                                field: "password",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                },
                                width: 10,
                                hidden: true
                            }
                            ,
                            {
                                title: "VIP",
                                field: "vip",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else if (data == 0) {
                                        return "否";
                                    } else if (data == 1) {
                                        return "是";
                                    }
                                },
                                width: 4
                            }

                            ,
                            {
                                title: "积分",
                                field: "point",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                },
                                width: 4
                            }
                            ,
                            {
                                title: "电话",
                                field: "tel",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                },
                                width: 8
                            },
                            {
                                title: "状态",
                                field: "status",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else if (data == 0) {
                                        return "<font color='#F00'>冻结</font>";
                                    } else {
                                        return "正常";
                                    }
                                },
                                width:4
                            },

                            {
                                title: "备注",
                                field: "remark",
                                width: 15
                            },
                            {
                                title: "注册时间",
                                field: "logindate",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                },
                                width: 12,
                                formatter:function(value,row,index){
                                    var unixTimestamp = new Date(value);
                                    return unixTimestamp.toLocaleString();
                                }
                            },
                            {
                                title: "邀请人",
                                field: "referralUname",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                },
                                width: 6
                            },
                            {
                                title: "操作",
                                field: "operate",
                                formatter: function (data, rowData) {
                                    if (data == null) {
                                        return "<input style='background:-moz-linear-gradient(top,#ffffff,#eeeeee);border-radius: 4px;border:1px #cccccc solid;' value='积分' type='button' onclick='openIntegralDiv(" + JSON.stringify(rowData) + ")'/><input style='background:-moz-linear-gradient(top,#ffffff,#eeeeee);border-radius: 4px;border:1px #cccccc solid;' value='推送' type='button' onclick='openMessageDiv(" + JSON.stringify(rowData) + ")'/> <input style='background:-moz-linear-gradient(top,#ffffff,#eeeeee);border-radius: 4px;border:1px #cccccc solid;' value='修改' type='button' onclick='openEditDiv(" + JSON.stringify(rowData) + ")'/> <input style='background:-moz-linear-gradient(top,#ffffff,#eeeeee);border-radius: 4px;border:1px #cccccc solid;' value='删除' type='button' onclick='removeUser(" + JSON.stringify(rowData) + ")'/>";
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

        function addTooltip(tooltipContentStr,tootipId){
            //添加相应的tooltip
            $('#'+tootipId).tooltip({
                position: 'bottom',
                content: tooltipContentStr,
                onShow: function(){
                    $(this).tooltip('tip').css({
                        backgroundColor: 'white',
                        borderColor: '#97CBFF'
                    });
                }
            });
        }
        function openMessageDiv2() {
            $('#messageDiv').window('open');  // open a window
            $('#messageDiv').window('center');
            $('#message_udi').val('0');
            $('#message_content').val("");
        }

        function openMessageDiv(data) {
            $('#messageDiv').window('open');  // open a window
            $('#messageDiv').window('center');
            $('#message_udi').val(data.uid);
            $('#message_content').val("");

        }

        function cancelMessageDiv() {
            $('#messageDiv').window('close');
        }

        function submitMessage() {
            $('#messageDiv').window('close');
            $("#messageForm").ajaxSubmit(function (resultJson) {
                //回调操作
                $.messager.confirm("提示", resultJson.msg);
            });
        }

        /**
         * search by name and username
         */
        function searchUser() {
            var username = $("#search_username").textbox("getValue");
            var name = $("#search_name").textbox("getValue");
            $("#userTable").datagrid({
                url: "/user/userList",
                queryParams: {
                    name: name,
                    username: username
                }
            });
            $('#win').window('close');
        }


        function removeUser(user) {

            var uid = user.uid;
            $.messager.confirm('提示', '确定要删除这条记录吗？', function (r) {
                if (r) {
                    $.ajax({
                        url: "/user/deleteUser",
                        dataType: "json",
                        type: "post",
                        data: {
                            uid: uid
                        },
                        error: function () {
                            alert("系统错误!");
                        },
                        //若Ajax处理成功后的回调函数，text是返回的页面信息
                        success: function (data) {
                            $.messager.confirm("提示", data.msg);
                            $("#userTable").datagrid("reload");
                        }
                    });
                }
            });
        }


        /**
         *open edit div
         */
        function openEditDiv(user) {


            $("#edit_uid").val(user.uid);

            $("#edit_name").textbox("setText", user.name);
            $("#edit_name").textbox("setValue", user.name);

            $("#edit_username").textbox("setText", user.username);
            $("#edit_username").textbox("setValue", user.username);

            $("#edit_password").textbox("setText", user.password);
            $("#edit_password").textbox("setValue", user.password);

            $("#edit_tel").textbox("setText", user.tel);
            $("#edit_tel").textbox("setValue", user.tel);

            $("#edit_remark").textbox("setText", user.remark);
            $("#edit_remark").textbox("setValue", user.remark);

            $('#editDiv').window('open');  // open a window
            $('#editDiv').window('center');
        }

        /**
         * close edit div
         */
        function cancelEditDiv() {
            $('#editDiv').window('close');
        }

        /**
         * commit edit form
         */
        function editUser() {

            //表单验证
            if (!$("#editForm").form('validate')) {
                return false;
            }

            $('#editDiv').window('close');

            $.messager.confirm('提示', '确定修改用户？', function (r) {
                if (r) {

                    $("#editForm").ajaxSubmit(function (resultJson) {
                        //回调操作
                        $.messager.confirm("提示", resultJson.msg);
                        $("#userTable").datagrid("reload");
                        $('#editDiv').window('close');
                    });

                }
            });
        }


        /**
         * freeze user
         */
        function freezeUser() {
            var user = $("#userTable").datagrid("getSelected");
            if (user == null) {
                $.messager.confirm('提示', '请选中一个用户进行操作!');
                return;
            }
            $.messager.confirm('提示', '确定冻结/解冻用户？', function (r) {
                if (r) {

                    var uid = user.uid;
                    var status = user.status;
                    $.ajax({
                        url: "/user/freezeUser",
                        dataType: "json",
                        type: "post",
                        data: {
                            uid: uid,
                            status: status
                        },
                        error: function () {
                            alert("系统错误!");
                        },
                        //若Ajax处理成功后的回调函数，text是返回的页面信息
                        success: function (data) {
                            $.messager.confirm("提示", data.msg);
                            $("#userTable").datagrid("reload");
                        }
                    });
                }
            });
        }


        //----------------------------------------------------------------积分管理

        function openIntegralDiv(data) {

            $('#integralDiv').window('open');  // open a window
            $('#integralDiv').window('center');
            $('#point_type').combobox('select', '1');
            $('#point_msg').val('');
            $('#point_quentity').numberbox('setText', '');
            $('#point_quentity').numberbox('setValue', '');
            $('#point_uid').val(data.uid);

        }

        function cancelIntegralDiv() {
            $('#integralDiv').window('close');
        }

        function submitIntegral() {

            //表单验证
            if (!$("#issueForm").form('validate')) {
                return false;
            }


            var quentity = $('#point_quentity').numberbox('getValue');

            if (quentity <= 0) {
                $.messager.confirm("提示", "请输入正确的数值！");
                return false;
            }

            var type = $('#point_type').combobox("getValue");
            if (type == 2) {
               var newquentity = quentity * -1;
                $('#point_quentity').numberbox('setValue', newquentity);
            }

            $("#integralForm").ajaxSubmit(function (resultJson) {
                //回调操作
                $.messager.confirm("提示", resultJson.msg);
                $("#userTable").datagrid("reload");
                $('#integralDiv').window('close');
            });

        }

        //---导出---
        function exportUser() {
            var url="/user/exportUser";
            window.open(url);
        }
    </script>
</head>
<body>
<div id="toolBar">
    <br/>
    用户名：<input class="easyui-textbox" id="search_username" type="text"/>
    昵称：<input class="easyui-textbox" id="search_name" type="text"/>
    <a class="easyui-linkbutton" onclick="searchUser()" data-options="iconCls:'icon-search'"
       style="margin:5px 5px 5px 0px;">查询</a>
    <a class="easyui-linkbutton" onclick="exportUser()" data-options="iconCls:'icon-redo'"
       style="margin:5px 5px 5px 0px;">导出</a>
    <br/>

    <a class="easyui-linkbutton" onclick="freezeUser()" data-options="iconCls:'icon-remove'"
       style="margin:5px 5px 5px 0px;">冻结用户</a>
    <a class="easyui-linkbutton" onclick="openMessageDiv2()" data-options="iconCls:'icon-tip'"
       style="margin:5px 5px 5px 0px;">全员推送</a>
</div>
<table id="userTable" style="width:auto;"></table>


<div id="editDiv" class="easyui-window" title="修改用户" style="width:250px;height:220px;"
     data-options="iconCls:'icon-search',modal:true" closed="true">
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'north'" style="height:80%;">
            <form id="editForm" action="/user/updateUser" method="post">
                <input id="edit_uid" name="uid" type="hidden"/>
                <table style="text-align: center;width: 100%;height:100%" border="0" cellpadding="2" cellspacing="0">
                    <tr>
                        <td>用户名:</td>
                        <td><input class="easyui-textbox" id="edit_username" name="username" type="text"/></td>
                    </tr>
                    <tr>
                        <td>姓名:</td>
                        <td><input class="easyui-textbox" id="edit_name" name="name" type="text"/></td>
                    </tr>
                    <tr>
                        <td>密码:</td>
                        <td><input class="easyui-textbox" id="edit_password" name="password" type="text"/></td>
                    </tr>
                    <tr>
                        <td>手机号:</td>
                        <td><input class="easyui-numberbox" id="edit_tel" name="tel" type="text" required="true"
                                   validType="length[1,11]"/></td>
                        <%--<td><input class="easyui-validatebox" id="edit_tel" name="tel" type="text" data-options="required:true,validType:'Mobile'" /></td>--%>
                    </tr>
                    <tr>
                        <td>备注:</td>
                        <td><input class="easyui-textbox" id="edit_remark" name="remark" type="text"/></td>
                    </tr>
                </table>
            </form>
        </div>
        <div data-options="region:'center'" style="text-align:center;height:20%">
            <a class="easyui-linkbutton" onclick="editUser()" data-options="iconCls:'icon-ok'"
               style="margin: 5px">确定</a>
            <a class="easyui-linkbutton" onclick="cancelEditDiv()" data-options="iconCls:'icon-cancel'"
               style="margin: 5px">取消</a>
        </div>
    </div>
</div>

<div id="messageDiv" class="easyui-window" title="发送信息" style="width:250px;height:220px;"
     data-options="iconCls:'icon-search',modal:true" closed="true">
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'north'" style="height:80%;">
            <form id="messageForm" action="/message/sendMessage" method="post">
                <input type="hidden" name="uid" id="message_udi"/>
                <textarea name="content" type="text" style="width: 100%;height: 100%" id="message_content"></textarea>
            </form>
        </div>
        <div data-options="region:'center'" style="text-align:center;height:20%">
            <a class="easyui-linkbutton" onclick="submitMessage()" data-options="iconCls:'icon-ok'"
               style="margin: 5px">确定</a>
            <a class="easyui-linkbutton" onclick="cancelMessageDiv()" data-options="iconCls:'icon-cancel'"
               style="margin: 5px">取消</a>
        </div>
    </div>
</div>


<div id="integralDiv" class="easyui-window" title="积分管理" style="width:280px;height:220px;"
     data-options="iconCls:'icon-search',modal:true" closed="true">
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'north'" style="height:80%;">
            <form id="integralForm" action="/point/modifyPoint" method="post">
                <input type="hidden" name="uid" id="point_uid"/>
                <table style="text-align: center;width: 100%;height:100%" border="0" cellpadding="2" cellspacing="0">
                    <tr>
                        <td>类型:</td>
                        <td><select id="point_type" class="easyui-combobox" style="width: 145px;"
                                    required="required" editable="false">
                            <option value="1">增加</option>
                            <option value="2">扣除</option>
                        </select>
                        </td>
                    </tr>
                    <tr>
                        <td>数值:</td>
                        <td><input class="easyui-numberbox" id="point_quentity" name="quentity" type="text"
                                   required="required"/></td>
                    </tr>
                    <tr>
                        <td>详情:</td>
                        <td><textarea name="pointMsg" type="text" style="width: 90%;height: 100%" id="point_msg"></textarea>
                        </td>
                    </tr>
                </table>


            </form>
        </div>
        <div data-options="region:'center'" style="text-align:center;height:20%">
            <a class="easyui-linkbutton" onclick="submitIntegral()" data-options="iconCls:'icon-ok'"
               style="margin: 5px">确定</a>
            <a class="easyui-linkbutton" onclick="cancelIntegralDiv()" data-options="iconCls:'icon-cancel'"
               style="margin: 5px">取消</a>
        </div>
    </div>
</div>


</body>
</html>
