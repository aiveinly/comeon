<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2016/2/17
  Time: 13:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<meta name="viewport" content="width=device-width,initial-scale=1">
<head>
    <meta charset="UTF-8">
    <title>产品规格</title>

    <script src="/static/jquery/jquery.min.js" type="text/javascript"></script>
    <script src="/static/jquery/jquery.form.js" type="text/javascript"></script>
    <style>
        h3 {
            text-align: center;
            position: relative;
            margin-top: 5px;
        }

        .div-hr {
            position: relative;
            margin-top: -10px
        }

        table {
            width: 100%;
        }

        input {
            border: 0;
        }

        button {
            width: 60px;
            height: 25px;
            border-radius: 5px;
            border: 1px solid silver;
            background: #0b63b4;
        }

        div {
            font-family: 宋体;
        }

        td {
            font-weight: bold;
        }
    </style>

    <script type="text/javascript">

        function openFile(url) {
//            alert(url);
            window.location.href = "http://image.comeon365.com/" + url;
        }

    </script>
</head>
<body>
<div>
    <h3>产品详细参数</h3>
    <hr class="div-h3">
</div>
<div>
    <div style="margin-left: 5%">
        <table border="0" rules="none" cellspacing="0" cellpadding="8">
            <td align="right">品名</td>
            <td><input value="${dataModel.productname}" readonly="readonly"></td>
            <td align="right">产品类型</td>
            <td><input
                    value="<c:if test="${dataModel.ptype == 1}">特价产品</c:if><c:if test="${dataModel.ptype == 2}">推荐产品</c:if><c:if test="${dataModel.ptype == 3}">普通产品</c:if>"
                    readonly="readonly"></td>
            <td align="right">品类</td>
            <td><input value="${dataModel.sortname}" readonly="readonly"></td>
            <tr>
                <td align="right">品牌</td>
                <td><input value="${dataModel.brandname}" readonly="readonly"></td>
                <td align="right">型&nbsp;&nbsp;号</td>
                <td><input value="${dataModel.modelname}" readonly="readonly"></td>
                <td align="right">系列</td>
                <td><input value="${dataModel.seriesname}" readonly="readonly"></td>
            </tr>
            <tr>
                <td align="right">价格</td>
                <td><input value="${dataModel.price}" readonly="readonly"></td>
                <td align="right">数&nbsp;&nbsp;量</td>
                <td><input value="${dataModel.qty}" readonly="readonly"></td>
                <td align="right">备注</td>
                <td><input value="${dataModel.remark}" readonly="readonly"></td>
            </tr>
            <tr>
                <td colspan="1" align="right">附件</td>
                <td colspan="4">
                    <c:forEach items="${fileModel}" varStatus="i" var="item">
                        <button class="button" onclick="openFile('${item.fileurl}')">附件</button>
                        &nbsp;&nbsp;&nbsp;
                    </c:forEach>
                    <%--<button onclick="window.location.href='http://www.baidu.com'">附件1</button>--%>
                    <%--&nbsp;&nbsp;&nbsp;--%>
                    <%--<button onclick="window.location.href='http://sina.com.cn '">附件2</button>--%>
                </td>

                <td align="right">
                    <button onclick="window.history.back(-1);">关闭</button>
                </td>
            </tr>
        </table>
    </div>
    <hr>
</div>
</body>
</html>