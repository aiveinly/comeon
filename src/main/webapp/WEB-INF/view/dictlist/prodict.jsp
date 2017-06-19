<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2016/1/20
  Time: 10:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
            $("#dictTable").datagrid({
                        url: "/dict/proDictList?type=${type}",
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
                                title: "编号",
                                field: "did",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                }
//                                checkbox: true
                            }]],
                        columns: [[
                            {
                                title: "type",
                                field: "type",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else if (data == 1) {
                                        return "品类";
                                    } else if (data == 2) {
                                        return "品牌";
                                    } else if (data == 3) {
                                        return "系列";
                                    }
                                },
                                width: 5,
                                hidden: true
                            },
                            {
                                title: "名称",
                                field: "dictname",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                },
                                hidden: true
                            }, {
                                title: "名称",
                                field: "bindinfo",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                },
                                width: 20
                            },
                            {
                                title: "前缀",
                                field: "pre",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                },
                                width: 10
                            }
                            ,
                            {
                                title: "排序",
                                field: "sn",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                },
                                width: 10
                            }, {
                                title: "图片",
                                field: "imgurl",
                                formatter: function (data) {
                                    if (data == null || data == "") {
                                        return "";
                                    } else {
                                        return "<img  src='/static/img/img.png' style='height: 20px;width: 20px' onclick='openImg(" + JSON.stringify(data) + ")'/>";
                                    }
                                },
                                width: 10,
                                hidden: false
                            },
                            {
                                title: "操作",
                                field: "operate",
                                formatter: function (data, rowData) {
                                    if (data == null) {
                                        return "<input style='background:-moz-linear-gradient(top,#ffffff,#eeeeee);border-radius: 4px;border:1px #cccccc solid;' value='修改' type='button' onclick='openEditDiv(" + JSON.stringify(rowData) + ")'/> <input style='background:-moz-linear-gradient(top,#ffffff,#eeeeee);border-radius: 4px;border:1px #cccccc solid;' value='删除' type='button' onclick='removeDic(" + JSON.stringify(rowData) + ")'/>";
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


        //        function openSearchDiv() {
        //            $("#search_dicname").textbox("setText", "");
        //            $("#search_dicname").textbox("setValue", "");
        //            $('#win').window('open');  // open a window
        //            $('#win').window('center');
        //
        //        }
        //
        //        function cancelSearchDiv() {
        //            $('#win').window('close');
        //        }

        /**
         * search by name and username
         */
        function searchDic() {
            var dicname = $("#search_dicname").textbox("getValue");
            $("#dictTable").datagrid({
                url: "/dict/proDictList?type=${type}",
                queryParams: {
                    dicname: dicname
                }
            });
            $('#win').window('close');
        }


        function removeDic(data) {
//            var data = $("#dictTable").datagrid("getSelected");
//            if (data == null) {
//                $.messager.confirm('提示', '请选中一条记录进行操作!');
//                return;
//            }
            var did = data.did;
            $.messager.confirm('提示', '确定要删除这条记录吗？', function (r) {
                if (r) {
                    $.ajax({
                        url: "/dict/deleteDic",
                        dataType: "json",
                        type: "post",
                        data: {
                            did: did
                        },
                        error: function () {
                            alert("系统错误!");
                        },
                        success: function (data) {
                            $.messager.confirm("提示", data.msg);
                            $("#dictTable").datagrid("reload");
                        }
                    });
                }
            });
        }


        var type = ${type};
        var firstOpenAddDiv = true;

        /**
         * open add div
         //         */
        function openAddDiv() {

            if (firstOpenAddDiv) {

                if (type == 2) {
                    $("#add_parentId").combobox("reload", "/dict/listDidAndDicName?type=1");
                } else if (type == 3) {
                    $("#add_parentId1").combobox("reload", "/dict/listDidAndDicName?type=1");
                    $("#add_parentId2").combobox("readonly", true);
                }

                firstOpenAddDiv = false;

            }

            var inputs = $("#addForm .easyui-textbox,#addForm .easyui-numberbox");


            inputs.textbox("setText", "");
            inputs.textbox("setValue", "");

            $("#add_sn").textbox("setText", "99");
            $("#add_sn").textbox("setValue", "99");

            $("#add_parentId").combobox("setText", "");
            $("#add_parentId").combobox("setValue", "");

            $("#add_file").textbox("setText", "");
            $("#add_file").textbox("setValue", "");

            $('#addDiv').window('open');  // open a window
            $('#addDiv').window('center');
        }

        /**
         * close add div
         */
        function cancelAddDiv() {
            $('#addDiv').window('close');
        }


        function reloadBrandId() {
            var parentId = $("#add_parentId1").combobox("getValue");
//            alert(parentId);
            $("#add_parentId2").combobox("reload", "/dict/listDidAndDicName?type=2&parentId=" + parentId);
            $("#add_parentId2").combobox("readonly", false);
        }
        function clearBrandId() {
            $("#add_parentId2").combobox("clear");
        }

        /**
         * add a new dic
         */
        function addDic() {
            //表单验证
            if (!$("#addForm").form('validate')) {
                return false;
            }

            $('#addDiv').window('close');
            $("#addForm").attr("action", "/dict/addDic");
            $("#addForm").ajaxSubmit(function (resultJson) {
                //回调操作
                $.messager.confirm("提示", resultJson.msg);
                $("#dictTable").datagrid("reload");
            });


//            var dicname = $("#add_dicname").textbox("getValue");
//            var pre = $("#add_pre").textbox("getValue");
//            var sn = $("#add_sn").textbox("getValue");

            <%--$.messager.confirm('提示', '确定添加记录？', function (r) {--%>
            <%--if (r) {--%>
            <%--$.ajax({--%>
            <%--url: "/dict/addDic",--%>
            <%--dataType: "json",--%>
            <%--type: "post",--%>
            <%--data: {--%>
            <%--dicname: dicname,--%>
            <%--pre: pre,--%>
            <%--sn: sn,--%>
            <%--type:${type}--%>
            <%--},--%>
            <%--error: function () {--%>
            <%--alert("系统错误!");--%>
            <%--},--%>
            <%--success: function (data) {--%>
            <%--$.messager.confirm("提示", data.msg);--%>
            <%--$("#dictTable").datagrid("reload");--%>
            <%--$('#addDiv').window('close');--%>
            <%--}--%>
            <%--});--%>
            <%--}--%>
            <%--});--%>

        }


        /**
         *open edit div
         */
        function openEditDiv(data) {

//
//            var data = $("#dictTable").datagrid("getSelected");
//            if (data == null) {
//                $.messager.confirm('提示', '请选中一条记录进行操作!');
//                return;
//            }
            $("#edit_did").val(data.did);

            $("#edit_dicname").textbox("setText", data.dictname);
            $("#edit_dicname").textbox("setValue", data.dictname);

            $("#edit_pre").textbox("setText", data.pre);
            $("#edit_pre").textbox("setValue", data.pre);

            $("#edit_sn").textbox("setText", data.sn);
            $("#edit_sn").textbox("setValue", data.sn);

            $("#edit_file").textbox("setText", "");
            $("#edit_file").textbox("setValue", "");

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
            $("#editForm").attr("action", "/dict/editDic");
            $("#editForm").ajaxSubmit(function (resultJson) {
                //回调操作
                $.messager.confirm("提示", resultJson.msg);
                $("#dictTable").datagrid("reload");
            });


//            $.messager.confirm('提示', '确定修改记录？', function (r) {
//                if (r) {
//                    var dicname = $("#edit_dicname").textbox("getValue");
//                    var pre = $("#edit_pre").textbox("getValue");
//                    var sn = $("#edit_sn").textbox("getValue");
//                    var did = $("#edit_did").val();
//                    $.ajax({
//                        url: "/dict/editDic",
//                        dataType: "json",
//                        type: "post",
//                        data: {
//                            did: did,
//                            dicname: dicname,
//                            pre: pre,
//                            sn: sn
//                        },
//                        error: function () {
//                            alert("系统错误!");
//                        },
//                        //若Ajax处理成功后的回调函数，text是返回的页面信息
//                        success: function (data) {
//                            $.messager.confirm("提示", data.msg);
//                            $("#dictTable").datagrid("reload");
//                            $('#editDiv').window('close');
//                        }
//                    });
//                }
//            });

        }
        var imgurl = "http://image.comeon365.com/";

        function openImg(url) {
//            alert(imgurl + url);
            window.parent.addTab(imgurl + url, "图片");
        }


    </script>
</head>
<body>
<div id="toolBar">

    <br/>
    名称：<input class="easyui-textbox" id="search_dicname" type="text"/>
    <a class="easyui-linkbutton" onclick="searchDic()" data-options="iconCls:'icon-search'"
       style="margin:5px 5px 5px 0px;">查询</a>
    <br/>

    <a class="easyui-linkbutton" onclick="openAddDiv()" data-options="iconCls:'icon-add'"
       style="margin:5px 5px 5px 0px;">添加</a>
    <%--<a class="easyui-linkbutton" onclick="openEditDiv()" data-options="iconCls:'icon-edit'"--%>
    <%--style="margin:5px 5px 5px 0px;">修改</a>--%>
    <%--<a class="easyui-linkbutton" onclick="deleteDic()" data-options="iconCls:'icon-remove'"--%>
    <%--style="margin:5px 5px 5px 0px;">删除</a>--%>
</div>
<table id="dictTable" style="width:auto;"></table>

<%--<div id="win" class="easyui-window" title="查询" style="width:250px;height:150px;"--%>
<%--data-options="iconCls:'icon-search',modal:true" closed="true">--%>
<%--<div class="easyui-layout" data-options="fit:true">--%>
<%--<div data-options="region:'north'" style="height:65%;">--%>
<%--<table style="text-align: center;width: 100%;height:100%" border="0" cellpadding="2" cellspacing="0">--%>
<%--<tr>--%>
<%--<td>名称:</td>--%>
<%--<td><input class="easyui-textbox" id="search_dicname" type="text"/></td>--%>
<%--</tr>--%>
<%--</table>--%>
<%--</div>--%>
<%--<div data-options="region:'center'" style="text-align:center;height:35%">--%>
<%--<a class="easyui-linkbutton" onclick="searchDic()" data-options="iconCls:'icon-ok'"--%>
<%--style="margin: 5px">确定</a>--%>
<%--<a class="easyui-linkbutton" onclick="cancelSearchDiv()" data-options="iconCls:'icon-cancel'"--%>
<%--style="margin: 5px">取消</a>--%>
<%--</div>--%>

<%--</div>--%>
<%--</div>--%>

<div id="addDiv" class="easyui-window" title="添加" style="width:250px;height:230px;"
     data-options="iconCls:'icon-search',modal:true" closed="true">
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'north'" style="height:80%;">
            <form id="addForm" method="POST" enctype="multipart/form-data">
                <input name="type" value="${type}" type="hidden">
                <table style="text-align: center;width: 100%;height:100%" border="0" cellpadding="2" cellspacing="0">
                    <c:if test="${type == 2}">
                        <tr>
                            <td>
                                品类:
                            </td>
                            <td><input class="easyui-combobox" id="add_parentId" name="parentId" required="required"
                                       data-options="valueField: 'did',textField: 'dictname'"/></td>
                        </tr>
                    </c:if>
                    <c:if test="${type == 3}">
                        <tr>
                            <td>
                                品类:
                            </td>
                            <td><input class="easyui-combobox" id="add_parentId1" required="required"
                                       data-options="valueField: 'did',textField: 'dictname',
                                            onSelect:function(selection){
                                            reloadBrandId();
                                            },
                                            onChange:function(newValue,oldValue){
                                            clearBrandId();
                                        }"/></td>
                        </tr>
                        <tr>
                            <td>
                                品牌:
                            </td>
                            <td><input class="easyui-combobox" id="add_parentId2" name="parentId" required="required"
                                       data-options="valueField: 'did',textField: 'dictname'"/></td>
                        </tr>
                    </c:if>
                    <tr>
                        <td>名称:</td>
                        <td><input class="easyui-textbox" id="add_dicname" name="dicname" type="text"/></td>
                    </tr>
                    <tr>
                        <td>前缀:</td>
                        <td><input class="easyui-textbox" id="add_pre" name="pre" type="text" required="true"
                                   validType="length[1,1]"/>
                        </td>
                    </tr>
                    <tr>
                        <td>排序:</td>
                        <td><input class="easyui-numberbox" id="add_sn" name="sn" type="text"/></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <%--<input type="file" id="file" name="file" style="width:90%"/>--%>
                            <input class="easyui-filebox" id="add_file" name="file" style="width:90%"
                                   data-options="
                            buttonText: '选择图片',
                            buttonAlign: 'left'"/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <div data-options="region:'center'" style="text-align:center;height:25%">
            <a class="easyui-linkbutton" onclick="addDic()" data-options="iconCls:'icon-ok'"
               style="margin: 5px">确定</a>
            <a class="easyui-linkbutton" onclick="cancelAddDiv()" data-options="iconCls:'icon-cancel'"
               style="margin: 5px">取消</a>
        </div>
    </div>
</div>


<div id="editDiv" class="easyui-window" title="修改" style="width:250px;height:220px;"
     data-options="iconCls:'icon-search',modal:true" closed="true">
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'north'" style="height:80%;">
            <form id="editForm" method="POST" enctype="multipart/form-data">
                <input id="edit_did" name="did" type="hidden"/>
                <table style="text-align: center;width: 100%;height:100%" border="0" cellpadding="2" cellspacing="0">
                    <tr>
                        <td>名称:</td>
                        <td><input class="easyui-textbox" id="edit_dicname" name="dicname" type="text"/></td>
                    </tr>
                    <tr>
                        <td>前缀:</td>
                        <td><input class="easyui-textbox" id="edit_pre" type="text" name="pre" required="true"
                                   validType="length[1,1]"/></td>
                    </tr>
                    <tr>
                        <td>排序号:</td>
                        <td><input class="easyui-numberbox" id="edit_sn" name="sn" type="text"/></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <%--<input type="file" id="file" name="file" style="width:90%"/>--%>
                            <input class="easyui-filebox" id="edit_file" name="file" style="width:90%"
                                   data-options="
                            buttonText: '选择图片',
                            buttonAlign: 'left'"/>
                        </td>
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
</body>
</html>
