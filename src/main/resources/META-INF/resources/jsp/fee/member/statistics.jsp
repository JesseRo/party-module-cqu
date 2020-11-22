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
            layui.use('table', function(){
                var table = layui.table;
                var unpay = '<span style="color: red;font-size: 13px;">未缴费</span>';
                tableObj = table.render({
                    elem: '#feeTable',
                    url: "http://" + document.domain + ':9007/fee/member/statistics', //数据接口
                    headers: {Authorization: sessionStorage.getItem("sessionKey")},
                    method: 'get',
                    // page: {
                    //     limit:10,   //每页条数
                    //     limits:[],
                    //     prev:'&lt;上一页',
                    //     next:'下一页&gt;',
                    //     groups:4,
                    // },
                    cols: [[ //表头
                        {field: 'id', title: 'id', hide: true},
                        {field: 'year', title: '年份/月份', width:'9%', templet: function (d) {
                                return d[-1].year > 0 ? d[-1].year : '总计';
                            }},
                        {field: 'jan', title: '1月', width:'7%', templet: function (d) {
                                return d[1] ? (d[1].state === 1 ? d[1].fee : unpay) : '';
                            }},
                        {field: 'feb', title: '2月', width:'7%', templet: function (d) {
                                return d[2] ? (d[2].state === 1 ? d[2].fee : unpay) : '';
                            }},
                        {field: 'mar', title: '3月', width:'7%', templet: function (d) {
                                return d[3] ? (d[3].state === 1 ? d[3].fee : unpay) : '';
                            }},
                        {field: 'apr', title: '4月', width:'7%', templet: function (d) {
                                return d[4] ? (d[4].state === 1 ? d[4].fee : unpay) : '';
                            }},
                        {field: 'may', title: '5月', width:'7%', templet: function (d) {
                                return d[5] ? (d[5].state === 1 ? d[5].fee : unpay) : '';
                            }},
                        {field: 'jun', title: '6月', width:'7%', templet: function (d) {
                                return d[6] ? (d[6].state === 1 ? d[6].fee : unpay) : '';
                            }},
                        {field: 'jul', title: '7月', width:'7%', templet: function (d) {
                                return d[7] ? (d[7].state === 1 ? d[7].fee : unpay) : '';
                            }},
                        {field: 'aug', title: '8月', width:'7%', templet: function (d) {
                                return d[8] ? (d[8].state === 1 ? d[8].fee : unpay) : '';
                            }},
                        {field: 'sep', title: '9月', width:'7%', templet: function (d) {
                                return d[9] ? (d[9].state === 1 ? d[9].fee : unpay) : '';
                            }},
                        {field: 'oct', title: '10月', width:'7%', templet: function (d) {
                                return d[10] ? (d[10].state === 1 ? d[10].fee : unpay) : '';
                            }},
                        {field: 'nov', title: '11月', width:'7%', templet: function (d) {
                                return d[11] ? (d[11].state === 1 ? d[11].fee : unpay) : '';
                            }},
                        {field: 'dec', title: '12月', width:'7%', templet: function (d) {
                                return d[12] ? (d[12].state === 1 ? d[12].fee : unpay) : '';
                            }},
                        {field: 'total', title: '总额', width:'7%', templet: function (d) {
                                return d[-1].fee
                            }},
                    ]],
                    parseData: function(res){ //res 即为原始返回的数据
                        return {
                            "code": res.code, //解析接口状态
                            "msg": res.message, //解析提示文本
                            "data": res.data //解析数据列表
                        };
                    }
                });
            });
        });
    </script>
</head>
<body>
<div class="party_manage_container">
    <!-- 右侧盒子内容 -->
    <div class="party_manage_page">
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