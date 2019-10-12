<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/init.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<portlet:resourceURL id="/brunch/report/approval" var="approval"/>
<portlet:resourceURL id="/brunch/report/download" var="download"/>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <title>组织部子页面主题</title>
    <link rel="stylesheet" href="${basePath }/css/party_member.css"/>
    <link rel="stylesheet" href="${basePath }/css/party_organization.css"/>
    <style type="text/css">
        .col-sm-6.col-xs-12 {
            float: right;
        }

        .export_excel {
            font-size: 13px;
            /* margin: 10px 0; */
            padding: 5px 0;
            display: inline-block;
        }
    </style>
</head>
<body>
<div class="content_title" style="margin-bottom:30px;">
    已发布
</div>
<table class="content_table" style="border:1px solid #dedede;">
    <thead class="table_title">
    <tr>
        <th>任务名称</th>
        <th>任务描述</th>
        <th>任务模板</th>
        <th>发布时间</th>
        <th>状态</th>
    </tr>
    </thead>
    <tbody class="table_info">
    <c:forEach var="c" items="${tasks }">
        <tr id="${c.task_id }">
            <td>
                ${c.theme }
            </td>
            <td>
                ${c.description }
            </td>
            <td style="color: red;padding-left: 25px;">
                <c:forEach var="f" items="${c.fileView }">
                    <a href="${f.path}">${f.filename}</a>
                </c:forEach>
            </td>
            <td>
                <c:if test="${not empty c.publish_time}">
                    <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${c.publish_time }" />
                </c:if>
            </td>
            <td>
                <c:if test="${c.status == 1}">
                    <a href="/brunch_report1?task=${c.task_id }">上报</a>
                </c:if>
                <c:if test="${c.status == 2}">
                    已上报
                </c:if>
            </td>
        </tr>
　　 </c:forEach>
    </tbody>
</table>

<!--    分页              -->

<div class="pagination_container">
    <ul class="pagination" id="page"></ul>
    <div class="pageJump">
        <input class='current_page' type="hidden" value="${pageNo}"/>
        <p>共<span class="total_page">${totalPage }</span>页</p>
        <portlet:actionURL name="/PageNoMVCActionCommand" var="pageNoUrl">
        </portlet:actionURL>
        <form action="#" id="getPageNo" method="post">
            <input type="hidden" id="pageNo" name="pageNo" value=""/>
            <input type="hidden" id="total_page_" name="total_page_" value="${totalPage}"/>
            <span>跳转到第</span>
            <input type="text" id="jumpPageNo" name="jumpPageNo"/>
            <span>页</span>
            <%--  <input label="站点id" name="Site"  value="${Site }" type="hidden"/>
            <input label="栏目id" name="Column"  value="${Column }" type="hidden"/> --%>
            <input type="hidden" id="date_date" name="date" value=""/>
            <button type="submit" class="button">确定</button>
        </form>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        var pages = $(".total_page").html();
        var currentPage = $('.current_page').val();
        $("input[name='pageNo']").val($('.current_page').val());
        if (currentPage == 1) {
            $('.page_next').removeClass('not_allow');
            $('.page_prev').addClass('not_allow');

        } else if (currentPage == pages) {
            $('.page_prev').removeClass('not_allow');
            $('.page_next').addClass('not_allow');

        } else {

        }

        $("#jumpPageNo").change(function () {
            $("input[name='pageNo']").val($(this).val());
        });
        Page({
            num: pages, //页码数
            startnum: currentPage, //指定页码
            elem: $('#page'), //指定的元素
            callback: function (n) { //回调函数
                $("input[name='pageNo']").val(n);
                //	alert($("input[name='pageNo']").val());
                $("#getPageNo").submit();
                if (n == 1) {
                    $('#page a').removeClass('not_allow');
                    $('.page_prev').addClass('not_allow');
                } else if (n >= $('.total_page').html()) {
                    $('#page a').removeClass('not_allow');
                    $('.page_next').addClass('not_allow')
                } else {
                    $('#page a').removeClass('not_allow');
                }
            }
        });
    });
</script>
</div>
<!-- 分页 -->


</body>

</html>