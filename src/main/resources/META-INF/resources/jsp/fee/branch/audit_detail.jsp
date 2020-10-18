<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/init.jsp" %>
<portlet:resourceURL id="/PartyPassCommand" var="PartyPass" />
<portlet:resourceURL id="/PartyRejectedCommand" var="PartyRejected" />
<portlet:resourceURL id="/api/download" var="downloadUrl" />
<html>
	<head>
		 <link rel="stylesheet" href="${basePath }/css/details.css" />
		 <style type="text/css">
		 	.layui-form-item layui-row >p{
		 		text-align:left !important;
		 	}
			.layui-layer-content{
				overflow: visible !important;
			}
			#rejectModal .layui-form-item .layui-input-inline{
				width:200px
			}
			#rejectModal .layui-form-label{
				width:140px;
				font-weight:bold;
			}
			#rejectModal .layui-form-label-text{
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
		$(function () {
			$.get("http://" + document.domain + ':9007/fee/member/fee-detail?id=${id}', function (res) {
				if (res.code === 0) {
					if (!res.data.feeState) {
						$('#button_pay').show();
					}
					$('#member_name').text(res.data.name);
					$('#member_org').text(res.data.secondaryName + res.data.orgName);
					$('#fee_type').text(res.data.yearMonth + '党费缴纳通知');
					$('.fee_amount').text(res.data.shouldFee + '元');
					$('#fee_yearMonth').text(res.data.yearMonth);
					$('#end_time').text(res.data.endDate);
					$('#fee_state').text(res.data.feeState ? '已缴费' : '未缴费');
				} else {
					layuiModal.alert(res.message);
				}
			})
		})
	</script>
	<div class="table_form_content">
		<%--   查看计划详情  --%>
		<div class="activity_manage_page">
			<div class="breadcrumb_group" style="margin-bottom: 20px;">
				当前位置：
				<span class="layui-breadcrumb" lay-separator=">">
					<a href="javascript:;">党费管理</a>
					<a href="javascript:;">审核详情</a>
				</span>
			</div>
			<div class="bg_white_container">
				<div class="details_container ">
<%--					<p class="details_title">${org.org_name}--${meetingTheme }</p>--%>
					<div class="details_content  layui-form">
						<div class="layui-form-item layui-row" >
							<p class="layui-col-xs6 layui-col-sm6 layui-col-md6"><span>党员姓名</span></p>
							<p class="layui-col-xs6 layui-col-sm6 layui-col-md6"><span id="member_name"></span></p>
						</div>
						<div class="layui-form-item layui-row" >
							<p class="layui-col-xs6 layui-col-sm6 layui-col-md6"><span>所在组织</span></p>
							<p class="layui-col-xs6 layui-col-sm6 layui-col-md6"><span id="member_org"></span></p>
						</div>
						<div class="layui-form-item layui-row" >
							<p class="layui-col-xs6 layui-col-sm6 layui-col-md6"><span>党费类型</span></p>
							<p class="layui-col-xs6 layui-col-sm6 layui-col-md6"><span id="config_type"></span></p>
						</div>
						<div class="layui-form-item layui-row" >
							<p class="layui-col-xs6 layui-col-sm6 layui-col-md6"><span>党费金额</span></p>
							<p class="layui-col-xs6 layui-col-sm6 layui-col-md6"><span id="fee_amount"></span>元/月</p>
						</div>
						<div class="layui-form-item layui-row">
							<button type="button" id="button_pass"
									class="layui-btn layui-btn-primary" style="display:none; background-color: transparent;color: #666;padding: 0 20px;font-size: 16px;height: 40px;line-height: 40px;border-radius: 4px;">
								通过
							</button>
							<button type="button" id="button_reject"
									class="layui-btn layui-btn-primary" style="background-color: transparent;color: #666;padding: 0 20px;font-size: 16px;height: 40px;line-height: 40px;border-radius: 4px;">
								驳回
							</button>
							<button type="button" onclick="window.history.back();"
									class="layui-btn layui-btn-primary" style="background-color: transparent;color: #666;padding: 0 20px;font-size: 16px;height: 40px;line-height: 40px;border-radius: 4px;">
								返回
							</button>
						</div>
					</div>
				</div>
			</div>
			<!-- 弹窗 -->
			<div style="display: none" id="rejectModal">
				<form class="layui-form" action="">
					<input type="hidden" class="layui-layer-input"  name="meetingId" value="1">
					<div class="layui-form-item">
						<div class="layui-inline">
							<label class="layui-form-label layui-required">驳回理由:</label>
							<div class="layui-input-inline">
								<select name="rejectReason" lay-verify="select" >
									<option value="">-请选择-</option>
									<c:forEach var="reason" items="${reasonList }">
										<option value="${reason.resources_value}">${reason.resources_value}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>

					<div class="layui-layer-btn layui-layer-btn-">
						<a class="layui-layer-btn0" type="button"  lay-submit="" lay-filter="rejectForm">确定</a>
						<a class="layui-layer-btn1">取消</a>
					</div>
				</form>
			</div>
		</div>
	</div>
	</body>
</html>