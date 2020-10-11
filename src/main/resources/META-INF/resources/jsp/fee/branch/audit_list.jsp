<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/activity-manage1.css?v=1" />
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/common.min.css" />


    <style type="text/css">
    </style>
    <script type="text/javascript" >
        var tableObj;

        function audit(state, id, msg) {
        layuiModal.confirm("确定要" + msg + "吗？", function () {
            $.post("http://" + document.domain + ':9007/fee/branch/audit', {id: id, state: state}, function (res) {
                if (res.code === 0) {
                    layuiModal.alert("已" + msg);
                    tableObj.reload();
                } else {
                    layuiModal.alert(res.message)
                }
            })
        })
    }

    $(function() {
            layui.use('table', function(){
                var table = layui.table;

                tableObj = table.render({
                    elem: '#feeTable',
                    url: "http://" + document.domain + ':9007/fee/branch/audit/list', //数据接口
                    headers: {Authorization: sessionStorage.getItem("sessionKey")},
                    method: 'get',
                    page: {
                        limit:10,   //每页条数
                        limits:[],
                        prev:'&lt;上一页',
                        next:'下一页&gt;',
                        groups:4,
                    },
                    cols: [[ //表头
                        {field: 'id', title: 'id', hide: true},
                        {field: 'memberName', title: '姓名', width:'20%'},
                        {field: 'orgName', title: '所在组织', width:'20%'},
                        {field: 'feeType', title: '党费类型', width:'20%'},
                        {field: 'fee', title: '党费金额', width: '20%'},
                        {field: 'operation', title: '操作', width: '12.5%', toolbar: '#operationButton'}
                    ]],
                    parseData: function(res){ //res 即为原始返回的数据
                        return {
                            "code": res.code, //解析接口状态
                            "msg": res.message, //解析提示文本
                            "count": res.data.count, //解析数据长度
                            "data": res.data.pageData //解析数据列表
                        };
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
                        <a href="javascript:;">党费管理</a>
                        <a href="javascript:;">类型审核</a>
                    </span>
        </div>
        <div class="bg_white_container">
            <table id="feeTable" lay-filter="feeTable" class="custom_table"></table>
        </div>
    </div>
    <!-- 右侧盒子内容 -->
</div>
<script type="text/html" id="operationButton">
    <a class="layui-btn layui-btn-xs" onclick="window.location.href='/audit_detail?id={{d.id}}'">详情</a>
    <a class="layui-btn layui-btn-xs" onclick="audit(1, '{{d.id}}', '通过')">通过</a>
    <a class="layui-btn layui-btn-xs" onclick="audit(2, '{{d.id}}', '驳回')">驳回</a>
</script>
</body>
</html>