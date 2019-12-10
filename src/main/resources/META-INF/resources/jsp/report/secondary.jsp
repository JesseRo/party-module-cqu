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
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/activity-manage1.css?v=1"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/common.min.css"/>
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
        .content_table thead tr{
            background: #F6F8FC;
            height: 48px;
            font-size: 16px;
        }
        .content_table tr:nth-child(2n) {
            background: #FBFCFE;
        }
        .content_table td{
            min-width: 130px;
            padding: 5px 15px !important;
            height: 48px;
            font-size: 14px;
        }
        .content_table thead th{
            padding: 5px 15px !important;
        }
    </style>
</head>
<body>
<div class="table_form_content">
    <!-- 右侧盒子内容 -->
    <div class="activity_manage_page">
        <div class="breadcrumb_group" style="margin-bottom: 20px;">
            当前位置：
            <span class="layui-breadcrumb" lay-separator=">">
                <a href="javascript:;">数据报送</a>
                <a href="javascript:;">数据汇总</a>
            </span>
        </div>
        <div class="bg_white_container">
            <div class="table_outer_box">
                <table class="layui-table custom_table">
                    <thead>
                    <tr>
                        <td>任务名称</td>
                        <td>任务描述</td>
                        <td>任务模板</td>
                        <td>发布时间</td>
                        <td>操作</td>
                    </tr>
                    </thead>
                    <tbody>
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
                                    <a href="${f.path}" target="_blank">${f.filename}</a>
                                </c:forEach>
                            </td>
                            <td>
                                <c:if test="${not empty c.publish_time}">
                                    <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${c.publish_time }" />
                                </c:if>
                            </td>
                            <td>
                                <a onclick="window.location.href='/secondaryReportDetail?task=${c.task_id }'" href="javascript:;">查看报送情况</a>
                            </td>
                        </tr>
                        　　 </c:forEach>
                    </tbody>
                </table>
            </div>
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
        </div>
    </div>

    <!--    分页              -->

    <script type="text/javascript">
        $(document).ready(function () {

            $('.approval').on('click', function () {
                var report = $(this).parent().parent().attr("id");
                $.post("${approval}", {report: report, status: 1}, function (res) {
                    if (res.success) {
                        alert("已通过");
                        window.location.reload();
                    }
                })
            });
            $('.reject').on('click', function () {
                var report = $(this).parent().parent().attr("id");
                $.post("${approval}", {report: report, status: 2}, function (res) {
                    if (res.success) {
                        alert("已驳回");
                        window.location.reload();
                    }
                })
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