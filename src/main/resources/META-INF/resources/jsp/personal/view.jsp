<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>


<portlet:resourceURL id="/hg/personal/page" var="personal"/>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/activity-manage1.css?v=1" />
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/common.min.css" />


    <style type="text/css">
    </style>
    <script type="text/javascript" >
        $(function() {
            var tableObj;
            layui.use('table', function(){
                var table = layui.table;
                tableObj = table.render({
                    elem: '#logTable',
                    url: '${personal}', //数据接口
                    method: 'post',
                    where: {search: $('#searchCondition').val()},
                    page: {
                        limit:10,   //每页条数
                        limits:[],
                        prev:'&lt;上一页',
                        next:'下一页&gt;',
                        groups:4
                    },
                    cols: [[ //表头
                        {field: 'meeting_id', title: 'id', hide: true},
                        {field: 'org_name', title: '党组织', width:'12.5%'},
                        {field: 'meeting_type', title: '会议类型', width:'12.5%'},
                        {field: 'meeting_theme', title: '会议主题', width:'12.5%'},
                        {field: 'start_time', title: '开始时间', width: '12.5%'},
                        {field: 'total_time', title: '开展时长(分钟)', width: '12.5%'},
                        {field: 'contact', title: '联系人', width: '12.5%'},
                        {field: 'contact_phone', title: '联系电话', width: '12.5%'},
                        {field: 'remark', title: '详情', width: '12.5%', toolbar: '#transportBtns'}
                    ]]
                });
            });
            $('#searchBtn').on('click', function () {
                tableObj.reload({
                    elem: '#logTable',
                    url: '${personal}', //数据接口
                    method: 'post',
                    where: {search: $('#searchCondition').val()},
                    page: {
                        limit:10,   //每页条数
                        limits:[],
                        prev:'&lt;上一页',
                        next:'下一页&gt;',
                        groups:4
                    },
                    cols: [[ //表头
                        {field: 'meeting_id', title: 'id', hide: true},
                        {field: 'org_name', title: '党组织', width:'12.5%'},
                        {field: 'meeting_type', title: '会议类型', width:'12.5%'},
                        {field: 'meeting_theme', title: '会议主题', width:'12.5%'},
                        {field: 'start_time', title: '开始时间', width: '12.5%'},
                        {field: 'total_time', title: '开展时长(分钟)', width: '12.5%'},
                        {field: 'contact', title: '联系人', width: '12.5%'},
                        {field: 'contact_phone', title: '联系电话', width: '12.5%'},
                        {field: 'remark', title: '详情', width: '12.5%', toolbar: '#transportBtns'}
                    ]]
                });
            })
        });
        function detail(e) {
            var id = $(e).parent().parent().parent().parent().find("[data-field='meeting_id']").children().text();
            window.location.href='/approvaldetails?meetingId=' + id;
        }
    </script>
</head>
<body>
<div class="table_form_content">
    <!-- 右侧盒子内容 -->
    <div class="activity_manage_page">
        <div class="breadcrumb_group">
            当前位置：
            <span class="layui-breadcrumb" lay-separator=">">
                        <a href="javascript:;">组织生活</a>
                    </span>
        </div>
        <div class="bg_white_container">
            <div class="operate_form_group">
                <input type="text" name="title" id="searchCondition"  placeholder="搜索条件" autocomplete="off" class="layui-input custom_input">
                <button type="button" id="searchBtn" class="layui-btn custom_btn search_btn">查询</button>
            </div>
            <table id="logTable" lay-filter="activityTable" class="custom_table"></table>
        </div>
    </div>
    <!-- 右侧盒子内容 -->
</div>
<script type="text/html" id="transportBtns">
    <div class="operate_btns">
        <span class="blue_text" onclick="detail(this)">详情</span>
    </div>
</script>
</body>
</html>