<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
	<head>
	    <meta charset='utf-8' />
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	    <%-- <script type="text/javascript" src="${basePath}/js/jquery-3.2.1.js"></script> --%>
	    <%-- <link rel="stylesheet" href="${basePath}/css/echarts/common.css" />
<%--	    <link rel="stylesheet" href="${basePath}/css/echarts/bootstrap.min.css" />--%>
		<link rel="stylesheet" href="${basePath}/cqu/css/party_branch_layout.css"/>
		<link rel="stylesheet" href="${basePath}/cqu/css/summary.min.css"/>
<%--		<link rel="stylesheet" href="${basePath}/css/echarts/summary_reports.css"/> --%>
		<script type="text/javascript" src="${basePath}/js/echarts.js"></script>

	</head>
	<body>
	<style>

	</style>
	<div class="table_form_content party_branch_outer_page">
		<div class="summary_page party_branch_page">
			<ul class="total_account_group">
				<li>
					<p class="title_group">
						<img class="title_icon" src="../images/account-icon3.png"></span>
						<span>支部党员人数</span>
					</p>
					<p class="total_num">
						<span>29999</span>个
					</p>
				</li>
				<li>
					<p class="title_group">
						<img class="title_icon" src="../images/account-icon3.png"></span>
						<span>支部组织生活次数</span>
					</p>
					<p class="total_num">
						<span>5555</span>个
					</p>
				</li>
			</ul>
			<div class="attend_views_activity">
				<div class="attend_views_group branch_activity_plan_group">
					<div class="attend_rate_group">
						<div class="operate_container">
							<p>
								党支部组织生活分布情况
							</p>
						</div>
						<div class="attend_rate_container" id="party_meeting_type"></div>
					</div>
<%--					<div class="views_group">--%>
<%--						<div class="operate_container">--%>
<%--							<p>--%>
<%--								支部党课开课情况--%>
<%--							</p>--%>
<%--							<form class="layui-form custom_form" id="selectForm1">--%>
<%--								<div class="layui-form-item">--%>
<%--									<div class="layui-input-block">--%>
<%--										<select class="custom_select terms_select" lay-filter="attend-terms-select">--%>
<%--											<option value="0">按学期</option>--%>
<%--											<option value="1">22</option>--%>
<%--											<option value="2">22</option>--%>
<%--											<option value="3">22</option>--%>
<%--											<option value="4">22222</option>--%>
<%--										</select>--%>
<%--									</div>--%>
<%--								</div>--%>
<%--							</form>--%>
<%--						</div>--%>
<%--						<div class="attend_rate_container" id="party_class_container"></div>--%>
<%--					</div>--%>
				</div>
				<div class="attend_views_group branch_activity_plan_group branch_party_relation_group">
					<div class="attend_rate_group">
						<div class="operate_container">
							<p>
								支部党员性别分布情况
							</p>
						</div>
						<div class="attend_rate_container" id="party_sex"></div>
					</div>
<%--					<div class="views_group">--%>
<%--						<div class="operate_container">--%>
<%--							<p>--%>
<%--								支部党员党龄分布情况--%>
<%--							</p>--%>
<%--						</div>--%>
<%--						<div class="attend_rate_container" id="party_age_container"></div>--%>
<%--					</div>--%>
				</div>
			</div>
		</div>
	</div>
	<script>
		$(document).ready(function () {
			// renderAttendcharts();
			// renderViewCharts();
			renderSexCharts();
			renderActivityCharts();
			// renderPartyAgeCharts();
		});

		function renderSexCharts() {
			var AttendChart = echarts.init(
					document.getElementById("party_sex")
			);
			option = {
				tooltip: {
					trigger: "item",
					formatter: function formatter(obj) {
						return '<div class="attend_tooltip">\n                            <p>'
								.concat(obj.name, ":")
								.concat(obj.percent, "%</p>\n                        </div>");
					}
				},
				legend: {
					show: false
				},
				color: ["#FF523A", "#FFAA17"],
				series: [
					{
						name: "支部党员性别分布情况",
						type: "pie",
						avoidLabelOverlap: false,
						label: {
							show: true,
							position: "outside"
						},
						labelLine: {
							normal: {
								show: true
							}
						},
						data: [
							{
								value: 200,
								name: "女"
							},
							{
								value: 200,
								name: "男"
							}
						]
					}
				]
			};
			AttendChart.setOption(option);
		} //渲染 党龄 图表

		function renderActivityCharts() {
			var AttendChart = echarts.init(
					document.getElementById("party_meeting_type")
			);
			option = {
				tooltip: {
					trigger: "item",
					formatter: function formatter(obj) {
						return '<div class="attend_tooltip">\n                            <p>'
								.concat(obj.name, ":")
								.concat(obj.percent, "%</p>\n                        </div>");
					}
				},
				legend: {
					show: false
				},
				// color: ["#FF523A", "#FFAA17"],
				series: [
					{
						name: "党支部组织生活分布情况",
						type: "pie",
						avoidLabelOverlap: false,
						label: {
							show: true,
							position: "outside"
						},
						labelLine: {
							normal: {
								show: true
							}
						},
						data: [{
								value: Math.round(Math.random() * 100),
								name: "党员大会"
							},
							{
								value: Math.round(Math.random() * 100),
								name: "支委会"
							},
							{
								value: Math.round(Math.random() * 100),
								name: "党小组会"
							},
							{
								value: Math.round(Math.random() * 100),
								name: "党课"
							},
							{
								value: Math.round(Math.random() * 100),
								name: "民主评议党员"
							},
							{
								value: Math.round(Math.random() * 100),
								name: "组织生活会"
							},
							{
								value: Math.round(Math.random() * 100),
								name: "主题党日"
							},
							{
								value: Math.round(Math.random() * 100),
								name: "谈心谈话"
							},
							{
								value: Math.round(Math.random() * 100),
								name: "民主生活会"
							},
							{
								value: Math.round(Math.random() * 100),
								name: "党委中心组学习"
							}
						]
					}
				]
			};
			AttendChart.setOption(option);
		} //渲染 组织生活 图表


	</script>
	</body>
</html>
					
				

