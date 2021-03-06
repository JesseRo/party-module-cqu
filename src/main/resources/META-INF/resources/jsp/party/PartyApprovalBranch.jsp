<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/init.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript" src="${basePath}/js/partyModal.js"></script>
<link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/activity-manage1.css?v=1"/>
<link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/common.min.css"/>
<portlet:resourceURL id="/PartyRejectedCommand" var="PartyRejected" />
<portlet:resourceURL id="/part/meeting/page" var="PartyMeetingPage" />
<portlet:resourceURL id="/PartyPassCommand" var="PartyPass" />
<portlet:resourceURL id="/meeting/delete" var="meetingDelete" />
<!DOCTYPE html>
<html>
	<head>
		<title>二级党委审批</title>
		<style>
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

			.table_outer_box > table thead, tbody tr {
				display: table-row !important;
				width: 100%;
				table-layout: auto;
			}
			#searchForm .layui-form-item .layui-inline .keyword {
				width: 300px;
				margin-right: 0px;
			}
			.layui-layer-content{
				overflow: visible !important;
			}
			#rejectModal .layui-form-item .layui-input-inline{
				width:200px
			}
			#rejectModal .layui-form-label{
				width:140px;
				font-weight:bold;
			}
			#rejectModal .layui-form-label-text{
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
	<body class="front">

		<div class="table_form_content">
			<div class="activity_manage_page">
				<div class="breadcrumb_group" style="margin-bottom: 20px;">
					当前位置：
					<span class="layui-breadcrumb" lay-separator=">">
                        <a href="javascript:;">组织生活管理</a>
                        <a href="javascript:;">审批计划</a>
                    </span>
				</div>
				<div class="bg_white_container">
					<form class="layui-form" id="searchForm">
						<div class="layui-form-item">
							<div class="layui-inline">
								<div class="layui-input-inline keyword">
									<input type="text" name="keyword"  placeholder="请输入组织名称、主题关键字" class="layui-input">
								</div>
								<button type="button"  class="layui-btn layui-btn-warm"  lay-submit="" lay-filter="searchForm"><icon class="layui-icon layui-icon-search"></icon>搜索</button>
							</div>
						</div>
					</form>
					<table id="meetingPlanTable" lay-filter="meetingPlanTable"></table>
				</div>
			</div>
		</div>
		<!-- 弹窗 -->
		<div style="display: none" id="rejectModal">
			<form class="layui-form" action="">
				<input type="hidden" class="layui-layer-input"  name="meetingId" value="1">
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label layui-required">驳回理由:</label>
						<div class="layui-input-inline">
							<input  class="layui-layer-input"  name="rejectReason" lay-verify="required" >
						</div>
					</div>
				</div>

				<div class="layui-layer-btn layui-layer-btn-">
					<a class="layui-layer-btn0" type="button"  lay-submit="" lay-filter="rejectForm">确定</a>
					<a class="layui-layer-btn1">取消</a>
				</div>
			</form>
		</div>
		<div id="editGroupModal" style="display: none;padding: 10px 0px;" >
			<div class="layui-form custom_form"  id="personInfo"
				 style="width: 960px;">
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名:</label>
						<div class="layui-input-inline">
							<label class="layui-form-label-text">${info.member_name }</label>
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别:</label>
						<div class="layui-input-inline">
							<label class="layui-form-label-text">${info.member_sex }</label>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
	<script type="text/html" id="meetingPlanTableBtns">
		{{#  if(d.task_status == "1"){ }}
		<%--<a class="layui-btn layui-btn-xs" lay-event="check">审核</a>--%>
		<a class="layui-btn layui-btn-xs" lay-event="pass">
			通过</a>
		{{#  } }}
		{{#  if(d.task_status == "1" || (d.task_status == '6' && !d.note_id)){ }}
		<a  class="layui-btn layui-btn-xs" lay-event="reject">
			驳回</a>
		{{#  } }}

		<%--		{{#  if(d.task_status == '4' || d.task_status == '5' || d.task_status == '6'){ }}--%>
<%--		<a class="layui-btn layui-btn-xs" lay-event="edit"> 编辑</a>--%>
<%--		&lt;%&ndash;<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>&ndash;%&gt;--%>
<%--		{{#  } }}--%>
		<a class="layui-btn layui-btn-xs" lay-event="detail">查看</a>
		{{#  if(d.task_status == '1' || d.task_status == '2' || d.task_status == '3'){ }}
		<a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="delete"> 删除</a>
		{{#  } }}
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
			var rejectId;
			renderTable(1,pageInfo.size);
			form.on('submit(searchForm)', function (data) {
				renderTable(1,pageInfo.size);
			})
			form.on('submit(rejectForm)', function (data) {
				var url = "${PartyRejected}";
				doRej(data.field.rejectReason);
				function doRej(d){
					$.ajax({
						url:url,
						data:{meeting_id2:rejectId,should_:d},
						dataType:'json',
						async:false,
						success:function(res){
							if(res){
								layer.closeAll();
								layer.msg("驳回成功。", {
									icon: 1,
									time: 1000
								});
								renderTable(pageInfo.page,pageInfo.size);
							}
						}
					});
				}
			})
			form.verify({
				select: function (value, item) {
					if (value == '' || value == null) {
						return "请选择必填项。";
					}
				}
			});
			function renderTable(page,size){
				var  where = {
					keyword: $("#searchForm input[name=keyword]").val()
				};
				var ins = table.render({
					elem: '#meetingPlanTable',
					where: where,
					height:560,
					url: '${PartyMeetingPage}', //数据接口
					method: 'post',
					page: {
						limit:size,   //每页条数
						curr:page,
						limits:[10,15,20],
						prev:'&lt;上一页',
						next:'下一页&gt;',
						theme: '#FFB800',
						groups:4
					},
					cols: [[ //表头
						{field: 'org_name', align:'center',width:'10.625%', title: '党组织'},
						{field: 'meeting_type', align:'center',width:'10.625%', title: '会议类型'},
						{field: 'meeting_theme', align:'center',width:'10.625%', title: '开展主题'},
						{field: 'submit_time', align:'center', title: '报送时间',width:'10.625%',templet: function(d){return new Date(d.submit_time).format("yyyy-MM-dd");}},
						{field: 'start_time', align:'center', title: '开始时间',width:'10.625%',templet: function(d){return new Date(d.start_time).format("yyyy-MM-dd hh:mm:ss");}},
						{field: 'member_name', align:'center', title: '联系人',width:'10.625%'},
						{field: 'contact_phone', align:'center', title: '联系电话',width:'10.625%'},
						{field: 'task_status', align:'center', title: '审批状态',width:'10.625%',templet: function(d){
								var status = '';
								switch(parseInt(d.task_status)){
									case 1:status = '待审核';break;
									case 2:status = '已撤回';break;
									case 3:status = '已驳回';break;
									case 4:
									case 5:
									case 6:
									case 7:status = '已通过';break;
								}
								if (d.note_id) {
									status = '已纪要'
								}
								return status;
							}},
						{field: 'operation', align:'center', title: '操作',width:'15%',toolbar: '#meetingPlanTableBtns'}
					]],
					done: function(res, curr, count){
						pageInfo.page = curr;
						pageInfo.size = ins.config.limit;
					}
				});
				$(".layui-table-view .layui-table-page").addClass("layui-table-page-center");
				$(".layui-table-view .layui-table-page").removeClass("layui-table-page");
				//监听事件
				table.on('tool(meetingPlanTable)', function(obj){
					switch(obj.event){
						case 'edit':
							window.location.href="/sendplan?orgType=secondary&type=edit&meetingId="+obj.data.meeting_id;
							break;
						case 'pass':
							pass(obj.data.meeting_id);
							break;
						case 'reject':
							reject(obj.data.meeting_id);
							break;
						case 'detail':
							window.location.href='/approvaldetails?meetingId='+obj.data.meeting_id;
							break;
						case 'delete':
							deleteMeeting(obj.data.meeting_id)
							break;
					};
				});
			}
			function deleteMeeting(id) {
				layer.confirm('确认刪除？', {
					btn: ['确定','取消'] //按钮
				}, function(){
					$.ajax({
						url:"${meetingDelete}",
						data:{meetingId:id},
						dataType:'json',
						success:function(res){
							if(res.code === 200){
								layuiModal.alert("已删除");
								renderTable(pageInfo.page,pageInfo.size);
							}
						}
					});
				});
			}
			function pass(meetingId){
				layer.confirm('确认通过？', {
					btn: ['确定','取消'] //按钮
				}, function(){
					$.ajax({
						url:"${PartyPass}",
						data:{meetingId:meetingId},
						dataType:'json',
						success:function(res){
							if(res.code == 200){
								layer.msg("审核成功");
								renderTable(pageInfo.page,pageInfo.size);
							}
						}
					});
				});
			}
			//点击驳回
			function reject(meetingId){
				rejectId = meetingId;
				layer.prompt({
					type: 1,
					btn: 0,
					content: $("#rejectModal")
				});
			}
		});
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


	</script>
</html>
