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
        function getQueryVariable(variable) {
            var query = window.location.search.substring(1);
            var vars = query.split("&");
            for (var i = 0; i < vars.length; i++) {
                var pair = vars[i].split("=");
                if (pair[0] == variable) {
                    return pair[1];
                }
            }
            return '';
        }
        $(function() {
            var startDate, endDate;
            var tableObj;
            function toDateStr(date){
                if (date.year) {
                    return date.year + '-' + date.month + '-' + date.date;
                }else {
                    return "";
                }
            }
            var where = {};
            $('#export').on('click', function () {
                var params = "";
                for (var k in where) {
                    if (where[k]) {
                        params += "&" + k + "=" + where[k];
                    }
                }
                window.open(sessionStorage.getItem("feeUrl") + '/fee/secondary/donate/statistics-export' + "?token=" + sessionStorage.getItem("sessionKey") + '&orgId=' + orgId + params);
            })
            var orgId = getQueryVariable('orgId');

            layui.use('table', function(){
                var table = layui.table;

                tableObj = table.render({
                    elem: '#feeTable',
                    url: sessionStorage.getItem("feeUrl") + '/fee/secondary/donate/statistics?orgId=' + orgId, //数据接口
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
                        {field: 'branchName', title: '支部', width:'25%', templet: function (d) {
                                return '<a onclick="window.location.href=\'/branch_donate_search?orgId=' + d.orgId + '\'">' + d.branchName + '</a>';
                            }},
                        {field: 'memberCount', title: '支部人数', width:'15%'},
                        {field: 'donateCount', title: '捐款人数', width:'15%'},
                        {field: 'title', title: '捐款项目', width:'25%'},
                        {field: 'amount', title: '捐款金额', width:'20%'}
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

            layui.use('laydate', function () {
                var laydate = layui.laydate;
                laydate.render({
                    elem: '#date_range'
                    , range: '-',
                    done: function (value, date, e) {
                        console.log(value); //得到日期生成的值，如：2017-08-18
                        console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
                        console.log(e); //得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。
                        startDate = toDateStr(date);
                        endDate = toDateStr(e);
                    }
                });
            })
            function reload() {
                where = {
                    task: $('#task').val(),
                    start: startDate,
                    end: endDate
                };
                tableObj.reload({
                    where: where,
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                });
            }
            $('#transportSearchBtn').on('click', reload);
            $('#task').on('change', reload);
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
                        <a href="javascript:;">党员捐款查询</a>
                    </span>
        </div>
        <div class="bg_white_container">
            <div class="operate_form_group">
                <div class="layui-input-inline" style="height: 40px;">
                    <input type="text" class="layui-input" id="date_range" placeholder="日期范围" autocomplete="off">
                </div>
                <button type="button" id="transportSearchBtn" class="layui-btn custom_btn search_btn"
                        style="float: none;">查询
                </button>
                <button type="button" id="export" class="layui-btn custom_btn search_btn"
                        style="float: none;height: 38px;">导出excel
                </button>
                <select type="text" name="title" id="task" autocomplete="off" class="form-control"
                        style="width: 15%;float: right;border-radius: 0;height: 40px!important;text-indent: 0;">
                    <option value="">所有项目</option>
                    <c:forEach items="${tasks}" var="task">
                        <option value="${task.id}">${task.title}</option>
                    </c:forEach>
                </select>
            </div>
            <table id="feeTable" lay-filter="feeTable"></table>
        </div>
    </div>
    <!-- 右侧盒子内容 -->
</div>
</body>
</html>