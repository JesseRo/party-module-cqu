<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/init.jsp" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript" src="${basePath}/js/partyModal.js"></script>

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
		</style>
	</head>
	<body class="front">
		<portlet:resourceURL id="/PartyOrganizationsRejectedCommand" var="PartyRejected" />
		<!-- 		驳回理由 -->
		<portlet:resourceURL id="/PartyReasonCommand" var="PartyReason" />
		<div class="content_title" style="margin-bottom:30px;">
                                                    审批计划
        </div>
		<div class="content_table_container">
                <table class="content_table">
                    <thead class="table_title">
                        <tr>
                            <th>二级党组织</th>
                            <th>会议类型</th>
                            <th class="PublishTime" style="min-width: 160px;">发布时间</th>
                            <th>开展主题</th>
                            <th style="min-width: 175px;">二级党组织主题</th>
                            <th class="LaunchTime" style="min-width: 160px;">开始时间</th>
                            <th>开展时长(分钟)</th>
                            <th>开展地点</th>
                            <th>主持人</th>
                            <th>联系人</th>
                            <th>联系人电话</th>
                            <th>任务状态</th>
                            <th>审核人</th>
                            <th>操作</th>
                            <th>备注</th>
                        </tr>
                    </thead>
                    <tbody class="table_info" id="tBody">
                   	    <c:forEach items="${list}" var="info" varStatus="status">
	                        <tr>
	                            <td data-label="二级党组织" class="Party_name">${info.org_name }</td>
	                            <td data-label="会议类型">${info.meeting_type }</td>
	                            <td data-label="发布时间" class="PublishTime"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${info.submit_time }" /></td>
	                            <td data-label="开展主题">
	                            	<a href="/approvaldetails?meetingId=${info.meeting}&orgType=secondary">${info.meeting_theme }</a>
	                            </td>
	                            <td data-label="二级党组织主题" style="min-width: 175px;">${info.meeting_theme_secondary }</td>
	                            <td data-label="开始时间" class="LaunchTime"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${info.start_p }" /></td>
	                            <td data-label="开展时长">${info.total_time }</td>
	                            <td data-label="开展地点">${info.place }</td>
	                            <td data-label="主持人">${info.host }</td>
	                            <td data-label="联系人">${info.contact }</td>
	                            <td data-label="联系人电话">${info.contact_phone }</td>
	                            <td data-label="任务状态">
	                            	<c:if test="${info.task_st == '1'}"> 
	                            		 已提交
									</c:if>
									<c:if test="${info.task_st == '2'}"> 
	                            		 已撤回
									</c:if>
									<c:if test="${info.task_st == '3'}"> 
	                            		 被驳回
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
	                            <td data-label="审核人">${info.auditor }</td>
	                            <td data-label="操作">
	                            	<c:if test="${info.task_st == '1'}">  
									    <portlet:resourceURL id="/PartyOrganizationsPassCommand" var="PartyPass" />
										<a onclick="Pass('${info.meeting }');">
											通过</a>
										<a class="_clickshow" onclick="entry('${info.meeting }');">
											驳回</a>
											
										<script>
// 											function Pass(meeting_id){
// 												if(confirm("确定通过吗？")){
// 													var url = "${PartyPass}";
// 													$.ajax({
// 														url:url,
// 														data:{"meeting_id":meeting_id},
// 									 					dataType:'json',
// 														success:function(){ 
// 															window.location.reload();	
// 														}
// 													});
// 												}
// 											}

											//点击通过 弹窗提示示例
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
										</script>
									</c:if>
									<c:if test="${info.task_st == '4' || info.task_st == '5' || info.task_st == '6'}">
										<a href="/sendplan?meetingId=${info.meeting}&orgType=secondary&type=edit">编辑</a>
									</c:if>
	                            </td>
	                            <td data-label="备注">${info.remark }</td>
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
		
				 <script type="text/javascript">
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