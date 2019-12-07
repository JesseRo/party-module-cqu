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
	    /*.reports_content .content_table{*/
	    /*	width:96%!important;*/
	    /*	margin:0 2%;*/
	    /*}*/
	    /*	.echartMore_{*/
	    /*		float: right;*/
    	/*		padding: 2px 10px 2px 0px;*/
	    /*	}*/
	    /*	.echartMore_ a {*/
	    /*		color：rgba(153,153,153,1);*/
	    /*		font-size: 12px;*/
	    /*		line-height: 58px;*/
	    /*	}*/
	    /*	thead th{*/
	    /*		padding:10px 25px!important;*/
	    /*	}*/
	    /*	.table_reports_info tr{*/
	    /*		margin-bottom:10px;*/
	    /*	}*/
	    /*	.table_reports_info tr td{*/
	    /*		padding:0 10px;*/
	    /*	}*/
	    /*	.table_reports_info tr td:first-child{*/
	    /*		width:150px;*/
	    /*	}*/
	    /*	.table_reports_info tr td a{*/
	    /*		color:#fff;*/
	    /*	}*/
	    /*	.table_reports_info tr td .num_show{*/
	    /*		color:#fff;*/
	    /*		position: absolute;*/
		/*	    top: 20%;*/
	    /*	}*/
	    /*	.progress_bar_box .keyname_a{*/
	    /*		display:inline-block;*/
	    /*		width:100%;*/
	    /*		height:40px;*/
	    /*	}*/
	    /*	.progress_bar_box .progress{*/
	    /*		margin-bottom:0;*/
	    /*		height:100%;*/
	    /*		position:relative;*/
	    /*	}*/
	    /*	.table_reports_info tr .object_name{*/
	    /*		height:40px;*/
	    /*		box-sizing:border-box;*/
	    /*	}*/
	    /*	.table_reports_info tr:nth-child(1) .object_name{*/
	    /*		background:#ff7f50;*/
	    /*	}*/
	    /*	.table_reports_info tr:nth-child(3) .object_name{*/
	    /*		background:#83b1de;*/
	    /*	}*/
	    /*	.table_reports_info tr:nth-child(5) .object_name{*/
	    /*		background:#83b1de;*/
	    /*	}*/
	    /*	.table_reports_info tr:nth-child(7) .object_name{*/
	    /*		background:#83b1de;*/
	    /*	}*/
	    /*	.table_reports_info tr:nth-child(9) .object_name{*/
	    /*		background:#83b1de;*/
	    /*	}*/
	    /*	.table_reports_info tr:nth-child(1) .progress-bar{*/
	    /*		background-color:#ff7f50;*/
	    /*	}*/
	    /*	.table_reports_info tr:nth-child(3) .progress-bar{*/
	    /*		background-color:#83b1de;*/
	    /*	}*/
	    /*	.table_reports_info tr:nth-child(5) .progress-bar{*/
	    /*		background-color:#83b1de;*/
	    /*	}*/
	    /*	.table_reports_info tr:nth-child(7) .progress-bar{*/
	    /*		background-color:#83b1de;*/
	    /*	}*/
	    /*	.table_reports_info tr:nth-child(9) .progress-bar{*/
	    /*		background-color:#83b1de;*/
	    /*	}*/
	    /*	.table_reports_info .line_space{*/
	    /*		height:10px;*/
	    /*	}*/
	    /*	.table_reports_info .line_space:last-child{*/
	    /*		height:0;*/
	    /*	 }*/
	    /*	 .content_table thead tr th{*/
	    /*	 	!* font-size: 16px; *!*/
	    /*	 }*/
	    /*	 #progress {*/
		/*        display: flex;*/
		/*        justify-content: space-between;*/
		/*        margin: 0 50px;*/
		/*     }*/
		/*     #progress section {*/
		/*        display: inline-block;*/
		/*     }*/
		/*     #progress section span{*/
		/*        display: block;*/
		/*        width: 126px;*/
		/*        text-align: center;*/
		/*        word-wrap:break-word;*/
		/*     }*/
		/*     @media (min-width: 768px) {*/
		/*     	.content_info {*/
		/*     		overflow-y: hidden;*/
		/*     	}*/
		/*     }*/
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
<%--	        <div class="content_title hidden-xs">--%>
<%--		            统计报表--%>
<%--	        </div>--%>
<%--	        <div class="summary_reports_box">--%>
<%--	            <div class="reports_header">--%>
<%--	            	<div class="header_item">--%>
<%--	                    <div class="img_box">--%>
<%--	                        <img src="/images/line-img2.png"/>--%>
<%--	                    </div>--%>
<%--	                    <div class="text_box">--%>
<%--	                        <p class="text_title">二级党组织</p>--%>
<%--	                        <p>--%>
<%--	                            <span class="num_text">${orgNumber}</span>个--%>
<%--	                        </p>--%>
<%--	                    </div>--%>
<%--	                </div>--%>
<%--	                <div class="header_item">--%>
<%--	                    <div class="img_box">--%>
<%--	                        <img src="/images/line-img3.png"/>--%>
<%--	                    </div>--%>
<%--	                    <div class="text_box">--%>
<%--	                        <p class="text_title">党支部</p>--%>
<%--	                        <p>--%>
<%--	                            <span class="num_text">${branchNumber}</span>个--%>
<%--	                        </p>--%>
<%--	                    </div>--%>
<%--	                </div>--%>
<%--	                <div class="header_item">--%>
<%--	                    <div class="img_box">--%>
<%--	                        <img src="/images/line-img4.png"/>--%>
<%--	                    </div>--%>
<%--	                    <div class="text_box">--%>
<%--	                        <p class="text_title">党员人数</p>--%>
<%--	                        <p>--%>
<%--	                            <span class="num_text">${userNumber}</span>个--%>
<%--	                        </p>--%>
<%--	                    </div>--%>
<%--	                </div>--%>
<%--	                <div class="header_item">--%>
<%--	                    <div class="img_box">--%>
<%--	                        <img src="/images/line-img.png"/>--%>
<%--	                    </div>--%>
<%--	                    <div class="text_box">--%>
<%--	                        <p class="text_title">组织活动</p>--%>
<%--	                        <p>--%>
<%--	                            <span class="num_text">${mettingNumber}</span>个--%>
<%--	                        </p>--%>
<%--	                    </div>--%>
<%--	                </div>--%>
<%--	            </div>--%>
<%--	            <div class="reports_box">--%>
<%--	            	<div class="reports_content_box">--%>
<%--	                    <div class="reports_header reports_heade_only">访问量</div>--%>
<%--	                    <span class="num_show">${dateVisit}</span>--%>
<%--	                    <div class="reports_content"style="padding-top: 10px;">--%>
<%--	                    	<img src="/images/line.png"/ style="display: block;margin: 0 auto;">--%>
<%--	                    	<div style="width:100%;height:1px;background:#ddd;margin-top:39px"></div>--%>
<%--	                    	<span class="reports_heade_only" style="display:inline-box;margin-top:16px;">日访问量</span>--%>
<%--	                    	<span class="num_show" style="font-size:20px;display:inline-box;margin-top:16px;">${dateVisit}</span>--%>
<%--	                    </div>--%>
<%--	                </div>--%>
<%--	                <div class="reports_content_box">--%>
<%--	                    <div class="reports_header">会议类型分布图</div>--%>
<%--	                    <div class="reports_content" id="distribution" style="height:218px"></div>--%>
<%--	                </div>--%>
<%--	            </div>--%>
<%--	            <div class="reports_box" style="height: 380px;">--%>
<%--	            	<div class="reports_content_box" style="width:100%;height:84%;margin-top:25px;margin-bottom:40px">--%>
<%--	                    <div class="reports_header">--%>
<%--	                    	学院参会统计--%>
<%--	                    	<div class="echartMore_">--%>
<%--	                    	<a href="${EchartMore }">更多></a>--%>
<%--	                    </div>--%>
<%--	                    </div>--%>
<%--	                    <div class="reports_content" style="height:322px">--%>
<%--	                    	<div id="progress" class="table_reports_info" style="height:100%;margin-top:30px;">--%>
<%--	                    	<c:forEach var="info" items="${atten }" varStatus="status">--%>
<%--						      <section>--%>
<%--						          <a class="keyname_a">--%>
<%--						          	<input type="hidden" class="annexName" value="${info.keyname }" name="annexName"/>--%>
<%--						          	<el-progress type="circle" :percentage="${info.valname }" stroke-width="12" class="progress-item" :color="colorList[${status.index}]"></el-progress>--%>
<%--						          </a>--%>
<%--						          <a class="keyname_a">--%>
<%--						          	<input type="hidden" class="annexName" value="${info.keyname }" name="annexName"/>--%>
<%--						          	<span>${info.keyname }</span>--%>
<%--						          </a>--%>
<%--						      </section>--%>
<%--						    </c:forEach>--%>
<%--						    </div>--%>
<%--	                    </div> --%>
<%--	                </div>--%>
<%--	            </div>--%>
<%--	        </div>--%>
<%--		--%>
	    <!-- 为ECharts准备一个具备大小（宽高）的Dom --> 
	    <!-- <div id="main" style="height:400px"></div> -->	
	    <!-- ECharts单文件引入 -->
	<div class="outer_content_container">
		<div class="summary_page" style="height: 83.4vh;margin-left: 2vw;margin-top: 2vh;">
			<ul class="total_account_group">
				<li>
					<p class="title_group">
						<img class="title_icon" src="../images/account-icon1.png"></span>
						<span>全校二级党组织数目</span>
					</p>
					<p class="total_num">
						<span>29999</span>个
					</p>
				</li>
				<li>
					<p class="title_group">
						<img class="title_icon" src="../images/account-icon2.png"></span>
						<span>全校党支部数目</span>
					</p>
					<p class="total_num">
						<span>29999</span>个
					</p>
				</li>
				<li>
					<p class="title_group">
						<img class="title_icon" src="../images/account-icon3.png"></span>
						<span>全校党员数目</span>
					</p>
					<p class="total_num">
						<span>29999</span>个
					</p>
				</li>
				<li>
					<p class="title_group">
						<img class="title_icon" src="../images/account-icon4.png"></span>
						<span>组织生活总数</span>
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
								<img src="../images/charts-title1.png"/>
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
						<div class="attend_rate_container" id="attend_rate_container"></div>
					</div>
					<div class="views_group">
						<div class="text_container">
							<p class="title">
								<img src="../images/charts-title2.png"/>
							</p>
							<div class="sub_text_container">
								<div class="sub_text current_view">
									<p class="sub_title">今日访问量</p>
									<p class="sub_num">3888</p>
								</div>
								<div class="sub_text">
									<p class="sub_title">总访问量</p>
									<p class="sub_num">388866</p>
								</div>
							</div>
						</div>
						<div class="view_conteiner" id="view_conteiner"></div>
						<p class="desc_text">同比昨日+7.9%</p>
					</div>
				</div>
				<div class="activity_plan_group">
					<div class="operate_container">
						<p>
							<img src="${basePath}/cqu/images/charts-title3.png"/>
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
					<div class="activity_plan_conteiner" id="activity_plan_conteiner">
						<div id="activity_plan_conteiner1"></div>
						<div id="activity_plan_conteiner2"></div>
						<div id="activity_plan_conteiner3"></div>
						<div id="activity_plan_conteiner4"></div>
						<div id="activity_plan_conteiner5"></div>
						<div id="activity_plan_conteiner6"></div>
					</div>
					<p class="desc_text"><a href="javascript:;">查看更多</a></p>
				</div>
			</div>
		</div>
	</div>


		<script>
			var menuData = [
				{
					title:'数据统计',
					link:'',
					icon:'../images/tree-icon1.png',
					children:[
						{
							title:'数据汇总',
							link:'./data-statistics/summary.html',
						}
					]
				},
				{
					title:'组织生活管理',
					link:'',
					icon:'../images/tree-icon2.png',
					children:[
						{
							title:'活动管理',
							link:'./data-statistics/summary.html',
						}
					]
				},
				{
					title:'组织关系转接',
					icon:'../images/tree-icon3.png',
					link:'',
					children:[
						{
							title:'组织关系转出',
							link:'./data-statistics/summary.html',
						}
					]
				},
				{
					title:'数据报送',
					icon:'../images/tree-icon4.png',
					link:'',
					children:[]
				},
				{
					title:'基础数据管理',
					icon:'../images/tree-icon5.png',
					link:'',
					children:[]
				},
				{
					title:'系统管理',
					icon:'../images/tree-icon6.png',
					link:'',
					children:[]
				},
			]
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
				var AttendChart = echarts.init(document.getElementById('attend_rate_container'));
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
				var ViewChart = echarts.init(document.getElementById('view_conteiner'));
				option = {
					xAxis: {
						show:false,
						type: 'category',
						data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
					},
					yAxis: {
						type: 'value',
						show:false,
					},
					series: [{
						data: [1820, 9232, 9301, 1934, 11290, 13130, 13820],
						type: 'line',
						smooth: true,
						itemStyle: {
							normal: {
								color: new echarts.graphic.LinearGradient(0, 0, 0, 1,
										[
											{offset: 0, color: '#E74933'},
											// {offset: 0.4, color: '#EB5BAB'},
											// {offset: 0.6, color: '#A152DE'},
											{offset: 1, color: '#FFAB33'}
										]
								)
							}
						}
					}]
				};
				ViewChart.setOption(option);
			}
			//渲染计划上报 图表
			function renderActivityCharts(){
				var isLg = window.innerWidth > 1536;
				var data = [10,20,20,20,40,60];
				var colorList = ['#FF523A', '#FFAB33', '#E74933', '#FFD233', '#FF7633', '#FF7133'];
				var titleList = ['国防生选拔培养办公室','国防生选拔培养办公','国防生选拔培养办','国防生选拔培养','国防生选拔培','国防生选拔'];
				var option = {
					title : {
						text: '',
						x:'center',
						// y:'bottom',
						bottom:isLg ? 0 : '-4%',
						textStyle:{
							fontSize:isLg ? 14 : 12,
							fontWeight:'normal',
							color:'#000',
						}
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
									fontSize:isLg ? 30 : 20
								}
							},
							data:[
								{value:33,name:'33%'},
								{value:77}
							]
						}
					]
				};
				data.map(function(i, idx) {
					var activityChart = echarts.init(document.getElementById("activity_plan_conteiner" + (idx + 1)));
				var chartOpt = option;
				var text = titleList[idx];
				chartOpt.title.text = chartOpt.series[0].name = text;
				chartOpt.series[0].data = [
					{value: data[idx], name: data[idx] + '%'},
					{value: 100 - data[idx]}
				];
				chartOpt.color[0] = colorList[idx];
				activityChart.setOption(chartOpt);
			})
			}
		</script>

<%--		<script>--%>
<%--	      new Vue({--%>
<%--	        el: '#progress',--%>
<%--	        data:function () {--%>
<%--	          return {--%>
<%--	            colorList: ["#BF2819","#03A9F4","#2FC25B","#FBC02D","#32D0DE"]--%>
<%--	          }--%>
<%--	        } --%>
<%--	      })	    	--%>
<%--	    </script>--%>
<%--	    <script type="text/javascript">--%>
<%--	    	//动态设置定位--%>
<%--	    	$(".num_show").each(function(){--%>
<%--	    		var _num = $(this).text();--%>
<%--	    		var _leftNum = _num.substr(0, _num.length - 1);--%>
<%--	    		$(this).css("left",parseInt(_leftNum) -20 + "%");--%>
<%--	    	})--%>
<%--	    	--%>
<%--		  //出勤率详细页跳转--%>
<%--	    	$(".table_reports_info").on("click",".keyname_a",function(){--%>
<%--				var name = $(this).find("input").val();--%>
<%--				console.log(name);--%>
<%--				if(confirm("是否进入详细页面？")){--%>
<%--					$(this).attr("href","${EchartBar}&keyname="+name);--%>
<%--				}else{--%>
<%--					$(this).attr("href","#");--%>
<%--				}--%>
<%--			});--%>
<%--	    </script>--%>
<%--	    <script type="text/javascript">--%>
<%--	    	/* 会议类型分布图 */--%>
<%--		     $(function(){--%>
<%--			 	var myChart_dis = echarts.init(document.getElementById("distribution")); --%>
<%--			 	var optionStr = '${mapStrType}';	    			//获取对象	    	--%>
<%--			 	var optionJson = eval("(" + optionStr + ")");   //转成json对象--%>
<%--	            // 2.获取数据  --%>
<%--	            var keyNameType = optionJson.keyNameType;	//key值   --%>
<%--	         	var valNameType = optionJson.valNameType;	//val值  --%>
<%--	         	--%>
<%--	            // 3.更新图表myChart_dis的数据  --%>
<%--	            option = {--%>
<%--				    xAxis: {--%>
<%--				        type: 'category',--%>
<%--				        data: keyNameType,--%>
<%--				        axisLabel: {--%>
<%--				        	interval: 0,--%>
<%--				        	rotate: -15--%>
<%--				        }--%>
<%--				    },--%>
<%--				    yAxis: {--%>
<%--				        type: 'value'--%>
<%--				    },--%>
<%--				    series: [{--%>
<%--				        data: valNameType,--%>
<%--				        type: 'line'--%>
<%--				    }],--%>
<%--				    grid: {--%>
<%--				    	x: 50,--%>
<%--				    	y: 25,--%>
<%--				    	x2: 50,--%>
<%--				    	y2: 75--%>
<%--				    }--%>
<%--				};--%>
<%--	            /* var option = {--%>
<%--	            	    title: {--%>
<%--	            	        text: '',--%>
<%--	            	        subtext: '',--%>
<%--	            	        left: 'center'--%>
<%--	            	    },--%>
<%--	            	    tooltip : {--%>
<%--	            	        trigger: 'item',--%>
<%--	            	        formatter: "{a} <br/>{b} : {c} ({d}%)"--%>
<%--	            	    },--%>
<%--	            	    legend: {--%>
<%--	            	        // orient: 'vertical',--%>
<%--	            	        // top: 'middle',--%>
<%--	            	        bottom: 10,--%>
<%--	            	        left: 'center',--%>
<%--	            	        data: keyNameType--%>
<%--	            	    },--%>
<%--	            	    series : [--%>
<%--	            	        {--%>
<%--	            	            type: 'pie',--%>
<%--	            	            radius : '65%',--%>
<%--	            	            center: ['50%', '50%'],--%>
<%--	            	            selectedMode: 'single',--%>
<%--	            	            data:valNameType,--%>
<%--	            	            itemStyle: {--%>
<%--	            	                emphasis: {--%>
<%--	            	                    shadowBlur: 10,--%>
<%--	            	                    shadowOffsetX: 0,--%>
<%--	            	                    shadowColor: 'rgba(0, 0, 0, 0.5)'--%>
<%--	            	                }--%>
<%--	            	            }--%>
<%--	            	        }--%>
<%--	            	    ]--%>
<%--	            	};--%>
<%--	            	 */--%>
<%--	            	--%>
<%--	            myChart_dis.setOption(option); --%>
<%--	          //点击跳转并传值--%>
<%--	            myChart_dis.on('click', function (param){  --%>
<%--	            	if(confirm("是否进去详细界面？")){--%>
<%--		        		var name = param.name;--%>
<%--		        		console.log(name);--%>
<%--			        	$("#paramsDataPie").val(name);--%>
<%--			        	$("#gotoHtmlformPie").submit();--%>
<%--		        	}           --%>
<%--	            });--%>
<%--	    	}); 	--%>
<%--	    --%>
<%--	    	/* 学院参会情况 */--%>
<%--	    	/* $(function(){--%>
<%--			 	var myChart = echarts.init(document.getElementById("main")); --%>
<%--			 	var optionStr = '${mapStr}';	    			//获取对象	    	--%>
<%--			 	var optionJson = eval("(" + optionStr + ")");   //转成json对象--%>
<%--	            // 2.获取数据  --%>
<%--	            var keyName = optionJson.keyName;	//key值   --%>
<%--	         	var valName = optionJson.valName;	//val值  --%>
<%--	         --%>
<%--	            // 3.更新图表myChart的数据  --%>
<%--	            var option = {--%>
<%--	            	    title: {--%>
<%--	            	        text: '',--%>
<%--	            	        subtext: ''--%>
<%--	            	    },--%>
<%--	            	    tooltip: {--%>
<%--	            	        trigger: 'axis',--%>
<%--	            	        axisPointer: {--%>
<%--	            	            type: 'shadow'--%>
<%--	            	        }--%>
<%--	            	    },--%>
<%--	            	    legend: {--%>
<%--	            	        data: ['参会次数']--%>
<%--	            	    },--%>
<%--	            	    grid: {--%>
<%--	            	        left: '50%',--%>
<%--	            	        right: '4%',--%>
<%--	            	        bottom: '3%',--%>
<%--	            	        containLabel: true--%>
<%--	            	    },--%>
<%--	            	    xAxis: {--%>
<%--	            	        type: 'value',--%>
<%--	            	        boundaryGap: [0, 0.01]--%>
<%--	            	    },--%>
<%--	            	    yAxis: {--%>
<%--	            	        type: 'category',--%>
<%--	            	        data: keyName,--%>
<%--	            	        axisLabel: {--%>
<%--	            	        	formatter: function (value) {--%>
<%--	            	                if (value.length > 4) {--%>
<%--	            	                  return value.substring(0,4) + "...";--%>
<%--	            	                } else {--%>
<%--	            	                  return value;--%>
<%--	            	                }--%>
<%--	            	            }--%>
<%--	            	        }--%>
<%--	            	    },--%>
<%--	            	    series: [--%>
<%--	            	        {--%>
<%--	            	            name: '参会次数',--%>
<%--	            	            type: 'bar',--%>
<%--	            	            data: valName--%>
<%--	            	        },--%>
<%--	            	    ]--%>
<%--	            	    --%>
<%--	            	};--%>
<%--	            myChart.setOption(option); --%>
<%--	            --%>
<%--	          //点击跳转并传值--%>
<%--        	    myChart.on('click', function (param) {--%>
<%--		        	if(confirm("是否进去详细界面？")){--%>
<%--		        		var name = param.name;--%>
<%--			        	$("#paramsData").val(name);--%>
<%--			        	$("#gotoHtmlform").submit();--%>
<%--		        	}--%>
<%--		        });--%>
<%--	    	}); */--%>
<%--	    	--%>
<%--	    	--%>
<%--	    </script> --%>
<%--	    <!-- 柱形图 -->--%>
<%--	    <portlet:renderURL var="gotoListHtmlURL">--%>
<%--		    <portlet:param name="mvcRenderCommandName" value="/EchartDetailedCommand" />--%>
<%--		</portlet:renderURL>--%>
<%--		<form action="${gotoListHtmlURL }" id="gotoHtmlform" method="post">--%>
<%--			<input type="hidden" id="paramsData" name="paramsData" value=""/>--%>
<%--		</form>--%>
<%--		--%>
<%--		<!-- 饼形图 -->--%>
<%--		<portlet:renderURL var="echartsPIE">--%>
<%--		    <portlet:param name="mvcRenderCommandName" value="/EchartDetailedPieCommand" />--%>
<%--		</portlet:renderURL>--%>
<%--		<form action="${echartsPIE }" id="gotoHtmlformPie" method="post">--%>
<%--			<input type="hidden" id="paramsDataPie" name="paramsDataPie" value=""/>--%>
<%--		</form>--%>
	    
	</body>
</html>
					
				

