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

        var imgurl = "http://image.comeon365.com/";

        $(function () {
            initTable();

//            $("#search_brandId").combobox("reload", "/dict/listDidAndDicName?type=2");
            $("#search_sortId").combobox("reload", "/dict/listDidAndDicName?type=1");
            $("#search_brandId").combobox("readonly", true);
            $("#search_seriesId").combobox("readonly", true);
//            $("#search_seriesId").combobox("reload", "/dict/listDidAndDicName?type=3");
        });

        function initTable() {
            $("#productTable").datagrid({
                        url: "/product/listProduct",
                        method: "post",
                        idField: "pid",
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
                                title: "产品编号",
                                field: "pid",
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
                            },
                            {
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
                            }
                            ,
                            {
                                title: "产品类型",
                                field: "ptype",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "-";
                                    } else if (data == 1) {
                                        return "特价产品";
                                    } else if (data == 2) {
                                        return "推荐产品";
                                    } else if (data == 3) {
                                        return "普通产品";
                                    }
                                },
                                width: 10,
                                hidden: true
                            },
                            {
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
                            }
                            ,
                            {
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
                            }
                            ,
                            {
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
                            }
                            ,
                            {
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
                            }
                            ,
                            {
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
                                }

                                ,
                                width: 10
                            }, {
                                title: "描述",
                                field: "describemodel",
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
                                title: "产品图片",
                                field: "imgurl",
                                formatter: function (data) {
                                    if (data == null || data == "" || data == "null") {
                                        return "";
                                    } else {
                                        return "<img  src='/static/img/img.png' style='height: 20px;width: 20px' onclick='openImg(" + JSON.stringify(data) + ")'/>";
                                    }
                                },
                                width: 10,
                                hidden: false
                            },
                            {
                                title: "推荐图片",
                                field: "imgurl2",
                                formatter: function (data) {
                                    if (data == null || data == "" || data == "null") {
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
                                        return "<input style='background:-moz-linear-gradient(top,#ffffff,#eeeeee);border-radius: 4px;border:1px #cccccc solid;' value='修改' type='button' onclick='openAddDiv(false," + JSON.stringify(rowData) + ")'/> <input style='background:-moz-linear-gradient(top,#ffffff,#eeeeee);border-radius: 4px;border:1px #cccccc solid;' value='删除' type='button' onclick='removeProduct(" + JSON.stringify(rowData) + ")'/>";
                                    } else {
                                        return "";
                                    }
                                },
                                width: 20
                            },
                            {
                                title: "状态",
                                field: "isIssue",
                                formatter: function (data) {
                                    if (data == null) {
                                        return "未发布";
                                    } else {
                                        return "<font color='red'>已发布</font>";
                                    }
                                }
                                ,
                                width: 10

                            }


                        ]]
                    }
            )
            ;
        }

        /**
         * search
         */
        function searchProduct() {
            var pid = $("#search_pid").textbox("getText");
            var productName = $("#search_productname").textbox("getText");
//            var pType = $("#search_pType").combobox("getValue");
            var brandId = $("#search_brandId").combobox("getValue");
            var seriesId = $("#search_seriesId").combobox("getValue");
            var sortId = $("#search_sortId").combobox("getValue");


            $("#productTable").datagrid({
                url: "/product/listProduct",
                queryParams: {
                    pid: pid,
                    productName: productName,
//                    pType: pType,
                    brandId: brandId,
                    seriesId: seriesId,
                    sortId: sortId
                }
            });
            $('#win').window('close');
        }


        function removeProduct(data) {

            var pid = data.pid;
            $.messager.confirm('提示', '确定要删除这条记录吗？', function (r) {
                if (r) {
                    $.ajax({
                        url: "/product/removeProduct",
                        dataType: "json",
                        type: "post",
                        data: {
                            pid: pid
                        },
                        error: function () {
                            alert("系统错误!");
                        },
                        success: function (data) {
                            $.messager.confirm("提示", data.msg);
                            $("#productTable").datagrid("reload");
                        }
                    });
                }
            });
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

        function reloadBrandId() {
            var parentId = $("#add_sortId").combobox("getValue");
            $("#add_brandId").combobox("reload", "/dict/listDidAndDicName?type=2&parentId=" + parentId);
            $("#add_brandId").combobox("readonly", false);
        }

        function reloadSeriesId() {
            var parentId = $("#add_brandId").combobox("getValue");
            $("#add_seriesId").combobox("reload", "/dict/listDidAndDicName?type=3&parentId=" + parentId);
            $("#add_seriesId").combobox("readonly", false);
        }

        function clearBrandId() {
            $("#add_brandId").combobox("clear");
        }

        function clearSeriesId() {
            $("#add_seriesId").combobox("clear");
        }
        function clearSearchBrandId() {
            $("#search_brandId").combobox("clear");
        }
        function clearSearchSeriesId() {
            $("#search_seriesId").combobox("clear");
        }

        var firstOpenAddDiv = true;

        var isSubmitAdd = false;
        /**
         * open add or edit div
         */
        function openAddDiv(isAdd, data) {

            isSubmitAdd = true;

            if (firstOpenAddDiv) {
                $("#add_sortId").combobox("reload", "/dict/listDidAndDicName?type=1");
                firstOpenAddDiv = false;
            }

            $("#add_file").textbox("setText", "");
            $("#add_file").textbox("setValue", "");
            $("#add_file2").textbox("setText", "");
            $("#add_file2").textbox("setValue", "");
            if (isAdd) {
                $('#addDiv').window({
                    title: "添加"
                });
                $('#addDiv').css({"height": "310px"});
                $("#productImg").css({"display": "none"});
                $("#productImg2").css({"display": "none"});
                $("#add_productName").textbox("setText", "");
                $("#add_price").textbox("setText", "");
                $("#add_describemodel").val('');
//                $("#add_pType").combobox("clear");
                $("#add_brandId").combobox("clear");
                $("#add_sortId").combobox("clear");
                $("#add_seriesId").combobox("clear");


            } else {
                isSubmitAdd = false;


                $('#addDiv').window({
                    title: "修改"
                });
                $("#productImg").css({"display": "block"});
                $("#productImg").attr("src", imgurl + data.imgurl);
                $("#productImg2").css({"display": "block"});
                $("#productImg2").attr("src", imgurl + data.imgurl2);

                //清空图片的标记, 默认为0 不清空, 如果为1 就清空
                $("#productImg_").val(0);
                $("#recommandImg_").val(0);

                $('#addDiv').css({"height": "400px"});
                $("#add_pid").val(data.pid);

                $("#add_productName").textbox("setText", data.productname);
                $("#add_productName").textbox("setValue", data.productname);
                $("#add_price").textbox("setText", data.price);
                $("#add_price").textbox("setValue", data.price);
                $("#add_describemodel").val(data.describemodel);
//                $("#add_pType").combobox("select", data.ptype);

//                $("#add_sortId").combobox("reload", "/dict/listDidAndDicName?type=1");
//                $("#add_brandId").combobox("reload", "/dict/listDidAndDicName?type=2");
//                $("#add_seriesId").combobox("reload", "/dict/listDidAndDicName?type=3");

                $("#add_sortId").combobox("select", data.sortid);
                $("#add_brandId").combobox("select", data.brandid);
                $("#add_seriesId").combobox("select", data.seriesid);

            }

            $("#add_brandId").combobox("readonly", true);
            $("#add_seriesId").combobox("readonly", true);


            $('#addDiv').window('open');  // open a window
            $('#addDiv').window('center');
        }

        /**
         * close add div
         */
        function cancelAddDiv() {
            $('#addDiv').window('close');
        }

        /**
         * add a new dic
         */
        function addProduct() {

            if (!$("#addForm").form('validate')) {
                return false;
            }


            $('#addDiv').window('close');

            var url;
            var msg;
            if (isSubmitAdd) {
                url = "/product/addProduct";
                msg = "确定添加记录?"
            } else {
                url = "/product/editProduct";
                msg = "确定修改记录?"
            }

            $.messager.confirm('提示', msg, function (r) {
                if (r) {
                    $("#addForm").attr("action", url);
                    $("#addForm").ajaxSubmit(function (resultJson) {
                        //回调操作
                        $.messager.confirm("提示", resultJson.msg);
                        $("#productTable").datagrid("reload");
                    });

                }
            });

        }


        /**
         * open issue div
         */
        function openIssueDiv() {
            var data = $("#productTable").datagrid("getSelected");
            if (data == null) {
                $.messager.confirm('提示', '请选中一条记录进行操作!');
                return;
            }

            $("#issue_pid").textbox("setText", data.pid);
            $("#issue_pid").textbox("setValue", data.pid);
            $("#issue_pid").textbox("readonly", true);
            $("#issue_productName").textbox("setText", data.productname);
            $("#issue_productName").textbox("readonly", true);
            $("#issue_price").textbox("setText", "");
            $("#issue_price").textbox("readonly", true);
            $("#issue_sn").textbox("setText", "");
            $("#issue_pType").combobox("clear");


            $('#issueDiv').window('open');  // open a window
            $('#issueDiv').window('center');
        }

        function cancelIssueDiv() {
            $('#issueDiv').window('close');
        }

        /**
         * 发布产品
         */
        function issueProduct() {

            //表单验证
            if (!$("#issueForm").form('validate')) {
                return false;
            }


            $('#issueDiv').window('close');
            $("#issueForm").ajaxSubmit(
                    function (data) {
                        //回调操作
                        $.messager.confirm("提示", data.msg);
                    }
            )
            ;
        }

        function openImg(url) {
            window.parent.addTab(imgurl + url, "产品图片");
        }


        function removeProductImg() {
            //清空图片的标记, 默认为0 不清空, 如果为1 就清空
            $("#productImg_").val(1);
            $("#productImg").css({'display': 'none'});
        }

        function removeRecommandImg() {
            //清空图片的标记, 默认为0 不清空, 如果为1 就清空
            $("#recommandImg_").val(1);
            $("#productImg2").css({'display': 'none'});
        }

    </script>

</head>
<body>
<div id="toolBar">

    <br/>
    编号：<input class="easyui-textbox" id="search_pid" type="text"/>
    名称：<input class="easyui-textbox" id="search_productname" type="text"/>
    <%--产品类型:<select id="search_pType" class="easyui-combobox" style="width: 145px">--%>
    <%--<option value="1">特价产品</option>--%>
    <%--<option value="2">推荐产品</option>--%>
    <%--<option value="3">普通产品</option>--%>
    <%--</select>--%>
    <br/><br/>
    品类：<input id="search_sortId" class="easyui-combobox"
              data-options="valueField: 'did',textField: 'dictname',
              onSelect:function(selection){
              reloadSearchBrandId();
              },
              onChange:function(newValue,oldValue){
              clearSearchBrandId();
              }"/>
    品牌：<input id="search_brandId" class="easyui-combobox"
              data-options="valueField: 'did',textField: 'dictname',
              onSelect:function(selection){
              reloadSearchSeriesId();
              },
              onChange:function(newValue,oldValue){
              clearSearchSeriesId();
              }"/>
    系列：<input id="search_seriesId" class="easyui-combobox"
              data-options="valueField: 'did',textField: 'dictname'"/>
    &nbsp<a class="easyui-linkbutton" onclick="searchProduct()" data-options="iconCls:'icon-search'"
            style="margin:5px 5px 5px 0px;">查询</a>

    <br/>

    <a class="easyui-linkbutton" onclick="openAddDiv(true)" data-options="iconCls:'icon-add'"
       style="margin:5px 5px 5px 0px;">添加</a>
    <a class="easyui-linkbutton" onclick="openIssueDiv()" data-options="iconCls:'icon-remove'"
       style="margin:5px 5px 5px 30px;">发布</a>
</div>
<table id="productTable" style="width:auto;"></table>


<div id="addDiv" class="easyui-window" title="添加" style="width:300px;"
     data-options="iconCls:'icon-search',modal:true" closed="true">
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'north'" style="height:88%;">
            <form id="addForm" method="POST" enctype="multipart/form-data">
                <table style="text-align: center;width: 100%;height:100%" border="0" cellpadding="2" cellspacing="0">
                    <input id="add_pid" name="pid" type="hidden"/>
                    <tr>
                        <td>名称:</td>
                        <td><input class="easyui-textbox" id="add_productName" name="productName" type="text"/></td>
                    </tr>
                    <tr>
                        <td>价格:</td>
                        <td><input class="easyui-numberbox" id="add_price" name="price" type="text" data-options="min:0.01,precision:2"/></td>
                    </tr>
                    <%--<tr>--%>
                    <%--<td>产品类型:</td>--%>
                    <%--<td><select id="add_pType" class="easyui-combobox" name="pType" style="width: 145px">--%>
                    <%--<option value="1">特价产品</option>--%>
                    <%--<option value="2">推荐产品</option>--%>
                    <%--<option value="3">普通产品</option>--%>
                    <%--</select>--%>
                    <%--</td>--%>
                    <%--</tr>--%>
                    <tr>
                        <td>品类:</td>
                        <td><input id="add_sortId" name="sortId" class="easyui-combobox" required="required"
                                   data-options="valueField: 'did',textField: 'dictname',
                                   onSelect:function(selection){
                                       reloadBrandId();
                                    },
                                    onChange:function(newValue,oldValue){
                                        clearBrandId();
                                    }"/>
                        </td>
                    </tr>
                    <tr>
                        <td>品牌:</td>
                        <td><input id="add_brandId" name="brandId" class="easyui-combobox" required="required"
                                   data-options="valueField: 'did',textField: 'dictname',
                                   onSelect:function(selection){
                                       reloadSeriesId();
                                    },
                                    onChange:function(newValue,oldValue){
                                        clearSeriesId();
                                    }"/>
                        </td>
                    </tr>
                    <tr>
                        <td>系列:</td>
                        <td><input id="add_seriesId" name="seriesId" class="easyui-combobox" required="required"
                                   data-options="valueField: 'did',textField: 'dictname'"/>
                        </td>
                    </tr>
                    <tr>
                        <td>描述:</td>
                        <td><textarea name="describemodel" type="text" style="width: 90%;height: 100px"
                                      id="add_describemodel"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <%--<input type="file" id="file" name="file" style="width:90%"/>--%>
                            <input class="easyui-filebox" id="add_file" name="file" style="width:90%"
                                   data-options="
                            buttonText: '选择产品图片',
                            buttonAlign: 'left',
                            onChange:function(path){
                            $('#productImg').attr('src',$(this).filebox('getValue'));
                            }"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <%--<input type="file" id="file" name="file" style="width:90%"/>--%>
                            <input class="easyui-filebox" id="add_file2" name="file2" style="width:90%"
                                   data-options="
                            buttonText: '选择推荐图片',
                            buttonAlign: 'left',
                            onChange:function(path){
                            $('#productImg2').attr('src',$(this).filebox('getValue'));
                            }"/>
                        </td>
                    </tr>
                    <tr>
                        <td>产品图:</td>
                        <td>
                            <img id="productImg" src=""
                                 style="width: 290px;height:290px;display: none;"/>
                        </td>

                    </tr>
                    <tr>
                        <td colspan="2">
                            <a class="easyui-linkbutton" onclick="removeProductImg()"
                               style="margin: 5px">清除图片</a>
                        </td>
                    </tr>
                    <tr>
                        <td>推荐图:</td>
                        <td>
                            <img id="productImg2" src=""
                                 style="width: 290px;height:290px;display: none;"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <a class="easyui-linkbutton" onclick="removeRecommandImg()"
                               style="margin: 5px">清除图片</a>
                        </td>
                    </tr>
                </table>

                <input id="productImg_" name="productImg_" type="hidden" value="0"/>
                <input id="recommandImg_" name="recommandImg_" type="hidden" value="0"/>
            </form>
        </div>
        <div data-options="region:'center'" style="text-align:center;height:12%">
            <a class="easyui-linkbutton" onclick="addProduct()" data-options="iconCls:'icon-ok'"
               style="margin: 5px">确定</a>
            <a class="easyui-linkbutton" onclick="cancelAddDiv()" data-options="iconCls:'icon-cancel'"
               style="margin: 5px">取消</a>
        </div>
    </div>
</div>


<div id="issueDiv" class="easyui-window" title="发布" style="width:260px;height:280px"
     data-options="iconCls:'icon-search',modal:true" closed="true">
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'north'" style="height:85%;">
            <form id="issueForm" method="post" action="/merchandise/addMerchandise">
                <table style="text-align: center;width: 100%;height:100%" border="0" cellpadding="2" cellspacing="0">
                    <tr>
                        <td>产品id:</td>
                        <td><input class="easyui-textbox" id="issue_pid" name="productId" type="text"/></td>
                    </tr>
                    <tr>
                        <td>名称:</td>
                        <td><input class="easyui-textbox" id="issue_productName" name="productName" type="text"/></td>
                    </tr>
                    <tr>
                        <td>产品类型:</td>
                        <td><select id="issue_pType" class="easyui-combobox" name="pType" style="width: 145px;"
                                    required="required" editable="false"
                                    data-options="onSelect:function(selection){
                                        if(selection.value==1){
                                            var data = $('#productTable').datagrid('getSelected');
                                            $('#issue_price').textbox('readonly',false);
                                            $('#issue_price').textbox('setText',data.price);
                                            $('#issue_price').textbox('setValue',data.price);
                                            $('#priceTr').css({'display':'table-row'});
                                        }else{
                                            $('#issue_price').textbox('setText', '');
                                             $('#issue_price').textbox('readonly',true);
                                             $('#priceTr').css({'display':'none'});
                                        }
                                    }">

                            <option value="1">特价产品</option>
                            <option value="2">推荐产品</option>
                        </select>
                        </td>
                    </tr>
                    <tr id="priceTr">
                        <td>价格:</td>
                        <td><input class="easyui-numberbox" id="issue_price" name="price" type="text" data-options="min:0.01,precision:2"/></td>
                    </tr>
                    <tr>
                        <td>库存:</td>
                        <td><input class="easyui-textbox" id="issue_stock" name="stock" type="text"/></td>
                    </tr>
                    <tr>
                        <td>排序:</td>
                        <td><input class="easyui-textbox" id="issue_sn" name="sn" type="text"/></td>
                    </tr>

                </table>
            </form>
        </div>
        <div data-options="region:'center'" style="text-align:center;height:15%">
            <a class="easyui-linkbutton" onclick="issueProduct()" data-options="iconCls:'icon-ok'"
               style="margin: 5px">确定</a>
            <a class="easyui-linkbutton" onclick="cancelIssueDiv()" data-options="iconCls:'icon-cancel'"
               style="margin: 5px">取消</a>
        </div>
    </div>
</div>

</div>
</body>
</html>
