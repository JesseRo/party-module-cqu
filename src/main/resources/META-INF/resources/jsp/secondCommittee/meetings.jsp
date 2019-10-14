<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn" %>
<%@ include file="/init.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title>二级党委-待办事项</title>
<%--     <link rel="stylesheet" href="${basePath }/css/common.css" /> --%>
    <link rel="stylesheet" href="${basePath }/css/party_member.css" />
    <link rel="stylesheet" href="${basePath }/css/party_organization.css" />
<%--     <link rel="stylesheet" href="${basePath }/css/bootstrap.min.css" /> --%>
        <script type="text/javascript" src="${basePath}/js/My97DatePicker/WdatePicker.js"></script>
     <style type="text/css">
	
	
	.content_table td{
	min-width: 150px;
	}
	
	  .replaytable th, td{
	text-align: left;
	}
   /* 转派给党支部弹窗样式 */
    
    #transmit .publish_obj_container {
        border: 1px solid #d8d8d8;
        padding: 10px 0;
    }
    
    #transmit .publish_obj_title {
        border-bottom: 1px solid #d8d8d8;
        padding-bottom: 10px;
    }
    
    #transmit .select_choice:nth-child(1) {
        margin-right: 10px;
    }
    
    #transmit .publish_obj_title .right .select_choice {
        display: inline-block;
    }
    
    #transmit .list-group {
        max-height: 150px;
        overflow-y: auto;
        margin-top: 10px;
        margin-bottom: 0px;
    }
    
    #transmit .publish_obj_info .list-group-item {
        border: none;
        font-size: 13px;
        color: #666;
        padding: 5px 10px;
        cursor: pointer;
    }
    select.height_34{
    	height:34px;
    }
        /* 转派给党支部弹窗样式 */
td.publicDate {
    min-width: 170px;
      }
       td, th {
    text-align: left;
    padding: 0;
} 

.content_table td {
    min-width: 160px;
}

select#taskStatus {
    border: none;
    background: #f7f7f7;
}

select#meetingType {
    /* size: 20px; */
    /* text-align: right; */
    border: none;
    background: #f7f7f7;
    width: 90px;
}
    </style>
</head>
	<body>

		<portlet:renderURL var="showConferenceDetailUrl">
			<portlet:param name="mvcRenderCommandName" value="/hg/showConferenceDetail"/>
		</portlet:renderURL>
		
		<portlet:renderURL var="submitPlanUrl">
			<portlet:param name="mvcRenderCommandName" value="/hg/SubmitPlan"/>
		</portlet:renderURL>
		
		<portlet:actionURL name="/hg/postSubmissions" var="submitForm"/> 
		<portlet:resourceURL id="/hg/taskCancle" var="taskCancle"/>
		<portlet:resourceURL id="/secondary/loadBranch" var="loadBranchUrl" />
		<portlet:resourceURL id="/secondary/resend" var="resendUrl" />
		 <portlet:resourceURL id="/hg/taskCheckReplyState" var="taskCheckReplyState"/>
		 
		 
		<portlet:renderURL var="showCommentDetailUrl">
			<portlet:param name="mvcRenderCommandName" value="/hg/showCommentDetail"/>
		</portlet:renderURL>
		<portlet:renderURL var="showExperienceDetailUrl">
			<portlet:param name="mvcRenderCommandName" value="/hg/showExperienceDetail"/>
		</portlet:renderURL>
	
		<div class="content_title hidden-xs">
                    待办事项
        </div>
                <div class="content_table_container">
                    <table class="content_table">
                        <thead class="table_title">
				 	<tr>
<%--						<th>消息状态</th>--%>
<%--						<th>--%>
<%--							<select id="meetingType" class="meetingtype height_34" onchange="typeStatusAjax()" >--%>
<%--						 		<option value="">会议类型</option>--%>
<%--						 		<option value="">全部</option>--%>
<%--							 <c:forEach var="c" items="${meetingTypeList}"  varStatus="status">--%>
<%--							 	<c:choose>--%>
<%--									<c:when test="${meetingType == c.resources_key }">--%>
<%--							 			<option value="${c.resources_key}" selected>${c.resources_value}</option>--%>
<%--									</c:when>--%>
<%--									<c:otherwise>--%>
<%--										<option value="${c.resources_key}">${c.resources_value}</option>--%>
<%--									</c:otherwise>--%>
<%--								</c:choose>--%>
<%--						 	</c:forEach>--%>
<%--				        	</select>--%>
<%--						</th>--%>
						<th>会议主题</th>
                        <th>上报主题</th>
						<th>发布时间</th>
						<th>截止时间</th>
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
				  		
				<c:forEach var="c" items="${informMeetingList}"  varStatus="status">
						<tr class="oneinfo" id="${c.inform_id }">
<%--								<td data-label="消息状态" >--%>
<%--									<c:choose>--%>
<%--										<c:when test="${c.read_status == '未读'}">--%>
<%--										 	<span style="color:red">未读</a>--%>
<%--										</c:when>--%>
<%--										<c:otherwise>--%>
<%--											已查看--%>
<%--										</c:otherwise>--%>
<%--									</c:choose>--%>
<%--								</td>--%>
<%--								<td data-label="会议类型" >--%>
<%--									<c:if test="${empty c.mmeetingtype}">--%>
<%--										 ${c.imeetingtype}--%>
<%--									</c:if>--%>
<%--									<c:if test="${not empty c.mmeetingtype}">--%>
<%--										 ${c.mmeetingtype}--%>
<%--									</c:if>--%>

<%--								</td>--%>
								<td data-label="会议主题" >
								
<%--									<c:if test="${empty c.mmeetingtype}">--%>
<%--										<a href="/checkdetail?informId=${c.inform_id }&orgId=${c.pub_org_id}" >${c.imeetingtheme}</a>--%>
<%--									</c:if>--%>
<%--									<c:if test="${not empty c.mmeetingtype}">--%>
<%--										<a href="/checkdetail?informId=${c.inform_id }&orgId=${c.pub_org_id}" >${c.mmeetingtheme}</a>--%>
<%--									</c:if>--%>
                                    ${c.imeetingtheme}
								</td>
								<td data-label="上报主题" >${c.meetingthemesecondary}</td>
								
								<td data-label="发布时间" class="publicDate" >
									${fn:substring(c.release_time,0,19)}
								</td>
								<td data-label="截止时间" class="deadline_time" >
									${fn:substring(c.deadline_time,0,19)}
								</td>
								<td data-label="任务状态" >
									<c:if test="${empty c.task_status}">

									</c:if>
									<c:choose>
										<c:when test="${not empty c.task_status and c.task_status gt 4 }">
											${statusMap[4]}
										</c:when>
										<c:otherwise>
											${statusMap[c.task_status]}
										</c:otherwise>
									</c:choose>
								</td>
								
								<td data-label="操作" >
									
									<c:if test="${empty c.task_status  && (c.meeting_type != '专题组织生活会' && c.send_to == 't') && !c.has_resend}">
										 <a href="/newinfo?informId=${c.inform_id}&resend=resend">部署</a>
									</c:if>

									<c:if test="${empty c.task_status && (c.meeting_type != '专题组织生活会' && c.send_to == 'f') && !c.has_resend }">
										<a href="/sendplan?infromId=${c.inform_id}&orgId=${c.pub_org_id}">拟定计划</a>
									</c:if>





									<c:if test="${c.task_status eq 1}">
										<a href="javascript:;" class="cancl_reson">撤回</a>
							            <input type="hidden" value="${c.inform_id }"/>
                                 		<input type="hidden" value="${c.pub_org_id }"/>
									</c:if>

									<c:if test="${empty c.task_status && c.org_type eq orgId }">
										<a href="/branchview?inform_id=${c.inform_id}">查看进度</a>
									</c:if>
									<c:if test="${c.task_status eq 4}">
										<a href="/informparty?meetingId=${c.meeting_id}">通知党员</a>
									</c:if>
									<c:if test="${c.task_status eq 2||c.task_status eq 3}">
										<a href="/sendplan?meetingId=${c.meeting_id}">重拟计划</a>
									</c:if>
								</td>
								
								<td data-label="已读回执" >
	                              <c:if test="${c.task_status gt 4}">
	　                                                                                         <a class="check_reply_state" href="javascript:;">查看</a>
	                                 <input type="hidden" value="${c.inform_id }"/>
	                                 <input type="hidden" value="${c.pub_org_id }"/>
	　　                                                                            </c:if>
	                            </td>
								
								<td data-label="上传会议记录" >
									<c:if test="${c.check_status != '已上传' && (c.task_status gt 4) }">
										<a href="/uploadnotes?meetingId=${c.meeting_id}">上传</a>	
									</c:if>
									
									<c:if test="${c.check_status eq '已上传'}">
										已上传
									</c:if>
								</td>
								
								<td data-label="备注" >
									<c:if test="${ empty c.mremark}">
										${c.iremark}
									</c:if>
									<c:if test="${not empty c.mremark}">
										${c.mremark}
									</c:if>
								</td>
							</tr>
						</c:forEach>
				  
				  </tbody>
               </table>
          <portlet:resourceURL id="/hg/exportseconedToDoList" var="exportseconedToDoList"/>
<%--           <a href="${exportseconedToDoList }">数据导出excel</a>   --%>
                </div>
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
				
		
		
		
	
    
    
    
    
    <!-- 模态框（Modal） -->
    <div class="modal fade" id="transmit" tabindex="-1" role="dialog" aria-labelledby="transmitLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                    <h4 class="modal-title" id="transmitLabel">部署</h4>
                </div>
                <div class="modal-body">
                    <div class="col-sm-12 col-xs-12 publish_obj_container">
                        <div class="col-sm-12 col-xs-12 publish_obj">
                            <div class="publish_obj_content">
                                <div class="publish_obj_title">
                                                                                                      所辖党支部
                                    <div class="right">
                                        <div class="select_choice all_select">
                                            <img src="${basePath }/images/not_check_icon.png" />
                                            <input type="hidden" />
                                            <span>全选</span>
                                        </div>
                                        <div class="select_choice oppsite_select">
                                            <img src="${basePath }/images/not_check_icon.png" />
                                            <input type="hidden" />
                                            <span>反选</span>
                                        </div>
                                    </div>

                                </div>
                                <div class="publish_obj_info container_scroll_hidden">
                                    <ul class="list-group col-sm-12 col-xs-12" id="branch-list">
                                        
                                    </ul>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal" id="cancel_resend">取消</button>
                    <button type="button" class="btn btn_main" id="resend">部署</button>
                </div>
            </div>
        </div>
    </div>
    
    
    
    
    
    
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
                                   <!--  <select class="select_state">
                                        <option value="allState">已读/未读</option>
                                        <option value="read">已读</option>
                                        <option value="noRead">未读</option>
                                    </select>-->
                                    <input class="reply_infromId" type="hidden">
                                    <input class="reply_orgId" type="hidden">
                                   
                                                                                                   通知
                                </th>
<!--                                 <th>评分</th> -->
                                <th>是否上传心得</th>
                            </tr>
                        </thead>
                        <tbody class="tbody_reply">
                          
                        </tbody>

                    </table>
                </div>
                <!-- <div class="modal-footer">
                    <button type="button" class="btn btn_main">确定</button>
                </div> -->
            </div>
        </div>
    </div>
    
    
    
    
    
    
	</body>
	
	<script>
	$(document).ready(function() {
		
		$(".deadline_time").each(function(){		 
	          if(new Date($(this).html())<new Date()){
	               $(this).css("color","red");
	               var name=$(this).next().next().find("a").html();
	               if(name ==='拟定计划' || name === "部署"){
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
		})
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

    /*常用人员 添加*/
    $(".add_btn").on("click", ">.btn", function() {
        $(this).siblings("form").css("display", "inline-block");
    });

    /*取消添加弹窗*/
    $(".add_btn").on("click", ".form_hide_btn", function() {
        $(this).parents("form").css("display", "none");
        $(this).parents(".form-group").find(".add_member_input").val("");
    });
    
   /*转派党支部*/
    $('.load-branch').on('click', function(){
    	var informId = $(this).parent().parent().attr('id');
    	$.post("${loadBranchUrl}", {}, function(res){
    		if(res.result){
    			$('#branch-list').attr('informId', informId);
    			var branchLi = "<li class=\"list-group-item col-sm-6 col-xs-12\">" +
	                				"<span id=\"{{branchId}}\" title=\"马克思主义学院党委\">{{branchName}}</span>" + 
	                				"<input type=\"hidden\" />" + 
	                				"<img class=\"right\" src=\"${basePath }/images/radio.png\" />" +
            					"</li>";
            	var html = '';
            	for(var i in res.data){
            		var branch = res.data[i];
            		html += branchLi.replace("{{branchId}}", branch.org_id).replace("{{branchName}}", branch.org_name);
            	}
            	
    		     $('#branch-list').html(html);
    		}else{
    			alert(res.message);
    		}
    	});
    });
    
    /*转派*/
    $('#resend').on('click', function(){
    	var selected = [];
    	$('.publish_obj_info li').each(function() {
    		var branch = $(this);
    		var branchId = branch.find('input').val();
    		if(branchId === branch.find('span').attr('id')){
    			selected.push(branchId);
    		}
        });
    	if(selected.length > 0){
    		var informId = $('#branch-list').attr('informId');
    		$.post("${resendUrl}", {branches: selected.join(','), informId: informId}, function(res){
    			if(res.result){
    				alert("已指派");
    				$('.load-branch').parent().html('<a href="/branchview?inform_id="' + informId + '>查看进度</a>	');
					

    				$('#cancel_resend').click();
    			}else{
    				alert(res.message);
    			}
    		});
    	}
    });
    
    /*sss*/
    $(".publish_obj_info").on("click", "li", function() {
            $(".publish_obj_content .select_choice").find("img").attr("src", "${basePath}/images/not_check_icon.png");
            var _text = $(this).find("span").attr('id');
            var oldSrc = $(this).find(".right").attr("src");
            var newSrc = "";
            if (oldSrc.indexOf("on") > 0) {
                newSrc = oldSrc.replace(/_on.png/, ".png");
                $(this).find("input").val("");
            } else {
                newSrc = oldSrc.replace(/.png/, "_on.png");
                $(this).find("input").val(_text);
            }
            $(this).find(".right").attr("src", newSrc);
            var sum = $(".publish_obj_info").find("img.right").length;
            var imgNum = $(".publish_obj_info").find("img[src='${basePath}/images/radio_on.png']").length;
            if (sum == imgNum) {
                $(".all_select").find("img").attr("src", "${basePath}/images/checked_icon.png");
                $(".oppsite_select").find("img").attr("src", "${basePath}/images/not_check_icon.png");
            } else {
                $(".all_select").find("img").attr("src", "${basePath}/images/not_check_icon.png");
            }
        });

    
    
        /*二级党委全选*/
        $(".all_select").click(function() {
            $(this).find("img").attr("src", "${basePath}/images/checked_icon.png");
            $(this).siblings(".oppsite_select").find("img").attr("src", "${basePath}/images/not_check_icon.png");
            $(".publish_obj_info").find("img.right").each(function() {
                $(this).attr("src", "${basePath}/images/radio_on.png");
            });
        });

        /*二级党委反选*/
        $(".oppsite_select").click(function() {
            $(this).find("img").attr("src", "${basePath}/images/checked_icon.png");
            $(this).siblings(".all_select").find("img").attr("src", "${basePath}/images/not_check_icon.png");
            $(".publish_obj_info").find("img.right").each(function() {
                var Src = $(this).attr("src");
                if (Src.indexOf("on") > 0) {
                    $(this).attr("src", "${basePath}/images/radio.png");
                } else {
                    $(this).attr("src", "${basePath}/images/radio_on.png");
                }
            });
        });

    
    
	function  typeStatusAjax(){
		$("input[name='pageNo']").val(1);
	   	$("#mt").val($("#meetingType").val());
    	$("#ts").val($("#taskStatus").val());
        $("#getPageNo").submit();
	} 
 	 
 	 
 	 /*撤回*/
     $(".table_info").on("click",".cancl_reson",function(){
     	 $(".table_info").find(".cancl_reson_value").removeClass("cancl_reson_value");
     	 $(this).addClass("cancl_reson_value");
     	 $("#recall").modal("show");
     });
     
     /*撤回确定按钮*/
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
     	    					  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已通知</td> <td><a class="seeScoreDetail" href="'+ url1 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">'+ c.comments_score +'分</a></td><td><a class="seeExperienceDetail" href="'+ url2 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">是</a></td> </tr>') ; 
     	    				  }else{
     	    					  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已通知</td> <td><a class="seeScoreDetail" href="'+ url1 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">'+ c.comments_score +'分</a></td><td>否</td> </tr>') ; 
     	    				  }
     	    			  }else{
     	    				  if(c.upload_state == 1){
     	    					  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已通知</td> <td><a class="seeExperienceDetail" href="'+ url2 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">是</a></td> </tr>') ; 
     	    				  }else{
     	    					  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已通知</td> <td>否</td> </tr>') ; 
     	    				  }
     	    			  }
    	   	    		  }else{
		       	   	    		if(c.comments_state == 1){
			  	    				  if(c.upload_state == 1){
			  	    					  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已通知</td> <td><a class="seeScoreDetail" href="'+ url1 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">'+ c.comments_score +'分</a></td><td><a class="seeExperienceDetail" href="'+ url2 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">是</a></td> </tr>') ; 
			  	    				  }else{
			  	    					  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已通知</td> <td ><a class="seeScoreDetail" href="'+ url1 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">'+ c.comments_score +'分</a></td><td>否</td> </tr>') ; 
			  	    				  }
			  	    			  }else{
			  	    				  if(c.upload_state == 1){
			  	    					  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已通知</td> <td><a class="seeExperienceDetail" href="'+ url2 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">是</a></td> </tr>') ; 
			  	    				  }else{
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
	    		 if(!c.user_name){continue;}
	    		 if(c.check_status == '未读'){
	    			  if(c.comments_state == 1){
	    				  if(c.upload_state == 1){
 	    					 /*  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>未读</td> <td><span class="score">'+ c.comments_score +'分</span></td><td><a class="seeExperienceDetail" href="'+ url2 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">是</a></td> </tr>') ;  */
	    					  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>未读</td> <td><a class="seeExperienceDetail" href="'+ url2 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">是</a></td> </tr>') ; 
	    				  }else{
 	    					 /*  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>未读</td> <td><a class="seeScoreDetail" href="'+ url1 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">'+ c.comments_score +'分</a></td><td>否</td> </tr>') ;  */
 	    					  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>未读</td> <td>否</td> </tr>') ; 
	    				  }
	    			  }else{
	    				  if(c.upload_state == 1){
	    					 /*  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>未读</td> <td></td><td><a class="seeExperienceDetail" href="'+ url2 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">是</a></td> </tr>') ;  */
	    					  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>未读</td> <td><a class="seeExperienceDetail" href="'+ url2 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">是</a></td> </tr>') ; 
	    				  }else{
 	    					  /* $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>未读</td> <td></td><td>否</td> </tr>') ;  */
	    					  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>未读</td> <td>否</td> </tr>') ;
	    				  }
	    			  }
	   	    		  }else{
   	   	    		if(c.comments_state == 1){
	  	    				  if(c.upload_state == 1){
 	  	    					/*   $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已读</td> <td><a class="seeScoreDetail" href="'+ url1 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">'+ c.comments_score +'分</a></td><td><a class="seeExperienceDetail" href="'+ url2 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">是</a></td> </tr>') ;  */
	  	    					  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已读</td> <td><a class="seeExperienceDetail" href="'+ url2 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">是</a></td> </tr>') ; 
	  	    				  }else{
	  	    					 /*  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已读</td> <td ><a class="seeScoreDetail" href="'+ url1 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">'+ c.comments_score +'分</a></td><td>否</td> </tr>') ;  */
	  	    					  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已读</td> <td>否</td> </tr>') ; 
	  	    				  }
	  	    			  }else{
	  	    				  if(c.upload_state == 1){
 	  	    					 /*  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已读</td> <td></td><td><a class="seeExperienceDetail" href="'+ url2 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">是</a></td> </tr>') ;  */
	  	    					  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已读</td> <td><a class="seeExperienceDetail" href="'+ url2 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">是</a></td> </tr>') ; 
	  	    				  }else{
 	  	    					 /*  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已读</td> <td></td><td>否</td> </tr>') ;  */
	  	    					  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已读</td> <td>否</td> </tr>') ; 
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
     
     
     /* 截止时间 */
   
  /*  function deadline_time(){
	   $(".deadline_time").each(function(){		 
	          if(new Date($(this).html())>new Date()){
	               $(this).css("color","red");
	          }
		   });
     }
 	  */
	</script>
	
</html>