<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/activity-manage1.css?v=1" />
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/common.min.css" />


    <style type="text/css">
        .table_outer_box > table thead, tbody tr {
            display: table-row !important;
            width: 100%;
            table-layout: fixed;
        }
        .layui-table-page {
            text-align: center;
        }
    </style>
    <script type="text/javascript" >
        $(function() {
            var tableObj;
            function renderFee(d) {
                return d ? (d / 100) : '';
            }
            layui.use('table', function(){
                var table = layui.table;

                tableObj = table.render({
                    elem: '#feeTable',
                    url: "http://" + document.domain + ':9007/fee/secondary/year/statistics', //数据接口
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
                        {field: 'year', title: '支部', width:'9%', templet: function (d) {
                                return d[-1]
                            }},
                        {field: 'year', title: '党员人数', width:'6.5%', templet: function (d) {
                                return d[0]
                            }},
                        {field: 'jan', title: '1月', width:'6.5%', templet: function (d) {
                                return renderFee(d[1]);
                            }},
                        {field: 'feb', title: '2月', width:'6.5%', templet: function (d) {
                                return renderFee(d[2]);
                            }},
                        {field: 'mar', title: '3月', width:'6.5%', templet: function (d) {
                                return renderFee(d[3]);
                            }},
                        {field: 'apr', title: '4月', width:'6.5%', templet: function (d) {
                                return renderFee(d[4]);
                            }},
                        {field: 'may', title: '5月', width:'6.5%', templet: function (d) {
                                return renderFee(d[5]);
                            }},
                        {field: 'jun', title: '6月', width:'6.5%', templet: function (d) {
                                return renderFee(d[6]);
                            }},
                        {field: 'jul', title: '7月', width:'6.5%', templet: function (d) {
                                return renderFee(d[7]);
                            }},
                        {field: 'aug', title: '8月', width:'6.5%', templet: function (d) {
                                return renderFee(d[8]);
                            }},
                        {field: 'sep', title: '9月', width:'6.5%', templet: function (d) {
                                return renderFee(d[9]);
                            }},
                        {field: 'oct', title: '10月', width:'6.5%', templet: function (d) {
                                return renderFee(d[10]);
                            }},
                        {field: 'nov', title: '11月', width:'6.5%', templet: function (d) {
                                return renderFee(d[11]);
                            }},
                        {field: 'dec', title: '12月', width:'6.5%', templet: function (d) {
                                return renderFee(d[12]);
                            }},
                        {field: 'total', title: '总额', width:'6.5%', templet: function (d) {
                                return renderFee(d[13]);
                            }},
                    ]],
                    parseData: function(res){ //res 即为原始返回的数据
                        return {
                            "code": res.code, //解析接口状态
                            "msg": res.message, //解析提示文本
                            "count": res.data.count, //解析数据长度
                            "data": res.data //解析数据列表
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
                        <a href="javascript:;">党费统计</a>
                    </span>
        </div>
        <div class="bg_white_container">
            <table id="feeTable" lay-filter="feeTable"></table>
        </div>
    </div>
    <!-- 右侧盒子内容 -->
</div>
</body>
</html>