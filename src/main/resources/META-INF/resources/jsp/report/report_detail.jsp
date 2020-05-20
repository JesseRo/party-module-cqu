<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/init.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<portlet:resourceURL id="/brunch/report/approval" var="approval"/>
<portlet:resourceURL id="/brunch/report/download" var="download"/>
<portlet:resourceURL id="/brunch/report/download/sheet" var="sheet"/>
<portlet:resourceURL id="/hg/report/number" var="number"/>

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

        .content_table thead tr {
            background: #F6F8FC;
            height: 48px;
            font-size: 16px;
        }

        .content_table thead th {
            padding: 5px 15px !important;
        }

        .content_table tr:nth-child(2n) {
            background: #FBFCFE;
        }

        .content_table td {
            min-width: 130px;
            padding: 5px 15px !important;
            height: 48px;
            font-size: 14px;
        }

        .report_table_box.table_outer_box {
            height: calc(100% - 118px);
        }

        .table_outer_box .report_detail {
            position: absolute;
            top: 0;
            left: 0;
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
                <c:choose>
                    <c:when test="${not empty taskId}">
                        <a href="javascript:;" onclick="window.location.href='/secondary_report'">数据汇总</a>
                        <a href="javascript:;">报送情况</a>
                    </c:when>
                    <c:otherwise>
                        <a href="javascript:;">审批上报</a>
                    </c:otherwise>
                </c:choose>
            </span>
        </div>
        <div class="bg_white_container">
            <c:if test="${not empty taskId }">
                <div class="operate_form_group">
                    <button id="export" type="button" class="layui-btn custom_btn publish_acti_btn">数据汇总导出</button>
                    <c:if test="${task.type == 'excel' or task.type == 'both'}">
                        <button id="exportAsSheet" type="button" class="layui-btn custom_btn publish_acti_btn"
                                style="margin-right: 20px">汇总为Sheet导出
                        </button>
                    </c:if>
                </div>
            </c:if>
            <div class="table_outer_box report_table_box">
                <table class="layui-table custom_table report_detail content_table" id="${taskId}">
                    <thead>
                    <tr>
                        <td>党委</td>
                        <td>任务主题</td>
                        <td>发布时间</td>
                        <td>上报数据</td>
                        <td>上报时间</td>
                        <td>状态</td>
                        <td>备注</td>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="c" items="${reports }">
                        <tr id="${c.report_id }">
                            <td title=" ${c.org_name}">
                                    ${c.org_name}
                            </td>
                            <td>
                                <a onclick="window.location.href = '/report_task_detail?taskId=${c.task_id }'"
                                   title="查看任务详情" style="cursor: pointer;">${c.theme}</a>
                            </td>
                            <td>
                                <c:if test="${not empty c.publish_time}">
                                    <fmt:formatDate pattern="yyyy-MM-dd" value="${c.publish_time }"/>
                                </c:if>
                            </td>
                            <td style="color: red;padding-left: 25px;">
                                <a style="cursor: pointer" onclick="templateDetail(this);">查看上报</a>
                                <div class="report_template" style="display:none;">
                                    <c:forEach var="f" items="${c.fileView }">
                                        <li><a style="cursor: pointer;color: #1e9fff;" href="${f.path}"
                                               target="_blank">${f.filename}</a></li>
                                    </c:forEach>
                                </div>
                            </td>
                            <td>
                                <c:if test="${not empty c.time}">
                                    <fmt:formatDate pattern="yyyy-MM-dd" value="${c.publish_time }"/>
                                </c:if>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${c.status == 0}">
                                        <div>
                                            <a class="approval"
                                               style="cursor: pointer;margin-right: 10%; color: #11D43B">通过 </a>
                                            <a class="reject" style="cursor: pointer;color: #FE4D4D;">驳回</a>
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
            </div>
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
            <div id="file_detail" style="display: none;">
                <div style="width: 500px; ">
                    <ul>
                    </ul>
                </div>
            </div>
        </div>
        <script type="text/javascript">
            var layer;

            function templateDetail(e) {
                $('#file_detail').find('ul').html($(e).next().html());
                layer.open(
                    {
                        title: '上报文件',
                        content: $('#file_detail').html()
                    });
            }

            $(document).ready(function () {
                layui.use("layer", function () {
                    layer = layui.layer;
                });
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
                    var reason = layuiModal.prompt("驳回原因：", "", function () {
                        $.post("${approval}", {report: report, status: 2, reason: reason}, function (res) {
                            if (res.success) {
                                alert("已驳回");
                                $(that).parent().parent().html("已驳回");
                            }
                        })
                    });
                });
                var totalReportSize = ${totalReportSize};
                function excelExport(url){
                    $.post('${number}', {taskId: '${taskId}'}, function (res) {
                        if (res.result) {
                            var currentReportSize = res.data;
                            if (currentReportSize > 0) {
                                if (currentReportSize < totalReportSize) {
                                    layuiModal.confirm("还有" + (totalReportSize - currentReportSize) + "个组织没有上报或通过审核，是否仍要汇总？", function () {
                                        window.location.href = url + "&taskId=" + $('table.content_table').attr("id");
                                    });
                                } else {
                                    window.location.href = url + "&taskId=" + $('table.content_table').attr("id");
                                }
                            } else {
                                layuiModal.alert("暂无审核通过的报送数据，无法汇总")
                            }
                        } else {
                            layuiModal.alert("汇总出错")
                        }
                    });
                }

                $('#export').on("click", function () {
                    excelExport("${download}");
                });
                $('#exportAsSheet').on("click", function () {
                    excelExport("${sheet}");
                })
                ;

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
            })
            ;
        </script>
    </div>
</div>
<!-- 分页 -->


</body>

</html>.