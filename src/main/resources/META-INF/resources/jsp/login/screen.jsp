<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/init.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>重庆大学智慧党建云平台</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="${basePath }/cqu/css/common.min.css?v=2"/>
    <link rel="stylesheet" type="text/css" href="${basePath }/cqu/css/index.min.css"/>
    <script type="text/javascript" src="${basePath}/js/echarts.js"></script>
</head>
<div class="big_screen_container">
    <div class="content_container">
        <div class="page_title_container" style="cursor: pointer;">
            <p id="platformTitle">重庆大学智慧党建云平台</p>
            <img src="/images/index-title-bg.png"/>
        </div>
        <div class="first_container">
            <div class="organ_inspector_container">
                <div class="party_organ_container">
                    <div class="organ_item">
                        <p class="organ_title">二级党组织</p>
                        <p class="organ_num">3</p>
                    </div>
                    <div class="organ_item">
                        <p class="organ_title">党支部</p>
                        <p class="organ_num">5</p>
                    </div>
                    <div class="organ_item">
                        <p class="organ_title">党员</p>
                        <p class="organ_num">186</p>
                    </div>
                    <div class="organ_item">
                        <p class="organ_title">党组织生活</p>
                        <p class="organ_num">35</p>
                    </div>
                    <div class="logo_container">
                        <img src="/images/index-logo.png"/>
                    </div>
                </div>
                <div class="inspector_container">
                    <p class="index_title">活动督查情况</p>
                    <div id="inspectorChart" class="inspector_chart"></div>
                    <div class="echarts_legend"></div>
                    <div class="echarts_num"></div>
                </div>
            </div>
            <div class="map_outer_container">
                <img src="/images/index-map.png"/>
                <div id="mapContainer" class="map_container"></div>
            </div>
            <div class="data_report_container">
                <p class="index_title">
                    数据上报
                    <img class="title_icon" src="/images/index-date-icon.png"/>
                </p>
                <p class="report_title">
                    <span>二级党委</span>
                    <span>发布时间</span>
                    <span>上报时间</span>
                </p>
                <div class="report_list_container">
                    <ul class="report_list" id="reportList"></ul>
                    <ul class="report_list"></ul>
                </div>
            </div>
        </div>
        <div class="bottom_container">
            <div class="attend_outer_container">
                <p class="index_title">活动出勤率</p>
                <div id="attendContainer" class="attend_container"></div>
            </div>
            <div class="activity_outer_container">
                <p class="index_title">活动计划上报</p>
                <div id="activityContainer" class="activity_container"></div>
            </div>
            <div class="view_outer_container">
                <p class="index_title">
                    系统总访问量
                    <span class="view_num"></span>
                    <span class="view_desrc">比昨日<span class="red_text">+7.9%</span></span>
                </p>
                <div id="viewContainer" class="view_container"></div>
            </div>
        </div>
    </div>
</div>
<script>
    $(document).ready(function(){
        renderInspectorChart();
        renderMap();
        renderReportList();
        renderAttendChart();
        renderActivityChart();
        renderViewChart();

        $('#platformTitle').on('click', function () {
            window.location.href = "${turnTo}";
        })
        autoScroll();

    });
    //渲染活动督察情况
    function renderInspectorChart(){
        var inspectorEchart = echarts.init(document.getElementById('inspectorChart'));
        var heightNum = $("#inspectorChart").height();
        var legendDom = $(".echarts_legend");
        var domStr = '';
        var numStr = '';
        var legendData = [
            {
                title:'重庆大学A区',
                num:400,
                color:'#E60012',
                percent:'30%'
            },
            {
                title:'重庆大学B区',
                num:300,
                color:'#0DE5D9',
                percent:'40%'
            },
            {
                title:'重庆大学C区',
                num:200,
                color:'#FFB100',
                percent:'50%'
            },
            {
                title:'重庆大学虎溪校区',
                num:100,
                color:'#FF6926',
                percent:'60%'
            },
        ];
        legendData.map(function (i) {
            domStr += '<div class="legend_item">' +
                '<span class="icon" style=background:' + i.color + '></span>' +
                '<span class="title">' + i.title + '</span>' +
                '<span class="num">' + i.num + '个</span></div>';
            numStr += '<div class="num_item">' +
                '<span class="icon" style=background:' + i.color + '></span>' +
                '<span>' + i.percent + '</span></div>';
        });
        $(".echarts_legend").html(domStr);
        $(".echarts_num").html(numStr);
        var inspectorOption = {
            animation:false,
            legend: {
                show:false,
                data:['重庆大学A区','重庆大学B区','重庆大学C区','重庆大学虎溪校区'],
                icon:'circle',
                top: 0,
                left: '55%',
                // itemGap: 5,
                itemWidth: 4,
                itemHeight: 4,
                padding:0,
                orient: 'vertical',
                textStyle:{
                    color:'#480707',
                    fontSize:10,
                    lineHeight:(heightNum * 0.04),
                }
            },
            borderRadius:4,
            series: [{
                name:'活动督查情况',
                type:'pie',
                radius: ['35%', '40%'],
                clockwise:false,
                label: {
                    show: false
                },
                color: ['#FF6926', '#F9EAEC'],
                data:[
                    {value:100, name:'重庆大学虎溪校区'},
                    {value:679, name:'重庆大学虎溪校区1'},
                ]
            },
                {
                    name:'活动督查情况',
                    type:'pie',
                    radius: ['55%', '60%'],
                    clockwise:false,
                    color: ['#FFB100', '#F9EAEC'],
                    label: {
                        show: false
                    },
                    data:[
                        {value:200, name:'重庆大学C区'},
                        {value:679, name:'重庆大学C区1'},
                    ]
                },
                {
                    name:'活动督查情况',
                    type:'pie',
                    radius: ['75%', '80%'],
                    clockwise:false,
                    color: ['#0DE5D9', '#F9EAEC'],
                    label: {
                        show: false
                    },
                    data:[
                        {value:300, name:'重庆大学B区'},
                        {value:679, name:'重庆大学B区1'},
                    ]
                },
                {
                    name:'活动督查情况',
                    type:'pie',
                    radius: ['95%', '100%'],
                    clockwise:false,
                    color: ['#E60012', '#F9EAEC'],
                    label: {
                        show: false
                    },
                    data:[
                        {value:400, name:'重庆大学A区'},
                        {value:679, name:'重庆大学A区1'},
                    ]
                },
            ]
        };
        inspectorEchart.setOption(inspectorOption);
    };
    //渲染地图 动画效果
    function renderMap(){
        var mapEchart = echarts.init(document.getElementById('mapContainer'));
        var toolTipData = [
            {
                name:'A区',
                title:'测试数据A'
            },{
                name:'C区',
                title:'测试数据C'
            },{
                name:'B区',
                title:'测试数据B'
            },{
                name:'虎溪区',
                title:'测试数据D'
            }
        ];
        var option = {
            xAxis: {
                show:false
            },
            yAxis: {
                show:false
            },
            tooltip:{
                formatter:function(params)  {
                    return  (toolTipData[params.dataIndex] || {}).title;
                }
            },
            series: [{
                type: 'effectScatter',
                symbolSize: 10,
                color:'#FFF067',
                rippleEffect: {
                    brushType: 'stroke'
                },
                data: [
                    [112.4, 400.2, 'test1'],
                    [552, 62],
                    [662, 440],
                    [1062, 306],
                ]
            }]
        };
        mapEchart.setOption(option);
    };
    //数据上报
    function renderReportList(){
        var data = {
            name:'音乐学院',
            releaseTime:'08-20 09:00',
            repoprtTime:'08-20 09:00',
        };
        var reportDom = '';
        var renderItem = function(num) {
            if(num <= 18){
                reportDom += '<div class="report_item">' +
                    '<span>' + data.name + num + '</span>' +
                    '<span>' + data.releaseTime + '</span>' +
                    '<span>' + data.repoprtTime + '</span></div>';
                return renderItem(num+=1);
            }
        };
        renderItem(1);
        $(".report_list").html(reportDom);
    };
    function autoScroll(){
        var scrollAnimation = function(){
            var container = document.querySelector(".report_list_container");
            var list = document.querySelector("#reportList");
            if( container.scrollTop >= list.scrollHeight){
                container.scrollTop = 0;
            }else{
                container.scrollTop++;
            }
        };
        setInterval(scrollAnimation, 30)
    }

    //渲染活动出勤率
    function renderAttendChart(){
        var attendChart = echarts.init(document.getElementById('attendContainer'));
        var mockData1 = [];
        var mockData2 = [];
        var pushItem = function(num){
            if(num > 0){
                mockData1.push({
                    value: 10 * num,
                    symbol: 'roundRect',
                    symbolRepeat: true,
                    symbolSize: ['150%', '25%'],
                });
                mockData2.push({
                    value: 150,
                    symbol: 'roundRect',
                    symbolRepeat: true,
                    symbolSize: ['150%', '25%'],
                });
                return pushItem(num -= 1)
            }
        };
        pushItem(12);
        option = {
            grid:{
                top:0,
                left:0,
                bottom:0,
                right:0,
                containLabel:true
            },
            xAxis: [{
                data: ['音乐学院',  '音乐学院1', '音乐学院2', '音乐学院',  '音乐学院1', '音乐学院2', '音乐学院',  '音乐学院1', '音乐学院2', '音乐学院',  '音乐学院1', '音乐学院2'],
                axisTick: {show: false},
                axisLine: {
                    lineStyle:{
                        color:'#F9EAEC'
                    }
                },
                axisLabel: {
                    interval:0,
                    color:'#480707',
                    fontSize:(14 / 1920) * window.innerWidth
                }
            }],
            yAxis: {
                splitLine: {show: false},
                axisTick: {show: false},
                axisLine: {show: false},
                axisLabel: {show: false}
            },
            series: [{
                type: 'pictorialBar',
                hoverAnimation: false,
                zlevel:10,
                color:'#E60012',
                barWidth:(30 / 1920) * window.innerWidth,
                tooltip:{
                    show:true
                },
                data: mockData1,
            },{
                type: 'pictorialBar',
                hoverAnimation: false,
                zlevel:1,
                color:'#F9EAEC',
                label:{show:false},
                barWidth:(30 / 1920) * window.innerWidth,
                data: mockData2,
            }]
        };
        attendChart.setOption(option);
    };
    function renderActivityChart(){
        var activityEchart = echarts.init(document.getElementById('activityContainer'));
        var fontSize = (14 / 1920) * window.innerWidth;
        option = {
            xAxis: {
                type: 'category',
                data: ['音乐学院', '音乐', '音乐学院', '音乐', '音乐学院', '音乐', '音乐学院', '音乐', '音乐学院', '音乐', '音乐学院', '音乐'],
                // nameLocation:'center',
                padding:4,
                splitNumber:12,
                axisLine:{
                    lineStyle:{
                        color:'#F9EAEC'
                    }
                },
                axisTick:{
                    show:false
                },
                axisLabel:{
                    color:'#480707',
                    fontSize:fontSize,
                    interval:0
                }
            },
            yAxis: {
                show:false,
            },
            grid:{
                top:0,
                left:0,
                right:0,
                bottom:0,
                containLabel:true
            },
            series: [{
                data: [120, 200, 150, 80, 70, 110, 130, 150, 80, 70, 110, 130],
                type: 'bar',
                barWidth:(24 / 1920) * window.innerWidth,
                // barGap:(44 / 1920) * window.innerWidth,
                itemStyle:{
                    color:new echarts.graphic.LinearGradient(
                        0, 0, 1, 1,
                        [
                            {offset: 0, color: '#FFCC3D'},
                            {offset: 1, color: '#F14F5D'}
                        ]
                    ),
                }
            }]
        };
        activityEchart.setOption(option);
    };
    //渲染访问量
    function renderViewChart(){
        var num = '1,2,3,4,5,6,7,8';
        var numDom = '';
        num.split(',').map(function(i) { numDom += '<span class="num_item">' + i + '</span>'});
        $(".view_num").html(numDom);
        var viewEchart = echarts.init(document.getElementById('viewContainer'));
        option = {
            tooltip:{
                formatter: function (params) {
                    return '<div class="view_tooltip">' + params.data + '</div>';
                }
            },
            xAxis: {
                type: 'category',
                data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
                axisTick:{
                    show:false
                },
                axisLine:{
                    lineStyle:{
                        color:'#F9EAEC'
                    }
                },
                axisLabel:{
                    color:'#480707'
                }
            },
            yAxis: {
                type: 'value',
                axisTick:{
                    show:false
                },
                axisLine:{
                    show:false
                },
                splitLine:{
                    lineStyle:{
                        color:['#F9EAEC']
                    }
                }
            },
            grid:{
                top:'5%',
                left:0,
                right:0,
                bottom:0,
                containLabel:true
            },
            series: [{
                data: [820, 932, 901, 934, 1290, 1330, 1320],
                type: 'line',
                color:'#E60012',
                smooth: true,
                areaStyle: {
                    color:{
                        type: 'linear',
                        x: 0,
                        y: 0,
                        x2: 0,
                        y2: 1,
                        colorStops: [{
                            offset: 0, color: 'rgba(255,56,56,0.43)' // 0% 处的颜色
                        }, {
                            offset: 1, color: 'rgba(255,56,56,0)' // 100% 处的颜色
                        }],
                    }
                }
            }]
        };
        viewEchart.setOption(option);
    };
</script>
</html>