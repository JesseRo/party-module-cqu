<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <link rel="stylesheet" href="${basePath}/css/account_manage_1.css"/>

    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/party-info-manage.min.css"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/common.min.css" />


    <style type="text/css">
        .layui-laydate-content table {
            table-layout: fixed;
            width: 100%;
        }
    </style>
    <script type="text/javascript" >
        $(function() {
            var tableObj;
            var startDate, endDate;

            function toDateStr(date){
                if (date.year) {
                    return date.year + '-' + date.month + '-' + date.date;
                }else {
                    return "";
                }
            }
            layui.use('table', function(){
                var table = layui.table;

                tableObj = table.render({
                    elem: '#feeTable',
                    url: "http://" + document.domain + ':9007/fee/school/donate/list', //数据接口
                    headers: {Authorization: sessionStorage.getItem("sessionKey")},
                    method: 'post',
                    contentType: 'application/json',
                    page: {
                        limit:10,   //每页条数
                        limits:[],
                        prev:'&lt;上一页',
                        next:'下一页&gt;',
                        groups:4,
                    },
                    cols: [[ //表头
                        {field: 'id', title: 'id', hide: true},
                        {field: 'secondaryName', title: '二级党组织', width:'16.6%', templet: function (d) {
                                return '<a onclick="window.location.href=\'/secondary_donate_statistics?orgId=' + d.orgId + '\'">' + d.secondaryName + '</a>';
                            }},
                        {field: 'secretary', title: '党组织书记', width:'16.6%'},
                        {field: 'memberCount', title: '党组织人数', width:'16.6%'},
                        {field: 'title', title: '捐款项目', width:'16.6%'},
                        {field: 'donateCount', title: '已捐人数', width:'16.6%'},
                        {field: 'amount', title: '捐款金额', width:'16.6%'},
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
                tableObj.reload({
                    where: {
                        task: $('#task').val(),
                        start: startDate,
                        name: $('#searchCondition').val(),
                        end: endDate
                    },
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
                        <a href="javascript:;">党员捐款</a>
                    </span>
        </div>
        <div class="bg_white_container">
            <div class="operate_form_group">
                <button type="button" class="layui-btn custom_btn search_btn"
                        onclick="window.location.href='/school_donate_task'" style="margin-left: 0;">新建党员捐款</button>
                <div class="layui-input-inline" style="margin-left: 20px;height: 40px;">
                    <input type="text" class="layui-input" id="date_range" placeholder="日期范围">
                </div>
                <input type="text" name="title" id="searchCondition" placeholder="输入二级党组织" autocomplete="off"
                       class="layui-input custom_input"
                       style="margin-left: 20px; float: none;height: 40px;">
                <button type="button" id="transportSearchBtn" class="layui-btn custom_btn search_btn"
                        style="float: none;">查询
                </button>
                <select type="text" name="title" id="task" autocomplete="off" class="form-control"
                        style="width: 15%;float: right;border-radius: 0;height: 40px!important;text-indent: 0;">
                    <option value="">所有项目</option>
                    <c:forEach items="${tasks}" var="task">
                        <option value="${task.id}">${task.title}</option>
                    </c:forEach>
                </select>
            </div>

            <table id="feeTable" lay-filter="feeTable" class="custom_table"></table>
        </div>
    </div>
    <!-- 右侧盒子内容 -->
</div>
</body>
</html>