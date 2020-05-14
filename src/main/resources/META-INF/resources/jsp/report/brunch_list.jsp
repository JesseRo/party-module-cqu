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
    </style>
</head>
<body>
<div class="table_form_content">
    <!-- 右侧盒子内容 -->
    <div class="activity_manage_page">
        <div class="breadcrumb_group">
            当前位置：
            <span class="layui-breadcrumb" lay-separator=">">
                <a href="javascript:;">数据报送</a>
            </span>
        </div>
        <div class="bg_white_container">
            <div class="table_outer_box">
                <table class="layui-table custom_table">
                    <thead>
                    <tr>
                        <td>任务名称</td>
                        <td>任务模板</td>
                        <td>发布时间</td>
                        <td>状态</td>
                        <td>上报文件</td>
                        <td>操作</td>
                    </tr>
                    </thead>
                    <tbody class="table_info">
                    <c:forEach var="c" items="${tasks }">
                        <tr id="${c.task_id }">
                            <td title="${c.theme}">
                                <a onclick="window.location.href = '/report_task_detail?taskId=${c.task_id }'"
                                   title="查看任务详情" style="cursor: pointer;">${c.theme}</a>
                            </td>
                            <td style="color: red;padding-left: 25px;">
                                <a style="cursor: pointer" onclick="templateDetail(this, '报送模板');">查看模板</a>
                                <div class="report_template" style="display:none;">
                                    <c:forEach var="f" items="${c.templateFileView }">
                                        <li><a style="cursor: pointer;color: #1e9fff;" href="${f.path}"
                                               target="_blank">${f.filename}</a></li>
                                    </c:forEach>
                                </div>
                            </td>
                            <td>
                                <c:if test="${not empty c.publish_time}">
                                    <fmt:formatDate pattern="yyyy-MM-dd" value="${c.publish_time }"/>
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${c.status == 1}">
                                    未上报
                                </c:if>
                                <c:if test="${c.status == 2}">
                                    <c:choose>
                                        <c:when test="${c.report_status == 0}">
                                            已上报
                                        </c:when>
                                        <c:when test="${c.report_status == 1}">
                                            已通过
                                        </c:when>
                                        <c:when test="${c.report_status == 2}">
                                            已驳回
                                        </c:when>
                                        <c:when test="${c.report_status == 5}">
                                            已重新上报
                                        </c:when>
                                    </c:choose>
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${c.status == 2}">
                                    <a style="cursor: pointer" onclick="templateDetail(this, '上报文件');">查看上报</a>
                                    <div class="report_template" style="display:none;">
                                        <c:forEach var="u" items="${c.uploadFileView }">
                                            <li><a style="cursor: pointer;color: #1e9fff;" href="${u.path}"
                                                   target="_blank">${u.filename}</a></li>
                                        </c:forEach>
                                    </div>
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${c.status == 1}">
                                    <a href="javascript:;"
                                       onclick="window.location.href='/brunch_report1?task=${c.task_id }'">上报</a>
                                </c:if>
                                <c:if test="${c.status == 2 and c.report_status == 2}">
                                    <a href="javascript:;"
                                       onclick="window.location.href='/brunch_report1?task=${c.task_id }&redo=redo'">重新上报</a>
                                </c:if>
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
                    <form action="${pageNoUrl}" id="getPageNo" method="post">
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
        <div id="file_detail" style="display: none;">
            <div style="width: 500px; ">
                <ul>
                </ul>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var layer;

    function templateDetail(e, title) {
        $('#file_detail').find('ul').html($(e).next().html());
        layer.open(
            {
                title: title,
                content: $('#file_detail').html()
            });
    }

    $(document).ready(function () {
        layui.use("layer", function () {
            layer = layui.layer;
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