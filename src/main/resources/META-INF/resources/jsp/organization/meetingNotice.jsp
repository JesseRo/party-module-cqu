<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/init.jsp" %>
<!DOCTYPE html>
<html>
<portlet:resourceURL id="/party/original/inform/page" var="InformPage" />
<portlet:resourceURL id="/api/download" var="downloadUrl" />
<head>
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <title>组织部子页面主题</title>
    <link rel="stylesheet" href="${basePath }/css/party_member.css"/>
    <link rel="stylesheet" href="${basePath }/css/party_organization.css"/>
    <style type="text/css">
        .content_table thead tr{
            background: #F6F8FC;
            height: 48px;
            font-size: 16px;
        }
        .content_table thead th{
            padding: 5px 15px !important;
        }
        .content_table tr:nth-child(2n) {
            background: #FBFCFE;
        }
        .content_table td{
            min-width: 130px;
            padding: 5px 15px !important;
            height: 48px;
            font-size: 14px;
        }

        table_outer_box > table thead, tbody tr {
            display: table-row !important;
            width: 100%;
            table-layout: fixed;
        }
        #searchForm .layui-form-item .layui-inline .keyword {
            width: 300px;
            margin-right: 0px;
        }
        #personInfo .layui-form-item .layui-input-inline{
            width:200px
        }
        #searchForm .layui-form-label{
            margin-bottom: 0px;
            width:120px;
        }
        #personInfo .layui-form-label{
            width:140px;
            font-weight:bold;
        }
        #personInfo .layui-form-label-text{
            float: left;
            display: block;
            padding: 0 10px;
            width: 200px;
            font-weight: 400;
            line-height: 40px;
            font-size: 16px;
            text-align: left;
        }
        th, tr{
            text-align:center !important;
        }
        .layui-table-page-center{
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="table_form_content ">
    <!-- 右侧盒子内容 -->
    <div class="activity_manage_page">
        <div class="breadcrumb_group">
            当前位置：
            <span class="layui-breadcrumb" lay-separator=">">
                <a href="javascript:;">组织生活管理</a>
                <a href="javascript:;">通知信息</a>
            </span>
        </div>
        <div class="bg_white_container">
            <form class="layui-form" id="searchForm">
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">发布时间</label>
                        <div class="layui-input-inline">
                            <select name="dateType">
                                <option value="">全部</option>
                                <option value="day_1">本日</option>
                                <option value="day_7">本周</option>
                                <option value="month_1">本月</option>
                                <option value="more">更早</option>
                            </select>
                        </div>
                    </div>
                    <div class="layui-inline">
                        <div class="layui-input-inline keyword">
                            <input type="text" name="keyword"  placeholder="请输入活动关键字" class="layui-input">
                        </div>
                        <button type="button"  class="layui-btn layui-btn-warm"  lay-submit="" lay-filter="searchForm"><icon class="layui-icon layui-icon-search"></icon>搜索</button>
                        <button type="button"  class="layui-btn layui-btn-warm" id="plan">拟定计划</button>
                    </div>
                </div>
            </form>
            <table id="informTable" lay-filter="informTable"></table>
     </div>
    </div>
</div>
</body>
<script type="text/html" id="informTableBtns">
    <a class="layui-btn layui-btn-xs" lay-event="detail">查看</a>
</script>
<script type="text/javascript">
    layui.use(['table','layer','form'], function() {
        var table = layui.table,
            layer = layui.layer,
            form = layui.form;
        var pageInfo = {
            page:1,
            size:10
        };
        renderTable(1,pageInfo.size);
        form.on('submit(searchForm)', function (data) {
            renderTable(1,pageInfo.size);
        })
        function renderTable(page,size){
            var  where = {
                keyword: $("#searchForm input[name=keyword]").val(),
                dateType: $("#searchForm select[name=dateType]").val()
            };

            var ins = table.render({
                elem: '#informTable',
                where: where,
                height:560,
                url: '${InformPage}', //数据接口
                method: 'post',
                page: {
                    limit:size,   //每页条数
                    limits:[10,15,20],
                    prev:'&lt;上一页',
                    curr:page,
                    next:'下一页&gt;',
                    theme: '#FFB800',
                    groups:4
                },
                cols: [[ //表头
                    {field: 'meeting_theme', align:'center', title: '活动名称'},
                    {field: 'release_time', align:'center', title: '开始时间',width:180,templet: function(d){return new Date(d.release_time).format("yyyy-MM-dd hh:mm:ss");}},
/*                    {field: 'inform_status', align:'center', title: '查看状态'},*/
                    {field: 'operation', align:'center', title: '操作',width:120,toolbar: '#informTableBtns'},
                    {field: 'attachment', align:'center', title: '附件下载',width:180,templet: function(d){
                            if(d.attachment == "t"){
                                return '<a href="javascript:void(0)"  path="'+d.attachment_url+'" name="'+d.attachment_name+'" onclick="downloadFile(this)">'+d.attachment_name+'</a>';
                            }else{
                                return '';
                            }
                        }}
                ]],
                done: function(res, curr, count){
                    pageInfo.page = curr;
                    pageInfo.size = ins.config.limit;
                    if(count<(pageInfo.page-1)*pageInfo.size){
                        renderTable(pageInfo.page-1,pageInfo.size);
                    }
                }
            });
            $(".layui-table-view .layui-table-page").addClass("layui-table-page-center");
            $(".layui-table-view .layui-table-page").removeClass("layui-table-page");
            //监听事件
            table.on('tool(informTable)', function(obj){
                switch(obj.event){
                    case 'detail':
                        renderDetail(obj.data);
                        break;
                    case 'other':
                        //deleteInform(obj.data.inform_id);
                        break;
                };
            });
        }
        function renderDetail(inform){
            window.location.href = "/noticeDetail?informId="+inform.inform_id;
        }
    });
    function downloadFile(o){
        var path = $(o).attr("path");
        var name = $(o).attr("name");
        window.location.href="${downloadUrl}&filePath="+path+"&fileName="+name;
    }
    Date.prototype.format = function (fmt) {
        var o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "h+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }
    $("#plan").click(function(){
        window.location.href="/sendplan";
    })
</script>
</html>