<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<portlet:resourceURL id="/unit/delete" var="delete"/>
<portlet:resourceURL id="/unit/page" var="unitPage"/>
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
                    elem: '#unitTable',
                    url: '${unitPage}', //数据接口
                    method: 'post',
                    page: {
                        limit:10,   //每页条数
                        limits:[],
                        prev:'&lt;上一页',
                        next:'下一页&gt;',
                        groups:4,
                    },
                    cols: [[ //表头
                        {field: 'id', title: 'id', hide: true},
                        {field: 'unit_name', title: '行政机构名称', width:'40%'},
                        {field: 'update_member_name', title: '更新人员', width:'25%'},
                        {field: 'update_time', title: '更新时间', width: '20%'},
                        {field: 'operate', title: '操作', width: '15%', toolbar: '#unitBtns'}
                    ]]
                });
            });
        });
        function unitEdit(e){
            var id = $(e).parent().parent().parent().parent().find("[data-field='id']").children().text();
            window.location.href = "/unit_new?id=" + id;
        }
        function unitDelete(e) {
            var id = $(e).parent().parent().parent().parent().find("[data-field='retention_id']").children().text();
            $.post("${delete}", {id: id},function (res) {
                if (res.result){
                    alert("已删除");
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
            <div class="operate_form_group">
                <input type="text" name="title" placeholder="搜索条件" autocomplete="off" class="layui-input custom_input">
                <button type="button" class="layui-btn custom_btn search_btn">查询</button>
                <button type="button" class="layui-btn custom_btn publish_acti_btn" id="newUnit" onclick="window.location.href='/unit_new'">新增信息</button>
            </div>
            <table id="unitTable" lay-filter="unitTable" class="custom_table"></table>
        </div>
    </div>
    <!-- 右侧盒子内容 -->
</div>
<script type="text/html" id="unitBtns">
    <div class="operate_btns">
        <span class="blue_text" onclick="unitEdit(this);">编辑</span>
        <span class="red_text" onclick="unitDelete(this);">删除</span>
    </div>
</script>
</body>
</html>