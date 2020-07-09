<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/init.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<head>
    <title>查看二级党委活动进度</title>
    <style>
        .table_info .publish_time {
            min-width: 200px;
        }

        .content_info .content_form {
            padding: 0px 20px 0 20px;
            height: 100%;
        }

        .content_form .content_table _container {
            height: calc(100% - 250px);
        }

        th, td {
            text-align: left;
        }

        .content_info .content_form .form-group .control-label {
            text-align: right;
            color: #333;
            font-size: 16px;
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

        .form-group .col-sm-6 {
            margin-bottom: 20px;
        }

        .bg_white_container .content_table_container {
            height: calc(100% - 230px);
        }
    </style>
    <script type="text/javascript" src="${basePath}/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${basePath}/js/pagination.js"></script>
    <portlet:resourceURL id="/hg/assigned" var="assigned"/>
    <portlet:resourceURL id="/hg/getMeetingTypeAndTheme" var="getMeetingTypeAndTheme"/>
    <script type="text/javascript">
        function getBranch(seconedId) {
            console.log(11111);
            $(".party_branch").empty();
            $(".party_branch").append('<option value="">请选择</option>')
            var branchId = $(".party_branch_id").val();
            var url = "${assigned}";
            var pid = seconedId;
            var data = {paramType: "partyBranch", pid: pid};
            $.ajax({
                url: url,
                data: data,
                dataType: "json",
                success: function (result) {
                    for (var i = 0; i < result.length; i++) {
                        if (branchId && branchId == result[i].org_id) {
                            $(".party_branch").prepend('<option selected="selected" value="' + result[i].org_id + '">' + result[i].org_name + '</option>')
                        } else {
                            $(".party_branch").append('<option value="' + result[i].org_id + '">' + result[i].org_name + '</option>')

                        }
                    }
                }
            });
        }

        $(function () {
            var orgId = "${seconedId}";
            var meetTheme = "${theme}";
            var meetType = "${meetType}";
            var branchId = "${branchId}";
            var meetTheme = "${meetTheme}";
            var seconedId = "${seconedId}";
            var url = "${assigned}";
            var data = {paramType: "sconedParty"};
            $.ajax({
                url: url,
                data: data,
                dataType: "json",
                success: function (result) {
                    for (var i = 0; i < result.length; i++) {
                        if (orgId && orgId == result[i].org_id) {
                            $(".sconed_party").prepend('<option selected="selected" value="' + result[i].org_id + '">' + result[i].org_name + '</option>')
                        } else {
                            $(".sconed_party").append('<option value="' + result[i].org_id + '">' + result[i].org_name + '</option>')
                        }
                    }
                }
            });

            $(".sconed_party").change(function () {
                $(".party_branch").empty();
                $(".party_branch").append('<option value="">--选择--</option>')
                var url = "${assigned}";
                var pid = $(".sconed_party").val();
                var data = {paramType: "partyBranch", pid: pid};
                $.ajax({
                    url: url,
                    data: data,
                    dataType: "json",
                    success: function (result) {
                        for (var i = 0; i < result.length; i++) {
                            $(".party_branch").append('<option value="' + result[i].org_id + '">' + result[i].org_name + '</option>')
                        }
                    }
                });
            });

            $.ajax({
                url: '${getMeetingTypeAndTheme}',
                type: 'POST',
                data: "",
                dataType: 'json',
                async: false,
                success: function (data) {
                    for (var i = 0; i < data.length; i++) {
                        var c = data[i];
                        if (c.type == 'meetingType') {
                            if (meetType && meetType == c.resources_value) {
                                $("select[name='meetType']").prepend('<option selected="selected" value="' + c.resources_value + '">' + c.resources_value + '</option>');
                            } else {
                                $("select[name='meetType']").append('<option value="' + c.resources_value + '">' + c.resources_value + '</option>');
                            }
                        }
                        if (c.type == 'news') {
                            if (meetTheme && c.resources_value == meetTheme) {
                                $("select[name='theme']").prepend('<option selected="selected" value="' + c.resources_value + '">' + c.resources_value + '</option>');
                            } else {
                                $("select[name='theme']").append('<option value="' + c.resources_value + '">' + c.resources_value + '</option>');
                            }
                        }
                    }
                },
                error: function (data) {
                    alert("获取数据失败");
                }
            });
            getBranch(seconedId);
        });


    </script>
</head>
<body>
<%--<div class="table_form_content activity_manage_container">--%>
<div class="table_form_content ">
    <!-- 右侧盒子内容 -->
    <div class="activity_manage_page">
        <div class="breadcrumb_group" style="margin-bottom: 20px;">
            当前位置：
            <span class="layui-breadcrumb" lay-separator=">">
				<a href="javascript:;">数据统计</a>
				<a href="javascript:;">组织活动检索</a>
			</span>
        </div>
        <div class="bg_white_container">
            <div class="content_form form_container">
                <portlet:actionURL var="findURL" name="/meeting/FindRender">
                </portlet:actionURL>
                <form class="form-horizontal" role="form" action="${findURL }" method="post">
                    <div class="form-group">
                        <div class="col-sm-6 col-xs-12">
                            <span class="col-sm-3 col-xs-3 control-label">会议类型</span>
                            <div class="col-sm-9 col-xs-9">
                                <select class="form-control" name="meetType" id="meetType">
                                    <option value="">请选择</option>
                                </select>
                            </div>
                        </div>

                        <div class="col-sm-6 col-xs-12">
                            <span class="col-sm-3 col-xs-3 control-label">会议主题</span>
                            <div class="col-sm-9 col-xs-9">
                                <input type="text" class="form-control" name="meetTheme" id="meetTheme"
                                       value="${meetTheme}">
                            </div>
                        </div>

                        <div class="col-sm-6 col-xs-12">
                            <span class="col-sm-3 col-xs-3 control-label">二级党组织</span>
                            <div class="col-sm-9 col-xs-9">
                                <select class="form-control sconed_party" name="seconedId" id="seconedId">
                                    <option value="">请选择</option>
                                </select>
                            </div>
                        </div>

                        <div class="col-sm-6 col-xs-12">
                            <span class="col-sm-3 col-xs-3 control-label">党支部</span>
                            <div class="col-sm-9 col-xs-9">
                                <input type="hidden" class="party_branch_id" value="${branchId}">
                                <select class="form-control party_branch" name="branchId" id="branchId">

                                </select>
                            </div>
                        </div>

                        <div class="col-sm-6 col-xs-12">
                            <span class="col-sm-3 col-xs-3 control-label">开始时间</span>
                            <div class="col-sm-9 col-xs-9">
                                <input type="text" name="startTime" id="labCheckDate" value="${startTime }"
                                       onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="form-control start_date">
                            </div>
                        </div>

                        <div class="col-sm-6 col-xs-12">
                            <span class="col-sm-3 col-xs-3 control-label">结束时间</span>
                            <div class="col-sm-9 col-xs-9">
                                <input type="text" name="endTime" id="labCheckEndDate" value="${endTime }"
                                       onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="form-control start_date">
                            </div>
                        </div>

                    </div>
                    <portlet:resourceURL id="/hg/MeetingCountExport" var="MeetingCountExport"/>
                    <div class="btn_group" style="height: 34px;">
                        <button type="button" id="searchButton" class="custom_btn layui-btn">查询</button>

                        <a style="margin-left: 20px;float: right;"
                           href="${MeetingCountExport }&meetType=${meetType}&meetTheme=${meetTheme}&branchId=${branchId}&seconedId=${seconedId}&startTime=${startTime}&endTime=${endTime}&ifExportAll=false">
                            <button type="button" class="btn btn-default">
                                <img src="/images/import_icon.png"/>
                                导出当前字段
                            </button>
                        </a>
                        <a style="float: right;"
                           href="${MeetingCountExport }&meetType=${meetType}&meetTheme=${meetTheme}&branchId=${branchId}&seconedId=${seconedId}&startTime=${startTime}&endTime=${endTime}&ifExportAll=true">
                            <button type="button" class="btn btn-default">
                                <img src="/images/import_icon.png"/>
                                导出全部字段
                            </button>
                        </a>
                    </div>
                </form>
                <div class="content_table_container" style="margin-top: 20px;">
                    <div class="table_outer_box">
                        <table class="layui-table custom_table">
                            <thead>
                            <tr>
                                <td>党组织</td>
                                <td>会议类型</td>
                                <td>上报主题</td>
                                <td>开展时间</td>
                                <td>联系人</td>
                                <td>联系电话</td>
                                <td>任务状态</td>
                                <td>详情</td>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${list}" var="info" varStatus="status">
                                <tr>
                                        <%--	                            <td data-label="二级党组织" class="Party_name">	                           --%>
                                        <%--	                                <c:if test="${not empty info.second_name }"> --%>
                                        <%--	                            	 ${info.second_name }--%>
                                        <%--									</c:if>--%>
                                        <%--									 <c:if test="${ empty info.second_name }"> --%>
                                        <%--	                            	  ${info.branch_name }--%>
                                        <%--									</c:if>--%>
                                        <%--	                            </td>--%>
                                        <%--	                            <td data-label="党支部">                           --%>
                                        <%--	                                <c:if test="${not empty info.second_name }"> --%>
                                        <%--	                            	 ${info.branch_name }--%>
                                        <%--									</c:if>--%>
                                        <%--	                            </td>--%>
                                    <td data-label="党支部" title="${info.branch_name }">
                                            ${info.branch_name }
                                    </td>
                                    <td data-label="会议类型" title="${info.meeting_type }">${info.meeting_type }</td>
                                        <%--	                            <td data-label="发布时间" class="publish_time"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${info.release_time }" /></td>--%>
                                        <%--	                            <td data-label="开展主题">${info.meeting_theme }</td>--%>
                                    <td data-label="党支部主题" title="${info.meeting_theme }">${info.meeting_theme }</td>
                                    <td data-label="开展时间" title="${info.start_time }" class="publish_time start_time">
                                        <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${info.start_time }"/></td>
                                        <%--	                            <td data-label="开展地点">${info.place }</td>--%>
                                        <%--	                            <td data-label="主持人">${info.host }</td>--%>
                                    <td data-label="联系人" title="${info.contact_name }">${info.contact_name }</td>
                                    <td data-label="联系电话" title="${info.contact_phone }">${info.contact_phone }</td>
                                        <%--	                            <td data-label="联系人电话">${info.contact_phone }</td>--%>
                                    <td data-label="任务状态">
                                        <c:if test="${info.plan_state == '1'}">
                                            已提交
                                        </c:if>
                                        <c:if test="${info.plan_state == '2'}">
                                            已撤回
                                        </c:if>
                                        <c:if test="${info.plan_state == '3'}">
                                            被驳回
                                        </c:if>
                                        <c:if test="${info.plan_state == '4'}">
                                            已通过
                                        </c:if>
                                        <c:if test="${info.plan_state == '5'}">
                                            已指派
                                        </c:if>
                                        <c:if test="${info.plan_state == '6'}">
                                            未检查
                                        </c:if>
                                        <c:if test="${info.plan_state == '7'}">
                                            已检查
                                        </c:if>
                                    </td>
                                    <td><a style="cursor: pointer;"
                                           onclick="window.location.href='/approvaldetails?meetingId=${info.meeting_id}'">查看详情</a>
                                    </td>
                                        <%--	                            <td data-label="审核人">${info.auditor }</td>--%>
                                        <%--	                            <td data-label="检查人">${info.check_person_name }</td>--%>
                                        <%--	                            <td data-label="抽查人">${info.check_person_org_name }</td>--%>
                                        <%--	                            <td data-label="应到人数">${info.shoule_persons }</td>--%>
                                        <%--	                            <td data-label="实到人数">${info.actual_persons }</td>--%>
                                        <%--	                            <td data-label="请假人员">${info.leave_persons }</td>--%>
                                        <%--	                            <td data-label="出勤率">${info.attendance }</td>--%>
                                        <%--	                            <td data-label="备注" class="note">${info.note }</td>--%>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <!-- -----------------------分页--------------------------->
                    <div class="pagination_container">
                        <ul class="pagination" id="page"></ul>
                        <div class="pageJump">
                            <input class='current_page' type="hidden" value="${pageNo}"/>
                            <p>共<span class="total_page">${totalPage }</span>页</p>
                            <portlet:actionURL name="/PageNoMVCActionCommand" var="pageNoUrl">
                                <%--								<portlet:param name="startTime" value="${startTime }"/>--%>
                                <%--								<portlet:param name="endTime" value="${endTime }"/>--%>
                                <%--								<portlet:param name="seconedId" value="${seconedId }"/>--%>
                                <%--								<portlet:param name="branchId" value="${branchId }"/>--%>
                                <%--								<portlet:param name="meetTheme" value="${meetTheme }"/>--%>
                                <%--								<portlet:param name="meetType" value="${meetType }"/>--%>
                            </portlet:actionURL>
                            <form action="${pageNoUrl }" id="getPageNo" method="post">
                                <input type="hidden" id="pageNo" name="pageNo" value=""/>
                                <span>跳转到第</span>
                                <input type="text" id="jumpPageNo" name="jumpPageNo"/>
                                <span>页</span>
                                <button type="submit" class="button">确定</button>

                                <input name="startTime" type="hidden" value="${startTime }"/>
                                <input name="endTime" type="hidden" value="${endTime }"/>
                                <input name="seconedId" type="hidden" value="${seconedId }"/>
                                <input name="branchId" type="hidden" value="${branchId }"/>
                                <input name="meetTheme" type="hidden" value="${meetTheme }"/>
                                <input name="meetType" type="hidden" value="${meetType }"/>
                            </form>
                        </div>
                    </div>
                </div>


                <script type="text/javascript">
                    //分页
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
                        ;
                        $("#jumpPageNo").change(function () {
                            $("input[name='pageNo']").val($(this).val());
                        })
                        Page({
                            num: parseInt(pages), //页码数
                            startnum: parseInt(currentPage), //指定页码
                            elem: $('#page'), //指定的元素
                            callback: function (n) { //回调函数
                                $("input[name='pageNo']").val(n);
                                // 				         	alert($("input[name='pageNo']").val());
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
                        $('#searchButton').on('click', function () {
                            $('#getPageNo [name=meetType]').val($('.form-group [name=meetType]').val());
                            $('#getPageNo [name=meetTheme]').val($('.form-group [name=meetTheme]').val());
                            $('#getPageNo [name=branchId]').val($('.form-group [name=branchId]').val());
                            $('#getPageNo [name=seconedId]').val($('.form-group [name=seconedId]').val());
                            $('#getPageNo [name=endTime]').val($('.form-group [name=endTime]').val());
                            $('#getPageNo [name=startTime]').val($('.form-group [name=startTime]').val());
                            $('#getPageNo').submit();
                        })
                    });
                </script>

            </div>
        </div>
    </div>
</div>
</body>