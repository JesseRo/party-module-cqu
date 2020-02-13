<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
	<head>
	    <meta charset='utf-8' />
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	    <script type="text/javascript" src="${basePath}/js/echarts.js"></script>
			    <link rel="stylesheet" href="${basePath}/cqu/css/summary.min.css"/>

	<%-- <script type="text/javascript" src="${basePath}/js/jquery-3.2.1.js"></script> --%>
	    <%-- <link rel="stylesheet" href="${basePath}/css/echarts/common.css" /> --%>
<%--	    <link rel="stylesheet" href="${basePath}/css/echarts/summary_reports.css"/>--%>
<%--	    <link rel="stylesheet" href="${basePath}/css/index2.css">--%>
	    <script>
		    define._amd = define.amd;
		    define.amd = false;
		</script>
			 <!-- 引入样式 -->
<%--			  <script src="${basePath}/js/vue.js"></script>--%>
			  <!-- 引入组件库 -->
<%--			  <script src="${basePath}/js/index.js"></script>--%>
		<script>
		    define.amd = define._amd;
		</script>

	    <title>定制报表</title>
	    <style>
			.content_info.table_form_content{
				background: 0 !important;
			}
	    </style>
	</head>
	<body>
		<!-- 更多 -->
		<portlet:renderURL var="EchartMore">
		    <portlet:param name="mvcRenderCommandName" value="/EchartMoreCommand" />
		</portlet:renderURL>
		<!-- 详细 -->
		<portlet:renderURL var="EchartBar">
		    <portlet:param name="mvcRenderCommandName" value="/EchartDetailedBarCommand" />
		</portlet:renderURL>
	<div class="outer_content_container">
		<div class="summary_page" style="height: 83.4vh;margin-left: 2vw;margin-top: 2vh;">
			<ul class="total_account_group">
				<li>
					<p class="title_group">
						<img class="title_icon" src="../images/account-icon2.png"></span>
						<span>二级党组织党支部个数</span>
					</p>
					<p class="total_num">
						<span>29999</span>个
					</p>
				</li>
				<li>
					<p class="title_group">
						<img class="title_icon" src="../images/account-icon3.png"></span>
						<span>二级党组织党员数目</span>
					</p>
					<p class="total_num">
						<span>29999</span>个
					</p>
				</li>
			</ul>
			<div class="attend_views_activity">
				<div class="attend_views_group">
					<div class="attend_rate_group">
						<div class="operate_container">
							<p>
								党支部活动开展情况
							</p>
							<form class="layui-form custom_form" id="selectForm1">
								<div class="layui-form-item">
									<div class="layui-input-block">
										<select class="custom_select month_select" lay-filter="attend-month-select">
											<option value="0">9月</option>
											<option value="1">9月</option>
											<option value="2">9月</option>
											<option value="3">9月</option>
										</select>
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
						<div class="attend_rate_container" id="brunch_meeting_container"></div>
						<p class="desc_text"><a href="javascript:;" onclick="window.location.href='/attend_charts'">查看更多</a></p>
					</div>
					<div class="views_group">
						<div class="text_container">
							<p class="title">
								党员性别分布情况
<%--								<img src="../images/charts-title2.png"/>--%>
							</p>
							<div class="sub_text_container">
								<div class="sub_text current_view">
									<p class="sub_title">男性党员</p>
									<p class="sub_num">3888</p>
								</div>
								<div class="sub_text">
									<p class="sub_title">女性党员</p>
									<p class="sub_num">2222</p>
								</div>
							</div>
						</div>
						<div class="view_conteiner" id="sex_container"></div>
<%--						<p class="desc_text">同比昨日+7.9%</p>--%>
					</div>
				</div>
				<div class="activity_plan_group">
					<div class="operate_container">
						<p>
<%--							<img src="${basePath}/cqu/images/charts-title3.png"/>--%>
							组织生活类型分布情况
						</p>
						<form class="layui-form custom_form" id="selectForm2">
							<div class="layui-form-item">
								<div class="layui-input-block">
									<select class="custom_select month_select" lay-filter="activity-month-select">
										<option value="0">9月</option>
										<option value="1">9月</option>
										<option value="2">9月</option>
										<option value="3">9月</option>
									</select>
									<select class="custom_select terms_select" lay-filter="activity-terms-select">
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
					<div class="activity_plan_conteiner" id="activity_conteiner">
					</div>
					<p class="desc_text"><a href="javascript:;" onclick="window.location.href='/plan_charts'">查看更多</a></p>
				</div>
			</div>
		</div>
	</div>


		<script>
			$(document).ready(function () {
				renderAttendcharts();
				renderViewCharts();
				renderActivityCharts();
			});
			//表单
			layui.use('form', function(){
				var form = layui.form;
				//出勤率 月份 监听select
				form.on('select(attend-month-select)', function(data){
					console.log(data.value); //得到被选中的值
				});
				form.on('select(attend-terms-select)', function(data){
					console.log(data.value);
				});
				form.on('select(activity-month-select)', function(data){
					console.log(data.value);
				});
				form.on('select(activity-terms-select)', function(data){
					console.log(data.value);
				});
			});
			//渲染出勤率图表
			function renderAttendcharts(){
				var AttendChart = echarts.init(document.getElementById('brunch_meeting_container'));
				var option = {
					title: {
						// text: '活动出勤率统计图',
					},
					tooltip: {
						formatter: function(obj) {
						return '<div class="attend_tooltip">' +
                            '<p>' + obj.name + '</p>' +
                            '<p>' + obj.seriesName + obj.data + '% </p>' +
                        '</div>'
					}
				},
						grid: {
					left: 0,
							right: '10%',
							top:'15%',
							bottom: '20%',
							containLabel: true
				},
				xAxis: {
					axisTick:{
						show:true,
					},
					axisLabel: {
						interval:0,
								rotate:-40,
								textStyle: {
							color: '#666',
									fontSize:12,
						},
					},
					axisLine:{
						lineStyle:{
							color:'#f6f6f6',
									width:6,   //这里是坐标轴的宽度,可以去掉
						}
					},
					data: ["音乐学院委员会","物理科学与技术...","音乐学院委员会","物理科学与技术...","音乐学院委员会","物理科学与技术...","音乐学院委员会","物理科学与技术...","音乐学院委员会","物理科学与技术...","音乐学院委员会","物理科学与技术..."]
				},
				yAxis: {
					show:false,
					// axisLine:{       //y轴
					//     show:false
					// },
					// axisTick:{       //y轴刻度线
					//     show:false
					// },
					// splitLine: {     //网格线
					//     show: false
					// }
				},
				series: [{
					name: '出勤率',
					type: 'bar',
					data: [5, 20, 36, 10, 10, 20,5, 20, 100, 10, 10, 20],
					barWidth: 12,
					itemStyle:{
						marginBottom:6,
						normal:{
							barBorderRadius:[7, 7, 7, 7],
							color:'#FFAB33',
						},
						emphasis:{
							color:'#E74933'
						}
					}
				}]
			};
				AttendChart.setOption(option);
			}
			//渲染访问量 图表
			function renderViewCharts(){
				var ViewChart = echarts.init(document.getElementById('sex_container'));
				var option = {
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
									value: 220,
									name: "女"
								},
								{
									value: 312,
									name: "男"
								}
							]
						}
					]
				};
				ViewChart.setOption(option);
			}
			//渲染计划上报 图表
			function renderActivityCharts(){
				var ViewChart = echarts.init(document.getElementById('activity_conteiner'));

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
				ViewChart.setOption(option);
			}
		</script>
	</body>
</html>
					
				

