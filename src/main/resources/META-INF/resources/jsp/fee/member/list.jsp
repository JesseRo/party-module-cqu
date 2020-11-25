<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/activity-manage1.css?v=1" />
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/common.min.css" />


    <style type="text/css">
    </style>
    <script type="text/javascript" >
        function feePay(id) {
            $.ajax({
                type: "post",
                url: "http://" + document.domain + ':9007/fee/member/fee-transaction',
                data: JSON.stringify({
                    id: [id]
                }),
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                success: function (res) {
                    if (res.code === 0) {
                        payment(res.data.sign, res.data.data);
                    } else {
                        layuiModal.alert(res.message)
                    }
                }
            });
        }
        $(function() {
            var tableObj;
            layui.use('table', function(){
                var table = layui.table;

                tableObj = table.render({
                    elem: '#feeTable',
                    url: "http://" + document.domain + ':9007/fee/member/list', //数据接口
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
                        {field: 'memberName', title: '姓名', width:'12.5%'},
                        {field: 'orgName', title: '所在组织', width:'12.5%'},
                        {field: 'feeTypeName', title: '党费类型', width:'12.5%'},
                        {field: 'yearMonth', title: '交费期间', width: '12.5%'},
                        {field: 'shouldFee', title: '党费金额', width: '12.5%', templet: function (d) {
                                return Number(d.shouldFee) / 100;
                            }},
                        {field: 'fee', title: '已交金额', width: '12.5%', templet: function (d) {
                                return d.state == 0 ? 0 : Number(d.shouldFee) / 100
                            }},
                        {field: 'state', title: '交费状态', width: '12.5%', templet: function (d) {
                                return d.state == 0 ? '未缴' : '已缴'
                            }},
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
                        <a href="javascript:;">党费列表</a>
                    </span>
        </div>
        <div class="bg_white_container">
            <div class="operate_form_group">
                <button type="button" class="layui-btn custom_btn search_btn" style="margin-left: 0;"
                        onclick="window.location.href='/member_fee_statistics'">历史党费查询</button>
                <button type="button" class="layui-btn custom_btn search_btn"
                        onclick="window.location.href='/member_donate_list'">党员捐款</button>
            </div>
            <table id="feeTable" lay-filter="feeTable" class="custom_table"></table>
        </div>
    </div>
    <!-- 右侧盒子内容 -->
</div>
<script type="text/html" id="operationButton">
    {{# if(d.state == 0){ }}
    <a class="layui-btn layui-btn-xs" onclick="feePay('{{d.recordId}}');">缴费</a>
    {{# } }}
    <a class="layui-btn layui-btn-xs" onclick="window.location.href='/member_fee_detail?id={{d.id}}&memberId={{d.memberId}}'">详情</a>
</script>
</body>
</html>