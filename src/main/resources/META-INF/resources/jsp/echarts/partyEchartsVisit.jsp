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
			height: calc(100% - 60px);
			margin-top: 40px;
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

	</style>
	<div class="table_form_content">
		<div class="activity_manage_page">
			<div class="breadcrumb_group">
				当前位置：
				<span class="layui-breadcrumb" lay-separator=">">
							<a href="javascript:;">系统管理</a>
							<a href="javascript:;">访问统计</a>
						</span>
			</div>
			<div class="operate_btns">
				<button type="button" class="layui-btn">正常</button>
				<button type="button" class="layui-btn">排列</button>
			</div>
			<div class="charts_container">
				<div class="view_charts" id="viewEcharts" style="width: 100%; height: 100%;"></div>
			</div>
		</div>
	</div>
	<script>
		$(document).ready(function () {
			renderViewCharts();
		});
		function renderViewCharts(){
			var viewEchart = echarts.init(document.getElementById('viewEcharts'));
			var option = {
				tooltip: {
					formatter:function(data) {
						console.log(data,'data')
						return '<div class="tooltip_container">' +
                        '<p>' + data.name + '</p>' +
                        '<p>' + data.seriesName + ':' + data.value + '</p>' +
                    '</div>'
					}
				},
				legend: {
					width:48,
					height:20,
					textStyle:{
						fontSize:16,
					},
					data: ['访问次数']
				},
				grid: {
					left: 0,
					right: 0,
					bottom: '6%',
					containLabel: true
				},
				xAxis: {
					type: 'value',
					axisTick:{
						show:false,
					},
					splitLine:{
						show:false
					},
					axisLabel:{
						show:false,
					},
					axisLine:{
						lineStyle:{
							color:'#FFAB33'
						}
					},
				},
				yAxis: {
					type: 'category',
					axisTick:{
						show:false,
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
					data: ['资源环境学院党委','植物保护学院党委','园艺园林学院党委','音乐学院党委','医院总支学院党委','新闻传媒学院党委','资源环境学院党委','植物保护学院党委','园艺园林学院党委','音乐学院党委','医院总支学院党委','新闻传媒学院党委']
				},
				series: [
					{
						name: '访问次数',
						type: 'bar',
						barWidth:32,
						itemStyle: {
							normal: {
								color: new echarts.graphic.LinearGradient(
										0, 1, 1, 0,
										[
											{offset: 0, color: '#FFAB33'},
											{offset: 1, color: '#E74933'}
										]
								)
							},
							emphasis: {
								color: '#E74933'
							}
						},
						data: [18203, 23489, 29034, 104970, 131744, 630230,18203, 23489, 29034, 104970, 131744, 630230]
					},
				]
			};
			viewEchart.setOption(option);
		}

	</script>
	</body>
</html>
					
				

