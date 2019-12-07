<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<portlet:resourceURL id="/hg/memberEdit/page" var="transport"/>
<portlet:resourceURL id="/hg/memberEdit/approval" var="approval"/>

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
                    elem: '#transportTable',
                    url: '${transport}', //数据接口
                    method: 'post',
                    page: {
                        limit:10,   //每页条数
                        limits:[],
                        prev:'&lt;上一页',
                        next:'下一页&gt;',
                        groups:4,
                    },
                    cols: [[ //表头
                        {field: 'transport_id', title: 'member_id', hide: true},
                        {field: 'member_name', title: '姓名', width:'20%'},
                        {field: 'org_name', title: '所在支部', width:'20%'},
                        {field: 'submit_time', title: '申请时间', width:'20%'},
                        {field: 'status', title: '任务状态', width: '20%'},
                        {field: 'operate', title: '操作', width: '10%', toolbar: '#transportBtns'},
                        {field: 'reason', title: '备注', width: '10%'}
                    ]]
                });
            });
        });

        function transportApprove(e, status){
            var id = $(e).parent().parent().parent().parent().find("[data-field='transport_id']").children().text();
            $.post("${approval}", {id: id, type: 'transport', status: status},function (res) {
                if (res.result){
                    alert("已" + $(e).text());
                    window.location.reload();
                }
            })
        }
    </script>
</head>
<body>
<div class="table_form_content">
<%--    <div class="activity_manage_container">--%>

    <!-- 右侧盒子内容 -->
    <div class="activity_manage_page">
        <div class="breadcrumb_group" style="margin-bottom: 20px;">
            当前位置：
            <span class="layui-breadcrumb" lay-separator=">">
                <a href="javascript:;">基础数据管理</a>
                <a href="javascript:;">审批任务</a>
            </span>
        </div>
        <div class="bg_white_container">
            <table id="transportTable" lay-filter="activityTable" class="custom_table"></table>
        </div>
    </div>
    <!-- 右侧盒子内容 -->
</div>
<script type="text/html" id="transportBtns">
    <div class="operate_btns">
        <span class="blue_text" onclick="transportApprove(this, 1);">通过</span>
        <span class="red_text" onclick="transportApprove(this, 2);">驳回</span>
    </div>
</script>
</body>
</html>