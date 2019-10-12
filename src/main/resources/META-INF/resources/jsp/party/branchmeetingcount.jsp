<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/init.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<head>
	<title>二级党委会议统计</title>
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
<portlet:resourceURL id="/hg/assigned" var="assigned"/>
<portlet:resourceURL id="/hg/getMeetingTypeAndTheme" var="getMeetingTypeAndTheme"/>
<script type="text/javascript">
 /* function getBranch(seconedId){
	 console.log(11111);
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
}  */
$(function(){
	   var orgId="${seconedId}";
	   var meetTheme="${theme}";
	   var meetType="${meetType}";
	   var branchId="${branchId}";
	   var meetTheme="${meetTheme}";
	   var seconedId="${seconedId}";
	   var url="${assigned}";
	   var data={paramType:"sconedParty"};
	  /*  $.ajax({
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
	   }); */
	  
	   /* $(".sconed_party").change(function(){
		   $(".party_branch").empty();
		   $(".party_branch").append('<option value="">--选择--</option>')
		   var url="${assigned}";
		   var pid=$(".sconed_party").val();
		   var data={paramType:"partyBranch",pid:pid};
		   $.ajax({
			   url:url,
	 		   data:data,
	 		   dataType:"json",
	 		   success:function(result){
	 			  for(var i=0;i<result.length;i++){
			        	 $(".party_branch").append('<option value="'+result[i].org_id+'">'+result[i].org_name+'</option>')
			         }
	 	          }
	 		   });
	   }); */
	    	   
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
     /*   getBranch(seconedId);    */
});	   
	   
	   
</script>
</head>
<body>
      <div class="content_title" style="margin-bottom:30px;">
                  会议统计
         </div> 
		<div class="content_form">
        	<portlet:actionURL var="findURL" name="/meeting/FindRender">
			</portlet:actionURL>
           	<form class="form-horizontal" role="form" action="${findURL }" method="post">
	           	<div class="form-group">
	           	
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
	           	    
	           	  <!--    <div class="col-sm-6 col-xs-12">
	                    <span class="col-sm-3 col-xs-3 control-label" >二级党组织</span>
	                    <div class="col-sm-9 col-xs-9">
						 <select class="form-control sconed_party"  name="seconedId" id="seconedId">
		                   <option value="">请选择</option>
		                 </select> 
	                    </div>
	                </div> -->
	             
	              <%--  <div class="col-sm-6 col-xs-12">
	                    <span class="col-sm-3 col-xs-3 control-label">党支部</span>
	                    <div class="col-sm-9 col-xs-9">
	                        <input type="hidden" class="party_branch_id" value="${branchId}">
							<select class="form-control party_branch"  name="branchId" id="branchId">
								
		                    </select> 
	                    </div>
	                </div>     
	           	             --%>     	           	                
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
	                </div>
	                      	
	            </div>	
	            <portlet:resourceURL id="/hg/MeetingCountExport" var="MeetingCountExport"/>
	          	<div class="btn_group">
	                <button type="submit" class="btn btn-default main_color_btn search_btn col-sm-1 col-xs-3" >查询</button>

	                <span  style="float:right;">	      
	                	<a href="${MeetingCountExport }&meetType=${meetType}&meetTheme=${meetTheme}&branchId=${branchId}&seconedId=${seconedId}&startTime=${startTime}&endTime=${endTime}" > 
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
                            <th>会议类型</th>
                            <th>开展主题</th>
                            <th>党支部主题</th>
                            <th>开展时间</th>
                            <th>发布时间</th>
                            <th>开展地点</th>
                            <th>主持人</th>
                            <th>联系人</th>
                            <th>联系人电话</th>
                            <th>任务状态</th>
                            <th>审核人</th>
                          <!--  <th>检查人</th> -->
                            <th>抽查人</th>
                             <th>应到人数</th>
                            <th>实到人数</th>
                            <th>请假人员</th>
                            <th>出勤率</th>
                           <!--  <th>备注</th> -->
                        </tr>
                    </thead>
                    <tbody class="table_info">
                   	    <c:forEach items="${list}" var="info" varStatus="status">
	                        <tr>
	                            <td data-label="二级党组织" class="Party_name">	                           
	                                <c:if test="${not empty info.second_name }"> 
	                            	 ${info.second_name }
									</c:if>
									 <c:if test="${ empty info.second_name }"> 
	                            	  ${info.branch_name }
									</c:if>
	                            </td>
	                            <td data-label="党支部">                           
	                                <c:if test="${not empty info.second_name }"> 
	                            	 ${info.branch_name }
									</c:if>
	                            </td>
	                            <td data-label="会议类型">${info.meeting_type }</td>
	                            <td data-label="开展主题">${info.meeting_theme }</td>
	                            <td data-label="党支部主题">${info.meeting_theme_secondary }</td>
	                            <td data-label="开展时间" class="publish_time start_time"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${info.start_time }" /></td>
	                            <td data-label="发布时间" class="publish_time"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${info.release_time }" /></td>
	                            <td data-label="开展地点">${info.place }</td>
	                            <td data-label="主持人">${info.host }</td>
	                            <td data-label="联系人">${info.contact }</td>
	                            <td data-label="联系人电话">${info.contact_phone }</td>
	                            <td data-label="任务状态">
                                    <c:if test="${info.plan_state == '1'}"> 
	                            		 已提交
									</c:if>
									<c:if test="${info.plan_state == '2'}"> 
	                            		 已撤回
									</c:if>
									<c:if test="${info.plan_state == '3'}"> 
	                            		被驳回
									</c:if>
									<c:if test="${info.plan_state == '4'}"> 
	                            		 已通过
									</c:if>
									<c:if test="${info.plan_state == '5'}"> 
	                            		 已指派
									</c:if>
									<c:if test="${info.plan_state == '6'}"> 
	                            		 未检查
									</c:if>
									<c:if test="${info.plan_state == '7'}"> 
	                            		 已检查
									</c:if>
	                            </td>
	                            <td data-label="审核人">${info.auditor }</td>
	                           <%--  <td data-label="检查人">${info.check_person_name }</td> --%>
	                            <td data-label="抽查人">${info.check_person_org_name }</td>
	                            <td data-label="应到人数">${info.shoule_persons }</td>
	                            <td data-label="实到人数">${info.actual_persons }</td>
	                            <td data-label="请假人员">${info.leave_persons }</td>
	                            <td data-label="出勤率">${info.attendance }</td>
	                           <%--  <td data-label="备注" class="note">${info.note }</td> --%>
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