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
							<img class="title_icon" src="../images/account-icon1.png"></span>
							<span>全校二级党组织总数</span>
						</p>
						<p class="total_num">
							<span>${orgNumber}</span>个
						</p>
					</li>
					<li>
						<p class="title_group">
							<img class="title_icon" src="../images/account-icon2.png"></span>
							<span>全校党支部总数</span>
						</p>
						<p class="total_num">
							<span>${branchNumber}</span>个
						</p>
					</li>
					<li>
						<p class="title_group">
							<img class="title_icon" src="../images/account-icon3.png"></span>
							<span>全校党员总数</span>
						</p>
						<p class="total_num">
							<span>${userStatistics.count}</span>个
						</p>
					</li>
			</ul>
			<div class="attend_views_activity">
				<div class="attend_views_group">
					<div class="attend_rate_group">
						<div class="operate_container">
							<p>二级党组织组织生活开展次数</p>
						</div>
						<div class="attend_rate_container" id="brunch_meeting_container"></div>
						<p class="desc_text"><a id="collegeMore" href="/attend_charts">查看更多</a></p>
					</div>
					<div class="views_group">
						<div class="text_container">
							<p class="title">党员性别分布</p>
							<div class="sub_text_container">
								<div class="sub_text current_view">
									<p class="sub_title">男性党员</p>
									<p class="sub_num">${userStatistics.maleCount}</p>
								</div>
								<div class="sub_text">
									<p class="sub_title">女性党员</p>
									<p class="sub_num">${userStatistics.femaleCount}</p>
								</div>
							</div>
						</div>
						<div class="view_conteiner" id="sex_container"></div>
						<%--						<p class="desc_text">同比昨日+7.9%</p>--%>
					</div>
				</div>
				<div class="activity_plan_group">
					<div class="operate_container">
						<p>组织生活类型分布</p>
					</div>
					<div class="activity_plan_conteiner" id="activity_conteiner">
					</div>
<%--					<p class="desc_text"><a href="/activitiesTypeStatisticDetail'">查看更多</a></p>--%>
				</div>
			</div>
		</div>
	</div>


		<script>
			$(document).ready(function () {
				showCollegeCharts();
				renderViewCharts();
				showActivityCharts();
			});
			//表单
			layui.use('form', function(){
				var form = layui.form;
				form.on('select(attend-month-select)', function(data){
					var year = $("#selectForm1 .terms_select").val();
					var month = data.value;
					showCollegeCharts(year,month);
				});
				form.on('select(attend-terms-select)', function(data){
					var year = data.value;
					var month = $("#selectForm1 .month_select").val();
					showCollegeCharts(year,month);
				});
				form.on('select(activity-month-select)', function(data){
					var year = $("#selectForm2 .terms_select").val();
					var month = data.value;
					showActivityCharts(year,month);
				});
				form.on('select(activity-terms-select)', function(data){
					var year = data.value;
					var month = $("#selectForm2 .month_select").val();
					showActivityCharts(year,month);
				});
			});


			function showCollegeCharts(){
				var arr = JSON.parse('${activitiesStatisticsList}');
				var colData = new Array();
				var rowData =new Array();
				if(arr != null && arr.length>0){
					for(var i=0;i<arr.length;i++){
						colData.push(arr[i].property);
						rowData.push(arr[i].num);
					}
				}
				renderCollegeCharts(colData,rowData)
			}
			function showActivityCharts(){
				var arr = JSON.parse('${activitiesTypeStatisticList}');
				var eChartData = new Array();
				if(arr != null && arr.length>0){
					for(var i=0;i<arr.length;i++){
						eChartData.push({
							name:arr[i].property,
							value:arr[i].num
						});
					};
				}
				renderActivityCharts(eChartData);
			}
			//学院活动开展情况
			function renderCollegeCharts(colData,rowData){
				var AttendChart = echarts.init(document.getElementById('brunch_meeting_container'));
				var option = {
					title: {
						// text: '党支部活动开展情况',
					},
					tooltip: {
						formatter: function(obj) {
							return '<div class="attend_tooltip">' +
									'<p>' + obj.name + '</p>' +
									'<p>' + obj.data + '次 </p>' +
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
						data: colData
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
						name: '',
						type: 'bar',
						data: rowData,
						barWidth: 12,
						barMinHeight: 2,
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
									value: parseInt('${userStatistics.femaleCount}'),
									name: "女"
								},
								{
									value: parseInt('${userStatistics.maleCount}'),
									name: "男"
								}
							]
						}
					]
				};
				ViewChart.setOption(option);
			}
			//渲染党组织活动类型统计
			function renderActivityCharts(data){
				if(data.length<1){
					$("#activity_conteiner").text("暂无数据。");
					return
				}
				var ViewChart = echarts.init(document.getElementById('activity_conteiner'));
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
					// color: ["#FF523A", "#FFAA17"],
					series: [
						{
							name: "党支部组织生活类型分布",
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
							data: data
						}
					]
				};
				ViewChart.setOption(option);
			}
		</script>

	    
	</body>
</html>
					
				

