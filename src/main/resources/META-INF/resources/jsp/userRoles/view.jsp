<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/init.jsp" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <title>角色选择</title>
    <%--  <link rel="stylesheet" href="${basePath }/css/common.css" /> --%>
    <link rel="stylesheet" href="${basePath }/css/login.css"/>
    <link rel="stylesheet" href="${basePath }/css/bootstrap.min.css"/>
</head>

<body>
<%--<div class="col-sm-6 col-sm-offset-3" style="height:660px;margin-top:100px;">--%>
<%--    <form class="form-horizontal" role="form">--%>
<%--        <div class="form-group">--%>
<%--            <div class="col-sm-12 select_role" style="text-align: center;">--%>

<%--            </div>--%>
<%--        </div>--%>
<%--        <div class="form-group login_btn">--%>
<%--            <div class="col-sm-2 col-sm-offset-5 ">--%>
<%--                <button type="button" onclick="ajaxLogin();" style="background-color: #ce0000; color: white;"--%>
<%--                        class="btn btn-default form-control">确认--%>
<%--                </button>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </form>--%>
<%--</div>--%>

<script>

    $(function () {
        if (!${ok}) {
            layuiModal.alert("您没有进入党务系统的权限")
        }
    })
</script>
</body>

</html>
