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
		var id;

		function donatePay() {
			layuiModal.prompt("请输入捐款金额（元）", "", function (v) {
				$.ajax({
					type: "post",
					url: sessionStorage.getItem("feeUrl") + '/fee/member/donate-transaction',
					data: {
						amount: v,
						donateId: id
					},
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
		}
		function getQueryVariable(variable)
		{
			var query = window.location.search.substring(1);
			var vars = query.split("&");
			for (var i=0;i<vars.length;i++) {
				var pair = vars[i].split("=");
				if(pair[0] == variable){return pair[1];}
			}
		}
		$(function () {
			$.get(sessionStorage.getItem("feeUrl") + '/fee/member/donate/detail?id=' + getQueryVariable('id'), function (res) {
				if (res.code === 0) {
					if (res.data.state == 1) {
						$('#button_pay').show();
					}
					$('#donate_title').text(res.data.title);
					$('#donate_comment').text(res.data.donateComment);
					$('#donate_time').text(res.data.startTime + ' - ' + res.data.endTime);
					if (res.data.file) {
						$('#donate_file').attr('href', res.data.file);
						$('#donate_file').show();
						$('#no_file').hide();
					}
					id = res.data.id;
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
<%--						<div class="layui-form-item layui-row" >--%>
<%--							<p class="layui-col-xs6 layui-col-sm6 layui-col-md6"><span>党员姓名</span></p>--%>
<%--							<p class="layui-col-xs6 layui-col-sm6 layui-col-md6"><span id="member_name"></span></p>--%>
<%--						</div>--%>
<%--						<div class="layui-form-item layui-row" >--%>
<%--							<p class="layui-col-xs6 layui-col-sm6 layui-col-md6"><span>所在组织</span></p>--%>
<%--							<p class="layui-col-xs6 layui-col-sm6 layui-col-md6"><span id="member_org"></span></p>--%>
<%--						</div>--%>
						<div class="layui-form-item layui-row" >
							<p class="layui-col-xs3 layui-col-sm3 layui-col-md3"><span>捐款项目</span></p>
							<p class="layui-col-xs6 layui-col-sm6 layui-col-md6"><span id="donate_title"></span></p>
						</div>
						<div class="layui-form-item layui-row" >
							<p class="layui-col-xs3 layui-col-sm3 layui-col-md3"><span>捐款说明</span></p>
							<p class="layui-col-xs6 layui-col-sm6 layui-col-md6"><span id="donate_comment"></span></p>
						</div>
						<div class="layui-form-item layui-row" >
							<p class="layui-col-xs3 layui-col-sm3 layui-col-md3"><span>捐款起止时间</span></p>
							<p class="layui-col-xs6 layui-col-sm6 layui-col-md6"><span id="donate_time"></span></p>
						</div>
						<div class="layui-form-item layui-row">
							<p class="layui-col-xs3 layui-col-sm3 layui-col-md3"><span>附件下载</span></p>
							<p class="layui-col-xs6 layui-col-sm6 layui-col-md6"><a id="donate_file" style="display: none;">捐款倡议书</a><span id="no_file">无附件</span></p>
						</div>
						<div class="layui-form-item layui-row">
							<button type="button" id="button_pay" onclick="donatePay()"
									class="layui-btn layui-btn-warm" style="display:none;">
								我要捐款
							</button>
							<button type="button" onclick="window.history.back();"
									class="layui-btn layui-btn-primary" style="background-color: transparent;color: #666;padding: 0 20px;font-size: 16px;height: 40px;line-height: 40px;border-radius: 4px;">
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