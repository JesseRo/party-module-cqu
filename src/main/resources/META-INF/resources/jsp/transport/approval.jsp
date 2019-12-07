<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<portlet:resourceURL id="/transport/page" var="transport"/>
<portlet:resourceURL id="/transport/approval" var="approval"/>
<portlet:resourceURL id="/retention/page" var="retention"/>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/activity-manage1.css?v=1" />
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/common.min.css" />


    <style type="text/css">
    </style>
    <script type="text/javascript" >
        $(function() {
            layui.use('table', function(){
                var table = layui.table;

                //第一个实例
                table.render({
                    elem: '#retentionTable',
                    url: '${retention}', //数据接口
                    method: 'post',
                    page: {
                        limit:10,   //每页条数
                        limits:[],
                        prev:'&lt;上一页',
                        next:'下一页&gt;',
                        groups:4,
                    },
                    cols: [[ //表头
                        {field: 'transport_id', title: 'id', hide: true},
                        {field: 'user_name', title: '姓名', width:'20%'},
                        {field: 'org_name', title: '所在支部', width:'20%'},
                        {field: 'time', title: '申请时间', width:'20%'},
                        {field: 'reason', title: '原因', width: '30%'},
                        {field: 'operate', title: '操作', width: '10%', toolbar: '#retentionBtns'}
                    ]]
                });
                table.render({
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
                        {field: 'transport_id', title: 'id', hide: true},
                        {field: 'user_name', title: '姓名', width:'20%'},
                        {field: 'org_name', title: '所在支部', width:'20%'},
                        {field: 'time', title: '申请时间', width:'20%'},
                        {field: 'reason', title: '原因', width: '30%'},
                        {field: 'operate', title: '操作', width: '10%', toolbar: '#transportBtns'}
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
        function retentionApprove(e, status) {
            var id = $(e).parent().parent().parent().parent().find("[data-field='retention_id']").children().text();
            $.post("${approval}", {id: id, type: 'retention', status: status},function (res) {
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
    <!-- 右侧盒子内容 -->
    <div class="activity_manage_page">
        <div class="breadcrumb_group">
            当前位置：
            <span class="layui-breadcrumb" lay-separator=">">
                        <a href="javascript:;">组织关系转接</a>
                        <a href="javascript:;">审批申请</a>
                    </span>
        </div>
        <div class="bg_white_container">
            <div class="layui-tab">
                <ul class="layui-tab-title">
                    <li class="layui-this">组织关系转移</li>
                    <li>组织关系保留</li>
                </ul>
                <div class="layui-tab-content">
                    <div class="layui-tab-item layui-show">
                        <table id="transportTable" lay-filter="activityTable" class="custom_table"></table>
                    </div>
                    <div class="layui-tab-item">
                        <table id="retentionTable" lay-filter="retentionTable" class="custom_table"></table>
                    </div>
                </div>
            </div>
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
<script type="text/html" id="retentionBtns">
    <div class="operate_btns">
        <span class="blue_text" onclick="retentionApprove(this, 1);">编辑</span>
        <span class="red_text" onclick="retentionApprove(this, 2);">删除</span>
    </div>
</script>
</body>
</html>