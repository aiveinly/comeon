<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2016/2/14
  Time: 11:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>预订单列表</title>

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
            $("#order").datagrid({
                        url: "/preOrder/listPreOrder?oid=${oid}",
                        method: "post",
                        idField: "oid",
                        fitColumns: true,
                        fit: true,
                        striped: true,
//                        pagination: true,
                        rownumbers: true,
                        singleSelect: true,
                        autoRowHeight: true,
                        toolbar: toolBar,
//                        pageSize: 10,
//                        pageList: [10, 15, 20, 25, 30],
                        frozenColumns: [[
                            {
                                title: "预订单号",
                                field: "poid",
                                width: 50,
                                hidden: true
                            }]],
                        columns: [[
                            {
                                title: "qid",
                                field: "qid",
                                width: 10,
                                hidden: true
                            },
                            {
                                title: "品名",
                                field: "productname",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                },
                                width: 10
                            }, {
                                title: "价格",
                                field: "price",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                },
                                width: 10
                            }, {
                                title: "数量",
                                field: "qty",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                },
                                width: 10
                            }, {
                                title: "品类",
                                field: "sortid",
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
                                title: "品类",
                                field: "sortname",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                },
                                width: 10
                            }, {
                                title: "品牌",
                                field: "brandid",
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
                                title: "品牌",
                                field: "brandname",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                },
                                width: 10
                            }, {
                                title: "系列",
                                field: "seriesid",
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
                                title: "系列",
                                field: "seriesname",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                },
                                width: 10
                            }, {
                                title: "型号",
                                field: "modelname",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                },
                                width: 10
                            }, {
                                title: "备注",
                                field: "remark",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else {
                                        return data;
                                    }
                                },
                                width: 10
                            }, {
                                title: "货期",
                                field: "deliverytime",
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


        function openAddDiv(isAdd) {
            if (isAdd) {
                var inputs = $("#addForm input.easyui-textbox,#addForm .easyui-numberbox");
                inputs.textbox("setText", "");
                inputs.textbox("setValue", "");
                $('#add_remark').val("");
                $("#addForm").attr("action", "/preOrder/addPreOrder");
                $('#addDiv').window({title: "增加"});
                $('#addDiv').window('open');  // open a window
                $('#addDiv').window('center');

            } else {

                var data = $("#order").datagrid("getSelected");
                if (data == null) {
                    $.messager.confirm('提示', '请选中一条记录进行操作!');
                    return;
                }

                $("#addForm").attr("action", "/preOrder/editPreOrder");
                $('#addDiv').window({title: "修改"});

                $('#add_productName').textbox('setText', data.productname);
                $('#add_productName').textbox('setValue', data.productname);
                $('#add_price').textbox('setText', data.price);
                $('#add_price').textbox('setValue', data.price);
                $('#add_qty').textbox('setText', data.qty);
                $('#add_qty').textbox('setValue', data.qty);
                $('#add_sortName').textbox('setText', data.sortname);
                $('#add_sortName').textbox('setValue', data.sortname);
                $('#add_brandName').textbox('setText', data.brandname);
                $('#add_brandName').textbox('setValue', data.brandname);
                $('#add_seriesName').textbox('setText', data.seriesname);
                $('#add_seriesName').textbox('setValue', data.seriesname);
                $('#add_modelName').textbox('setText', data.modelname);
                $('#add_modelName').textbox('setValue', data.modelname);
                $('#add_deliveryTime').textbox('setText', data.deliverytime);
                $('#add_deliveryTime').textbox('setValue', data.deliverytime);
//                $('#add_remark').textbox('setText', data.remark);
//                $('#add_remark').textbox('setValue', data.remark);
                $('#add_remark').val(data.remark);
                $('#add_poid').val(data.poid);
//                alert(data.poid);

                $('#addDiv').window('open');  // open a window
                $('#addDiv').window('center');
            }
        }
        function cancelAddDiv() {
            $('#addDiv').window('close');
        }

        function addPreOrder() {
            $('#addDiv').window('close');

            $.messager.confirm('提示', "确定添加或修改预订单？", function (r) {
                if (r) {
                    $("#addForm").ajaxSubmit(function (resultJson) {
                        //回调操作
                        $.messager.confirm("提示", resultJson.msg);
                        $("#order").datagrid("reload");
                    });

                }
            });
        }

        <%--function showRequirment() {--%>
        <%--var rid = "${rid}";--%>

        <%--window.parent.addTab('/req/index?rid=' + rid, '需求订单');--%>
        <%--}--%>


        function removePreOrder() {
            var data = $("#order").datagrid("getSelected");
            if (data == null) {
                $.messager.confirm('提示', '请选中一条记录进行操作!');
                return;
            }

            var poid = data.poid;
            $.messager.confirm('提示', '确定要删除这条记录吗？', function (r) {
                if (r) {
                    $.ajax({
                        url: "/preOrder/removePreOrder",
                        dataType: "json",
                        type: "post",
                        data: {
                            poid: poid
                        },
                        error: function () {
                            alert("系统错误!");
                        },
                        success: function (data) {
                            $.messager.confirm("提示", data.msg);
                            $("#order").datagrid("reload");
                        }
                    });
                }
            });
        }


        function showBaoJiaDan() {
//            var data = $("#order").datagrid("getSelected");
//            if (data == null) {
//                $.messager.confirm('提示', '请选中一条记录进行操作!');
//                return;
//            }


            $.ajax({
                url: "/quoquotation/existBaojiadan",
                dataType: "json",
                type: "post",
                data: {
                    oid: "${oid}"
                },
                error: function () {
                    alert("系统错误!");
                },
                success: function (data) {
                    if (data.success == 2) {
                        $.messager.confirm("提示", data.msg, function (r) {
                            if (r) {
                                window.parent.addTab("/quoquotation/index?oid=${oid}", '报价单');
                            }
                        });
                    } else if (data.success == 1) {
                        window.parent.addTab("/quoquotation/index?oid=${oid}", '报价单');
                    } else {
                        $.messager.confirm("提示", data.msg);
                    }
                }
            });
        }

    </script>


</head>
<body>

<div id="toolBar">

    <h3>订单编号:${oid}</h3>
    <a class="easyui-linkbutton" onclick="openAddDiv(true)" data-options="iconCls:'icon-add'"
       style="margin:5px 5px 5px 0px;">新增</a>
    <a class="easyui-linkbutton" onclick="openAddDiv(false)" data-options="iconCls:'icon-edit'"
       style="margin:5px 5px 5px 0px;">修改</a>
    <a class="easyui-linkbutton" onclick="removePreOrder()" data-options="iconCls:'icon-remove'"
       style="margin:5px 5px 5px 0px;">删除</a>
    <a class="easyui-linkbutton" href="/req/index?rid=${rid}" target='_blank' data-options="iconCls:'icon-search'"
       style="margin:5px 5px 5px 0px;">查看需求订单</a>
    <a class="easyui-linkbutton" onclick="showBaoJiaDan()" data-options="iconCls:'icon-search'"
       style="margin:5px 5px 5px 0px;">查看报价单</a>
</div>
<table id="order" style="width:auto;"></table>


<div id="addDiv" class="easyui-window" title="添加" style="height:400px;width:260px;"
     data-options="iconCls:'icon-search',modal:true" closed="true">
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'north'" style="height:90%;">
            <form id="addForm" method="POST">
                <table style="text-align: center;width: 100%;height:100%" border="0" cellpadding="2" cellspacing="0">
                    <input id="add_oid" name="oid" value="${oid}" type="hidden"/>
                    <input id="add_rid" name="rid" value="${rid}" type="hidden"/>
                    <input id="add_poid" name="poid" type="hidden"/>
                    <tr>
                        <td>名称:</td>
                        <td><input class="easyui-textbox" id="add_productName" name="productName" type="text"/></td>
                    </tr>
                    <tr>
                        <td>价格:</td>
                        <td><input class="easyui-textbox" id="add_price" name="price" type="text"/></td>
                    </tr>
                    <tr>
                        <td>数量:</td>
                        <td><input class="easyui-numberbox" id="add_qty" name="qty" type="text"/></td>
                    </tr>
                    <tr>
                        <td>品类:</td>
                        <td><input class="easyui-textbox" id="add_sortName" name="sortName" type="text"/></td>
                    </tr>
                    <tr>
                        <td>品牌:</td>
                        <td><input class="easyui-textbox" id="add_brandName" name="brandName" type="text"/></td>
                    </tr>
                    <tr>
                        <td>系列:</td>
                        <td><input class="easyui-textbox" id="add_seriesName" name="seriesName" type="text"/></td>
                    </tr>
                    <tr>
                        <td>型号:</td>
                        <td><input class="easyui-textbox" id="add_modelName" name="modelName" type="text"/></td>
                    </tr>
                    <tr>
                        <td>货期:</td>
                        <td><input class="easyui-textbox" id="add_deliveryTime" name="deliveryTime" type="text"/></td>
                    </tr>
                    <tr>
                        <td>备注:</td>
                        <td><textarea id="add_remark" name="remark" style="width: 150px;"></textarea></td>
                    </tr>
                </table>
            </form>
        </div>
        <div data-options="region:'center'" style="text-align:center;">
            <a class="easyui-linkbutton" onclick="addPreOrder()" data-options="iconCls:'icon-ok'"
               style="margin: 5px">确定</a>
            <a class="easyui-linkbutton" onclick="cancelAddDiv()" data-options="iconCls:'icon-cancel'"
               style="margin: 5px">取消</a>
        </div>
    </div>
</div>

</body>

</html>
