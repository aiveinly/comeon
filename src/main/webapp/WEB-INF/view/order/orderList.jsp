<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2016/2/14
  Time: 11:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>订单列表</title>

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
            $("#order").datagrid({
                onSortColumn: function (sort, order) {

                }
            });
        });

        function initTable() {
            $("#order").datagrid({
                        url: "/order/listOrder",
                        method: "post",
                        idField: "oid",
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
                                field: "oid",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                },
                                width: 100
//                                checkbox: true
                            }]],
                        columns: [[
                            {
                                title: "rid",
                                field: "rid",
                                width: 10,
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
                                width: 10
                            }, {
                                title: "姓名",
                                field: "name",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                },
                                width: 5
                            }, {
                                title: "VIP",
                                field: "vip",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else if (data == 0) {
                                        return "否";
                                    } else {
                                        return "是";
                                    }
                                },
                                width: 5
                            },
                            {
                                title: "订单类型",
                                field: "ptype",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else if (data == 1) {
                                        return "特价";
                                    } else if (data == 2)  {
                                        return "推荐";
                                    } else {
                                        return "普通";
                                    }
                                },
                                width: 5

                            },

                            {
                                title: "订单状态",
                                field: "status",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else if (data == 0) {
                                        return "生成";
                                    } else if (data == 1) {
                                        return "预订单已生成";
                                    } else if (data == 2) {
                                        return "报价单已生成";
                                    } else if (data == 3) {
                                        return "报价单已确认";
                                    } else if (data == 5) {
                                        return "订单完成";
                                    } else if (data == -1) {
                                        return "订单取消";
                                    }


                                }
                                ,
                                width: 8
                            }, {
                                title: "预订单数",
                                field: "opCount",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                },
                                width: 4
                            }, {
                                title: "报价单",
                                field: "quoCount",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else if (data == 0) {
                                        return "无";
                                    } else {
                                        return "有";
                                    }
                                },
                                width: 7
                            }, {
                                title: "创建时间",
                                field: "createdateString",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                },
                                width: 10
                            },
                            {
                                title: "操作",
                                field: "operate",
                                formatter: function (data, rowData) {
                                    if (data == null) {
                                        return "<input style='background:-moz-linear-gradient(top,#ffffff,#eeeeee);border-radius: 4px;border:1px #cccccc solid;' value='详情' type='button' onclick='orderDetail(" + JSON.stringify(rowData) + ")'/><input style='background:-moz-linear-gradient(top,#ffffff,#eeeeee);border-radius: 4px;border:1px #cccccc solid;' value='完成' type='button' onclick='completeOrder(" + JSON.stringify(rowData) + ")'/><input style='background:-moz-linear-gradient(top,#ffffff,#eeeeee);border-radius: 4px;border:1px #cccccc solid;' value='删除' type='button' onclick='removeOrder(" + JSON.stringify(rowData) + ")'/>";
                                    } else {
                                        return "";
                                    }
                                },
                                width: 10
                            }
                        ]]
                    }
            )
            ;
        }


        function searchOrder() {

            $('#win').window('close');

            var oid = $("#search_oid").textbox("getValue");
            var userName = $("#search_username").textbox("getValue");
            var status = $("#search_status").combobox("getValue");

            $("#order").datagrid({
                url: "/order/listOrder",
                queryParams: {
                    oid: oid,
                    userName: userName,
                    status: status
                }
            });

        }

        /**
         * 订单详情
         */
        function orderDetail(data) {

//            var data = $("#order").datagrid("getSelected");
//            if (data == null) {
//                $.messager.confirm('提示', '请选中一条记录进行操作!');
//                return;
//            }

            var oid = data.oid;
            var rid = data.rid;
            window.parent.addTab('/preOrder/index?oid=' + oid + "&rid=" + rid, '订单详情');

        }

        function completeOrder(data){
            var oid= data.oid;
            $.messager.confirm('提示', '确定要提交这条订单吗？', function (r) {
                if (r) {
                    $.ajax({
                        url: "/order/completeOrder",
                        dataType: "json",
                        type: "post",
                        data: {
                            oid: oid
                        },
                        error: function () {
                            $.messager.confirm("提示","系统错误");
                        },
                        success: function (data) {
                            $.messager.confirm("提示", data.msg);
                            $("#order").datagrid("reload");
                        }
                    });
                }
            });
        }

        //---导出---
        function exportOrder() {
            var url="/order/exportOrder";
            window.open(url);
        }

    </script>

</head>
<body>

<div id="toolBar">
    <br/>
    订单状态：
    <select id="search_status" class="easyui-combobox" name="status" style="width: 145px;">
        <option value="">全部</option>
        <option value="0">生成</option>
        <option value="1">预订单已生成</option>
        <option value="2">报价单已生成</option>
        <option value="3">报价单已确认</option>
        <option value="5">订单完成</option>
        <option value="-1">订单取消</option>
    </select>
    编号：
    <input class="easyui-textbox" id="search_oid" name="oid" type="text"/>
    用户名：
    <input class="easyui-textbox" id="search_username" name="username" type="text"/>
    <a class="easyui-linkbutton" onclick="searchOrder()" data-options="iconCls:'icon-search'"
       style="margin:5px 5px 5px 0px;">查询</a>
    <a class="easyui-linkbutton" onclick="exportOrder()" data-options="iconCls:'icon-redo'"
       style="margin:5px 5px 5px 0px;">导出</a>
    <br/>


    <%--<a class="easyui-linkbutton" onclick="orderDetail()" data-options="iconCls:'icon-edit'"--%>
    <%--style="margin:5px 5px 5px 0px;">详情</a>--%>
    <%--<a class="easyui-linkbutton" onclick="removeOrder()" data-options="iconCls:'icon-remove'"--%>
    <%--style="margin:5px 5px 5px 0px;">删除</a>--%>
</div>
<table id="order" style="width:auto;"></table>


</body>

</html>
