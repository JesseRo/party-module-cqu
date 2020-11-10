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
			$.post('http://' + window.location.hostname + ":9007/fee/branch/members", function (res) {
				if (res.code === 0) {
					var html = '<option disabled selected>请选择</option>'
					for(var i = 0; i < res.data.length; i++) {
						var m = res.data[i];
						memberMap[m.memberId] = m;
						html += '<option value="' + m.memberId + '">' + m.memberName + '</option>';
					}
					$('#member_id').html(html);
					form.render('select');
				}
			})

			var form = layui.form;

			layui.use(['laydate', 'form'], function() {
				form = layui.form;

				var laydate = layui.laydate;
				var startDate, endDate;
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

				form.on('select(aihao)', function(data){
					var memberId = data.value;
					if (memberId) {
						$('#member_org').val(memberMap[memberId].orgName);
						$('#fee_type_name').val(memberMap[memberId].feeTypeName);
						$('#fee_type').val(memberMap[memberId].feeType);
					}
				});

				form.on('submit(submit)', function(data){
					var postData = {};
					if (startDate && endDate) {
						postData.memberId = data.field.member_id;
						postData.startDate = startDate;
						postData.endDate = endDate;

						$.ajax({
							'type': 'POST',
							'url': 'http://' + window.location.hostname + ":9007/fee/branch/back-fee",
							'contentType': 'application/json; charset=utf-8',
							'data': JSON.stringify(postData),
							'dataType': 'json',
							'success': function (res) {
								if (res.code === 0) {
									layuiModal.confirm("录入成功，是否继续录入？", function () {
										form.render('select');
										$('#fee_type_name').val('');
										$('#fee_type').val('');
										$('#member_id').val('');
										$('#date_range').val('');
									}, function () {
										window.history.back();
									});
								}else {
									layuiModal.alert(res.message);
								}
							}
						});
					} else {
						layuiModal.alert("请选择起止日期")
					}

					return false;
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
							<select name="member_id" lay-filter="aihao" id="member_id" lay-search>
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
							<input type="text" name="member_org" id="fee_type" style="display: none;">
							<input type="text" name="member_org" id="fee_type_name" readonly autocomplete="off" class="layui-input" lay-verify="title|required">
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label layui-required">补缴月份</label>
						<div class="layui-input-inline">
							<input type="text" name="date" id="date_range" autocomplete="off" class="layui-input" lay-verify="title|required">
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