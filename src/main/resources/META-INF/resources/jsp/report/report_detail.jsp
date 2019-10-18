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
<div class="content_title">
    <label>报送情况</label>
    <c:if test="${not empty taskId}">
        <button id="export" type="button" class="btn btn-default col-sm-2 col-xs-4" style="float: right;margin-top: -10px;">
            <img src="/images/import_icon.png" style="margin-right: 10px;">数据汇总导出
        </button>
    </c:if>
</div>
<table class="content_table" style="border:1px solid #dedede;" id="${taskId}">
    <thead class="table_title">
    <tr>
        <th>党委</th>
        <th>任务主题</th>
        <th>任务描述</th>
        <th>发布时间</th>
        <th>上报数据</th>
        <th>上报时间</th>
        <th>状态</th>
        <th>备注</th>
    </tr>
    </thead>
    <tbody class="table_info">
    <c:forEach var="c" items="${reports }">
        <tr id="${c.report_id }">
            <td>
                ${c.org_name}
            </td>
            <td>
                ${c.theme }
            </td>
            <td>
                ${c.description }
            </td>
            <td>
                <c:if test="${not empty c.publish_time}">
                    <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${c.publish_time }" />
                </c:if>
            </td>
            <td style="color: red;padding-left: 25px;">
                <c:forEach var="f" items="${c.fileView }">
                    <a href="${f.path}" target="_blank">${f.filename}</a>
                </c:forEach>
            </td>
            <td>
                <c:if test="${not empty c.time}">
                    <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${c.publish_time }" />
                </c:if>
            </td>
            <td>
                <c:choose>
                    <c:when test="${c.status == 0}">
                        <div>
                            <a class="approval" style="cursor: pointer;">通过 </a>|
                            <a class="reject" style="cursor: pointer;">驳回</a>
                        </div>
                    </c:when>
                    <c:when test="${c.status == 1}">
                        已审批
                    </c:when>
                    <c:otherwise>
                        已驳回
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                ${c.reason}
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

        $('.approval').on('click', function () {
            var report = $(this).parent().parent().parent().attr("id");
            var that = this;
            $.post("${approval}", {report: report, status: 1}, function (res) {
                if (res.success) {
                    alert("已审批");
                    $(that).parent().parent().html("已审批");
                }
            })
        });
        $('.reject').on('click', function () {
            var report = $(this).parent().parent().parent().attr("id");
            var that = this;
            var reason = prompt("驳回原因：");
            $.post("${approval}", {report: report, status: 2, reason: reason}, function (res) {
                if (res.success) {
                    alert("已驳回");
                    $(that).parent().parent().html("已驳回");
                }
            })
        });

        $('#export').on("click", function () {
            window.location.href = "${download}" + "&taskId=" + $('table.content_table').attr("id");
        });

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