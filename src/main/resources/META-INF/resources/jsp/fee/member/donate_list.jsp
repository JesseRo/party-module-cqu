<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/activity-manage1.css?v=1" />
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/common.min.css" />


    <style type="text/css">
        .layui-layer-page {
            background-color: unset !important;
        }
    </style>
    <script type="text/javascript" >
        function thankLetter(v) {

        }
        function donatePay(id) {
            layuiModal.prompt("请输入捐款金额（元）", "", function (v) {
                if (isNaN(Number(v))) {
                    layuiModal.alert("请输入正确的金额!");
                }
                $.ajax({
                    type: "post",
                    url: "http://" + document.domain + ':9007/fee/member/donate-transaction',
                    data: {
                        amount: v,
                        donateId: id
                    },
                    dataType: "json",
                    success: function (res) {
                        if (res.code === 0) {
                            payment(res.data.sign, res.data.data);
                            layer.closeAll();
                        } else {
                            layuiModal.alert(res.message)
                        }
                    }
                });
            })
        }
        var layer;

        $(function() {
            var tableObj;
            var noticedId;
            layui.use("layer", function () {
                layer = layui.layer;
                $.get("http://" + document.domain + ':9007/fee/member/donate/notice', function (res) {
                    if (res.code === 0) {
                        $('#notice_content').text(res.data.notice);
                        noticedId = res.data.id;
                        layer.open({
                            title: false,
                            shadeClose: true,
                            closeBtn: 0,
                            area: '1200px',
                            type: 1,
                            content: $('.donate_notice')
                        });
                        $('.donate_notice').show();
                    }
                })
            });

            $('.donate').on('click', function () {
                donatePay(noticedId);
            })

            $('.cancel').on('click', function () {
                layer.closeAll();
            })

            layui.use('table', function(){
                var table = layui.table;

                tableObj = table.render({
                    elem: '#feeTable',
                    url: "http://" + document.domain + ':9007/fee/member/donate/list', //数据接口
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
                        {field: 'title', title: '捐款项目', width:'35%'},
                        {field: 'endTime', title: '捐款截止时间', width:'25%'},
                        {field: 'state', title: '状态', width:'25%', templet: function (d) {
                                return ['未开始', '进行中', '已结束'][d.state]
                            }},
                        {field: 'operation', title: '操作', width: '15%', toolbar: '#operationButton'}
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
                        <a href="javascript:;">捐款列表</a>
                    </span>
        </div>
        <div class="bg_white_container">
            <div class="operate_form_group">
                <button type="button" class="layui-btn custom_btn search_btn" style="margin-left: 0;"
                        onclick="window.location.href='/member_donate_record'">捐款记录</button>
            </div>
            <table id="feeTable" lay-filter="feeTable" class="custom_table"></table>
        </div>
    </div>
    <div class="donate_notice" style="display: none;background-image: url('/images/img_juankuan_bg.png');width: 1200px;height: 700px;padding: 20px 150px;background-repeat: repeat no-repeat;background-size: 100% 100%;">
        <div style="background-image: url('/images/img_juankuan_content_bg.png'); width: 900px;padding: 0px 0px 0px 120px;background-repeat: repeat no-repeat;height: 660px;background-size: 100% 100%;">
            <div style="
                height: 100px;
                text-align: center;
                font-size: 30px;
                line-height: 100px;
                padding-right: 150px;
            ">捐&nbsp;&nbsp;&nbsp;款&nbsp;&nbsp;&nbsp;倡&nbsp;&nbsp;&nbsp;议&nbsp;&nbsp;&nbsp;书</div>
            <div style="height: 440px;
    overflow: auto;
    padding-right: 15px;
    margin-right: 60px;" id="notice_content">

            </div>
            <div style="float: right; margin-top: 20px;margin-right: 110px;">
                <button class="donate" style="background-image: url('/images/btn_juankuan.png'); width: 80px;">&nbsp;</button>
                <button class="cancel" style="background-image: url('/images/btn_close.png'); width: 80px; margin-left: 30px;">&nbsp;</button>
            </div>
        </div>
    </div>
    <!-- 右侧盒子内容 -->
</div>
<script type="text/html" id="operationButton">
    {{# if(d.state == 1){ }}
    <a class="layui-btn layui-btn-xs layui-btn-danger" onclick="donatePay('{{d.id}}')">我要捐款</a>
    {{# } }}
    <a class="layui-btn layui-btn-xs" onclick="window.location.href='/member_donate_detail?id={{d.id}}'">详情</a>
</script>
</body>
</html>