<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn" %>
<%@ include file="/init.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title>党支部-待办</title>
    <link rel="stylesheet" href="${basePath }/css/party_member.css" />
    <link rel="stylesheet" href="${basePath }/css/party_organization.css" />
<style type="text/css">
.themeth{
    display: inline-block;
    text-overflow: ellipsis;
    overflow: hidden;
    width: 180px;
    white-space: nowrap;
    line-height: 50.4px;
 }
 .replaytable th, td{
    text-align: left;
}
      
 select#taskStatus {
    border: none;
    background: #f7f7f7;
}
 select#meetingType {
    width: 90px;
    border: none;
    background: #f7f7f7;
}  
    </style>
     <portlet:resourceURL id="/hg/getMeetingTypeAndTheme" var="getMeetingTypeAndTheme"/>
</head>
<body>
                <div class="content_title">
                                                   待办事项                           
                </div>
                 <input type="hidden" class="inform_id"/>
                 <input type="hidden" class="pub_org_id"/>
                 <div class="content_table_container" style="margin-top: 0px;">
	                <table class="content_table">
	                    <thead class="table_title">
	                        <tr>
	                        	<th>消息状态</th>
	                            <th>
									<select id="meetingType" class="meetingtype height_34" onchange="typeStatusAjax()" >
								 		<option value="">会议类型</option>
								 		<option value="">全部</option>
									 <c:forEach var="c" items="${meetingTypeList}"  varStatus="status">
									 	<c:choose>
											<c:when test="${meetingType == c.resources_key }">
									 			<option value="${c.resources_key}" selected>${c.resources_value}</option>
											</c:when>
											<c:otherwise>
												<option value="${c.resources_key}">${c.resources_value}</option>
											</c:otherwise>
										</c:choose>
								 	</c:forEach>
						        	</select>
								</th>
	                            <th >会议主题</th>
	                            <th style="min-width:180px">发布时间</th>
	                            <th style="min-width:180px">截止时间</th>
	                            <th>
									<select id="taskStatus" class="taskstatus height_34" onchange="typeStatusAjax()">
										<option value="">任务状态</option>
								 		<option value="">全部</option>
									<c:forEach var="c" items="${taskStatusList}"  varStatus="status">
										<c:choose>
											<c:when test="${taskStatus == c.resources_key }">
									 			<option value="${c.resources_key}" selected>${c.resources_value}</option>
											</c:when>
											<c:otherwise>
												<option value="${c.resources_key}">${c.resources_value}</option>
											</c:otherwise>
										</c:choose>
									 </c:forEach>
							        </select>
								</th>
	                            <th>操作</th>
	                            <th>已读回执</th>
	                            <th>上传会议记录</th>
	                            <th>备注</th>
	                        </tr>
	                    </thead>
	                   
	                    <tbody class="table_info">                  
		                       <c:forEach var="c" items="${list }"> 
		                        <tr>
		                        	 <td data-label="消息状态" >
										<c:choose>
											<c:when test="${c.read_status eq '未读'}">
											 	<span style="color:red;">未读</a>
											</c:when>
											<c:otherwise>
												已查看
											</c:otherwise>
										</c:choose>
									</td>
		                            <td data-label="会议类型" >
		                                <input type="hidden" value="${c.inform_id }"/>
		                                <input type="hidden" value="${c.pub_org_id }"/>
		                                 <c:if test="${empty c.mmeetingtype}">
											 ${c.imeetingtype}	
										</c:if>
										<c:if test="${not empty c.mmeetingtype}">
											 ${c.mmeetingtype}		
										</c:if>
		                            </td>
		                            <td data-label="会议主题" class="themeth">
		                            	<c:if test="${empty c.mmeetingtype}">
											<a href="/checkdetail?informId=${c.inform_id }&orgId=${c.pub_org_id}" title="${c.imeetingtheme}">${c.imeetingtheme}</a>
										</c:if>
										<c:if test="${not empty c.mmeetingtype}">
											<a href="/checkdetail?informId=${c.inform_id }&orgId=${c.pub_org_id}" title="${c.mmeetingtheme}">${c.mmeetingtheme}</a>
										</c:if>
		                            </td>
		                            <td data-label="发布时间" class="publicth">${fn:substring(c.release_time,0,19)}</td>
		                            <td data-label="截止时间" class="deadline_time">${fn:substring(c.deadline_time,0,19)}</td>
		                            <td data-label="任务状态">
			                            	<c:if test="${c.task_status eq '1'}">已提交</c:if>
											<c:if test="${c.task_status eq '2'}">已撤回</c:if>
											<c:if test="${c.task_status eq '3'}">被驳回</c:if>
											<%-- <c:if test="${c.task_status eq '4' ||c.task_status eq '5' || c.task_status eq '6' || c.task_status eq '7'}">
			　                                                      --%>  
											<c:if test="${c.task_status eq '4'}">已通过</c:if>
											<c:if test="${c.task_status gt 4}">已通知</c:if>
		                            </td>
				            <td data-label="操作" >
							  <c:if test="${empty c.task_status }">
								  <a href="/submitplan?orgId=${c.pub_org_id }&infromId=${c.inform_id }">拟定计划</a>
						　　    </c:if>
			                  <c:if test="${c.task_status eq '1' }">
			                  <a href="javascript:;" class="cancl_reson">撤回</a>
			                       <input type="hidden" value="${c.inform_id }"/>
			                       <input type="hidden" value="${c.pub_org_id }"/>
			　　                   	  </c:if>  
			                  <c:if test="${c.task_status eq '2' || c.task_status eq '3'}">
			                  	<a href="/submitplan?meetingId=${c.meeting_id }">重拟计划</a>
			　　                   	  </c:if>
				              <c:if test="${c.task_status eq '4'}">
				              	<a href="/informparty?meetingId=${c.meeting_id }">通知党员</a>
			                     <input type="hidden" value="${c.inform_id }"/>
			                     <input type="hidden" value="${c.pub_org_id }"/>
			　　                          </c:if>
				            </td>
			                <td data-label="已读回执">
			                      <%--  <c:if test="${c.task_status eq '5' || c.task_status eq '6' || c.task_status eq '7'}">
			　                                                   --%> 
								<c:if test="${c.task_status gt 4}">
								     <a class="check_reply_state">查看</a>
				                     <input type="hidden" value="${c.inform_id }"/>
				                     <input type="hidden" value="${c.pub_org_id }"/>
			　　                                                </c:if>
			                 </td>
			                 <td data-label="上传会议记录">
				                             <%-- <c:if test="${empty c.check_status && (c.task_status eq '7' || c.task_status eq '6' || c.task_status eq '5')}">
			                         --%>
			                      <c:if test="${empty c.check_status && (c.task_status gt 4)}">
			                      		<a href="/uploadnotes?meetingId=${c.meeting_id}&ogrtype=branch">上传</a>	
			                       </c:if>
			                       <c:if test="${c.check_status eq '已上传'}">已上传</c:if>
			                 </td>
			                 <td data-label="备注">
			                  	<c:if test="${ empty c.mremark}">${c.iremark}</c:if>
								<c:if test="${not empty c.mremark}">${c.mremark}</c:if>
			                  </td>
				            </tr>　 
	　　        			</c:forEach>  
	                   </tbody>
	               </table>  
                 </div>
                 <portlet:resourceURL id="/hg/exportbranchToDoList" var="exportbranchToDoList"/>
<%--                  <a href="${exportbranchToDoList }">数据导出excel</a>  --%>
                  <!--        分页              数据下载          -->
             <div class="pagination_container">
		        <ul class="pagination" id="page"></ul>
		        <div class="pageJump">
		        	<input class='current_page' type="hidden" value="${pageNo}"/>
		            <p>共<span class="total_page">${pages }</span>页</p>
                    <portlet:actionURL name="/PageNoMVCActionCommand" var="pageNoUrl">
					</portlet:actionURL>
		            <form action="${pageNoUrl}" id="getPageNo" method="post">
		                <input type="hidden" id="pageNo" name="pageNo"/>
		                <input type="hidden" id="total_page_" name="total_page_" value="${pages}"/>
		                <span>跳转到第</span>
		                <input type="text" id="jumpPageNo" name="jumpPageNo"/>
		                <span>页</span>
   		                <input type="hidden" id="mt" name="meetingType"/>
   		                <input type="hidden" id="ts" name="taskStatus"/>
		                
		                <button type="submit" class="button">确定</button>
		            </form>
		        </div>
		    </div>
                
   <%--  <!--    分页              -->
	        <div class="pagination_container">
		        <ul class="pagination" id="page"></ul>
		        <div class="pageJump">
		        	<input class='current_page' type="hidden" value="${pageNo}"/>
		            <p>共<span class="total_page">${pages }</span>页</p>
		            <portlet:actionURL name="/PageNoMVCActionCommand" var="pageNoUrl">
					</portlet:actionURL>
		            <form action="${pageNoUrl}" id="getPageNo" method="post">
		                <input type="hidden" id="pageNo" name="pageNo" value=""/>
		                <span>跳转到第</span>
		                <input type="text" id="jumpPageNo" name="jumpPageNo"/>
		                <span>页</span>
		                 <input label="站点id" name="Site"  value="${Site }" type="hidden"/>
		                <input label="栏目id" name="Column"  value="${Column }" type="hidden"/>
		                 <input type="hidden" id="date_type" name="meetingTheme" value="${meetinType }"/>
		                 <input type="hidden" id="date_typeValue" name="meetingTheme" value="${meetingTheme }"/>
		                
		                <input type="hidden" id="mt" name="meetingType"/>
   		                <input type="hidden" id="ts" name="taskStatus"/>
		                <button type="submit" class="button">确定</button>
		            </form>
		        </div>
	  	 </div>
	 </div>	
	 <!-- 分页 --> --%>
	 
  
  
    <!--撤回弹窗  -->
      <!-- 撤回模态框 -->
    <!-- <a href="javascript:;" data-toggle="modal" data-target="#recall">撤回</a> -->
    <!-- 模态框（Modal） -->
    
    
    
      <!--撤回弹窗  -->
      <!-- 撤回模态框 -->
<!--     <a href="javascript:;" data-toggle="modal" data-target="#recall">撤回</a> -->
    <!-- 模态框（Modal） -->
    <div class="modal fade" id="recall" tabindex="-1" role="dialog" aria-labelledby="recallLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                    <h4 class="modal-title" id="recallLabel">撤回</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" role="form">
                        <div class="form-group">
                            <div class="col-sm-12 col-xs-12">
                            	请确认撤回该计划？
                                <input type="hidden" class="form-control reasonContent" placeholder="请输入撤回理由" />
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn_main">确定</button>
                </div>
            </div>
        </div>
    </div>
    
    
    
    
    <!-- 
    <div class="modal fade" id="recall" tabindex="-1" role="dialog" aria-labelledby="recallLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                    <h4 class="modal-title" id="recallLabel">撤回</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" role="form">
                        <div class="form-group">
                            <div class="col-sm-12 col-xs-12">
                                <input type="text" class="form-control reasonContent" placeholder="请输入撤回理由" />
                               <p class="form-control reasonContent">你确定需要撤回吗？</p>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn_main">确定</button>
                </div>
            </div>
        </div>
    </div> -->
     <!-- 查看模态框 -->
   <!--  <a href="javascript:;" data-toggle="modal" data-target="#check">查看已读回执</a> -->
    <!-- 模态框（Modal） -->
   
	
    <div class="modal fade check" id="check" tabindex="-1" role="dialog" aria-labelledby="checkLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                     &times;
                   </button>
                    <h4 class="modal-title" id="checkLabel">已读回执</h4>
                </div>
                <div class="modal-body">
                    <table class="col-sm-12 col-xs-12 replaytable">
                        <thead>
                            <tr>
                                <th>姓名</th>
                                <th class="select_state">
                                    <!-- <select class="select_state">
                                        <option value="allState">已读/未读</option>
                                        <option value="read">已读</option>
                                        <option value="noRead">未读</option>
                                    </select> -->
                                                                                                             通知
                                    <input class="reply_infromId" type="hidden">
                                    <input class="reply_orgId" type="hidden">
                                  
                                </th>
<!--                                 <th>评分</th> -->
                                <th>是否上传心得</th>
                            </tr>
                        </thead>
                        <tbody class="tbody_reply">
                          
                        </tbody>

                    </table>
                </div>
<!--                 <div class="modal-footer"> -->
<!--                     <button type="button" class="btn btn_main">确定</button> -->
<!--                 </div> -->
            </div>
        </div>
    </div>
    <portlet:resourceURL id="/hg/deleteGrafts" var="deleteGrafts"/>
    <portlet:resourceURL id="/hg/publicGrafts" var="publicGrafts"/>
    <portlet:resourceURL id="/hg/taskCancle" var="taskCancle"/>
    <portlet:resourceURL id="/hg/taskCheckReplyState" var="taskCheckReplyState"/>
     <portlet:renderURL var="showCommentDetailUrl">
		<portlet:param name="mvcRenderCommandName" value="/hg/showCommentDetail"/>
	</portlet:renderURL>
	
	<portlet:renderURL var="showExperienceDetailUrl">
		<portlet:param name="mvcRenderCommandName" value="/hg/showExperienceDetail"/>
	</portlet:renderURL>
	
	
    <script>
	$(document).ready(function() {
		
		$(".deadline_time").each(function(){		 
	          if(new Date($(this).html())<new Date()){
	               $(this).css("color","red");
	               if( $(this).next().next().find("a").html()==='拟定计划'){
	            	   $(this).next().next().find("a").removeAttr("href"); 
	               }
	          }
		   });
		
	
		
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
		});
	    Page({
	        num: pages, //页码数
	        startnum: currentPage, //指定页码
	        elem: $('#page'), //指定的元素
	        callback: function(n) { //回调函数
	            $("input[name='pageNo']").val(n);
	        	$("#mt").val($("#meetingType").val());
	        	$("#ts").val($("#taskStatus").val());
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
	
    
        //撤回
        $(".table_info").on("click",".cancl_reson",function(){
        	 $("#recall").modal("show");
        	 $(".table_info").find(".cancl_reson_value").removeClass("cancl_reson_value");
        	 $(this).addClass("cancl_reson_value");
        });
        
        //撤回确定按钮
        $(".btn_main").click(function(){
        	var infrom_id= $(".table_info").find(".cancl_reson_value") .next().val();
        	var org_id=$(".table_info").find(".cancl_reson_value").next().next().val();
            var url="${taskCancle}";
            var reasonContent=$(".reasonContent").val();
            $.ajax({
      	      url:url,
      	      data:{infromId:infrom_id,orgId:org_id,cancleReson:reasonContent},
      	      dataType:"text",
      	      success:function(succee){ 
      	    	  console.log(succee);
      	    	      if("SUCCEE"==succee){window.location.reload();}
      	              }
                });
        });
        
        
        
        /*查看*/
        $(".table_info").on("click",".check_reply_state",function(){
        	$("#check").modal("show");
        	var infromId=$(this).next().val();
        	var orgId=$(this).next().next().val();
        	$(".reply_infromId").val(infromId);
        	$(".reply_orgId").val(orgId);
        	var url="${taskCheckReplyState}";
        	/* var url1="${showCommentDetailUrl}"; */
        	var url1="/showevaluation?meet=1";
        	var url2="${showExperienceDetailUrl}";
        	
            $.ajax({
        	      url:url,
        	      data:{infromId:infromId,orgId:orgId,path:"allState"},
        	      dataType:"json",
        	      async: false,
        	      success:function(data){ 
        	    	  $(".tbody_reply").empty();
            	    	 for(var i=0;i<data.length;i++){
        	    		  var c=data[i];
       	   	    		  if(!c.user_name){continue;}
        	    		  if(c.check_status == '未读'){
        	    			  if(c.comments_state == 1){
        	    				  if(c.upload_state == 1){
         	    					 /*  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>未读</td> <td><a class="seeScoreDetail" href="'+ url1 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">'+ c.comments_score +'分</a></td><td><a class="seeExperienceDetail" href="'+ url2 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">是</a></td> </tr>') ; */
        	    					  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已通知</td> <td><a class="seeExperienceDetail" href="'+ url2 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">是</a></td> </tr>') ;
        	    				  }else{
         	    					  /* $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>未读</td> <td><a class="seeScoreDetail" href="'+ url1 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">'+ c.comments_score +'分</a></td><td>否</td> </tr>') ;  */
        	    					  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已通知</td> <td>否</td> </tr>') ; 
        	    				  }
        	    			  }else{
        	    				  if(c.upload_state == 1){
         	    					 /*  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>未读</td> <td></td><td><a class="seeExperienceDetail" href="'+ url2 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">是</a></td> </tr>') ;  */
        	    					  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已通知</td> <td><a class="seeExperienceDetail" href="'+ url2 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">是</a></td> </tr>') ; 
        	    				  }else{
         	    					/*   $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>未读</td> <td></td><td>否</td> </tr>') ;  */
        	    					  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已通知</td> <td>否</td> </tr>') ; 
        	    				  }
        	    			  }
       	   	    		  }else{
   		       	   	    		if(c.comments_state == 1){
   			  	    				  if(c.upload_state == 1){
    			  	    					/*   $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已读</td> <td><a class="seeScoreDetail" href="'+ url1 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">'+ c.comments_score +'分</a></td><td><a class="seeExperienceDetail" href="'+ url2 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">是</a></td> </tr>') ;  */
   			  	    				 	  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已通知</td> <td><a class="seeExperienceDetail" href="'+ url2 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">是</a></td> </tr>') ; 
   			  	    				  }else{
   			  	    					/*   $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已读</td> <td ><a class="seeScoreDetail" href="'+ url1 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">'+ c.comments_score +'分</a></td><td>否</td> </tr>') ;  */
   			  	    					  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已通知</td> <td>否</td> </tr>') ; 
   			  	    				  }
   			  	    			  }else{
   			  	    				  if(c.upload_state == 1){
    			  	    					/*   $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已读</td> <td></td><td><a class="seeExperienceDetail" href="'+ url2 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">是</a></td> </tr>') ;  */
   			  	    					  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已通知</td> <td><a class="seeExperienceDetail" href="'+ url2 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">是</a></td> </tr>') ; 
   			  	    				  }else{
    			  	    					/*   $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已读</td> <td></td><td>否</td> </tr>') ;  */
   			  	    					$(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已通知</td> <td>否</td> </tr>') ; 
   			  	    				  }
   			  	    			  }
       	   	    		  }
        	    		  
        	    		 /*  $(".seeExperienceDetail").attr("href",url1 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id);
        	    		  $(".seeScoreDetail").attr("href",url2 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id);
        	    		   */
       	   	    		  
        	    	    }
        	      	}
                });
            });
        
        
        
       

        /*sss*/
        $(".select_state").change(function(){
   	        	var state= $(".select_state").val();
   	        	var infromId=$(".reply_infromId").val();
   	        	var orgId=$(".reply_orgId").val();
   	        	var url="${taskCheckReplyState}";
   	        	/* var url1="${showCommentDetailUrl}"; */
   	        	var url1="/showevaluation?meet=1";
   	        	var url2="${showExperienceDetailUrl}";
        	 $.ajax({
       	      url:url,
       	      data:{infromId:infromId,orgId:orgId,state:state,path:"find"},
       	      dataType:"json",
       	      success:function(data){ 
       	    	 $(".tbody_reply").empty();
       	    	 for(var i=0;i<data.length;i++){
   	    		  var c=data[i];
   	    		 if(c.check_status == '未读'){
   	    			  if(c.comments_state == 1){
   	    				  if(c.upload_state == 1){
    	    					 /*  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>未读</td> <td><span class="score">'+ c.comments_score +'分</span></td><td><a class="seeExperienceDetail" href="'+ url2 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">是</a></td> </tr>') ;  */
   	    					$(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已通知/td> <td><a class="seeExperienceDetail" href="'+ url2 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">是</a></td> </tr>') ; 
   	    				  }else{
    	    					/*   $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>未读</td> <td><a class="seeScoreDetail" href="'+ url1 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">'+ c.comments_score +'分</a></td><td>否</td> </tr>') ;  */
   	    					  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已通知</td> <td>否</td> </tr>') ; 
   	    				  }
   	    			  }else{
   	    				  if(c.upload_state == 1){
   	    					 /*  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>未读</td> <td></td><td><a class="seeExperienceDetail" href="'+ url2 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">是</a></td> </tr>') ;  */
   	    					  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已通知</td> <td><a class="seeExperienceDetail" href="'+ url2 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">是</a></td> </tr>') ; 
   	    				  }else{
    	    					/*   $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>未读</td> <td></td><td>否</td> </tr>') ;  */
   	    					  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已通知</td> <td>否</td> </tr>') ; 
   	    				  }
   	    			  }
   	   	    		  }else{
      	   	    		if(c.comments_state == 1){
   	  	    				  if(c.upload_state == 1){
  	  	    					/*   $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已读</td> <td><a class="seeScoreDetail" href="'+ url1 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">'+ c.comments_score +'分</a></td><td><a class="seeExperienceDetail" href="'+ url2 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">是</a></td> </tr>') ;  */
   	  	    					$(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已通知</td> <td><a class="seeExperienceDetail" href="'+ url2 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">是</a></td> </tr>') ; 
   	  	    				  }else{
  	  	    					/*   $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已读</td> <td ><a class="seeScoreDetail" href="'+ url1 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">'+ c.comments_score +'分</a></td><td>否</td> </tr>') ;  */
   	  	    					$(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已通知</td> <td>否</td> </tr>') ; 
   	  	    				  }
   	  	    			  }else{
   	  	    				  if(c.upload_state == 1){
    	  	    					 /*  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已读</td> <td></td><td><a class="seeExperienceDetail" href="'+ url2 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">是</a></td> </tr>') ;  */
   	  	    					  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已通知</td> <td><a class="seeExperienceDetail" href="'+ url2 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">是</a></td> </tr>') ; 
   	  	    				  }else{
   	  	    					  /* $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已读</td> <td></td><td>否</td> </tr>') ;  */
   	  	    					  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已通知</td> <td>否</td> </tr>') ; 
   	  	    				  }
   	  	    			  }
   	   	    		  }
   	    	    }  
       	      }
          });
      });
        
        
        $(".seeScoreDetail").click(function(){
         	$("#check").modal("hide");
         });
        
        
        $(".seeExperienceDetail").click(function(){
         	$("#check").modal("hide");
         });
        
        
        
        
        function  typeStatusAjax(){
    		$("input[name='pageNo']").val(1);
    	   	$("#mt").val($("#meetingType").val());
        	$("#ts").val($("#taskStatus").val());
            $("#getPageNo").submit();
    	} 
     	 
      
      /*  根据 会议类型 查询 
      $(".meetingType").change(function(){
    	  var dValue=$(".meetingType").val();
    	  $("#date_type").val("meetingType");
    	  $("#date_typeValue").val(dValue);
    	  $("#getPageNo").submit();
      })
        根据 任务状态 查询 
          $(".taskState").change(function(){
    	  var dValue=$(".taskState").val();
    	  $("#date_type").val("taskState");
    	  $("#date_typeValue").val(dValue);
    	  $("#getPageNo").submit();
      }) */
    </script>
</body>

</html>