<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>


<portlet:resourceURL id="/hg/log/page" var="log"/>
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
                    url: '${log}', //数据接口
                    method: 'post',
                    where: {search: $('#searchBtn').val()},
                    page: {
                        limit:10,   //每页条数
                        limits:[],
                        prev:'&lt;上一页',
                        next:'下一页&gt;',
                        groups:4,
                    },
                    cols: [[ //表头
                        {field: 'id', title: 'id', hide: true},
                        {field: 'user_id', title: '操作人账户', width:'20%'},
                        {field: 'visit_time', title: '操作时间', width:'20%'},
                        {field: 'ip', title: '操作人IP地址', width:'20%'},
                        {field: 'type', title: '事件', width: '20%'},
                        {field: 'remark', title: '备注', width: '20%'}
                    ]]
                });
            });
            $('#searchBtn').on('click', function () {
                tableObj.reload({
                    elem: '#logTable',
                    url: '${log}', //数据接口
                    method: 'post',
                    where: {search: $('#searchBtn').val()},
                    page: {
                        limit:10,   //每页条数
                        limits:[],
                        prev:'&lt;上一页',
                        next:'下一页&gt;',
                        groups:4,
                    },
                    cols: [[ //表头
                        {field: 'id', title: 'id', hide: true},
                        {field: 'user_id', title: '操作人账户', width:'20%'},
                        {field: 'visit_time', title: '操作时间', width:'20%'},
                        {field: 'ip', title: '操作人IP地址', width:'20%'},
                        {field: 'type', title: '事件', width: '20%'},
                        {field: 'remark', title: '备注', width: '20%'}
                    ]]
                })
            })
        });
    </script>
</head>
<body>
<div class="table_form_content">
    <!-- 右侧盒子内容 -->
    <div class="activity_manage_page">
        <div class="breadcrumb_group">
            当前位置：
            <span class="layui-breadcrumb" lay-separator=">">
                        <a href="javascript:;">系统管理</a>
                        <a href="javascript:;">日志管理</a>
                    </span>
        </div>
        <div class="bg_white_container">
            <div class="operate_form_group">
                <input type="text" name="title" id="searchBtn" placeholder="搜索条件" autocomplete="off" class="layui-input custom_input">
                <button type="button" class="layui-btn custom_btn search_btn">查询</button>
            </div>
            <table id="logTable" lay-filter="activityTable" class="custom_table"></table>
        </div>
    </div>
    <!-- 右侧盒子内容 -->
</div>
</body>
</html>