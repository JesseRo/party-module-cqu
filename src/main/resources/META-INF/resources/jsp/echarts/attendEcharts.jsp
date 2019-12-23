<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
	<head>
	    <meta charset='utf-8' />
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	    <%-- <script type="text/javascript" src="${basePath}/js/jquery-3.2.1.js"></script> --%>
	    <%-- <link rel="stylesheet" href="${basePath}/css/echarts/common.css" />
	    <link rel="stylesheet" href="${basePath}/css/echarts/bootstrap.min.css" />
	    <link rel="stylesheet" href="${basePath}/css/echarts/summary_reports.css"/> --%>
		<script type="text/javascript" src="${basePath}/js/echarts.js"></script>

		<title>访问量统计</title>
	</head>
	<body>
	<style>
		.activity_manage_page .charts_container{
			width: 100%;
			height: calc(100% - 20px)!important;
			margin-top: 0!important;
		}
		.activity_manage_page .operate_btns {
			position: absolute;
			width: 100%;
			height: 40px;
			text-align: right;
			top: 40px;
			right: 0;
			z-index: 10;
		}
		.activity_manage_page .operate_btns .layui-btn{
			height: 40px;
			background: #fbaa33;
			font-size: 16px;
			width: 90px;
			border-radius: 6px;
		}
		.main_content{
			background: transparent!important;
		}

	</style>
	<div class="table_form_content">
		<div class="activity_manage_page">
			<div class="breadcrumb_group">
				当前位置：
				<span class="layui-breadcrumb" lay-separator=">">
							<a href="javascript:;">系统管理</a>
							<a href="javascript:;">活动出勤率</a>
						</span>
			</div>
			<div class="charts_container attend_charts_container">
				<div class="view_charts" id="attendEcharts" style="width: 100%; height: 100%;"></div>
			</div>
		</div>
	</div>
	<script>
		$(document).ready(function () {
			renderViewCharts();
		});
		function renderViewCharts(){
			var AttendChart = echarts.init(document.getElementById('attendEcharts'));
			option = {
				title: {
					// text: '活动出勤率统计图',
				},
				tooltip: {
					formatter:function(obj) {
					return '<div class="attend_tooltip">' +
                            '<p>' + obj.name + '</p>' +
                            '<p>' + obj.seriesName + ':' + obj.data + '%</p>' +
                        '</div>';
				}
			},
			grid: {
				left: '2%',
				right: '6%',
				bottom: '6%',
				containLabel: true
			},
			xAxis: {
				axisTick:{       //y轴刻度线
					show:false
				},
				splitLine: {     //网格线
					show: false
				},
				axisLabel:{
					fontSize:16,
					formatter:function(data){
						return data + '%'
					}
				},
				axisLine:{
					lineStyle:{
						color:'#FFAB33'
						// width:6,
					}
				},
			},
			yAxis: {
				axisTick:{
					show:false
				},
				axisLabel:{
					fontSize:16,
					color:'#333'
				},
				axisLine:{
					lineStyle:{
						color:'#FFAB33'
					}
				},
				data: ["音乐学院委员会","物理科学与技术学院","音乐学院委员会","物理科学与技术","音乐学院委员会","物理科学与技术","音乐学院委员会","物理科学与技术","音乐学院委员会","物理科学与技术","音乐学院委员会","物理科学与技术","物理科学与技术","音乐学院委员会","物理科学与技术","音乐学院委员会","物理科学与技术"]
			},
			series: [{
				name: '出勤率',
				type: 'bar',
				data: [5, 20, 36, 10, 10, 20,5, 20, 100, 10, 10, 20, 36, 10, 10, 20,5],
				barWidth: 12,
				itemStyle:{
					marginBottom:6,
					normal:{
						barBorderRadius:[7, 7, 7, 7],
						color:'#FFAB33'
					},
					emphasis:{
						color:'#E74933'
					}
				}
			}]
		};
			AttendChart.setOption(option);
		}

	</script>
	</body>
</html>
					
				

