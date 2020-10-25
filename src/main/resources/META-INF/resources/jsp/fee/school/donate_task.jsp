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
			function toDateStr(date){
				if (date.year) {
					return date.year + '-' + date.month + '-' + date.date;
				}else {
					return "";
				}
			}
			layui.use(['upload', 'laydate', 'transfer', 'form'], function() {
				var upload = layui.upload;
				var transfer = layui.transfer;
				upload.render({
					elem: '#upload_button'
					,url: 'http://' + window.location.host + ":9007/app/file/upload" //改成您自己的上传接口
					,accept: 'file' //普通文件
					,done: function(res){
						$('#donate_file').val(res.data);
						layer.msg('上传成功');
						console.log(res.data);
					}
				});

				var laydate = layui.laydate;
				var startDate, endDate;
				laydate.render({
					elem: '#date_range'
					, range: '-',
					done: function (value, date, e) {
						console.log(value); //得到日期生成的值，如：2017-08-18
						console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
						console.log(e); //得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。
						startDate = toDateStr(date);
						endDate = toDateStr(e);
					}
				});

				$.post('http://' + window.location.host + ":9007/fee/school/donate/org", function (res) {
					if (res.code === 200) {
						transfer.render({
							elem: '#donate_range',
							title: ['应到人员', '实到人员'],
							height: 210,
							id: 'attendance',
							data: res.data.all,
							value: res.data.selected
						});
						transfer.render({
							elem: '#donate_range'
							,data: res.data
						})
					}
				})

				form.on('submit(submit)', function(data){
					var postData = {};
					if (startDate && endDate) {
						postData.title = data.field.title;
						postData.startDate = startDate;
						postData.endDate = endDate;
						postData.comment = data.field.comment;
						postData.file = $('#donate_file').val();
						postData.org = transfer.getData('donate_range')
								.map(function(att){return att.value})
						$.ajax({
							'type': 'POST',
							'url': 'http://' + window.location.host + ":9007/fee/school/donate/task",
							'contentType': 'application/json; charset=utf-8',
							'data': JSON.stringify(data),
							'dataType': 'json',
							'success': function (res) {
								if (res.code === 200) {
									layuiModal.alert("发布成功");
								}else {
									layuiModal.alert(res.message);
								}
							}
						});
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
					<a href="javascript:;">新建捐款</a>
				</span>
			</div>
			<div class="bg_white_container release_event_form">
				<form class="layui-form form-horizontal new_publish_form" action="" id="donate_form">
					<div class="layui-form-item">
						<label class="layui-form-label">捐款项目</label>
						<div class="layui-input-block">
							<input type="text" name="title" lay-verify="title" autocomplete="off"
								   placeholder="捐款批次/类型说明" class="layui-input">
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label">起止时间</label>
						<div class="layui-input-inline">
							<input type="text" name="date" id="date_range" autocomplete="off" class="layui-input">
						</div>
					</div>
					<div class="layui-form-item layui-form-text">
						<label class="layui-form-label">捐款说明</label>
						<div class="layui-input-block">
							<textarea placeholder="请输入捐款说明" name="comment" class="layui-textarea"></textarea>
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label">上传附件</label>
						<input name="file" style="display: none;" id="donate_file">
						<div class="layui-input-block">
							<button type="button" class="layui-btn" id="upload_button"><i class="layui-icon"></i>上传文件</button>
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label">捐款范围</label>
						<div class="layui-input-block">
							<div id="test1" class="demo-transfer" id="donate_range"></div>
						</div>
					</div>
					<div class="layui-form-item">
						<div class="layui-input-block">
							<button type="button" class="layui-btn" lay-submit="" lay-filter="submit">立即提交</button>
							<button type="button" class="layui-btn layui-btn-primary" onclick="window.history.back();">返回</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	</body>
</html>