<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/init.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title>个人评分详情</title>
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


			<div class="content_title hidden-xs" >
                 	评分详情
             </div>
             
             <div class="details_container">
             	 <p class="details_title"></p>
                 <div class="details_content">
                     <div class="details_content_title">个人评分详情
                     </div>
                     <div class="details_content_info">
                     	<p class="col-sm-6 col-xs-12"><span>会议类型:</span>${meetingType}</p>
                         <p class="col-sm-6 col-xs-12"><span>会议主题:</span>${meetingTheme}</p>
                     	 <p class="col-sm-6 col-xs-12"><span>会议评分:</span>${commentScore} 分</p>
                         <p class="col-sm-6 col-xs-12"><span>评论时间:</span>${fn:substring(commentTime,0,19)}</p>
                         
                         <p class="col-sm-6 col-xs-12"><span>评分维度1:</span>${commentScore1}</p>
                         <p class="col-sm-6 col-xs-12"><span>评分维度2:</span>${commentScore2}</p>
                         <p class="col-sm-6 col-xs-12"><span>评分维度3:</span>${commentScore3}</p>
                         <p class="col-sm-6 col-xs-12"><span>评分维度4:</span>${commentScore4}</p>
                 	 </div>
                 	 <br>
			              <%--  <div class="details_content_info">${meetingComment.comments_content } --%>
					 <div class="btn_group">
		                	<button class="btn btn-default btn-lg main_color_btn"> 返回 </button>
		             </div>
             	</div>
          </div>


	<script type="text/javascript">
		$(function(){
			$(".modal-backdrop").css("display","none");
		});
	
		 $(".main_color_btn").click(function(){
			 window.history.back();
			 /* window.location.href="/task"; */
		 });
		 
		 
	 </script>

</body>

</html>