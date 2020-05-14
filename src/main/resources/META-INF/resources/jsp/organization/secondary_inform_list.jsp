<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<portlet:resourceURL id="/secondary_inform/page" var="informPage"/>



<html>
<head>
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/activity-manage1.css?v=1"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/common.min.css"/>
    <script type="text/javascript" src="${basePath}/js/jquery.jqprint-0.3.js"></script>


    <style type="text/css">
        .bg_white_container .layui-tab .layui-tab-content .layui-show {
            height: 100%;
        }

        .bg_white_container .layui-tab .layui-tab-content {
            padding: 0;
            height: calc(100% - 50px);
        }

        .bg_white_container .layui-tab {
            height: 100%;
            margin: 0;
        }

        .form-label {
            padding: 0 !important;
        }

        .control-label {
            line-height: 36px;
        }

        #letter_modal input, select {
            height: 36px !important;
            text-indent: 0 !important;
        }

        .form-group > div {
            margin-top: 10px;
            margin-bottom: 10px;
        }

        #div_print p {
            margin-bottom: 2px;
        }

        .layui-layer-dialog .layui-layer-content {
            line-height: 22px;
        }
    </style>
    <script type="text/javascript">
        $(function () {
            var statusList = ["审核中", "已通过", "已驳回", "已上传回执", "已确认回执", "已重新申请"];
            var retentionCount;
            layui.use('table', function () {
                var table = layui.table;
                var transportCount;
                var transportTable = table.render({
                    elem: '#transportTable',
                    url: '${informPage}', //数据接口
                    method: 'post',
                    page: {
                        limit: 10,   //每页条数
                        limits: [],
                        prev: '&lt;上一页',
                        next: '下一页&gt;',
                        groups: 4
                    },
                    cols: [[ //表头
                        {field: 'meeting_theme',width:320, title: '活动名称'},
                        {field: 'content',  title: '发布内容'},
                        {field: 'release_time', title: '发布时间',width:180}
                    ]],
                    parseData: function (res) { //将原始数据解析成 table 组件所规定的数据
                        transportCount = res.count;
                        return res;
                    }
                });
            });
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
                        <a href="javascript:;">组织生活管理</a>
                        <a href="javascript:;">通知列表</a>
                    </span>
        </div>
        <div class="bg_white_container">
            <table id="transportTable" lay-filter="activityTable" class="custom_table"></table>
        </div>
    </div>
    <!-- 右侧盒子内容 -->
</div>

</body>
</html>