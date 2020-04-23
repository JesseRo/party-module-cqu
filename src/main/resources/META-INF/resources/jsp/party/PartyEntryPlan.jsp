<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/init.jsp" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript" src="${basePath}/js/partyModal.js"></script>
<link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/activity-manage1.css?v=1"/>
<link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/common.min.css"/>
<portlet:resourceURL id="/PartyOrganizationsRejectedCommand" var="PartyRejected" />
<portlet:resourceURL id="/PartyReasonCommand" var="PartyReason" />
<portlet:resourceURL id="/part/meeting/page" var="PartyMeetingPage" />
<!DOCTYPE html>
<html>
	<head>
		<title>组织部审批</title>
		<style>
			#tBody .color_blue{
				color:#CE0000;
			}
			#tBody .color_blue_1{
				color:#FF0000;
			}

			@media(min-width:768px){
				.table_info .PublishTime{
					min-width:200px;
				}
				.table_info .LaunchTime{
					min-width:200px;
				}
			}

			/* 录入弹窗样式 */

	        #input .form-group>div {
	            height: 34px;
	            margin-bottom: 10px;
	        }

	        #input .col-sm-9,
	        #input .col-xs-8 {
	            padding: 0;
	        }

	        .file_input {
	            height: 100px;
	            border: 1px dashed #d8d8d8;
	            border-radius: 4px;
	        }

	        .file_input input {
	            width: 77px;
	            height: 77px;
	            opacity: 0;
	            position: absolute;
	            top: 50%;
	            left: 50%;
	            margin-left: -38.5px;
	            margin-top: -38.5px;
	            z-index: 5;
	        }

	        .file_input .show_path {
	            width: 100%;
	            height: 100%;
	            position: absolute;
	            top: 0;
	            left: 0;
	            line-height: 32px;
	            text-indent: 1em;
	            text-align: center;
	            overflow-y: auto;
	        }

	        .show_path img {
	            width: 77px;
	            margin: 10.5px 0;
	        }

	        .show_path p {
	            text-align: left;
	            color: #999;
	            font-size: 13px;
	            line-height: 15px;
	        }
	        /* 录入弹窗样式 */

			.content_table thead tr{
				background: #e5e5e5;
				height: 48px;
				font-size: 16px;
			}
			.content_table thead th{
				padding: 5px 15px !important;
			}
			.content_table tr:nth-child(2n) {
				background: #EDF3FF;
			}
			.content_table td{
				min-width: 130px;
				padding: 5px 15px !important;
				height: 48px;
				font-size: 14px;
				border-bottom-width: 1px;
				border-bottom-style: solid;
				border-bottom-color: #e6e6e6;
			}

			.table_outer_box > table thead, tbody tr {
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
	<body class="front">

		<div class="table_form_content">
			<!-- 右侧盒子内容 -->
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

			<!-- 模态框（Modal） -->
			 <div class="modal fade" id="input" tabindex="-1" role="dialog" aria-labelledby="inputLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
							&times;
						</button>
							<h4 class="modal-title" id="inputLabel">驳回备注信息</h4>
						</div>
						<div class="modal-body content_form">
							<form class="form-horizontal" role="form">
								<div class="form-group">
									<div class="col-sm-12 col-xs-12">
										<span class="col-sm-3 col-xs-4 control-label">驳回理由：</span>
										<div class="col-sm-9 col-xs-8">
	<!-- 	                                    <input type="text" class="form-control" id="_should"/> -->
											<div style="position:relative;">
												<span style="margin-left:100px;width:18px;overflow:hidden;">
													<select id="reject_select" style="width:235px;margin-left:-86px;height:31px;" onchange="getReason()">
														 <!-- <option value="m3/s">m3/s</option>
														 <option value="mm">mm</option>
														 <option value="℃">℃</option>
														 <option value="KV">KV</option>   -->
													 </select>
												 </span>
											  <input type="text" id="_should" style="width: 215px;margin-left: -239px;height: 31px;color:#000;">
										   </div>
										 </div>
									</div>

								</div>
							</form>
						</div>
						<div class="modal-footer">
							<input type="hidden" id="entry_id" value="" />
							<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
							<button type="button" class="btn btn_main" onclick="Write()">确定</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
	<script type="text/html" id="meetingPlanTableBtns">
		{{#  if(d.task_status == "1"){ }}
		<%--<a class="layui-btn layui-btn-xs" lay-event="check">审核</a>--%>
		<a class="layui-btn layui-btn-xs" onclick="Pass('${d.meeting_id }');">
			通过</a>
		<a  class="layui-btn layui-btn-xs" onclick="entry('${d.meeting_id }');">
			驳回</a>
		{{#  } }}
		{{#  if(d.task_status == '4' || d.task_status == '5' || d.task_status == '6'){ }}
		<a class="layui-btn layui-btn-xs" href="/sendplan?meetingId=${d.meeting_id }&orgType=secondary&type=edit"> 编辑</a>
		<%--<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>--%>
		{{#  } }}
		<a class="layui-btn layui-btn-xs" lay-event="detail">查看</a>
	</script>
	<script type="text/javascript">
		layui.use(['table','layer','form'], function() {
			var table = layui.table,
					layer = layui.layer,
					form = layui.form;
			renderTable();
			form.on('submit(searchForm)', function (data) {
				renderTable();
			})
			function renderTable(){
				var  where = {
					keyword: $("#searchForm input[name=keyword]").val()
				};
				table.render({
					elem: '#meetingPlanTable',
					where: where,
					height:560,
					url: '${PartyMeetingPage}', //数据接口
					method: 'post',
					page: {
						limit:10,   //每页条数
						limits:[10,15,20],
						prev:'&lt;上一页',
						next:'下一页&gt;',
						theme: '#FFB800',
						groups:4
					},
					cols: [[ //表头
						{field: 'org_name', align:'center',width:320, title: '二级党组织'},
						{field: 'meeting_type', align:'center', title: '活动类型'},
						{field: 'meeting_theme', align:'center',width:320, title: '活动主题',templet:function(d){
								return '<a  href="/approvaldetails?meetingId='+d.meeting_id+'&orgType=secondary">'+d.meeting_theme+'</a>';
							}},
						{field: 'start_time', align:'center', title: '开始时间',width:180,templet: function(d){return new Date(d.start_time).format("yyyy-MM-dd hh:mm:ss");}},
						{field: 'total_time', align:'center', title: '时长',width:100,templet: function(d){return d.total_time/60;}},
						{field: 'member_name', align:'center', title: '联系人'},
						{field: 'task_status', align:'center', title: '任务状态',width:120,templet: function(d){
								var status = '';
								switch(parseInt(d.task_status)){
									case 1:status = '待审核';break;
									case 2:status = '已撤回';break;
									case 3:status = '已驳回';break;
									case 4:status = '已通过';break;
									case 5:status = '已指派';break;
									case 6:status = '未检查';break;
									case 7:status = '已检查';break;
								}
								return status;
							}},
						{field: 'operation', align:'center', title: '操作',width:200,toolbar: '#meetingPlanTableBtns'},

					]]
				});
				$(".layui-table-view .layui-table-page").addClass("layui-table-page-center");
				$(".layui-table-view .layui-table-page").removeClass("layui-table-page");
				//监听事件
				table.on('tool(meetingPlanTable)', function(obj){
					switch(obj.event){
						case 'check':
							//renderDetail('check',obj);
							break;
						case 'detail':
							// renderDetail('check',obj);
							break;
					};
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
		function Pass(meeting_id){
			$.hgConfirm("提示","确认通过?");
			$("#hg_confirm").modal("show");
			$("#hg_confirm .btn_main").click(function(){
				var url = "${PartyPass}";
				$.ajax({
					url:url,
					data:{"meeting_id":meeting_id},
					dataType:'json',
					success:function(){
						$("#hg_confirm").modal("hide");
						$.tip("审核成功");
						window.location.reload();
					}
				});
			})
		}
		//点击驳回
		function entry(meetingId){
			$('#entry_id').val(meetingId);
			$("#input").modal("show");
			$("#reject_select").html("");
			var url = "${PartyReason}";
			$.ajax({
				url:url,
				data:{},
				dataType:'json',
				async:false,
				success:function(res){
					console.log(res);
					for(var i=0;i<res.length;i++){
						var _option = '<option data-id="'+ res[i].id +'" value="'+ res[i].resources_value +'">'+ res[i].resources_value +'</option>'
						$("#reject_select").append(_option);
					}
				}
			});
		}

		//驳回保存
		function Write(){
			var meeting_id2 = $('#entry_id').val();//会议id
			var should_ = $('#_should').val(); //驳回备注
			var url = "${PartyRejected}";
			$.ajax({
				url:url,
				data:{meeting_id2:meeting_id2,should_:should_},
				dataType:'json',
				async:false,
				success:function(succee){
					$('#input').modal('hide');
					window.location.reload();
				}
			});
		}

		//用于选择理由更换赋值给文本框
		function getReason(){
			var text=$("#reject_select").val();
			$("#_should").val(text);
		}
	</script>
</html>