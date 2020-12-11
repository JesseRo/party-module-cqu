<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/init.jsp" %>
<portlet:resourceURL id="/PartyPassCommand" var="PartyPass"/>
<portlet:resourceURL id="/PartyRejectedCommand" var="PartyRejected"/>
<portlet:resourceURL id="/api/download" var="downloadUrl"/>
<html>
<head>
    <link rel="stylesheet" href="${basePath }/css/details.css"/>
    <style type="text/css">
        .layui-form-item layui-row > p {
            text-align: left !important;
        }

        .layui-layer-content {
            overflow: visible !important;
        }

        #rejectModal .layui-form-item .layui-input-inline {
            width: 200px
        }

        #rejectModal .layui-form-label {
            width: 140px;
            font-weight: bold;
        }

        #rejectModal .layui-form-label-text {
            float: left;
            display: block;
            padding: 0 10px;
            width: 200px;
            font-weight: 400;
            line-height: 40px;
            font-size: 16px;
            text-align: left;
        }
    </style>
</head>
<body>
<script>
    function getQueryVariable(variable) {
        var query = window.location.search.substring(1);
        var vars = query.split("&");
        for (var i = 0; i < vars.length; i++) {
            var pair = vars[i].split("=");
            if (pair[0] == variable) {
                return pair[1];
            }
        }
    }

    $(function () {
        layui.use(['form'], function () {
            var form = layui.form;
        })
        $.get(sessionStorage.getItem("feeUrl") + '/fee/member/fee-config', function (res) {
            if (res.code === 0) {
                $('#config_type').val(res.data.feeType);
                $('#fee_amount').val(Number(res.data.fee) / 100);
                $('#fee_state').text((res.data.stateName || '') + '  ' + (res.data.reason || ''));
                $('#fee_state_container').show();
                if (res.data.state === 0) {
                    $('#button_pass').show();
                    $('#button_reject').hide();
                } else {
                    $('#button_pass').hide();
                    $('#button_reject').show();
                }
            } else {
                layuiModal.alert(res.message, function () {
                    window.history.back();
                });
            }
        })
    })
</script>
<div class="table_form_content">
    <div class="activity_manage_page">
        <div class="breadcrumb_group" style="margin-bottom: 20px;">
            当前位置：
            <span class="layui-breadcrumb" lay-separator=">">
					<a href="javascript:;">党费管理</a>
					<a href="javascript:;">党费设置</a>
				</span>
        </div>
        <div class="bg_white_container">
            <div class="details_container ">
                <div class="details_content  layui-form">
                    <form class="layui-form form-horizontal new_publish_form" action="">
<%--                        <div class="layui-form-item layui-row">--%>
<%--                            <p class="layui-col-xs3 layui-col-sm3 layui-col-md3"><span>党员姓名</span></p>--%>
<%--                            <p class="layui-col-xs6 layui-col-sm6 layui-col-md6"><span id="member_name"></span></p>--%>
<%--                        </div>--%>
<%--                        <div class="layui-form-item layui-row">--%>
<%--                            <p class="layui-col-xs3 layui-col-sm3 layui-col-md3"><span>所在组织</span></p>--%>
<%--                            <p class="layui-col-xs6 layui-col-sm6 layui-col-md6"><span id="member_org"></span></p>--%>
<%--                        </div>--%>
                        <div class="layui-form-item layui-row">
                            <p class="layui-col-xs3 layui-col-sm3 layui-col-md3"><span>党费类型</span></p>
                            <div class="layui-input-inline">
                                <select id="config_type" disabled>
                                    <option disabled>请选择类型</option>
                                    <option value="1">月薪制党员</option>
                                    <option value="2">年薪制党员</option>
                                    <option value="3">企业员工/其他协议工资党员</option>
                                    <option value="4">离退休教职工党员</option>
                                    <option value="5">学生党员</option>
                                    <option value="6">在职就读硕士博士党员</option>
                                </select>
                            </div>
                        </div>
                        <div class="layui-form-item layui-row">
                            <p class="layui-col-xs3 layui-col-sm3 layui-col-md3"><span>党费金额</span></p>
                            <div class="layui-input-inline"><input id="fee_amount" class="layui-input" disabled></div>
                            <p style="line-height: 30px;">元/月</p>
                        </div>
                        <div class="layui-form-item layui-row" id="fee_state_container">
                            <p class="layui-col-xs3 layui-col-sm3 layui-col-md3"><span>审核状态</span></p>
                            <p class="layui-col-xs6 layui-col-sm6 layui-col-md6"><span style="color: red;" id="fee_state"></span></p>
                        </div>
                    </form>
                    <div class="layui-form-item layui-row">
                        <button type="button" id="button_reject" onclick="window.location.href='/member/config?resubmit=1'" style="display: none;"
                                class="layui-btn layui-btn-danger">
                            重新设置
                        </button>
                        <button type="button" onclick="window.history.back();"
                                class="layui-btn layui-btn-primary"
                                style="background-color: transparent;color: #666;padding: 0 20px;font-size: 16px;height: 40px;line-height: 40px;border-radius: 4px;">
                            返回
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>