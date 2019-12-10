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
		.charts_container.activity_charts_container{
			margin-top: 0!important;
		}
		.charts_container .view_charts.activity_plan_conteiner > div{
			width: 33.33%;
			height: 33.33%;
			display: inline-block;
			float: left;
		}
		.charts_container .view_charts.activity_plan_conteiner{
			height: 100%;
			width: 100%;
		}
		.activity_manage_page .charts_container{
			width: 100%;
			height: calc(100% - 20px)!important;
			margin-top: 0!important;
		}
		#activity_plan_conteiner{
			width: 100%;
			height: 100%;
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
                        <a href="javascript:;">活动计划上报</a>
                    </span>
			</div>
			<!-- <div class="operate_btns">
                <button type="button" class="layui-btn">正常</button>
                <button type="button" class="layui-btn">排列</button>
            </div> -->
			<div class="charts_container activity_charts_container">
				<div class="view_charts activity_plan_conteiner" id="activity_plan_conteiner">
					<div id="activity_plan_conteiner1"></div>
					<div id="activity_plan_conteiner2"></div>
					<div id="activity_plan_conteiner3"></div>
					<div id="activity_plan_conteiner4"></div>
					<div id="activity_plan_conteiner5"></div>
					<div id="activity_plan_conteiner6"></div>
					<div id="activity_plan_conteiner7"></div>
					<div id="activity_plan_conteiner8"></div>
					<div id="activity_plan_conteiner9"></div>
				</div>
			</div>
		</div>
	</div>
	<script>
		$(document).ready(function () {
			renderViewCharts();
		});
		function renderViewCharts(){
			var isLg = window.innerWidth > 1536;
			var data = [10,20,20,20,40,60,70,80,90];
			var colorList = ['#FF523A', '#FFAB33', '#E74933', '#FFD233', '#FF7633', '#FF7133', '#FF523A', '#FFAB33', '#E74933'];
			var titleList = ['国防生选拔培养办公室','国防生选拔培养办公','国防生选拔培养办','国防生选拔培养','国防生选拔培','国防生选拔','国防生选拔qqq','国防生选拔111','国防1111生选拔'];
			var option = {
				title : {
					text: '',
					x:'center',
					bottom:'-2%',
					textStyle:{
						fontSize:16,
						fontWeight:'normal',
						color:'#000',
					}
				},
				grid:{
					bottom:0,
					right:0,
				},
				color:['#FF523A', '#E5E5E5'],
				series: [
					{
						name:'',
						type:'pie',
						radius: ['50%', '70%'],
						label: {
							normal: {
								show: true,
								position:'center',
								color:'#000',
								fontSize:30
							}
						},
						data:[
							{value:33,name:'33%'},
							{value:77}
						]
					}
				]
			};
			data.map(function(i, idx){
				var activityChart = echarts.init(document.getElementById('activity_plan_conteiner' + (idx + 1)));
			var chartOpt = option;
			var text = titleList[idx];
			chartOpt.title.text = chartOpt.series[0].name = text;
			var newData = [
				{value:data[idx],name:data[idx] + '%'},
				{value:100 - data[idx]}
			];
			chartOpt.series[0].data = newData;
			chartOpt.color[0] = colorList[idx];
			activityChart.setOption(chartOpt);
		})
		}
	</script>
	</body>
</html>
					
				

