<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/init.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<head>
	<title>人员会议统计</title>
	<style>
		@media(min-width:768px){
			.table_info .publish_time{
				min-width:200px;
			}
			.content_info .content_form {
                padding: 0px 20px 0 20px;
            }
		}
		
		th,td{
		     text-align: left; 
		}
		.content_info .content_form .form-group .control-label {
             text-align: right;
             color: #333;
             font-size: 16px;
        }
	</style>
<script type="text/javascript" src="${basePath}/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${basePath}/js/pagination.js"></script>
<portlet:resourceURL id="/hg/assignedd" var="assigned"/>
<portlet:resourceURL id="/hg/assignedd/branch" var="assignedBranch"/>
<portlet:resourceURL id="/hg/getMeetingTypeAndThemed" var="getMeetingTypeAndTheme"/>
<script type="text/javascript">
 function getBranch(seconedId){
	   $(".party_branch").empty();
	   $(".party_branch").append('<option value="">--选择--</option>')
	   var branchId=$(".party_branch_id").val();
	   var url="${assigned}";	  
	   var pid=seconedId;
	   var data={paramType:"partyBranch",pid:pid};
	   $.ajax({
		   url:url,
		   data:data,
		   dataType:"json",
		   success:function(result){
			  for(var i=0;i<result.length;i++){
		         if(branchId&&branchId==result[i].org_id){
		        	 $(".party_branch").prepend('<option selected="selected" value="'+result[i].org_id+'">'+result[i].org_name+'</option>')
		         }else{
		        	 $(".party_branch").append('<option value="'+result[i].org_id+'">'+result[i].org_name+'</option>')
 
		         }
			  }
	          }
		   });
} 
$(function(){
	   var orgId="${seconedId}";
	   var meetTheme="${theme}";
	   var meetType="${meetType}";
	   var branchId="${branchId}";
	   var meetTheme="${meetTheme}";
	   var seconedId="${seconedId}";
	   var url="${assigned}";
	   var data={paramType:"sconedParty"};
	   $.ajax({
		   url:url,
 		   data:data,
 		   dataType:"json",
 		   success:function(result){
 			         for(var i=0;i<result.length;i++){
 			        	 if(orgId&&orgId==result[i].org_id){
 			        		 $(".sconed_party").prepend('<option selected="selected" value="'+result[i].org_id+'">'+result[i].org_name+'</option>') 
 			        	 }else{
 			        		 $(".sconed_party").append('<option value="'+result[i].org_id+'">'+result[i].org_name+'</option>') 
 			        	 }
 			         }
 		     }
	   });
	  
	 //姓名框改变事件
	   $("#userName").change(function(){
		   $(".sconed_party").empty();
		   $(".sconed_party").append('<option value="">--选择--</option>')
		   var url = "${assigned}";
		   var pid = $("#userName").val();
		   var data = {paramType:"sconed_party",pid:pid};
		   $.ajax({
			   url:url,
	 		   data:data,
	 		   dataType:"json",
	 		   success:function(result){
	 			  for(var i=0;i<result.length;i++){
			        	 $(".sconed_party").append('<option value="'+result[i].org_names+'">'+result[i].org_names+'</option>')
			         }
	 	          }
	 		   });
	   });
	 
	 //二级党组织框改变事件
	   $("#seconedId").change(function(){
		   $(".party_branch").empty();
		   $(".party_branch").append('<option value="">--选择--</option>')
		   var url = "${assignedBranch}";
		   var pid = $("#seconedId").val();
		   var userName = $("#userName").val();
		   var data = {paramType:"branchId",pid:pid,userName:userName};
		   $.ajax({
			   url:url,
	 		   data:data,
	 		   dataType:"json",
	 		   success:function(result){
	 			  for(var i=0;i<result.length;i++){
			        	 $(".party_branch").append('<option value="'+result[i].org_na+'">'+result[i].org_na+'</option>')
			         }
	 	          }
	 		   });
	   });
	   //如果二级党组织框内容不为空
	   var seconedName = $("#seconedId").val();
	   if(seconedName != ""){
		   $(".party_branch").empty();
		   $(".party_branch").append('<option value="">--选择--</option>')
		   var url = "${assignedBranch}";
		   var pid = $("#seconedId").val();
		   var userName = $("#userName").val();
		   var data = {paramType:"branchId",pid:pid,userName:userName};
		   $.ajax({
			   url:url,
	 		   data:data,
	 		   dataType:"json",
	 		   success:function(result){
	 			  for(var i=0;i<result.length;i++){
			        	 $(".party_branch").append('<option value="'+result[i].org_na+'">'+result[i].org_na+'</option>')
			         }
	 	          }
	 		   });
	   }
	   //二级党组织查询 secondary
	   var typ = "${orgType}";
	   $("#userName").change(function(){
		   if(typ == "secondary"){
			   $(".party_branch").empty();
			   $(".party_branch").append('<option value="">--选择--</option>')
			   var url = "${assignedBranch}";
			   var pid = "secondary";
			   var userName = $("#userName").val();
			   var data = {paramType:"branchId",pid:pid,userName:userName};
			   $.ajax({
				   url:url,
		 		   data:data,
		 		   dataType:"json",
		 		   success:function(result){
		 			  for(var i=0;i<result.length;i++){
				        	 $(".party_branch").append('<option value="'+result[i].org_na+'">'+result[i].org_na+'</option>')
				         }
		 	          }
		 		   }); 
		   }
	   });
	   //二级党组织查询（判断姓名框不为空）
	   var nameInput = $("#userName").val();
	   if(typ == "secondary" && nameInput != ""){
		   $(".party_branch").empty();
		   $(".party_branch").append('<option value="">--选择--</option>')
		   var url = "${assignedBranch}";
		   var pid = "secondary";
		   var userName = $("#userName").val();
		   var data = {paramType:"branchId",pid:pid,userName:userName};
		   $.ajax({
			   url:url,
	 		   data:data,
	 		   dataType:"json",
	 		   success:function(result){
	 			  for(var i=0;i<result.length;i++){
			        	 $(".party_branch").append('<option value="'+result[i].org_na+'">'+result[i].org_na+'</option>')
			         }
	 	          }
	 		   });
	   }
	   
       $.ajax({
           url: '${getMeetingTypeAndTheme}',  
           type: 'POST',  
           data: "",
           dataType:'json',
           async: false,   
           success: function (data) {  
                   for(var i=0;i<data.length;i++){
                    var c=data[i];
                   	if(c.type=='meetingType'){
                       if(meetType&&meetType==c.resources_value){
                      		$("select[name='meetType']").prepend('<option selected="selected" value="'+c.resources_value+'">'+c.resources_value+'</option>');
                       }else{
                      		 $("select[name='meetType']").append('<option value="'+c.resources_value+'">'+c.resources_value+'</option>');
                       }
                   	}	
                   	if(c.type=='news'){
                   		if(meetTheme&&c.resources_value == meetTheme){
                       		$("select[name='theme']").prepend('<option selected="selected" value="'+c.resources_value+'">'+c.resources_value+'</option>');
                        }else{
                       		 $("select[name='theme']").append('<option value="'+c.resources_value+'">'+c.resources_value+'</option>');
                        }
                   	}	
                  }
           },  
           error: function (data) {  
                  alert("获取数据失败");  
           }  
    });
       getBranch(seconedId);   
});	   
	   
	
	</script>
</head>
<body>
      <div class="content_title" style="margin-bottom:30px;">
                   人员会议统计
         </div> 
		<div class="content_form">
        	<portlet:actionURL var="findURL" name="/meeting/FindRenderd">
			</portlet:actionURL>
           	<form class="form-horizontal" role="form" action="${findURL }" method="post">
	           	<div class="form-group">
	           	
	           		<div class="col-sm-6 col-xs-12">
	                    <span class="col-sm-3 col-xs-3 control-label">人员姓名</span>
	                    <div class="col-sm-9 col-xs-9">
	                           <input type="text" class="form-control"  name="userName" id="userName" value="${userName}">
	                    </div>
	                </div>
	                <c:if test = "${orgType != 'branch' }">
	                	<c:if test = "${orgType != 'secondary' }">
			                <div class="col-sm-6 col-xs-12">
			                    <span class="col-sm-3 col-xs-3 control-label" >二级党组织</span>
			                    <div class="col-sm-9 col-xs-9">
				                    <input type="hidden" class="party_branch_id" value="${seconedId}">
									<select class="form-control sconed_party"  name="seconedId" id="seconedId">
										<option value="${seconedId}">${seconedId}</option>
					                </select> 
			                    </div>
			                </div>
		                </c:if> 
		                <div class="col-sm-6 col-xs-12">
		                    <span class="col-sm-3 col-xs-3 control-label">党支部</span>
		                    <div class="col-sm-9 col-xs-9">
		                        <input type="hidden" class="party_branch_id" value="${branchId}">
								<select class="form-control party_branch"  name="branchId" id="branchId">
									<option value="${branchId}">${branchId}</option>
			                    </select> 
		                    </div>
		                </div>
	           		</c:if> 
	           		
	           	<%--
	           	   <div class="col-sm-6 col-xs-12">
	                    <span class="col-sm-3 col-xs-3 control-label">会议类型</span>
	                    <div class="col-sm-9 col-xs-9">
	                        <select class="form-control"  name="meetType" id="meetType">
						      <option value="">请选择</option>
		                    </select> 
	                    </div>
	                </div>
	           	
	           	     <div class="col-sm-6 col-xs-12">
	                    <span class="col-sm-3 col-xs-3 control-label">会议主题</span>
	                    <div class="col-sm-9 col-xs-9">
	                           <input type="text" class="form-control"  name="meetTheme" id="meetTheme" value="${meetTheme}">
	                    </div>
	                </div>
	           	    
	           	     <div class="col-sm-6 col-xs-12">
	                    <span class="col-sm-3 col-xs-3 control-label" >二级党组织</span>
	                    <div class="col-sm-9 col-xs-9">
						 <select class="form-control sconed_party"  name="seconedId" id="seconedId">
		                   <option value="">请选择</option>
		                 </select> 
	                    </div>
	                </div>
	             
	               <div class="col-sm-6 col-xs-12">
	                    <span class="col-sm-3 col-xs-3 control-label">党支部</span>
	                    <div class="col-sm-9 col-xs-9">
	                        <input type="hidden" class="party_branch_id" value="${branchId}">
							<select class="form-control party_branch"  name="branchId" id="branchId">
								
		                    </select> 
	                    </div>
	                </div>     
	           	                 	           	                
	            	<div class="col-sm-6 col-xs-12">
	                    <span class="col-sm-3 col-xs-3 control-label">开始时间</span>
	                    <div class="col-sm-9 col-xs-9">
                             <input type="text" name="startTime" id="labCheckDate"  value="${startTime }"
                             onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"   class="form-control start_date">
	                    </div>
	                </div>
	                
	                <div class="col-sm-6 col-xs-12">
	                    <span class="col-sm-3 col-xs-3 control-label">结束时间</span>
	                    <div class="col-sm-9 col-xs-9">
                             <input type="text" name="endTime" id="labCheckEndDate"  value="${endTime }"
                             onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"   class="form-control start_date">
	                    </div>
	                </div> --%>
	                      	
	            </div>	
	            <portlet:resourceURL id="/hg/ExclMeetingCount" var="MeetingCountExport"/>
	          	<div class="btn_group">
	                <button type="submit" class="btn btn-default main_color_btn search_btn col-sm-1 col-xs-3" >查询</button>

	                <span  style="float:right;">	      
	                	<a href="${MeetingCountExport }&meetType=${meetType}&meetTheme=${meetTheme}&branchId=${branchId}&seconedId=${seconedId}&startTime=${startTime}&endTime=${endTime}&userName=${userName}" > 
		                   <button type="button" class="btn btn-default col-sm-11 col-xs-4">
								<img src="/images/import_icon.png"/>
								导出到excel
		                  </button>
                      	</a>
	                </span> 
	            </div>
	             
                
            </form>
        	<div class="content_table_container">
                <table class="content_table">
                    <thead class="table_title">
                        <tr>
                        	<th>二级党组织</th>
                            <th>党支部</th>
                            <th>姓名</th>
                            <th>会议类型</th>
                            <th>开展主题</th>
                            <th>党支部主题</th>
                            <th>开展时间</th>
                            <th>开展地点</th>
                            <th>主持人</th>
                            <th>联系人</th>
                            <th>联系人电话</th>
                            <th>任务状态</th>
                            <th>审核人</th>
                        </tr>
                    </thead>
                    <tbody class="table_info">
                   	    <c:forEach items="${list}" var="info" varStatus="status">
	                        <tr>
	                        	<td data-label="二级党组织">${info.org_names }</td>
	                            <td data-label="党支部">${info.org_nam }</td>
	                            <td data-label="姓名">${info.user_name }</td>
	                            <td data-label="会议类型">${info.meeting_type }</td>
	                            <td data-label="开展主题">${info.meeting_theme }</td>
	                            <td data-label="党支部主题">${info.meeting_theme_secondary }</td>
	                            <td data-label="开展时间" class="publish_time start_time"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${info.start_time }" /></td>
	                            <td data-label="开展地点">${info.place }</td>
	                            <td data-label="主持人">${info.host }</td>
	                            <td data-label="联系人">${info.contact }</td>
	                            <td data-label="联系人电话">${info.contact_phone }</td>
	                            <td data-label="任务状态">
                                    <c:if test="${info.task_status == '1'}"> 
	                            		 已提交
									</c:if>
									<c:if test="${info.task_status == '2'}"> 
	                            		 已撤回
									</c:if>
									<c:if test="${info.task_status == '3'}"> 
	                            		被驳回
									</c:if>
									<c:if test="${info.task_status == '4'}"> 
	                            		 已通过
									</c:if>
									<c:if test="${info.task_status == '5'}"> 
	                            		 已指派
									</c:if>
									<c:if test="${info.task_status == '6'}"> 
	                            		 未检查
									</c:if>
									<c:if test="${info.task_status == '7'}"> 
	                            		 已检查
									</c:if>
	                            </td>
	                            <td data-label="审核人">${info.auditor }</td>
	                        </tr>
                        </c:forEach>
                    </tbody>
                </table>
                 
            </div>
           
   <!-- -----------------------分页------------------------- -->
 
				<div class="pagination_container">
			        <ul class="pagination" id="page"></ul>
			        <div class="pageJump">
			        	<input class='current_page' type="hidden" value="${pageNo}"/>
			            <p>共<span class="total_page">${totalPage }</span>页</p>
			            <portlet:actionURL name="/PageNoMVCActionCommand" var="pageNoUrl">
			            	<portlet:param name="startTime" value="${startTime }"/>
			            	<portlet:param name="endTime" value="${endTime }"/>
			            	<portlet:param name="seconedId" value="${seconedId }"/>
			            	<portlet:param name="branchId" value="${branchId }"/>
			            	<portlet:param name="meetTheme" value="${meetTheme }"/>
			            	<portlet:param name="meetType" value="${meetType }"/>
			            	<portlet:param name="userName" value="${userName }"/>
						</portlet:actionURL>
			            <form action="${pageNoUrl }" id="getPageNo" method="post">
			                <input type="hidden" id="pageNo" name="pageNo" value=""/>
			                <span>跳转到第</span>
			                <input type="text" id="jumpPageNo" name="jumpPageNo"/>
			                <span>页</span>
			                <button type="submit" class="button">确定</button>
			            </form>
			        </div>
				 </div>
				 <script type="text/javascript">
				 	
				 	//分页
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
				         num: parseInt(pages), //页码数
				         startnum: parseInt(currentPage), //指定页码
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
</body>