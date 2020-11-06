<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

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
                                return d[1] ? (d[1].state === 1 ? d[1].fee : '<span style="color: red;">未缴费</span>') : '';
                            }},
                        {field: 'feb', title: '2月', width:'7%', templet: function (d) {
                                return d[2] ? (d[2].state === 1 ? d[2].fee : '<span style="color: red;">未缴费</span>') : '';
                            }},
                        {field: 'mar', title: '3月', width:'7%', templet: function (d) {
                                return d[3] ? (d[3].state === 1 ? d[3].fee : '<span style="color: red;">未缴费</span>') : '';
                            }},
                        {field: 'apr', title: '4月', width:'7%', templet: function (d) {
                                return d[4] ? (d[4].state === 1 ? d[4].fee : '<span style="color: red;">未缴费</span>') : '';
                            }},
                        {field: 'may', title: '5月', width:'7%', templet: function (d) {
                                return d[5] ? (d[5].state === 1 ? d[5].fee : '<span style="color: red;">未缴费</span>') : '';
                            }},
                        {field: 'jun', title: '6月', width:'7%', templet: function (d) {
                                return d[6] ? (d[6].state === 1 ? d[6].fee : '<span style="color: red;">未缴费</span>') : '';
                            }},
                        {field: 'jul', title: '7月', width:'7%', templet: function (d) {
                                return d[7] ? (d[7].state === 1 ? d[7].fee : '<span style="color: red;">未缴费</span>') : '';
                            }},
                        {field: 'aug', title: '8月', width:'7%', templet: function (d) {
                                return d[8] ? (d[8].state === 1 ? d[8].fee : '<span style="color: red;">未缴费</span>') : '';
                            }},
                        {field: 'sep', title: '9月', width:'7%', templet: function (d) {
                                return d[9] ? (d[9].state === 1 ? d[9].fee : '<span style="color: red;">未缴费</span>') : '';
                            }},
                        {field: 'oct', title: '10月', width:'7%', templet: function (d) {
                                return d[10] ? (d[10].state === 1 ? d[10].fee : '<span style="color: red;">未缴费</span>') : '';
                            }},
                        {field: 'nov', title: '11月', width:'7%', templet: function (d) {
                                return d[11] ? (d[11].state === 1 ? d[11].fee : '<span style="color: red;">未缴费</span>') : '';
                            }},
                        {field: 'dec', title: '12月', width:'7%', templet: function (d) {
                                return d[12] ? (d[12].state === 1 ? d[12].fee : '<span style="color: red;">未缴费</span>') : '';
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
            <table id="feeTable" lay-filter="feeTable" class="custom_table"></table>
        </div>
    </div>
    <!-- 右侧盒子内容 -->
</div>
</body>
</html>