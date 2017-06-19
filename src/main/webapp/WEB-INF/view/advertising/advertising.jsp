<%--
  Created by IntelliJ IDEA.
  User: aiveily
  Date: 2017/6/8
  Time: 17:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../../include/jsphead.inc"%>
<html>
<head>
  <title></title>


  <script type="text/javascript">
    $(function () {
      initTable();
    });

    function initTable(){
      $("#imgTable").datagrid({
        url: "/advertising/listAdvertising",
        method: "post",
        idField: "id",
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
            field: "id",
            formatter: function (data) {
              if (data == null) {
                return "-";
              } else {
                return data;
              }
            },
            width: 100
          }]],
        columns: [[
          {
          title: "名称",
          field: "imgname",
          formatter: function (data) {
            if (data == null) {
              return "-";
            } else {
              return data;
            }
          },
          width: 80
          },
          {
            title: "图片",
            field: "imgurl",
            formatter: function (data) {
              if (data == null || data == "") {
                return "";
              } else {
                return "<img  src='/static/img/img.png' style='height: 20px;width: 20px' onclick='openImg(" + JSON.stringify(data) + ")'/>";
              }
            },
            width: 40
          },
          {
            title: "是否启用",
            field: "isuse",
            formatter: function (data) {
              if (data == null) {
                return "-";
              } else if (data == 1) {
                return "是";
              } else if (data == 0) {
                return "否";
              }
            },
            width: 40
          },
          {
            title: "排序",
            field: "sort",
            formatter: function (data) {
              if (data == null) {
                return "-";
              } else {
                return data;
              }
            },
            width: 40
          },
          {
            title: "操作",
            field: "operate",
            formatter: function (data, rowData) {
              if (data == null) {
                return "<input style='background:-moz-linear-gradient(top,#ffffff,#eeeeee);border-radius: 4px;border:1px #cccccc solid;' value='修改' type='button' onclick='openEditDiv(" + JSON.stringify(rowData) + ")'/> <input style='background:-moz-linear-gradient(top,#ffffff,#eeeeee);border-radius: 4px;border:1px #cccccc solid;' value='删除' type='button' onclick='removeAdver(" + JSON.stringify(rowData) + ")'/>";
              } else {
                return "";
              }
            },
            width: 100
          }]]

      })
    }


    function openAddDiv() {
      $('#addDiv').window('open');  // open a window
      $('#addDiv').window('center');
    }

    function cancelAddDiv() {
      $('#addDiv').window('close');
    }

    function addImgMethod() {
      //表单验证
      if (!$("#addForm").form('validate')) {
        return false;
      }

      $('#addDiv').window('close');
      $("#addForm").attr("action", "/advertising/addAdvertising");
      $("#addForm").ajaxSubmit(function (resultJson) {
        //回调操作
        $.messager.confirm("提示", resultJson.msg);
        $("#imgTable").datagrid("reload");
      });
    }

    var imgurl = "http://image.comeon365.com/";

    function openImg(url) {
      window.parent.addTab(imgurl + url, "图片");
    }

    function openEditDiv(data) {

      $("#edit_id").val(data.id);

      $("#edit_imgname").textbox("setText", data.imgname);
      $("#edit_imgname").textbox("setValue", data.imgname);

      $("#edit_isuse").combobox("setValue", data.isuse);

      $("#edit_sort").textbox("setText", data.sort);
      $("#edit_sort").textbox("setValue", data.sort);

      $('#editDiv').window('open');  // open a window
      $('#editDiv').window('center');
    }

    function cancelEditDiv() {
      $('#editDiv').window('close');
    }

    function editImgMethod() {
      //表单验证
      if (!$("#editForm").form('validate')) {
        return false;
      }
      $('#editDiv').window('close');
      $("#editForm").attr("action", "/advertising/editAdvertising");
      $("#editForm").ajaxSubmit(function (resultJson) {
        //回调操作
        $.messager.confirm("提示", resultJson.msg);
        $("#imgTable").datagrid("reload");
      });
    }

    function removeAdver(data) {
      var id = data.id;
      $.messager.confirm('提示', '确定要删除这条记录吗？', function (r) {
        if (r) {
          $.ajax({
            url: "/advertising/deletAdvertisin",
            dataType: "json",
            type: "post",
            data: {
              id: id
            },
            error: function () {
              alert("系统错误!");
            },
            success: function (data) {
              $.messager.confirm("提示", data.msg);
              $("#imgTable").datagrid("reload");
            }
          });
        }
      });
    }
  </script>

</head>
<body>
  <div id="toolBar">
    <a class="easyui-linkbutton" onclick="openAddDiv()" data-options="iconCls:'icon-add'"
       style="margin:5px 5px 5px 0px;">添加</a>
  </div>

  <div id="addDiv" class="easyui-window" title="添加" style="width:250px;height:230px;"
       data-options="iconCls:'icon-search',modal:true" closed="true">
    <div class="easyui-layout" data-options="fit:true">
      <div data-options="region:'north'" style="height:80%;">
        <form id="addForm" method="POST" enctype="multipart/form-data">
          <table style="text-align: center;width: 100%;height:100%" border="0" cellpadding="2" cellspacing="0">
            <tr>
              <td>名称:</td>
              <td><input class="easyui-textbox" id="add_imgname" name="imgname" type="text" required="required"/></td>
            </tr>
            <tr>
              <td>
                启用:
              </td>
              <td><select class="easyui-combobox" id="add_isuse" name="isuse" style="width:80%">
                    <option value="0">否</option>
                    <option value="1">是</option>
                  </select>
              </td>
            </tr>
            <tr>
              <td>排序:</td>
              <td><input class="easyui-textbox" id="add_sort" name="sort" type="text"
                         validType="length[1,1]"/>
              </td>
            </tr>
            <tr>
              <td colspan="2">
                <%--<input type="file" id="file" name="file" style="width:90%"/>--%>
                <input class="easyui-filebox" id="add_file" name="file" style="width:90%"
                       data-options="
                            buttonText: '选择图片',
                            buttonAlign: 'left'" required="required"/>
              </td>
            </tr>
          </table>
        </form>
      </div>
      <div data-options="region:'center'" style="text-align:center;height:25%">
        <a class="easyui-linkbutton" onclick="addImgMethod()" data-options="iconCls:'icon-ok'"
           style="margin: 5px">确定</a>
        <a class="easyui-linkbutton" onclick="cancelAddDiv()" data-options="iconCls:'icon-cancel'"
           style="margin: 5px">取消</a>
      </div>
    </div>
  </div>

  <div id="editDiv" class="easyui-window" title="修改" style="width:250px;height:230px;"
       data-options="iconCls:'icon-search',modal:true" closed="true">
    <div class="easyui-layout" data-options="fit:true">
      <div data-options="region:'north'" style="height:80%;">
        <form id="editForm" method="POST" enctype="multipart/form-data">
          <input id="edit_id" name="id" type="hidden"/>
          <table style="text-align: center;width: 100%;height:100%" border="0" cellpadding="2" cellspacing="0">
            <tr>
              <td>名称:</td>
              <td><input class="easyui-textbox" id="edit_imgname" name="imgname" type="text" required="required"/></td>
            </tr>
            <tr>
              <td>
                启用:
              </td>
              <td><select class="easyui-combobox" id="edit_isuse" name="isuse" style="width:80%">
                <option value="0">否</option>
                <option value="1">是</option>
              </select>
              </td>
            </tr>
            <tr>
              <td>排序:</td>
              <td><input class="easyui-textbox" id="edit_sort" name="sort" type="text"
                         validType="length[1,1]"/>
              </td>
            </tr>
            <tr>
              <td colspan="2">
                <%--<input type="file" id="file" name="file" style="width:90%"/>--%>
                <input class="easyui-filebox" id="edit_file" name="file" style="width:90%"
                       data-options="
                            buttonText: '选择图片',
                            buttonAlign: 'left'" />
              </td>
            </tr>
          </table>
        </form>
      </div>
      <div data-options="region:'center'" style="text-align:center;height:25%">
        <a class="easyui-linkbutton" onclick="editImgMethod()" data-options="iconCls:'icon-ok'"
           style="margin: 5px">确定</a>
        <a class="easyui-linkbutton" onclick="cancelEditDiv()" data-options="iconCls:'icon-cancel'"
           style="margin: 5px">取消</a>
      </div>
    </div>
  </div>

  <table id="imgTable" style="width:auto;"></table>
</body>
</html>
