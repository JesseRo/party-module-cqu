<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/init.jsp" %>

<portlet:actionURL name="/PageNoMVCActionCommand" var="pageNoUrl">


</portlet:actionURL>
<portlet:renderURL var="showConferenceDetailUrl">
    <portlet:param name="mvcRenderCommandName" value="/hg/showConferenceDetail"/>
</portlet:renderURL>

<portlet:renderURL var="submitPlanUrl">
    <portlet:param name="mvcRenderCommandName" value="/hg/SubmitPlan"/>
</portlet:renderURL>

<portlet:actionURL name="/hg/postSubmissions" var="submitForm"/>
<portlet:resourceURL id="/org/meetingNote/page" var="meetingNotePageUrl"/>
<portlet:resourceURL id="/hg/taskCancle" var="taskCancle"/>
<portlet:resourceURL id="/secondary/loadBranch" var="loadBranchUrl"/>
<portlet:resourceURL id="/secondary/resend" var="resendUrl"/>
<portlet:resourceURL id="/hg/taskCheckReplyState" var="taskCheckReplyState"/>


<portlet:renderURL var="showCommentDetailUrl">
    <portlet:param name="mvcRenderCommandName" value="/hg/showCommentDetail"/>
</portlet:renderURL>
<portlet:renderURL var="showExperienceDetailUrl">
    <portlet:param name="mvcRenderCommandName" value="/hg/showExperienceDetail"/>
</portlet:renderURL>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/activity-manage1.css?v=1"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/common.min.css"/>
    <%--     <link rel="stylesheet" href="${basePath }/css/bootstrap.min.css" /> --%>
    <script type="text/javascript" src="${basePath}/js/My97DatePicker/WdatePicker.js"></script>
    <style type="text/css">


        /* 转派给党支部弹窗样式 */

        #transmit .publish_obj_container {
            border: 1px solid #d8d8d8;
            padding: 10px 0;
        }

        #transmit .publish_obj_title {
            border-bottom: 1px solid #d8d8d8;
            padding-bottom: 10px;
        }

        #transmit .select_choice:nth-child(1) {
            margin-right: 10px;
        }

        #transmit .publish_obj_title .right .select_choice {
            display: inline-block;
        }

        #transmit .list-group {
            max-height: 150px;
            overflow-y: auto;
            margin-top: 10px;
            margin-bottom: 0px;
        }

        #transmit .publish_obj_info .list-group-item {
            border: none;
            font-size: 13px;
            color: #666;
            padding: 5px 10px;
            cursor: pointer;
        }

        select.height_34 {
            height: 34px;
        }

        /* 转派给党支部弹窗样式 */
        td.publicDate {
            min-width: 170px;
        }

        .content_table td {
            min-width: 160px;
        }

        select#taskStatus {
            border: none;
            background: #f7f7f7;
        }

        select#meetingType {
            /* size: 20px; */
            /* text-align: right; */
            border: none;
            background: #f7f7f7;
            width: 90px;
        }
        .content_table thead tr{
            background: #F6F8FC;
            height: 48px;
            font-size: 16px;
        }
        .content_table thead th{
            padding: 5px 15px !important;
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

        .table_outer_box > table thead, tbody tr {
            display: table-row !important;
            width: 100%;
            table-layout: fixed;
        }
        #searchForm .layui-form-item .layui-inline .keyword {
            width: 300px;
            margin-right: 0px;
        }
        #personInfo .layui-form-item .layui-input-inline{
            width:200px
        }
        #personInfo .layui-form-label{
            width:140px;
            font-weight:bold;
        }
        #personInfo .layui-form-label-text{
            float: left;
            display: block;
            padding: 0 10px;
            width: 200px;
            font-weight: 400;
            line-height: 40px;
            font-size: 16px;
            text-align: left;
        }
        th, tr{
            text-align:center !important;
        }
        .layui-table-page-center{
            text-align: center;
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
                        <a href="javascript:;">组织生活管理</a>
                        <a href="javascript:;">会议纪要</a>
                    </span>
        </div>
        <div class="bg_white_container">
            <form class="layui-form" id="searchForm">
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <div class="layui-input-inline keyword">
                            <input type="text" name="keyword"  placeholder="请输入组织名称、主题关键字" class="layui-input">
                        </div>
                        <button type="button"  class="layui-btn layui-btn-warm"  lay-submit="" lay-filter="searchForm"><icon class="layui-icon layui-icon-search"></icon>搜索</button>
                    </div>
                </div>
            </form>
            <table id="meetingNoteTable" lay-filter="meetingNoteTable"></table>
        </div>
    </div>
</div>


<!-- 模态框（Modal） -->
<div class="modal fade" id="transmit" tabindex="-1" role="dialog" aria-labelledby="transmitLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="transmitLabel">部署</h4>
            </div>
            <div class="modal-body">
                <div class="col-sm-12 col-xs-12 publish_obj_container">
                    <div class="col-sm-12 col-xs-12 publish_obj">
                        <div class="publish_obj_content">
                            <div class="publish_obj_title">
                                所辖党支部
                                <div class="right">
                                    <div class="select_choice all_select">
                                        <img src="${basePath }/images/not_check_icon.png"/>
                                        <input type="hidden"/>
                                        <span>全选</span>
                                    </div>
                                    <div class="select_choice oppsite_select">
                                        <img src="${basePath }/images/not_check_icon.png"/>
                                        <input type="hidden"/>
                                        <span>反选</span>
                                    </div>
                                </div>

                            </div>
                            <div class="publish_obj_info container_scroll_hidden">
                                <ul class="list-group col-sm-12 col-xs-12" id="branch-list">

                                </ul>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" id="cancel_resend">取消</button>
                <button type="button" class="btn btn_main" id="resend">部署</button>
            </div>
        </div>
    </div>
</div>


<!--撤回弹窗  -->
<!-- 撤回模态框 -->
<!--     <a href="javascript:;" data-toggle="modal" data-target="#recall">撤回</a> -->
<!-- 模态框（Modal） -->
<div class="modal fade" id="recall" tabindex="-1" role="dialog" aria-labelledby="recallLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="recallLabel">撤回</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" role="form">
                    <div class="form-group">
                        <div class="col-sm-12 col-xs-12">
                            请确认撤回该计划？
                            <input type="hidden" class="form-control reasonContent" placeholder="请输入撤回理由"/>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn_main">确定</button>
            </div>
        </div>
    </div>
</div>


<!-- 查看模态框 -->
<!--  <a href="javascript:;" data-toggle="modal" data-target="#check">查看已读回执</a> -->
<!-- 模态框（Modal） -->
<div class="modal fade check" id="check" tabindex="-1" role="dialog" aria-labelledby="checkLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="checkLabel">已读回执</h4>
            </div>
            <div class="modal-body">
                <table class="col-sm-12 col-xs-12 replaytable">
                    <thead>
                    <tr>
                        <th>姓名</th>
                        <th class="select_state">
                            <!--  <select class="select_state">
                                 <option value="allState">已读/未读</option>
                                 <option value="read">已读</option>
                                 <option value="noRead">未读</option>
                             </select>-->
                            <input class="reply_infromId" type="hidden">
                            <input class="reply_orgId" type="hidden">

                            通知
                        </th>
                        <!--                                 <th>评分</th> -->
                        <th>是否上传心得</th>
                    </tr>
                    </thead>
                    <tbody class="tbody_reply">

                    </tbody>

                </table>
            </div>
            <!-- <div class="modal-footer">
                <button type="button" class="btn btn_main">确定</button>
            </div> -->
        </div>
    </div>
</div>


</body>
<script type="text/html" id="meetingNoteTableBtn">
    <a class="layui-btn layui-btn-xs" lay-event="note">会议纪要</a>
</script>
<script>
    layui.use(['table','layer','form'], function() {
        var table = layui.table,
            layer = layui.layer,
            form = layui.form;
        var pageInfo = {
            page:1,
            size:10
        };
        renderTable(1,pageInfo.size);
        form.on('submit(searchForm)', function (data) {
            renderTable(1,pageInfo.size);
        })
        form.verify({
            select: function (value, item) {
                if (value == '' || value == null) {
                    return "请选择必填项。";
                }
            }
        });
        function renderTable(page,size){
            var  where = {
                keyword: $("#searchForm input[name=keyword]").val()
            };
            var ins = table.render({
                elem: '#meetingNoteTable',
                where: where,
                height:560,
                url: '${meetingNotePageUrl}', //数据接口
                method: 'post',
                page: {
                    limit:size,   //每页条数
                    curr:page,
                    limits:[10,15,20],
                    prev:'&lt;上一页',
                    next:'下一页&gt;',
                    theme: '#FFB800',
                    groups:4
                },
                cols: [[ //表头
                    {field: 'meeting_type', align:'center', title: '会议类型'},
                    {field: 'meeting_theme', align:'center',width:320, title: '会议主题'},
                    {field: 'start_time', align:'center', title: '发布时间',width:180,templet: function(d){return new Date(d.start_time).format("yyyy-MM-dd hh:mm:ss");}},
                    {field: 'start_time', align:'center', title: '开始时间',width:180,templet: function(d){return new Date(d.start_time).format("yyyy-MM-dd hh:mm:ss");}},
                    {field: 'total_time', align:'center', title: '时长',width:100,templet: function(d){return d.total_time/60;}},
                    {field: 'operation', align:'center', title: '操作',width:200,toolbar: '#meetingNoteTableBtn'},

                ]],
                done: function(res, curr, count){
                    pageInfo.page = curr;
                    pageInfo.size = ins.config.limit;
                }
            });
            $(".layui-table-view .layui-table-page").addClass("layui-table-page-center");
            $(".layui-table-view .layui-table-page").removeClass("layui-table-page");
            //监听事件
            table.on('tool(meetingNoteTable)', function(obj){
                switch(obj.event){
                    case 'check':
                        //renderDetail('check',obj);
                        break;
                    case 'note':
                        window.location.href='/uploadnotes?meetingId='+obj.data.meeting_id;
                        break;
                };
            });
        }
    })
    Date.prototype.format = function (fmt) {
        var o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "h+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }
</script>

</html>