<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn" %>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/activity-manage1.css?v=1" />
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/common.min.css" />


    <style type="text/css">
        .custom_table {

        }
        .layui-laydate-content table {
            table-layout: fixed;
            width: 100%;
        }
    </style>
    <script type="text/javascript" >
        function feePay(id) {
            $.ajax({
                type: "post",
                url: sessionStorage.getItem("feeUrl") + '/fee/member/fee-transaction',
                data: JSON.stringify({
                    id: id
                }),
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: function (res) {
                    if (res.code === 0) {
                        payment(res.data.sign, res.data.data);
                    } else {
                        layuiModal.alert(res.message)
                    }
                }
            });
        }
        function sendSms(id) {
            $.ajax({
                type: "post",
                url: sessionStorage.getItem("feeUrl") + '/fee/branch/sms',
                data: JSON.stringify({
                    recordIds: id
                }),
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: function (res) {
                    if (res.code === 0) {
                        layuiModal.alert("已发送信息");
                    } else {
                        layuiModal.alert(res.message)
                    }
                }
            });
        }
        function audit(memberId, memberName, month, fee, recordId, text) {
            var html = memberName + "：" + month + '月党费' + Number(fee) / 100 + "元<br><br>是否" + text + "？";
            layer.open({
                type: 1
                ,title: false //不显示标题栏
                ,closeBtn: false
                ,area: '300px;'
                ,shade: 0.8
                ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
                ,btn: ['确定', '取消']
                ,btnAlign: 'c'
                ,moveType: 1 //拖拽模式，0或者1
                ,content: '<div style="padding: 50px; line-height: 22px; background-color: #B82F3E; color: #fff; font-weight: 300;text-align: center;">' + html + '</div>'
                ,success: function(layero){
                },
                yes: function (index) {
                    layer.close(index);
                    if (text === '短信催缴') {
                        sendSms([recordId]);
                    } else if (text === '代缴') {
                        feePay([recordId]);
                    }
                }
            });
        }
        var checked = {};


        $(function() {
            var startDate, endDate, state = 0;
            var tableObj;
            var all;
            function toDateStr(date){
                if (date.year) {
                    return date.year + '-' + date.month + '-01';
                }else {
                    return "";
                }
            }
            layui.use('laydate', function () {
                var laydate = layui.laydate;
                laydate.render({
                    elem: '#date_range'
                    , range: '-'
                    , type: 'month'
                    , done: function (value, date, e) {
                        console.log(value); //得到日期生成的值，如：2017-08-18
                        console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
                        console.log(e); //得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。
                        startDate = toDateStr(date);
                        endDate = toDateStr(e);
                    }
                });
            })
            layui.use('table', function(){
                var table = layui.table;
                tableObj = table.render({
                    elem: '#feeTable',
                    url: sessionStorage.getItem("feeUrl") + '/fee/branch/list', //数据接口
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
                    where: {
                        state: state
                    },
                    cols: [[ //表头
                        {type:'checkbox'},
                        {field: 'id', title: 'id', hide: true},
                        {field: 'memberName', title: '姓名', width:'10%'},
                        {field: 'secondaryName', title: '所在组织', width:'20%'},
                        {field: 'orgName', title: '所在支部', width:'20%'},
                        {field: 'yearMonth', title: '缴费年月', width:'10%'},
                        {field: 'feeTypeName', title: '党费类型', width:'10%'},
                        {field: 'fee', title: '党费金额', width: '10%', templet: function (d) {
                                return Number(d.fee) / 100;
                            }},
                        {field: 'operation', title: '操作', width: '20%', toolbar: '#operationButton'}
                    ]],
                    parseData: function(res){ //res 即为原始返回的数据
                        all = res.data.pageData;
                        return {
                            "code": res.code, //解析接口状态
                            "msg": res.message, //解析提示文本
                            "count": res.data.count, //解析数据长度
                            "data": res.data.pageData //解析数据列表
                        };
                    }
                });

                table.on('checkbox(feeTable)', function(obj){
                    if (obj.checked) {
                        if (obj.type == 'all') {
                            checked = {};
                            for (var i = 0; i < all.length; i++) {
                                var a = all[i];
                                checked[a.memberId + '@' + a.yearMonth] = {name: a.memberName, month: a.month, fee: a.fee, id: a.recordId}
                            }
                        } else {
                            checked[obj.data.memberId + '@' + obj.data.yearMonth] = {
                                name: obj.data.memberName,
                                month: obj.data.month,
                                fee: obj.data.fee,
                                id: obj.data.recordId
                            }
                        }
                    } else {
                        if (obj.type == 'all') {
                            checked = {}
                        }
                        delete checked[obj.data.memberId + '@' + obj.data.yearMonth]
                    }
                    console.log(obj.checked); //当前是否选中状态
                    console.log(obj.data); //选中行的相关数据
                    console.log(obj.type); //如果触发的是全选，则为：all，如果触发的是单选，则为：one
                });
            });
            function reload() {
                tableObj.reload({
                    where: {
                        orgId: $('#secondary').val(),
                        keys: $('#searchCondition').val(),
                        start: startDate,
                        end: endDate,
                        state: state
                    },
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                });
            }
            $('#transportSearchBtn').on('click', reload);

            var representButton = $('#represent_fee');
            representButton.on('click', function () {
                representFee("代缴");
            });
            var smsButton = $('#sms_fee');
            smsButton.on('click', function () {
                representFee("短信催缴");
            });
            var backButton = $('#back_fee');
            backButton.on('click', function () {
                window.location.href = '/back_fee'
            })
            function representFee(text) {
                if (Object.keys(checked).length <= 0) {
                    layuiModal.alert("请选择人员");
                    return
                }
                var html = '';
                var totalFee = 0;
                for (var k in checked) {
                    var d = checked[k];
                    html += d.name + "：" + d.month + '月党费' + Number(d.fee) / 100 + "元<br>";
                    totalFee += Number(d.fee) / 100;
                }
                html += "<br>合计金额：" + totalFee + "<br><br>是否批量" + text + "？";
                layer.open({
                    type: 1
                    ,title: false //不显示标题栏
                    ,closeBtn: false
                    ,area: '300px;'
                    ,shade: 0.8
                    ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
                    ,btn: ['确定', '取消']
                    ,btnAlign: 'c'
                    ,moveType: 1 //拖拽模式，0或者1
                    ,content: '<div style="padding: 50px; line-height: 22px; background-color: #B82F3E; color: #fff; font-weight: 300;text-align: center;">' + html + '</div>'
                    ,success: function(layero){

                    },
                    yes: function (index) {
                        var ids = [];
                        for (var k in checked) {
                            var d = checked[k];
                            ids.push(d.id);
                        }
                        if (text === '短信催缴') {
                            sendSms(ids);
                        } else if (text === '代缴') {
                            feePay(ids);
                        }
                        layer.close(index);
                    }
                });
            }


            layui.use('element', function(){
                var element = layui.element;

                //监听Tab切换，以改变地址hash值
                element.on('tab(docDemoTabBrief)', function(data){
                    if (data.index == 1) {
                        state = 1;
                        smsButton.hide();
                        representButton.hide();
                    } else {
                        smsButton.show();
                        representButton.show();
                        state = 0
                    }
                    reload();
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
                        <a href="javascript:;">缴费查询</a>
                    </span>
        </div>
        <div class="bg_white_container">
            <div class="operate_form_group">
                <c:if test="${orgs != null && fn:length(orgs) > 0}">
                <select type="text" name="title" id="secondary" autocomplete="off" class="form-control"
                        style="width: 15%;float: left;border-radius: 0;height: 38px!important;text-indent: 0;">
                    <option value="">所有</option>
                    <c:forEach items="${orgs}" var="org">
                        <option value="${org.org_id}">${org.org_name}</option>
                    </c:forEach>
                </select>
                </c:if>
                <div class="layui-input-inline" style="height: 38px;margin-left: 20px;">
                    <input type="text" class="layui-input" id="date_range" placeholder="日期范围">
                </div>
                <input type="text" name="title" id="searchCondition" placeholder="查询条件" autocomplete="off"
                       class="layui-input custom_input"
                       style="margin-left: 20px; float: none;height: 38px;">
                <button type="button" id="transportSearchBtn" class="layui-btn custom_btn search_btn"
                        style="float: none;height: 38px;">查询
                </button>
                <button type="button" id="back_fee" class="layui-btn custom_btn search_btn"
                        style="float: right;height: 38px;">补缴
                </button>
                <button type="button" id="represent_fee" class="layui-btn custom_btn search_btn"
                        style="float: right;height: 38px;">代缴
                </button>
                <button type="button" id="sms_fee" class="layui-btn custom_btn search_btn"
                        style="float: right;height: 38px;">短信催缴
                </button>
            </div>
            <div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief" style="height: 100%;">
                <ul class="layui-tab-title" >
                    <li class="layui-this">未交</li>
                    <li>已交</li>
                </ul>
                <div class="layui-tab-content" style="height: 100%;">
                    <table id="feeTable" lay-filter="feeTable" class="custom_table"></table>
                </div>
            </div>
        </div>
    </div>
    <!-- 右侧盒子内容 -->
</div>
<script type="text/html" id="operationButton">
    {{# if(d.state == 0){ }}
    <a class="layui-btn layui-btn-xs" onclick="audit('{{d.memberId}}', '{{d.memberName}}', '{{d.month}}','{{d.fee}}', '{{d.recordId}}', '代缴')">代缴</a>
    <a class="layui-btn layui-btn-xs" onclick="audit('{{d.memberId}}', '{{d.memberName}}', '{{d.month}}','{{d.fee}}', '{{d.recordId}}', '短信催缴')">短信催缴</a>
    {{# } }}
</script>
</body>
</html>