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
            layui.use(['table','layer'], function(){
                var table = layui.table;
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
                        {field: 'transport_id', title: 'member_id', hide: true},
                        {field: 'member_name', title: '姓名', width:'20%'},
                        {field: 'org_name', title: '所在支部', width:'20%'},
                        {field: 'submit_time', title: '申请时间', width:'20%'},
                        {field: 'status', title: '任务状态', width: '20%'},
                        {field: 'operate', title: '操作', width: '10%', toolbar: '#transportBtns'},
                        {field: 'reason', title: '备注', width: '10%'}
                    ]]
                });
                //监听事件
                table.on('tool(transportTable)', function(obj){
                    //var checkStatus = table.checkStatus(obj.config.id);
                    switch(obj.event){
                        case 'pass':
                            transportApprove(obj.data.id, 1);
                            break;
                        case 'reject':
                            transportApprove(obj.data.id, 2);
                            break;
                    };
                });
                function transportApprove(id, status){
                    var content  = status ==1?'您确认通过审批吗？':'您确认不通过审批吗？';
                    layer.confirm(content, {
                        btn: ['确定','取消'] //按钮
                    }, function(){
                        $.post("${approval}", {id: id, type: 'transport', status: status},function (res) {
                            if (res.code == 200){
                                var msg  = status ==1?'审批通过成功。':'审批拒绝成功。';
                                layer.msg(msg)
                                window.location.reload();
                            }
                        },"json")
                    });
                }
            });
        });


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
            <table id="transportTable" lay-filter="transportTable" class="custom_table"></table>
        </div>
    </div>
    <!-- 右侧盒子内容 -->
</div>
<script type="text/html" id="transportBtns">
    <a class="layui-btn layui-btn-xs" lay-event="pass">通过</a>
    <a class="layui-btn layui-btn-xs red_text" lay-event="reject">驳回</a>
</script>
</body>
</html>