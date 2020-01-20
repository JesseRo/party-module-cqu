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
						<img class="title_icon" src="../images/account-icon4.png"></span>
						<span>全校党员数目</span>
					</p>
					<p class="total_num">
						<span>29999</span>个
					</p>
				</li>
			</ul>
			<div class="attend_views_activity">
				<div class="attend_views_group branch_activity_plan_group">
					<div class="attend_rate_group">
						<div class="operate_container">
							<p>
								活动出勤率
							</p>
							<form class="layui-form custom_form" id="selectForm1">
								<div class="layui-form-item">
									<div class="layui-input-block">
										<select class="custom_select terms_select" lay-filter="attend-terms-select">
											<option value="0">按学期</option>
											<option value="1">22</option>
											<option value="2">22</option>
											<option value="3">22</option>
											<option value="4">22222</option>
										</select>
									</div>
								</div>
							</form>
						</div>
						<div class="attend_rate_container" id="attend_rate_container"></div>
					</div>
					<div class="views_group">
						<div class="operate_container">
							<p>
								支部党课开课情况
							</p>
							<form class="layui-form custom_form" id="selectForm1">
								<div class="layui-form-item">
									<div class="layui-input-block">
										<select class="custom_select terms_select" lay-filter="attend-terms-select">
											<option value="0">按学期</option>
											<option value="1">22</option>
											<option value="2">22</option>
											<option value="3">22</option>
											<option value="4">22222</option>
										</select>
									</div>
								</div>
							</form>
						</div>
						<div class="attend_rate_container" id="party_class_container"></div>
					</div>
				</div>
				<div class="attend_views_group branch_activity_plan_group branch_party_relation_group">
					<div class="attend_rate_group">
						<div class="operate_container">
							<p>
								支部党员组织关系转接情况
							</p>
						</div>
						<div class="attend_rate_container" id="party_relation_container"></div>
					</div>
					<div class="views_group">
						<div class="operate_container">
							<p>
								支部党员党龄分布情况
							</p>
						</div>
						<div class="attend_rate_container" id="party_age_container"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script>
		$(document).ready(function () {
			renderAttendcharts();
			renderViewCharts();
			renderActivityCharts();
			renderPartyAgeCharts();
		});

		//渲染出勤率图表
		function renderAttendcharts() {
			var AttendChart = echarts.init(
					document.getElementById("attend_rate_container")
			);
			option = {
				title: {
					// text: '活动出勤率统计图',
				},
				tooltip: {
					formatter: function formatter(obj) {
						return '<div class="attend_tooltip">\n                            <p>\u652F\u90E8'
								.concat(
										obj.name,
										"\u51FA\u52E4\u7387</p>\n                            <p>"
								)
								.concat(obj.seriesName, ":")
								.concat(obj.data, "%</p>\n                        </div>");
					}
				},
				grid: {
					left: 0,
					right: "10%",
					top: "15%",
					bottom: "15%",
					containLabel: true
				},
				xAxis: {
					axisTick: {
						show: false
					},
					axisLabel: {
						interval: 0,
						rotate: -40,
						textStyle: {
							color: "#666",
							fontSize: 12
						}
					},
					axisLine: {
						lineStyle: {
							color: "FFD8C9"
						}
					},
					data: [
						"一月",
						"二月",
						"三月",
						"四月",
						"五月",
						"六月",
						"七月",
						"八月",
						"九月",
						"十月",
						"十一月",
						"十二月"
					]
				},
				yAxis: {
					min: 0,
					max: 100,
					interval: 25,
					splitNumber: 5,
					axisLine: {
						//y轴
						lineStyle: {
							color: "#FFD8C9"
						}
					},
					axisLabel: {
						formatter: function formatter(i) {
							return i + "%";
						},
						textStyle: {
							color: "#666"
						}
					},
					axisTick: {
						//y轴刻度线
						show: false
					},
					splitLine: {
						//网格线
						show: false
					}
				},
				series: [
					{
						name: "出勤率",
						type: "bar",
						data: [5, 20, 36, 10, 10, 20, 5, 20, 100, 10, 10, 20],
						barWidth: 12,
						itemStyle: {
							marginBottom: 6,
							normal: {
								barBorderRadius: [7, 7, 7, 7],
								color: "#FFAB33"
							},
							emphasis: {
								color: "#E74933"
							}
						}
					}
				]
			};
			AttendChart.setOption(option);
		} //渲染访问量 图表

		function renderViewCharts() {
			var ViewChart = echarts.init(
					document.getElementById("party_class_container")
			);
			option = {
				title: {
					// text: '活动出勤率统计图',
				},
				tooltip: {
					formatter: function formatter(obj) {
						return '<div class="attend_tooltip">\n                            <p>\u652F\u90E8'
								.concat(
										obj.name,
										"\u515A\u8BFE\u5F00\u8BFE\u60C5\u51B5</p>\n                            <p>"
								)
								.concat(obj.seriesName, ":")
								.concat(obj.data, "\u5B66\u65F6</p>\n                        </div>");
					}
				},
				grid: {
					left: 0,
					right: "10%",
					top: "15%",
					bottom: "15%",
					containLabel: true
				},
				xAxis: {
					axisTick: {
						show: false
					},
					axisLabel: {
						interval: 0,
						rotate: -40,
						textStyle: {
							color: "#666",
							fontSize: 12
						}
					},
					axisLine: {
						lineStyle: {
							color: "FFD8C9"
						}
					},
					data: [
						"一月",
						"二月",
						"三月",
						"四月",
						"五月",
						"六月",
						"七月",
						"八月",
						"九月",
						"十月",
						"十一月",
						"十二月"
					]
				},
				yAxis: {
					min: 0,
					max: 40,
					interval: 10,
					splitNumber: 5,
					axisLine: {
						//y轴
						lineStyle: {
							color: "#FFD8C9"
						}
					},
					axisLabel: {
						formatter: function formatter(i) {
							return i + "学时";
						},
						textStyle: {
							color: "#666"
						}
					},
					axisTick: {
						//y轴刻度线
						show: false
					},
					splitLine: {
						//网格线
						show: false
					}
				},
				series: [
					{
						name: "开课学时",
						type: "bar",
						data: [5, 20, 36, 10, 10, 20, 5, 20, 40, 10, 10, 20],
						barWidth: 12,
						itemStyle: {
							marginBottom: 6,
							normal: {
								barBorderRadius: [7, 7, 7, 7],
								color: "#FFAB33"
							},
							emphasis: {
								color: "#E74933"
							}
						}
					}
				]
			};
			ViewChart.setOption(option);
		} //渲染党员组织关系转接 图表

		function renderActivityCharts() {
			var AttendChart = echarts.init(
					document.getElementById("party_relation_container")
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
						name: "支部党员组织关系转接情况",
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

		function renderPartyAgeCharts() {
			var AgeChart = echarts.init(document.getElementById("party_age_container"));
			option = {
				title: {
					// text: '活动出勤率统计图',
				},
				tooltip: {
					formatter: function formatter(obj) {
						return '<div class="attend_tooltip">\n                            <p>\u652F\u90E8\u515A\u5458\u515A\u9F84</p>\n                            <p>'
								.concat(obj.name, ":")
								.concat(obj.data, "\u4EBA</p>\n                        </div>");
					}
				},
				grid: {
					left: 0,
					right: "10%",
					top: "15%",
					bottom: "15%",
					containLabel: true
				},
				xAxis: {
					axisTick: {
						show: false
					},
					axisLabel: {
						interval: 0,
						rotate: -40,
						textStyle: {
							color: "#666",
							fontSize: 12
						}
					},
					axisLine: {
						lineStyle: {
							color: "FFD8C9"
						}
					},
					data: ["小于1年", "1-5年", "5-10年", "10-15年", "15-20年", "大于20年"]
				},
				yAxis: {
					min: 0,
					max: 100,
					interval: 25,
					splitNumber: 5,
					axisLine: {
						//y轴
						lineStyle: {
							color: "#FFD8C9"
						}
					},
					axisLabel: {
						formatter: function formatter(i) {
							return i + "人";
						},
						textStyle: {
							color: "#666"
						}
					},
					axisTick: {
						//y轴刻度线
						show: false
					},
					splitLine: {
						//网格线
						show: false
					}
				},
				series: [
					{
						name: "支部党员党龄分布情况",
						type: "bar",
						data: [5, 20, 36, 10, 10, 100],
						barWidth: 12,
						itemStyle: {
							marginBottom: 6,
							normal: {
								barBorderRadius: [7, 7, 7, 7],
								color: "#FFAB33"
							},
							emphasis: {
								color: "#E74933"
							}
						}
					}
				]
			};
			AgeChart.setOption(option);
		}

	</script>
	</body>
</html>
					
				

