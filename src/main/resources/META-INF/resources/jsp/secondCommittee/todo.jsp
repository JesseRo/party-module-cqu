<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/init.jsp" %>
<portlet:resourceURL id="/org/meeting/page" var="OrgMeetingPage" />
<portlet:resourceURL id="/api/download" var="downloadUrl" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <title>二级党委-待办事项</title>
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/activity-manage1.css?v=1"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/common.min.css"/>
    <style type="text/css">
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
        .layui-table-body .layui-table-cell{
            height: auto;
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

<portlet:renderURL var="showConferenceDetailUrl">
    <portlet:param name="mvcRenderCommandName" value="/hg/showConferenceDetail"/>
</portlet:renderURL>

<portlet:renderURL var="submitPlanUrl">
    <portlet:param name="mvcRenderCommandName" value="/hg/SubmitPlan"/>
</portlet:renderURL>

<portlet:actionURL name="/hg/postSubmissions" var="submitForm"/>
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

<div class="table_form_content">
    <!-- 右侧盒子内容 -->
    <div class="activity_manage_page">
        <div class="breadcrumb_group" style="margin-bottom: 20px;">
            当前位置：
            <span class="layui-breadcrumb" lay-separator=">">
                        <a href="javascript:;">组织生活管理</a>
                        <a href="javascript:;">已发计划</a>
                    </span>
        </div>
        <div class="bg_white_container">
            <form class="layui-form" id="searchForm">
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <div class="layui-input-inline keyword">
                            <input type="text" name="keyword"  placeholder="请输入姓名、组织、主题关键字" class="layui-input">
                        </div>
                        <button type="button"  class="layui-btn layui-btn-warm"  lay-submit="" lay-filter="searchForm"><icon class="layui-icon layui-icon-search"></icon>搜索</button>
                    </div>
                </div>
            </form>
            <table id="meetingPlanTable" lay-filter="meetingPlanTable"></table>
        </div>
    </div>
</div>
</body>
<script type="text/html" id="meetingPlanTableBtns">
    {{#  if(d.task_status == '4' || d.task_status == '5' || d.task_status == '6'){ }}
    <a class="layui-btn layui-btn-xs" href="/sendplan?meetingId=${d.meeting_id }&orgType=secondary&type=edit"> 编辑</a>
    {{#  } }}
    <a class="layui-btn layui-btn-xs" lay-event="detail">查看</a>
</script>
<script>
    layui.use(['table','layer','form'], function() {
        var table = layui.table,
            layer = layui.layer,
            form = layui.form;
        renderTable();
        form.on('submit(searchForm)', function (data) {
            renderTable();
        })
        function renderTable(){
            var  where = {
                keyword: $("#searchForm input[name=keyword]").val()
            };
            table.render({
                elem: '#meetingPlanTable',
                where: where,
                height:560,
                url: '${OrgMeetingPage}', //数据接口
                method: 'post',
                page: {
                    limit:10,   //每页条数
                    limits:[10,15,20],
                    prev:'&lt;上一页',
                    next:'下一页&gt;',
                    theme: '#FFB800',
                    groups:4
                },
                cols: [[ //表头
                    {field: 'meeting_type', align:'center', title: '活动类型'},
                    {field: 'meeting_theme', align:'center',width:320, title: '活动主题'},
                    {field: 'start_time', align:'center', title: '开始时间',width:180,templet: function(d){return new Date(d.start_time).format("yyyy-MM-dd hh:mm:ss");}},
                    {field: 'total_time', align:'center', title: '时长',width:100,templet: function(d){return d.total_time/60;}},
                    {field: 'member_name', align:'center', title: '联系人',width:120},
                    {field: 'task_status', align:'center', title: '任务状态',width:120,templet: function(d){
                            var status = '';
                            switch(parseInt(d.task_status)){
                                case 0:status = '待提交';break;
                                case 1:status = '待审核';break;
                                case 2:status = '已撤回';break;
                                case 3:status = '已驳回';break;
                                case 4:status = '已通过';break;
                                case 5:status = '已指派';break;
                                case 6:status = '未检查';break;
                                case 7:status = '已检查';break;
                            }
                            return status;
                        }},
                    {field: 'operation', align:'center', title: '操作',width:160,toolbar: '#meetingPlanTableBtns'},
                    {field: 'member_name', align:'center', title: '联系人',width:120},
                    {field: 'attachment', align:'center', title: '附件',width:200,templet:function(d){
                            var fileData;
                            if(d.attachment==''||d.attachment==null || d.attachment == undefined ){
                                fileData = new Array();
                            }else{
                                fileData =  eval("(" + d.attachment + ")");
                            }
                            var ul = '<ul>';
                            for(var i=0;fileData.length>0 && i<fileData.length;i++){
                                ul = ul +'<li><a href="javascript:void(0)" class="file-download" path="'+fileData[i].path+'" name="'+fileData[i].name+'" onclick="downloadFile(this)">'+fileData[i].name+'</a></li>';
                            }
                            ul = ul +'</ul>';
                            return ul;
                    }},
                    {field: 'remark', align:'center', title: '备注',templet:function(d){
                            if(d.task_status == 3){
                                return "驳回原因："+d.remark;
                            }else{
                                if(d.remark == undefined ||d.remark == null){
                                    return '';
                                }
                                return d.remark;
                            }
                        }
                    }
                ]]
            });
            $(".layui-table-view .layui-table-page").addClass("layui-table-page-center");
            $(".layui-table-view .layui-table-page").removeClass("layui-table-page");
            //监听事件
            table.on('tool(meetingPlanTable)', function(obj){
                switch(obj.event){
                    case 'check':
                        //renderDetail('check',obj);
                        break;
                    case 'detail':
                        //renderDetail('check',obj);
                        window.location.href='/approvaldetails?meetingId='+obj.data.meeting_id;
                        break;
                };
            });
        }

    });
    function downloadFile(o){
        var path = $(o).attr("path");
        var name = $(o).attr("name");
        window.location.href="${downloadUrl}&filePath="+path+"&fileName="+name;
    }
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