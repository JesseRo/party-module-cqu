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
			$.get("http://" + document.domain + ':9007/fee/member/fee-detail', function (res) {
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
					<a href="javascript:;">缴费详情</a>
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
							<p class="layui-col-xs6 layui-col-sm6 layui-col-md6"><span>交费类型</span></p>
							<p class="layui-col-xs6 layui-col-sm6 layui-col-md6"><span>党   费</span></p>
						</div>
						<div class="layui-form-item layui-row" >
							<p class="layui-col-xs6 layui-col-sm6 layui-col-md6"><span>交费项目</span></p>
							<p class="layui-col-xs6 layui-col-sm6 layui-col-md6"><span id="fee_type"></span></p>
						</div>
						<div class="layui-form-item layui-row" >
							<p class="layui-col-xs6 layui-col-sm6 layui-col-md6"><span>交费明细</span></p>
						</div>
						<div class="layui-form-item layui-row" >
							<p class="layui-col-xs6 layui-col-sm6 layui-col-md6"><span id="fee_yearMonth"></span></p>
							<p class="layui-col-xs6 layui-col-sm6 layui-col-md6"><span class="fee_amount"></span></p>
						</div>
						<div class="layui-form-item layui-row" >
							<p class="layui-col-xs6 layui-col-sm6 layui-col-md6"><span>交费说明</span></p>
						</div>
						<div class="layui-form-item layui-row" >
							<p class="layui-col-xs12 layui-col-sm12 layui-col-md12">
								<span>
									按照规定向党组织交纳党费，是每个党员应尽的义务，也是党员党性和组织观念的体现。对不按照规定交纳党费的党员，其所在党组织应及时对其进行批评教育，限期改正。对无正当理由，连续6个月不交纳党费的党员，按自行脱党处理。请各位党员在规定时间内自觉交纳党费。
								</span>
							</p>
						</div>
						<div class="layui-form-item layui-row" >
							<p class="layui-col-xs6 layui-col-sm6 layui-col-md6"><span>交费截止时间</span></p>
							<p class="layui-col-xs6 layui-col-sm6 layui-col-md6"><span id="end_time"></span></p>
						</div>
						<div class="layui-form-item layui-row" >
							<p class="layui-col-xs6 layui-col-sm6 layui-col-md6"><span>交费金额</span></p>
							<p class="layui-col-xs6 layui-col-sm6 layui-col-md6"><span class="fee_amount"></span></p>
						</div>
						<div class="layui-form-item layui-row" >
							<p class="layui-col-xs6 layui-col-sm6 layui-col-md6"><span>交费状态</span></p>
							<p class="layui-col-xs6 layui-col-sm6 layui-col-md6"><span id="fee_state"></span></p>
						</div>
						<div class="layui-form-item layui-row">
							<button type="button" onclick="window.history.back();" id="button_pay"
									class="layui-btn layui-btn-primary" style="display:none; background-color: transparent;color: #666;padding: 0 20px;font-size: 16px;height: 40px;line-height: 40px;border-radius: 4px;">
								支付
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
		<script>
			layui.use(['layer','form'], function() {
				var layer = layui.layer,
						form = layui.form;
				form.on('submit(rejectForm)', function (data) {
					var url = "${PartyRejected}";
					$.ajax({
						url:url,
						data:{meeting_id2:"${meetingPlan.meeting_id}",should_:data.field.rejectReason},
						dataType:'json',
						async:false,
						success:function(res){
							if(res){
								layer.msg("驳回成功。");
								window.history.back();
							}
						}
					});

				})
				form.verify({
					select: function (value, item) {
						if (value == '' || value == null) {
							return "请选择必填项。";
						}
					}
				});
				$("#pass").click(function(){
					layer.confirm('确认通过？', {
						btn: ['确定','取消'] //按钮
					}, function(){
						$.ajax({
							url:"${PartyPass}",
							data:{ meetingId:"${meetingPlan.meeting_id}"},
							dataType:'json',
							success:function(res){
								if(res.code == 200){
									layer.msg("审核通过");
									window.history.back();
								}
							}
						});
					});
				});
				$("#reject").click(function(){
					layer.prompt({
						type: 1,
						btn: 0,
						content: $("#rejectModal")
					});
				});
			})
			function downloadFile(){
				var path = $(this).attr("path");
				var name = $(this).attr("name");
				window.location.href="${downloadUrl}&filePath="+path+"&fileName="+name;
			}
		</script>
	</div>
	</body>
</html>