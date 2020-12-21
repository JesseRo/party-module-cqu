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
			.layui-form-label {
				width: 110px;
			}
			.layui-input-block {
				width: 500px;
			}
			.layui-laydate-content table {
				table-layout: fixed;
				width: 100%;
			}
		 </style>
	</head>
	<body>
	<script>
		$(function () {
			function toDateStr(date){
				if (date.year) {
					return date.year + '-' + date.month + '-01' ;
				}else {
					return "";
				}
			}
			var memberMap = {};
			$.post(sessionStorage.getItem("feeUrl") + "/fee/branch/orgMembers", function (res) {
				if (res.code === 0) {
					var html = '<option disabled selected>请选择</option>'
					for(var i = 0; i < res.data.length; i++) {
						var m = res.data[i];
						memberMap[m.memberId] = m;
						html += '<option value="' + m.memberId + '">' + m.memberName + '</option>';
					}
					$('#memberId').html(html);
					form.render('select');
				}
			})

			var form = layui.form;

			layui.use(['laydate', 'form'], function() {
				form = layui.form;

				form.on('select(aihao)', function(data){
					var memberId = data.value;
					if (memberId) {
						$('#member_org').val(memberMap[memberId].orgName);
					}
				});

				form.on('submit(submit)', function(data){
					var postData = {};
					postData.memberId = data.field.memberId;
					postData.fee =  Math.round(Number(data.field.fee) * 100);
					postData.feeType = data.field.feeType;

					$.ajax({
						'type': 'POST',
						'url': sessionStorage.getItem("feeUrl") + "/fee/branch/config-set",
						'contentType': 'application/json; charset=utf-8',
						'data': JSON.stringify(postData),
						'dataType': 'json',
						'success': function (res) {
							if (res.code === 0) {
								layuiModal.confirm("录入成功，是否继续录入？", function () {
									form.render('select');
									$('#feeType').val('');
									$('#member_org').val('');
									$('#memberId').val('');
									$('#fee').val('');
								}, function () {
									window.history.back();
								});
							}else {
								layuiModal.alert(res.message);
							}
						}
					});
				});
			});
		})
	</script>
	<div class="table_form_content">
		<%--   查看计划详情  --%>
		<div class="activity_manage_page">
			<div class="breadcrumb_group" style="margin-bottom: 20px;">
				当前位置：
				<span class="layui-breadcrumb" lay-separator=">">
					<a href="javascript:;">党费管理</a>
					<a href="javascript:;">补缴录入</a>
				</span>
			</div>
			<div class="bg_white_container release_event_form">
				<form class="layui-form form-horizontal new_publish_form" action="" id="donate_form">
					<div class="layui-form-item">
						<label class="layui-form-label layui-required">党员姓名</label>
						<div class="layui-input-block" id="member_div">
							<select name="memberId" lay-filter="aihao" id="memberId" lay-search lay-verify="required">
								<option value="" disabled>输入关键字搜索</option>
							</select>
						</div>
					</div>
					<div class="layui-form-item layui-form-text">
						<label class="layui-form-label layui-required">所在组织</label>
						<div class="layui-input-block">
							<input type="text" name="member_org" id="member_org" readonly autocomplete="off" class="layui-input" lay-verify="title|required">
						</div>
					</div>
					<div class="layui-form-item layui-form-text">
						<label class="layui-form-label layui-required">党费类型</label>
						<div class="layui-input-block">
							<select id="feeType" name="feeType" lay-verify="required">
								<option disabled selected>请选择类型</option>
								<option value="1">月薪制党员</option>
								<option value="2">年薪制党员</option>
								<option value="3">企业员工/其他协议工资党员</option>
								<option value="4">离退休教职工党员</option>
								<option value="5">学生党员</option>
								<option value="6">在职就读硕士博士党员</option>
							</select>
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label layui-required">党费</label>
						<div class="layui-input-inline">
							<input type="text" name="fee" id="fee" autocomplete="off" class="layui-input" lay-verify="number|required">
						</div>
					</div>
					<div class="layui-form-item">
						<div class="layui-input-block">
							<button type="button" class="layui-btn layui-btn layui-btn-warm" lay-submit="" lay-filter="submit">补缴录入</button>
							<button type="button" class="layui-btn layui-btn-primary" onclick="window.history.back();">返回</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	</body>
</html>