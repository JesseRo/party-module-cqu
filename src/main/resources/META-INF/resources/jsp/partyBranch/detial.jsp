<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/init.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title>计划详情</title>
   <!--  <link rel="stylesheet" href="../css/common.css" /> -->
    <link rel="stylesheet" href="${basePath}/css/details.css" />
   <!--  <link rel="stylesheet" href="../css/bootstrap.min.css" /> -->
   <style type="text/css">
p.details_title {
    text-align: center;
    font-size: 30px;
}

.details_content_info p{
    max-height: 590px;
    padding-top: 20px;
    font-size: 14px;
    line-height: 28px;
}
.details_content_title .title_time{
	text-align:right;
}
   </style>
</head>

<body>
		<portlet:renderURL var="confirmDetailUrl">
			<portlet:param name="mvcRenderCommandName" value="/hg/confirmDetail"/> 
		</portlet:renderURL>
			<div class="content_title hidden-xs">
                 	消息详情
             </div>
             
             <div class="details_container">
                 <p class="details_title">${detail.meeting_theme }</p>
                 <div class="details_content">
                     <div class="details_content_title">
                         <p class="col-sm-6 col-xs-12"><span>会议类型</span>${detail.meeting_type }</p>
                         <p class="col-sm-6 col-xs-12 title_time"><span>开展时间</span>${fn:substring(detail.start_time,0,19)}—${fn:substring(detail.end_time,0,19)}</p>
                     </div>
                     <div class="details_content_info">${detail.content }               
			              <portlet:resourceURL id="/hg/detailPage" var="detailPage"/>
			              <div class="attachment">
			                 <a class="left" href="${detailPage }&url=${detail.attachment_url}&name=${detail.attachment_name}">${detail.attachment_name}</a>
			              </div>
			              <br>
			              <c:choose>
			              	<c:when test="${org == 'secondary' }">
			              	<div class='col-sm-10 col-xs-12'>
			              		<span class='col-sm-3 col-xs-3 control-label'>是否部署</span>
			              		<div class='col-sm-5 col-xs-9'>				              		
				              		<input  type="radio" name="sendTo" value="branch">是 &nbsp;&nbsp;
				              		<input  type="radio" name="sendTo" value="secondary">否
				              	</div>
			              	</div>
			              	</c:when>
			              </c:choose>
			
				  		  <div class="btn_group">
				  		     <c:choose>
			              		<c:when test="${detail.send_to == 't' or detail.send_to == 'f'}">
				                	<button class="btn btn-default btn-lg main_color_btn" onclick="history.go(-1)">确定</button>
			                	</c:when>
			                	<c:otherwise>
				                	<button class="btn btn-default btn-lg main_color_btn main_color_btn_">确定</button>
			                	</c:otherwise>
		                	</c:choose> 
				          </div>
                 	</div>
             	</div>
              
   
          </div>


	<script type="text/javascript">
	     var org="${org}";
	     console.log(org);
		 $(".main_color_btn_").click(function(){
			 var url = "${confirmDetailUrl}&informId=${detail.inform_id}&informStatus=${detail.task_status}";
			// var sendto = $('#sendTo').val();
			 var sendto =$('input:radio:checked').val();
			 if(sendto){
				 url += "&sendto=" + sendto;
				 window.location.href=url;
			 }else if(!sendto && org==='secondary'){
				    window.history.back(); 	
			 }else {
				 window.location.href=url; 
			 }
			
		 });
	 </script>

</body>

</html>