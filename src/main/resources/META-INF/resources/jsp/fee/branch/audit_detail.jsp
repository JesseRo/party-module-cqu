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
		function getQueryVariable(variable)
		{
			var query = window.location.search.substring(1);
			var vars = query.split("&");
			for (var i=0;i<vars.length;i++) {
				var pair = vars[i].split("=");
				if(pair[0] == variable){return pair[1];}
			}
		}
		function audit(state, msg) {

			layuiModal.confirm("确定要" + msg + "吗？", function () {
				var id = getQueryVariable('id');
				var fee = $('#fee_amount').val();
				var feeType = $('#config_type').val();
				$.post("http://" + document.domain + ':9007/fee/branch/audit', {id: id, state: state, fee: Number(fee) * 100, feeType: feeType}, function (res) {
					if (res.code === 0) {
						layuiModal.alert("已" + msg);
						tableObj.reload();
					} else {
						layuiModal.alert(res.message)
					}
				})
			})
		}
		$(function () {
			layui.use(['form'], function() {
				var form = layui.form;
			})
			$.get("http://" + document.domain + ':9007/fee/branch/audit/detail?id=' + getQueryVariable('id'), function (res) {
				if (res.code === 0) {
					$('#member_name').text(res.data.memberName);
					$('#member_org').text(res.data.orgName);
					$('#config_type').val(res.data.feeType);
					$('#fee_amount').val(Number(res.data.fee) / 100);
					$('#fee_state').text(res.data.stateName || '');
					if (res.data.state !== 0) {
						$('#button_pass').hide();
						$('#button_reject').hide();
					}
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
						<form class="layui-form form-horizontal new_publish_form" action="">
							<div class="layui-form-item layui-row" >
								<p class="layui-col-xs6 layui-col-sm4 layui-col-md4"><span>党员姓名</span></p>
								<p class="layui-col-xs6 layui-col-sm4 layui-col-md4"><span id="member_name"></span></p>
							</div>
							<div class="layui-form-item layui-row" >
								<p class="layui-col-xs6 layui-col-sm4 layui-col-md4"><span>所在组织</span></p>
								<p class="layui-col-xs6 layui-col-sm4 layui-col-md4"><span id="member_org"></span></p>
							</div>
							<div class="layui-form-item layui-row" >
								<p class="layui-col-xs6 layui-col-sm4 layui-col-md4"><span>党费类型</span></p>
								<div class="layui-input-inline">
									<select id="config_type">
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
							<div class="layui-form-item layui-row" >
								<p class="layui-col-xs6 layui-col-sm4 layui-col-md4"><span>党费金额</span></p>
								<div class="layui-input-inline"><input id="fee_amount" class="layui-input"></div>
								<p style="line-height: 30px;">元/月</p>
							</div>
							<div class="layui-form-item layui-row" >
								<p class="layui-col-xs6 layui-col-sm4 layui-col-md4"><span>审核状态</span></p>
								<p class="layui-col-xs6 layui-col-sm4 layui-col-md4"><span style="color: red;" id="fee_state"></span></p>
							</div>
						</form>
						<div class="layui-form-item layui-row">
							<button type="button" id="button_pass"
									class="layui-btn layui-btn-warm" onclick="audit(1, '通过')">
								通过
							</button>
							<button type="button" id="button_reject" onclick="audit(2, '驳回')"
									class="layui-btn layui-btn-danger">
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