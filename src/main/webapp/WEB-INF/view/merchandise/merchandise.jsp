<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2016/1/20
  Time: 10:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
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
//            $("#search_brandId").combobox("reload", "/dict/listDidAndDicName?type=2");
//            $("#search_sortId").combobox("reload", "/dict/listDidAndDicName?type=1");
//            $("#search_seriesId").combobox("reload", "/dict/listDidAndDicName?type=3");

            $("#search_sortId").combobox("reload", "/dict/listDidAndDicName?type=1");
            $("#search_brandId").combobox("readonly", true);
            $("#search_seriesId").combobox("readonly", true);

        });

        function initTable() {
            $("#merchandise").datagrid({
                        url: "/merchandise/listMerchandise",
                        method: "post",
                        idField: "psid",
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
                                title: "psid",
                                field: "psid",
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
                                title: "pid",
                                field: "pid",
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
                                title: "产品名称",
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
                                title: "库存",
                                field: "stock",
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
                                title: "产品类型",
                                field: "ptype",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else if (data == 1) {
                                        return "特价产品";
                                    } else if (data == 2) {
                                        return "推荐产品";
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
                                }

                                ,
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
                                }

                                ,
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
                                }

                                ,
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
                                }

                                ,
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
                                }

                                ,
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
                                title: "发布日期",
                                field: "saledateString",
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
                                        return "<input style='background:-moz-linear-gradient(top,#ffffff,#eeeeee);border-radius: 4px;border:1px #cccccc solid;' value='修改' type='button' onclick='openEditDiv(" + JSON.stringify(rowData) + ")'/> <input style='background:-moz-linear-gradient(top,#ffffff,#eeeeee);border-radius: 4px;border:1px #cccccc solid;' value='删除' type='button' onclick='removeMerchandise(" + JSON.stringify(rowData) + ")'/>";
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

        //        var firstOpenSearchDiv = true;
        //
        //        function openSearchDiv() {
        //            $("#search_productName").textbox("setText", "");
        //            $("#search_psid").textbox("setText", "");
        //            $("#search_pType").combobox("clear");
        //            $("#search_brandId").combobox("clear");
        //            $("#search_sortId").combobox("clear");
        //            $("#search_seriesId").combobox("clear");
        //            if (firstOpenSearchDiv) {
        //                $("#search_brandId").combobox("reload", "/dict/listDidAndDicName?type=2");
        //                $("#search_sortId").combobox("reload", "/dict/listDidAndDicName?type=1");
        //                $("#search_seriesId").combobox("reload", "/dict/listDidAndDicName?type=3");
        //                firstOpenSearchDiv = false;
        //            }
        //            $('#win').window('open');  // open a window
        //            $('#win').window('center');
        //
        //        }
        //
        //        function cancelSearchDiv() {
        //            $('#win').window('close');
        //        }

        /**
         * search
         */
        function searchMerchandise() {

            $('#win').window('close');
            var psid = $("#search_psid").textbox("getText");
            var productName = $("#search_productName").textbox("getText");
            var pType = $("#search_pType").combobox("getValue");
            var brandId = $("#search_brandId").combobox("getValue");
            var seriesId = $("#search_seriesId").combobox("getValue");
            var sortId = $("#search_sortId").combobox("getValue");


            $("#merchandise").datagrid({
                url: "/merchandise/listMerchandise",
                queryParams: {
                    psid: psid,
                    productName: productName,
                    pType: pType,
                    brandId: brandId,
                    seriesId: seriesId,
                    sortId: sortId
                }
            });

        }


        function removeMerchandise(data) {
//            var data = $("#merchandise").datagrid("getSelected");
//            if (data == null) {
//                $.messager.confirm('提示', '请选中一条记录进行操作!');
//                return;
//            }
            var psid = data.psid;
            $.messager.confirm('提示', '确定要删除这条记录吗？', function (r) {
                if (r) {
                    $.ajax({
                        url: "/merchandise/removeMerchandise",
                        dataType: "json",
                        type: "post",
                        data: {
                            psid: psid
                        },
                        error: function () {
                            alert("系统错误!");
                        },
                        success: function (data) {
                            $.messager.confirm("提示", data.msg);
                            $("#merchandise").datagrid("reload");
                        }
                    });
                }
            });
        }


        var firstOpenEditDiv = true;

        var isSubmitAdd = false;
        /**
         * open add or edit div
         */
        function openEditDiv(data) {

//
//            var data = $("#merchandise").datagrid("getSelected");
//            if (data == null) {
//                $.messager.confirm('提示', '请选中一条记录进行操作!');
//                return;
//            }

            if (firstOpenEditDiv) {
                $("#edit_brandId").combobox("reload", "/dict/listDidAndDicName?type=2");
                $("#edit_sortId").combobox("reload", "/dict/listDidAndDicName?type=1");
                $("#edit_seriesId").combobox("reload", "/dict/listDidAndDicName?type=3");
                firstOpenEditDiv = false;
            }


            $("#edit_psid").val(data.psid);
            if (data.productname == null)
                $("#edit_name").text('空');
            else
                $("#edit_name").text(data.productname);
            $("#edit_price").textbox("setText", data.price);
            $("#edit_price").textbox("setValue", data.price);
            $("#edit_stock").textbox("setText", data.stock);
            $("#edit_stock").textbox("setValue", data.stock);
            $("#edit_sn").textbox("setText", data.sn);
            $("#edit_sn").textbox("setValue", data.sn);
//            $("#edit_pType").combobox("select", data.ptype);
//            $("#edit_brandId").combobox("select", data.brandid);
//            $("#edit_sortId").combobox("select", data.sortid);
//            $("#edit_seriesId").combobox("select", data.seriesid);


            $('#editDiv').window('open');  // open a window
            $('#editDiv').window('center');
        }

        /**
         * close add div
         */
        function cancelEditDiv() {
            $('#editDiv').window('close');
        }

        /**
         * add a new dic
         */
        function editProduct() {
            $('#editDiv').window('close');

//            var productName = $("#edit_productname").textbox("getText");
//            var price = $("#edit_price").textbox("getText");
//            var brandId = $("#edit_brandId").combobox("getValue");
//            var seriesId = $("#edit_seriesId").combobox("getValue");
//            var pType = $("#edit_sortId").combobox("getValue");
//            var pid = $("#edit_pid").val();
//            var url;
//            var msg;
            $.messager.confirm('提示', "确定修改记录？", function (r) {
                if (r) {
                    $("#editForm").ajaxSubmit(function (resultJson) {
                        //回调操作
                        $.messager.confirm("提示", resultJson.msg);
                        $("#merchandise").datagrid("reload");
                    });

                }
            });

        }


        function clearSearchBrandId() {
            $("#search_brandId").combobox("clear");
        }
        function clearSearchSeriesId() {
            $("#search_seriesId").combobox("clear");
        }

        function reloadSearchBrandId() {
            var parentId = $("#search_sortId").combobox("getValue");
            $("#search_brandId").combobox("reload", "/dict/listDidAndDicName?type=2&parentId=" + parentId);
            $("#search_brandId").combobox("readonly", false);
        }

        function reloadSearchSeriesId() {
            var parentId = $("#search_brandId").combobox("getValue");
            $("#search_seriesId").combobox("reload", "/dict/listDidAndDicName?type=3&parentId=" + parentId);
            $("#search_seriesId").combobox("readonly", false);
        }

    </script>

</head>
<body>
<div id="toolBar">
    <br/>
    编号：<input class="easyui-textbox" id="search_psid" name="psid" type="text"/>
    名称：<input class="easyui-textbox" id="search_productName" name="productName" type="text"/>
    产品类型：<select id="search_pType" class="easyui-combobox" name="pType" style="width: 145px">
    <option value="1">特价产品</option>
    <option value="2">推荐产品</option>
</select>
    <br/><br/>
    品类：<input id="search_sortId" name="sortId" class="easyui-combobox"
              data-options="valueField: 'did',textField: 'dictname',
              onSelect:function(selection){
              reloadSearchBrandId();
              },
              onChange:function(newValue,oldValue){
              clearSearchBrandId();
              }"/>
    品牌：<input id="search_brandId" name="brandId" class="easyui-combobox"
              data-options="valueField: 'did',textField: 'dictname',
              onSelect:function(selection){
              reloadSearchSeriesId();
              },
              onChange:function(newValue,oldValue){
              clearSearchSeriesId();
              }"/>
    系列：<input id="search_seriesId" name="seriesId" class="easyui-combobox"
              data-options="valueField: 'did',textField: 'dictname'"/>
    &nbsp<a class="easyui-linkbutton" onclick="searchMerchandise()" data-options="iconCls:'icon-search'"
            style="margin:5px 5px 5px 0px;">查询</a>
    <br/>

    <%--<a class="easyui-linkbutton" onclick="openEditDiv()" data-options="iconCls:'icon-edit'"--%>
    <%--style="margin:5px 5px 5px 0px;">修改</a>--%>
    <%--<a class="easyui-linkbutton" onclick="removeProduct()" data-options="iconCls:'icon-remove'"--%>
    <%--style="margin:5px 5px 5px 0px;">删除</a>--%>
</div>
<table id="merchandise" style="width:auto;"></table>

<%--<div id="win" class="easyui-window" title="查询" style="width:260px;height:280px;"--%>
<%--data-options="iconCls:'icon-search',modal:true" closed="true">--%>
<%--<div class="easyui-layout" data-options="fit:true">--%>
<%--<div data-options="region:'north'" style="height:85%;">--%>
<%--<form method="post" id="searchForm" action="/merchandise/listMerchandise">--%>
<%--<table style="text-align: center;width: 100%;height:100%" border="0" cellpadding="2" cellspacing="0">--%>

<%--<tr>--%>
<%--<td>ID:</td>--%>
<%--<td><input class="easyui-textbox" id="search_psid" name="psid" type="text"/></td>--%>
<%--</tr>--%>
<%--<tr>--%>
<%--<td>名称:</td>--%>
<%--<td><input class="easyui-textbox" id="search_productName" name="productName" type="text"/></td>--%>
<%--</tr>--%>
<%--<tr>--%>
<%--<td>产品类型:</td>--%>
<%--<td><select id="search_pType" class="easyui-combobox" name="pType" style="width: 145px">--%>
<%--<option value="1">特价产品</option>--%>
<%--<option value="2">推荐产品</option>--%>
<%--</select>--%>
<%--</td>--%>
<%--</tr>--%>
<%--<tr>--%>
<%--<td>品牌:</td>--%>
<%--<td><input id="search_brandId" name="brandId" class="easyui-combobox"--%>
<%--data-options="valueField: 'did',textField: 'dictname'"/>--%>
<%--</td>--%>
<%--</tr>--%>
<%--<tr>--%>
<%--<td>品类:</td>--%>
<%--<td><input id="search_sortId" name="sortId" class="easyui-combobox"--%>
<%--data-options="valueField: 'did',textField: 'dictname'"/>--%>
<%--</td>--%>
<%--</tr>--%>
<%--<tr>--%>
<%--<td>系列:</td>--%>
<%--<td><input id="search_seriesId" name="seriesId" class="easyui-combobox"--%>
<%--data-options="valueField: 'did',textField: 'dictname'"/>--%>
<%--</td>--%>
<%--</tr>--%>
<%--</table>--%>
<%--</form>--%>
<%--</div>--%>
<%--<div data-options="region:'center'" style="text-align:center;height:15%">--%>
<%--<a class="easyui-linkbutton" onclick="searchMerchandise()" data-options="iconCls:'icon-ok'"--%>
<%--style="margin: 5px">确定</a>--%>
<%--<a class="easyui-linkbutton" onclick="cancelSearchDiv()" data-options="iconCls:'icon-cancel'"--%>
<%--style="margin: 5px">取消</a>--%>
<%--</div>--%>

<%--</div>--%>
<%--</div>--%>

<div id="editDiv" class="easyui-window" title="修改" style="width:260px;height: 180px;"
     data-options="iconCls:'icon-search',modal:true" closed="true">
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'north'" style="height:75%;">
            <form id="editForm" method="POST" enctype="multipart/form-data" action="/merchandise/editMerchandise">
                <table style="text-align: center;width: 100%;height:100%" border="0" cellpadding="2" cellspacing="0">
                    <input id="edit_psid" name="psid" type="hidden"/>
                    <tr>
                        <td>名称:</td>
                        <td><label id="edit_name"></label></td>
                    </tr>
                    <tr>
                        <td>价格:</td>
                        <td><input class="easyui-numberbox" id="edit_price" name="price" type="text" data-options="min:0.01,precision:2"/></td>
                    </tr>
                    <tr>
                        <td>库存:</td>
                        <td><input class="easyui-numberbox" id="edit_stock" name="stock" type="text"/></td>
                    </tr>
                    <tr>
                        <td>排序:</td>
                        <td><input class="easyui-numberbox" id="edit_sn" name="sn" type="text"/></td>
                    </tr>
                    <%--<tr>--%>
                    <%--<td>产品类型:</td>--%>
                    <%--<td><select id="edit_pType" class="easyui-combobox" name="pType" style="width: 145px">--%>
                    <%--<option value="1">特价产品</option>--%>
                    <%--<option value="2">推荐产品</option>--%>
                    <%--</select>--%>
                    <%--</td>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                    <%--<td>品牌:</td>--%>
                    <%--<td><input id="edit_brandId" name="brandId" class="easyui-combobox"--%>
                    <%--data-options="valueField: 'did',textField: 'dictname'"/>--%>
                    <%--</td>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                    <%--<td>品类:</td>--%>
                    <%--<td><input id="edit_sortId" name="sortId" class="easyui-combobox"--%>
                    <%--data-options="valueField: 'did',textField: 'dictname'"/>--%>
                    <%--</td>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                    <%--<td>系列:</td>--%>
                    <%--<td><input id="edit_seriesId" name="seriesId" class="easyui-combobox"--%>
                    <%--data-options="valueField: 'did',textField: 'dictname'"/>--%>
                    <%--</td>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                    <%--<td colspan="2">--%>
                    <%--&lt;%&ndash;<input type="file" id="file" name="file" style="width:90%"/>&ndash;%&gt;--%>
                    <%--<input class="easyui-filebox" name="file" style="width:90%"--%>
                    <%--data-options="--%>
                    <%--buttonText: '选择图片',--%>
                    <%--buttonAlign: 'left',--%>
                    <%--onChange:function(path){--%>
                    <%--$('#productImg').attr('src',path);--%>
                    <%--}"/>--%>
                    <%--</td>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                    <%--<td rowspan="2" colspan="2">--%>
                    <%--<img id="productImg" src=""--%>
                    <%--style="width: 200px;height:150px;"/>--%>
                    <%--</td>--%>
                    <%--</tr>--%>
                </table>
            </form>
        </div>
        <div data-options="region:'center'" style="text-align:center;height:25%">
            <a class="easyui-linkbutton" onclick="editProduct()" data-options="iconCls:'icon-ok'"
               style="margin: 5px">确定</a>
            <a class="easyui-linkbutton" onclick="cancelEditDiv()" data-options="iconCls:'icon-cancel'"
               style="margin: 5px">取消</a>
        </div>
    </div>
</div>


</body>
</html>
