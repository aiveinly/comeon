<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2016/2/19
  Time: 15:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta charset="UTF-8">
    <title>Title</title>
</head>

<script src="/static/jquery/jquery.min.js" type="text/javascript"></script>
<script src="/static/jquery/jquery.form.js" type="text/javascript"></script>
<link href="/static/jquery/themes/icon.css" rel="stylesheet" type="text/css"/>
<link href="/static/jquery/themes/color.css" rel="stylesheet" type="text/css"/>
<link href="/static/jquery/themes/default/easyui.css" rel="stylesheet" type="text/css"/>
<script src="/static/jquery/jquery.easyui.min.js" type="text/javascript"></script>
<script src="/static/jquery/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>


<style>
    .input {
        border: 0px;
        position: relative;
        margin-left: 10px;
        top: -0.15em;;
        font-weight: bold;
    }

    .input-1 {
        position: relative;
        margin-left: 10px;
        height: 20px;
        line-height: 20px;
        top: -0.15em;
        font-weight: bold;
        border-radius: 5px;
        border: 1px solid silver;
    }

    .input2 {
        border: 0px;
        position: relative;
        margin-left: 6px;
        top: -0.10em;;
        font-weight: bold;
    }

    .span {
        position: relative;
        float: right;
        font-weight: bold;
        top: 20px;
        font-size: 15px;
        margin-right: 4px;
    }

    .span-2 {
        position: relative;
        float: right;
        font-weight: bold;
        top: 21px;
        font-size: 15px;
        letter-spacing: 4.3px;
        margin-right: 3px;
    }

    .span-1 {
        font-weight: bold;
        position: relative;
        top: 20px;
        font-size: 15px;
    }
</style>

<script language=javascript>
    function clearNoNum(obj) {
        //先把非数字的都替换掉，除了数字和.
        obj.value = obj.value.replace(/[^\d. -]/g, "");
        //必须保证第一个为数字而不是.
        obj.value = obj.value.replace(/^\./g, "");
        //保证只有出现一个.而没有多个.
        obj.value = obj.value.replace(/\.{2,}/g, ".");
        //保证.只出现一次，而不能出现两次以上
        obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
    }


    function saveBaojiadan() {

        $("#saveFrom").ajaxSubmit(function (resultJson) {
            //回调操作
            $.messager.confirm("提示", resultJson.msg);
        });

    }
</script>
<body>
<div>
    <form method="post" id="saveFrom" action="/quoquotation/editQuo">
        <input name="status" type="hidden" value="${quoData.status}"/>
        <div>
            <span class="span-1">报价单号：<input name="qid" readonly="readonly" type="text" value="${quoData.qid}"
                                             class="input"></span>
        <span class="span">报价单状态：<input readonly="readonly" type="text" class="input" value="
        <c:if test="${quoData.status == 0}">
            报价单生成
        </c:if>
        <c:if test="${quoData.status == 1}">
            报价单已确认
        </c:if>
        <c:if test="${quoData.status == 5}">
            报价单完成
        </c:if>

"></span><br><br>

        <span class="span-2">反馈时间：<input type="text" readonly="readonly" value="${quoData.backdateString}"
                                         class="input2"></span>
        </div>


        <div>
            <h2 style="position: relative;top: 0.7em;">产品清单</h2>
            <div>
                <table border="1" width="100%" rules="none" cellpadding="10">
                    <td width="5%"><input value="" type="text" readonly="readonly"
                                          style="width:100%;border: 0;text-align: center"></td>
                    <td><input value="预定单号" type="text" readonly="readonly"
                               style="text-align: center;width:100%;border: 0;"></td>
                    <td><input value="数量" type="text" readonly="readonly"
                               style="text-align: center;width:100%;border: 0;">
                    </td>
                    <td><input value="单价" type="text" readonly="readonly"
                               style="text-align: center;width:100%;border: 0;">
                    </td>
                    <td><input value="开票价" type="text" readonly="readonly"
                               style="text-align: center;width:100%;border: 0;background: red"></td>
                    <td><input value="品名" type="text" readonly="readonly"
                               style="text-align: center;width:100%;border: 0;">
                    </td>
                    <td><input value="型号" type="text" readonly="readonly"
                               style="text-align: center;width:100%;border: 0;">
                    </td>


                    <c:forEach items="${orderData}" varStatus="i" var="item">

                        <tr>
                            <td width="5%"><input value="${i.index + 1}" type="text"
                                                  readonly="readonly"
                                                  style="width:100%;border: 0;text-align: center"></td>
                            <td><input value="${item.poid}" name="preOrderIds" type="text" readonly="readonly"
                                       style="width:80%;border: 0px solid silver;text-align: center;border-radius: 5px;position: relative;margin-left: 10%"
                                       readonly="readonly">
                            </td>
                            <td><input value="${item.qty}" type="text"
                                       style="width:80%;border: 1px solid silver;text-align: center;border-radius: 5px;position: relative;margin-left: 10%;"
                                       onkeyup="clearNoNum(this)" readonly="readonly"></td>
                            <td><input value="${item.price}" type="text"
                                       style="width:80%;border: 1px solid silver;text-align: center;border-radius: 5px;position: relative;margin-left: 10%"
                                       onkeyup="clearNoNum(this)" readonly="readonly"></td>
                            <td><input id="kaipiaojias" value="${item.quoPrice}" name="kaipiaojias" type="text"
                                       style="width:80%;border: 1px solid silver;text-align: center;border-radius: 5px;position: relative;margin-left: 10%;color: red"
                                       onkeyup="clearNoNum(this)"></td>
                            <td><input value="${item.productname}" type="text"
                                       style="width:80%;border: 1px solid silver;text-align: center;border-radius: 5px;position: relative;margin-left: 10%"
                                       readonly="readonly">
                            </td>
                            <td><input value="${item.modelname}" type="text"
                                       style="width:80%;border: 1px solid silver;text-align: center;border-radius: 5px;position: relative;margin-left: 10%"
                                       readonly="readonly">
                            </td>
                        </tr>
                    </c:forEach>

                </table>
            </div>
        </div>
        <div>
            <h2 style="position: relative;top: 0.7em;">反馈信息</h2>
        <textarea name="remark" style="width: 100%;height: 50px;font-family: 华文宋体"
                  type="text">${quoData.remark}</textarea>
        </div>
        <div style="background: #c2ebd6">
            <h2 style="position: relative;top: 0.7em;">开票信息</h2>
            <div style="margin-left: 3%">
            <span>开户行：<input name="bank" value="${quoData.bank}"
                             style="border: 1px solid silver;border-radius: 5px;height: 20px;width: 20%;margin-left: 8px"></span>
            <span style="margin-left: 30%;letter-spacing: 16px">账号：<input name="account" value="${quoData.account}"
                                                                          style="border: 1px solid silver;border-radius: 5px;height: 20px;width: 20%"
                                                                          onkeyup="clearNoNum(this)"></span><br>
            <span style="top: 10px;position: relative;letter-spacing: 8px">税号：<input name="tax" value="${quoData.tax}"
                                                                                     style="border: 1px solid silver;border-radius: 5px;height: 20px;width: 20%;"
                                                                                     onkeyup="value=value.replace(/[\W]/g,'')"></span>
            <span style="margin-left: 30%;top:10px;position: relative">公司电话：<input name="companytel" value="${quoData.companytel}"
                                                                                   style="border: 1px solid silver;border-radius: 5px;height: 20px;width: 20%;margin-left: 16px"
                                                                                   onkeyup="clearNoNum(this)"></span><br>
            <span style="position: relative;top: 20px">联系人：<input name="receiver" value="${quoData.receiver}"
                                                                  style="border: 1px solid silver;border-radius: 5px;height: 20px;width: 20%;margin-left: 8px"></span>
            <span style="position: relative;top: 20px;margin-left: 30%">联系电话：<input name="receivertel" value="${quoData.receivertel}"
                                                                                    style="border: 1px solid silver;border-radius: 5px;height: 20px;width: 20%;margin-left: 16px"
                                                                                    onkeyup="clearNoNum(this)"></span><br>
            <span style="position: relative;top: 30px;">公司地址： <input name="address"
                    style="width: 45%;height: 20px;font-family: 华文宋体;top: -3px;position: relative" type="text"
                    value="${quoData.address}"></span>
                <div style="text-align: center;position: relative;top: 40px">
                    <a class="easyui-linkbutton" onclick="saveBaojiadan()" style="margin: 5px">&nbsp;&nbsp;保&nbsp;存&nbsp;&nbsp;</a>
                    &nbsp;&nbsp;&nbsp;
                    <%--<button style="width: 60px;height: 30px;border-radius: 5px">关闭</button>--%>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
</html>