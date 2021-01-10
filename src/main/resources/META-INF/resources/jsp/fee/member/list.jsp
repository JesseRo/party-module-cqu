<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/activity-manage1.css?v=1" />
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/common.min.css" />
    <style type="text/css">

        .layui-layer-content {
            overflow: visible !important;
        }
        .layui-layer-page {
            background-color: unset !important;
        }

        .statue {
            background-image: url('images/img_choudai.png');
            height: 360px;
            background-repeat: repeat no-repeat;
            width: 540px;
            background-size: 100% 100%;
            position: absolute;
            top: 170px;
            left: -135px;
        }
        .notice_top {
            margin-top: 20px;
            height: 80px;
            text-align: right;
            margin-right: 20px;
            line-height: 90px;
            font-size: 30px;
            font-family: SimSun;
            font-weight: 900;
            letter-spacing: 12px;
        }
        .inner_left {
            background-image: url('images/img_choudai_bg.png');
            height: 441px;
            width: 25%;
            margin-left: 5%;
        }
        .inner_right {
            background-color: #fff;
            height: 441px;
            width: 70%;
            border-radius: 7px;
        }
        .notice_inner {
            display: flex;
        }
        .inner_right p {
            margin-top: 0;
            margin-bottom: 0;
            font-size: 21px;
            font-family: SimSun;
            font-weight: 900;
            color: #000000;
            line-height: 39px;
            letter-spacing: 14px;
            text-align: center;
        }
        .inner_right .roast_title {
            height: 30px;
            font-size: 30px;
            font-family: SimSun;
            font-weight: 900;
            color: #F80A0A;
            line-height: 30px;
            margin-top: 35px;
            text-align: center;
            letter-spacing: 20px;
        }
    </style>
    <script type="text/javascript" >
        var layer;
        var id;
        function feePay(_id) {
            id = _id;
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
        $(function() {
            layuiModal.alert("目前党费收交系统处于测试中，尚未正式开放，请勿交纳党费。")
            var tableObj;
            layui.use("layer", function () {
                layer = layui.layer;
            })
            $('#pay_fee').on('click', function () {
                $.ajax({
                    type: "post",
                    url: sessionStorage.getItem("feeUrl") + '/fee/member/fee-transaction',
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
            })
            layui.use('table', function(){
                var table = layui.table;

                tableObj = table.render({
                    elem: '#feeTable',
                    url: sessionStorage.getItem("feeUrl") + '/fee/member/list', //数据接口
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
                                return d.state == 0 ? '<p style="color: red;">未交</p>' : '已交'
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
<%--                <button type="button" class="layui-btn custom_btn search_btn"--%>
<%--                        onclick="window.location.href='/member_donate_list'">党员捐款</button>--%>
            </div>
            <table id="feeTable" lay-filter="feeTable" class="custom_table"></table>
        </div>
    </div>
    <!-- 右侧盒子内容 -->
</div>
<div class="donate_notice" style="display: none;">
    <div style="background-image: url('images/img_juankuan_bg.png');width: 1200px;height: 700px;padding: 20px 150px;background-repeat: repeat no-repeat;background-size: 100% 100%;box-sizing: border-box;">
        <div class="notice_top">
            <img src="images/ic_danghui.png" style="margin-right: 20px;">党员交费
        </div>
        <div class="notice_inner">
            <div class="inner_left">
            </div>
            <div class="inner_right">
                <p class="roast_title">入党誓词</p>

                <p style="text-align: left;margin-left: 120px; margin-top: 20px;">我志愿加入中国共产党拥护党</p>

                <p>的纲领，遵守党的章程，履行党的</p>

                <p>义务，执行党的决定，严守党的纪</p>

                <p>律，保守党的秘密，对党忠诚，积</p>

                <p>极工作，为共产主义奋斗终身，随</p>

                <p>时准备为党和人民牺牲一切，永不</p>

                <p style="text-align: left;margin-left: 49px;">叛党。</p>
            </div>
        </div>
        <div class="notice_top" style="margin-right: 272px;">
            <button id="pay_fee" style="background-image: url('/images/我要支付@2x.png');width: 120px;background-size: 100% 100%;background-repeat: repeat no-repeat;height: 40px;">&nbsp;</button>
        </div>
    </div>
    <div class="statue">

    </div>
</div>
<script type="text/html" id="operationButton">
    {{# if(d.state == 0){ }}
    <a class="layui-btn layui-btn-xs" onclick="feePay('{{d.recordId}}');">交纳党费</a>
    {{# } }}
    <a class="layui-btn layui-btn-xs" onclick="window.location.href='/member_fee_detail?id={{d.id}}&memberId={{d.memberId}}'">详情</a>
</script>
</body>
</html>