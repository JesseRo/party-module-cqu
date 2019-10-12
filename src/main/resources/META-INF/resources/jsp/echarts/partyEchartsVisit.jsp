<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
	<head>
	    <meta charset='utf-8' />
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	    <script type="text/javascript" src="${basePath}/js/echarts-all.js"></script>
	    <%-- <script type="text/javascript" src="${basePath}/js/jquery-3.2.1.js"></script> --%>
	    <%-- <link rel="stylesheet" href="${basePath}/css/echarts/common.css" />
	    <link rel="stylesheet" href="${basePath}/css/echarts/bootstrap.min.css" />
	    <link rel="stylesheet" href="${basePath}/css/echarts/summary_reports.css"/> --%>
	    
	    <title>访问量统计</title>
	</head>
	<body>
        <div class="content_title hidden-xs">
	            	访问量统计
        </div>
	    <div id="main" style="height:${hiet}px;" style="padding-top: 10px;"></div>	
	    <!-- ECharts单文件引入 -->
	    <script type="text/javascript">
	    	/* 访问量统计 */
	    	$(function(){
			 	var myChart = echarts.init(document.getElementById("main")); 
			 	var optionStr = '${mapStr}';	    			//获取对象	    	
			 	var optionJson = eval("(" + optionStr + ")");   //转成json对象
	            // 2.获取数据  
	            var keyName = optionJson.keyName;	//key值   
	         	var valName = optionJson.valName;	//val值  
	         
	            // 3.更新图表myChart的数据  
	            var option = {
	            	    title: {
	            	        text: '',
	            	        subtext: ''
	            	    },
	            	    tooltip: {
	            	        trigger: 'axis',
	            	        axisPointer: {
	            	            type: 'shadow'
	            	        }
	            	    },
	            	    legend: {
	            	        data: ['访问次数']
	            	    },
	            	    grid: {
	            	        left: 400,
	            	        right: '4%',
	            	        bottom: '3%',
	            	        containLabel: true
	            	    },
	            	    xAxis: {
	            	        type: 'value',
	            	        boundaryGap: [0, 0.01]
	            	    },
	            	    yAxis: {
	            	        type: 'category',
	            	        data: keyName,
	            	        axisLabel: {
	            	            formatter: function (value) {
	            	                if (value.length > 4) {
	            	                  return value.substring(0,4) + "...";
	            	                } else {
	            	                  return value;
	            	                }
	            	            },
	            	        }
	            	    },
	            	    series: [
	            	        {
	            	            name: '访问次数',
	            	            type: 'bar',
	            	            data: valName
	            	        },
	            	    ]
	            	    
	            	};
	            myChart.setOption(option); 
	            
	          //点击跳转并传值
        	    myChart.on('click', function (param) {
		        	if(confirm("是否进去详细界面？")){
		        		var name = param.name;
			        	$("#paramsData").val(name);
			        	$("#gotoHtmlform").submit();
		        	}
		        });
	    	});
	    </script> 
	    <!-- 访问量柱形图 -->
	    <portlet:renderURL var="gotoListHtmlURL">
		    <portlet:param name="mvcRenderCommandName" value="/EchartDetailedVisitCommand" />
		</portlet:renderURL>
		<form action="${gotoListHtmlURL }" id="gotoHtmlform" method="post">
			<input type="hidden" id="paramsData" name="paramsData" value=""/>
		</form>
	    
	</body>
</html>
					
				

