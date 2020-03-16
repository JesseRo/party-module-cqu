<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/init.jsp" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript" src="${basePath}/js/partyModal.js"></script>
<link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/activity-manage1.css?v=1"/>
<link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/common.min.css"/>
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

	        .export_excel {
				font-size: 13px;
				    /* margin: 10px 0; */
				    padding: 5px 0;
				    display: inline-block;
				}
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
		</style>
	</head>
	<body class="front">
		<portlet:resourceURL id="/PartyOrganizationsRejectedCommand" var="PartyRejected" />
		<!-- 		驳回理由 -->
		<portlet:resourceURL id="/PartyReasonCommand" var="PartyReason" />
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
<%--				<table id="activityTable" lay-filter="activityTable" class="custom_table"></table>--%>
<%--				<div class="layui-form layui-border-box layui-table-view" lay-filter="LAY-table-1" lay-id="activityTable"--%>
<%--					 style=" ">--%>
<%--					<div class="layui-table-box">--%>
<%--						<div class="layui-table-header">--%>
<%--							<table cellspacing="0" cellpadding="0" border="0" class="layui-table">--%>
<%--								<thead>--%>
<%--								<tr>--%>
<%--									<th data-field="name" data-key="1-0-0" class="" style="width: 14.28%;">--%>
<%--										<div class="layui-table-cell laytable-cell-1-0-0"><span>党支部</span></div>--%>
<%--									</th>--%>
<%--									<th data-field="name" data-key="1-0-0" class="" style="width: 14.28%;">--%>
<%--										<div class="layui-table-cell laytable-cell-1-0-0"><span>会议类型</span></div>--%>
<%--									</th>--%>
<%--									<th data-field="name" data-key="1-0-0" class="" style="width: 14.28%;">--%>
<%--										<div class="layui-table-cell laytable-cell-1-0-0"><span>会议主题</span></div>--%>
<%--									</th>--%>
<%--									<th data-field="name" data-key="1-0-0" class="" style="width: 14.28%;">--%>
<%--										<div class="layui-table-cell laytable-cell-1-0-0"><span>开始时间</span></div>--%>
<%--									</th>--%>
<%--									<th data-field="name" data-key="1-0-0" class="" style="width: 14.28%;">--%>
<%--										<div class="layui-table-cell laytable-cell-1-0-0"><span>开展时长(分钟)</span></div>--%>
<%--									</th>--%>
<%--									<th data-field="name" data-key="1-0-0" class="" style="width: 14.28%;">--%>
<%--										<div class="layui-table-cell laytable-cell-1-0-0"><span>联系人</span></div>--%>
<%--									</th>--%>
<%--									<th data-field="name" data-key="1-0-0" class="" style="width: 14.28%;">--%>
<%--										<div class="layui-table-cell laytable-cell-1-0-0"><span>操作</span></div>--%>
<%--									</th>--%>
<%--								</tr>--%>
<%--								</thead>--%>
<%--							</table>--%>
<%--						</div>--%>
<%--						<div class="layui-table-body layui-table-main">--%>
<%--							<table cellspacing="0" cellpadding="0" border="0" class="layui-table">--%>
<%--								<tbody>--%>
<%--								<c:forEach items="${list}" var="info" varStatus="status">--%>
<%--								<tr data-index="9" class="" id="${c.inform_id }">--%>
<%--									<td data-field="name" data-key="1-0-0" class=""  style="width: 14.28%;">--%>
<%--										<div class="layui-table-cell laytable-cell-1-0-0">${info.org_name }</div>--%>
<%--									</td>--%>
<%--									<td data-field="name" data-key="1-0-0" class=""  style="width: 14.28%;">--%>
<%--										<div class="layui-table-cell laytable-cell-1-0-0">--%>
<%--												${info.meeting_type }--%>
<%--										</div>--%>
<%--									</td>--%>
<%--									<td data-field="name" data-key="1-0-0" class=""  style="width: 14.28%;">--%>
<%--										<div class="layui-table-cell laytable-cell-1-0-0">--%>
<%--											<a href="/approvaldetails?meetingId=${info.meeting}&orgType=secondary">${info.meeting_theme_secondary }</a>--%>
<%--										</div>--%>
<%--									</td>--%>
<%--									<td data-field="name" data-key="1-0-0" class=""  style="width: 14.28%;">--%>
<%--										<div class="layui-table-cell laytable-cell-1-0-0">--%>
<%--											<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${info.start_p }" />--%>
<%--										</div>--%>
<%--									</td>--%>
<%--									<td data-field="name" data-key="1-0-0" class=""  style="width: 14.28%;">--%>
<%--										<div class="layui-table-cell laytable-cell-1-0-0">--%>
<%--												${info.total_time }--%>
<%--										</div>--%>
<%--									</td>--%>
<%--									<td data-field="name" data-key="1-0-0" class=""  style="width: 14.28%;">--%>
<%--										<div class="layui-table-cell laytable-cell-1-0-0">--%>
<%--												${info.contact }--%>
<%--										</div>--%>
<%--									</td>--%>
<%--									<td data-field="name" data-key="1-0-0" class=""  style="width: 14.28%;">--%>
<%--										<div class="layui-table-cell laytable-cell-1-0-0">--%>
<%--											<c:if test="${info.task_st == '1'}">--%>
<%--												<portlet:resourceURL id="/PartyOrganizationsPassCommand" var="PartyPass" />--%>
<%--												<a onclick="Pass('${info.meeting }');">--%>
<%--													通过</a>--%>
<%--												<a class="_clickshow" onclick="entry('${info.meeting }');">--%>
<%--													驳回</a>--%>
<%--												<script>--%>
<%--													//点击通过 弹窗提示示例--%>
<%--													function Pass(meeting_id){--%>
<%--														$.hgConfirm("提示","确认通过?");--%>
<%--														$("#hg_confirm").modal("show");--%>
<%--														$("#hg_confirm .btn_main").click(function(){--%>
<%--															var url = "${PartyPass}";--%>
<%--															$.ajax({--%>
<%--																url:url,--%>
<%--																data:{"meeting_id":meeting_id},--%>
<%--																dataType:'json',--%>
<%--																success:function(){--%>
<%--																	$("#hg_confirm").modal("hide");--%>
<%--																	$.tip("审核成功");--%>
<%--																	window.location.reload();--%>
<%--																}--%>
<%--															});--%>
<%--														})--%>
<%--													}--%>
<%--												</script>--%>
<%--											</c:if>--%>
<%--											<c:if test="${info.task_st == '4' || info.task_st == '5' || info.task_st == '6'}">--%>
<%--												<a href="/sendplan?meetingId=${info.meeting}&orgType=secondary&type=edit">编辑</a>--%>
<%--											</c:if>--%>
<%--										</div>--%>
<%--									</td>--%>
<%--								</tr>--%>
<%--								</c:forEach>--%>
<%--								</tbody>--%>
<%--							</table>--%>
<%--						</div>--%>
<%--					</div>--%>
<%--				</div>--%>
				<div class="bg_white_container">
					<div class="table_outer_box">
						<table class="layui-table custom_table">
							<thead>
								<tr>
									<td>二级党组织</td>
									<td>会议类型</td>
			<%--                            <th class="PublishTime" style="min-width: 160px;">发布时间</th>--%>
			<%--                            <th>开展主题</th>--%>
									<td>二级党组织主题</td>
									<td>开始时间</td>
									<td style="width: 70px;">时长</td>
			<%--                            <th>开展地点</th>--%>
			<%--                            <th>主持人</th>--%>
									<td>联系人</td>
			<%--                            <th>联系人电话</th>--%>
									<td style="width: 120px;">任务状态</td>
			<%--                            <th>审核人</th>--%>
									<td>操作</td>
			<%--                            <th>备注</th>--%>
								</tr>
							</thead>
							<tbody class="table_info" id="tBody">
								<c:forEach items="${list}" var="info" varStatus="status">
									<tr>
										<td data-label="二级党组织">${info.org_name }</td>
										<td data-label="会议类型">${info.meeting_type }</td>
			<%--	                            <td data-label="发布时间" class="PublishTime"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${info.submit_time }" /></td>--%>
										<td data-label="开展主题">
											<a href="javascript:;" onclick="window.location.href='/approvaldetails?meetingId=${info.meeting}&orgType=secondary'">${info.meeting_theme }</a>
										</td>
			<%--	                            <td data-label="二级党组织主题" style="min-width: 175px;">${info.meeting_theme_secondary }</td>--%>
										<td data-label="开始时间" >
											<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${info.start_p }" />
										</td>
										<td data-label="开展时长" style="width: 70px;">${info.total_time/60 }</td>
			<%--	                            <td data-label="开展地点">${info.place }</td>--%>
			<%--	                            <td data-label="主持人">${info.host }</td>--%>
										<td data-label="联系人">${info.contactName }</td>
			<%--	                            <td data-label="联系人电话">${info.contact_phone }</td>--%>
										<td data-label="任务状态" style="width: 120px;">
											<c:if test="${info.task_st == '1'}">
												 待审核
											</c:if>
											<c:if test="${info.task_st == '2'}">
												 已撤回
											</c:if>
											<c:if test="${info.task_st == '3'}">
												 已驳回
											</c:if>
											<c:if test="${info.task_st == '4'}">
												 已通过
											</c:if>
											<c:if test="${info.task_st == '5'}">
												 已指派
											</c:if>
											<c:if test="${info.task_st == '6'}">
												 未检查
											</c:if>
											<c:if test="${info.task_st == '7'}">
												 已检查
											</c:if>
										</td>
			<%--	                            <td data-label="审核人">${info.auditor }</td>--%>
										<td data-label="操作">
											<c:if test="${info.task_st == '1'}">
												<portlet:resourceURL id="/PartyPassCommand" var="PartyPass" />
												<a onclick="Pass('${info.meeting }');" style="margin-right: 10%; color: #11D43B">
													通过</a>
												<a class="_clickshow" onclick="entry('${info.meeting }');" style="cursor: pointer;color: #FE4D4D;">
													驳回</a>
											</c:if>
											<c:if test="${info.task_st == '4' || info.task_st == '5' || info.task_st == '6'}">
												<a href="javascript:;" onclick="window.location.href='/sendplan?meetingId=${info.meeting}&orgType=secondary&type=edit'"  style="color: #2E87FF">编辑</a>
											</c:if>
										</td>
			<%--	                            <td data-label="备注">${info.remark }</td>--%>
									</tr>
								</c:forEach>
							</tbody>
					</table>
					</div>
            <portlet:resourceURL id="/ApprovalMeetingExcelCommand" var="export"/>
<%--             <a class="export_excel" href="${export }">数据导出excel</a>   --%>

   <!-- -----------------------分页------------------------- -->
				<div class="pagination_container">
			        <ul class="pagination" id="page"></ul>
			        <div class="pageJump">
			        	<input class='current_page' type="hidden" value="${pageNo}"/>
			            <p>共<span class="total_page">${totalPage }</span>页</p>
			            <portlet:actionURL name="/PageNoMVCActionCommand" var="pageNoUrl">
						</portlet:actionURL>
			            <form action="${pageNoUrl }" id="getPageNo" method="post">
			                <input type="hidden" id="pageNo" name="pageNo" value=""/>
			                <input type="hidden" id="total_page_" name="total_page_" value="${totalPage}"/>
			                <span>跳转到第</span>
			                <input type="text" id="jumpPageNo" name="jumpPageNo"/>
			                <span>页</span>
			                <button type="submit" class="button">确定</button>
			            </form>
			        </div>
				 </div>
				</div>
				 <script type="text/javascript">
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
					  $(document).ready(function() {
						 var pages = $(".total_page").html();
						var currentPage = $('.current_page').val();
						$("input[name='pageNo']").val($('.current_page').val());
						if(currentPage == 1){
							$('.page_next').removeClass('not_allow');
							$('.page_prev').addClass('not_allow');

						}else if(currentPage == pages){
							$('.page_prev').removeClass('not_allow');
							$('.page_next').addClass('not_allow');

						}else{

						};
						$("#jumpPageNo").change(function(){
							$("input[name='pageNo']").val($(this).val());
						})
				     Page({
				         num: pages, //页码数
				         startnum: currentPage, //指定页码
				         elem: $('#page'), //指定的元素
				         callback: function(n) { //回调函数
				             $("input[name='pageNo']").val(n);
// 				         	alert($("input[name='pageNo']").val());
				             $("#getPageNo").submit();
				             if (n == 1) {
				                 $('#page a').removeClass('not_allow');
				                 $('.page_prev').addClass('not_allow');
				             } else if (n >= $('.total_page').html()) {
				                 $('#page a').removeClass('not_allow');
				                 $('.page_next').addClass('not_allow')
				             } else {
				                 $('#page a').removeClass('not_allow');
				             }
				         }
				     });
				  });

		</script>
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
	    <script type="text/javascript">
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
	</body>
</html>