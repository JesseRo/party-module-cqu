<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!-- 党组织支部活动统计-->
<portlet:resourceURL id="/hg/part/collegeActivitiesStatistics" var="collegeActivitiesStatistics" />
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

		<title>党支部活动统计</title>
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
		.activitiesTypeEChart{
			padding:5px 5px;
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
							<a href="javascript:;">数据统计</a>
							<a href="javascript:;">党支部活动</a>
						</span>
			</div>
			<div class="activitiesTypeEChart">
				日期范围：
				<div class="layui-inline">
					<input type="text" class="layui-input" id="dateSelect">
				</div>
			</div>
			<div class="charts_container attend_charts_container">
				<div class="view_charts" id="attendEcharts" style="width: 100%; height: 100%;"></div>
			</div>
		</div>
	</div>
	<script>
		var attendChart;
		$(document).ready(function () {
			showCollegeCharts();
			renderDateSelect();
		});
		function showCollegeCharts(){
			var arr = JSON.parse('${collegeActivitiesStatisticsList}');
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
		function renderDateSelect(){
			layui.use('laydate', function(){
				var laydate = layui.laydate;
				laydate.render({
					elem: '#dateSelect'
					,type: 'month'
					,value: '${dateStr}'
					,isInitValue: true
					,range: true
					//,trigger: 'click'
					,done: function(value, date, endDate){
						var startTime = date.year+"-"+date.month+"-"+date.date+" 00:00:00";
						var endTime = date.year+"-"+date.month+"-"+date.date+" 00:00:00";
						getStatisticsData(startTime,endTime);
					}
					,change: function(value, date, endDate){
						this.elem.val(value)
					}
				});
			})
		}
		function getStatisticsData(startTime,endTime){
			$.ajax({
				url:"${collegeActivitiesStatistics}",
				data:{startTime:startTime,endTime:endTime},
				type:"POST",
				dataType:'json',
				async:true,
				success:function(data){
					if(data.code == 200) {
						var arr = data.data;
						var colData = new Array();
						var rowData =new Array();
						if(arr != null && arr.length>0){
							for(var i=0;i<arr.length;i++){
								colData.push(arr[i].property);
								rowData.push(arr[i].num);
							}
						}
						renderCollegeCharts(colData,rowData);
					}
				}
			});
		}
		//学院党组织活动开展情况
		function renderCollegeCharts(colData,rowData){
			if (attendChart != null && attendChart != "" && attendChart != undefined) {
				attendChart.dispose();
			}
			attendChart = echarts.init(document.getElementById('attendEcharts'));
			var option = {
				title: {
					// text: '活动出勤率统计图',
				},
				legend: {
					data: ['组织活动次数']
				},
				tooltip: {
					formatter:function(obj) {
						return '<div class="attend_tooltip">' +
								'<p>' + obj.name + '</p>' +
								'<p>' + obj.seriesName + ':' + obj.data + ' 次</p>' +
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
							return data + ' 次'
						}
					},
					axisLine:{
						lineStyle:{
							color:'#FFAB33'
							// width:6,
						}
					},
					type: 'value',
					minInterval: 1,
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
					data: colData
				},
				series: [{
					name: '组织活动次数',
					type: 'bar',
					data: rowData,
					barWidth: 12,
					barMinHeight: 2,
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
			attendChart.setOption(option);
		}

	</script>
	</body>
</html>
					
				

